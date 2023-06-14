package ru.itis.tdportal.paymentservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.paymentservice.dtos.BankCardDto;
import ru.itis.tdportal.paymentservice.models.entities.BankCard;
import ru.itis.tdportal.paymentservice.models.exceptions.BankCardNotFouldException;
import ru.itis.tdportal.paymentservice.models.mappers.BankCardMapper;
import ru.itis.tdportal.paymentservice.repositories.BankCardRepository;

@Service
@RequiredArgsConstructor
public class BankCardService {

    private final BankCardRepository repository;
    private final BankCardMapper mapper;

    @Transactional
    public void save(Long userId, BankCardDto bankCardDto) {
        BankCard bankCard = repository.findBankCardByCreatorId(userId).orElse(new BankCard());
        mapper.merge(bankCard, bankCardDto);
        bankCard.setCreatorId(userId); // FIXME: 23.04.2023  
        repository.save(bankCard);
    }

    @Transactional(readOnly = true)
    public BankCardDto getBankCardByUserId(Long userId) {
        BankCard bankCard = repository.findBankCardByCreatorId(userId).orElse(new BankCard());
        return mapper.toDto(bankCard);
    }

    @Transactional(readOnly = true)
    public BankCard getBankCardOrThrow(Long userId) {
        return repository.findBankCardByCreatorId(userId)
                .orElseThrow(() -> new BankCardNotFouldException(
                        String.format("Bank card not found for userId = %s", userId))
                );
    }
}
