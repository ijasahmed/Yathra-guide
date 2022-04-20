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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Viewhotel extends Activity  implements OnItemClickListener{
	ListView l;
	SharedPreferences sp;
    ArrayList<String> id,name,place,post,district,state,pin,phone_no,e_mail,description,image,room_type,price,no_of_rooms,hotel_id,latitude,longitude;
String url;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewhotel);
		 l=(ListView)findViewById(R.id.listView1);
		 sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 
		 
		  url="http://"+sp.getString("ip", "")+":5000/view_hotel";
		  l.setOnItemClickListener(this);
		  if(android.os.Build.VERSION.SDK_INT>9)
		  {
			  StrictMode.ThreadPolicy policy=new
			StrictMode.ThreadPolicy.Builder().permitAll().build();
			  StrictMode.setThreadPolicy(policy);
		  }
		 RequestQueue queue = Volley.newRequestQueue(Viewhotel.this);


	        // Request a string response from the provided URL.
	        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	            @Override
	            public void onResponse(String response) {
	                // Display the response string.
	                Log.d("+++++++++++++++++",response);
	                try {

	                    JSONArray ar=new JSONArray(response);

	                    id=new ArrayList<String>();
	                    name=new ArrayList<String>();
	                    place=new ArrayList<String>();
	                    post=new ArrayList<String>();
	                    district=new ArrayList<String>();
	                    state=new ArrayList<String>();
	                    pin=new ArrayList<String>();
	                    phone_no=new ArrayList<String>();
	                    e_mail=new ArrayList<String>();
	                    description=new ArrayList<String>();
	                    image=new ArrayList<String>();
	                    room_type=new ArrayList<String>();
	                    price=new ArrayList<String>();
	                    no_of_rooms=new ArrayList<String>();
	                    hotel_id=new ArrayList<String>();
	                    latitude=new ArrayList<String>();
	                    longitude=new ArrayList<String>();

	                    for(int i=0;i<ar.length();i++)
	                    {
	                        JSONObject jo=ar.getJSONObject(i);
	                        id.add(jo.getString("id"));
	                        name.add(jo.getString("name"));
	                        place.add(jo.getString("place"));
	                        post.add(jo.getString("post"));
	                        district.add(jo.getString("district"));
	                        state.add(jo.getString("state"));
	                        pin.add(jo.getString("pin"));
	                        phone_no.add(jo.getString("phone_no"));
	                        e_mail.add(jo.getString("e_mail"));
	                        description.add(jo.getString("description"));
	                        image.add(jo.getString("image"));
	                        room_type.add(jo.getString("room_type"));
	                        price.add(jo.getString("price"));
	                        no_of_rooms.add(jo.getString("no_of_rooms"));
	                        hotel_id.add(jo.getString("hotel_id"));
	                        latitude.add(jo.getString("latitude"));
	                        longitude.add(jo.getString("longitude"));
	                    }
//	                    ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
	                    l.setAdapter(new Custom(Viewhotel.this, name, image));
	                   

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
		getMenuInflater().inflate(R.menu.viewhotel, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		 Intent i=new Intent(getApplicationContext(),Viewrooms.class);
		    i.putExtra("name",name.get(arg2));
		    i.putExtra("place", place.get(arg2));
			i.putExtra("phone_no", phone_no.get(arg2));
			i.putExtra("e_mail", e_mail.get(arg2));
			i.putExtra("room_type",room_type.get(arg2));
			i.putExtra("price",price.get(arg2));
			i.putExtra("no_of_rooms", no_of_rooms.get(arg2));
			i.putExtra("rid", id.get(arg2));
			i.putExtra("hid", hotel_id.get(arg2));
			i.putExtra("latitude", latitude.get(arg2));
			i.putExtra("longitude", longitude.get(arg2));
			
			
			
			startActivity(i);	
		 
				
	        
		
		
		
		
		
	}

}
