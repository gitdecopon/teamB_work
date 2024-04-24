import java.sql.SQLException;

public class Sqlite3Go {
	public static void main(String[] args) {
		Sqlite3Ac sq3 = new Sqlite3Ac();
		System.out.println("接続");
		try {
			sq3.dbClose();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("切断");
	}
}
