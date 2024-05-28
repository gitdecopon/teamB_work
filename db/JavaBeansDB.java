package browser.db;

import java.io.Serializable;

public class JavaBeansDB implements Serializable {
	private String userID, userName, errMessage;
	private double userLat, userLon;
	
	public JavaBeansDB () {
		//引数のないコンストラクタ
	}
	
	
	public void setUserID(String id) {this.userID = id;}
	public void setUserName(String name) {this.userName = name;}
	public void setUserLat(double lat) {this.userLat = lat;}
	public void setUserLon(double lon) {this.userLon = lon;}
	
	public void setErrMessage(String errMes) {this.errMessage = errMes;}
	
	public String getUserID() {return this.userID;}
	public String getUserName() {return this.userName;}
	public String getUserLat() {return String.valueOf(this.userLat);}
	public String getUserLon() {return String.valueOf(this.userLon);}
	
	public String getErrMessage() {return this.errMessage;}
}
