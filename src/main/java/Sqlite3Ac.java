import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Sqlite3Ac {
	private Connection con;
	public Sqlite3Ac() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException (
					"JDBCドライバを読み込めませんでした");
		}
			String db_name = "D:\\pleiades\\workspace_java\\teamB_work\\src\\main\\webapp\\WEB-INF\\sqlite3db.sqlite3";
			String url = "jdbc:sqlite:" + db_name;
			//String user = "sa";
			//String pass = "rootroot";
		try {
			//データベースに接続
			con = DriverManager.getConnection(url);
			
		} catch (SQLException e) {
			e.printStackTrace();	//接続やSQL処理失敗時の所;
		}
	}
	
	public void sqlExecute(String sql)  {
		//値を返す必要のないSQLを実行するメソッド

		//sql文をDBに届けるPreparedStatementを取得する
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		try {
			//SQL文を実行
			pStmt.execute();	
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
	
	
	public Object[][] sqlResult(String sql) {
		//ResultSetを2次元配列で返すメソッド。
		ResultSet rs = null;
		Object[][] objArray = null;
		try {
			//sql文をDBに届けるStatementを取得する
			Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(sql);
			
			//ResultSet rs のカラム数を取得するため、
			//ResultSetMetaData rsmdを取得する
			ResultSetMetaData rsmd = rs.getMetaData();
			int icol = rsmd.getColumnCount();
			
			//ResultSet rsの行数を取得する
			int irow = 0 ;
			while (rs.next()) {
				irow++;
			}
			//ResultSetの位置を初期の場所へ戻す
			rs.beforeFirst();
			
			//配列の行数用変数
			int i = 0;
			//Object型の配列をirowとicol分だけ作成
			objArray = new Object[irow][icol];
			while (rs.next()) {
				for (int j = 0; j < icol; j++) {
					objArray[i][j] = rs.getObject(j + 1);
				}
				//次の行のためiをインクリメント
				i++;
			}
		
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//配列を戻す。
		return objArray;
	}
	
	public void dbClose() throws SQLException {
		//データベースを切断
		con.close();
	}
}
