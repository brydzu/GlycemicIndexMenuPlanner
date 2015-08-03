package com.naelteam.glycemicindexmenuplanner.provider;

import android.util.Log;

import com.couchbase.lite.Attachment;
import com.couchbase.lite.Context;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.View;
import com.couchbase.lite.android.AndroidContext;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fg on 30/07/15.
 */
public class CouchDBManager {

    private final static String TAG = CouchDBManager.class.getSimpleName();

    public static final String LIST_PRODUCTS_VIEW = "LIST_PRODUCTS_VIEW";
    public static final String DB_NAME = "gi_db";

    private Context mAndroidContext;
    private Database database;
    private Manager manager;

    private static boolean initialized;

    private static CouchDBManager instance = new CouchDBManager();

    public static CouchDBManager getInstance(){
        return instance;
    }

    public synchronized void init(android.content.Context context) throws Exception{

        Log.d(TAG, "init .. initialized = " + initialized);

        if (initialized){
            return;
        }
        initialized = true;

        mAndroidContext = new AndroidContext(context);
        manager = new Manager(mAndroidContext, null);
        database = manager.getDatabase(DB_NAME);

        Log.d(TAG, "init, db = " + database.getName());
        Log.d(TAG, "init, path = " + database.getPath());

        Log.d(TAG, "init done");
    }

    private View getListProductView(){
        View listProductsView = database.getView(LIST_PRODUCTS_VIEW);
        listProductsView.setMap(new Mapper() {
            @Override
            public void map(Map<String, Object> document, Emitter emitter) {
                emitter.emit("values", document);
            }
        }, "1");
        return listProductsView;
    }

    public Map<String, Object> getDocument(String docId){
        Document doc = database.getExistingDocument(docId);
        return doc.getProperties();
    }

    public QueryEnumerator list() throws CouchbaseLiteException {
        Query query = getListProductView().createQuery();
        return query.run();
    }

    public List<Attachment> getDocumentAttachments(String docId){
        Document doc = database.getExistingDocument(docId);
        SavedRevision savedRevision = doc.getCurrentRevision();
        if (savedRevision != null){
            savedRevision.getAttachments();
        }
        return null;
    }

    public void insertBatch(List<Map<String, Object>> properties, List<Map<String, Object>> attachments) throws CouchbaseLiteException {

        Log.d(TAG, "Inserting batch " + properties.size() + " objects");
        long timeIn = System.currentTimeMillis();

        try {
            database.beginTransaction();
            for (Map<String, Object> property : properties) {
                insert(property, null);
            }
            database.endTransaction(true);
            Log.d(TAG, "Insert batch done in " + (System.currentTimeMillis() - timeIn) + " ms");
        }catch (Exception e){
            database.endTransaction(false);
        }
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
            Log.w(TAG, "destroy..");
            database.close();
        }
    }

    public boolean deleteCurrentDatabase(){
        try {
            if (database!=null) {
                database.delete();
                Log.w(TAG, "deleteCurrentDatabase - Current database deleted");
                return true;
            }
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "deleteCurrentDatabase - error", e);
        }
        return false;
    }
}
