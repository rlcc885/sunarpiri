<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ResourceBundle"%>
<% 
	ArrayList list = (ArrayList)request.getAttribute("arrBusqueda");
	ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.extranet.publicidad.properties.Publicidad");
%>
<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script> 
<TITLE>
	   <logic:present name="Certificado" scope="request">	
          <logic:equal name="Certificado" property="tpo_certificado" value="N">Certificado Negativo</logic:equal> 	
		  <logic:equal name="Certificado" property="tpo_certificado" value="P">Certificado Positivo</logic:equal> 
	   </logic:present> 	  
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
        	<TD>&nbsp;</TD>
            <TD width="601"><IMG src="<%=request.getContextPath()%>/images/orlclogo.gif"></TD>
			<td width="256">
			<table border="0">
				<tr>
					<td width="242">Expediente N°</td>
					<td width="242"><bean:write name="Certificado" property="solicitud_id" scope="request"/></td>
				</tr>
				<tr>
					<td width="242">Derechos Pagados:</td>
					<td width="242">S/. <bean:write name="Certificado" property="total" scope="request"/></td>
				</tr>
			</table>
			</td>
			<TD width="45">
            </TD>
        </TR>
        <TR>
        	<TD>&nbsp;</TD>
            <TD colspan="2">&nbsp;</TD><TD width="45">&nbsp;</TD>
        </TR>
        <tr>
        	<TD>&nbsp;</TD>
            <TD colspan="2" width="575"><div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><font
				size="+1">REGISTRO DE BIENES MUEBLES</font></b></div></TD><TD width="45">&nbsp;</TD>
        </tr>  
    	<tr>
        	<TD>&nbsp;</TD>
            <TD colspan="2" width="575"><div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font
				size="+1"><b>REGISTRO <logic:equal name="Certificado" property="certificado_busq_id" value="33">VEHICULAR</logic:equal><logic:equal name="Certificado" property="certificado_busq_id" value="34">DE EMBARCACIONES PESQUERAS</logic:equal><logic:equal name="Certificado" property="certificado_busq_id" value="35">DE AERONAVES</logic:equal><logic:equal name="Certificado" property="certificado_busq_id" value="36">DE BUQUES</logic:equal></b></font></div></TD><TD width="45">&nbsp;</TD>
        </tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2" width="575"><div align="center">
			<table border="1" bordercolor="#000000" cellspacing="0"><tr><td align="center"><font style="font-size: 15px" >CERTIFICADO DE BUSQUEDA</font></td></tr></table></div>
			</td>
			<td width="45"><br><br></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td><br><br></td>
			<td colspan="2"><div align="left"><font size="1"><b>EL
			ABOGADO CERTIFICADOR QUE SUSCRIBE, CERTIFICA LO SIGUIENTE</b></font></div></td>
			<td width="45"><br></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2" width="550"><div align="justify">Que realizada la búsqueda <logic:equal name="tipoBusqueda" value="D">directa</logic:equal><logic:equal name="tipoBusqueda" value="I">de índices</logic:equal> de partidas en el libro <logic:equal name="Certificado" property="certificado_busq_id" value="33">Vehicular</logic:equal><logic:equal name="Certificado" property="certificado_busq_id" value="34">Embarcaciones Pesqueras</logic:equal><logic:equal name="Certificado" property="certificado_busq_id" value="35">Aeronaves</logic:equal> con el criterio solicitado :<br>
			<bean:write name="Certificado" property="crit_busq" scope="request"/> , se ha encontrado la información que a continuación se detalla :</div></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="justify" colspan="2">
			<table cellspacing="0" width="570">
				<logic:equal name="flagMetodo" value="1">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Area Registral</th>
					    <th width="8%">Registro de</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="areaRegistralDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="libroDescripcion" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="2">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Area Registral</th>
					    <th width="8%">Registro de</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="areaRegistralDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="libroDescripcion" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="3">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Area Registral</th>
					    <th width="8%">Registro de</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="areaRegistralDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="libroDescripcion" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="4">
					<tr align="center" class=grilla>
						<th width="10%">Registro P&uacute;blico</th>
						<th width="10%">Oficina Registral</th>
						<th width="15%">Partida</th>
						<th width="5%">Placa</th>
						<th width="10%">Estado de Vehiculo</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="numeroPlaca" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="baja" filter="false"></bean:write></td>	
						</tr>
					</logic:iterate>
				  </logic:equal>	
				  <logic:equal name="flagMetodo" value="41">
					<tr align="center" class=grilla>
						<th width="69">Registro P&uacute;blico</th>
						<th width="75">Oficina Registral</th>
						<th width="58">Partida</th>
						<th width="52">Placa</th>
						<th width="87">Estado de Vehiculo</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="numeroPlaca" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="baja" filter="false"></bean:write></td>	
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="5">
					<tr align="center" class=grilla>
						<th width="69">Registro P&uacute;blico</th>
						<th width="75">Oficina Registral</th>
						<th width="58">Partida</th>
						<th width="52">Placa</th>
						<th width="87">Propietario</th>
						<th>Estado de Vehiculo</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="numeroPlaca" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="participanteDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="baja" filter="false"></bean:write></td>	
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="6">
					<tr align="center" class=grilla>
						<th width="69">Registro P&uacute;blico</th>
						<th width="75">Oficina Registral</th>
						<th width="58">Partida</th>
						<th width="52">Placa</th>
						<th width="87">Propietario</th>
						<th>Estado de Vehiculo</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="numeroPlaca" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="participanteDescripcion" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="baja" filter="false"></bean:write></td>	
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="7">
					<tr align="center" class=grilla>
						<th width="69">Registro P&uacute;blico</th>
						<th width="75">Oficina Registral</th>
						<th width="58">Partida</th>
						<th width="52">Placa</th>
						<th width="87">N&uacute;mero de Motor</th>
						<th>N&uacute;mero de Serie</th>
						<th>Marca</th>
						<th>Modelo</th>
						<th>Estado de Vehiculo</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
					<tr align="center" class=grilla2>
						<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
						<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
						<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
						<td width="52"><bean:write name="partida" property="numeroPlaca" filter="false"></bean:write></td>
						<td width="87"><bean:write name="partida" property="numeroMotor" filter="false"></bean:write></td>
						<td><bean:write name="partida" property="numeroSerie" filter="false"></bean:write></td>
						<td><bean:write name="partida" property="marca" filter="false"></bean:write></td>	
						<td><bean:write name="partida" property="modelo" filter="false"></bean:write></td>
						<td><bean:write name="partida" property="baja" filter="false"></bean:write></td>
					</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="8">
					<tr align="center" class=grilla>
						<th width="69">Registro P&uacute;blico</th>
						<th width="75">Oficina Registral</th>
						<th width="58">Partida</th>
						<th width="52">Placa</th>
						<th width="87">N&uacute;mero de Motor</th>
						<th>N&uacute;mero de Serie</th>
						<th>Marca</th>
						<th>Modelo</th>
						<th>Estado de Vehiculo</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
					<tr align="center" class=grilla2>
						<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
						<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
						<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
						<td width="52"><bean:write name="partida" property="numeroPlaca" filter="false"></bean:write></td>
						<td width="87"><bean:write name="partida" property="numeroMotor" filter="false"></bean:write></td>
						<td><bean:write name="partida" property="numeroSerie" filter="false"></bean:write></td>
						<td><bean:write name="partida" property="marca" filter="false"></bean:write></td>	
						<td><bean:write name="partida" property="modelo" filter="false"></bean:write></td>
						<td><bean:write name="partida" property="baja" filter="false"></bean:write></td>
					</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="9">
					<tr align="center" class=grilla>
						<th width="69"><font style="font-size: 6pt">Zona Registral</font></th>
					    <th width="75"><font style="font-size: 6pt">Oficina Registral</font></th>
					    <th width="58"><font style="font-size: 6pt">Partida</font></th>
					    <th width="52"><font style="font-size: 6pt">N&uacute;mero de Ficha</font></th>
					    <th width="87"><font style="font-size: 6pt">Tomo</font></th>
					    <th><font style="font-size: 6pt">Folio</font></th>
					    <th><font style="font-size: 6pt">Area Registral</font></th>
					    <th><font style="font-size: 6pt">Registro de</font></th>
					    <th><font style="font-size: 6pt">Participante</font></th>
						<th><font style="font-size: 6pt">Documento identidad</font></th>
						<th><font style="font-size: 6pt">N&uacute;mero documento</font></th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><font style="font-size: 6pt"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></font></td>
							<td width="75"><font style="font-size: 6pt"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></font></td>
							<td width="58"><font style="font-size: 6pt"><bean:write name="partida" property="numPartida" filter="false"></bean:write></font></td>
							<td width="52"><font style="font-size: 6pt"><bean:write name="partida" property="fichaId" filter="false"></bean:write></font></td>
							<td width="87"><font style="font-size: 6pt"><bean:write name="partida" property="tomoId" filter="false"></bean:write></font></td>	
							<td><font style="font-size: 6pt"><bean:write name="partida" property="fojaId" filter="false"></bean:write></font></td>	
							<td><font style="font-size: 6pt"><bean:write name="partida" property="areaRegistralDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="libroDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participanteDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participanteTipoDocumentoDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participanteNumeroDocumento" filter="false"></bean:write></font></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="10">
					<tr align="center" class=grilla>
						<th width="69"><font style="font-size: 6pt">Zona Registral</font></th>
					    <th width="75"><font style="font-size: 6pt">Oficina Registral</font></th>
					    <th width="58"><font style="font-size: 6pt">Partida</font></th>
					    <th width="52"><font style="font-size: 6pt">N&uacute;mero de Ficha</font></th>
					    <th width="87"><font style="font-size: 6pt">Tomo</font></th>
					    <th><font style="font-size: 6pt">Folio</font></th>
					    <th><font style="font-size: 6pt">Area Registral</font></th>
					    <th><font style="font-size: 6pt">Registro de</font></th>
					    <th><font style="font-size: 6pt">Participante</font></th>
					    <th><font style="font-size: 6pt">Participación</font></th>
						<th><font style="font-size: 6pt">Documento identidad</font></th>
						<th><font style="font-size: 6pt">N&uacute;mero documento</font></th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><font style="font-size: 6pt"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></font></td>
							<td width="75"><font style="font-size: 6pt"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></font></td>
							<td width="58"><font style="font-size: 6pt"><bean:write name="partida" property="numPartida" filter="false"></bean:write></font></td>
							<td width="52"><font style="font-size: 6pt"><bean:write name="partida" property="fichaId" filter="false"></bean:write></font></td>
							<td width="87"><font style="font-size: 6pt"><bean:write name="partida" property="tomoId" filter="false"></bean:write></font></td>	
							<td><font style="font-size: 6pt"><bean:write name="partida" property="fojaId" filter="false"></bean:write></font></td>	
							<td><font style="font-size: 6pt"><bean:write name="partida" property="areaRegistralDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="libroDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participanteDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participacionDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participanteTipoDocumentoDescripcion" filter="false"></bean:write></font></td>
							<td><font style="font-size: 6pt"><bean:write name="partida" property="participanteNumeroDocumento" filter="false"></bean:write></font></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="11">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Matrícula</th>
					    <th width="8%">Nombre Embarcación</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="embarcacionMatricula" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="embarcacionNombre" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="12">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Matrícula</th>
					    <th width="8%">Tipo titular</th>
					    <th width="8%">Titular</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="aeronaveMatricula" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="aeronaveTipoTitular" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="aeronaveTitular" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>
				  <logic:equal name="flagMetodo" value="13">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Matrícula</th>
					    <th width="8%">Tipo titular</th>
					    <th width="8%">Titular</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="aeronaveMatricula" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="aeronaveTipoTitular" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="aeronaveTitular" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>	
				  <logic:equal name="flagMetodo" value="14">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Matrícula</th>
					    <th width="8%">Tipo titular</th>
					    <th width="8%">Titular</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class=grilla2>
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="aeronaveMatricula" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="aeronaveTipoTitular" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="aeronaveTitular" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>	
				  <logic:equal name="flagMetodo" value="15">
					<tr align="center" class=grilla>
						<th width="22%">Zona Registral</th>
					    <th width="24%">Oficina Registral</th>
					    <th width="18%">Partida</th>
					    <th width="17%">N&uacute;mero de Ficha</th>
					    <th width="28%">Tomo</th>
					    <th width="4%">Folio</th>
					    <th width="13%">Matrícula</th>
					    <th width="8%">Nombre Buque</th>
					</tr>
					<logic:iterate name="arrBusqueda" id="partida">
						<tr align="center" class="grilla2">
							<td width="69"><bean:write name="partida" property="regPubDescripcion" filter="false"></bean:write></td>
							<td width="75"><bean:write name="partida" property="oficRegDescripcion" filter="false"></bean:write></td>
							<td width="58"><bean:write name="partida" property="numPartida" filter="false"></bean:write></td>
							<td width="52"><bean:write name="partida" property="fichaId" filter="false"></bean:write></td>
							<td width="87"><bean:write name="partida" property="tomoId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="fojaId" filter="false"></bean:write></td>	
							<td><bean:write name="partida" property="buqueMatricula" filter="false"></bean:write></td>
							<td><bean:write name="partida" property="buqueNombre" filter="false"></bean:write></td>
						</tr>
					</logic:iterate>
				  </logic:equal>	
			</table>
			</td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td><br><br></td>
			<td colspan="2" width="575"><div align="justify">En fe de lo cual se
			expide en la ciudad de <bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/>, siendo las <bean:write
				name="Certificado" property="hora" scope="request" /> horas con <bean:write name="Certificado" property="minutos" scope="request" /> minutos del
			d&iacute;a <bean:write name="Certificado" property="dia"
				scope="request" /> de <bean:write name="Certificado" property="mes"
				scope="request" /> de <bean:write name="Certificado"
				property="anno" scope="request" /></div></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			
		</tr>
		<tr>
			<td></td>
			<td colspan="2" width="575"><div align="justify"><bean:write name="Certificado" property="constancia" scope="request"/></div></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td><br><br><br><br></td>
			<td colspan="2"><div align="left">Base Legal<br>Art. 94° y 109° de la<br>Resolución N° 142 - 142-2006-SUNARP-SN(RIRMCVRJB)</div></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2" width="575"><div align="justify"><%=bundle.getString("msn.certificado.busqueda.textofijo1")%></div></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2" width="575"><div align="justify"><%=bundle.getString("msn.certificado.busqueda.textofijo2")%></div>
			</td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
		<tr>
			<td></td>
			<td align="center" colspan="2"></td>
			<td width="45"></td>
		</tr>
	</table></logic:present>
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
</BODY>
</HTML>


