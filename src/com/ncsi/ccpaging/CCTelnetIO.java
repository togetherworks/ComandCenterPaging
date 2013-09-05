package com.ncsi.ccpaging;
import org.apache.commons.net.telnet.*;


import java.io.*;
import java.util.*;
import java.text.*;

/**
 * 
 * @author cheng zhang
 * 
 *
 */
public class CCTelnetIO {
	  static final String TAB = "\t";

	  private TelnetClient telnet = new TelnetClient();
	  //private InputStream in;
	  private InputStreamReader inr;
	  private BufferedReader in;
	  private PrintStream out;
	  private PrintWriter pw;
	  private boolean bConnected = false;
	  
	  int iPort = 754;
	  
	  Timer timer;
	  int seconds=60;
	  
	  
	  public CCTelnetIO(String sServer1, String sServer2, String sLogFile ) {
		  try {
				openLogFile(sLogFile);
				
				// Set timeout to 60 secs
				//timer = new Timer();
				//timer.schedule(new RemindTask(), seconds*1000);
				// Connect to the specified server
				
				try
				{
					System.out.println("Contact OD 1.....");
					writeToLogFile("\n\nContact OD 1.....");
					System.out.println(sServer1);
					telnet.connect(sServer1, iPort);
					
					bConnected = true;
				}
				catch ( Exception e)
				{
						 e.printStackTrace();
						 writeToLogFile(e.toString());

						 try
						 {
							System.out.println("Contact OD 2.....");
							writeToLogFile("\n\nContact OD 2.....");
							telnet.connect( sServer2, iPort );
							
							bConnected = true;
						 }
						 catch (Exception e2)
						 {
							writeToLogFile(e2.toString());
							throw new Exception(e2);
						 }
				}

				//System.out.println("Contacted...");
				// Get input and output stream references
				inr = new InputStreamReader(telnet.getInputStream());
				//Cp1252
				//System.out.println("Encoding standard..." + inr.getEncoding());	 
				in = new BufferedReader(inr,1000); 
				out = new PrintStream( telnet.getOutputStream() );

		   }
		   catch( Exception e ) {
				e.printStackTrace();
		   }
		  }
	  
	  public void openLogFile(String sLogFile) {
		  try {
		 		pw= new PrintWriter(new BufferedWriter(new FileWriter(sLogFile,true)));
		  }
		  catch( Exception e ) {
		 	 e.printStackTrace();
		  }
		  }
	  
	  public void writeToLogFile(String sText) {
		  try {
		 		pw.print(printTimeNow("yyyy.MM.dd G 'at' hh:mm:ss z") + " ===> ");
		 		pw.println(sText);
		 		pw.flush();
		  }
		  catch( Exception e ) {
		 	 e.printStackTrace();
		  }
		  }
	  
	  public String printTimeNow (String format) {
		    Date today = new Date();
		    SimpleDateFormat formatter = new SimpleDateFormat(format);
		    String datenewformat = formatter.format(today);
		    return  datenewformat;
		 }

	  
	  
	  public boolean isBConnected() {
		return bConnected;
	  }

	  public void setBConnected(boolean connected) {
		bConnected = connected;
	  }
	
	  public static String formatPagerID(String sHP) {
		if(sHP.length()>=10)
		    return "5IGSM" + TAB + "D " + sHP + " 160"  + TAB;
		else
		    return "5ILink" + TAB + "A " + sHP + " 160"  + TAB;
		 }
	  
	  public void disconnect() {
			  try {
			 		System.out.println("Disconnecting now");
			 		telnet.disconnect();
			 		//timer.cancel(); //Terminate the timer thread
			  }
			  catch( Exception e ) {
			 	 e.printStackTrace();
			  }
	  }
	  public void closeLogFile() {
			  try {
			 		//System.out.println("Close writer");
			 		pw.close();
			  }
			  catch( Exception e ) {
			 	 e.printStackTrace();
			  }
	  }
	  
	  public String readLine() {
		  try {
		 		//System.out.println( "reading from inputstream now....");
		 		//byte[] byLine = new byte[5000];
		 		//in.read (byLine,0,5000);
		 		//String s = new String(byLine, 0, 5000, "ASCII");
		 		char[] byLine = new char[1000];
		 		for(int i=0;i<50;i++) {
		 			if (in.ready() == true){
		 				in.read (byLine,0,1000);
		 				break;
		 			}
		 			//System.out.println("not ready yet");
		 		}
		 		String s = new String(byLine);
		 		System.out.print( s);
		  }
		  catch( Exception e ) {
		 	 e.printStackTrace();
		  }
		    return null;
		  }
	  
	  public void write( String value ) {
		  try {
		 	 out.println( value );
		 	 out.flush();
		 	 System.out.println( value );
		  }
		  catch( Exception e ) {
		 	 e.printStackTrace();
		  }
		  }
	  


	  
}
