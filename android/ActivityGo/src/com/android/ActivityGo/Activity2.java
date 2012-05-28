package com.android.ActivityGo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Activity2 extends Activity {
    String value;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
        
        Intent i = this.getIntent();
        
        Bundle b = i.getExtras();
        
        String v1 = b.getString("first");
        String v2 = b.getString("second");
        
        value = v1 + v2;
        
        findViewById(R.id.reply).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				replyCalculation();
			}
        });
        
    }
    
    
    public void replyCalculation(){
    	Intent i = new Intent();
    	
    	Bundle b = new Bundle();
    	b.putString("CALCULATION", value);
    	
    	i.putExtras(b);
    	
    	this.setResult(RESULT_OK, i);
    	this.finish();
    	
    }
}