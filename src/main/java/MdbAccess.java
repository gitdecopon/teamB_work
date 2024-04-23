import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MdbAccess {
	private String strPath;
	private String strUser;
	private String strPass;
	private String strUrl;
	private Connection con = null;
	
	private final String JDBC_DRIVER = "jdbc:ucanaccess://\\";
	
	public MdbAccess(String path) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
	     	throw new IllegalStateException("ドライバのロードに失敗しました");
		}
	
		try {
			this.strPath = path;
			this.strUser = "";
			this.strPass = "";
			this.strUrl = JDBC_DRIVER + this.strPath;
			
			//データベースに接続
			con = DriverManager.getConnection(this.strUrl, this.strUser, this.strPass);
			System.out.println(strUrl);
			
		} catch (SQLException e) {
			e.printStackTrace();	//接続やSQL処理失敗時の所;
		}
	}
	
	public void Hello() throws SQLException {
		try {
		//System.out.println("hello");
		// ステートメントの作成
		Statement statement = con.createStatement();
		
		
		String sql = "CREATE TABLE [test2] (tests INTEGER);";
		//String sql = "SELECT * FROM [test];";
		//test();
		
		statement.executeUpdate(sql);
		
		//test2();
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void test() throws SQLException {
		con.setAutoCommit(false);
		
	}
	
	public void test2() throws SQLException {
		con.commit();
	}
	
	//public static void main(String[] args) {
	//		MdbAccess m = new MdbAccess(args[0]);
	//		System.out.println("あ");
			//データベースを切断
			//con.close();
			
			//mdbへの接続はサードパーティードライバが必用だったここまで
			
	//}
}

