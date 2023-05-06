package com.ecommerce.transaction.dto;

import java.math.BigDecimal;

public class PayTxnDTO {
    public int trxID;
    public String username;
    public BigDecimal totalTrx;
    public String status;

    public int getTrxID() {
        return trxID;
    }

    public void setTrxID(int trxID) {
        this.trxID = trxID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalTrx() {
        return totalTrx;
    }

    public void setTotalTrx(BigDecimal totalTrx) {
        this.totalTrx = totalTrx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
