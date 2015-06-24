package com.naelteam.glycemicindexmenuplanner.provider.mt.parser;

import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.provider.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SearchListGIParser implements Parser<List<GlycemicIndex>> {

    public final static String TAG = SearchListGIParser.class.getSimpleName();

    @Override
    public List<GlycemicIndex> parse(String data) {
        Document doc  = Jsoup.parse(new String(data));

        Elements metaElems = doc.getElementsByClass("item-row");
        List<GlycemicIndex> glycemicIndexes=null;

        if (metaElems != null) {
            glycemicIndexes = new ArrayList<GlycemicIndex>();
            for (Element metaElem : metaElems) {
                Elements itemTitle = metaElem.getElementsByClass("item-title");
                //Log.d(TAG, "title = " + itemTitle.text());
                Elements itemIG = metaElem.getElementsByClass("item-ig-alpha");

                glycemicIndexes.add(new GlycemicIndex(itemTitle.text(), "", itemIG.text()));
            }
        }

        return glycemicIndexes;
    }
}
