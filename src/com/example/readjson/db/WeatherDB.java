package com.example.readjson.db;

import java.util.ArrayList;
import java.util.List;

import com.example.readjson.cityDomain.city;
import com.example.readjson.cityDomain.province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	public static final String DB_NAME = "weather_db";
	public static final int VERSION =1;
	private static WeatherDB weatherDB;
	private SQLiteDatabase db;
	private WeatherDB(Context context){
		WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
		db= dbHelper.getWritableDatabase();
	}
	public synchronized static WeatherDB getInstance(Context context ){
		if(weatherDB==null){
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
		
	}
	public void SaveProvince(province p){
		if(p !=null){
			ContentValues values = new ContentValues();
			values.put("province_name", p.getProvince());
			values.put("province_id", p.getProvinceId());
			
			db.insert("Province", null, values);
			
		}
		
	}
	public List<province> loadProvince(){
		List<province> list = new ArrayList<province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				province Province = new province();
				
				Province.setProvince(cursor.getString(cursor.getColumnIndex("province_name")));
				Province.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
				
				list.add(Province);
				
				
			}while(cursor.moveToNext());
		}
		return list;
	}
	public void saveCity(city City){
		if(City !=null){
			ContentValues values = new ContentValues();
			values.put("city_name",City.getCityName());
			values.put("city_code",City.getCityId());
			values.put("province_id",City.getProvinceId());
			
			db.insert("City", null, values);
			
		}
	}
	public List<city> loadCity(int provinceId){
		List<city> list = new ArrayList<city>();
		Cursor cursor =db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, 
				null, null, null);
		if(cursor.moveToFirst()){
			do {
				city City = new city();
				City.setCityId(cursor.getString(cursor.getColumnIndex("city_code")));
				City.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				City.setProvinceId(provinceId);
				list.add(City);
				
			} while (cursor.moveToNext());
		}
		return list;
		
	}
	
	
	
	

}
