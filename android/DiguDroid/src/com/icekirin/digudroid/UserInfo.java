package com.icekirin.digudroid;

import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.UserBean;
import com.icekirin.digudroid.util.NetUtil;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfo extends Activity{

	private String user_id;
	private UserBean userBean;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private ImageView userHead;
    private TextView screen_name;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_info);		
		user_id = getIntent().getExtras().get("user_id")+"";
		U.dout("userinfo ="+user_id);
		userHead = (ImageView)findViewById(R.id.userHead);
		screen_name = (TextView)findViewById(R.id.screen_name);
		getUserBean();
	}
	
	private void getUserBean(){
		
		progressDialog=ProgressDialog.show(UserInfo.this, U.R("public_txt_plesse_wait"), "读取网络好友详细信息...", true);
    	new Thread(){
            @Override
			public void run(){
            	DiguApi api = new DiguApi();            	
            	try {
            		userBean = api.getUserById(user_id);
            		            		
				} catch (Exception e) {
					e.printStackTrace();
				}
            	handler.post(new Runnable() { 
		            public void run() {
		            	screen_name.setText(userBean.getScreen_name());
		            	
		            	String userHeadUrl = userBean.getProfile_image_url();
		            	try {
		            		//获取大头像图
		            		String bigImgUrl = NetUtil.getBigHeadUrl(userHeadUrl);
		            		//U.dout("bigImgUrl="+bigImgUrl);
							NetUtil.downLoadUserHead(bigImgUrl);
							String userHeadPath = U.url2UserHeadPath(bigImgUrl);
							//U.dout("userHeadPath="+userHeadPath);
							NetUtil.loadLocalImage(userHead, userHeadPath, U.getCacheData().userHeadMap);

						} catch (Exception e) {

							e.printStackTrace();
						}
		            }
		        });				
				progressDialog.dismiss();
            }
        }.start();
	}

}
