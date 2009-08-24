<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="gob.pe.sunarp.extranet.reportes.controller.*" %>
<HTML>
<HEAD>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
	<script language="JavaScript" src="javascript/util.js"></script>
	<title>Resumen de Ingreso diario</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">


<script language="JavaScript">

	function buscar(){
	
		document.form1.method="POST";
		document.form1.action="/iri/BalanceCajaCajero.do?state=buscar";
		document.form1.submit();
	}

</script>


</HEAD>


<%
String[] arrDays = (String[]) request.getAttribute("arrDays");
String[] arrMonths = (String[]) request.getAttribute("arrMonths");
String[] arrYears = (String[]) request.getAttribute("arrYears");

String selectedDay = (String) request.getAttribute("selectedDay");
String selectedMonth = (String) request.getAttribute("selectedMonth");
String selectedYear = (String) request.getAttribute("selectedYear");

%>


<BODY>
<table cellspacing=0 class=titulo>
   <tr>
	<td height="20">
		<FONT COLOR="black">REPORTES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Balance de Cajas</FONT>
	</td>
  </tr>
</table>
<br>

<form name="form1" method="post" class="formulario">

<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
		<th colspan="10">REPORTE DE BALANCE DE CAJA</th>
  </tr>
  <tr>
		<td colspan="10" height="6"></td>
  </tr>
  <tr>
	<td>&nbsp;</td>
	<td><b>Fecha</b></td>
    <td width="43"></td>
    <td width="35"></td>
    <td width="38"></td>
    <td width="29"></td>
    <td width="60"></td>
    <td width="123"></td>
    <td width="59"></td>
	<td>&nbsp;</td>
  </tr>
  <tr>
    <td width="10"></td>
    <td width="30" align="center">d&iacute;a</td>
    <td width="43">
    	<SELECT size="1" name="dia">
			<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
		</SELECT>
    </td>
    <td align="center" width="35">mes</td>
    <td width="38">
    	<SELECT size="1" name="mes">
			<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
    	</SELECT>
    </td>
    <td align="center" width="29">año</td>
    <td width="60">
    	<SELECT size="1" name="anio">
			<% for (int w = 0; w < arrYears.length; w++ ) { %>
    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedYear)){%>selected<%}%>><%=arrYears[w]%></option>
    		<% } %>	
		</SELECT>
	</td>
    <td width="123"></td>
    <td valign="middle" width="59">
    	<A href="javascript:buscar();" onmouseover="javascript:mensaje_status('Buscar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    		<IMG border="0" src="images/btn_buscar.gif" width="83" height="25">
    	</A>	
    </td>
  </tr>
  <tr>
	  <td width="10"></td>
	  <td colspan="7"></td>
	  <td width="59"><div align="center"></div></td>
  </tr>
</table>

</form>

<script language="JavaScript">
<% String mensajeError = (String) request.getAttribute("mensajeError"); 
   if(mensajeError != null){%>
		alert("<%=mensajeError%>");
<% } %>
</script>	

</BODY>



</HTML>
