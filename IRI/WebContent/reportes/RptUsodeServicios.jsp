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
<bean:define id="list_OficinaRegistral" name="formusodeserviciosbean" property="list_OficinaRegistral" type="java.util.List" />
<script language="javascript">
var array_OfiReg = new Array();
<% int k = 0; %>
<logic:iterate id="str_OficinaRegistral" name="list_OficinaRegistral">
	var temp_array = new Array();
	temp_array[0]="<bean:write name="str_OficinaRegistral" property="codigoRegistroPublico"/>"; //id 
	temp_array[1]="<bean:write name="str_OficinaRegistral" property="codigoOficinaRegistral"/>"; //id 
	temp_array[2]="<bean:write name="str_OficinaRegistral" property="nombreOficinaRegistral"/>"; //descripcion 
	array_OfiReg[<%=k%>]=temp_array;
	<%  k++; %>
</logic:iterate>


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
	document.form1.action = "/iri/ReporteUsodeServicios.do?state=verReporte";
	document.form1.submit();
	return true;
	}
	return false;
}
function ExportarResultados(){
	if(validarformulario())
	{
	document.form1.action = "/iri/ReporteUsodeServicios.do?state=exportarReporte";
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
		document.form1.action = "/iri/ReporteUsodeServicios.do?state=verDetalle";
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



<logic:present name="formusodeserviciosbean" >
<bean:define id="list_RegistroPublico" name="formusodeserviciosbean" property="list_RegistroPublico" type="java.util.List" />
<bean:define id="list_TipoUsuario" name="formusodeserviciosbean" property="list_TipoUsuario" type="java.util.List" />
</logic:present>

<body>
<br>
<table cellspacing=0 class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; </FONT> Uso de Servicios</td></tr>
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
    <th width="100%" colspan="5"><font size="2">Reporte de Uso de Servicios</font>&nbsp;</th>
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
    </td>
  </tr>
 
  <tr>
    <td width="90" valign="middle"></td>
    <td width="185" valign="middle" colspan="2"></td>
    <td width="85" valign="middle"></td>
    <td  valign="middle"></td>
  </tr>
  <tr> 
    <td colspan="5" valign="center"><b>Regi&oacute;n</b> <select size="1" name="sel_RegistroPublico" onChange="sel_Reiniciar_Opciones(document.form1.sel_OficinaRegistral,array_OfiReg,document.form1.sel_RegistroPublico,'TODAS');">
	    <logic:iterate id="strRegistroPublico" name="list_RegistroPublico" >    
			<option value="<bean:write name="strRegistroPublico" property="codigoRegistroPublico" />" <bean:write name="strRegistroPublico" property="codigoRegistroPublicoSelected" />><bean:write name="strRegistroPublico" property="nombreRegistroPublico" /></option>
		</logic:iterate>    
        </select><b>Of.Reg</b> <select size="1" name="sel_OficinaRegistral">
        </select>
        <b>Usuarios</b> <select size="1" name="sel_TipoUsuario">
	    <logic:iterate id="strTipoUsuario" name="list_TipoUsuario" >    
			<option value="<bean:write name="strTipoUsuario" property="codigoTipoUsuario" />" <bean:write name="strTipoUsuario" property="codigoTipoUsuarioSelected" />><bean:write name="strTipoUsuario" property="nombreTipoUsuario" /></option>
		</logic:iterate>       
        </select> 
     </td>
  </tr>  
</table>
<br>
<logic:present name="formusodeserviciosbean" property="list_UsodeServicios">
<bean:define id="list_UsodeServicios" name="formusodeserviciosbean" property="list_UsodeServicios" type="java.util.List" />
  <table class="titulo">
    <tr> 
      <td><font color="#000000">Reporte de Uso de Servicios del</font>&nbsp;<bean:write name="formusodeserviciosbean" property="str_Date_Inicio"/>
        <font color="#000000">al</font>&nbsp;<bean:write name="formusodeserviciosbean" property="str_Date_Fin"/></td>
    </tr>
  </table>

  <table width="100%" border="0" class="grilla">

	<logic:iterate id="detalle_usodeservicios" name="list_UsodeServicios" > 
	<logic:present name="detalle_usodeservicios"  property="nombreRegistroPublico">
    <tr> 
      <td width="223" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="53" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="53" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="53" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="52" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="136" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="138" align="middle" height="20" bgcolor="#ffffff"></td>
      <td width="133" align="middle" height="20" bgcolor="#ffffff"></td>
    </tr>
    <tr> 
      <td width="223" align="middle" height="20" bgcolor="#ffffff"><b>&nbsp;<bean:write name="detalle_usodeservicios" property="nombreRegistroPublico"/></b></td>
      <th width="53" align="middle" height="12">RPN</th>
      <th width="53" align="middle" height="12">RPJ </th>
      <th width="53" align="middle" height="12">RPI </th>
      <th width="52" align="middle" height="12">RBM </th>
      <th width="136" align="middle" height="12">Total</th>
      <th width="138" align="middle" height="12">Porcentajes (%)</th>
      <th width="133" align="middle" height="12">(%) Nivel Nacional&nbsp;</th>      

    </tr>
    </logic:present>
	<logic:notPresent name="detalle_usodeservicios"  property="nombreRegistroPublico">
    <tr> 
	<logic:equal name="detalle_usodeservicios"  property="nombreServicio" value="Totales">
      <th width="223" align="left" height="20" bgcolor="#ffffff">&nbsp;<bean:write name="detalle_usodeservicios" property="nombreServicio"/></th>	
	</logic:equal>
	<logic:notEqual name="detalle_usodeservicios"  property="nombreServicio" value="Totales">
      <td width="223" align="left" height="20" bgcolor="#ffffff">&nbsp;<bean:write name="detalle_usodeservicios" property="nombreServicio"/></td>
	</logic:notEqual>      
      <td width="53" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="totalRPN"/></td>
      <td width="53" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="totalRPJ"/></td>
      <td width="53" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="totalRPI"/></td>
      <td width="52" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="totalRBM"/></td>
      <td width="136" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="totalGeneralRegistroPublico"/></td>
      <td width="138" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="porcentajeRegistroPublico"/></td>
      <td width="133" align="middle" height="20" bgcolor="<bean:write name="detalle_usodeservicios" property="celda_bgcolor"/>">&nbsp;<bean:write name="detalle_usodeservicios" property="porcentajeNacionalRegistroPublico"/></td>
    </tr>
    </logic:notPresent>
   	</logic:iterate>
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

<script language="javascript">
 sel_Reiniciar_Opciones(document.form1.sel_OficinaRegistral,array_OfiReg,document.form1.sel_RegistroPublico,'TODAS');
// sel_Reiniciar_Opciones(document.form1.sel_OficinaRegistral,array_OfiReg,document.form1.sel_RegistroPublico);
 sel_ActivarOpcion(document.form1.sel_OficinaRegistral, '<bean:write  name="formusodeserviciosbean" property="str_OficinaRegistral"/>')
</script>
</body>
</html>