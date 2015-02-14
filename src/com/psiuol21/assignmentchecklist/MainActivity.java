package com.psiuol21.assignmentchecklist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.psiuol21.homeworkhelper.R;

public class MainActivity extends Activity {
	EditText title,date,subject,des;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		title=(EditText)findViewById(R.id.name);
		date=(EditText)findViewById(R.id.date);
		subject=(EditText)findViewById(R.id.subject);
		des=(EditText)findViewById(R.id.description);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/reg.otf");
		title.setTypeface(font);
		date.setTypeface(font);
		subject.setTypeface(font);
		des.setTypeface(font);
		
	}
	public void addassign(View v)
	{
	
		DBAdapter db=new DBAdapter(this);
		
		try {
			db.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.insertRecord(title.getText().toString(), date.getText().toString(), subject.getText().toString(), des.getText().toString());
		db.close();
		title.setText("");
		date.setText("");
		subject.setText("");
		des.setText("");
		Toast.makeText(getApplicationContext(), "RECORD ADDED", Toast.LENGTH_SHORT).show();
		
	}
	
	public void viewassign(View v)
	{
		Intent i=new Intent(this,Assignview.class);
		startActivity(i);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
