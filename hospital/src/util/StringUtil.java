package util;

import java.util.Locale;

/**   
* @Title: StringUtil.java 
* @Package util 
* @Description:  
* @author Pengbin Li   
* @date 2017年9月9日 下午10:00:19 
* @version V1.0   
*/

public class StringUtil {
	
	public static boolean isEmptyOrNull(String str) {
		boolean result = false;
		
		if (null == str ) {
			result = true;
		} else if (str.trim().length() == 0) {
			result = true;
		}
		
		return result;
	}
	
	public static String lowerFirstLetter(String str) {		
		String result = str.substring(0, 1).toLowerCase(Locale.US) + str.substring(1);
		
		return result;
	}
	
}
