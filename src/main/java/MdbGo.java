import java.sql.SQLException;

public class MdbGo {
	public static void main(String[] args) throws SQLException {
		//MdbAccess m = new MdbAccess("\\PC001\\Users\\nobuyuki\\Desktop\\share\\Database.mdb");
		MdbAccess m = new MdbAccess("\\\\RSHiroshimaTS\\h305\\database\\umehan\\Database.mdb");
		m.Hello();
	}

}
