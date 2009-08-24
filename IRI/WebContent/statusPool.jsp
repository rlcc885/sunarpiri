<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<TITLE>Gracias</TITLE>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">

</HEAD>
<BODY>
Pool de Conexiones a DB
<br> 
<pre>
DBConnectionFactory = <%=
	gob.pe.sunarp.extranet.pool.DBConnectionFactory.getInstance().getStatus()
%>
DBConnectionFactoryV = <%=
	gob.pe.sunarp.extranet.pool.DBConnectionFactoryV.getInstance().getStatus()
%>
</pre>
<br>
Pool de Conexiones a CM
<br> 
<pre>
CMConnectionFactory = <%=gob.pe.sunarp.extranet.common.cm.CMConnectionFactory.getInstance().getStatus()%>
</pre>
<br>
Pool de Threads
<br>
<pre>
THREADS:
<BR>
<%			Thread[] at = new Thread[5000];
			int nt = Thread.enumerate(at);
			for (int i = 0; i < nt; i++) {
				%>
				<BR><%=at[i].getName()%>
				<%
			}
%>
</pre>
</BODY>
</HTML>
