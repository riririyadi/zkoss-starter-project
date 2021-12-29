/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author RIRI
 */
public class MostPopularItem {
    private Long num_of_sold_units;
    private String item_name;
    private Integer num_of_transactions;
    private Integer num_of_countries;

    public Long getNum_of_sold_units() {
        return num_of_sold_units;
    }

    public void setNum_of_sold_units(Long num_of_sold_units) {
        this.num_of_sold_units = num_of_sold_units;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getNum_of_transactions() {
        return num_of_transactions;
    }

    public void setNum_of_transactions(Integer num_of_transactions) {
        this.num_of_transactions = num_of_transactions;
    }

    public Integer getNum_of_countries() {
        return num_of_countries;
    }

    public void setNum_of_countries(Integer num_of_countries) {
        this.num_of_countries = num_of_countries;
    }
    
    
}
