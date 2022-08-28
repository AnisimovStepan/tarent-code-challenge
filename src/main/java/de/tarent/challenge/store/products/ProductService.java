package de.tarent.challenge.store.products;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductCatalogRepository productCatalogRepository;
    
    public ProductService(ProductCatalogRepository productCatalogRepository) {
        this.productCatalogRepository = productCatalogRepository;
    }

    public List<Product> getProducts() {
        return productCatalogRepository.findAll();
    }

    public Product getProduct(String sku) {
        return productCatalogRepository.findBySku(sku);
    }
    
    public Product addProduct(Product product) {
        return productCatalogRepository.save(product);
    }
    
    public Product updateProduct(Product product) {
        return productCatalogRepository.save(product);
    }
    
}
