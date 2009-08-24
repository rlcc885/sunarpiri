<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%-- 
	PANTALLA del link "Editar mis datos"
	
	Para los perfiles: IE y AE

	ESTA PANTALLA ES SIMILAR A /administracion/formularioDatosUSR.jsp
--%>

<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>


<%
DatosUsuarioBean datosUsuarioBean = (DatosUsuarioBean) request.getAttribute("DATOS_FORMULARIO");
//DESCAJ 03/01/2007 IFIGUEROA
String strPerfilIdUsuario=datosUsuarioBean.getPerfilId();
long perfilIdUsuario;
try{
perfilIdUsuario= Integer.parseInt(strPerfilIdUsuario);
}catch(NumberFormatException e){
 perfilIdUsuario=0;
}
%>

<html>

<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<title></title>

<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>

<script language="javascript">
/*
arreglo hijo (provincia, que depende del combo padre DEPARTAMENTO)

	Id Provincia,  Descripci&oacute;n Provincia,  Id Departamento
*/

var arr2 = new Array();
<% int k = 0; %>
<logic:iterate name="arrProvincias" id="itemp" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="itemp" property="value01"/>"; //id provincia
	arrx[1]="<bean:write name="itemp" property="value02"/>"; //descripcion provincia
	arrx[2]="<bean:write name="itemp" property="value03"/>"; //id departamento
	arr2[<%=k%>]=arrx;
	<%  k++; %>
</logic:iterate>


function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}

function llenaComboHijo()
{
var obj1;
var obj2;
obj1 = document.frm1.departamento;  //papa
obj2 = document.frm1.provincia;  //hijo

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
		for(var i=0; i<obj2.options.length; ++i)
			{
				obj2.options[i]=null;
				--i;
			}
    }
    
//llenar combo hijo con informaci&oacute;n de acuerdo al Id de combo padre
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
	//doSendChildren();
	
	//validaciones
	if (esVacio(document.frm1.apellidoPaterno.value)  || !contieneCarateresValidos(document.frm1.apellidoPaterno.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Paterno");
			document.frm1.apellidoPaterno.focus();
			return;
		}	
	if (!esVacio(document.frm1.apellidoMaterno.value)  && !contieneCarateresValidos(document.frm1.apellidoMaterno.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Materno");
			document.frm1.apellidoMaterno.focus();
			return;
		}	
		
	if (esVacio(document.frm1.nombres.value) || !contieneCarateresValidos(document.frm1.nombres.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Nombre");
			document.frm1.nombres.focus();
			return;
		}	
	if (esVacio(document.frm1.numDoc.value))
		{	
			alert("Por favor ingrese correctamente el Numero del Documento.\nEl Numero del Documento requiere al menos 8 caracteres numericos (0-9)");
			document.frm1.numDoc.focus();
			return;
		}	
	
	if (!esEntero(document.frm1.numDoc.value) || !esMayor(document.frm1.numDoc.value,8) || !esEnteroMayor(document.frm1.numDoc.value,1))
		{	
			alert("Por favor ingrese correctamente el Numero del Documento.\nEl Numero del Documento requiere al menos 8 caracteres numericos (0-9)");
			document.frm1.numDoc.focus();
			return;
		}
		
		
	if(!esVacio(document.frm1.telefono.value) ){	
		if(!contieneCarateresValidos(document.frm1.telefono.value,"telefono") || !esMayor(document.frm1.telefono.value,6))	
		{
			alert("Por favor ingrese correctamente el Numero de Telefono.\nEl Numero de Telefono debe contener mas de 6 caracteres.\nEl Numero de Telefono puede contener caracteres numericos(0-9), espacios( ) y guiones(-).");
			document.frm1.telefono.focus();
			return;		
		}
	}

	if(!esVacio(document.frm1.anexo.value) ){	
		if(!contieneCarateresValidos(document.frm1.anexo.value,"telefono"))	
		{
			alert("Por favor ingrese correctamente el Numero de Anexo\nEl Numero de Anexo puede contener caracteres numericos(0-9), espacios( ) y guiones(-).");
			document.frm1.anexo.focus();
			return;		
		}
	}	
	
	if(!esVacio(document.frm1.fax.value)){	
		if(!contieneCarateresValidos(document.frm1.fax.value,"telefono") || !esMayor(document.frm1.fax.value,6))	
		{
			alert("Por favor ingrese correctamente el Numero de Fax.\nEl Numero de Fax debe contener mas de 6 caracteres.\nEl Numero de Fax puede contener caracteres numericos(0-9), espacios( ) y guiones(-).");
			document.frm1.fax.focus();
			return;
		}
	}	
	if (esVacio(document.frm1.distrito.value) || !contieneCarateresValidos(document.frm1.distrito.value,"numeronombre"))
		{	
			alert("Por favor ingrese correctamente el Distrito");
			document.frm1.distrito.focus();
			return;
		}	
	
	if (esVacio(document.frm1.direccion.value) )
		{	
			alert("Por favor ingrese correctamente la Direccion");
			document.frm1.direccion.focus();
			return;
		}	
	if (!esEmail(document.frm1.email.value) || !contieneCarateresValidos(document.frm1.email.value,"correo"))
		{	
			alert("Por favor ingrese correctamente el Correo Electronico.\nEl Correo Electronico puede contener caracteres alfanumericos(A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
			document.frm1.email.focus();
			return;
		}
	if (!esLongitudEntre(document.frm1.userId.value,6,13))
		{	
			alert("Por favor ingrese correctamente el usuario.\nEl usuario debe contener entre 6 y 13 caracteres.\nLos 3 ultimos caracteres del usuario no deben ser numericos (0-9)");
			document.frm1.userId.focus();
			return;		
		}	

	//valida contrasena solamente si hay data en el campo
//	if (document.frm1.contrasena1.value.length > 0)
//	{	
//		if (document.frm1.contrasena2.value != document.frm1.contrasena2.value)
//			{
//				alert("La contrasena no esta confirmada");
//				document.frm1.clave.focus();
//				return;
//			}
//	}

	//valida contrasena solamente si hay data en el campo
	if (document.frm1.contrasena1.value.length > 0 ||
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
		
		
		if (document.frm1.contrasena2.value != document.frm1.contrasena3.value){
				alert("No coinciden la nueva contraseña y su confirmación");
				document.frm1.contrasena2.focus();
				return;
		}
		if (document.frm1.contrasena2.value == document.frm1.contrasena1.value){
				alert("La nueva contraseña debe ser diferente a la contraseña actual");
				document.frm1.contrasena3.focus();
				return;
		}
		if (document.frm1.respuestaSecreta.value.length <= 0){
				alert("Debe ingresar valor en Respuesta");
				document.frm1.respuestaSecreta.focus();
				return;
		}
		<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
		   if(document.frm1.cboCaducidad.value == -1){
					alert("Debe seleccionar la caducidad de la contraseña");
					document.frm1.cboCaducidad.focus();
				return;
		}
		<%}%>
		
	}
	<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
		document.frm1.cboCaducidad.disabled=false;	
	<%}%>
	document.frm1.action = "/iri/EditarDatosPersonales.do?state=actualizaDatosPersona";
	document.frm1.submit();
}
function habilitaCaducidad(){
	<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
		if(document.frm1.contrasena1.value.length > 0)
			document.frm1.cboCaducidad.disabled=false;
		else
			document.frm1.cboCaducidad.disabled=true;
	<%}%>
}
function baja(){

	document.frm1.action = "/iri/Baja.do?state=elegirZona";
	document.frm1.submit();
}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table cellspacing=0 class=titulo>
<tr><td>DATOS PERSONALES &gt;&gt; Edici&oacute;n de datos Personales</td></tr>
</table>
<br>

<form name="frm1" method="POST" class="formulario">
<input type="hidden" value="<%=perfilIdUsuario%>" name="hidPerfilUsuario">
<table class=tablasinestilo>
<tr><th colspan=4>DATOS DEL USUARIO</th></tr>
<tr>
    <td width="114">APELLIDO PATERNO</td>
    <td width="187"><input type="text" name="apellidoPaterno" size="11" style="width:133" onblur="sololet(this)"></td>
    <td width="140">APELLIDO MATERNO</td>
    <td><input type="text" name="apellidoMaterno" size="11" style="width:133" onblur="sololet(this)"></td>
</tr>
  <tr>
    <td width="114">NOMBRES</td>
    <td width="187"><input type="text" name="nombres" size="11" style="width:133" onblur="sololet(this)"></td>
    <td width="140">&nbsp;</td>
    <td></td>
  </tr>
  <tr>
    <td width="114">TIPO DOCUMENTO</td>
    <td width="187">
		<select size="1" name="tipoDoc">
        <logic:iterate name="arrTiposDocumento" id="tipDoc" scope="request">
            <option value="<bean:write name="tipDoc" property="codigo"/>"><bean:write name="tipDoc" property="descripcion"/></option>
        </logic:iterate> 
      </select>
    </td>
    <td width="140">N&Uacute;MERO DOCUMENTO</td>
    <td><input type="text" name="numDoc" size="11" style="width:133" onblur="solonum(this)"></td>
  </tr>
  <tr>
    <td width="140">TELEFONO</td>
    <td><input type="text" name="telefono" size="11" style="width:133" onblur="solonum(this)"></td>
    <td width="114">ANEXO</td>
    <td width="187"><input type="text" name="anexo"  maxlenght="10" size="12" style="width:133" onblur="solonum(this)"></td>    
  </tr>
  <tr>
    <td width="114">FAX</td>
    <td width="187"><input type="text" name="fax" size="11" style="width:133" onblur="solonum(this)"></td>
    <td></td>
    <td></td>
  </tr>  
  <tr>
    <td width="114">PA&Iacute;S</td>
    <td width="187">
      <select size="1" name="pais" onChange="doValidaPais();" style="width:187">
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
    <td width="187">      <select name="departamento"  onchange=llenaComboHijo(); style="width:187">
       <logic:iterate name="arrDepartamentos" id="dpto" scope="request">
            <option value="<bean:write name="dpto" property="codigo"/>" selected><bean:write name="dpto" property="descripcion"/></option>
       </logic:iterate>
      </select>
    </td>
    <td width="140">OTRO</td>
    <td>
  	<input type="text" name="otroDepartamento" disabled="true" size="11" style="width:133" onblur="solonumlet(this)" >
    </td>
  </tr>

  <tr>
    <td width="114">PROVINCIA</td>
    <td width="187">
      <SELECT  name="provincia" style="width:187">
      </SELECT>
    </td>
    <td width="140">DISTRITO</td>
    <td><input type="text" name="distrito" size="11" style="width:133" onblur="solonumlet(this)">    </td>
  </tr>
  <tr>
    <td width="114">AV/CALLE/JR Y NRO</td>
    <td width="187"><input type="text" name="direccion" size="11" style="width:133" value="" onblur="solonumlet(this)">    </td>
    <td width="140">COD POSTAL</td>
    <td><input type="text" name="codPostal" size="3" style="width:133" onblur="solonum(this)">    </td>
  </tr>
  <tr>
    <td width="114">CORREO ELECTR&Oacute;ICO</td>
    <td width="187"><input type="text" name="email"  size="11" style="width:133">    </td>
    <td width="140">    </td>
    <td>    </td>
  </tr>
<tr><th colspan=4>DATOS DE LA CUENTA</th></tr>
  <tr>
    <td width="114">USUARIO ID</td>
    <td width="187"><input type="text" name="userId" size="11" style="width:133" onblur="solonumlet(this)" disabled="true"></td>
    <td width="140">&nbsp;</td>
    <td></td>
  </tr>
  <tr>
    <td width="114">CONTRASE&Ntilde;A ACTUAL</td>
    <td width="187"><input type="password" name="contrasena1" size="11" style="width:133" onblur="solonumlet(this)" onchange="javascript:habilitaCaducidad();"></td>
    <td colspan="2">Llene este campo si desea cambiar su contrase&ntilde;a</td>    
  </tr>
  <tr>
    <td width="114">NUEVA CONTRASE&Ntilde;A</td>
    <td width="187"><input type="password" name="contrasena2" size="11" style="width:133" onblur="solonumlet(this)">
     <%if (perfilIdUsuario == Constantes.PERFIL_TESORERO || 
      perfilIdUsuario == Constantes.PERFIL_CAJERO ||
      perfilIdUsuario == Constantes.PERFIL_INTERNO) {%>
      <span lang="ES-PE" class="textorojoclaro"> * </span>
      
<%   }  %>
    </td>
    <td width="140">CONFIRMACI&Oacute;N CONTRASE&Ntilde;A</td>
    <td><input type="password" name="contrasena3" size="11" style="width:133" onblur="solonumlet(this)"> 
      <%if (perfilIdUsuario == Constantes.PERFIL_TESORERO || 
      perfilIdUsuario == Constantes.PERFIL_CAJERO ||
      perfilIdUsuario == Constantes.PERFIL_INTERNO) {%>
      <span lang="ES-PE" class="textorojoclaro"> * </span>
      
<%   }  %>
    </td>    
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
    <td width="140">RESPUESTA</td>
    <td><input type="text" name="respuestaSecreta" size="11" style="width:133" onblur="solonumlet(this)"></td>
  </tr>
  <%if (perfilIdUsuario == Constantes.PERFIL_TESORERO || 
      perfilIdUsuario == Constantes.PERFIL_CAJERO ||
      perfilIdUsuario == Constantes.PERFIL_INTERNO) {%>
      <tr>
       <td  align="left" colspan="4" class="textorojoclaro">
	    <span lang="ES-PE" > (*) La contraseña modificada tiene una vigencia de <bean:write name="nDiasCad"/> días </span>
    	<input type="hidden" name="ndias" value="<bean:write name="nDiasCad"/>"></td>
      </tr>
      
<%   }  %>
<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
   <tr>
    <td width="114">CADUCIDAD DE CONTRASEÑA</td>
    <td width="187">
	<select size="1" name="cboCaducidad" style="width:130"  disabled="disabled">
    	<option selected value="-1">  </option>
      	<logic:iterate name="arrCaducidad" id="caducidad" scope="request">
        	<option value="<bean:write name="caducidad" property="atributo1"/>"><bean:write name="caducidad" property="descripcion"/></option>
        </logic:iterate>
	</select>        
    </td>
    <td width="159"></td>
    <td width="140"></td>
  </tr>
  <%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
  <tr><td  colspan="4" ><br><a class="linkrojoclaro" href="javascript:baja()">Presione aqu&iacute; si desea dar de baja la cuenta de nuestro servicio </a><br><br></td>
  </tr>
  <%}%>
 
  <%}%>
  <tr>
    <td align="center" colspan=4>
      <A href="javascript:doAceptar()"><img border=0 src="images/btn_aceptar.gif"></a>
      <A href="javascript:doCancelar()"><img border=0 src="images/btn_cancelar.gif"></a>
	</td>
  </tr>

  
</table>


<script LANGUAGE="JavaScript">
	//llenaComboHijo();
</script>

<script LANGUAGE="JavaScript">
	document.frm1.apellidoPaterno.value  = "<%=datosUsuarioBean.getApellidoPaterno()%>";
	document.frm1.apellidoMaterno.value  = "<%=datosUsuarioBean.getApellidoMaterno()%>";
	document.frm1.nombres.value          = "<%=datosUsuarioBean.getNombres()%>";
	doCambiaCombo(document.frm1.tipoDoc, "<%=datosUsuarioBean.getTipoDocumento()%>");
	document.frm1.numDoc.value           = "<%=datosUsuarioBean.getNumDocumento()%>";
	document.frm1.fax.value              = "<%=datosUsuarioBean.getFax()%>";			
	document.frm1.telefono.value         = "<%=datosUsuarioBean.getTelefono()%>";
	doCambiaCombo(document.frm1.pais,      "<%=datosUsuarioBean.getPais()%>");
	document.frm1.email.value            = "<%=datosUsuarioBean.getEmail()%>";
	doCambiaCombo(document.frm1.departamento, "<%=datosUsuarioBean.getDepartamento()%>");
	document.frm1.otroDepartamento.value = "<%=datosUsuarioBean.getOtroDepartamento()%>";
	
	llenaComboHijo();
	doCambiaCombo(document.frm1.provincia, "<%=datosUsuarioBean.getProvincia()%>");
	document.frm1.distrito.value         = "<%=datosUsuarioBean.getDistrito()%>";
	document.frm1.direccion.value        = "<%=datosUsuarioBean.getDireccion()%>";
	document.frm1.codPostal.value        = "<%=datosUsuarioBean.getCodPostal()%>";
	doCambiaCombo(document.frm1.provincia, "<%=datosUsuarioBean.getProvincia()%>");
	document.frm1.userId.value           = '<%=datosUsuarioBean.getUserId()%>';
	
	doCambiaCombo(document.frm1.pregSecreta, "<%=datosUsuarioBean.getPreguntaSecreta()%>");
	document.frm1.respuestaSecreta.value   = "<%=datosUsuarioBean.getRespuestaSecreta()%>";
	
	
	
	document.frm1.departamento.disabled=true;
	document.frm1.provincia.disabled=true;
	
	<%if (datosUsuarioBean.getPais().equals("01")) { %>
			document.frm1.pais.disabled=true;
			document.frm1.otroDepartamento.disabled=true;
	<% } else {%>
			document.frm1.pais.disabled=false;
			document.frm1.otroDepartamento.disabled=false;	
	<% } %>
	
	
	
</script>
<input type="hidden" name="hid4" value="<%=datosUsuarioBean.getUserId()%>">

</form>

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
</body>
</html>