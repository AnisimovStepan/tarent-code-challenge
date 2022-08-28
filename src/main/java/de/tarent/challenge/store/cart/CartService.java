package de.tarent.challenge.store.cart;

import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    private static final String CART_WITH_ID_IS_CHECKED_OUT_TEMPLATE = "Cart with id \"%d\" is checked out. Changes are blocked.";
    private final CartRepository cartRepository;
    private final ProductService productService;
    
    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Cart getCart(long id) {
        return cartRepository.findOne(id);
    }
    
    @Transactional
    public Cart addCart(Cart cart) {
        cart.setItems(getCartItemsWithExistedProduct(cart));
        return cartRepository.save(cart);
    }
    
    private List<CartItem> getCartItemsWithExistedProduct(Cart cart) {
        List<CartItem> cartItemsWithExistedProducts = new ArrayList<>();
        cart.getItems().forEach(cartItem -> {
            Product product = productService.getProduct(cartItem.getProduct().getSku());
            if (product != null && product.isAvailable()) {
                cartItem.setProduct(product);
                cartItemsWithExistedProducts.add(cartItem);
                
            }
        });
        
        return cartItemsWithExistedProducts;
    }
    
    @Transactional
    public Cart updateCart(Cart cart) {
        if (getCart(cart.getId()).isCheckout()) {
            throw new RuntimeException(String.format(CART_WITH_ID_IS_CHECKED_OUT_TEMPLATE, cart.getId()));
        }
        
        return cartRepository.save(cart);
    }
    
}
