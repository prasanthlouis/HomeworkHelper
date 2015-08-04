package com.psiuol21.assignmentchecklist;

import android.animation.Animator;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.psiuol21.homeworkhelper.R;

public class MainActivity extends AppCompatActivity {
	EditText title,date,subject,des;
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    View myView,myView1;
    int cx1,cy1,cx2,cy2,finalRadius1,finalRadius2;
    Animator anim,anim1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Explode());
			getWindow().setExitTransition(new Explode());

		}
        myView=findViewById(R.id.Addassign);
        myView1=findViewById(R.id.Viewassign);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/reg.ttf");
		title=(EditText)findViewById(R.id.name);
		date=(EditText)findViewById(R.id.date);
		subject=(EditText)findViewById(R.id.subject);
		des = (EditText)findViewById(R.id.description);
        title.setTypeface(font);
        date.setTypeface(font);
        subject.setTypeface(font);
        des.setTypeface(font);
        checker();
        checker2();
		
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

                            cx1 = finalHeight / 2;
                            cy1 = finalWidth / 2;


                            finalRadius1 = Math.max(finalHeight, finalWidth);
                            anim = ViewAnimationUtils.createCircularReveal(myView, cx1, cy1, 0, finalRadius1);
                            myView.setVisibility(View.VISIBLE);
                            anim.setDuration(2000);

                            anim.start();

                        }
                        return true;
                    }
                });
    }
    public void checker2()
    {

        myView1.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
                            int finalHeight = myView1.getMeasuredHeight();
                            int finalWidth = myView1.getMeasuredWidth();
                            // Do your work here
                            myView1.getViewTreeObserver().removeOnPreDrawListener(this);

                            cx2 = finalHeight / 2;
                            cy2 = finalWidth / 2;


                            finalRadius2 = Math.max(finalHeight, finalWidth);
                            anim1 = ViewAnimationUtils.createCircularReveal(myView1, cx2, cy2, 0, finalRadius2);
                            myView1.setVisibility(View.VISIBLE);
                            anim1.setDuration(2000);

                            anim1.start();

                        }
                        return true;
                    }
                });



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
        SuperToast.create(this, "RECORD ADDED", SuperToast.Duration.SHORT, Style.getStyle(Style.RED, SuperToast.Animations.FLYIN)).show();
		//Toast.makeText(getApplicationContext(), "RECORD ADDED", Toast.LENGTH_SHORT).show();
		
	}
	
	public void viewassign(View v)
	{
	//	Intent i=new Intent(this,Assignview.class);
      //  startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            myView.setVisibility(View.INVISIBLE);
            myView1.setVisibility(View.INVISIBLE);
        }
finish();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
