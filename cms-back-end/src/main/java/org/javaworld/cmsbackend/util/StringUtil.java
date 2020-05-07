package org.javaworld.cmsbackend.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class StringUtil {

	/**
	 * @param uglyJson an ugly formatted json string
	 * @return a pretty formatted json string
	 */
	public static String prettyJson(String uglyJson) {
		String prettyJson = null;
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			prettyJson = gson.toJson(JsonParser.parseString(uglyJson));
		} catch (Exception e) {
			return uglyJson;
		}
		return prettyJson;
	}

	
	public static String removeNewLineCharacters(String str) {
		try {
			String modifiedStr = str.replace("\r", "").replace("\n", "").replace("\r\n", "");
			return modifiedStr;
		} catch (Exception e) {
			return str;
		}
	}

}
