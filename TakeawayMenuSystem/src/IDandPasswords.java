import java.util.HashMap;

public class IDandPasswords {
	HashMap<String, String> loginInfo = new HashMap<String, String>();
	
	IDandPasswords(){
		loginInfo.put("Dagogo", "helloDagogo");
		loginInfo.put("Alex", "helloAlex");
		loginInfo.put("Kelvin", "helloKelvin");
	}
	
	protected HashMap getLoginInfo(){
		return loginInfo();
	}
}
