<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%-- 
	PANTALLA PARA AFILIACION de organizaciones
--%>

<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.afiliacion.bean.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<title></title>
<LINK href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">
/*
arreglo hijo (provincia, que depende del combo padre DEPARTAMENTO)

	Id Provincia,  Descripcion Provincia,  Id Departamento
*/
var arr2 = new Array();

<% int k = 0; %>
<logic:iterate name="arrpro" id="itemp" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="itemp" property="value01"/>"; //id provincia
	arrx[1]="<bean:write name="itemp" property="value02"/>"; //descripcion provincia
	arrx[2]="<bean:write name="itemp" property="value03"/>"; //id departamento
	arr2[<%=k%>]=arrx;
	<%  k++; %>
</logic:iterate>
function llenaDepProv()
{
obj1 = document.frm1.orgPais;
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPais=obj1.options[i].value;			
				break;
			}
	}
	
if (codigoPais=="01"){
document.frm1.orgDpto.disabled=false;
document.frm1.orgProvincia.disabled=false;
document.frm1.orgLug.disabled=true;
}else{
document.frm1.orgDpto.disabled=true;
document.frm1.orgProvincia.disabled=true;
document.frm1.orgLug.disabled=false;
}	

}
function llenaDepProvAdm()
{
obj1 = document.frm1.admPais;
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPais=obj1.options[i].value;			
				break;
			}
	}
	
if (codigoPais=="01"){
document.frm1.admDpto.disabled=false;
document.frm1.admProvincia.disabled=false;
document.frm1.admOtro.disabled=true;
}else{
document.frm1.admDpto.disabled=true;
document.frm1.admProvincia.disabled=true;
document.frm1.admOtro.disabled=false;
}	

}


function llenaComboHijo(tipo)
{

var obj1;
var obj2;

if (tipo=='1')
{
	obj1 = document.frm1.orgDpto;  //papa
	obj2 = document.frm1.orgProvincia;  //hijo
}
else
{
	obj1 = document.frm1.admDpto;  //papa
	obj2 = document.frm1.admProvincia;  //hijo
}

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


function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}










function doCancela()
{ 
	window.open ("/iri/acceso/displayLogin.html", target="_top");
}

function doAcepta()
{
	//validaciones

if (esVacio(document.frm1.orgRazon.value) )
	{	
		alert("Por favor ingrese correctamente la Razón Social de la Organización");
		document.frm1.orgRazon.focus();
		return;
	}
if (esVacio(document.frm1.orgRuc.value) || !esEntero(document.frm1.orgRuc.value) || !esMayor(document.frm1.orgRuc.value,11) || !esEnteroMayor(document.frm1.orgRuc.value,1))
	{	
		alert("Por favor ingrese correctamente el Número de Documento de la Organización.\nEl Número de Documento requiere al menos 11 caracteres numéricos (0-9) ");
		document.frm1.orgRuc.focus();
		return;
	}	
	
/*
29oct
validar distrito solamente si pais es peru
*/	
if (document.frm1.orgPais.value=="01")
{
if (esVacio(document.frm1.orgDistrito.value) || !contieneCarateresValidos(document.frm1.orgDistrito.value,"numeronombre"))
	{	
		alert("Por favor ingrese correctamente el Distrito de la Organización");
		document.frm1.orgDistrito.focus();
		return;
	}		
}
if (esVacio(document.frm1.orgVia.value) )
	{	
		alert("Por favor ingrese correctamente la Vía de la Organización");
		document.frm1.orgVia.focus();
		return;
	}		
	
if(!esVacio(document.frm1.orgTelefono.value)){	
	if(!contieneCarateresValidos(document.frm1.orgTelefono.value,"telefono") || !esLongitudEntre(document.frm1.orgTelefono.value,6,30))	
	{
			alert("Por favor ingrese correctamente el Número de Teléfono de la Organización.\nEl Número de Teléfono debe contener mas de 6 caracteres.\nEl Numero de Teléfono puede contener caracteres numéricos(0123456789), espacios( ) y guiones(-).");
			document.frm1.orgTelefono.focus();
			return;
	
	}
}

if(!esVacio(document.frm1.orgFax.value)){	
	if(!contieneCarateresValidos(document.frm1.orgFax.value,"telefono") || !esLongitudEntre(document.frm1.orgFax.value,6,30))	
	{
			alert("Por favor ingrese correctamente el Número de Fax de la Organización\nEl Número de Fax debe contener mas de 6 caracteres.\nEl Número de Fax puede contener caracteres numéricos(0123456789), espacios( ) y guiones(-).");
			document.frm1.orgFax.focus();
			return;
	
	}
}

if(esVacio(document.frm1.orgEmail.value) || !esEmail(document.frm1.orgEmail.value) || !contieneCarateresValidos(document.frm1.orgEmail.value,"correo")){	
		alert("Por favor ingrese correctamente el Correo Electrónico de la Organización.\nEl Correo Electrónico puede contener caracteres alfanuméricos(A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
		document.frm1.orgEmail.focus();
		return;

}
if (esVacio(document.frm1.repApePat.value) || !contieneCarateresValidos(document.frm1.repApePat.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Apellido Paterno del Representante");
		document.frm1.repApePat.focus();
		return;
	}	

if (!esVacio(document.frm1.repApeMat.value))
	{
	  if (esEspacio(document.frm1.repApeMat.value) || !contieneCarateresValidos(document.frm1.repApeMat.value,"nombre") )
		 {	
			alert("Por favor ingrese correctamente el Apellido Materno del Representante");
			document.frm1.repApeMat.focus();
			return;
		 }
	}	
if (esVacio(document.frm1.repNombre.value) || !contieneCarateresValidos(document.frm1.repNombre.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Nombre del Representante");
		document.frm1.repNombre.focus();
		return;
	}
if (esVacio(document.frm1.repNumeroDocumento.value) || !esEntero(document.frm1.repNumeroDocumento.value) || !esMayor(document.frm1.repNumeroDocumento.value,8) || !esEnteroMayor(document.frm1.repNumeroDocumento.value,1))
	{	
		alert("Por favor ingrese correctamente el Número del Documento del Representante.\nEl Número de Documento requiere al menos 8 caracteres numéricos (0-9) ");
		document.frm1.repNumeroDocumento.focus();
		return;
	}	
if (esVacio(document.frm1.admApePat.value) || !contieneCarateresValidos(document.frm1.admApePat.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Apellido Paterno del Administrador");
		document.frm1.admApePat.focus();
		return;
	}	

if (!esVacio(document.frm1.admApeMat.value)  && !contieneCarateresValidos(document.frm1.admApeMat.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Apellido Materno del Administrador");
		document.frm1.admApeMat.focus();
		return;
	}	

if (esVacio(document.frm1.admNombre.value) || !contieneCarateresValidos(document.frm1.admNombre.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Nombre del Administrador");
		document.frm1.admNombre.focus();
		return;
	}


if (esVacio(document.frm1.admEmail.value) || !esEmail(document.frm1.admEmail.value) || !contieneCarateresValidos(document.frm1.admEmail.value,"correo"))
	{	
		alert("Por favor ingrese correctamente el Correo Electrónico del Administrador.\nEl Correo Electrónico puede contener caracteres alfanuméricos (A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
		document.frm1.admEmail.focus();
		return;
	}

if (esVacio(document.frm1.admNumeroDocumento.value) || !esEntero(document.frm1.admNumeroDocumento.value) || !esMayor(document.frm1.admNumeroDocumento.value,8) || !esEnteroMayor(document.frm1.admNumeroDocumento.value,1))
	{	
		alert("Por favor ingrese correctamente el Número del Documento del Administrador.\nEl Número de Documento requiere al menos 8 caracteres numéricos (0-9) ");
		document.frm1.admNumeroDocumento.focus();
		return;
	}	
//29oct validar distrito solamente si pais es peru
if (document.frm1.admPais.value=="01")
{
if (esVacio(document.frm1.admDistrito.value) || !contieneCarateresValidos(document.frm1.admDistrito.value,"numeronombre"))
	{	
		alert("Por favor ingrese correctamente el Distrito del Administrador");
		document.frm1.admDistrito.focus();
		return;
	}	
}

if (esVacio(document.frm1.admVia.value) )
	{	
		alert("Por favor ingrese correcatmente la Dirección del Administrador");
		document.frm1.admVia.focus();
		return;
	}	
	
		
if(!esVacio(document.frm1.admTelefono.value)){	
	if(!contieneCarateresValidos(document.frm1.admTelefono.value,"telefono") || !esMayor(document.frm1.admTelefono.value,6))	
	{
			alert("Por favor ingrese correctamente el Número de Teléfono del Administrador.\nEl Número de Teléfono debe contener mas de 6 caracteres .\nEl Número de Teléfono puede contener caracteres numéricos(0123456789), espacios( ) y guiones(-).");
			document.frm1.admTelefono.focus();
			return;
	
	}
}

if(!esVacio(document.frm1.admAnexo.value)){	
	if(!contieneCarateresValidos(document.frm1.admAnexo.value,"telefono"))	
	{
			alert("Por favor ingrese correctamente el Número de Anexo del Administrador.\nEl Número de Anexo puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.admAnexo.focus();
			return;
	}
}

if(!esVacio(document.frm1.admFax.value)){	
	if(!contieneCarateresValidos(document.frm1.admFax.value,"telefono") || !esMayor(document.frm1.admFax.value,6))	
	{
			alert("Por favor ingrese correctamente el Número de Fax del Administrador.\nEl Número de Fax debe contener mas de 6 caracteres.\nEl Número de Fax puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.admFax.focus();
			return;
	
	}
}
//campo usuario	
if (!esLongitudEntre(document.frm1.admPrefijo.value,6,10) || !contieneCarateresValidos(document.frm1.admPrefijo.value,"usuariopassword") || esEntero(extraeDer(document.frm1.admPrefijo.value,3)))
	{	
		alert("Por favor ingrese correctamente el Prefijo.\nEl Prefijo debe contener entre 6 y 10 caracteres.\nLos 3 últimos caracteres del Prefijo no deben ser numéricos (0-9)");
		document.frm1.admPrefijo.focus();
		return;		
	}
//valida password
if(esVacio(document.frm1.password.value) || !contieneCarateresValidos(document.frm1.password.value,"usuariopassword") || !esLongitudEntre(document.frm1.password.value,6,10) || !contieneNumero(document.frm1.password))
{
		alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico (0-9)");
		document.frm1.password.focus();
		return;		
}

if (document.frm1.password.value.toUpperCase() == document.frm1.admPrefijo.value.toUpperCase())
	{	
		alert("La Contraseña no puede ser igual al campo Prefijo");
		document.frm1.password.focus();
		return;		
	}
if (document.frm1.admConfirmaPassword.value.toUpperCase() != document.frm1.password.value.toUpperCase())
	{	
		alert("La Contraseña no esta verificada");
		document.frm1.admConfirmaPassword.focus();
		return;		
	}
		
//validar respuesta secreta
var res = document.frm1.admRespuesta.value;
if (!esLongitudEntre(document.frm1.admRespuesta.value,5,30) || !contieneCarateresValidos(document.frm1.admRespuesta.value,"numeronombrebas"))
	{	
		alert("Por favor ingrese correctamente la Respuesta Secreta.\nLa Respuesta Secreta debe contener entre 5 y 30 caracteres");
		document.frm1.admRespuesta.focus();
		return;		
	}
if (document.frm1.admRespuesta.value.toUpperCase() == document.frm1.admPrefijo.value.toUpperCase())
	{	
		alert("La Respuesta Secreta no puede ser igual al campo Prefijo");
		document.frm1.admRespuesta.focus();
		return;		
	}
			
	//submit
	
	obj_mayuscula(document.frm1.orgRazon);
	obj_mayuscula(document.frm1.orgDistrito);	
	obj_mayuscula(document.frm1.orgVia);
		
	obj_mayuscula(document.frm1.repApePat);
	obj_mayuscula(document.frm1.repApeMat);	
	obj_mayuscula(document.frm1.repNombre);
	
	obj_mayuscula(document.frm1.admApePat);
	obj_mayuscula(document.frm1.admApeMat);	
	obj_mayuscula(document.frm1.admNombre);
	obj_mayuscula(document.frm1.admDistrito);
	obj_mayuscula(document.frm1.admVia);

	obj_mayuscula(document.frm1.admPrefijo);
	obj_mayuscula(document.frm1.password);
	obj_mayuscula(document.frm1.admConfirmaPassword);
	obj_mayuscula(document.frm1.admRespuesta);
	
	document.frm1.action="/iri/Afiliacion.do?state=mostrarContratoOrg";	
	document.frm1.submit();
}

</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>


<body>


<table class="titulo">
  <tr>
    <td>Afiliaci&oacute;n de Organizaci&oacute;n (Paso 1 de 3)</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
  <table border="0" class="tablasinestilo">
    <tr>
    <th colspan="4" bgcolor="#990000">DATOS DE LA ORGANIZACI&Oacute;N</th>
  </tr>
  
  <tr>
    <td valign="middle" width="114">RAZ&Oacute;N SOCIAL</td>  
    <td valign="middle" width="187">
		<input type="text" name="orgRazon" size="30" maxlength="100" style="width:185" onblur="solonumlet(this)">
    </td>
    <td valign="middle" width="150">RUC</td>
    <td valign="middle" width="149">
		<input type="text" name="orgRuc" size="11" maxlength="11" style="width:133" onblur="solonum(this)">
    </td>
  </tr>
  
  <tr>
    <td valign="middle" width="114">GIRO </td>
    <td valign="middle" width="187" colspan="3">
		<select name="orgGiro">
			<logic:iterate name="arr0" id="item0" scope="request">
				<option value="<bean:write name="item0" property="codigo"/>" ><bean:write name="item0" property="descripcion"/></option>
			</logic:iterate>
      </select>
    </td>
  </tr>

  <tr>
    <td valign="middle" width="114">PA&Iacute;S</td>
    <td valign="middle" width="187">
	  <select  name="orgPais" onchange=llenaDepProv(); width="187" style="width:187">
			<logic:iterate name="arr1" id="item1" scope="request">
					<logic:equal name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>" selected> <bean:write name="item1" property="descripcion"/> </option>
					</logic:equal>
					<logic:notEqual name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
					</logic:notEqual>
			</logic:iterate>
      </select>
    </td>
    <td valign="middle" width="145"></td>
    <td valign="middle" >
    	
    </td>
  </tr>
  
  <tr>
    <td valign="middle" width="114">DPTO</td>
    <td valign="middle" width="187">
  	  <select name="orgDpto" onchange=llenaComboHijo(1); width="187" style="width:187">
			<logic:iterate name="arr3" id="item3" scope="request">
				<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
			</logic:iterate>
      </select>
    </td>
    <td  valign="middle" width="145">OTRO</td>
    <td valign="middle" ><input type="text" name="orgLug" size="11" maxlength=30 style="width:133" onblur="solonumlet(this)" disabled="true">
   	</td>
  </tr>
  
  <tr>
    <td valign="middle" width="114">PROVINCIA</td>
    <td valign="middle" width="187">
  	  <select name="orgProvincia" width="187" style="width:187">
      </select>
    </td>
    <td  valign="middle" width="145"></td>
    <td valign="middle" >
   	</td>
  </tr>
  
  <tr>
    <td valign="middle" width="114">DISTRITO</td>
    <td valign="middle" width="187">
    	<input type="text" name="orgDistrito" size="11" maxlength=40 style="width:133" onblur="solonumlet(this)">
    </td>
    <td valign="middle" width="145">DIRECCI&Oacute;N</td>
    <td valign="middle" >
    	<input type="text" name="orgVia" size="11" maxlength=40 style="width:133" onblur="solonumlet(this)">
    </td>
  </tr>
  
  
  <tr>
    <td valign="middle" width="114">TEL&Eacute;FONO</td>
    <td valign="middle" width="187">
    	<input type="text" name="orgTelefono" size="11" maxlength=32 style="width:133" onblur="solonum(this)">
    </td>
    <td height="3" valign="middle" width="150">FAX</td>
    <td height="3" valign="middle" >
    	<input type="text" name="orgFax" size="11" maxlength=32 style="width:133" onblur="solonum(this)" >
    </td>
  </tr>
  
  
  <tr>
    <td valign="middle" width="114">CORREO ELECTR&Oacute;NICO</td>
    <td valign="middle" colspan="3">
    	<input type="text" name="orgEmail" size="11" maxlength=40 style="width:133">
    </td>
  </tr>
  
<!-- *******************************************************************************  -->  
  <tr>
    <th colspan="4" bgcolor="#990000" valign="middle" width="100%">
      DATOS DEL REPRESENTANTE
    </th>
  </tr>

  <tr>
    <td valign="middle" width="114">APELLIDO PATERNO</td>
    <td valign="middle" width="187">
      <input type="text" name="repApePat" size="11" maxlength="30" style="width:133" onblur="sololet(this)">
    </td>
    <td valign="middle" width="145">
      APELLIDO MATERNO
    </td>
    <td valign="middle" >
    	<input type="text" name="repApeMat" size="11" maxlength="30" style="width:133" onblur="sololet(this)">
    </td>
  </tr>
  
  
  <tr>
    <td valign="middle" width="114">NOMBRES</td>
    <td valign="middle"  colspan="3">
      <input type="text" name="repNombre" size="11" maxlength="40" style="width:133" onblur="sololet(this)">
    </td>
  </tr>
  
  
  <tr>
    <td valign="middle" width="114">TIPO DOCUMENTO</td>
    <td valign="middle" width="187">
      <select  name="repTipoDocumento">
			<logic:iterate name="arr4" id="item4" scope="request">
				<option value="<bean:write name="item4" property="codigo"/>" ><bean:write name="item4" property="descripcion"/></option>
			</logic:iterate>
      </select>
    </td>
    <td height="10" valign="middle" width="150">N&Uacute;MERO DOCUMENTO
    </td>
    <td height="10" valign="middle" >
    	<input type="text" name="repNumeroDocumento" size="11" maxlength="15" style="width:80" onblur="solonum(this)" ></td>
  </tr>
  
  
<!-- ***************************************************************************** -->  

  <tr>
    <th colspan="4" bgcolor="#990000" height="3" valign="middle" width="100%">
      DATOS DEL ADMINISTRADOR
    </th>
  </tr>
  
  <tr>
    <td height="10" valign="middle" width="114">APELLIDO PATERNO</td>
    <td height="10" valign="middle" width="187">
		<input type="text" name="admApePat" size="11" maxlength="11" style="width:133"" onblur="sololet(this)" >
    </td>
    <td height="10" valign="middle" width="145">APELLIDO MATERNO</td>
    <td height="10" valign="middle" >
    	<input type="text" name="admApeMat" size="11" maxlength="11" style="width:133" onblur="sololet(this)" >
    </td>
  </tr>
  
  
  <tr>
    <td height="10" valign="middle" width="114">NOMBRES</td>
    <td height="10" valign="middle" width="187">
      <input type="text" name="admNombre" size="11" maxlength="40" style="width:133" onblur="sololet(this)" >
    </td>
    <td height="10" valign="middle" width="145">CORREO ELECTR&Oacute;NICO</td>
    <td height="10" valign="middle" >
    	<input type="text" name="admEmail" size="11" maxlength=40 style="width:133" >
    </td>
  </tr>
  <tr>
    <td height="10" valign="middle" width="114">TIPO DOCUMENTO</td>
    <td height="10" valign="middle" width="187">
      <select name="admTipoDocumento">
			<logic:iterate name="arr4" id="item4" scope="request">
				<option value="<bean:write name="item4" property="codigo"/>" ><bean:write name="item4" property="descripcion"/></option>
			</logic:iterate>
      </select>
    </td>
    <td height="10" valign="middle" width="145">N&Uacute;MERO DOCUMENTO</td>
    <td height="10" valign="middle" >
    	<input type="text" name="admNumeroDocumento" size="11" maxlength="15" style="width:80" onblur="solonum(this)" >
    </td>
  </tr>
  
  <tr>
    <td  width="114" height=18>PA&Iacute;S</TD>
    <td width="187" height=18>
		<SELECT name="admPais" onchange="llenaDepProvAdm()" width="187" style="width:187"> 
			<logic:iterate name="arr1" id="item1" scope="request">
					<logic:equal name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>" selected> <bean:write name="item1" property="descripcion"/> </option>
					</logic:equal>
					<logic:notEqual name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
					</logic:notEqual>
			</logic:iterate>
		</SELECT>
	</TD>
    <td vAlign=top width="145" height=19></TD>
    <td vAlign=top  height=19>
	</TD>
  </TR>
  <tr>
    <td  width="114" height=18>DEPARTAMENTO</TD>
    <td width="187" height=18>
		<SELECT name="admDpto" onchange="llenaComboHijo(2);" width="187" style="width:187"> 
			<logic:iterate name="arr3" id="item3" scope="request">
				<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
			</logic:iterate>
		</SELECT>
	</TD>
    <td vAlign=top width="145" height=19>OTRO</TD>
    <td vAlign=top height=19>
	<INPUT size="11" maxlength="30" style="width:133" name="admOtro" onblur="solonumlet(this)"  disabled="true">
	</TD>
  </TR>

  <TR>
    <td  width="114" height=18>PROVINCIA</TD>
    <td width="187" height=18>
	<select name="admProvincia" width="187" style="width:187">
 	 </select>
	 </TD>
    <td vAlign=top width="145" height=19>DISTRITO</TD>
    <td vAlign=top  height=19><INPUT name="admDistrito" size="11" maxlength=40 style="width:133" onblur="solonumlet(this)" ></TD>
  </TR>

  <TR>
	<TD width="114" height=18>DIRECCI&Oacute;N</TD>
	<TD width="187" height=18>
	<INPUT name="admVia" size="11" maxlength=40 style="width:133" onblur="solonumlet(this)">
	</TD>
    <td vAlign=top width="145" height=19>COD POSTAL</TD>
    <td vAlign=top  height=19><INPUT size=11 name="admCodPostal" maxlength="12" style="width:133" onblur="solonum(this)"></TD>
  </TR> 

  <tr>
    <td height="10" valign="middle" width="114">TEL&Eacute;FONO</td>
    <td height="10" valign="middle" width="187"><input type="text" name="admTelefono" size="11" maxlength=30 style="width:133" onblur="solonum(this)" ></td>
    <td height="10" valign="middle" width="145">ANEXO</td>
    <td height="10" valign="middle" ><input type="text" name="admAnexo" size="3" maxlength=10 style="width:133" onblur="solonum(this)" ></td>
  </tr>

  <tr>
    <td height="10" valign="middle" width="145">FAX</td>
    <td height="10" valign="middle" ><input type="text" name="admFax" size="11" maxlength=30 style="width:133" onblur="solonum(this)" ></td>
    <td></td>
    <td></td>
  </tr>  

  <tr>
    <td height="10" valign="middle" width="114">PREFIJO CUENTA</td>
    <td height="10" valign="middle" width="187">
      <input type="text" name="admPrefijo" size="11" maxlength=10 style="width:133"  onblur="solonumlet(this)" >
    </td>
    <td height="10" valign="middle" colspan=2>El prefijo cuenta se utiliza para generar los nombres de usuario de su organizacion</td>
  </tr>

  <tr>
    <td height="10" valign="middle" width="114">CONTRASE&Ntilde;A</td>
    <td height="10" valign="middle" width="187"><input type="password" name="password" size="11" maxlength="10" style="width:90"  onblur="solonumlet(this)">
    </td>
    <td height="10" valign="middle" width="145">CONFIRMA CONTRASE&Ntilde;A</td>
    <td height="10" valign="middle" >
    	<input type="password" name="admConfirmaPassword" size="11" maxlength="10" style="width:90" onblur="solonumlet(this)"  >
    </td>
  </tr>

  <tr>
    <td height="10" valign="middle" width="114">PREGUNTA SECRETA</td>
    <td height="10" valign="middle" width="187">
      <select  name="admTipoPregunta" width="187" style="width:187">
			<logic:iterate name="arr5" id="item5" scope="request">
				<option value="<bean:write name="item5" property="codigo"/>" ><bean:write name="item5" property="descripcion"/></option>
			</logic:iterate>
      </select>
    </td>
    <td height="10" valign="middle" width="145">RESPUESTA</td>
    <td height="10" valign="middle" >
    	<input type="text" name="admRespuesta" size="11" size="20" maxlength=30 style="width:133" onblur="solonumlet(this)" >
    </td>
  </tr>    
 <tr>
 	<td height="19" valign="middle" align=right colspan="2"></td>
    <td height="19" valign="middle" align=left colspan="2"><input type="checkbox" name="admQuiero" value="ON"> Quiero recibir mensajes en mi correo</td>
  </tr>
  <tr>
    <td width="100%" height="19" colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td width="100%" height="19" align="center" valign="middle" colspan="4">
	  <A href="javascript:doAcepta()"><IMG src="images\btn_continuar.gif" style="border:0" onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
 	  <A href="javascript:doCancela();"><IMG src="images\btn_cancelar.gif" style="border:0" onmouseover="javascript:mensaje_status('Cancelar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
	</td>
  </tr>

</table>

</form>

<script LANGUAGE="JavaScript">
	llenaComboHijo(1);	
	llenaComboHijo(2);	
</script>

<%-- llenar pantalla con datos anteriores --%>
<% 
DatosOrganizacionBean bean1 = (DatosOrganizacionBean) request.getAttribute("DATOS_FORMULARIO");
if (bean1!=null)
{
%>
<script LANGUAGE="JavaScript">
	document.frm1.orgRazon.value           = "<%=bean1.getOrgRazon()%>";
	document.frm1.orgRuc.value             = "<%=bean1.getOrgRuc()%>";
	
	document.frm1.orgLug.value             = "<%=bean1.getOrgLug()%>";	
	document.frm1.orgDistrito.value        = "<%=bean1.getOrgDistrito()%>";
	document.frm1.orgVia.value             = "<%=bean1.getOrgVia()%>";
	document.frm1.orgTelefono.value        = "<%=bean1.getOrgTelefono()%>";
	document.frm1.orgFax.value             = "<%=bean1.getOrgFax()%>";
	document.frm1.orgEmail.value           = "<%=bean1.getOrgEmail()%>";
	
	document.frm1.repApePat.value          = "<%=bean1.getRepApePat()%>";
	document.frm1.repApeMat.value          = "<%=bean1.getRepApeMat()%>";
	document.frm1.repNombre.value          = "<%=bean1.getRepNombre()%>";
	document.frm1.repNumeroDocumento.value = "<%=bean1.getRepNumeroDocumento()%>";
	
	document.frm1.admPrefijo.value         = "<%=bean1.getAdmPrefijo()%>";
	document.frm1.admApePat.value          = "<%=bean1.getAdmApePat()%>";
	document.frm1.admApeMat.value          = "<%=bean1.getAdmApeMat()%>";
	document.frm1.admNombre.value          = "<%=bean1.getAdmNombre()%>";
	document.frm1.admEmail.value           = "<%=bean1.getAdmEmail()%>";
	document.frm1.admNumeroDocumento.value = "<%=bean1.getAdmNumeroDocumento()%>";
	document.frm1.admOtro.value            = "<%=bean1.getAdmOtro()%>";
	
	document.frm1.admDistrito.value        = "<%=bean1.getAdmDistrito()%>";
	document.frm1.admVia.value             = "<%=bean1.getAdmVia()%>";
	document.frm1.admCodPostal.value       = "<%=bean1.getAdmCodPostal()%>";
	document.frm1.admTelefono.value        = "<%=bean1.getAdmTelefono()%>";
	document.frm1.admAnexo.value           = "<%=bean1.getAdmAnexo()%>";
	document.frm1.admFax.value             = "<%=bean1.getAdmFax()%>";
	document.frm1.admTipoPregunta.value    = "<%=bean1.getAdmTipoPregunta()%>";
	document.frm1.admRespuesta.value       = "<%=bean1.getAdmRespuesta()%>";
	
	doCambiaCombo(document.frm1.orgGiro, "<%=bean1.getOrgGiro()%>");
	
	doCambiaCombo(document.frm1.orgPais, "<%=bean1.getOrgPais()%>");
	llenaDepProv();
	
	doCambiaCombo(document.frm1.orgDpto, "<%=bean1.getOrgDpto()%>");
	llenaComboHijo(1);

	doCambiaCombo(document.frm1.orgProvincia, "<%=bean1.getOrgProvincia()%>");

	doCambiaCombo(document.frm1.repTipoDocumento, "<%=bean1.getRepTipoDocumento()%>");
	doCambiaCombo(document.frm1.admTipoDocumento, "<%=bean1.getAdmTipoDocumento()%>");

	doCambiaCombo(document.frm1.admPais, "<%=bean1.getAdmPais()%>");
	llenaDepProvAdm();
	
	doCambiaCombo(document.frm1.admDpto, "<%=bean1.getAdmDpto()%>");
	llenaComboHijo(2);
				
	doCambiaCombo(document.frm1.admProvincia, "<%=bean1.getAdmProvincia()%>");
	
	<%if (bean1.getAdmQuiero()==true) {%>
		document.frm1.admQuiero.checked = "true";
	<%  } %>			
</script>
<% } %>

<%-- mostrar mensaje de error de validacion, si hubiera --%>
<%
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
%>
	<script LANGUAGE="JavaScript">
		alert("<%=validacionException.getMensaje()%>");
		<% 
		String foco = validacionException.getFocus();
		if (foco.trim().length() > 0) {%>
			document.frm1.<%=foco%>.focus();
		<% } %>
	</script>
<% } %>

</body>
</html>