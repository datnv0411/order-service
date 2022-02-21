package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.Cart;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.repository.CartProductRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.CartRepository;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

import javax.transaction.Transactional;
import java.util.List;

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
}