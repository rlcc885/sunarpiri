<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script language="javascript">
function Regresa()
{ 
	//window.document.location.target="_top";
	//window.location="/iri/acceso/displayLogin.html";
	top.location.href = "/iri/acceso/displayLogin.html"
	//window.open ("/iri/acceso/displayLogin.html", target="_top")
	
}

</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
</head>
<body>
<br>
<table class="titulo">
  <tr>
    <td>Afiliaci&oacute;n </td>
  </tr>
</table>
<br>
<br>
<br>
<form class="formulario">
<table width="100%" border="0" class="tablasinestilo">
  <tr> 
    <th width="50%" colspan="3">LO SENTIMOS...</th>
  </tr>
  <tr> 
    <td width="50%" colspan="3"></td>
  </tr>
  <tr> 
    <td width="50%" colspan="3"> <p align="justify">Lo sentimos, Ud. no ha aceptado 
        los t&eacute;rminos de uso de la Extranet no pudi&eacute;ndose completarse su registro. 
        Pero Ud. puede acceder a nuestros servicios gratuitos de consulta de estado 
        de T&iacute;tulos desde nuestra P&aacute;gina Principal, de todas formas si Ud. desea afiliarse 
        en un futuro podr&aacute; contar con todos los servicios de los usuarios registrados.</td>
  </tr>
  <tr> 
    <td width="50%" colspan="3"></td>
  </tr>
  <tr> 
    <td width="16%"></td>
    <td width="20%"><br>
        <center>
          <input name="B3" type="image" onclick="Regresa()" value="Ir a Home Page" src="../images/btn_regresa.gif" border="0">
        </center>
    </td>
    <td width="14%"></td>
  </tr>
</table>
</body>
</html>