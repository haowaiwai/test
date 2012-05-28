package cn.icekirin.digudroid.data;

import cn.icekirin.digudroid.util.U;

public class F {
	
	public static String USER_HEAD_PATH = U.getCacheData().localPath+"/user";
	public static String PUB_USER_HEAD_PATH = U.getCacheData().localPath+"/pubuser";
	public static String IMG_PATH =U.getCacheData().localPath+"/img";
	public static final String I_FOLLOW ="friends";
	public static final String FOLLOW_ME ="followers";
	public static String userName = "";
	public static String passWord = "";
	
	public static final String PUB_MSG = "pub_msg";//公告消息
	public static final String PRIVATE_MSG = "private_msg";//个人消息
	
	public static final String OP_TYPE_WRITE = "write";
	public static final String OP_TYPE_REPLY = "reply";
	public static final String OP_TYPE_FAV = "fav";
	public static final String OP_TYPE_ZZ = "zz";
	public static final String OP_TYPE_DELETE = "delete";
	
	public static final String ACTION_REPLY_DIRECT_MSG = "reply_direct_msg";	
	
	public static final String MESSAGE_GET = "0";
	public static final String MESSAGE_SEND = "message_send";
	public static final String MESSAGE_SYSTEM = "4";
	public static final String MESSAGE_WRITE = "message_write";
	public static int PAGE_SIZE = 30;
	
}
