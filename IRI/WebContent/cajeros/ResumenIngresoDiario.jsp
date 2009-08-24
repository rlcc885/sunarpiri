<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="gob.pe.sunarp.extranet.reportes.controller.*" %>
<HTML>
<HEAD>
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
	<title>Resumen de Ingreso diario</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">

<script language="JavaScript">

	function buscar(){
	
		document.form1.method="POST";
		document.form1.action="/iri/ResumenIngresoTesorero.do?state=buscar";
		document.form1.submit();
	}

</script>


</HEAD>


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

java.util.List listaEdificios = (java.util.List) request.getAttribute("listaEdificios");

%>
<BODY>
<br>
<table cellspacing=0 class=titulo>
   <tr>
	<td>
		<FONT COLOR="black">REPORTES <font size="1">&gt;&gt;</font></FONT><font color="900000">Resumen de Ingreso</FONT>
	</td>
  </tr>
</table>
<br>
<form name="form1" method="post" class="formulario">
<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
    <th colspan="5"><font size="2">Resumen de Ingreso</font></th>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td valign="middle" width="43"><b>Desde</b></td>
    <td valign="middle" width="251">d&iacute;a <SELECT size="1"
			name="diaInicio">
			<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedIDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
			</SELECT>
	mes <SELECT size="1"
			name="mesInicio">
			<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedIMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
    	</select>
	año <SELECT size="1"
			name="anioInicio">
			<% for (int w = 0; w < arrYears.length; w++ ) { %>
    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedIYear)){%>selected<%}%>><%=arrYears[w]%></option>
    		<% } %>	
			</SELECT>
	</td>
    <td valign="middle" width="38"><b>Hasta</b></td>
    <td valign="middle" width="257"> d&iacute;a <SELECT size="1"
			name="diaFin">
			<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedFDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
		</SELECT> &nbsp;mes <SELECT size="1" name="mesFin">
			<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedFMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
		</SELECT> año <SELECT size="1" name="anioFin">
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
	  <td width="43"><strong>Edificio</strong></td>
	  <td width="251">
	  
	  <select size="1" name="edificio">
            <option value="ALL">TODOS</option>
		    <logic:iterate name="listaEdificios" id="edificio" scope="request">
		        <option value="<bean:write name="edificio" property="codigo"/>"><bean:write name="edificio" property="descripcion"/></option>
		    </logic:iterate> 
      </select>

	  </td>
	  <td colspan="3"><div align="center">
	  <A href="javascript:buscar();" onmouseover="javascript:mensaje_status('Buscar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	  <IMG border="0" src="images/btn_buscar.gif" width="83" height="25"> </div></td>
  </tr>
 <tr> </tr>
</table>
</form>
</BODY>
</HTML>
