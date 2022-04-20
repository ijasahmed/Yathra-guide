package com.example.yagu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Viewplaces extends Activity implements OnItemClickListener{
	ListView l;
	SharedPreferences sp;
    String url="",ip="";

    ArrayList<String>id,place,district,description,image,latitude,longitude;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewplaces);
		l=(ListView)findViewById(R.id.listView1);
		sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		  url="http://"+sp.getString("ip", "")+":5000/view_place";
		  l.setOnItemClickListener(this);

		  if(android.os.Build.VERSION.SDK_INT>9)
		  {
			  StrictMode.ThreadPolicy policy=new
			StrictMode.ThreadPolicy.Builder().permitAll().build();
			  StrictMode.setThreadPolicy(policy);
		  }
		 RequestQueue queue = Volley.newRequestQueue(Viewplaces.this);


	        // Request a string response from the provided URL.
	        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	            @Override
	            public void onResponse(String response) {
	                // Display the response string.
	                Log.d("+++++++++++++++++",response);
	                try {

	                    JSONArray ar=new JSONArray(response);

	                    id=new ArrayList<String>();
	                  
	                    place=new ArrayList<String>();
	                    district=new ArrayList<String>();
	                    description=new ArrayList<String>();
	                    image=new ArrayList<String>();
	                    latitude=new ArrayList<String>();
	                    longitude=new ArrayList<String>();

	       

	                    for(int i=0;i<ar.length();i++)
	                    {
	                        JSONObject jo=ar.getJSONObject(i);
	                        id.add(jo.getString("id"));
	                       
	                        place.add(jo.getString("place"));
	                        district.add(jo.getString("district"));
	                        description.add(jo.getString("description"));
	                        image.add(jo.getString("image"));
	                        latitude.add(jo.getString("latitude"));
	                        longitude.add(jo.getString("longitude"));
	                        
	                    }
	                    ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,place);
	                    l.setAdapter(new Custom(Viewplaces.this, place, image));

	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }


	            }
	        }, new Response.ErrorListener() {
	            
	            

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					
				}
	        });
	        // Add the request to the RequestQueue.
	        queue.add(stringRequest);


		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewplaces, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
		SharedPreferences.Editor ed=sp.edit();
        ed.putString("pid",id.get(arg2));
        ed.commit();


		
		Intent i=new Intent(getApplicationContext(),Place.class);
		i.putExtra("place",place.get(arg2));
		i.putExtra("district", district.get(arg2));
		i.putExtra("description", description.get(arg2));
		i.putExtra("id", id.get(arg2));
		i.putExtra("img", image.get(arg2));
		i.putExtra("latitude", latitude.get(arg2));
		i.putExtra("longitude", longitude.get(arg2));
		startActivity(i);
		
        	
	}

}
