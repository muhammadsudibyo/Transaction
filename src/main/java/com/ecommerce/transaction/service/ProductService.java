package com.ecommerce.transaction.service;

import com.ecommerce.transaction.constant.ECOMMERCE_CONSTANT;
import com.ecommerce.transaction.model.Product;
import com.ecommerce.transaction.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private  final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductRepo productRepo;

    //get All product
    public List getAllProduct(){
        return productRepo.findAll();
    }

    //insert Product
    public void insertProduct(Product product){
        if (getProductByProductId(product.getProductId()) == null){
            product.setDeleteFlag(ECOMMERCE_CONSTANT.NO);
            productRepo.save(product);
        }
    }

    //update Product
        public void updateProductName(String productId,String productName){
        Product product = this.getProductByProductId(productId);
        product.setProductName(productName);
        productRepo.save(product);
    }
    public void updateProductPrice(String productId,BigDecimal price){
        Product product = this.getProductByProductId(productId);
        product.setPrice(price);
        productRepo.save(product);
    }

    //decerease Product
    public void decreaseStock(String productId,int stock){
        Product product = this.getProductByProductId(productId);
        int stockNow = product.getStock()-stock;
        product.setStock(stockNow);
        productRepo.save(product);
    }


    //get Product by id
    public  Product getProductById (int id){
        return productRepo.findProductById(id);

    }


    //get Product by Product Id
    public  Product getProductByProductId (String productId){
        return productRepo.findProductByProductId(productId);

    }

    //soft delete Product
    public void deleteProduct(String productId){
        Product product = this.getProductByProductId(productId);
        product.setDeleteFlag(ECOMMERCE_CONSTANT.YES);
        productRepo.save(product);
    }

    //Check Stock by product Id
    public  int getStockByProductId (String productId){
        Product product = productRepo.findProductByProductId(productId);
        return  product.getStock();
    }
}
