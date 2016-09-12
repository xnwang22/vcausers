package org.nbme.vca.users.utils;

import org.springframework.beans.factory.annotation.Value;

public class UserUtil {
	
	private static String serviceUrl;
	
	
	
	@Value("${"+ UserConstants.a + "}")
	public void setServiceUrl(String serviceUrlProp){
		serviceUrl = serviceUrlProp;
	}

}
