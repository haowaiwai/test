package cn.icekirin.digudroid.data;

import java.util.ArrayList;
import java.util.HashMap;

import cn.icekirin.digudroid.util.DbPersist;

import android.graphics.Bitmap;


public class CacheData {
	
	//静态变量
	private static CacheData cache = null;
	public HashMap<String,Object> hmap;
	public HashMap<String, Bitmap> userHeadMap;
	public HashMap<String, Bitmap> imgMap;
	public DbPersist dbp;
	public String localPath;
	public MsgBean toDisplayBigPicMsgBean;
	
	public MsgBean toOperateMsgBean;//回复等操作的msgBean
	public DirectMessageBean toOperateDirectMsgBean;//回应等操作的DirectMessageBean
	
	public ArrayList<UserBean> Followers = new ArrayList<UserBean>();
	public ArrayList<UserBean> Friends = new ArrayList<UserBean>();
	
	/**
	 *私有构造器 
	 */		
	private CacheData() {		
		hmap = new HashMap<String,Object>();
		userHeadMap = new HashMap<String, Bitmap>();
		imgMap = new HashMap<String, Bitmap>();
		
	}	

	//	获取实例方法
	public static CacheData getInstance() {
		if (cache == null) {
            synchronized(CacheData.class){
                //再次检测，保证只创建一个对象
                if(cache == null){
                	cache = new CacheData();
                }
            }			
		}
		return cache;
	}
}
