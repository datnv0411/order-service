package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.Cart;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;
import vn.cmc.du21.orderservice.persistence.internal.repository.CartProductRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.CartRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartProductRepository cartProductRepository;

    @Transactional
    public Cart getMyCart(long userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    @Transactional
    public void updateProductOnCart(List<CartProduct> cartProductList, long userId) {
        Optional<Cart> foundCart = cartRepository.findByUserId(userId);

        if(foundCart.isPresent())
        {
            for (CartProduct item : cartProductList)
            {
                CartProductId itemCartProductId = item.getCartProductId();
                itemCartProductId.setCartId(foundCart.get().getCartId());

                item.setCartProductId(itemCartProductId);
                item.setCart(foundCart.get());
            }

            foundCart.get().setCartProducts(cartProductList);

            cartRepository.save(foundCart.get());
        }
    }
}