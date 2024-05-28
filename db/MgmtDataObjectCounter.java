package browser.db;

import java.util.ArrayList;

public class MgmtDataObjectCounter {
	private Sqlite3Ac sq3;
	private Object[][] userData;
	//private String userID;
	
	public MgmtDataObjectCounter() {
		
	}
	
	//自動採番用にDATA_COUNTER_ITEM_IDの値を返すstaticここから
	public static Object[][] callMgmtDataItemCounter() {
		//自動採番用にDATA_COUNTER_ITEM_IDの現在の値を返す。
		//実行用staticメソッド
		MgmtDataObjectCounter mgdc = new MgmtDataObjectCounter();
		return mgdc.ProcessExecutionItemCounter();
	}

	public Object[][] ProcessExecutionItemCounter() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		//自動採番用にDATA_COUNTER_ITEM_IDの現在の値を返す。
		//受け取り側で+1して使用する。
		return itemCounter();
	}

	public Object[][] itemCounter() {
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		StringBuilder strSelectSql = new StringBuilder();
		
		
		strSelectSql.append("SELECT DATA_COUNTER_ITEM_ID FROM DATA_OBJECT_COUNTER ");
		strSelectSql.append("WHERE DATA_COUNTER_USER_ID = ? ");
		strPlaces.add("share_user");
		
		
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		
		return this.userData;
		
	}
	//自動採番用にDATA_COUNTER_ITEM_IDの値を返すstaticここまで
	
	//自動採番用にDATA_COUNTER_COODINAT_IDの値を返すstaticここから
	public static Object[][] callMgmtDataCoodCounter() {
		//自動採番用にDATA_COUNTER_COODINAT_IDの現在の値を返す。
		//実行用staticメソッド
		MgmtDataObjectCounter mgdc = new MgmtDataObjectCounter();
		return mgdc.ProcessExecutionCoodCounter();
	}
	
	
	public Object[][] ProcessExecutionCoodCounter() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		//自動採番用にDATA_COUNTER_COODINAT_IDの現在の値を返す。
		//受け取り側で+1して使用する。
		return coodCounter();
	}
	
	
	public Object[][] coodCounter() {
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		StringBuilder strSelectSql = new StringBuilder();
		
		
		strSelectSql.append("SELECT DATA_COUNTER_COODINAT_ID FROM DATA_OBJECT_COUNTER ");
		strSelectSql.append("WHERE DATA_COUNTER_USER_ID = ? ");
		strPlaces.add("share_user");
		
		
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		
		return this.userData;
		
	}
	//自動採番用にDATA_COUNTER_COODINAT_IDの値を返すstaticここまで
	
	//DATA_COUNTER_ITEM_IDの値をインクリメント用staticここから
	public static void IncrementlITEM_ID() {
		MgmtDataObjectCounter mgdc = new MgmtDataObjectCounter();
		mgdc.executeUpdateSqlITEM_ID();
	}
	
	public void executeUpdateSqlITEM_ID() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		StringBuilder strSelectSql = new StringBuilder();
		
		strSelectSql.append("UPDATE DATA_OBJECT_COUNTER ");
		strSelectSql.append("SET DATA_COUNTER_ITEM_ID = DATA_COUNTER_ITEM_ID +1 ");
		strSelectSql.append("WHERE DATA_COUNTER_USER_ID = ? ");
		strPlaces.add("share_user");
		
		this.sq3.sqlExecute(strSelectSql.toString(), strPlaces);
		
	}
	//DATA_COUNTER_ITEM_IDの値をインクリメント用staticここまで
	
	//DATA_COUNTER_COODINAT_IDの値をインクリメント用staticここから
	public static void IncrementCOODINAT_ID() {
		MgmtDataObjectCounter mgdc = new MgmtDataObjectCounter();
		mgdc.executeUpdateSqlCOODINAT_ID();
	}
	
	
	public void executeUpdateSqlCOODINAT_ID() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
				
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		StringBuilder strSelectSql = new StringBuilder();
		
		strSelectSql.append("UPDATE DATA_OBJECT_COUNTER ");
		strSelectSql.append("SET DATA_COUNTER_COODINAT_ID = DATA_COUNTER_COODINAT_ID +1 ");
		strSelectSql.append("WHERE DATA_COUNTER_USER_ID = ? ");
		strPlaces.add("share_user");
		
		this.sq3.sqlExecute(strSelectSql.toString(), strPlaces);
		
	}
	//DATA_COUNTER_COODINAT_IDの値をインクリメント用staticここまで
	
	
	//DATA_COUNTER_ITEM_IDのMAX値を返すstaticここから
	public static Object[][] maxItemID() {
		MgmtDataObjectCounter mgdc = new MgmtDataObjectCounter();
		return mgdc.itemIDMax();
	}
	
	public Object[][] itemIDMax() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		StringBuilder strSelectSql = new StringBuilder();
		
		
		strSelectSql.append("SELECT MAX(DATA_COUNTER_ITEM_ID) FROM DATA_OBJECT_COUNTER ");
		strSelectSql.append("WHERE DATA_COUNTER_USER_ID = ? ");
		strPlaces.add("share_user");
		
		
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		
		return this.userData;
		
	}
	//DATA_COUNTER_ITEM_IDのMAX値を返すstaticここまで

	//DATA_COUNTER_COODINAT_IDのMAX値を返すstaticここから
		public static Object[][] maxCoodinatIDMax() {
			MgmtDataObjectCounter mgdc = new MgmtDataObjectCounter();
			return mgdc.CoodinatIDMax();
		}
	
	public Object[][] CoodinatIDMax() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		StringBuilder strSelectSql = new StringBuilder();
		
		
		strSelectSql.append("SELECT MAX(DATA_COUNTER_COODINAT_ID) FROM DATA_OBJECT_COUNTER ");
		strSelectSql.append("WHERE DATA_COUNTER_USER_ID = ? ");
		strPlaces.add("share_user");
		
		
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		
		return this.userData;
		
	}
	//DATA_COUNTER_COODINAT_IDのMAX値を返すstaticここまで
	
	
}
