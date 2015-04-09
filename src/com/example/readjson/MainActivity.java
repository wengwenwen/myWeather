package com.example.readjson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.readjson.cityDomain.city;
import com.example.readjson.cityDomain.province;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String result;
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("cityId.json")));  
            String line = null;  
            StringBuilder builder = new StringBuilder();  
            while (null != (line = bufferedReader.readLine())) {  
                builder.append(line).append("\n");  
            } 
            result=builder.toString();
            Log.d("json", result);
            bufferedReader.close();
            JSONObject top = new JSONObject(result);
            JSONArray jsonprovince = top.getJSONArray("城市代码");
            List<province> provinces = new ArrayList<province>();
            for(int i=0;i< jsonprovince.length();i++){
            	JSONObject jsonProvince = jsonprovince.getJSONObject(i);
            	
            	String PName = jsonProvince.getString("省");
            	
            	JSONArray cityJ = jsonProvince.getJSONArray("市") ;
            	province p = new province();
            	p.setProvince(PName);
            	System.out.println(PName);
            	
            	for(int j =0;j< cityJ.length();j++){
            		JSONObject cityN = cityJ.getJSONObject(j);
            		String Cname = cityN.getString("市名");
            		String CID = cityN.getString("编码");
            		city c = new city();
            		System.out.println("城市"+Cname+CID);
            		c.setCityId(CID);
            		c.setCityName(Cname);
            		p.addCity(c);
            		
            		
            	}
            	provinces.add(p);
            	
            	
            }
            
            
            
            
            
            
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	
}
