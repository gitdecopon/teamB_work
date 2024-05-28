package browser.db;

public class DBControllerJsp {
	public static Object[][] DBAssignment(String section, String type, String id)  {
		//section:項目　例)usermaster type:戻り値の項目　例)name　id：ユーザーID
		Object[][] arrResult = null;
		switch (section) {
		
		case "usermaster" :
			switch (type) {
			case "name" :
				arrResult[0][0] = MgmtUserMaster.getRegistNameJsp(id);
				break;
			}
			break;

		case "ManageCategory" :
			switch (type) {
			case "data":
				arrResult = MgmtManageCategory.callManageCategoryRegistData(id);
				break;
			}
			break;

		case "ManageStorage":
			switch (type) {
			case "data":
				arrResult = MgmtStorageMaster.callManageStorageRegistData(id);
				break;
			}
			break;
		
		case "ManageSeason":
			switch (type) {
			case "data":
				arrResult = MgmtMstSeason.callSeasonMaster();
				break;
			}
			break;
			
		case "DataCounter":
			switch (type) {
			case "ITEM_ID":
				arrResult = MgmtDataObjectCounter.callMgmtDataItemCounter();
				break;
			case "COODINAT_ID":
				arrResult = MgmtDataObjectCounter.callMgmtDataCoodCounter();
				break;
				
			}
			break;
	}
		return arrResult;
	}
	
}

