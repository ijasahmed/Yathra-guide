package com.example.yagu;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Userhome extends Activity {
	Button b1,b2,b3,b4,b5;
	SharedPreferences sp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userhome);
		 b1=(Button)findViewById(R.id.button2);
		 b2=(Button)findViewById(R.id.button3);
		 b3=(Button)findViewById(R.id.button5);
		 b4=(Button)findViewById(R.id.button1);
		 b5=(Button)findViewById(R.id.button4);
		 sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		 
		 
		 
		
		 b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					
					Intent i=new Intent(getApplicationContext(),Viewplaces.class);
					startActivity(i);
				}
			});
		 
		 
		 b2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					
					Intent i=new Intent(getApplicationContext(),Viewhotel.class);
					startActivity(i);
				}
			});
		 
		
		
			
		 b3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					
					Intent i=new Intent(getApplicationContext(),Login.class);
					startActivity(i);
				}
			});
		 b4.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					
					Intent i=new Intent(getApplicationContext(),Viewbookingstatus.class);
					startActivity(i);
				}
			});
			
		 b5.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					
					Intent i=new Intent(getApplicationContext(),Viewplacestatus.class);
					startActivity(i);
				}
			});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.userhome, menu);
		return true;
	}

}
