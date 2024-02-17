package com.dingsai.dingsaibackend.model.entity;

public class Order {
    private String outTradeNo;
    private String productCode;
    private double totalAmount;
    private String subject;
    private String body;

    public Order(String outTradeNo, String productCode, double totalAmount, String subject, String body) {
        this.outTradeNo = outTradeNo;
        this.productCode = productCode;
        this.totalAmount = totalAmount;
        this.subject = subject;
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

