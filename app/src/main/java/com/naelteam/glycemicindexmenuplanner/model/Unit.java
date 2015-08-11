package com.naelteam.glycemicindexmenuplanner.model;

/**
 * Created by fg on 04/08/15.
 */
public enum Unit {

    POUND("lb");

    private String code;

    Unit(String code){
        this.code = code;
    }

    public static Unit fromValue(String val){
        for (Unit unit:Unit.values()){
            if (unit.toString().equals(val)){
                return unit;
            }
        }
        return null;
    }

}
