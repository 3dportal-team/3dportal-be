package ru.itis.tdportal.mainservice.contollers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.common.clients.constants.HttpHeader;
import ru.itis.tdportal.common.models.enums.PaymentStatus;
import ru.itis.tdportal.mainservice.dtos.OrderBatchDto;
import ru.itis.tdportal.mainservice.services.OrderBatchService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderBatchController {

    private final OrderBatchService orderBatchService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Создание черновика заказа на основе корзины ")
    public OrderBatchDto createOrderBatchDraft() {
        return orderBatchService.createOrderBatchDraft();
    }

    @PostMapping("/{idempotenceKey}")
    //TODO: должно быть internal
    public void updateOrderBatchStatus(@PathVariable UUID idempotenceKey,
                                       @RequestParam PaymentStatus status) {
        orderBatchService.updateOrderBatchStatus(idempotenceKey, status);
    }
}
