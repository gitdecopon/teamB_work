package browser.db;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MgmtStorageMaster {
	private Sqlite3Ac sq3;
	private Object[][] userData;
	
	private String userID;
	private String StorageID;
	private String StorageName;
	
	//コンストラクタここから
	public MgmtStorageMaster(HttpServletRequest request, HttpSession session) {
		//ここにJSP側の値のnameが決まったら
		//getParamaterで値を取得しprivate変数のuserID StorageID orageNameeに代入する
		
		//JSP側の値のnameが決まるまでの仮代入
		this.userID =(String)session.getAttribute("userMail");
		this.StorageID =request.getParameter("storageId");
		this.StorageName = request.getParameter("storageName");
	}
	
	public MgmtStorageMaster(String id) {
		this.userID = id;
	}
	//コンストラクタここまで
	
	public static Object[][] callManageStorageRegistData(String id) {
		MgmtStorageMaster mgsm = new MgmtStorageMaster(id);
		return mgsm.ProcessExecutionRegist();
	}
	
	public Object[][] ProcessExecutionRegist() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
	
		return getRegistUserData(this.userID);
	}
	
	//DBControllerから呼ばれる　ここから
	public static void callMgmtStorageMaster(HttpServletRequest request, HttpSession session) {
		MgmtStorageMaster mgsm = new MgmtStorageMaster(request,session);
		mgsm.ProcessExecution();
	}

	//ProcessExecutionの仲間ここから
	public void ProcessExecution() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		//JSPからpostが届いたら毎回カテゴリマスタを洗い替えるので
		//削除とインサートを続けて実行する
		executeDeleteSql(this.StorageID, this.userID);

		//this.StorageNameが""(未入力)は削除のみ実行する
		if (this.StorageName!="") {
			executeInsertIntoSql(this.StorageID, this.StorageName, this.userID);
		}
	}
	//DBControllerから呼ばれる　ここまで
	
	
	
	public void executeInsertIntoSql(String cid, String cname, String uid) {
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		//
		StringBuilder strInsertSql = new StringBuilder();
		strInsertSql.append("INSERT INTO MST_STORAGE ");
		strInsertSql.append("(MST_STORAGE_ID, MST_STORAGE_NAME, MST_STORAGE_USER_ID) ");
		strInsertSql.append("VALUES (?, ? , ?)");
		
		strPlaces.add(cid);
		strPlaces.add(cname);
		strPlaces.add(uid);
		
		//SQL文の実行
		this.sq3.sqlExecute(strInsertSql.toString(), strPlaces);
		
	}

	public void executeDeleteSql(String id, String uid) {
		StringBuilder strDeleteSql = new StringBuilder();
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		strDeleteSql.append("DELETE FROM MST_STORAGE ");
		strDeleteSql.append("WHERE MST_STORAGE_ID = ?");
		strDeleteSql.append("AND MST_STORAGE_USER_ID = ?");
		
		strPlaces.add(id);
		strPlaces.add(uid);
		
		this.sq3.sqlExecute(strDeleteSql.toString(), strPlaces);
	}
	//ProcessExecutionの仲間ここまで

	//DB保存データを返す処理ここから
	public Object[][] getRegistUserData(String uid) {
		//JSPが完成したらString uidを引数に移動する。
		//String id = "1";
		//String uid = "c@test.com";

		StringBuilder strSelectSql = new StringBuilder();
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		
		strSelectSql.append("SELECT MST_STORAGE_ID, MST_STORAGE_NAME FROM MST_STORAGE ");
		strSelectSql.append("WHERE MST_STORAGE_USER_ID = ? ");
		strSelectSql.append("ORDER BY MST_STORAGE_ID ");
		
		//strPlaces.add(id);
		strPlaces.add(uid);
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		return this.userData;
		
	}

}
