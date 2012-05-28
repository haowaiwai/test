package com.android.tutor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class LayoutInflaterDemo extends Activity implements 
OnClickListener {
    
	private Button button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		
		showCustomDialog();
	}
	
	public void showCustomDialog()
	{
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		Context mContext = LayoutInflaterDemo.this;
		
		//下面俩种方法都可以
		////LayoutInflater inflater = getLayoutInflater();
		LayoutInflater inflater = (LayoutInflater) 
mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.custom_dialog,null);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Hello, Welcome to Mr Wei's blog!");
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.icon);
		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}
}