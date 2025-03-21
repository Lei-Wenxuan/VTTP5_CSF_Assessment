package vttp.batch5.csf.assessment.server.models;

import java.util.UUID;

public class Payment {
    private String orderId;
    private String payer;
    private String payee;
    private float payment;

    public Payment(String payer, String payee, float payment) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8);
        this.payer = payer;
        this.payee = payee;
        this.payment = payment;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

}
