package servletPackage;


import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();   
	    HttpPost httppost = new HttpPost("http://localhost:8080/alfresco/service/api/upload");
	    httpclient.getCredentialsProvider().setCredentials(
				new AuthScope("localhost", 8080),
				new UsernamePasswordCredentials("admin", "admin"));
	    // һ��ص��ļ�   
	    FileBody bin = new FileBody(new File(request.getParameter("filedata")));
	    // �ಿ�ֵ�ʵ��   
	    MultipartEntity reqEntity = new MultipartEntity();   
	    // ��� 
	    reqEntity.addPart("filedata", bin);   
	    reqEntity.addPart("filename", new StringBody(request.getParameter("filename")));
	    reqEntity.addPart("siteid", new StringBody(request.getParameter("siteid")));  
	    reqEntity.addPart("containerid", new StringBody(request.getParameter("containerid")));  
	    reqEntity.addPart("uploaddirectory", new StringBody(request.getParameter("uploaddirectory")));  
	    reqEntity.addPart("contenttype", new StringBody(request.getParameter("contenttype")));  
	    // ����   
	    httppost.setEntity(reqEntity);   
	    System.out.println("申请的url: " + httppost.getRequestLine());   
	    HttpResponse resp = httpclient.execute(httppost);   
	    HttpEntity resEntity = resp.getEntity();   
	    System.out.println("----------------------------------------");   
	    System.out.println(resp.getStatusLine());   
	    if (resEntity != null) {   
	      System.out.println("返回结果的长度: " + resEntity.getContentLength()+"\ncontentType:"+resEntity.getContentType());   
	    }
	    String result=null;
	    
	    if (resEntity != null) {   
	    	byte[] a=new byte[10240];
	    	resEntity.getContent().read(a);
	      System.out.println(new String(a));
	      result=new String(a);
	      request.setAttribute("result", result);
	    }
	    httpclient.getConnectionManager().shutdown();
	    RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/download.jsp");
	    dispatcher.forward(request, response);
	}
}
