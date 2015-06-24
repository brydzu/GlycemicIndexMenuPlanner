package com.naelteam.glycemicindexmenuplanner.provider;

/**
 * Created by fab on 03/06/15.
 */
public interface Parser<T> {

    T parse(String data);

}
