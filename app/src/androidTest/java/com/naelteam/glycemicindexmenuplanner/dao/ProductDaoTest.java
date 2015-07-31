package com.naelteam.glycemicindexmenuplanner.dao;

import android.test.AndroidTestCase;

import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Section;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fab on 04/07/15.
 */
public class ProductDaoTest extends AndroidTestCase {

    AndroidContext mAndroidContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAndroidContext = new AndroidContext(getContext());
    }

    public void testInsertProduct(){
        try {
            ProductDao dao = new ProductDao();
            Product product = new Product();
            product.setTitle("title");
            product.setThumbnailUrl("http://www.google.com/image.jpg");
            product.setDescription("description");
            product.setValue("value");
            Section section1 = new Section("titleSec");
            section1.setDescription("descriptionSec");
            section1.setImagesUrl(new String[]{"image1", "image2"});
            Section section2 = new Section("titleSec2");
            section2.setDescription("descriptionSec2");
            section2.setImagesUrl(new String[]{"image3", "image4"});
            product.addSection(section1);
            product.addSection(section2);

            dao.insertProduct(product);

            product.setTitle("title2");
            dao.updateProduct(product);

            Product otherProduct = dao.fetchProduct(product.getId());

            //assertEquals(otherProduct.getTitle(), product.getTitle());
            assertEquals(otherProduct.getDescription(), product.getDescription());
            assertEquals(otherProduct.getValue(), product.getValue());
            assertEquals(otherProduct.getThumbnailUrl(), product.getThumbnailUrl());

            assertEquals(otherProduct.getSections().get(0).getTitle(), product.getSections().get(0).getTitle());
            assertEquals(otherProduct.getSections().get(0).getDescription(), product.getSections().get(0).getDescription());
            assertEquals(otherProduct.getSections().get(0).getImagesUrl()[0], product.getSections().get(0).getImagesUrl()[0]);
            assertEquals(otherProduct.getSections().get(0).getImagesUrl()[1], product.getSections().get(0).getImagesUrl()[1]);
            assertEquals(otherProduct.getSections().get(1).getImagesUrl()[0], product.getSections().get(1).getImagesUrl()[0]);
            assertEquals(otherProduct.getSections().get(1).getImagesUrl()[1], product.getSections().get(1).getImagesUrl()[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            CouchDBManager.getInstance().destroy();
        }
    }
}
