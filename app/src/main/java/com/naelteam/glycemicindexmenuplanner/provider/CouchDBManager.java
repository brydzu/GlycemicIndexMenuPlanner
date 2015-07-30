package com.naelteam.glycemicindexmenuplanner.provider;

import com.couchbase.lite.Context;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Set;

/**
 * Created by fg on 30/07/15.
 */
public class CouchDBManager {

    private Context mAndroidContext;
    private Database database;
    private Manager manager;

    private static CouchDBManager instance = new CouchDBManager();

    public static CouchDBManager getInstance(){
        return instance;
    }

    public void init(android.content.Context context) throws Exception{
        mAndroidContext = new AndroidContext(context);
        manager = new Manager(mAndroidContext, null);
        database = manager.getDatabase("gi_db");
    }

    public String insert(Map<String, Object> properties, Map<String, Object> attachments) throws CouchbaseLiteException {
        Document document = database.createDocument();
        document.putProperties(properties);

        if (attachments != null){
            UnsavedRevision revision = document.createRevision();
            Set<String> keySet = attachments.keySet();
            for (String key:keySet){
                revision.setAttachment(key, "", new ByteArrayInputStream((byte[]) attachments.get(key)));
            }
            revision.save();
        }

        return document.getId();
    }

    public void destroy(){
        if (database!=null){
            database.close();
        }
    }
}
