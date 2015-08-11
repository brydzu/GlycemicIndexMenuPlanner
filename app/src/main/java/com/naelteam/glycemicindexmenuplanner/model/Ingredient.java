package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 */
public class Ingredient implements Parcelable{

    private String name;
    private String amount;
    private Unit unit;
    private String productId;
    private String notes;

    public Ingredient(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        amount = in.readString();
        unit = Unit.valueOf(in.readString());
        productId = in.readString();
        notes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(amount);
        parcel.writeString(unit.toString());
        parcel.writeString(productId);
        parcel.writeString(notes);
    }

    public String htmlText() {
        StringBuilder txt = new StringBuilder("");
        if (!TextUtils.isEmpty(name)){
            txt.append("<b>" + name + "</b><br>");
        }
        if (!TextUtils.isEmpty(amount)){
            txt.append("<b>" + amount + "</b>&nbsp;" + unit + "<br>");
        }if (!TextUtils.isEmpty(notes)){
            txt.append("<i>" + notes + "</i>");
        }
        return txt.toString();
    }
}
