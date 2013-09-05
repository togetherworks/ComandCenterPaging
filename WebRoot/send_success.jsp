<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<style type="text/css">
<!--
.style1 {
	font-family: Arial;
	color: #FF0000; 
	font-size: 14px;
	font-weight: bold; 
}
.style2 {
	font-family: Arial;
	color: <?php echo "$Status_Colour"; ?>; 
	font-size: 14px;
	font-weight: bold; 
}
.style5 {
	font-family: Arial;
	font-size: 12px;
}
.style6 {
    font-size: 12px; 
	color: #000040; 
	font-family: Arial;
}	
.style7 {
	color: #FF0000;
}
.style15 {
	font-family: 
	font-family: Arial;
}
.style19 {
	font-family: "Century Gothic"; 
	font-size: 14px; 
	font-weight: bold; 
	color: #003F5E; 
}
.style20 {
	color: #004F4F; 
	font-weight: bold; 
	font-size: 12px;
}
.style21 {font-size: 14px
}
.style22 {
	font-family: "Century Gothic"; 
	font-size: 14px; 
	font-weight: bold; 
	color: #003F5E; 
}

-->
</style>
	
  </head>
  
 <body>

<div align="center">


<br><br>
  <div align="center">
    <table width="501" border="0" align="center" cellpadding="2" cellspacing="0">
      
</table>
<div align="center"></div>
<p align="center"><span class="style1">Command Center Paging System</span><br>
<p align="center"><span class="style1">This site can only be viewed and used by Command Center.</span><br>
  <br>
  <br>
  <hr>
  

<form name="Paging"  method="get" action="http://ncssdprd.optus.com.au/CCPaging/">
<table width="400" border="0" align="center" cellpadding="3" cellspacing="0">
    <tr>
      <td><div align="left"><span class="style22">
	      <%=request.getAttribute("success") %>
      </span></div></td>
    </tr>
    <tr>
      <td><div align="left"><span class="style22">
      	 <ul><li> <%=request.getAttribute("mobileno") %></li></ul>
      </span></div></td>
    </tr>
     <tr>
	      <td><div align="left"><span class="style22">
	     	 SMS Message:
	      </span></div></td>
     </tr>
     <tr>
       <td><div align="left"><span class="style22">
    		  <ul><li><%=request.getAttribute("message") %></li></ul>
        </span></div></td>
  	</tr>	
 	
	<tr><td><input type="submit" align="left" value="Return"></td></tr>
  
  </table>
</form>
	


</body>
</html>
