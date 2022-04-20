package com.example.yagu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Viewexperiance extends Activity {
	ListView l;
	SharedPreferences sp;
    String url="",ip="";

    ArrayList<String>id,desc,image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewexperiance);
		
		l=(ListView)findViewById(R.id.listView1);
		sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		  url="http://"+sp.getString("ip", "")+":5000/view_experience";
		
		  if(android.os.Build.VERSION.SDK_INT>9)
		  {
			  StrictMode.ThreadPolicy policy=new
			StrictMode.ThreadPolicy.Builder().permitAll().build();
			  StrictMode.setThreadPolicy(policy);
		  }
		 RequestQueue queue = Volley.newRequestQueue(Viewexperiance.this);


	        // Request a string response from the provided URL.
	        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	            @Override
	            public void onResponse(String response) {
	                // Display the response string.
	                Log.d("+++++++++++++++++",response);
	                try {

	                    JSONArray ar=new JSONArray(response);

	                    id=new ArrayList<String>();
	                  
	                 
	                    desc=new ArrayList<String>();
	                  
	                    image=new ArrayList<String>();
	       

	                    for(int i=0;i<ar.length();i++)
	                    {
	                        JSONObject jo=ar.getJSONObject(i);
	                        id.add(jo.getString("id"));
	                       
	                  
	                        desc.add(jo.getString("description"));
	                       
	                        image.add(jo.getString("photos"));
	                        
	                    }
//	                    ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,place);
	                    l.setAdapter(new Custom2(Viewexperiance.this, image,desc));

	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }


	            }
	        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("pid", sp.getString("pid", ""));
                   
                    return params;
                }
            };
           // Add the request to the RequestQueue.
            queue.add(stringRequest);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewexperiance, menu);
		return true;
	}

}
