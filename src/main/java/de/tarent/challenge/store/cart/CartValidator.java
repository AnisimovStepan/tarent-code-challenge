package de.tarent.challenge.store.cart;

import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Service
public class CartValidator {
    
    private static final String CART_WITH_ID_NOT_FOUND_TEMPLATE = "Cart with id \"%d\" is not found.";
    private static final String CART_HAVE_NOT_ITEMS = "Cart have not items.";
    
    public void validate(Cart cart) {
        StringJoiner sj = new StringJoiner(" ");
        
        if (cart.getItems().isEmpty()) {
            sj.add(CART_HAVE_NOT_ITEMS);
        }
        
        if (sj.length() != 0) {
            throw new RuntimeException(sj.toString());
        }
    }
    
    public String notFoundMessage(long id){
        return String.format(CART_WITH_ID_NOT_FOUND_TEMPLATE, id);
    }
    
}
