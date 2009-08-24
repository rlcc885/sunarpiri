<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="gob.pe.sunarp.extranet.reportes.controller.*" %>
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
<HTML>
<HEAD>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
	<LINK REL="stylesheet" type="text/css" href="styles/global.css">

	<title>B&uacute;squeda de d&iacute;as no laborables</title>
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
					document.form1.method="POST";
					document.form1.action="/iri/DiaNoLabo.do?state=buscarDiasNoLabo";		
					document.form1.submit();
				}
			}
		}
}
</script>
</HEAD>

<BODY>
<br>
<table cellspacing=0 class=titulo>
   <tr>
	<td>
		<FONT COLOR="black">DIAS NO LABORABLES <font size="1">&gt;&gt;</font></FONT><font color="900000">B&uacute;squeda de d&iacute;as no laborables</FONT>
	</td>
  </tr>
</table>
<br>
<form name="form1" method="post" class="formulario">
<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
    <th colspan="5"><font size="2">Filtro: b&uacute;squeda de d&iacute;as no laborables</font></th>
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
	        		<option value="10|07">Andahuaylas</option>
	        		<option value="06|02">Apurimac</option>
	        		<option value="03|01">Arequipa</option>
			        <option value="10|05">Ayacucho</option>
			        <option value="01|06">Barranca</option>
			        <option value="11|04">Bagua</option>
			        <option value="01|02">Callao</option>
			        <option value="11|02">Cajamarca</option>
			        <option value="03|02">Caman&aacute;</option>
			        <option value="01|05">Ca&ntilde;ete</option>
			        <option value="04|02">Casma</option>
			        <option value="03|03">Castilla</option>
			        <option value="11|05">Chachapoyas</option>
			        <option value="08|02">Chep&eacute;n</option>
			        <option value="11|01">Chiclayo</option>
			        <option value="04|03">Chimbote</option>
			        <option value="10|02">Chincha</option>
			        <option value="11|06">Chota</option>
			        <!--option value="13|01">Coronel portillo</option-->
			        <option value="06|01">Cusco</option>
			        <option value="01|04">Huacho</option>
			        <option value="08|03">Huamachuco</option>
			        <option value="10|08">Huancavelica</option>
			        <option value="02|01">Huancayo</option>
			        <option value="10|06">Huanta</option>
			        <option value="02|02">Hu&aacute;nuco</option>
			        <option value="01|03">Huaral</option>
			        <option value="04|01">Huaraz</option>
			        <option value="10|01">Ica</option>
			        <option value="07|02">Ilo</option>
			        <option value="09|01">Iquitos</option>
			        <option value="03|04">Islay</option>
			        <option value="11|03">Ja&eacute;n</option>
			        <option value="12|03">Juanju&iacute;</option>
			        <option value="07|03">Juliaca</option>
			        <option value="02|06">La Merced</option>        
			        <option value="01|01" >Lima</option>
			        <option value="06|03">Madre de dios</option>
			        <option value="07|04">Moquegua</option>
			        <option value="12|01">Moyobamba</option>
			        <option value="10|04">Nazca</option>
			        <option value="08|04">Otuzco</option>
			        <option value="02|04">Pasco</option>
			        <option value="10|03">Pisco</option>
			        <option value="05|01">Piura</option>
			        <option value="13|01">Pucallpa</option>
			        <option value="07|05">Puno</option>
			        <option value="06|04">Quillabamba</option>
			        <option value="08|05">San pedro de lloc</option>
			        <option value="02|05">Satipo</option>
			        <!--option value="02|06">Selva Central</option-->
			        <option value="06|05">Sicuani</option>
			        <option value="05|02">Sullana</option>
			        <option value="07|01">Tacna</option>
			        <option value="12|02">Tarapoto</option>
			        <option value="02|07">Tarma</option>
			        <option value="02|08">Tingo Mar&iacute;a</option>
			        <option value="08|01">Trujillo</option>
			        <option value="05|03">Tumbes</option>
			        <option value="09|02">Yurimaguas</option>
		</select>

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
