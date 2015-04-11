package com.example.readjson.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.example.readjson.cityDomain.city;
import com.example.readjson.cityDomain.province;
import com.example.readjson.db.WeatherDB;

public class util {
	
	
	public void HandleProvinceAndCity(Context context,WeatherDB weatherDB){
	String result;
	
	try {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("cityId.json")));  
        String line = null;  
        StringBuilder builder = new StringBuilder();  
        while (null != (line = bufferedReader.readLine())) {  
            builder.append(line).append("\n");  
        } 
        result=builder.toString();
        
        bufferedReader.close();
        JSONObject top = new JSONObject(result);
        JSONArray jsonprovince = top.getJSONArray("城市代码");
        
        for(int i=0;i< jsonprovince.length();i++){
        	JSONObject jsonProvince = jsonprovince.getJSONObject(i);
        	
        	String PName = jsonProvince.getString("省");
        	
        	JSONArray cityJ = jsonProvince.getJSONArray("市") ;
        	province p = new province();
        	p.setProvince(PName);
        	p.setProvinceId(i);
        	weatherDB.SaveProvince(p);
        	
        	for(int j =0;j< cityJ.length();j++){
        		JSONObject cityN = cityJ.getJSONObject(j);
        		String Cname = cityN.getString("市名");
        		String CID = cityN.getString("编码");
        		city c = new city();
        		
        		c.setCityId(CID);
        		c.setCityName(Cname);
        		c.setProvinceId(i);
        		weatherDB.saveCity(c);
        		
        		
        		
        	}
        	
        	
        	
        }
        
        
        
        
        
        
	} catch (IOException | JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
