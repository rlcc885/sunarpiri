<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<TITLE>loginError.jsp</TITLE>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
<BODY>
<table class=formulario>
  <tr>
	<td><center><br><h3><%=request.getAttribute("mensaje")%></h3><br></center></td>
  </tr>
  <tr>
	<td><center>Presione en regresar para identificarse ... </center></td>
  </tr>
  <tr>
	<td><center><a href="javascript:window.open('acceso/displayLogin.html',target='_top')"><img src='images/btn_regresa.gif'></a></center></td>
  </tr>
</table>
<br>
</BODY>
</HTML>
