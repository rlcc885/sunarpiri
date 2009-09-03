<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="gob.pe.sunarp.extranet.reportes.controller.*" %>
<%
System.out.println("iniciando jsp...");
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
<HTML>
<HEAD>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>

	<title>B&uacute;squeda de Reservas de Denominaci&oacute;n</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="JavaScript">
function buscar(){
	cboOficina = document.form1.cboOficinas.value;
	cboDiaInic = document.form1.diaInicio.value;
	cboMesInic = document.form1.mesInicio.value;
	cboAnioInic = document.form1.anioInicio.value;
	cboDiaFin = document.form1.diaFin.value;
	cboMesFin = document.form1.mesFin.value;
	cboAnioFin = document.form1.anioFin.value;
	//chBox = document.form1.presentadas.value;
		if (cboDiaInic == "00" || cboMesInic=="00" || cboAnioInic=="00" ){
			alert('Ingrese la fecha inicial correctamente.');
		}else{
			if (cboDiaFin == "00" || cboMesFin=="00" || cboAnioFin=="00" ){
				alert('Ingrese la fecha final correctamente.');
			}
			else {
				if (cboOficina=="00"){
					alert('Seleccione la oficina.');
				}else{
					var a= "0";
					if (document.form1.tipoPresentadas.checked ){
						a="1";
						document.form1.method="POST";
						document.form1.action="/iri/Denominacion.do?state=verReportes";		
						document.form1.submit();
					}
					if (document.form1.tipoTrabajadas.checked){
						a="2"
						document.form1.method="POST";
						document.form1.action="/iri/Denominacion.do?state=verReportes";		
						document.form1.submit();
					}
					
					if (a=="0" ){
						alert("Seleccione el tipo de reporte: Presentadas o Trabajadas")
					}
					
				}
			}
		}
}
function seleccionaPresentadas(){
	if (document.form1.tipoPresentadas.checked){
		document.form1.tipoTrabajadas.checked=false;
		document.form1.tipoPresentadas.checked=true;
	}
}
function seleccionaTrabajadas(){
	if (document.form1.tipoTrabajadas.checked){
		document.form1.tipoPresentadas.checked=false;
		document.form1.tipoTrabajadas.checked=true;
	}

}
</script>
</HEAD>

<BODY>
<br>
<table cellspacing=0 class=titulo>
   <tr>
	<td>
		<FONT COLOR="black">SOLICITUDES   <font size="1">&gt;&gt;</font></FONT><font color="900000">Reserva de Denominacion <font
			color="black"><font size="1">&gt;&gt; <font color="900000">Reportes</font></font></FONT>
	</td>
  </tr>
</table>
<br>
<form name="form1" method="post" class="formulario">
<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
    <th colspan="5"><font size="2">Filtro: Reporte de Solicitudes de Reserva Presentadas / Trabajadas</font></th>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td valign="middle" width="43"><b>Desde</b></td>
    <td valign="middle" width="251">d&iacute;a <SELECT size="1"
			name="diaInicio">
			<option value="00" selected>00</option>
			<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedIDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
			</SELECT>
	mes <SELECT size="1"
			name="mesInicio">
			<option value="00" selected>00</option>
			<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedIMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
    	</select>
	año <SELECT size="1"
			name="anioInicio">
			<option value="00" selected>0000</option>
			<% for (int w = 0; w < arrYears.length; w++ ) { %>
    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedIYear)){%>selected<%}%>><%=arrYears[w]%></option>
    		<% } %>	
			</SELECT>
	</td>
    <td valign="middle" width="38"><b>Hasta</b></td>
    <td valign="middle" width="257"> d&iacute;a <SELECT size="1"
			name="diaFin">
			<option value="00" selected>00</option>
			<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedFDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
		</SELECT> &nbsp;mes <SELECT size="1" name="mesFin">
			<option value="00" selected>00</option>
			<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedFMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
		</SELECT> año <SELECT size="1" name="anioFin">
			<option value="00" selected>00</option>
			<% for (int w = 0; w < arrYears.length; w++ ) { %>
    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedFYear)){%>selected<%}%>><%=arrYears[w]%></option>
    		<% } %>
		</SELECT></td>
    <td  valign="middle" width="1"></td>
  </tr>
  <tr>
  	<td colspan="5">&nbsp;<td>
  </tr>
  <tr>
	  <td width="80"><strong>Oficina Registral</strong></td>
	  <td width="251">
	  
	  <select size="1" name="cboOficinas" style="width:220" >
		<option value="00" selected>Seleccionar</option>
		<option value="01" >Todas</option>
	     <logic:iterate name="listaOficinas" id="oficina">
			<!--  <option value=<bean:write name="oficina" property="codigo"/> selected><bean:write name="oficina" property="descripcion"/></option>-->
			<option value=<bean:write name="oficina" property="descripcion"/> selected><bean:write name="oficina" property="descripcion"/></option>
		 </logic:iterate>
	  </select>
		
	  </td>
	  <td colspan="3"><div align="center">
	  <input type="checkbox" name="tipoPresentadas" onClick="javascript:seleccionaPresentadas()"
			
			value="01" ><b>Presentadas</b>
			<input type="checkbox" name="tipoTrabajadas" onClick="javascript:seleccionaTrabajadas()"
			
			value="02" ><b>Trabajadas</b>
	  </td>
	  
  </tr>
  <tr>
	  <td width="80"><strong></strong></td>
	  <td width="251">
		
	  </td>
	  <td colspan="3"><div align="center">
	  <A href="javascript:buscar();" onmouseover="javascript:mensaje_status('Buscar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	  <IMG border="0" src="<%=request.getContextPath()%>/images/btn_buscar.gif" width="83" height="25"> </div></td>
  </tr>
 <tr> </tr>
</table>
</form>
</BODY>
</HTML>
