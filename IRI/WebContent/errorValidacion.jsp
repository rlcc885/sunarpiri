<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%
String mensaje="No se pudo completar el proceso";
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
	mensaje = validacionException.getMensaje();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<SCRIPT language="JavaScript">
function doCancel()
{
	history.back(1);
}
</script>


<title>Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link href="styles/global.css" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<table class="titulo">
  <tr> 
    <td>&nbsp;</td>
  </tr>
</table>
<br>
<br>
<table class="formulario">
  <tr> 
    <td ><div align="left"><strong><font size="3">ERROR:</font> </strong></div></td>
  </tr>
  <tr>
    <td ><%=mensaje%></td>
  </tr>

</table>
<br>
<table >
  <tr>
    <td width="591"><div align="center"><a href="javascript:history.back();"><img src="images/btn_regresa.gif" border=0></a></div></td>
  </tr>
</table>
<br>
<br>
</body>
</html>



