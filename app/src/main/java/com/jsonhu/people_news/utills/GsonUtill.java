package com.jsonhu.people_news.utills;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GsonUtill {

	public static String setObjectToJSON(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static <T> T getObejctFromJSON(String jsonStr, Class<T> cls) {
		Gson gson = new Gson();
		T t = gson.fromJson(jsonStr, cls);
		return t;
	}

	// public static <T> List<T> getListObjectFromJSON(String jsonStr, Class<T>
	// cls) {
	// Gson gson = new Gson();
	// List<T> list = gson.fromJson(jsonStr, new TypeToken<List<T>>() {
	// }.getType());
	// return list;
	// }
	public static List getListObjectFromJSON(String jsonStr, TypeToken typeToken) {
		Gson gson = new Gson();
		List list = gson.fromJson(jsonStr, typeToken.getType());
		return list;
	}

	public static <T> List<Map<String, Object>> getListMapFromJSON(
			String jsonStr, Class<T> cls) {
		return new Gson().fromJson(jsonStr,
				new TypeToken<List<Map<String, Object>>>() {
				}.getType());
	}

}
