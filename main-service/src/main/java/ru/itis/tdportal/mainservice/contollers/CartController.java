package ru.itis.tdportal.mainservice.contollers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.mainservice.dtos.CartDto;
import ru.itis.tdportal.mainservice.services.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
// TODO: удаление просроченной корзины (TTL)
public class CartController {

    private final CartService cartService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Добавление новой модели в корзину")
    public CartDto addModelToCart(
            @RequestParam("modelId") Long modelId) {
        return cartService.saveModel(modelId);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Получение текущей корзины заказа")
    public CartDto getCurrentOrderBatch() {
        return cartService.getCurrentUserCart();
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Удаление модели из корзины по ее идентификатору")
    public CartDto deleteModelFromCart(
            @RequestParam("modelId") Long modelId) {
        return cartService.deleteModel(modelId);
    }
}
