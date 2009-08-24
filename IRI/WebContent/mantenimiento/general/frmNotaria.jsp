<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="java.util.*" %>

<%
String x;
x = (String) request.getAttribute("modo");
int modo            = Integer.parseInt(x);
GenericBean bean    = (GenericBean) request.getAttribute("genericBean");
String codTabla     = (String) request.getAttribute("codTabla");
String nombreTabla  = (String) request.getAttribute("nombreTabla");
ArrayList arrOficinas  = (ArrayList) session.getAttribute("ARR1");

String texto_modo="";
switch (modo)
{
	case 1: texto_modo="DETALLE";       break;
	case 2: texto_modo="ACTUALIZACION"; break;
	case 3: texto_modo="ELIMINACION";   break;
	case 4: texto_modo="INSERCION";     break;
}
%>

<html>
<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>

<script language="javascript">

function doBack()
{
	history.back(1);
}

function doCancelar()
{
	document.frm1.P1.value="0";
	document.frm1.submit();
}

function doAceptar()
{ 
<%if (modo==2 || modo==4) { %>
	if(esVacio(document.frm1.val03.value))
		{
			alert("Por favor ingrese correctamente el CUR de institucion");
			document.frm1.val03.focus();
			return;	
		}
	if(esVacio(document.frm1.val04.value))
		{
			alert("Por favor ingrese correctamente el Nombre Institucion");
			document.frm1.val04.focus();
			return;	
		}		
	if(esVacio(document.frm1.val05.value))
		{
			alert("Por favor ingrese correctamente las Siglas");
			document.frm1.val05.focus();
			return;	
		}			
	if(esVacio(document.frm1.val06.value) || !esEntero(document.frm1.val06.value))
		{
			alert("Por favor ingrese correctamente el ID de Persona Juridica");
			document.frm1.val06.focus();
			return;	
		}		
	/*
	if(esVacio(document.frm1.val08.value))
		{
			alert("Por favor ingrese el Agente de sincronizacion");
			document.frm1.val08.focus();
			return;	
		}	
	*/
<% } %>
	document.frm1.submit();
}

function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}

</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<table border="0" width="100%" class="titulo">
	<tr>
		<td>Mantenimiento de tabla : <%=nombreTabla%></td>
		<td><%=texto_modo%></td>
	</tr>
</table>
<br>

<form name="frm1" class="formulario" action = "/iri/ManteTabla.do">
<input type="hidden" name="state" value="finProceso">
<input type="hidden" name="P0" value="<%=codTabla%>">
<input type="hidden" name="P1" value="<%=modo%>">

<input type="hidden" name="PK1" value="<%=request.getAttribute("PK1")%>">
<input type="hidden" name="PK2" value="<%=request.getAttribute("PK2")%>">
<input type="hidden" name="PK3" value="<%=request.getAttribute("PK3")%>">
<input type="hidden" name="PK4" value="<%=request.getAttribute("PK4")%>">
<input type="hidden" name="PK5" value="<%=request.getAttribute("PK5")%>">
<input type="hidden" name="PK6" value="<%=request.getAttribute("PK6")%>">
<input type="hidden" name="PK7" value="<%=request.getAttribute("PK7")%>">
<input type="hidden" name="PK8" value="<%=request.getAttribute("PK8")%>">
<input type="hidden" name="PK9" value="<%=request.getAttribute("PK9")%>">
<input type="hidden" name="PK10" value="<%=request.getAttribute("PK10")%>">

<table>
<tr>
	<td>Oficina</td>
	<td>
      <select name="val01" disabled="true"> 
      <%for (int i = 0; i < arrOficinas.size(); i++) 
      	{
      	ComboBean cbean = (ComboBean) arrOficinas.get(i);%>
      	<option value="<%=cbean.getCodigo()%>"><%=cbean.getDescripcion()%></option>
      <%}%>	
      </select>
	</td>
</tr>	
<tr>
	<td>CUR Instituci&oacute;n</td>
	<td><input type="text" name="val03" value="<%=bean.getValor03()%>" maxlength="14" size="30" disabled="true" onblur="sololet(this)"></td>
</tr>	
<tr>
	<td>Nombre Instituci&oacute;n</td>
	<td><input type="text" name="val04" value="<%=bean.getValor04()%>" maxlength="250" size="60" disabled="true" onblur="sololet(this)"></td>
</tr>	
<tr>
	<td>Siglas</td>
	<td><input type="text" name="val05" value="<%=bean.getValor05()%>" maxlength="20" size="30" disabled="true" onblur="sololet(this)"></td>
</tr>	
<tr>
	<td>Id Persona Jur&iacute;dica</td>
	<td><input type="text" name="val06" value="<%=bean.getValor06()%>" maxlength="10" size="15" disabled="true" onblur="sololet(this)"></td>
</tr>	
<%--
<tr>
	<td>Fecha y Hora de &Uacute;ltima sincronizaci&oacute;n</td>
	<td><input type="text" name="val07" value="<%=bean.getValor07()%>" maxlength="30" size="30" disabled="true" onblur="sololet(this)"></td>
</tr>	
<tr>
	<td>Agente de sincronizaci&oacute;n</td>
	<td><input type="text" name="val08" value="<%=bean.getValor08()%>" maxlength="4" size="10" disabled="true" onblur="sololet(this)"></td>
</tr>	
--%>
</table>
</form>

<br>
<table class="tablasinestilo">
  <tr>
    <td align="center">
      <%if (modo==1) {%>
      <A href="javascript:doBack()" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="images/btn_aceptar.gif"></a>
      <% } else {%>
      <A href="javascript:doAceptar()" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="images/btn_aceptar.gif"></a>
      <A href="javascript:doCancelar()" onmouseover="javascript:mensaje_status('Cancelar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="images/btn_cancelar.gif"></a>
      <% } %>
	</td>
  </tr>
</table>

</body>

<script LANGUAGE="JavaScript">
<%if (bean!=null) {%>
	doCambiaCombo(document.frm1.val01,'<%=bean.getValor01()%>');
	document.frm1.val03.value = '<%=bean.getValor03()%>';
	document.frm1.val04.value = '<%=bean.getValor04()%>';
	document.frm1.val05.value = '<%=bean.getValor05()%>';
	document.frm1.val06.value = '<%=bean.getValor06()%>';
	<%--
	document.frm1.val07.value = '<%=bean.getValor07()%>';
	document.frm1.val08.value = '<%=bean.getValor08()%>';
	--%>
<% } %>

<%if (modo == 2 || modo == 4) {%>
	document.frm1.val04.disabled=false;
	document.frm1.val05.disabled=false;
	document.frm1.val06.disabled=false;
	//document.frm1.val08.disabled=false;
	<%if (modo==4) {%>	
		document.frm1.val01.disabled=false;
		document.frm1.val03.disabled=false;
		//document.frm1.val07.value="---";
	<% } %>
<%}%>
</script>

<%
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
%>
<br>
<script LANGUAGE="JavaScript">
	alert("<%=validacionException.getMensaje()%>");
	<% 
	String foco = validacionException.getFocus();
	if (foco.trim().length() > 0) {%>
		document.frm1.<%=foco%>.focus();
	<% } %>
</script>
<% } %>

</html>