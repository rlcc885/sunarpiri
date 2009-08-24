<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>

<html>
<head><title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<MET  A name="GENERATOR" content="IBM WebSphere Studio">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
<%
  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  long perfilusuarioid = usuarioBean.getPerfilId();
%>
Navegador();
//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;

function busqdirectapornumero(){

	if (document.frm1.listbox2.options.length == 0)
	{
		alert("Por favor seleccione por lo menos una Oficina Registral");
		document.frm1.listbox1.focus();
		return;
	}


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
		if (esVacio(document.frm1.apepat.value) && esVacio(document.frm1.nombres.value))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Paterno y el Nombre");
			document.frm1.apepat.focus();
			return;
		}	

		if (esVacio(document.frm1.apepat.value) || !esLongitudMayor(document.frm1.apepat.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Paterno");
			document.frm1.apepat.focus();
			return;
		}	
		if (esVacio(document.frm1.nombres.value) || !esLongitudMayor(document.frm1.nombres.value,2))
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
			if (!esLongitudMayor(document.frm1.razsoc.value,2))
			{	
				alert("Por favor ingrese al menos dos caracteres para la Razon Social");
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
		if (esVacio(document.frm1.apepatp.value) && esVacio(document.frm1.nombrep.value))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Paterno y para el Nombre");
			document.frm1.apepatp.focus();
			return;
		}	

		if (esVacio(document.frm1.apepatp.value) || !esLongitudMayor(document.frm1.apepatp.value,2))
		{	
			alert("Por favor ingrese al menos dos caracteres para el Apellido Paterno");
			document.frm1.apepatp.focus();
			return;
		}	
		if (esVacio(document.frm1.nombrep.value) || !esLongitudMayor(document.frm1.nombrep.value,2))
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


/*
arreglo hijo (provincia, que depende del combo padre DEPARTAMENTO)

	Id Provincia,  Descripcion Provincia,  Id Departamento
*/
var arr2 = new Array();

<% int k = 0; %>
<logic:iterate name="arr_hijo1" id="itemp" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="itemp" property="value01"/>"; //id provincia
	arrx[1]="<bean:write name="itemp" property="value02"/>"; //descripcion provincia
	arrx[2]="<bean:write name="itemp" property="value03"/>"; //id departamento
	arr2[<%=k%>]=arrx;
	<%  k++; %>
</logic:iterate>

function llenaDepProv()
{
obj1 = document.frm1.cboEnvPais;
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPais=obj1.options[i].value;			
				break;
			}
	}
	
if (codigoPais=="01")
{
	document.frm1.cboEnvDpto.disabled=false;
	document.frm1.cboEnvProv.disabled=false;

}
else
{
	document.frm1.cboEnvDpto.disabled=true;
	document.frm1.cboEnvProv.disabled=true;

}	

}
function llenaComboHijo()
{

var obj1;
var obj2;

obj1 = document.frm1.cboEnvDpto;  //papa
obj2 = document.frm1.cboEnvProv;  //hijo

//obtener codigo de papa
var codigoPapa ="";
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa=obj1.options[i].value;
				break;
			}
	}

//limpiar combo hijo
if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length ; ++i)
			{
				obj2.options[i]=null;
						--i;
			}
    }
    
//llenar combo hijo con informacion de acuerdo al Id de combo padre
		//TUTTI!!!!!!			objeto.options[objeto.options.length] = new Option("<TUTTI>","<TUTTI>");
var x0;
var x1;
var x2;			

for (var j=0; j<arr2.length; j++)
		{
			x0 = arr2[j][0];
			x1 = arr2[j][1];
			x2 = arr2[j][2];
			if (x2 == codigoPapa)
				obj2.options[obj2.options.length] = new Option(x1,x0);
		}

} // function llenaComboHijo

function HideCheques()
{ cheques.style.visibility="hidden"
}

function ShowCheques(valor)
{  if (valor=="C|Cheque")
 cheques.style.visibility="visible"
   if (valor=="E|Efectivo")
   cheques.style.visibility="hidden"
}
function gSoli(tipo) 
{
	if(tipo=="1")
	{
		areaSoliNatu.style.visibility="visible";
		areaSoliNatu.style.display="";
		areaSoliJuri.style.visibility="hidden";
		areaSoliJuri.style.display="none";
		doCambiaCombo(document.frm1.cboSolTipDoc,"09");
	}
	else
	{
		areaSoliJuri.style.visibility="visible";
		areaSoliJuri.style.display="";
		areaSoliNatu.style.visibility="hidden";
		areaSoliNatu.style.display="none";
		doCambiaCombo(document.frm1.cboSolTipDoc,"05");
	}
	
}
function gDest(tipo) 
{
	if(tipo=="1")
	{
		areaDestNatu.style.visibility="visible";
		areaDestNatu.style.display="";
		areaDestJuri.style.visibility="hidden";
		areaDestJuri.style.display="none";
	}
	else
	{
		areaDestJuri.style.visibility="visible";
		areaDestJuri.style.display="";
		areaDestNatu.style.visibility="hidden";
		areaDestNatu.style.display="none";
	}
	
}

function gEnvio(tipo) 
{
	if(tipo=="1")
	{
		document.frm1.txtDisEnv.value="<%=request.getAttribute("delivery")%>";
		areaDomi.style.visibility="visible";
		areaDomi.style.display="";
		areaVent.style.visibility="hidden";
		areaVent.style.display="none";
		areaCajero.style.visibility="visible";
		areaCajero.style.display="";
	}
	else
	{
		document.frm1.txtDisEnv.value="0";
		areaVent.style.visibility="visible";
		areaVent.style.display="";
		areaDomi.style.visibility="hidden";
		areaDomi.style.display="none";
		areaCajero.style.visibility="hidden";
		areaCajero.style.display="none";		
	}
	document.frm1.txtMonto.value = eval(document.frm1.txtDisServ.value) + eval(document.frm1.txtDisEnv.value);
	document.frm1.monto.value = document.frm1.txtMonto.value;
}

function popVisa() 
{
    window.open('http://www.visanet.com.pe/visa.htm','3DSecure','scrollbars=yes,Left=0,Top=120,Width=606,Height=402');
}

function Validar() 
{
	if(document.frm1.radSolTipPers[0].checked==true)
	{
		//alert("Sol Natural");
		if (esVacio(document.frm1.txtSolApPa.value) && esVacio(document.frm1.txtSolApMa.value) && esVacio(document.frm1.txtSolNom.value))
		{
			alert("Debe ingresar los datos del Solicitante");
			document.frm1.txtSolApPa.focus();
			return
		}
		if (document.frm1.txtSolApPa.value.length<2)
		{
			alert("El Apellido Paterno del Solicitante debe tener al menos 2 caracteres");					
			document.frm1.txtSolApPa.focus();
			return;
		}
		if (document.frm1.txtSolApMa.value.length==1)
		{
			alert("El Apellido Materno del Solicitante debe tener al menos 2 caracteres");
			document.frm1.txtSolApMa.focus();
			return;
		}
		if (document.frm1.txtSolNom.value.length<2)
		{
			alert("Los Nombres del Solicitante deben tener al menos 2 caracteres");
			document.frm1.txtSolNom.focus();
			return;
		}
		if (document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].value == "05")
		{
			alert("El Tipo de Documento seleccionado no está disponible para Persona Natural");
			doCambiaCombo(document.frm1.cboSolTipDoc,"09");
			document.frm1.txtSolNumDoc.focus();
			return;
		}
	}
	else
	//if(document.frm1.radSolTipPers[1].checked==true)
	{
		//alert("Sol Juridica");
		if (esVacio(document.frm1.txtSolRazSoc.value))
		{	
			alert("Ingrese la Razón Social del Solicitante");
			document.frm1.txtSolRazSoc.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtSolRazSoc.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtSolRazSoc.focus();
			return;
		}
		if (document.frm1.txtSolRazSoc.value.length<3)
		{	
			alert("La Razón Social del Solicitante debe tener al menos 3 caracteres");
			document.frm1.txtSolRazSoc.focus();
			return;
		}
		if (document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].value != "05")
		{
			alert("Sólo puede elegir R.U.C. para Persona Jurídica");
			doCambiaCombo(document.frm1.cboSolTipDoc,"05");
			document.frm1.txtSolNumDoc.focus();
			return;
		}
	}
	//Documento
	if (esVacio(document.frm1.txtSolNumDoc.value)|| !esEntero(document.frm1.txtSolNumDoc.value) || !esEnteroMayor(document.frm1.txtSolNumDoc.value,1))
	{	
		alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
		document.frm1.txtSolNumDoc.focus();
		return;
	}
	if(tieneCaracterNoValido(document.frm1.txtSolNumDoc.value))
	{
		alert("Por favor, no ingrese caracteres no válidos");
		document.frm1.txtSolNumDoc.focus();
		return;
	}
	if(document.frm1.radSolTipPers[0].checked==true)
	{
		if (document.frm1.txtSolNumDoc.value.length<8)
		{	
			alert("El Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
			document.frm1.txtSolNumDoc.focus();
			return;
		}
	}
	else
	{
		if (document.frm1.txtSolNumDoc.value.length<11)
		{	
			alert("El R.U.C. requiere 11 caracteres numéricos (0-9)");
			document.frm1.txtSolNumDoc.focus();
			return;
		}
	}
	
	if(document.frm1.radEnvTipo[0].checked==true)
	{
		//alert("Domicilio");
		if (esVacio(document.frm1.txtEnvDist.value))
		{	
			alert("Ingrese el Distrito del Destinatario");
			document.frm1.txtEnvDist.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtEnvDist.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtEnvDist.focus();
			return;
		}
		if (esVacio(document.frm1.txtEnvDire.value))
		{	
			alert("Ingrese la Dirección del Destinatario");
			document.frm1.txtEnvDire.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtEnvDire.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtEnvDire.focus();
			return;
		}
	}
	else
	//if(document.frm1.radEnvTipo[1].checked==true)
	{
		//alert("Ventanilla");
	}
	if(document.frm1.radEnvTipoPer[0].checked==true)
	{
		//alert("Desti Natural");
		if (esVacio(document.frm1.txtEnvApPa.value) && esVacio(document.frm1.txtEnvApMa.value) && esVacio(document.frm1.txtEnvNom.value))
		{
			alert("Debe ingresar los datos del Destinatario");
			document.frm1.txtEnvApPa.focus();
			return
		}
		if (document.frm1.txtEnvApPa.value.length<2)
		{
			alert("El Apellido Paterno del Destinatario debe tener al menos 2 caracteres");					
			document.frm1.txtEnvApPa.focus();
			return;
		}
		if (document.frm1.txtEnvApMa.value.length==1)
		{
			alert("El Apellido Materno del Destinatario debe tener al menos 2 caracteres");					
			document.frm1.txtEnvApMa.focus();
			return;
		}
		if (document.frm1.txtEnvNom.value.length<2)
		{
			alert("Los Nombres del Destinatario deben tener al menos 2 caracteres");					
			document.frm1.txtEnvNom.focus();
			return;
		}
	}
	else
	//if(document.frm1.radEnvTipoPer[1].checked==true)
	{
		//alert("Desti Juridica");
		if (esVacio(document.frm1.txtEnvRazSoc.value))
		{	
			alert("Ingrese la Razón Social del Destinatario");
			document.frm1.txtEnvRazSoc.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtEnvRazSoc.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtEnvRazSoc.focus();
			return;
		}
		if (document.frm1.txtEnvRazSoc.value.length<3)
		{	
			alert("La Razón Social del Destinatario debe tener al menos 3 caracteres");
			document.frm1.txtEnvRazSoc.focus();
			return;
		}
	}
<%if(perfilusuarioid!=Constantes.PERFIL_CAJERO)
{%>
	if(document.frm1.tipopago[0].checked==true)
	{
		//alert("VISA");
		if (esVacio(document.frm1.txtPan.value) || !esEntero(document.frm1.txtPan.value) || !esEnteroMayor(document.frm1.txtPan.value,1))
		{	
			alert("Debe ingresar un número de tarjeta válido.");
			document.frm1.txtPan.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtPan.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtPan.focus();
			return;
		}
		if(document.frm1.txtPan.value.length<16)
		{
			alert("El número de tarjeta debe tener 16 dígitos.");
			document.frm1.txtPan.focus();
			return;
		}
	}
	else
	//if(document.frm1.tipopago[1].checked==true)
	{
		//alert("Prepago");
	}
<%}
else
{%>
	if(document.frm1.tipopago[0].checked==true)
	{
		//alert("Efectivo");
	}
	else
	//if(document.frm1.tipopago[1].checked==true)
	{
		//alert("Cheque");
		if (esVacio(document.frm1.numcheque.value) || !esEntero(document.frm1.numcheque.value) || !esEnteroMayor(document.frm1.numcheque.value,1))
		{	
			alert("Debe ingresar un número de Cheque válido.");
			document.frm1.numcheque.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.numcheque.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.numcheque.focus();
			return;
		}
	}
	
<%}%>
	
	<%
		//inicio:dbravo:15/08/2007
		//descipcion: Cambio para el descuento de saldo de CREM, solo se puede enviar a domicilio si acepta las condiciones
		//			  del descuento en su saldo de linea.
		//			  Esta opción solo esta disponible para certificados CREM que sean creados por un perfil diferente a cajero
    	if((request.getAttribute("hidTipo") != null) && ( request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO) || 
    	    request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_ACTOS_VIGENTES)       ||
            request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)
           ) && (perfilusuarioid != Constantes.PERFIL_CAJERO)){
    %>
    		if(document.frm1.radEnvTipo[0].checked == true && document.frm1.flagAceptaCondicion.checked == false){
    			alert('Solo es posible enviar el certificado a domicilio si acepta el descuento a su saldo.\n En caso contrario se deberá recoger por ventanilla.');
    			return;
    		}
    <%
    	}
    	//fin:dbravo:15/08/2007
    %>
	
	/**** AGREGADO JBUGARIN DESCAJ 08/01/07****/
	<% 
	//String prueba= request.getAttribute("estadoActivo");
	//System.out.println("estado::"+estado);
	if ( request.getAttribute("estadoActivo")!=null && !((String)request.getAttribute("estadoActivo")).equals("2")) {
	
	%> 
	 var p = confirm("La partida asociada no se encuentra en estado ACTIVA. Esta seguro que desea realizar la solicitud?")
		if(p==true){
			document.frm1.action="/iri/Certificados.do?state=guardarDatosComplementarios";
			document.frm1.submit();
		}
	
	<%
	}else{
	%>
	document.frm1.action="/iri/Certificados.do?state=guardarDatosComplementarios";
	document.frm1.submit();
	/**** JBUGARIN FIN DESCAJ 08/01/07 ****/
	<%}%>
	
	
	
	
	
}
     
function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
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

<BODY onLoad="javascript:gEnvio('1');">
<script language="JavaScript">
<!--
	var startTime = new Date();

	// -->
</script>
<form name="frm1" method="post">
<br>
<table class=titulo cellspacing=0>
  <tr> 
      <td>
        <%
        if((request.getAttribute("hidTipo") != null) && (request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_RMC) ||request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_RMC)||
        request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_GRAVAMEN_RJB)||request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_DOMINIAL_RJB)||
        request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_ACTOS_VIGENTES) ||
        request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO))
         ){%>
         <font color=black>SERVICIOS &gt;&gt;</font>Solicitud de Certificados
        <%}
        else if(request.getAttribute("hidTipo") != null && request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_NEGATIVO)){%>
        <font color=black>SERVICIOS &gt;&gt;</font>Solicitud de Certificados Positivos / Negativos
        <%}else if(request.getAttribute("hidTipo") != null && request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_BUSQUEDA)){%>
        <font color=black>SERVICIOS &gt;&gt;</font>Solicitud de Certificados de Busqueda
        <%}else{%>
        <font color=black>SERVICIOS &gt;&gt;</font>Solicitud de Copia Literal
        <%} %>
	  </td>
	</tr>
</table>
<br>
<table class=formulario cellspacing=0>
	<tr>
	  <td> <font color=black> <strong> 
	  		<!-- Inicio: jascencio:12/06/2007
	  			CC:SUNARP-REGMOBCOM
	  		 -->
	        <%
	        if((request.getAttribute("hidTipo") != null) && (request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_NEGATIVO) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_RMC)
	        	||request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO) ||request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_DOMINIAL_RJB)
	        	||request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_GRAVAMEN_RJB) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)
	        	||request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_ACTOS_VIGENTES) )){
	        	if((request.getAttribute("codigoCertificado") != null) && (request.getAttribute("codigoCertificado").equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS) ||
	        	request.getAttribute("codigoCertificado").equals(Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA) ||
	        	request.getAttribute("codigoCertificado").equals(Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN)||
	        	request.getAttribute("codigoCertificado").equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_D)				
	        	)){
	        	%>	
	        	Usted ha solicitado un &quot;<bean:write name="beanObjSol" property="desc_certi" scope="session"/>&quot;<%=request.getAttribute("des1")%>
			    <%
	        	}
	        	else {
	        	%>
	        	Usted ha solicitado un &quot;<bean:write name="beanObjSol" property="desc_certi" scope="session"/>&quot; 
		        de la Persona <bean:write name="beanObjSol" property="desc_tipop" scope="session"/>: 
		        <logic:equal name="beanObjSol" property="tpo_pers" value="N">
	    	    &quot;<bean:write name="beanObjSol" property="nombres" scope="session"/>&nbsp;<bean:write name="beanObjSol" property="ape_pat" scope="session"/>&nbsp;<bean:write name="beanObjSol" property="ape_mat" scope="session"/>&quot;
            	</logic:equal>
            	<logic:notEqual name="beanObjSol" property="tpo_pers" value="N">
	            &quot;<bean:write name="beanObjSol" property="raz_soc" scope="session"/>&quot;
	            </logic:notEqual><%=request.getAttribute("des1")%>
	        <%
	        	}
	        %>
	        <!-- FIn:jascencio -->
	        
            
            <%}
            //<!-- Inicio:mgarate:04/06/2007 -->
            else if(request.getAttribute("hidTipo") != null && request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_BUSQUEDA)){%>
            Ud. ha solicitado un &quot;<bean:write name="beanObjSol" property="desc_certi" scope="session"/>&quot; 
            de <bean:write name="beanObjSol" property="criterioBusqueda" scope="session"/>
            <%}else{
            //Inicio:jascencio:09/08/2007
            	if(request.getAttribute("hidTipo") != null && request.getAttribute("hidTipo").equals(Constantes.COPIA_LITERAL_RMC)){%>
            	Ud. ha solicitado una &quot;<bean:write name="beanObjSol" property="desc_certi" scope="session"/>&quot; 
	            de la Partida: <bean:write name="beanObjSol" property="numeroPartida" scope="session"/> 
            <%}else{%>
	            Ud. ha solicitado una &quot;<bean:write name="beanObjSol" property="desc_certi" scope="session"/>&quot; 
	            de la Partida: <bean:write name="beanObjSol" property="numPartida" scope="session"/> 
            <%	}//Fin:jascencio %>
            <!-- Fin:mgarate:04/06/2007 -->
            
            
            <logic:equal name="beanObjSol" property="certificado_id" value="8">
            <logic:present name="beanObjSol" property="num_titu">
            <logic:notEqual name="beanObjSol" property="num_titu" value="">
            T&iacute;tulo: 
            </logic:notEqual>
            </logic:present>
            <logic:present name="beanObjSol" property="aa_titu">
            <logic:notEqual name="beanObjSol" property="aa_titu" value="">
            <bean:write name="beanObjSol" property="aa_titu" scope="session"/> - 
            </logic:notEqual>
            </logic:present>
            <logic:present name="beanObjSol" property="num_titu">
            <logic:notEqual name="beanObjSol" property="num_titu" value="">
            <bean:write name="beanObjSol" property="num_titu" scope="session"/>
            </logic:notEqual>
            </logic:present>
            Acto: &quot;<bean:write name="beanObjSol" property="desc_acto" scope="session"/>&quot;
            </logic:equal>
            que consta de <bean:write name="beanObjSol" property="num_pag" scope="session"/> PAGINA(S)
            <!-- Inicio:jascencio:09/08/2007 -->
            <logic:present name="beanObjSol" property="numeroPartidaAnterior">
			y que fue migrada de la partida: <bean:write name="beanObjSol" property="numeroPartidaAnterior" scope="session"/>
            que consta de <bean:write name="beanObjSol" property="numeroPaginasAnterior" scope="session"/> PAGINA(S)
            </logic:present>
            <!-- Fin:jascencio -->
            <%}%>
            <!-- Inicio:mgarate:04/06/2007 -->
            <%if((request.getAttribute("hidTipo") != null) && (request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_BUSQUEDA) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_NEGATIVO) ||
            	request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_RMC) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO) ||
            	request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_DOMINIAL_RJB) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_GRAVAMEN_RJB)
            	)){%>
            . Por favor, complete los datos de env&iacute;o y la forma de pago, y presione el bot&oacute;n &quot;Pagar&quot; para cancelar el monto de su solicitud. </strong> 
            <%}else{%>
            , en la Oficina Registral de <bean:write name="beanObjSol" property="desc_regis" scope="session"/>. Por favor, complete los datos de env&iacute;o y la forma de pago, y presione el bot&oacute;n &quot;Pagar&quot; para cancelar el monto de su solicitud.  
            <%}
            %>
			<!-- Fin:mgarate:04/06/2007 -->        
        </font> </td>
  </tr>
</table>
<br>  

<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>1. DATOS DEL SOLICITANTE</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="150"><strong>TIPO DE PERSONA</strong></td>
      <td width="133"> <input type="radio" name="radSolTipPers" value="N" onClick="javascript:gSoli(1);" checked> 
        <strong>Persona Natural</strong> 
      </td>
      <td width="133"> <input type="radio" name="radSolTipPers" value="J" onClick="javascript:gSoli(0);">
        <strong>Persona Jur&iacute;dica</strong> 
      </td>
      <td width="133">&nbsp; </td>
      
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
    <tr> 
      <td colspan="5">
      <div id="areaSoliNatu" style="visibility:visible;display:">
        <table>
          <tr> 
            <td>&nbsp;</td>
            <td width="150"><strong>APELLIDOS Y NOMBRES<br>De quien realiza la solicitud</strong></td>
            <td>
              <input type="text" name="txtSolApPa" size="20" maxlength="18" style="width:133" onBlur="sololet(this)" value=""> 
            </td>
            <td width="133">
              <input type="text" name="txtSolApMa" size="20" maxlength="18" style="width:133" onBlur="sololet(this)" value=""> 
            </td>
            <td width="133">
              <input type="text" name="txtSolNom" size="20" maxlength="18" style="width:133" onBlur="sololet(this)" value=""> 
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
              &nbsp;Apellido Paterno
            </td>
            <td>
              &nbsp;Apellido Materno
            </td>
            <td>
              &nbsp;Nombres
            </td>
          </tr>
        </table>
      </div>  
      <div id="areaSoliJuri" style="visibility:hidden;display:none">  
        <table>
          <tr> 
            <td>&nbsp;</td>
            <td width="150"><strong>RAZON SOCIAL<br>De quien realiza la solicitud</strong></td>
            <td colspan="3"><input type="text" name="txtSolRazSoc" size="60" maxlength="50" style="width:407" onBlur="sololet(this)" value=""></td>
          </tr>
        </table>
      </div>  
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="150"><strong>TIPO DE DOCUMENTO</strong></td>
      <td> 
	    <select name="cboSolTipDoc">
			<logic:iterate name="arrDocu" id="item1" scope="request">
			  <option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
			</logic:iterate>
		</select>
		
	  </td>
      <td width="133"> <strong>NUMERO DE DOCUMENTO</strong> </td>
      <td width="133"> <input type="text" name="txtSolNumDoc" size="12" maxlength="11" style="width:87" onBlur="sololet(this)" value=""> 
      </td>
      
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
  </table>
  
<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>2. DATOS DEL ENVIO</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0>
    <tr>
    <td width="5">&nbsp;</td>
	<td width="133">&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
      <td width="150"><strong>FORMA DE ENVIO</strong></td>
      <td width="130"> 
        <input type="radio" name="radEnvTipo" value="D" onClick="javascript:gEnvio('1');" checked>
        <strong>Env&iacute;o a Domicilio</strong>
      </td>
	  <td width="140"> 
        <input type="radio" name="radEnvTipo" value="V" onClick="javascript:gEnvio('0');"> 
		<strong>Recojo en Ventanilla</strong>
	  </td>
	  <td width="120">&nbsp; </td>
    
  </tr>
  <tr>
    <td colspan="5">

  
<div id="areaDomi" style="visibility:hidden;display:none">
<table cellspacing=0>

  <tr>
    <td>&nbsp;</td>
	  <td>&nbsp;<!--strong>PAIS</strong--></td>
	  <td>
	    <input type="hidden" name="hidEnvPais" value="01">
		<!--SELECT name=cboEnvPais onchange=llenaDepProv() width="187" style="width:187">
		<logic:iterate name="arr2" id="item2" scope="request">
		<logic:equal name="item2" property="codigo" value="01">
			<option value="<bean:write name="item2" property="codigo"/>" selected> <bean:write name="item2" property="descripcion"/> </option>
		</logic:equal>
		<logic:notEqual name="item2" property="codigo" value="01">
			<option value="<bean:write name="item2" property="codigo"/>"> <bean:write name="item2" property="descripcion"/> </option>
		</logic:notEqual>
		</logic:iterate>
		 </SELECT--> 
	  </td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
  </tr><tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	  <td width="150">&nbsp;<strong>DEPARTAMENTO</strong></td>
	  <td width="180">
	    <SELECT name=cboEnvDpto onchange=llenaComboHijo(); width="187" style="width:187" >
		<logic:iterate name="arr3" id="item3" scope="request">
		<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
		</logic:iterate>
		</SELECT>
	  </td>
	  <td width="100"><!--strong>OTRO</strong--></td>
	  <td width="150"><input type="hidden" name="hidEnvOtro" value=""><!--input type="text" name="txtEnvOtro" onBlur="sololet(this)"--></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	  <td width="150">&nbsp;<strong>PROVINCIA</strong></td>
	  <td width="180"> 
        <select name="cboEnvProv" width="187" style="width:187">
	    </select>
	</td>
	  <td width="100"><strong>DISTRITO</strong></td>
	  <td width="150">
	    <input type="text" name="txtEnvDist" onBlur="sololet(this)">
	  </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	  <td width="150">&nbsp;<strong>DIRECCION</strong></td>
	  <td width="180"><input name="txtEnvDire" type="text" onBlur="sololet(this)" maxlength="90"></td>
	  <td width="100"><strong>COD POSTA</strong>L</td>
	  <td width="150"><input name="txtEnvCodPost" type="text" onBlur="sololet(this)"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
</table>
</div>
<div id="areaVent" style="visibility:visible;display:">	
<table cellspacing=0>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
      <td><strong>OFICINA REGISTRAL</strong></td>
      <td width="150"> 
        <select size="1" name="cboOficinas" onChange="frm1.hidDestiOficDesc.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text">
          <option value="10|07">Andahuaylas</option>
          <option value="06|02">Apurimac</option>
          <option value="03|01">Arequipa</option>
          <option value="10|05">Ayacucho</option>
          <option value="01|06">Barranca</option>
          <option value="11|04">Bagua</option>
          <option value="01|02">Callao</option>
          <option value="11|02">Cajamarca</option>
          <option value="03|02">Caman&aacute;</option>
          <option value="01|05">Ca&ntilde;ete</option>
          <option value="04|02">Casma</option>
          <option value="03|03">Castilla</option>
          <option value="11|05">Chachapoyas</option>
          <option value="08|02">Chep&eacute;n</option>
          <option value="11|01">Chiclayo</option>
          <option value="04|03">Chimbote</option>
          <option value="10|02">Chincha</option>
          <option value="11|06">Chota</option>
          <!--option value="13|01">Coronel portillo</option-->
          <option value="06|01">Cusco</option>
          <option value="01|04">Huacho</option>
          <option value="08|03">Huamachuco</option>
          <option value="10|08">Huancavelica</option>
          <option value="02|01">Huancayo</option>
          <option value="10|06">Huanta</option>
          <option value="02|02">Hu&aacute;nuco</option>
          <option value="01|03">Huaral</option>
          <option value="04|01">Huaraz</option>
          <option value="10|01">Ica</option>
          <option value="07|02">Ilo</option>
          <option value="09|01">Iquitos</option>
          <option value="03|04">Islay</option>
          <option value="11|03">Ja&eacute;n</option>
          <option value="12|03">Juanju&iacute;</option>
          <!--option value="02|03">La Unión</option-->
          <option value="07|03">Juliaca</option>
          <option value="02|06">La Merced</option>          
          <option value="01|01">Lima</option>
          <option value="06|03">Madre de dios</option>
          <option value="07|04">Moquegua</option>
          <option value="12|01">Moyobamba</option>
          <option value="10|04">Nazca</option>
          <option value="08|04">Otuzco</option>
          <option value="02|04">Pasco</option>
          <option value="10|03">Pisco</option>
          <option value="05|01">Piura</option>
          <option value="13|01">Pucallpa</option>          
          <option value="07|05">Puno</option>
          <option value="06|04">Quillabamba</option>
          <option value="08|05">San pedro de lloc</option>
          <option value="02|05">Satipo</option>
          <!--option value="02|06">Selva Central</option-->
          <option value="06|05">Sicuani</option>
          <option value="05|02">Sullana</option>
          <option value="07|01">Tacna</option>
          <option value="12|02">Tarapoto</option>
          <option value="02|07">Tarma</option>
          <option value="02|08">Tingo Mar&iacute;a</option>
          <option value="08|01">Trujillo</option>
          <option value="05|03">Tumbes</option>
          <option value="09|02">Yurimaguas</option>
        </select>
        <input type="hidden" name="hidDestiOficDesc" value="">
      </td>
      <td colspan="2">
	    <a href="javascript:Abrir_Ventana('/iri/acceso/mapas/MAPA1.htm','Oficinas_Registrales','',500,600)" onmouseover="javascript:mensaje_status('Identifique su Oficina Resgistral');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	      Identifique su Oficina Registral
	    </a>
	  </td>
	  
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td width="130">&nbsp;</td>
	  <td width="130">&nbsp;</td>
	  <td width="130">&nbsp;</td>
  </tr>
</table>
</div>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
      <td width="150"><strong>TIPO DE PERSONA</strong></td>
      <td width="130"> 
        <input type="radio" name="radEnvTipoPer" value="N" onClick="javascript:gDest('1');" checked>
        <strong>Persona Natural</strong>
      </td>
	  <td width="130"> 
        <input type="radio" name="radEnvTipoPer" value="J" onClick="javascript:gDest('0');"> <strong>Persona Jur&iacute;dica</strong>
	  </td>
	  <td width="130">&nbsp; </td>
    
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5">
    <div id="areaDestNatu" style="visibility:visible;display:">
      <table>
        <tr>
          <td>&nbsp;</td>
            <td width="150"><strong>APELLIDOS Y NOMBRES<br>De quien recibirá la solicitud</strong></td>
            <td width="130"> 
              <input type="text" name="txtEnvApPa" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value="">
      	  </td>
      	  <td width="130"> 
              <input type="text" name="txtEnvApMa" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value="">
      	  </td>
      	  <td width="130"> 
              <input type="text" name="txtEnvNom" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value="">
      	  </td>
            
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
              &nbsp;Apellido Paterno
            </td>
            <td>
              &nbsp;Apellido Materno
            </td>
            <td>
              &nbsp;Nombres
            </td>
          </tr>
      </table>
    </div>
    <div id="areaDestJuri" style="visibility:hidden;display:none">  
    
      <table>
        <tr>
          <td>&nbsp;</td>
            <td width="150"><strong>RAZON SOCIAL<br>De quien recibirá la solicitud</strong></td>
            <td colspan="3"><input type="text" name="txtEnvRazSoc" size="60" maxlength="50" style="width:407" onblur="sololet(this)" value=""></td>
            
        </tr>
      </table>
    </div>  
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
	  <td width="150">&nbsp;</td>
  </tr>
  
</table>
<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>3. DATOS DEL PAGO</strong></td>
  </tr>
</table>
  <table class=formulario cellspacing=0>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="300">&nbsp;</td>
      <td width="280">&nbsp;</td>
    </tr>
    <%
    	if( ((request.getAttribute("hidTipo") != null) && (request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO) || 
    	    request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_ACTOS_VIGENTES)       ||
            request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)
           )) && (perfilusuarioid != Constantes.PERFIL_CAJERO)){
    %>
	    <tr> 
	      <td width="5">&nbsp;</td>
	      <td width="580" colspan="2">
	      	<strong>"Se le cobrará el costo de la primera pagina del certificado en el momento de pagar.  Acepta que se le <BR>
	      	         descuente el resto del costo cuando se atienda su certificado? (En caso de no aceptar, solo se podrá recoger
	      	         el certificado por ventanilla)<BR>
					 _ Si acepto."</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="flagAceptaCondicion" value="1"/>
					 <BR>
	      </td>
	    </tr>
    <%
    	}
     %>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="300"><strong>COSTO DEL SERVICIO</strong></td>
      <td width="280" align="center">
        S/. <input type="text" name="txtDisServ" value="<bean:write name="beanObjSol" property="subTotal" scope="session"/>" size="12" maxlength="12" readonly>
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="300"><strong>MENSAJERÍA REGISTRAL (en caso de env&iacute;o a domicilio)</strong></td>
      <td width="280" align="center">
        S/. <input type="text" name="txtDisEnv" value="" size="12" maxlength="12" readonly>
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="300"><strong>TOTAL</strong></td>
      <td width="280" align="center">
        S/. <input type="text" name="txtMonto" value="" size="12" maxlength="12" readonly>
        <input type="hidden" name="monto" value="">
      </td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="300">&nbsp;</td>
      <td width="280">&nbsp;</td>
    </tr>
    <tr> 
    	<td colspan="3">
    		<div id="areaCajero" style="visibility:visible;display:">
<!-- Inicio:jascencio:18/09/2007 CC:SUNARP-REGMOBCON-2006 -->    		
<%if(perfilusuarioid == Constantes.PERFIL_CAJERO){
	if(request.getAttribute("hidTipo") != null && (request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)|| 
	request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_ACTOS_VIGENTES) || request.getAttribute("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO))){

%>    		
    		<table>
    			<tr>
    			  <td width="5">&nbsp;</td>
			      <td>Debe informar al cliente que sólo se enviará a su domicilio el certificado si la búsqueda retorna sólo una página,
		          caso contrario tendrá que acercarse a Caja a cancelar la diferencia.
			      </td>
			      <td width="10">&nbsp;</td>
			     <tr>
		     </table>
<%		}
	} %>  		     
			</div>	
    	</td>
    </tr>	
<!-- Fin:jascencio -->    		    
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="300">&nbsp;</td>
      <td width="280">&nbsp;</td>
    </tr>
  </table>
<%if(perfilusuarioid!=Constantes.PERFIL_CAJERO)
{%>
    
  <table class=formulario cellspacing=0>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="150"></td>
      <td width="150"></td>
      <td width="150">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="150"><strong>FORMA DE PAGO</strong></td>
      <td width="130"> <input type="radio" name="tipopago" value="<%=Constantes.PAGO_TARJETA_DE_CREDITO%>" onClick="javascript:invisible_navegador(navegador,'area3','narea3','v');"> 
        <strong>EN LINEA CON TARJETA VISA</strong> </td>
      <td width="130"> <input type="radio" name="tipopago" value="<%=Constantes.PAGO_LINEA_PREPAGO%>" onClick="javascript:invisible_navegador(navegador,'area3','narea3','i');" checked> 
        <strong>EN LINEA CON MI SALDO DISPONIBLE</strong> </td>
      <td width="130"> 
        <div align="center">
          <a href="javascript:Validar();" onmouseover="javascript:mensaje_status('Pagar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
            <IMG src="images/btn_pagar.gif" border="0">
          </A> 
        </div>
      </td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="130"></td>
      <td width="130"></td>
      <td width="130">&nbsp;</td>
    </tr>
  </table>
<layer id="narea3">
  <div id="area3" style="visibility: hidden;">	
  <table class=formulario cellspacing=0>
	<tr> 
      <td width="5">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="130"></td>
      <td width="130"></td>
      <td width="130">&nbsp;</td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133" rowspan="2" valign="center">
        <div align="center">
          <a href="javascript:popVisa();">
            <img src="images/verVisa.gif">
          </a>
          <!--img src="images/verVisa.gif" width="102" height="84"-->
        </div>
      </td>
      <td width="150">Ingrese el n&uacute;mero de su Tarjeta VISA:</td>
      <td width="150"><input name="txtPan" type="text" size="18" maxlength="16" onBlur="sololet(this)"></td>
      <td width="150" rowspan="2" valign="center">
        <div align="center">
          <a href = "http://www.visanet.com.pe" target="_blank">
            <img src="images/visagrande.jpg" width="109" height="70" vspace="6">
          </a>
          <!--img src="images/visagrande.jpg" width="109" height="70"-->
        </div>
      </td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="150">Ingrese la fecha de vencimiento:</td>
      <td width="150"> 
	    <select name="cboMonth">
          <option value="1">Enero</option>
          <option value="2">Febrero</option>
          <option value="3">Marzo</option>
          <option value="4">Abril</option>
          <option value="5">Mayo</option>
          <option value="6">Junio</option>
          <option value="7">Julio</option>
          <option value="8">Agosto</option>
          <option value="9">Septiembre</option>
          <option value="10">Octubre</option>
          <option value="11">Noviembre</option>
          <option value="12">Diciembre</option>
        </select>
        <select name="cboYear">
        <!-- Inicio:rbahamonde:30/12/2008 Cambio de Año -->
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
        <!-- Fin:rbahamonde -->
        </select> </td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>
</table>
  </div>	
</layer>	

<%}
else
{%>

<table class=formulario cellspacing=0>
  <tr>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <th width="20%" height="11" colspan="2">TIPO DE PAGO</th>
    <td width="20%" height="11" colspan="2"><input type="radio" value="E|Efectivo" name="tipopago" checked onClick="ShowCheques(this.value)">Efectivo&nbsp; <input type="radio" value="C|Cheque" name="tipopago" onClick="ShowCheques(this.value)">Cheque&nbsp;</td>
    <td width="20%" height="11" colspan="2"><p align="left">&nbsp;</p></td>
    <td width="20%" height="11" colspan="2">
      <input type="hidden" name="T2">
      <input type="hidden" name="usuario" value="<%=Comodin.getInstance().getUsuario()%>">
      <input type="hidden" name="nombre" value="<%=Comodin.getInstance().getUsuario()%>">
      <input type="hidden" name="contratoId" value="<%=Comodin.getInstance().getContratoAbono()%>">
    </td>
    <td width="20%" height="11" colspan="2">
      <div align="center">
        <a href="javascript:Validar();" onmouseover="javascript:mensaje_status('Pagar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
          <IMG src="images/btn_pagar.gif" border="0">
        </A> 
      </div>
    </td>
  </tr>
  <tr>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
</table>
<input type="hidden" name="linea" value="<%=Constantes.COMODIN_LINEA_PREPAGO%>">
<div id="cheques" style="visibility:hidden">
<table class="formulario">
  <tr>
    <th width="19%">BANCO</th>
    <td width="34%">
    <select size="1" name="bancoId">
		<logic:iterate name="listaBancos" id="item" scope="request">
	          <option value="<bean:write name="item" property="id"/>|<bean:write name="item" property="descripcion"/>"><bean:write name="item" property="descripcion"/></option>
		</logic:iterate>
    </select>
    </td>
    <th width="13%">TIPO DE CHEQUE</th>
    <td width="12%">
    <select size="1" name="tipocheque">
        <option value="G|Gerencia" selected>Gerencia</option>
        <option value="S|Simple" >Simple</option>
        <option value="C|Certificado" >Certificado</option>
    </select>
    </td>
    <td width="30%"></td>
  </tr>
  <tr>
    <th width="19%">NUMERO DE CHEQUE</th>
    <td width="34%"><input type="text" name="numcheque"  maxlength="15" size="20"></td>
    <td width="13%"></td>
    <td width="12%"></td>
    <td width="30%"></td>
  </tr>
</table>
</div>
<%}%>
<br>
<table class=tablasinestilo>
  <tr>
  	<td width="50%" align="left">
  	  <div id="HOJA2"> 
  	    <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
  	      <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
  	    </a>
  	  </div>
  	</td>
	<td width="50%" align="right">
	  <div id="HOJA3">	
	    <a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	      <IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0>
	    </a>
	  </div>
	</td>
  </tr>
</table>



</form>
<script LANGUAGE="JavaScript">
	llenaComboHijo();
	window.top.frames[0].location.reload();
</script>
<% if (perfilusuarioid != Constantes.PERFIL_CAJERO) 
	{ 
	DatosUsuarioBean datosUsuarioBean = (DatosUsuarioBean) request.getAttribute("DATOS_FORMULARIO");
	%>
<script LANGUAGE="JavaScript">
	document.frm1.txtSolApPa.value  = "<%=datosUsuarioBean.getApellidoPaterno()%>";
	document.frm1.txtSolApMa.value  = "<%=datosUsuarioBean.getApellidoMaterno()%>";
	document.frm1.txtSolNom.value          = "<%=datosUsuarioBean.getNombres()%>";
	doCambiaCombo(document.frm1.cboSolTipDoc, "<%=datosUsuarioBean.getTipoDocumento()%>");
	document.frm1.txtSolNumDoc.value           = "<%=datosUsuarioBean.getNumDocumento()%>";
	document.frm1.hidEnvPais.value=      "<%=datosUsuarioBean.getPais()%>";
	doCambiaCombo(document.frm1.cboEnvDpto, "<%=datosUsuarioBean.getDepartamento()%>");
	document.frm1.hidEnvOtro.value = "<%=datosUsuarioBean.getOtroDepartamento()%>";
	llenaComboHijo();
	doCambiaCombo(document.frm1.cboEnvProv, "<%=datosUsuarioBean.getProvincia()%>");
	document.frm1.txtEnvDist.value         = "<%=datosUsuarioBean.getDistrito()%>";
	document.frm1.txtEnvDire.value        = "<%=datosUsuarioBean.getDireccion()%>";
	document.frm1.txtEnvCodPost.value        = "<%=datosUsuarioBean.getCodPostal()%>";
	doCambiaCombo(document.frm1.cboOficinas, "<%=request.getAttribute("regPubId")%>|<%=request.getAttribute("oficRegId")%>");
	document.frm1.txtEnvApPa.value  = "<%=datosUsuarioBean.getApellidoPaterno()%>";
	document.frm1.txtEnvApMa.value  = "<%=datosUsuarioBean.getApellidoMaterno()%>";
	document.frm1.txtEnvNom.value          = "<%=datosUsuarioBean.getNombres()%>";
</script>
<%}%>
<script LANGUAGE="JavaScript">
	frm1.hidDestiOficDesc.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text;
</script>
<br>
</BODY>
</HTML>
