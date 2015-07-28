package com.naelteam.glycemicindexmenuplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fab on 03/07/15.
 */
public class WikProduct {
    private String title;
    private String thumbnailUrl;
    private String description;
    private List<WikSection> wikSections;

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WikSection> getWikSections(){
        return wikSections;
    }

    public void addSection(WikSection wikSection){
        if (wikSections == null){
            wikSections = new ArrayList<WikSection>();
        }
        wikSections.add(wikSection);
    }
}
