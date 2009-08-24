<%@ page contentType="text/html;charset=ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

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

<html>
<head><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<title></title>
<script language="javascript">
function validarformulario()
{
    if(!esRangoFecha(sel_Obtener_Texto(document.form1.diainicio),sel_Obtener_Texto(document.form1.mesinicio),sel_Obtener_Texto(document.form1.anoinicio),sel_Obtener_Texto(document.form1.diafin),sel_Obtener_Texto(document.form1.mesfin),sel_Obtener_Texto(document.form1.anofin)) )
    {
    	return false;
    }	

	return true;  
}
function ShowReport(){
	if(validarformulario())
	{
	document.form1.method="POST";
	document.form1.action="/iri/VerReporteIngresos.do?state=verReporte";
	document.form1.submit();
	return true;
	}
	return false;	
}
function Regresa(){

	document.form1.method="POST";
	document.form1.action="/iri/VerReporteIngresos.do";
	document.form1.submit();
	return true;
}
function Exporta(){
	if(validarformulario())
	{
	document.form2.method="POST";
	document.form2.action="/iri/VerReporteIngresos.do?state=exportar";
	document.form2.submit();
	return true;
	}
	return false;		
}
function Organizacion(oficina){
	if(validarformulario())
	{
	document.form2.method="POST";
	document.form2.action="/iri/VerReporteIngresos.do?state=verAbono&entidad=O&oficina=" + oficina;
	document.form2.submit();
	return true;
	}
	return false;		

}
function Individual(oficina){
	if(validarformulario())
	{
	document.form2.method="POST";
	document.form2.action="/iri/VerReporteIngresos.do?state=verAbono&entidad=I&oficina=" + oficina;
	document.form2.submit();
	return true;
	}
	return false;		

}
</script>
</head>

<body>
<br>
<table class="titulo">
	<tr>
		<td><font color="black">ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; </font>Reporte de Ingresos por Extranet</td>
	</tr>
</table>
<br>
<form name="form1">
  <table width="100%" border="0" class="formulario">
    <tr> 
      <th colspan="5"><font size="2">Reporte de Ingresos por Extranet</font>&nbsp;</th>
    </tr>
    <tr> 
      <td valign="middle"></td>
      <td valign="middle"></td>
      <td valign="middle"> </td>
      <td valign="middle"> </td>
      <td valign="middle"></td>
    </tr>
    <tr> 
	    <td valign="middle"><b>Desde</b></td>
	    <td valign="middle"><b>Hasta</b></td>
	    <td valign="middle"></td>
	    <td valign="middle"></td>
	    <td valign="middle"></td>
    </tr>
  <tr>
    <td valign="middle" nowrap>dia <select size="1" name="diainicio">
    		<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedIDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
        	</select>
		mes <select size="1" name="mesinicio">
    		<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedIMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
      	</select>
		a&ntilde;o <select size="1" name="anoinicio">
    		<% for (int w = 0; w < arrYears.length; w++ ) { %>
    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedIYear)){%>selected<%}%>><%=arrYears[w]%></option>
    		<% } %>		
      	</select>
    </td>
    <td valign="middle" nowrap>
      	dia <select size="1" name="diafin">
    		<% for (int w = 0; w < arrDays.length; w++ ) { %>
    			<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedFDay)){%>selected<%}%>><%=arrDays[w]%></option>
    		<% } %>
      	</select>
      	mes <select size="1" name="mesfin">
    		<% for (int w = 0; w < arrMonths.length; w++ ) { %>
    			<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedFMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
    		<% } %>
      </select>
      a&ntilde;o <select size="1" name="anofin">
    		<% for (int w = 0; w < arrYears.length; w++ ) { %>
    			<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedFYear)){%>selected<%}%>><%=arrYears[w]%></option>
    		<% } %>      
      </select>
    </td>
    <td></td>
    <td></td>
    <td><input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" border="0" onClick="return ShowReport();" onmouseover="javascript:mensaje_status('Buscar Ingresos de la Extranet');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
  </tr>
</table>
</form>


<br>
<form name="form2">
<logic:present name="hayData">
	<table class="titulo">
	    <tr >
	    	<td ><font color="#000000">Reporte de Ingresos por Extranet del </font> <bean:write name="fecini"/><font color="#000000"> al </font> <bean:write name="fecfin"/></td>
	    </tr>
	</table>
</logic:present>
<br>

<logic:present name="hayData">
	<logic:equal name="hayData" value="N">
		<center><font color="red" size="2">NO SE HAN REGISTRADO INGRESOS</font></center>
	</logic:equal>
</logic:present>

<logic:present name="hayData">
	<logic:equal name="hayData" value="S">
		<!------------ Guardando Fechas para la paginacion ------------->
		<input type="hidden" name="diainicio" value="<%=request.getAttribute("selectedIDay")%>">
		<input type="hidden" name="mesinicio" value="<%=request.getAttribute("selectedIMonth")%>">
		<input type="hidden" name="anoinicio" value="<%=request.getAttribute("selectedIYear")%>">
		<input type="hidden" name="diafin" value="<%=request.getAttribute("selectedFDay")%>">
		<input type="hidden" name="mesfin" value="<%=request.getAttribute("selectedFMonth")%>">
		<input type="hidden" name="anofin" value="<%=request.getAttribute("selectedFYear")%>">

		<%boolean color = true;%>
		<table class="grilla">
		    <tr> 
		      <th width="8%" align="center" height="12">Oficina Registral</th>
		      <th width="9%" align="center" height="12">Organizaciones</th>
		      <th width="9%" align="center" height="12">Monto </th>
		      <th width="6%" align="center" height="12">Individuales</th>
		      <th width="10%" align="center" height="12">Monto</th>
		      <th width="13%" align="center" height="12">Total</th>
		      <th width="9%" align="center" height="12">Porcentajes (%)</th>
		    </tr>
			<logic:iterate name="lista" id="item" scope="request">
			    <%if(color){%>
			    	<tr class="grilla2"> 
			    <%}else{%>
			    	<tr>
			    <%}%> 
			     <%color = !color;%>
			    
			      <td width="8%" height="19" align="center"><bean:write name="item" property="oficina"/></td>
			      <td width="9%" height="19" align="center"><logic:notEqual name="item" property="montoOrg" value="0"><a href="javascript:Organizacion('<bean:write name="item" property="regPubId"/>|<bean:write name="item" property="oficRegId"/>')" target="_self"><bean:write name="item" property="organizacion"/></a></logic:notEqual><logic:equal name="item" property="montoOrg" value="0">0</logic:equal></td>
			      <td width="9%" height="19" align="right"><bean:write name="item" property="montoOrg"/></td>
			      <td width="6%" height="19" align="center"><logic:notEqual name="item" property="montoIndiv" value="0"><a href="javascript:Individual('<bean:write name="item" property="regPubId"/>|<bean:write name="item" property="oficRegId"/>')" target="_self"><bean:write name="item" property="individual"/></a></logic:notEqual><logic:equal name="item" property="montoIndiv" value="0">0</logic:equal></td>
			      <td width="10%" height="19" align="right"><bean:write name="item" property="montoIndiv"/></td>
			      <td width="13%" height="19" align="right"><bean:write name="item" property="total"/></td>
			      <td width="9%" height="19" align="center"><bean:write name="item" property="porcentaje"/></td>
			    </tr>
		    </logic:iterate>
			<tr>
		      <td width="8%" height="19" align="center">TOTALES</td>
		      <td width="9%" height="19" align="center"><bean:write name="numeroOrg"/></td>
		      <td width="9%" height="19" align="right"><bean:write name="totalmontoOrg"/></td>
		      <td width="6%" height="19" align="center"><bean:write name="numeroIndiv"/></td>
		      <td width="10%" height="19" align="right"><bean:write name="totalmontoIndiv"/></td>
		      <td width="13%" height="19" align="right"><bean:write name="totalgeneral"/></td>
			</tr>
		</table>
	</logic:equal>
</logic:present>

<table align=center width=600>
    <tr> 
      <td> 
		<a href="javascript:Regresa();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border=0 onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
		<logic:present name="hayData">
			<logic:equal name="hayData" value="S">
				<a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border=0 onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
				<a href="javascript:Exporta();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border=0 onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
			</logic:equal>
		</logic:present>
	  </td>
    </tr>
</table>
</form>
</body>
</html>