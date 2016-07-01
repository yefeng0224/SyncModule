package Main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;

import SyncModule.syncMoudle;

public class Test {
	public static void main(String[] args) {
		System.out.println("start");
		syncMoudle mySync = new syncMoudle("yefeng");
		
		//add data
		
		
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		JSONObject item = new JSONObject();
		item.put("time", df.format(date));
		item.put("title", "first test");
		mySync.InsertItem(item);
		
		JSONObject item11 = new JSONObject();
        item11.put("time", df.format(date));
        item11.put("title", "first test");
        mySync.InsertItem(item11);
		
        
		JSONObject item2 = new JSONObject();
        item2.put("time", "20160630");
        item2.put("title", "second test");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        mySync.UpdateItem(item2,uuid);
        
        JSONObject item3 = new JSONObject();
        item3.put("time", "20160601");
        item3.put("title", "third test");
        String uuid2 = UUID.randomUUID().toString().replace("-", "");
        mySync.UpdateItem(item3,uuid2);
		
        mySync.DeleteItem(uuid);
        
		try {
			String result = mySync.PostChangeList("http://127.0.0.1:3000/test");
			System.out.println(result);
			if(!result.contains("Server_Fail") && result.startsWith("{\"version\""))
			{
			    mySync.DropCollections();
			    JSONObject serverRes = new JSONObject(result);
                mySync.UpdateVersion(serverRes.getInt("version"));
                mySync.UpdateFromServer(serverRes.getJSONArray("new"), serverRes.getJSONArray("delete"));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		Date date2=new Date();
        SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        JSONObject item22 = new JSONObject();
        item22.put("time", df2.format(date));
        item22.put("title", "first test");
        mySync.InsertItem(item22);
        
        JSONObject item23 = new JSONObject();
        item23.put("time", "20160630");
        item23.put("title", "second test");
        String uuid23 = UUID.randomUUID().toString().replace("-", "");
        mySync.UpdateItem(item23,uuid23);
        
        JSONObject item24 = new JSONObject();
        item24.put("title", "third test");
        String uuid24 = UUID.randomUUID().toString().replace("-", "");
        mySync.UpdateItem(item24,uuid24);
        
        mySync.DeleteItem(uuid23);
        
        try {
            String result = mySync.PostChangeList("http://127.0.0.1:3000/test");
            System.out.println(result);
            if(!result.contains("Server_Fail"))
            {
                mySync.DropCollections();
                JSONObject serverRes = new JSONObject(result);
                mySync.UpdateVersion(serverRes.getInt("version"));
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		*/
		System.out.println("end");
	}
}
