package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {
}
