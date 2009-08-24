<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
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
	//if(document.form1.radio[0].checked==true){	
	//}	
	//if(document.form1.radio[1].checked==true){
	//}
	//if(document.form1.radio[3].checked==true){	
	//}

	return true;  
}
function MuestraResultados(){
	if(validarformulario())
	{
	
	document.form1.action = "/iri/VerReporteMediosPago.do?state=verReporte";
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


function verReporte()
{ 
	document.form1.action = "/iri/VerReporteMediosPago.do?state=verReporte";
	document.form1.submit()	
}

function exportarReporte()
{ 
	document.form1.action = "/iri/VerReporteMediosPago.do?state=exportar";
	document.form1.submit()	
}


</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<BR>
<table cellspacing=0 class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reporte &gt;&gt; </FONT> Medios de Pago</td></tr>
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
<form name="form1" method="post" class="formulario">
<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
    <th width="100%" colspan="5"><font size="2">Reporte de Medios de Pago</font>&nbsp;</th>
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
		      dia&nbsp; <select size="1" name="diainicio">
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
    <td colspan=2>      
			dia<select size="1" name="diafin">
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
	   <input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados();" onmouseover="javascript:mensaje_status('Buscar Medio de Pago');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    </td>
  </tr>
  <tr>
    <td width="90" valign="middle"></td>
    <td width="110" align="right"><b>Tipo de Persona</b></td>
    <td  colspan= 3 nowrap >
    <input type="radio" value="1"<logic:equal name="evolucionBean" property="tipoPersona" value="1">checked</logic:equal> name="tipoPersona"><b>Naturales</b><input type="radio" value="0" <logic:equal name="evolucionBean" property="tipoPersona" value="0">checked</logic:equal> name="tipoPersona"><b>Juridicas</b><input type="radio" value="2" <logic:equal name="evolucionBean" property="tipoPersona" value="2">checked</logic:equal> name="tipoPersona"><b>Ambas</b></td>
  </tr>
  
</table>  
</form>
<logic:present name="mediosPago">
<logic:equal name="evolucionBean" property="hayRegistros" value="0">  
	<br>
	<br>

	<div align="center"><bean:write name="evolucionBean" property="mensajeError" /></div>

</logic:equal>

<br>
<table class=titulo>
<tr><td>
        <P><FONT color=black>Reporte de Medios de Pagos del</FONT> 
        &nbsp;<bean:write name="evolucionBean" property="str_Date_Inicio"/>
        <font color="black">al</font>&nbsp;<bean:write name="evolucionBean" property="str_Date_Fin"/>
        <font color="black">Tipo Persona :</FONT><bean:write name="evolucionBean" property="tipoPersonaName"/> </P></td></tr>
</table>
<br>

        <table class=grilla >
          <tr> 
            <th width="9%" align="middle" height="19"></th>
            <th width="3%" align="middle" height="12">Efectivo</th>
            <th width="3%" align="middle" height="12">Credito</th>
            <th width="2%" align="middle" height="12">Debito</th>
            <th width="7%" align="middle" height="12">Total </th>
            <th width="7%" align="middle" height="12">Porcentajes </th>
            <th width="3%" align="middle" height="12">Efectivo S/.</th>
            <th width="3%" align="middle" height="12">Credito <span id="reporte5">S/.</span></th>
            <th width="2%" align="middle" height="12">Debito <span id="reporte6">S/.</span></th>
            <th width="6%" align="middle" height="12">Total</th>
            <th width="6%" align="middle" height="12">Porcentajes</th>
 
          </tr>
          
          <logic:iterate id ="consulta" name="mediosPago" scope="request">
          <tr class=grilla2 > 
            <th width="9%" align="middle" height="19"><bean:write name="consulta" property="zona" /></th>
            <td width="3%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="efectivo" /></font></td>
            <td width="3%" align="middle" height="19" bgcolor="#ffffff"><font color="#000000"><bean:write name="consulta" property="credito" /></font></td>
            <td width="2%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="debito" /></font></td>
            <td width="7%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="total" /></font></td>
            <td width="7%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="porcentaje" /></font></td>
            <td width="7%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="efectivoSoles" /></font></td>
            <td width="5%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="creditoSoles" /></font></td>
            <td width="5%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="debitoSoles" /></font></td>
            <td width="6%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="totalSoles" /></font></td>
            <td width="6%" align="middle" height="19" bgcolor="#e2e2e2"><font color="#000000"><bean:write name="consulta" property="porcentajeSoles" /></font></td>
          </tr>
   	     </logic:iterate>
          
          <tr> 
            <th width="8%" align="middle" height="12" bgcolor="#ffffff">TOTAL</th>
            <td width="3%" align="middle" height="24" bgcolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalEfectivo" /></font></td>
            <td width="3%" align="middle" height="24" bgcolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalCredito" /></font></td>
            <td width="2%" align="middle" height="24" bgcolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalDebito" /></font></td>
            <td width="7%" align="middle" height="24" bgcolor="#ffffff" bordercolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalTotal1" /></font></td>
            <td width="7%" align="middle" height="24" bgcolor="#ffffff" bordercolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalPorcentaje" /></font></td>
            <td width="7%" align="middle" height="24" bgcolor="#ffffff" bordercolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalEfectivoSoles" /></font></td>
            <td width="5%" align="middle" height="24" bgcolor="#ffffff"> <p align="center"><font color="#000000">&nbsp;&nbsp; <bean:write name="totalMediosPago" property="totalCreditoSoles" /></font></p></td>
            <td width="5%" align="middle" height="24" bgcolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalDebitoSoles" /></font></td>
            <td width="6%" align="middle" height="24" bgcolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalTotal2" /></font></td>
            <td width="6%" align="middle" height="24" bgcolor="#ffffff"><font color="#000000"><bean:write name="totalMediosPago" property="totalPorcentajeSoles" /></font></td>
          </tr>

        </table>
        <table style="WIDTH: 597px; HEIGHT: 31px">
          <tr> 
            <td width="66%" align="middle" height="12" bgcolor="#ffffff" colspan="11"> 
              <div align="right"><A href="javascript:history.back();"><IMG src="<%=request.getContextPath()%>/images/btn_regresa.gif" border=0 onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
                <A href="javascript:window.print();"><IMG src="<%=request.getContextPath()%>/images/btn_print.gif" border=0 onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
                <A href="javascript:exportarReporte();"><IMG src="<%=request.getContextPath()%>/images/btn_exportar.gif" border=0 onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
              </div>
            </td>
          </tr>
        </table>
</logic:present>
<html:link page="/DiaNoLabo.do?state=muestraFormulario">DIA</html:link>
<html:link page="/DiaNoLabo.do?state=muestraFiltroConsulta">Consulta</html:link>
<html:link page="/Denominacion.do?state=muestraForm">Denominacion</html:link>
<html:link page="/Denominacion.do?state=muestraFiltro">Reporte Denominacion</html:link>
<html:link page="/solicitudes/denominacion/FrmConSol.jsp">Consulta Solicitudes Reserva</html:link>
<html:link page="/IndicePJ.do?state=formBusqueda">Buscador</html:link>
<html:link page="/buscadorPJ/index.jsp">BuscadorLucene</html:link>
</body>
</html>