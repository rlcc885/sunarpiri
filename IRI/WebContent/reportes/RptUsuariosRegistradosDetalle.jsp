<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title>
<script language="javascript">
function validarformulario()
{
	return true;  
}
function MuestraResultados(){
	if(validarformulario())
	{
	document.form1.action = "/iri/ReporteUsuariosRegistrados.do?state=verReporte";
	document.form1.submit();
	return true;
	}
	return false;
}
function VerDetalle(pagina){
	if(validarformulario())
	{
		document.form2.pagina.value= pagina;
		document.form2.action = "/iri/ReporteUsuariosRegistrados.do?state=verDetalle";
		document.form2.submit();
		return true;
	}
	return false;
}

function doCambiaRadio(obj_radio, valor)
{ 
for (var rr = 0; rr < obj_radio.length; rr++)
	{
		var xvlr = obj_radio[rr].value;
		if (xvlr == valor)
			obj_radio[rr].checked=true;
	}
}


</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<BODY>
<br>
<table class="titulo"><tr>
      <td><font color="black">ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; reporte de usuarios registrados &gt;&gt;</font> detalle</td>
    </tr></table> 
<br>


<form name="form1" method="post" >
<table class="titulo"><tr>
      <td><font color="black">Registro de Usuarios</font> 
        <input type="text" name="txttipo1" size=14 disabled=true value="<bean:write name="formusuariosregistradosbean" property="str_NombreTipo"/>">
        <font color="black" size="1">del</font><font size="1"> <font color="#993300"> 
        <bean:write name="formusuariosregistradosbean" property="str_Date_Inicio"/></font> <font color="black">al</font> <font color="#993300"> 
        <bean:write name="formusuariosregistradosbean" property="str_Date_Fin"/></font>&nbsp;</font><font color="black">Oficina : </font>
<input type="text" name="txtdep" size=15 disabled=true value="<bean:write name="formusuariosregistradosbean" property="str_NombreOfiReg"/>"></td></tr></table>

<logic:present name="formusuariosregistradosbean" property="list_UsuariosRegistradosOrganizacion">
<bean:define id="list_UsuariosRegistradosOrganizacion" name="formusuariosregistradosbean" property="list_UsuariosRegistradosOrganizacion" type="java.util.List" />
 <br> 
  <table width="95%" height="101" border="0" class="grilla">
    <tr> 
      <th width="12%" align="center" height="12">Raz&oacute;n Social</th>
      <th width="8%" align="center" height="12">Fecha Registro</th>
      <th width="8%" align="center" height="12">Hora Registro</th>
      <th width="12%" align="center" height="12">RUC</th>
      <th width="13%" align="center" height="12">Usuarios</th>
      <th width="21%" align="center" height="12">ADMIN APELLIDOS</th>
      <th width="21%" align="center" height="12">ADMIN NOMBRES</th>
      <th width="16%" align="center" height="12">Fecha Ult. Acceso</th>
      <th width="21%" align="center" height="12">Hora Ult. Acceso</th>
    </tr>
    
    <logic:iterate id="detalle_usuariosRegistrados" name="list_UsuariosRegistradosOrganizacion" > 
    <tr> 
      <td width="12%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_RazonSocial"/></td>
      <td width="8%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_FechaRegistro"/></td>
      <td width="8%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_HoraRegistro"/></td>
      <td width="12%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_Documento"/></td>
      <td width="13%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_NumeroUsuarios"/></td>
      <td width="21%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_Apellidos"/></td>
      <td width="21%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_Nombres"/></td>
      <td width="16%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_FechaUltimoAcceso"/></td>
      <td width="21%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_HoraUltimoAcceso"/></td>
    </tr>
 	</logic:iterate>    
    <tr> 
      <td height="24" colspan="9" align="center" bgcolor="#FFFFFF"> </td>
    </tr>
</table>
</logic:present>

<logic:present name="formusuariosregistradosbean" property="list_UsuariosRegistradosIndividuales">
<bean:define id="list_UsuariosRegistradosIndividuales" name="formusuariosregistradosbean" property="list_UsuariosRegistradosIndividuales" type="java.util.List" />
 <br> 
  <table width="100%" height="101" border="0" class="grilla">
    <tr> 
      <th width="8%" align="center" height="12">Usuario</th>
      <th width="8%" align="center" height="12">Fecha Registro</th>
      <th width="8%" align="center" height="12">Hora Registro</th>
      <th width="11%" align="center" height="12">TIPO DOC IDENT</th>
      <th width="12%" align="center" height="12">NRO DOC</th>
      <th width="18%" align="center" height="12">APELLIDOS</th>
      <th width="22%" align="center" height="12">NOMBRES</th>
      <th width="12%" align="center" height="12">Fecha Ult Acceso</th>
      <th width="12%" align="center" height="12">Hora Ult Acceso</th>
    </tr>
    <logic:iterate id="detalle_usuariosRegistrados" name="list_UsuariosRegistradosIndividuales">   
    <tr> 
      <td width="8%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_UsuarioIndividual"/></td>
      <td width="8%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_FechaRegistro"/></td>
      <td width="8%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_HoraRegistro"/></td>
      <td width="11%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_TipoDocumento"/></td>
      <td width="12%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_Documento"/></td>
      <td width="18%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_Apellidos"/></td>
      <td width="22%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_Nombres"/></td>
      <td width="12%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_FechaUltimoAcceso"/></td>
      <td width="12%" align="center" height="11"><bean:write name="detalle_usuariosRegistrados" property="str_HoraUltimoAcceso"/></td>
    </tr>
 	</logic:iterate>    
    <tr> 
      <td height="24" colspan="9" align="center" bgcolor="#FFFFFF"> </td>
    </tr>
</table>
</logic:present>

<table class="tablasinestilo">
	<tr>
		<td>
			<div align="center">
				<a href="javascript:MuestraResultados();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif"></a> 			
				<a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a> 
			</div>
		</td>
	</tr>
</table>

<!--
  <table width="600">
    <tr> 
      <td height="24" align="center" bgcolor="#FFFFFF">
      <logic:notEqual name="formusuariosregistradosbean" property="str_Pagina_Anterior" value="">
      <a href="javascript:VerDetalle('<bean:write name="formusuariosregistradosbean" property="str_Pagina_Anterior"/>');"><img src="/iri/images/btn_ant.gif" border="0"></a> 
      </logic:notEqual>    
	  <logic:notEqual name="formusuariosregistradosbean" property="str_Pagina_Siguiente" value="">
	  <a href="javascript:VerDetalle('<bean:write name="formusuariosregistradosbean" property="str_Pagina_Siguiente"/>');"><img src="/iri/images/btn_sig.gif" border="0"></a> 
      </logic:notEqual>    
	  </td>
    </tr>
  </table>
-->

<input type="hidden" name="diainicio" value="<bean:write name="formusuariosregistradosbean" property="str_Dia_Inicio"/>">
<input type="hidden" name="mesinicio" value="<bean:write name="formusuariosregistradosbean" property="str_Mes_Inicio"/>">
<input type="hidden" name="anoinicio" value="<bean:write name="formusuariosregistradosbean" property="str_Ano_Inicio"/>">
<input type="hidden" name="diafin" value="<bean:write name="formusuariosregistradosbean" property="str_Dia_Fin"/>">
<input type="hidden" name="mesfin" value="<bean:write name="formusuariosregistradosbean" property="str_Mes_Fin"/>">
<input type="hidden" name="anofin" value="<bean:write name="formusuariosregistradosbean" property="str_Ano_Fin"/>">
</form>
<form name="form2" method="post">
<input type="hidden" name="pagina" value="1">
<input type="hidden" name="codregpub" value="<bean:write name="formusuariosregistradosbean" property="str_CodRegPub"/>">
<input type="hidden" name="codofireg" value="<bean:write name="formusuariosregistradosbean" property="str_CodOfiReg"/>">
<input type="hidden" name="codtipo" value="<bean:write name="formusuariosregistradosbean" property="str_CodTipo"/>">
<input type="hidden" name="diainicio" value="<bean:write name="formusuariosregistradosbean" property="str_Dia_Inicio"/>">
<input type="hidden" name="mesinicio" value="<bean:write name="formusuariosregistradosbean" property="str_Mes_Inicio"/>">
<input type="hidden" name="anoinicio" value="<bean:write name="formusuariosregistradosbean" property="str_Ano_Inicio"/>">
<input type="hidden" name="diafin" value="<bean:write name="formusuariosregistradosbean" property="str_Dia_Fin"/>">
<input type="hidden" name="mesfin" value="<bean:write name="formusuariosregistradosbean" property="str_Mes_Fin"/>">
<input type="hidden" name="anofin" value="<bean:write name="formusuariosregistradosbean" property="str_Ano_Fin"/>">
<input type="hidden" name="nomofireg" value=<bean:write name="formusuariosregistradosbean" property="str_NombreOfiReg"/>">
</form>
</BODY>
</HTML>