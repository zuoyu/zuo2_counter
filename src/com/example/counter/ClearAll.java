package com.example.counter;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClearAll extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_all);
		
		final Button backm = (Button) findViewById(R.id.backr);
		backm.setOnClickListener(new mback());
		
		final Button clearfile = (Button) findViewById(R.id.result);
		clearfile.setOnClickListener(new fileclear());
	}
	class fileclear implements OnClickListener
	{

		@Override
		 public void onClick(View v) 

        { 

			String s = "";
			ArrayList<DataSaving> mydata = new ArrayList<DataSaving>();
			Gson gson = new Gson();
			try{
				FileInputStream fis = openFileInput("file.txt");
				BufferedReader er = new BufferedReader(new InputStreamReader(fis));
				s= er.readLine();
			}catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try{
				FileOutputStream fas = openFileOutput("file.txt",Context.MODE_PRIVATE);
				
				int length = mydata.size();
				for(int i = 0; i< length;i++){
					fas.write(gson.toJson(mydata.get(i)).getBytes());
					fas.write("\n".getBytes());
				}
				if(length == 0)
				{
					String ggg = "";
					fas.write(ggg.getBytes());
					
					String massage = "All counters are removed.";
					Toast.makeText(ClearAll.this, massage, Toast.LENGTH_LONG).show();
					
					Intent backbotton = new Intent();
					backbotton.setClass(ClearAll.this,NewCounter.class);
					ClearAll.this.startActivity(backbotton);
					
				}
				fas.close();
			}catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } 

   }
	class mback implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent backbotton = new Intent();
			backbotton.setClass(ClearAll.this,ViewList.class);
			ClearAll.this.startActivity(backbotton);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clear_all, menu);
		return true;
	}

}