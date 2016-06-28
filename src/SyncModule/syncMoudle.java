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
import java.util.UUID;

public class syncMoudle {
	
	private static final int TIMEOUT = 10000;// 10��
	private static int version = 0;
	
	public syncMoudle()
	{
		
	}
	
	public String PostChangeList(String urlPath) throws IOException
	{
		String jsonData = new dbOperations().getChangeList();
		
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(urlPath);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print("userid=yefeng&password=123456&version=" + version + "&list=" + jsonData + "&data=[{\"_id\":\"123asdase2331212\", \"value\":{\"title\":\"123\"}},"
                    + "{\"_id\":\"123asdase2331213\", \"value\":{\"title\":\"123\"}}]");
            // flush������Ļ���
            out.flush();
            
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
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
	
	public void InsertItem(String JsonData)
	{
		String uuid = UUID.randomUUID().toString().replace("-", "");
		new dbOperations().InsertOrUpdateRecord(JsonData, uuid);
		//version = uuid;
	}
	
	public void UpdateItem(String JsonData, String uuid)
	{
		new dbOperations().InsertOrUpdateRecord(JsonData, uuid);
		//version  = UUID.randomUUID().toString().replace("-", "");
	}
	
	public void DeleteItem(String uuid)
	{
		new dbOperations().DeleteRecord(uuid);
		//version  = UUID.randomUUID().toString().replace("-", "");
	}
	
	public void GetAllItem()
	{
		
	}
	
	public void GetUpdatedItems(int version)
	{
		
	}

}
