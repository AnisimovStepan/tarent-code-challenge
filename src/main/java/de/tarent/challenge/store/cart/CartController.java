package de.tarent.challenge.store.cart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final CartValidator cartValidator;
    
    public CartController(CartService cartService, CartValidator cartValidator) {
        this.cartService = cartService;
        this.cartValidator = cartValidator;
    }
    
    @GetMapping
    public List<Cart> getCarts() {
        return cartService.getCarts();
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable long id) {
        Cart cart = cartService.getCart(id);
        return Optional.ofNullable(cart)
                .orElseThrow(() -> new EntityNotFoundException(cartValidator.notFoundMessage(id)));
    }
    
    @PostMapping
    public Cart addCart(@RequestBody Cart cart) {
        cartValidator.validate(cart);
        return cartService.addCart(cart);
    }
     
    @PutMapping("/{id}")
    public Cart updateProduct(@RequestBody Cart cart, @PathVariable long id) {
        Optional.ofNullable(cartService.getCart(id))
                .orElseThrow(() -> new EntityNotFoundException(cartValidator.notFoundMessage(id)));
    
        cartValidator.validate(cart);
        return cartService.updateCart(cart);
    }
    
}
