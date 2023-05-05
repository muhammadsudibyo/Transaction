package com.ecommerce.transaction.controller;

import com.ecommerce.transaction.dto.TransactionInputDto;
import com.ecommerce.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class TransactionController {
    private  final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    TransactionService transactionService;

    //get all transaction
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List getAllTransaction(){
        return transactionService.getAllTransaction();
    }


    //getTransaction By User Id

    //get Transaction By Status

    //execute Transaction
    public TransactionInputDto executeTransaction(@RequestBody TransactionInputDto trx){
        trx.setStatus(transactionService.executePayment(trx.getUserId(),trx.getProductId(),trx.getTotalItems()));
        return trx;
    }
}
