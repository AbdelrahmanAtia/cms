package org.javaworld.cmsbackend.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

	/*
	 * format may look like this:- 
	 *   dd/MM/yyyy HH:mm:ss 
	 *   yyyy/MM/dd HH:mm:ss
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		String now = formatter.format(date);
		return now;
	}

	public static long getStartOfTheDayAsTimeStamp() {
		LocalDate now = LocalDate.now();
		Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
		return timestamp.getTime();
	}

	public static long getEndOfTheDayAsTimeStamp() {
		return getStartOfTheDayAsTimeStamp() + (24 * 60 * 60 * 1000) - 1;
	}

	public static long getCurrentDateTimeAsTimeStamp() {
		LocalDate now = LocalDate.now();
		Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
		return timestamp.getTime();
	}

}
