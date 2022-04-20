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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Bank extends Activity {
	
	Spinner s1,s2;
	EditText e1,e2;
	TextView e3;
	Button b;
	SharedPreferences sp;
	String url="",bid="";
	String bank[]={"Federal","Gramin"};
	String branch[]={"Pulamanthole","Perinthalmanna"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank);
		e1=(EditText)findViewById(R.id.editText2);
		e2=(EditText)findViewById(R.id.editText1);
		e3=(TextView)findViewById(R.id.textView6);
	    b=(Button)findViewById(R.id.button1);
	    s1=(Spinner)findViewById(R.id.spinner1);
	    s2=(Spinner)findViewById(R.id.spinner2);
	    e3.setText(getIntent().getStringExtra("pr"));
	    bid=getIntent().getStringExtra("bid");
	    sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    
	    ArrayAdapter<String> ad= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,bank);
	    s1.setAdapter(ad);
	    ArrayAdapter<String> ad2= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,branch);
	    s2.setAdapter(ad2);
	    
	    

	    b.setOnClickListener(new View.OnClickListener() {
	   			
	   			@Override
	   			public void onClick(View arg0) {
	   				// TODO Auto-generated method stub
	   				
	   				final String ifsc=e1.getText().toString();
	   				final String accno=e2.getText().toString();
	   				final String amount=e3.getText().toString();
	   				final String name=s1.getSelectedItem().toString();
	   				final String branch=s2.getSelectedItem().toString();
	   				

	                if (ifsc.equals("")) {
	                    e1.setError("Enter ifsc code");
	                    e1.requestFocus();
	                } else if (accno.equals("")) {
	                	e2.setError("Enter account number");
	                	e2.requestFocus();
	                } else if(accno.length()!=14)
	                {
	                    e2.setError("Minimum 14 nos required");
	                    e2.requestFocus();
	                }
	                else
	                {



	   				
	   		
	   				
	   			 url="http://"+sp.getString("ip", "")+":5000/bank";

	                   // Instantiate the RequestQueue.
	                   RequestQueue queue = Volley.newRequestQueue(Bank.this);
	                  
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
	                                       	 Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
	                                            startActivity(new Intent(getApplicationContext(),Userhome.class));
	                                     
	                                       }
	                                       else
	                                       {
	                                       	 Toast.makeText(getApplicationContext(),"invalid",Toast.LENGTH_LONG).show();
	                                           
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
	                           params.put("name", name);
	                           params.put("branch",branch);
	                           params.put("ifsc",ifsc);
	                           params.put("accno",accno);
	                           params.put("amount",amount);
	                           params.put("bookid",bid);
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
		getMenuInflater().inflate(R.menu.bank, menu);
		return true;
	}

}
