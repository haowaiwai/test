package com.icekirin.digudroid.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.icekirin.digudroid.data.CacheData;
import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.UserBean;

public class U {
	
	public static boolean debugFlag = false;
	public static String nLine = System.getProperty( "line.separator" );
	public static int WC = LayoutParams.WRAP_CONTENT;
	public static int FP = LayoutParams.FILL_PARENT;
	public static LinearLayout.LayoutParams paramWC = new LinearLayout.LayoutParams(WC, WC);
	public static LinearLayout.LayoutParams paramFP = new LinearLayout.LayoutParams(FP, FP);
	public static Context ctx_for_getResString;
	
	/**
	 * 打印log
	 * @param obj
	 */
	public static void dout(Object obj){ 
		if(debugFlag) {
			Log.d("[dout]","obj>>>>>>>>>>>>>"+obj.getClass().getName()+">>"+obj.toString());
		}
	}
	public static void dout(int obj){
		if(debugFlag) {
			Log.d("[dout]","int>>>>>>>>>>>>>"+obj); 
		}
	}
	public  static void dout(String str){
		if(debugFlag) {
			Log.d("[dout]","str>>>>>>>>>>>>>"+str);
		}
	}
	public  static void dout(String str,String str2){
		if(debugFlag) {
			Log.d("[dout]","str>>>>>>>>>>>>>"+str+" "+str2);
		}
	}
	public  static void dout(String[] str){
		if(debugFlag){
		for(int i=0; i<str.length; i++) {
			Log.d("[dout]","str["+i+"]>>>>>>>>>>>>>"+str[i]);
		}
		}
	}
	
	public static CacheData getCacheData(){
		return CacheData.getInstance();
	}

	public static String getDisplayDateStr(String dateStr){
		//dateStr = "Thu Apr 30 01:33:41 +0000 2009";
		Date dd = new Date(dateStr);
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"); 
		String str = "";
		try{
			str = myFmt.format(dd);			
		}catch(Exception e){
			e.printStackTrace();
		}
		dout(str);
		return str;
	}
	
	public static String getDateStr(String dateStr){
		Date date = str2Date(dateStr);
		return getHowLongStr(date);		
	}
	
	public static String getDateStr(Long l){
		Date date = long2Date(l);
		return getHowLongStr(date);		
	}
	
	public static Date long2Date(Long l){
		Date d = new Date();
		d.setTime(l);
		return d;
	}
	
	//输入"Thu Apr 30 01:33:41 +0000 2009" 返回Date
	public static Date str2Date(String dateStr){
		return new Date(dateStr);
	}
	
	//传入一个Date，判断是多久前，返回示例"4小时前"
	public static String getHowLongStr(Date date){
		String rs = "刚刚";
		Long i = date.getTime();
		Date now = new Date();
		Long j = now.getTime();
		//dout(j - i);
		long day = 1000 * 60 * 60 * 24;
		long hour = 1000 * 60 * 60;
		long min = 1000 * 60;
		long sec = 1000;
		if (((j-i)/day)>0)
			rs = ((j-i)/day)+"天前";
		else if (((j-i)/hour)>0)
			rs = ((j-i)/hour)+"小时前";
		else if (((j-i)/min)>0)
			rs = ((j-i)/min)+"分钟前";
		else if (((j-i)/sec)>0)
			rs = ((j-i)/sec)+"秒前";
		return rs;
	}
	
	public static Long dateStr2Long(String dateStr){
		if(TextUtils.isEmpty(dateStr))
			return new Date().getTime();
		return  new Date(dateStr).getTime();
	}
	
	
	
	public static String downFileToLocalPath(String url){
		String localPath = "";
		
		return localPath;
	}
	
	/**将Bitmap转成文件放在程序文件目录下*/
	public static void Bitmap2File(Bitmap bitmap, String fileName, Context ctx) {
		
		try {
			FileOutputStream fos = ctx.openFileOutput(fileName, android.content.Context.MODE_WORLD_READABLE);
			bitmap.compress(CompressFormat.JPEG, 80, fos);			
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	/**
	 * 把url转成本地下载好的头像地址
	 * @param url
	 * @return
	 */
	public static String url2UserHeadPath(String url){
		String path = "";
		String [] str = url.split("/");
		path = F.USER_HEAD_PATH+"/"+str[str.length-1];
		return path;
	}
	
	/**
	 * 把本地下载好的头像地址转成url
	 * @param url
	 * @return
	 */
	public static String userHeadPath2Url(String userHeadPath){
		
		String [] str = userHeadPath.split("/");	
		String name = str[str.length-1];	//SIGN10120986_24x24.jpg
		String id = name.substring(4, 12);  //10120986
		String [] temp = name.split("_");
		String rs = "http://pic.minicloud.com.cn:80/file/"+id.substring(0, 2)+"/"+id.substring(2, 4)+"/"
		+id.substring(4, 6)+"/"+id.substring(6, 8)+"/default/SIGN"+id+"_"+temp[1];	
		if(id.equals("10120986"))
			U.dout(rs);
		return rs;
	}
	
	public static String getUserHeadUrlById(String id){
		String rs = "http://pic.minicloud.com.cn:80/file/"+id.substring(0, 2)+"/"+id.substring(2, 4)+"/"
			+id.substring(4, 6)+"/"+id.substring(6, 8)+"/default/SIGN"+id+"_24x24.jpg";
		return rs;
	}
	
	/**
	 * 把url转成本地下载好的图片地址
	 * @param url
	 * @return
	 */
	public static String url2ImgPath(String url){
		String path = "";
		String [] str = url.split("/");
		path = F.IMG_PATH+"/"+str[str.length-1];
		return path;
	}
	
	/**
	 * 对picPath的处理，去掉外框[""]
	 * @param str
	 * @return
	 */
	public static String chgPicPath(String str){
		// ["http://pic.minicloud.com.cn:80/file/11/29/69/27/200911/530e2683f13ce0ad5c4e8f2dba815fae_100x75.jpg"]
		String rs = "";		
		if (str.length()>2){
			rs = str.substring(2, str.length()-2);
		}
		return rs;
	}
	
	public static String appendSql(String [] columns , String sql){
		
		StringBuffer sb = new StringBuffer();		
		int l = columns.length;
		for(int i=0;i<l;i++){
			String s = columns [i];
			if(i==l-1){
				sb.append(s);
			}else{
				sb.append(s+",");
			}
		}
		sql = sql.replace("*", sb.toString());
		return sql;
	}	
	
	public static void dpost(Context ctx, String str){
    	if(str==null)
    		str = "null point";
    	Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
    }
	
	public static void createFolder(){
		String path = F.USER_HEAD_PATH;
		File PATH = new File(path);
		if (!PATH.exists()) {
			U.dout("mkdir USER_HEAD_PATH, " + path);
			PATH.mkdirs();
		}
		String path2 = F.IMG_PATH;
		File PATH2 = new File(path2);
		if (!PATH2.exists()) {
			U.dout("mkdir IMG_PATH, " + path2);
			PATH2.mkdirs();
		}
		String path3 = F.PUB_USER_HEAD_PATH;
		File PATH3 = new File(path3);
		if (!PATH3.exists()) {
			U.dout("mkdir PUB_USER_HEAD_PATH, " + path3);
			PATH3.mkdirs();
		}
		

	}
	
	public static ArrayAdapter<String> getSpinnerNameAdapter(Context ctx, ArrayList<UserBean> flist){
		String [] mStrings = new String [flist.size()];
		for(int i=0;i<flist.size();i++){
			mStrings[i] = flist.get(i).getScreen_name();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, mStrings);
		return adapter;
	}
	
	
	public static void setCtxForGetResString(Context ctx){
		ctx_for_getResString = ctx;
	}
	/**
	 * 获取res字符串资源
	 * @param ctx
	 * @param str
	 * @return
	 */
	public static String R(String str){		
		if(ctx_for_getResString==null)
			return str;
		String rs = "";
		int i = ctx_for_getResString.getResources().getIdentifier(str,"string", ctx_for_getResString.getPackageName());
		rs = ctx_for_getResString.getResources().getString(i);
		return rs;
	}
	
	public static ArrayList<UserBean> getFollowersWithCache(){
		DiguApi api = new DiguApi();
		if(U.getCacheData().Followers.size()==0){
			try {
				U.getCacheData().Followers = api.getFollowersList();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
		return U.getCacheData().Followers;
		
	}
	
	public static ArrayList<UserBean> getFriendsWithCache(){
		DiguApi api = new DiguApi();
		if(U.getCacheData().Friends.size()==0){
			try {
				U.getCacheData().Friends = api.getFriendsList();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
		return U.getCacheData().Friends;
		
	}
	
	/**
	 * 判读是否有加载sdcard
	 * @return
	 */
	public static Boolean hasSdCard(){
		Environment  en =  new Environment();
		if ( !android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) ) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判读是否有加载sdcard
	 * @return
	 */
	
	public static boolean hasEnoughSpace(long fileLengh) {
		return (getSdCardSpace() > fileLengh);
	}
	
	/**
	 * 获取sdcard剩余空间 单位为byte 1024*1024 byte = 1024 KB = 1 MB
	 * @return
	 */
	
	public static long getSdCardSpace() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long availableBlocks = stat.getAvailableBlocks();
		
		return availableBlocks*stat.getBlockSize();
	}
}
