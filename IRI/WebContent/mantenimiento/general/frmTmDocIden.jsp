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
	if(esVacio(document.frm1.val01.value))
	{
		alert("Por favor ingrese Codigo");
		document.frm1.val01.focus();
		return;	
	}
	if(esVacio(document.frm1.val02.value))
	{
		alert("Por favor ingrese Nombre abreviado");
		document.frm1.val02.focus();
		return;	
	}	
	if(esVacio(document.frm1.val05.value))
	{
		alert("Por favor ingrese Descripcion");
		document.frm1.val05.focus();
		return;	
	}		
<%}%>
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
<br>
<table class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt;Mantenimiento de tablas &gt;&gt; </FONT>Tabla <%=nombreTabla%></td><td></td></tr>
</table>
<br>
<table width=600 border=0>
<tr><td align=left ><b><font color= 900000><%=texto_modo%></font><b></td></tr>
</table>
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
	<td>C&oacute;digo de Tipo de Documento</td>
	<td><input type="text" name="val01" value="<%=bean.getValor01()%>" maxlength="2" size="3" disabled="true" onblur="sololet(this)"></td>
</tr>	
<tr>
	<td>Nombre Abreviado</td>
	<td><input type="text" name="val02" value="<%=bean.getValor02()%>" maxlength="10" size="20" disabled="true" onblur="sololet(this)"></td>
</tr>	
<tr>
	<td>Tipo Persona</td>
	<td>
      <select name="val03" disabled="true"> 
        <option value="N">Natural</option>
        <option value="J">Jur&iacute;dica</option>
        <option value="A">Ambas</option>
      </select>
	</td>
</tr>
<tr>
	<td>Estado</td>
	<td>
      <select name="val04" disabled="true"> 
        <option value="1">Activo</option>
        <option value="0">Inactivo</option>
      </select>
	</td>
</tr>
<tr>
	<td>Descripci&oacute;n</td>
	<td><input type="text" name="val05" value="<%=bean.getValor05()%>" maxlength="50" size="80" disabled="true" onblur="sololet(this)"></td>
</tr>	
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
	document.frm1.val01.value = '<%=bean.getValor01()%>';
	document.frm1.val02.value = '<%=bean.getValor02()%>';
	document.frm1.val05.value = '<%=bean.getValor05()%>';
	doCambiaCombo(document.frm1.val03,'<%=bean.getValor03()%>');
	doCambiaCombo(document.frm1.val04,'<%=bean.getValor04()%>');
<% } %>

<%if (modo == 2 || modo == 4) {%>
	document.frm1.val02.disabled=false;
	document.frm1.val03.disabled=false;
	document.frm1.val04.disabled=false;
	document.frm1.val05.disabled=false;
	<%if (modo==4) {%>	
		document.frm1.val01.value="---";
		document.frm1.val02.focus();
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