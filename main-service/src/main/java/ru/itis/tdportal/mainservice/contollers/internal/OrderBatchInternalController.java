package ru.itis.tdportal.mainservice.contollers.internal;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.common.models.enums.PaymentStatus;
import ru.itis.tdportal.mainservice.services.OrderBatchService;

import java.util.UUID;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderBatchInternalController {

    private final OrderBatchService orderBatchService;

    @PostMapping("/{idempotenceKey}")
    public void updateOrderBatchStatus(@PathVariable UUID idempotenceKey,
                                       @RequestParam PaymentStatus status) {
        orderBatchService.updateOrderBatchStatus(idempotenceKey, status);
    }
}
