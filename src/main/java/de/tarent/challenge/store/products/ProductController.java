package de.tarent.challenge.store.products;

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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductValidator productValidator;
    
    public ProductController(ProductService productService, ProductValidator productValidator) {
        this.productService = productService;
        this.productValidator = productValidator;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{sku}")
    public Product getProduct(@PathVariable String sku) {
        Product product = productService.getProduct(sku);
        return Optional.ofNullable(product)
                .orElseThrow(() -> new EntityNotFoundException(productValidator.notFoundMessage(sku)));
    }
    
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        productValidator.validate(product);
        return productService.addProduct(product);
    }
     
    @PutMapping("/{sku}")
    public Product updateProduct(@RequestBody Product product, @PathVariable String sku) {
        Optional.ofNullable(productService.getProduct(sku))
                .orElseThrow(() -> new EntityNotFoundException(productValidator.notFoundMessage(sku)));
    
        product.setSku(sku);
        
        productValidator.validate(product);
        return productService.updateProduct(product);
    }
    
}
