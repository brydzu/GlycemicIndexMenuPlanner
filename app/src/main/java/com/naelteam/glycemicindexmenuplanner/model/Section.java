package com.naelteam.glycemicindexmenuplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fab on 12/07/15.
 */
public class Section {

    private String title;
    private List<Section> sections;
    private String description;
    private String[] imagesUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        if (sections == null) {
            sections = new ArrayList<Section>();
        }
        this.sections.add(section);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String[] imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
