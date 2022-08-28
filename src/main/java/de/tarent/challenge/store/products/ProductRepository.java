package de.tarent.challenge.store.products;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findBySku(String sku);
    List<Product> findAll();

}
