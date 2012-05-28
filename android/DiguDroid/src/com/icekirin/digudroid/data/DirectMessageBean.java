package com.icekirin.digudroid.data;

public class DirectMessageBean {
		
	private String id;
	private int category;	
	private String text;	
	private String sender_id;
	private String recipient_id;
	private Long created_at;	
	private String sender_screen_name;
	private String recipient_screen_name;
	private UserBean sender; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getRecipient_id() {
		return recipient_id;
	}
	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}
	public Long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}
	public String getSender_screen_name() {
		return sender_screen_name;
	}
	public void setSender_screen_name(String sender_screen_name) {
		this.sender_screen_name = sender_screen_name;
	}
	public String getRecipient_screen_name() {
		return recipient_screen_name;
	}
	public void setRecipient_screen_name(String recipient_screen_name) {
		this.recipient_screen_name = recipient_screen_name;
	}
	public UserBean getSender() {
		return sender;
	}
	public void setSender(UserBean sender) {
		this.sender = sender;
	}

}
