package com.psiuol21.assignmentchecklist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.psiuol21.homeworkhelper.R;

public class Assignview extends Activity implements OnItemClickListener {

ListView listView;
DBAdapter db;
ArrayAdapter<String> listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view2);
	
		// TODO Auto-generated method stub
		
		listView=(ListView)findViewById(R.id.listView1);
		listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
		TextView tf=(TextView)findViewById(R.id.textView1);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/reg.otf");
		
		tf.setTypeface(font);
		
	
	
	try
	{
		String path="/data/data"+getPackageName()+"databases/helperdb";
		File f=new File(path);
		if(!f.exists())
		{
			CopyDB(getBaseContext().getAssets().open("myDB"),new FileOutputStream(path));
		}
		
	}catch(Exception e){}
	db=new DBAdapter(this);

	
	try {
		db.open();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Cursor c=db.getAllRecords();
	if(c.moveToFirst())
	{
		do
		{
			listAdapter.add(c.getString(0)+")"+" "+c.getString(1)+"\n"+c.getString(2)+"\t"+c.getString(3)+"\n"+c.getString(4));
			
		}while(c.moveToNext());
	}
	db.close();
	}
	
	



	public void startact(View v)
	{
		Intent i=new Intent(Assignview.this, MainActivity.class);
		startActivity(i);
	}
	
	public void CopyDB(InputStream is,OutputStream os) throws Exception
	{
		byte[] buffer=new byte[100];
		int l;
		while((l=is.read(buffer))>0)
		{
			os.write(buffer,0,l);
		}
		is.close();
		os.close();
	}





	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try {
			db.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int z = 0;
		String s=(listAdapter.getItem(arg2));
		char x=s.charAt(0);
		if(s.charAt(1)>47 && s.charAt(1)<57 )
		{
			char y=s.charAt(1);
		 z=(((x-48)*10)+(y-48));
		}
		else
		{
		z=x-48;
		}
		listAdapter.remove(s);
		listAdapter.notifyDataSetChanged();
	db.deleteRecord(z);
	db.close();
	Toast.makeText(getApplicationContext(),"RECORD DELETED", Toast.LENGTH_SHORT).show();
	


	
	
		// TODO Auto-generated method stub
	}
	}


	

