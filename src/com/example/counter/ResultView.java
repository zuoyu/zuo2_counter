package com.example.counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.counter.MainActivity.bresult;

public class ResultView extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_view);
		populateListView();// put the result into the view list. 
		registerClickCallback(); // to able click the view list.
		
		final Button backr = (Button) findViewById(R.id.backr);
		backr.setOnClickListener(new rback());
		
		final Button searchr = (Button) findViewById(R.id.SEARCH);
		searchr.setOnClickListener(new rsearch());
	}
	
	class rback implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent backbotton = new Intent();
			backbotton.setClass(ResultView.this,MainActivity.class);
			ResultView.this.startActivity(backbotton);
			
		}
		
	}
	
	class rsearch implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent backbotton = new Intent();
			backbotton.setClass(ResultView.this,Searching.class);
			ResultView.this.startActivity(backbotton);
			
		}

		/**
		 * @uml.property  name="searching"
		 * @uml.associationEnd  inverse="rsearch:com.example.counter.Searching"
		 */
		private Searching searching;

		/**
		 * Getter of the property <tt>searching</tt>
		 * @return  Returns the searching.
		 * @uml.property  name="searching"
		 */
		public Searching getSearching()
		{

			return searching;
		}

		/**
		 * Setter of the property <tt>searching</tt>
		 * @param searching  The searching to set.
		 * @uml.property  name="searching"
		 */
		public void setSearching(Searching searching)
		{

			this.searching = searching;
		}
		
	}
	private void registerClickCallback()
	{
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position,
					long id)
			{

				// TODO Auto-generated method stub
				TextView textView = (TextView) viewClicked;
				String massage = "You Clicked #" + position + ", which is string:" + textView.getText().toString();
				Toast.makeText(ResultView.this, massage, Toast.LENGTH_LONG).show();
				
			}
		});
		
	}

	private void populateListView()
	{
		
		// TODO Auto-generated method stub
		
		String ss = "";
		ArrayList<String> myIteam = new ArrayList<String>();
		ArrayList<Counter> orderlist = new ArrayList<Counter>();
		ArrayList<String> record =  new ArrayList<String>();
		ArrayList<Integer> numberrecord = new ArrayList<Integer>();
		
		Gson gson = new Gson();
		try{
			FileInputStream fis = openFileInput("file.txt");
			BufferedReader er = new BufferedReader(new InputStreamReader(fis));
			ss= er.readLine();
			Log.v("PRNT JSON",ss);
			while (ss != null ){
				DataSaving datas = gson.fromJson(ss,DataSaving.class);
				String texti = datas.getText();
				myIteam.add(texti);
				ss= er.readLine();
			}
		}
		catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		int record_number =0;
		int size = myIteam.size();
		String record_name = "";
		Counter obj = new Counter(record_name,record_number);
		
		for (int i = 0; i<size; i++)
		{
			record_name = myIteam.get(i);
			if(record_name != null){
				obj.setText(record_name);
				for (int j =0; j<size;j++){
					String namei = myIteam.get(j);
					if(obj.getText().equals(namei)){
						obj.addNumber();
						myIteam.set(j, null);
					}
				}
				String fin_name = obj.getText();
				int fin_number = obj.getNumber();
				Counter abj =new Counter(fin_name,fin_number);
				orderlist.add(abj);
				record.add(fin_name +" | "+fin_number);
				obj.setNumber(0);
				
			}
		}
		
	
		for (int i =0;i<orderlist.size();i++){
			Counter good = orderlist.get(i);
			int number_pop = good.getNumber();
			numberrecord.add(number_pop);
		}
		
		// the bubble sort two list then can get 
		int n = numberrecord.size();
		boolean swapped = true;
		while(swapped){
			swapped = false;
			for(int i = 0; i< (n-1);i++){
				if(numberrecord.get(i) > numberrecord.get(i+1)){
					int temp = numberrecord.get(i);
					numberrecord.set(i, numberrecord.get(i+1));
					numberrecord.set(i+1, temp);
					
					String temp1 = record.get(i);
					record.set(i, record.get(i+1));
					record.set(i+1, temp1);
					
					swapped = true;
				}
			}
			
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this,
				R.layout.data_item,
				record);
		
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_view, menu);
		return true;
	}


	/**
	 * @uml.property  name="bresult"
	 * @uml.associationEnd  inverse="resultView:com.example.counter.MainActivity.bresult"
	 */
	private bresult bresult;


	/**
	 * Getter of the property <tt>bresult</tt>
	 * @return  Returns the bresult.
	 * @uml.property  name="bresult"
	 */
	public bresult getBresult()
	
	{
	
		return bresult;
	}

	/**
	 * Setter of the property <tt>bresult</tt>
	 * @param bresult  The bresult to set.
	 * @uml.property  name="bresult"
	 */
	public void setBresult(bresult bresult)
	
	{
	
		this.bresult = bresult;
	}

}
