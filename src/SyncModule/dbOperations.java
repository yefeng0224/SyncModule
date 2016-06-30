package SyncModule;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import org.bson.Document;

public class dbOperations {
    private boolean isConnected;
    private String user;
    public dbOperations (String user)
    {
        this.user = user;
        isConnected = false;
        connectMongo();
    }
    
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    
    MongoCollection<Document> userCollection;
    MongoCollection<Document> dataCollection;
    MongoCollection<Document> insertCollection;
    MongoCollection<Document> updateCollection;
    MongoCollection<Document> deleteCollection;
    
	//for test
    
    private void connectMongo()
    {
        try
        {
            mongoClient = new MongoClient( "localhost" , 27017 );
            // 连接到数据库
            mongoDatabase = mongoClient.getDatabase("SyncMoudle");  
            
            userCollection = mongoDatabase.getCollection("users");
            dataCollection = mongoDatabase.getCollection(user + "_data");
            insertCollection = mongoDatabase.getCollection(user + "_insert");
            updateCollection = mongoDatabase.getCollection(user + "_update");
            deleteCollection = mongoDatabase.getCollection(user + "_delete");
            isConnected = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void disconnectMongo()
    {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
    
    public boolean isConnected()
    {
        return isConnected;
    }
    
	public String getChangeList() throws JSONException, UnsupportedEncodingException
	{
		JSONObject chgList = new JSONObject();
		JSONObject item;
		JSONArray array = new JSONArray();
		FindIterable<Document> iterable = insertCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            array.put(new JSONObject(cursor.tryNext().toJson()));
        }
        chgList.put("INSERT", array);
        
        array = new JSONArray();
        iterable = updateCollection.find();
        cursor = iterable.iterator();
        while (cursor.hasNext()) {
            array.put(new JSONObject(cursor.tryNext().toJson()));
        }
        chgList.put("UPDATE", array);
        
        array = new JSONArray();
        iterable = deleteCollection.find();
        cursor = iterable.iterator();
        while (cursor.hasNext()) {
            array.put(new JSONObject(cursor.tryNext().toJson()));
        }
        chgList.put("DELETE", array);
        
        return chgList.toString();
		
	}
	
	public void UpdateVersion(int version)
	{
	    Document query = new Document();
        query.put("user", user);
        
        Document newDoc = new Document();
        newDoc.put("version", version);
        
        
        UpdateOptions options = new UpdateOptions();
        //如果这里是true，当查不到结果的时候会添加一条newDoc,默认为false
        options.upsert(true);
        
        Document NewDoc = new Document("$set", newDoc);  
        userCollection.updateOne(query, NewDoc,options);
	}
	
	public void InsertRecord(String Data)
	{
	    Document Doc = Document.parse(Data);
	    dataCollection.insertOne(Doc);
	    insertCollection.insertOne(Doc);
	}
	
	public void UpdateRecord(String Data, String uuid)
    {
        Document query = new Document();
        query.put("_id", uuid);
        
        UpdateOptions options = new UpdateOptions();
        //如果这里是true，当查不到结果的时候会添加一条newDoc,默认为false
        options.upsert(true);
        
        Document NewDoc = new Document("$set", Document.parse(Data));  
        dataCollection.updateOne(query, NewDoc,options);
        
        updateCollection.updateOne(query, NewDoc,options);
    }
	
	public void DeleteRecord(String Data)
	{
	    UpdateOptions options = new UpdateOptions();
        //如果这里是true，当查不到结果的时候会添加一条newDoc,默认为false
        options.upsert(true);
	    Document Doc = Document.parse(Data);
        dataCollection.deleteOne(Doc);
        
        Document NewDoc = new Document("$set", Document.parse(Data));
        deleteCollection.updateOne(Doc,NewDoc,options);
       
	}
	
	public void DropChgColletions()
	{
	    insertCollection.drop();
	    updateCollection.drop();
	    deleteCollection.drop();
	}
	
	
}
