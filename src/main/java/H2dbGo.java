//import java.sql.ResultSet;
import java.sql.SQLException;

public class H2dbGo {
	public static void main(String[] args) {
		H2dbAc h2 = new H2dbAc();
		
		Gethash gh = new Gethash("abc1234yeh");
		String hashPassword = gh.hash();
		
		String strSql = "INSERT INTO MST_USER\n"
				+ "(MST_USER_ID, MST_USER_PASS, MST_USER_LAT, MST_USER_LON, MST_USER_NAME)\n"
				+ "VALUES\n"
				+ "('i@test.com','" + hashPassword + "', 1234.123456, 1234.123456, 'テスト９'); ";
		
		h2.H2Execute(strSql);

		try {
			Object[][] r = h2.H2Result("SELECT * FROM MST_USER;");

			System.out.println(r[0][4]);
			h2.H2dbClose();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
