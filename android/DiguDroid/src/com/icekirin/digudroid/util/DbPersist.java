/*
 * Version 1.0
 *
 * =============================================================
 * Revision History
 * 
 * Modification                    Tracking
 * Date           Author           Number      Description of changes
 * ----------     --------------   ---------   -------------------------
 * 2009-09-23     WengKun                      create
 */

package com.icekirin.digudroid.util;

import java.util.ArrayList;

import com.icekirin.digudroid.data.DirectMessageBean;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.MsgBean;
import com.icekirin.digudroid.data.UserBean;
import com.icekirin.digudroid.data.DiguTable.DirectMessageTable;
import com.icekirin.digudroid.data.DiguTable.MsgTable;
import com.icekirin.digudroid.data.DiguTable.UserTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**db操作的辅助类 tip表*/
public class DbPersist {
	private static final String TAG = "DBPersist";
	private static final String DB_NAME = "digu.db";
	private static final int DB_VERSION = 2;	
	 
	private final DatabaseHelper mOpenHelper;
	private static Context mContext;
	private final SQLiteDatabase dbW;			
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DB_NAME, null, DB_VERSION);
			mContext = context;
		}
 
		@Override
		public void onCreate(SQLiteDatabase db) {
					
			//msg表
			String sql = "CREATE TABLE " + MsgTable.TABLE_NAME + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MsgTable.CONTENT +" varchar(255),"            
            + MsgTable.WID +" varchar(50),"
            + MsgTable.SOURCE+" varchar(255),"
            + MsgTable.PIC_Path_URL +" varchar(255),"
            + MsgTable.PIC_Path_LOCAL +" varchar(255),"
            + MsgTable.IN_REPLY_TO_STATUS_ID +" varchar(50),"
            + MsgTable.IN_REPLY_TO_USER_ID +" varchar(50),"
            + MsgTable.IN_REPLY_TO_USER_NAME +" varchar(50),"
            + MsgTable.MSG_USER_NAME +" varchar(50),"
            + MsgTable.MSG_USER_SCREEN_NAME +" varchar(50),"
            + MsgTable.MSG_USER_WID +" varchar(50),"
            + MsgTable.MSG_CREATED_AT +" INTEGER,"
            + MsgTable.LOGIN_USER_NAME +" varchar(50),"
            + MsgTable.DB_ADD_TIME +" timestamp DEFAULT CURRENT_TIMESTAMP"
            + ");";
			db.execSQL(sql);
			
			//user表
			sql = "CREATE TABLE " + UserTable.TABLE_NAME + " ("
            + UserTable.WID +" varchar(50),"
            + UserTable.NAME+" varchar(255),"
            + UserTable.SCREEN_NAME+" varchar(255),"
            + UserTable.DESCRIPTION +" text,"
            + UserTable.PROFILE_IMAGE_URL +" varchar(255),"
            + UserTable.PROFILE_IMAGE_PATH +" varchar(255)"
            + ");";
			db.execSQL(sql);
			
			//DirectMessage表
			sql = "CREATE TABLE " + DirectMessageTable.TABLE_NAME + " ("
            + DirectMessageTable.WID +" varchar(50),"
            + DirectMessageTable.CATEGORY+" INTEGER,"
            + DirectMessageTable.TEXT+" varchar(255),"
            + DirectMessageTable.SENDER_ID +" varchar(50),"
            + DirectMessageTable.RECIPIENT_ID +" varchar(50),"
            + DirectMessageTable.CREATED_AT +" INTEGER,"
            + DirectMessageTable.LOGIN_USER_NAME +" varchar(50),"
            + DirectMessageTable.SENDER_SCREEN_NAME +" varchar(50),"
            + DirectMessageTable.RECIPIENT_SCREEN_NAME +" varchar(50)"
            + ");";
			db.execSQL(sql);
			 
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+MsgTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+UserTable.TABLE_NAME);

            onCreate(db);
		}
		
	}
	
	public DbPersist(Context ctx){
		DbPersist.mContext = ctx;
		mOpenHelper = new DatabaseHelper(mContext);
		dbW = mOpenHelper.getWritableDatabase();	
	}
	
	public int getNextIdForMsg(){
		int id = getLastIdForMsg();
		id++;
		return id;
	}
	
	public int getLastIdForMsg(){
		String str [] = {"max(_id)"}; 
		Cursor cur = dbW.query(MsgTable.TABLE_NAME, str, null, null, null, null, null);
		if(cur.getCount()==0){
			cur.close();
			return 0;			
		}
		cur.moveToFirst();		
		int id = cur.getInt(0);
		cur.close();
		return id;
	}
	
	public long insertDirectMessage(DirectMessageBean msgBean){
		ContentValues cv = new ContentValues();
		cv.put(DirectMessageTable.WID, msgBean.getId());
		cv.put(DirectMessageTable.CATEGORY, msgBean.getCategory());
		cv.put(DirectMessageTable.TEXT, msgBean.getText());
		cv.put(DirectMessageTable.SENDER_ID, msgBean.getSender_id());
		cv.put(DirectMessageTable.RECIPIENT_ID, msgBean.getRecipient_id());
		cv.put(DirectMessageTable.SENDER_SCREEN_NAME, msgBean.getSender_screen_name());
		cv.put(DirectMessageTable.RECIPIENT_SCREEN_NAME, msgBean.getRecipient_screen_name());
		//存long型，便于排序
		cv.put(DirectMessageTable.CREATED_AT, msgBean.getCreated_at());
		cv.put(DirectMessageTable.LOGIN_USER_NAME, F.userName);
		insertUser(msgBean.getSender());
		return dbW.insert(DirectMessageTable.TABLE_NAME, null, cv);

	}
	
	public int deleteDirectMessage(DirectMessageBean msgBean){
		String whereClause = DirectMessageTable.WID+" = ?";
		String [] whereArgs = {msgBean.getId()};
		return dbW.delete(DirectMessageTable.TABLE_NAME, whereClause, whereArgs);		
	}

	/**
	 * 获得DirectMessage list
	 * @param loginUserName
	 * @return
	 */
	public ArrayList<DirectMessageBean> queryDirectMessage(String loginUserName, int page, String type){
		
		String[] selectionArgs = {loginUserName, type};
		String [] columns = {
			DirectMessageTable.WID,     				//0
			DirectMessageTable.CATEGORY,      			//1
			DirectMessageTable.TEXT,    				//2
			DirectMessageTable.SENDER_ID,      	    	//3	
			DirectMessageTable.RECIPIENT_ID, 			//4
			DirectMessageTable.CREATED_AT,				//5
			DirectMessageTable.SENDER_SCREEN_NAME,		//6
			DirectMessageTable.RECIPIENT_SCREEN_NAME	//7
		};
		String orderBy = DirectMessageTable.CREATED_AT + " DESC";		
		int pageSize = F.PAGE_SIZE; 
		String start = page * pageSize + "";
		String limit = " "+start + "," + pageSize+" ";		
		String sql = "select * from "+DirectMessageTable.TABLE_NAME+" where "+DirectMessageTable.LOGIN_USER_NAME +" = ?"
		+" and " + DirectMessageTable.CATEGORY+" = ?" + " order by " + orderBy + " limit " + limit;
		sql = U.appendSql(columns, sql);		
		Cursor cur = dbW.rawQuery(sql, selectionArgs);
		int count = cur.getCount();
		cur.moveToFirst();
		ArrayList<DirectMessageBean> rlist = new ArrayList<DirectMessageBean>();		
		for(int i=0;i<count;i++){
			DirectMessageBean msgBean = new DirectMessageBean();
			msgBean.setId(cur.getString(0));
			msgBean.setCategory(cur.getInt(1));
			msgBean.setText(cur.getString(2));
			msgBean.setSender_id(cur.getString(3));
			UserBean sender = queryUser(msgBean.getSender_id());
			msgBean.setSender(sender);
			msgBean.setRecipient_id(cur.getString(4));
			msgBean.setCreated_at(cur.getLong(5));
			msgBean.setSender_screen_name(cur.getString(6));
			msgBean.setRecipient_screen_name(cur.getString(7));
			rlist.add(msgBean);
			cur.moveToNext();
		}
		cur.close();
		return rlist;
	}
	
	/**
	 * 获取本地数据库中最新一条消息
	 * @param loginUserName
	 * @return
	 */
	public DirectMessageBean getLastCreatedOfDirectMessage(String loginUserName, String type){
				
		String [] columns = {
				DirectMessageTable.WID,     				//0
				DirectMessageTable.CATEGORY,      			//1
				DirectMessageTable.TEXT,    				//2
				DirectMessageTable.SENDER_ID,      	    	//3	
				DirectMessageTable.RECIPIENT_ID, 			//4
				DirectMessageTable.CREATED_AT,				//5
				DirectMessageTable.SENDER_SCREEN_NAME,		//6
				DirectMessageTable.RECIPIENT_SCREEN_NAME	//7
		};
		String selection = DirectMessageTable.LOGIN_USER_NAME +" = ? and " + DirectMessageTable.CATEGORY+" = ?";
		String[] selectionArgs = {loginUserName, type};
		String orderBy = DirectMessageTable.CREATED_AT + " DESC limit 1";//按照时间降序，获得最新一条
		Cursor cur = dbW.query(DirectMessageTable.TABLE_NAME, columns, selection, selectionArgs, null, null, orderBy);
		int count = cur.getCount();
		if(count==0){
			cur.close();
			return null;
		}
		cur.moveToFirst();
		DirectMessageBean msgBean = new DirectMessageBean();
		
		msgBean.setId(cur.getString(0));
		msgBean.setCategory(cur.getInt(1));
		msgBean.setText(cur.getString(2));
		msgBean.setSender_id(cur.getString(3));
		UserBean sender = queryUser(msgBean.getSender_id());
		msgBean.setSender(sender);
		msgBean.setRecipient_id(cur.getString(4));
		msgBean.setCreated_at(cur.getLong(5));
		msgBean.setSender_screen_name(cur.getString(6));
		msgBean.setRecipient_screen_name(cur.getString(7));
		cur.close();
		return msgBean;
	}
	
	public int insertMsg(MsgBean msgBean){
		int id = -1;
		ContentValues cv = new ContentValues();
		id = getNextIdForMsg();
		cv.put("_id", id);
		cv.put(MsgTable.MSG_USER_NAME, msgBean.getUserBean().getName());
		cv.put(MsgTable.MSG_USER_WID, msgBean.getUserBean().getId());
		cv.put(MsgTable.MSG_USER_SCREEN_NAME, msgBean.getUserBean().getScreen_name());
		//存long型，便于排序
		cv.put(MsgTable.MSG_CREATED_AT, msgBean.getCreated_at());
		cv.put(MsgTable.SOURCE, msgBean.getSource());
		cv.put(MsgTable.CONTENT, msgBean.getText());
		cv.put(MsgTable.PIC_Path_URL, msgBean.getPicPath());
		cv.put(MsgTable.WID, msgBean.getId());
		cv.put(MsgTable.LOGIN_USER_NAME, F.userName);
		insertUser(msgBean.getUserBean());
		dbW.insert(MsgTable.TABLE_NAME, null, cv);
		return id;
	}
	
	public int deleteMsg(MsgBean msgBean){		
		String whereClause = MsgTable.WID+" = ?";
		String [] whereArgs = {msgBean.getId()};
		return dbW.delete(MsgTable.TABLE_NAME, whereClause, whereArgs);		
	}
	
	
	public void insertUser(UserBean userBean){
		//如果已有 返回
		if(isAlreadyInUserTable(userBean))
			return;
		ContentValues cv = new ContentValues();
		cv.put(UserTable.WID, userBean.getId());
		cv.put(UserTable.NAME, userBean.getName());
		cv.put(UserTable.SCREEN_NAME, userBean.getScreen_name());
		cv.put(UserTable.DESCRIPTION, userBean.getDescription());
		cv.put(UserTable.PROFILE_IMAGE_URL, userBean.getProfile_image_url());
				
		dbW.insert(UserTable.TABLE_NAME, null, cv); 
	}
	
	
	/**
	 * 获得消息list
	 * @param loginUserName
	 * @return
	 */
	public ArrayList<MsgBean> queryMsg(String loginUserName, int page){
		
		String selection = MsgTable.LOGIN_USER_NAME +" = ?";
		String[] selectionArgs = {loginUserName};
		String [] columns = {
			MsgTable.MSG_USER_NAME,     //0
			MsgTable.MSG_USER_WID,      //1
			MsgTable.MSG_CREATED_AT,    //2
			MsgTable.SOURCE,      	    //3	
			MsgTable.CONTENT, 			//4
			MsgTable.PIC_Path_URL,		//5
			MsgTable.WID,		//6
			
		};
		String orderBy = MsgTable.MSG_CREATED_AT + " DESC";		
		int pageSize = F.PAGE_SIZE; 
		String start = page * pageSize + "";
		String limit = " "+start + "," + pageSize+" ";		
		
		
		//Cursor cur = dbW.query(MsgTable.TABLE_NAME, columns, selection, selectionArgs, null, null, orderBy, limit);
		String sql = "select * from "+MsgTable.TABLE_NAME+" where "+MsgTable.LOGIN_USER_NAME +" = ?"
		+" order by " + orderBy + " limit " + limit;
		sql = U.appendSql(columns, sql);		
		Cursor cur = dbW.rawQuery(sql, selectionArgs);
		int count = cur.getCount();
		cur.moveToFirst();
		ArrayList<MsgBean> rlist = new ArrayList<MsgBean>();		
		for(int i=0;i<count;i++){
			MsgBean msgBean = new MsgBean();
			UserBean userBean = queryUser(cur.getString(1));
//			userBean.setName(cur.getString(0));
//			userBean.setId(cur.getString(1));
			msgBean.setUserBean(userBean);
			msgBean.setCreated_at(cur.getLong(2));
			msgBean.setSource(cur.getString(3));
			msgBean.setText(cur.getString(4));
			msgBean.setPicPath(cur.getString(5));
			msgBean.setId(cur.getString(6));
			rlist.add(msgBean);
			
			cur.moveToNext();
		} 
		cur.close();
		return rlist;
	}
	
	/**
	 * 获取msg数目
	 * @return
	 */
	public int getMsgCount(String loginUserName){
		int rs = 0;
		String sql = "select count(*) from " + MsgTable.TABLE_NAME + " where "+ MsgTable.LOGIN_USER_NAME+ " = ?";
		String str [] = {loginUserName};
		Cursor cur = dbW.rawQuery(sql, str);
		cur.moveToFirst();
		rs = cur.getInt(0);
		cur.close();
		return rs;
	} 
	
	/**
	 * 获取DirectMessage数目
	 * @return
	 */
	public int getDirectMessageCount(String loginUserName, String type){
		int rs = 0;
		String sql = "select count(*) from " + DirectMessageTable.TABLE_NAME + " where "
		+ DirectMessageTable.LOGIN_USER_NAME+ " = ? and " +DirectMessageTable.CATEGORY+" = ?" ;
		String str [] = {loginUserName, type};
		Cursor cur = dbW.rawQuery(sql, str);
		cur.moveToFirst();
		rs = cur.getInt(0);
		cur.close();
		return rs;
	} 
	
	/**
	 * 通过消息网络id获得本地消息表中一条数据 返回MsgBean
	 * @param loginUserName
	 * @return
	 */
	public MsgBean queryMsgById(String loginUserName, String id){
		
		String selection = MsgTable.LOGIN_USER_NAME +" = ? and "+MsgTable.WID +" =?";
		String[] selectionArgs = {loginUserName, id};
		String [] columns = {
			MsgTable.MSG_USER_NAME,     //0
			MsgTable.MSG_USER_WID,      //1
			MsgTable.MSG_CREATED_AT,    //2
			MsgTable.SOURCE,      	    //3	
			MsgTable.CONTENT, 			//4
			MsgTable.PIC_Path_URL,		//5
			MsgTable.WID,		//6
		};
		Cursor cur = dbW.query(MsgTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		int count = cur.getCount();
		if(count==0){
			cur.close();
			return null;
		}			
		cur.moveToFirst();						
		MsgBean msgBean = new MsgBean();
		UserBean userBean = queryUser(cur.getString(1));
		msgBean.setUserBean(userBean);
		msgBean.setCreated_at(cur.getLong(2));
		msgBean.setSource(cur.getString(3));
		msgBean.setText(cur.getString(4));
		msgBean.setPicPath(cur.getString(5));
		msgBean.setId(cur.getString(6));
		cur.close();
		return msgBean;
	}
	
	/**
	 * 获取本地数据库中最新一条消息
	 * @param loginUserName
	 * @return
	 */
	public MsgBean getLastCreatedOfMsg(String loginUserName){
				
		String str [] = {
				MsgTable.MSG_USER_NAME,     //0
				MsgTable.MSG_USER_WID,      //1
				MsgTable.MSG_CREATED_AT,    //2
				MsgTable.SOURCE,      	    //3	
				MsgTable.CONTENT, 			//4
				MsgTable.PIC_Path_URL,		//5
				MsgTable.WID,		//6
		};
		String selection = MsgTable.LOGIN_USER_NAME +" = ?";
		String[] selectionArgs = {loginUserName};
		String orderBy = MsgTable.MSG_CREATED_AT + " DESC limit 1";//按照时间降序，获得最新一条
		Cursor cur = dbW.query(MsgTable.TABLE_NAME, str, selection, selectionArgs, null, null, orderBy);
		int count = cur.getCount();
		if(count==0){
			cur.close();
			return null;
		}			
		cur.moveToFirst();						
		MsgBean msgBean = new MsgBean();
		UserBean userBean = queryUser(cur.getString(1));
		msgBean.setUserBean(userBean);
		msgBean.setCreated_at(cur.getLong(2));
		msgBean.setSource(cur.getString(3));
		msgBean.setText(cur.getString(4));
		msgBean.setPicPath(cur.getString(5));
		msgBean.setId(cur.getString(6));
		cur.close();
		return msgBean;
	}
	
	
	
	/**
	 * 通过用户网络id获取本地用户表中一条数据 返回UserBean
	 * @param wid
	 * @return
	 */
	public UserBean queryUser(String wid){
		UserBean userBean = new UserBean();
		String selection = UserTable.WID +" = ?";
		String[] selectionArgs = {wid};
		String [] columns = {
			UserTable.WID,     			//0
			UserTable.NAME,     	 	//1
			UserTable.DESCRIPTION,    	//2
			UserTable.PROFILE_IMAGE_URL,      	    //3	
			UserTable.PROFILE_IMAGE_PATH, 			//4
			UserTable.SCREEN_NAME, 			//5
		};
		Cursor cur = dbW.query(UserTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		int count = cur.getCount();
		if(cur.getCount()==0){
			cur.close();
			return userBean;			
		}
		
		cur.moveToFirst();
		userBean.setId(cur.getString(0));
		userBean.setName(cur.getString(1));
		userBean.setDescription(cur.getString(2));		
		userBean.setProfile_image_url(cur.getString(3));
		userBean.setScreen_name(cur.getString(5));
		cur.close();
		return userBean;
	}
	
	
	
	public boolean isAlreadyInUserTable(UserBean userBean){
		
		String selection = UserTable.WID+"= ?";
		String[] selectionArgs = {""+userBean.getId()};
		String [] columns = {UserTable.WID};
		Cursor cur = dbW.query(UserTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		int i = cur.getCount();
		cur.close();
		if(cur.getCount()==0){
			return false;
		}else{
			return true;	
		}
	}
	/**判断已经数据库里是否已经有该菜谱的信息*/
//	public boolean isAlreadyInsertDb(Dish dish){
//		
//		String selection = Dish.COLUMNS[1]+"= ?";
//		String[] selectionArgs = {""+dish.dishWId};
//		Cursor cur = dbW.query(TABLE_NAME_DISH, Dish.COLUMNS, selection, selectionArgs, null, null, null);
//		int i = cur.getCount();
//		cur.close();
//		if(cur.getCount()==0){
//			return false;
//		}else{
//			return true;	
//		}
//	}
	
	
	/**将图片插入file表*/
//	public int insertFile(File f){        
//        int id = -1;
//		try {
//			InputStream is = new FileInputStream(f);
//			byte[] blobByte = new byte[is.available()];
//	        is.read(blobByte);
//	        is.close();
//			ContentValues cv = new ContentValues();
//			id = getNextIdForFile();
//			cv.put(Img.COLUMNS[0], id);
//			cv.put(Img.COLUMNS[1], f.getName());
//			cv.put(Img.COLUMNS[2], FILE_TYPE_IMG);
//			cv.put(Img.COLUMNS[3], blobByte);
//			dbW.insert(TABLE_NAME_FIlE, null, cv);
//		} catch (Exception e) {			
//			e.printStackTrace();
//			id = -1;
//		}
//		return id;
//	}
			

	
//	public Tip getTipDataById(String id){
//		HashMap<String, Object> hmap = new HashMap<String, Object>();
//		String selection = Tip.COLUMNS[0]+"= ?";
//		String[] selectionArgs = {""+id};
//		Cursor cur = dbW.query(TABLE_NAME_TIP, Tip.COLUMNS, selection, selectionArgs, null, null, null);		
//		cur.moveToFirst();		
//		Tip tip = new Tip();
//		tip.name = cur.getString(1);
//		tip.txt = cur.getString(2);
//		tip.matching = cur.getString(3);
//		tip.addImg(getImgById(cur.getString(4)));
//		cur.close();
//		return tip; 
//	}
	
//	public int getLastIdForFile(){
//		String str [] = {"max("+Img.COLUMNS[0]+")"}; 
//		Cursor cur = dbW.query(TABLE_NAME_FIlE, str, null, null, null, null, null);
//		if(cur.getCount()==0){
//			cur.close();
//			return 0;			
//		}			
//		cur.moveToFirst();		
//		int id = cur.getInt(0);
//		cur.close();
//		return id;
//	}
//
//	
//	public int getNextIdForTip(){
//		int id = getLastIdForTip();
//		id++;
//		return id;
//	}
	
	
	public void close(){
		if(dbW.isOpen()) {
			dbW.close();
		}		
		mOpenHelper.close();
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		close();
	}

	 	
	/**根据菜名查询菜谱*/
	public String queryDish(String str){
//		String rs= "";
//		String selection = Dish.COLUMNS[3] +" like ?";
//		String[] selectionArgs = {" '%"+str+"%' "};
//		Cursor cur = dbW.query(TABLE_NAME_DISH, Dish.COLUMNS, selection, selectionArgs, null, null, null);
//		int count = cur.getCount();
//		U.dout("count="+count);
//		cur.moveToFirst();
//		for(int i=0;i<count;i++){
//			rs += cur.getString(3)+" ";
//			cur.moveToNext();
//		}
//		cur.close();
//		U.dout("rs="+rs);
		return "";
	}
}
