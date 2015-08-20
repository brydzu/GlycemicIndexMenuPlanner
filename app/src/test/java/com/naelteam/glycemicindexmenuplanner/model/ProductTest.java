package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.naelteam.glycemicindexmenuplanner.BaseTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductTest extends BaseTest{

    public static final String TITLE_P = "titlep";
    public static final String DESCRIPTION_P = "descriptionp";
    public static final String THUMBNAILURLP = "thumbnailurlp";
    public static final String VALUEP = "valuep";
    public static final String TITLE_S = "titleS";
    public static final String DESCRIPTION_S = "descriptionS";
    public static final String IMAGESURL_S = "imagesurlS";
    public static final String SUBTITLE_S = "subtitleS";
    public static final String TITLE_SUB_S = "titleSubS";
    public static final String DESCRIPTION_SUB_S = "descriptionSubS";
    public static final String IMAGESURL_SUB_S = "imagesurlSubS";
    public static final String SUBTITLE_SUB_S = "subtitleSubS";
    private Product sut;

    @Before
    public void setUp()  {
        sut = createProduct();
    }

    private Product createProduct(){
        Product product = new Product();
        product.setTitle(TITLE_P);
        product.setDescription(DESCRIPTION_P);
        product.setSelected(true);
        product.setThumbnailUrl(THUMBNAILURLP);
        product.setValue(VALUEP);
        Section section = new Section();
        section.setTitle(TITLE_S);
        section.setDescription(DESCRIPTION_S);
        section.setImagesUrl(new String[]{IMAGESURL_S});
        product.addSection(section);
        Section subSection = new Section();
        subSection.setTitle(TITLE_SUB_S);
        subSection.setDescription(DESCRIPTION_SUB_S);
        subSection.setImagesUrl(new String[]{IMAGESURL_SUB_S});
        section.addSection(subSection);
        return product;
    }

    @Test
    public void testParcelable(){
        Parcel parcel = Parcel.obtain();
        sut.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        parcel.setDataPosition(0);
        Product product2 = Product.CREATOR.createFromParcel(parcel);

        Assert.assertEquals(TITLE_P, product2.getTitle());
        Assert.assertEquals(DESCRIPTION_P, product2.getDescription());
        Assert.assertEquals(THUMBNAILURLP, product2.getThumbnailUrl());
        Assert.assertEquals(VALUEP, product2.getValue());

        final Section section = product2.getSections().get(0);
        Assert.assertEquals(TITLE_S, section.getTitle());
        Assert.assertEquals(DESCRIPTION_S, section.getDescription());
        Assert.assertEquals(IMAGESURL_S, section.getImagesUrl()[0]);
        Assert.assertEquals(1, section.getSections().size());

        final Section subSection = section.getSections().get(0);
        Assert.assertEquals(TITLE_SUB_S, subSection.getTitle());
        Assert.assertEquals(DESCRIPTION_SUB_S, subSection.getDescription());
        Assert.assertEquals(IMAGESURL_SUB_S, subSection.getImagesUrl()[0]);
    }

    @Test
    public void testEquals(){
        Product product2 = new Product(TITLE_P,DESCRIPTION_P,VALUEP);

        List<Product> productList = new ArrayList<Product>();
        productList.add(sut);
        productList.add(product2);

        Assert.assertEquals(sut.hashCode(), product2.hashCode());
        Assert.assertEquals(true, sut.equals(product2));
        Assert.assertEquals(true, productList.contains(product2));
    }

    @Test
    public void testNotEquals(){
        Product product2 = new Product("othertitle",DESCRIPTION_P,VALUEP);

        Assert.assertTrue(sut.hashCode() != product2.hashCode());
        Assert.assertEquals(false, sut.equals(product2));
    }
}
