package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Product implements Parcelable, IGlycemicIndex{

    private String id;
    private String revId;

    private String title;
    private String description;
    private String value;
    private boolean selected;
    private String thumbnailUrl;
    private List<Section> sections;

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product(){

    }

    public Product(String title, String description, String value){
        this.title = title;
        this.description = description;
        this.value = value;
    }

    protected Product(Parcel in) {
        title = in.readString();
        description = in.readString();
        thumbnailUrl = in.readString();
        value = in.readString();
        boolean[] array = new boolean[1];
        in.readBooleanArray(array);
        selected=array[0];
        sections=new ArrayList<>();
        in.readTypedList(sections, Section.CREATOR);
    }

    public String getRevId() {
        return revId;
    }

    public void setRevId(String revId) {
        this.revId = revId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        this.description = mDescription;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String mValue) {
        this.value = mValue;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public List<Section> getSections(){
        return sections;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addSection(Section section){
        if (sections == null){
            sections = new ArrayList<Section>();
        }
        sections.add(section);
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(thumbnailUrl);
        parcel.writeString(value);
        parcel.writeBooleanArray(new boolean[]{selected});
        parcel.writeTypedList(sections);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }


}
