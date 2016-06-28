package Main;

import java.io.IOException;

import SyncModule.syncMoudle;

public class Test {
	public static void main(String[] args) {
		System.out.println("start");
		syncMoudle mySync = new syncMoudle();
		try {
			String result = mySync.PostChangeList("http://127.0.0.1:3000/test");
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
