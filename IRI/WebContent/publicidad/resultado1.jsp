<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
// Inicio:mgarate:30/05/2007
   String flag = (String) request.getAttribute("flagCertBusq");
// Fin:mgarate:30/05/2007
FormOutputBuscarPartida output = (FormOutputBuscarPartida) request.getAttribute("output");
ArrayList arr1 = output.getResultado();
UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
boolean certi=true;
if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
			 || usuarioBean.getExonPago()))
{
	certi=false;
}
%>

<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE></TITLE>
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="JavaScript">

function Regresa()
{
	history.back();
}

function doCancel()
{
  if((window.opener)&&(!window.opener.closed)){
	close();
  } else {
	history.back(1);
  }
}

function VerPartida(refnum_Part,estado)
{
  doyou = true;
  if (estado == '0') { 
    doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	ventana=window.open('/iri/Publicidad.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
  }
}

function paginar(numero)
{
	document.frm1.salto.value=numero;
	document.frm1.submit();
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
function enviar()
{	
	//************************************************************************************
	// SUNARP-RMC-BORRAR
	//inicio:mgarate:02/10/2007
	//descripcion: cambio para validar acceso a nuevos recursos
	//if(noTieneAccesoRecursoRMC(9, <%--=usuarioBean.getPerfilId()%>, '<%=usuarioBean.getUserId()--%>'))
	//{
	//	return;	
	//}
	//fin:mgarate:02/10/2007
	//************************************************************************************
	document.frm1.action="/iri/Certificados.do?state=guardarDatosBasicos&hidTipo=B&criterio=<%=request.getAttribute("criterioBusqueda")%>&flagCertBusq=<%=flag%>&flagBusq=D";
	document.frm1.submit();		
}
</script>

</HEAD>

<BODY>
<br>
<table class=titulo cellspacing=0>
  <tr> 
    <td><font color=black>SERVICIOS &gt;&gt;  Consulta de Partidas </font>Resultado</td>
  </tr>
</table>
<br>
Resultado de la Consulta de Partidas por N&uacute;mero <%=request.getAttribute("outNumber")%>
<br>
<table border=0 width=600>
  <tr> 
	    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<%=request.getAttribute("tarifa")%></td>
	    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=request.getAttribute("usuaEtiq")%></td>
	    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><%=request.getAttribute("fechaAct")%></td>
	    <td vAlign=top align=left width = 55%><font color="black">Ahora tenemos un nuevo medio de publicidad registral, de libre acceso para todos: www.sunarp.gob.pe.</font></td>
  </tr>
</table>
<br>
Total de registros encontrados : <%=output.getCantidadRegistros()%>
<br>
<table class=grilla cellspacing=0>
  <tr >
    <th width="8%">Zona Registral</th>
    <th width="7%">Oficina Registral</th>
    <th width="9%">Partida</th>
    <th width="6%">N&uacute;mero de Ficha</th>
    <th width="8%">Tomo</th>
    <th width="7%">Folio</th>
    <th width="11%">Area Registral</th>
    <th width="13%">Registro de</th>
    <th width="10%">Visualizar</th>
    <%if(certi){%>
    <th width="10%">Copia Literal de Partida</th>
    <%}%>
  </tr>
  <%
  for (int i=0; i < arr1.size(); i++)
  	{
  	PartidaBean bean = (PartidaBean) arr1.get(i);
  	%>
  <tr class=grilla2>
	<td><%=bean.getRegPubDescripcion()%></td>
	<td><%=bean.getOficRegDescripcion()%></td>
	<td><%=bean.getNumPartida()%></td>
	<td><%=bean.getFichaId()%></td>
	<td><%=bean.getTomoId()%></td>
	<td><%=bean.getFojaId()%></td>
	<td><%=bean.getAreaRegistralDescripcion()%></td>
	<td><%=bean.getLibroDescripcion()%></td>
	<td align="center"><input name="image2" type="image" onclick="VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Visualizar" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
	<%if(certi){%>
	<td align="center">
	  <a href="/iri/Certificados.do?state=guardarDatosBasicos&refnum_part=<%=bean.getRefNumPart()%>&noPartida=<%=bean.getNumPartida()%>&hidOfic=<%=bean.getOficRegDescripcion()%>&area=<%=bean.getAreaRegistralId()%>&hidTipo=L">
	    <image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	  </a>
	</td>
	<%}%>
  </tr>
  <% } %>
</table>
<br>
Mostrando Partidas del <%=output.getNdel()%> al <%=output.getNal()%>

<%-- formulario para pagineo --%>
<br>
<form  name="frm1" method="post" action="<%=output.getAction()%>">
<input type="hidden" name="flagPagineo" value="1">
<input type="hidden" name="salto" value="0">
<input type="hidden" name="cantidad" value="<%=output.getCantidadRegistros()%>">
<table width="100%">    
 <tr>
    <td width="50%" align="left">
    <% if (output.getPagAnterior() >= 0) {%>
    	<a href="javascript:paginar('<%=output.getPagAnterior()%>')" target="_self" onmouseover="javascript:mensaje_status('Anterior');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">anterior</a>
    <% } else {%>
    &nbsp;
    <% } %>
    </td>
    <td width="50%" align="right">
    <% if (output.getPagSiguiente() >= 0) {%>
    	<a href="javascript:paginar('<%=output.getPagSiguiente()%>')" target="_self" onmouseover="javascript:mensaje_status('Siguiente');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">siguiente</a>
    <% } else {%>
    &nbsp;
    <% } %>
    </td>
  </tr>
</table> 
</form>

<br>
<table class=tablasinestilo>
  <tr>
  	<!-- Inicio:mgarate:30/05/2007 -->
    	<td width="15%" align="right">
	<!-- Fin:mgarate:30/05/2007 -->
  	<div id="HOJA2"> 
  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	</div></td>
 <% if(usuarioBean.getPerfilId()==Constantes.PERFIL_CAJERO || usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO)
{ %> 	
  	<%if(flag!=null){
  	   if(flag.equals("6") || flag.equals("11") || flag.equals("12")){
  	 %>
  	  <td width="35%" align="left">
  	  <div id="HOJA2"> 
  	  <a href="javascript:enviar();" onmouseover="javascript:mensaje_status('Solicitar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_solic.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	  </div></td>
  	 <%}}}%>
	<td width="50%" align="right">
	<div id="HOJA3">	
	
	<logic:notPresent name="flagVerifica">
		<a href="javascript:Regresa()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</logic:notPresent>
	<logic:present name="flagVerifica">
		<a href="javascript:doCancel()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</logic:present>
	</div></td>
  </tr>
</table>
<logic:notPresent name="flagVerifica">
	<script>
		window.top.frames[0].location.reload();
	</script>
</logic:notPresent>	
</BODY>
</HTML>