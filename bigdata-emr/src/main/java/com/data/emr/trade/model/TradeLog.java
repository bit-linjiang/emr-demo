package com.data.emr.trade.model;

/**
 * @author linjiang
 * @since 2017/2/8
 */
public class TradeLog {
    //时间
    private String time;
    //买家ID
    private String buyerId;
    //订单编号
    private String orderNo;
    //来源
    private String from;
    //是否付款
    private String paid;

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
