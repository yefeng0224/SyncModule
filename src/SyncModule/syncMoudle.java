package SyncModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class syncMoudle {
	
	private static final int TIMEOUT = 10000;// 10锟斤拷
	private static int version = 0;
	
	private dbOperations db;
	public  syncMoudle(String user)
	{
	    db = new dbOperations(user);
	}
	
	public String PostChangeList(String urlPath) throws IOException
	{
		String jsonData = db.getChangeList();
		
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(urlPath);
            // 锟津开猴拷URL之锟斤拷锟斤拷锟斤拷锟�
            URLConnection conn = realUrl.openConnection();
            // 锟斤拷锟斤拷通锟矫碉拷锟斤拷锟斤拷锟斤拷锟斤拷
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 锟斤拷锟斤拷POST锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 锟斤拷取URLConnection锟斤拷锟斤拷锟接︼拷锟斤拷锟斤拷锟斤拷
            out = new PrintWriter(conn.getOutputStream());
            // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
            //out.print("userid=yefeng&password=123456&version=" + version + "&data={\"DELETE\":[],\"INSERT\":[],\"UPDATE\":[]}");
            out.print("userid=yefeng&password=123456&version=" + version + "&data=" + java.net.URLEncoder.encode(jsonData,"UTF-8"));
            // flush锟斤拷锟斤拷锟斤拷幕锟斤拷锟�
            out.flush();
            
            // 锟斤拷锟斤拷BufferedReader锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷取URL锟斤拷锟斤拷应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("锟斤拷锟斤拷 POST 锟斤拷锟斤拷锟斤拷锟斤拷斐ｏ拷锟�"+e);
            e.printStackTrace();
        }
        //使锟斤拷finally锟斤拷锟斤拷锟截憋拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
		
        return "send post error!";
	}
	
	public void InsertItem(JSONObject JsonData)
	{
		String uuid = UUID.randomUUID().toString().replace("-", "");
		JsonData.put("_id",uuid);
		db.InsertRecord(JsonData.toString());
		//version = uuid;
	}
	
	public void UpdateItem(JSONObject JsonData, String uuid)
	{
		db.UpdateRecord(JsonData.toString(), uuid, false);
		//version  = UUID.randomUUID().toString().replace("-", "");
	}
	
	public void DeleteItem(String uuid)
	{
	    JSONObject JsonData = new JSONObject();
	    JsonData.put("_id", uuid);
		db.DeleteRecord(JsonData.toString(),false);
		//version  = UUID.randomUUID().toString().replace("-", "");
	}
	
	public void UpdateVersion(int newVersion)
	{
	    System.out.println("UpdateVersion: " + newVersion);
	    version = newVersion;
	    db.UpdateVersion(newVersion);
	}
	
	public void UpdateFromServer(JSONArray newArray, JSONArray deleteArray)
	{
	    JSONObject jsonData;
	    JSONObject jsonResult;
	    String uuid = "";
	    String key;
	    for(int i = 0; i < newArray.length(); i++)
	    {
	        jsonData = newArray.getJSONObject(i);
	        Iterator it = jsonData.keys();
	        jsonResult = new JSONObject();
	        while(it.hasNext()){ 
	           key = it.next().toString();
	           if(key.equals("_id"))
	           {
	        	   uuid = jsonData.getString(key);
	           }
	           else
	           {
	        	   jsonResult.put(key, jsonData.getString(key));
	           }
	        }  
	        db.UpdateRecord(jsonResult.toString(), uuid, true);
	    }
	    
	    for(int i = 0; i < deleteArray.length(); i++)
	    {
	    	jsonData = deleteArray.getJSONObject(i);
	    	db.DeleteRecord(jsonData.toString(), true);
	    }
	}
	
	public void DropCollections()
	{
	    db.DropChgColletions();
	}
	
	public void GetAllItem()
	{
		
	}
	
	public void GetUpdatedItems(int version)
	{
		
	}

}
