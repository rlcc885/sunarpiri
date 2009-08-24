<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>
<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head><title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
Navegador();
//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;
function BuscarSolicitud()
	{
		if (esVacio(document.frm1.txtnumSol.value) || !esEntero(document.frm1.txtnumSol.value) || !esEnteroMayor(document.frm1.txtnumSol.value,1))
		{	
			alert("Por favor ingrese correctamente el Número de Solicitud");
			document.frm1.txtnumSol.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtnumSol.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtnumSol.focus();
			return;
		}
		document.frm1.action="/iri/BusquedaSolicitudPublico.do?state=muestraSolicitudExt";
		document.frm1.submit();
	
	}	
	
function DetalleSolicitud(sol_id, tipo)
	{		
		//document.frm1.solicitud.value = sol_id;
		//document.frm1.registrador.value = Registrador;
		document.frm1.action="/iri/BusquedaSolicitudPublico.do?state=detalleSolicitudExt&solicitud="+ sol_id +"&registrador="+tipo;
		document.frm1.submit();
	
	}
	
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
</SCRIPT>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
<BODY>
<script language="JavaScript">
<!--
	var startTime = new Date();

	// -->
</script>
<form name="frm1" method="post">
<br>
<table class=titulo cellspacing=0>
  <tr> 
      <td><font color=black>SERVICIOS&gt;&gt; </font>Consulta de Estado de Solicitudes</td>
	</tr>
</table>
<br>
<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>BUSQUEDA DE SOLICITUD</strong> Para Solicitudes de Reserva de Denominaci&oacute;n clic<html:link page="/solicitudes/denominacion/FrmConSol.jsp">Aqu&iacute;</html:link></td>
  </tr>
</table>
  <table class=formulario cellspacing=0>
    <tr> 
      <td width="8">&nbsp;</td>
      <td width="111">&nbsp;</td>
      <td width="278">&nbsp;</td>
      <td width="193">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="111">
        <strong>Por N&uacute;mero</strong> </td>
      <td>
      <input type="text" name="txtnumSol" size="20" maxlength="18" style="width:133" onBlur="sololet(this)" value=""> 
      </td>
      <td width="193">
      	<a href="javascript:BuscarSolicitud();" onmouseover="javascript:mensaje_status('Buscar solicitud por Numero');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
      	<IMG src="images/btn_buscar.gif" border="0">
      	</a> 
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="193">&nbsp;</td>
    </tr>
  </table>	
<logic:present name="solicitud" scope="request">
	<logic:present name="arrResultado" scope="request">
	  <br>
	  <br>
	    <table class=grilla>
	      <tr> 
	        <th width="23">No.</th>
	        <th width="133">Tipo de Certificado</th>
	        <th colspan="2">Objeto del Certificado</th>
	        <th width="57">Ofic.Reg.</th>
	        <th width="94">Estado</th>
	        <th width="43">Ver Detalle</th>
	      </tr>
			<logic:iterate name="arrResultado" id="ResultBusq" scope="request">
				<tr>		
					<td><bean:write name="ResultBusq" property="solicitud_id"/> </td>
					<td><bean:write name="ResultBusq" property="nombre_Cert"/></td>							
			       <!-- inicio:jrosas 31-05-2007
					   SUNARP-REGMOBCOM: Setear la descripcion de Objeto de Certificado dependiendo del tipo de certificado  -->
					   		
					<!-- Para certificado Negativo/Positivo -->		
					<logic:equal name="ResultBusq" property="certificado_id" value="N">	
						<!-- Tipo: No es certificado de mobiliario de contratos -->		
						<logic:notEqual name="ResultBusq" property="tipoCertificado" value="18">
						    <td><bean:write name="ResultBusq" property="tpo_persona"/>&nbsp;</td>
							<logic:equal name="ResultBusq" property="tpo_persona" value="PN">
							   <td><bean:write name="ResultBusq" property="objeto_certPN"/>&nbsp;</td>
							</logic:equal>
							<logic:equal name="ResultBusq" property="tpo_persona" value="PJ">
							   <td><bean:write name="ResultBusq" property="objeto_certPJ"/>&nbsp;</td>
							</logic:equal>
						</logic:notEqual>
						<!-- Tipo: certificado de mobiliario de contratos -->		
						<logic:equal name="ResultBusq" property="tipoCertificado" value="18">		
							<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
						</logic:equal>
					</logic:equal>
					
					<!-- Para certificado de Vigencia o de Gravamen -->		
					<logic:equal name="ResultBusq" property="certificado_id" value="R">	
						<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
					</logic:equal>
					
					<!-- Para certificado RJB: Gravamen, Dominial -->		
					<logic:equal name="ResultBusq" property="certificado_id" value="D">	
						<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
					</logic:equal>
					<logic:equal name="ResultBusq" property="certificado_id" value="G">	
						<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
					</logic:equal>
				
					<!-- Para certificado CREM: actos vigentes, historico, condicionado -->		
					<logic:equal name="ResultBusq" property="certificado_id" value="C">	
						<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
					</logic:equal>
			 	
					<!-- Para certificado Copia Literal -->		
					<logic:equal name="ResultBusq" property="certificado_id" value="L">
						<td><bean:write name="ResultBusq" property="num_partida"/>&nbsp;</td>
						<td><bean:write name="ResultBusq" property="area_reg_id"/>&nbsp;</td>
					</logic:equal>
					
					<!-- Inicio:mgarate 31-05-2007-->	
					<logic:equal name="ResultBusq" property="certificado_id" value="B">	
						<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
					</logic:equal>
					<!-- Fin:mgarate 31-05-2007-->
			
					<td><bean:write name="ResultBusq" property="ofic_registral"/></td>
					
					<td><center>
						<logic:equal name="ResultBusq" property="flagPagoCrem" value="0">
							Liquidado
						</logic:equal>
						<logic:equal name="ResultBusq" property="flagPagoCrem" value="1">
							<bean:write name="ResultBusq" property="estado_sol"/>
						</logic:equal>
					   </center>
					</td>
								
					<td>
					  <center>
					    <a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>,'DET');" onmouseover="javascript:mensaje_status('Ver detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					      <IMG src="images/lupa.gif" border="0">
					    </a>
					  </center>
					</td>
				</tr>  
			</logic:iterate>
		</table>
		<br>
	    <br>
		<table class=tablasinestilo>
		  <tr>
		  	<td width="33%" align="left">
		  	<div id="HOJA2"> 
		  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
		  	</div></td>
			<td width="33%" align="certer">
		  	<div id="HOJA4"> 
		    </td>
			<td width="33%" align="right">
			<div id="HOJA3">	
			<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
			</div></td>
		  </tr>
		</table>
	</logic:present>
</logic:present>
<logic:present name="mensaje">
<br>
<br>
<bean:write name="mensaje"/>
</logic:present>
</form>
</BODY>
</HTML>
