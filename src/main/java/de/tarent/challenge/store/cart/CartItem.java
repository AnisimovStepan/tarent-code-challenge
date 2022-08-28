package de.tarent.challenge.store.cart;

import de.tarent.challenge.store.products.Product;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Product product;
    
    private int quantity = 1;
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
