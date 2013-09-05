package com.ncsi.ccpaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: paging
 * 
 */
public class CCPaging extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final String TAB = "\t";
	String status;
	private BufferedReader in;

//	public CCPaging() {
//		super();
//	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String userName =  request.getParameter("USERNAME");
		if (userName==null || !userName.startsWith("OCAUS01") ){
			response.sendRedirect("http://ncssdprd.optus.com.au/CCPaging/");
			return;
		}
		System.out.println("*************************"+userName+"*************************");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		
		
		String mobileno = request.getParameter("mobileNo");
		String message = request.getParameter("message");
		String[] temp = new String[] { mobileno, message };
		PrintWriter out= response.getWriter();
//		System.out.println("*************************"+userType+"*************************");
		
		try {
			InputStream inputStream = getServletContext().getResourceAsStream(
					"WEB-INF/odt.properties");
			Properties properties = new Properties();
			properties.load(inputStream);

			String sServer1 = (String) properties.getProperty("SERVER1");
			String sServer2 = (String) properties.getProperty("SERVER2");
			String sUserid = (String) properties.getProperty("USER");
			String sPassword = (String) properties.getProperty("PASSWORD");
			String sLogFile = (String) properties.getProperty("LOG");
			
			String path = "C:/Program Files/Apache Software Foundation/Tomcat 6.0/webapps/ComandCenterPaging/WEB-INF/CCTelnetIO.log";
			CCTelnetIO telnet = new CCTelnetIO(sServer1, sServer2, path);
			
			String[] strContactNum = temp[0].split(",");
			for (int x = 0; x < strContactNum.length; x++) {
				// System.out.println(strContactNum[x]);
				telnet.writeToLogFile("Contact No : " + strContactNum[x]);
			}
			String sMsg = temp[1];
			 out.println("<html>");
			 out.println("<body>");
			 out.println("<head>");
			 out.println("<title>Comand Center Paging System</title>");
			 out.println("</head>");
			 out.println("<body>");
			 
			telnet.writeToLogFile("SMS msg : " + sMsg);
			//System.out.println("------------------"+sMsg+"------------------");
			 
			 
			if (telnet.isBConnected() == true) {
//			if (telnet.isBConnected() == false ) {
				

				Thread.sleep(500);
				telnet.write("5F"+sUserid+TAB+sPassword);
				//System.out.println("5F"+sUserid+TAB+sPassword);
				
				Thread.sleep(15000);
				telnet.readLine();
				System.out.println("\nAuthenticate with OD");
				
				
				// Submitting Msg
				String sPagerID = "";
				for (int x = 0; x < strContactNum.length; x++) {
					// System.out.println(strContactNum[x]);
					Thread.sleep(5000);
					sPagerID = CCTelnetIO.formatPagerID(strContactNum[x]);
					telnet.write(sPagerID + sMsg  + TAB + sUserid);

				}
				Thread.sleep(8000);
				
				System.out.println("Disconnecting from OD - 5F");
				telnet.write("5F");
				System.out.println("Telnet Disconnect");
				telnet.disconnect();
				status ="You message has been send to contact number at: ";
				request.setAttribute("success", status);
				request.setAttribute("mobileno", mobileno);
				request.setAttribute("message", sMsg);
				RequestDispatcher rd = request.getRequestDispatcher("send_success.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("Unable to contact both OD servers");
				
				status ="ERROR: - Unable to contact both OD servers";
				
				request.setAttribute("error", status);
				RequestDispatcher rd = request.getRequestDispatcher("send_error.jsp");
				rd.forward(request, response);
				
			}
			
			telnet.closeLogFile();
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}