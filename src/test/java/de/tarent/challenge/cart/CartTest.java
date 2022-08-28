package de.tarent.challenge.cart;

import de.tarent.challenge.store.cart.Cart;
import de.tarent.challenge.store.cart.CartItem;
import de.tarent.challenge.store.products.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CartTest {
    
    @Test
    public void testGetTotalPrice() {
        Cart cart = createCart();
        assertEquals(cart.getTotalPrice(), 8);
    }
    
    private Cart createCart() {
        Cart cart = new Cart();
        cart.setItems(createCartItems());
        
        return cart;
    }
    
    private List<CartItem> createCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2);
        cartItem.setProduct(createProduct(3));
        cartItems.add(cartItem);
        
        cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(createProduct(2));
        cartItems.add(cartItem);
        
        return cartItems;
    }
    
    private Product createProduct(long price) {
        Product product = new Product();
        product.setPrice(price);
        
        return product;
    }
    
}
