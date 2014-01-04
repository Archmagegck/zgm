package com.pms.app.util;

import org.json.simple.JSONObject;

public class UploadError {

	@SuppressWarnings("unchecked")
	public static String getError(String message){
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
}
