package com.naelteam.glycemicindexmenuplanner.model;

import java.util.List;

/**
 * Created by fab on 03/07/15.
 */
public class WikProduct {
    private String thumbnailUrl;
    private String description;
    private List<Section> sections;

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

    public List<Section> getSections(){
        return sections;
    }
}
