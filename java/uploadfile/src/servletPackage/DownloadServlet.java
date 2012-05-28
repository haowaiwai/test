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
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String nodeRef = request.getParameter("nodeRef");
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope("localhost", 8080),
				new UsernamePasswordCredentials("admin", "admin"));
		String url = "http://localhost:8080/alfresco/service/api/node/";

		url += nodeRef;
		url += "/contentContentStreamFilename";
		HttpGet httpget = new HttpGet(url);

		System.out.println("executing request" + httpget.getRequestLine());
		HttpResponse resp = httpclient.execute(httpget);
		HttpEntity entity = resp.getEntity();

		System.out.println("----------------------------------------");
		System.out.println(resp.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: "
					+ entity.getContentLength());
		}
		String result = null;
		if (entity != null) {
			byte[] a = new byte[10240];
			entity.getContent().read(a);
			result = new String(a);
			System.out.println("result length:"+result.trim().length());
			System.out.println("-------------------------------------------------");
			entity.consumeContent();
		}

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
		request.setAttribute("result", result);
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
