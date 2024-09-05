package com.sc.e_commerce_platform.entity;

public class Orders {

    private Long id;
    //商品信息
    private Long goodId;
    //地址
    private String address;
    //配送信息
    private String distribution;
    //备注
    private String remarks;
    //所属用户id
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", goodId=" + goodId +
                ", address='" + address + '\'' +
                ", distribution='" + distribution + '\'' +
                ", remarks='" + remarks + '\'' +
                ", userId=" + userId +
                '}';
    }
}
