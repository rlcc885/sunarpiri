<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>loginError.jsp</TITLE>
</HEAD>
<BODY>

<br>

<table>
<tr><td align="center" class = "tdtitmens">Pagina default de error</td></tr>
<tr><td align="center" class="tdimparred">
	<br>NICE MESSAGE: <bean:write name="events" property="niceMessage"/>
	<br>OTHER MESSAGE: <bean:write name="events" property="otherMessage"/>
	<br>COMPLETE MESSAGE: <bean:write name="events" property="completeMessage"/>
	<br>USER IDENTIFYING STRING: <bean:write name="events" property="userIdentifyingString"/>
	<br>LEVEL PRINCIPAL: <bean:write name="events" property="levelPrincipal"/>
</td>
</tr>
</table>

Error de sesion. Ingrese de nuevo al sistema
<a href="/iri/Ingreso.do"> click </a>

<pre>
	<bean:write name="events" property="logString"/>
</pre>

</BODY>
</HTML>
