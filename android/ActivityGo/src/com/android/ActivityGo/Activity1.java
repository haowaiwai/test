package com.android.ActivityGo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

public class Activity1 extends Activity {
	EditText first, second;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		first = (EditText) findViewById(R.id.first);
		second = (EditText) findViewById(R.id.second);

		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sendCalculate();
			}
		});
	}

	public void sendCalculate() {
		Intent i = new Intent(this, Activity2.class);

		Bundle b = new Bundle();

		b.putString("first", first.getText().toString());
		b.putString("second", second.getText().toString());

		i.putExtras(b);

		startActivityForResult(i, 10);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			Bundle b = data.getExtras();

			String string = b.getString("CALCULATION");

			updateText(string);
		}
	}

	public void updateText(String s) {
		TextView t = (TextView) findViewById(R.id.text);
		t.setText(s);
	}
}