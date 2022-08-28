package de.tarent.challenge.cart;

import de.tarent.challenge.store.cart.Cart;
import de.tarent.challenge.store.cart.CartItem;
import de.tarent.challenge.store.cart.CartRepository;
import de.tarent.challenge.store.cart.CartService;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductRepository;
import de.tarent.challenge.store.products.ProductService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceTest {
    
    private static final String TEST = "test";
    private static final String TEST_2 = "test2";
    private static final String TEST_3 = "test3";
    private static final long ID = 1L;
    
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    private CartService cartService;
    private ProductService productService;
    private CartRepository cartRepository;
    
    @Before()
    public void setUp() {
        productService = new ProductService(mock(ProductRepository.class));
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(cartRepository, productService);
    }
    
    @Test
    public void testUpdateCheckedOutCart() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(String.format(CartService.CART_WITH_ID_IS_CHECKED_OUT_TEMPLATE, ID));
    
        Cart cart = createCart();
        when(cartRepository.findOne(ID)).thenReturn(cart);
    
        cartService.updateCart(cart);
    
        verify(cartRepository, never()).save(cart);
    }
    
    @Test
    public void testAddCart() {
        when(productService.getProduct(TEST_3)).thenReturn(createProduct(true, TEST_3, 1));
        when(productService.getProduct(TEST_2)).thenReturn(createProduct(false, TEST_2, 4));
        
        cartService.addCart(createCart());
        
        ArgumentCaptor<Cart> argumentCaptorCart = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository, times(1)).save(argumentCaptorCart.capture());
        
        Cart expectedCart = argumentCaptorCart.getValue();
        
        List<CartItem> expectedItems = expectedCart.getItems();
        assertEquals(1, expectedItems.size());
        
        CartItem expectedCartItem = expectedItems.get(0);
        assertEquals(TEST_3, expectedCartItem.getProduct().getSku());
    }
    
    private Cart createCart() {
        Cart cart = new Cart();
        cart.setId(ID);
        cart.setCheckout(true);
        cart.setItems(createCartItems());
        
        return cart;
    }
    
    private List<CartItem> createCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2);
        cartItem.setProduct(createProduct(true, TEST, 2));
        cartItems.add(cartItem);
        
        cartItem = new CartItem();
        cartItem.setQuantity(3);
        cartItem.setProduct(createProduct(false, TEST_2, 4));
        cartItems.add(cartItem);
        
        cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(createProduct(true, TEST_3, 1));
        cartItems.add(cartItem);
        
        return cartItems;
    }
    
    private Product createProduct(boolean isAvailable, String sku, long price) {
        Product product = new Product();
        product.setSku(sku);
        product.setAvailable(isAvailable);
        product.setPrice(price);
        
        return product;
    }
    
}
