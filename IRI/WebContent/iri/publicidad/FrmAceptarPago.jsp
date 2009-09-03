<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>

<%@page import="java.util.ResourceBundle"%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<title>resultadoConsolidado</title>
<script language="JavaScript" src="/webapp/extranet/javascript/util.js"></script>
<script language="JavaScript" SRC="/webapp/extranet/javascript/overlib.js"></script>
<% 
	ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.iri.publicidad.properties.Publicidad");
%>
<script language="javascript">
function regresar()
{
	document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do";	
	document.frm1.submit();
}
function aceptar(valor)
{
	if(valor=="A")
	{
		if(document.frm1.registro.value=="V")
		{
			document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaVehicular";	
		}
		if(document.frm1.registro.value=="A")
		{
			document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaAeroNave";	
		}
		if(document.frm1.registro.value=="B")
		{
			document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaBuques";	
		}
		if(document.frm1.registro.value=="E")
		{
			document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaEmbarcacionPesquera";	
		}
		if(document.frm1.registro.value=="R")
		{
			document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaRMC";	
		}
	}else if(valor=="D")
	{	
		document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do";	
	}
	document.frm1.submit();
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
</head>
<body>
<form name="frm1" method="post">
	<input type="hidden" name="lista" value="<%=request.getAttribute("listaResult")%>"/>
	<input type="hidden" name="flagRespuesta" value="1"/>
	<input type="hidden" name="cantidad" value="<%=request.getAttribute("cantidad")%>"/>
	<input type="hidden" name="registro" value="<%=request.getAttribute("regis")%>"/>
	<input type="hidden" name="precio" value="<%=request.getAttribute("precio")%>"/>
	<table width="600" border="0" cellpadding="0" cellspacing="2">
	  <tr> 
	    <td><b>SERVICIOS&gt;&gt;<font size="1"></font> <font color="#90000">Aceptación del Pago</font></b></td>
	  </tr>
	  <tr> 
	    <td bgcolor="#000000"><img src="../images/space.gif" width="5" height="1"></td>
	  </tr>
	</table>
	<table class=formulario cellpadding="0">
		<tr>
			<th colspan="4" width="200" align="left">RESULTADOS DE LA BUSQUEDA</th>
			<td width="120"></td>
			<td></td>
		</tr>
		<tr>
			<td width="200"><br>Total de Registros: <%=request.getAttribute("cantidad")%></td>
			<td align="center" width="140"><br>Monto Total: S/&nbsp;<%=request.getAttribute("precio")%> </td>
			<td></td>
		</tr>
		<logic:present name="bean" property="saldo">
			<logic:equal name="bean" property="saldo" value="0">
				<tr>
					<td colspan="2"><font color="red"><%=bundle.getString("msn.publicidad.masiva.mensaje.saldo")%></font></td>
				</tr>
			</logic:equal>
		</logic:present>
		<tr>
			<td colspan="3" align="center"><br>¿Acepta pagar el monto de la b&uacute;squeda?</td>
		</tr>
	</table>
	<table class=tablasinestilo>
	  <tr>
	  <logic:present name="bean" property="saldo">
	  	  <logic:equal name="bean" property="saldo" value="1">
	  	<br><br>
	  	<td align="left" width="26%">
	  	<div id="HOJA2"> 
	  		<a href="javascript:aceptar('A');" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=28 hspace=4 src="images/btn_SiAcepto.gif" width=83 align=absMiddle vspace=5 border=0></a>
	  		</div></td>
			<td align="center" width="74%">
			<div id="HOJA3">	
			<a href="javascript:aceptar('D');" onmouseover="javascript:mensaje_status('No Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=28 hspace=4 src="images/btn_NoAcepto.gif" width=83 align=absMiddle vspace=5 border=0></a>
			</div></td>
	 </logic:equal>
	 <logic:equal name="bean" property="saldo" value="0">
	 	<br>
	 	<td align="left" width="60%">
		<div id="HOJA3">	
		<a href="javascript:regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
		</div>
	 </logic:equal>
	</logic:present>		
	

	  </tr>
	</table>
</form>
</body>
</html>