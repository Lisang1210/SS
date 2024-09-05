package com.sc.e_commerce_platform.entity;


/**
 * (Goods)表实体类
 *
 * @author makejava
 * @since 2024-05-26 16:28:08
 */

public class Goods {

    private Long id;
//图片url信息
    private String picture;

    private String title;

    private Float price;
//售出数量
    private String soldNum;
//多少小时内发货
    private String deliveryTime;
//是否支持七天无理由
    private String sevenDaysWithoutReason;
//配送信息
    private String distribution;
//产品种类
    private String kind;
//评分
    private Float mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(String soldNum) {
        this.soldNum = soldNum;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSevenDaysWithoutReason() {
        return sevenDaysWithoutReason;
    }

    public void setSevenDaysWithoutReason(String sevenDaysWithoutReason) {
        this.sevenDaysWithoutReason = sevenDaysWithoutReason;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", soldNum='" + soldNum + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", sevenDaysWithoutReason='" + sevenDaysWithoutReason + '\'' +
                ", distribution='" + distribution + '\'' +
                ", kind='" + kind + '\'' +
                ", mark=" + mark +
                '}';
    }
}

