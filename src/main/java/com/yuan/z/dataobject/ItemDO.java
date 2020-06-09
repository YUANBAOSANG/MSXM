package com.yuan.z.dataobject;

import java.math.BigDecimal;

public class ItemDO {
    private Integer id;

    private String title;

    private BigDecimal price;

    private String description;

    private String imgUrl;

    private Integer sales;

    public ItemDO(Integer id, String title, BigDecimal price, String description, String imgUrl, Integer sales) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.sales = sales;
    }

    public ItemDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }
}