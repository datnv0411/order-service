package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {
    Optional<CartProduct> findByCartProductId(CartProductId cartProductId);
    CartProduct findCartProductByCartProductId(CartProductId cartProductId);
    Integer deleteAllByCartProductId_CartId(long cartId);
}
