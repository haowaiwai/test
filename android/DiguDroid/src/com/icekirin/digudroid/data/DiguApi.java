package com.icekirin.digudroid.data;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.icekirin.digudroid.util.*;

public class DiguApi {

	//嘀咕的api地址
	private static final String BASE_URL = "http://api.minicloud.com.cn";
	private static final String EXTENSION = ".json";
	private static final String PUBLIC_TIMELINE_URL = BASE_URL + "/statuses/public_timeline" + EXTENSION;
	private static final String FRIENDS_TIMELINE_URL = BASE_URL + "/statuses/friends_timeline" + EXTENSION;
	private static final String FRIENDS_URL = BASE_URL + "/statuses/friends" + EXTENSION;
	private static final String FOLLOWERS_URL = BASE_URL + "/statuses/followers" + EXTENSION;
	private static final String FRIENDS_COUNT_URL = BASE_URL + "/statuses/friends_count" + EXTENSION;
	private static final String FOLLOWERS_COUNT_URL = BASE_URL + "/statuses/followers_count" + EXTENSION;
	private static final String UPDATE_STATUS_URL = BASE_URL + "/statuses/update" + EXTENSION;
	private static final String UPDATE_MESSAGE_URL = BASE_URL + "/messages/handle/new" + EXTENSION;
	private static final String DELETE_STATUS_URL = BASE_URL + "/statuses/destroy" + EXTENSION;
	private static final String MESSAGE_GET_URL = BASE_URL + "/messages/0" + EXTENSION;
	private static final String MESSAGE_SYSTEM_URL = BASE_URL + "/messages/4" + EXTENSION;
	private static final String DELETE_MESSAGE_URL = BASE_URL + "/messages/handle/destroy" + EXTENSION;
	
	private static final String VERIFY_URL = BASE_URL + "/account/verify" + EXTENSION;
	private static final String SOURCE_PARAMETER = "火";
	private static final int COUNT = 20;
	
	//http://api.minicloud.com.cn/statuses/public_timeline.json
	//http://api.minicloud.com.cn/statuses/friends_timeline.json
	//http://api.minicloud.com.cn/statuses/friends.json
	//http://api.minicloud.com.cn/statuses/friends_count.json
	//http://api.minicloud.com.cn/statuses/followers_count.json
	//http://api.minicloud.com.cn/statuses/friends.json?friendUserId=11319295
	private String userName,passWord;	
	
	public DiguApi(String userName, String passWord){
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public DiguApi(){
		this.userName = F.userName;
		this.passWord = F.passWord;
	}
	
	public boolean verify(String name, String password){
		//http://api.minicloud.com.cn/statuses/account/verify.xml 
		JSONObject jObj = null;
		String url = VERIFY_URL;
		U.dout("verify:"+name+","+password);
		try {
			HashMap hmap = new HashMap();
			hmap.put("test", "test");
			jObj = new JSONObject(NetUtil.doRequest(url, name, password, hmap));	
			U.dout(jObj);
			U.dout(jObj.get("authorized"));
			if(jObj.getBoolean("authorized"))
				return true;
		} catch (JSONException e){
		}catch (Exception e) {
		} 
		return false;
	}
	
	
	/**
	 * 获取广场消息
	 * @return
	 * @throws Exception
	 */
	public JSONArray getPublicTimeline() throws Exception{
		String str = NetUtil.SendGetRequest(PUBLIC_TIMELINE_URL);
		JSONArray rs = new JSONArray(str);		
		return rs;
	}
	
	/**
	 * 获取悄悄话(私信)
	 * @return
	 * @throws Exception
	 */
	public JSONArray getDirectMessage(Long since, int page, String type) throws Exception {
		String url = MESSAGE_GET_URL + "?";
		if(type.equals(F.MESSAGE_SYSTEM)){
			url = MESSAGE_SYSTEM_URL + "?";
		}
			
		url+="count="+COUNT;
		if(since!=0L){
			url += "&since="+since;
		}if(page>0){
			url += "&page="+page;
		}
		String str = NetUtil.SendGetRequest(url,userName,passWord);
		JSONArray rs = new JSONArray(str);	
		return rs;
	}
	
	public boolean deleteDirectMessage(long id) {
		String url = DELETE_STATUS_URL+"?id="+id;
		JSONObject jObj = null;
		try {
			HashMap<String, String> hmap = new HashMap<String, String>();					
			hmap.put("id",	""+id);
			jObj = new JSONObject(NetUtil.doRequest(url, userName, passWord, hmap));
		} catch (JSONException e){
			return false;
		}catch (Exception e) {
			return false;
		} 
		return true;
	}
	
	public ArrayList<DirectMessageBean> getDirectMessageBean(Context ctx, String type) throws Exception{
		ArrayList<DirectMessageBean> rs = new ArrayList<DirectMessageBean>();
		DbPersist dbp = new DbPersist(ctx);
		DirectMessageBean  lastMsg  = dbp.getLastCreatedOfDirectMessage(F.userName, type);				
    	Long since = 0L;
    	String wid = null;
    	Long msg_created_time = null;
    	if(lastMsg!=null){
    		wid = lastMsg.getId();
    		msg_created_time = lastMsg.getCreated_at();
    	}    	    	    	
    	if(msg_created_time!=null){
    		since = msg_created_time;
    	}
    	boolean flag =true;
//    	if(lastMsg==null)//第一次的话，本地没数据，只取一页
//    		flag = false;
    	int page = 1;
    	do{	
    		//获取消息，20条一页，不够20条，说明一页就够了；如果满20条，准备获取下一页。
    		JSONArray jrr;
    		try{
    			jrr = getDirectMessage(since, page, type);
    		}catch(JSONException e){
    			//如果上一页是正好20条，这页是error Page，退出
    			U.dout("this is error page,can not to be a JsonArray ,break");
    			break;
    		}
	    	
	    	if(jrr.length()<20){
	    		flag =false;
	    	}else{
	    		page++;
	    	}
	    	
	    	for(int i=0;i<jrr.length();i++){
	    		JSONObject jo = jrr.getJSONObject(i);
	    		DirectMessageBean msgBean = jsonToDirectMessageBean(jo);
	    		String id = jo.getString("id");
	    		if(wid!=null&&wid.equals(id)){
	    			continue;
	    		}else{
	    			rs.add(msgBean);
	    		}
	    	}
	    	
    	}while(flag);
    	
    	
    	//写入本地数据库
    	int j = rs.size()-1;
    	for(;j>=0;j--){
    		dbp.insertDirectMessage(rs.get(j));
    	}
    	//再从数据库中读 -_-!
    	rs = dbp.queryDirectMessage(F.userName, 0, type);
    	dbp.close();
		return rs;
	}		

	/**
	 * 获取自己及好友的消息
	 * @param since
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public JSONArray getFriendsTimeline(Long since, int page) throws Exception {
		
		String url = FRIENDS_TIMELINE_URL + "?";
		url+="count="+COUNT;
		if(since!=0L){
			url += "&since="+since;
		}if(page>0){
			url += "&page="+page;
		}
		String str = NetUtil.SendGetRequest(url,userName,passWord);
		JSONArray rs = new JSONArray(str);	
		return rs;
	}
	
	/**
	 * 从网络获取FriendsTimeline，写入本地数据库 并返回MsgBean List
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public ArrayList<MsgBean> getMsgBeanOfFriendsTimeline(Context ctx) throws Exception{
		ArrayList<MsgBean> rs = new ArrayList<MsgBean>();
		DbPersist dbp = new DbPersist(ctx);
    	MsgBean  lastMsg  = dbp.getLastCreatedOfMsg(F.userName);
		
//		MsgBean  lastMsg  = new MsgBean();//test linwu的zzz
//		lastMsg.setId("6843142");
//		lastMsg.setCreated_at(1257829733000L);
		
    	Long since = 0L;
    	String wid = null;
    	Long msg_created_time = null;
    	if(lastMsg!=null){
    		wid = lastMsg.getId();
    		msg_created_time = lastMsg.getCreated_at();
    	}    	    	    	
    	if(msg_created_time!=null){
    		since = msg_created_time;
    	}
    	boolean flag =true;
    	if(lastMsg==null)//第一次的话，本地没数据，只取一页
    		flag = false;
    	int page = 1;
    	do{	
    		//获取消息，20条一页，不够20条，说明一页就够了；如果满20条，准备获取下一页。
    		JSONArray jrr;
    		try{
    			jrr = getFriendsTimeline(since, page);
    		}catch(JSONException e){
    			//如果上一页是正好20条，这页是error Page，退出
    			U.dout("this is error page,can not to be a JsonArray ,break");
    			break;
    		}    			    	
	    	
	    	if(jrr.length()<20){
	    		flag =false;
	    	}else{
	    		page++;
	    	}
	    	
	    	for(int i=0;i<jrr.length();i++){
	    		JSONObject jo = jrr.getJSONObject(i);
	    		MsgBean msgBean = jsonToMsgBean(jo);
	    		String id = jo.getString("id");
	    		if(wid!=null&&wid.equals(id)){
	    			continue;
	    		}else{
	    			rs.add(msgBean);
	    		}
	    	}
	    	
    	}while(flag);
    	
    	
    	//写入本地数据库
    	int j = rs.size()-1;
    	for(;j>=0;j--){
    		dbp.insertMsg(rs.get(j));
    	}
    	//再从数据库中读 -_-!
    	rs = dbp.queryMsg(F.userName,0);
    	dbp.close();
		return rs;
	}
	
	/**
	 * 从网络返回“我跟随的人”list
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UserBean> getFriendsList(int page) throws Exception{
		
		ArrayList<UserBean> rs = new ArrayList<UserBean>();
		String pageStr = "?page="+page;									
		String str = NetUtil.SendGetRequest(FRIENDS_URL + pageStr,userName,passWord);
		JSONArray jarr = new JSONArray(str);
		//U.dout("page"+page+",jarr="+jarr.length());
		for(int i=0;i<jarr.length();i++){
			//digu把自己的userBean也返回，所以一页满数据是21条，把第一条自己的剔除
			
			JSONObject jo = jarr.getJSONObject(i);
			UserBean userBean = jsonToUserBean(jo);
			if(userBean.getName().equals(F.userName)){
				continue;
			}
			rs.add(userBean);
		}
		//U.dout("page"+page+",rs="+rs.size());
		return rs;
	}
	
	/**
	 * 从网络返回“我跟随的人”list 所有人
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UserBean> getFriendsList() throws Exception{
		
		ArrayList<UserBean> rs = new ArrayList<UserBean>();
		String str = NetUtil.SendGetRequest(FRIENDS_URL,userName,passWord);
		JSONArray jarr = new JSONArray(str);
		
		for(int i=0;i<jarr.length();i++){
			//digu把自己的userBean也返回，所以一页满数据是21条，把第一条自己的剔除
			
			JSONObject jo = jarr.getJSONObject(i);
			UserBean userBean = jsonToUserBean(jo);
			if(userBean.getName().equals(F.userName)){
				continue;
			}
			rs.add(userBean);
		}
		//U.dout("jarr="+jarr.length());
		//如果需要(装满了21条),向服务器要求更多数据
		if(jarr.length()==21){
			int friends_count = getFriendsCount();
			//U.dout("friends_count="+friends_count);
			//正好20个好友就不必要求第二页
			if(friends_count==20){
				return rs;
			}
			int recount = friends_count / 20;
			for(int i=0;i<recount;i++){
				int page = recount + 1;
				rs.addAll(getFriendsList(page)); 
			}
		}
		//U.dout("final rs="+rs.size());
		return rs;
	}
	
	
	/**
	 * 从网络返回“跟随我的人”list
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UserBean> getFollowersList(int page) throws Exception{
		
		ArrayList<UserBean> rs = new ArrayList<UserBean>();
		String pageStr = "?page="+page;									
		String str = NetUtil.SendGetRequest(FOLLOWERS_URL + pageStr,userName,passWord);
		JSONArray jarr = new JSONArray(str);
		//U.dout("page"+page+",jarr="+jarr.length());
		for(int i=0;i<jarr.length();i++){
			//digu把自己的userBean也返回，所以一页满数据是21条，要把自己的剔除
			JSONObject jo = jarr.getJSONObject(i);
			UserBean userBean = jsonToUserBean(jo);
			if(userBean.getName().equals(F.userName)){
				continue;
			}
			rs.add(userBean);
		}
		//U.dout("page"+page+",rs="+rs.size());
		return rs;
	}
	
	/**
	 * 从网络返回“跟随我的人”list 所有人
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UserBean> getFollowersList() throws Exception{
		
		ArrayList<UserBean> rs = new ArrayList<UserBean>();
		String str = NetUtil.SendGetRequest(FOLLOWERS_URL,userName,passWord);
		JSONArray jarr = new JSONArray(str);
		
		for(int i=0;i<jarr.length();i++){
			//digu把自己的userBean也返回，所以一页满数据是21条，把自己的剔除
			
			JSONObject jo = jarr.getJSONObject(i);
			UserBean userBean = jsonToUserBean(jo);
			if(userBean.getName().equals(F.userName)){
				continue;
			}
			rs.add(userBean);
		}
		//U.dout("jarr="+jarr.length());
		//如果需要(装满了21条),向服务器要求更多数据
		if(jarr.length()==21){
			int friends_count = getFollowersCount();
			//U.dout("friends_count="+friends_count);
			//正好20个好友就不必要求第二页
			if(friends_count==20){
				return rs;
			}
			int recount = friends_count / 20;
			for(int i=0;i<recount;i++){
				int page = recount + 1;
				rs.addAll(getFollowersList(page)); 
			}
		}
		//U.dout("final rs="+rs.size());
		return rs;
	}		
	
	/**
	 * 获取“我跟随的人”数目
	 * @return
	 * @throws Exception
	 */
	public int getFriendsCount() throws Exception{
		String str = NetUtil.SendGetRequest(FRIENDS_COUNT_URL,userName,passWord);
		JSONObject jo = new JSONObject(str);
		String c = jo.getString("friendsCount");
		return Integer.parseInt(c);
	}
	
	/**
	 * 获取“跟随我的人”数目
	 * @return
	 * @throws Exception
	 */
	public int getFollowersCount() throws Exception{
		String str = NetUtil.SendGetRequest(FOLLOWERS_COUNT_URL,userName,passWord);
		JSONObject jo = new JSONObject(str);
		String c = jo.getString("followersCount");
		return Integer.parseInt(c);
	}
	
	/**
	 * 通过用户id获取详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserBean getUserById(String id) throws Exception{
		UserBean userBean = new UserBean();
		String userIdStr = "?friendUserId="+ id;
		String str = NetUtil.SendGetRequest(FRIENDS_URL + userIdStr,userName,passWord);
		JSONArray jarr = new JSONArray(str);
		for(int i=0;i<jarr.length();i++){
			JSONObject jo = jarr.getJSONObject(i);
			userBean = jsonToUserBean(jo);
			if(userBean.getId().equals(id))
				return userBean;
		}
		return userBean;
		
	}
	
	
	public boolean updateStatus(String message, long inReplyToId) {
		String url = UPDATE_STATUS_URL;
//		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//		formParams.add(new BasicNameValuePair("content", message));
//		formParams.add(new BasicNameValuePair("source", SOURCE_PARAMETER));
//		if (inReplyToId > 0) {
//			formParams.add(new BasicNameValuePair("digu_id",String.valueOf(inReplyToId)));
//		}
		JSONObject jObj = null;
		try {
			//jObj = new JSONObject(NetUtil.SendPostRequest(url, new UrlEncodedFormEntity(formParams, HTTP.UTF_8),userName, passWord));
			HashMap<String, String> hmap = new HashMap<String, String>();
			hmap.put("content",message);
			hmap.put("source",SOURCE_PARAMETER);
			if (inReplyToId > 0) {
				hmap.put("digu_id",	""+inReplyToId);
			}
			jObj = new JSONObject(NetUtil.doRequest(url, userName, passWord, hmap));
			U.dout("server return the msg id ="+jObj.get("id"));
		} catch (JSONException e){
			return false;
		}catch (Exception e) {
			return false;
		} 
		return true;
	}
	
	public boolean updateDirectMessage(String message, String receiveUserId) {
		String url = UPDATE_MESSAGE_URL;
		JSONObject jObj = null;
		try {
			HashMap<String, String> hmap = new HashMap<String, String>();
			hmap.put("content",message);
			hmap.put("source",SOURCE_PARAMETER);
			hmap.put("message","0");
			hmap.put("receiveUserId", receiveUserId);						
			jObj = new JSONObject(NetUtil.doRequest(url, userName, passWord, hmap));
			U.dout(jObj);
			//U.dout("server return the msg id ="+jObj.get("id"));
		} catch (JSONException e){
			return false;
		}catch (Exception e) {
			return false;
		} 
		return true;
	}
	
	public boolean deleteStatus(long id) {
		String url = DELETE_STATUS_URL+"?id="+id;
		JSONObject jObj = null;
		try {
			//jObj = new JSONObject(NetUtil.SendPostRequest(url, new UrlEncodedFormEntity(formParams, HTTP.UTF_8),userName, passWord));
			HashMap<String, String> hmap = new HashMap<String, String>();					
			hmap.put("id",	""+id);
			jObj = new JSONObject(NetUtil.doRequest(url, userName, passWord, hmap));
			U.dout("server return the msg id ="+jObj.get("id"));
		} catch (JSONException e){
			return false;
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public MsgBean jsonToMsgBean(JSONObject jo) throws Exception{
		
		JSONObject user = jo.getJSONObject("user");
		UserBean userBean = jsonToUserBean(user);
		
		MsgBean msgBean = new MsgBean();
		msgBean.setUserBean(userBean);
		
		msgBean.setCreated_at(U.dateStr2Long(jo.getString("created_at")));
		msgBean.setId(jo.getString("id"));
		msgBean.setText(jo.getString("text"));
		String str = jo.getString("picPath");
		str = U.chgPicPath(str);
		msgBean.setPicPath(str);
		msgBean.setSource(jo.getString("source"));
		msgBean.setIn_reply_to_status_id(jo.getString("in_reply_to_status_id"));
		msgBean.setIn_reply_to_user_id(jo.getString("in_reply_to_user_id"));
		msgBean.setIn_reply_to_user_name(jo.getString("in_reply_to_user_name"));
		msgBean.setFavorited(jo.getString("favorited"));
		msgBean.setIn_reply_to_screen_name(jo.getString("in_reply_to_screen_name"));
						
		return msgBean;
	}
	
	public DirectMessageBean jsonToDirectMessageBean(JSONObject jo) throws Exception{
		
		DirectMessageBean msgBean = new DirectMessageBean();		
		msgBean.setCreated_at(U.dateStr2Long(jo.getString("created_at")));
		msgBean.setId(jo.getString("id"));
		msgBean.setText(jo.getString("text"));
		msgBean.setCategory(jo.getInt("category"));
		msgBean.setSender_id(jo.getString("sender_id"));
		msgBean.setSender_screen_name(jo.getString("sender_screen_name"));
		msgBean.setRecipient_id(jo.getString("recipient_id"));
		msgBean.setRecipient_screen_name(jo.getString("recipient_screen_name"));
		UserBean sender = jsonToSender(jo.getJSONObject("sender"));
		msgBean.setSender(sender);
		return msgBean;
	}
	
	public UserBean jsonToUserBean(JSONObject user) throws Exception{
		//U.dout("profile_image_url"+user.getString("profile_image_url"));
		UserBean userBean = new UserBean();
		userBean.setId(user.getString("id"));
		userBean.setName(user.getString("name"));		
		userBean.setScreen_name(user.getString("screen_name"));		
		userBean.setLocation(user.getString("location"));
		userBean.setGender(""+user.getInt("gender"));
		userBean.setDescription(user.getString("description"));
		userBean.setProfile_image_url(user.getString("profile_image_url"));
		userBean.setUrl(user.getString("url"));
		userBean.setFollowers_count(user.getString("followers_count"));
		userBean.setFriends_count(user.getString("friends_count"));
		userBean.setFavourites_count(user.getString("favourites_count"));
		userBean.setStatuses_count(user.getString("statuses_count"));
		userBean.setPrivacy(user.getString("privacy"));
		return userBean;
	}
	
	public UserBean jsonToSender(JSONObject user) throws Exception{
		UserBean userBean = new UserBean();
		userBean.setId(user.getString("id"));
		userBean.setName(user.getString("name"));		
		userBean.setScreen_name(user.getString("screen_name"));		
		userBean.setLocation(user.getString("location"));
		userBean.setGender(""+user.getInt("gender"));
		userBean.setDescription(user.getString("description"));
		userBean.setProfile_image_url(user.getString("profile_image_url"));
		userBean.setUrl(user.getString("url"));
		userBean.setFollowers_count(user.getString("followers_count"));						
		userBean.setPrivacy(user.getString("privacy"));
		return userBean;
	}

	

}
