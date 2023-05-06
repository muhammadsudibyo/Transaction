package com.ecommerce.transaction.service;

import com.ecommerce.transaction.constant.ECOMMERCE_CONSTANT;
import com.ecommerce.transaction.dto.PayTxnDTO;
import com.ecommerce.transaction.kafka.KafkaProducerSystem;
import com.ecommerce.transaction.model.Product;
import com.ecommerce.transaction.model.Transaction;
import com.ecommerce.transaction.repo.TransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.annotation.KafkaListener;
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

    @Autowired private KafkaProducerSystem sender;



    //Get All Trx
    public List getAllTransaction(){
        return transactionRepo.findAll();
    }

    //get All Trx by Status
    public List getAllTransactionByStatus(String status){
        return transactionRepo.findAllByStatus(status);
    }



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
    public String executePayment(String userId, String productId, int totalItems)throws JsonProcessingException{
        Transaction trx = this.createTrxLog(userId,productId,totalItems);
        //ngehit kafka
        System.out.println("send executee =========");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(this.prepareDTOSendPayment(trx));
        sender.send(ECOMMERCE_CONSTANT.TOPIC_execute_trx, json);
            //send trxId +
        return ECOMMERCE_CONSTANT.RETURN_OK;
    }

    private PayTxnDTO prepareDTOSendPayment(Transaction trx){
        System.out.println("receive executee =========");
        PayTxnDTO payTxnDTO = new PayTxnDTO();
        payTxnDTO.setTrxID(trx.getId());
        payTxnDTO.setUsername(trx.getUserName());
        payTxnDTO.setTotalTrx(trx.getAmount());
        return  payTxnDTO;
    }

    //receive payment + update status TrxLog
    @KafkaListener(topics = ECOMMERCE_CONSTANT.TOPIC_receive_trx, groupId = "test-consumer-group")
	public void getMessageKafka(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        PayTxnDTO  payTxnDTO = null;
        System.out.println(json);
        try {
            payTxnDTO = objectMapper.readValue(json, PayTxnDTO.class);

        Transaction transaction = transactionRepo.findById(payTxnDTO.getTrxID());
        if (payTxnDTO.getStatus().equalsIgnoreCase(ECOMMERCE_CONSTANT.TRX_STATUS_SUCCESS)){
            transaction.setStatus(ECOMMERCE_CONSTANT.TRX_STATUS_SUCCESS);
            this.reduceStock(transaction.getTotalItems(), transaction.getProductId());
        }else {
            transaction.setStatus(ECOMMERCE_CONSTANT.TRX_STATUS_FAILED);
        }

        transactionRepo.save(transaction);
		System.out.println("yang di lempar " + transaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
	}

    private void reduceStock(int stock, String productId){
        RestTemplate restTemplate = new RestTemplate();
        final String url =ECOMMERCE_CONSTANT.URL_REDUCE_PRODUCT_STOCK +productId+"+"+stock;
        restTemplate.getForObject(url,Product.class);
    }


    //get Transaction by user Id
    public List getAllTransactionByUserId(String userId){
        return transactionRepo.findAllByUserName(userId);
    }
}
