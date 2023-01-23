package ru.itis.tdportal.mainservice.contollers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tdportal.mainservice.dtos.OrderBatchDto;
import ru.itis.tdportal.mainservice.services.OrderBatchService;

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
}
