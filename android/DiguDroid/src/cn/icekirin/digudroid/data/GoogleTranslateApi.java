package cn.icekirin.digudroid.data;

import org.json.JSONObject;

import cn.icekirin.digudroid.util.NetUtil;

public class GoogleTranslateApi {
	private static final String BASE_URL = "http://ajax.googleapis.com/ajax/services/language/translate?v=1.0";
	private static final String EN2CN_URL = BASE_URL + "&langpair=en%7Czh-CN";
	private static final String CN2EN_URL = BASE_URL + "&langpair=zh-CN%7Cen";
	
	public static String getStringEN2CN(String eng){
		String rs = "";
		String str = EN2CN_URL + "&q=" + eng;
		try {
			str = NetUtil.SendGetRequest(str);
			JSONObject jo = new JSONObject(str);
			if(!jo.getString("responseStatus").equals("200")){
				rs = "error";
			}else{
				rs = jo.getJSONObject("responseData").getString("translatedText");				 
			}
			
		} catch (Exception e) {
		}
		return rs;
	}
	
	public static String getStringCN2EN(String eng){
		String rs = "";
		String str = CN2EN_URL + "&q=" + eng;
		try {
			str = NetUtil.SendGetRequest(str);
			JSONObject jo = new JSONObject(str);
			if(!jo.getString("responseStatus").equals("200")){
				rs = "error";
			}else{
				rs = jo.getJSONObject("responseData").getString("translatedText");				 
			}
			
		} catch (Exception e) {
		}
		return rs;
	}
	
}
