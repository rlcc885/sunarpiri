<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>loginError.jsp</TITLE>
</HEAD>
<BODY>

<br>
MENSAJE:<br><br>
<b>
	<bean:write property="mensaje"/>
</b>
<p>
Por favor sirvase logearse de nuevo <a href="<%=request.getContextPath()%>/acceso/displayLogin.html">Pulse aqui.</a>
</BODY>
</HTML>
