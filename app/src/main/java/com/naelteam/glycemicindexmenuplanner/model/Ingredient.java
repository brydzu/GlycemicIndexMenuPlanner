package com.naelteam.glycemicindexmenuplanner.model;

/**
 * Created by fg on 29/07/15.
 */
public class Ingredient {

    private String name;
    private String amount;
    private Unit unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String productId;
    private String notes;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
