package SyncModule;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dbOperations {
	
	public String getChangeList() throws JSONException, UnsupportedEncodingException
	{
		String result = null;
		JSONObject test = new JSONObject();
		JSONArray testarray = new JSONArray();
		//JSONObject item = new JSONObject();
		//item.put("item1", java.net.URLEncoder.encode("11&33","UTF-8"));
		testarray.put(java.net.URLEncoder.encode("11&33","UTF-8"));
		//item = new JSONObject();
		//item.put("item2","22");
		testarray.put(java.net.URLEncoder.encode("221&44","UTF-8"));
		test.append("data", testarray);
		return testarray.toString();
		/*
		result = "hahasdsadsadsadsadsadasdsadsada" + "asdsadsadsadasdsadasdasdasdsadasd" + 
				"hahasdsadsadsad&adsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  +
				"hahasdsadsadsadsadsadasdsadsada"  +  "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  +  "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  +  "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  +  "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  + 
				"hahasdsadsadsadsadsadasdsadsada"  + "hahasdsadsadsadsadsadasdsadsada"  ;
		return result;
		*/
	}
	
	private void AddToChangeList(String uuid, String method)
	{
		//add to DB_data
		//add to db_change
	}
	
	public void InsertOrUpdateRecord(String Data, String uuid)
	{
		AddToChangeList(uuid,"REPLACE");
	}
	
	public void DeleteRecord(String uuid)
	{
		AddToChangeList(uuid,"DELETE");
	}
	
	
}
