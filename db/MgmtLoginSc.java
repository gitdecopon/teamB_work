package browser.db;

/*
 ログインしたユーザーのデータを返す。
 */

public class MgmtLoginSc {
	
	public static String callRegistPassword(String id) {
		//ログインしたユーザーのパスワードを返す
		MgmtUserMaster userMaster = new MgmtUserMaster();
		userMaster.callMasterUserDefault(id);
		return userMaster.getRegistPass();
		
	}

	public static String callRegistUserId(String id)  {
		//ログインしたユーザーのユーザーIDを返す
		MgmtUserMaster userMaster = new MgmtUserMaster();
		userMaster.callMasterUserDefault(id);
		return userMaster.getRegistUserId();
	}
	
}
