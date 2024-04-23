import java.sql.ResultSet;
import java.sql.SQLException;

public class H2dbGo {
	public static void main(String[] args) {
		H2dbAc h2 = new H2dbAc();
		System.out.println("接続しました");
		
		try {
			ResultSet r = h2.H2Execute("SELECT * FROM MST_USER");
			System.out.println(r);
			h2.H2dbClose();
			System.out.println("切断しました");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
