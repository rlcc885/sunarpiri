<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
String x = (String) request.getAttribute("perfilId");
long perfilId = Long.parseLong(x);

String userId="";
x = (String) request.getAttribute("userId");
if (x!=null)
	userId = x;

String str_Criterio_Busqueda="";
String str_Variable_Busqueda="";

x = (String) request.getAttribute("str_Criterio_Busqueda");
if (x!=null)
	str_Criterio_Busqueda=x;
x = (String) request.getAttribute("str_Variable_Busqueda");
if (x!=null)
	str_Variable_Busqueda=x;	
	
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
    
<%if (perfilId==Constantes.PERFIL_ADMIN_GENERAL ||
      perfilId==Constantes.PERFIL_TESORERO ||
      perfilId==Constantes.PERFIL_CAJERO ||
      perfilId==Constantes.PERFIL_ADMIN_JURISDICCION ||
      perfilId==Constantes.PERFIL_INTERNO)
  {%>  
	if(document.form1.radio[0].checked==true)
	{	
		if(esVacio(document.form1.razon_social.value) && esVacio(document.form1.ruc.value))
		{
			alert("Por favor ingrese al menos la Razón Social o el Número del Documento");
			document.form1.razon_social.focus();		
			return false;
		}
		if(!esVacio(document.form1.ruc.value))
		{
			if(!esEntero(document.form1.ruc.value) || !esMayor(document.form1.ruc.value,8) || !esEnteroMayor(document.form1.ruc.value,1))
			{
				alert("Por favor ingrese correctamente el Número del Documento");
				document.form1.ruc.focus();		
				return false;
			}
		}
	}	
	if(document.form1.radio[1].checked==true)
	{
		if(esVacio(document.form1.userId.value))
		{
			alert("Por favor ingrese el Usuario");
			document.form1.userId.focus();
			return false;
		}
	}
<%}%>
  
<%if (perfilId==Constantes.PERFIL_ADMIN_ORG_EXT) {%>
		/*
		if(esVacio(document.form1.userId.value)){
			alert("Por favor ingrese el Usuario");
			document.form1.userId.focus();
			return false;
		}
		*/
<%}%>  
	return true;  
}

function MuestraResultados()
{
	if(validarformulario())
	{
		document.form1.action = "/iri/ReporteMovimientos.do?state=verReporte";
		document.form1.submit();
		return true;
	}
	return false;
}

function MuestraResultadosDetalle(a,b,c,d,e)
{
	document.form2.peJuriId.value = a;
	document.form2.personaId.value = b;
	document.form2.lpId.value = c;

	document.form2.rz_aux.value = d;
	document.form2.ndoc_aux.value = e;
	document.form2.submit();
	return true;
}


function ExportarResultados()
{
	if(validarformulario())
	{
		document.form1.action = "/iri/ReporteMovimientos.do?state=exportarReporte";
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

function Transacciones()
{
<%if (str_Variable_Busqueda.trim().length()>0) { %> 	
		doCambiaRadio(document.form1.radio,'2');
		document.form1.userId.value="<%=str_Variable_Busqueda%>";
		obj_mayuscula(document.form1.userId);
<%}%> 

	if(esRangoFecha(sel_Obtener_Texto(document.form1.diainicio),sel_Obtener_Texto(document.form1.mesinicio),sel_Obtener_Texto(document.form1.anoinicio),sel_Obtener_Texto(document.form1.diafin),sel_Obtener_Texto(document.form1.mesfin),sel_Obtener_Texto(document.form1.anofin)) )
	{
		document.form1.method="POST";
		document.form1.action="/iri/VerReporteTransacciones.do?primeravez=NO&pagina=1";
		document.form1.submit();
		return;
	}
	return;
}	

</script>



<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<BR>
<table cellspacing=0 class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; </FONT> Movimientos</td></tr>
</table>
<br>
<form name="form1" method="post" class="formulario">
<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
    <th width="100%" colspan="5"><font size="2">Reporte de Movimientos</font>&nbsp;</th>
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
      &nbsp;dia 
      <select size="1" name="diafin">
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
	   <input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados();" onmouseover="javascript:mensaje_status('Buscar Movimiento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    </td>
  </tr>
  
<%if (perfilId==Constantes.PERFIL_ADMIN_GENERAL ||
      perfilId==Constantes.PERFIL_TESORERO ||
      perfilId==Constantes.PERFIL_CAJERO ||
      perfilId==Constantes.PERFIL_ADMIN_JURISDICCION ||
      perfilId==Constantes.PERFIL_INTERNO)
  {%> 
  <tr>
    <td width="90" valign="middle"><b>Tipo de Usuario</b></td>
    <td width="110" valign="middle"><input type="radio" value="1" checked name="radio"><b>Organizaci&oacute;n</b></td>
    <td width="75" valign="middle">Raz&oacute;n Social</td>
    <td width="85" valign="middle"><input type="text" name="razon_social" maxlength=150" style="width:133" onFocus="doCambiaRadio(document.form1.radio,'1');" onblur="solonumlet(this)"></td>
    <td  valign="middle"></td>
  </tr>
  <tr>
    <td width="90" valign="middle"></td>
    <td width="110" valign="middle"></td>
    <td width="75" valign="middle">RUC</td>
    <td width="85" valign="middle"><input type="text"  size="12" style="width:100" maxlength=11" name="ruc" onFocus="doCambiaRadio(document.form1.radio,'1');" onblur="solonumlet(this)"></td>
    <td  valign="middle"></td>
  </tr>
  
  <tr>
    <td width="90" valign="middle"></td>
    <td width="110" valign="middle"><input type="radio" value="2" name="radio" ><b>Individual</b></td>
    <td width="75" valign="middle">Usuario ID</td>
    <td width="85" valign="middle"><input type="text" size="20" maxlength=13" style="width:133" name="userId" onFocus="doCambiaRadio(document.form1.radio,'2');" onblur="solonumlet(this)"></td>
    <td  valign="middle"></td>
  </tr>
  
<%}%>
  
<%if (perfilId==Constantes.PERFIL_ADMIN_ORG_EXT ||
	  perfilId==Constantes.PERFIL_AFILIADO_EXTERNO) {%>
  <%
  //<tr>
  //  <td width="90" valign="middle"></td>
  //  <td width="110" valign="middle"><input type="radio" value="2" name="radio" checked><b>Individual</b></td>
  //  <td width="75" valign="middle">Usuario ID</td>
  //  <td width="85" valign="middle"><input type="text" size="20" maxlength=13" style="width:133" name="userId" onFocus="doCambiaRadio(document.form1.radio,'2');" onblur="solonumlet(this)"></td>
  //  <td  valign="middle"></td>
  //</tr>
  %>
  <input type="hidden" value="1" name="radio">
  <tr>
    <td width="90" valign="middle"></td>
    <td width="185" valign="middle" colspan="2"></td>
    <td width="85" valign="middle"></td>
    <td  valign="middle"></td>
  </tr>
<%}%>  
  
<%if (perfilId==Constantes.PERFIL_INDIVIDUAL_EXTERNO ) {%>
   <input type="hidden" value="2" name="radio">
   <input type="hidden" name="userId" value="<%=request.getAttribute("usuario_logeado")%>">
<%}%>   

</table> 
</form>  







<logic:present name="listOrganizaciones">
<br>
<table cellspacing=0 class=titulo>
<tr><td><font color="black">Reporte de Movimientos del</font>&nbsp;<%=request.getAttribute("dateInicio")%>
        <font color="black">al</font>&nbsp;<%=request.getAttribute("dateFin")%>
        <FONT color=black><%=str_Criterio_Busqueda%> :</FONT><%=str_Variable_Busqueda%></td>
</tr>      
</table>
<br>
   
<p align="center"></p><STRONG><FONT size=3></FONT></STRONG>

	<FORM NAME="form2" ACTION="/iri/ReporteMovimientos.do?state=verReporteDetalle" METHOD="POST">
  	<INPUT TYPE="HIDDEN" NAME="peJuriId" VALUE="">
  	<INPUT TYPE="HIDDEN" NAME="personaId" VALUE="">
  	<INPUT TYPE="HIDDEN" NAME="lpId" VALUE="">
  	<INPUT TYPE="HIDDEN" NAME="diainicio" VALUE="<%=request.getAttribute("selectedIDay")%>">
	<INPUT TYPE="HIDDEN" NAME="mesinicio" VALUE="<%=request.getAttribute("selectedIMonth")%>">
	<INPUT TYPE="HIDDEN" NAME="anoinicio" VALUE="<%=request.getAttribute("selectedIYear")%>">
	<INPUT TYPE="HIDDEN" NAME="diafin" VALUE="<%=request.getAttribute("selectedFDay")%>">
	<INPUT TYPE="HIDDEN" NAME="mesfin" VALUE="<%=request.getAttribute("selectedFMonth")%>">
	<INPUT TYPE="HIDDEN" NAME="anofin" VALUE="<%=request.getAttribute("selectedFYear")%>">

  	<INPUT TYPE="HIDDEN" NAME="rz_aux" VALUE="">
  	<INPUT TYPE="HIDDEN" NAME="ndoc_aux" VALUE="">
    </FORM>

<table class=grilla>
  <tr  class=grilla2>
    <th width="50%" align="left" height="12">Raz&oacute;n Social de Organizaci&oacute;n</th>
    <th width="20%" align="center" height="12">Nro. de Documento</th>
    <th width="30%" align="center" height="12">Reporte de Movimiento</th>
  </tr>
  
  <logic:iterate id="organizacion" name="listOrganizaciones" > 	
  <tr>
    <td width="50%" align="center" height="19"><bean:write name="organizacion" property="razonSocial"/></td>
    <td width="20%" align="center" height="19"><bean:write name="organizacion" property="nroDocumento"/></td>
    <td width="30%" align="center" height="19"><a href="javascript:MuestraResultadosDetalle(<bean:write name='organizacion' property='peJuriId'/>,<bean:write name='organizacion' property='personaId'/>,<bean:write name='organizacion' property='lpId'/>,'<bean:write name='organizacion' property='razonSocial'/>','<bean:write name='organizacion' property='nroDocumento'/>')" onmouseover="javascript:mensaje_status('Ver Detalle');return true;">Ver</a>
    </td>
  </tr>
</logic:iterate>   

<!-- /logic:present-->






<logic:present name="listMovimientosTotal">
<br>
<table cellspacing=0 class=titulo>
<tr><td><font color="black">Reporte de Movimientos del</font>&nbsp;<%=request.getAttribute("dateInicio")%>
        <font color="black">al</font>&nbsp;<%=request.getAttribute("dateFin")%>
        <FONT color=black><%=str_Criterio_Busqueda%> :</FONT> <%=str_Variable_Busqueda%>
        <br>
        <%if(request.getAttribute("rz_aux") != null) out.print("<font color='black'> Razon Soc.: </font><font color='red'>" + request.getAttribute("rz_aux") + "</font>");%>
        <%if(request.getAttribute("ndoc_aux") != null) out.print(" <font color='black'>RUC: </font><font color='red'>" + request.getAttribute("ndoc_aux"));%>
    </td>
</tr>      
</table>
<br>
   
   
   
<p align="center"></p><STRONG><FONT size=3></FONT></STRONG>

<table class=grilla>
  <tr  class=grilla2>
    <th width="16%" align="center" height="12">N&uacute;mero de Abono</th>
    <th width="8%" align="center" height="12">Fecha</th>
    <th width="7%" align="center" height="12">Hora</th>
    <th width="10%" align="center" height="12">Monto</th>
    <th width="11%" align="center" height="12">Agencia</th>
    <th width="18%" align="center" height="12">Forma de Pago</th>
  </tr>

<logic:present name="listMovimientos">
  <logic:iterate id="str_DetalleMovimientos" name="listMovimientos" > 	
  <tr>
    <td width="16%" align="center" height="19"><bean:write name="str_DetalleMovimientos" property="abonoId"/></td>
    <td width="8%" align="center" height="19"><bean:write name="str_DetalleMovimientos" property="fecha"/></td>
    <td width="7%" align="center" height="19"><bean:write name="str_DetalleMovimientos" property="hora"/></td>
    <td width="10%" align="center" height="19"><bean:write name="str_DetalleMovimientos" property="monto"/></td>
    <td width="11%" align="center" height="19"><bean:write name="str_DetalleMovimientos" property="nombre"/></td>
    <td width="18%" align="center" height="19"><bean:write name="str_DetalleMovimientos" property="tipoPagoVentanilla"/></td>
  </tr>
</logic:iterate>   
</logic:present>

<logic:notPresent name="listMovimientos">
  <tr>
    <td width="16%" align="center" height="19">&nbsp;</td>
    <td width="8%" align="center" height="19">&nbsp;</td>
    <td width="7%" align="center" height="19">&nbsp;</td>
    <td width="10%" align="center" height="19">&nbsp;</td>
    <td width="11%" align="center" height="19">&nbsp;</td>
    <td width="18%" align="center" height="19">&nbsp;</td>
  </tr>
</logic:notPresent>

<logic:iterate id="str_DetalleMovimientosTotal" name="listMovimientosTotal" >
  <tr>
    <th width="16%" align="center" height="19">SALDO INICIAL</th>
    <td width="8%" align="center" height="19"><bean:write name="str_DetalleMovimientosTotal" property="saldoInicial"/></td>
    <th width="7%" align="center" height="24" bgcolor="#ffffff">TOTAL ABONOS</th>
    <td width="10%" align="center" height="24" bgcolor="#ffffff"><bean:write name="str_DetalleMovimientosTotal" property="abonos"/></td>
    <th width="11%" align="center" height="24" bgcolor="#ffffff">SALDO FINAL</th>
    <td width="18%" align="center" height="24" bgcolor="#ffffff"><bean:write name="str_DetalleMovimientosTotal" property="saldoFinal"/></td>
  </tr>
<tr>
    <th width="7%" align="center" height="24" bgcolor="#ffffff">CONSUMOS DURANTE EL PERIODO</th>
	<logic:equal name="str_DetalleMovimientosTotal" property="consumos" value="0">
	    <td width="10%" align="center" height="24" bgcolor="#ffffff"><bean:write name="str_DetalleMovimientosTotal" property="consumos"/></td>
	</logic:equal>    
	<logic:notEqual name="str_DetalleMovimientosTotal" property="consumos" value="0">	
 	   <td width="10%" align="center" height="24" bgcolor="#ffffff"><!--a href="javascript:Transacciones();"-->  <bean:write name="str_DetalleMovimientosTotal" property="consumos"/><!-- /a --></td>    
		<% if (str_Criterio_Busqueda.equals("userId")) { %> 	
			<input type="hidden" value="1" name="r1" >   
		<%}%> 
	</logic:notEqual>        
    <td width="7%" align="middle" height="24" bgcolor="#ffffff" bordercolor="#ffffff"></td>
    <td width="10%" align="middle" height="24" bgcolor="#ffffff"></td>
    <td width="11%" align="middle" height="24" bgcolor="#ffffff"></td>
    <td width="18%" align="middle" height="24" bgcolor="#ffffff"></td>
 
  </tr>
  


</logic:iterate>

<table> 
	<tr>
    	<td width="16%" align="center" >
			<a href="javascript:history.back()"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border=0 onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
			<a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border=0 onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
			<a href="javascript:ExportarResultados();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border=0 onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
		</td>
   </tr>

</table>
</logic:present>



</body>
</html>