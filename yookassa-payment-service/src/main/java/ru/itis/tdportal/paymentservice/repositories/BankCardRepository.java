package ru.itis.tdportal.paymentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.paymentservice.models.entities.BankCard;

import java.util.Optional;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    Optional<BankCard> findBankCardByCreatorId(Long userId);
}
