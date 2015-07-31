package com.naelteam.glycemicindexmenuplanner.provider;

import android.util.Log;

import com.couchbase.lite.Attachment;
import com.couchbase.lite.Context;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fg on 30/07/15.
 */
public class CouchDBManager {

    private final static String TAG = CouchDBManager.class.getSimpleName();

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

    public Map<String, Object> getDocument(String docId){
        Document doc = database.getExistingDocument(docId);
        return doc.getProperties();
    }

    public List<Attachment> getDocumentAttachments(String docId){
        Document doc = database.getExistingDocument(docId);
        SavedRevision savedRevision = doc.getCurrentRevision();
        if (savedRevision != null){
            savedRevision.getAttachments();
        }
        return null;
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

    public String update(String docId, Map<String, Object> properties, Map<String, Object> attachments) throws CouchbaseLiteException {
        Document document = database.getExistingDocument(docId);

        if (document != null) {

            Log.d(TAG, "update - storing properties : docId = " + document.getId() + ", revId = " + document.getCurrentRevisionId());

            document.putProperties(properties);

            if (attachments != null) {
                UnsavedRevision revision = document.createRevision();
                Set<String> keySet = attachments.keySet();
                for (String key : keySet) {
                    revision.setAttachment(key, "", new ByteArrayInputStream((byte[]) attachments.get(key)));
                }
                revision.save();
                Log.d(TAG, "update - stored attachments : docId = " + document.getId() + ", revId = " + document.getCurrentRevisionId());
            }
            return document.getId();
        }
        return null;
    }

    public boolean delete(String docId) throws CouchbaseLiteException {
        return database.deleteLocalDocument(docId);
    }

    public void destroy(){
        if (database!=null){
            database.close();
        }
    }
}
