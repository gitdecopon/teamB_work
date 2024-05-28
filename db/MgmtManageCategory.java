package browser.db;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
データベーステーブル：MST_CATEGORYを管理するクラス
データベースへの接続や実行は「Sqlite3AC」クラスをインスタンス化して
各メソッドを介して実行する。 

制作途中：戻り値を返す部分。

*/
public class MgmtManageCategory {
	private Sqlite3Ac sq3;
	private Object[][] userData;
	
	private String userID;
	private String categoryID;
	private String categoryName;
	
	
	//コンストラクタここから
	public MgmtManageCategory(HttpServletRequest request, HttpSession session) {
		//ここにJSP側の値のnameが決まったら
		//getParamaterで値を取得しprivate変数のcategoryID categoryNameに代入する
		//session.getAttribute("userMail")でuserID
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		this.categoryID = request.getParameter("categoryId");
		this.categoryName = request.getParameter("CategoryeName");
		this.userID = (String)session.getAttribute("userMail");
	}
	
	public MgmtManageCategory(String id) {
		this.userID = id;
		
	}
	//コンストラクタここまで
	
	//DBControllerJspから呼ばれる　ここから
	public static Object[][] callManageCategoryRegistData(String id) {
		MgmtManageCategory mgmc = new MgmtManageCategory(id);
		return mgmc.ProcessExecutionRegist();
	}
	
	public Object[][] ProcessExecutionRegist() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		return getRegistUserData(this.userID);

	}
	//DBControllerJspから呼ばれる　ここまで
	
	//DBControllerから呼ばれる　ここから
	public static void callManageCategory(HttpServletRequest request, HttpSession session) {
		MgmtManageCategory mgmc = new MgmtManageCategory(request,session);
		mgmc.ProcessExecution();
		
	}
	
	//ProcessExecutionの仲間ここから
	public void ProcessExecution() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();

		
		//JSPからpostが届いたら毎回カテゴリマスタを洗い替えるので
		//削除とインサートを続けて実行する
		executeDeleteSql(this.categoryID, this.userID);

		//this.categoryNameが""(未入力)は削除のみ実行する
		if (this.categoryName!="") {
			executeInsertIntoSql(this.categoryID, this.categoryName, this.userID);
		}
	}
	
	//DBControllerから呼ばれる　ここまで
	
	public void executeInsertIntoSql(String cid, String cname, String uid) {
		//
		//StringBuilder strColumn = new StringBuilder();
		//
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		//
		StringBuilder strInsertSql = new StringBuilder();
		strInsertSql.append("INSERT INTO MST_CATEGORY ");
		strInsertSql.append("(MST_CATEGORY_ID, MST_CATEGORY_NAME, MST_CATEGORY_USER_ID) ");
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
		strDeleteSql.append("DELETE FROM MST_CATEGORY ");
		strDeleteSql.append("WHERE MST_CATEGORY_ID = ?");
		strDeleteSql.append("AND MST_CATEGORY_USER_ID = ?");
		
		strPlaces.add(id);
		strPlaces.add(uid);
		
		this.sq3.sqlExecute(strDeleteSql.toString(), strPlaces);
	}
	//ProcessExecutionの仲間ここまで
	
	
	//DB保存データを返す処理ここから
	public Object[][] getRegistUserData(String uid) {
		//JSPが完成したらString uidを引数に移動する。
		//String uid = "c@test.com";

		StringBuilder strSelectSql = new StringBuilder();
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		
		strSelectSql.append("SELECT MST_CATEGORY_ID, MST_CATEGORY_NAME FROM MST_CATEGORY ");
		strSelectSql.append("WHERE MST_CATEGORY_USER_ID = ? ");
		strSelectSql.append("ORDER BY MST_CATEGORY_ID ");
		
		//strPlaces.add(id);
		strPlaces.add(uid);
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		return this.userData;
	}
	
	
}
