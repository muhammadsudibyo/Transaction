package com.ecommerce.transaction.service;

import com.ecommerce.transaction.constant.ECOMMERCE_CONSTANT;
import com.ecommerce.transaction.model.Product;
import com.ecommerce.transaction.model.Transaction;
import com.ecommerce.transaction.repo.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionService {
    private  final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionRepo transactionRepo;




    //Get All Trx
    public List getAllTransaction(){
        return transactionRepo.findAll();
    }

    //get All Trx by Status
    public List getAllTransactionByStatus(String status){
        return transactionRepo.findAllByStatus(status);
    }

    //get trx today


    //create Trx Log
    private Transaction createTrxLog(String userId, String productId, int totalItems){
        RestTemplate restTemplate = new RestTemplate();
        final String url =ECOMMERCE_CONSTANT.URL_GET_PRODUCT_BY_ID +productId;
        Product product = restTemplate.getForObject(url,Product.class);
        Transaction trx = new Transaction();

        trx.setUserName(userId);
        trx.setStatus(ECOMMERCE_CONSTANT.TRX_STATUS_CREATED);
        trx.setCreatedDated(new Timestamp(System.currentTimeMillis()));

        trx.setProductId(product.getProductId());
        trx.setProductName(product.getProductName());
        trx.setPrice(product.getPrice());
        trx.setTotalItems(totalItems);
        trx.setAmount(trx.getPrice().multiply(BigDecimal.valueOf(trx.getTotalItems())));
        transactionRepo.save(trx);
        return trx;
    }

    //create Payment
    public String executePayment(String userId, String productId, int totalItems){
        this.createTrxLog(userId,productId,totalItems);
        //ngehit kafka
        //send trxId +

        return ECOMMERCE_CONSTANT.RETURN_OK;
    }

    //receive payment + update status TrxLog



    //get Transaction by user Id
    public List getAllTransactionByUserId(String userId){
        return transactionRepo.findAllByUserId(userId);
    }
}
