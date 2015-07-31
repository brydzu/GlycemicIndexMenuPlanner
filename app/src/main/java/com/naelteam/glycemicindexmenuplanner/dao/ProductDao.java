package com.naelteam.glycemicindexmenuplanner.dao;

import com.couchbase.lite.CouchbaseLiteException;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Section;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fg on 30/07/15.
 */
public class ProductDao {

    public static final String TITLE = "title";
    public static final String VALUE = "value";
    public static final String DESCRIPTION = "description";
    public static final String THUMBNAILURL = "thumbnailurl";
    public static final String IMAGESURL = "imagesurl";
    public static final String SECTIONS = "sections";
    private CouchDBManager dbManager = CouchDBManager.getInstance();

    public Product fetchProduct(String id){
        Map<String, Object> properties = dbManager.getDocument(id);
        return loadProductFromProperties(properties);
    }

    public void insertProduct(Product product) throws CouchbaseLiteException {

        Map<String, Object> properties = storeProperties(product);
        String docId = dbManager.insert(properties, null);
        product.setId(docId);
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
        List<Map<String, Object>> sectionPropertiesList = null;
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
