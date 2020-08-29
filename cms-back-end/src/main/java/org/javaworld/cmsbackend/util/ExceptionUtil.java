package org.javaworld.cmsbackend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

	public static void logExceptionDetails(Exception ex) {
		logger.info(">> ///////////////////--Exception Details--////////////////////////////////////////////");
		// print exception details
		StackTraceElement[] stackTraces = ex.getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraces) {
			logger.info(">> " + stackTraceElement.toString());
		}

		// print exception cause details
		Throwable cause = ex.getCause();
		if (cause != null) {
			logger.info("Caused by: " + cause.getClass() + ": " + cause.getMessage());
			StackTraceElement[] causeStackTraces = cause.getStackTrace();
			for (StackTraceElement stackTraceElement : causeStackTraces) {
				logger.info(">> " + stackTraceElement.toString());
			}
		}
		logger.info(">> /////////////////////////////////////////////////////////////////////////////////");
	}

}
