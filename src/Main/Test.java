package Main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import SyncModule.syncMoudle;

public class Test {
	public static void main(String[] args) {
		System.out.println("start");
		syncMoudle mySync = new syncMoudle("yefeng");
		
		//add data
		JSONObject item = new JSONObject();
		
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		item.put("time", df.format(date));
		item.put("title", "first test");
		mySync.InsertItem(item);
		
		JSONObject item2 = new JSONObject();
        item2.put("time", "20160630");
        item2.put("title", "second test");
        mySync.UpdateItem(item2,"57289e96699043979fe3581d7e725a29");
        
        JSONObject item3 = new JSONObject();
        item3.put("title", "third test");
        mySync.UpdateItem(item3,"57289e96699043979fe3581d7e725a30");
		
        mySync.DeleteItem("57289e96699043979fe3581d7e725a29");
        
		try {
			String result = mySync.PostChangeList("http://127.0.0.1:3000/test");
			System.out.println(result);
			if(!result.contains("Server_Fail"))
			{
			    mySync.DropCollections();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
