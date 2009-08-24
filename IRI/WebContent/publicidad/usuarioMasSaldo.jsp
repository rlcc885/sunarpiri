<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso-bean.tld" prefix="bean"%>
<html>
<head> 
<link rel="stylesheet" href="styles/global.css"><title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
</head>
<body>
 <!-- onLoad="setInterval('location.reload()', 20000);" -->

<form>
  <table border="0" width="100%">
    <tr> 
      <th width="27%">Usuario</th>
      <td><font color="#993300"> 
        <INPUT DISABLED="TRUE" name="usuario" size="10" value="<%=request.getAttribute("usuario")%>">
        </font><font color="#993300">&nbsp; </font></td>
    </tr>
    <tr> 
      <th>Saldo</th>
      <td><font color="#993300"> 
        <input disabled="TRUE" name="saldo" size="5" value="<%=request.getAttribute("saldo")%>">
        </font></td>
    </tr>
  </table>
<hr>
</form>

<%
String xparam = (String) session.getAttribute("MENSAJE_INCOMPLETO");
if (xparam!=null)
	{
%>
<br>
<script LANGUAGE="JavaScript">
	alert("Partida incompleta. No es visible para usuarios externos");
</script>
<% } %>

</body>
</html>
