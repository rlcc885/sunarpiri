<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>
<%System.out.println("refresca:::" + request.getAttribute("refresca")); %>
<% if ("si".equals(request.getAttribute("refresca"))){%>
<script type="text/javascript">
top.top_frame.location.reload();
</script>
<%}else{%>
<% }%>
<script type="text/javascript">
function imprimir()
{
	HOJA2.style.visibility="hidden";
	//HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	//HOJA3.style.visibility="visible";
}
function siguiente(){
		
		top.top_frame.location.reload();
		document.frm1.method="POST";
		document.frm1.action="/iri/IndicePJ.do?state=paginandoAdelante&inicio=<%= request.getAttribute("inicio")%>";
		document.frm1.submit();
}
function anterior(){
		document.frm1.method="POST";
			document.frm1.action="/iri/IndicePJ.do?state=paginandoAtras&inicio=<%= request.getAttribute("fin")%>";
		document.frm1.submit();
}
function finalizar(){
		document.frm1.method="POST";
		document.frm1.action="/iri/IndicePJ.do";
		document.frm1.submit();
}
</script>
<body>
<table cellspacing=0 class=titulo>
  <br>
  <tr>
	<td>
		<FONT COLOR="black">BUSQUEDAS <font size="1">&gt;&gt;</font></FONT><font color="900000"> Resultado de Busqueda de Personas Juridicas a Nivel Nacional </FONT></td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
  <div id="total">Encontrados: <strong><%=request.getAttribute("total")%></strong> Criterio de Busqueda: <strong><%=request.getAttribute("criterio")%></strong> Tipo de Busqueda:<strong><%=request.getAttribute("tipoBusqueda")%></strong> Costo:<strong><%=request.getAttribute("costo")%></strong></div>
<div id="resul">
<table>	
 		<tr>	
		<td colspan="5">
			<table class="grilla">
					
				
				<tr class="grilla2">
		      		<th align="center" height="19" width="19%"><div align="center">Persona Juridica</div></th>
		      		<th align="center" height="19" width="32%"><div align="center">Razon Social</div></th>
		      				      		<th align="center" height="19" width="38%"><div align="center">Siglas</div></th>
		      		<th align="center" height="19" width="37%"><div align="center">Oficina Registral</div></th>
		      		<th align="center" height="19" width="37%"><div align="center">Nacionalidad</div></th>
					<th align="center" height="19" width="37%"><div align="center">Estado</div></th>
		      		<th align="center" height="19" width="37%"><div align="center">Titulo</div></th>
					<th align="center" height="19" width="37%"><div align="center">Partida Elec.</div></th>
					<th align="center" height="19" width="37%"><div align="center">Ficha</div></th>
					<th align="center" height="19" width="37%"><div align="center">Tomo / Folio</div></th>
		      	</tr>
				<%if ( !request.getAttribute("total").equals("0") ) {%>
				<logic:iterate name="recuperados" id="razSoc">
					<tr class="grilla2">
						<td width="223"><div align="left"><bean:write name="razSoc" property="tipoPersonaJuridica"/></div></td>
							<td width="389"><div align="left"><bean:write name="razSoc" property="razSoc"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="sigla"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="oficReg"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="nacionalidad"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="estado"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="titulo"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="partidaElectronica"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="ficha"/></div></td>
							<td width="200"><div align="left"><bean:write name="razSoc" property="tomoFolio"/></div></td>
							
				    </tr>	
				</logic:iterate>
				<%}else{%>
				<table>
					<tr>
						<td>
						<b>No se encontraron resultados.</b>
						</td>
					</tr>
				</table>
				<%}%>								
</table>
<div>
<table>
<tr>
<%if ("si".equals(request.getAttribute("anterior"))){%>
	<td><a href="javascript:anterior();">Anterior&nbsp; </a></td>
<%}%>	
<% if ("si".equals(request.getAttribute("siguiente"))) {%>
<td><a href="javascript:siguiente();">&nbsp;Siguiente</a></td>
<%}%>
</tr>
</table>
<div id="botones">
<table class=tablasinestilo width="634">
  <tr>
  	<td width="96%" align="right">
  	  <div id="HOJA2"> 
  	    <a href="javascript:imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
  	      <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
  	    </a>
  	  </div>
  	</td>
    <td align="right" width="4%">
	  <div id="HOJA3">	
	    <a href="javascript:finalizar();" onmouseover="javascript:mensaje_status('Finalizar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	      <IMG height=25 hspace=4 src="images/btn_finalizar.gif" width=83 align=absMiddle vspace=5 border=0>
	    </a>
	  </div>
	</td>
        </tr>
</table>
</div>
</div>
</td>
</tr>
</table>
</div>
</form>
</body>
</html>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-1499572-11");
pageTracker._trackPageview("/ResultadosIndice");
} catch(err) {}</script>