package de.tarent.challenge.store.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductCatalogRepository productCatalogRepository;

    @Autowired
    public ProductService(ProductCatalogRepository productCatalogRepository) {
        this.productCatalogRepository = productCatalogRepository;
    }

    public List<Product> getProducts() {
        return productCatalogRepository.findAll();
    }

    public Product getProduct(String sku) {
        return productCatalogRepository.findBySku(sku);
    }
    
}
