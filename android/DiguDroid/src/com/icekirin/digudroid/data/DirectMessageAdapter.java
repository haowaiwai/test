package com.icekirin.digudroid.data;

import java.util.List;

import com.icekirin.digudroid.util.NetUtil;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DirectMessageAdapter extends BaseAdapter{

	private Context ctx;
	private LayoutInflater mInflater;
	private List<DirectMessageBean> msgBeanList;
	private String type;

	public DirectMessageAdapter(Context ctx, List<DirectMessageBean> msgBeanList){
		this.ctx = ctx;
		this.mInflater = LayoutInflater.from(ctx);
		this.msgBeanList = msgBeanList;

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgBeanList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return msgBeanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup rs = (ViewGroup) mInflater.inflate(R.layout.msg_list_item, null);
		TextView post_content = (TextView)rs.findViewById(R.id.post_content);
		TextView post_name = (TextView)rs.findViewById(R.id.post_name);
		TextView post_time = (TextView)rs.findViewById(R.id.post_time);
		TextView source = (TextView)rs.findViewById(R.id.source);
		ImageView pic = (ImageView)rs.findViewById(R.id.pic);
		ImageView userHead = (ImageView)rs.findViewById(R.id.userHead);
		DirectMessageBean temp = msgBeanList.get(position);
		post_content.setText(temp.getText());
		//U.dout("getScreen_name="+temp.getUserBean().getScreen_name());
		post_name.setText(temp.getSender_screen_name());
		String date = U.getDateStr(temp.getCreated_at());
		post_time.setText(date);
		//source.setText("通过"+Html.fromHtml(temp.getSource())); 
		String userHeadUrl = temp.getSender().getProfile_image_url();
		if(userHeadUrl!=null&&userHeadUrl.length()>2){
			try {
				NetUtil.downLoadUserHead(userHeadUrl);
				String userHeadPath = U.url2UserHeadPath(userHeadUrl);
				//加载本地图片(头像)
				NetUtil.loadLocalImage(userHead, userHeadPath, U.getCacheData().userHeadMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return rs;
	}

}
