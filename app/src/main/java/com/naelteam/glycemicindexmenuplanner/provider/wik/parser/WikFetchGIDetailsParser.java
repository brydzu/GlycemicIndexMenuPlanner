package com.naelteam.glycemicindexmenuplanner.provider.wik.parser;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.WikSection;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Set;

/**
 * Created by fab on 05/07/15.
 */
public class WikFetchGIDetailsParser {

    private static final String THUMBNAIL_TAG = "infobox";
    private static final String HEADLINE_TAG = "mw-headline";
    private static final String EDIT_TAG = "Edit";
    private static final String GALLERY_TAG = "gallery";

    private static final String TAG = WikFetchGIDetailsParser.class.getSimpleName();
    private static final String SEEALSO_VALUE = "See also";
    private static final String REFERENCES_VALUE = "References";

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
                    final String text = siblingElement.text().replace(EDIT_TAG, "");
                    Log.d(TAG, "siblingElement text = " + text);
                    builder.append(text + "\r\n");
                }
                wikProduct.setDescription(builder.toString());
            }

            Elements sectionElements = doc.getElementsByTag("h2");
            if (sectionElements!=null){
                WikSection wikSection = null;
                for (Element sectionElement:sectionElements){

                    if (sectionElement.childNodeSize() > 1){
                        Elements titleElements = sectionElement.getElementsByClass(HEADLINE_TAG);
                        String text = titleElements.first().text();
                        if (discardTags(text)){
                            break;
                        }
                        wikSection = new WikSection(text);
                    }
                    Element nextElementSibling = sectionElement.nextElementSibling();

                    WikSection subWikSection = null;
                    while ((nextElementSibling != null) && (!nextElementSibling.tagName().equals("h2"))) {

                        Elements childNodes = nextElementSibling.children();

                        for (Element childNode:childNodes){

                            if (childNode.tagName().equals("h3")) {
                                subWikSection = new WikSection();
                                subWikSection.setTitle(childNode.text().replace(EDIT_TAG, ""));
                                wikSection.addSection(subWikSection);
                            } else if (childNode.tagName().equals("p")) {
                                String text = childNode.text();
                                if (subWikSection != null) {
                                    String description = subWikSection.getDescription();
                                    if (description != null && (!description.isEmpty())) {
                                        subWikSection.setDescription(description + "\r\n" + text);
                                    } else {
                                        subWikSection.setDescription(text);
                                    }
                                } else {
                                    String description = wikSection.getDescription();
                                    if (description != null && (!description.isEmpty())) {
                                        wikSection.setDescription(description + "\r\n" + text);
                                    } else {
                                        wikSection.setDescription(text);
                                    }
                                }
                            } else {
                                Set<String> classNames = childNode.classNames();
                                if (classNames.contains(GALLERY_TAG)) {
                                    Elements imgSubElements = childNode.getElementsByTag("img");
                                    String[] imgSubUrls = new String[imgSubElements.size()];
                                    int count = -1;
                                    for (Element imgSubEl : imgSubElements) {
                                        String imgSubUrl = imgSubEl.attr("src");
                                        Log.d(TAG, "imgSubUrl = " + imgSubUrl);
                                        imgSubUrls[++count] = "https:" + imgSubUrl;
                                    }
                                    if (subWikSection != null) {
                                        subWikSection.setImagesUrl(imgSubUrls);
                                    } else {
                                        wikSection.setImagesUrl(imgSubUrls);
                                    }
                                }
                            }
                        }
                        nextElementSibling = nextElementSibling.nextElementSibling();
                    }
                    if (wikSection != null){
                        wikProduct.addSection(wikSection);
                    }
                }
            }
        }

        return wikProduct;
    }

    private boolean discardTags(String text){
        if (text.equals(SEEALSO_VALUE) || text.equals(REFERENCES_VALUE)){
            return true;
        }
        return false;
    }
}
