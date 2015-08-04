package com.naelteam.glycemicindexmenuplanner.dao;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Section;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDbKeys;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class ProductDao implements CouchDbKeys{

    private final static String TAG = ProductDao.class.getSimpleName();

    public static final String TITLE = "title";
    public static final String VALUE = "value";
    public static final String DESCRIPTION = "description";
    public static final String THUMBNAILURL = "thumbnailurl";
    public static final String IMAGESURL = "imagesurl";
    public static final String SECTIONS = "sections";

    private CouchDbManager dbManager = CouchDbManager.getInstance();

    public List<Product> listAllProducts(){
        Log.d(TAG, "List all products");
        long timeIn = System.currentTimeMillis();

        try {
            QueryEnumerator enumerator = dbManager.list();
            List<Product> products = new ArrayList<>();
            while (enumerator.hasNext()){
                QueryRow queryRow = enumerator.next();
                Map<String, Object> properties = (Map) queryRow.getValue();
                products.add(loadProductFromProperties(properties));
            }
            Log.d(TAG, "List all products size = " + products.size() + ", done in " + (System.currentTimeMillis() - timeIn) + " ms");
            return products;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on list all products", e);
        }
        return null;
    }

    public Product fetchProduct(String id){
        Map<String, Object> properties = dbManager.getDocument(id);
        return loadProductFromProperties(properties);
    }

    public boolean insertProducts(List<Product> products) {
        Log.d(TAG, "Insert Products");
        long timeIn = System.currentTimeMillis();
        try {
            List<Map<String, Object>> propertiesList = new ArrayList<>();
            for (Product product:products){
                propertiesList.add(storeProperties(product));
            }
            dbManager.insertBatch(propertiesList, null);
            Log.d(TAG, "Insert Products size = " + products.size() + ", done in " + (System.currentTimeMillis() - timeIn) + " ms");

            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on insert products", e);
        }
        return false;
    }

    public boolean insertProduct(Product product) {
        try {
            Map<String, Object> properties = storeProperties(product);
            String[] doc = dbManager.insert(properties, null);
            product.setId(doc[0]);
            product.setRevId(doc[1]);
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on insert product", e);
        }
        return false;
    }

    public boolean updateProduct(Product product) {
        try {
            Map<String, Object> properties = storeProperties(product);
            dbManager.update(product.getId(), properties, null);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on update product", e);
        }
        return false;
    }

    public boolean deleteProduct(Product product) {
        try {
            return dbManager.delete(product.getId());
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on delete product", e);
            }
        return false;
    }


    private Map<String, Object> storeProperties(Product product){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(TITLE, product.getTitle());
        properties.put(VALUE, product.getValue());
        properties.put(DESCRIPTION, product.getDescription());
        properties.put(THUMBNAILURL, product.getThumbnailUrl());

        final List<Section> sections = product.getSections();
        if (sections != null && sections.size() > 0){
            List<Map<String, Object>> sectionPropertiesList = storeSections(sections);
            if (sectionPropertiesList != null){
                properties.put(SECTIONS, sectionPropertiesList);
            }
        }
        return properties;
    }

    private List<Map<String, Object>> storeSections(List<Section> sections){
        List<Map<String, Object>> sectionPropertiesList = new ArrayList<Map<String, Object>>();
        for (Section section:sections){
            Map<String, Object> subProperties = new HashMap<String, Object>();
            subProperties.put(TITLE, section.getTitle());
            subProperties.put(DESCRIPTION, section.getDescription());
            subProperties.put(IMAGESURL, section.getImagesUrlStr());
            if (section.getSections() != null && section.getSections().size() > 0){
                List<Map<String, Object>> subSectionPropertiesList = storeSections(section.getSections());
                if (subSectionPropertiesList != null){
                    subProperties.put(SECTIONS, subSectionPropertiesList);
                }
            }
            sectionPropertiesList.add(subProperties);
        }
        return sectionPropertiesList;
    }

    private Product loadProductFromProperties(Map<String, Object> properties){
        Product product = new Product();
        product.setId((String) properties.get(ID));
        product.setRevId((String) properties.get(REV_ID));
        product.setTitle((String) properties.get(TITLE));
        product.setValue((String) properties.get(VALUE));
        product.setDescription((String) properties.get(DESCRIPTION));
        product.setThumbnailUrl((String) properties.get(THUMBNAILURL));

        Object sectionsObj = properties.get(SECTIONS);
        if (sectionsObj != null){
            List<Section> sections = loadSectionsFromProperties((List<Map<String, Object>>) sectionsObj);
            if (sections != null){
                product.setSections(sections);
            }
        }
        return product;
    }

    private List<Section> loadSectionsFromProperties(List<Map<String, Object>> properties){
        List<Section> sections = null;

        if (properties != null){
            sections = new ArrayList<Section>();
            for (Map<String, Object> property:properties){
                Section section = new Section();
                section.setTitle((String) property.get(TITLE));
                section.setImagesUrlStr((String) property.get(IMAGESURL));
                section.setDescription((String) property.get(DESCRIPTION));

                Object sectionsObj = property.get(SECTIONS);
                if (sectionsObj != null){
                    List<Section> subSections = loadSectionsFromProperties((List<Map<String, Object>>)sectionsObj);
                    if (subSections != null){
                        section.setSections(subSections);
                    }
                }
                sections.add(section);
            }
        }
        return sections;
    }

}
