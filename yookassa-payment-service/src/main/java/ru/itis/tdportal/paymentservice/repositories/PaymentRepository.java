package ru.itis.tdportal.paymentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.paymentservice.models.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
