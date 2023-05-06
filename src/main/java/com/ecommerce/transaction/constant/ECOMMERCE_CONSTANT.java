package com.ecommerce.transaction.constant;

public class ECOMMERCE_CONSTANT {
    public static final String NO = "N";
    public static final String YES = "Y";

    public static final String TRX_STATUS_CREATED = "CREATED";
    public static final String TRX_STATUS_SUCCESS = "SUCCESS";
    public static final String TRX_STATUS_FAILED = "FAILED";

    public static final String URL_GET_PRODUCT_BY_ID =  "http://localhost:8082/product/get/";
    public static final String URL_REDUCE_PRODUCT_STOCK =  "http://localhost:8082/product/reducestock/";

    public static final String RETURN_OK = "OK";
    public static final String RETURN_ERROR = "ERROR";

    public static final String TOPIC_execute_trx ="execute_Transaction";
    public static final String TOPIC_receive_trx ="status_Transaction";
}
