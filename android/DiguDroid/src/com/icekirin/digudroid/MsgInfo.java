package com.icekirin.digudroid;

import com.icekirin.digudroid.data.MsgBean;
import com.icekirin.digudroid.data.UserBean;
import com.icekirin.digudroid.util.NetUtil;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MsgInfo extends Activity {

	private String			user_id;
	private UserBean		userBean;
	private MsgBean			msgBean;
	private ProgressDialog	progressDialog;
	private Handler			handler	= new Handler();
	private ImageView 		userHead;
	private ImageView		img;
	private TextView		content;
	private TextView 		post_name;
	private TextView 		post_time;
	private TextView 		source;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.msg_info);
		content = (TextView) findViewById(R.id.content);
		post_name = (TextView) findViewById(R.id.post_name);
		post_time = (TextView) findViewById(R.id.post_time);
		source = (TextView) findViewById(R.id.source);
		img = (ImageView) findViewById(R.id.img);
		userHead = (ImageView) findViewById(R.id.userHead);
		
		getMsgBean();
	}

	private void getMsgBean() {

		progressDialog = ProgressDialog.show(MsgInfo.this, U.R("public_txt_plesse_wait"), U.R("public_txt_loadpic"), true);
		new Thread() {
			@Override
			public void run() {
				//SystemClock.sleep(3000);
				try {
					msgBean = U.getCacheData().toDisplayBigPicMsgBean;
					String imgUrl = msgBean.getPicPath();
					String bigImgUrl = NetUtil.getBigImgUrl(imgUrl);
					NetUtil.downLoadImg(bigImgUrl);
					String userHeadUrl = msgBean.getUserBean().getProfile_image_url();
					//如果需要 下载中头像
					userHeadUrl = NetUtil.getMiddleHeadUrl(userHeadUrl);
					NetUtil.downLoadUserHead(userHeadUrl);

				} catch (Exception e) { 
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						content.setText(msgBean.getText());
						post_name.setText(msgBean.getUserBean().getScreen_name());
						String date = U.getDateStr(msgBean.getCreated_at());
						post_time.setText(date);
						source.setText(U.R("public_txt_source")+Html.fromHtml(msgBean.getSource()));						
						String userHeadUrl = msgBean.getUserBean().getProfile_image_url();						
						try {
							String userHeadPath = U.url2UserHeadPath(userHeadUrl);
							//加载本地图片(头像)
							NetUtil.loadLocalImage(userHead, userHeadPath, U.getCacheData().userHeadMap);
						} catch (Exception e) {
							e.printStackTrace();
						} 
						
						String imgUrl = msgBean.getPicPath();
						String bigImgUrl = NetUtil.getBigImgUrl(imgUrl);
						String imgPath = U.url2ImgPath(bigImgUrl);
						//U.dout("imgUrl="+imgUrl);
						if(!TextUtils.isEmpty(imgUrl)){
							try {
								//获取大图
								NetUtil.loadLocalImage(img, imgPath, U.getCacheData().imgMap);								
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						}
						
						
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}

}
