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
function Aceptar()
{  
  document.form1.method="POST";
	document.form1.action="/iri/ConstanciaPago.do?";
	document.form1.submit();
	return true; 
}
function atras(pag)
{ 
	document.form1.method="POST";
	<logic:present name="tipoUsr">
		<logic:equal name="tipoUsr" value="I">
			document.form1.action="/iri/ConstanciaPago.do?state=certificadoIndividual&pagina="+pag;
		</logic:equal>
		<logic:equal name="tipoUsr" value="O">
			document.form1.action="/iri/ConstanciaPago.do?state=certificadoIndividualOrg&pagina="+pag;
		</logic:equal>
	</logic:present>
	document.form1.submit();
	return true; 
}

function adelante(pag)
{ 
	document.form1.method="POST";
	<logic:present name="tipoUsr">
		<logic:equal name="tipoUsr" value="I">
			document.form1.action="/iri/ConstanciaPago.do?state=certificadoIndividual&pagina="+pag;
		</logic:equal>
		<logic:equal name="tipoUsr" value="O">
			document.form1.action="/iri/ConstanciaPago.do?state=certificadoIndividualOrg&pagina="+pag;
		</logic:equal>
	</logic:present>
	document.form1.submit();
	return true; 
}
function Regresar()
{
	document.form1.method="POST";
	document.form1.action="/iri/ConstanciaPago.do?state=regresar";
	document.form1.submit();
	return true;
	
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";	
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<style type="text/css">
<!--
.Estilo1 {font-size: 24px}
.Estilo3 {font-size: 18px}
.Estilo4 {font-size: 34px}
-->
</style>
</head>
<body >

<form name="form1">
<table class="formularioimpresion"  >

  <tr>
     <td height="160" colspan="6" align="center"></td>
  </tr>
  <tr>
     <td height="14" colspan="6" align="center" class="tituloSolicitud Estilo4">CERTIFICADO DE PAGO</td>
  </tr>
  <tr>
     <td height="20" colspan="6" align="center"></td>
  </tr>
  <tr>
     <td height="14" colspan="6" align="center" class="tituloSolicitud"><span class="Estilo1">TESORERIA NRO </span>
      <span class="Estilo1"><bean:write name="abonoActual" property="numConstancia"/></span></td>
  </tr>
  <tr>
     <td height="50" colspan="6" align="center"></td>
  </tr>
  <tr>
   <td width="5%" height="14" ></td>
    <td height="14" colspan="4" align="justify">
    <p align="justify" class="Estilo3 textoSolicitud">Mediante la presente se certifica el pago realizado por <u>
      <b><bean:write name="usuarioCer" property="nombres"/></b></u>
      identificado(a) con 
    <b><bean:write name="usuarioCer" property="tipoDocumento"/> </b>
   <b> Nro 
    <bean:write name="usuarioCer" property="numDocumento"/> 
    a la  
    <bean:write name="zonaRegistral" /></b>
    , por el <b><u>Servicio de Publicidad en L&iacute;nea</u></b>,
    por el importe de S/. 
    <b><bean:write name="montoTotal"/>
    Nuevos Soles.</b> El cual fue pagado en tesorería según movimiento de Caja es el siguiente:</p></td>
     <td width="5%" height="14" >     </td>
  </tr>
   <tr>
     <td height="40" colspan="6" align="center"></td>
  </tr>
  <tr>
  	<td width="5%" height="14" ></td>
    <th height="14" align="left"class="textoSolicitud" width="29%"><span class="Estilo3"><b>Fecha de Oper.</b></span></th>
    <th height="14" align="left"class="textoSolicitud" width="28%"><span class="Estilo3"><b>Caja</b></span></th>
    <th height="14"align="left"class="textoSolicitud" width="17%"><span class="Estilo3"><b>Recibo</b></span></th>
    <th height="14" align="right" class="textoSolicitud" width="21%"><span class="Estilo3"><b>Importe S/.</b></span></th>
    <td width="5%" height="14" ></td>
  </tr>
  

  <tr>
  <td width="5%" height="14" ></td>
    <td height="14" class="textoSolicitud" width="29%"><span class="Estilo3"><bean:write name="abonoActual" property="fecha"/></span></td>
    <td height="14" class="textoSolicitud" width="28%"><span class="Estilo3"><bean:write name="abonoActual" property="cajeroId"/></span></td>
    <td height="14" class="textoSolicitud" width="17%"><span class="Estilo3"><bean:write name="abonoActual" property="comprobanteId"/></span></td>
    <td height="14" class="textoSolicitud" align="right" width="21%"><span class="Estilo3"><bean:write name="abonoActual" property="monto"/></span></td> 
    <td width="5%" height="14" ></td>
  </tr>
  <tr>
  <td width="5%" height="14" ></td>
    <td height="14" class="textoSolicitud" width="29%"><span class="Estilo3"><b>Total</b></span> </td>
    <td height="14" width="28%"></td>
    <td height="14" width="17%"></td>
    <td height="14" class="textoSolicitud" align="right" width="21%"><span class="Estilo3"><b><bean:write name="montoTotal"/></b></span></td> 
    <td width="5%" height="14" ></td>
  </tr>
   <tr>
     <td height="80" colspan="6" align="center"></td>
  </tr>
  <tr>
  <td width="5%" height="14" ></td>
    <td height="14" colspan="4" class="textoSolicitud"><span class="Estilo3"><bean:write name="fechaHoy"/></span></td>
    <td width="5%" height="14" ></td>
  </tr>
</table>

<br>
<div id="HOJA2" style="position:absolute; left:380px; top:50px; visibility: visible;">
<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a>
<a href="javascript:Regresar();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif"></a>
<br>
<logic:present name="previous">
<a href="javascript:atras(<bean:write  name="previous"/>);"><img src="images/btn_ant.gif"></a>
</logic:present>
<logic:present name="next" >
<a href="javascript:adelante(<bean:write  name="next"/>);"><img src="images/btn_sig.gif"></a>
</logic:present>
</div>
<logic:present name="userId" >
<input type="hidden" name="userId" value="<bean:write  name="userId"/>">
</logic:present>
<logic:present name="ruc" >
<input type="hidden" name="ruc" value="<bean:write  name="ruc"/>">
</logic:present>
<logic:present name="razsoc" >
<input type="hidden" name="razsoc" value="<bean:write  name="razsoc"/>">
</logic:present>
<logic:present name="lineaPrepago" >
<input type="hidden" name="lineaPrepago" value="<bean:write  name="lineaPrepago"/>">
</logic:present>


<!-- -->
<input type="hidden" name="diainicio" value="<bean:write  name="diainicio"/>">
<input type="hidden" name="mesinicio" value="<bean:write  name="mesinicio"/>">
<input type="hidden" name="anoinicio" value="<bean:write  name="anoinicio"/>">
<input type="hidden" name="diafin" value="<bean:write  name="diafin"/>">
<input type="hidden" name="mesfin" value="<bean:write  name="mesfin"/>">
<input type="hidden" name="anofin" value="<bean:write  name="anofin"/>">
<input type="hidden" name="radio" value="<bean:write  name="radio"/>">
<input type="hidden" name="razsoc" value="<bean:write  name="razsoc"/>">
<input type="hidden" name="ruc" value="<bean:write  name="ruc"/>">
<input type="hidden" name="userId" value="<bean:write  name="userId"/>">
<input type="hidden" name="tipopago" value="<bean:write  name="tipopago"/>">
<input type="hidden" name="agencia" value="<bean:write  name="agencia"/>">  
<logic:present name="prevRegresar">
	<input type="hidden" name="prevRegresar" value="<bean:write name="prevRegresar"/>">
</logic:present>
<logic:present name="nextRegresar">
	<input type="hidden" name="nextRegresar" value="<bean:write name="nextRegresar"/>">
</logic:present>
<input type="hidden" name="entidadRegresa" value="<bean:write name="entidadRegresa"/>">
<!-- -->
</form>
</body>
</html>