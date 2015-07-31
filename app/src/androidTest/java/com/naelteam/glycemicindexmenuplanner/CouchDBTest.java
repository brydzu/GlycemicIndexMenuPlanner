package com.naelteam.glycemicindexmenuplanner;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.NetworkReachabilityManager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.android.AndroidNetworkReachabilityManager;
import com.couchbase.lite.android.AndroidSQLiteStorageEngineFactory;
import com.couchbase.lite.storage.SQLiteStorageEngineFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fab on 04/07/15.
 */
public class CouchDBTest extends AndroidTestCase {

    AndroidContext mAndroidContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAndroidContext = new AndroidContext(getContext());
    }

    public void testConnect(){
        Manager manager = null;
        try {
            manager = new Manager(mAndroidContext, null);

            Database database = manager.getDatabase("gi_db");

            Document document = database.createDocument();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("key", "3");
            data.put("time", 102313131321d);
            List<String> list = new ArrayList<String>();
            list.add("2");
            data.put("list", list);

            document.putProperties(data);

            String docId = document.getId();
            Document document1 = database.getDocument(docId);
            assertEquals(document1.getProperties().get("key"), "3");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            manager.close();
        }
    }
}
