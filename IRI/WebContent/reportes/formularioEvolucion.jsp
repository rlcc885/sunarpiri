<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>


<%@ page import="gob.pe.sunarp.extranet.reportes.beans.*" %>

<%
String x = (String) request.getAttribute("modo");
int modo=0;
if (x!=null)
	modo = Integer.parseInt(x);
%>

<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title>
<script language="javascript">

function getSelectedRadio(buttonGroup) 
{
   // returns the array number of the selected radio button or -1 if no button is selected
   if (buttonGroup[0]) 
   { // if the button group is an array (one button is not an array)
      for (var i=0; i<buttonGroup.length; i++) {
         if (buttonGroup[i].checked) {
            return i
         }
      }
   } else 
   {
      if (buttonGroup.checked) { return 0; } // if the one button is checked, return zero
   }
   // if we get to this point, no radio button is selected
   return -1;
} // Ends the "getSelectedRadio" function

function getSelectedRadioValue(buttonGroup) 
{
   // returns the value of the selected radio button or "" if no button is selected
   var i = getSelectedRadio(buttonGroup);
   if (i == -1) 
   {
      return "";
   } else 
   {
      if (buttonGroup[i]) { // Make sure the button group is an array (not just one button)
         return buttonGroup[i].value;
      } else { // The button group is just the one button, and it is checked
         return buttonGroup.value;
      }
   }
} // Ends the "getSelectedRadioValue" function

function validarformulario(){
    if(!esRangoFecha(sel_Obtener_Texto(document.form1.diainicio),sel_Obtener_Texto(document.form1.mesinicio),sel_Obtener_Texto(document.form1.anoinicio),sel_Obtener_Texto(document.form1.diafin),sel_Obtener_Texto(document.form1.mesfin),sel_Obtener_Texto(document.form1.anofin)) ){
    	return false;
    }
    return true;
}	
function MuestraResultados()
{
	if(validarformulario())
	{
		var tipoReporte = getSelectedRadioValue(document.form1.tipoReporte);
		
		if(tipoReporte == "1")
			document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion1";
		if(tipoReporte == "2")
			document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion2";
		if(tipoReporte == "3")
			document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion3";
		
		document.form1.fgPorc.value = "0";
		document.form1.submit()	
		return true;		
	}
	return false;			

}



function VerPorcentajes()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion1";
	document.form1.fgPorc.value = "1";
	document.form1.submit()	
	return ;
	}
	return ;
}

function VerPorcentajes2()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion2";
	document.form1.fgPorc.value = "1";
	document.form1.submit()	
	return ;
	}
	return ;
}

function VerValores()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion1";
	document.form1.fgPorc.value = "0";
	document.form1.submit()	
	return ;
	}
	return ;
}

function VerValores2()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion2";
	document.form1.fgPorc.value = "0";
	document.form1.submit()	
	return ;
	}
	return ;
}

function exportar1()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=exportarReporteEvolucion1";
	document.form1.fgPorc.value = "0";
	document.form1.submit()	
	return ;
	}
	return ;
}

function exportar1Porc()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=exportarReporteEvolucion1";
	document.form1.fgPorc.value = "1";
	document.form1.submit()	
	return ;
	}
	return ;
}


function exportar2()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=exportarReporteEvolucion2";
	document.form1.fgPorc.value = "0";
	document.form1.submit()	
	return ;
	}
	return ;
}

function exportar2Porc()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=exportarReporteEvolucion2";
	document.form1.fgPorc.value = "1";
	document.form1.submit()	
	return ;
	}
	return ;
}

function exportar3Consultas()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=exportarReporteEvolucion3";
	document.form1.fgPorc.value = "1";
	document.form1.submit()	
	return ;
	}
	return ;
}

function exportar3Abonos()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=exportarReporteEvolucion3";
	document.form1.fgPorc.value = "0";
	document.form1.submit()	
	return ;
	}
	return ;
}



function VerAbonos()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion3";
	document.form1.fgPorc.value = "1";
	document.form1.submit()	
	return ;
	}
	return ;
}

function VerConsultas()
{ 
	if(validarformulario())
	{
	document.form1.action = "/iri/VerReporteEvolucion.do?state=verReporteEvolucion3";
	document.form1.fgPorc.value = "0";
	document.form1.submit()	
	return ;
	}
	return ;
}



</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>






<body bgcolor="#FFFFFF">
<br>
<TABLE class=titulo>
    <TR>
        <TD><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; </FONT> Reporte de evoluci&oacute;n de la Extranet</TD>
    </TR>
</TABLE>
<br>
<%
EvolucionBean evolucionBean = (EvolucionBean) request.getAttribute("evolucionBean");

String tipoPersona="2";

if (evolucionBean!=null)
		tipoPersona = evolucionBean.getTipoPersona();
%>


<form name="form1" method="post">
<input type="hidden" name="fgPorc" value="">
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
<table class=formulario>
    <tr>
        <td  colspan="3"><b>Desde</b></td>
        <td width="51%" ><b>Hasta</b></td>
        <td width="11%" ></td>
    </tr>

    <tr>
    <td nowrap colspan=2>&nbsp; 
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
    <td colspan=2>&nbsp;
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

    <td >
        <input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados();" onmouseover="javascript:mensaje_status('Evolucion de la Extranet');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    </td>
    </tr>

    
    <tr>
        <td  colspan="5"><b> TIPO PERSONA</b> <input type="radio" value="1" name="tipoPersona" <%if (tipoPersona.equals("1")) {%>  checked <%}%>> NATURAL&nbsp; 
											  <input type="radio" value="0" name="tipoPersona" <%if (tipoPersona.equals("0")) {%>  checked <%}%>> JURIDICA&nbsp; 
											  <input type="radio" value="2" name="tipoPersona" <%if (tipoPersona.equals("2")) {%>  checked <%}%>> TODOS
        </td>
    </tr>
    
    <tr>
        <td width="10%" >&nbsp;</td>
        <td width="4%"  bgcolor="#ffffff"><input type="radio" value="1" name="tipoReporte" <logic:equal name="evolucionBean" property="indicador" value="1">  checked </logic:equal>></td>
        <td  colspan="3">Numero de Consultas efectuadas fuera de Zona Registral vs Total Consultas efectuadas por usuarios de una Zona Registral.</td>
    </tr>
    <tr>
        <td >&nbsp;<b> Indicador</b></td>
        <td width="4%"  bgcolor="#ffffff"><input type="radio" value="2" name="tipoReporte" <logic:equal name="evolucionBean" property="indicador" value="2">  checked </logic:equal>></td>
        <td colspan="3">Valor de las consultas efectuadas fuera de la zona registral Vs. Valor total de las consultas efectuadas por los usuarios de una determinada zona registral.</td>
    </tr>
    <tr>
        <td ></td>
        <td width="4%"  bgcolor="#ffffff"><input type="radio" value="3" name="tipoReporte" <logic:equal name="evolucionBean" property="indicador" value="3">  checked </logic:equal>></td>
        <td  colspan="2">Ranking de usuarios que m&aacute;s consultas y m&aacute;s ingresos generan en el sistema.</td>
        <td width="11%" ></td>
    </tr>
    
</table>
<!--<hr>-->
<br>
<logic:present name="consultasEfectuadasVector"> <br>
<table class=titulo>
    <tr>
        <td>N&uacute;mero de consultas efectuadas fuera de la zona registral Vs. N&uacute;mero total de consultas efectuadas por los usuarios de una determinada zona registral</td>
    </tr>
</table>
<br>
<table class="grilla">
    <tr>
        <td width=48 height="36"><b><font size="1">Numero<br>de<br>Consultas</font></b></td>
        <td width=48 height="36"><b><font size="1">Zona Registral Destino</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR1</font></b></td>
        <td width=40 height="36"><b><font size="1">ZR2</font></b></td>
        <td width=28 height="36"><b><font size="1">ZR3</font></b></td>
        <td width=39 height="36"><b><font size="1">ZR4</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR5</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR6</font></b></td>
        <td width=36 height="36"><b><font size="1">ZR7</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR8</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR9</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR10</font></b></td>
        <td width=26 height="36"><b><font size="1">ZR11</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR12</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR13</font></b></td>
        <td width=30 height="36"><b><font size="1">Total</font></b></td>
        <td width=25 height="36"><b><font size="1">I</font></b></td>
    </tr>
    <tr>
        <td colspan=2 height="11">Zona Registral<br>Origen</td>
        <td height="11" colspan="15">&nbsp;</td>
    </tr>
    
    <logic:iterate id ="consulta" name="consultasEfectuadasVector" scope="request">    
		
		
    <tr>
        <td vAlign=top bgColor=#ffffff colSpan=2 height=10><b><font size=1><bean:write name="consulta" property="zonaRegistral" /></FONT></B></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg1"  /></FONT></TD>
        <td width=40 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg2"  /></FONT></TD>
        <td width=28 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg3"  /></FONT></TD>
        <td width=39 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg4"  /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg5"  /></FONT></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg6"  /></FONT></TD>
        <td width=36 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg7"  /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg8"  /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg9"  /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg10" /></FONT></TD>
        <td width=26 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg11" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg12" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg13" /></FONT></TD>
        <td width=30 bgColor=#ffffff height=10><bean:write name="consulta" property="totalZonaReg" /></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="porcentZonaReg" /></FONT></TD>
    </TR>
</logic:iterate>

</table>


<table width="600">
    <tr>
        <td>
        <div align="center"><A href="javascript:VerPorcentajes()" onmouseover="javascript:mensaje_status('Ver Porcentajes');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Porcentajes</A></div>
        </td>
    </tr>
</table>

<table class="tablasinestilo">
    <tr> 
      <TD height=24  bgColor=#ffffff><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=history.go(-1) type=button value=Regresar name=back-->
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Imprimir name=btnimprime-->
        <a href="javascript:exportar1();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Exportar name=btnimprime1-->
      </TD>
    </TR>
  </TABLE>
</logic:present>


<logic:present name="consultasEfectuadasVectorPorc">
<table class="titulo">
    <tr>
        <td>Porcentaje de consultas efectuadas fuera de la zona registral Vs. N&uacute;mero total de consultas efectuadas por los usuarios de una determinada zona registral</td>
    </tr>
</table>
<!--
<table>
    <tr>
        <td>
        <center><IMG src="/iri/images/indicador1_porc.gif"></CENTER>
        </TD>
    </TR>
</TABLE>
-->
<br>

<table class="grilla">
    <tr>
        <td width=48 height="36"><b><font size="1">Numero<br>de<br>Consultas</font></b></td>
        <td width=48 height="36"><b><font size="1">Zona Registral Destino</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR1</font></b></td>
        <td width=40 height="36"><b><font size="1">ZR2</font></b></td>
        <td width=28 height="36"><b><font size="1">ZR3</font></b></td>
        <td width=39 height="36"><b><font size="1">ZR4</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR5</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR6</font></b></td>
        <td width=36 height="36"><b><font size="1">ZR7</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR8</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR9</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR10</font></b></td>
        <td width=26 height="36"><b><font size="1">ZR11</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR12</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR13</font></b></td>
        <td width=40 height="36"><b><font size="1">Total</font></b></td>
    </tr>
    <tr>
        <td colspan=2 height="11"><b><font size="1">Zona Registral<br>Origen</font></b></td>
        <td height="11" colspan="15">&nbsp;</td>
    </tr>
    
    
    
    
    <logic:iterate id ="consulta" name="consultasEfectuadasVectorPorc" scope="request">    
		
		
    <tr>
        <td vAlign=top bgColor=#ffffff colSpan=2 height=10><b><font size=1><bean:write name="consulta" property="zonaRegistral" /></FONT></B></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg1" /></FONT></TD>
        <td width=40 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg2" /></FONT></TD>
        <td width=28 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg3" /></FONT></TD>
        <td width=39 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg4" /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg5" /></FONT></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg6" /></FONT></TD>
        <td width=36 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg7" /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg8" /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg9" /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg10" /></FONT></TD>
        <td width=26 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg11" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg12" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg13" /></FONT></TD>
        <td width=30 bgColor=#ffffff height=10><bean:write name="consulta" property="totalZonaReg" /></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="porcentZonaReg" /></FONT></TD>
    </TR>
    
</logic:iterate>

</table>


<table width="600">
    <tr>
        <td>
        <div align="center"><A href="javascript:VerValores()" onmouseover="javascript:mensaje_status('Ver Valores');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Valores</A></div>
        </td>
    </tr>
</table>
<table class="tablasinestilo">
    <tr> 
      <TD height=24  bgColor=#ffffff><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=history.go(-1) type=button value=Regresar name=back-->
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Imprimir name=btnimprime-->
        <a href="javascript:exportar1Porc();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Exportar name=btnimprime1-->
      </TD>
    </TR>
  </TABLE>
</logic:present>

<logic:present name="consultasEfectuadas2Vector"> <br>
<table class=titulo>
    <tr>
        <td>Valor de las consultas efectuadas fuera de la zona registral Vs. Valor total de las consultas efectuadas por los usuarios de una determinada zona registral</td>
    </tr>
</table>


<br>



<table class="grilla">
    <tr>
        <td width=48 height="36"><b><font size="1">Numero<br>de<br>Consultas</font></b></td>
        <td width=48 height="36"><b><font size="1">Zona Registral Destino</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR1</font></b></td>
        <td width=40 height="36"><b><font size="1">ZR2</font></b></td>
        <td width=28 height="36"><b><font size="1">ZR3</font></b></td>
        <td width=39 height="36"><b><font size="1">ZR4</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR5</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR6</font></b></td>
        <td width=36 height="36"><b><font size="1">ZR7</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR8</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR9</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR10</font></b></td>
        <td width=26 height="36"><b><font size="1">ZR11</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR12</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR13</font></b></td>
        <td width=30 height="36"><b><font size="1">Total</font></b></td>
        <td width=25 height="36"><b><font size="1">I</font><font size="1"></font></b></td>
    </tr>
    <tr>
        <td colspan=2 height="11"><b><font size="1">Zona Registral<br>Origen</font></b></td>
        <td height="11" colspan="15">&nbsp;</td>
    </tr>
    <logic:iterate id ="consulta" name="consultasEfectuadas2Vector" scope="request">    
		
		
    <tr>
        <td vAlign=top bgColor=#ffffff colSpan=2 height=10><b><font size=1><bean:write name="consulta" property="zonaRegistral" /></FONT></B></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg1" /></FONT></TD>
        <td width=40 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg2" /></FONT></TD>
        <td width=28 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg3" /></FONT></TD>
        <td width=39 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg4" /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg5" /></FONT></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg6" /></FONT></TD>
        <td width=36 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg7" /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg8" /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg9" /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg10" /></FONT></TD>
        <td width=26 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg11" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg12" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg13" /></FONT></TD>
        <td width=30 bgColor=#ffffff height=10><bean:write name="consulta" property="totalZonaReg" /></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="porcentZonaReg" /></FONT></TD>
    </TR>
</logic:iterate>

</table>


<table width="600">
    <tr>
        <td>
        <div align="center"><A href="javascript:VerPorcentajes2()" onmouseover="javascript:mensaje_status('Ver Porcentajes');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Porcentajes</A></div>
        </td>
    </tr>
</table>
<table class="tablasinestilo">
    <tr> 
      <TD height=24  bgColor=#ffffff><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=history.go(-1) type=button value=Regresar name=back-->
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Imprimir name=btnimprime-->
        <a href="javascript:exportar2();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Exportar name=btnimprime1-->
      </TD>
    </TR>
  </TABLE>
</logic:present>

<logic:present name="consultasEfectuadas2VectorPorc">
<table class="titulo">
    <tr>
        <td>Valor de las consultas (Porcentajes) efectuadas fuera de la zona registral Vs. Valor total de las consultas efectuadas por los usuarios de una determinada zona registral</td>
    </tr>
</table>
<!--
<table>
    <tr>
        <td>
        <center><IMG src="/iri/images/indicador1_porc.gif"></CENTER>
        </TD>
    </TR>
</TABLE>
-->
<br>


<table class="grilla">
    <tr>
        <td width=48 height="36"><b><font size="1">Numero<br>de<br>Consultas</font></b></td>
        <td width=48 height="36"><b><font size="1">Zona Registral Destino</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR1</font></b></td>
        <td width=40 height="36"><b><font size="1">ZR2</font></b></td>
        <td width=28 height="36"><b><font size="1">ZR3</font></b></td>
        <td width=39 height="36"><b><font size="1">ZR4</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR5</font></b></td>
        <td width=38 height="36"><b><font size="1">ZR6</font></b></td>
        <td width=36 height="36"><b><font size="1">ZR7</font></b></td>
        <td width=32 height="36"><b><font size="1">ZR8</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR9</font></b></td>
        <td width=31 height="36"><b><font size="1">ZR10</font></b></td>
        <td width=26 height="36"><b><font size="1">ZR11</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR12</font></b></td>
        <td width=25 height="36"><b><font size="1">ZR13</font></b></td>
        <td width=40 height="36"><b><font size="1">Total</font></b></td>
    </tr>
    <tr>
        <td colspan=2 height="11"><b><font size="1">Zona Registral<br>Origen</font></b></td>
        <td height="11" colspan="15">&nbsp;</td>
    </tr>
    
    
    <logic:iterate id ="consulta" name="consultasEfectuadas2VectorPorc" scope="request">    
		
		
    <tr>
        <td vAlign=top bgColor=#ffffff colSpan=2 height=10><b><font size=1><bean:write name="consulta" property="zonaRegistral" /></FONT></B></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg1" /></FONT></TD>
        <td width=40 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg2" /></FONT></TD>
        <td width=28 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg3" /></FONT></TD>
        <td width=39 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg4" /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg5" /></FONT></TD>
        <td width=38 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg6" /></FONT></TD>
        <td width=36 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg7" /></FONT></TD>
        <td width=32 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg8" /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg9" /></FONT></TD>
        <td width=31 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg10" /></FONT></TD>
        <td width=26 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg11" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg12" /></FONT></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="destReg13" /></FONT></TD>
        <td width=30 bgColor=#ffffff height=10><bean:write name="consulta" property="totalZonaReg" /></TD>
        <td width=25 bgColor=#ffffff height=10><font size=1><bean:write name="consulta" property="porcentZonaReg" /></FONT></TD>
    </TR>
    
</logic:iterate>

</table>


<table width="600">
    <tr>
        <td>
        <div align="center"><A href="javascript:VerValores2()" onmouseover="javascript:mensaje_status('Ver Valores');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Valores</A></div>
        </td>
    </tr>
</table>
<table class="tablasinestilo">
    <tr> 
      <TD height=24  bgColor=#ffffff><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=history.go(-1) type=button value=Regresar name=back-->
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Imprimir name=btnimprime-->
        <a href="javascript:exportar2Porc();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Exportar name=btnimprime1-->
      </TD>
    </TR>
  </TABLE>
</logic:present>

<!-- Reporte 3 -->

<logic:equal name="evolucionBean" property="hayRegistros" value="0">  
	<br>
	<br>
	
	<div align="center"><bean:write name="evolucionBean" property="mensajeError" /></div>

</logic:equal>

<logic:present name="arrayConsultasPeNatu"> <br>
<table class="titulo">
    <tr>
        <td>Ranking de Usuarios de la Extranet</td>
    </tr>
</table>
<table class=grilla>
    <COLGROUP>
        <col style="WIDTH: 20pt" width=26>
        <col style="WIDTH: 23pt" width=31>
        <col style="WIDTH: 93pt" width=124>
        <col style="WIDTH: 48pt" span=4 width=64>
    <tr>
        <th class=xl24 style="PADDING-BOTTOM: 0in; WIDTH: 20pt; PADDING-TOP: 0in; HEIGHT: 35.25pt"  width=26 height=47 rowSpan=2>Nro</TH>
        <th class=xl24 style="WIDTH: 23pt" width=31 rowSpan=2>Tipo</TH>
        <th class=xl24 style="WIDTH: 93pt" width=124 rowSpan=2>Nombre/Razon Social</TH>
        <th class=xl25 style="BORDER-LEFT: medium none; WIDTH: 144pt" width=192 colSpan=3>Numero de Consultas</TH>
        <th class=xl25 rowSpan=2>Ingresos</TH>
    </TR>
    <tr>
        <th class=xl26 style="BORDER-TOP: medium none; BORDER-LEFT: medium none; WIDTH: 48pt; HEIGHT: 22.5pt" width=64 height=30>Titulos</TH>
        <th class=xl26 style="BORDER-TOP: medium none; BORDER-LEFT: medium none; WIDTH: 48pt" width=64>Partidas</TH>
        <th class=xl25 style="BORDER-TOP: medium none; BORDER-LEFT: medium none; WIDTH: 48pt" width=64>Total</TH>
    </TR>
    
    <logic:iterate id ="consultaPeNat" name="arrayConsultasPeNatu" scope="request">
    
    <tr>
        <td class=xl27 align=right width=26 bgColor="<bean:write name="consultaPeNat" property="colorCelda" />" height=17 ><b><bean:write name="consultaPeNat" property="posicion"/></B></TD>
        <td class=xl27 width=31             bgColor="<bean:write name="consultaPeNat" property="colorCelda" />"> <b><bean:write name="consultaPeNat" property="tipoPersona"/></B></TD>
        <td class=xl28 width=150 			  bgColor="<bean:write name="consultaPeNat" property="colorCelda" />"> <b><bean:write name="consultaPeNat" property="nombreRazon"/></B></TD>
        <td class=xl27 align=right width=64 bgColor="<bean:write name="consultaPeNat" property="colorCelda" />"> <b><bean:write name="consultaPeNat" property="numTitulos"/></B></TD>
        <td class=xl27 align=right width=64 bgColor="<bean:write name="consultaPeNat" property="colorCelda" />"> <b><bean:write name="consultaPeNat" property="numPartidas"/></B></TD>
        <td class=xl27 align=right width=64 bgColor="<bean:write name="consultaPeNat" property="colorCelda" />"> <b><bean:write name="consultaPeNat" property="totalConsultas"/></B></TD>
        <td class=xl27 align=right width=64 bgColor="<bean:write name="consultaPeNat" property="colorCelda" />"> <b><bean:write name="consultaPeNat" property="ingreso"/></B></TD>
    </TR>
</logic:iterate>
    </TABLE>

<table class="tablasinestilo">
    <tr> 
      <TD height=24 bgColor=#ffffff><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=history.go(-1) type=button value=Regresar name=back-->
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Imprimir name=btnimprime-->
        <a href="javascript:exportar3Abonos();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Exportar name=btnimprime1-->
      </TD>
    </TR>
  </TABLE>
</logic:present>

<%
if (modo==30) { %>
<table class="titulo">
    <tr>
        <td>
        <div align="center"><A href="javascript:VerAbonos()" onmouseover="javascript:mensaje_status('Ver Abonos');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Ingresos por Abonos</A></div>
        </td>
    </tr>
</table>
<% } %>

<!-- Reporte 3.2 -->

<logic:present name="arrayConsultasTotalAbonos"> <br>
<table class="titulo">
    <tr>
        <td>Ranking de Usuarios de la Extranet por Total de Abonos</td>
    </tr>
</table>
<table class=grilla>
    <COLGROUP>
        <col style="WIDTH: 20pt" width=26>
        <col style="WIDTH: 23pt" width=31>
        <col style="WIDTH: 93pt" width=124>
        <col style="WIDTH: 48pt" span=4 width=64>
    <tr>
            <th class=xl30 style="WIDTH: 25pt; HEIGHT: 25.5pt" width=33 height=34>Nro</TH>
            <th class=xl30 style="WIDTH: 26pt" width=35>Tipo</TH>
            <th class=xl30 style="WIDTH: 116pt" width=155>Nombre/Razon Social</TH>
            <th class=xl31 style="BORDER-LEFT: medium none; WIDTH: 66pt" width=88>Total Abonos</TH>
     </tr>
    
    <logic:iterate id ="consultaAbonos" name="arrayConsultasTotalAbonos" scope="request">
    
    <tr>
        <td class=xl27 align=right width=26 bgColor="<bean:write name="consultaAbonos" property="colorCelda" />" height=17><b><bean:write name="consultaAbonos" property="posicion"/></B></TD>
        <td class=xl27 width=31 bgColor="<bean:write name="consultaAbonos" property="colorCelda" />"><b><bean:write name="consultaAbonos" property="tipoPersona"/></B></TD>
        <td class=xl28 width=150 			 bgColor="<bean:write name="consultaAbonos" property="colorCelda" />"><b><bean:write name="consultaAbonos" property="nombreRazon"/></B></TD>
        <td class=xl27 align=right width=64 bgColor="<bean:write name="consultaAbonos" property="colorCelda" />"> <b><bean:write name="consultaAbonos" property="totalConsultas"/></B></TD>
    </TR>
</logic:iterate>
    </TABLE>
<table class="titulo">
    <tr>
        <td>
        <div align="center"><A href="javascript:VerConsultas()" onmouseover="javascript:mensaje_status('Ver Consultas');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver N&uacute;mero de Consultas</A></div>
        </td>
    </tr>
</table>
<table class="tablasinestilo">
    <tr> 
      <TD height=24 bgColor=#ffffff><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border="0" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=history.go(-1) type=button value=Regresar name=back-->
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border="0" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Imprimir name=btnimprime-->
        <a href="javascript:exportar3Consultas();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border="0" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a><!--INPUT onclick=ShowReport() type=button value=Exportar name=btnimprime1-->
      </TD>
    </TR>
  </TABLE>
</logic:present>



</form>

</body>
</html>