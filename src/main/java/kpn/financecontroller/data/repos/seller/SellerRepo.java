package kpn.financecontroller.data.repos.seller;

import kpn.financecontroller.data.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<SellerEntity, Long> {
}
