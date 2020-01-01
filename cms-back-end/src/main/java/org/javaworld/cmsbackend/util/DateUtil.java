package org.javaworld.cmsbackend.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String getCurrentDate(String format) {
		//format may look like this:-
		//         dd/MM/yyyy HH:mm:ss
		//         yyyy/MM/dd HH:mm:ss
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		String now = formatter.format(date);
		return now;
	}

}
