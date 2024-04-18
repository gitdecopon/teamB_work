import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MdbAccess {
	private String strPath;
	private String strUser;
	private String strPass;
	private String strUrl;
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
			
			//System.out.println(strUrl);
			
			//データベースに接続
			Connection con = DriverManager.getConnection(this.strUrl, this.strUser, this.strPass);
		} catch (SQLException e) {
			e.printStackTrace();	//接続やSQL処理失敗時の所;
		}
	}
	
	public void Hello() {
		System.out.println("hello");
	}
	
	
	//public static void main(String[] args) {
	//		MdbAccess m = new MdbAccess(args[0]);
	//		System.out.println("あ");
			//データベースを切断
			//con.close();
			
			//mdbへの接続はサードパーティードライバが必用だったここまで
			
	//}
}

