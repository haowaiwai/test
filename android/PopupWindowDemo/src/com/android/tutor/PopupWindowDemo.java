package com.android.tutor;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class PopupWindowDemo extends Activity{

	private Handler mHandler = new Handler(){
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				showPopupWindow();
				break;
			}
		};
	};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //create the timer 
        Timer timer = new Timer();
        timer.schedule(new initPopupWindow(), 100);
    }

	
    private class initPopupWindow extends TimerTask{
		@Override
		public void run() {
			
			Message message = new Message();
			message.what = 1;
			mHandler.sendMessage(message);
			
		}   	
    }
    
    
	public void showPopupWindow() {
		Context mContext = PopupWindowDemo.this;
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View music_popunwindwow = mLayoutInflater.inflate(
				R.layout.music_popwindow, null);
		PopupWindow mPopupWindow = new PopupWindow(music_popunwindwow,
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		mPopupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);
	}

}