<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="JavaScript">
function Imprimir(){
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
function enregistre() {
	if (document.all) {
		var OLECMDID_SAVEAS = 4;
		var OLECMDEXECOPT_DONTPROMPTUSER = 2;
		var OLECMDEXECOPT_PROMPTUSER = 1;
		var WebBrowser = "<OBJECT ID=\"WebBrowser1\" WIDTH=0 HEIGHT=0 CLASSID=\"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\"></OBJECT>";
		document.body.insertAdjacentHTML("beforeEnd", WebBrowser);
		WebBrowser1.ExecWB(OLECMDID_SAVEAS, OLECMDEXECOPT_PROMPTUSER);
		WebBrowser1.outerHTML = "";
	} else {
		alert("Esta accion solo funciona en Internet Explorer");
	}
}
function continuar(){
	document.frm1.action="/iri/Denominacion.do?state=resumen";		
	document.frm1.submit();
}
</script>
</head>
<body ><!--onload="CargaDatos()"-->
<br>
<table width="600" border="0" cellpadding="0" cellspacing="2">
  <tr> 
	<td>
	
        <b>SOLICITUDES<font size="1">>></font><font color="#993300">Solicitud de inscripcion</font> >></b>
    
    
        <b>RESERVA DE PREFERENCIA REGISTRAL <font size="1">>></font><font color="#993300"> Comprobante</font></b>
    
	  
	</td>
  </tr> 
  <tr> 
	<td bgcolor="#000000"><img src="/images/space.gif" width="5" height="1"></td>
  </tr>
  
</table>
<br>

<form name="frm1"  method="post">
<table class=formulario>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="2">
      <img src="images/orlclogo.gif">
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="4"><b>SUPERINTENDENCIA NACIONAL DE REGISTROS PUBLICOS</b></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="4">
        Su pago se ha procesado con &eacute;xito.
    </td>
    <td width="20%" height="14" colspan="2"></td>
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
  <tr>
    <th width="40%" height="14" colspan="2">RUC</th>
    <td width="40%" height="14" colspan="2">20267073580</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">SERVICIO</th>
    <td width="40%" height="14" colspan="2">
        Publicidad Registral en Linea
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">OFICINA</th>
    <td width="40%" height="14" colspan="2">WEB</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  <!--  <tr>
    <th width="40%" height="14" colspan="2"><b>RECIBO No.</b></th>
    <td width="40%" height="14" colspan="2">12345</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>-->
  
  
  <tr>
    <th width="40%" height="14" colspan="2"><b>HOJA PRESENTACION No.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write name="denominacion" property="anio"/>-<bean:write name="denominacion" property="numeroHoja"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  
  <tr>
    <th width="40%" height="14" colspan="2"><b>DESCRIPCION.</b></th>
    <td width="40%" height="14" colspan="2">Solicitud de reserva de Denominacion</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  
  
  
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">CAJERO</th>
    <td width="40%" height="14" colspan="2">WEB - Pago en L&iacute;nea</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  <tr>
    <th width="40%" height="14" colspan="2">FECHA/HORA</th>
    <td width="40%" height="14" colspan="2"><bean:write name="datosPago" property="fechaPago"/>-<bean:write name="datosPago" property="horaPago"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">MONTO PAGADO</th>
    <td width="40%" height="14" colspan="2">S/. <bean:write name="denominacion" property="monto"/>0</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  <tr>
    <th width="40%" height="14" colspan="2">USUARIO ID</th>
    <td width="40%" height="14" colspan="2"><bean:write name="denominacion" property="codigoUsuario"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  <tr>
    <th width="40%" height="14" colspan="2">NOMBRE PRESENTANTE</th>
    <td width="40%" height="14" colspan="2"><bean:write name="presentante" property="apePaterno"/>&nbsp;<bean:write name="presentante" property="apeMaterno"/>&nbsp;<bean:write name="presentante" property="nombre"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  
  <!--  <tr>
    <th width="40%" height="14" colspan="2">CONTRATO</th>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>-->
  
  <tr>
    <th width="40%" height="14" colspan="2">TIPO DE PAGO</th>
    <td width="40%" height="14" colspan="2">EN LINEA - <bean:write name="datosPago" property="descripcionTipoPago"/></td>
    <td width="20%" height="14" colspan="2"><p align="left">&nbsp;</p></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2">PARA: Usuario</td>
    <td width="20%" height="14" colspan="2"><p align="left">&nbsp;</p></td>
    
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
</table>

<br>
<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;"> 
  <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
  </a><br>
  <a href="javascript:continuar();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    <IMG height=25 hspace=4 src="images/btn_continuar.gif" width=83 align=absMiddle vspace=5 border=0>
  </a><br>
  <a href="javascript:enregistre();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    <IMG height=25 hspace=4 src="images/btn_grabar.gif" width=83 align=absMiddle vspace=5 border=0>
  </a>
</div>

  
  <br>
<div id="HOJA3" style="position:absolute; left:485px; top:95px; visibility: visible;">

			  
</div>
</form>
<script>
window.top.frames[0].location.reload();
</script>
</body>
</html>