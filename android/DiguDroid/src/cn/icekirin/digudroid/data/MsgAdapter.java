package cn.icekirin.digudroid.data;

import java.util.List;

import cn.icekirin.digudroid.util.NetUtil;
import cn.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter{

	private Context ctx;
	private LayoutInflater mInflater;
	private List<MsgBean> msgBeanList;
	private String type;
	public MsgAdapter(Context ctx, List<MsgBean> msgBeanList, String type){
		this.ctx = ctx;
		this.mInflater = LayoutInflater.from(ctx);
		this.msgBeanList = msgBeanList;
		this.type = type;
	}
	
	public MsgAdapter(Context ctx){
		this.ctx = ctx;
		this.mInflater = LayoutInflater.from(ctx);
		//this.msgBeanList = msgBeanList;
	} 
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgBeanList.size();
		//return 5;
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
		MsgBean temp = msgBeanList.get(position);
		//存入msgBean供点击用
		rs.setTag(temp);
		post_content.setText(temp.getText());
		//U.dout("getScreen_name="+temp.getUserBean().getScreen_name());
		post_name.setText(temp.getUserBean().getScreen_name());
		String date = U.getDateStr(temp.getCreated_at());
		post_time.setText(date);
		source.setText("通过"+Html.fromHtml(temp.getSource())); 
		 
		String imgUrl = temp.getPicPath();
		if((!TextUtils.isEmpty(imgUrl))&&(imgUrl.length()>2)){
			
			try {
				pic.setVisibility(View.VISIBLE);
				if(type.equals(F.PRIVATE_MSG)){//如果是个人信息 下载图片保存图片到本地
					NetUtil.downLoadImg(imgUrl);
					String imgPath = U.url2ImgPath(imgUrl);
					NetUtil.loadLocalImage(pic, imgPath, U.getCacheData().imgMap);
				}else{//如果是广场信息,不保存到本地
					NetUtil.loadNetPicUseCache(pic, imgUrl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String userHeadUrl = temp.getUserBean().getProfile_image_url();
		if(userHeadUrl!=null&&userHeadUrl.length()>2){
			
			try {
				if(type.equals(F.PRIVATE_MSG)){//如果是个人信息 下载头像
					
					NetUtil.downLoadUserHead(userHeadUrl);
					String userHeadPath = U.url2UserHeadPath(userHeadUrl);
					//加载本地图片(头像)
					NetUtil.loadLocalImage(userHead, userHeadPath, U.getCacheData().userHeadMap);					
				}else{//如果是广场信息,不保存到本地
					NetUtil.loadNetPicUseCache(userHead, userHeadUrl);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return rs;
	}

}
