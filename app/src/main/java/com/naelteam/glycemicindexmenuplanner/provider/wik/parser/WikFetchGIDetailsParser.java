package com.naelteam.glycemicindexmenuplanner.provider.wik.parser;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Section;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;

/**
 * Created by fab on 05/07/15.
 */
public class WikFetchGIDetailsParser {

    private static final String FIRST_HEADING_TAG = "firstHeading";
    private static final String THUMBNAIL_TAG = "infobox";
    private static final String HEADLINE_TAG = "mw-headline";
    private static final String DEFAULT_CONTENT_TAG = "mw-content-text";
    private static final String CONTENT_TAG = "content";

    private static final String EDIT_TAG = "\\[edit\\]";
    private static final String GALLERY_TAG = "gallery";

    private static final String TAG = WikFetchGIDetailsParser.class.getSimpleName();
    private static final String SEEALSO_VALUE = "See also";
    private static final String REFERENCES_VALUE = "References";

    public Product parse(String data) {
        Document doc  = Jsoup.parse(new String(data));
        Log.d(TAG, "Connected to data");
        Product product = new Product();

        Elements titleEl = doc.getElementsByClass(FIRST_HEADING_TAG);

        if (titleEl.size() > 0){
            product.setTitle(titleEl.first().text());
        }

        Element contentElement = doc.getElementById(DEFAULT_CONTENT_TAG);

        if (contentElement == null){
            contentElement = doc.getElementById(CONTENT_TAG);
        }

        if (contentElement != null) {

            Elements thumbElems = contentElement.getElementsByClass(THUMBNAIL_TAG);

            if (thumbElems.size() > 0) {
                Element thumbElement = thumbElems.first();
                Elements imgElements = thumbElement.getElementsByTag("img");
                String imgUrl = imgElements.first().attr("src");
                Log.d(TAG, "imgUrl = " + imgUrl);
                product.setThumbnailUrl("https:" + imgUrl);
            }

            Elements childElements = contentElement.children();
            if (childElements.size() > 0) {
                StringBuilder builder = new StringBuilder();
                for (Element childElement : childElements) {
                    if (childElement.id().equals("toc")){
                        break;
                    }
                    if (childElement.tagName().equals("p")) {
                        final String text = childElement.text();
                        Log.d(TAG, "childElement text = " + text);
                        builder.append(text + "\r\n" + "\r\n");
                    }
                }
                builder.append("<a href='http://www.google.com'>http://www.google.com</a>");
                product.setDescription(builder.toString());
            }

            Elements sectionElements = contentElement.getElementsByTag("h2");
            if (sectionElements!=null){
                Section section = null;
                for (Element sectionElement:sectionElements){

                    Elements titleElements = sectionElement.getElementsByClass(HEADLINE_TAG);
                    if (titleElements.size() > 0){
                        String text = titleElements.first().text();
                        if (discardTags(text)){
                            break;
                        }
                        section = new Section(text);
                    }
                    Element nextElementSibling = sectionElement.nextElementSibling();

                    Section subSection = null;
                    while ((nextElementSibling != null) && (!nextElementSibling.tagName().equals("h2"))) {
                        if (nextElementSibling.tagName().equals("h3")) {
                            subSection = new Section();
                            subSection.setTitle(filterText(nextElementSibling.text()));
                            section.addSection(subSection);
                        } else if (nextElementSibling.tagName().equals("p")) {
                            String text = nextElementSibling.text();
                            if (subSection != null) {
                                String description = subSection.getDescription();
                                if (description != null && (!description.isEmpty())) {
                                    subSection.setDescription(description + "\r\n" + "\r\n" + text);
                                } else {
                                    subSection.setDescription(text);
                                }
                            } else {
                                String description = section.getDescription();
                                if (description != null && (!description.isEmpty())) {
                                    section.setDescription(description + "\r\n" + "\r\n" + text);
                                } else {
                                    section.setDescription(text);
                                }
                            }
                        } else {
                            Set<String> classNames = nextElementSibling.classNames();
                            if (classNames.contains(GALLERY_TAG)) {
                                Elements imgSubElements = nextElementSibling.getElementsByTag("img");
                                String[] imgSubUrls = new String[imgSubElements.size()];
                                int count = -1;
                                for (Element imgSubEl : imgSubElements) {
                                    String imgSubUrl = imgSubEl.attr("src");
                                    Log.d(TAG, "imgSubUrl = " + imgSubUrl);
                                    imgSubUrls[++count] = "https:" + imgSubUrl;
                                }
                                if (subSection != null) {
                                    subSection.setImagesUrl(imgSubUrls);
                                } else {
                                    section.setImagesUrl(imgSubUrls);
                                }
                            }
                        }
                        nextElementSibling = nextElementSibling.nextElementSibling();
                    }
                    if (section != null){
                        product.addSection(section);
                    }
                }
            }
        }

        return product;
    }

    private String filterText(String text) {
        return text.replaceAll(EDIT_TAG, "");
    }

    private boolean discardTags(String text){
        if (text.equals(SEEALSO_VALUE) || text.equals(REFERENCES_VALUE)){
            return true;
        }
        return false;
    }
}
