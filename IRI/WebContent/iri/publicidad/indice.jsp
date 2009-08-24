<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%

UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
boolean certi=true;
if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
			 || usuarioBean.getExonPago()))
{
	certi=false;
}
//certi=false;
//Inicio:jascencio:27/08/2007
//CC: SUNARP-REGMOBCON-2006
boolean esRmc = false;
PartidaBean partidaBean = null;
partidaBean = (PartidaBean)request.getAttribute("partida");
if(partidaBean != null){
	if(partidaBean.getCodLibro() !=null && partidaBean.getCodLibro().equals(Constantes.CODIGO_LIBRO_RMC)){
		
		esRmc = true;
	}
}
//Fin:jascencio
%>

<html>
<head>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title><meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
</head>

<script>

var lock = false;
var visualizando = "";

function bloquear(imagenVisualizada) 
	{
		visualizando = imagenVisualizada;
		lock = true;
	}
	
function liberar() 
	{
		lock = false;
	}
	
	<% int contador = 0; %>
<logic:present name="asientos">
  <logic:iterate name="asientos" id="asiento">
    	<% for (int i = ((AsientoElectronicoBean)asiento).getNroPaginas(); i >=1 ; i--) 
    	{ %>
    	
    	function callVisualizar<%= contador %>(ultimo, imagenVisualizada, pagRef) 
    	{
			
			alert("GO!!");
			parent.rigth.location = '/iri/PublicidadIRI.do?state=muestraTituloVisualizacion'
				+ '&ultimo=' + ultimo
				+ '&contador=<%= contador %>'
				+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
				+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
				+ '&objetoID=<bean:write name="asiento" property="objectId"/>'
				+ '&numPagina=<%= i %>'
				+ '&noPgna=' + pagRef
				+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
				+ '&noPartida=' + document.frmPartida.noPartida.value
				+ '&accion=<%=request.getParameter("accion")%>'
				+ '&noSolicitud=<%=request.getParameter("noSolicitud")%>'
				+ '&indexClass=<%= Constantes.INDEX_SUBCLASS_ASIENTO %>';
			
    	}
	    <% contador++;
	    } %>
  </logic:iterate>
</logic:present>
	
<logic:present name="ficha">
    	<% for (int i = ((AsientoFichaBean) request.getAttribute("ficha")).getNroPaginas(); i >=1 ; i--) 
    	{ %>
	    function callVisualizar<%= contador %>(ultimo, imagenVisualizada, pagRef) 
	    {
	    	//pagRef = pagRef + <%= i %>
			parent.rigth.location = '/iri/PublicidadIRI.do?state=muestraTituloVisualizacion'
				+ '&ultimo=' + ultimo
				+ '&contador=<%= contador %>'
				+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
				+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
				+ '&objetoID=<bean:write name="ficha" property="objectId"/>'
				+ '&numPagina=<%= i %>'
				+ '&noPgna=' + pagRef
				+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
				+ '&noPartida=' + document.frmPartida.noPartida.value
				+ '&accion=<%=request.getParameter("accion")%>'
				+ '&noSolicitud=<%=request.getParameter("noSolicitud")%>'
				+ '&indexClass=<%= Constantes.INDEX_SUBCLASS_FICHA %>';
    	}
	    <% contador++;
	    } %>
	</logic:present>

<logic:present name="folios">
		<logic:iterate name="folios" id="folio">
	    function callVisualizar<%= contador %>(ultimo, imagenVisualizada, pagRef) 
	    {
			parent.rigth.location = '/iri/PublicidadIRI.do?state=muestraTituloVisualizacion'
				+ '&ultimo=' + ultimo
				+ '&contador=<%= contador %>'
				+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
				+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
				+ '&objetoID=<bean:write name="folio" property="objectId"/>'
				+ '&numPagina=1'
				+ '&noPgna=' + pagRef
				+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
				+ '&noPartida=' + document.frmPartida.noPartida.value
				+ '&accion=<%=request.getParameter("accion")%>'
				+ '&noSolicitud=<%=request.getParameter("noSolicitud")%>'
				+ '&indexClass=<%= Constantes.INDEX_SUBCLASS_FOLIO %>';
    	}
    	<% contador++; %>
		</logic:iterate>
</logic:present>
		<% int ultimo = --contador; %>

<%if(certi){%>
function Solicitar() 
{
	//Por investigar: parent.opener.parent.parent.window.SetFocus();
	parent.opener.parent.parent.main_frame.main_frame1.location='/iri/Certificados.do?state=guardarDatosBasicos'
	+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
	+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
	+ '&noPartida=' + document.frmPartida.noPartida.value
	+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
	+ '&cboOficinas=<bean:write name="partida" property="regPubId"/>|<bean:write name="partida" property="oficRegId"/>'
	+ '&hidOfic=<bean:write name="partida" property="oficRegDescripcion"/>&area=<bean:write name="partida" property="areaRegistralId"/>'
	+ '&hidTipo=L';
				
				
}
function SolicitarAsie(ano,pags,nsasie,nroTitulo,codActo,actoDesc,placa,nsasieplaca) 
{
	//Por investigar: parent.opener.parent.parent.window.SetFocus();
	parent.opener.parent.parent.main_frame.main_frame1.location='/iri/Certificados.do?state=guardarDatosBasicos'
	+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
	+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
	+ '&noPartida=' + document.frmPartida.noPartida.value
	+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
	+ '&cboOficinas=<bean:write name="partida" property="regPubId"/>|<bean:write name="partida" property="oficRegId"/>'
	+ '&hidOfic=<bean:write name="partida" property="oficRegDescripcion"/>&area=<bean:write name="partida" property="areaRegistralId"/>'
	+ '&hidTipo=LA&ano='+ano+'&nroPags='+pags+'&nsasie='+nsasie+'&nroTitulo='+nroTitulo+'&codActo='+codActo+'&actoDesc='+actoDesc+'&placa='+placa+'&nsasieplaca='+nsasieplaca;
				
				
}
function solicitarRMC() 
{
	//Por investigar: parent.opener.parent.parent.window.SetFocus();
	parent.opener.parent.parent.main_frame.main_frame1.location='/iri/Certificados.do?state=guardarDatosBasicos'
	+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
	+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
	+ '&noPartida=' + document.frmPartida.noPartida.value
	+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
	+ '&cboOficinas=<bean:write name="partida" property="regPubId"/>|<bean:write name="partida" property="oficRegId"/>'
	+ '&hidOfic=<bean:write name="partida" property="oficRegDescripcion"/>&area=<bean:write name="partida" property="areaRegistralId"/>'
	+ '&hidTipo=LR';
				
				
}
function solicitarAsientoRMC(ano,pags,nsasie,nroTitulo,codActo,actoDesc,placa,nsasieplaca) 
{
	//Por investigar: parent.opener.parent.parent.window.SetFocus();
	parent.opener.parent.parent.main_frame.main_frame1.location='/iri/Certificados.do?state=guardarDatosBasicos'
	+ '&refnum_part=<bean:write name="partida" property="refNumPart"/>'
	+ '&codLibro=<bean:write name="partida" property="codLibro"/>'
	+ '&noPartida=' + document.frmPartida.noPartida.value
	+ '&noPgnaTotal=' + document.frmPartida.noPgnaTotal.value
	+ '&cboOficinas=<bean:write name="partida" property="regPubId"/>|<bean:write name="partida" property="oficRegId"/>'
	+ '&hidOfic=<bean:write name="partida" property="oficRegDescripcion"/>&area=<bean:write name="partida" property="areaRegistralId"/>'
	+ '&hidTipo=LAR&ano='+ano+'&nroPags='+pags+'&nsasie='+nsasie+'&nroTitulo='+nroTitulo+'&codActo='+codActo+'&actoDesc='+actoDesc+'&placa='+placa+'&nsasieplaca='+nsasieplaca;
				
				
}


<%}%>


</script>

	<% contador = 0; %>

<body>
<FORM NAME="frmPartida">
<input type="hidden" name="noPartida" value="<bean:write name="partida" property="numPartida"/>">
<input type="hidden" name="noPgnaTotal" value="<%=request.getAttribute("numPgnaTotal")%>">
</FORM>
<% if (request.getParameter("orden") == null) 
{ %>
<a href="/iri/PublicidadIRI.do?state=muestraIndiceAsientos&refnum_part=<%= request.getParameter("refnum_part") %>&orden=1">
Ordenar por rubro
</a>
<% } else { %>
<a href="/iri/PublicidadIRI.do?state=muestraIndiceAsientos&refnum_part=<%= request.getParameter("refnum_part") %>">
Ordenar por fecha de inscripci&oacute;n
</a>
<% } %>
<%if(certi){%>
<table width="100%" border="1" cellpadding="1" cellspacing="2">
  <tr> 
    <td bordercolor="#000000" colspan="2" align="center">

<table width="100%" border="0" cellpadding="1" cellspacing="2" class="TitDetalle">
  <tr> 
    
    <td colspan="2" align="center">
      <font size="1" color="000000">Leyenda<br>Solicitar Certificados</font>
    </td>
  </tr>
  
  <tr> 
    <td align="center">
      <img src="<%=request.getContextPath()%>/images/copia.gif">
    </td>
    <td>
    <font size="1" color="000000">Partida Completa</font>
    </td>
  </tr>
  <tr> 
    <td align="center">
      <image src="/iri/images/asie.gif" style="border:0">
    </td>
    <td>
    <font size="1" color="000000">Asiento Completo</font>
    </td>
  </tr>
</table>

    </td>
  </tr>
</table>
<% } %>
<table width="100%" border="1" cellpadding="1" cellspacing="2" bordercolor="#FFFFFF">
  <tr> 
    <td bordercolor="#000000" class="txtCabecera">
      <%if(certi){
      		if(esRmc){
      %>
      		<A href="javascript:solicitarRMC();">
      		&nbsp;<image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Copia Literal Partida Completa');return true;" onmouseOut="javascript:mensaje_status(' ');return true;" alt="Solicitar Copia Literal Partida Completa RMC">
        	</A>
      <%	}else{
      %>
      		<A href="javascript:Solicitar();">
      		&nbsp;<image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Copia Literal Partida Completa');return true;" onmouseOut="javascript:mensaje_status(' ');return true;" alt="Solicitar Copia Literal Partida Completa">
        	</A>	
      <%	}
      }
      %>
    Partida  : <bean:write name="partida" property="numPartida"/></td>
  </tr>
  <tr> 
    <td bordercolor="#000000" class="txtCabecera">No. Pags. : <%=request.getAttribute("numPgnaTotal")%></td>
  </tr>

<%-- ITERANDO POR ASIENTOS --%>
<logic:present name="asientos">
  <logic:iterate name="asientos" id="asiento">

  <tr> 
    <td bordercolor="#000000" class="TitDetalle">
          		<a href="javascript:alert(
    				'Inscripci&oacute;n: <bean:write name="asiento" property="fechaInscripcion"/>\n'
    					+ 'Presentaci&oacute;n: <bean:write name="asiento" property="fechaPresentacion"/>\n'
    					+ 'Rubro: <bean:write name="asiento" property="rubroDescripcion"/>\n'
    					+ 'Acto: <bean:write name="asiento" property="actoDescripcion"/>\n'
    					+ 'Participantes Naturales: <bean:write name="asiento" property="participantesPN"/>\n'
    					+ 'Participantes Juridicos: <bean:write name="asiento" property="participantesPJ"/>')">
    			<img src="<%=request.getContextPath()%>/images/ico_titulo.jpg" width="12" height="12" align="absmiddle"> 
    		</a>
    		<bean:write name="asiento" property="numRef"/>
      		T&iacute;tulo : 
      		<bean:write name="asiento" property="nroTitulo"/>
      		
      
    </td>
  </tr>
  <tr> 
    <td bordercolor="#000000" class="Titulo1"><strong>A&ntilde;o: <bean:write name="asiento" property="aaTitulo"/> Rubro: 
      <bean:write name="asiento" property="rubroLetra"/></strong></td>
  </tr>
  <tr> 
    <td bordercolor="#000000" class="Titulo1">
    	<%if(certi){
      		if(esRmc){
    	%>	
    		<A href="javascript:solicitarAsientoRMC('<bean:write name="asiento" property="aaTitulo"/>','<bean:write name="asiento" property="nroPaginas"/>','<bean:write name="asiento" property="nsAsie"/>','<bean:write name="asiento" property="nroTitulo"/>','<bean:write name="asiento" property="actoId"/>','<bean:write name="asiento" property="actoDescripcion"/>','<bean:write name="asiento" property="placa"/>','<bean:write name="asiento" property="nsAsiePlaca"/>');">
			&nbsp;<image src="/iri/images/asie.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Copia Literal de Asiento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;" alt="Solicitar Copia Literal de Asiento RMC">
			</A>
    	<%	}else{
    	%>	
    		<A href="javascript:SolicitarAsie('<bean:write name="asiento" property="aaTitulo"/>','<bean:write name="asiento" property="nroPaginas"/>','<bean:write name="asiento" property="nsAsie"/>','<bean:write name="asiento" property="nroTitulo"/>','<bean:write name="asiento" property="actoId"/>','<bean:write name="asiento" property="actoDescripcion"/>','<bean:write name="asiento" property="placa"/>','<bean:write name="asiento" property="nsAsiePlaca"/>');">
		    &nbsp;<image src="/iri/images/asie.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Copia Literal de Asiento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;" alt="Solicitar Copia Literal de Asiento">
        	</A>
    	<%	}
		  }
	  	%>
    	<strong>P&aacute;ginas:  <logic:equal name="asiento" property="objectId" value="0"> NO DISPONIBLE </logic:equal>
    	<% for (int i = 1; i <=((AsientoElectronicoBean)asiento).getNroPaginas() ; i++) { %>
	    [ <a href="javascript:callVisualizar<%= ultimo + 1 - Integer.parseInt(((AsientoElectronicoBean)asiento).getNumPagRef()) - i %>(<%= ultimo %>, 'T&iacute;tulo ' + '<bean:write name="asiento" property="aaTitulo"/>' + '-' + '<bean:write name="asiento" property="nroTitulo"/>' + ' Pag.' + '<%= i %>', <bean:write name="asiento" property="numPagRef"/> + <%= i %>)" class="opcLeft2"><%= i %></a> ]
	    <% contador++;
	       } %>
	    </strong>
	</td>
  </tr>

  </logic:iterate>
</logic:present>


<%-- SI LA FICHA ESTA PRESENTE --%>
	<logic:present name="ficha">


  <tr> 
    <td bordercolor="#000000" ><img src="<%=request.getContextPath()%>/images/space.gif" width="15" height="2"></td>
  </tr>
  <tr> 
    <td bordercolor="#000000" class="TitFicha"><img src="<%=request.getContextPath()%>/images/ico_titulo.jpg" width="12" height="12" align="absmiddle">
      Ficha : <bean:write name="ficha" property="nroFicha"/> </td>
  </tr>
  <tr> 
    <td bordercolor="#000000" class="TxtFicha">
    	<strong>P&aacute;ginas: <logic:equal name="ficha" property="objectId" value="0"> NO DISPONIBLE </logic:equal>
    	<% int nroPaginas = ((AsientoFichaBean) request.getAttribute("ficha")).getNroPaginas();
    	   int nroAsientos = contador;
    	   for (int i = 1; i <=nroPaginas ; i++) 
    	   { %>
	    [ <a href="javascript:callVisualizar<%= nroAsientos + nroPaginas - i  %>(<%= ultimo %>, 'Ficha ' + '<bean:write name="ficha" property="nroFicha"/>' + ' Pag.' + '<%= i %>',<bean:write name="ficha" property="numPagRef"/> - <%= nroPaginas %> + <%= i %>)" class="opcLeft2"><%= i %></a> ]
	    <% contador++;
	       } %>
	    </strong>
    </td>
  </tr>

	</logic:present>


<%-- SI LA FOLIOS ESTA PRESENTE --%>
<logic:present name="folios">

  <tr> 
    <td bordercolor="#000000" ><img src="<%=request.getContextPath()%>/images/space.gif" width="15" height="2"></td>
  </tr>

<%-- ITERANDO POR LOS FOLIOS --%>
		<logic:iterate name="folios" id="folio">
  <tr> 
    <td bordercolor="#000000" class="TitTomo"><img src="<%=request.getContextPath()%>/images/ico_tomo.gif" width="12" height="12" align="absmiddle">
      Tomo : <bean:write name="folio" property="nroTomo"/></td>
  </tr>
  <tr> 
    <td bordercolor="#000000" class="TxtTomo">
    	<strong>Folio: </strong>
    	<a href="javascript:callVisualizar<%= contador %>(<%= ultimo %>, 'Tomo ' + '<bean:write name="folio" property="nroTomo"/>' + ' Folio ' + '<bean:write name="folio" property="nroFolio"/>',<bean:write name="folio" property="numPagRef"/>)" class="opcLeft2">
    	<bean:write name="folio" property="nroFolio"/>
    	</a>
    </td>
  </tr>
    	<% contador++; %>
		</logic:iterate>


</logic:present>



<%-- SI TITULOS ESTA PRESENTE --%>
<logic:present name="titulos">

  <tr> 
    <td bordercolor="#000000" ><img src="<%=request.getContextPath()%>/images/space.gif" width="15" height="2"></td>
  </tr>

<%-- ITERANDO POR LOS TITULOS --%>
		<logic:iterate name="titulos" id="titulo">
  <tr>
    <td bordercolor="#000000" class="txtDescrip"><p>T&iacute;tulos Pendientes</p>
      <p><b>Nro.</b> <bean:write name="titulo" property="nroTitulo"/> <b>A&ntilde;o:</b> <bean:write name="titulo" property="aaTitulo"/><br>
        <b>Acto:</b> <bean:write name="titulo" property="acto"/><br>
        <b>Registro:</b> <bean:write name="titulo" property="zonaReg"/><br>
        <b>Oficina:</b> <bean:write name="titulo" property="oficReg"/><br>
      </p></td>
  </tr>
		</logic:iterate>


</logic:present>


</table>
<p>&nbsp; </p>
<p>&nbsp; </p>

<%
String pi = (String) session.getAttribute("partidaIncompleta");
if (pi!=null)
	if(pi.equals("1")) {%>
<script LANGUAGE="JavaScript">
	alert("Partida Incompleta. No visualizada por usuario externo. Informe a la zona registral correspondiente. El error ha sido reportado a SUNARP para su corrección, gracias por su comprensión.");
</script>
<%}%>
</body>
</html>