package de.tarent.challenge.store.cart;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new LinkedList<>();
    
    private boolean checkout = false;
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public boolean isCheckout() {
        return checkout;
    }
    
    public void setCheckout(boolean checkout) {
        this.checkout = checkout;
    }
    
    public long getTotalPrice() {
        return items.stream()
                .mapToLong(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }
    
}
