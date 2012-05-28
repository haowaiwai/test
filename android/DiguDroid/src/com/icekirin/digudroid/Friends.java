package com.icekirin.digudroid;

import java.util.ArrayList;

import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.FriendsAdapter;
import com.icekirin.digudroid.data.UserBean;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Friends extends Activity{

	private ListView list;
	private TextView friends_tag;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private Context ctx;
    private ArrayList<UserBean> userBeanList;
    private String type;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ctx = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friends_list);
		list = (ListView)findViewById(R.id.friends_list);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				String user_id = v.getTag()+"";
				//U.dout("user_id="+user_id);
				Intent it = new Intent().setClass(ctx, UserInfo.class);
				it.putExtra("user_id", user_id);
				startActivity(it);
			}
			
		});
		friends_tag = (TextView)findViewById(R.id.friends_tag);
		type = getIntent().getExtras().get("type")+"";
		//U.dout("type="+type);
		getFriends();
	}
	
	private void getFriends(){
    	progressDialog=ProgressDialog.show(Friends.this, U.R("public_txt_plesse_wait"), U.R("loading_friends_net"), true);
    	new Thread(){
            @Override
			public void run(){
            	DiguApi api = new DiguApi();
            	
            	try {
            		
            		if(type.equals(F.FOLLOW_ME)){
            			//userBeanList = api.getFollowersList();
            			userBeanList = U.getFollowersWithCache();
            		}else{
            			//userBeanList = api.getFriendsList();
            			userBeanList = U.getFriendsWithCache();
            		}
            		
            		            		
				} catch (Exception e) {
					e.printStackTrace();
				}
            	handler.post(new Runnable() {
		            public void run() {
		            	list.setAdapter(new FriendsAdapter(ctx, userBeanList));
		            	if(type.equals(F.FOLLOW_ME)){
		            		//friends_tag.setText("所有跟随我的人("+userBeanList.size()+"人)");
		            		String str = getResources().getString(R.string.all_followers, userBeanList.size()+"");
		            		friends_tag.setText(str);
	            		}else{
	            			//friends_tag.setText("所有已跟随的人("+userBeanList.size()+"人)");
		            		String str = getResources().getString(R.string.all_friends, userBeanList.size()+"");
		            		friends_tag.setText(str);	
	            		}
		            }
		        });				
				progressDialog.dismiss();
            }
        }.start();
        
	}

}
