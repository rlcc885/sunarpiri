<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
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
	document.frm1.action="/iri/DenominacionIRI.do?state=resumen";		
	document.frm1.submit();
}
</script>
</head>
<body >
<div id="maincontent">
		<div class="innertube">
		
		<table width="600px"><tr><td>
		
        <b><font color="#949400">SOLICITUDES</font><font size="1">&gt;&gt;</font><font color="#949400">Solicitud de inscripcion</font>&gt;&gt;</b>
    
    
        <b>RESERVA DE PREFERENCIA REGISTRAL <font size="1">&gt;&gt;</font><font color="#949400"> Comprobante</font></b>
    
	  


<form name="frm1"  method="post">
<table class="punteadoTablaTop">
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="2">&nbsp;
      <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
    </td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
     <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="4"><b>SUPERINTENDENCIA NACIONAL DE REGISTROS PUBLICOS</b></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>

  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="4">
        Su pago se ha procesado con &eacute;xito.
    </td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"><b>RUC</b></td>
    <td width="40%" height="14" colspan="2">20267073580</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"><b>SERVICIO</b></td>
    <td width="40%" height="14" colspan="2">
        Publicidad Registral en Linea
    </td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"><b>OFICINA</b></td>
    <td width="40%" height="14" colspan="2">WEB</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  

  
  
  <tr>
    <td width="40%" height="14" colspan="2"><b>HOJA PRESENTACION No.</b></td>
    <td width="40%" height="14" colspan="2"><bean:write name="denominacion" property="anio"/>-<bean:write name="denominacion" property="numeroHoja"/></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  
  
  <tr>
    <td width="40%" height="14" colspan="2"><b>DESCRIPCION.</b></td>
    <td width="40%" height="14" colspan="2">Solicitud de reserva de Denominacion</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  
  
  
  
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"><b>CAJERO</b></td>
    <td width="40%" height="14" colspan="2">WEB - Pago en L&iacute;nea</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  
  <tr>
    <td width="40%" height="14" colspan="2"><b>FECHA/HORA</b></td>
    <td width="40%" height="14" colspan="2"><bean:write name="datosPago" property="fechaPago"/>-<bean:write name="datosPago" property="horaPago"/></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"><b>MONTO PAGADO</b></td>
    <td width="40%" height="14" colspan="2">S/. <bean:write name="denominacion" property="monto"/>0</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  
  <tr>
    <td width="40%" height="14" colspan="2"><b>USUARIO ID</b></td>
    <td width="40%" height="14" colspan="2"><bean:write name="denominacion" property="codigoUsuario"/></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  
  <tr>
    <td width="40%" height="14" colspan="2"><b>NOMBRE PRESENTANTE</b></td>
    <td width="40%" height="14" colspan="2"><bean:write name="presentante" property="apePaterno"/>&nbsp;<bean:write name="presentante" property="apeMaterno"/>&nbsp;<bean:write name="presentante" property="nombre"/></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  

  <tr>
    <td width="40%" height="14" colspan="2"><b>TIPO DE PAGO</b></td>
    <td width="40%" height="14" colspan="2">EN LINEA - <bean:write name="datosPago" property="descripcionTipoPago"/></td>
    <td width="20%" height="14" colspan="2"><p align="left">&nbsp;</p></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2"><b>PARA:</b> Usuario</td>
    <td width="20%" height="14" colspan="2">&nbsp;<p align="left">&nbsp;</p></td>
    
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
</table>

</form>

<br>
<br>
<div id="HOJA3" style="position:absolute; left:1px; top:1px; visibility: visible;">
<br>
<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;"> 
   <INPUT class="formbutton" value="Imprimir" type="button" onclick="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
<br/><br/>
  <INPUT class="formbutton" value="Continuar" type="button" onclick="javascript:continuar();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
    <br/><br/>
      <INPUT class="formbutton" value="Grabar" type="button" onclick="javascript:enregistre();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
      
  
</div></div>
<script>
window.top.frames[0].location.reload();
</script>

</td></tr></table>

</div></div>

</body>
</html>