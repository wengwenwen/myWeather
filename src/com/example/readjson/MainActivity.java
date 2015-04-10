package com.example.readjson;



import java.util.ArrayList;
import java.util.List;

import com.example.readjson.Util.util;

import com.example.readjson.cityDomain.city;
import com.example.readjson.cityDomain.province;
import com.example.readjson.db.WeatherDB;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private WeatherDB weatherDB;
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	private TextView titleText;
	private ListView list_view;
	private List<province> provinceList = new ArrayList<province>();
	private List<city> cityList;
	private List<String> datalist= new ArrayList<String>();
	private province provinceSelected;
	private city citySelected;
	private int currentLevel;
	private ArrayAdapter<String> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		list_view =(ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.title_text);
		
		weatherDB = WeatherDB.getInstance(this);
		
		
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datalist);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(currentLevel == LEVEL_PROVINCE){
					provinceSelected = provinceList.get(position);
					queryCities();
				}else if(currentLevel == LEVEL_CITY){
					citySelected = cityList.get(position);
				}
				
				
			}
			
		});
		
		
		queryProvinces();
		
		
		
	}


	private void queryProvinces() {
		// TODO Auto-generated method stub
		provinceList = weatherDB.loadProvince();
		
		if(provinceList.size()>0){
			datalist.clear();
			for(province Province:provinceList){
				String add = Province.getProvince();
				if(add !=null){
				datalist.add(add);
				}
			}
			adapter.notifyDataSetChanged();
			list_view.setSelection(0);
			titleText.setText("中国");
			currentLevel = LEVEL_PROVINCE;
			
		}else{
			return;
		}
		
		
	}
	private void queryCities(){
		cityList = weatherDB.loadCity(provinceSelected.getProvinceId());
		if(cityList.size() > 0){
			datalist.clear();
			for(city City:cityList){
				if(City.getCityName() !=null){
				datalist.add(City.getCityName());
				}
			}
			adapter.notifyDataSetChanged();
			list_view.setSelection(0);
			titleText.setText(provinceSelected.getProvince());
			currentLevel = LEVEL_CITY;
		}else{
			return;
		}
	}

	
}
