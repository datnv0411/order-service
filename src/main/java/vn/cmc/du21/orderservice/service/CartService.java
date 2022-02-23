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

    @Transactional
    public void createCart(long userId){
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setCartId(0);
        cartRepository.save(newCart);
    }

    @Transactional
    public Cart findCart (long userId){
        return cartRepository.findByUserId(userId).orElse(null);
    }

    @Transactional
    public void addProduct (CartProduct cartProduct){
        if(cartProductRepository.findCartProductByCartProductId(cartProduct.getCartProductId()) == null){
            cartProductRepository.save(cartProduct);
        }else {
            cartProduct.setQuantity(cartProduct.getQuantity() +
                    cartProductRepository.findCartProductByCartProductId(cartProduct.getCartProductId()).getQuantity());
            cartProductRepository.save(cartProduct);
        }

    }
    @Transactional
    public void removeProduct(CartProductId cartProductId) throws Throwable {
        CartProduct checkfound = cartProductRepository.findByCartProductId(cartProductId).orElseThrow(
                () -> {
                    throw new RuntimeException("product does not exist !!!");
                }
        );
        cartProductRepository.delete(checkfound);
    }
    @Transactional
    public void removeAll(long cartId){
        cartProductRepository.deleteAllByCartProductId_CartId(cartId);
    }
    @Transactional
    public List<CartProduct> findAllByCartId (long cartId){
        return cartProductRepository.findAllByCartProductId_CartId(cartId);
    }
}
