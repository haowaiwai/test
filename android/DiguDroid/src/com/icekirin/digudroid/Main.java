package com.icekirin.digudroid;

import com.icekirin.digudroid.data.LoginUser;
import com.icekirin.digudroid.misc.BounceInterpolator;
import com.icekirin.digudroid.misc.EasingType.Type;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Main extends Activity {

    private Animation animLeft, animRight;
    private Interpolator interpolator;
    private LinearLayout main_login, main_menu;
    private LinearLayout twitterRow, messageRow, bookmarkRow, aboutRow, friendRow, exitRow;
    private LoginUser user;
    private EditText usernameField, passwordField;
    private ImageButton loginButton;
    private Handler handler = new Handler();
    private ProgressDialog progressDialog = null;
    private Context ctx;
    private LayoutInflater mInflater; 
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        ctx = this;
        U.setCtxForGetResString(ctx);        
        main_login = (LinearLayout)findViewById(R.id.main_login);
        main_menu = (LinearLayout)findViewById(R.id.main_menu);

		user = new LoginUser(ctx);
        usernameField = (EditText)findViewById(R.id.username_field);
        passwordField = (EditText)findViewById(R.id.password_field);
        loginButton = (ImageButton)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(!U.hasSdCard()){
            		U.dpost(ctx, U.R("login_msg_no_sd"));//没有sd卡
            		return;
            	}else{ 
            		
            	}
                //U.getCacheData().localPath = ctx.getFilesDir().getPath();
            	U.getCacheData().localPath = "/sdcard/Digu";
            	U.dout("localPath="+U.getCacheData().localPath);
                U.createFolder();
                user.name = usernameField.getText().toString();
                user.password = passwordField.getText().toString();
                if (user.name.length() == 0 || user.password.length() == 0) { 
                    new AlertDialog.Builder(Main.this)
                        .setMessage(U.R("login_txt_not_null"))
                        .setPositiveButton("Okay", null)
                        .show();  
                } else { 
                    doLogin();
                }
            }
        });
        if (user.name != null && user.password != null) {
        	 usernameField.setText(user.name);
        	 passwordField.setText(user.password); 
             //doLogin();
        }
    }
    
    private void doLogin() {
        progressDialog = ProgressDialog.show(Main.this, U.R("public_txt_plesse_wait"), U.R("login_txt_logining_digu"), true); 
        new Thread() {
            @Override
			public void run() {
                try {                	
                    if (user.verify()) {
                    	handler.post(new Runnable() {
                            public void run() {
                            	showMenu();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(Main.this)
                                .setMessage(U.R("login_txt_login_fail"))
                                .setPositiveButton("Ok", null)
                                .show();  
                            }
                        });
                    }
                } catch (Exception e) {
                }
                progressDialog.dismiss();
            }
        }.start();
    }
    
     public void showMenu(){
    	 main_login.setVisibility(View.GONE);
    	 main_menu.setVisibility(View.VISIBLE);
         animLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
         animRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
         interpolator = new BounceInterpolator(Type.IN);
         animLeft.setInterpolator(interpolator);
         animRight.setInterpolator(interpolator);
         
         twitterRow = (LinearLayout)findViewById(R.id.twitter_row);
         twitterRow.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Intent i = new Intent(Main.this, TwitterPage.class);
                 startActivity(i);
             }
         });
         
         messageRow = (LinearLayout)findViewById(R.id.message_row);
         messageRow.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Intent i = new Intent(Main.this, DirectMessageTabActivity.class);
                 i.putExtra("action", "default");
                 startActivity(i);
             }
         });
                  
         aboutRow = (LinearLayout)findViewById(R.id.about_row);
         aboutRow.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Intent i = new Intent(Main.this, About.class);            	 
                 startActivity(i);
             }
         });
         
         friendRow = (LinearLayout)findViewById(R.id.friend_row);
         friendRow.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {            	 
                 Intent i = new Intent(Main.this, FriendsTabActivity.class);
                 startActivity(i);
             }
         });
         
         exitRow = (LinearLayout)findViewById(R.id.exit_row);
         exitRow.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 finish();
                 android.os.Process.killProcess(android.os.Process.myPid());
             }
         });
         
         
         twitterRow.startAnimation(animRight);
         messageRow.startAnimation(animRight);
         aboutRow.startAnimation(animRight);
         friendRow.startAnimation(animRight);        
         exitRow.startAnimation(animRight); 
     }
}
