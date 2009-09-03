<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%-- PANTALLA PARA el link "Editar mis datos"

     VALIDO PARA PERFILES:
            AO
            AJ
			AG
			
	ESTA PANTALLA ES SIMILAR A /administracion/formularioDatosOrg.jsp
--%>
            
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>

<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>

<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<%
DatosOrganizacionBean datosOrganizacionBean = (DatosOrganizacionBean) request.getAttribute("DATOS_FORMULARIO");
%>

<script language="javascript">

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

function doCancelar()
{
	history.back(1);
}

function doAceptar()
{
//validaciones de campos:
	if (esVacio(document.frm1.distritoOrganizacion.value) || !contieneCarateresValidos(document.frm1.distritoOrganizacion.value,"numeronombre"))
		{	
			alert("Por favor ingrese correctamente el Distrito de la Organizacion");
			document.frm1.distritoOrganizacion.focus();
			return;
		}		
	if (esVacio(document.frm1.direccionOrganizacion.value))
		{	
			alert("Por favor ingrese correctamente la Direccion de la Organizacion");
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
			alert("Por favor ingrese correctamente el Numero del Documento del Representante\nEl Numero de Documento debe contener al menos 8 digitos numericos (0-9)");
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
			alert("Por favor ingrese correctamente el Correo Electronico del Administrador.\nEl Correo Electronico puede contener caracteres alfanumericos(A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
			document.frm1.emailAdministrador.focus();
			return;
		}
	
	if (esVacio(document.frm1.numeroDocumentoAdministrador.value) || !esEntero(document.frm1.numeroDocumentoAdministrador.value) || !esMayor(document.frm1.numeroDocumentoAdministrador.value,8) || !esEnteroMayor(document.frm1.numeroDocumentoAdministrador.value,1))
		{	
			alert("Por favor ingrese correctamente Numero del Documento del Administrador.\nEL Numero de Documento debe contener al menos 8 digitos numericos (0-9)");
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
			alert("Por favor ingrese correctamente la Direccion del Administrador");
			document.frm1.direccionAdministrador.focus();
			return;
		}


<%//=Tarea.validaJS("direccionOrganizacion","Av/Calle/Jr y Nro",0,true,false)%>
<%//=Tarea.validaJS("apellidoPaternoRepresentante","Apellido Paterno del representante",2,true,true)%>
<%//=Tarea.validaJS("apellidoMaternoRepresentante","Apellido Materno del representante",2,false,true)%>
<%//=Tarea.validaJS("nombresRepresentante","Nombre del representante",2,true,true)%>
<%//=Tarea.validaJS("numeroDocumentoRepresentante","Numero documento del representante",0,true,true)%>

<%//=Tarea.validaJS("apellidoPaternoAdministrador","Apellido Paterno del Administrador",2,true,true)%>
<%//=Tarea.validaJS("apellidoMaternoAdministrador","Apellido Materno del Administrador",2,false,true)%>
<%//=Tarea.validaJS("nombresAdministrador","Nombre del Administrador",2,true,true)%>
<%//=Tarea.validaJS("emailAdministrador","Correo electronico del Administrador",0,true,false)%>
<%//=Tarea.validaJS("numeroDocumentoAdministrador","Numero documento del Administrador",0,true,true)%>
<%//=Tarea.validaJS("direccionAdministrador","Direccion del Administrador",0,true,false)%>

	if (document.frm1.contrasena1.value.lenght>0)
	{
		if (document.frm1.contrasena1.value != document.frm1.contrasena2.value)
			{
			alert("Nueva contrasena no confirmada");
			document.frm1.confirmacionClave.value="";
			document.frm1.clave.focus;
			return;
			}
	}

	var vc = esEmail(document.frm1.emailAdministrador.value);
	if (vc == false)
		{
			alert("El correo electronico es invalido");
			document.frm1.emailAdministrador.focus();
			return;
		}
	
	
	//valida contrasena solamente si hay data en el campo
	/*if (document.frm1.contrasena1.value.length > 0 ||
		document.frm1.contrasena2.value.length > 0 ||
		document.frm1.contrasena3.value.length > 0 ||
		document.frm1.respuestaSecreta.value.length > 0)
	{	
		if (document.frm1.contrasena1.value.length <= 0){
				alert("Debe ingresar valor en Contraseña Actual");
				document.frm1.contrasena1.focus();
				return;
		}
		if (document.frm1.contrasena2.value.length <= 0){
				alert("Debe ingresar valor en Nueva Contraseña");
				document.frm1.contrasena2.focus();
				return;
		}
		if (document.frm1.contrasena3.value.length <= 0){
				alert("Debe ingresar valor en Confirmación Contraseña");
				document.frm1.contrasena3.focus();
				return;
		}
		if (document.frm1.respuestaSecreta.value.length <= 0){
				alert("Debe ingresar valor en Respuesta");
				document.frm1.respuestaSecreta.focus();
				return;
		}
		if (document.frm1.contrasena2.value != document.frm1.contrasena3.value){
				alert("No coinciden la nueva contraseña y su confirmación");
				document.frm1.contrasena2.focus();
				return;
		}
	}
	*/	
	document.frm1.action = "/iri/EditarDatosPersonales.do?state=actualizaDatosOrganizacion";
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
function baja(){

	document.frm1.action = "/iri/Baja.do?state=elegirZona";
	document.frm1.submit();
}
</script>

<body>
<br>
<table cellspacing=0 class=titulo>
  <tr>
	<td>
		<FONT COLOR=black>ADMINISTRACI&Oacute;N &nbsp; &gt;&gt; Organizaciones&gt;&gt; </FONT>Datos de la Organizaci&oacute;n
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
<table class=tablasinestilo>
<tr><th colspan=4>DATOS DE LA ORGANIZACI&Oacute;N</th></tr>
  <tr>
    <td width="114">RAZ&Oacute;N SOCIAL</td>
    <td colspan="3"><input type="text" name="razonSocial" size="20" maxlength="100" style="width:133" disabled=true onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">RUC</td>
    <td width="187"><input type="text" name="ruc"  size="11" maxlength="11" disabled=true onblur="solonum(this)" style="width:133"></td>
    <td width="140">GIRO</td>
    <td>
      <select size="1" name="giroNegocio" style="width:140">
       <logic:iterate name="arrGiros" id="giro" scope="request">
        <option value="<bean:write name="giro" property="codigo"/>"><bean:write name="giro" property="descripcion"/></option>
       </logic:iterate>
  
  		
       
  
      </select>  
    </td>
  </tr>
  <tr>
    <td width="114">PA&Iacute;S</td>
    <td width="187">
		<select size="1" name="paisOrganizacion" onChange="CambioPaisOrganizacion(this.selectedIndex);" style="width:187">
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
    <td width="140"></td>
    <td></td>
  </tr>
  <tr>
    <td width="114">DEPARTAMENTO</td>
    <td width="187">
		<SELECT  name="departamentoOrganizacion" onchange=llenaComboHijo(); style="width:187">
                <logic:iterate name="arrDepartamentos" id="dpto" scope="request">
                <option value="<bean:write name="dpto" property="codigo"/>"><bean:write name="dpto" property="descripcion"/></option>
                </logic:iterate>
        </SELECT>    </td>
    <td width="140">OTRO:</td>
    <td><input type="text" name="otroDepartamentoOrganizacion" size="11" style="width:133" disabled="true" onblur="sololet(this)"></td>
  </tr>

  <tr>
    <td width="114">PROVINCIA</td>
    <td width="187"><SELECT  name="provinciaOrganizacion" style="width:187">
         </SELECT>
    </td>
    <td width="140">DISTRITO</td>
    <td ><input type="text" name="distritoOrganizacion" size="11" maxlength="40" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">AV/CALLE/JR Y NRO</td>
    <td width="187"><input type="text" name="direccionOrganizacion" size="11" maxlength="40" style="width:133" onblur="sololet(this)"></td>
    <td width="140">COD POSTAL</td>
    <td><input type="text" name="codPostalOrganizacion" size="11" maxlength="12" style="width:133" onblur="sololet(this)"></td>
  </tr>

<tr><th colspan=4>DATOS DEL REPRESENTANTE</th></tr>
  <tr>
    <td width="114">APELLIDO&nbsp;PATERNO</td>
    <td width="187"><input type="text" name="apellidoPaternoRepresentante" size="11" style="width:133" maxlength="30" onblur="sololet(this)"></td>
    <td width="140">APELLIDO MATERNO</td>
    <td><input type="text" name="apellidoMaternoRepresentante"  size="11" style="width:133" maxlength="30" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">NOMBRES</td>
    <td width="187"><input type="text" name="nombresRepresentante" size="11" maxlength="40" style="width:133" onblur="sololet(this)"></td>
    <td width="140"></td>
    <td></td>
  </tr>
  <tr>
    <td width="114">TIPO DOCUMENTO</td>
    <td width="187"><select size="1" name="tipoDocumentoRepresentante" style="width:187">
        <logic:iterate name="arrTiposDocumento" id="tipDoc" scope="request">
            	<option value="<bean:write name="tipDoc" property="codigo"/>"><bean:write name="tipDoc" property="descripcion"/></option>
        </logic:iterate> 
            </select>
    </td>
    <td width="140">N&Uacute;MERO DOCUMENTO</td>
    <td><input type="text" name="numeroDocumentoRepresentante" size="11" maxlength="15" style="width:133"></td>
  </tr>

<tr><th colspan=4>DATOS DEL ADMINISTRADOR</th></tr>
  <tr>
    <td width="114">APELLIDO PATERNO</td>
    <td width="187"><input type="text" name="apellidoPaternoAdministrador" size="11" maxlength="30" style="width:133" onblur="sololet(this)"></td>
    <td width="140">APELLIDO MATERNO</td>
    <td><input type="text" name="apellidoMaternoAdministrador" size="11" maxlength="30" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114">NOMBRES</td>
    <td width="187"><input type="text" name="nombresAdministrador" size="11" maxlength="40" style="width:133" onblur="sololet(this)"></td>
    <td width="140">CORREO ELECTR&Oacute;NICO</td>
    <td><INPUT type="text" name="emailAdministrador"  size="11" maxlength="40" style="width:133"></td>
  </tr>
  <tr>
    <td width="114">TIPO DOCUMENTO</td>
    <td width="187"><select size="1" name="tipoDocumentoAdministrador" style="width:187">
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
    <td width="187"><select size="1" name="paisAdministrador" onChange="CambioPaisAdministrador(this.selectedIndex);" style="width:187">
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
    <td width="140"></td>
    <td>
    </td>
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
    <td><input type="text" name="otroDepartamentoAdministrador" size="11" maxlength="30" disabled="true" style="width:133">
    </td>
  </tr>

  <tr>
    <td width="114">PROVINCIA</td>
    <td width="187"><SELECT  name="provinciaAdministrador" style="width:187"></SELECT></td>
    <td width="140">DISTRITO</td>
    <td><input type="text" name="distritoAdministrador" size="11" maxlength="40" onblur="sololet(this)" ></td>
  </tr>
  <tr>
    <td width="114">AV/CALLE/JR Y NRO</td>
    <td width="187"><input type="text" name="direccionAdministrador" size="11" maxlength="40" onblur="sololet(this)" style="width:133"></td>
    <td width="140">COD POSTAL</td>
    <td><input type="text" name="codPostalAdministrador" size="5" maxlength="5" onblur="sololet(this)" style="width:133"></td>
  </tr>

  <tr>
    <td width="114">TELEFONO</td>
    <td width="187"><input type="text" name="telefonoAdministrador"  size="11" maxlength="10" style="width:133"></td>
    <td width="140">ANEXO</td>
    <td ><input type="text" name="anexoAdministrador" size="12" maxlength="10" style="width:133"></td>
  </tr>
  <tr>
    <td width="140">FAX</td>
    <td ><input type="text" name="faxAdministrador" size="11" maxlength="20" style="width:133"></td>
    <td></td>
    <td></td>
  </tr>  
  <tr>
    <td width="114">PREFIJO CUENTA</td>
    <td width="187"><input type="text" name="prefijoCuenta"  size="11" maxlength="12" disabled=true style="width:133"></td>
    <td width="140"></td>
    <td></td>	    
  </tr>
  <tr>
    <td width="114">CONTRASE&Ntilde;A ACTUAL</td>
    <td width="187"><input type="password" name="contrasena1" size="11" onblur="solonumlet(this)" style="width:133"></td>
    <td colspan="2">Llene este campo si desea cambiar contrase&ntilde;a</td>    
  </tr>
  <tr>
    <td width="114">NUEVA CONTRASE&Ntilde;A</td>
    <td width="187"><input type="password" name="contrasena2" size="11" onblur="solonumlet(this)" style="width:133"></td>
    <td width="140">CONFIRMACI&Oacute;N CONTRASE&Ntilde;A</td>
    <td><input type="password" name="contrasena3" size="11" onblur="solonumlet(this)" style="width:133"></td>    
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
    <td>RESPUESTA</td>
    <td><input type="text" size="11" name="respuestaSecreta" style="width:133" onblur="solonumlet(this)" ></td>
  </tr>
 <tr><td  colspan="4" ><br><a class="linkrojoclaro" href="javascript:baja()">Presione aqu&iacute; si desea dar de baja la cuenta de nuestro servicio </a><br><br></td>
 </tr>

</table>
<table>
  <tr>     
  	<td colspan=4>
  	<hr>
  	</td>     
  </tr>  
  <tr>
    <td align="center" colspan=4>
      <A href="javascript:doAceptar()"><img border=0 src="images/btn_aceptar.gif"></a>      
      <A href="javascript:doCancelar()"><img border=0 src="images/btn_cancelar.gif"></a>
    </td>
  </tr>
</table>
  

<script LANGUAGE="JavaScript">
	llenaComboHijo();	
	llenaComboHijo2();
    document.frm1.paisOrganizacion.disabled ="true";
    document.frm1.paisAdministrador.disabled="true";	   

</script>

<script LANGUAGE="JavaScript">
   document.frm1.razonSocial.value			            ="<%=datosOrganizacionBean.getRazonSocial()%>";
   document.frm1.ruc.value				                ="<%=datosOrganizacionBean.getRuc()%>";
   doCambiaCombo(document.frm1.paisOrganizacion, "<%=datosOrganizacionBean.getPaisIdOrganizacion()%>");
   doCambiaCombo(document.frm1.departamentoOrganizacion, "<%=datosOrganizacionBean.getDepartamentoIdOrganizacion()%>");
   llenaComboHijo();
   doCambiaCombo(document.frm1.provinciaOrganizacion,"<%=datosOrganizacionBean.getProvinciaIdOrganizacion()%>");
   doCambiaCombo(document.frm1.giroNegocio,"<%=datosOrganizacionBean.getGiroNegocio()%>");
   document.frm1.distritoOrganizacion.value	            ="<%=datosOrganizacionBean.getDistritoOrganizacion()%>";
   document.frm1.direccionOrganizacion.value	        ="<%=datosOrganizacionBean.getDireccionOrganizacion()%>";
   document.frm1.codPostalOrganizacion.value	        ="<%=datosOrganizacionBean.getCodPostalOrganizacion()%>";
   
   doCambiaCombo(document.frm1.pregSecreta,"<%=datosOrganizacionBean.getPreguntaSecretaId()%>");

   document.frm1.respuestaSecreta.value		            ="<%=datosOrganizacionBean.getRespuestaSecreta()%>";
                                                        
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
   document.frm1.anexoAdministrador.value	        ="<%=datosOrganizacionBean.getAnexoAdministrador()%>";
   document.frm1.prefijoCuenta.value		            ="<%=datosOrganizacionBean.getPrefijoCuenta()%>";   
   document.frm1.faxAdministrador.value		            ="<%=datosOrganizacionBean.getFaxAdministrador()%>"; 


   	document.frm1.departamentoOrganizacion.disabled=true;
   	document.frm1.provinciaOrganizacion.disabled=true;   
   	
   <%if (datosOrganizacionBean.getPaisIdOrganizacion().equals("01")) {%>
	   	document.frm1.paisOrganizacion.disabled=true;
	   	document.frm1.otroDepartamentoOrganizacion.disabled=true;
   <% } else { %>
	   document.frm1.paisOrganizacion.disabled=false;
	   document.frm1.otroDepartamentoOrganizacion.disabled=false;
   <% } %>
</script>

<input type ="hidden" name="hid1" value="<%=datosOrganizacionBean.getOrganizacionPeJuriId()%>">


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
</body>
</html>