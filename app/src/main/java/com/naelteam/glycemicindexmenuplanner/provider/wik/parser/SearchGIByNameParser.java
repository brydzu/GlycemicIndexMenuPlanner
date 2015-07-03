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

public class SearchGIByNameParser implements Parser<List<GlycemicIndex>> {

    public final static String TAG = SearchGIByNameParser.class.getSimpleName();
    private final static String NO_RESULT_TAG = "searchdidyoumean";
    private final static String EXISTING_RESULT_TAG = "mw-search-exists";

    @Override
    public List<GlycemicIndex> parse(String data) {
        Document doc  = Jsoup.parse(new String(data));
        Log.d(TAG, "Connected to [" + data + "]");

        Elements metaElems = doc.getElementsByClass("item-row");
        List<GlycemicIndex> glycemicIndexes=null;

        if (metaElems != null) {
            glycemicIndexes = new ArrayList<GlycemicIndex>();
            for (Element metaElem : metaElems) {
                Elements itemTitle = metaElem.getElementsByClass("item-title");
                Log.d(TAG, "title = " + itemTitle.text());
                Elements itemIG = metaElem.getElementsByClass("item-ig");

                glycemicIndexes.add(new GlycemicIndex(itemTitle.text(), "", itemIG.text()));
            }
        }

        return glycemicIndexes;
    }
}
