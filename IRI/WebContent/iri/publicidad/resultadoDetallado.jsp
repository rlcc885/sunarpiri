<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="gob.pe.sunarp.extranet.util.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%
InputPMasivaRelacionalBean output = (InputPMasivaRelacionalBean) request.getAttribute("bean");
UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
boolean certi=true;
if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
			 || usuarioBean.getExonPago()))
{
	certi=false;
}
%>
<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<title>resultadoDetallado</title>
<script language="JavaScript" src="/webapp/extranet/javascript/util.js"></script>
<script language="JavaScript" SRC="/webapp/extranet/javascript/overlib.js"></script>
<script language="javascript">
var clicked = false;
function VerPartida(num_placa,estado,ofic)
{
//	ventana=window.open('/webapp/extranet/Publicidad.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
	if(clicked == false)
	{
		clicked = true;
		doyou = true;
		if (estado == '0') { 
		  doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
		}
		if(doyou == true) {
		  document.frm1.action="/webapp/extranet/Publicidad.do?state=buscarXPlacaDet&txt4=" + num_placa+ "&CboOficinas=" + ofic;
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
	  ventana=window.open('/webapp/extranet/Publicidad.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
	}
}
function regresar()
{
	document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do";	
	document.frm1.submit();
}
function paginar(numero)
{
	document.frm1.salto.value=numero;
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
	document.frm1.submit();
}
function VerDetalle(refnum_Part,estado)
{ 
  doyou = true;
  if (estado == '0') { 
    doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	//ventana=window.open('/webapp/extranet/Publicidad.do?state=detallePartidaRMC&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
	//documento.frm1.refNumPart.value=refnum_Part;
	//documento.frm1.estado.value=estado;
	document.frm1.action="/webapp/extranet/Publicidad.do?state=detallePartidaRMC&refnum_part=" + refnum_Part;	
	document.frm1.submit();
  }
}
function ordenar(valor)
{
	document.frm1.salto.value=1;
	document.frm1.criterio.value=valor;
	if(document.frm1.registro.value=="V")
	{
		document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaVehicular&criterio="+valor;	
	}
	if(document.frm1.registro.value=="A")
	{
		document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaAeroNave&criterio="+valor;	
	}
	if(document.frm1.registro.value=="B")
	{
		document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaBuques&criterio="+valor;	
	}
	if(document.frm1.registro.value=="E")
	{
		document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaEmbarcacionPesquera&criterio="+valor;	
	}
	if(document.frm1.registro.value=="R")
	{
		document.frm1.action="/webapp/extranet/PublicidadMasivaRelacionalIRI.do?state=busquedaRMC&criterio="+valor;	
	}
	document.frm1.submit();
}
function Imprime()
{
 window.print();
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
window.top.frames[0].location.reload();
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
</head>
<body>
<form name="frm1" method="post">
<input type="hidden" name="flagPagineo2" value="1">
<input type="hidden" name="salto" value="0">
<input type="hidden" name="cantidad" value="<%=output.getCantidadRegistros()%>"/>
<input type="hidden" name="registro" value="<%=request.getAttribute("regis")%>"/>
<logic:present name="criterio">
	<input type="hidden" name="criterio" value="<%=request.getAttribute("criterio")%>"/>
</logic:present>
<logic:notPresent name="criterio">
	<input type="hidden" name="criterio" value=""/>
</logic:notPresent>
<table width="600" border="0" cellpadding="0" cellspacing="2">
	  <tr> 
	    <td><b>SERVICIOS&gt;&gt;<font size="1"></font> <font color="#90000">Publicidad
		Masiva Relacional</font></b></td>
	  </tr>
	  <tr> 
	    <td bgcolor="#000000"><img src="../images/space.gif" width="5" height="1"></td>
	  </tr>
</table>
<table border=0 width=600>
  <tr> 
	    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<bean:write name="bean" property="precio" scope="session"/></td>
	    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=usuarioBean.getUserId()%></td>
	    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><bean:write name="bean" property="fechaAct" scope="session"/></td>
	    <td vAlign=top align=left width = 55%><font color="black">Ahora tenemos un nuevo medio de publicidad registral, de libre acceso para todos: www.sunarp.gob.pe.</font></td>
  </tr>
</table>
<table class="tablasinestilo" cellspacing="0" width="500">
		<tr>
			<td width="250">Los criterios que fueron usados son:<td/>
		</tr>
			 <logic:present name="bean" property="fechaInscripcionDesde" scope="session">
				 <logic:notEqual name="bean" property="fechaInscripcionDesde" scope="session" value="">
				 	<tr><td>Fecha de Inscripcion : 
				 		<bean:write name="bean" property="fechaInscripcionDesde"  scope="session"/>
				 		<logic:notEqual name="bean" property="fechaInscripcionHasta" scope="session" value="">-<bean:write name="bean" property="fechaInscripcionHasta"  scope="session"/>
					 	</logic:notEqual>
				 	</td></tr>
				 </logic:notEqual>
				 <logic:equal name="bean" property="fechaInscripcionDesde" scope="session" value="">
				 	<logic:notEqual name="bean" property="fechaInscripcionHasta" scope="session" value="">
				 	  <tr><td>Fecha de Inscripcion : 
				 		<bean:write name="bean" property="fechaInscripcionHasta"  scope="session"/>
				 	  </td></tr>
				 	</logic:notEqual>
				 </logic:equal>
			 </logic:present>
			 <logic:present name="bean" property="marca" scope="session">
				 <logic:notEqual name="bean" property="marca" scope="session" value="">
				 	<tr><td>Marca : <bean:write name="bean" property="marca"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="modelo" scope="session">
				 <logic:notEqual name="bean" property="modelo" scope="session" value="">
				 	<tr><td>Modelo : <bean:write name="bean" property="modelo"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="anoFabricacionDesde" scope="session">
				 <logic:notEqual name="bean" property="anoFabricacionDesde" scope="session" value="">
				 	<tr><td>Año de Fabricación : 
				 		<bean:write name="bean" property="anoFabricacionDesde"  scope="session"/>
				 		<logic:notEqual name="bean" property="anoFabricacionHasta" scope="session" value="">-<bean:write name="bean" property="anoFabricacionHasta" scope="session"/>
					 	</logic:notEqual>
				 	</td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="montoGarantiaDesde" scope="session">
				 <logic:notEqual name="bean" property="montoGarantiaDesde" scope="session" value="">
				 	<tr><td>Monto de Garantia : 
				 		<bean:write name="bean" property="montoGarantiaDesde"  scope="session"/>
				 		<logic:notEqual name="bean" property="montoGarantiaHasta" scope="session" value="">-<bean:write name="bean" property="montoGarantiaHasta" scope="session"/>
					 	</logic:notEqual>
				 	</td></tr>
				 </logic:notEqual>
				 <logic:equal name="bean" property="montoGarantiaDesde" scope="session" value="">
				 	<logic:notEqual name="bean" property="montoGarantiaHasta" value="">
				 		<tr><td>Monto de Garantia : <bean:write name="bean" property="montoGarantiaHasta" scope="session"/></td></tr>
				 	</logic:notEqual>
				 </logic:equal>
			 </logic:present>
			 <logic:present name="bean" property="descripcionTipoVehiculo" scope="session">
				 <logic:notEqual name="bean" property="descripcionTipoVehiculo" scope="session" value="">
				 	<tr><td>Tipo de Vehiculo : <bean:write name="bean" property="descripcionTipoVehiculo"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="colorVeh" scope="session">
				 <logic:notEqual name="bean" property="colorVeh" scope="session" value="">
				 	<tr><td>Color : <bean:write name="bean" property="colorVeh"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="descripcionTipoComb" scope="session">
				 <logic:notEqual name="bean" property="descripcionTipoComb" scope="session" value="">
				 	<tr><td>Tipo de Combustible : <bean:write name="bean" property="descripcionTipoComb"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="tipActo" scope="session">
				 <logic:notEqual name="bean" property="tipActo" scope="session" value="">
				 	<logic:equal name="bean" property="tipActo" scope="session" value="T">
				 		<tr><td>Tipo de Acto : TODOS</td></tr>
				 	</logic:equal>
				 	<logic:equal name="bean" property="tipActo" scope="session" value="CAN">
				 		<tr><td>Tipo de Acto : CANCELACIONES</td></tr>
				 	</logic:equal>
				 	<logic:equal name="bean" property="tipActo" scope="session" value="GRA">
				 		<tr><td>Tipo de Acto : GRAVAMENES</td></tr>
				 	</logic:equal>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="descripcionTipoAeronave" scope="session">	
				 <logic:notEqual name="bean" property="descripcionTipoAeronave" scope="session" value="">
				 	<tr><td>Tipo de AeroNave : <bean:write name="bean" property="descripcionTipoAeronave"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="nombreBuque" scope="session">
				 <logic:notEqual name="bean" property="nombreBuque" scope="session" value="">
				 	<tr><td>Nombre de Buque : <bean:write name="bean" property="nombreBuque"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="descripcionCapitania" scope="session">
				 <logic:notEqual name="bean" property="descripcionCapitania" scope="session" value="">
				 	<tr><td>Capitania : <bean:write name="bean" property="descripcionCapitania"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="nombreEmbarcacionPesquera" scope="session">
				 <logic:notEqual name="bean" property="nombreEmbarcacionPesquera" scope="session" value="">
				 	<tr><td>Nombre de Embarcación : <bean:write name="bean" property="nombreEmbarcacionPesquera"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
			 <logic:present name="bean" property="descripcionTipoEmb" scope="session">
				 <logic:notEqual name="bean" property="descripcionTipoEmb" scope="session" value="">
				 	<tr><td>Tipo de Embacación : <bean:write name="bean" property="descripcionTipoEmb"  scope="session"/></td></tr>
				 </logic:notEqual>
			 </logic:present>
		<tr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td><br></td></tr>
		<tr>
			<td width="180"><b><%=request.getAttribute("registro")%></b></td>
		</tr>
</table>
<br>
Total de registros encontrados : <bean:write name="bean" property="cantidadRegistros"/>
<br>
<table cellspacing="0" class=grilla>
	<logic:equal name="regis" value="A">
		<tr>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('REG.SIGLAS')" style="color: white"><u>Registro Publico</u></a></span></th>
			<th bgcolor="993300">Oficina Registral</th>
			<th bgcolor="993300">Partida</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('PART.TS_INSCRIP')" style="color: white"><u>Fecha de Inscripción</u></a></span></th>
			<th bgcolor="993300">Matricula</th>
			<th bgcolor="993300">Serie</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('TIPO')" style="color: white"><u>Tipo</u></a></span></th>
			<th bgcolor="993300">Modelo</th>
			<th bgcolor="993300">Propietario</th>
			<th bgcolor="993300">Ver Asientos</th>
			<%if(certi){%>
			<th bgcolor="993300">Copia Literal de Partida</th>
			<%}%>
		</tr>
		<logic:iterate name="listaResult" id="lista">
			<tr class="grilla2">
				<td><bean:write name="lista" property="sigla"/></td>
				<td><bean:write name="lista" property="nombreoficina"/></td>
				<td><bean:write name="lista" property="numeroPartida"/></td>
				<td><bean:write name="lista" property="fechaInscripcionDesde"/></td>
				<td><bean:write name="lista" property="numeromatricula"/></td>
				<td>&nbsp;<bean:write name="lista" property="numeroserie"/>&nbsp;</td>
				<td>&nbsp;<bean:write name="lista" property="tipoAeronave"/>&nbsp;</td>
				<td>&nbsp;<bean:write name="lista" property="modelo"/>&nbsp;</td>
				<td>&nbsp;<bean:write name="lista" property="propietario"/>&nbsp;</td>
				<td align="center"><a href="javascript:VerAsientos('<bean:write name="lista" property="refnumpart"/>','<bean:write name="lista" property="estado"/>');"><image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Asientos');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%if(certi){%>
				<td align="center"><a href="/webapp/extranet/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="lista" property="refnumpart"/>&noPartida=<bean:write name="lista" property="numeroPartida"/>&hidOfic=<bean:write name="lista" property="nombreoficina"/>&area=<bean:write name="lista" property="areaid"/>&hidTipo=L"><image src="/webapp/extranet/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%}%>
			</tr>
		</logic:iterate>
	</logic:equal><logic:equal name="regis" value="B">
		<tr class=grilla>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('REG.SIGLAS')" style="color: white"><u>Registro Publico</u></a></span></th>
			<th bgcolor="993300">Oficina Registral</th>
			<th bgcolor="993300">Partida</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('PART.TS_INSCRIP')" style="color: white"><u>Fecha de Inscripción</u></a></span></th>
			<th bgcolor="993300">Matricula</th>
			<th bgcolor="993300">Nombre</th>
			<th bgcolor="993300">Capitania</th>
			<th bgcolor="993300">Propietario</th>
			<th bgcolor="993300">Ver Asientos</th>
			<%if(certi){%>
			<th bgcolor="993300">Copia Literal de Partida</th>
			<%}%>
		</tr>
		<logic:iterate name="listaResult" id="lista">
			<tr class="grilla2">
				<td><bean:write name="lista" property="sigla"/></td>
				<td><bean:write name="lista" property="nombreoficina"/></td>
				<td><bean:write name="lista" property="numeroPartida"/></td>
				<td><bean:write name="lista" property="fechaInscripcionDesde"/></td>
				<td><bean:write name="lista" property="numeromatricula"/></td>
				<td><bean:write name="lista" property="nombreBuque"/></td>
				<td>&nbsp;<bean:write name="lista" property="capitaniaDescripcion"/>&nbsp;</td>
				<logic:notPresent name="lista" property="propietario">
					<td align="center">&nbsp;</td>					
				</logic:notPresent>
				<logic:present name="lista" property="propietario">
					<td>&nbsp;<bean:write name="lista" property="propietario"/>&nbsp;</td>
				</logic:present>
				<td align="center"><a href="javascript:VerAsientos('<bean:write name="lista" property="refnumpart"/>','<bean:write name="lista" property="estado"/>');"><image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Asientos');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%if(certi){%>
				<td align="center"><a href="/webapp/extranet/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="lista" property="refnumpart"/>&noPartida=<bean:write name="lista" property="numeroPartida"/>&hidOfic=<bean:write name="lista" property="nombreoficina"/>&area=<bean:write name="lista" property="areaid"/>&hidTipo=L"><image src="/webapp/extranet/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%}%>
			</tr>
		</logic:iterate>
	</logic:equal>
	<logic:equal name="regis" value="E">
		<tr>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('REG.SIGLAS')" style="color: white"><u>Registro Publico</u></a></span></th>
			<th bgcolor="993300">Oficina Registral</th>
			<th bgcolor="993300">Partida</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('PART.TS_INSCRIP')" style="color: white"><u>Fecha de Inscripción</u></a></span></th>
			<th bgcolor="993300">Matricula</th>
			<th bgcolor="993300">Nombre</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('TIPO')" style="color: white"><u>Tipo</u></a></span></th>
			<th bgcolor="993300">Propietario</th>
			<th bgcolor="993300">Capitania</th>
			<th bgcolor="993300">Ver Asientos</th>
			<%if(certi){%>
			<th bgcolor="993300">Copia Literal de Partida</th>
			<%}%>
		</tr>
		<logic:iterate name="listaResult" id="lista">
			<tr class="grilla2">
				<td><bean:write name="lista" property="sigla"/></td>
				<td><bean:write name="lista" property="nombreoficina"/></td>
				<td><bean:write name="lista" property="numeroPartida"/></td>
				<td><bean:write name="lista" property="fechaInscripcionDesde"/>&nbsp;</td>
				<td><bean:write name="lista" property="numeromatricula"/></td>
				<td><bean:write name="lista" property="nombreEmbarcacion"/></td>
				<td>&nbsp;<bean:write name="lista" property="tipoEmbarcacion"/>&nbsp;</td>
				<logic:notPresent name="lista" property="propietario">
					<td align="center">&nbsp;</td>					
				</logic:notPresent>
				<logic:present name="lista" property="propietario">
					<td>&nbsp;<bean:write name="lista" property="propietario"/>&nbsp;</td>
				</logic:present>
				<td><bean:write name="lista" property="capitaniaDescripcion"/>&nbsp;</td>
				<td align="center"><a href="javascript:VerAsientos('<bean:write name="lista" property="refnumpart"/>','<bean:write name="lista" property="estado"/>');"><image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Asientos');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%if(certi){%>
				<td align="center"><a href="/webapp/extranet/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="lista" property="refnumpart"/>&noPartida=<bean:write name="lista" property="numeroPartida"/>&hidOfic=<bean:write name="lista" property="nombreoficina"/>&area=<bean:write name="lista" property="areaid"/>&hidTipo=L"><image src="/webapp/extranet/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%}%>
			</tr>
		</logic:iterate>
	</logic:equal>
	<logic:equal name="regis" value="V">
		<tr>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('REG.SIGLAS')" style="color: white"><u>Registro Publico</u></a></span></th>
			<th bgcolor="993300">Oficina Registral</th>
			<th bgcolor="993300">Partida</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('VEH.TS_INSCRIP')" style="color: white"><u>Fecha de Inscripción</u></a></span></th>
			<th bgcolor="993300">Placa</th>
			<th bgcolor="993300">Serie</th>
			<th bgcolor="993300">Motor</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('VEH.ANO_FABRIC')" style="color: white"><u>Año de Fabricacion</u></a></span></th>
			<th bgcolor="993300">Color</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('MARCA')" style="color: white"><u>Marca</u></a></span></th>
			<th bgcolor="993300">Modelo</th>
			<th bgcolor="993300">Propietario</th>
			<th bgcolor="993300">Dirección</th>
			<th bgcolor="993300">Ver Detalle</th>
			<th bgcolor="993300">Ver Asientos</th>
			<%if(certi){%>
			<th bgcolor="993300">Copia Literal de Partida</th>
			<%}%>
		</tr>
		<logic:iterate name="listaResult" id="lista">
			<tr class="grilla2">
				<td><bean:write name="lista" property="sigla"/></td>
				<td><bean:write name="lista" property="nombreoficina"/></td>
				<td><bean:write name="lista" property="numeroPartida"/></td>
				<td><bean:write name="lista" property="fechaInscripcionDesde"/></td>
				<td><bean:write name="lista" property="numeroplaca"/></td>
				<td><bean:write name="lista" property="numeroserie"/></td>
				<td><bean:write name="lista" property="numeromotor"/></td>
				<td><bean:write name="lista" property="anofabricacion"/></td>
				<td><bean:write name="lista" property="color"/></td>
				<td><bean:write name="lista" property="marca"/></td>
				<td><bean:write name="lista" property="modelo"/></td>
				<logic:notPresent name="lista" property="propietario">
					<td align="center">&nbsp;</td>					
				</logic:notPresent>
				<logic:present name="lista" property="propietario">
					<td><bean:write name="lista" property="propietario"/></td>
				</logic:present>
				<logic:notPresent name="lista" property="direccion">
					<td align="center">&nbsp;</td>
				</logic:notPresent>
				<logic:present name="lista" property="direccion">
					<td><bean:write name="lista" property="direccion" filter="false"/></td>
				</logic:present>
				<td align="center"><a href="javascript:VerPartida('<bean:write name="lista" property="numeroplaca"/>','<bean:write name="lista" property="estadoVehiculo"/>','<bean:write name="lista" property="regpubid"/>|<bean:write name="lista" property="oficregid"/>');"><image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<td align="center"><a href="javascript:VerAsientos('<bean:write name="lista" property="refnumpart"/>','<bean:write name="lista" property="estado"/>');"><image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Asientos');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%if(certi){%>
				<td align="center"><a href="/webapp/extranet/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="lista" property="refnumpart"/>&noPartida=<bean:write name="lista" property="numeroPartida"/>&hidOfic=<bean:write name="lista" property="nombreoficina"/>&area=<bean:write name="lista" property="areaid"/>&hidTipo=L"><image src="/webapp/extranet/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%}%>
			</tr>
		</logic:iterate>
	</logic:equal>
	<logic:equal name="regis" value="R">
		<tr class=grilla>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('REG.SIGLAS')" style="color: white"><u>Registro Publico</u></a></span></th>
			<th bgcolor="993300">Oficina Registral</th>
			<th bgcolor="993300">Partida</th>
			<th bgcolor="993300"><span style="color: white"><a href="javascript:ordenar('PART.TS_INSCRIP')" style="color: white"><u>Fecha de Inscripción</u></a></span></th>
			<th bgcolor="993300">Monto</th>
			<th bgcolor="993300">Tipo de Contrato</th>
			<th bgcolor="993300">Estado de Partida</th>
			<th bgcolor="993300">Ver Detalle</th>
			<th bgcolor="993300">Ver Asientos</th>
			<%if(certi){%>
			<th bgcolor="993300">Copia Literal de Partida</th>
			<%}%>
		</tr>
		<logic:iterate name="listaResult" id="lista">
			<tr class="grilla2">
				<td align="center"><bean:write name="lista" property="sigla"/></td>
				<td><bean:write name="lista" property="nombreoficina"/></td>
				<td align="center"><bean:write name="lista" property="numeroPartida"/></td>
				<td align="center"><bean:write name="lista" property="fechaInscripcionDesde"/></td>
				<logic:notPresent name="lista" property="monto">
					<td align="center">&nbsp;</td>
				</logic:notPresent>
				<logic:present name="lista" property="monto">
					<td align="center"><bean:write name="lista" property="monto"/></td>
				</logic:present>
				<td align="center"><bean:write name="lista" property="tipoContrato"/></td>
				<td align="center"><bean:write name="lista" property="estadoPartida"/></td>
				<td align="center">
				<a href="javascript:VerDetalle('<bean:write name="lista" property="refnumpart"/>','<bean:write name="lista" property="estado"/>');">
				<logic:equal name="lista" property="codLibro" value="099">
					<image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				</logic:equal>
				</a>
				</td>
				<td align="center"><a href="javascript:VerAsientos('<bean:write name="lista" property="refnumpart"/>','<bean:write name="lista" property="estado"/>');"><image src="/webapp/extranet/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Ver Asientos');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				<%if(certi){%>
				<logic:equal name="lista" property="codLibro" value="099">
					<td align="center"><a href="/webapp/extranet/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="lista" property="refnumpart"/>&noPartida=<bean:write name="lista" property="numeroPartida"/>&hidOfic=<bean:write name="lista" property="nombreoficina"/>&area=<bean:write name="lista" property="areaid"/>&hidTipo=LR"><image src="/webapp/extranet/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				</logic:equal>
				<logic:notEqual name="lista" property="codLibro" value="099">
					<td align="center"><a href="/webapp/extranet/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="lista" property="refnumpart"/>&noPartida=<bean:write name="lista" property="numeroPartida"/>&hidOfic=<bean:write name="lista" property="nombreoficina"/>&area=<bean:write name="lista" property="areaid"/>&hidTipo=L"><image src="/webapp/extranet/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a></td>
				</logic:notEqual>
				<%}%>
			</tr>
		</logic:iterate>
	</logic:equal>
</table>

<br>
Mostrando Partidas del <%=output.getNdel()%> al <%=output.getNal()%>
<br>
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
<table class=tablasinestilo>
  <tr>
  	<td align="left" width="40%">
	  	<div id="HOJA2"> 
	  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
	  	</div>
  	</td>
	<td align="right" width="74%">
	<div id="HOJA3">	
	<a href="javascript:regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div></td>
  </tr>
</table> 
</form>
</body>
</html>
