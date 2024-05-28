package browser.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 データベース:sqlite3への接続・SQL実行・SQL実行(結果を返す) ・接続切断
 を担当するclass
 sqlite3を使用するためのドライバは
 「sqlite-jdbc-3.45.3.0.jar」
 sqlite3を使用する際のログ管理機能に必要なドライバは
 「slf4j-api-2.0.13.jar」
 接続実行のためには最低この2つの.jarが必用。
 上記バージョンのドライバが提供終了している場合はその時点での
 提供ドライババージョンで試してください。
 */
public class Sqlite3Ac {
	private Connection con;
	public void callSqlite3Ac() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException (
					"JDBCドライバを読み込めませんでした");
		}
			//config.propertiesからDBのパスを取得	
			String db_name = retrunDbConnection();
			
			//ユーザーディレクトリを取得
			//例) C:\pleiades\workspace_otk\teamB_KM
			//String basepath = "C:\\pleiades\\workspace_otk\\teamB_KM";
			
			//basepathとdb_nameを連結してDBへのパスを作成してurlを作成
			String url = "jdbc:sqlite:" + db_name;
			//String user = "sa";
			//String pass = "rootroot";
		try {
			//データベースに接続
			this.con = DriverManager.getConnection(url);
			
		} catch (SQLException e) {
			e.printStackTrace();	//接続やSQL処理失敗時の所;
		}
	}
	
	//public void sqlExecute(String sql, String... place)  {
	public void sqlExecute(String sql, ArrayList<String> sPlace)  {
		//値を返す必要のないSQLを実行するメソッド

		//sql文をDBに届けるPreparedStatementを取得する
		PreparedStatement pStmt = null;
		try {
			pStmt = this.con.prepareStatement(sql);
		
			//iPlaceはPreparedStatement.setString(int parameterIndex, String x)
			//の第一引数に使用する。
			int iPlace = 1;
			for (String s: sPlace) {
				pStmt.setString(iPlace, s);
				iPlace++;
			}

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
	
	public Object[][] sqlResult(String sql, ArrayList<String> sPlace) {
		//値を返す必要のあるSQLを実行するメソッド
		//結果は2次元配列のスプレッドシートぽく返す
		
		//ResultSetを2次元配列で返すメソッド。
		ResultSet rs = null;
		Object[][] objArray = null;
		try {
			//sql文をDBに届けるStatementを取得する
			//sqlite3はTYPE_FORWARD_ONLYのカーソルしかサポートしていないため変更 2024/4/26 12:35
			//Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			PreparedStatement pStmt = this.con.prepareStatement(sql);

			//iPlaceはPreparedStatement.setString(int parameterIndex, String x)
			//の第一引数に使用する。
			int iPlace = 1;
			for (String s: sPlace) {
				pStmt.setString(iPlace, s);
				iPlace++;
			}
			rs = pStmt.executeQuery();
			
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
			//sqlite3はTYPE_FORWARD_ONLYのカーソルしかサポートしていないため
			//rs.beforeFirst()は使用できないので再度rsを取得する方法に変更 2024/4/26 11:57
			//rs.beforeFirst();
			rs.close();
			rs = pStmt.executeQuery();
			
			
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
		this.con.close();
	}
	
	public String retrunDbConnection() {
		//basepath + config.propertiesの値でDBへの接続文字列をFileInputStreamへ渡す

		//ユーザーディレクトリを取得
		//例) C:\pleiades\workspace_otk\teamB_KM
		//String basepath = "C:\\pleiades\\workspace_otk\\teamB_KM";
		
		//OSのシステム環境変数からdatabaseのパスを取得
		String databaseUrl = System.getenv("SQLITE_US");
		/*
		Properties props = new Properties();
		try (FileInputStream input = new FileInputStream(basepath + "//src//main//java//browser//db//config.properties")) {
            props.load(input);
            databaseUrl = props.getProperty("database.url");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        */
        return databaseUrl;
	}
}
