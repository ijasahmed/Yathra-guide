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
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	EditText e1,e2;
	Button b;
	TextView t;
	SharedPreferences sp;
	String url="",ip="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        b=(Button)findViewById(R.id.button1);
        t=(TextView)findViewById(R.id.textView3);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
       
        ip="192.168.43.74";
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("ip",ip);
        ed.commit();


        url="http://"+sp.getString("ip", "")+":5000/login";


        
        t.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i= new Intent(getApplicationContext(),Registration.class);
				startActivity(i);
				
			}
		});
        
        
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final String uname=e1.getText().toString();
				final String password=e2.getText().toString();

                if (uname.equals("")) {
                    e1.setError("Enter username");
                    e1.requestFocus();
                } else if (password.equals("")) {
                	e2.setError("Enter password");
                	e2.requestFocus();
                } 

                else
                {
				

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Login.this);
               
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
                                    	 SharedPreferences.Editor ed=sp.edit();
                                         ed.putString("lid",res);
                                         ed.commit();

                                    	
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
                        params.put("name", uname);
                        params.put("pwd", password);
                       
                        return params;
                    }
                };
               // Add the request to the RequestQueue.
                queue.add(stringRequest);
                }

				
			}
		});
        
        
        


        
        
        
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    
}
