package com.example.yagu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Place extends Activity {

	Button b1,b2,b3,b4;
	TextView t1,t2,t3;
	SharedPreferences sp;
    String url="";
   ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);

	    b1=(Button)findViewById(R.id.button1);
	    b2=(Button)findViewById(R.id.button2);
	    b3=(Button)findViewById(R.id.button3);
	    b4=(Button)findViewById(R.id.button4);
	    t1=(TextView)findViewById(R.id.textView4);
		t2=(TextView)findViewById(R.id.textView5);
		t3=(TextView)findViewById(R.id.textView6);
		img=(ImageView)findViewById(R.id.imageView1);
	    sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t1.setText(getIntent().getStringExtra("place"));
        t2.setText(getIntent().getStringExtra("district"));
        t3.setText(getIntent().getStringExtra("description"));
        final String lat=(getIntent().getStringExtra("latitude"));;
		final String longi=(getIntent().getStringExtra("longitude"));;
		
        
        java.net.URL thumb_u;

        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            thumb_u = new java.net.URL("http://"+sp.getString("ip","")+":5000/static/placeimg/"+getIntent().getStringExtra("img"));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            img.setImageDrawable(thumb_d);
            
        

        }
       
        catch (Exception e)
        {
        	Toast.makeText(getApplicationContext(), e+"",Toast.LENGTH_LONG).show();
        }
  	
        
        
        
        
   	 url="http://"+sp.getString("ip", "")+":5000/book_place";
		
   	b1.setOnClickListener(new View.OnClickListener() {
   				
   				@Override
   				public void onClick(View arg0) {
   					// TODO Auto-generated method stub
   				
   					
   					

   	                // Instantiate the RequestQueue.
   	                RequestQueue queue = Volley.newRequestQueue(Place.this);
   	               
   	                // Request a string response from the provided URL.
   	                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
   	                            @Override
   	                            public void onResponse(String response) {
   	                                // Display the response string.
   	                                Log.d("+++++++++++++++++",response);
   	                                try {
   	                                    JSONObject json=new JSONObject(response);
   	                                    String res=json.getString("task");
   	                                    
   	                                    if(res.equals("invalid"))
   	                                    {
   	                                    	 Toast.makeText(getApplicationContext(),"invalid",Toast.LENGTH_LONG).show();
   	                                         
   	                                    	
   	                                    }
   	                                    else
   	                                    { 
   	                                    	
   	                                    	Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
   	                                    startActivity(new Intent(getApplicationContext(),Userhome.class));
   	                                    
   	                                    	
   	                                    }
   	                                    
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
   	                        params.put("uid",sp.getString("lid", "") );
   	                        params.put("id", getIntent().getStringExtra("id"));
   	                        return params;
   	                    }
   	                };
   	               // Add the request to the RequestQueue.
   	                queue.add(stringRequest);

   					
   				}
   			});
   	b3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(getApplicationContext(),Experience.class);
			startActivity(i);
			
		}
	}); 
   	
b4.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(getApplicationContext(),Viewexperiance.class);
			startActivity(i);
			
		}
	}); 
b2.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
		Intent intent=new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?q="+lat+","+longi+""));
		startActivity(intent);
	}
});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place, menu);
		return true;
	}

}
