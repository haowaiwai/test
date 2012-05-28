package cn.icekirin.digudroid.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import cn.icekirin.digudroid.data.F;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

public class NetUtil {

	/**
	 * 处理httpResponse信息,返回String
	 * 
	 * @param httpEntity 
	 * @return String
	 */
	protected static String retrieveInputStream(HttpEntity httpEntity) {
		Long l = httpEntity.getContentLength();
		int length = (int) httpEntity.getContentLength();
		if (length < 0) {
			U.dout("ee, httpEntity length <0");
			length = 10000;
		}

		StringBuffer stringBuffer = new StringBuffer(length);
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(httpEntity.getContent(), HTTP.UTF_8);
			char buffer[] = new char[length];
			int count;
			while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}

		} catch (UnsupportedEncodingException e) {
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		}
		//U.dout("retrieveInputStream=" + stringBuffer);
		return stringBuffer.toString();
	}

	/**
	 * 向服务器发送post请求
	 * @param url 服务器地址
	 * @param hmap 参数
	 * @throws Exception
	 */
	public static HttpResponse SendPostRequest(String url, HashMap hmap) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		//httpost.addHeader("Authorization", "Basic " + getCredentials(userName, passWord));
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		for (Iterator iter = hmap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			Object strKey = element.getKey(); //键值
			Object strValue = element.getValue(); //value值
			nvps.add(new BasicNameValuePair("" + strKey, "" + strValue));
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse response = httpclient.execute(httpost);
		//HttpEntity entity = response.getEntity();

		//U.dout(retrieveInputStream(entity)+TimeUtil.getNowOfDateByFormat("yyyy-MM-dd hh:mm:ss EE"));
		//if (entity != null) {
		//	entity.consumeContent();
		//}		
		httpclient.getConnectionManager().shutdown();
		return response;

	}

	public static String SendPostRequest(String url, UrlEncodedFormEntity formParams, String userName, String passWord)
			throws Exception {
		U.dout("SendPostRequest url" + url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		httpost.addHeader("Authorization", "Basic " + getCredentials(userName, passWord));
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (formParams != null) {
			httpost.setEntity(formParams);
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse response = httpclient.execute(httpost);
		int statusCode = response.getStatusLine().getStatusCode();
		U.dout("statusCode " + statusCode);
		HttpEntity entity = response.getEntity();
		String rs = retrieveInputStream(entity);
		httpclient.getConnectionManager().shutdown();

		return rs;

	}

	/**
	 * 向服务器发送post请求，加参数(走HttpURLConnection)
	 * @param urlString
	 * @param name
	 * @param password
	 * @param params
	 * @return
	 */
	public static String doRequest(String urlString, String name, String password, HashMap<String, String> params) {
		try {
			U.dout("doRequest" + urlString);
			URL url = new URL(urlString);
			String userPassword = name + ":" + password;
			String encoding = Base64.encodeBytes((userPassword).getBytes());
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.setRequestProperty("Authorization", "Basic " + encoding);
			uc.setRequestProperty("User-Agent", "Mozilla/5.0");
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setRequestMethod("POST");

			if (params != null && !params.isEmpty()) {
				StringBuffer buf = new StringBuffer();
				for (String key : params.keySet()) {
					String str = params.get(key);//中文问题 用URLEncoder
					buf.append("&").append(key).append("=").append(URLEncoder.encode(str));
				}
				buf.deleteCharAt(0);
				uc.getOutputStream().write(buf.toString().getBytes("UTF-8"));
				uc.getOutputStream().close();
			}

			InputStream content = uc.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content, "UTF-8"));
			String line = in.readLine();
			in.close();
			return line.trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";

	}

	/**
	 * 向服务器发送get请求
	 * @param url 服务器地址
	 * @throws Exception
	 */
	public static String SendGetRequest(String url) throws Exception {
		//U.dout("SendGetRequest="+url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String rs = retrieveInputStream(entity);
		if (entity != null) {
			entity.consumeContent();
		}
		httpclient.getConnectionManager().shutdown();
		return rs;

	}

	public static String SendGetRequest(String url, String userName, String passWord) throws Exception {
		U.dout("SendGetRequest=" + url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//		httpclient.getCredentialsProvider().setCredentials(
		//    			new AuthScope(null, -1),
		//    			new UsernamePasswordCredentials(userName, passWord));		
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("Authorization", "Basic " + getCredentials(userName, passWord));
		//httpget.setHeader("User-Agent", "Mozilla/4.5");

		//添加用户密码验证信息

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String rs = retrieveInputStream(entity);
		U.dout("retrieveInputStream,size="+rs.length());
		if (entity != null) {
			entity.consumeContent();
		}
		httpclient.getConnectionManager().shutdown();
		return rs;

	}

	/**
	 * Get the HTTP digest摘要 authentication证明. Uses Base64 to encode credentials资格审查.
	 * 
	 * @return String
	 */
	protected static String getCredentials(String userName, String passWord) {
		return new String(Base64.encodeBytes((userName + ":" + passWord).getBytes()));
	}

	public static void loadNetPic(ImageView view, String fileUrl) throws Exception {

		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			U.dout("create conn for " + fileUrl);
			conn.setDoInput(true);
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			view.setImageBitmap(bitmap);
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 加载网络头像，如果本地有缓存，从缓存中取
	 * @param view
	 * @param fileUrl
	 * @throws Exception
	 */
	public static void loadNetPicUseCache(ImageView view, String fileUrl) throws Exception {

		HashMap userHeadMap = U.getCacheData().userHeadMap;
		if (U.getCacheData().userHeadMap.containsKey(fileUrl)) {
			//U.dout("oldbitmap");
			Bitmap oldbitmap = (Bitmap) userHeadMap.get(fileUrl);
			view.setImageBitmap(oldbitmap);
			return;
		}

		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			//U.dout("create conn for "+fileUrl);
			conn.setDoInput(true);
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			view.setImageBitmap(bitmap);
			userHeadMap.put(fileUrl, bitmap);
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
	
	/**
	 * 利用缓存加载图片view 若无从本地文件加载
	 * @param view
	 * @param localPath
	 * @throws Exception
	 */
	public static void loadLocalImage(ImageView view, String localPath, HashMap cacheMap) throws Exception{
		
		view.setVisibility(View.VISIBLE);
		Bitmap bitmap = (Bitmap)cacheMap.get(localPath);
		if(bitmap!=null){
			//U.dout("use cache bitmap for "+localPath);
			view.setImageBitmap(bitmap);
		}else{
//			U.dout("no cache for "+localPath);
			Bitmap bm = BitmapFactory.decodeFile(localPath);
			view.setImageBitmap(bm);
			cacheMap.put(localPath, bm);
		}
					
	}

	/**
	 * 下载用户头像，并加入缓存
	 * @param fileUrl
	 * @throws Exception
	 */
	public static void downLoadUserHead(String fileUrl) throws Exception {		
		String path = F.USER_HEAD_PATH;
		String str[] = fileUrl.split("/");
		String fileName = str[str.length - 1];
		
		File file = new File(path +"/"+ fileName);		
		if(file.exists()){
			return;			
		}
		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int urlFile_length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			file = new File(path +"/"+ fileName);
			FileOutputStream fops = new FileOutputStream(file);
			int k = 0;
			while (k > -1) {
				k = is.read(buffer);
				if (k > 0) {
					fops.write(buffer, 0, k);
				}
			}
			fops.flush();
			fops.close();
			is.close();
			conn.disconnect();
			//加入缓存名单中
			Bitmap bm = BitmapFactory.decodeFile(file.getPath());
			U.getCacheData().userHeadMap.put(fileName, bm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 更新用户头像，并加入缓存
	 * @param fileUrl
	 * @throws Exception
	 */
	public static void updateUserHead(String fileUrl) throws Exception {
		U.dout("update the file url ="+fileUrl);
		String path = F.USER_HEAD_PATH;
		String str[] = fileUrl.split("/");
		String fileName = str[str.length - 1];
		
		File file = new File(path +"/"+ fileName);
		long localFile_length = file.length();
		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int urlFile_length = conn.getContentLength();						
			
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			file = new File(path +"/"+ fileName);
			FileOutputStream fops = new FileOutputStream(file);
			int k = 0;
			while (k > -1) {
				k = is.read(buffer);
				if (k > 0) {
					fops.write(buffer, 0, k);
				}
			}
			fops.flush();
			fops.close();
			is.close();			
			conn.disconnect();
			//加入缓存名单中
			Bitmap bm = BitmapFactory.decodeFile(file.getPath());
			U.getCacheData().userHeadMap.put(fileName, bm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
	
	/**
	 * 返回大头像的url
	 * @param imgUrl
	 */
	public static String getMiddleHeadUrl(String imgUrl){
		String rs = "";
		rs = imgUrl.replace("24x24", "48x48");
		return rs;
	}
	
	/**
	 * 返回大头像的url
	 * @param imgUrl
	 */
	public static String getBigHeadUrl(String imgUrl){
		String rs = "";
		rs = imgUrl.replace("24x24", "128x128");
		return rs;
	}
	
	/**
	 * 返回大图片的url
	 * @param imgUrl
	 */
	public static String getBigImgUrl(String imgUrl){
		String rs = "";
		rs = imgUrl.replace("100x75", "640x480");
		return rs;
	}
	/**
	 * 下载图片，并加入缓存
	 * @param fileUrl
	 * @throws Exception
	 */
	public static void downLoadImg(String fileUrl) throws Exception {
		
		String path = F.IMG_PATH;
		File PATH = new File(path);
		if (!PATH.exists()) {
			U.dout("mkdir, " + path);
			PATH.mkdirs();
		}
		String str[] = fileUrl.split("/");
		String fileName = str[str.length - 1];
		//U.dout("fileName=" + fileName);
		File file = new File(path +"/"+ fileName);		
		//如果已经有就不下载了
		if(file.exists()){ 
			//U.dout("UseLocal Img fileUrl=" + file.getPath());
			return;
		}
		U.dout("downLoadImg fileUrl=" + fileUrl);
		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			file = new File(path +"/"+ fileName);
			FileOutputStream fops = new FileOutputStream(file);
			int k = 0;
			while (k > -1) {
				k = is.read(buffer);
				if (k > 0) {
					fops.write(buffer, 0, k);
				}
			}
			fops.flush();
			fops.close();
			is.close();
			conn.disconnect();
			//加入缓存名单中
			Bitmap bm = BitmapFactory.decodeFile(file.getPath());
			U.getCacheData().imgMap.put(fileName, bm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

}
