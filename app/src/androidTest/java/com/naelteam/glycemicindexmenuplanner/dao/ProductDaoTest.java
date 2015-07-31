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
            List<Section> sections = new ArrayList<>();
            Section section1 = new Section("titleSec");
            section1.setDescription("descriptionSec");
            section1.setImagesUrl(new String[]{"image1", "image2"});

            dao.insertProduct(product);

            Product otherProduct = dao.fetchProduct(product.getId());

            assertEquals(otherProduct.getTitle(), product.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            CouchDBManager.getInstance().destroy();
        }
    }
}
