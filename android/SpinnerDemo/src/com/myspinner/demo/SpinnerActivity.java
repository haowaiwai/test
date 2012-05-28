package com.myspinner.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerActivity extends Activity {
	
	int[] hobbyStrIds = {R.string.basketball, 
			R.string.football, R.string.volleyball};
	
	int[] hobbyImgIds = {R.drawable.basketball, 
			R.drawable.football, R.drawable.volleyball};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_layout);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter();
        spinner.setAdapter(adapter);
        
        //选中监听器
        OnItemSelectedListener listener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textView = (TextView) findViewById(R.id.textView);
				
				LinearLayout linearLayout = (LinearLayout) view;
				
				TextView spinnerTextView = (TextView) linearLayout.getChildAt(1);
				StringBuilder strBuilder = new StringBuilder();
				
				strBuilder.append(getResources().getText(R.string.hobby));
				strBuilder.append(" : ");
				strBuilder.append(spinnerTextView.getText());
				
				textView.setText(strBuilder);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		};
		
		spinner.setOnItemSelectedListener(listener);
    }
    
    class SpinnerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if(hobbyStrIds.length <= hobbyImgIds.length) {
				return hobbyStrIds.length;
			}
			return hobbyImgIds.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout linearLayout = new LinearLayout(SpinnerActivity.this);
			linearLayout.setOrientation(LinearLayout.HORIZONTAL);
			linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
			
			//设置小图标
			ImageView imageView = new ImageView(SpinnerActivity.this);
			imageView.setImageDrawable(getResources()
					.getDrawable(hobbyImgIds[position]));
			linearLayout.addView(imageView);
			
			//设置内容
			TextView textView = new TextView(SpinnerActivity.this);
			textView.setText("  " + getResources().getText(hobbyStrIds[position]));
			textView.setTextSize(24);
			textView.setTextColor(R.color.black);
			linearLayout.addView(textView);
			
			return linearLayout;
		}
    }
}