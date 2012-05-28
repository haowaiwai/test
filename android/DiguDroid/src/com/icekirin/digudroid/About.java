package com.icekirin.digudroid;

import com.admob.android.ads.AdView;

import com.icekirin.digudroid.R;
import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.FriendsAdapter;
import com.icekirin.digudroid.misc.BackInterpolator;
import com.icekirin.digudroid.misc.EasingType.Type;
import com.icekirin.digudroid.util.U;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public class About extends Activity{
	
    private LinearLayout logoRow, authorRow;
    //private Animation animLeft, animRight;
    private Interpolator interpolator;
    private AdView adview;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private 	Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ctx = this;
        adview = (AdView)findViewById(R.id.ad);
        logoRow = (LinearLayout)findViewById(R.id.logo_row);
        authorRow = (LinearLayout)findViewById(R.id.author_row);
        show();
    }
    
    boolean flag = false;
    private void show(){
    	progressDialog=ProgressDialog.show(About.this, "请稍等...", "正在读取...", true);
    	new Thread(){
            @Override
			public void run(){
            	
            	            	
            	try {
                   		
            		            		
				} catch (Exception e) {
					e.printStackTrace();
				}
            	handler.post(new Runnable() {
		            public void run() {
		            	//U.dpost(ctx,"adview.isShown="+adview.isShown());
		            }
		        });				
				progressDialog.dismiss();
            }
        }.start(); 
        
	}
    
    private void show2(){
//        animLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        //animRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        //interpolator = new BackInterpolator(Type.OUT, 0);
//        animLeft.setInterpolator(interpolator);
//        animRight.setInterpolator(interpolator); 
        
        logoRow = (LinearLayout)findViewById(R.id.logo_row);
        logoRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CHINAANDROIDDEV_URL));
                //startActivity(intent);
            }
        });
        authorRow = (LinearLayout)findViewById(R.id.author_row);
        authorRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ICEKIRIN_BASE_URL));
//                startActivity(intent);
            }
        });
        //authorRow.startAnimation(animLeft);

    }
    
}
