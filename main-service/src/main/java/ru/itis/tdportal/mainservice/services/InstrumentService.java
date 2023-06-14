package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.mainservice.dtos.InstrumentDto;
import ru.itis.tdportal.mainservice.dtos.forms.InstrumentFormDto;
import ru.itis.tdportal.mainservice.models.entities.Instrument;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.exceptions.InstrumentNotFoundException;
import ru.itis.tdportal.mainservice.models.filters.InstrumentFilter;
import ru.itis.tdportal.mainservice.models.mappers.InstrumentMapper;
import ru.itis.tdportal.mainservice.repositories.InstrumentRepository;
import ru.itis.tdportal.mainservice.repositories.specifications.InstrumentSpecification;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final PortalUserService portalUserService;
    private final InstrumentMapper instrumentMapper;
    private final InstrumentSpecification specification;

    @Transactional
    public boolean createInstrument(InstrumentFormDto instrumentForm) {
        PortalUser user = portalUserService.getByIdOrElseThrow(instrumentForm.getAuthorId());

        Instrument instrument = instrumentMapper.toEntity(instrumentForm);
        instrument.setAuthor(user);

        return Objects.nonNull(instrument.getId());
    }

    @Transactional
    public InstrumentDto changeInstrumentById(InstrumentDto instrumentDto) {
        Long instrumentId = instrumentDto.getId();
        Instrument foundInstrument = instrumentRepository.findById(instrumentId).orElseThrow(
                () -> new InstrumentNotFoundException(String.format("Instrument with id: '%s' does not exist!", instrumentId))
        );

        Instrument updatedInstrument = instrumentMapper.merge(foundInstrument, instrumentDto);
        Instrument savedInstrument = instrumentRepository.saveAndFlush(updatedInstrument);

        return instrumentMapper.toDto(savedInstrument);
    }

    @Transactional(readOnly = true)
    public List<InstrumentDto> getInstrumentsByFilter(InstrumentFilter filter) {
        Long userId = filter.getAuthorId();
        if (Objects.nonNull(userId)) {
            portalUserService.getByIdOrElseThrow(userId);
        }

        List<Instrument> instruments = instrumentRepository.findAll(specification.byFilter(filter));
        return instrumentMapper.toListDto(instruments);
    }
}
