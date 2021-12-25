/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author RIRI
 */
public class DetailSO {
    private Long id, so_id, item_id;
    private Integer line, quantity, price, discount, tax_amount, item_price;

    public Integer getItem_price() {
        return item_price;
    }

    public void setItem_price(Integer item_price) {
        this.item_price = item_price;
    }
    private Date creation_date;
    private String item_code, item_description;

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }
    private Boolean IS_NEW;    
    private Boolean IS_EDIT;

    public Boolean getIS_NEW() {
        return IS_NEW;
    }

    public void setIS_NEW(Boolean IS_NEW) {
        this.IS_NEW = IS_NEW;
    }

    public Boolean getIS_EDIT() {
        return IS_EDIT;
    }

    public void setIS_EDIT(Boolean IS_EDIT) {
        this.IS_EDIT = IS_EDIT;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSo_id() {
        return so_id;
    }

    public void setSo_id(Long so_id) {
        this.so_id = so_id;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(Integer tax_amount) {
        this.tax_amount = tax_amount;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setSo_id(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
