package com.icekirin.digudroid;

import java.util.ArrayList;

import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.DirectMessageBean;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.UserBean;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class DirectMessageEditor extends Activity { 
	private Context				ctx;
	private Spinner				mSpinner;
	private EditText			mContent;
	private Button				btn_submit;
	private Button				btn_return;
	private ProgressDialog		progressDialog;
	private Handler				handler			= new Handler();
	private boolean				sendflag		= false;
	private String				type			= "";
	private ArrayList<UserBean>	alist			= new ArrayList<UserBean>();
	private UserBean			selectUserBean	= new UserBean();
	private String action = "default";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.ctx = this;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.direct_message_editor);
		if(getIntent().getExtras()!=null){			
			action = getIntent().getExtras().getString("action");
			U.dout("action="+action);
		}
		
		mSpinner = (Spinner) findViewById(R.id.spinner1);
		mSpinner.setOnItemSelectedListener(spOSL);
		
		mContent = (EditText) findViewById(R.id.content);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_return = (Button) findViewById(R.id.btn_return);
		btn_submit.setOnClickListener(OCL);
		btn_return.setOnClickListener(OCL);
		
		getFollowerSpinner();
		//F.ACTION_REPLY_DIRECT_MSG;
		
	}

	private final OnClickListener	OCL	= new OnClickListener() {

											@Override
											public void onClick(View v) {
												switch (v.getId()) {
												case (R.id.btn_submit): {
													if(TextUtils.isEmpty(mContent.getText().toString().trim())){
														U.dpost(ctx, getResources().getString(R.string.do_not_null_message));
														break;
													}else{
														updateDirectMessage();
														break;	
													}
												}
												case (R.id.btn_return): {
													finish();
													break;
												}
												}
											}

										};

	private void updateDirectMessage() {
		progressDialog = ProgressDialog.show(ctx, getResources().getString(R.string.please_wait), getResources().getString(R.string.sending_direct_message), true);
		new Thread() {
			@Override
			public void run() {
				try {
					DiguApi api = new DiguApi();
					api.updateDirectMessage(mContent.getText().toString(), selectUserBean.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						U.dpost(ctx, getResources().getString(R.string.sending_suess));
						mContent.setText("");
					}
				});
				progressDialog.dismiss();
				 
			}
		}.start();

	}
	
	boolean spinneReadyFlag = false;
	
	public void getFollowerSpinner() {
		String[]  str = {getResources().getString(R.string.loading_followers)};
		ArrayAdapter<String> adapterInit = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, str);
		mSpinner.setAdapter(adapterInit);
		adapterInit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		new Thread() {
			@Override
			public void run() {
				try {
					DiguApi api = new DiguApi();
					alist = U.getFollowersWithCache();
					selectUserBean = alist.get(0);
					spinneReadyFlag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {					
						ArrayAdapter<String> adapter = U.getSpinnerNameAdapter(ctx, alist);
						mSpinner.setAdapter(adapter);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						mSpinner.setPrompt(getResources().getString(R.string.select_your_follower));
						//如果是点回应进入，直接定位该好友
						if(action!=null&&action.equals(F.ACTION_REPLY_DIRECT_MSG)){
							DirectMessageBean dmsg = U.getCacheData().toOperateDirectMsgBean;
							for(int i=0;i<alist.size();i++){
								if(dmsg.getSender_id().equals(alist.get(i).getId())){
									mSpinner.setSelection(i);
								}
							}
						}
						//U.dpost(ctx, "获取好友列表完毕");
					}
				});
			}
		}.start();

	}

	private final OnItemSelectedListener	spOSL	= new OnItemSelectedListener() {
														public void onItemSelected(AdapterView<?> parent, View view,
																int position, long id) {
															if(spinneReadyFlag){
																selectUserBean = alist.get(position);
															}
															
														}

														public void onNothingSelected(AdapterView<?> parent) {

														}
													};

}
