package browser.db;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*
データベーステーブル：MST_ITEMSを管理するクラス
データベースへの接続や実行は「Sqlite3AC」クラスをインスタンス化して
各メソッドを介して実行する。 

制作途中：戻り値を返す部分。

*/

public class MgmtItemRegister {
	private Sqlite3Ac sq3;
	private Object[][] userData;
	
	private String itemID;
	private String itemCategory;
	private String itemPurchaseDate;
	private String itemBrand;
	private String itemImageAddress;
	private String itemLaundry;
	private String itemMemo;
	private String itemSeason;
	private String itemStorage;
	private String itemUserID;
	private String rdoBtnNumber;
	
	public MgmtItemRegister (HttpServletRequest request, HttpSession session) {
		//ここにJSP側の値のnameが決まったら
		//getParamaterで値を取得しprivate変数に代入する
		
		//JSP側の値のnameが決まるまでの仮代入
		this.itemID = request.getParameter("autoNumbered");
		this.itemCategory = request.getParameter("itemCategory");
		this.itemPurchaseDate = request.getParameter("purchaseDate");
		this.itemBrand = "";
		this.itemImageAddress = request.getParameter("itemImage");
		this.itemLaundry = "";
		this.itemMemo = request.getParameter("ItemMemo");
		this.itemSeason = request.getParameter("season"); 
		this.itemStorage = request.getParameter("itemStorage");
		this.itemUserID = (String)session.getAttribute("userMail");
		this.rdoBtnNumber = request.getParameter("act");
	}
	
	public MgmtItemRegister() {}
	
	public static void callMgmtItemMaster(HttpServletRequest request, HttpSession session) {
		MgmtItemRegister mgim = new MgmtItemRegister(request,session);
		mgim.ProcessExecution();
	}
	
	//ProcessExecutionの仲間ここから
	public void ProcessExecution() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
		
		//JSPからpostが届いたら毎回カテゴリマスタを洗い替えるので
		//削除とインサートを続けて実行する
		executeDeleteSql(this.itemID, this.itemUserID);

		if (this.rdoBtnNumber.equals("0")) {
		//ラジオボタンで0：登録が選択されている場合実行
			executeInsertIntoSql(this.itemID, this.itemCategory, this.itemPurchaseDate
								, this.itemBrand, this.itemImageAddress, this.itemLaundry
								, this.itemMemo, this.itemSeason, this.itemStorage, this.itemUserID);
			//DBのITEM_IDのMAX値を++した値とJSP上の品目IDが同じ場合にインクリメントを行う
			//違う場合は修正などで
			//DATA_OBJECT_COUNTERテーブルの
			//DATA_COUNTER_ITEM_IDの値を+1を実行。
			Object[][] o = MgmtDataObjectCounter.maxItemID();
			int s = (int)o[0][0];
			s++;
			if (s==Integer.parseInt(this.itemID)){
				MgmtDataObjectCounter.IncrementlITEM_ID();
			}

		}
	}
	
	public void executeInsertIntoSql(String id, String category, String pdate, String brand
									, String iaddress, String laundry, String memo 
									, String season, String storage,String uid) {
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		//
		StringBuilder strInsertSql = new StringBuilder();
		strInsertSql.append("INSERT INTO MST_ITEMS ");
		strInsertSql.append("(MST_ITEM_ID, MST_ITEM_CATEGORY, MST_ITEM_PURCHASE_DATE,  ");
		strInsertSql.append(" MST_ITEM_BRAND, MST_ITEM_IMAGE_ADDRESS, MST_ITEM_LAUNDRY, ");
		strInsertSql.append(" MST_ITEM_MEMO, MST_ITEM_SEASON, MST_ITEM_USER_STORAGE, MST_ITEM_USER_ID )");
		strInsertSql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		strPlaces.add(id);
		strPlaces.add(category);
		strPlaces.add(pdate);
		strPlaces.add(brand);
		strPlaces.add(iaddress);
		strPlaces.add(laundry);
		strPlaces.add(memo);
		strPlaces.add(season);
		strPlaces.add(storage);
		strPlaces.add(uid);
		
		//SQL文の実行
		this.sq3.sqlExecute(strInsertSql.toString(), strPlaces);
		
	}

	public void executeDeleteSql(String id, String uid) {
		StringBuilder strDeleteSql = new StringBuilder();
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		
		strDeleteSql.append("DELETE FROM MST_ITEMS ");
		strDeleteSql.append("WHERE MST_ITEM_ID = ? ");
		strDeleteSql.append("AND MST_ITEM_USER_ID = ?");
		
		strPlaces.add(id);
		strPlaces.add(uid);
		
		this.sq3.sqlExecute(strDeleteSql.toString(), strPlaces);
	}
	//ProcessExecutionの仲間ここまで

	public static Object[][] callRegistUserData(String uid) {
		MgmtItemRegister mgim = new MgmtItemRegister();
		return mgim.getRegistUserData(uid);
	}
	
	
	//DB保存データを返す処理ここから
	public Object[][] getRegistUserData(String uid) {
		//JSPが完成したらString uidを引数に移動する。
		//String id = "1";
		//String uid = "c@test.com";
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();

		
		StringBuilder strSelectSql = new StringBuilder();
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		
		strSelectSql.append("SELECT MST_ITEM_ID, MST_ITEM_PURCHASE_DATE ");
		strSelectSql.append("MST_ITEM_CATEGORY, MST_ITEM_MEMO ");
		strSelectSql.append("MST_ITEM_USER_STORAGE, MST_ITEM_IMAGE_ADDRESS ");
		
		
		strSelectSql.append("FROM MST_ITEMS ");
		strSelectSql.append("WHERE MST_ITEM_USER_ID = ?");
		
		
		//strSelectSql.append("WHERE MST_ITEM_ID = ? ");
		//strSelectSql.append("AND MST_ITEM_USER_ID = ?");
		
		//strPlaces.add(id);
		strPlaces.add(uid);
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		return this.userData;
		
	}

	
}
