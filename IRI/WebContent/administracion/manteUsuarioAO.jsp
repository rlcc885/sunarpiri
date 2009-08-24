<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>	
<script language="javascript">

<logic:present name="cambioSaldoAdm">
	window.top.frames[0].location.reload();
</logic:present>


function validarformulario()
{
/*
	if(esVacio(document.form1.apepat.value) && esVacio(document.form1.nombres.value)){
		alert("Ingrese al menos el Apellido Paterno y/o el Nombre");
		document.form1.apepat.focus();		
		return false;
	}
*/		
	return true;	
}

function Saldo(linea){
	document.form1.method = "POST";
	document.form1.action = "/iri/ReasignarSaldo.do?linea=" + linea;
	document.form1.submit();
}

function doActiva(id)
{
	var p = confirm("¿Está seguro que desea activar?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=activacion&param1=1&param2="+id;

}
function doDesactiva(id)
{
	var p = confirm("¿Está seguro que desea desactivar?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=activacion&param1=0&param2="+id;

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

function MuestraResultados(){ 
	if(validarformulario()){
		document.form1.method = "POST";
		document.form1.action = "/iri/Mantenimiento.do?state=mantenimientoUsuario";
		document.form1.submit();
		return true;
	}
	return false;	
}
</script>

</head>

<body>
<form name="form1"  onSubmit="Javascript:if (!MuestraResultados()) return false;">
<INPUT type=hidden name=radio value="6">
<input type="hidden" name="param1">
<br>
<table class="titulo">
	<tr>
		<td>
		<font color=black>ADMINISTRACI&Oacute;N ORGANIZACI&Oacute;N &gt;&gt;  Mantenimiento de Usuarios Afiliados a &gt;&gt;  </font>
		</td>
	</tr>
</table>
<br>
<table class=cabeceraformulario>
  <tr>
    <td width="31%">
      <b>B&uacute;squeda</b>
    </td>
	</tr>
</table>
<table class=formulario>
  <tr>
	<td></td>
    <td width="19%">Apellido Paterno</td>
    <td ><input type="text" name="apepat" size="20" onblur="sololet(this)"></td>
    <td colspan=2 align=right>
    <input type="image" src="images/btn_buscar.gif" border="0">
    </td>
  </tr>
  <tr>
    <td width="31%">
    </td>
    <td width="19%">Apellido Materno</td>
    <td ><input type="text" size="20" name="apemat" onblur="sololet(this)"></td>
    <td ></td>
  </tr>
  <tr>
    <td width="31%">
    </td>
    <td width="19%">Nombres</td>
    <td ><input type="text" name="nombres" size="20" onblur="sololet(this)"></td>
    <td ></td>
  </tr>
  <tr>
    <td colspan="5" align=center>
      <a href="javascript:UsuarioNuevo()" onmouseover="javascript:mensaje_status('Nuevo Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="<%=request.getContextPath()%>/images/btn_nuevo.gif"></a>
	</td>
  </tr>
</table>
<br>
<logic:present name="rptatransferencia">
<font color="red"><bean:write name="rptatransferencia"/></font>
</logic:present>

<logic:present name="listaMantUsuario">

<span id="resultados">
<table class=grilla>
  <tr>
    <th width="7%">USUARIO</th>
    <th width="16%">APELLIDOS Y NOMBRES</th>
    <th width="9%" align="center">Fec/Hora Reg</th>
    <th width="2%">SALDO DISPONIBLE</th>
    <th width="8%">ESTADO</th>
    <th width="6%">ADM. ORG.</th>
	    <th width="8%">FECHA DE AFILIACION</th>
	    <th width="8%">ULTIMO ACCESO</th>
	    <th width="8%">DIAS DESDE ULTIMO ACCESO</th>    	    
    <th >OPCIONES.</th>
  </tr>


<logic:iterate name="listaMantUsuario" id="item" scope="request">
  <tr class=grilla2>
    <td width="7%" bgcolor="#E2E2E2"><bean:write name="item" property="usuario"/></td>
    <td width="16%" bgcolor="#E2E2E2"><bean:write name="item" property="ape_Nom"/></td>
    <td width="9%" bgcolor="#E2E2E2" align="center"><bean:write name="item" property="fechaHoraRegistro"/></td>
    <td width="2%" bgcolor="#E2E2E2" align="center"><bean:write name="item" property="saldo"/></td>
    <td width="8%" bgcolor="#E2E2E2" align="center">&nbsp;<bean:write name="item" property="estado"/></td>
    <td width="6%" bgcolor="#E2E2E2" align="center"><bean:write name="item" property="admin_Org"/></td>
    
	          <td bgcolor="#E2E2E2" width="8%" height="27"><bean:write name="item" property="fechaAfiliacion"/></td>
	          <td bgcolor="#E2E2E2" width="8%" height="27"><bean:write name="item" property="fechaUltimoAcceso"/></td>
	          <td bgcolor="#E2E2E2" width="8%" height="27" align="center"><bean:write name="item" property="diasDesdeUltimoAcceso"/></td>
	          
	              
    <td width="21%" align=center>
    	<a href="javascript:EditarDatos('<bean:write name="item" property="usuario"/>')" onmouseover="javascript:mensaje_status('Editar Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">EDITAR DATOS</a><p>
	    <a href="javascript:Saldo(<bean:write name="item" property="lineaPrepago"/>)" onmouseover="javascript:mensaje_status('Linea Prepago');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">EDITAR SALDO</a>
	</td>
  </tr>
</logic:iterate>

</table>
</span>
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