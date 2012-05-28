

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class hello
 */
public class hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		java.io.File objFile = new java.io.File("g:\\1.txt");
		try{
		       FileInputStream fis = new FileInputStream(objFile);
		       BufferedReader d = new BufferedReader(new InputStreamReader(fis));
		       while(true)
		       {
		    	   String line = d.readLine();
		    	   if(line == null)
		    	   {
		    		   break;
		    	   }
		    	   response.getWriter().print(line);
		       }
		   }
		catch(Exception e)
		{
		 e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int len = request.getContentLength();
		if (len > 0)
		{
			char[] readerBuffer = new char[len];
			BufferedReader reader = request.getReader();
			int i;
			StringBuffer ss = new StringBuffer();
			while ((i = reader.read(readerBuffer)) != -1)
			{
				ss.append(readerBuffer, 0, i);
			}
			System.out.println((new Date()).toString() + ss);
			reader.close();
			response.getWriter().print("OK");
		} else
		{
			System.err.println("len < 0:" + len);
		}
		response.getWriter().print("hello");
	}

}
