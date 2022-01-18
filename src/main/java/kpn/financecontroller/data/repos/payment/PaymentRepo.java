package kpn.financecontroller.data.repos.payment;

import kpn.financecontroller.data.entities.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentEntity, Long> {
}
