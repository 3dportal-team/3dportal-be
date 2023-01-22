package ru.itis.tdportal.mainservice.contollers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.mainservice.dtos.InstrumentDto;
import ru.itis.tdportal.mainservice.dtos.MessageDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.dtos.forms.InstrumentFormDto;
import ru.itis.tdportal.mainservice.models.enums.MessageTypes;
import ru.itis.tdportal.mainservice.models.filters.InstrumentFilter;
import ru.itis.tdportal.mainservice.services.InstrumentService;
import ru.itis.tdportal.mainservice.services.ModelService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instruments")
public class InstrumentsController {

    private final ModelService modelService;
    private final InstrumentService instrumentService;

    @GetMapping(value = "/models/model--{generatedName}")
    public ResponseEntity<?> getModelByGeneratedName(
            @PathVariable String generatedName
    ) {
        ModelFileDto modelFileDto = modelService.getModelByGeneratedName(generatedName);
        return ResponseEntity.ok(modelFileDto);
    }

    @PostMapping
    public MessageDto createInstrument(
            @RequestBody InstrumentFormDto instrumentForm
    ) {
        instrumentService.createInstrument(instrumentForm);
        return MessageDto.builder()
                .type(MessageTypes.SUCCESS)
                .message("Инструмент успешно создан!")
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getInstrumentsByFilter(InstrumentFilter filter) {
        return ResponseEntity.ok(instrumentService.getInstrumentsByFilter(filter));
    }

    @PutMapping(value = "/{instrumentId}")
    public ResponseEntity<?> changeInstrumentById(
            @PathVariable Long instrumentId,
            @RequestBody InstrumentDto instrumentDto
    ) {
        instrumentDto.setId(instrumentId);
        return ResponseEntity.ok(instrumentService.changeInstrumentById(instrumentDto));
    }
}
