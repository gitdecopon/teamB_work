package browser.db;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DBController {
	
	//最終的に梅田さんのModelFacade.javaにオーバーライドされる予定
	
	public static Map<String, String> DBAssignment(HttpServletRequest request
													, String attribute
													, HttpSession session)  {
		//DBController dbc = new DBController(request,attribute);
		Map<String, String> localmap = new HashMap<String, String>();
		switch (attribute) {
		
		case "usermaster" :
			switch (request.getParameter("act")) {
			case "0", "1", "2":
				MgmtUserMaster.CalledUserName(request);
				localmap = MgmtUserMaster.getMapLoginUser(request);
				break;
			case "3":
				//登録内容紹介の場合
				String id = MgmtUserMaster.getRegistIDJsp(request.getParameter("userId"));
				localmap.put("userID", id);
				String name = MgmtUserMaster.getRegistNameJsp(request.getParameter("userId"));
				localmap.put("userName", name);
				String lat = MgmtUserMaster.getRegistLatJsp(request.getParameter("userId"));
				localmap.put("userLat", lat);
				String lon = MgmtUserMaster.getRegistLonJsp(request.getParameter("userId"));
				localmap.put("userLon", lon);
				
				//System.out.println(localmap);
				
				JavaBeansDB jbdb = new JavaBeansDB();
				jbdb.setUserID(id);
				jbdb.setUserName(name);
				jbdb.setUserLat(Double.parseDouble(lat));
				jbdb.setUserLon(Double.parseDouble(lon));
				
				session.setAttribute("javab", jbdb);
				
				break;
			}
			break;
		
		case "manageCategorySc" :
			MgmtManageCategory.callManageCategory(request,session);
			
			break;
			
		case "manageStorageSc":
			MgmtStorageMaster.callMgmtStorageMaster(request,session);
			
			break;
			
		case "itemRegisterSc":
			MgmtItemRegister.callMgmtItemMaster(request,session);
		
			break;
	}
		return localmap;
	}
	
}



