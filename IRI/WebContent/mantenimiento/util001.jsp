<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean" %>

<HTML>

<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<TITLE>Eliminar Persona</TITLE>

</HEAD>

<BODY>
<pre>
<%
EliminaPersona ep = new EliminaPersona();
ep.setUsuario((UsuarioBean) session.getAttribute(Constantes.SESSION_DATA));
ep.setOut_jsp(out);
ep.ejecuta();
ep.setIP(request.getRemoteAddr());
%>
</pre>
</BODY>
</HTML>
