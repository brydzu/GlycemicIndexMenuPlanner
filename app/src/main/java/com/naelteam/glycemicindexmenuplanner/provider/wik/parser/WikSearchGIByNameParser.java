package com.naelteam.glycemicindexmenuplanner.provider.wik.parser;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.provider.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WikSearchGIByNameParser implements Parser<String> {

    public final static String TAG = WikSearchGIByNameParser.class.getSimpleName();

    private final static String EXISTING_RESULT_TAG = "mw-search-exists";

    @Override
    public String parse(String data) {
        Document doc  = Jsoup.parse(new String(data));
        Log.d(TAG, "parse - Connected to data");

        String productUrl=null;
        Elements resultElems = doc.getElementsByClass(EXISTING_RESULT_TAG);

        if (resultElems != null) {
            Element resultElement = resultElems.first();

            if (resultElement != null) {
                Elements linkElements = resultElement.getElementsByTag("a");
                productUrl = linkElements.first().attr("href");
                Log.d(TAG, "parse - productUrl = " + productUrl);
            }
        }
        return productUrl;
    }
}
