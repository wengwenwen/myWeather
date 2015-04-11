package com.example.readjson;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.readjson.Util.sharepreferenceMake;

import android.app.Activity;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Switch;
import android.widget.TextView;

public class WeatherActivity extends Activity implements OnClickListener {
	private Button refresh,switchcity ;
	private TextView cityname,date,weather,temp;
	private SharedPreferences presf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather);
		refresh = (Button) findViewById(R.id.refresh);
		switchcity = (Button) findViewById(R.id.cityswitch);
		cityname = (TextView) findViewById(R.id.cityName);
		date = (TextView) findViewById(R.id.date);
		weather = (TextView) findViewById(R.id.weather);
		temp = (TextView) findViewById(R.id.temp);
		presf = PreferenceManager.getDefaultSharedPreferences(this);
		
		final String citycode = getIntent().getStringExtra("cityid");
		if(!TextUtils.isEmpty(citycode)){
			
					queryWeatherCode(citycode);
				
			
		}else{
			showWeather();
		}
		refresh.setOnClickListener(this);
		switchcity.setOnClickListener(this);
		
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cityswitch:
			SharedPreferences.Editor editor =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
			editor.putBoolean("city_selected", false);
			editor.commit();
			
			Intent intent1 = new Intent(WeatherActivity.this,MainActivity.class);
			startActivity(intent1);
			finish();
			
			break;
		case R.id.refresh:
			String weathercod = presf.getString("weather_code", "");
			if(weathercod!=null){
				queryWeatherCode(weathercod);
			}
			
		default:
			break;
		}
		
	}
	private void showWeather() {
		// TODO Auto-generated method stub
		
		cityname.setText(presf.getString("cityname", ""));
		weather.setText(presf.getString("weather", ""));
		date.setText(presf.getString("date", ""));
		temp.setText(presf.getString("temp", ""));
		
		
		
		
	}

	private void queryWeatherCode(String citycode) {
		//http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=101010600&weatherType=0
		String path = "http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode="+citycode+"&weatherType=0";
		
		 queryFromServer(path);
		
	
		
	}



	private void queryFromServer(final String path) {
		new Thread(){
			@Override
			public void run(){
		try {
			HttpClient httpc = new DefaultHttpClient();
			HttpGet httpG = new  HttpGet(path);
			HttpResponse httpR = httpc.execute(httpG);
			int code = httpR.getStatusLine().getStatusCode();
			if(code == 200){
				HttpEntity entity = httpR.getEntity();
				String response = EntityUtils.toString(entity);
				sharepreferenceMake.handleWeatherResponse(getApplicationContext(), response);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						showWeather();
					}
				});
			}
			
			
			
			
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		}.start();
		
	}



	
	

	
}
