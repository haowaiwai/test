package com.icekirin.digudroid.data;

public class DiguTable {
	
	public static final class UserTable {
		/**
    	 * 表名 "user"
    	 */
		public static final String TABLE_NAME = "user";
		//以下常量分别对应数据表里的字段
        /**
         * 字段名 "wid"
         */
        public static final String WID = "wid";
        /**
         * 字段名 "name" 登录名
         */
        public static final String NAME = "name";
        /**
         * 字段名 "screen_name" 昵称
         */
        public static final String SCREEN_NAME = "screen_name";
        /**
         * 字段名 "screen_name"
         */
        public static final String DESCRIPTION = "description";
        /**
         * 字段名 "profile_image_url"
         */
        public static final String PROFILE_IMAGE_URL = "profile_image_url";
        /**
         * 字段名 "profile_image_path"
         */
        public static final String PROFILE_IMAGE_PATH = "profile_image_path";

	}
	
	public static final class PicTable {
		/**
    	 * 表名 "pic"
    	 */
		public static final String TABLE_NAME = "pic";
		//以下常量分别对应数据表里的字段
        /**
         * 字段名 "pic_path"
         */
        public static final String PIC_PATH = "pic_path";
        /**
         * 字段名 "pic_url"
         */
        public static final String PIC_URL = "pic_url";
        
        /**
         * 字段名 "msg_wid"
         */
        public static final String MSG_WID = "msg_wid";

	}
	
    public static final class MsgTable {
    	        	        
    	/**
    	 * 表名 "msg"
    	 */
    	public static final String TABLE_NAME = "msg";	
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.note";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.note";
        public static final String DEFAULT_SORT_ORDER = "created DESC";//默认排序

        /**
         * 字段名 "content"
         */
        public static final String CONTENT = "content";//以下常量分别对应数据表msgs里的字段
        
        /**
         * 字段名 "wid"
         */
        public static final String WID = "wid";

        /**
         * 字段名 "source"
         */
        public static final String SOURCE = "source";
        
        
        /**
         * 字段名 "pic_path_url"
         */
        public static final String PIC_Path_URL  = "pic_path_url";
        
        /**
         * 字段名 "pic_path_local"
         */
        public static final String PIC_Path_LOCAL  = "pic_path_local";
        
        /**
         * 字段名 "in_reply_to_status_id"
         */
        public static final String IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
        
        /**
         * 字段名 "in_reply_to_user_id"
         */
        public static final String IN_REPLY_TO_USER_ID = "in_reply_to_user_id";
        
        /**
         * 字段名 "in_reply_to_user_name"
         */
        public static final String IN_REPLY_TO_USER_NAME = "in_reply_to_user_name";
        
        /**
         * 字段名 "msg_user_name" 发送消息的用户名(登录名)
         */
        public static final String MSG_USER_NAME = "msg_user_name";
        
        /**
         * 字段名 "msg_user_screen_name" 发送消息的用户名(昵称)
         */
        public static final String MSG_USER_SCREEN_NAME = "msg_user_screen_name";
        
        /**
         * 字段名 "msg_user_wid" 发送消息的用户网络id
         */
        public static final String MSG_USER_WID = "msg_user_wid";


        /**
         * 字段名 "login_user_name" 登录用户名
         */
        public static final String LOGIN_USER_NAME = "login_user_name";
        /**
         * 字段名 "msg_created_time" 网络上消息生成的时间
         */
        public static final String MSG_CREATED_AT  = "msg_created_time";
        /**
         * 字段名 "db_add_time" 消息添加到本地数据库的时间
         */
        public static final String DB_ADD_TIME = "db_add_time";
    }
    
    public static final class DirectMessageTable {
        
    	/**
    	 * 表名 "direct_message"
    	 */
    	public static final String TABLE_NAME = "direct_message";	
        public static final String DEFAULT_SORT_ORDER = "created DESC";//默认排序
        
        /**
         * 字段名 "wid"
         */
        public static final String WID = "wid";

        /**
         * 字段名 "category"
         */
        public static final String CATEGORY  = "category";
                
        /**
         * 字段名 "msg_text"
         */
        public static final String TEXT  = "msg_text";
        /**
         * 字段名 "sender_id"
         */
        public static final String SENDER_ID  = "sender_id";
        /**
         * 字段名 "recipient_id"
         */
        public static final String RECIPIENT_ID  = "recipient_id";
        /**
         * 字段名 "created_at"
         */
        public static final String CREATED_AT  = "created_at";
        /**
         * 字段名 "login_user_name" 登录用户名
         */
        public static final String LOGIN_USER_NAME = "login_user_name";
        /**
         * 字段名 "sender_screen_name"
         */
        public static final String SENDER_SCREEN_NAME  = "sender_screen_name";
        /**
         * 字段名 "recipient_screen_name"
         */
        public static final String RECIPIENT_SCREEN_NAME  = "recipient_screen_name";


        
    }
    
}
