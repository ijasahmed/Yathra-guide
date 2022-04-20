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

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Viewrooms extends Activity {
	Button b,b1;
	TextView t1,t2,t3,t4,t5,t6,t7;
	EditText e1;
	SharedPreferences sp;
	String url="",ip="";
	
	String noofroomss="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewrooms);
		b=(Button)findViewById(R.id.button1);
		b1=(Button)findViewById(R.id.button2);
		e1=(EditText)findViewById(R.id.editText1);
		t1=(TextView)findViewById(R.id.textView5);
		t2=(TextView)findViewById(R.id.textView6);
		t3=(TextView)findViewById(R.id.textView7);
		t4=(TextView)findViewById(R.id.textView8);
		t5=(TextView)findViewById(R.id.textView10);
		t6=(TextView)findViewById(R.id.textView13);
		t7=(TextView)findViewById(R.id.textView14);
		sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		t1.setText(getIntent().getStringExtra("name"));
		t2.setText(getIntent().getStringExtra("place"));
		t3.setText(getIntent().getStringExtra("phone_no"));
		t4.setText(getIntent().getStringExtra("e_mail"));
		t5.setText(getIntent().getStringExtra("room_type"));
		t6.setText(getIntent().getStringExtra("price"));
		t7.setText(getIntent().getStringExtra("no_of_rooms"));
		final String lat=(getIntent().getStringExtra("latitude"));;
		final String longi=(getIntent().getStringExtra("longitude"));;
		
		 url="http://"+sp.getString("ip", "")+":5000/book_rooms";
		
b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			 noofroomss=e1.getText().toString();
			 int availroom=Integer.parseInt(getIntent().getStringExtra("no_of_rooms"));
			 Toast.makeText(getApplicationContext(),"resh"+noofroomss,Toast.LENGTH_LONG).show();
			if (noofroomss.equals("")) {
                e1.setError("Enter a number");
                e1.requestFocus();
            } 
			else if(Integer.parseInt(noofroomss)>availroom)
			{
				 Toast.makeText(getApplicationContext(),"Rooms not available",Toast.LENGTH_LONG).show();
			}
			
            else
            {
		
				
				

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Viewrooms.this);
               
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
                        params.put("hid", getIntent().getStringExtra("hid"));
                        params.put("roomid",getIntent().getStringExtra("rid")); 
                        params.put("no_of_room",noofroomss); 
                        return params;
                    }
                };
               // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
			}
		});
b1.setOnClickListener(new View.OnClickListener() {
	
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
		getMenuInflater().inflate(R.menu.viewrooms, menu);
		return true;
	}

}
