package servletPackage;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "http://localhost:8080/alfresco/service/api/search/keyword.html?q=";
		String queryItem = request.getParameter("queryItem");
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope("localhost", 8080),
				new UsernamePasswordCredentials("admin", "admin"));
		url += queryItem;
		httpget = new HttpGet(url);
		System.out.println("requestLine:" + httpget.getRequestLine());
		HttpResponse resp = httpclient.execute(httpget);

		HttpEntity entity = resp.getEntity();

		if (entity != null) {
			System.out.println("contentLength:" + entity.getContentLength());
			System.out.println("contentType:" + entity.getContentType());
			byte[] a = new byte[10240];
			entity.getContent().read(a);
			System.out.println("content:\n" + new String(a));
			request.setAttribute("result", new String(a));
			entity.consumeContent();
		}

		httpclient.getConnectionManager().shutdown();
		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/success.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
