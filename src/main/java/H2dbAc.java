import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class H2dbAc {
	private Connection con;
	public H2dbAc() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException (
					"JDBCドライバを読み込めませんでした");
		}
			//H2への接続はサードパーティードライバが必用だったここから
			String url = "jdbc:h2:tcp://localhost/~/H2database";
			String user = "sa";
			String pass = "rootroot";
			
		try {
			//データベースに接続
			con = DriverManager.getConnection(url, user, pass);
			
		} catch (SQLException e) {
			e.printStackTrace();	//接続やSQL処理失敗時の所;
		}
	}
	
	public ResultSet H2Execute(String sql) {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = con.prepareStatement(sql);
			rs = pStmt.executeQuery();
		
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return rs;
	}
	
	public void H2dbClose() throws SQLException {
		//データベースを切断
		con.close();
	}
}
