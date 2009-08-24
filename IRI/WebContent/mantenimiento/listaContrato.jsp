<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.mantenimiento.bean.*" %>

<html>
<head>
<title></title>

<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
</head>
<body>
<br>
<table class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt;</FONT>  Mantenimiento de Contratos</td></tr>
</table>
<br>
<form METHOD="POST" action="/iri/MantenimientoContrato.do?state=mantenimiento&P1=4" class="formulario">
<table border="0" width="100%"  class="tablasinestilo">
  <tr>
    <td colspan=3 height="10">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%" height="10"></td>
    <td width="60%" height="10" colspan="3" align=center>
      <h3>Mantenimiento de Versiones de Contratos</h3>
    </td>
    <td width="20%" height="10"></td>
  </tr>
</table>

<table width=100% class = tablasinestilo>  
  <tr> 
    <td width="20%" height="15"></td>
    <td width="20%" align="center" height="15"></td>
    <td width="13%" align="center" height="15">	</td>
    <td width="27%" align="center" height="15">	</td>
    <td width="20%" height="15"><input type="image" src="images/btn_nuevo.gif" border="0" onmouseover="javascript:mensaje_status('Nuevo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
  </tr>
</table>


<!-- ****************LISTA************************* -->
<% java.util.ArrayList arr1 = (java.util.ArrayList) request.getAttribute("arr1");%>
<table border="0" width="100%" class=tablasinestilo>
  <tr>
    <td width="15%" align="center"><b>CODIGO</b></td>
    <td width="23%" align="center"><b>Version</b></td>
    <td width="21%" align="center"><b>Fecha/Hora Creacion</b></td>
    <td width="32%">&nbsp;</td>
  </tr>
<%for (int i = 0; i < arr1.size(); i++)
	{
	VerContratoBean bean1 = new VerContratoBean();
	bean1 = (VerContratoBean) arr1.get(i);
	%>
	<tr>
			<td align= center><%=bean1.getVerContratoId()%></td>
			<td align=center><%=bean1.getVerContrato()%></td>
			<td align=center ><%=bean1.getFechaHoraCreacion()%></td>
			<%if (i==0) {%>	
			<td>
			<font style="size:30">
			<a href="/iri/MantenimientoContrato.do?state=mantenimiento&P1=2&P2=<%=bean1.getVerContratoId()%>">Editar</a>
			</font> 
			</td>
			<%} else {%>
			<td>&nbsp;</td>	
			<%}%>
	</tr>
	<%}%>
</logic:iterate>
</table>
</form>
<BR>

</body>
</html>