<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
// Inicio:mgarate:31/05/2007
   String flag = (String)request.getAttribute("flagCertBusq");
// Fin:mgarate:31/05/2007
FormOutputBuscarPartida output = (FormOutputBuscarPartida) request.getAttribute("output");
ArrayList arr1 = output.getResultado();
boolean flagEstado = output.getFlagEstado();
UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
boolean certi=true;
if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
			 || usuarioBean.getExonPago()))
{
	certi=false;
}
//Inicio:jascencio:27/07/07
//CC: REGMOBCON-2006
boolean flagUsuarioInterno = false;
flagUsuarioInterno=usuarioBean.getFgInterno();
FormOutputBuscarPartida outputInterno = null;
outputInterno=output.getOutputInterno();
ArrayList arrInterno=null;
if(flagUsuarioInterno == true && outputInterno != null){
arrInterno=outputInterno.getResultado();
}
//Fin:jascencio
%>

<HTML>
<HEAD>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<TITLE></TITLE>

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
    doyou = confirm("Esta partida est� cerrada, �Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	ventana=window.open('/iri/PublicidadIRI.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
  }
}
function VerDetalleVehicular(param,numeroPartida,ofic){
	  document.form1.action="/iri/PublicidadIRI.do?state=buscarXPlacaDet&radBuscar2=" + param + "&txt4=" + numeroPartida+ "&CboOficinas=" + ofic;
	  document.form1.submit();	
}

function VerDetalle(refnum_Part,estado)
{ 
  doyou = true;
  if (estado == '0') { 
    doyou = confirm("Esta partida est� cerrada, �Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	//ventana=window.open('/iri/PublicidadIRI.do?state=detallePartidaRMC&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
	//documento.frm1.refNumPart.value=refnum_Part;
	//documento.frm1.estado.value=estado;
	document.form1.action="/iri/PublicidadIRI.do?state=detallePartidaRMC&refnum_part=" + refnum_Part;	
	document.form1.submit();
  }
}

function Imprimir()
{
	//HOJA2.style.visibility="hidden";
	//HOJA3.style.visibility="hidden";
	window.print();
	//HOJA2.style.visibility="visible";
	//HOJA3.style.visibility="visible";	
}
function Imprime()
{
 window.print();
}

//Inicio:jascencio:27/07/07
//CC:REGMOBCON-2006
<%
if(flagUsuarioInterno == true &&  flag != null && flag.equals(Constantes.CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS)){
%>
	function paginar(numero)
	{
		document.frm1.salto.value=numero;
	<%
	if(usuarioBean.getFgInterno() == true){
		
		if(outputInterno.getPagSiguiente() >= 0){
	%>
			document.frm1.saltoInferior.value=<%=outputInterno.getPagSiguiente()-1%>
	<%		
		}else{
			if(outputInterno.getPagAnterior()>= 0){
	%>
			document.frm1.saltoInferior.value=<%=outputInterno.getPagAnterior()+1%>
	<%
			}
			else{
	%>
				document.frm1.saltoInferior.value=1;
	<%	
			}
		}
		
	%>	
		document.frm1.cantidadInferior.value=<%=outputInterno.getCantidadRegistros()%>
	<%
	}
	%>	
		
		alert("Paginar-->"+document.frm1.action);
		document.frm1.submit();
		
	}
	function paginarInferior(numero)
	{
		alert("Nada1");
	<%
		if(output.getPagSiguiente() >= 0){
	%>
			document.frm2.salto.value=<%=output.getPagSiguiente()-1%>
	<%		
		}else{
			if(output.getPagAnterior()>= 0){
	%>
			document.frm2.salto.value=<%=output.getPagAnterior()+1%>
	<%
			}
			else{
	%>
				document.frm2.salto.value=1;
	<%	
			}
		}
		
	%>	
				alert("Nada2");
		document.frm2.cantidad.value=<%=output.getCantidadRegistros()%>
		document.frm2.saltoInferior.value=numero;
		
		alert("Paginar XX-->"+document.frm2.action);
				
		document.frm2.submit();
	}
	function enviar(){
		var numero=1;
		if(document.frm2.checkInactivosSIGC.checked == true){
			document.frm2.checkInactivosArea15.value=1;
		}
		else{
			document.frm2.checkInactivosArea15.value=0;
		}
		document.frm2.flagPagineoInferior.value=1;
		paginarInferior(numero);
	}
	
	function cargaCheckInativos(){
<%
	if(outputInterno.isFlagInactivo() == true ){
%>
		document.frm2.checkInactivosArea15.value=1;
		document.frm2.checkInactivosSIGC.checked=true;
		
<%	
	}else{
%>
		document.frm2.checkInactivosArea15.value=0;
		document.frm2.checkInactivosSIGC.checked=false;
<%
	}
%>
	}
<%
}else{
%>
//Fin:jascencio
	function paginar(numero)
	{
		alert("Wasap");
		document.frm1.salto.value=numero;
		alert(document.frm1.action);
		document.frm1.submit();
	
	}
	
<%	
}

%>
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
	document.frm1.action="/iri/CertificadosIRI.do?state=guardarDatosBasicos&hidTipo=B&criterio=<%=request.getAttribute("criterioBusqueda")%>&flagCertBusq=<%=flag%>&flagBusq=I";
	document.frm1.submit();
}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY
<%
//Inicio:jascencio:27/07/07
//CC:REGMOBCON-2006
if(flagUsuarioInterno == true &&  flag != null && flag.equals(Constantes.CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS)){ 
%>
onload="cargaCheckInativos()"
<%}
//Fin:jascencio
 %>
>

<div id="maincontent">
	<div class="innertube">

<form name="form1" method="POST">
<input type="hidden" name="busq" value="1">
</form>
<b><font color="#949400">Resultado de la b&uacute;squeda</font></b>
<br>
<table border=0 width=600>
  <tr> 
	    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<%=request.getAttribute("tarifa")%></td>
	    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=request.getAttribute("usuaEtiq")%></td>
	    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><%=request.getAttribute("fechaAct")%></td>
	    <td vAlign=top align=left width = 55%>
	    	&nbsp;	
	    </td>
  </tr>
</table>
<br>
<%//Inicio:jascencio:27/07/2007
//CC:SUNARP-REGMOBCON-2006
	if(flag !=null && flag.equals(Constantes.CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS)){
%>
 <table>
 	<tr bordercolor="#000000">
 		<td>&nbsp;</td>
 		<td>&nbsp;</td> 		 		
 		<td>&nbsp;</td>
 		<td>&nbsp;</td>
 		<td>&nbsp;</td> 		 		
 		<td align="center"><strong>Garant�a, contratos de afectaci�n y otros grav�menes</strong></td>
 		<td>&nbsp;</td>
 		<td>&nbsp;</td>
 		<td>&nbsp;</td> 		 		
 		<td>&nbsp;</td>
 		<td>&nbsp;</td>
 		<td>&nbsp;</td> 		 		
 	</tr>
  </table>
 <br>
<%	
	}
	//Fin:jascencio
 %>
Total de registros encontrados : <%=output.getCantidadRegistros()%>
<table border="1">
<tr bgcolor="#949400">
	<th><font color="white">Registro P&uacute;blico</font></th>
	<th><font color="white">Oficina Registral</font></th>
	<th><font color="white">Partida</font></th>
	<th><font color="white">Ficha</font></th>
	<th><font color="white">Tomo</font></th>
	<th><font color="white">Folio</font></th>
	<th><font color="white">Area Registral</font></th>
	   <logic:present name="rmc">
	   	<logic:notEqual name="rmc" value="sigc">
	     	<th><font color="white">Continua de</font></th>
		 	<th><font color="white"># de p&aacute;ginas</font></th>
		</logic:notEqual>
	   </logic:present>
	<th><font color="white">Registro de</font></th>
	<th><font color="white">Participante1</font></th>
	<!-- <th>Participaci&oacute;n</th> -->
	<th><font color="white">Documento identidad</font></th>
	<th><font color="white">N&uacute;mero documento</font></th>
	<%if (flagEstado==true){%>
	<th><font color="white">Estado</font></th>
	<%}%>	
	 <!-- inicio:jrosas 23-07-07 -->
    <logic:present name="rmc">
    	<th width="10%"><font color="white">Ver Detalle</font></th>    
	    <th width="10%"><font color="white">Ver Asiento</font></th>	
    </logic:present>
    <logic:notPresent name="rmc">
	    <th width="10%"><font color="white">Visualizar</font></th>    
    </logic:notPresent>
    <!-- fin:jrosas 23-07-07 -->
	<%if(certi){%>
	<th><font color="white">Copia Literal de Partida</font></th>
	<%}%>
</tr>

  <%
  if(arr1 != null){
  for (int i=0; i < arr1.size(); i++)
  	{
  	PartidaBean bean = (PartidaBean) arr1.get(i);
  	%>
			  <tr >
				<td><%=bean.getRegPubDescripcion()%></td>
				<td><%=bean.getOficRegDescripcion()%></td>
				<td><%=bean.getNumPartida()%></td>
				<td><%=bean.getFichaId()%></td>
				<td><%=bean.getTomoId()%></td>
				<td><%=bean.getFojaId()%></td>
				<td><%=bean.getAreaRegistralDescripcion()%></td>
				<logic:present name="rmc">
					<logic:notEqual name="rmc" value="sigc">
						<td>
						<%if (bean.getNumPartidaMigrado()== null || bean.getNumPartidaMigrado().equals("")){ %>
							&nbsp;
						<%}else{ %>
							<%=bean.getNumPartidaMigrado() %>
							<% if (!(bean.getLibroDescripcionMigrado().equals("&nbsp;"))){ %>
								-&nbsp;<%= bean.getLibroDescripcionMigrado() %>
							<% }
						 }%>	
						</td>
						<td><%=bean.getNumeroPaginas() %></td>
					</logic:notEqual>
				</logic:present>
				<td><%=bean.getLibroDescripcion()%></td>
				<td><%=bean.getParticipanteDescripcion()%></td>
				<!-- <td><%=bean.getParticipacionDescripcion()%></td> -->
				<td><%=bean.getParticipanteTipoDocumentoDescripcion()%></td>
				<td><%=bean.getParticipanteNumeroDocumento()%></td>
				<%if (flagEstado==true){%>
				<td><%=bean.getEstadoInd()%></td>
				<%}%>
				<!-- inicio:jrosas 23-07-07 -->
				<logic:present name="rmc" >
					<logic:notEqual name="rmc" value="sigc">
						<%if(bean.getCodLibro() != null && bean.getCodLibro().equals(Constantes.CODIGO_LIBRO_RMC)){%>
							<td align="center">
								<input name="image2" type="image" onclick="VerDetalle('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Ver Detalle" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
							</td>	
                        <%}else{%>
                        	<td align="center">&nbsp;</td>
                        <%}%>							
						<td align="center"><a href="javascript:VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')"><image src="/iri/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>	
					</logic:notEqual>
					<!-- Inicio:jascencio:15/08/2007  CC:SUNARP-REGMOBCON-2006 -->
					<logic:equal name="rmc" value="sigc">
					<%if(bean.getCodLibro()!=null && bean.getCodLibro().length() > 0 && (bean.getCodLibro().equals(Constantes.CODIGO_LIBRO_RMC) || bean.getCodLibro().equals(Constantes.CODIGO_LIBRO_VEHICULAR))){
						if(bean.getCodLibro().equals(Constantes.CODIGO_LIBRO_VEHICULAR)){%>
							<td align="center"><input name="image2" type="image" onclick="VerDetalleVehicular('P','<%=bean.getNumPartida()%>','<%=bean.getRegPubId()%>|<%=bean.getOficRegId()%>')" value="Ver Detalle" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>							
						<%}else{%>
							<td align="center"><input name="image2" type="image" onclick="VerDetalle('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Ver Detalle" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>							
					  <%}
					}else{%>
						<td>&nbsp;</td>
					<%}%>
					<td align="center"><a href="javascript:VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')"><image src="/iri/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>						
					</logic:equal>
					<!-- Fin:jascencio -->
				</logic:present>				
				<logic:notPresent name="rmc">
					<td align="center"><a href="javascript:VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')"><image src="/iri/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>	
				</logic:notPresent>
				<!-- fin:jrosas 23-07-07 -->
				<%if(certi){%>
				<td align="center">
				<!-- Inicio:jascencio:08/08/2007 -->
				<%if(bean.getCodLibro() != null && bean.getCodLibro().equals(Constantes.CODIGO_LIBRO_RMC)){%>
   	                  <a href="/iri/CertificadosIRI.do?state=guardarDatosBasicos&refnum_part=<%=bean.getRefNumPart()%>&noPartida=<%=bean.getNumPartida()%>&hidOfic=<%=bean.getOficRegDescripcion()%>&area=<%=bean.getAreaRegistralId()%>&hidTipo=LR">					
				<%}else{%>
					  <a href="/iri/CertificadosIRI.do?state=guardarDatosBasicos&refnum_part=<%=bean.getRefNumPart()%>&noPartida=<%=bean.getNumPartida()%>&hidOfic=<%=bean.getOficRegDescripcion()%>&area=<%=bean.getAreaRegistralId()%>&hidTipo=L">										
				<%}%>
				<!-- Fin:jascencio  -->
				    <image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				  </a>
				</td>
				<%}%>
			  </tr>
<%  }
  }
 %>
</table>

<br>
Mostrando Partidas del <%=output.getNdel()%> al <%=output.getNal()%>

<%-- formulario para pagineo --%>
<form  name="frm1" method="post" action="<%=output.getAction()%>">

<% if(flag != null && flag.equals(Constantes.CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS)){%>
	<input type="hidden" name="flagPagineo2" value="1">
<% }else{%>
	<input type="hidden" name="flagPagineo" value="1">
<% } %>

<input type="hidden" name="salto" value="0">
<input type="hidden" name="cantidad" value="<%=output.getCantidadRegistros()%>">
<!-- inicio: jrosas 13-08-07  -->
<input type="hidden" name="refNumPart" value="">
<input type="hidden" name="estado" value="">
<!-- fin: jrosas 13-08-07 -->
<% if(usuarioBean.getFgInterno() == true){%>
<input type="hidden" name="saltoInferior" value="0">
<input type="hidden" name="cantidadInferior" value="<%=output.getCantidadRegistros()%>">
<input type="hidden" name="hidFlagInterno" value="1" >
<%
} %>
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

<!-- Inicio:jascencio:27/07/07
	CC: REGMOBCON-2006
 -->
 
 
 <%
 if(flag != null && flag.equals(Constantes.CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS)){

 	if(usuarioBean.getFgInterno() == true){  %>
	<table >
	 	<tr bordercolor="#000000">
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td> 		 		
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td> 		 		
	 		<td align="center"><strong>T�tulos de dominio y otros</strong></td>
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td> 		 		
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td>
	 		<td>&nbsp;</td> 		 		
	 	</tr>
 	</table>
 
 <form  name="frm2" method="post" action="<%=outputInterno.getAction()%>">
 <input type="hidden" name="checkInactivosArea15" />
 <table>
 	<tr>
 		<td><input type="checkbox" 	name="checkInactivosSIGC" checked="false" onclick="enviar()"/>Visualizar Participaciones Inactivas</td>
 		<td></td>
 		<td></td>
 	</tr>
 	<tr>
 		<td></td>
 		<td></td>
 		<td></td>
 	</tr>
 </table>
Total de registros encontrados :<%=outputInterno.getCantidadRegistros()%>


<table border="1" >
<tr bgcolor="#949400">
	<th><font color="white">Registro P�blico</font></th>
	<th><font color="white">Oficina Registral</font></th>
	<th><font color="white">Partida</font></th>
	<th><font color="white">Ficha</font></th>
	<th><font color="white">Tomo</font></th>
	<th><font color="white">Folio</font></th>
	<th><font color="white">Area Registral</font></th>
    <th><font color="white">Registro de</font></th>
	<th><font color="white">Participante</font></th>
	<th><font color="white">Documento identidad</font></th>
	<th><font color="white">N�mero documento</font></th>
	<th><font color="white">Estado</font></th>
	<th><font color="white">Visualizar</font></th>
</tr>
<%
  if(arrInterno != null){
  for (int j=0; j < arrInterno.size(); j++)
  	{
  	PartidaBean bean = (PartidaBean) arrInterno.get(j);
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
	<td><%=bean.getParticipanteDescripcion()%></td>
	<td><%=bean.getParticipanteTipoDocumentoDescripcion()%></td>
	<td><%=bean.getParticipanteNumeroDocumento()%></td>
	<td><%=bean.getEstadoInd()%></td>
	<td align="center">
	<a href="javascript:VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')"><image src="/iri/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
	</td>

</tr>
<%  } %>
</table>
<%}
 %>
<br>
Mostrando Partidas del <%=outputInterno.getNdel()%> al <%=outputInterno.getNal()%>

<%-- formulario para pagineo --%>


<input type="hidden" name="flagPagineo2" value="1">
<input type="hidden" name="flagPagineoInferior" value="0">
<input type="hidden" name="salto">
<input type="hidden" name="cantidad" >
<input type="hidden" name="saltoInferior" value="0">
<input type="hidden" name="cantidadInferior" value="<%=outputInterno.getCantidadRegistros()%>">
<input type="hidden" name="hidFlagInterno" value="1" >
<table width="100%">    
 <tr>
    <td width="50%" align="left">
    <% if (outputInterno.getPagAnterior() >= 0) {%>
    	<a href="javascript:paginarInferior('<%=outputInterno.getPagAnterior()%>')" target="_self" onmouseover="javascript:mensaje_status('Anterior');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">anterior</a>
    <% } else {%>
    &nbsp;
    <% } %>
    </td>
    <td width="50%" align="right">
    <% if (outputInterno.getPagSiguiente() >= 0) {%>
    	<a href="javascript:paginarInferior('<%=outputInterno.getPagSiguiente()%>')" target="_self" onmouseover="javascript:mensaje_status('Siguiente');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">siguiente</a>
    <% } else {%>
    &nbsp;
    <% } %>
    </td>
  </tr>
</table> 
</form>
<%}
}
 %>


<!-- Fin:jascencio -->


<table>
  <tr>
  	<!-- Inicio:mgarate:31/05/2007 -->
    <td width="15%" align="left">
	<!-- Fin:mgarate:31/05/2007 -->
  	<div id="HOJA2"> 
  		<input type="button" class="formbutton" value="Imprimir" onclick="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
  	</div></td>
  	<!-- Inicio:mgarate:31/05/2007 -->
  	<% if(usuarioBean.getPerfilId()==Constantes.PERFIL_CAJERO || usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO)
{ %>
  	<%if(flag!=null){
  	   if(flag.equals("6") || flag.equals("11") || flag.equals("12")){
  	 %>
  	  <td width="35%" align="left">
  	  	<div id="HOJA2"> 
  	  		<input type="button" class="formbutton" value="Solicitar" onclick="javascript:enviar()" onmouseover="javascript:mensaje_status('Solicitar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
  	  	</div>
  	  </td>
  	 <%}}}%>
  	 <!-- Fin:mgarate:31/05/2007 -->
	<td width="50%" align="right">
		<div id="HOJA3">	
			<input type="button" class="formbutton" value="Cancelar" onclick="javascript:doCancel()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		</div>
	</td>
  </tr>
   <!-- inicio:jrosas 23-07-07 -->
   <logic:present name="rmc">
   <logic:notEqual name="rmc" value="sigc">
		<table>
			<tr>
		        <td align="center" valign="center">
		          (*)Las partidas correspondientes a los libros: Prenda Agr�cola, Prenda Industrial,Prenda Global y Flotante y
		          Prenda minera se visualizan a trav�s del �rea registral: Registro Mobiliario de Contratos. Las partidas del
		          Registro Fiscal de Ventas a Plazo a trav�s del Registro Mobiliario de Contratos o el Registro de Propiedad
		          Veh�cular, seg�n corresponda
		        </td>
		    </tr>
		</table>
	</logic:notEqual>
   </logic:present>
  <!-- fin:jrosas 23-07-07 -->
</table>
<logic:notPresent name="flagVerifica">
	<script>
		window.top.frames[0].location.reload();
	</script>
</logic:notPresent>
</BODY>
</HTML>