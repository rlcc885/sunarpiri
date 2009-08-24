<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.acceso.bean.*" %>
<%
  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  long perfilusuarioid =usuarioBean.getPerfilId();
%>

<html>
<head><title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">

//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;

function busqdirectapornumero(){

	if (esVacio(document.frm1.numtitu.value) || !esEntero(document.frm1.numtitu.value) || !esEnteroMayor(document.frm1.numtitu.value,1))
	{	
		alert("Por favor ingrese correctamente el Numero del Titulo");
		document.frm1.numtitu.focus();
		return;
	}

	if(tieneCaracterNoValido(document.frm1.numtitu.value)){
		alert("Por favor no ingrese caracteres no válidos");
		document.frm1.numtitu.focus();
		return;
	}
	var numeroTitulo = document.frm1.numtitu.value;
	if(numeroTitulo < 77000000){
		if (document.frm1.listbox2.options.length == 0)
		{
			alert("Por favor seleccione por lo menos una Oficina Registral");
			document.frm1.listbox1.focus();
			return;
		}
	}
	
	document.frm1.numtitu.value = rellenaIzq(document.frm1.numtitu.value,"0",8);
	document.frm1.method ="POST";
	document.frm1.action = "/iri/BusquedaTitulo.do?state=buscarXNroTituloG";
	doSendChildren();
	document.frm1.submit();
}

function PorPresentante(tipo){
	if (document.frm1.listbox2.options.length == 0)
	{
		alert("Por favor seleccione por lo menos una Oficina Registral");
		document.frm1.listbox1.focus();
		return;
	}

	if(tipo=='T')
	{
		if (esVacio(document.frm1.numdocpn.value) || !esEntero(document.frm1.numdocpn.value) || !esLongitudMayor(document.frm1.numdocpn.value,8) || !esEnteroMayor(document.frm1.numdocpn.value,1))
		{	
			alert("Por favor ingrese correctamente el Numero de Documento");
			document.frm1.numdocpn.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.numdocpn.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.numdocpn.focus();
			return;
		}
	}
	if(tipo=='N')
	{
		if (esVacio(document.frm1.apepat.value) || !esLongitudMayor(document.frm1.apepat.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Paterno");
			document.frm1.apepat.focus();
			return;
		}
		else
		{
			if(esVacio(document.frm1.nombres.value) && esVacio(document.frm1.apemat.value))
			{
				alert("Por favor ingrese al menos dos caracteres para el Apellido Materno o el Nombre");
				document.frm1.apemat.focus();
				return;			
			}
		}	
			
		if (!esVacio(document.frm1.nombres.value) && !esLongitudMayor(document.frm1.nombres.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Nombre");
			document.frm1.nombres.focus();
			return;
		}	
		if (!esVacio(document.frm1.apemat.value) && !esLongitudMayor(document.frm1.apemat.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Materno");
			document.frm1.apemat.focus();
			return;
		}	
		if(tieneCaracterNoValido(document.frm1.apepat.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.apepat.focus();
			return;
		}		
		if(tieneCaracterNoValido(document.frm1.nombres.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.nombres.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.apemat.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.apemat.focus();
			return;
		}
	}
	document.frm1.apepat.value.toUpperCase();
	document.frm1.apemat.value.toUpperCase();	
	document.frm1.nombres.value.toUpperCase();
	document.frm1.method = "POST";
	document.frm1.action = "/iri/BusquedaTitulo.do?state=buscarXPresentantePNG&tipob=" + tipo;
	doSendChildren();	
	document.frm1.submit();
}

function PorPresentanteJ(tipo){
	if (document.frm1.listbox2.options.length == 0)
	{
		alert("Por favor seleccione por lo menos una Oficina Registral");
		document.frm1.listbox1.focus();
		return;
	}
	if(tipo=='T')
	{
		if (esVacio(document.frm1.ruc.value) || !esEntero(document.frm1.ruc.value) || !esLongitudMayor(document.frm1.ruc.value,8) || !esEnteroMayor(document.frm1.ruc.value,1))
		{	
			alert("Por favor ingrese correctamente el Numero de Documento");
			document.frm1.ruc.focus();
			return;
		}	
		if(tieneCaracterNoValido(document.frm1.ruc.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.ruc.focus();
			return;
		}
	}
	if(tipo=='R')
	{
		if (esVacio(document.frm1.razsoc.value) && esVacio(document.frm1.siglas.value))
		{	
			alert("Por favor ingrese al menos la Razon Social  o las Siglas");
			document.frm1.razsoc.focus();
			return;
		}	
		if (!esVacio(document.frm1.razsoc.value)){
			if (!esLongitudMayor(document.frm1.razsoc.value,3))
			{	
				alert("Por favor ingrese al menos tres caracteres para la Razon Social");
				document.frm1.razsoc.focus();
				return;
			}	
		}
		if (!esVacio(document.frm1.siglas.value)){
			if (!esLongitudMayor(document.frm1.siglas.value,2))
			{	
				alert("Por favor ingrese al menos dos caracteres para las Siglas");
				document.frm1.siglas.focus();
				return;
			}	
		}
		if(tieneCaracterNoValido(document.frm1.razsoc.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.razsoc.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.siglas.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.siglas.focus();
			return;
		}
	}
	document.frm1.siglas.value.toUpperCase();
	document.frm1.razsoc.value.toUpperCase();
	document.frm1.method = "POST";
	document.frm1.action = "/iri/BusquedaTitulo.do?state=buscarXPresentantePJG&tipob=" + tipo;
	doSendChildren();	
	document.frm1.submit();
}

function PorParticipante(tipo){
	if (document.frm1.listbox2.options.length == 0)
	{
		alert("Por favor seleccione por lo menos una Oficina Registral");
		document.frm1.listbox1.focus();
		return;
	}
	
	if(tipo=='T')
	{
		if (esVacio(document.frm1.numdocpnp.value) || !esEntero(document.frm1.numdocpnp.value) || !esLongitudMayor(document.frm1.numdocpnp.value,8) || !esEnteroMayor(document.frm1.numdocpnp.value,1))
		{	
			alert("Por favor ingrese correctamente el Numero de Documento");
			document.frm1.numdocpnp.focus();
			return;
		}	
		if(tieneCaracterNoValido(document.frm1.numdocpnp.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.numdocpnp.focus();
			return;
		}
	}	
	if(tipo=='N')
	{
		if (esVacio(document.frm1.apepatp.value) || !esLongitudMayor(document.frm1.apepatp.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Paterno");
			document.frm1.apepatp.focus();
			return;
		}
		else
		{
			if(esVacio(document.frm1.nombrep.value) && esVacio(document.frm1.apematp.value))
			{
				alert("Por favor ingrese al menos dos caracteres para el Apellido Materno o el Nombre");
				document.frm1.apematp.focus();
				return;			
			}
		}	
					
		if (!esVacio(document.frm1.nombrep.value) && !esLongitudMayor(document.frm1.nombrep.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Nombre");
			document.frm1.nombrep.focus();
			return;
		}	
		if (!esVacio(document.frm1.apematp.value) && !esLongitudMayor(document.frm1.apematp.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Materno");
			document.frm1.apematp.focus();
			return;
		}	
		if(tieneCaracterNoValido(document.frm1.apepatp.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.apepatp.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.nombrep.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.nombrep.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.apematp.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.apematp.focus();
			return;
		}
	}

	document.frm1.apepatp.value.toUpperCase();
	document.frm1.apematp.value.toUpperCase();
	document.frm1.nombrep.value.toUpperCase();	
	document.frm1.method = "POST";
	document.frm1.action = "/iri/BusquedaTitulo.do?state=buscarXParticipantePNG&tipob=" + tipo;
	doSendChildren();
	document.frm1.submit();
}

function PorParticipanteJ(tipo){
	if (document.frm1.listbox2.options.length == 0)
	{
		alert("Por favor seleccione por lo menos una Oficina Registral");
		document.frm1.listbox1.focus();
		return;
	}
	
	if(tipo=='T')
	{
		if (esVacio(document.frm1.rucp.value) || !esEntero(document.frm1.rucp.value) || !esLongitudMayor(document.frm1.rucp.value,8) || !esEnteroMayor(document.frm1.rucp.value,1))
		{	
			alert("Por favor ingrese correctamente el Numero de Documento");
			document.frm1.rucp.focus();
			return;
		}	
		if(tieneCaracterNoValido(document.frm1.rucp.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.rucp.focus();
			return;
		}
	}	
	if(tipo=='R')
	{
		if (esVacio(document.frm1.razsocp.value) && esVacio(document.frm1.siglasp.value))
		{	
			alert("Por favor ingrese al menos la Razon Social o las Siglas");
			document.frm1.razsocp.focus();
			return;
		}	
		if (!esVacio(document.frm1.razsocp.value)){	
			if (!esLongitudMayor(document.frm1.razsocp.value,2))
			{	
				alert("Por favor ingrese al menos dos caracteres para la Razon Social");
				document.frm1.razsocp.focus();
				return;
			}	
		}		
		if (!esVacio(document.frm1.siglasp.value)){
			if (!esLongitudMayor(document.frm1.siglasp.value,2))
			{	
				alert("Por favor ingrese al menos dos caracteres para las Siglas");
				document.frm1.siglasp.focus();
				return;
			}	
		}
		if(tieneCaracterNoValido(document.frm1.razsocp.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.razsocp.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.siglasp.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.frm1.siglasp.focus();
			return;
		}
	}
	document.frm1.razsocp.value.toUpperCase();
	document.frm1.siglasp.value.toUpperCase();
	document.frm1.method = "POST";
	document.frm1.action = "/iri/BusquedaTitulo.do?state=buscarXParticipantePJG&tipob=" + tipo;
	doSendChildren();
	document.frm1.submit();
}

function CambioOficinaRegistral(opcionSeleccionada) {
	var registroPublico;
	var o = parseInt(opcionSeleccionada);
	switch (o) {
		case  1 : registroPublico = "05"; break;
		case  2 : registroPublico = "11"; break;
		case  3 : registroPublico = "12"; break;
		case  4 : registroPublico = "09"; break;
		case  5 : registroPublico = "08"; break;
		case  6 : registroPublico = "13"; break;
		case  7 : registroPublico = "04"; break;
		case  8 : registroPublico = "02"; break;
		case  9 : registroPublico = "01"; break;
		case 10 : registroPublico = "06"; break;
		case 11 : registroPublico = "10"; break;
		case 12 : registroPublico = "03"; break;
		case 13 : registroPublico = "07"; break;
		case 35 : registroPublico = "01"; break;	//Chequear esto hp
		default: alert("No tiene registro Publico");
	}
	return registroPublico;
}
function RefrescaCombos(codProv) {
	codProv = CambioOficinaRegistral(codProv);
	fbox = document.frm1.listbox1;;
	for(var i=0; i<fbox.options.length; i++)
	{
	  	fbox.options[i].selected=false;
	  	
	  	if (fbox.options[i].value == codProv) {
			fbox.options[i].selected=true;
		}
	}
	doAdd1();
}

function doSubmit(){
	doSendChildren();
}

function doAdd1(){
var objeto = document.frm1.listbox1;

//busca seleccionados
  if (objeto.length != 0)
  { 
     if (objeto.selectedIndex != -1)
     {
		for(var i=0; i < objeto.options.length ; ++i)
		{
	   		if (objeto.options[i].selected)
	   		{
	   			var g = objeto.options[i].value;
	   			var h = objeto.options[i].text;
	       		//alert("pareja elegida = " + g + "/" + h);
	 		     
	 		     //verificar que no exista en el arreglo destino
	 		     var flag = false;
	 		     for (var j = 0; j < arr2.length ; j++)
	 		     		{
	 		     			var estado = arr2[j][0];
	 		     			var codigo = arr2[j][1];
	 		     			if (estado != "**********" )
	 		     				{
	 		     					if (codigo == g)
	 		     						{
	 		     							//alert("elemento " + h + " ya elegido");
	 		     							flag = true;
	 		     						}
	 		     				}
	 		     		} //for j
	 		     		
	 		     	//agregar a arreglo destino
	 		     	if (flag == false)
	 		     	  {
										     var arrx = new Array();
										     	arrx[0]="activo";
										     	arrx[1]=g;
										     	arrx[2]=h;
										     arr2[cont2] = arrx;
										     cont2++;		 		

     		
						}
	   		}
        } //for i
     }
  }  
			     
doFill1();
} // fin metodo doAdd1

//*******************************************************************************************
function doRemove1()
{
var objeto = document.frm1.listbox2;

//borrar del arreglo los elementos seleccionados
if (objeto.length != 0)
  { 
     if (objeto.selectedIndex != -1)
     {
		for(var i=0; i < objeto.options.length ; ++i)
		{
	   		if (objeto.options[i].selected)
	   		{
	   			var g = objeto.options[i].value;
	       		arr2[g][0]="**********";  // marcarlo como "inactivo"
	       		objeto.options[i] = null;	       
	       		--i;
	   		}
        }           
     }
  }   
  
doFill1();
} // function doRemove1

function doFill1(){
	var objeto = document.frm1.listbox2;

//borrar contenido actual de objeto
	if (objeto.length != 0)
		{ 
		for(var i=0; i<objeto.options.length ; ++i)
			{
				objeto.options[i]=null;
						--i;
			}
    	}
    	
//llenar objeto con elemento activos del arreglo
	for (var j=0; j < arr2.length; j++)
		{
			var activo = arr2[j][0];
			//var g = arr2[j][1];
			var xTexto = arr2[j][2];
			
			if (activo != "**********")
					objeto.options[objeto.options.length] = new Option(xTexto,j);
		} //for
} 


//***************************************
function doSendChildren()
{
   var cadena1="";
   for(var i=0; i< cont2; ++i)
   {  
      if (arr2[i][0]!="**********")
      	{
          cadena1 = cadena1 + arr2[i][1] + "|"; //end of field
          //cadena1 = cadena1 + arr2[i][2] + "*"; //end of record
        }
   }   
   document.frm1.hid1.value=cadena1;
}

function selectAllOptions(obj) {
	for (var i=0; i<obj.options.length; i++) {
		obj.options[i].selected = true;
	}
	doAdd1();
}
function removeAllOptions(obj) {
	for (var i=0; i<obj.options.length; i++) {
		obj.options[i].selected = true;
	}
	doRemove1();
}
     
	
</SCRIPT>
<script Language="JavaScript">
	function VentanaFlotante(pag)
		{
		var ancho= 500;
		var alto= 563;
		NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
		}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY >
<form name="frm1" method="post">
<input type="hidden" name="hid1">
<input type="hidden" name="hid2">
<br>
<table class=titulo cellspacing=0>
  <tr> 
    <td><font color=black>SERVICIOS &gt;&gt;</font>Consulta de Estados de T&iacute;tulos </td>
  </tr>
</table>
<br>
<%--
listaDocsId
<table border="0" width="680">
  <tr>
    <td width="94" align="right" rowspan="3">Sede&nbsp;</td>
    <td width="234" align="right" rowspan="3">
      <select size="6" name="listbox1" multiple>
			<logic:iterate name="arreglo1" id="item1" scope="request">
				<option value="<bean:write name="item1" property="codigo"/>"><bean:write name="item1" property="descripcion"/></option>
			</logic:iterate>
    </select>
    </td>
    <td width="75" align="right" rowspan="3">
    	<input type="button" value=" &gt;&gt; " onClick="doAdd1();">
        <input type="button" value=" &lt;&lt; " onClick="doRemove1();">
    </td>
    <td width="148" align="right" >
      <select size="6" name="listbox2" multiple>
      </select>
    </td>
 </tr>
</table>
--%>
<table class=formulario cellspacing=0>
    <tr> 
      <td align=center><select multiple name="listbox1"  size="10" width="260px" onDblClick="doAdd1()" style="width:260px">
          <option value="05" >Zona Registral No I - Sede Piura</option>
          <option value="11" >Zona Registral No II - Sede Chiclayo</option>
          <option value="12" >Zona Registral No III - Sede Moyobamba</option>
          <option value="09" >Zona Registral No IV - Sede Iquitos</option>
          <option value="08" >Zona Registral No V - Sede Trujillo</option>
          <option value="13" >Zona Registral No VI - Sede Pucallpa</option>
          <option value="04" >Zona Registral No VII - Sede Huaraz</option>
          <option value="02" >Zona Registral No VIII - Sede Huancayo</option>
          <option value="01">Zona Registral No IX - Sede Lima</option>
          <option value="06">Zona Registral No X - Sede Cuzco</option>
          <option value="10">Zona Registral No XI - Sede Ica</option>
          <option value="03">Zona Registral No XII - Sede Arequipa</option>
          <option value="07">Zona Registral No XIII - Sede Tacna</option>
        </select>
      </td>
      <td align=center>
      	<input type="button" value=">>" onclick="selectAllOptions(document.frm1.listbox1)" title="Seleccionar Todas las Oficinas Registrales" style="width:25px"><br>       
      	<input type="button" value=">" onclick="doAdd1()" title="Seleccionar Oficina Registral" style="width:25px"><br> 
        <input type="button" onClick="doRemove1()" value="<" title="Retirar de la Seleccion la Oficina Registral" style="width:25px"><br> 
      	<input type="button" value="&lt;&lt;" onclick="removeAllOptions(document.frm1.listbox2)" title="Retirar de la Seleccion Todas las Oficinas Registrales" style="width:25px"><br>               
      </td>
      <td align=center><select name="listbox2" multiple size="10" style="width:250px" width="250px" onDblClick="doRemove1()">
        </select></td>
    </tr>
</table>
<table class=formulario cellspacing=0>
    <tr>
      <td align="center"><a href="javascript:VentanaFlotante('/iri/acceso/mapas/MAPA2.htm')" onmouseover="javascript:mensaje_status('Identifique su Oficina Registral');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Identifique su Oficina Registral</a></td>
    </tr>
</table>

<br>
<table class=cabeceraformulario cellspacing=0>
  <tr>
    <td><strong>Por N&uacute;mero de T&iacute;tulo</strong></td>
  </tr>
</table>

<table class=formulario cellspacing=0>
  <tr>
    <td width="5">&nbsp;</td>  
    <td width="130" >A&ntilde;o</td>
    <td width="132"><select size="1" name="ano">
        <option>2001</option>
        <option>2002</option>
        <option>2003</option>
        <option>2004</option>
        <option>2005</option>
        <option>2006</option>
        <!-- Inicio:rbahamonde:31/12/2008 -->
        <!-- se agrego el año 2009 -->
        <option>2007</option>
        <option>2008</option>
        <option selected>2009</option>
        <!-- Fin:rbahamonde -->
      </select></td>
    <td width=100>N&uacute;mero de T&iacute;tulo</td>
    <td width=132><input type="text" name="numtitu" size="12" maxlength="8" style="width:100" onblur="sololet(this)" value=""></td>
    <td  ><a href="javascript:busqdirectapornumero();" onmouseover="javascript:mensaje_status('Buscar por Numero de Titulo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A>
    <!--<input type="button" value="Buscar" name="DD" onClick="busqdirectapornumero()" ></td>--></td>
  </tr>
</table>
<br>
  <!--Inicio:jascencio:11/07/07 
  	  CC:REGMOB-COM	
   -->
<table border="1" cellpadding="2" cellspacing="2" bordercolor="#FFFFFF">
  <tr>
  	<td></td>
  	<td></td>
  	<td></td>
  	<td></td>
  	<td></td>
  	<td></td>  	  	
  </tr>
  <tr>
  	<td colspan="6" bordercolor="red" align="center"><font color="red">&nbsp;(*)Para los títulos que inician con la
		serie 77 millones no se requiere inidicar la oficina registral&nbsp;</font></td>
  </tr>
</table>
<br>

  <!--Fin:jascencio  --> 
<!--<span id="presentante">-->
<table class=cabeceraformulario cellspacing=0>
  <tr>
    <td><strong>Por Presentante - Persona Natural</strong></td>
  </tr>
</table>

<table class=formulario cellspacing=0>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Apellido Paterno</td>
    <td width="132"><input type="text" name="apepat" size="20" maxlength="30" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">Apellido Materno</td>
    <td width="132"><input type="text" name="apemat" size="20" maxlength="30" style="width:133" onblur="sololet(this)" value=""></td>
    <td><!--<a href="javascript:PorPresentante();"><IMG src="/iri/images/btn_buscar2.gif" border="0"></A>--></td>
  </tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Nombres</td>
    <td width="132"><input type="text" name="nombres" size="20" maxlength="40" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">&nbsp;</td>
    <td width="132">&nbsp;</td>
    <td><a href="javascript:PorPresentante('N');" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
  <tr><td colspan="6"><hr></td></tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Tipo Doc&nbsp;
      	<select size="1" name="tipdocpn">
			<logic:iterate name="listaDocsId" id="tdpn" scope="request">
				<option value="<bean:write name="tdpn" property="codigo"/>"><bean:write name="tdpn" property="descripcion"/></option>
			</logic:iterate>
    	</select>
    </td>
    <td width="132"><input type="text" name="numdocpn" size="20" maxlength="15" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">&nbsp;</td>
    <td width="132">&nbsp;</td>
    <td><a href="javascript:PorPresentante('T');" onmouseover="javascript:mensaje_status('Buscar por Numero de Documento de Persona Natural Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
</table>
<br>
<table class=cabeceraformulario cellspacing=0>
  <tr>
    <td><strong>Por Presentante - Persona Juridica</strong></td>
  </tr>
</table>

<table class=formulario cellspacing=0>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Raz&oacute;n Social</td>
    <td width="132"><input type="text" name="razsoc" size="20" maxlength="100" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">Siglas</td>
    <td width="132"><input type="text" name="siglas" size="12" maxlength="50" style="width:133" onblur="sololet(this)" value=""></td>
    <td><a href="javascript:PorPresentanteJ('R');" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Juridica Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
  <tr><td colspan="6"><hr></td></tr>


  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">RUC&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	<select size="1" name="tipdocpj">
			<logic:iterate name="listaDocsIdPJ" id="tdpj" scope="request">
				<option value="<bean:write name="tdpj" property="codigo"/>"><bean:write name="tdpj" property="descripcion"/></option>
			</logic:iterate>
    	</select>
    </td>
    <td width="132"><input type="text" name="ruc" size="12" maxlength="11" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">&nbsp;</td>
    <td width="132">&nbsp;</td>
    <td><a href="javascript:PorPresentanteJ('T');" onmouseover="javascript:mensaje_status('Buscar por Numero de RUC de Persona Juridica Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>

  
</table>
<br>
<!--<span id="participante">-->




<table class=cabeceraformulario cellspacing=0>
  <tr>
    <td><strong>Por Participante - Persona Natural</strong></td>
  </tr>
</table>

<table class=formulario cellspacing=0>
  <tr>NO DISPONIBLE PARA REGISTRO DE PROPIEDAD VEHICULAR.</tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Apellido Paterno</td>
    <td width="132"><input type="text" name="apepatp" size="20" maxlength="30" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">Apellido Materno</td>
    <td width="132"><input type="text" name="apematp" size="20" maxlength="30" style="width:133" onblur="sololet(this)" value=""></td>
	<td></td>
  </tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Nombres</td>
    <td width="132"><input type="text" name="nombrep" size="20" maxlength="40" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">&nbsp;</td>
    <td width="132">&nbsp;</td>
    <td><a href="javascript:PorParticipante('N');" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural Participante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
  <tr><td colspan="6"><hr></td></tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130"> Tipo Doc&nbsp;
      	<select size="1" name="tipdocpnp">
			<logic:iterate name="listaDocsId" id="tdpnp" scope="request">
				<option value="<bean:write name="tdpnp" property="codigo"/>"><bean:write name="tdpnp" property="descripcion"/></option>
			</logic:iterate>
    	</select>
    </td>
    <td width="132"><input type="text" name="numdocpnp" size="20" maxlength="15" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">&nbsp;</td>
    <td width="132">&nbsp;</td>
    <td><a href="javascript:PorParticipante('T');" onmouseover="javascript:mensaje_status('Buscar por Numero de Documento de Persona Natural Participante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
</table>





<br>




<table class=cabeceraformulario cellspacing=0>
  <tr>
    <td><strong>Por Participante - Persona Juridica</strong></td>
  </tr>
</table>

<table class=formulario cellspacing=0>
  <tr>NO DISPONIBLE PARA REGISTRO DE PROPIEDAD VEHICULAR.</tr>  
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">Raz&oacute;n Social</td>
    <td width="132"><input type="text" name="razsocp" size="20" maxlength="100" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">Siglas</td>
    <td width="132"><input type="text" name="siglasp" size="12" maxlength="50" style="width:133" onblur="sololet(this)" value=""></td>
    <td><a href="javascript:PorParticipanteJ('R');" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Juridica Participante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
  <tr><td colspan="6"><hr></td></tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="130">RUC&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	<select size="1" name="tipdocpjp">
			<logic:iterate name="listaDocsIdPJ" id="tdpjp" scope="request">
				<option value="<bean:write name="tdpjp" property="codigo"/>"><bean:write name="tdpjp" property="descripcion"/></option>
			</logic:iterate>
    	</select>
    </td>
    <td width="132"><input type="text" name="rucp" size="12" maxlength="11" style="width:133" onblur="sololet(this)" value=""></td>
    <td width="100">&nbsp;</td>
    <td width="132">&nbsp;</td>
    <td><a href="javascript:PorParticipanteJ('T');" onmouseover="javascript:mensaje_status('Buscar por Numero de RUC de Persona Juridica Participante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="<%=request.getContextPath()%>/images/btn_buscar2.gif" border="0"></A></td>
  </tr>
</table>





<input type="hidden" name="pagina" value="1">
</form>
<br>
<script LANGUAGE="JavaScript">
	doFill1();	
</script>
</BODY>
</HTML>