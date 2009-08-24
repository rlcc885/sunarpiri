<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.OficRegistralesBean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
String x = (String) request.getAttribute("perfilId");
long perfilId = Long.parseLong(x);
%>
<html>
<head>

<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">

<script language="javascript">

function ShowReporte()
{
//validar rango fechas
    if(!esRangoFecha(sel_Obtener_Texto(document.form1.diainicio),sel_Obtener_Texto(document.form1.mesinicio),sel_Obtener_Texto(document.form1.anoinicio),sel_Obtener_Texto(document.form1.diafin),sel_Obtener_Texto(document.form1.mesfin),sel_Obtener_Texto(document.form1.anofin)) )
    	return;
    	
<%if (perfilId==Constantes.PERFIL_ADMIN_GENERAL ||
      perfilId==Constantes.PERFIL_TESORERO ||
      perfilId==Constantes.PERFIL_CAJERO ||
      perfilId==Constantes.PERFIL_ADMIN_JURISDICCION ||
      perfilId==Constantes.PERFIL_INTERNO)
  {%> 
	if(document.form1.r1[0].checked==true)
	{
		if(esVacio(document.form1.razsoc.value) && esVacio(document.form1.ruc.value))
		{
			alert("Datos Incorrectos. Ingrese la razón Social o el número de RUC");
			document.form1.razsoc.focus();
			return;
		}	
		if(esVacio(document.form1.razsoc.value))
		{
			if(isNaN(document.form1.ruc.value) || document.form1.ruc.value.indexOf(".")>0 || !esEnteroMayor(document.form1.ruc.value,1) || !esLongitudMayor(document.form1.ruc.value,11)){
				alert("Datos Incorrectos. Ingrese el número de RUC correctamente");
				document.form1.ruc.focus();
				return;
			}			
		}
	}
	
	if(document.form1.r1[1].checked==true)
	{
		if(esVacio(document.form1.userId.value))
			{
				alert("Datos Incorrectos. Ingrese el Usuario");
				document.form1.userId.focus();		
				return;
			}
	}	
	<%}%>
	
<%if (perfilId==Constantes.PERFIL_ADMIN_ORG_EXT) {%>
		//if(esVacio(document.form1.userId.value))
		//{
		//		alert("Datos Incorrectos. Ingrese el Usuario");
		//		document.form1.userId.focus();		
		//		return;
		//}
	<%}%>		
	document.form1.action="/iri/VerReporteTransacciones.do?primeravez=NO&pagina=1";
	document.form1.submit();
}

function Exportar()
{
	document.form2.action="/iri/VerReporteTransacciones.do?primeravez=NO&exportar=X";
	document.form2.submit();
}

function paginacion(pagina)
{
	document.form2.action="/iri/VerReporteTransacciones.do?primeravez=NO&pagina=" + pagina;
	document.form2.submit();
}

function PrintReport()
{
	document.form2.action="/iri/VerReporteTransacciones.do?state=exporta";
	document.form2.submit();
}

function Regresar()
{
	document.form1.action="/iri/VerReporteTransacciones.do";
	document.form1.submit();
}

function doCambiaRadio(obj, valor)
{ 
for (var rr = 0; rr < obj.length; rr++)
	{
		var xvlr = obj[rr].value;
		if (xvlr == valor)
			obj[rr].checked=true;
	}
}


function MuestraResultadosDetalle(a,b,c){
	document.form2.razSoc.value = b;
	document.form2.numDoc.value = c;
	document.form2.peJuriId.value = a;
	document.form2.method="POST";
	document.form2.action="/iri/VerReporteTransacciones.do?primeravez=NO&detalle=SI";
	document.form2.submit();
	return true;
}

</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head> 




<body>
<br>
<table class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Reportes &gt;&gt; </FONT> Reporte de transacciones</td></tr>
</table>
<br>
<form name="form1" method="POST">
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
  <tr><th colspan="7"><font size="2">REPORTE DE TRANSACCIONES</font>&nbsp;</th>
        </tr>
  <tr>
            <td colspan="2"><b>Desde</b></td>
            <td colspan="2"><b>Hasta</b></td>
            <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td nowrap colspan="2">
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
    <td valign="middle" nowrap colspan="2">
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
    <td></td>
    <td></td>
    <td><input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" border="0" onClick="return ShowReporte();" onmouseover="javascript:mensaje_status('Buscar Transacciones');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
  </tr>

<%if (perfilId==Constantes.PERFIL_ADMIN_GENERAL ||
      perfilId==Constantes.PERFIL_TESORERO ||
      perfilId==Constantes.PERFIL_CAJERO ||
      perfilId==Constantes.PERFIL_ADMIN_JURISDICCION ||
      perfilId==Constantes.PERFIL_INTERNO)
  {%> 
	  <tr>
            <td valign="middle"><b>Tipo de Usuario</b></td>
            <TD valign="middle"><input type="radio" value="2" name="r1" checked><b>Organizaci&oacute;n</b></TD>
            <td valign="middle"><b>&nbsp;Raz&oacute;n Social</b></td>
            <TD valign="middle"><input type="text" size="20" name="razsoc" maxlength=100 onfocus="doCambiaRadio(document.form1.r1,'2');" onblur="solonumlet(this)"></TD>
            <td valign="middle"></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
            <td valign="middle" ></td>
            <TD valign="middle" ></TD>
            <td valign="middle"><b>&nbsp;RUC</b></td>
            <TD valign="middle"><input type="text" size="11" maxlength=11 name="ruc" onfocus="doCambiaRadio(document.form1.r1,'2');" onblur="solonumlet(this)"></TD>
            <td valign="middle"></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
            <td  valign="middle"></td>
            <td  valign="middle"><input type="radio" value="1" name="r1" checked><b>Individual</b></td>
            <td  valign="middle"><b> &nbsp;Usuario ID&nbsp; </b></td>
            <TD valign="middle"><input type="text" size="20" maxlength=13 name="userId" onfocus="doCambiaRadio(document.form1.r1,'1');" onblur="solonumlet(this)"></TD>
            <td></td>
	    <td></td>
	    <td></td>
	  </tr>	  
  <%}%>

<%if (perfilId==Constantes.PERFIL_ADMIN_ORG_EXT) {%>
	  <tr>
            <td  valign="middle"></td>
            <td  valign="middle"><input type="radio" value="1" name="r1" checked><b>Individual</b></td>
            <td  valign="middle"><b> &nbsp;Usuario ID&nbsp; </b></td>
            <TD valign="middle"><input type="text" size="20" maxlength=13 name="userId"  onblur="solonumlet(this)"></TD>
            <td></td>
	    <td></td>
	    <td></td>
	  </tr>
<%}%>
  
  
<%if (perfilId==Constantes.PERFIL_INDIVIDUAL_EXTERNO ||
	  perfilId==Constantes.PERFIL_AFILIADO_EXTERNO) {%>  
<input type="hidden" value="1" name="r1">
<input type="hidden" name="userId" value="<%=request.getAttribute("usuario_logeado")%>">
<%}%>	  
  <tr>
     		<td></td>
            <td></td>
            <td colspan="4" valign="middle"><input type="checkbox" name="costocero">Incluir Transacciones con costo cero</td>
		    <td></td>
  </tr>
</table>
</form>
<br>




<form name="form2" method="POST">
<logic:present name="hayData">
	<table class=titulo>
	<tr>
		<td> 
		  <FONT color=black>Reporte de Transacciones del</FONT> <bean:write name="fecini"/> <FONT color=black>al</FONT> <bean:write name="fecfin"/>
		  <FONT color=black>Tipo Usuarios:</FONT> <bean:write name="tipousuario"/>
		  <FONT color=black>Usuario:</FONT> 
		  <logic:present name="userId"><bean:write name="userId"/></logic:present>
		  <logic:present name="razsoc"><bean:write name="razsoc"/> </logic:present>
		  <logic:present name="ruc"><bean:write name="ruc"/></logic:present>
		  
		</td>
	</tr>
	</table>
	<br>
</logic:present>

<logic:present name="hayData">
	<logic:equal name="hayData" value="N">
		<center><font color="red" size="2">NO SE HAN REGISTRADOS TRANSACCIONES</font></center>
	</logic:equal>
</logic:present>


<input type="hidden" name="paginar" value="X">
<logic:present name="hayData">
	<logic:equal name="hayData" value="S">
		<input type="hidden" name="diainicio" value="<%=request.getAttribute("selectedIDay")%>">
		<input type="hidden" name="mesinicio" value="<%=request.getAttribute("selectedIMonth")%>">
		<input type="hidden" name="anoinicio" value="<%=request.getAttribute("selectedIYear")%>">
		<input type="hidden" name="diafin" value="<%=request.getAttribute("selectedFDay")%>">
		<input type="hidden" name="mesfin" value="<%=request.getAttribute("selectedFMonth")%>">
		<input type="hidden" name="anofin" value="<%=request.getAttribute("selectedFYear")%>">

		<%String costocero = (String) request.getAttribute("costocero");
		  if (costocero!=null) {%>
			<input type="hidden" name="costocero" value="X">
		<% } %>
		
		<input type="hidden" name="r1" value="<%=request.getAttribute("r1")%>">
		<input type="hidden" name="userId" value="<%=request.getAttribute("userId")%>">
		<input type="hidden" name="razsoc" value="<%=request.getAttribute("razsoc")%>">
		<input type="hidden" name="ruc" value="<%=request.getAttribute("ruc")%>">
		
	<%boolean color = false;%>	
<!--Presenta Lista-->	
	<logic:present name="hayDetalle">
		<input type="hidden" name="razSoc" value="">
		<input type="hidden" name="numDoc" value="">
		<input type="hidden" name="peJuriId" value="">

		<table class=grilla>
		  <tr  class=grilla2>
		    <th width="50%" align="center" height="12">Raz&oacute;n Social de Organizaci&oacute;n</th>
		    <th width="20%" align="center" height="12">Nro. de RUC</th>
		    <th width="30%" align="center" height="12">Reporte de Tranmsacciones</th>
		  </tr>
		  
		  <logic:iterate id="organizacion" name="listaOrganizaciones" > 	
			<%if(color){%>	<tr class="grilla2"> <%}else{%>	<tr>	<%}%> 
				<%color = !color;%>
			    <td width="50%" align="center" height="19"><bean:write name="organizacion" property="razonSocial"/></td>
			    <td width="20%" align="center" height="19"><bean:write name="organizacion" property="numDoc"/></td>
			    <td width="30%" align="center" height="19"><a href="javascript:MuestraResultadosDetalle('<bean:write name='organizacion' property='peJuriID'/>','<bean:write name='organizacion' property='razonSocial'/>','<bean:write name='organizacion' property='numDoc'/>')" onmouseover="javascript:mensaje_status('Ver Detalle');return true;">Ver</a></td>
		    </tr>
		  </logic:iterate>   
		</table>
	</logic:present>
<!--FIN Presenta Lista-->	

<!--Solo Detalle-->	
	<input type="hidden" name="peJuri" value="<%=request.getAttribute("peJuriId")%>">
	<input type="hidden" name="razS" value="<%=request.getAttribute("razSoc")%>">
	<logic:notPresent name="hayDetalle">
		<table class=grilla style="WIDTH: 604px">
		  <tr>
		    <logic:notPresent name="noEmpresa">
		    	<th width="64" height="12">Organizaci&oacute;n</th>
		    </logic:notPresent>
		    <th width="56" align="center" height="20">Usuario</th>
		    <th width="64" align="center" height="20">Fecha</th>
		    <th width="32" align="center" height="20">Hora</th>
		    <th width="99" align="center" height="20">Ofic Registral</th>
		    <th width="118" align="center" height="20">Tipo Servicio</th>
		    <th width="156" align="center" height="20">Id. Documento</th>
		    <th width="31" align="center" height="20">Costo</th>
		  </tr>
			<logic:iterate name="listatransacciones" id="item" scope="request">
			    <%if(color){%><tr class="grilla2"><%}else{%><tr><%}%> 
			     <%color = !color;%>

			    <logic:notPresent name="noEmpresa">
			    	<td width="64" align="center" height="12"><bean:write name="item" property="razonSocial"/></td>
			    </logic:notPresent>
			    <td width="56" align="center" height="12"><bean:write name="item" property="userId"/></td>
			    <td width="64" align="center" height="12"><bean:write name="item" property="fecha"/></td>
			    <td width="32" align="center" height="12"><bean:write name="item" property="hora"/></td>
			    <td width="99" align="center" height="12"><bean:write name="item" property="nomOficinaRegistral"/></td>
			    <td width="118" align="center" height="12"><bean:write name="item" property="nomServicio"/></td>
			    <td width="156" align="center" height="12"><bean:write name="item" property="idDocumento"/></td>
			    <td width="31" align="center" height="12"><bean:write name="item" property="costo"/></td>
			  </tr>
			</logic:iterate>  
		  <tr>
		    <logic:notPresent name="noEmpresa">
		        <th width="64" align="center" height="20"></th>
		    </logic:notPresent>  
		    <th width="56" height="20"></th>
		    <th width="64" height="20"></th>
		    <th width="32" height="20"></th>
		    <th width="99" height="20"></th>
		    <th width="118" height="20"></th>
		    <th width="156" align="right" height="20">TOTAL:&nbsp;&nbsp;</th>
		    <th width="31" height="20"><bean:write name="costo_total"/></th>
		  </tr>
		</table>
	</logic:notPresent>
<!--FIN Solo Detalle-->
		<logic:notPresent name="hayDetalle">
			<a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border=0 onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
			<a href="javascript:Exportar();"><img src="<%=request.getContextPath()%>/images/btn_exportar.gif" border=0 onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
		</logic:notPresent>
	</logic:equal>
	<a href="javascript:Regresar();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border=0 onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
</logic:present>
</form>
</body>
</html>