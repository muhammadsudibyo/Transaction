package com.ecommerce.transaction.repo;

import com.ecommerce.transaction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {

    @Query("select p from Product p where productId = ?1 and deleteFlag = 'N' ")
    public Product findProductByProductId(String productId);

    @Query("select p from Product p where deleteFlag = 'N' ")
    public List findAll();
    public Product findProductById(int id);
}
