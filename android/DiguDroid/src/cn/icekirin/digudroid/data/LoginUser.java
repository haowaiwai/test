package cn.icekirin.digudroid.data;

import cn.icekirin.digudroid.util.U;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginUser {
	Context ctx;
	public String name;
	public String password;
	SharedPreferences sp;
	public LoginUser(Context ctx){
		this.ctx = ctx;
		sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		this.name = sp.getString("Last_name", null);
		this.password = sp.getString("Last_password", null);
	}
	
	public boolean verify(){
		DiguApi api = new DiguApi();
		if(api.verify(name, password)){ 
			F.userName = name;
			F.passWord = password;
			SharedPreferences.Editor editor = sp.edit();			
			editor.putString("Last_name", F.userName);
			editor.putString("Last_password", F.passWord);
			editor.commit();
			U.dout("login suess"+F.userName +F.passWord);
			return true;
		}
		U.dout("login false"+F.userName +F.passWord);
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
