package cn.icekirin.digudroid.data;

import java.util.ArrayList;

import cn.icekirin.digudroid.util.NetUtil;
import cn.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsAdapter extends BaseAdapter{
	
	private Context ctx;
	private LayoutInflater mInflater;
	private ArrayList<UserBean> flist;
	
	public FriendsAdapter(Context ctx, ArrayList<UserBean> list){
		
		this.ctx = ctx;
		this.mInflater = LayoutInflater.from(ctx);
		this.flist = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return flist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return flist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		UserBean user = flist.get(position);
		ViewGroup rs = (ViewGroup) mInflater.inflate(R.layout.friends_list_item, null);
		TextView name = (TextView)rs.findViewById(R.id.name);
		TextView screen_name = (TextView)rs.findViewById(R.id.screen_name);
		TextView intro = (TextView)rs.findViewById(R.id.intro);
		ImageView userHead = (ImageView)rs.findViewById(R.id.userHead);
		screen_name.setText(user.getScreen_name());
		name.setText(user.getName());
		String temp = user.getDescription();		
		intro.setText(Html.fromHtml(temp));
		String userHeadUrl = user.getProfile_image_url();
		try {
			NetUtil.downLoadUserHead(userHeadUrl);
			String userHeadPath = U.url2UserHeadPath(userHeadUrl);
			NetUtil.loadLocalImage(userHead, userHeadPath, U.getCacheData().userHeadMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String user_id = user.getId();
		rs.setTag(user_id);
		return rs;
	}

}
