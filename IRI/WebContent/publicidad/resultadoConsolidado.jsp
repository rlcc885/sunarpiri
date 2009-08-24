<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="gob.pe.sunarp.extranet.util.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*"%>
<%@page import="java.util.ArrayList;"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<title>resultadoConsolidado</title>
<% 
	 InputPMasivaRelacionalBean output = (InputPMasivaRelacionalBean) request.getAttribute("bean");
	 UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
	 boolean certi=true;
	 if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
				 || usuarioBean.getExonPago()))
	 {
		certi=false;
	 }
	 OutputPMasivaRelacionalBean bean1 = new OutputPMasivaRelacionalBean();
	 ArrayList lista1;
	 lista1 = (ArrayList)request.getAttribute("listaResult");
	
 %>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<script language="JavaScript" SRC="<%=request.getContextPath()%>/javascript/overlib.js"></script>
<script language="javascript">
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
function regresar()
{
	document.frm1.action="/iri/PublicidadMasivaRelacional.do";	
	document.frm1.submit();
}
window.top.frames[0].location.reload();
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
</head>
<body>
<form name="frm1" method="post">
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
	<br>
	<table cellpadding="0" width="500" class="tablasinestilo">
		<tr>Los criterios que fueron usados son:</tr>
		<tr><br></tr>
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
				 <logic:equal name="bean" property="anoFabricacionDesde" scope="session" value="">
				 	<logic:notEqual name="bean" property="anoFabricacionHasta" scope="session" value="">
				 		<tr><td>Año de Fabricación : <bean:write name="bean" property="anoFabricacionHasta" scope="session"/></td></tr>
				 	</logic:notEqual>
				 </logic:equal>
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
			 <logic:present name="bean" property="agrupación" scope="session">
				 <logic:notEqual name="bean" property="agrupación" scope="session" value="1">
				 	<tr><td>Agrupado por : <bean:write name="bean" property="descripcionAgrupacion" scope="session"/></td></tr>
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
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="180"><b><%=request.getAttribute("registro")%></b><br><br></td>
			<td width="40"></td>
			<td width="20"></td>
		</tr>
	</table>
	<table class=grilla cellspacing="0" border="0" cellpadding="0">	
		<logic:present name="bean" property="agrupación" scope="session">
			<logic:equal name="bean" property="agrupación" scope="session" value="1">
				<tr>
					<th width="50"></th>
					<th bgcolor="993300" width="220">Zona</th>
					<th bgcolor="993300" width="50" align="center">Cantidad</th>
					<th></th>
				</tr>
				<logic:iterate name="listaResult" id="lista">
					<tr class="grilla2">
						<td width="50"></td>
						<td width="220"><bean:write name="lista" property="zonaregistral"/> </td>
						<td width="50" align="right"><bean:write name="lista" property="cantidadRegistros"/> </td>
						<td></td>
					</tr>
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="bean" property="agrupación" scope="session" value="1">
				<% String cadena2="";
				   int subtotal = 0;
				   boolean flag = false ;
				   for(int i=0;i<lista1.size();i++){%>
					<% 
					   flag = false;
					   String cadena = ((OutputPMasivaRelacionalBean)lista1.get(i)).getZonaregistral();
					   
					   if(!cadena.equals(cadena2))
					   {
					   	  if(!cadena2.equals(""))
					  	  { flag=true; }
					   	  if(flag)
					   	  { 
					   	    %>
					   	    	<tr>
					   	    		<td><td/>
				 					<b><hr size="2" color="BLACK"></b>
				 					<td align="right"><hr color="BLACK"><td/>
					   	   
					   	    	</tr>
					   	    	<tr>
					   	    		<td></td>
					   	    		<td><b>SubTotal:<br>&nbsp;</b></td>
					   	    		<td align="right"><b><%=subtotal%><br>&nbsp;</b></td>
					   	    		
					   	    	</tr> 
								
					   	    <% subtotal=0;
					   	   
					      }
					  	  %><tr class="grilla2">
					  	  		<th align="right"></th>
					  	  		<th bgcolor="993300" width="50%"><%=cadena%><br>
					  	  		</th><th bgcolor="993300">Cantidad</th>
					  	  </tr> <% 
					   }
					   subtotal=subtotal + Integer.parseInt(((OutputPMasivaRelacionalBean)lista1.get(i)).getCantidadRegistros());
					   cadena2=cadena;
					%>
					<tr>
						<td></td>
						<td width="150"><%=((OutputPMasivaRelacionalBean)lista1.get(i)).getSubagrupacion()%></td>
						<td align="right"><%=((OutputPMasivaRelacionalBean)lista1.get(i)).getCantidadRegistros()%></td>
					</tr>
				<%}%>
				<%if(subtotal!=0)
				   {
				   	  %>
        		   	    <tr>
			   	    		<td></td>
			   	    		<td>
				<hr color="BLACK">
				</td>
			   	    		<td><hr color="BLACK"></td>
			   	    	</tr> 
			   	    	<tr>
			   	    		<td></td>
			   	    		<td><b>SubTotal:<br>&nbsp;</b></td>
			   	    		<td align="right"><b><%=subtotal%><br>&nbsp;</b></td>
			   	    	</tr> 
					  <%
				   }
				%>
			</logic:notEqual>
		</logic:present>
		<logic:notPresent name="bean" property="agrupación" scope="session">
				<logic:iterate name="listaResult" id="lista">
					<tr>
						<th width="50"></th>
						<th bgcolor="993300" width="220">Zona</th>
						<th bgcolor="993300" width="50" align="center">Cantidad</th>
						<th></th>
					</tr>
					<tr class="grilla2">
						<td width="50"></td>
						<td width="220"><bean:write name="lista" property="zonaregistral"/> </td>
						<td align="right" width="50"><bean:write name="lista" property="cantidadRegistros"/> </td>
					</tr>
				</logic:iterate>
		</logic:notPresent>
		<tr>
			<td></td>
			<td><hr color="BLACK"></td>
			<td><hr color="BLACK"></td>
		</tr>
		<tr>
			<td width="50"></td>
			<td width="220"><b>TOTAL</b></td>
			<td align="right" width="50"><b><%=request.getAttribute("cantidad")%></b></td>
		</tr>
	</table>
<br>
<br>
<table class=tablasinestilo>
  <tr>
  	<td align="left" width="40%">
	  	<div id="HOJA2"> 
	  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
	  	</div>
  	</td>
	<td align="left" width="60%">
		<div id="HOJA3">	
		<a href="javascript:regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
		</div>
	</td>
  </tr>
</table>
</form>
</body>
</html>
