package ru.itis.tdportal.paymentservice.controllers.openapi;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tdportal.paymentservice.services.PaymentService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/{idempotenceKey}")
    @ApiResponses(value =
    @ApiResponse(content = {
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(example = "{\"confirmationUrl\": \"string\", \"type\": \"redirect\"}")
            )
    })
    )
    public Map<String, String> movePaymentToStatusPending(
            @PathVariable UUID idempotenceKey) {
        return paymentService.movePaymentToStatusPending(idempotenceKey);
    }
}
