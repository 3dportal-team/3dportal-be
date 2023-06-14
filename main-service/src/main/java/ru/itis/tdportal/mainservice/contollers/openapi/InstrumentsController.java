package ru.itis.tdportal.mainservice.contollers.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.mainservice.dtos.InstrumentDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.dtos.forms.InstrumentFormDto;
import ru.itis.tdportal.mainservice.models.filters.InstrumentFilter;
import ru.itis.tdportal.mainservice.services.InstrumentService;
import ru.itis.tdportal.mainservice.services.ModelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instruments")
public class InstrumentsController {

    private final ModelService modelService;
    private final InstrumentService instrumentService;

    @GetMapping(value = "/models/model--{generatedName}") // FIXME: 12.03.2023 
    public ModelFileDto getModelByGeneratedName(@PathVariable String generatedName) {
        return modelService.getModelByGeneratedName(generatedName);
    }

    @PostMapping
    public Boolean createInstrument(@RequestBody InstrumentFormDto instrumentForm) {
        return instrumentService.createInstrument(instrumentForm);
    }

    @GetMapping
    public List<InstrumentDto> getInstrumentsByFilter(InstrumentFilter filter) {
        return instrumentService.getInstrumentsByFilter(filter);
    }

    @PutMapping(value = "/{instrumentId}")
    public InstrumentDto changeInstrumentById(
            @PathVariable Long instrumentId,
            @RequestBody InstrumentDto instrumentDto
    ) {
        instrumentDto.setId(instrumentId);
        return instrumentService.changeInstrumentById(instrumentDto);
    }
}
