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
    if(!esRangoFecha(sel_Obtener_Texto(document.form1.diainicio),sel_Obtener_Texto(document.form1.mesinicio),sel_Obtener_Texto(document.form1.anoinicio),sel_Obtener_Texto(document.form1.diafin),sel_Obtener_Texto(document.form1.mesfin),sel_Obtener_Texto(document.form1.anofin)) ){
    	return false;
    }
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
function ExportarResultados(){
	if(validarformulario())
	{
	document.form1.action = "/iri/ReporteUsuariosRegistrados.do?state=exportarReporte";
	document.form1.submit();
	return true;
	}
	return false;
}

function VerDetalle(codregpub,codofireg,codtipo,nomofireg){
	if(validarformulario())
	{
		document.form1.codregpub.value=codregpub;
		document.form1.codofireg.value=codofireg;	
		document.form1.codtipo.value=codtipo;	
		document.form1.nomofireg.value=nomofireg;	
		document.form1.action = "/iri/ReporteUsuariosRegistrados.do?state=verDetalle";
		document.form1.submit();
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



<body>
<br>
<table cellspacing=0 class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; </FONT> Usuarios Registrados</td></tr>
</table>
<br>
<%
String[] arrDays = (String[]) request.getAttribute("arrDays");
String[] arrMonths = (String[]) request.getAttribute("arrMonths");
String[] arrYears = (String[]) request.getAttribute("arrYears");

String selectedIDay = (String) request.getAttribute("selectedIDay");
String selectedIMonth = (String) request.getAttribute("selectedIMonth");
String selectedIYear = (String) request.getAttribute("selectedIYear");

String selectedFDay = (String) request.getAttribute("selectedFDay");
String selectedFMonth = (String) request.getAttribute("selectedFMonth");
String selectedFYear = (String) request.getAttribute("selectedFYear");
%>
<form name="form1" method="post" >
<input type="hidden" name="codregpub" value="">
<input type="hidden" name="codofireg" value="">
<input type="hidden" name="codtipo" value="">
<input type="hidden" name="nomofireg" value="">
<table border="0" class="formulario" cellspacing=0 >
  <tr>
    <th width="100%" colspan="5"><font size="2">Reporte de Usuarios Registrados</font>&nbsp;</th>
  </tr>
  <tr>
    <td width="100%" colspan="5">&nbsp;</td>
  </tr>
  
  <tr>
    <td width="90" valign="middle"><b>Desde</b></td>
    <td width="110" valign="middle"></td>
    <td width="75" valign="middle"><b>Hasta</b></td>
    <td width="85" valign="middle"></td>
    <td  valign="middle"></td>
  </tr>
  <tr>
    <td valign="middle" nowrap colspan=2>&nbsp;
		      dia&nbsp;<select size="1" name="diainicio">
		    		<% for (int w = 0; w < arrDays.length; w++ ) { %>
		    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedIDay)){%>selected<%}%>><%=arrDays[w]%></option>
		    		<% } %>
		      </select>mes&nbsp;<select size="1" name="mesinicio">
		    		<% for (int w = 0; w < arrMonths.length; w++ ) { %>
		    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedIMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
		    		<% } %>
		      </select>a&ntilde;o
		      <select size="1" name="anoinicio">
		    		<% for (int w = 0; w < arrYears.length; w++ ) { %>
		    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedIYear)){%>selected<%}%>><%=arrYears[w]%></option>
		    		<% } %>	
		      </select>
		    </td>
    
    <td nowrap colspan=2>&nbsp;
			dia&nbsp; <select size="1" name="diafin">
		    		<% for (int w = 0; w < arrDays.length; w++ ) { %>
		    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedFDay)){%>selected<%}%>><%=arrDays[w]%></option>
		    		<% } %>
		      </select> mes 
		      <select size="1" name="mesfin">
		    		<% for (int w = 0; w < arrMonths.length; w++ ) { %>
		    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedFMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
		    		<% } %>
				</select>a&ntilde;o
				<select size="1" name="anofin">
		    		<% for (int w = 0; w < arrYears.length; w++ ) { %>
		    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedFYear)){%>selected<%}%>><%=arrYears[w]%></option>
		    		<% } %>  
		      </select>
    </td>
    
    <td  valign="middle">
	   <input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados();" onmouseover="javascript:mensaje_status('Buscar Usuarios Registrados');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
       <!--<input type="button" value="Ejecutar" onClick="ValidateForm()">-->
    </td>
  </tr>
 
  <tr>
    <td width="90" valign="middle"></td>
    <td width="185" valign="middle" colspan="2"></td>
    <td width="85" valign="middle"></td>
    <td  valign="middle"></td>
  </tr>
</table>
<br>
<logic:present name="formusuariosregistradosbean" property="list_UsuariosRegistrados">
<bean:define id="list_UsuariosRegistrados" name="formusuariosregistradosbean" property="list_UsuariosRegistrados" type="java.util.List" />
<bean:define id="list_TotalesUsuariosRegistrados" name="formusuariosregistradosbean" property="list_TotalesUsuariosRegistrados" type="java.util.List" />

  <table class="titulo">
    <tr> 
      <td><font color="#000000">Reporte de Usuarios Registrados del</font>&nbsp;<bean:write name="formusuariosregistradosbean" property="str_Date_Inicio"/>
        <font color="#000000">al</font>&nbsp;<bean:write name="formusuariosregistradosbean" property="str_Date_Fin"/></td>
    </tr>
  </table>
  <table width="100%" border="0" class="grilla">
    <tr> 
      <td width="9%" align="center" height="19"></td>
      <td width="9%" align="center" height="19"></td>
      <td width="11%" align="center" height="19"></td>
      <td width="11%" align="center" height="19"></td>
      <td width="9%" align="center" height="19"></td>
    </tr>
    <tr> 
      <th width="9%" align="center" height="12">Oficina Registral</th>
      <th width="9%" align="center" height="12">Organizacion </th>
      <th width="11%" align="center" height="12">Individuales</th>
      <th width="11%" align="center" height="12">Totales</th>
      <th width="9%" align="center" height="12">Porcentajes (%)</th>
    </tr>
	<logic:iterate id="detalle_usuariosRegistrados" name="list_UsuariosRegistrados" > 
    <tr> 
      <td width="9%" align="center" height="19" bgcolor="#E2E2E2">&nbsp;<bean:write name="detalle_usuariosRegistrados" property="nombreOficinaRegistral"/></td>
      <td width="9%" align="center" height="19" bgcolor="#E2E2E2">&nbsp;<logic:notEqual name="detalle_usuariosRegistrados" property="totalOrganizacionOficinaRegistral" value="0"><a href="javascript:VerDetalle('<bean:write name="detalle_usuariosRegistrados" property="codigoRegistroPublico"/>','<bean:write name="detalle_usuariosRegistrados" property="codigoOficinaRegistral"/>','org','<bean:write name="detalle_usuariosRegistrados" property="nombreOficinaRegistral"/>')" target="_self"><bean:write name="detalle_usuariosRegistrados" property="totalOrganizacionOficinaRegistral"/></a></logic:notEqual></td>
      <td width="11%" align="center" height="19" bgcolor="#E2E2E2">&nbsp;<logic:notEqual name="detalle_usuariosRegistrados" property="totalIndividualOficinaRegistral" value="0"><a href="javascript:VerDetalle('<bean:write name="detalle_usuariosRegistrados" property="codigoRegistroPublico"/>','<bean:write name="detalle_usuariosRegistrados" property="codigoOficinaRegistral"/>','ind','<bean:write name="detalle_usuariosRegistrados" property="nombreOficinaRegistral"/>')" target="_self"><bean:write name="detalle_usuariosRegistrados" property="totalIndividualOficinaRegistral"/></a></logic:notEqual></td>
      <td width="11%" align="center" height="19" bgcolor="#E2E2E2">&nbsp;<logic:notEqual name="detalle_usuariosRegistrados" property="totalGeneralOficinaRegistral" value="0"><bean:write name="detalle_usuariosRegistrados" property="totalGeneralOficinaRegistral"/></logic:notEqual></td>
      <td width="9%" align="center" height="19" bgcolor="#E2E2E2">&nbsp;<logic:notEqual name="detalle_usuariosRegistrados" property="totalGeneralOficinaRegistral" value="0"><bean:write name="detalle_usuariosRegistrados" property="porcentajesOficinaRegistral"/></logic:notEqual></td>
    </tr>
   	</logic:iterate>
   	<logic:iterate id="total_usuariosRegistrados" name="list_TotalesUsuariosRegistrados" > 
    <tr> 
      <th width="9%" align="center" height="24" bgcolor="#FFFFFF">&nbsp;<bean:write name="total_usuariosRegistrados" property="nombreOficinaRegistral"/></th>
      <td width="9%" align="center" height="24" bgcolor="#FFFFFF">&nbsp;<bean:write name="total_usuariosRegistrados" property="totalOrganizacionOficinaRegistral"/></td>
      <td width="11%" align="center" height="24" bgcolor="#FFFFFF">&nbsp;      <bean:write name="total_usuariosRegistrados" property="totalIndividualOficinaRegistral"/></td>
      <td width="11%" align="center" height="24" bgcolor="#FFFFFF">&nbsp;<bean:write name="total_usuariosRegistrados" property="totalGeneralOficinaRegistral"/></td>
      <td width="9%" align="center" height="24" bgcolor="#FFFFFF"></td>
    </tr>
    </logic:iterate>
    <tr> 
      <td colspan="5" align="center" bgcolor="#FFFFFF"></td>
    </tr>
  </table>
  <table width="600">
    <tr> 
      <td height="24" align="right" bgcolor="#FFFFFF">
      <a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" width="83" height="25" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
	  <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print2.gif" width="85" height="26" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
	  <a href="javascript:ExportarResultados();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" width="83" height="24" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
	  </td>
    </tr>
  </table>
</logic:present>
</form>
</body>
</html>