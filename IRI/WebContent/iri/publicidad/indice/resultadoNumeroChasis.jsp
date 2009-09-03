<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>

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
var clicked = false;

function VerPartida(num_placa,estado,ofic)
{
//	ventana=window.open('/iri/PublicidadIRI.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
	if(clicked == false)
	{
		clicked = true;
		doyou = true;
		if (estado == '0') { 
		  doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
		}
		if(doyou == true) {
		  document.frm1.action="/iri/PublicidadIRI.do?state=buscarXPlacaDet&txt4=" + num_placa+ "&CboOficinas=" + ofic;
		  document.frm1.submit();	
		}
	}
		
}
function VerAsientos(refnum_Part,estado)
{
	doyou = true;
	if (estado == '0') { 
	  doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
	}
	if(doyou == true) {
	  ventana=window.open('/iri/PublicidadIRI.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
	}
}
function Imprime()
{
 window.print();
}
function Imprimir()
{
	//HOJA2.style.visibility="hidden";
	//HOJA3.style.visibility="hidden";
	window.print();
	//HOJA2.style.visibility="visible";
	//HOJA3.style.visibility="visible";	
}
function paginar(numero)
{
	document.frm1.salto.value=numero;
	document.frm1.submit();
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
	document.frm1.action="/iri/CertificadosIRI.do?state=guardarDatosBasicos&hidTipo=B&criterio=<%=request.getAttribute("criterioBusqueda")%>&flagCertBusq=<%=flag%>&flagBusq=I";
	document.frm1.submit();
}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<div id="maincontent">
	<div class="innertube">
		<form name="form1">
		</form>
		<br>
		<b><font color="#949400">Resultado de la b&uacute;squeda</font></b>
		<br>
		<table border=0 width=600>
		  <tr> 
			    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<%=request.getAttribute("tarifa")%></td>
			    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=request.getAttribute("usuaEtiq")%></td>
			    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><%=request.getAttribute("fechaAct")%></td>
			    <td vAlign=top align=left width = 55%></td>
		  </tr>
		</table>
		<br>
		Total de registros encontrados : <%=output.getCantidadRegistros()%>
		<br>
		<table border="1">
		  <tr bgcolor="#949400">
			<th><font color="white">Registro P&uacute;blico</font></th>
			<th><font color="white">Oficina Registral</font></th>
			<th><font color="white">Partida</font></th>
			<th><font color="white">Placa</font></th>
			<th><font color="white">N&uacute;mero de Motor</font></th>
			<th><font color="white">N&uacute;mero de Serie</font></th>	
			<th><font color="white">Marca</font></th>
			<th><font color="white">Modelo</font></th>
			<th><font color="white">Estado de Vehiculo</font></th>
			<th><font color="white">Ver Detalle</font></th>
			<th><font color="white">Ver Asientos</font></th>
			<%if(certi){%>
			<th><font color="white">Copia Literal de Partida</font></th>
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
						<td><%=bean.getNumeroPlaca()%></td>
						<td><%=bean.getNumeroMotor()%></td>
						<td><%=bean.getNumeroSerie()%></td>				
						<td><%=bean.getMarca()%></td>
						<td><%=bean.getModelo()%></td>
						<td><%=bean.getBaja()%></td>
						<!-- td align="center"><input name="image2" type="image" onclick="VerPartida('<%=bean.getRefNumPart()%>')" value="Visualizar" src="images/lupa.gif" onmouseover="javascript:mensaje_status('Ver Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td -->
						<td align="center"><input name="image2" type="image" onclick="VerPartida('<%=bean.getNumeroPlaca()%>','<%=bean.getEstado()%>','<%=bean.getRegPubId()%>|<%=bean.getOficRegId()%>')" value="Visualizar" src="images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
						<td align="center"><input name="image2" type="image" onclick="VerAsientos('<%=bean.getRefNumPart()%>','<%=bean.getEstado()%>')" value="Visualizar" src="images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
						<%if(certi){%>
						<td align="center">
						  <a href="/iri/CertificadosIRI.do?state=guardarDatosBasicos&refnum_part=<%=bean.getRefNumPart()%>&noPartida=<%=bean.getNumPartida()%>&hidOfic=<%=bean.getOficRegDescripcion()%>&area=<%=bean.getAreaRegistralId()%>&hidTipo=L">
						    <image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
						  </a>
						</td>
						<%}%>
						</tr>
		<%  } %>
		</table>
		
		<br>
		Mostrando Partidas del <%=output.getNdel()%> al <%=output.getNal()%>
		
		<%-- formulario para pagineo --%>
		<br>
		<form  name="frm1" method="post" action="<%=output.getAction()%>">
		<input type="hidden" name="flagPagineo" value="1">
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
		
		<br>
		<table class=tablasinestilo>
		  <tr>
		  	<!-- Inicio:mgarate:31/05/2007 -->
		    <td width="15%" align="left">
			<!-- Fin:mgarate:31/05/2007 -->
		  	<div id="HOJA2"> 
		  		<input type="button" class="formbutton" value="Imprimir" onclick="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/> 
		  	</div></td>
		  	<!-- Inicio:mgarate:31/05/2007 -->
		  	<% if(usuarioBean.getPerfilId()==Constantes.PERFIL_CAJERO || usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO)
		{ %>
		  	<%if(flag!=null){
		  	   if(flag.equals("6") || flag.equals("11") || flag.equals("12")){
		  	 %>
		  	  <td width="35%" align="left">
		  	  <div id="HOJA2"> 
		  	  <input type="button" class="formbutton" value="Solicitar" onclick="javascript:enviar();" onmouseover="javascript:mensaje_status('Solicitar Certificado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		  	  </div></td>
		  	 <%}}}%>
		  	 <!-- Fin:mgarate:31/05/2007 -->
			<td width="50%" align="right">
			<div id="HOJA3">	
				<input type="button" class="formbutton" value="Cancelar" onclick="javascript:doCancel()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/> 
			</div></td>
		  </tr>
		</table>
		<script>
		window.top.frames[0].location.reload();
		</script>
	</div>
</div>
</BODY>
</HTML>