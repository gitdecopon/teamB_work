package browser.db;

import java.util.ArrayList;

public class MgmtMstSeason {
	private Sqlite3Ac sq3;
	private Object[][] userData;
	
	//private String SeasonID;
	//private String SeasonName;

	public MgmtMstSeason() {
		//DBのSqlite3db.sqlite3をインスタンス化する。
		this.sq3 = new Sqlite3Ac();
		this.sq3.callSqlite3Ac();
	}
	
	public static Object[][] callSeasonMaster() {
		MgmtMstSeason mgms = new MgmtMstSeason();
		return mgms.getMstSeason();
	}
	
	
	//DB保存データを返す処理ここから
	public Object[][] getMstSeason() {
		StringBuilder strSelectSql = new StringBuilder();
		ArrayList<String>  strPlaces = new ArrayList<String>  ();
		
		strSelectSql.append("SELECT MST_SEASON_ID, MST_SEASON_NAME FROM MST_SEASON ");
		strSelectSql.append("WHERE MST_SEASON_USER_ID = ? ");
		strSelectSql.append("ORDER BY MST_SEASON_ID ");
		
		//strPlaces.add(id);
		strPlaces.add("share_user");
		this.userData = this.sq3.sqlResult(strSelectSql.toString(), strPlaces);
		return this.userData;
		
	}
}
