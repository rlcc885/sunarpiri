<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%
// Inicio:jascencio:09/08/2007
   String flag = (String)request.getAttribute("flagCertBusq");
// Fin:jascencio

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
    doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	ventana=window.open('/iri/PublicidadIRI.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
  }
}

function VerDetalle(refnum_Part,estado)
{ 
  doyou = true;
  if (estado == '0') { 
    doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	//ventana=window.open('/iri/Publicidad.do?state=detallePartidaRMC&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
	document.form1.action="/iri/PublicidadIRI.do?state=detallePartidaRMC&refnum_part=" + refnum_Part;	
	document.form1.submit();
  }
}

function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
function Imprime()
{
 window.print();
}
function paginar(numero)
{
	document.frm1.salto.value=numero;
	document.frm1.submit();
}

</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<div id="maincontent">
	<div class="innertube">
<form name="form1" method="POST">
<input type="hidden" name="busq" value="1">
</form>
<br>
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
Total de registros encontrados : <%=output.getCantidadRegistros()%>
<br>
<table border="1">
<tr bgcolor="#949400">
	<th ><font color="white">Registro P&uacute;blico</font></th>
	<th><font color="white">Oficina Registral</font></th>
	<th><font color="white">Partida</font></th>
	<th><font color="white">Ficha</font></th>
	<th><font color="white">Tomo</font></th>
	<th><font color="white">Folio</font></th>
	<th><font color="white">Area Registral</font></th>
	<logic:present name="rmc">
	     <th><font color="white">Continua de</font></th>
		 <th><font color="white"># de p&aacute;ginas</font></th>
    </logic:present>
	<th><font color="white">Registro de</font> </th>
	<th><font color="white">Participante</font></th>
	<th><font color="white">Participaci&oacute;n</font></th>
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
  if (arr1!= null){
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
					<logic:present name="rmc">
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
					</logic:present>
					<td><%=bean.getLibroDescripcion()%></td>
					<td><%=bean.getParticipanteDescripcion()%></td>
					<% if (bean.getTipoPersona().equals("J")){ %>
					    <td><%=bean.getParticipacionDescripcion()%></td>
					<%}else{
					    if (bean.getTipoPersona().equals("N")){ %>    
						   <td>&nbsp;</td>
					    <%}
					} %>	   
					<td><%=bean.getParticipanteTipoDocumentoDescripcion()%></td>
					<td><%=bean.getParticipanteNumeroDocumento()%></td>
					<%if (flagEstado==true){%>
					<td><%=bean.getEstadoInd()%></td>
					<%}%>
					<!-- inicio:jrosas 23-07-07 -->
					<logic:present name="rmc">
						<%if(bean.getCodLibro() != null && bean.getCodLibro().equals(Constantes.CODIGO_LIBRO_RMC)){%>
							<td align="center"><input name="image2" type="image" onclick="VerDetalle('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Ver Detalle" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>	
	                    <%}else{%>
	                       	<td align="center">&nbsp;</td>
	                    <%}%>	
						<td align="center"><input name="image2" type="image" onclick="VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Ver Asiento" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>	
					</logic:present>				
					<logic:notPresent name="rmc">
						<td align="center"><input name="image2" type="image" onclick="VerPartida('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Visualizar" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>	
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
						<!-- Fin:jascencio -->
					    <image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					  </a>
					</td>
					<%}%>
				  </tr>
	<%  }
	} %>
</table>

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
<table width="100%" class="tablasinestilo">    
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


<table class=tablasinestilo>
  <tr>
  	<!-- Inicio:mgarate:31/05/2007 -->
    <td width="15%" align="left">
	<!-- Fin:mgarate:31/05/2007 -->
  	<div id="HOJA2"> 
		<input type="button" class="formbutton" value="Imprimir" onclick="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>	  	
  	</div></td>
	<td width="50%" align="right">
	<div id="HOJA3">	
		<input type="button" class="formbutton" value="Cancelar" onclick="javascript:doCancel()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
	</div></td>
  </tr>
   <!-- inicio:jrosas 23-07-07 -->
   <logic:present name="rmc">
		<table class=formulario cellspacing=0 style="border-color: #949400; border-style:ridge; " width="600px">
		    <tr>
		        <td align="center" valign="center">
		          (*)Las partidas correspondientes a los libros: Prenda Agrícola, Prenda Industrial,Prenda Global y Flotante y
		          Prenda minera se visualizan a través del área registral: Registro Mobiliario de Contratos. Las partidas del
		          Registro Fiscal de Ventas a Plazo a través del Registro Mobiliario de Contratos o el Registro de Propiedad
		          Vehícular, según corresponda
		        </td>
		    </tr>
		</table>
   </logic:present>
  <!-- fin:jrosas 23-07-07 -->
</table>
<script>
window.top.frames[0].location.reload();
</script>

</div>
</div>

</BODY>
</HTML>