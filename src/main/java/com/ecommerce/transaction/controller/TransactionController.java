package com.ecommerce.transaction.controller;

import com.ecommerce.transaction.dto.TransactionInputDto;
import com.ecommerce.transaction.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trx")
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
    @GetMapping("/getTrx/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List getAllTransactionByUserId(@PathVariable("userId") String userId){
        return transactionService.getAllTransactionByUserId(userId);
    }

    //get Transaction By Status
    @GetMapping("/getStatus/{status}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List getAllTransactionByStatus(@PathVariable("status") String status){
        return transactionService.getAllTransactionByStatus(status);
    }

    //execute Transaction
    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.OK)
    public TransactionInputDto executeTransaction(@RequestBody TransactionInputDto trx) throws JsonProcessingException {
        trx.setStatus(transactionService.executePayment(trx.getUserId(),trx.getProductId(),trx.getTotalItems()));
        return trx;
    }
}
