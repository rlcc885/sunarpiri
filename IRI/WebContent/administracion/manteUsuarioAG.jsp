<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<%-- 8sep2002HT se agrega estilo global.css y cambios de diseno --%>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
function validarformulario(){
	if(document.form1.radio[0].checked==true){
		if(esVacio(document.form1.userId.value)){
			alert("Por favor ingrese el Usuario");
			document.form1.userId.focus();
			return false;
		}
	}	
	if(document.form1.radio[1].checked==true)
	{
		/*
			if(esVacio(document.form1.apepat.value) && esVacio(document.form1.nombres.value)){
				alert("Por favor ingrese al menos el Apellido Paterno y/o el Nombre");
				document.form1.apepat.focus();		
				return false;
			}
			if (!esVacio(document.form1.apepat.value)  && !contieneCarateresValidos(document.form1.apepat.value,"nombre"))
				{	
					alert("Por favor ingrese correctamente el Apellido Paterno");
					document.form1.apepat.focus();					
					return false;
				}	
			if (!esVacio(document.form1.apemat.value)  && !contieneCarateresValidos(document.form1.apemat.value,"nombre"))
				{	
					alert("Por favor ingrese correctamente el Apellido Materno");
					document.form1.apemat.focus();
					return false;
				}
				
			if (!esVacio(document.form1.nombres.value) && !contieneCarateresValidos(document.form1.nombres.value,"nombre"))
				{	
					alert("Por favor ingrese correctamente el Nombre del Representante");
					document.form1.nombres.focus();
					return false;
				}	
		*/
			
	}		
	if(document.form1.radio[2].checked==true){
			   
			if(esVacio(document.form1.numdoc.value) || !esEntero(document.form1.numdoc.value) || !esMayor(document.form1.numdoc.value,8) || !esEnteroMayor(document.form1.numdoc.value,1)){
				alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
				document.form1.numdoc.focus();		
				return false;
			}			
	}		
	if(document.form1.radio[3].checked==true){
			if(esVacio(document.form1.razsoc.value) && esVacio(document.form1.ruc.value)){
				alert("Por favor ingrese al menos la Razón Social o el Número del Documento");
				document.form1.razsoc.focus();		
				return false;
			}
			if(!esVacio(document.form1.ruc.value)){
			if(!esEntero(document.form1.ruc.value) || !esMayor(document.form1.ruc.value,8) || !esEnteroMayor(document.form1.ruc.value,1)){
				alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 11 caracteres numéricos (0-9)");
				document.form1.ruc.focus();		
				return false;
			}
	}		}
	if(document.form1.radio[4].checked==true){
				
			if(esVacio(document.form1.tiempo.value) || !contieneCarateresValidos(document.form1.tiempo.value,"numeroneg") || !esEnteroMayor(document.form1.tiempo.value,-1)){
				alert("Por Favor ingrese correctamente el Tiempo de Inactividad");
				document.form1.tiempo.focus();		
				return false;
			}

	}	
	return true;	
}

function doActiva(id)
{
	var p = confirm("¿Está seguro que desea activar al usuario?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=activacion&param1=1&param2="+id;

}
function doDesactiva(id)
{
	var p = confirm("¿Está seguro que desea desactivar al usuario?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=activacion&param1=0&param2="+id;

}

function doExonera(id)
{
	var p = confirm("¿Está seguro que desea exonerar de pago al usuario?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=exoneracion&param1=1&param2="+id;

}
function doExonera2(id)
{
	var p = confirm("¿Está seguro que desea activar el pago al usuario?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=exoneracion&param1=0&param2="+id;

}

function EditarDatos(param)
{ 
	document.form1.param1.value=param;
	document.form1.method = "POST";
	document.form1.action = "/iri/EditarUsuario.do";
	document.form1.submit();
	return true;
}

function UsuarioNuevo()
{ 
	document.form1.method = "POST";
	document.form1.action = "/iri/CrearUsuario.do";
	document.form1.submit();
	return true;
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

function MuestraResultados(){ 
	if(validarformulario()){
		obj_mayuscula(document.form1.userId);
		obj_mayuscula(document.form1.apepat);
		obj_mayuscula(document.form1.apemat);
		obj_mayuscula(document.form1.nombres);	
		obj_mayuscula(document.form1.razsoc);
		document.form1.method = "POST";
		document.form1.action = "/iri/Mantenimiento.do?state=mantenimientoUsuario";
		document.form1.submit();
		return true;
	}
	return false;
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Usuarios &gt;&gt; </font> Registro de Usuarios Individuales
		</td>
	</tr>
</table>
<br>
<form name="form1" class=formulario>
<input type="hidden" name="param1">
<table class="tablasinestilo">
  <tr>
    <td width="33%">
      <p align="left">
      <b><font size="2">B&uacute;squeda</font></b></p>
    </td>
    <td width="20%"></td>
    <td width="32%"></td>
    <td width="15%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="left">
      <input type="radio" value="1" name="radio" checked><b>Directa por ID</b>
    </td>
    <td width="20%" align="left">Usuario ID</td>
    <td width="32%"><input type="text" name="userId" size="20" maxlength="13" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'1');"></td>
    <td width="15%"><input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados();" onmouseover="javascript:mensaje_status('Buscar Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
  </tr>
  <tr>
    <td width="33%" align="left"><input type="radio" value="2" name="radio"><b>Por Apellidos y Nombres</b></td>
    <td width="20%" align="left">Apellido Paterno</td>
    <td width="32%"><input type="text" name="apepat" size="20" maxlength="30" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <td width="15%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="left"></td>
    <td width="20%" align="left">Apellido Materno</td>
    <td width="32%"><input type="text" name="apemat" size="20" maxlength="30" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <td width="15%"></td>
  </tr>
  <tr>
    <td width="33%" align="left"></td>
    <td width="20%" align="left">Nombres</td>
    <td width="32%"><input type="text" name="nombres" size="20" maxlength="40" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <td width="15%"></td>
  </tr>
  <tr>
    <td width="33%" align="left"><input type="radio" value="3" name="radio"><b>Por Documento Identidad</b></td>
    <td width="20%" align="left">Tipo</td>
    <td width="32%" colspan=2>
    <select size="1" name="numopt" >

		<logic:iterate name="listaDocsId" id="item" scope="request">
			<option value="<bean:write name="item" property="tipoDoc"/>"><bean:write name="item" property="nomAbre"/></option>
		</logic:iterate>
 
     </select>&nbsp;Numero <input type="text" size="12" maxlength="15" style="width:133" name="numdoc" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'3');">
    </td>
  </tr>
  <tr>
    <td width="33%" align="left"><input type="radio" value="4" name="radio"><b>Por Organizaci&oacute;n</b></td>
    <td width="20%" align="left">Raz&oacute;n Social</td>
    <td width="32%">
    <input type="text" size="20" maxlength="150" style="width:133" name="razsoc" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'4');"> 
    </td>
    <td width="15%"></td>
  <tr>
    <td width="33%" align="left"></td>
    <td width="20%" align="left">RUC </td>
    <td width="32%">
    <input type="text" size="12" maxlength="11" style="width:133" name="ruc" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'4');"> 
    </td>
    <td width="15%"></td>
  </tr>  
  <tr>
    <td width="33%"><input type="radio" value="5" name="radio"><b>Tiempo Inactividad mayor a</b></td>
    <td width="20%" align="right">
      <p align="left"><input type="text" size="3" maxlength="3" style="width:30" name="tiempo" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'5');">dias</td>
    <td width="32%"></td>
    <td width="15%"></td>
  </tr>
  <tr>
    <td width="100%" colspan="4">
      <p align="center"><br>
      <A href="javascript:UsuarioNuevo()" onmouseover="javascript:mensaje_status('Nuevo Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img style="border:0" src="<%=request.getContextPath()%>/images/btn_nuevo.gif"></a>
      <!--<A href="javascript:history.back();"><img style="border:0" src="/iri/images/btn_regresa.gif"></a>-->
    </td>
  </tr>
</table>
<br>

<!-- ******************************************************************************************************* -->

<logic:present name="listaMantUsuario">
<table class="grilla">
  <tr class=grilla2>
		<th width="11%">USUARIO</th>
		<th width="8%">TIPO</th>
		<th width="16%">APELLIDOS Y NOMBRES</th>
		<th width="18%">AFILIADO A ORGANIZACI&Oacute;N</th>
    	<th width="8%">ADM. ORG.</th>
	    <th width="8%">FECHA DE AFILIACION</th>
	    <th width="8%">SALDO</th>
	    <th width="8%">ULTIMO ACCESO</th>
	    <th width="8%">DIAS DESDE ULTIMO ACCESO</th>    	
	    <th height="14">Edici&oacute;n</th>
	    <th height="14">Estado</th>
	    <th height="14">Pago</th>
    </tr>
      <%
	  	//atributos de diseno
	  	boolean flag1=false;
	  	String dFila, dColumna;
	  %>
		<logic:iterate name="listaMantUsuario" id="item" scope="request">
		<% if (flag1==false)
					{
						dFila="";
						dColumna="bgcolor='#e2e2e2'";
					}
				else
					{
						dFila="class=grilla2";
						dColumna="";
					}
				flag1 = !flag1; %>
	        <tr <%=dFila%>> 
	          <td <%=dColumna%> width="11%" height="27"><bean:write name="item" property="usuario"/></td>
	          <td <%=dColumna%> width="8%"  height="27"><bean:write name="item" property="tipo"/></td>
	          <td <%=dColumna%> width="16%" height="27"><bean:write name="item" property="ape_Nom"/></td>
	          <td <%=dColumna%> width="18%" height="27"><bean:write name="item" property="org_afiliada"/></td>
	          <td <%=dColumna%> width="8%"  height="27"><bean:write name="item" property="admin_Org"/></td>
	          
	          <td <%=dColumna%> width="8%" height="27"><bean:write name="item" property="fechaAfiliacion"/></td>
	          <td <%=dColumna%> width="8%" height="27" align="center">S/. <bean:write name="item" property="saldo"/></td>
	          <td <%=dColumna%> width="8%" height="27"><bean:write name="item" property="fechaUltimoAcceso"/></td>
	          <td <%=dColumna%> width="8%" height="27" align="center"><bean:write name="item" property="diasDesdeUltimoAcceso"/></td>
	          	          
	          <td <%=dColumna%> height="27" align="center">
	          		<a href="javascript:EditarDatos('<bean:write name="item" property="usuario"/>')" onmouseover="javascript:mensaje_status('Editar Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Editar</a></td>
	          <td <%=dColumna%> height="27" align="center">
	          	<%-- 8sep2002HT boton activar/desactivar --%>
				<logic:equal name="item" property="flagActivo" value="0">
					<a href="javascript:doActiva('<bean:write name="item" property="usuario"/>')" onmouseover="javascript:mensaje_status('Activar Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Activar</a>
				</logic:equal>	          
				<logic:equal name="item" property="flagActivo" value="1">
					<a href="javascript:doDesactiva('<bean:write name="item" property="usuario"/>')" onmouseover="javascript:mensaje_status('Desactivar Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Desactivar</a>				
				</logic:equal>		
			  </td>
	          <td <%=dColumna%> height="27" align="center">
			<logic:equal name="item" property="tipo" value="EXTERNO">	
				<logic:equal name="item" property="flagExonPago" value="0">
					<a href="javascript:doExonera('<bean:write name="item" property="usuario"/>')" onmouseover="javascript:mensaje_status('Exonerar de Pago a Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Exonerar</a>				
				</logic:equal>	          
				<logic:equal name="item" property="flagExonPago" value="1">
					<a href="javascript:doExonera2('<bean:write name="item" property="usuario"/>')" onmouseover="javascript:mensaje_status('Activar Opcion de Pago a Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Activar Pago</a>				
				</logic:equal>
			</logic:equal>
	          </td>
	        </tr>
		</logic:iterate>
</table>
</logic:present>

</form>

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