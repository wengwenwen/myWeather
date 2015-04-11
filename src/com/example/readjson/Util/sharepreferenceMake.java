package com.example.readjson.Util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class sharepreferenceMake {
	public static void handleWeatherResponse(Context context,String response){
		try {
			JSONObject jsono = new JSONObject(response);
			JSONObject weather = jsono.getJSONObject("weatherinfo");
			String cityname = weather.getString("city");
			String wea = weather.getString("weather1");
			String temp = weather.getString("temp1");
			String date = weather.getString("date_y");
			String weathercode = weather.getString("cityid");
			saveWeatherInfo(context,cityname,wea,temp,date,weathercode);
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void saveWeatherInfo(Context context, String cityname,
			String wea, String temp, String date,String weathercode) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor =  PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString("cityname", cityname);
		editor.putString("weather", wea);
		editor.putString("temp", temp);
		editor.putString("date", date);
		editor.putString("weathercode", weathercode);
		editor.putBoolean("city_selected", true);
		editor.commit();
	}
}
