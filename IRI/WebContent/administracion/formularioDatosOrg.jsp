<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- PANTALLA PARA INSERCION Y ACTUALIZACION DE DATOS DE ORGANIZACION

     VALIDO PARA PERFILES:
            AG - ADMINISTRADOR GENERAL        - INSERCION Y MODIFICACION
            AJ - ADMINSTRADOR DE JURISDICCION - INSERCION Y MODIFICACION
            CA - CAJERO                       - SOLO INSERCION          --%>
            
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<%
String x = (String) request.getAttribute("perfilId");
long perfilId = Long.parseLong(x);

DatosOrganizacionBean datosOrganizacionBean = (DatosOrganizacionBean) request.getAttribute("DATOS_ORGANIZACION_BEAN");
// modo, valores:
//     0 : INSERCION, muestra p&aacutegina vacia para ingreso de nuevos valores
//     1 : INSERCION, hubo un error y muestra p&aacutegina con datos cargados
//                    para que el usuario corrija su error
//     2 : ACTUALIZACION, muestra pagina con datos cargados

x = (String) request.getAttribute("modo");
int modo = Integer.parseInt(x);

if (datosOrganizacionBean != null)
	{
	if (modo==0)
		 modo=1;
	}
%>

<script language="javascript">
Navegador();
//_modo=<%=modo%>_<%=perfilId%>
/* arreglo hijo (provincia, que depende del combo padre DEPARTAMENTO)

	Id provincia,  Descripcion provincia,  Id Departamento
*/
var arr2 = new Array();

<% int k = 0; %>
<logic:iterate name="arrProvincias" id="itemp" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="itemp" property="value01"/>"; //id provincia
	arrx[1]="<bean:write name="itemp" property="value02"/>"; //descripcion provincia
	arrx[2]="<bean:write name="itemp" property="value03"/>"; //id departamento
	arr2[<%=k%>]=arrx;
	<%  k++;%>
</logic:iterate>

function llenaComboHijo()
{

var obj1;
var obj2;

obj1 = document.frm1.departamentoOrganizacion;  //papa
obj2 = document.frm1.provinciaOrganizacion;  //hijo

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

function llenaComboHijo2()
{

var obj1;
var obj2;

obj1 = document.frm1.departamentoAdministrador;  //papa
obj2 = document.frm1.provinciaAdministrador;  //hijo

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

function CambioPaisOrganizacion()
{  
var codigo = "";
var obj1   = document.frm1.paisOrganizacion;
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigo = obj1.options[i].value;
				break;
			}
	}
    if (codigo == "01") 
    { 
    	//es Peru
    	document.frm1.departamentoOrganizacion.disabled=0;
    	document.frm1.provinciaOrganizacion.disabled=0;
		document.frm1.otroDepartamentoOrganizacion.disabled = "true";
    }
    else 
    { 
    <% if (perfilId == Constantes.PERFIL_ADMIN_GENERAL){  %>
       	//no es Peru
      	//un usuario interno DEBE ser peruano
    	if (document.frm1.tipoOrg[0].checked == true)
    		{
    			alert('Usuario Interno debe ser peruano');
    			doCambiaCombo(document.frm1.paisOrganizacion, '01');
    		}
		else
			{
		    	document.frm1.departamentoOrganizacion.disabled="true";
		    	document.frm1.provinciaOrganizacion.disabled="true";
		    	document.frm1.otroDepartamentoOrganizacion.disabled = 0;
		    }
	<% } else {%>
				document.frm1.departamentoOrganizacion.disabled="true";
		    	document.frm1.provinciaOrganizacion.disabled="true";
		    	document.frm1.otroDepartamentoOrganizacion.disabled = 0;
	<% } %>
    }
}

function CambioPaisAdministrador()
{  
var codigo = "";
var obj1   = document.frm1.paisAdministrador;
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigo = obj1.options[i].value;
				break;
			}
	}
    if (codigo == "01") 
    { //es Peru
    	document.frm1.departamentoAdministrador.disabled=0;
    	document.frm1.provinciaAdministrador.disabled=0;
		document.frm1.otroDepartamentoAdministrador.disabled = "true";
    }
    else 
    { //no es Peru
	    	document.frm1.departamentoAdministrador.disabled="true";
	    	document.frm1.provinciaAdministrador.disabled="true";
	    	document.frm1.otroDepartamentoAdministrador.disabled = 0;
    }
}

function RecalculaRadio(opc)
{  
    nav_nombre = navigator.appName.substring(0,5);
	myform=document.forms[0];
<% if (modo < 2)
   { %>    
   

	<% if (perfilId == Constantes.PERFIL_ADMIN_GENERAL) 
		   {%>
		  if(opc == 1) 
		  { 
   		    myform.cur.style.visibility="hidden";
   		    myform.giroNegocio.style.visibility="hidden";
   		    //if(nav_nombre =="Netsc")
			/*if (esNS4){
				nlbl_giro.visibility="hide";
				nlbl_cur.visibility="hide";			
			}
			else if (esIE4) {
				lbl_giro.style.visibility="hidden";
	  		    lbl_cur.style.visibility="hidden";
			}
			else if (esIE5 || esNS6) {

			    lbl_giro = document.getElementById("lbl_giro");
			    lbl_cur = document.getElementById("lbl_cur");
				lbl_giro.style.visibility="hidden";
	  		    lbl_cur.style.visibility="hidden";
			}*/
			invisible_navegador(navegador,'lbl_giro','nlbl_giro','i');
			invisible_navegador(navegador,'lbl_cur','nlbl_cur','i');			
   		    myform.cur.disabled="true";
   		    myform.giroNegocio.disabled="true";
		    //usuarios internos DEBE SER PERUANOS
		    if (document.frm1.paisOrganizacion.value!='01')
		    	{
		    		alert('La organizacion interna debe ser peruana');
		    		myform.tipoOrg[1].checked = true;
					return;
		    	}
			// Org Interna
			myform.exonerarPago[0].checked="true";
		    myform.exonerarPago[0].disabled="true";
		    myform.exonerarPago[1].disabled="true";
		    
		    <% if (perfilId!=Constantes.PERFIL_ADMIN_JURISDICCION) {%>
		    myform.jurisdiccion.disabled = 0;
		    <% } %>
		  }
		  else 
		  { 
		  	//org externa
			myform.exonerarPago[0].disabled=0;
		    myform.exonerarPago[1].disabled=0;
		    
		    <% if (perfilId!=Constantes.PERFIL_ADMIN_JURISDICCION) {%>		    
		    myform.jurisdiccion.disabled = "true";
		    myform.cur.disabled=0;
		    myform.giroNegocio.disabled=0;
			invisible_navegador(navegador,'lbl_giro','nlbl_giro','v');
			invisible_navegador(navegador,'lbl_cur','nlbl_cur','v');			
		    
			/*if (esNS4){
				nlbl_giro.visibility="show";
				nlbl_cur.visibility="show";			
			}
			else if (esIE4) {
				lbl_giro.style.visibility="visible";
	  		    lbl_cur.style.visibility="visible";
			}
			else if (esIE5 || esNS6) {

			    lbl_giro = document.getElementById("lbl_giro");
			    lbl_cur = document.getElementById("lbl_cur");
				lbl_giro.style.visibility="visible";
	  		    lbl_cur.style.visibility="visible";
			}*/
   		    myform.cur.style.visibility="visible";
   		    myform.giroNegocio.style.visibility="visible"; 		    
		    <% } %>
		  }
   <%    }%>
<% }%>      
}

function Recalcula(opc)
{  
    nav_nombre = navigator.appName.substring(0,5);
	myform=document.forms[0];
<% if (modo < 2)
   { %>    
   

	<% if (perfilId == Constantes.PERFIL_ADMIN_GENERAL) 
		   {%>
		  if(myform.tipoOrg[0].checked == true) 
		  { 
   		    myform.cur.style.visibility="hidden";
   		    myform.giroNegocio.style.visibility="hidden";

   		    if(nav_nombre =="Netsc")
			{
				//nlbl_giro.visibility="hide";
	  		    //nlbl_cur.visibility="hide";			
			}else{
				lbl_giro.style.visibility="hidden";
	  		    lbl_cur.style.visibility="hidden";
			}
  		       		       		    
   		    myform.cur.disabled="true";
   		    myform.giroNegocio.disabled="true";
		    //usuarios internos DEBE SER PERUANOS
		    if (document.frm1.paisOrganizacion.value!='01')
		    	{
		    		alert('La organizacion interna debe ser peruana');
		    		myform.tipoOrg[1].checked = true;
					return;
		    	}
			// Org Interna
			myform.exonerarPago[0].checked="true";
		    myform.exonerarPago[0].disabled="true";
		    myform.exonerarPago[1].disabled="true";
		    
		    <% if (perfilId!=Constantes.PERFIL_ADMIN_JURISDICCION) {%>
		    myform.jurisdiccion.disabled = 0;
		    <% } %>
		  }
		  else 
		  { 
		  	//org externa
			myform.exonerarPago[0].disabled=0;
		    myform.exonerarPago[1].disabled=0;
		    
		    <% if (perfilId!=Constantes.PERFIL_ADMIN_JURISDICCION) {%>		    
		    myform.jurisdiccion.disabled = "true";
		    myform.cur.disabled=0;
		    myform.giroNegocio.disabled=0;		    
   		    if(nav_nombre =="Netsc")
			{
	   		    //nlbl_giro.visibility="show";
 	  		    //nlbl_cur.visibility="show";				
			}else{
	   		    lbl_giro.style.visibility="visible";
 	  		    lbl_cur.style.visibility="visible";	
			}
   		    myform.cur.style.visibility="visible";
   		    myform.giroNegocio.style.visibility="visible"; 		    
		    <% } %>
		  }
   <%    }%>
<% }%>      
}


function doCancelar()
{
	history.back(1);
}

function doAceptar()
{
//validaciones de campos:
<% if (modo<=1) {%>
	if(esVacio(document.frm1.razonSocial.value))
	{
		alert("Por favor ingrese correctamente la Razón Social de la Organización");
		document.frm1.razonSocial.focus();
		return;	
	}
	if (esVacio(document.frm1.ruc.value) || !esEntero(document.frm1.ruc.value) || !esMayor(document.frm1.ruc.value,11) || !esEnteroMayor(document.frm1.ruc.value,1))
		{	
			alert("Por favor ingrese correctamente el Número de Documento de la Organización.\nEl Número de Documento debe contener 11 caracteres numéricos (0-9)");
			document.frm1.ruc.focus();
			return;
		}	
<%}%>
	if (esVacio(document.frm1.distritoOrganizacion.value) || !contieneCarateresValidos(document.frm1.distritoOrganizacion.value,"numeronombre"))
		{	
			alert("Por favor ingrese correctamente el Distrito de la Organización");
			document.frm1.distritoOrganizacion.focus();
			return;
		}		
	if (esVacio(document.frm1.direccionOrganizacion.value))
		{	
			alert("Por favor ingrese correctamente la Dirección de la Organización");
			document.frm1.direccionOrganizacion.focus();
			return;
		}
		
	if (esVacio(document.frm1.apellidoPaternoRepresentante.value)  || !contieneCarateresValidos(document.frm1.apellidoPaternoRepresentante.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Paterno del Representante");
			document.frm1.apellidoPaternoRepresentante.focus();
			return;
		}	
	if (!esVacio(document.frm1.apellidoMaternoRepresentante.value)  && !contieneCarateresValidos(document.frm1.apellidoMaternoRepresentante.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Materno del Representante");
			document.frm1.apellidoMaternoRepresentante.focus();
			return;
		}	
		
	if (esVacio(document.frm1.nombresRepresentante.value) || !contieneCarateresValidos(document.frm1.nombresRepresentante.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Nombre del Representante");
			document.frm1.nombresRepresentante.focus();
			return;
		}	
	if (esVacio(document.frm1.numeroDocumentoRepresentante.value) || !esEntero(document.frm1.numeroDocumentoRepresentante.value) || !esMayor(document.frm1.numeroDocumentoRepresentante.value,8) || !esEnteroMayor(document.frm1.numeroDocumentoRepresentante.value,1))
		{	
			alert("Por favor ingrese correctamente el Número del Documento del Representante\nEl Número de Documento debe contener al menos 8 digitos numéricos (0-9)");
			document.frm1.numeroDocumentoRepresentante.focus();
			return;
		}	
	
	if (esVacio(document.frm1.apellidoPaternoAdministrador.value)  || !contieneCarateresValidos(document.frm1.apellidoPaternoAdministrador.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Paterno del Administrador");
			document.frm1.apellidoPaternoAdministrador.focus();
			return;
		}	
	if (!esVacio(document.frm1.apellidoMaternoAdministrador.value)  && !contieneCarateresValidos(document.frm1.apellidoMaternoAdministrador.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Materno del Administrador");
			document.frm1.apellidoMaternoAdministrador.focus();
			return;
		}	
		
	if (esVacio(document.frm1.nombresAdministrador.value) || !contieneCarateresValidos(document.frm1.nombresAdministrador.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Nombre del Administrador");
			document.frm1.nombresAdministrador.focus();
			return;
		}	
	
	if (!esEmail(document.frm1.emailAdministrador.value) || !contieneCarateresValidos(document.frm1.emailAdministrador.value,"correo"))
		{	
			alert("Por favor ingrese correctamente el Correo Electrónico del Administrador.\nEl Correo Electrónico puede contener caracteres alfanuméricos(A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
			document.frm1.emailAdministrador.focus();
			return;
		}
	
	if (esVacio(document.frm1.numeroDocumentoAdministrador.value) || !esEntero(document.frm1.numeroDocumentoAdministrador.value) || !esMayor(document.frm1.numeroDocumentoAdministrador.value,8) || !esEnteroMayor(document.frm1.numeroDocumentoAdministrador.value,1))
		{	
			alert("Por favor ingrese correctamente Número del Documento del Administrador.\nEl Número de Documento debe contener al menos 8 digitos numéricos (0-9)");
			document.frm1.numeroDocumentoAdministrador.focus();
			return;
		}	
	
	if (esVacio(document.frm1.distritoAdministrador.value)|| !contieneCarateresValidos(document.frm1.distritoAdministrador.value,"numeronombre"))
		{	
			alert("Por favor ingrese correctamente el Distrito del Administrador");
			document.frm1.distritoAdministrador.focus();
			return;
		}
	if (esVacio(document.frm1.direccionAdministrador.value))
		{	
			alert("Por favor ingrese correctamente la Dirección del Administrador");
			document.frm1.direccionAdministrador.focus();
			return;
		}

	if(!esVacio(document.frm1.telefonoAdministrador.value)){	
		if(!contieneCarateresValidos(document.frm1.telefonoAdministrador.value,"telefono") || !esMayor(document.frm1.telefonoAdministrador.value,6))	
			{
					alert("Por favor ingrese correctamente el Número de Teléfono del Administrador.\nEl Número de Teléfono puede contener caracteres numéricos (0-9), espacios() y guiones(-)");
					document.frm1.telefonoAdministrador.focus();
					return;
			
			}
		
	}
	
	if(!esVacio(document.frm1.anexoAdministrador.value)){	
		if(!contieneCarateresValidos(document.frm1.anexoAdministrador.value,"telefono"))	
			{
					alert("Por favor ingrese correctamente el Numero de Anexo del Administrador.\nEl Numero de Anexo puede contener caracteres numericos (0-9), espacios() y guiones(-)");
					document.frm1.anexoAdministrador.focus();
					return;
			
			}
		
	}
	
	if(!esVacio(document.frm1.faxAdministrador.value)){	
		if(!contieneCarateresValidos(document.frm1.faxAdministrador.value,"telefono") || !esMayor(document.frm1.faxAdministrador.value,6))	
			{
					alert("Por favor ingrese correctamente el Numero de Fax del Administrador.\nEl Numero de Fax puede contener caracteres numericos (0-9), espacios() y guiones(-)");
					document.frm1.faxAdministrador.focus();
					return;
			
			}
	}	

<% if (perfilId!=Constantes.PERFIL_CAJERO) {%>
	<% if (modo<=1) {%>
	//campo usuario	
	//var usuario = document.frm1.prefijoCuenta.value;
	if (!esLongitudEntre(document.frm1.prefijoCuenta.value,6,10) || !contieneCarateresValidos(document.frm1.prefijoCuenta.value,"usuariopassword") || esEntero(extraeDer(document.frm1.prefijoCuenta.value,3)))
		{	
			alert("Por favor ingrese correctamente el prefijo.\nEl Prefijo debe contener entre 6 y 10 caracteres.\nLos 3 últimos caracteres del Prefijo no deben ser numéricos (0-9)");
			document.frm1.prefijoCuenta.focus();
			return;		
		}	

	if(esVacio(document.frm1.clave.value) || !esLongitudEntre(document.frm1.clave.value,6,10) || !contieneNumero(document.frm1.clave) || !contieneCarateresValidos(document.frm1.clave.value,"usuariopassword")){
			alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico (0-9)");
			document.frm1.clave.focus();
			return;		
	}

	if (document.frm1.clave.value.toUpperCase()== document.frm1.prefijoCuenta.value.toUpperCase())
		{	
			alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña no puede ser igual al campo Prefijo");
			document.frm1.clave.focus();
			return;		
		}
		
	//var pwd2 = document.frm1.admConfirmaPassword.value;
	//pwd2 = pwd2.toUpperCase();
	if (document.frm1.confirmacionClave.value.toUpperCase() != document.frm1.clave.value.toUpperCase())
		{	
			alert("La Contraseña no esta verificada");
			document.frm1.confirmacionClave.focus();
			return;		
		}
		
	//validar respuesta secreta
	//var res = document.frm1.admRespuesta.value;
	if (!esLongitudEntre(document.frm1.respPregSecreta.value,5,30) || !contieneCarateresValidos(document.frm1.respPregSecreta.value,"usuariopassword"))
		{	
			alert("Por favor ingrese correctamente la Respuesta Secreta.\nLa Respuesta Secreta debe contener entre 5 y 30 caracteres");
			document.frm1.respPregSecreta.focus();
			return;		
		}
	if (document.frm1.respPregSecreta.value.toUpperCase == document.frm1.prefijoCuenta.value)
		{	
			alert("La Respuesta Secreta no puede ser igual al campo Prefijo");
			document.frm1.respPregSecreta.focus();
			return;		
		}

	<%  }  %>

	<% if (modo>=2) {%>	
	//valida password SOLAMENTE SI SE HA INGRESADO
	if(!esVacio(document.frm1.clave.value))
	{
		if (!esLongitudEntre(document.frm1.clave.value,6,10) || !contieneNumero(document.frm1.clave) || !contieneCarateresValidos(document.frm1.clave.value,"usuariopassword"))
			{	
				alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico (0-9)");
				document.frm1.clave.focus();
				return;		
			}
		if (document.frm1.clave.value.toUpperCase()== document.frm1.prefijoCuenta.value.toUpperCase())
			{	
				alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña no puede ser igual al campo Prefijo");
				document.frm1.clave.focus();
				return;		
			}		
		if (document.frm1.confirmacionClave.value.toUpperCase() != document.frm1.clave.value.toUpperCase())
			{	
				alert("La Contraseña no esta verificada");
				document.frm1.confirmacionClave.focus();
				return;		
			}			
		if (!esLongitudEntre(document.frm1.respPregSecreta.value,5,30) || !contieneCarateresValidos(document.frm1.respPregSecreta.value,"usuariopassword"))
			{	
				alert("Por favor ingrese correctamente la Respuesta Secreta.\nLa Respuesta Secreta debe contener entre 5 y 30 caracteres");
				document.frm1.respPregSecreta.focus();
				return;		
			}
		if (document.frm1.respPregSecreta.value.toUpperCase == document.frm1.prefijoCuenta.value)
			{	
				alert("La Respuesta Secreta no puede ser igual al campo Prefijo");
				document.frm1.respPregSecreta.focus();
				return;		
			}				
	}
	<% } %>
<%}else{%>
	if (!esLongitudEntre(document.frm1.prefijoCuenta.value,6,10) || !contieneCarateresValidos(document.frm1.prefijoCuenta.value,"usuariopassword") || esEntero(extraeDer(document.frm1.prefijoCuenta.value,3)))
		{	
			alert("Por favor ingrese correctamente el prefijo.\nEl Prefijo debe contener entre 6 y 10 caracteres.\nLos 3 ultimos caracteres del Prefijo no deben ser numericos (0-9)");
			document.frm1.prefijoCuenta.focus();
			return;		
		}
		
<%  }  %>

/*
obj_mayuscula(document.frm1.apellidoPaterno);
obj_mayuscula(document.frm1.apellidoMaterno);
obj_mayuscula(document.frm1.nombres);
obj_mayuscula(document.frm1.distrito);
obj_mayuscula(document.frm1.direccion);
obj_mayuscula(document.frm1.apellidoPaterno);
*/

<%if (modo<=1) {%>
	document.frm1.action = "/iri/CrearOrganizacion.do?state=registraDatos";
<%} else {%>	
	document.frm1.action = "/iri/EditarOrganizacion.do?state=registraDatos";
<%   } %>	
	
	document.frm1.submit();
}


function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}

function doCambiaRadio(obj, valor)
{ 
for (var rr = 0; rr < obj.length; rr++)
	{
		var xvlr = obj[rr].value;
		if (xvlr == valor)
			obj[rr].checked=true;
	}
}
</script>
<body <% if (perfilId!=Constantes.PERFIL_CAJERO) {%> onload="Recalcula();"<%}%>>
<%
String titulo="";

if (modo<=1)
{
	titulo="Creaci&oacute;n de Nueva Organizaci&oacute;n";
	if (perfilId==Constantes.PERFIL_CAJERO)
		titulo = "Afiliaci&oacute;n de Organizaci&oacute;n (Ventanilla)";
}
else
{
	titulo="Edici&oacute;n de Nueva Organizaci&oacute;n";
	if (perfilId==Constantes.PERFIL_CAJERO)
		titulo = "Edici&oacute;n de Organizaci&oacute;n (Ventanilla)";	
}
%>
<br>
<table cellspacing=0 class=titulo>
  <tr>
	<td>
		<FONT COLOR=black>ADMINISTRACI&Oacute;N &nbsp; &gt;&gt; Organizaciones&gt;&gt; </FONT><%=titulo%>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" <% if (perfilId!=Constantes.PERFIL_CAJERO) {%> onload="Recalcula();"<%}%> class="formulario">
<table class=tablasinestilo>
<tr><th colspan=4>DATOS DE LA ORGANIZACI&Oacute;N</th></tr>
  <tr>
    <td width="114">RAZ&Oacute;N SOCIAL</td>
    <td colspan="3"><input type="text" name="razonSocial" size="186" maxlength="100" <%if (modo>=2){%>disabled=true<%}%> onblur="sololet(this)" style="width:466"></td>
  </tr>
  <tr>
    <td width="114">RUC</td>
    <td width="187"><input type="text" name="ruc"  size="11" maxlength="11" <%if (modo>=2){%>disabled=true<%}%> onblur="solonum(this)" style="width:110"></td>
    <td width="140"><layer id="nlbl_giro"><div id="lbl_giro" name="lbl_giro">GIRO </div></layer></td>
    <td>
    	<select size="1" name="giroNegocio" style="width:140">
            	<logic:iterate name="arrGiros" id="giro" scope="request">
            		<option value="<bean:write name="giro" property="codigo"/>"><bean:write name="giro" property="descripcion"/></option>
            	</logic:iterate>
    	</select>  
    <%--
    	<select size="1" name="giroNegocio" style="width:140">
            	<logic:iterate name="arrGiros" id="giro" scope="request">
            		
            		<logic:equal name="giro" property="codigo" value="5">
            			<option value="<bean:write name="giro" property="codigo"/>" selected><bean:write name="giro" property="descripcion"/></option>
            		</logic:equal>
            		<logic:notEqual name="giro" property="codigo" value="5">
            			<option value="<bean:write name="giro" property="codigo"/>"><bean:write name="giro" property="descripcion"/></option>
            		</logic:notEqual>
            	</logic:iterate>
    	</select>  
    --%>
    </td>
  </tr>
  <tr>
    <td width="114">PA&Iacute;S</td>
    <td width="187">
		<select size="1" name="paisOrganizacion" onChange="CambioPaisOrganizacion();" style="width:187">
			<logic:iterate name="arrPaises" id="item1" scope="request">
					<logic:equal name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>" selected> <bean:write name="item1" property="descripcion"/> </option>
					</logic:equal>
					<logic:notEqual name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
					</logic:notEqual>
			</logic:iterate>
            </select>
    </td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td width="114">DEPARTAMENTO</td>
    <td width="187">
		<SELECT  name="departamentoOrganizacion" 
		<%if (modo>=2)
		{%>disabled=true<%}%> 
		onchange=llenaComboHijo(); style="width:187">
                <logic:iterate name="arrDepartamentos" id="dpto" scope="request">
                <option value="<bean:write name="dpto" property="codigo"/>"><bean:write name="dpto" property="descripcion"/></option>
                </logic:iterate>
        </SELECT>    </td>
    <td width="140">OTRO</td>
    <td><input type="text" name="otroDepartamentoOrganizacion" size="11" disabled="true" style="width:133" onblur="sololet(this)">
    </td>
  </tr>
  
  <tr>
    <td width="114">PROVINCIA</td>
    <td width="187"><SELECT  name="provinciaOrganizacion" style="width:187" <%if (modo>=2){%>disabled=true<%}%>>
         </SELECT>
    </td>
    <td width="140">DISTRITO</td>
    <td><input type="text" name="distritoOrganizacion" size="20" maxlength="40" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">AV/CALLE/JR Y NRO</td>
    <td width="187"><input type="text" name="direccionOrganizacion" size="20" maxlength="40" style="width:133" onblur="sololet(this)"></td>
    <td width="140">COD POSTAL</td>
    <td><input type="text" name="codPostalOrganizacion" size="11" maxlength="12" style="width:133" onblur="sololet(this)"></td>
  </tr>
<tr><th colspan=4>DATOS DEL REPRESENTANTE</th></tr>
  <tr>
    <td width="114">APELLIDO&nbsp;PATERNO</td>
    <td width="187"><input type="text" name="apellidoPaternoRepresentante" size="20" maxlength="30" style="width:133" onblur="sololet(this)"></td>
    <td width="140">APELLIDO MATERNO</td>
    <td><input type="text" name="apellidoMaternoRepresentante"  size="20" maxlength="30" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">NOMBRES</td>
    <td width="187"><input type="text" name="nombresRepresentante" size="20" maxlength="40" style="width:133" onblur="sololet(this)"></td>
    <td width="140"></td>
    <td></td>
  </tr>
  <tr>
    <td width="114">TIPO DOCUMENTO</td>
    <td width="187"><select size="1" name="tipoDocumentoRepresentante">
        <logic:iterate name="arrTiposDocumento" id="tipDoc" scope="request">
            	<option value="<bean:write name="tipDoc" property="codigo"/>"><bean:write name="tipDoc" property="descripcion"/></option>
        </logic:iterate> 
            </select>
    </td>
    <td width="140">N&Uacute;MERO DOCUMENTO</td>
    <td><input type="text" name="numeroDocumentoRepresentante" size="10" maxlength="15" style="width:133" onblur="sololet(this)"></td>
  </tr>
<tr><th colspan=4>DATOS DEL ADMINISTRADOR</th></tr>
  <tr>
    <td width="114">APELLIDO PATERNO</td>
    <td width="187"><input type="text" name="apellidoPaternoAdministrador" size="20" maxlength="30"  style="width:133" onblur="sololet(this)"></td>
    <td width="140">APELLIDO MATERNO</td>
    <td><input type="text" name="apellidoMaternoAdministrador"  size="20" maxlength="30" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">NOMBRES</td>
    <td width="187"><input type="text" name="nombresAdministrador"  size="20" maxlength="40" style="width:133" onblur="sololet(this)"></td>
    <td width="140">CORREO ELECTR&Oacute;NICO</td>
    <td><INPUT type="text" name="emailAdministrador"  size="20" maxlength="40" style="width:133" ></td>
  </tr>
  <tr>
    <td width="114">TIPO DOCUMENTO</td>
    <td width="187"><select size="1" name="tipoDocumentoAdministrador">
        <logic:iterate name="arrTiposDocumento" id="tipDoc" scope="request">
            	<option value="<bean:write name="tipDoc" property="codigo"/>"><bean:write name="tipDoc" property="descripcion"/></option>
	     </logic:iterate> 
            </select>
    </td>
    <td width="140">N&Uacute;MERO DOCUMENTO</td>
    <td><input type="text" name="numeroDocumentoAdministrador" size="10" maxlength="15" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">PA&Iacute;S</td>
    <td width="187"><select size="1" name="paisAdministrador" onChange="CambioPaisAdministrador();" style="width:187">
			<logic:iterate name="arrPaises" id="item1" scope="request">
					<logic:equal name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>" selected> <bean:write name="item1" property="descripcion"/> </option>
					</logic:equal>
					<logic:notEqual name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
					</logic:notEqual>
			</logic:iterate>
          </select>
    </td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td width="114">DEPARTAMENTO</td>
    <td width="187"><SELECT size=1 name="departamentoAdministrador" onchange=llenaComboHijo2(); style="width:187">
        <logic:iterate name="arrDepartamentos" id="dpto" scope="request">
	       	<option  value="<bean:write name="dpto" property="codigo"/>"><bean:write name="dpto" property="descripcion"/></option>
	    </logic:iterate>
         </SELECT>  
    </td>
    <td width="140">OTRO</td>
    <td><input type="text" name="otroDepartamentoAdministrador" size="11" maxlength="30" disabled="true" style="width:133" onblur="sololet(this)">
    </td>
  </tr>

  <tr>
    <td width="114">PROVINCIA</td>
    <td width="187"><SELECT  name="provinciaAdministrador" style="width:187"></SELECT></td>
    <td width="140">DISTRITO</td>
    <td><input type="text" name="distritoAdministrador" size="11" maxlength="40" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">AV/CALLE/JR Y NRO</td>
    <td width="187"><input type="text" name="direccionAdministrador" size="11" maxlength="40" style="width:133" onblur="sololet(this)"> </td>
    <td width="140">COD POSTAL</td>
    <td><input type="text" name="codPostalAdministrador" size="11" maxlength="12" style="width:133" onblur="sololet(this)"></td>
  </tr>

  <tr>
    <td width="114">TELEFONO</td>
    <td><input type="text" name="telefonoAdministrador"  size="11" maxlength="30" style="width:133" onblur="sololet(this)"></td>
    <td width="140">ANEXO</td>
    <td><input type="text" name="anexoAdministrador" size="11" maxlength="10" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">FAX</td>
    <td><input type="text" name="faxAdministrador" size="11" maxlength="30" style="width:133" onblur="sololet(this)"></td>
    <td></td>
    <td></td>
  </tr>  
 
  <tr>
    <td width="114">PREFIJO CUENTA</td>
    <td width="187"><input type="text" name="prefijoCuenta"  size="12" maxlength="10"  style="width:133" onblur="sololet(this)" <%if (modo>=2){%>disabled=true<%}%>></td>
    <td colspan=2>El prefijo cuenta se utiliza para generar los nombres de usuario de su organizaci&oacute;n</td>	
  </tr>
	<% if (perfilId == Constantes.PERFIL_ADMIN_GENERAL) 
		{%>
  <tr>
    <td width="114">TIPO</td>
    <td width="187">
	    <input type="radio" value="I" name="tipoOrg"         onClick="RecalculaRadio(1)" <%if (modo>=2){%>disabled=true<%}%>>INTERNO&nbsp;
	    <input type="radio" value="E" name="tipoOrg" checked onClick="RecalculaRadio(2)" <%if (modo>=2){%>disabled=true<%}%>>EXTERNO
	</td>    
	<td></td>
	<td></td>	    
  </tr>
	<%  } %>	
  <tr>
  	<% if (perfilId!=Constantes.PERFIL_CAJERO) { %>
    <td width="114">EXONERAR COBRO</td>
    <td><input type="radio" value="S" name="exonerarPago"  >SI&nbsp; <input type="radio" value="N" name="exonerarPago" checked  >NO</td>
    <%} else {%>
    <td></td>
    <td></td>
   <% } %>
    <td><layer id="nlbl_cur"><div id="lbl_cur" name="lbl_cur">CUR</div></layer></td>
    <td><INPUT type="text" name="cur" size="15" maxlength="14" style="width:133" onblur="sololet(this)"></td>
  </tr>
<% if (perfilId!=Constantes.PERFIL_CAJERO) { %>  
  <tr>
    <td width="114">CONTRASE&Ntilde;A</td>
    <td width="187"><input type="password" name="clave" size="10" maxlength="10" onblur="sololet(this)" ></td>
    <td width="140">CONFIRME CONTRASE&Ntilde;A</td>
    <td><input type="password" name="confirmacionClave" size="10" maxlength="10" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">PREGUNTA SECRETA</td>
    <td width="187">
      <select  name="pregSecreta" size="1" style="width:187">
        <logic:iterate name="arrPreguntas" id="preg" scope="request">
            	<option value="<bean:write name="preg" property="codigo"/>"><bean:write name="preg" property="descripcion"/></option>
       </logic:iterate>
      </select>  
    </td>
    <td width="140">RESPUESTA A PREGUNTA</td>
    <td><input type="text" name="respPregSecreta" size="20" maxlength="30" onblur="sololet(this)" style="width:133" onblur="sololet(this)"></td>
  </tr>
<%}%>  
<% if (perfilId==Constantes.PERFIL_ADMIN_GENERAL)
   { %>    
<tr>
<th colspan=4>ALCANCE</th></tr>
  <tr>
  	<td colspan=4>
    	<SELECT size="1" name="jurisdiccion">
            <logic:iterate name="arrJurisdicciones" id="juris" scope="request">
	          	<option value="<bean:write name="juris" property="codigo"/>"><bean:write name="juris" property="descripcion"/></option>
      		</logic:iterate>
        </SELECT>
     </td>
  </tr>     
  <tr>     
  	<td colspan=4>
  	<hr>
  	</td>     
  </tr>
<% } %>
  <tr>
    <td align="center" colspan=4>
      <A href="javascript:doAceptar()" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="<%=request.getContextPath()%>/images/btn_aceptar.gif"></a>      
      <A href="javascript:doCancelar()" onmouseover="javascript:mensaje_status('Cancelar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="<%=request.getContextPath()%>/images/btn_cancelar.gif"></a>
    </td>
  </tr>
</table>
  

<script LANGUAGE="JavaScript">
	llenaComboHijo();	
	llenaComboHijo2();
</script>

<% if (modo>=1) {%>
<script LANGUAGE="JavaScript">
   document.frm1.razonSocial.value			            ="<%=datosOrganizacionBean.getRazonSocial()%>";
   document.frm1.ruc.value				                ="<%=datosOrganizacionBean.getRuc()%>";
   doCambiaCombo(document.frm1.paisOrganizacion, "<%=datosOrganizacionBean.getPaisIdOrganizacion()%>");
   
	document.frm1.otroDepartamentoOrganizacion.value = "<%=datosOrganizacionBean.getOtroDepartamentoOrganizacion()%>";
   doCambiaCombo(document.frm1.departamentoOrganizacion, "<%=datosOrganizacionBean.getDepartamentoIdOrganizacion()%>");
   llenaComboHijo();
   doCambiaCombo(document.frm1.provinciaOrganizacion,"<%=datosOrganizacionBean.getProvinciaIdOrganizacion()%>");
   doCambiaCombo(document.frm1.giroNegocio,"<%=datosOrganizacionBean.getGiroNegocio()%>");
   document.frm1.distritoOrganizacion.value	            ="<%=datosOrganizacionBean.getDistritoOrganizacion()%>";
   document.frm1.direccionOrganizacion.value	        ="<%=datosOrganizacionBean.getDireccionOrganizacion()%>";
   document.frm1.codPostalOrganizacion.value	        ="<%=datosOrganizacionBean.getCodPostalOrganizacion()%>";
   document.frm1.cur.value				                ="<%=datosOrganizacionBean.getCur()%>";
   <% if (perfilId == Constantes.PERFIL_ADMIN_GENERAL) 
   	  { %>
   	  
   	  
   	  	  <%if (datosOrganizacionBean.getFlagOrganizacionInterna()==true)
   	  		{%>
   	  			doCambiaRadio(document.frm1.tipoOrg,"I");
		  <%} else {%>
		  		doCambiaRadio(document.frm1.tipoOrg,"E");
          <% } %>
   
   
   
   		doCambiaCombo(document.frm1.jurisdiccion,"<%=datosOrganizacionBean.getJurisdiccionId()%>");
   <% } %>
     
   <% if (perfilId!=Constantes.PERFIL_CAJERO) 
       { 
       	  if (datosOrganizacionBean.getFlagExonerarPago()==true) {%> 
		 		doCambiaRadio(document.frm1.exonerarPago,"S");       	  
		  <% } else { %>
				doCambiaRadio(document.frm1.exonerarPago,"N");
		  <%  } %>

		   doCambiaCombo(document.frm1.pregSecreta,"<%=datosOrganizacionBean.getPreguntaSecretaId()%>");

		   document.frm1.respPregSecreta.value		            ="<%=datosOrganizacionBean.getRespuestaSecreta()%>";
		   document.frm1.prefijoCuenta.value		            ="<%=datosOrganizacionBean.getPrefijoCuenta()%>";
   <%  }  %>
                                                        
   document.frm1.apellidoPaternoRepresentante.value     ="<%=datosOrganizacionBean.getApellidoPaternoRepresentante()%>";
   document.frm1.apellidoMaternoRepresentante.value     ="<%=datosOrganizacionBean.getApellidoMaternoRepresentante()%>";
   document.frm1.nombresRepresentante.value	            ="<%=datosOrganizacionBean.getNombresRepresentante()%>";
   document.frm1.tipoDocumentoRepresentante.value	    ="<%=datosOrganizacionBean.getTipoDocumentoRepresentante()%>";
   document.frm1.numeroDocumentoRepresentante.value     ="<%=datosOrganizacionBean.getNumeroDocumentoRepresentante()%>";
                                                        
   document.frm1.apellidoPaternoAdministrador.value     ="<%=datosOrganizacionBean.getApellidoPaternoAdministrador()%>";
   document.frm1.apellidoMaternoAdministrador.value     ="<%=datosOrganizacionBean.getApellidoMaternoAdministrador()%>";
   document.frm1.nombresAdministrador.value	            ="<%=datosOrganizacionBean.getNombresAdministrador()%>";
   
   document.frm1.tipoDocumentoAdministrador.value	    ="<%=datosOrganizacionBean.getTipoDocumentoAdministrador()%>";
   document.frm1.numeroDocumentoAdministrador.value     ="<%=datosOrganizacionBean.getNumeroDocumentoAdministrador()%>";
   document.frm1.emailAdministrador.value		        ="<%=datosOrganizacionBean.getEmailAdministrador()%>";
   doCambiaCombo(document.frm1.paisAdministrador,"<%=datosOrganizacionBean.getPaisAdministrador()%>");
   doCambiaCombo(document.frm1.departamentoAdministrador,"<%=datosOrganizacionBean.getDepartamentoAdministrador()%>");
   llenaComboHijo2();
   doCambiaCombo(document.frm1.provinciaAdministrador,"<%=datosOrganizacionBean.getProvinciaAdministrador()%>");
   document.frm1.otroDepartamentoAdministrador.value    ="<%=datosOrganizacionBean.getOtroDepartamentoAdministrador()%>";
   document.frm1.distritoAdministrador.value	        ="<%=datosOrganizacionBean.getDistritoAdministrador()%>";
   document.frm1.direccionAdministrador.value	        ="<%=datosOrganizacionBean.getDireccionAdministrador()%>";
   document.frm1.codPostalAdministrador.value	        ="<%=datosOrganizacionBean.getCodPostalAdministrador()%>";
   document.frm1.telefonoAdministrador.value	        ="<%=datosOrganizacionBean.getTelefonoAdministrador()%>";
   document.frm1.anexoAdministrador.value	            ="<%=datosOrganizacionBean.getAnexoAdministrador()%>";
   document.frm1.faxAdministrador.value		            ="<%=datosOrganizacionBean.getFaxAdministrador()%>";
   
   CambioPaisAdministrador();
   CambioPaisOrganizacion();
   
   
<% if (modo >= 2) {%>
	
	<%if (datosOrganizacionBean.getFlagOrganizacionInterna()==true) {%>	
		document.frm1.paisOrganizacion.disabled=true;
		document.frm1.departamentoOrganizacion.disabled=true;
		document.frm1.provinciaOrganizacion.disabled=true;
		document.frm1.giroNegocio.style.visibility="hidden";
		document.frm1.cur.style.visibility="hidden";
		lbl_giro.style.visibility="hidden";
	    lbl_cur.style.visibility="hidden";		
		
		<% if (perfilId!=Constantes.PERFIL_CAJERO) { %>
			doCambiaRadio(document.frm1.exonerarPago,"S");
			document.frm1.exonerarPago[0].disabled=true;
			document.frm1.exonerarPago[1].disabled=true;
		<%  } %>
	<% } %>


  <% if (perfilId==Constantes.PERFIL_ADMIN_GENERAL)   { %>    
	   document.frm1.jurisdiccion.disabled=true;
	<%  } %>   
   
<% }%>   


</script>
<input type ="hidden" name="hid1" value="<%=datosOrganizacionBean.getOrganizacionPeJuriId()%>">
<% } %>


<%
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
%>
<br>
<script LANGUAGE="JavaScript">
	alert("<%=validacionException.getMensaje()%>");
	<% 
	String foco = validacionException.getFocus();
	if (foco.trim().length() > 0) {%>
		document.frm1.<%=foco%>.focus();
	<% } %>
</script>
<% } %>
</form>
</html>