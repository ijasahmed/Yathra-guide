package com.example.yagu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
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
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity {
	EditText e1,e2,e3;
	RadioButton r1,r2;
	Button b;
	SharedPreferences sp;
	String url="",ip="";
	String gender="";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		e1=(EditText)findViewById(R.id.editText1);
	    e2=(EditText)findViewById(R.id.editText2);
	    e3=(EditText)findViewById(R.id.editText3);
	    r1=(RadioButton)findViewById(R.id.radioButton1);
	    r2=(RadioButton)findViewById(R.id.radioButton2);
	    b=(Button)findViewById(R.id.button1);
	    sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	
	    
	    url="http://"+sp.getString("ip", "")+":5000/userreg";
	    
	    b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			  final	String fname=e1.getText().toString();
			  final String lname=e2.getText().toString();
				
				if(r1.isChecked())
				{
					gender=r1.getText().toString();
				}
				else
				{
					gender=r2.getText().toString();
				}
			  final String phone=e3.getText().toString();
			  
			  if (fname.equals("")) {
				  e1.setError("Enter first name");
				  e1.requestFocus();
              } else if (lname.equals("")) {
            	  e2.setError("Enter last name");
            	  e2.requestFocus();
              } else if (phone.equals("")) {
            	  e3.setError("Enter phone number");
            	  e3.requestFocus();
              } else if(phone.length()!=10)
              {
            	  e3.setError("Minimum 10 nos required");
            	  e3.requestFocus();


              }

              else {

				
				


                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Registration.this);
               
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++",response);
                                try {
                                    JSONObject json=new JSONObject(response);
                                    String res=json.getString("task");
                                    
                                    if(res.equals("success"))
                                    {
                                    	 Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_LONG).show();
                                         startActivity(new Intent(getApplicationContext(),Login.class));
                                  
                                    }
                                    else
                                    {
                                    	 Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
                                        
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
                        params.put("first_name", fname);
                        params.put("second_name", lname);
                        params.put("gender", gender);
                        params.put("phone_no", phone);

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
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
