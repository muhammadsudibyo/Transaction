package com.ecommerce.transaction.controller;

import com.ecommerce.transaction.model.Product;
import com.ecommerce.transaction.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private  final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List getAllProduct(){
        return productService.getAllProduct();
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public Product addProduct(@RequestBody Product product){
       productService.insertProduct(product);
       return product;
    }

    @PostMapping("/update/price/{prodId}+{price}")
    @ResponseStatus(HttpStatus.OK)
    public void addProduct(@PathVariable("prodId") String prodId, @PathVariable("price") BigDecimal price){
        productService.updateProductPrice(prodId, price);
    }

    @GetMapping("/getStock/{prodId}")
    @ResponseStatus(HttpStatus.OK)
    public int getAllProductByUserId(@PathVariable("prodId") String prodId){
      return productService.getStockByProductId(prodId);
    }

    @GetMapping("/get/{prodId}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByProdId(@PathVariable("prodId") String prodId){
        return productService.getProductByProductId(prodId);
    }
}
