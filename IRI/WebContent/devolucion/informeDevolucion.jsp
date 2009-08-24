<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
function Siguiente()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Devolucion.do?state=reimprimeRecibo";
  document.form1.submit();
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";	
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body >

<form name="form1">
<table class=formularioimpresion>
  <tr>
    <td width="10%" height="14"></td>
    <td width="25%" height="14"></td>
    <td width="40%" height="14"></td>
    <td width="15%" height="14" ></td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="90%" height="14" colspan="4">
      <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="100%" height="50" colspan="5"></td>
  </tr>
  <tr>
   <td width="10%" height="14" ></td>
    <td width="90%" height="14"colspan="5" align="left" class="textoSolicitud"> 
    	<bean:write name="fechaHoy"/> 
    </td>
   
  </tr>
  <tr>
    <td width="100%" height="20" colspan="5"></td>
  </tr>
 
  
  
 
   <tr>
    <td width="10%" height="14" ></td>
    <td width="90%" height="14" colspan="4" class="textoSolicitud">
	  </td>
  </tr>
  <tr>
    <td width="100%" height="20" colspan="5"></td>
  </tr>
  <tr>
    <td width="10%" height="12" ></td>
    <td width="90%" height="20" colspan="4" class="textoSolicitud"><b>Informe
    Nº <bean:write name="solDev" property="numInforme"/></b><br>
     <b>Sr. Ing.</b><br>
    <b>OSWALDO FLORES BENAVIDES</b><br>
    <b>Gerente de Administraci&oacute;n y Finanzas</b><br>
     Presente.-</td>
  </tr> 
  <tr>
    <td width="100%" height="20" colspan="5"></td>
  </tr>
  <tr>
    <td width="10%" height="12" ></td>
    <td width="90%" height="20" colspan="3" class="textoSolicitud"></td>
  </tr>
  
   <tr>
    <td width="100%" height="20" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ></td>
    <td width="80%" height="14" colspan="3">
    <p align="justify" class="textoSolicitud">
    Es grato dirigirme a usted, a fin de informale que habiendo sido previamente verificado por esta Sub-Gerencia y en m&eacute;rito al Oficio Circular Nº. 457-2003-SUNARP/GL-SG (12.06.2003),
    alcanzo al presente la(s) hoja(s) de tr&aacute;mite  <bean:write name="solDev" property="anoHojaTramite"/>-<bean:write name="solDev" property="numHojaTramite"/> (<bean:write name="solDev" property="fechaTramite"/>), respectivamente sobre devoluci&oacute;n de Derechos Registrales correspondiente al
    presente ejercicio para su aprobaci&oacute;n y proyecto de Resoluci&oacute;n de Gerencia respectiva por <b>Pago Indebido de Publicidad Registral en Línea</b>.</p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="100%" height="20" colspan="5"></td>
  </tr>
  <tr>
  <td width="10%" height="14" ></td>
    <th width="25%" height="14" align="center" class="textoSolicitud"><b>Recibo</b></th>
    <th width="40%" height="14" align="center" class="textoSolicitud"><b>Interesado</b></th>
    <th width="15%" height="14" align="center" class="textoSolicitud" ><b>Importe </b></th>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
  <td width="10%" height="14" ></td>
    <td width="25%" height="14" class="textoSolicitud" align="center"><bean:write name="comprobante" property="numeroDoc"/>(<bean:write name="comprobante" property="fecha_hora"/>)</td>
    <td width="40%" height="14" class="textoSolicitud" align="center"><bean:write name="comprobante" property="nombreEntidad"/></td>   
    <td width="15%" height="14" align="center" class="textoSolicitud">S/. <u><bean:write name="comprobante" property="monto"/></u></td> 
    <td width="10%" height="14" ></td>
  </tr>
 <tr>
    <td width="100%" height="80" colspan="4"></td>
  </tr>
   <tr>
    <td width="10%" height="14" ></td>
    <td width="90%" height="14"colspan="5" align="left" class="textoSolicitud"> 
    	SON: <bean:write name="montoPalabra"/> Y 00/100 NUEVOS SOLES 
    </td>
   
  
  <tr>
    <td width="100%" height="80" colspan="4" align="right" class="textoSolicitud"> Atentamente,</td>
  </tr>
  
  
  <tr>
    <td width="100%" height="155" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="1" ></td>
    <td width="90%" height="1" colspan="2" >
       
	</td>
	 <td width="10%" height="14" ></td>
    <td width="10%" height="1" ></td>
  </tr>
 
 
</table>

<br>
<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;">
<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a><br>
<a href="javascript:history.back();" ><IMG  src="images/btn_regresa.gif" border=0></a>
</div>
</form>
</body>
</html>