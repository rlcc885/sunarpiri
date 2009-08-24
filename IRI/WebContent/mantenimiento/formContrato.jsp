<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.mantenimiento.bean.*" %>

<html>
<head><title></title>
<LINK href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">
function validarformulario()
{
	if(esVacio(document.form1.txt1.value)){
			alert("Por favor ingrese correctamente la versión del Contrato");
			document.form1.txt1.focus();
			return false;	
	}
	return true;
}
function Regresar()
{
	history.back();
	//document.form1.method="post";
	//document.form1.action="/iri/MantenimientoContrato.do";	
	//document.form1.submit();
}
function Enviar()
{
	if(validarformulario()){
	//location.href="/iri/Publicidad.do";
	document.form1.method="post";
	document.form1.action="/iri/MantenimientoContrato.do?state=mantenimiento";	
	document.form1.submit();
	return true;	
	}else{
	return false;
	}
}
</script>


<META name="GENERATOR" content="IBM WebSphere Studio">
</head>



<%
String x = (String) request.getAttribute("modo");
int modo = Integer.parseInt(x);
String titulo = "";

switch (modo)
{
	case 20:
		titulo = "Modificación de Versión";
		break;
	case 40:
		titulo = "Nueva Versión de Contrato";
		break;
}//switch

VerContratoBean bean1 = (VerContratoBean) request.getAttribute("bean1");

%>


<body>

<br>
<table class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt;</FONT>  Mantenimiento de Contratos</td></tr>
</table>
<br>
<br>
<form name="form1"    class="formulario">
<input type="hidden" name="P1" value="<%=request.getAttribute("modo")%>">
<table border="0" width="100%" height="52" class="tablasinestilo">
  <tr>
    <td width="100%" height="17" colspan="3">
      <h3 align="center"><%=titulo%></h3>
    </td>
  </tr>
</table>
<table border="0" width="100%" class=tablasinestilo>
  <tr>
	<td width="50" align="center"></td>
    <td width="150" align="center"><b>C&Oacute;DIGO</b></td>
    <td width="200" align="center"><b>Versi&oacute;n</b></td>
    <td width="150" align="center"><b>Fecha/Hora Creaci&oacute;n</b></td>
	<td width="50" align="center"></td>    
  </tr>
  <tr>
	<td width="50" align="center"></td>  
    <td align=center><%=bean1.getVerContratoId()%></td>
    <td align=center><input type="text" name="txt1" value="<%=bean1.getVerContrato()%>"></td>
    <td align=center><%=bean1.getFechaHoraCreacion()%></td>
	<td width="50" align="center"></td>    
  </tr>
</table>
<BR>
<center>
<a href="javascript:Regresar();"><img src="images/btn_regresa.gif"  border=0 onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
<input type="image" src="images/btn_aceptar.gif" border="0" onclick="javascript:return Enviar();" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"> 
</center>
<input type="hidden" name="txt0" value="<%=bean1.getVerContratoId()%>">
</form>
</body>
</html>