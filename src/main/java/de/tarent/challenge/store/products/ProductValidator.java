package de.tarent.challenge.store.products;

import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Service
public class ProductValidator {
    
    private static final String PRODUCT_WITH_SKU_NOT_FOUND_TEMPLATE = "Product with sku \"%s\" not found.";
    private static final String PRODUCT_WITH_SKU_ALREADY_EXISTS_TEMPLATE = "Product with sku \"%s\" already exists.";
    private static final String PRODUCT_NAME_IS_EMPTY = "Product name is empty.";
    private static final String PRODUCT_HAVE_NOT_EANS = "Product have not eans.";
    private static final String PRODUCT_HAVE_EMPTY_EANS = "Product have empty's eans.";
    private static final String PRICE_MUST_BE_GREATER_THAN_0 = "Price must be greater than 0.";
    
    private final ProductService productService;
    
    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }
    
    public void validate(Product product) {
        StringJoiner sj = new StringJoiner(" ");

        if (productService.getProduct(product.getSku()) != null) {
            sj.add(String.format(PRODUCT_WITH_SKU_ALREADY_EXISTS_TEMPLATE, product.getSku()));
        }
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            sj.add(PRODUCT_NAME_IS_EMPTY);
        }
        
        if (product.getEans().isEmpty()) {
            sj.add(PRODUCT_HAVE_NOT_EANS);
        }
        
        if (product.getEans().stream().allMatch(s -> s.trim().isEmpty())) {
            sj.add(PRODUCT_HAVE_EMPTY_EANS);
        }
    
        if (product.getPrice() <= 0) {
            sj.add(PRICE_MUST_BE_GREATER_THAN_0);
        }
    
        if (sj.length() != 0) {
            throw new RuntimeException(sj.toString());
        }
    }
    
    public String notFoundMessage(String sku){
        return String.format(PRODUCT_WITH_SKU_NOT_FOUND_TEMPLATE, sku);
    }
    
}
