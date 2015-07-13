package com.naelteam.glycemicindexmenuplanner.provider.wik.parser;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.Section;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by fab on 05/07/15.
 */
public class WikFetchGIDetailsParser {

    private static final String THUMBNAIL_TAG = "infobox";
    private static final String TABLE_OF_CONTENTS_TAG = "toc";
    private static final String TAG = WikFetchGIDetailsParser.class.getSimpleName();

    public WikProduct parse(String data) {
        Document doc  = Jsoup.parse(new String(data));
        Log.d(TAG, "Connected to data");
        WikProduct wikProduct = new WikProduct();

        Elements thumbElems = doc.getElementsByClass(THUMBNAIL_TAG);

        if (thumbElems != null) {
            Element thumbElement = thumbElems.first();

            Elements imgElements = thumbElement.getElementsByTag("img");
            String imgUrl = imgElements.first().attr("src");
            Log.d(TAG, "imgUrl = " + imgUrl);
            wikProduct.setThumbnailUrl("https:" + imgUrl);

            Elements siblingElements = thumbElement.parent().siblingElements();
            if (siblingElements != null) {
                StringBuilder builder = new StringBuilder();
                for (Element siblingElement : siblingElements) {
                    final String text = siblingElement.text();
                    Log.d(TAG, "siblingElement text = " + text);
                    builder.append(text + "\r\n");
                }
                wikProduct.setDescription(builder.toString());
            }

            Elements sectionElements = doc.getElementsByTag("h2");
            if (sectionElements!=null){
                Section section = new Section();
                for (Element sectionElement:sectionElements){
                    // look for title
                    Elements titleElements = sectionElement.getElementsByClass("mw-headline");
                    section.setTitle(titleElements.first().text());

                    /*do {
                        if (sectionElement.tagName().equals("h3")){

                            StringBuilder builder = new StringBuilder();
                            Element element = parentElement;

                        }

                        element = element.nextElementSibling();
                    } while ((element != element.lastElementSibling()) && (!element.tagName().equals("h2")));*/

                }
            }
        }

        return wikProduct;
    }
}
