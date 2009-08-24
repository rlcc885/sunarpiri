<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<% 
	ArrayList list = (ArrayList)request.getAttribute("arrBusqueda");
	ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.extranet.publicidad.properties.Publicidad");
%>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script> 
<TITLE>
	 Registros Jurídicos de Bienes (RJB)  
</TITLE>
<SCRIPT LANGUAGE="JavaScript">
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
</script> 

</HEAD>
<BODY>

<logic:present name="Certificado" scope="request">	
	<table border="0">
		<TR>
			<TD width="60">&nbsp;</TD>
			<TD width="601">&nbsp;</TD>
			<td width="256"></td>
			<TD width="45">&nbsp;</TD>
		</TR>	
		<TR>
			<TD>&nbsp;</TD>
			<TD width="601">&nbsp;</TD>
			<td width="256"></td>
			<TD width="45">&nbsp;</TD>
		</TR>	
		<TR>
			<TD>&nbsp;</TD>
			<TD width="601">&nbsp;</TD>
			<td width="256"></td>
			<TD width="45">&nbsp;</TD>
		</TR>	
		<TR>
        	<TD><img src="<%=request.getContextPath()%>/images/orlclogo.gif"></TD>
            <TD width="601"></TD>
			<td width="256">
			<table border="0">
				<tr>
					<td width="242">Expediente N°</td>
					<td width="242"><bean:write name="Certificado" property="solicitud_id" scope="request"/></td>
				</tr>
				<tr>
					<td width="242">Derechos Pagados:</td>
					<td width="242"><bean:write name="Certificado" property="total" scope="request"/></td>
				</tr>
			</table>
			</td>
			<TD width="45">
            </TD>
        </TR>
        <TR>
        	<TD><bean:write name="detalle" property="zonaRegistral" /></TD>
            <TD colspan="2"></TD>
            <TD width="45">&nbsp;</TD>
        </TR>
        <tr>
        	<td>SEDE : <bean:write name="detalle" property="oficinaRegistral" /></td>
        	<td></td>
        	<td></td>
        </tr>
        <tr>
            <TD colspan="3" width="620"><div align="center">
                <b><font size="+1">
                	<logic:equal name="Certificado" property="tpo_certificado" value="M"><%=bundle.getString("msn.certificado.rjb.gravamen.constancia.titulo")%></logic:equal><logic:equal name="Certificado" property="tpo_certificado" value="D"><%=bundle.getString("msn.certificado.rjb.dominial.constancia.titulo")%></logic:equal></font></b></div>
                </TD>	
        </tr>
</table>
<logic:equal name="Certificado" property="tpo_certificado" value="M">
 <logic:present name="detalle" property="nombrePropietario">
	<br>
	<b>Datos del Propietario</b>
	<br>
	<br>
	<table cellspacing="0">	
	   <% DetalleRjbBean nombresProp = (DetalleRjbBean) request.getAttribute("detalle");
	      ArrayList listadonombresProp = (ArrayList)nombresProp.getNombrePropietario();
	      ArrayList listadodireccionProp = (ArrayList)nombresProp.getDireccion();
	      ArrayList listadodniProp = (ArrayList)nombresProp.getDni();
	      ArrayList listadotipoDocProp = (ArrayList)nombresProp.getTipoDocumento();
	      
	      for (int j=0; j<listadonombresProp.size(); j++){
	      	String nombrepropietario = (String)listadonombresProp.get(j);
			if (nombrepropietario != null){
	    %>
 			   <tr>
				  <td>Nombre : </td>
				  <td align="left"><%= nombrepropietario %></td>
				  <td></td>
			   </tr>
			   <% String direccion =  (String)listadodireccionProp.get(j); 
			      if (direccion != null ){
			         if (direccion.equals("")){
			         }else{ %>
	 					 <tr>
							<td>Direccion : </td>
							<td><%= direccion %></td>
							<td></td>
						 </tr>
			   <%    }
			   	   } %>					
			   <% String tipodocumento =  (String)listadotipoDocProp.get(j); 
			      if (tipodocumento != null ){
			     	 if (tipodocumento.equals("")){ 
			     	 }else{ %>
						<tr>
							<td><%= tipodocumento %> : </td>
							<% String dni =  (String)listadodniProp.get(j); 
			      			   if (dni != null ){
			     	 		     if (!dni.equals("")){ %>
									<td><%= dni %></td>
							<%   }
							   } %>	
   					    </tr>
   			   <%    }
   			      } %>
   			     <tr><td colspan="3"></td></tr>
   			  <br><br>    
   	    <%   } 
   	      } %>		      		  
	</table>
  
 </logic:present>
</logic:equal>
<br><br>
<b>Caracteristicas del Bien</b>
<br>
<br>
<logic:present name="detalle" property="tipoRegistro">
<logic:equal name="detalle" property="tipoRegistro" value="V">
<table border="0" cellspacing="0">
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr background="AFAFAF">
		<td>Placa</td>
		<td width="20"></td>
		<td align="left"><bean:write name="detalle" property="numPlaca"/></td>
		<td width="20"></td>
		<td></td>
		<td width="20">Oficina</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="oficinaRegistral"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Estado</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="estadoVehiculo"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Fecha de Inscripción</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="fechaInscripcion"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Fecha de Propiedad</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="fechaPropiedad"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Condición</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="condicionVehiculo"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Partida Registral</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numPartida"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Clase</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="claseVehiculo"/></td>
		<td></td>
		<td></td>
		<td>Marca</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="marcaVehiculo"/></td>
		<td width="20"></td>
		<td>Año de fabricación</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="anoFabricacion"/></td>
	</tr>
	<tr>
		<td>Modelo</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="modeloVehiculo"/></td>
		<td></td>
		<td></td>
		<td>Combustible</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="tipoCombustible"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Carroceria</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="carrocería"/></td>
		<td></td>
		<td></td>
		<td>Ejes</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="ejeVehiculo"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Colores</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="color"/></td>
		<td></td>
		<td></td>
		<td>N° de Motor</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numMotor"/></td>
		<td width="20"></td>
		<td>Cilindros</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numCilindro"/></td>
	</tr>
	<tr>
		<td>N° Serie</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numSerie"/></td>
		<td></td>
		<td></td>
		<td>Ruedas</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numRueda"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Pasajeros</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numPasajeros"/></td>
		<td></td>
		<td></td>
		<td>Asientos</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="asiento"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Peso Seco</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="pesoSeco"/></td>
		<td></td>
		<td></td>
		<td>Peso Bruto</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="pesoBruto"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Longitud</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="longitud"/></td>
		<td></td>
		<td></td>
		<td>Altura</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="altura"/></td>
		<td width="20"></td>
		<td>Ancho</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="ancho"/></td>
	</tr>
	<tr>
		<td>Carga Util</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="cargaUtil"/></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
</logic:equal>
<logic:notEqual name="detalle" property="tipoRegistro" value="V">
<table>
	<tr>
		<td>Clase</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="claseBien"/></td>
		<td width="20"></td>
		<td>Oficina</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="oficinaRegistral"/></td>
	</tr>
	<tr>
		<td>Matricula</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numMatricula"/></td>
		<td width="20"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<logic:notEqual name="detalle" property="tipoRegistro" value="A">
	<tr>
		<td>Nombre</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="nombreBien"/></td>
		<td width="20"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	</logic:notEqual>
	<logic:equal name="detalle" property="tipoRegistro" value="A">
	<tr>
		<td>Modelo</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="modeloBien"/></td>
		<td width="20"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	</logic:equal>
	<tr>
		<td>Fecha de Inscripción</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="fechaInscripcion"/></td>
		<td width="20"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Partida Registral</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numPartida"/></td>
		<td width="20"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
	<logic:present name="detalle" property="numSerie">
		<td>Serie</td>
		<td width="20"></td>
		<td><bean:write name="detalle" property="numSerie"/></td>
		<td width="20"></td>
	</logic:present>
	<logic:notPresent name="detalle" property="numSerie">
		<td></td>
		<td width="20"></td>
		<td></td>
		<td width="20"></td>
    </logic:notPresent>
    <logic:present name="detalle" property="tipoBien">
		<td>Tipo</td>                   
		<td width="20"></td>
		<td><bean:write name="detalle" property="tipoBien"/></td>
	</logic:present>
	</tr>
</table>
</logic:notEqual>
<logic:equal name="Certificado" property="tpo_certificado" value="M">
	<br>
	<table cellspacing="0">
		<tr>
			<td width="610"><hr size="3" color="BLACK"></td>
		</tr>
		<tr>
			<td align="center" width="630" bordercolor="1"><font size="2"><b>AFECTACIONES</b></font></td>
		</tr>
		<tr>
			<td width="610"><hr size="3" color="BLACK"></td>
		</tr>
	</table>
</logic:equal>
</logic:present>
<table>
	<tr>
		<td colspan="3" align="left"><div align="justify"><br>
				<% Certificado objcert= (Certificado)request.getAttribute("Certificado"); %>
				<% StringBuffer constancia = objcert.getConstancia2();
				   if (constancia.length()==0){ %>
						<script type="text/javascript">
			            	document.write(window.opener.document.frm1.Constancia2.value);
		            	</script>
	              <%}else{
	                
	                String q = constancia.toString().trim().replace(" ","&nbsp;");
	                %>
		          <%=q%>
	              <%}%>
			</div>
		</td>
	</tr>
</table>
<br>
<table width=600>
	<tr>
		<td>Se expide el presente Certificado de <logic:equal name="Certificado" property="tpo_certificado" value="M">Certificado de Gravamen</logic:equal><logic:equal name="Certificado" property="tpo_certificado" value="D">Certificado compendioso de Dominio</logic:equal> a solicitud del interesado, para los fines que estime conveniente.
			hora <bean:write name="Certificado" property="hora" scope="request" />:<bean:write name="Certificado" property="minutos" scope="request" />.  
			<bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/>, <bean:write name="Certificado" property="dia" scope="request" /> de <bean:write name="Certificado" property="mes"
				scope="request" /> de <bean:write name="Certificado"
				property="anno" scope="request" /></td>
	</tr>
</table>
<br>
<br>
<table class=tablasinestilo>
  <tr>
  	<td width="50%" align="left">
  	<div id="HOJA2"> 
  	<a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	</div></td>
	<td width="50%" align="right">
	<div id="HOJA3">	
	<a href="javascript:window.close();" onmouseover="javascript:mensaje_status('Cerrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div></td>
  </tr>
</table>
</logic:present>
</body>
</html>
