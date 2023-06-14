package ru.itis.tdportal.paymentservice.controllers.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.paymentservice.dtos.BankCardDto;
import ru.itis.tdportal.paymentservice.services.BankCardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class BankCardController {

    private final BankCardService bankCardService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{userId}/bank-card")
    public void saveBankCard(@PathVariable("userId") Long userId, // TODO: проверка доступа к ресурсу
                             @RequestBody BankCardDto bankCard) {
        bankCardService.save(userId, bankCard);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}/bank-card")
    public BankCardDto getBankCard(@PathVariable("userId") Long userId) { // TODO: проверка доступа к ресурсу
        return bankCardService.getBankCardByUserId(userId);
    }
}
