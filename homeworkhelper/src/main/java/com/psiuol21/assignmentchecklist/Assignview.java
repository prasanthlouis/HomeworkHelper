package com.psiuol21.assignmentchecklist;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.psiuol21.homeworkhelper.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Assignview extends AppCompatActivity implements OnItemClickListener {

ListView listView;
DBAdapter db;
ArrayAdapter<String> listAdapter;
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    int  cx,cy,finalRadius;
    Animator anim;
    View myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view2);

        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        myView=findViewById(R.id.button1);
        checker();


        // TODO Auto-generated method stub

        listView = (ListView) findViewById(R.id.listView1);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        TextView tf = (TextView) findViewById(R.id.textView1);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/reg.ttf");
        tf.setTypeface(font);
        letsdoit();

    }

    public void checker() {
        myView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
                            int finalHeight = myView.getMeasuredHeight();
                            int finalWidth = myView.getMeasuredWidth();
                            // Do your work here
                            myView.getViewTreeObserver().removeOnPreDrawListener(this);

                            cx = finalHeight / 2;
                            cy = finalWidth / 2;


                            finalRadius = Math.max(finalHeight, finalWidth);
                            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                            myView.setVisibility(View.VISIBLE);
                            anim.setDuration(2000);

                            anim.start();

                        }
                        return true;
                    }
                });



    }





	public void letsdoit()
    {
	try
	{
		String path="/data/data"+getPackageName()+"databases/helperdb";
		File f=new File(path);
		if(!f.exists())
		{
			CopyDB(getBaseContext().getAssets().open("myDB"),new FileOutputStream(path));
        }

    } catch (Exception e) {
    }
        db = new DBAdapter(this);


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
			listAdapter.add(c.getString(0)+")"+" " + c.getString(1)+"\n"+c.getString(2)+"\t"+c.getString(3)+"\n"+c.getString(4));
			
		}while(c.moveToNext());
	}
	db.close();
	}


    public void startact(View v)
	{
		Intent i=new Intent(Assignview.this, MainActivity.class);
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            myView.setVisibility(View.INVISIBLE);
            anim.end();
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        else
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
    protected void onRestart() {
        super.onRestart();
        listAdapter.clear();
        checker();

        letsdoit();


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
		if(s.charAt(1)>47 && s.charAt(1)<=57 )
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
        SuperToast.create(this,"RECORD DELETED",SuperToast.Duration.SHORT,Style.getStyle(Style.RED, SuperToast.Animations.FLYIN)).show();
	//Toast.makeText(getApplicationContext(),"RECORD DELETED", Toast.LENGTH_SHORT).show();



	
	
		// TODO Auto-generated method stub
	}
	}


	

