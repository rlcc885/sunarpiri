<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*"%>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>

<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head>
<title>Solicitud de Datos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">

//arreglo 2 empieza vacio:



function doSubmit(){
	doSendChildren();
}

	
</SCRIPT>
<script Language="JavaScript">
	function VentanaFlotante(pag)
	{
		var ancho= 500;
		var alto= 563;
		NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
	}
	
	function CambioCert()
	{   var auxTipo="";
		frm1.hidCert.value = document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].text;
		auxTipo= frm1.hidCert.value;
		if (auxTipo=="Certificado de Vigencia en RMC"){
			frm1.hidTipoGravVig.value = "RV"
		}else{
			if (auxTipo=="Certificado Compendioso de Gravámen en RMC"){
				frm1.hidTipoGravVig.value = "RG"		
			}else{
				frm1.hidTipoGravVig.value = ""
			}	
		}
	}
	
	function Validar()
	{
		if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value=="")
		{
			alert("Seleccione un tipo de Certificado.");
			return;
		}
		document.frm1.action="/iri/CertificadosIRI.do?state=datosBasicos";
		document.frm1.submit();
	
	}
	
	function isAlphaNum( str ) {
		if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
			return false;
	
		var isValid = true;
		str += ""; 
	
		for (i = 0; i < str.length; i++)
		{
	
			if (!(((str.charAt(i) >= "0") && (str.charAt(i) <= "9")) || 
			((str.charAt(i) >= "a") && (str.charAt(i) <= "z")) ||
			((str.charAt(i) >= "A") && (str.charAt(i) <= "Z"))))
			{
			isValid = false;
			break;
			} 
		}
		
		return isValid;
	} 
	
	//la funcion isCharacter recibe 2 parametros, el str debe ser diferente de vacio y nulo
	function isCharacter(str,char){
		if (char == "undefined" || char == "null" ||char == "")
			return false;
	
		var bandera=false;
		str += ""; 
	
		for (i = 0; i < str.length; i++)
		{
	
			if (!(str.charAt(i) != char))
			{
			bandera = true;
			break;
			} 
		}
		return bandera;
	 }
	
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY onLoad="CambioCert()">
<script language="JavaScript">
<!--
	var startTime = new Date();

	// -->
</script>
<form name="frm1" method="post">
<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Solicitud de Certificados</b>
<br>
<table cellspacing=0>
	<tr> 
		<td width="685" colspan="8" >
			<p align="left"><font color="#949400" size="2"><strong>ELIJA EL CERTIFICADO QUE DESEA</strong></font></p>
		</td>
    </tr>
</table>
<table cellspacing=0  class="punteadoTablaTop" width="600px">
	<tr>
		<td width="5">&nbsp;</td>
		<td width="150"><strong>TIPO DE CERTIFICADO</strong></td>
		<td><!--Tarifario--> <input type="hidden" name="hidGLA">
			<input type="hidden" name="hidArea">
			<select  name="cboTipoCert" size="1" onChange="CambioCert()">
				<option value="">&lt;&lt; Seleccione el Tipo de Certificado&gt;&gt;</option>
				<option value="N">Certificado Positivo/Negativo</option>
				<option value="R">Certificado de Vigencia en RMC</option>
				<option value="R">Certificado Compendioso de Gravámen en RMC</option>
				<option value="D">Certificado Compendioso de Antecedentes Dominiales en RJB</option>
				<option value="G">Certificado Compendioso de Historial de Gravamenes en RJB</option>
				<option value="C">Certificado Registral Mobiliario</option>
				
			</select>
			<input type="hidden" name="hidCert"> 
			<input type="hidden" name="hidTipoGravVig" value=""> 
		</td>
		<td width="65">
			<input type="button" class="formbutton" value="Solicitar" onclick="javascript:Validar();" 
				   onmouseover="javascript:mensaje_status('Datos Básicos de Solicitud del tipo de certificado elegido');return true;"
				   onmouseOut="javascript:mensaje_status(' ');return true;"/>
		</td>
	</tr>
</table>
</form>
<br>
</BODY>
</HTML>
