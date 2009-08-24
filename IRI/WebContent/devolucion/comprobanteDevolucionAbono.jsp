<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="JavaScript">
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
function Siguiente()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Devolucion.do?state=muestraConstanciaSegEstado";
  document.form1.submit();
}
function Regresar()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Publicidad.do?state=solicitarFormulario";
  document.form1.submit();
}
</script>
</head>
<body ><!--onload="CargaDatos()"-->

<br>

<table width="600" border="0" cellpadding="0" cellspacing="2">
  <tr> 
	<td>
	    <b>PUBLICIDAD CERTIFICADA <font size="1">>></font><font color="#993300"> Comprobante</font></b>
    </td>
  </tr> 
  <tr> 
	<td bgcolor="#000000"><img src="<%=request.getContextPath()%>/images/space.gif" width="5" height="1"></td>
  </tr>
</table>
<br>

<form name="form1">
<table class=formulario>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="2">
      <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="4"><b>SUPERINTENDENCIA NACIONAL DE REGISTROS PUBLICOS</b></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <!--<td width="10%" height="14" colspan="1"></td>-->
  
    <td width="70%" height="14" colspan="4">Por el concepto de Devoluci&oacute;n a su cuenta, debido a la declaratoria de improcedente de la solicitud <%=request.getAttribute("solicitud") %>&nbsp; Fecha:<%=request.getAttribute("fecha") %>   , se abon&oacute; a <bean:write name="entidad" /> el monto de S./ <bean:write name="monto" />  Nuevos Soles</td>
  </tr>

  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <br>
<div id="HOJA2" style="position:absolute; left:480px; top:65px; visibility: visible;"> 
  	  <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	    <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
	  </a>
	  <a href="javascript:Regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	  <IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0>
	  </a>
  	
</div>

  
  <br>
<div id="HOJA3" style="position:absolute; left:485px; top:95px; visibility: visible;">

</div>

<script>
window.top.frames[0].location.reload();
</script>
</body>
</html>