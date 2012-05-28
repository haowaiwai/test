package com.icekirin.digudroid;

import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.MsgBean;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast; 

public class WriteMsgDialog extends Activity {

	private Context			ctx;
	private EditText		mContent;
	private Button			btn_submit;
	private Button			btn_return;
	private ProgressDialog	progressDialog;
	private Handler			handler		= new Handler();
	private boolean			sendflag	= false;
	private String			type		= "";
	private MsgBean 		op_msgBean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.ctx = this;
		setContentView(R.layout.msg_write);

		mContent = (EditText) findViewById(R.id.content);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_return = (Button) findViewById(R.id.btn_return);
		
		type = getIntent().getExtras().get("type") + "";
		op_msgBean = U.getCacheData().toOperateMsgBean;
		if (type.equals(F.OP_TYPE_REPLY)) {
			setTitle("@" + op_msgBean.getUserBean().getScreen_name());
		}else if(type.equals(F.OP_TYPE_ZZ)){
			setTitle("转载: @" + op_msgBean.getUserBean().getScreen_name());
			mContent.setText(op_msgBean.getText());
		}
		
		btn_submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//隐藏软键盘
				InputMethodManager inputManager = (InputMethodManager) ctx
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(WriteMsgDialog.this.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				if (type.equals(F.OP_TYPE_REPLY)){
					reply();
				}else if(type.equals(F.OP_TYPE_WRITE)){
					updateStatus();
				}else if(type.equals(F.OP_TYPE_ZZ)){					
					zz();
				}
			}
		});
		btn_return.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		

	}

	public void updateStatus() {
		progressDialog = ProgressDialog.show(this, U.R("public_txt_plesse_wait"), U.R("public_txt_doing"), true);
		new Thread() {
			@Override
			public void run() {
				String status = mContent.getText() + "";
				DiguApi api = new DiguApi();
				try {
					sendflag = api.updateStatus(status, -1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						if (sendflag) {
							Toast.makeText(ctx, U.R("send_ok"), Toast.LENGTH_SHORT).show();
							SystemClock.sleep(1000);
							finish();
						} else {
							Toast.makeText(ctx, U.R("send_fail"), Toast.LENGTH_SHORT).show();
						}
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}

	public void reply() {
		progressDialog = ProgressDialog.show(this, U.R("public_txt_plesse_wait"), U.R("public_txt_doing"), true);
		new Thread() {
			@Override
			public void run() {
				
				String status = mContent.getText() + "";
				DiguApi api = new DiguApi();
				try {
					U.dout(op_msgBean.getId());
					sendflag = api.updateStatus(status, Long.parseLong(op_msgBean.getId()));
					//如果发送消息成功，准备msgBeanList刷新
					if (sendflag) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						if (sendflag) {
							Toast.makeText(ctx, U.R("reply_ok"), Toast.LENGTH_SHORT).show();
							SystemClock.sleep(1000);
							finish();
						} else {
							Toast.makeText(ctx, U.R("reply_fail"), Toast.LENGTH_SHORT).show();
						}
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}
	
	public void zz() {
		progressDialog = ProgressDialog.show(this, U.R("public_txt_plesse_wait"), U.R("public_txt_doing"), true);
		new Thread() {
			@Override
			public void run() {
				String status = getTitle().toString() +" " + mContent.getText() + "";				
				DiguApi api = new DiguApi();
				try {
					U.dout(op_msgBean.getId());
					sendflag = api.updateStatus(status, -1);
					//如果发送消息成功，准备msgBeanList刷新
					if (sendflag) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						if (sendflag) {
							Toast.makeText(ctx, U.R("zz_ok"), Toast.LENGTH_SHORT).show();
							SystemClock.sleep(1000);
							finish();
						} else {
							Toast.makeText(ctx, U.R("zz_fail"), Toast.LENGTH_SHORT).show();
						}
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}

}
