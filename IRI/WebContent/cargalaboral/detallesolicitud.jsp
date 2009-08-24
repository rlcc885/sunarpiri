
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>

<html>
<head>
<title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<!--meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"-->
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">

//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;
	//inicio:dbravo:06/08/2007
	//descripcion: función que se encarga de reemplazar los caracteres de salto de linea y espacio en blanco de un 
	//             textarea a un formato html  
	function convertirFormatoHTML(cadena){
		
		var temp = cadena;
	    var indice = 0;	
	    var continuar = true;
	    var lineas = 0;
	    for ( ; continuar; ){ 
	    	indice = temp.indexOf(' ',indice);
	        if(indice<0){
	        	break;
	        }else{
	        	temp = temp.substring(0,indice)+'&nbsp;'+temp.substring(indice+1,temp.length);
				indice = indice + 1;
	        }
	    }
	    indice = 0;
	    for ( ; continuar; ){ 
	    	indice = temp.indexOf('\n',indice);
	        if(indice<0){
	        	break;
	        }else{
	        	lineas=lineas+1;
	        	temp = temp.substring(0,indice)+'<br>'+temp.substring(indice+1,temp.length);
				indice = indice + 1;
	        }
	    }
	    document.frm1.cantidadLineas.value=lineas
	    
		return temp;
		
	}
	//fin:dbravo:06/08/2007
	// inicio:jrosas 10-08-07
	function convertirFormatoBD(cadena){
		var temp = cadena;
	    var indice = 0;	
	    var continuar = true;
	    var lineas = 1;
	    
	    indice = 0;
	    for ( ; continuar; ){ 
	    	indice = temp.indexOf('\n',indice);
	        if(indice<0){
	        	break;
	        }else{
	        	lineas=lineas+1;
	        	temp = temp.substring(0,indice)+'<br>'+temp.substring(indice+1,temp.length);
				indice = indice + 1;
	        }
	    }
	    document.frm1.cantidadLineas.value=lineas
	    
		return temp;

	}
	// fin:jrosas 10-08-07
	function BuscarSol(ApePat, ApeMat, Nombre, ofic_reg_desc){
	
			//document.frm1.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNatural";
			window.open("/iri/BuscaPartidasXIndices.do?state=buscarPersonaNatural&area1ApePat="+ApePat+"&area1ApeMat="+ApeMat+"&area1Nombre="+Nombre+"&ofic_reg_desc="+ofic_reg_desc+"&area1Razon='23000'& hid2= '1' & area1Siglas='MAS' ,fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no");
			
	}


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
     

	
</SCRIPT>
<!--script Language="JavaScript">
	function VentanaFlotante(pag)
		{
		var ancho= 500;
		var alto= 563;
		NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
		}
</script-->

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<script language="JavaScript">
<!--
	var startTime = new Date();

	// -->

function corta(i)
{
	if (event.keyCode == 13) event.returnValue = false;
	
	if ((i==1)&&(document.frm1.Comentario.value.length>120))
		document.frm1.Comentario.value=document.frm1.Comentario.value.substring(0,120);
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol1" scope="request">
					<logic:equal name="objsol1" property="tpo_cert" value="N">  
						<logic:equal name="objsol1" property="certificado_id" value="18">
								if ((i==2)&&(document.frm1.Constancia2.value.length>120))
									document.frm1.Constancia2.value=document.frm1.Constancia2.value.substring(0,120);			
						</logic:equal>
						<logic:notEqual name="objsol1" property="certificado_id" value="18">
								if ((i==2)&&(document.frm1.Constancia.value.length>120))
									document.frm1.Constancia.value=document.frm1.Constancia.value.substring(0,120);			
						</logic:notEqual>
					</logic:equal>
					<logic:notEqual name="objsol1" property="tpo_cert" value="N">  
							if ((i==2)&&(document.frm1.Constancia.value.length>120))
									document.frm1.Constancia.value=document.frm1.Constancia.value.substring(0,120);			
					</logic:notEqual>
					
			</logic:iterate>
}
function limita_largo(max)
{
	if (document.frm1.titulos_temp.value.length>max)
			document.frm1.titulos_temp.value=document.frm1.titulos_temp.value.substring(0,max);	
}

function doBuscar(param1, param2)
{

document.frm2.comboAreaLibro.value=document.frm1.comboAreaLibro.value;
if (param1 == 1)
{
	if (param2==1)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNatural";
	}
	if (param2==2)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaJuridica";
	}
}
if (param1 == 2)
{
	if (param2==1)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNatural";
	}
	if (param2==2)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaJuridica";				
	}
	if (param2==3)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarRazonSocial";
	}
}
if (param1 == 3)
{
	if (param2==1)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNatural";
	}
	if (param2==2)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaJuridica";
	}
	if (param2==3)
	{	
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPredio";
	}
	if (param2==41)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarMineria";
	}
	if (param2==51)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarEmbarcacion";
	}
	if (param2==61)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarBuque";
	}		
	if (param2==7)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarAeronaveXMatricula";
	}
	if (param2==8)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarAeronaveXNombre";
	}
	if (param2==9)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarAeronaveXRazonSocial";
	}
}
if (param1 == 4)
{
	if (param2==1)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarXNombreVehicular&tipo=1";
	}
	if (param2==2)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarXNombreVehicularJuri&tipo=2";
	}
	if (param2==3)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarXNombreVehicularMotor&tipo=2";
	}
	if (param2==4)
	{
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarXNombreVehicularChasis&tipo=2";
	}
}
//Inicio:mgarate:10-08-2007
if(param1 == 5)
{
	if(param2==1)
	{
		var placa = document.frm2.numplaca.value;
		var partida;
		if(document.frm2.numpart.value!=null && document.frm2.numpart.value!="")
		{
			partida = document.frm2.numpart.value;
		}else if(document.frm2.numeroPartida.value!=null && document.frm2.numeroPartida.value!=" ")
		{
			partida = document.frm2.numeroPartida.value;
		}
		if(placa!=null && placa!="")
		{
			document.frm2.radBuscar2.value="";
			document.frm2.txt4.value=document.frm2.numplaca.value;
		}
		else if(partida!=null && partida!="")		
		{
			document.frm2.radBuscar2.value="P";
			document.frm2.txt4.value=partida;	
		}
		if(document.frm2.comboAreaLibro.value=="17")
		{
			document.frm2.comboAreaLibro.value="6";
		}
		document.frm2.action="/iri/Publicidad.do?state=buscarXPlaca&tipo=1";
	}
	if(param2==2)
	{
		var matricula = document.frm2.numMatricula.value;
		var partida;
		if(document.frm2.numpart.value!=null && document.frm2.numpart.value!="")
		{
			partida = document.frm2.numpart.value;
		}else if(document.frm2.numeroPartida.value!=null && document.frm2.numeroPartida.value!=" ")
		{
		
			partida = document.frm2.numeroPartida.value;
		}
		if(partida!=null && partida!="")
		{
			document.frm2.txt1.value = partida;
			document.frm2.action="/iri/Publicidad.do?state=buscarXNroPartida&tipo=1";
		}else if(matricula!=null && matricula!="")
		{
			document.frm2.area3EmbarcacionNumeroMatricula.value = matricula;
			document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarEmbarcacion&tipo=1";
		}
	}
	if(param2==3)
	{
		var partida;
		var matricula = document.frm2.numMatricula.value;
		if(document.frm2.numpart.value!=null && document.frm2.numpart.value!="")
		{
			partida = document.frm2.numpart.value;
		}else if(document.frm2.numeroPartida.value!=null && document.frm2.numeroPartida.value!=" ")
		{
		
			partida = document.frm2.numeroPartida.value;
		}
		if(partida!=null && partida!="")
		{
			document.frm2.txt1.value = partida;
			document.frm2.hidAreaLibro.value = "Propiedad Inmueble No Predial";
			document.frm2.action="/iri/Publicidad.do?state=buscarXNroPartida&tipo=1";
		}else if(matricula!=null && matricula!="")
		{
			document.frm2.area3EmbarcacionNumeroMatricula.value = matricula;
			document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarBuque&tipo=1";
		}
	}
	if(param2==4)
	{
		var partida;
		if(document.frm2.numpart.value!=null && document.frm2.numpart.value!="")
		{
			partida = document.frm2.numpart.value;
		}else if(document.frm2.numeroPartida.value!=null && document.frm2.numeroPartida.value!=" ")
		{
		
			partida = document.frm2.numeroPartida.value;
		}
		var matricula = document.frm2.numMatricula.value;
		var serie = document.frm2.numSerie.value;
		if(partida!=null && partida!="")
		{
			document.frm2.txt1.value = partida;
			document.frm2.action="/iri/Publicidad.do?state=buscarXNroPartida&tipo=1";
		}else if(matricula!=null && matricula!="")
		{
			document.frm2.area3EmbarcacionNumeroMatricula.value = matricula;
			document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarAeronaveXMatricula&tipo=1";
		}
		else if(serie!=null && serie!="")
		{
			document.frm2.txtNumeroSerieArea15.value = serie;
			document.frm2.tipo.value="B";
			document.frm2.comboAreaLibro.value="28";
			document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarBienesSigc";
		}
	}
}if(param1==6)
{
	if(param2==1)
	{
		document.frm2.action="/iri/Publicidad.do?state=buscarXTomoFolio&tipo=1";
	}
	if(param2==2)
	{
		document.frm2.txt1.value = document.frm2.numeroFicha.value;
		document.frm2.action="/iri/Publicidad.do?state=buscarXFicha&tipo=1";
	}
	if(param2==3)
	{
		document.frm2.action="/iri/Publicidad.do?state=buscarXPlaca&tipo=1";
	}
	if(param2==4)
	{
		document.frm2.action="/iri/Publicidad.do?state=buscarXPlaca&tipo=1";
	}
}

//Fin:mgarate

/*** inicio: jrosas 10-08-07 **/
if (param1 == 7)
{
	if (param2==1)
	{	
	    document.frm2.tipo.value="N";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNaturalSigc";
	}
	if (param2==2)
	{
	    document.frm2.tipo.value="J";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaJuridicaSigc";
	}
	
}
/*** fin: jrosas 10-08-07 **/	
// Inicio: ifigueroa 14/08/2007
if (param1 == 8)
{
	if (param2==1)
	{	
		document.frm2.tipo.value="B";
	   	document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarBienesRmc";
	}
	
}
if (param1 == 9)
{
	if (param2==1)
	{	
	    document.frm2.tipo.value="N";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNaturalRmc";
	}
	if (param2==2)
	{
	    document.frm2.tipo.value="J";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaJuridicaRmc";
	}
	if (param2==3)
	{
	    document.frm2.tipo.value="D";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarTipoNumeroDocumentoRmc";
	}
	
}
if (param1 == 10)
{
	if (param2==1)
	{	
	    document.frm2.tipo.value="N";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaNaturalRmc";
	}
	if (param2==2)
	{
	    document.frm2.tipo.value="J";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarPersonaJuridicaRmc";
	}
	if (param2==3)
	{
	    document.frm2.tipo.value="D";
		document.frm2.action="/iri/BuscaPartidasXIndices.do?state=buscarTipoNumeroDocumentoRmc";
	}
	
}
// Fin: ifigueroa 14/08/2007
document.frm2.hid2.value = param1;
	var numOfElements = document.frm2.elements.length; 
	var urlIndices = "";
	for (var i=0; i<numOfElements; i++) 
	{
		if(urlIndices.length>0){
			urlIndices = urlIndices + "&";
		}
		urlIndices = urlIndices + document.frm2.elements[i].name + "=" + document.frm2.elements[i].value;
	}
	urlIndices = document.frm2.action + "&" + urlIndices
	ventana=window.open(urlIndices,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
}	

function CertificadoPorVerificar(){
		tipocert = document.frm1.radioValor.value;				 		
		if (tipocert=='N'||tipocert=='P'){
			// inicio: ifigueroa 09/08/2007
	   <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol2" scope="request">	
			<logic:equal name="objsol2" property="certificado_id" value="18">
				solid = document.frm1.sol_id.value;
				constancia_temp = document.frm1.Constancia_temp.value;
				titulos_temp=document.frm1.titulos_temp.value;
				var cadena = convertirFormatoHTML(constancia_temp);
				cadTitulos=convertirFormatoHTML(titulos_temp);
		
				document.frm1.Constancia2.value = cadena;
				document.frm1.txtTitulos.value = cadTitulos;
				constancia = "";			
				window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=850');		
			</logic:equal>
			<logic:notEqual name="objsol2" property="certificado_id" value="18">				
				solid = document.frm1.sol_id.value;
				constancia = document.frm1.Constancia.value;
				window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');
			</logic:notEqual>
		</logic:iterate>
		// fin: ifigueroa 09/08/2007
		}
		else if(document.frm1.tpo_cert.value=="N"){
			alert ("Seleccionar un tipo de certificado Positivo o Negativo");
		}
		//Inicio:jascencio:25/06/2007
		else if(tipocert=='V'){
			solid = document.frm1.sol_id.value;
			//Ininico: ifigueroa 13/08/2007
			constancia_temp = document.frm1.Constancia_temp.value;
			titulos_temp=document.frm1.titulos_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			cadTitulos=convertirFormatoHTML(titulos_temp);
			
			document.frm1.Constancia2.value = cadena;
			document.frm1.txtTitulos.value = cadTitulos;
			constancia = "";			
			//Fin: ifigueroa 13/08/2007
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');
		
		}
		else if(document.frm1.tpo_cert.value=='RMCV'){
			alert("Seleccionar Tipo de Certificado Vigencia");
		}
		else if(tipocert=='G'){
			solid = document.frm1.sol_id.value;
			//Inicio: ifigueroa 13/08/2007
			constancia_temp = document.frm1.Constancia_temp.value;
			titulos_temp=document.frm1.titulos_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			cadTitulos=convertirFormatoHTML(titulos_temp);
			
			document.frm1.Constancia2.value = cadena;
			document.frm1.txtTitulos.value = cadTitulos;
			constancia = "";			
			//Fin: ifigueroa 13/08/2007
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');
		}
		else if(document.frm1.tpo_cert.value=='RMCG'){
			alert("Seleccionar Tipo de Certificado Gravamen");
		}
		// inicio: jrosas 15-08- 07
		else if(tipocert=='A'){  // crem - actos vigentes
			solid = document.frm1.sol_id.value;
			constancia_temp = document.frm1.Constancia_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			//constancia = document.frm1.Constancia2.value;
			document.frm1.hidVisualizaConstancia.value = cadena;
			constancia = "";	
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');		
		}
		else if(document.frm1.tpo_cert.value=='CREMA'){
			alert("Seleccionar Tipo de Certificado Registral Mobiliario");
		}
		else if(tipocert=='H'){  // crem - Historico
			solid = document.frm1.sol_id.value;
			constancia_temp = document.frm1.Constancia_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			//document.frm1.Constancia2.value = cadena;
			document.frm1.hidVisualizaConstancia.value = cadena;
			constancia = "";			
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=850');		
		}
	    // fin: jrosas 15-08- 07
		else if(document.frm1.tpo_cert.value=='CREMH'){
			alert("Seleccionar Tipo de Certificado Registral Mobiliario");
		}else if(tipocert=='C'){
			solid = document.frm1.sol_id.value;
			constancia_temp = document.frm1.Constancia_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			document.frm1.Constancia2.value = cadena;
			constancia = cadena;
			constancia = document.frm1.Constancia2.value;
			
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');		
		}
		else if(document.frm1.tpo_cert.value=='CREMC'){
			alert("Seleccionar Tipo de Certificado Registral Mobibliario");
		}else if(tipocert=='M'){
			solid = document.frm1.sol_id.value;
			constancia_temp = document.frm1.Constancia_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			document.frm1.Constancia2.value = cadena;
			constancia = "";
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');		
		}
		else if(document.frm1.tpo_cert.value=='RJBG'){
			alert("Seleccionar tipo de Certificado Compendioso de Historial de Gravámenes de RJB");
		}else if(tipocert=='D'){
			solid = document.frm1.sol_id.value;
			constancia_temp = document.frm1.Constancia_temp.value;
			var cadena = convertirFormatoHTML(constancia_temp);
			document.frm1.Constancia2.value = cadena;
			constancia = "";
			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');		
		}
		else if(document.frm1.tpo_cert.value=='RJBD'){
			alert("Seleccionar tipo de Certificado Compendioso de Antecedentes Dominiales de RJB");
		}

		//Fin:jascencio:25/06/2007
		
		// Inicio:mgarate:05/06/2007
		else if(tipocert=='B')
		{
		 	solid = document.frm1.sol_id.value;
			constancia = document.frm1.Constancia2.value;

			window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');
		}else if(document.frm1.tpo_cert.value=='B')
		{
			alert ("Seleccionar Certificado de Busqueda");
		}
		// Fin:mgarate:05/06/2007
		
}

function CertificadoPorExpedir(){
		tipocert = document.frm1.tip_cert.value;	
		solid = document.frm1.sol_id.value;
		//alert("soli id"+solid+"tipo cert"+tipocert);
		//window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='V'","Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=719,height=997');
		window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado=V","Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=880');

}

function ValorRadio(valor){
			document.frm1.radioValor.value = valor;
}

function GuardarCertVerificado(){
		var tipoCertificado=""; var numclave ="";
		numclave= document.frm1.usrPass.value;	
		
		if(document.frm1.tpo_cert.value=='CREMC'){
			//inicio:dbravo:10/08/2007
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value); // aqui se cambiara para que tenga el formato requerido
			//inicio:dbravo:10/08/2007
		}else if(document.frm1.tpo_cert.value=="B")
		{
			if(document.frm1.radioValor.value == "B"){
				//Inicio:mgarate:20/06/2007
				if(document.frm1.Constancia2.value=="")
				{
					alert("Debe ingresar información en Títulos.");
					return;
				}
				//Fin:mgarate:20/06/2007
			}
		}
		//Inicio:mgarate:17/08/2007
		
		else if(document.frm1.tpo_cert.value=="RJBD")
		{
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
		}else if(document.frm1.tpo_cert.value=="RJBG")
		{
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
		}
		//Fin:mgarate
		// inicio:jrosas 17-08-07
		else if(document.frm1.tpo_cert.value=="CREMH")
		{
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
		}
		else if(document.frm1.tpo_cert.value=="CREMA")
		{
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
		}
		// inicio: ifigueroa 20/08/2007
		else if(document.frm1.tpo_cert.value=="RMCG")
		{
			document.frm1.txtTitulos.value = convertirFormatoBD(document.frm1.titulos_temp.value);
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
			limita_largo(1024);
		}
		else if(document.frm1.tpo_cert.value=="RMCV")
		{
			document.frm1.txtTitulos.value = convertirFormatoBD(document.frm1.titulos_temp.value);
			document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
			limita_largo(1024);
		}else if(document.frm1.tpo_cert.value=="N")
		{
			if(document.frm1.hidCertificadoId.value=="18"){
				document.frm1.txtTitulos.value = convertirFormatoBD(document.frm1.titulos_temp.value);
				document.frm1.Constancia2.value = convertirFormatoBD(document.frm1.Constancia_temp.value);
				limita_largo(1024);
			}
			
		}
		// fin: ifigueroa 20/08/2007
		
		//Inicio:jascencio:22/06/2007
		<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol3" scope="request">
			<logic:notEqual name="objsol3" property="tpo_cert" value="L">  
				tipoCertificado = document.frm1.radioValor.value;	
				
				if ((tipoCertificado == undefined) || (tipoCertificado == null) || (tipoCertificado == "")){
					alert("Elija el tipo de certificado");
					return;
				}
			</logic:notEqual>
		</logic:iterate>			
		//Fin:jascencio:22/06/2007
			
		
		
///-->
	if(document.frm1.radioValor.value != "I"){
	
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol4" scope="request">
					<logic:equal name="objsol4" property="tpo_cert" value="N">  
						<logic:equal name="objsol4" property="certificado_id" value="18">
														
								if (document.frm1.Constancia2.value.length<=0){
										alert("Debe ingresar información en la constancia");
										return;
								}
								if (document.frm1.txtTitulos.value.length<=0){
									alert("Debe ingresar información en los Titulos");
									return;									
								}
								
						</logic:equal>
						<logic:notEqual name="objsol4" property="certificado_id" value="18">
																

								if (document.frm1.Constancia.value.length<=0){
									alert("Debe ingresar información en la constancia");
									return;									
								}
								
						</logic:notEqual>
					</logic:equal>
					<logic:equal name="objsol4" property="tpo_cert" value="R">  
													
							if (document.frm1.Constancia2.value.length<=0){
								alert("Debe ingresar información en la constancia");
								return;									
							}
							if (document.frm1.txtTitulos.value.length<=0){
									alert("Debe ingresar información en los Titulos");
									return;									
							}
							
					</logic:equal>
					<logic:equal name="objsol4" property="tpo_cert" value="D">  
						
						if(document.frm1.radioValor.value!="I")
						{
							if (document.frm1.Constancia_temp.value.length<=0){
									alert("Debe ingresar información en la constancia");
									return;									
							}
						}
					</logic:equal>
					<logic:equal name="objsol4" property="tpo_cert" value="G">  
						
						if(document.frm1.radioValor.value!="I")
						{		
							if (document.frm1.Constancia_temp.value.length<=0){
									alert("Debe ingresar información en la constancia");
									return;									
							}
						}
					</logic:equal>
					/** inicio: jrosas 15-08- 07 **/
					<logic:equal name="objsol4" property="tpo_cert" value="C">  
						
								
						if (document.frm1.Constancia_temp.value.length<=0){
								alert("Debe ingresar información en la constancia");
								return;									
						}
							
					</logic:equal>
				    /** fin: jrosas 15-08- 07 **/
					<logic:equal name="objsol4" property="tpo_cert" value="L">  
						document.frm1.radioValor.value="L";
					</logic:equal>
					
			</logic:iterate>
		}						
///-->
		if ((numclave == undefined) || (numclave == null) || (numclave=="")){
			alert("Debe ingresar su clave")
			return;
		}

		doyou = true;  		
  		doyou = confirm("Ud. está enviando el resultado de su verificación para su emisión. ¿Desea continuar?"); //Your question.  		
  		
  		if(doyou == true) {
			document.frm1.action="/iri/CargaLaboral.do?state=certificadoVerficado";
			document.frm1.submit();
		}	
}

function GuardarCertExpedido()
{
		doyou = true;
		//Inicio:mgarate:18/09/2007
		if(document.frm1.usrPassExp.value==null || document.frm1.usrPassExp.value=="")
		{
			alert("Debe ingresar su clave");
			document.frm1.usrPassExp.focus();
			return;
		}  		
		//Fin:mgarate
  		doyou = confirm("Ud. está expidiendo esta solicitud ¿Desea continuar?"); //Your question.  		
  		if(doyou == true) {
			document.frm1.action="/iri/CargaLaboral.do?state=certificadoExpedido";
			document.frm1.submit();
		}
}
function devolucion(){
		document.frm1.action="/iri/Devolucion.do?state=muestraModoDevolucion";
		document.frm1.submit();
}
function VerPartida(refnum_Part,accion)
{
	obj_sol_id = document.frm1.objeto_Sol_Id.value;	
ventana=window.open('/iri/Publicidad.do?state=visualizaPartida&objetoId='+obj_sol_id+'&accion=' + accion +'&noSolicitud=' + <bean:write name="Solicitud" property="solicitud_id" scope="request"/> + '&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
//ventana=window.open('/iri/Publicidad.do?state=visualizaPartida&objetoId='+obj_sol_id+'&accion=' + accion + '&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
//	ventana=window.open('/iri/Publicidad.do?state=visualizaPartida&objetoId='+obj_sol_id+'&accion=' + accion <%--<logic:present name="Solicitud" property="solicitud_id" scope="request">+'&noSolicitud=' + <bean:write name="Solicitud" property="solicitud_id" scope="request"/></logic:present>--%> + '&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
	//accion=verifica, entonces debe insertar/actualizar las imagenes en las tablas de verificacion, antes de visualizarlas
	//accion=expide, entonces debe visualizar las imagenes desde las tablas de verificacion
	//accion=null, entonces sigue el procedimiento normal de visualizacion
}

//Inicio:jascencio:10/08/2007
//CC: SUNARP REGMOBCON-2006
function VerPartidaAnt(refnum_Part,accion)
{
	obj_sol_id = document.frm1.objetoSolicitudId.value;	
	ventana=window.open('/iri/Publicidad.do?state=visualizaPartida&objetoId='+obj_sol_id+'&accion=' + accion +'&noSolicitud=' + <bean:write name="Solicitud" property="solicitud_id" scope="request"/> + '&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
}
//Fin:jascencio

<%
  boolean flagInter=false;
  if(session!=null)
  {
  	UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  	if(usuarioBean!=null)
  	  if(usuarioBean.getFgInterno())
  	    flagInter = true;
  }
  //long perfilusuarioid =usuarioBean.getPerfilId();
%>
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
</script>

<%-- inicio:dbravo:03/08/2007--%>
<%-- descripcion: Si el certificado es CREM Condicionado(23) se construye la constancia mediante el jsp constanciaCremCondicionado --%>
   <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol5" scope="request">
		<logic:equal name="objsol5" property="certificado_id" value="23">	
			<logic:equal name="Solicitud" property="estado" value="C">
				<jsp:include flush="true" page="constanciaCremCondicionado.jsp"></jsp:include>
			</logic:equal>
		</logic:equal>
		<%-- fin:dbravo:03/08/2007--%> 
		<!-- Iinicio: jrosas 06-08-07 -->
		<logic:equal name="objsol5" property="certificado_id" value="21">	
					<jsp:include flush="true" page="constanciaCremActosVigentes.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="22">	
			<jsp:include flush="true" page="constanciaCremHistorico.jsp"></jsp:include>
		</logic:equal>		
						
		<!-- Fin: jrosas 06-08-07 -->
		<%-- inicio:mgarate:07/08/2007--%>
		<logic:equal name="objsol5" property="certificado_id" value="25">	
			<jsp:include flush="true" page="constanciaRjbDominial.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="26">	
			<jsp:include flush="true" page="constanciaRjbDominial.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="27">	
			<jsp:include flush="true" page="constanciaRjbDominial.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="28">	
			<jsp:include flush="true" page="constanciaRjbDominial.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="29">	
			<jsp:include flush="true" page="constanciaRjbGravamen.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="30">	
			<jsp:include flush="true" page="constanciaRjbGravamen.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="31">	
			<jsp:include flush="true" page="constanciaRjbGravamen.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="32">	
			<jsp:include flush="true" page="constanciaRjbGravamen.jsp"></jsp:include>
		</logic:equal>
		<%-- fin:mgarate:07/08/2007--%>
		<!-- Iinicio: ifigueroa 09/08/2007 -->
		<logic:equal name="objsol5" property="certificado_id" value="18">	
			<jsp:include flush="true" page="constanciaPositivoRmc.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="19">	
			<jsp:include flush="true" page="constanciaVigenciaRmc.jsp"></jsp:include>
		</logic:equal>
		<logic:equal name="objsol5" property="certificado_id" value="20">	
			<jsp:include flush="true" page="constanciaGravamenRmc.jsp"></jsp:include>
		</logic:equal>
		<!-- Fin: ifigueroa 09/08/2007 -->
	</logic:iterate>	

<!-- Inicio de form --> 
<form name="frm1" method="post">
<logic:present name="Solicitud" scope="request">

<!-- inicio:dbravo:10/08/2007 -->
<input type="hidden" name="cantidadLineas" value="0"/>				
<!-- fin:dbravo:10/08/2007 -->
<!-- inicio:ifigueroa:15/08/2007 -->
<logic:present name="nombreRepresentanteRMC" scope="request">				
	<input type="hidden" name="representanteRMC" value="<bean:write name='nombreRepresentanteRMC'/>"/>				
</logic:present>
<!-- fin:ifigueroa:15/08/2007 -->

<input type="hidden" name="hid1"/>
<input type="hidden" name="hid2"/>
<input type="hidden" name="radioValor" value=""/>
<input type="hidden" name="tiposRegistroCondicionado" value=""/>
<input type="hidden" name="flagHistoricoCondicionado" value=""/>
<input name="hidVisualizaConstancia" type="hidden" value=""/>
<br>
<table class=titulo cellspacing=0>
  <tr> 
      <td><font color=black>REGISTRADOR &gt;&gt; CARGA REGISTRAL &gt;&gt;</font> 
        Detalle de la Solicitud</td>
	</tr>
</table>
<br>
<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong> DATOS BASICOS DE LA SOLICITUD</strong></td>
  </tr>
</table>
<table class=formulario cellspacing=0>

	    <tr> 
	      <td width="5">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	    </tr>
	    <tr> 
	      <td>&nbsp;</td>
	      <td width="133"><strong>NUMERO &nbsp; </strong></td>     
     	  <td>&nbsp;
	      <logic:present name="Solicitud" scope="request">				
			<bean:write name="Solicitud" property="solicitud_id" scope="request"/>
			<input type="hidden" name="sol_id" value="<bean:write name="Solicitud" property="solicitud_id" scope="request"/>">	    
		  </logic:present>
	  	  </td>
	      <td width="133">&nbsp; </td>
	      <td width="133">
		  <logic:present name="devolucion">
			 <logic:equal name="devolucion"  value="true">
				<a href="javascript:devolucion();"><img src="images/btn_solicitar_devolucion.gif"></a>
			 </logic:equal>
			 <logic:equal name="devolucion"  value="false">
				<strong><font color="#993300">Solicitud con Devoluci&oacute;n</font></strong>
			 </logic:equal>
		  </logic:present> 
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
		    <td><strong>ESTADO</strong></td>
		    <%if(flagInter) {%>
	 	    <td >&nbsp;<strong><font color="#993300"><bean:write name="Solicitud" property="estado_descripcion" scope="request"/></font></strong> 
	        <%} else {%>
	        
	        	<logic:present name="certificado">
	        		<logic:equal name="certificado" property="flagPagoCrem" value="0">
						<td ><strong><font color="#993300">Liquidado</font></strong>
						<br/>Por favor apersonarse a ventanilla para cancelar la deuda y recoger su certificado registral.
					</logic:equal>
					<logic:equal name="certificado" property="flagPagoCrem" value="1">
						<td ><strong><font color="#993300"><bean:write name="Solicitud" property="estado_ext_descripcion" scope="request"/></font></strong>
					</logic:equal>
	        	</logic:present>
	        	<logic:notPresent name="certificado">
	        		<td >&nbsp;<strong><font color="#993300"><bean:write name="Solicitud" property="estado_ext_descripcion" scope="request"/></font></strong>		
	        	</logic:notPresent>
	        
	        <%}%>
	        </td>
	        <logic:equal name="Solicitud" property="estado" value="<%= Constantes.ESTADO_SOL_DESPACHADA%>">
	        	<logic:present name="Solicitud" parameter="destinatarioBean" scope="request" >	      		
					<logic:equal name="Solicitud" property="destinatarioBean.tpo_env" value="V">
					<!--<td>&nbsp;</td>-->
					<td colspan="2">Puede apersonarse a ventanilla a recoger su certificado registral.</td>	
					</logic:equal>
				</logic:present>         
			</logic:equal>
        </tr>
        <tr> 
      		<td width="4">&nbsp;</td>
      		<td>&nbsp;</td>
      		<td width="425">&nbsp;</td>
    	</tr>
    
 	<!--  inicio:jrosas 01-06-2007
   		SUNARP-REGMOBCOM: Modificación de los campos que se mostrarn para el detalle y la verificacion de la solicitud -->  
	   
	    <tr> 
		      <td>&nbsp;</td>
		      <td width="163"><strong>TIPO DE CERTIFICADO</strong></td>
		      <td>
			  <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol6" scope="request">	
				  <logic:equal name="objsol6" property="tpo_cert" value="G">   	
		    			<bean:write name="objsol6" property="certificado_desc"/>	
				  </logic:equal>
				  <logic:notEqual name="objsol6" property="tpo_cert" value="G">
					  <logic:equal name="objsol6" property="tpo_cert" value="D">   	
			    			<bean:write name="objsol6" property="certificado_desc"/>	
					  </logic:equal>
					  <!-- Si el tipo de certificado es: L(copia literal),N (Positivo/Negativo),R (Vigencia/Gravamen),B (Busquedas),
					       C (Crem) -->
					  <logic:notEqual name="objsol6" property="tpo_cert" value="D">
						  <!-- Inicio:mgarate:06/06/2007 -->
						    <logic:equal name="objsol6" property="tpo_cert" value="B">
						       &nbsp;&nbsp;<bean:write name="objsol6" property="certificado_desc"/>
							</logic:equal>
							<logic:notEqual name="objsol6" property="tpo_cert" value="B">
							   <bean:write name="objsol6" property="certificado_desc"/>		
							</logic:notEqual>
					  </logic:notEqual>
					  <!-- Fin:mgarate:06/06/2007 -->
				  </logic:notEqual>
			  </logic:iterate>
	      	  </td>
	    </tr>
	    
	    <!-- fin:jrosas 01-06-2007 -->
    	<tr> 
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
    	</tr>
	    <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol7" scope="request">				
		    <logic:equal name="objsol7" property="certificado_id" value="8">        	
		    <!-- Copia Literal Asiento--> 
			    <tr>
		 	   		<td>&nbsp;</td>        	        	
		        	<td><strong>NRO SEC ASIENTO</strong></td>	
		        	<td><bean:write name="objsol7" property="ns_asiento"/></td> 
		        	<td><strong>ACTO</strong></td>	
		        	<td><bean:write name="objsol7" property="desc_acto"/></td>                	
		    	</tr>
		    	<tr> 
		     		 <td>&nbsp;</td>
		     		 <td>&nbsp;</td>
		     	 	 <td>&nbsp;</td>
		    	</tr>
		    	<tr>
		 	   		<td>&nbsp;</td>        	        	
		        	<td><strong>AÑO TITULO</strong></td>	
		        	<td><bean:write name="objsol7" property="aa_titu"/></td> 
		        	<td><strong>NRO TITULO</strong></td>	
		        	<td><bean:write name="objsol7" property="num_titu"/></td>                	
		    	</tr>
		    	<tr> 
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
		    	</tr>
		    </logic:equal>                	      
	    </logic:iterate>          
	    
	        <% 
	        	Solicitud Sol= (Solicitud)request.getAttribute("Solicitud");
	         	ObjetoSolicitudBean objSol11= Sol.getObjetoSolicitudList(0);
	         %>
      		<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol8" scope="request">		
        <!--  inicio:jrosas 01-06-2007
			  SUNARP-REGMOBCOM: Modificación de los campos que se mostrarn para el detalle y la verificacion de la solicitud -->  
		   			  
		        <!-- Certificado Negativo--> 
		        <logic:equal name="objsol8" property="tpo_cert" value="N">   
		            <logic:equal name="objsol8" property="certificado_id" value="18">  <!-- Certificado Mobiliario de Contratos--> 
			             <% if (objSol11.getPlaca() != null){ %>
		               	   <tr>
			                   <td>&nbsp;</td>
			                    <td width="163"><strong>NRO PLACA</strong></td>
						        <td>      	
									<bean:write name="objsol8" property="placa"/>			
								</td>	
						   </tr>
						   <tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
		    			  </tr>
						<%} %>
						<% if (objSol11.getNombreBien() != null){ %>
							<tr>	
							   <td>&nbsp;</td>
			                    <td width="163"><strong>NOMBRE BIEN</strong></td>
			                    <td>
									<bean:write name="objsol8" property="nombreBien"/>			
								</td>	
							</tr>
							<tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
		    			  </tr>
						<%} %>
						<% if (objSol11.getNumeroMatricula() != null){ %>	
							<tr>	
							    <td>&nbsp;</td>
		                	    <td width="163"><strong>NRO MATRICULA</strong></td>
					        	<td>      	
									<bean:write name="objsol8" property="numeroMatricula"/>			
								</td>	
							</tr>	
							<tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
		    			  </tr>
						<%} %>	
						<!-- inicio: jrosas 31-08-07 impresion de numero de serie -->
						<% if (objSol11.getNumeroSerie() != null){ %>	
							<tr>	
							    <td>&nbsp;</td>
		                	    <td width="163"><strong>NRO SERIE</strong></td>
					        	<td>      	
									<bean:write name="objsol8" property="numeroSerie"/>			
								</td>	
							</tr>	
							<tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
		    			  </tr>
						<%} %>	
						<!-- fin:jrosas 31-08-07 -->
		            </logic:equal>
		            <logic:notEqual name="objsol8" property="certificado_id" value="18">
		            	<tr>
				            <logic:equal name="objsol8" property="tpo_pers" value="N">
				      			<td>&nbsp;</td>
				        		<td width="163"><strong>APELLIDOS Y NOMBRES</strong></td>
				        		<td>      	
									<bean:write name="objsol8" property="ape_pat"/>
									<bean:write name="objsol8" property="ape_mat"/>
									<bean:write name="objsol8" property="nombres"/>
								</td>			
							</logic:equal>	
							<logic:equal name="objsol8" property="tpo_pers" value="J">
					      		<td>&nbsp;</td>
					        	<td width="163"><strong>RAZON SOCIAL</strong></td>
					        	<td>      	
									<bean:write name="objsol8" property="raz_soc"/>			
								</td>			
							</logic:equal>					
						</tr>	
		            </logic:notEqual>
				</logic:equal> 
				
				<!-- Certificado de Gravamen/Vigencia
				     Cert. Gravamen: certificado_id: 19  
				     Cert. Vigencia: certificado_id: 20 --> 
				     
			    <logic:equal name="objsol8" property="tpo_cert" value="R">  
			    	<!-- Persona Natural-->
			    	<logic:equal name="objsol8" property="tpo_pers" value="N"> 
			    		<tr>  
		      				<td>&nbsp;</td>
			        		<td width="163"><strong>APELLIDOS Y NOMBRES</strong></td>
			        		<td>      	
								<bean:write name="objsol8" property="ape_pat"/>
								<bean:write name="objsol8" property="ape_mat"/>
								<bean:write name="objsol8" property="nombres"/>
							</td>	
						</tr>	
					</logic:equal>	
					<!-- Persona Juridica-->
		    		<logic:equal name="objsol8" property="tpo_pers" value="J"> 
			    		<tr> 
				      		<td>&nbsp;</td>
				        	<td width="163"><strong>RAZON SOCIAL</strong></td>
				        	<td>      	
								<bean:write name="objsol8" property="raz_soc"/>			
							</td>			
						</tr>	
						<TR><td colspan="3">&nbsp;</td></TR>
						<logic:present name="objsol8" property="siglas">
							<tr> 
				      			<td>&nbsp;</td>
					        	<td width="163"><strong>SIGLAS</strong></td>
					        	<td>      	
									<bean:write name="objsol8" property="siglas"/>			
								</td>			
							</tr>	
						</logic:present>
					</logic:equal>		
					<logic:present name="objsol8" property="numeroDocumento">
					 	<tr>	
							<td>&nbsp;</td>
				        	<td width="163"><strong>TIPO DE DOCUMENTO</strong></td>
				        	<td>      	
								<bean:write name="objsol8" property="nombreDocumento"/>			
							</td>	
						</tr>
						<tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
		    			</tr>
						<tr>	
							<td>&nbsp;</td>
				        	<td width="163"><strong>NUMERO DE DOCUMENTO</strong></td>
				        	<td>      	
								<bean:write name="objsol8" property="numeroDocumento"/>			
							</td>	
						</tr>
						<tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
	    			    </tr>
					</logic:present>
					<logic:present name="objsol8" property="tipoParticipante">
						<tr>	
							<td>&nbsp;</td>
				        	<td width="163"><strong>TIPO DE PARTICIPANTE</strong></td>
				        	<td>      	
								<bean:write name="objsol8" property="nombreParticipante"/>			
							</td>	
						</tr>	
						<tr> 
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
	    			    </tr>
					</logic:present>
				</logic:equal>
				<!-- Fin Certificado de Gravamen/Vigencia--> 
				
				<!-- Certificado de Gravamen RJB--> 
				
				<logic:equal name="objsol8" property="tpo_cert" value="G">   
		          	<tr> 
		                <td>&nbsp;</td>
				        <td width="163"><strong>AREA</strong></td>
				        <td>      	
							<bean:write name="objsol8" property="desc_GLA"/>			
						</td>	
					</tr>	
					<tr> 
					      <td>&nbsp;</td>
					      <td>&nbsp;</td>
					      <td>&nbsp;</td>
    			    </tr>
				</logic:equal> 
				
				<!-- Fin Certificado de Gravamen de RJB--> 
				
				<!-- Certificado Dominial RJB--> 
				
				<logic:equal name="objsol8" property="tpo_cert" value="D">   
		          	<tr> 
		                <td>&nbsp;</td>
				        <td width="163"><strong>AREA</strong></td>
				        <td>      	
							<bean:write name="objsol8" property="desc_GLA"/>			
						</td>	
					</tr>	
					<tr> 
					      <td>&nbsp;</td>
					      <td>&nbsp;</td>
					      <td>&nbsp;</td>
    			   </tr>
				</logic:equal> 
				
				<!-- Fin Certificado de Gravamen de RJB--> 
				
				<!-- Certificados Tipo CREM
				     Cert. Registral Mobiliario (Actos Vigentes): certificado_id: 21   
				     Cert. CREM Historico: certificado_id: 22 
					 Cert. CREM Condicionado: certificado_id: 23   --> 
				
			    <logic:equal name="objsol8" property="tpo_cert" value="C">  
			    	<!-- Persona Natural-->
			    	<logic:equal name="objsol8" property="tpo_pers" value="N"> 
			    		<tr>  
		      				<td>&nbsp;</td>
			        		<td width="163"><strong>APELLIDOS Y NOMBRES</strong></td>
			        		<td>      	
								<bean:write name="objsol8" property="ape_pat"/>
								<bean:write name="objsol8" property="ape_mat"/>
								<bean:write name="objsol8" property="nombres"/>
							</td>	
						</tr>	
					</logic:equal>	
					<!-- Persona Juridica-->

		    		<logic:equal name="objsol8" property="tpo_pers" value="J"> 
			    		<tr> 
				      		<td>&nbsp;</td>
				        	<td width="163"><strong>RAZON SOCIAL</strong></td>
				        	<td>      	
								<bean:write name="objsol8" property="raz_soc"/>			
							</td>			
						</tr>	
					</logic:equal>	
					<!-- Certificado Condicionado CREM -->
					<logic:equal name="objsol8" property="certificado_id" value="23">
						<tr><td colspan="3">&nbsp;</td></tr>
						<tr>	
							<td>&nbsp;</td>
				        	<td width="163"><strong>TIPO DE REGISTRO</strong></td>
				        	<td>      	
								<bean:write name="objsol8" property="desTipoRegistro"/>			
							</td>	
						</tr>
						<tr><td colspan="3">&nbsp;</td></tr>	
						<tr>	
							<td>&nbsp;</td>
				        	<td width="163"><strong>HIST&Oacute;RICO</strong></td>
				        	<logic:equal name="objsol8" property="flagHistorico" value="0" >
				        		<td> NO	</td>	
				        	</logic:equal>
				        	<logic:equal name="objsol8" property="flagHistorico" value="1" >
				        		<td> SI	</td>
			        		</logic:equal>
						</tr>
						<tr><td colspan="3">&nbsp;</td></tr>		
		        		<logic:present name="objsol8" property="fechaInscripcionASientoDesde">
			        		<tr>	
								<td>&nbsp;</td>
					        	<td width="163"><strong>AÑO DE INSCRIPCI&Oacute;N DE ASIENTO</strong></td>
					        	<td>   
					        		DESDE&nbsp;<bean:write name="objsol8" property="fechaInscripcionASientoDesde"/>
					        		<logic:present name="objsol8" property="fechaInscripcionASientoHasta">
					        			&nbsp;HASTA&nbsp;<bean:write name="objsol8" property="fechaInscripcionASientoHasta"/>
					        		</logic:present>   	
								</td>	
							</tr>	
				   		</logic:present>   	
					</logic:equal>
					<TR><td colspan="3">&nbsp;</td></TR>
					<!-- Certificado Registro Mobiliaro, Historico CREM -->
						<logic:present name="objsol8" property="tipoParticipante">
							<tr>	
								<td>&nbsp;</td>
					        	<td width="163"><strong>TIPO DE PARTICIPANTE</strong></td>
					        	<td>      	
									<bean:write name="objsol8" property="nombreParticipante"/>			
								</td>	
							</tr>	
							<tr> 
							      <td>&nbsp;</td>
							      <td>&nbsp;</td>
							      <td>&nbsp;</td>
		    			    </tr>
						</logic:present>
				</logic:equal>
				<!-- Fin Certificado Tipo CREM--> 
				
		        <!-- Certificado de Copia Literal--> 
		        <logic:equal name="objsol8" property="tpo_cert" value="L">  
		          <tr>                   		
		        	<td>&nbsp;</td>
			       	<td width="163"><strong>NUMERO:</strong></td>
			       	<td><bean:write name="objsol8" property="refnum_part"/></td>        
			       	<logic:equal name="objsol8" property="certificado_id" value="8">
			       		<td width="163"><strong>NRO PAGINAS:</strong></td>
			       		<td><bean:write name="objsol8" property="numpag"/></td>
			       	</logic:equal>
			      </tr> 	
			 	</logic:equal>
			 	<!-- Fin Certificado de Copia Literal-->
			 	<!-- Inicio:mgarate:07/06/2007 --> 	
			 	<logic:equal name="objsol8" property="tpo_cert" value="B">  
			 	  <logic:present name="objsol8" property="tomo">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>TOMO</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="tomo"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="folio">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>FOLIO</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="folio"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="numPartida">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>NUMERO DE PARTIDA</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="numPartida"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="ficha">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>FICHA</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="ficha"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="ape_pat">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>APELLIDO PATERNO</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="ape_pat"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="ape_mat">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>APELLIDO MATERNO</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="ape_mat"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="nombres">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>NOMBRES</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="nombres"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="raz_soc">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>RAZON SOCIAL</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="raz_soc"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="numeroMotor">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>NUMERO DE MOTOR</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="numeroMotor"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="numeroSerie">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>NUMERO DE SERIE</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="numeroSerie"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="numeroMatricula">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>NUMERO DE MATRICULA</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="numeroMatricula"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="nombreBien">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>NOMBRE DE BIEN</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="nombreBien"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	  <logic:present name="objsol8" property="placa">
			 		<tr>	
					  <td>&nbsp;</td>
					  <td width="163"><strong>PLACA</strong></td>
					  <td>      	
						<bean:write name="objsol8" property="placa"/>			
					  </td>	
					 </tr>	
					 <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
		    		 </tr>
			 	  </logic:present>
			 	</logic:equal>
			 	<!-- Fin:mgarate:07/06/2007 -->	
	  		</logic:iterate>	    
	  		
	  	   <!-- fin:jrosas 01-06-2007 -->	
	  	       
    	<tr> 
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
    	</tr>
    	<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol9" scope="request">
    	    <!-- CERTIFICADO NEGATIVO (POSITIVO/NEGATIVO:N) -->
    	    <logic:equal name="objsol9" property="tpo_cert" value="N">
	    		<logic:notEqual name="objsol9" property="certificado_id" value="18">		
			    	<tr> 
				        <td>&nbsp;</td>
				        <td width="163"><strong>OFICINA REGISTRAL</strong></td>
				        <td>
							<bean:write name="objsol9" property="ofic_reg_desc"/>	
				        </td>
			    	</tr>
			    </logic:notEqual>	
    	    </logic:equal>
    	    <!-- CERTIFICADO COPIA LITERAL -->
    	    <logic:equal name="objsol9" property="tpo_cert" value="L">
  	    	   	<tr> 
			        <td>&nbsp;</td>
			        <td width="163"><strong>OFICINA REGISTRAL</strong></td>
			        <td>
						<bean:write name="objsol9" property="ofic_reg_desc"/>	
			        </td>
		    	</tr>
	    	</logic:equal>
	    	<!-- CERTIFICADO DE GRAVAMEN -->
	    	<logic:equal name="objsol9" property="tpo_cert" value="G">
  	    	   	<tr> 
			        <td>&nbsp;</td>
			        <td width="163"><strong>OFICINA REGISTRAL</strong></td>
			        <td>
						<bean:write name="objsol9" property="ofic_reg_desc"/>	
			        </td>
		    	</tr>
	    	</logic:equal>
	    	<!-- CERTIFICADO DOMINIALL -->
	    	<logic:equal name="objsol9" property="tpo_cert" value="D">
  	    	   	<tr> 
			        <td>&nbsp;</td>
			        <td width="163"><strong>OFICINA REGISTRAL</strong></td>
			        <td>
						<bean:write name="objsol9" property="ofic_reg_desc"/>	
			        </td>
		    	</tr>
	    	</logic:equal>
   	    </logic:iterate>
    	<tr> 
      		<td>&nbsp;</td>
      		<td>&nbsp;</td>
      		<td>&nbsp;</td>
    	</tr>
    	
    	<!-- DATOS EXTRAS PARA CERTIFICADO DE GRAVAMEN Y DOMINIAL DE RJB -->
    	<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol10" scope="request">
    	    <!-- Certificado de gravamen -->
    	    <logic:equal name="objsol10" property="tpo_cert" value="G">
        	    <!-- Tipo Vehicular -->
        	    <!--  inicio: jrosas 31-09-07 -->
	    		<logic:equal name="objsol10" property="certificado_id" value="29">	
		    		<logic:present name="objsol10" property="placa">		
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>PLACA</strong></td>
					        <td>
								<bean:write name="objsol10" property="placa"/>	
					        </td>
				    	</tr>
				    </logic:present>	
			    	<logic:present name="objsol10" property="numeroPartida">		
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>PARTIDA</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroPartida"/>	
					        </td>
				    	</tr>
				    </logic:present>	
			    </logic:equal>
			    <!-- Tipo: Aeronave (FALTARIA EL NUMEO DE SERIE),Buques,Embarcaciones Pesqueras -->	
			    <logic:notEqual name="objsol10" property="certificado_id" value="29">
			    	<logic:present name="objsol10" property="numeroSerie">
				    	<tr><td colspan="3">&nbsp;</td></tr>
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>NÚMERO DE SERIE</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroSerie"/>	
					        </td>
				    	</tr>
			    	</logic:present>
			    	<logic:present name="objsol10" property="numeroMatricula">			    
			    		<tr><td colspan="3">&nbsp;</td></tr>
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>NÚMERO DE MATRÍCULA</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroMatricula"/>	
					        </td>
				    	</tr>
				    </logic:present>
				    <logic:present name="objsol10" property="numeroPartida">	
   			    		<tr><td colspan="3">&nbsp;</td></tr>		
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>PARTIDA</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroPartida"/>	
					        </td>
				    	</tr>
				    </logic:present>	
			    </logic:notEqual>	
    	    </logic:equal>
       	    <!--  fin: jrosas 31-09-07 -->
    	    <!-- Fin de Certificado de Gravamen -->
    	    <!-- Certificado de dominial -->
    	     <!--  inicio: jrosas 31-09-07 -->
    	    <logic:equal name="objsol10" property="tpo_cert" value="D">
    	    	<logic:present name="objsol10" property="tipoInformacionDominio">
	    	    	<tr><td colspan="3">&nbsp;</td></tr>
		    	    <tr> 
				        <td>&nbsp;</td>
				        <td width="163"><strong>TIPO DE INFORMACIÓN DE DOMINIO</strong></td>
				        <td>
				        	<logic:equal name="objsol10" property="tipoInformacionDominio" value="C">Completa
				        	</logic:equal>
				        	<logic:notEqual name="objsol10" property="tipoInformacionDominio" value="C">Último Propietario
				        	</logic:notEqual>
				        </td>
			    	</tr>
			    </logic:present>	
    	    	<!-- Tipo Vehicular -->
	    		<logic:equal name="objsol10" property="certificado_id" value="25">	
		    		<logic:present name="objsol10" property="placa">	
			    		<tr><td colspan="3">&nbsp;</td></tr>
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>PLACA</strong></td>
					        <td>
								<bean:write name="objsol10" property="placa"/>	
					        </td>
				    	</tr>
				    </logic:present>	
				    <logic:present name="objsol10" property="numeroPartida">	
					    <tr><td colspan="3">&nbsp;</td></tr>
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>PARTIDA</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroPartida"/>	
					        </td>
				    	</tr>
				    </logic:present>	
			    </logic:equal>
			    <!-- Tipo: Aeronave (FALTARIA EL NUMEO DE SERIE),Buques,Embarcaciones Pesqueras -->	
			    <logic:notEqual name="objsol10" property="certificado_id" value="25">		
			    	<logic:present name="objsol10" property="numeroSerie">
				    	<tr><td colspan="3">&nbsp;</td></tr>
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>NÚMERO DE SERIE</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroSerie"/>	
					        </td>
				    	</tr>
			    	</logic:present>		
			    	<logic:present name="objsol10" property="numeroMatricula">	
				    	<tr><td colspan="3">&nbsp;</td></tr>	    
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>NÚMERO DE MATRÍCULA</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroMatricula"/>	
					        </td>
				    	</tr>
				    </logic:present>
				    <logic:present name="objsol10" property="numeroPartida">	
					    <tr><td colspan="3">&nbsp;</td></tr>		
				    	<tr> 
					        <td>&nbsp;</td>
					        <td width="163"><strong>PARTIDA</strong></td>
					        <td>
								<bean:write name="objsol10" property="numeroPartida"/>	
					        </td>
				    	</tr>
				    </logic:present>	
			    </logic:notEqual>
			</logic:equal> 
			<!--  fin: jrosas 31-09-07 -->
			<!-- Fin de Certificado de dominial -->  
    	</logic:iterate>    
</table>

<!-- Para los registradores--> 
<logic:present name="Registrador" scope="request">				
	<logic:equal name="Registrador" value="REG"> 
	<!-- Solicitud en estado= Por Verificar -->
 	<logic:present name="Solicitud" scope="request">		
		<logic:equal name="Solicitud" property="estado" value="C"> 
		  	<table class=cabeceraformulario cellspacing=0> 
  				<tr>  	    
			    	<td>&nbsp;</td>
			      	<td><strong>COMENTARIOS DE LA VERIFICACIÓN </strong></td>  						
		  		</tr>  
		  	</table>
			<table class=formulario cellspacing=0>
			  	<tr> 
			    	<td width="5">&nbsp;</td>
				    <td width="133">&nbsp;</td>
			    	<td width="133">&nbsp;</td>
			    	<td width="133">&nbsp;</td>
			    	<td width="133">&nbsp;</td>
			    </tr>
			   	<tr>
					<td>&nbsp;</td>		
					<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol13" scope="request">
						<!-- Inicio:mgarate:10/08/2007 -->
						<logic:equal name="objsol13" property="tpo_cert" value="B">
						<input type="hidden" name = "comboAreaLibro" value = "<bean:write name="objsol13" property="cod_GLA"/>">
						<td colspan="4"><strong>Para efectuar la búsqueda en la Extranet presiona aqui</strong>
						    <logic:equal name="objsol13" property="flagmetodo" value="1">
									<A href="javascript:doBuscar(6,1)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
						    <logic:equal name="objsol13" property="flagmetodo" value="2">
									<A href="javascript:doBuscar(5,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
						    <logic:equal name="objsol13" property="flagmetodo" value="3">
									<A href="javascript:doBuscar(6,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
							<logic:equal name="objsol13" property="flagmetodo" value="4">
									<A href="javascript:doBuscar(5,1)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
							<logic:equal name="objsol13" property="flagmetodo" value="5">
									<A href="javascript:doBuscar(4,1)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
							<logic:equal name="objsol13" property="flagmetodo" value="6">
									<A href="javascript:doBuscar(4,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
						    <logic:equal name="objsol13" property="flagmetodo" value="7">
									<A href="javascript:doBuscar(4,3)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
							<logic:equal name="objsol13" property="flagmetodo" value="8">
									<A href="javascript:doBuscar(4,4)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
							<logic:equal name="objsol13" property="flagmetodo" value="9">
									<A href="javascript:doBuscar(1,1)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>        	        	
				        	<logic:equal name="objsol13" property="flagmetodo" value="10">
									<A href="javascript:doBuscar(1,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
				        	<logic:equal name="objsol13" property="flagmetodo" value="11">
									<A href="javascript:doBuscar(3,51)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
				        	<logic:equal name="objsol13" property="flagmetodo" value="12">
									<A href="javascript:doBuscar(3,7)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
				        	<logic:equal name="objsol13" property="flagmetodo" value="13">
									<A href="javascript:doBuscar(3,8)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
				        	<logic:equal name="objsol13" property="flagmetodo" value="14">
									<A href="javascript:doBuscar(3,9)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
				        	<logic:equal name="objsol13" property="flagmetodo" value="15">
									<A href="javascript:doBuscar(3,61)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
				        	<logic:equal name="objsol13" property="flagmetodo" value="41">
									<A href="javascript:doBuscar(5,1)"><IMG src="images/btn_buscar.gif" border="0"></A>					
				        	</logic:equal>
						</logic:equal>
						<!-- Fin:mgarate:10/08/2007 -->
						<logic:equal name="objsol13" property="tpo_cert" value="N">
						<!-- Inicio: ifigueroa 14/08/2007 -->
						<logic:equal name="objsol13" property="certificado_id" value="18">
						<input type="hidden" name = "comboAreaLibro" value = "21">
							<td colspan="4"><strong>Para efectuar la búsqueda en la Extranet presiona aqui</strong>     	
				        		<!-- busqueda en por indice de partidas por bienes -->
									<A href="javascript:doBuscar(8,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
				        	</td>
						</logic:equal>
						<!-- Fin: ifigueroa 14/08/2007 -->
						<logic:notEqual name="objsol13" property="certificado_id" value="18">
				        	<input type="hidden" name = "comboAreaLibro" value = "<bean:write name="objsol13" property="cod_GLA"/>">
					   		<td colspan="4"><strong>Para efectuar la búsqueda en la Extranet presiona aqui</strong>     	
				        	<!-- Area Registral: Registro de Propiedad Inmueble -->
				        	<logic:equal name="objsol13" property="area_reg_id" value="21000">
				        		<logic:equal name="objsol13" property="tpo_pers" value="N">		
				        			<!-- busqueda en RPI por participante PN -->
									<A href="javascript:doBuscar(1,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
								</logic:equal>
								<logic:equal name="objsol13" property="tpo_pers" value="J">		
									<!-- busqueda en RPI por participante PJ -->
									<A href="javascript:doBuscar(1,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
								</logic:equal>        	
				        	</logic:equal>        	        	
				        	<!-- Area Registral: Registro de Personas Juridicas -->        	
				        	<logic:equal name="objsol13" property="area_reg_id" value="22000">
				        		<logic:equal name="objsol13" property="tpo_pers" value="N">		
				        			<!-- busqueda en RPJ por participante PN -->
									<A href="javascript:doBuscar(2,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
								</logic:equal>
								<logic:equal name="objsol13" property="tpo_pers" value="J">		
									<!-- busqueda en RPJ por participante PJ -->
									<A href="javascript:doBuscar(2,3)"><IMG src="images/btn_buscar.gif" border="0"></A>					
								</logic:equal>        	
				        	</logic:equal>
				        	<!-- Area Registral: Registro de Personas Naturales -->        	
				        	<logic:equal name="objsol13" property="area_reg_id" value="23000">
				        		<logic:equal name="objsol13" property="tpo_pers" value="N">		
				        			<!-- busqueda en RPN por participante PN -->
									<A href="javascript:doBuscar(3,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
								</logic:equal>
								<logic:equal name="objsol13" property="tpo_pers" value="J">		
									<!-- busqueda en RPN por participante PJ -->
									<A href="javascript:doBuscar(3,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
								</logic:equal>        	
				        	</logic:equal>
				        	<!-- Area Registral: Registro de Propiedad Vehicular -->
				        	<logic:equal name="objsol13" property="area_reg_id" value="24000">
				        		<logic:equal name="objsol13" property="tpo_pers" value="N">		
				        			<!-- busqueda en RPV por participante PN  -->
									<A href="javascript:doBuscar(4,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
								</logic:equal>
								<logic:equal name="objsol13" property="tpo_pers" value="J">		
									<!-- busqueda en RPV por participante PJ -->
									<A href="javascript:doBuscar(4,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
								</logic:equal>        	
				        	</logic:equal>
        				</td>    
        				</logic:notEqual> 	
        			</logic:equal>
		        	<logic:equal name="objsol13" property="tpo_cert" value="L">
        				<td colspan="4"><strong>Para visualizar la partida en la Extranet presione aqui</strong>
        					<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol13" property="objeto_sol_id"/>">	            			
							<a href="javascript:VerPartida('<bean:write name="objsol13" property="refnum"/>','verifica')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
						</td>
		        	</logic:equal> 
		        	<!-- Aqui iran las busquedas de los certificados de Gravamen RJB, Dominial, CREM, Vigencia, busquedas -->       	
		        	<!-- Certificado RMC Gravamen y Vigencia -->
		        	<logic:equal name="objsol13" property="tpo_cert" value="R">
		        	<!-- inicio: ifigueroa: 20/08/07 -->
		        	<logic:equal name="objsol13" property="certificado_id" value="19"> 
        				<td colspan="4"><strong>Para visualizar la búsqueda en la Extranet presione aqui</strong>
        					<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol13" property="objeto_sol_id"/>">	            			
        					<input type="hidden" name = "comboAreaLibro" value = "21">
        					<logic:equal name="objsol13" property="tpo_pers" value="N">
								<a href="javascript:doBuscar(9,1)"><img src="images/btn_buscar.gif" border="0"  ></a>
							</logic:equal>
							<logic:equal name="objsol13" property="tpo_pers" value="J">
								<a href="javascript:doBuscar(9,2)"><img src="images/btn_buscar.gif" border="0"  ></a>
							</logic:equal>	
							<logic:equal name="objsol13" property="tpo_pers" value="T">
								<a href="javascript:doBuscar(9,3)"><img src="images/btn_buscar.gif" border="0"  ></a>
							</logic:equal>					
						</td>
		        	</logic:equal>
		        	<logic:equal name="objsol13" property="certificado_id" value="20"> 
        				<td colspan="4"><strong>Para visualizar la búsqueda en la Extranet presione aqui</strong>
        					<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol13" property="objeto_sol_id"/>">	            			
        					<input type="hidden" name = "comboAreaLibro" value = "21">
        					<logic:equal name="objsol13" property="tpo_pers" value="N">
								<a href="javascript:doBuscar(10,1)"><img src="images/btn_buscar.gif" border="0"  ></a>				
							</logic:equal>
							<logic:equal name="objsol13" property="tpo_pers" value="J">
								<a href="javascript:doBuscar(10,2)"><img src="images/btn_buscar.gif" border="0"  ></a>				
							</logic:equal>	
							<logic:equal name="objsol13" property="tpo_pers" value="T">
								<a href="javascript:doBuscar(10,3)"><img src="images/btn_buscar.gif" border="0"  ></a>
							</logic:equal>					
											
						</td>
		        	</logic:equal>
		        	<!-- fin: ifigueroa: 20/08/07 -->
		        	</logic:equal>
		        	<!-- Certificado CREM -->
		        	<!-- inicio: jrosas: 10-08-07 -->
		        	<logic:equal name="objsol13" property="tpo_cert" value="C">
						<input type="hidden" name = "comboAreaLibro" value = "28">
				   		<td colspan="4"><strong>Para efectuar la búsqueda en la Extranet presiona aqui</strong>     	
			        		<logic:equal name="objsol13" property="tpo_pers" value="N">		
			        			<!-- busqueda en SIGC por participante PN -->
								<A href="javascript:doBuscar(7,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
							</logic:equal>
							<logic:equal name="objsol13" property="tpo_pers" value="J">		
								<!-- busqueda en SIGC por participante PJ -->
								<A href="javascript:doBuscar(7,2)"><IMG src="images/btn_buscar.gif" border="0"></A>					
							</logic:equal>        	
			        	</td>
		        	</logic:equal>
		        	<!-- fin: jrosas: 10-08-07 -->
		        	<!--Inicio:mgarate:10-08-2007 -->
		        	<!-- Certificado RJB Gravamen -->
		        	<logic:equal name="objsol13" property="tpo_cert" value="G">
		        	    <input type="hidden" name = "comboAreaLibro" value = "<bean:write name="objsol13" property="cod_GLA"/>">
        				<td colspan="4"><strong>Para efectuar la búsqueda en la Extranet presiona aqui</strong>
        				  <logic:equal name="tipoRegistro" value="V">
        					<A href="javascript:doBuscar(5,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
        				  <logic:equal name="tipoRegistro" value="E">
        					<A href="javascript:doBuscar(5,2)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
        				  <logic:equal name="tipoRegistro" value="B">
        					<A href="javascript:doBuscar(5,3)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
        				  <logic:equal name="tipoRegistro" value="A">
        					<A href="javascript:doBuscar(5,4)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>	
						</td>
		        	</logic:equal>
		        	<!-- Certificado RJB Dominial -->
		        	<logic:equal name="objsol13" property="tpo_cert" value="D">
		        		<input type="hidden" name = "comboAreaLibro" value = "<bean:write name="objsol13" property="cod_GLA"/>">
        				<td colspan="4"><strong>Para efectuar la búsqueda en la Extranet presiona aqui</strong>
        				  <logic:equal name="tipoRegistro" value="V">
        					<A href="javascript:doBuscar(5,1)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
        				  <logic:equal name="tipoRegistro" value="E">
        					<A href="javascript:doBuscar(5,2)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
        				  <logic:equal name="tipoRegistro" value="B">
        					<A href="javascript:doBuscar(5,3)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
        				  <logic:equal name="tipoRegistro" value="A">
        					<A href="javascript:doBuscar(5,4)"><IMG src="images/btn_buscar.gif" border="0"></A>										
        				  </logic:equal>
						</td>
		        	</logic:equal>
		        	<!--Fin:mgarate -->
				</logic:iterate> 
			</tr>
			<!-- Inicio:jascencio:08/08/2007 -->
			<tr>
				<td>&nbsp;</td>
				<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol14" scope="request">				
				<logic:equal name="objsol14" property="tpo_cert" value="L">
					<logic:equal name="objsol14" property="cod_GLA" value="21">
						<logic:present name="objsol14" property="refNumParAnterior">
							<td colspan="4"><strong>Para visualizar la partida antigua en la Extranet presione aqui</strong>
		       					<input type="hidden" name="objetoSolicitudId" value="<bean:write name="objsol14" property="objeto_sol_id"/>">	            			
								<a href="javascript:VerPartida('<bean:write name="objsol14" property="refNumParAnterior"/>','verifica')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
							</td>
						</logic:present>
					</logic:equal>
		       	</logic:equal> 
				</logic:iterate>
			</tr>
			<!-- Fin:jascencio -->
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
		 <!--  inicio:jrosas 01-06-2007
		   SUNARP-REGMOBCOM: Modificación  de tipo de certificado  -->  
		
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol15" scope="request">				
				<tr>	
					<td>&nbsp;</td>
					<td colspan="2">&nbsp;<strong>Elegir el tipo de certificado:</strong></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>	
				<!-- Certificado de Busqueda-->
				<!-- Inicio:mgarate:07/06/2007 -->
				<logic:equal name="objsol15" property="tpo_cert" value="B"> 
				<input type="hidden" name="tpo_cert" value="B">		
   					<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
							<td>&nbsp;</td>
						</tr>		
						<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="B" onclick="javascript:ValorRadio('B');"><strong>Certificado de Busqueda</strong></td>
							<td>&nbsp;</td>
					</tr>
					<tr>	
						<td>&nbsp;</td>
						<td >&nbsp;</td>	
							<td >&nbsp;</td>	
						 <logic:equal name="Solicitud" property="estado" value="C">
			 	     		<td colspan="2"><a href="javascript:CertificadoPorVerificar();" onmouseover="javascript:mensaje_status('Impresion Preliminar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Impresión Preliminar </a></td>
			 	        </logic:equal>      
			    	</tr>
			    </logic:equal>
			    <!-- Fin:mgarate:07/06/2007 -->
				<!-- Certificado Negativo--> 
		        <logic:equal name="objsol15" property="tpo_cert" value="N"> 
		        <input type="hidden" name="tpo_cert" value="N">
		         <!-- Inicio:ifigueroa :20/08/2007 -->
		        <logic:equal name="objsol15" property="certificado_id" value="18"> 
		        	<input type="hidden" name="hidCertificadoId" value="18">
		        </logic:equal>
		        <logic:notEqual name="objsol15" property="certificado_id" value="18"> 
		        	<input type="hidden" name="hidCertificadoId" value="">
		        </logic:notEqual>
		         <!-- Fin:ifigueroa:20/08/2007 -->
			   		<tr>	
						<td>&nbsp;</td>
						<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
						<td>&nbsp;</td>
					</tr>		
					<tr>	
						<td>&nbsp;</td>
						<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="N" onclick="javascript:ValorRadio('N');" ><strong>Certificado Negativo </strong></td>
						<td>&nbsp;</td>
					</tr>	
					<tr>	
						<td>&nbsp;</td>
						<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="P" onclick="javascript:ValorRadio('P');"><strong>Certificado Positivo</strong></td>
						<td >&nbsp;</td>		
						 <logic:equal name="Solicitud" property="estado" value="C">
			 	     		<td colspan="2"><a href="javascript:CertificadoPorVerificar();" onmouseover="javascript:mensaje_status('Impresion Preliminar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Impresión Preliminar </a></td>
			 	        </logic:equal>      
			         		<%--<logic:equal name="Solicitud" property="estado" value="V">
				      		<td><a href="cargaRegistralDetalle2.htm" onmouseover="javascript:mensaje_status('Expedir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				      		<IMG src="images/btn_emitir.gif" border="0">
				      		</A></td>
				         </logic:equal>--%>
			    	</tr>	
				    <!-- guarda el valor del tipo de certificado negativo elegido-->	
			    </logic:equal> 	
			    <!-- Fin de Certificado Positivo/Negativo -->
			    
			    <!-- Certificado Gravamen/Vigencia -->
			    <logic:equal name="objsol15" property="tpo_cert" value="R"> 
			        <!-- Gravamen -->
			        
				    <logic:equal name="objsol15" property="certificado_id" value="20"> 
					    <input type="hidden" name="tpo_cert" value="RMCG" >
				    	<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
							<td>&nbsp;</td>
						</tr>		
						<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="G" onclick="javascript:ValorRadio('G');" ><strong>Gravamen </strong></td>
							<td>&nbsp;</td>
						</tr>	
					</logic:equal>	
					<!-- Fin de Gravamen -->
					
			        <!-- Vigencia -->
				    <logic:equal name="objsol15" property="certificado_id" value="19">
				    	<input type="hidden" name="tpo_cert" value="RMCV" >
				    	<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
							<td>&nbsp;</td>
						</tr>		
						<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="V" onclick="javascript:ValorRadio('V');" ><strong>Vigencia </strong></td>
							<td>&nbsp;</td>
						</tr>	
					</logic:equal>	
					<logic:equal name="Solicitud" property="estado" value="C">
			 	     <tr>
			 	     		<td>&nbsp;</td>
			 	     		<td>&nbsp;</td>
			 	     		<td colspan="2"><a href="javascript:CertificadoPorVerificar();" onmouseover="javascript:mensaje_status('Impresion Preliminar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Impresión Preliminar </a></td>
			 	     </tr>
			 	     </logic:equal>  
					<!-- Fin de Vigencia -->
				</logic:equal>	
			    <!-- Fin de Certificado de Gravamen/Vigencia -->
			    
			     <!-- Certificado Gravamen RJB -->
			    <logic:equal name="objsol15" property="tpo_cert" value="G"> 
			    <input type="hidden" name="tpo_cert" value="RJBG">
			    	<tr>	
						<td>&nbsp;</td>
						<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
						<td>&nbsp;</td>
					</tr>		
					<tr>	
						<td>&nbsp;</td>
						<td colspan="3">&nbsp;<INPUT type="radio" name="radCertNeg" value="M" onclick="javascript:ValorRadio('M');" ><strong>Certificado Compendioso de historial de gravámenes </strong></td>
						<!--  <td>&nbsp;</td> -->
					</tr>	
					<logic:equal name="Solicitud" property="estado" value="C">
			 	     <tr>
			 	     		<td>&nbsp;</td>
			 	     		<td>&nbsp;</td>
			 	     		<td colspan="3" align="right"><a href="javascript:CertificadoPorVerificar();" onmouseover="javascript:mensaje_status('Impresion Preliminar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Impresión Preliminar </a></td>
			 	     </tr>
			 	     </logic:equal>
			    </logic:equal>
			    
			    <!-- Fin de Certificado de Gravamen -->

			     <!-- Certificado Dominial RJB -->
			    <logic:equal name="objsol15" property="tpo_cert" value="D"> 
			    <input type="hidden" name="tpo_cert" value="RJBD">
			    	<tr>	
						<td>&nbsp;</td>
						<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
						<td>&nbsp;</td>
					</tr>		
					<tr>	
						<td>&nbsp;</td>
						<td colspan="3">&nbsp;<INPUT type="radio" name="radCertNeg" value="D" onclick="javascript:ValorRadio('D');" ><strong>Certificado Compendioso de antecedentes dominiales </strong></td>
						<!--  <td>&nbsp;</td> -->
					</tr>
					<logic:equal name="Solicitud" property="estado" value="C">
			 	     <tr>
			 	     		<td>&nbsp;</td>
			 	     		<td>&nbsp;</td>
			 	     		<td colspan="3" align="right"><a href="javascript:CertificadoPorVerificar();" onmouseover="javascript:mensaje_status('Impresion Preliminar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Impresión Preliminar </a></td>
			 	     </tr>
			 	     </logic:equal>	
			    </logic:equal>
			    <!-- Fin de Certificado dominial -->
			    
			    <!-- Certificado CREM :Registro de Mobiliaro, Historico, Condicionado-->
			    <logic:equal name="objsol15" property="tpo_cert" value="C"> 
				    <!-- Registro de Mobiliario -->
				    
			    	<logic:equal name="objsol15" property="certificado_id" value="21"> 
			    		<input type="hidden" name="tpo_cert" value="CREMA">
				    	<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
							<td>&nbsp;</td>
						</tr>		
						<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="A" onclick="javascript:ValorRadio('A');" ><strong>Certificado de Registro Mobiliaro </strong></td>
							<td>&nbsp;</td>
						</tr>	
					</logic:equal>	
					<!-- Fin de Registro de Mobiliaro -->
				    <!-- Registro CREM Historico -->
			    	<logic:equal name="objsol15" property="certificado_id" value="22"> 
			    		<input type="hidden" name="tpo_cert" value="CREMH">
				    	<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
							<td>&nbsp;</td>
						</tr>		
						<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="H" onclick="javascript:ValorRadio('H');" ><strong>Certificado CREM Histórico </strong></td>
							<td>&nbsp;</td>
						</tr>	
					</logic:equal>	
					<!-- Fin de CREM Historico -->
				    <!-- Registro CREM Condicionado -->
			    	<logic:equal name="objsol15" property="certificado_id" value="23"> 
			    		<input type="hidden" name="tpo_cert" value="CREMC">
				    	<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="I" onclick="javascript:ValorRadio('I');"><strong>Improcedente </strong></td>
							<td>&nbsp;</td>
						</tr>		
						<tr>	
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;<INPUT type="radio" name="radCertNeg" value="C" onclick="javascript:ValorRadio('C');" ><strong>Certificado CREM Condicionado</strong></td>
							<td>&nbsp;</td>
						</tr>	
					</logic:equal>	
					<logic:equal name="Solicitud" property="estado" value="C">
			 	     <tr>
					 	    <td>&nbsp;</td>
							<td >&nbsp;</td>	
							<td >&nbsp;</td>	
			 	     		<td colspan="3"><a href="javascript:CertificadoPorVerificar();" onmouseover="javascript:mensaje_status('Impresion Preliminar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Visualizaci&oacute;n Preliminar </a></td>
			 	     </tr>
			 	     </logic:equal>
					<!-- Fin de CREM Condicionado -->
			    </logic:equal>	
			    <!-- Fin de Certificado CREM -->
			    			    
			    <!-- Certificado de Copia Literal -->	
		        <logic:equal name="objsol15" property="tpo_cert" value="L"> 		
		        <input type="hidden" name="tpo_cert">
   					<tr>	
			   			<!-- Copia Literal--> 
			   			<td>&nbsp;</td>
				    	<td colspan="3"><input type="checkbox" name="chkImprocedente" value="checkbox">Copia Literal de Partida Improcedente </td>
				    	    
   					</tr>
			   </logic:equal> 
			   <!-- Fin de Certificado Copia Literal -->
			   
			</logic:iterate> 
			
		   <!-- fin:jrosas 01-06-2007 -->	
		  	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		 <!--  inicio:jrosas 01-06-2007
		   SUNARP-REGMOBCOM: Agregacion del campo titulo   -->  
	
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol16" scope="request">
				<!-- CERTIFICADO POSITIVO/NEGATIVO:N -->
			 	<logic:equal name="objsol16" property="tpo_cert" value="N">  
	    			<logic:notEqual name="objsol16" property="certificado_id" value="18">		
					  	<tr>
							<td>&nbsp;</td>
							<td><STRONG>Mensaje para el Emisor:</STRONG></td>
					      	<td>&nbsp;</td>
					      	<td>&nbsp;</td>
			      			<td>&nbsp;</td>
						</tr>
					</logic:notEqual>
					<logic:equal name="objsol16" property="certificado_id" value="18">
					  	<tr>
							<td>&nbsp;</td>
							<td><STRONG>Comentario:</STRONG></td>
					      	<td>&nbsp;</td>
					      	<td>&nbsp;</td>
			      			<td>&nbsp;</td>
						</tr>
					</logic:equal>
				</logic:equal>
				<!-- CERTIFICADO DIFERENTE DE POSITIVO/NEGATIVO -->
				<logic:notEqual name="objsol16" property="tpo_cert" value="N">
					<!-- CERTIFICADO DE COPIA LITERAL-->
					<logic:equal name="objsol16" property="tpo_cert" value="L">
						<tr>
							<td>&nbsp;</td>
							<td><STRONG>Mensaje para el Emisor:</STRONG></td>
					      	<td>&nbsp;</td>
				    	  	<td>&nbsp;</td>
		      				<td>&nbsp;</td>
						</tr>
					</logic:equal>
					<!-- CERTIFICADO DIFERENTE DE COPIA LITERAL-->
					<logic:notEqual name="objsol16" property="tpo_cert" value="L">
						<logic:equal name="objsol16" property="tpo_cert" value="D">
					  		<tr>
								<td>&nbsp;</td>
								<td><STRONG>Mensaje para el Emisor:</STRONG></td>
						      	<td>&nbsp;</td>
						      	<td>&nbsp;</td>
				      			<td>&nbsp;</td>
							</tr>
						</logic:equal>
						<logic:notEqual name="objsol16" property="tpo_cert" value="D">
							<tr>
								<td>&nbsp;</td>
								<td><STRONG>Comentario:</STRONG></td>
						      	<td>&nbsp;</td>
						      	<td>&nbsp;</td>
				      			<td>&nbsp;</td>
							</tr>
						</logic:notEqual>
					</logic:notEqual>
				</logic:notEqual>
			</logic:iterate>
			
		  <!-- fin:jrosas 01-06-2007 -->			
		  	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		    <tr> 
				<td>&nbsp;</td>
	 	     	<td colspan="4"><div align="center">
	          	<textarea name="Comentario" onkeypress="javascript:corta(1);" onkeyup="javascript:corta(1);" cols="64" rows="8" ></textarea>
	        	</div></td>
	    	</tr>
	    	<!-- Inicio:mgarate:07/06/2007 -->
	    	<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol17" scope="request">
	    		<logic:notEqual name="objsol17" property="tpo_cert" value="B">
			  		<tr>
						<td>&nbsp;</td>
						<td><STRONG>Constancia:</STRONG></td>
				      	<td>&nbsp;</td>
				      	<td>&nbsp;</td>		
				      	<td>&nbsp;</td>				
					</tr>	
				</logic:notEqual>
				<logic:equal name="objsol17" property="tpo_cert" value="B">
					<tr>
						<td>&nbsp;</td>
						<td><STRONG>Titulos:</STRONG></td>
				      	<td>&nbsp;</td>
				      	<td>&nbsp;</td>		
				      	<td>&nbsp;</td>				
					</tr>
				</logic:equal>
			</logic:iterate>
			<!-- Fin:mgarate:07/06/2007 -->
			 <!--  inicio:jrosas 06-06-2007
		   SUNARP-REGMOBCOM: pinta campo constancia o constancia2 -->  
			
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol18" scope="request">
				<!-- CERTIFICADO POSITIVO/NEGATIVO:N -->
			 	<logic:equal name="objsol18" property="tpo_cert" value="N">  
			 		<logic:equal name="objsol18" property="certificado_id" value="18">
	    			   	<tr> 
							<td>&nbsp;</td>
			 	     	    <td colspan="4"><div align="center">
			 	     	    <!-- Inicio: ifigueroa 09/08/2007 -->
			 	     	    <logic:present name="constancia">
			          	   		<textarea name="Constancia_temp" cols="64" rows="16" wrap="off"><%=(String)request.getAttribute("constancia")%></textarea>
			          	   		<input name="Constancia2" type="hidden">
			          	   	</logic:present>
			          	   	<logic:notPresent name="constancia">
				 	         	<textarea name="Constancia2" onkeypress="javascript:corta(2);" onkeyup="javascript:corta(2);" cols="64" rows="16" wrap="off"></textarea>
				 	         </logic:notPresent>	
				 	         <!-- Fin: ifigueroa 09/08/2007 -->
				          </div></td>
			            </tr>
			 		</logic:equal>
	    			<logic:notEqual name="objsol18" property="certificado_id" value="18">		
	    			   	<tr> 
							<td>&nbsp;</td>
			 	     	    <td colspan="4"><div align="center">
			          	    <textarea name="Constancia" onkeypress="javascript:corta(2);" onkeyup="javascript:corta(2);" cols="64" rows="8"></textarea>
				          </div></td>
			            </tr>
	    			</logic:notEqual>
	    		</logic:equal>
	    		<!-- CERTIFICADO DIFERENTE DE POSITIVO/NEGATIVO:N -->
	    		<logic:notEqual name="objsol18" property="tpo_cert" value="N">
	    			<!-- CERTIFICADO COPIA LITERAL -->
	    			<logic:equal name="objsol18" property="tpo_cert" value="L">
	    				<tr> 
							<td>&nbsp;</td>
			 	     	    <td colspan="4"><div align="center">
			          	    <textarea name="Constancia" onkeypress="javascript:corta(2);" onkeyup="javascript:corta(2);" cols="64" rows="8"></textarea>
				          </div></td>
			            </tr>
	    			</logic:equal>
	    			<logic:notEqual name="objsol18" property="tpo_cert" value="L">
	    				<tr> 
							<td>&nbsp;</td>
			 	     	    <td colspan="4" align="left"><div align="center">
			 	     	    <!-- Inicio:mgarate:07/06/2007 -->
			 	     	    <logic:notEqual name="objsol18" property="tpo_cert" value="B">
			 	     	    	<%-- inicio:jrosas:03/08/2007 --%>
			 	     	    	<%-- descripcion: Si el certificado fuera CREM Condicionado, el contenido de la constancia se obtendra de la variable constancia del request,
			 	     	    					  caso contrario, solo mostrara el comentario en blanco. --%>
			 	     	    	<logic:present name="constancia">
												<textarea name="Constancia_temp" cols="64" rows="16"
													wrap="off"><%=(String)request.getAttribute("constancia")%></textarea>
												<input name="Constancia2" type="hidden">
								</logic:present>
				          	    <logic:notPresent name="constancia">
				          	      <%-- inicio:mgarate:07/08/2007 --%>
					          	    <textarea name="Constancia_temp" cols="64" rows="16" wrap="off"><%=(String)request.getAttribute("constancia")%></textarea>
					          	    <input name="Constancia2" type="hidden">
					          	  <%-- inicio:mgarate:07/08/2007 --%>
				          	    </logic:notPresent>
				          	    <%-- final:jrosas:03/08/2007 --%>
			          	    </logic:notEqual>
			          	    <logic:equal name="objsol18" property="tpo_cert" value="B">
			          	    	<textarea style="text-align: left" name="Constancia2" cols="64" rows="16" wrap="off">Se deja constancia que realizada la búsqueda en el indice de titulos ingresados al Registro de Propiedad <logic:equal name="objsol18" property="certificado_id" value="33">Vehicular</logic:equal><logic:equal name="objsol18" property="certificado_id" value="34">de Embarcaciones Pesqueras</logic:equal><logic:equal name="objsol18" property="certificado_id" value="35">de Aeronaves</logic:equal><logic:equal name="objsol18" property="certificado_id" value="36">de Buques</logic:equal><logic:equal name="objsol18" property="certificado_id" value="37">Vehicular</logic:equal><logic:equal name="objsol18" property="certificado_id" value="38">de Embarcaciones Pesqueras</logic:equal><logic:equal name="objsol18" property="certificado_id" value="39">de Aeronaves</logic:equal><logic:equal name="objsol18" property="certificado_id" value="40">de Buques</logic:equal>, hasta las 24 horas anteriores a la expedición del presente certificado no se han encontrado titulos pendiente, respecto a dichas partidas.</textarea>
			          	    </logic:equal>
							<!-- Fin:mgarate:07/06/2007 -->
			          	    
				          </div></td>
			            </tr>
	    			</logic:notEqual>
	    		</logic:notEqual>
	    	</logic:iterate>		
            
            <!-- fin:jrosas 06-06-2007 -->
	      	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		 <!--  inicio:jrosas 01-06-2007
		   SUNARP-REGMOBCOM: Agregacion del campo titulo   -->  
			
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol19" scope="request">
			    <!-- certificado positivo/negativo -->
				<logic:equal name="objsol19" property="tpo_cert" value="N">  
	    			<logic:equal name="objsol19" property="certificado_id" value="18">		
	    				<tr>
							<td>&nbsp;</td>
							<td><STRONG>T&iacute;tulos:</STRONG></td>
					      	<td>&nbsp;</td>
					      	<td>&nbsp;</td>		
			      			<td>&nbsp;</td>				
						</tr>	
						<tr> 
							<td>&nbsp;</td>
				 	     	<td colspan="4"><div align="center">
    			      		  <!-- Inicio: ifigueroa 09/08/2007 -->
							    <logic:present name="titulos">
									<textarea name="titulos_temp" cols="64" rows="8" wrap="off" onkeypress="javascript:limita_largo(1024);" onkeyup="javascript:limita_largo(1024);"><%=(String)request.getAttribute("titulos")%></textarea>
								</logic:present>
								<input name="txtTitulos" type="hidden">
								<logic:notPresent name="titulos">
									<textarea name="txtTitulos" onkeypress="javascript:corta(2);" onkeyup="javascript:corta(2);" cols="64" rows="8"></textarea>
								 </logic:notPresent>	
							<!-- Fin: ifigueroa 09/08/2007 -->

				        	</div></td>
		    			</tr>
		    			<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</logic:equal>
				</logic:equal>		
				<!-- certificado diferente de psotivio/negativo -->
				<logic:notEqual name="objsol19" property="tpo_cert" value="N">
					<logic:notEqual name="objsol19" property="tpo_cert" value="L">
						<logic:equal name="objsol19" property="tpo_cert" value="R"> <!-- Certificado tipo vigencia o Gravamen RMC -->
		                	<tr>
								<td>&nbsp;</td>
								<td><STRONG>T&iacute;tulos:</STRONG></td>
						      	<td>&nbsp;</td>
						      	<td>&nbsp;</td>		
				      			<td>&nbsp;</td>				
							</tr>	
							<tr> 
								<td>&nbsp;</td>
					 	     	<td colspan="4"><div align="center">
					 	     	 <!-- Inicio: ifigueroa 09/08/2007 -->
							    <logic:present name="titulos">
									<textarea name="titulos_temp" cols="64" rows="8" wrap="off" onkeypress="javascript:limita_largo(1024);" onkeyup="javascript:limita_largo(1024);"><%=(String)request.getAttribute("titulos")%></textarea>
								</logic:present>
								<input name="txtTitulos" type="hidden">
								<logic:notPresent name="titulos">
									<textarea name="txtTitulos" onkeypress="javascript:corta(2);" onkeyup="javascript:corta(2);" cols="64" rows="8"></textarea>
								 </logic:notPresent>	
							<!-- Fin: ifigueroa 09/08/2007 -->
			    			      
					        	</div></td>
			    			</tr>
			    			<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</logic:equal>
	                </logic:notEqual> 		
				</logic:notEqual>
			</logic:iterate>	
			
		<!-- fin:jrosas 01-06-2007 -->	
    		<tr>	
				<td>&nbsp;</td>
				<td colspan="3">&nbsp;</td>
				<td colspan="2"><INPUT type="checkbox" name="chkVeriManu" value="manu">&nbsp;<strong>Efectu&oacute; verificaci&oacute;n local:</strong></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr> 
		      <td>&nbsp;</td>
		      <td colspan="3"><STRONG>Ingrese su clave para efectuar la verificaci&oacute;n:</STRONG></td>
		      <td><input name="usrPass" type="password" size="12" maxlength="10"  onblur="sololet(this)"></td>
		      <td><div align="center"><a href="javascript:GuardarCertVerificado();" onmouseover="javascript:mensaje_status('Guardar Certificado Verificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_grabar.gif" border="0"></A></div></td>
   			</tr>
	      	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
  		</table>
	</logic:equal>				  
    </logic:present>
    
    <!-- Solicitud en estado= Por Emitir -->
	<logic:present name="Solicitud" scope="request">				
	   <logic:equal name="Solicitud" property="estado" value="V"> 
			<table class=cabeceraformulario cellspacing=0> 
		 	 	<tr>  	    
		  		 	<td>&nbsp;</td>
		   		   	<td><strong>RESULTADOS DE LA VERIFICACIÓN </strong></td>  						
		  		</tr>  
		  	</table>
	  		 <table class=formulario cellspacing=0>
		  		 <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol20" scope="request">		
			        <!-- Certificado Negativo--> 
			        <logic:equal name="objsol20" property="tpo_cert" value="N">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<logic:present name="Solicitud" scope="request">				
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<td colspan="4"><strong>Para visualizar el 					  				
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="N">
									Certificado Negativo
								</logic:equal>
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="P">
									Certificado Positivo
								</logic:equal>	
								presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
							</strong></td>						
					  		</logic:present>														
						</tr>	
					</logic:equal>		
					<!--  inicio:jrosas 04-06-2007
						   SUNARP-REGMOBCOM: Agrego condicional solo para certificado dominial ya que los demas 
						   pasan a estado por despachar -->  
					
					 <!-- Certificado Dominial--> 
			        <logic:equal name="objsol20" property="tpo_cert" value="D">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<td colspan="4"><strong>Para visualizar el 					  				
									Certificado Compendioso de antecedentes dominiales
									presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
								</strong>
							</td>						
						</tr>	
					</logic:equal>	
				    <!-- fin:jrosas 04-06-2007 -->
						
					<logic:equal name="objsol20" property="tpo_cert" value="L">
						<tr>	
							<td>&nbsp;</td>
							<td colspan="4"><strong>Para visualizar la partida en la Extranet presione aqui </strong>
								<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol20" property="objeto_sol_id"/>">
								<a href="javascript:VerPartida('<bean:write name="objsol20" property="refnum"/>','expide')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
							</td>	
						</tr>	
			        </logic:equal>        	
				</logic:iterate> 
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><STRONG>Mensaje para el Emisor:</STRONG></td>
		      		<td>&nbsp;</td>
				</tr>	
	 				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
		   		<tr> 
			  	     <td>&nbsp;</td>
		             <td colspan="4">
		             <logic:present name="Solicitud" scope="request">
		                 <div align="center">      
			             <textarea name="ComentarioExpedir" readonly="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
			             </textarea>
			             </div>
			         </logic:present>
		             </td>
		   		 </tr>
				 <tr>
					<td>&nbsp;</td>
					<td><STRONG>Constancia:</STRONG></td>
		      		<td>&nbsp;</td>
				</tr>	
				 <logic:present name="Solicitud" scope="request">
		             <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol21" scope="request">
						<!-- CERTIFICADO POSITIVO/NEGATIVO:N -->
					 	<logic:equal name="objsol21" property="tpo_cert" value="N"> 
						 	<tr> 
								<td>&nbsp;</td>
				 	     	    <td colspan="4"><div align="center">              
				                <textarea name="ComentarioExpedir" readonly="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
  			                    </textarea></div></td>
  			                 </tr>   
			    		</logic:equal>
			    		<!-- CERTIFICADO DIFERENTE DE POSITIVO/NEGATIVO:N -->
			    		<logic:notEqual name="objsol21" property="tpo_cert" value="N">
			    			<logic:equal name="objsol21" property="tpo_cert" value="L">
			    				<tr> 
									<td>&nbsp;</td>
					 	     	    <td colspan="4"><div align="center">              
					                <textarea name="ComentarioExpedir" readonly="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
  				                    </textarea></div></td>
  			                    </tr>   
			    			</logic:equal>
			    			<logic:notEqual name="objsol21" property="tpo_cert" value="L"> <!-- CERTIFICADO DOMINIAL -->
				    			<tr> 
									<td>&nbsp;</td>
					 	     	    <td colspan="4"><div align="center">
					          	    <textarea name="Constancia" readonly="true" cols="64" rows="8" wrap="off"><bean:write name="Solicitud" property="constancia" filter="false" scope="request"/></textarea>
						            </div></td>
				    	        </tr>
			    			</logic:notEqual>
			    		</logic:notEqual>
	    			 </logic:iterate>		
		           </logic:present>
	 			<tr> 
	       	      <td>&nbsp;</td>
	     	      <td colspan="3"><STRONG>Ingrese su clave para emitir la publicidad:</STRONG></td>
	    	      <td><input name="usrPassExp" type="password" size="12" maxlength="10"  onblur="sololet(this)"></td>
	   	          <td><div align="center"><a href="javascript:GuardarCertExpedido();" onmouseover="javascript:mensaje_status('Guardar Certificado Verificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_grabar.gif" border="0"></A></div></td>
	  	     	</tr>
		  	     <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				 </tr>		
 				</table>		
		</logic:equal>				  
	</logic:present>
	
	<!-- Solicitud en estado= Despachada -->
	<!--  inicio:jrosas 04-06-2007
		   SUNARP-REGMOBCOM: Modificación de los campos que se mostrarn para el detalle y la verificacion de la solicitud -->  
	
	<logic:present name="Solicitud" scope="request">				
	   <logic:equal name="Solicitud" property="estado" value="D"> 
			<table class=cabeceraformulario cellspacing=0> 
		 	  	 <tr>  	    
		  		  	<td>&nbsp;</td>
		   		   	<td><strong>RESULTADOS DE LA EMISION </strong></td>  						
		  		 </tr>  
		  	</table>
	  		 <table class=formulario cellspacing=0>
		  		 <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol22" scope="request">		
		            <!-- Certificado Negativo--> 
			        <logic:equal name="objsol22" property="tpo_cert" value="N">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<logic:present name="Solicitud" scope="request">				
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<td colspan="4"><strong>Para visualizar el 					  				
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="N">
									Certificado Negativo
								</logic:equal>
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="P">
									Certificado Positivo
								</logic:equal>	
							presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
							</strong></td>						
					  		</logic:present>														
						</tr>	
					</logic:equal>		
					<!--  inicio:jrosas 04-06-2007
						   SUNARP-REGMOBCOM: Agrego condicional solo para certificado dominial ya que los demas 
						   pasan a estado por despachar -->  
					
					 <!-- Certificado Gravamen/Vigencia--> 
			        <logic:equal name="objsol22" property="tpo_cert" value="R">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<logic:present name="Solicitud" scope="request">				
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="G">
									<td colspan="4"><strong>Certificado Compendioso de Gravámen
								</logic:equal>
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="V">
									<td colspan="4"><strong>Certificado de Vigencia
								</logic:equal>							
								presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
														
					  		</logic:present>				
						</tr>	
					</logic:equal>		
					 <!-- Certificado de Gravamen de RJB--> 
			        <logic:equal name="objsol22" property="tpo_cert" value="G">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<td colspan="4"><strong>Certificado Compendioso de Historial de Gravámenes	
							presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
							</strong></td>									
						</tr>	
					</logic:equal>		
					 <!-- Certificado Dominial--> 
			        <logic:equal name="objsol22" property="tpo_cert" value="D">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<td colspan="4"><strong>Certificado Compendioso de antecedentes dominiales
							presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
							</strong></td>	
						</tr>	
					</logic:equal>	
				    <!-- Certificado CREM--> 	
					<logic:equal name="objsol22" property="tpo_cert" value="C">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<logic:present name="Solicitud" scope="request">				
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="A">
									<td colspan="4"><strong>Certificado Registral Mobiliario
								</logic:equal>
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="H">
									<td colspan="4"><strong>Certificado CREM Histórico
								</logic:equal>	
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="C">
									<td colspan="4"><strong>Certificado CREM Condicionado
								</logic:equal>	
								presione aqui <A href="javascript:CertificadoPorExpedir();"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></A>
										
					  		</logic:present>				
						</tr>	
					</logic:equal>		
				<!-- copia literal -->	
		<!-- fin:jrosas 04-06-2007 -->
		
			     <!-- Certificado de Copia Literal--> 
				<logic:equal name="objsol22" property="tpo_cert" value="L">
					<tr>	
						<td>&nbsp;</td>
						<td colspan="4"><strong>Para visualizar la partida en la Extranet presione aqui </strong>
							<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol22" property="objeto_sol_id"/>">
							<a href="javascript:VerPartida('<bean:write name="objsol22" property="refnum"/>','expide')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
						</td>	
					</tr>	
			   </logic:equal>        	
			</logic:iterate> 
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;<STRONG>Comentarios:</STRONG></td>
		      		<td>&nbsp;</td>
				</tr>	
		 	 	<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
		   		 <tr> 
			  	      <td>&nbsp;</td>
		              <td colspan="4">
		              <logic:present name="Solicitud" scope="request">
		              <div align="center">              
		              <textarea name="ComentarioExpedir" readonly="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
		              </textarea>
		              </div>
		              </logic:present>
		              </td>
		   		 </tr>
		   		 <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				 </tr>		
  		</table>		
	</logic:equal>				  
	</logic:present>
	   
  </logic:equal>
</logic:present>
<!-- Para los registradores - Fin --> 

<!-- Para los usuarios en Gral no registradores******************************DETALLE DE SOLICITUD--> 
<logic:present name="Registrador" scope="request">				
	<logic:equal name="Registrador" value="DET">		
	<!-- Solicitud en estado: Por Emitir --> 
	<logic:present name="Solicitud" scope="request">
	   <!-- La solicitud esta en estado: Por Emitir -->
	  
 	<!--  inicio:jrosas 01-06-2007
		   SUNARP-REGMOBCOM: Modificación de los campos que se mostrarn para el detalle y la verificacion de la solicitud -->  
	   <logic:equal name="Solicitud" property="estado" value="V"> 			
		  	<table class=cabeceraformulario cellspacing=0>
	 		 <tr>
	   		   <td><strong>DETALLE DE LA VERIFICACION</strong></td><!-- - USER NO REGISTRADOR-->
	  		</tr>
			</table>
 		    <table class=formulario cellspacing=0>
		  		<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol23" scope="request">		
			        <!-- Certificado Negativo--> 
			        <logic:equal name="objsol23" property="tpo_cert" value="N">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<logic:present name="Solicitud" scope="request">				
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="N">
									<td colspan="4"><strong>Certificado Negativo</strong>
								</logic:equal>
								<logic:equal name="Solicitud" property="tpo_cert_neg" value="P">
									<td colspan="4"><strong>Certificado Positivo</strong>
								</logic:equal>							
					  		</logic:present>				
							<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
						</tr>	
					</logic:equal>		
					<!-- Certificado Dominial--> 
			        <logic:equal name="objsol23" property="tpo_cert" value="D">                     		
				  		 <tr>
						    <td>&nbsp;</td>					  
							<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<td colspan="4"><strong>Certificado Compendioso de antecedentes dominiales</strong>
							<A href="javascript:CertificadoPorExpedir();">Impresión:</A></td>														
						</tr>	
					</logic:equal>		
					<!-- Certificado de copia literal--> 
					<logic:equal name="objsol23" property="tpo_cert" value="L">
						<tr>	
							<td>&nbsp;</td>
							<td colspan="4"><strong>Para comprobar la existencia de datos en la extranet </strong>
								<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol23" property="objeto_sol_id"/>">
								<a href="javascript:VerPartida('<bean:write name="objsol23" property="refnum"/>','expide')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
							</td>	
						</tr>	
			        </logic:equal>        	
				</logic:iterate> 
			 <!-- fin:jrosas 04-06-2007 -->	
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;<STRONG>Comentarios:</STRONG></td>
		      		<td>&nbsp;</td>
				</tr>	
		 	 	<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
		   		 <tr> 
			  	      <td>&nbsp;</td>
		              <td colspan="4">
		              <logic:present name="Solicitud" scope="request">
		              <div align="center">              
		              <textarea name="ComentarioExpedir" disabled="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
		              </textarea>
		              </div>
		              </logic:present>
		              </td>
		   		 </tr>
		   		 <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				 </tr>
			</table>	
		</logic:equal>
	</logic:present>		
  </logic:equal>
</logic:present>	

<!-- Solicitud en estado: Improcedente --> 
	
  <logic:present name="Solicitud" scope="request">
	<logic:equal name="Solicitud" property="estado" value="<%= Constantes.ESTADO_SOL_IMPROCEDENTE%>">				
	  	<table class=cabeceraformulario cellspacing=0> 
  			<tr>  	    
		    	<td>&nbsp;</td>
		      	<td><strong>COMENTARIOS DE LA VERIFICACIÓN IMPROCEDENTE </strong></td>  						
	  		</tr>  
  		</table> 	
	  	<table class=formulario cellspacing=0>
	  	 	<tr> 
	      		<td width="5">&nbsp;</td>
	      		<td width="133">&nbsp;</td>
	      		<td width="133">&nbsp;</td>
	      		<td width="133">&nbsp;</td>
	      		<td width="133">&nbsp;</td>
	    	</tr>
	    	<tr>
		    	<td>&nbsp;</td>
	    		<td colspan="4">Acérquese a la Oficina Registral donde presentó la solicitud</td>
	    	</tr>
	    	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
	  	   	 <tr> 
			     <td>&nbsp;</td>
	 	        <td colspan="4">
	 	        <logic:present name="Solicitud" scope="request">
	 	        <div align="center">              
	 	        <textarea name="ComentarioImprocedente" disabled="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
	 	        </textarea>
	 	        </div>
	 	        </logic:present>
	         </td>
	    	</tr>	
	    	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
    	</table> 		
	</logic:equal>
  </logic:present>

<%if(flagInter) {%>
<!-- Para Usuarios internos, muestro resultado y los datos de los registradores-->
	<!-- Solicitud en estado: Por Despachar --> 
	<logic:equal name="Solicitud" property="estado" value="E">
	  	<table class=cabeceraformulario cellspacing=0>
 			<tr>
	   		   <td><strong>DETALLE DE LA VERIFICACION</strong></td>
	  		</tr>
		</table>
  		 <table class=formulario cellspacing=0>
	  		 <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
	   <!--  inicio:jrosas 04-06-2007
	   SUNARP-REGMOBCOM: IMPRESION DE CERTIFICADOS PROCEDENTES DE LA EMISION Y VERIFICACION -->  
	   
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol24" scope="request">		
		        <!-- Certificado Negativo--> 
		        <logic:equal name="objsol24" property="tpo_cert" value="N">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<logic:present name="Solicitud" scope="request">				
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="N">
								<td colspan="4"><strong>Certificado Negativo</strong>
							</logic:equal>
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="P">
								<td colspan="4"><strong>Certificado Positivo</strong>
							</logic:equal>							
				  		</logic:present>				
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
					</tr>	
				</logic:equal>	
				 <!-- Certificado Gravamen/Vigencia--> 
		        <logic:equal name="objsol24" property="tpo_cert" value="R">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<logic:present name="Solicitud" scope="request">				
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="G">
								<td colspan="4"><strong>Certificado Compendioso de Gravámen</strong>
							</logic:equal>
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="V">
								<td colspan="4"><strong>Certificado de Vigencia</strong>
							</logic:equal>							
				  		</logic:present>				
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
					</tr>	
				</logic:equal>		
				 <!-- Certificado de Gravamen de RJB--> 
		        <logic:equal name="objsol24" property="tpo_cert" value="G">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
						<td colspan="4"><strong>Certificado Compendioso de Historial de Gravámenes</strong>
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A></td>														
					</tr>	
				</logic:equal>		
				 <!-- Certificado Dominial--> 
		        <logic:equal name="objsol24" property="tpo_cert" value="D">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
						<td colspan="4"><strong>Certificado Compendioso de antecedentes dominiales</strong>
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A></td>													
					</tr>	
				</logic:equal>	
			    <!-- Certificado CREM--> 	
				<logic:equal name="objsol24" property="tpo_cert" value="C">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<logic:present name="Solicitud" scope="request">				
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="A">
								<td colspan="4"><strong>Certificado Registral Mobiliario</strong>
							</logic:equal>
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="H">
								<td colspan="4"><strong>Certificado CREM Histórico</strong>
							</logic:equal>	
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="C">
								<td colspan="4"><strong>Certificado CREM Condicionado</strong>
							</logic:equal>	
				  		</logic:present>				
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
					</tr>	
				</logic:equal>	
			   <!-- fin:jrosas 04-06-2007 -->		
				<!-- copia literal -->	
				<logic:equal name="objsol24" property="tpo_cert" value="L">
					<tr>	
						<td>&nbsp;</td>
						<td colspan="4"><strong>Para comprobar la existencia de datos en la extranet </strong>
							<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol24" property="objeto_sol_id"/>">
							<a href="javascript:VerPartida('<bean:write name="objsol24" property="refnum"/>','expide')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
						</td>	
					</tr>	
      		  	</logic:equal>        	
			</logic:iterate> 
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;<STRONG>Comentarios:</STRONG></td>
	      		<td>&nbsp;</td>
			</tr>	
	 	 	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
	   		 <tr> 
		  	      <td>&nbsp;</td>
	              <td colspan="4">
	              <logic:present name="Solicitud" scope="request">
	              <div align="center">              
	              <textarea name="ComentarioExpedir" disabled="true" cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
	              </textarea>
	              </div>
	              </logic:present>
	              </td>
	   		 </tr>
	   		 <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			 </tr>
		</table>	
	</logic:equal>
	
    <!-- Solicitud en estado: Despachada --> 
	<logic:equal name="Solicitud" property="estado" value="D">
		 <table class=cabeceraformulario cellspacing=0>
	 		 <tr>
	   		   <td><strong>DETALLE DE LA VERIFICACION</strong></td>
	  		</tr>
		</table>
	    <table class=formulario cellspacing=0>
  		    <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		 <!--  inicio:jrosas 04-06-2007
	   SUNARP-REGMOBCOM: IMPRESION DE CERTIFICADOS PROCEDENTES DE LA VERIFICACION Y EMISION -->  
	   
			<logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol25" scope="request">		
				<!-- Certificado Negativo--> 
				<logic:equal name="objsol25" property="tpo_cert" value="N">                     		
					 <tr>
					    <td>&nbsp;</td>					  
						<logic:present name="Solicitud" scope="request">				
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="N">
								<td colspan="4"><strong>Certificado Negativo</strong>
							</logic:equal>
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="P">
								<td colspan="4"><strong>Certificado Positivo</strong>
							</logic:equal>							
				  		</logic:present>				
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
					</tr>	
				</logic:equal>	
				 <!-- Certificado Gravamen/Vigencia--> 
		        <logic:equal name="objsol25" property="tpo_cert" value="R">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<logic:present name="Solicitud" scope="request">				
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="G">
								<td colspan="4"><strong>Certificado Compendioso de Gravámen</strong>
							</logic:equal>
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="V">
								<td colspan="4"><strong>Certificado de Vigencia</strong>
							</logic:equal>							
				  		</logic:present>				
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
					</tr>	
				</logic:equal>		
				 <!-- Certificado de Gravamen de RJB--> 
		        <logic:equal name="objsol25" property="tpo_cert" value="G">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
						<td colspan="4"><strong>Certificado Compendioso de Historial de Gravámenes</strong>
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A></td>														
					</tr>	
				</logic:equal>		
				 <!-- Certificado Dominial--> 
		        <logic:equal name="objsol25" property="tpo_cert" value="D">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
						<td colspan="4"><strong>Certificado Compendioso de antecedentes dominiales</strong>
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A></td>													
					</tr>	
				</logic:equal>	
			    <!-- Certificado CREM--> 	
				<logic:equal name="objsol25" property="tpo_cert" value="C">                     		
			  		 <tr>
					    <td>&nbsp;</td>					  
						<logic:present name="Solicitud" scope="request">				
						<input type="hidden" name="tip_cert" value="<bean:write name="Solicitud" property="tpo_cert_neg" scope="request"/>">
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="A">
								<td colspan="4"><strong>Certificado Registral Mobiliario</strong>
							</logic:equal>
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="H">
								<td colspan="4"><strong>Certificado Registral Histórico</strong>
							</logic:equal>	
							<logic:equal name="Solicitud" property="tpo_cert_neg" value="C">
								<td colspan="4"><strong>Certificado Registral Condicionado</strong>
							</logic:equal>	
				  		</logic:present>				
						<A href="javascript:CertificadoPorExpedir();">Impresión:</A>														
					</tr>	
				</logic:equal>		
		   <!-- fin:jrosas 04-06-2007 -->		
				<!-- copia literal -->		
				<logic:equal name="objsol25" property="tpo_cert" value="L">
					<tr>	
						<td>&nbsp;</td>
						<td colspan="4"><strong>Para comprobar la existencia de datos en la extranet </strong>
							<input type="hidden" name="objeto_Sol_Id" value="<bean:write name="objsol25" property="objeto_sol_id"/>">
							<a href="javascript:VerPartida('<bean:write name="objsol25" property="refnum"/>','expide')"><img src="images/lupa.gif" border="0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>				
						</td>	
					</tr>	
		        </logic:equal>        	
			</logic:iterate> 
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;<STRONG>Comentarios:</STRONG></td>
	      		<td>&nbsp;</td>
			</tr>	
	 	 	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
	   		 <tr> 
		  	      <td>&nbsp;</td>
	              <td colspan="4">
	              <logic:present name="Solicitud" scope="request">
	              <div align="center">              
	              <textarea name="ComentarioExpedir" disabled="true"
	               cols="64" rows="4" ><bean:write name="Solicitud" property="comentario" scope="request"/>
	              </textarea>
	              </div>
	              </logic:present>
	              </td>
	   		 </tr>
	   		 <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			 </tr>
		</table>	
	</logic:equal>

    <!-- Datos de los registradores --> 
	<table class=cabeceraformulario cellspacing=0>
	  <tr>
	      <td><strong>DATOS DE LOS REGISTRADORES</strong></td>
	  </tr>
	</table>
	<table class=formulario cellspacing=0>
	  <tr>
		  <td width="5">&nbsp;</td>
	      <td width="133">&nbsp;</td>	  	  
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
	  
	   <!--  inicio:jrosas 04-06-2007
	   SUNARP-REGMOBCOM: IMPRESION DE REGISTRADOR VERIFICADOR Y EMISOR DEPENDIENDO DE TIPO DE CERTIFICADO -->  
	  
	  <tr>
	  	 <td>&nbsp;</td>	
	  	 <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol26" scope="request">
		  	  <logic:equal name="objsol26" property="tpo_cert" value="N">
		  	  		<logic:equal name="objsol26" property="certificado_id" value="18">
	  	  				 <td><STRONG>Registrador Verificador y Emisor</STRONG></td>
					  	 <td>&nbsp;</td>
		  	  		</logic:equal>
			  	    <logic:notEqual name="objsol26" property="certificado_id" value="18">		
					  	 <td><STRONG>Registrador Verificador</STRONG></td>
					  	 <td>&nbsp;</td>	
					</logic:notEqual>  	 
			 </logic:equal> 
			 <logic:notEqual name="objsol26" property="tpo_cert" value="N">
			  	<logic:equal name="objsol26" property="tpo_cert" value="L">
			  		 <td><STRONG>Registrador Verificador</STRONG></td>
					 <td>&nbsp;</td>	
			  	</logic:equal>
		  	    <logic:notEqual name="objsol26" property="tpo_cert" value="L">	
			  	     <logic:equal name="objsol26" property="tpo_cert" value="D">	
				  	 	<td><STRONG>Registrador Verificador</STRONG></td>
				  	 	<td>&nbsp;</td>
				  	 </logic:equal>
					 <logic:notEqual name="objsol26" property="tpo_cert" value="D">
		  	  		 	<td><STRONG>Registrador Verificador y Emisor</STRONG></td>
					  	<td>&nbsp;</td>
					</logic:notEqual>  	 
				</logic:notEqual>  	 
			 </logic:notEqual>	 
		 </logic:iterate> 	 
		 
		 <!--  fin:jrosas 04-06-2007 -->
		 
		<logic:present name="Solicitud" parameter="datosRegisVerificadorBean" scope="request" >	      				
			<td><bean:write name="Solicitud" property="datosRegisVerificadorBean.apellidoPaterno" scope="request"/>
			<bean:write name="Solicitud" property="datosRegisVerificadorBean.apellidoMaterno" scope="request"/>
			<bean:write name="Solicitud" property="datosRegisVerificadorBean.nombre" scope="request"/>
			</td>
			<td>&nbsp;</td>	
			<td><bean:write name="Solicitud" property="datosRegisVerificadorBean.correo_electronico" scope="request"/></td>
		</logic:present>
	  </tr>
	  <tr>
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  </tr>	
	  
	 <!--  inicio:jrosas 04-06-2007
	   SUNARP-REGMOBCOM: adicion de registrador/Emisor solo para certificados negativos que no sean de registro mobiliario -->  
	  
	  <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol27" scope="request">
         <!-- Certificado Postivivo/Negativo -->	  
	      <logic:equal name="objsol27" property="tpo_cert" value="N">
	        <!-- Diferente de tipo de Certificado de Mobiliario de Contratos -->
		    <logic:notEqual name="objsol27" property="certificado_id" value="18">		
		  		<tr>
				  	<td>&nbsp;</td>
				  	<td><STRONG>Registrador Emisor</STRONG></td>	  	 
				  	<td>&nbsp;</td>	
					<logic:present name="Solicitud" parameter="datosRegisEmisorBean" scope="request" >	      						
					<td><bean:write name="Solicitud" property="datosRegisEmisorBean.apellidoPaterno" scope="request"/>
					<bean:write name="Solicitud" property="datosRegisEmisorBean.apellidoMaterno" scope="request"/>
					<bean:write name="Solicitud" property="datosRegisEmisorBean.nombre" scope="request"/>
					</td>
					<td>&nbsp;</td>	
					<td><bean:write name="Solicitud" property="datosRegisEmisorBean.correo_electronico" scope="request"/></td>
					</logic:present>		
			   </tr>	 
		 	</logic:notEqual>
	      </logic:equal>
	      <!-- otros certificados que no son postivios/negativos -->
	      <logic:notEqual name="objsol27" property="tpo_cert" value="N">
	      		<!-- Solo para certificado de copia literal -->
	      		<logic:equal name="objsol27" property="tpo_cert" value="L">
		      		<tr>
					  	<td>&nbsp;</td>
					  	<td><STRONG>Registrador Emisor</STRONG></td>	  	 
					  	<td>&nbsp;</td>	
						<logic:present name="Solicitud" parameter="datosRegisEmisorBean" scope="request" >	      						
						<td><bean:write name="Solicitud" property="datosRegisEmisorBean.apellidoPaterno" scope="request"/>
						<bean:write name="Solicitud" property="datosRegisEmisorBean.apellidoMaterno" scope="request"/>
						<bean:write name="Solicitud" property="datosRegisEmisorBean.nombre" scope="request"/>
						</td>
						<td>&nbsp;</td>	
						<td><bean:write name="Solicitud" property="datosRegisEmisorBean.correo_electronico" scope="request"/></td>
						</logic:present>		
				   </tr>	 
	      		</logic:equal>
	      		<logic:equal name="objsol27" property="tpo_cert" value="D">
		      		<tr>
					  	<td>&nbsp;</td>
					  	<td><STRONG>Registrador Emisor</STRONG></td>	  	 
					  	<td>&nbsp;</td>	
						<logic:present name="Solicitud" parameter="datosRegisEmisorBean" scope="request" >	      						
						<td><bean:write name="Solicitud" property="datosRegisEmisorBean.apellidoPaterno" scope="request"/>
						<bean:write name="Solicitud" property="datosRegisEmisorBean.apellidoMaterno" scope="request"/>
						<bean:write name="Solicitud" property="datosRegisEmisorBean.nombre" scope="request"/>
						</td>
						<td>&nbsp;</td>	
						<td><bean:write name="Solicitud" property="datosRegisEmisorBean.correo_electronico" scope="request"/></td>
						</logic:present>		
				   </tr>	 
	      		</logic:equal>
	      </logic:notEqual>
	  </logic:iterate>	  
	  
	 <!-- fin:jrosas 01-06-2007 -->  
	 
	  <tr>
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  	  <td>&nbsp;</td>	
	  </tr>	
	
	</table>		
	  
<%}%>
 <!-- Datos del Solicitante que se mostraran para usuarios Internos--> 
 
	<table class=cabeceraformulario cellspacing=0>
	  <tr>
	      <td><strong>1. DATOS DEL SOLICITANTE</strong></td>
	  </tr>
	</table>
	<table class=formulario cellspacing=0>
	   <tr> 
	     <td width="5">&nbsp;</td>
	     <td width="133">&nbsp;</td>
	     <td width="133">&nbsp;</td>
	     <td width="133">&nbsp;</td>
	     <td width="133">&nbsp;</td>
	  </tr>
	    <!--<tr> 
	      <td>&nbsp;</td>
	      <td width="133"><strong>TIPO DE PERSONA</strong></td>
	      <td> 
	      <logic:present name="Solicitud" parameter="solicitanteBean" scope="request" >	      		
			<logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="N">
			NATURAL
			</logic:equal>
			<logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="J">
			JURIDICA
			</logic:equal>
		  </logic:present>
	      </td>
	      <td width="133">&nbsp; </td>
	      <td width="133">&nbsp; </td>
	    </tr>
	    <tr> 
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td width="133">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	    </tr>-->
      <tr>         
	      <logic:present name="Solicitud" parameter="solicitanteBean" scope="request" >	      
		        <logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="N">
		           <td>&nbsp;</td>
			       <td width="133"><strong>APELLIDOS Y NOMBRES</strong></td>
		 	       <td colspan="2">
					<bean:write name="Solicitud" property="solicitanteBean.ape_pat" scope="request"/>
					<bean:write name="Solicitud" property="solicitanteBean.ape_mat" scope="request"/>
					<bean:write name="Solicitud" property="solicitanteBean.nombres" scope="request"/>
				   </td>
			       <td width="133">&nbsp;</td>		
		    	</logic:equal>
		    	<logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="J">
		           <td>&nbsp;</td>
			       <td width="133"><strong>RAZON SOCIAL</strong></td>
		 	       <td colspan="2">
					<bean:write name="Solicitud" property="solicitanteBean.raz_soc" scope="request"/>			
				   </td>
			       <td width="133">&nbsp;</td>		
		    	</logic:equal>
		  </logic:present>
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
      <td width="133"><strong>TIPO DE DOCUMENTO</strong></td>
      <td>
      <logic:present name="Solicitud" parameter="solicitanteBean">	  
          <logic:present name="Solicitud" property="solicitanteBean.tipo_doc_id">	  
				<bean:write name="Solicitud" property="solicitanteBean.tipo_doc_id" />
		  </logic:present>
	  </logic:present>	  
      </td>
      <td width="133"> <strong>NUMERO DE DOCUMENTO</strong> </td>
      <td width="133">
      <logic:present name="Solicitud" parameter="solicitanteBean">	
	      <logic:present name="Solicitud" property="solicitanteBean.num_doc_iden">	      
			<bean:write name="Solicitud" property="solicitanteBean.num_doc_iden"/>
		  </logic:present>
	  </logic:present>	  
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
  
   <!-- Datos del Destinatario que se mostraran para usuarios Internos--> 
  <table class=cabeceraformulario cellspacing=0>
  	<tr>
   		 <td><strong>2. DATOS DEL DESTINATARIO</strong></td>
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
	      <td width="133"><strong>FORMA DE ENVIO</strong></td>
	      <td width="150"> 
	      <logic:present name="Solicitud" parameter="destinatarioBean">	      
			<%--<bean:write name="Solicitud" property="destinatarioBean.tpo_env" scope="request"/>--%>
			<logic:present name="Solicitud" property="destinatarioBean.tpo_env">	
				<logic:equal name="Solicitud" property="destinatarioBean.tpo_env" value="D">
					Envio a Domicilio
				</logic:equal>
				<logic:equal name="Solicitud" property="destinatarioBean.tpo_env" value="V">
					Recojo por Ventanilla
				</logic:equal>
		  	</logic:present>
		  </logic:present>	
	      </td>
	      <td width="150">&nbsp; </td>
	      <td width="150">&nbsp; </td>
	    </tr>
	    <tr> 
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td width="150">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	    </tr>
	    <logic:present name="Solicitud" parameter="destinatarioBean">
	    	<logic:present name="Solicitud" parameter="destinatarioBean.tpo_env">	
			    <logic:equal name="Solicitud" property="destinatarioBean.tpo_env" value="D">
				    <tr> 
					      <td>&nbsp;</td>
					      <!--<td>&nbsp;<strong>PAIS</strong></td>
					      <td width="150"> 
					      <logic:present name="Solicitud" parameter="destinatarioBean" scope="request" >	      
							<bean:write name="Solicitud" property="destinatarioBean.pais_desc" scope="request"/>
						  </logic:present>
					      </td>-->
					      <td width="150">&nbsp;<strong>DEPARTAMENTO</strong></td>
					      <td width="150">
						      <logic:present name="Solicitud" parameter="destinatarioBean" >	
					      	      <logic:present name="Solicitud" property="destinatarioBean.dpto_desc">	
									 <bean:write name="Solicitud" property="destinatarioBean.dpto_desc" />
								  </logic:present>	
							  </logic:present>
			      		</td>
			    	</tr>
				    <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td width="150">&nbsp;</td>
				      <td width="150">&nbsp;</td>
				      <td width="150">&nbsp;</td>
				    </tr>
			    	<tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;<strong>PROVINCIA</strong></td>
				      <td width="150">
				      <logic:present name="Solicitud" parameter="destinatarioBean" >
					      <logic:present name="Solicitud" property="destinatarioBean.prov_desc">	      
							 <bean:write name="Solicitud" property="destinatarioBean.prov_desc" />
						  </logic:present>
					  </logic:present>
				      </td>
				      <td width="150"><strong>&nbsp;DISTRITO</strong></td>
				      <td width="150">
				      <logic:present name="Solicitud" parameter="destinatarioBean">	      
				      	  <logic:present name="Solicitud" property="destinatarioBean.distrito">	 
							<bean:write name="Solicitud" property="destinatarioBean.distrito"/>
					  	  </logic:present>
					  </logic:present>	  
				      </td>
			    	</tr>
			    	<tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td width="150">&nbsp;</td>
				      <td width="150">&nbsp;</td>
				      <td width="150">&nbsp;</td>
			    	</tr>
				    <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;<strong>DIRECCION</strong></td>
				      <td width="150">
				      <logic:present name="Solicitud" parameter="destinatarioBean">	      
				      	  <logic:present name="Solicitud" property="destinatarioBean.direcc">	
							  <bean:write name="Solicitud" property="destinatarioBean.direcc" />
						  </logic:present>              
					  </logic:present>              
				      </td>
				      <td width="150"><strong>&nbsp;COD POSTAL</strong></td>
				      <td width="150">
				      <logic:present name="Solicitud" parameter="destinatarioBean" >	
					      <logic:present name="Solicitud" property="destinatarioBean.cod_post">	      
							<bean:write name="Solicitud" property="destinatarioBean.cod_post" />
						  </logic:present>     	
					  </logic:present>        
				      </td>
				    </tr>
				    <tr> 
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				      <td width="150">&nbsp;</td>
				      <td width="150">&nbsp;</td>
				      <td width="150">&nbsp;</td>
				    </tr>
		   	 	</logic:equal>
  			</logic:present>
  		</logic:present>	
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
      <logic:present name="Solicitud" parameter="destinatarioBean" scope="request" >
      	  <logic:present name="Solicitud" property="destinatarioBean.ofic_reg_desc" >      
			<bean:write name="Solicitud" property="destinatarioBean.ofic_reg_desc"/>
		  </logic:present>        
	  </logic:present>	  
       </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>
    <!--<tr> 
      <td>&nbsp;</td>
      <td width="133"><strong>TIPO DE DESTINATARIO</strong></td>
      <td width="150">
      <logic:present name="Solicitud" parameter="destinatarioBean" scope="request" >	      		
		<logic:equal name="Solicitud" property="destinatarioBean.tpo_pers" value="N">
		NATURAL
		</logic:equal>
		<logic:equal name="Solicitud" property="destinatarioBean.tpo_pers" value="J">
		JURIDICA
		</logic:equal>
	  </logic:present>       
      </td>
      <td width="150">&nbsp; </td>
      <td width="150">&nbsp; </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>-->
    <tr>         
      <logic:present name="Solicitud" parameter="destinatarioBean" scope="request" >
      	<logic:present name="Solicitud" property="destinatarioBean.tpo_pers" >      
			<logic:equal name="Solicitud" property="destinatarioBean.tpo_pers" value="N">
				<td>&nbsp;</td>
		    	<td width="133"><strong>APELLIDOS Y NOMBRES</strong></td>
	  	  	    <td colspan="3">
				<bean:write name="Solicitud" property="destinatarioBean.ape_pat" scope="request"/>
				<bean:write name="Solicitud" property="destinatarioBean.ape_mat" scope="request"/>
				<bean:write name="Solicitud" property="destinatarioBean.nombres" scope="request"/>
		    	</td>
		    </logic:equal> 
			<logic:equal name="Solicitud" property="destinatarioBean.tpo_pers" value="J">
				<td>&nbsp;</td>
		    	<td width="133"><strong>RAZON SOCIAL</strong></td>
	  	  	    <td colspan="3">
				<bean:write name="Solicitud" property="destinatarioBean.raz_soc" scope="request"/>
		    	</td>
		    </logic:equal> 	  		
	  	</logic:present>
	  </logic:present>	
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>
  </table>
  
     <!-- Datos de Pago que se mostraran para usuarios Internos--> 
     
	<table class=cabeceraformulario cellspacing=0>
	  <tr>
    	  <td><strong>3. DATOS DEL PAGO</strong></td>
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
	      <td width="5">&nbsp;</td>
	      <td width="133"><strong>MONTO</strong></td>
	      <td width="150">
		 <%--
		 	Solicitud solicitud2 = (Solicitud)request.getAttribute("Solicitud");
		 	
		 --%>
		  
		  <%--dbravo:inicio:24/08/2007
		  	  descripcion: Si el estado de la solicitud es por despachar, se valida que sea diferente a crem
		  --%>
	      
	  		<%
	  		Solicitud solicitud = (Solicitud)request.getAttribute("Solicitud");
	  		ObjetoSolicitudBean objSol12= solicitud.getObjetoSolicitudList(0);
	        //System.out.println("--->:"+objSol12.getTpo_cert());
	  		if((objSol12.getCertificado_id().equals("21") || objSol12.getCertificado_id().equals("22") || objSol12.getCertificado_id().equals("23")) &&
	  			solicitud!=null && 
	  			(solicitud.getEstado().equals("D") || solicitud.getEstado().equals("E"))
	  		){
			  		Certificado certificado = (Certificado)request.getAttribute("certificado");
   					
   					double montoPagado = Double.parseDouble(solicitud.getTotal());
   					if(certificado.getFlagPagoCrem()!=null && certificado.getFlagPagoCrem().equalsIgnoreCase("1") && certificado.getPagoCrem()!=null){
   						montoPagado = montoPagado + certificado.getPagoCrem().doubleValue();
   			  		}	
   					
   			   		out.println("S/ "+montoPagado);
	   		}
	   		else{
		%>
			S/<bean:write name="Solicitud" property="total" scope="request"/>
		<%	
	   		}
	    %>
	    
	      </td>
	      <td width="150"><strong>FECHA:</strong> </td>
	      <td width="150">
		  <logic:present name="Solicitud" parameter="pagoBean" scope="request" >	      
			<bean:write name="Solicitud" property="pagoBean.ts_crea" scope="request"/>
		  </logic:present>     
	      </td>
	    </tr>
	    <tr> 
	      <td width="5">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	    </tr>
	    <tr> 
	      <td>&nbsp;</td>
	      <td width="133"><strong>FORMA DE PAGO</strong></td>
	      <td colspan="2"> <strong>
		  <logic:present name="Solicitud" property="pagoBean.tipo_abono" scope="request" >	      
			
			<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_CHEQUE%>">
				Cheque		
			</logic:equal>
			<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_EFECTIVO%>">
				Efectivo		
			</logic:equal>
			<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_LINEA_PREPAGO%>">
				Linea Prepago		
			</logic:equal>
			<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_TARJETA_DE_CREDITO%>">
				Tarjeta de credito		
			</logic:equal>
		  </logic:present>           
	      </strong> </td>
	      <td width="150"> <div align="center"> </div></td>
	    </tr>
	    <tr> 
	      <td width="5">&nbsp;</td>
	      <td width="133">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	      <td width="150">&nbsp;</td>
	    </tr>
  	</table>
	<br>
	
	<table class=tablasinestilo>
	  <tr>
	  	<td width="50%" align="left">
	  	<div id="HOJA2"> 
	  	<a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
	  	</div></td>
		<td width="50%" align="right">
		<div id="HOJA3">	
		<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
		</div></td>
	  </tr>
	</table>
</logic:present>
<!-- Fin de form --> 
</form>
<!-- Declaracion de hidden --> 
<form name="frm2" method="post">
  <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol" scope="request">
	<!-- variables ocultas -->
	<!--<input type="hidden" name="hid1" value="01*">-->
	
	<input type="hidden" name="hid1" value="<bean:write name="objsol" property="reg_pub_id"/>*">
	
	<input type="hidden" name="hid2" value="">
	<input type="hidden" name="pidedis" value="S">
	<!-- sede -->
	<input type="hidden" name="listbox2" value="01">
	<!-- area registral-->
	<input type="hidden" name="comboArea" value="<bean:write name="objsol" property="area_reg_id"/>">
	<!-- costo -->
	<input type="hidden" name="costo" size="2" value="0" disabled="true">
	<!-- RPN -->
	<!-- participante PN -->
	<input type="hidden" name="area1ApePat" value="<bean:write name="objsol" property="ape_pat"/>">
	<input type="hidden" name="area1ApeMat" value="<bean:write name="objsol" property="ape_mat"/>">
	<input type="hidden" name="area1Nombre" value="<bean:write name="objsol" property="nombres"/>">
	<input type="hidden" name="checkInactivosPN3" value="true">
	<!-- participante PJ -->
	<input type="hidden" name="area1Razon" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="area1Siglas" value="">
	<input type="hidden" name="checkInactivosPJ3" value="true">
	<!-- RPJ -->
	<!-- participante PN -->
	<input type="hidden" name="area2ApePat" value="<bean:write name="objsol" property="ape_pat"/>">
	<input type="hidden" name="area2ApeMat" value="<bean:write name="objsol" property="ape_mat"/>">
	<input type="hidden" name="area2Nombre" value="<bean:write name="objsol" property="nombres"/>">
	<input type="hidden" name="checkInactivosPN2" value="true">
	<!-- participante PJ -->
	<input type="hidden" name="area2Razon1" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="area2Siglas" value="">
	<input type="hidden" name="checkInactivosPJ2" value="true">
	<!-- razon social -->
	<input type="hidden" name="area2Razon2" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="area2SiglasB" value="">
	<!-- RPI -->
	<!-- participante PN -->
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name="area3ParticipanteApePat" value="<bean:write name="objsol" property="ape_pat"/>">
	<input type="hidden" name="area3ParticipanteApeMat" value="<bean:write name="objsol" property="ape_mat"/>">
	<input type="hidden" name="area3ParticipanteNombre" value="<bean:write name="objsol" property="nombres"/>">
	<!-- Inicio:mgarate -->
	<input type="hidden" name="checkInactivosPN1" value="true">
	<!-- participante PJ -->
	<input type="hidden" name="area3ParticipanteRazon" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="area3Siglas" value="">
	<input type="hidden" name="checkInactivosPJ1" value="true">
	<!-- derecho minero -->
	<input type="hidden" name="area3MineriaDerechoMinero" maxlength="100" onblur="sololet(this)">
	<!-- sociedad minera -->
	<input type="hidden" name="area3MineriaSociedad" maxlength="100" onblur="sololet(this)">
	<!-- nro matricula embarcacion -->
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name="area3EmbarcacionNumeroMatricula" value="<bean:write name="objsol" property="numeroMatricula"/>" style="width:133" maxlength="15" onblur="sololet(this)">
	<!-- Inicio:mgarate -->
	<!-- nombre embarcacion -->
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name="area3EmbarcacionNombre" value="<bean:write name="objsol" property="nombreBien"/>" style="width:133" maxlength="100" onblur="sololet(this)">
	<!-- Inicio:mgarate -->
	<!-- nro matricula buque -->
	<input type="hidden" name="area3BuqueNumeroMatricula" value="<bean:write name="objsol" property="numeroMatricula"/>" style="width:133" maxlength="15" onblur="sololet(this)">
	<!-- nombre buque -->
	<input type="hidden" name="area3BuqueNombre" style="width:133" maxlength="100" onblur="sololet(this)" value="<bean:write name="objsol" property="nombreBien"/>">
	<!-- nro matricula aeronave -->
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name="area3AeronaveNumeroMatricula" value="<bean:write name="objsol" property="numeroMatricula"/>" maxlength="15" style="width:133" onblur="sololet(this)" >
	<!-- Fin:mgarate -->
	<!-- propietario PN aeronave -->
	<!-- Inicio:mgarate :10-08-2007 -->
	<input type="hidden" name="area3AeronaveApePat" value="<bean:write name="objsol" property="ape_pat"/>" maxlength="30" style="width:133" onblur="sololet(this)">
	<input type="hidden" name="area3AeronaveApeMat" value="<bean:write name="objsol" property="ape_mat"/>" maxlength="30" style="width:133" onblur="sololet(this)">
	<input type="hidden" name="area3AeronaveNombre" value="<bean:write name="objsol" property="nombres"/>" maxlength="40" style="width:133" onblur="sololet(this)">
	<!-- Fin:mgarate -->
	<!-- propietario PJ aeronave -->
	<!-- Inicio:mgarate :10-08-2007 -->
	<input type="hidden" name="area3AeronaveRazon" value="<bean:write name="objsol" property="raz_soc"/>" maxlength="150" style="width:133" onblur="sololet(this)">
	<!-- Fin:mgarate  -->
	<input type="hidden" name="area3SiglasB" maxlength="50" style="width:133" onblur="sololet(this)">
	<!-- RPV -->
	<!-- propietario PN -->
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name="area4PropietarioApePat" value="<bean:write name="objsol" property="ape_pat"/>" style="width:130" maxlength="30" onblur="sololet(this)">
	<input type="hidden" name="area4PropietarioApeMat" value="<bean:write name="objsol" property="ape_mat"/>" style="width:130" maxlength="30" onblur="sololet(this)">
	<input type="hidden" name="area4PropietarioNombre" value="<bean:write name="objsol" property="nombres"/>" style="width:130" maxlength="40" onblur="sololet(this)">
	<!-- Fin:mgarate -->
	<input type="hidden" name="checkInactivosPN4" value="true">
	<!-- propietario PJ -->
	<input type="hidden" name="area4PropietarioRazon" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="checkInactivosPJ4" value="true">
	<!-- nroMotor -->
	<input type="hidden" name="area4NumMotor" value="<bean:write name="objsol" property="numeroMotor"/>" style="width:130" maxlength="30" onblur="solonumlet(this)">
	<input type="hidden" name="checkInactivosNM4" value="true">
	<!-- chasis -->
	<input type="hidden" name="area4Chasis" value="<bean:write name="objsol" property="numeroSerie"/>" style="width:130" maxlength="35" onblur="solonumlet(this)">
	<input type="hidden" name="checkInactivosCH4" value="true">
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name = "comboAreaLibro" value = "<bean:write name="objsol" property="cod_GLA"/>"/>
	<!-- Fin:mgarate:10-08-2007 -->
	<input type="hidden" name = "comboAreaLibroXX" value = ""/>
	<!--  inicio:jrosas 04-06-2007
			   SUNARP-REGMOBCOM: Agregacion de nuevos hidden para certificado dominial y de gravamen -->  
			   
	<!-- Hidden tipo de certificado: Vehicular para dominial o gravamen, ademas falta hidden: comboAreaLibro que ya se definió antes-->
	<input type="hidden" name="hidAreaLibro" value = "<bean:write name="objsol" property="desc_GLA"/>">
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name="CboOficinas" value="<bean:write name="objsol" property="reg_pub_id"/>*<bean:write name="objsol" property="ofic_reg_id"/>">
	<!-- Fin:mgarate:10-08-2007 -->
	<input type="hidden" name ="radBuscar2" value = "">
	<input type="hidden" name ="txt4" value = "">
	<input type="hidden" name ="txt1" value = "">
	<!-- Inicio:mgarate:10-08-2007 -->
	<input type="hidden" name ="numplaca" value = "<bean:write name="objsol" property="placa"/>">
	<input type="hidden" name ="numpart" value = "<bean:write name="objsol" property="numPartida"/>">
	<input type="hidden" name ="numMatricula" value = "<bean:write name="objsol" property="numeroMatricula"/>">
	<input type="hidden" name ="numSerie" value = "<bean:write name="objsol" property="numeroSerie"/>">
	<input type="hidden" name ="numeroPartida" value = "<bean:write name="objsol" property="numeroPartida"/>">
	<input type="hidden" name="tipo" value="">
	<input type="hidden" name="txtNumeroPlacaArea15" value="">
	<input type="hidden" name="txtNumeroMatriculaArea15" value="">
	<input type="hidden" name="txtNombreBienArea15" value="">
	<input type="hidden" name="txtNumeroSerieArea15" value="">
	<input type="hidden" name="numeroFicha" value="<bean:write name="objsol" property="ficha"/>">
	<input type="hidden" name="txt2" value="<bean:write name="objsol" property="tomo"/>">
	<input type="hidden" name="txt3" value="<bean:write name="objsol" property="folio"/>">
	<input type="hidden" name="combo3" value="<bean:write name="objsol" property="libro"/>">
	
	<!-- Fin:mgarate:10-08-2007 -->
	<!-- Inicio:ifigueroa 17-08-2007 -->
	<input type="hidden" name ="nombreBien" value = "<bean:write name="objsol" property="nombreBien"/>">
	<input type="hidden" name="txtApellidoPaterno" value="<bean:write name="objsol" property="ape_pat"/>">
	<input type="hidden" name="txtApellidoMaterno" value="<bean:write name="objsol" property="ape_mat"/>">
	<input type="hidden" name="txtNombres" value="<bean:write name="objsol" property="nombres"/>">
	<input type="hidden" name="txtRazonSocial" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="txtSiglas" value="<bean:write name="objsol" property="siglas"/>">
	<input type="hidden" name="cboTipoDocumento" value="<bean:write name="objsol" property="tipoDocumento"/>">
	<input type="hidden" name="txtNumeroDocumento" value="<bean:write name="objsol" property="numeroDocumento"/>">
	<!-- Fin:ifigueroa 17-08-2007 -->
	<!-- inicio:jrosas 10-08-07 -->
	
	<input type="hidden" name = "verifica" value = "v">
	<!-- participante PN Sigc-->
	<input type="hidden" name="txtParticipanteApePatArea15" value="<bean:write name="objsol" property="ape_pat"/>">
	<input type="hidden" name="txtParticipanteApeMatArea15" value="<bean:write name="objsol" property="ape_mat"/>">
	<input type="hidden" name="txtParticipanteNombreArea15" value="<bean:write name="objsol" property="nombres"/>">
	<!-- participante PJ Sigc-->
	<input type="hidden" name="txtParticipanteRazonArea15" value="<bean:write name="objsol" property="raz_soc"/>">
	<input type="hidden" name="txtSiglasArea15" value="">
	<!-- fin:jrosas 10-08-07 -->
	
	<!-- Hidden tipo de certificado: Buques para dominial o gravamen,
	     falta hidden: CboOficinas,txt1, comboAreaLibro, area3BuqueNumeroMatricula, hidTipoCert, comboArea, 
	     ya definido antes -->
	<!-- Hidden tipo de certificado: Buques para dominial o gravamen,
	     falta hidden: CboOficinas,txt1, comboAreaLibro, area3BuqueNumeroMatricula, hidTipoCert, comboArea,  
	     ya definido antes -->  
	<!-- fin:jrosas 04-06-2007 -->   
	
	<!-- Hidden tipo de certificado: Embarcaciones pesqueras para dominial o gravamen,
	     falta hidden: CboOficinas,txt1, comboAreaLibro,comboArea,hid1 ya definido antes -->
	<input type="hidden" name ="area3EmbarcacionNumeroMatricula" value = "">
	<input type="hidden" name ="hidTipoCert" value = "">
	
	<!-- Hidden tipo de certificado: Buques para dominial o gravamen,
	     falta hidden: CboOficinas,txt1, comboAreaLibro, area3BuqueNumeroMatricula, hidTipoCert, comboArea, 
	     ya definido antes -->
	<!-- Hidden tipo de certificado: Buques para dominial o gravamen,
	     falta hidden: CboOficinas,txt1, comboAreaLibro, area3BuqueNumeroMatricula, hidTipoCert, comboArea,  
	     ya definido antes -->  
	<!-- fin:jrosas 04-06-2007 -->
  </logic:iterate>	     
</form>
<form name="frm3" method="post">
	<input type="hidden" name="constanciaMuestra" value="">
	<input type="hidden" name="sol_id" value="">
	<input type="hidden" name="Tipo_Cert" value="">
</form>
<br>

</BODY>
</HTML>

