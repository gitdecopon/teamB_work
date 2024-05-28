package browser.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/*
 データベーステーブル：MST_USERを管理するクラス
 データベースへの接続や実行は「Sqlite3AC」クラスをインスタンス化して
 各メソッドを介して実行する。 
 
 制作途中：callMasterUserDefaultメソッドの戻り値を
 JSPに返す部分。
 callMsterUserメソッド実行時の登録の有無などのメッセージを
 JSPに返す部分
 */


public class MgmtUserMaster {
	private String choiceNumber; 
	private Object[][] userData;
	private Sqlite3Ac sq3;
	
	private String userID;
	private String userName;
	private String userPass;
	private String userLat;
	private String userLon;
	
	private String newUserName;
	private String newUserPass;
	private String newUserLat;
	private String newUserLon;
	private String rdoBtnNumber;

	private MgmtUserMaster pmgum;
	
	//コンストラクタここから
	public MgmtUserMaster(String rdo){
		this.setChoiceNumber(rdo);
	}
	
	//radioボタンの値を使用せずUserMasterを参照したい場合
	public MgmtUserMaster () {}
	
	//HttpServletRequestを使用コンストラクタ
	public MgmtUserMaster(HttpServletRequest request) {
		
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		*/
		this.userID = request.getParameter("userId");
		this.userName = request.getParameter("currentUserName");
		this.userPass = request.getParameter("currentPassword");
		this.userLat = request.getParameter("currentLatitude");
		this.userLon = request.getParameter("currentLongitude");
		
		this.newUserName = request.getParameter("newUserName");
		this.newUserPass = request.getParameter("newPassword");
		this.newUserLat = request.getParameter("newLatitude");
		this.newUserLon = request.getParameter("newLongitude");
		
		this.rdoBtnNumber = request.getParameter("act");
		
		//コンストラクタ(String rdo)を通らないのでここで代入
		this.choiceNumber = request.getParameter("act");
		
		this.pmgum = new MgmtUserMaster();
		
	}	
	//コンストラクタここまで

	//staticで呼び出すstaticメソッドここから
		//static01 DBcontrollser: case"usermaster" case "3"で使用ここから
	public static String getRegistIDJsp (String id) {
		MgmtUserMaster mgum = new MgmtUserMaster();
		mgum.callMasterUserDefault(id);
		return mgum.getRegistUserId();
	}
	public static String getRegistNameJsp (String id) {
		MgmtUserMaster mgum = new MgmtUserMaster();
		mgum.callMasterUserDefault(id);
		return mgum.getRegistName();
	}
	
	public static String getRegistLatJsp (String id) {
		MgmtUserMaster mgum = new MgmtUserMaster();
		mgum.callMasterUserDefault(id);
		return String.valueOf(mgum.getRegistLat());
	}
	
	public static String getRegistLonJsp (String id) {
		MgmtUserMaster mgum = new MgmtUserMaster();
		mgum.callMasterUserDefault(id);
		return String.valueOf(mgum.getRegistLon());
	}
		
		//static01 DBcontrollser: case"usermaster" case "3"で使用ここまで

	
		//static01 DBcontrollser: case"usermaster" で使用ここから
	public static void CalledUserName (HttpServletRequest request)  {
		MgmtUserMaster mgum = new MgmtUserMaster(request);
		mgum.ProcessExecution();
		
	}
	
	//CalledUserNameから処理実行を行うメソッド
	public void ProcessExecution() {
		switch (this.rdoBtnNumber) {
		
		case "0", "1", "2":
			try {
				callMsterUser(this.userID, 
						this.userName, this.userPass, this.userLat, this.userLon, 
						this.newUserName, this.newUserPass, this.newUserLat, this.newUserLon);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				;
			}

			break;
		case "3":
			callMasterUserDefault(this.userID);

			break;
		}
	}
	
	
	public static Map<String, String> getMapLoginUser(HttpServletRequest request) {
		//ログインしたユーザーIDとユーザー名をMAPで返すメソッド
		MgmtUserMaster mgum = new MgmtUserMaster(request);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", mgum.getRequestID());
		map.put("userName", mgum.getRequestName());
		
		return map;
	}
	
	public String getRequestID() {
		return this.userID;
	}

	public String getRequestName() {
		return this.userName;
	}
	
		//static01 DBcontrollser: case"usermaster" で使用ここまで
	
	//DBControllerからstaticで呼び出すstaticメソッドここまで
	
	
	//ユーザーマスタの値を呼び出したい場合のメソッド
	public void callMasterUserDefault(String id)  {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();

		//該当ユーザーの情報をインスタンス化して配列変数userDataに代入する。
		getRegistUserData(id);
		
		
	}
	
	//MgmtUserMaster.javaから使用するメソッド
	//引数String passは新規入力時は登録したいパスワード
	//                 修正・削除時は現在のパスワードをユーザーに入力してもらう用
	public void callMsterUser(String id, String name, String pass, String lat, String lon, 
										String newname, String newpass, String newlat, String newlon)  throws SQLException, Exception  {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();

		//該当ユーザーの情報をインスタンス化して配列変数userDataに代入する。
		getRegistUserData(id);
		
		//SQL文のプレースホルダー用
		String strId = null;
		//ユーザー入力ハッシュ化パスワード代入用
		String hashPass = null;
		//DBに登録されているハッシュパスワード代入用
		String dbHashPass = null;
		//ユーザー入力（確認用）ハッシュ化パスワード代入用
		String usedHashPass = null;
		//パスワードが合っているかの判定用
		Boolean isPassCheck = null;
		
		//choiceNumber 0:登録 1:変更 2:削除 3:キャンセル
		switch (this.choiceNumber) {
		
		//新規登録の処理
		case "0":
			//新規登録として入力されたユーザーIDがデータベースへの登録有無を調べる。
			//入力されたユーザーIDでSELECT文を実行してユーザーIDを返す。
			strId = getRegistUserId();	
			//値がnull(登録なし)の場合INSRT INTO文を実行する。
			if (strId == null) {
				executeInsertIntoSql(id, pass, Double.parseDouble(lat), Double.parseDouble(lon), name);
			} else {
				System.out.println("すでに登録があるIDです。");
			}
			
			break;
		//内容変更登録
		case "1":
			//DBに登録されているパスワードを代入
			dbHashPass = getRegistPass();
			//ユーザーが入力した現在のパスワード（確認用）をハッシュ化
			usedHashPass = getHashPass(pass);
			//ユーザーが入力した変更後のパスワードをハッシュ化
			hashPass = getHashPass(newpass);
			
			//DBに登録されているパスワードとユーザーが画面上で入力したパスワードが合っていればtrueなければfalse
			if (dbHashPass.equals(usedHashPass)) {
				isPassCheck = true;
			} else {
				isPassCheck = false;
			}
			
			if (isPassCheck == true) {
				//DB情報と画面上情報が違う場合UPDATE文の実行
				//if (!(strOldValues.equals(strNewValues))) {
				if (checkOldNewValues(id, pass, Double.parseDouble(lat), Double.parseDouble(lon), name)) {
				//equalsではない。違う値の場合に変更する。
					executeUpdateSql(id, newpass, Double.parseDouble(newlat), Double.parseDouble(newlon), newname);
				} else {
					System.out.println("内容に変更点がないので実行されませんでした。");
				}
			} else {
				System.out.println("IDか確認用パスワードが違います。");
			}
			
			break;
		//削除
		case "2":
			//DBに登録されているパスワードを代入
			dbHashPass = getRegistPass();
			//ユーザーが入力した現在のパスワード（確認用）をハッシュ化
			usedHashPass = getHashPass(pass);
		
			//DBに登録されているパスワードとユーザーが画面上で入力したパスワードが合っていればtrueなければfalse
			if (dbHashPass.equals(usedHashPass)) {
				isPassCheck = true;
			} else {
				isPassCheck = false;
			}

			if (isPassCheck == true) {
				//パスワードチェックがtrueなら削除に入る
				executeDeleteSql(id);
			} else {
				System.out.println("IDか確認用パスワードが違います。");
			}
			
			break;
		}
	}
	
	public String getHashPass(String pass) {
		//passをハッシュ値(sha3_512)に変換して戻り値で返す。
		Gethash gh = new Gethash(pass);
		String hashPassword = gh.hash();
		
		return hashPassword;
	}
	
	public void executeInsertIntoSql(String inputID, String inputPass, 
			Double inputLat, Double inputLon, String inputName) {
		//INSERT INTO文を処理するメソッド
		//callMsterUserメソッドのラジオボタンの値が0の場合に呼ばれるメソッド
		
		//ユーザーが入力した変更後のパスワードをハッシュ化
		String hashPass = getHashPass(inputPass);
		
		//SQL文のカラム部分をセットする変数
		StringBuilder strColumn = new StringBuilder();
		//SQL文の値をセットする変数
		ArrayList<String> arrValues = new ArrayList<String>();
		//strColumnとstrValuesを元にSQL文を作成しセットする変数
		StringBuilder strInsertSql = new StringBuilder();
		
		strColumn.append("MST_USER_ID, ");
		strColumn.append("MST_USER_PASS, ");
		strColumn.append("MST_USER_LAT, ");
		strColumn.append("MST_USER_LON, ");
		strColumn.append("MST_USER_NAME");
		
		arrValues.add(inputID);
		arrValues.add(hashPass);
		arrValues.add(String.valueOf(inputLat));
		arrValues.add(String.valueOf(inputLon));
		arrValues.add(inputName);
		
		strInsertSql.append("INSERT INTO MST_USER (");
		strInsertSql.append(strColumn);
		strInsertSql.append(")  VALUES (?, ?, ?, ?, ? );");
		
		//SQL文の実行
		//strInsertSql.toString()はSQL文　arrValuesはプレースホルダーの値が配列でセットしてある。
		this.sq3.sqlExecute(strInsertSql.toString(), arrValues);
		
	}
	
	public void executeUpdateSql(String inputID, String inputPass, 
									Double inputLat, Double inputLon, String inputName) {
		//UPDATE文を処理するメソッド
		//callMsterUserメソッドのラジオボタンの値が1の場合に呼ばれるメソッド
		
		//ユーザーが入力した変更後のパスワードをハッシュ化
		String hashPass = getHashPass(inputPass);
		
		//SQL文の変更があるカラム部分をセットする変数
		StringBuilder strChangeColumn = new StringBuilder();
		//SQL文の変更がある値をセットする変数
		ArrayList<String> arrChangeValues = new ArrayList<String>();
		//strChangeColumnとstrChangeValuesを元にSQL文を作成しセットする変数
		StringBuilder strUpdateSql = new StringBuilder();
		
		//以下のIF文は変更のある項目のみ項目名と値を
		//変数strChangeColumnとarrChangeValuesにappendする。
		//（JSP上で変更後の値が入力されないとnullが届く）
		if (hashPass!=null) {
			strChangeColumn.append("MST_USER_PASS = ? "); 
			arrChangeValues.add(hashPass);
			
			//以降の項目にセットする値があれば","を付ける
			if (inputLat!=null || inputLon!=null || inputName!=null) {
				strChangeColumn.append(",");
			}
		}
		
		if (inputLat!=null) {
			strChangeColumn.append("MST_USER_LAT = ? ");
			arrChangeValues.add(String.valueOf(inputLat));

			//以降の項目にセットする値があれば","を付ける
			if (inputLon!=null || inputName!=null) {
				strChangeColumn.append(",");
			}
		}
		
		if (inputLon!=null) {
			strChangeColumn.append("MST_USER_LON = ? ");
			arrChangeValues.add(String.valueOf(inputLon));

			//以降の項目にセットする値があれば","を付ける
			if (inputName!=null) {
				strChangeColumn.append(",");
			}

		}
		
		if (inputName!=null) {
			strChangeColumn.append("MST_USER_NAME = ? ");
			arrChangeValues.add(inputName);
		}

		//SQL文の作成
		strUpdateSql.append("UPDATE MST_USER SET ");
		strUpdateSql.append(strChangeColumn);
		strUpdateSql.append("WHERE MST_USER_ID = ? ;");
		
		//WHERE句に使用する値をセットする。
		arrChangeValues.add(inputID);
		
		//SQL文の実行
		//strUpdateSql.toString()はSQL文　arrChangeValuesはプレースホルダーの値が配列でセットしてある。
		this.sq3.sqlExecute(strUpdateSql.toString(), arrChangeValues);
	}
	
	public void executeDeleteSql(String inputID) {
		//DELETE文を処理するメソッド
		//callMsterUserメソッドのラジオボタンの値が2の場合に呼ばれるメソッド
		
		//SQL文の変更がある値をセットする変数
		ArrayList<String> arrChangeValues = new ArrayList<String>();
		//strChangeColumnとstrChangeValuesを元にSQL文を作成しセットする変数
		StringBuilder strUpdateSql = new StringBuilder();
		
		strUpdateSql.append("DELETE FROM MST_USER WHERE MST_USER_ID = ?;");
		arrChangeValues.add(inputID);
		//SQL文の実行
		//strUpdateSql.toString()はSQL文　arrChangeValuesはプレースホルダーの値が配列でセットしてある。
		this.sq3.sqlExecute(strUpdateSql.toString(), arrChangeValues);
	}
	

	public boolean checkOldNewValues (String inputID, String inputPass, Double inputLat, Double inputLon, String inputName) {
		//JSP上で変更項目を入力した項目で実際に値が新旧違いがあるかチェック
		String hashPass = getHashPass(inputPass);
		
		StringBuilder oldValues = new StringBuilder();
		StringBuilder newValues = new StringBuilder();
		
		if (hashPass!=null) {
			oldValues.append("MST_USER_PASS = '" +  getRegistPass() + "'");
			newValues.append("MST_USER_PASS = '" +  hashPass + "'");
			
			//以降の項目にセットする値があれば","を付ける
			if (inputLat!=null || inputLon!=null || inputName!=null) {
				oldValues.append(",");
				newValues.append(",");
			}
		}
		
		if (inputLat!=null) {
			oldValues.append("MST_USER_LAT = " + getRegistLat() + "");
			newValues.append("MST_USER_LAT = " + inputLat + "");
			
			//以降の項目にセットする値があれば","を付ける
			if (inputLon!=null || inputName!=null) {
				oldValues.append(",");
				newValues.append(",");
			}
		}
		
		if (inputLon!=null) {
			oldValues.append("MST_USER_LON = " + getRegistLon() + "");
			newValues.append("MST_USER_LON = " + inputLon + "");
			
			//以降の項目にセットする値があれば","を付ける
			if (inputName!=null) {
				oldValues.append(",");
				newValues.append(",");
			}
		}
		
		if (inputName!=null) {
			oldValues.append("MST_USER_NAME = '" +  getRegistName() + "'");
			newValues.append("MST_USER_NAME = '" +  inputName + "'");
		}
		
		if (!(oldValues.equals(newValues))) {
			//値が違う場合UPDATE文を実行するので！でtrueを返す
			return true;
		} else {
			return false;
		}
	}

	//データベースに保存されているデータをユーザーIDを指定して
	//private変数userdata[][]へ代入して以下メソッドで値を取得する。ここから
	public void getRegistUserData(String id) {
		//引数idはMST_USERのMST_USER_IDを渡すと
		//該当ユーザーの情報をインスタンス化して配列変数userDataに代入する。。

		String sql = "SELECT * FROM MST_USER WHERE MST_USER_ID =  ?;";
		
		//SQL文の値をセットする変数
		ArrayList<String> arrValues = new ArrayList<String>();
		arrValues.add(id);
		this.userData = this.sq3.sqlResult(sql,arrValues);
	}

	//指定IDのDBに登録されているユーザーIDの呼び出し
	public String getRegistUserId () {
		try {
			if (this.userData[0] == null) {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return (String) this.userData[0][0];

	}

	//指定IDのDBに登録されているパスワードの呼び出し
	public String getRegistPass() {
		try {
			if (this.userData[0] == null) {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return (String) this.userData[0][1];
	}
	
	//指定IDのDBに登録されている緯度の呼び出し
	public double getRegistLat() {
		try {
			if (this.userData[0] == null) {
				return 0.0;
			}
		} catch (IndexOutOfBoundsException e) {
			return 0.0;
		}
		int i = (int)this.userData[0][2];
		double d = i;
		
		return d;
	}
	
	//指定IDのDBに登録されている経度の呼び出し
	public double getRegistLon() {
		try {
			if (this.userData[0] == null) {
				return 0.0;
			}
		} catch (IndexOutOfBoundsException e) {
			return 0.0;
		}
		int i = (int)this.userData[0][3];
		double d = i;
		return d;
	}
	
	//指定IDのDBに登録されているユーザー名の呼び出し
	public String getRegistName() {
		try {
			if (this.userData[0] == null) {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return (String) this.userData[0][4];
	}
	//private変数userdata[][]へ代入して以下メソッドで値を取得する。ここまで
	
	//ChoiceNumberのgetter
	public String getChoiceNumber() {
		return choiceNumber;
	}

	//ChoiceNumberのsetter
	public void setChoiceNumber(String choiceNumber) {
		this.choiceNumber = choiceNumber;
	}

}
