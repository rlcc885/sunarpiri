<%@ page contentType="text/html;charset=ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.caja.*" %>
<HTML>
<head>
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
</head>
<script>
function Aperturar(){
var p = confirm("¿Esta seguro que desea Aperturar la caja "+"<%=request.getAttribute("caja")%>"+"?");
	if (p == true)
	document.frm1.action="/iri/AperturaCajero.do?state=aperturaCaja";
	document.frm1.submit();

}
</script>
<table width="100%"  class="titulo" cellspacing="0">
  <br>
  <tr>
	<td>
		<FONT COLOR="black">CAJA <font size="1">&gt;&gt;</font></FONT><font color="900000">Aperturar Cajas</FONT>
	</td>
  </tr>
</table>
<br>
<BODY>
<form name="frm1" method="POST" class="formulario">


<TABLE>
	<tr>
		<td>Zona</td>
		<%if (request.getAttribute("nombreZona")!=null) %>
		<td><div align="left"><%=request.getAttribute("nombreZona") %></div></td>
	</tr>
	<tr>
		<td>Fecha</td>
		<% if (request.getAttribute("fecha")!=null) %>
		<td><%=request.getAttribute("fecha")%></td>
	</tr>
	<tr>
		<td>Edificio</td>
		<%  if (request.getAttribute("edificio")!=null) %>
		<td><%=request.getAttribute("edificio") %></td>
	</tr>
	<tr>
		<td>Cajero</td>
		<%if (request.getAttribute("caja")!=null) %>
		<td><%=request.getAttribute("caja") %></td>
	</tr>
	<tr>
	<td colspan="2" width="581"></td>
	</tr>
	<tr>
	<td colspan="6"><div align="center"><A href="javascript:Aperturar();"
			onmouseover="javascript:mensaje_status('Aperturar');return true;"
			onmouseout="javascript:mensaje_status(' ');return true;"><IMG
			border="0" src="images/btn_aperturar.gif" ></A></div></td>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>
