
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>

<html>
<head><title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
<%
  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  String nombre = usuarioBean.getNombres() + " " + usuarioBean.getApePat() + " " + usuarioBean.getApeMat();
%>
Navegador();
var timeDelay = <logic:present name="refreshTime"><bean:write name="refreshTime"/></logic:present><logic:notPresent name="refreshTime">120000</logic:notPresent>;
var timeOut;
//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;

function BuscarSolicitud()
	{
		document.frm1.action="/iri/CargaLaboral.do?state=muestraSolicitud&pagina=1&tamano=1";
		document.frm1.submit();
	
	}	
	
/*function DetalleSolicitud(sol_id)
	{
		document.frm1.solicitud.value = sol_id;
		document.frm1.action="/iri/CargaLaboral.do?state=detalleSolicitud";
		document.frm1.submit();
	
	}
*/	

function DetalleSolicitud(sol_id, tipo)
	{		
		document.frm1.solicitud.value = sol_id;
		//document.frm1.registrador.value = Registrador;
		document.frm1.action="/iri/CargaLaboral.do?state=detalleSolicitud&registrador="+tipo;
		document.frm1.submit();	
	}

function Despachar()
	{
		//document.frm1.solicitud.value = sol_id;
		document.frm1.action="/iri/CargaLaboral.do?state=despachaSolicitud";
		document.frm1.submit();
	
	}
function Exportar(sol_id)
	{
		//hphp
		document.frm1.solicitud.value = sol_id;
		document.frm1.action="/iri/CargaLaboral.do?state=exportarSolicitud";
		document.frm1.submit();
	
	}	
function HabilitaComboFecha(){
		//alert("valor del checl:"+document.frm1.chkRangoFecha.checked+":AAAAA"); 
		valorcheck = document.frm1.chkRangoFecha.checked;
		if (valorcheck){		
			//alert("valor en el IF:"+document.frm1.chkRangoFecha.checked+":AAAAA"); 
			document.frm1.cbodiainicio.disabled = false;
			document.frm1.cbomesinicio.disabled = false;
			document.frm1.cboanoinicio.disabled = false;
			document.frm1.cbodiafin.disabled = false;
			document.frm1.cbomesfin.disabled = false;
			document.frm1.cboanofin.disabled = false;
		}else{
			//alert("valor en el ELSE:"+document.frm1.chkRangoFecha.checked+":AAAAA"); 
			document.frm1.cbodiainicio.disabled = true;
			document.frm1.cbomesinicio.disabled = true;
			document.frm1.cboanoinicio.disabled = true;
			document.frm1.cbodiafin.disabled = true;
			document.frm1.cbomesfin.disabled = true;
			document.frm1.cboanofin.disabled = true;		
		}	
}		

function HabilitaRadioFecha(){
			
	HabilitaEnvio();
}

function HabilitaEnvio()
{
	document.frm1.chkRangoFecha.disabled = false;
	document.frm1.radTipBusq[0].checked=false;
	document.frm1.radTipBusq[1].checked=true;
	
	if(document.frm1.cboEstadoSol.options[document.frm1.cboEstadoSol.selectedIndex].value == "04")
	{
		document.frm1.cboEnvio.disabled = false;
	}
	else
	{
		document.frm1.cboEnvio.disabled = true;
	}		
	
}

function DesHabilitaRadioFecha(){
		document.frm1.chkRangoFecha.disabled = true;
		document.frm1.chkRangoFecha.checked = false;
		HabilitaComboFecha()
		document.frm1.cboEnvio.disabled = true;
		document.frm1.radTipBusq[0].checked=true;
		document.frm1.radTipBusq[1].checked=false;
}	

function ShowReport(pagina){	
		document.frm1.method="POST";
		document.frm1.action="/iri/CargaLaboral.do?state=muestraSolicitud&pagina="+pagina;
		document.frm1.submit();
					
}

function refrescarCargaLaboral()
	{
	  if(document.frm1.chkRefrescaAuto.checked) {
	  	//alert(timeDelay);
	  	timeOut = setTimeout("BuscarSolicitud();",timeDelay);
	  } else {
	  	clearTimeout(timeOut);
	  }
	}

function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	HOJA4.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";
	HOJA4.style.visibility="visible";
}
</SCRIPT>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<form name="frm1" method="post">
<br>
<table class=titulo cellspacing=0>
  <tr> 
      <td><font color=black>REGISTRADOR&gt;&gt; </font>Carga Laboral&gt;&gt; <%=nombre%></td>
	</tr>
</table>
<br>
  

<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>1. CARGA LABORAL</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0>
    <tr> 
      <td width="8">&nbsp;</td>
      <td width="111">&nbsp;</td>
      <td width="278">&nbsp;</td>
      <td width="193">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="111"><input type="radio" name="radTipBusq" value="N" <% if (request.getAttribute("stipobusq")==null||request.getAttribute("stipobusq").equals("N")){%> checked<%}%> onclick="javascript:DesHabilitaRadioFecha();"> 
        <strong>Por N&uacute;mero</strong> </td>
      <td>
      <input type="text" name="txtnumSol" size="20" maxlength="18" style="width:133" onBlur="sololet(this)" value="" onclick="javascript:DesHabilitaRadioFecha()"> 
			
			<%--<logic:present name="fechabusqBean" scope="request">
			<logic:equal name="fechabusqBean" property="dia_inicio" value="01">
				<bean:write name="fechabusqBean" property="dia_inicio"/>
			</logic:equal>      	
      		</logic:present>--%>      	
      </td>
      <td width="193">
      	<a href="javascript:BuscarSolicitud();" onmouseover="javascript:mensaje_status('Buscar solicitud por Numero o Estado');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
      	<IMG src="images/btn_buscar.gif" border="0">
      	</a> 
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="193">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="111"> <input type="radio" name="radTipBusq" value="E"  <% if (request.getAttribute("stipobusq")!=null&& request.getAttribute("stipobusq").equals("E")){%> checked<%}%> onclick="javascript:HabilitaRadioFecha();"> <strong>Por 
        Estado</strong> </td>
      <td>
        <select name="cboEstadoSol" onchange="javascript:HabilitaEnvio();" onclick="javascript:HabilitaEnvio();">
          <option <% if (request.getAttribute("sEstadoSol")==null||request.getAttribute("sEstadoSol").equals("01")){%> selected<%}%>  value="01">PENDIENTES</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("02")){%> selected<%}%> value="02">POR VERIFICAR</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("03")){%> selected<%}%> value="03">POR EXPEDIR</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("04")){%> selected<%}%> value="04">POR DESPACHAR</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("05")){%> selected<%}%> value="05">DESPACHADAS</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("06")){%> selected<%}%> value="06">ANULADA</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("07")){%> selected<%}%> value="07">IMPROCEDENTE</option>
          <option <% if (request.getAttribute("sEstadoSol")!=null&&request.getAttribute("sEstadoSol").equals("08")){%> selected<%}%> value="08">TODAS</option>          
        </select>
        &nbsp; 
        <select name="cboEnvio" <%if (request.getAttribute("cboEnvio")==null) {%> disabled<%}%>>
          <option value="T" <% if (request.getAttribute("cboEnvio")==null||request.getAttribute("cboEnvio").equals("T")){%> selected<%}%>>TODAS</option>
          <option value="V" <% if (request.getAttribute("cboEnvio")!=null&&request.getAttribute("cboEnvio").equals("V")){%> selected<%}%>>VENTANILLA</option>
          <option value="D" <% if (request.getAttribute("cboEnvio")!=null&&request.getAttribute("cboEnvio").equals("D")){%> selected<%}%>>DOMICILIO</option>
        </select> 
      </td>
      <td width="193"><input type="checkbox" name="chkRefrescaAuto" value="checkbox" <% if (request.getAttribute("sRefrescaAuto")!=null){%> checked<%}%> onclick="javascript:refrescarCargaLaboral()">
        Refrescar Autom&aacute;ticamente </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td colspan="2"> <table width="100%" cellspacing=0>
          <tr> 
            <td width="49%"> <input name="chkRangoFecha" type="checkbox" value="checkbox" onclick="javascript:HabilitaComboFecha()" <% if (request.getAttribute("sRangoFecha")==null){ }else{%>checked<%}%> <% if (request.getAttribute("stipobusq")!=null&& request.getAttribute("stipobusq").equals("E")){}else{%> disabled<%}%>>
              Usar Rango de Fechas </td>
            <td width="49%">&nbsp;</td>
          </tr>
          <tr> 
            <td><b>Desde</b></td>
            <td><b>Hasta</b></td>
          </tr>          
          <tr> 
            <!--td height="29" nowrap> dia&nbsp; <select size="1" name="cbodiainicio" >
                <option value ="01" selected>01</option>
                <option value ="02" >02</option>
                <option value ="03" >03</option>
                <option value ="04" >04</option>
                <option value ="05" >05</option>
                <option value ="06" >06</option>
                <option value ="07" >07</option>
                <option value ="08" >08</option>
                <option value ="09" >09</option>
                <option value ="10" >10</option>
                <option value ="11" >11</option>
                <option value ="12" >12</option>
                <option value ="13" >13</option>
                <option value ="14" >14</option>
                <option value ="15" >15</option>
                <option value ="16" >16</option>
                <option value ="17" >17</option>
                <option value ="18" >18</option>
                <option value ="19" >19</option>
                <option value ="20" >20</option>
                <option value ="21" >21</option>
                <option value ="22" >22</option>
                <option value ="23" >23</option>
                <option value ="24" >24</option>
                <option value ="25" >25</option>
                <option value ="26" >26</option>
                <option value ="27" >27</option>
                <option value ="28" >28</option>
                <option value ="29" >29</option>
                <option value ="30" >30</option>
                <option value ="31" >31</option>
              </select-->
          <td height="29" nowrap> dia&nbsp; <select size="1" name="cbodiainicio" disabled="true">
                <% String valor ="";
                for (int cont=1; cont<32;cont++){                	                	 
                		if(cont<10){
                			valor = "0"+String.valueOf(cont);
                		}else{
                			valor =""+String.valueOf(cont);                		
                		}%>
                		<option value ="<%=valor%>" 
                		<logic:present name="fechabusqBean" scope="request">
							<logic:equal name="fechabusqBean" property="dia_inicio" value="<%=valor%>">
								selected
							</logic:equal>      	
      					</logic:present>
                		
                		><%=valor%></option>
                <% }%>
          </select>  
              mes&nbsp; <select size="1" name="cbomesinicio" disabled="true">
                <!--option value ="01" >01</option>
                <option value ="02" >02</option>
                <option value ="03" >03</option>
                <option value ="04" >04</option>
                <option value ="05" >05</option>
                <option value ="06" >06</option>
                <option value ="07" selected>07</option>
                <option value ="08" >08</option>
                <option value ="09" >09</option>
                <option value ="10" >10</option>
                <option value ="11" >11</option>
                <option value ="12" >12</option-->
                <% //String valor ="";
                for (int cont=1; cont<13;cont++){                	                	 
                		if(cont<10){
                			valor = "0"+String.valueOf(cont);
                		}else{
                			valor =""+String.valueOf(cont);                		
                		}%>
                		<option value ="<%=valor%>" 
                		<logic:present name="fechabusqBean" scope="request">
							<logic:equal name="fechabusqBean" property="mes_inicio" value="<%=valor%>">
								selected
							</logic:equal>      	
      					</logic:present>
                		
                		><%=valor%></option>
                <% }%>
              </select>
              a&ntilde;o 
              <select size="1" name="cboanoinicio" disabled="true">              
                <!--option value ="2002" >2002</option>
                <option value ="2003" selected>2003</option>
                <option value ="2004" >2004</option-->
                <!-- Inicio:rbahamonde:30/12/2008 -->
                <!-- se cambio el for para que su intervalo llegue hasta 2009 -->
                <% //String valor ="";
                for (int cont=2002; cont<2010;cont++){                	                	 
                		if(cont<10){
                			valor = "0"+String.valueOf(cont);
                		}else{
                			valor =""+String.valueOf(cont);                		
                		}%>
                		<option value ="<%=valor%>" 
                		<logic:present name="fechabusqBean" scope="request">
							<logic:equal name="fechabusqBean" property="anno_inicio" value="<%=valor%>">
								selected
							</logic:equal>      	
      					</logic:present>
                		
                		><%=valor%></option>
                <% }%>
                <!-- Fin:rbahamonde -->
              </select> </td>
            <td valign="middle" nowrap> dia&nbsp; <select size="1" name="cbodiafin" disabled="true">
                <!--option value ="01" >01</option>
                <option value ="02" >02</option>
                <option value ="03" selected>03</option>
                <option value ="04" >04</option>
                <option value ="05" >05</option>
                <option value ="06" >06</option>
                <option value ="07" >07</option>
                <option value ="08" >08</option>
                <option value ="09" >09</option>
                <option value ="10" >10</option>
                <option value ="11" >11</option>
                <option value ="12" >12</option>
                <option value ="13" >13</option>
                <option value ="14" >14</option>
                <option value ="15" >15</option>
                <option value ="16" >16</option>
                <option value ="17" >17</option>
                <option value ="18" >18</option>
                <option value ="19" >19</option>
                <option value ="20" >20</option>
                <option value ="21" >21</option>
                <option value ="22" >22</option>
                <option value ="23" >23</option>
                <option value ="24" >24</option>
                <option value ="25" >25</option>
                <option value ="26" >26</option>
                <option value ="27" >27</option>
                <option value ="28" >28</option>
                <option value ="29" >29</option>
                <option value ="30" >30</option>
                <option value ="31" >31</option-->
                <% //String valor ="";
                for (int cont=1; cont<32;cont++){                	                	 
                		if(cont<10){
                			valor = "0"+String.valueOf(cont);
                		}else{
                			valor =""+String.valueOf(cont);                		
                		}%>
                		<option value ="<%=valor%>" 
                		<logic:present name="fechabusqBean" scope="request">
							<logic:equal name="fechabusqBean" property="dia_final" value="<%=valor%>">
								selected
							</logic:equal>      	
      					</logic:present>                		
                		><%=valor%></option>
                <% }%>
                
              </select>
              mes 
              <select size="1" name="cbomesfin" disabled="true">
                <!--option value ="01" >01</option>
                <option value ="02" >02</option>
                <option value ="03" >03</option>
                <option value ="04" >04</option>
                <option value ="05" >05</option>
                <option value ="06" >06</option>
                <option value ="07" selected>07</option>
                <option value ="08" >08</option>
                <option value ="09" >09</option>
                <option value ="10" >10</option>
                <option value ="11" >11</option>
                <option value ="12" >12</option-->
                <% //String valor ="";
                for (int cont=1; cont<13;cont++){                	                	 
                		if(cont<10){
                			valor = "0"+String.valueOf(cont);
                		}else{
                			valor =""+String.valueOf(cont);                		
                		}%>
                		<option value ="<%=valor%>" 
                		<logic:present name="fechabusqBean" scope="request">
							<logic:equal name="fechabusqBean" property="mes_final" value="<%=valor%>">
								selected
							</logic:equal>      	
      					</logic:present>
                		
                		><%=valor%></option>
                <% }%>
                
              </select>
              a&ntilde;o 
              <select size="1" name="cboanofin" disabled="true">
                <!--option value ="2002" >2002</option>
                <option value ="2003" selected>2003</option>
                <option value ="2004" >2004</option-->
                <!-- Inicio:rbahamonde:30/12/2008 -->
                <!-- se cambio el for para que su intervalo llegue hasta 2009 -->
                <% //String valor ="";
                for (int cont=2002; cont<2010;cont++){                	                	 
                		if(cont<10){
                			valor = "0"+String.valueOf(cont);
                		}else{
                			valor =""+String.valueOf(cont);                		
                		}%>
                		<option value ="<%=valor%>" 
                		<logic:present name="fechabusqBean" scope="request">
							<logic:equal name="fechabusqBean" property="anno_final" value="<%=valor%>">
								selected
							</logic:equal>      	
      					</logic:present>
                		
                		><%=valor%></option>
                <% }%>
                <!-- Fin:rbahamonde -->
              </select> </td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="193">&nbsp;</td>
    </tr>
  </table>	
  
  <input type="hidden" name="solicitud" value="">
  <!--input type="hidden" name="registrador" value=""-->

<logic:notPresent name="mensaje">

	
  
<logic:present name="encontro">
	<logic:present name="tamano">
		<input type="hidden" name="tamano" value="<bean:write name="tamano"/>">
	</logic:present>
  
	<logic:present name="arrResultado" scope="request">
	<logic:notPresent name="cboEnvio" scope="request">
	<br>
	<logic:present name="numeroderegistros">
		Total de registros encontrados : <bean:write name="numeroderegistros"/><br>
    </logic:present>
	<br>
    <table class=grilla>
      <tr> 
        <th width="23">No.</th>
        <th width="133">  Asiento - Titulo</th>
        <th colspan="2">Objeto del Certificado</th>
        <th width="57">Ofic.Reg.</th>
        <th width="94">Estado</th>
        <th width="43">Ver Detalle</th>
        <th width="10"> Acci&oacute;n </th>
      </tr>
		<logic:iterate name="arrResultado" id="ResultBusq" scope="request">
		   <tr>		
			<td><CENTER><bean:write name="ResultBusq" property="solicitud_id"/> </CENTER></td>
			<td><bean:write name="ResultBusq" property="nombre_Cert"/></td>	
					
			<!-- inicio:jrosas 31-05-2007
					   SUNARP-REGMOBCOM: Setear la descripcion de Objeto de Certificado dependiendo del tipo de certificado  -->
					   				
			<!-- Para certificado Negativo/Positivo -->				
			<logic:equal name="ResultBusq" property="certificado_id" value="N">	
				<!-- Tipo: No es certificado de mobiliario de contratos -->		
				<logic:notEqual name="ResultBusq" property="tipoCertificado" value="18">			
					<logic:equal name="ResultBusq" property="tpo_persona" value="N">
						<td><CENTER>PN</CENTER></td>
						<td><bean:write name="ResultBusq" property="objeto_certPN"/></td>
					</logic:equal>
					<logic:equal name="ResultBusq" property="tpo_persona" value="J">
						<td><CENTER>PJ</CENTER></td>
						<td><bean:write name="ResultBusq" property="objeto_certPJ"/></td>
					</logic:equal>
				</logic:notEqual>	
				<!-- Tipo: certificado de mobiliario de contratos -->		
				<logic:equal name="ResultBusq" property="tipoCertificado" value="18">		
					<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
				</logic:equal>
			</logic:equal>
			
			<!-- Para certificado Copia Literal -->				
			<logic:equal name="ResultBusq" property="certificado_id" value="L">
				<td><bean:write name="ResultBusq" property="num_partida"/></td>
				<td><bean:write name="ResultBusq" property="area_reg_id"/></td>
			</logic:equal>
			
			<!-- Para certificado de Vigencia o de Gravamen -->		
			<logic:equal name="ResultBusq" property="certificado_id" value="R">	
				<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
			</logic:equal>
			
			<!-- Para certificado RJB: Gravamen, Dominial -->		
			<logic:equal name="ResultBusq" property="certificado_id" value="D">	
				<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
			</logic:equal>
			<logic:equal name="ResultBusq" property="certificado_id" value="G">	
				<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
			</logic:equal>
			
			<!-- Para certificado CREM: actos vigentes, historico, condicionado -->		
			<logic:equal name="ResultBusq" property="certificado_id" value="C">	
				<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
			</logic:equal>
			
			<!-- Inicio:mgarate 31-05-2007-->	
			<logic:equal name="ResultBusq" property="certificado_id" value="B">	
				<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
			</logic:equal>
			<!-- Fin:mgarate 31-05-2007-->
			
			<td><CENTER><bean:write name="ResultBusq" property="ofic_registral"/></CENTER></td>
			<td><CENTER><bean:write name="ResultBusq" property="estado_sol"/></CENTER></td>				
			<td>
				<a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>,'DET');" onmouseover="javascript:mensaje_status('Verficar la solicitud');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				<center><IMG src="images/lupa.gif" border="0"></center></a>
			</td>
			<td>
				<logic:equal name="ResultBusq" property="accion" value="<%= Constantes.REGIS_VERIFICADOR%>">						
					
					<a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>,'REG');" onmouseover="javascript:mensaje_status('Verficar la solicitud');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<IMG src="images/btn_verificar.gif" border="0">
					</a>
				</logic:equal>
				<logic:equal name="ResultBusq" property="accion" value="<%= Constantes.REGIS_EMISOR%>">			
					<!--td>
					<a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>,'DET');" onmouseover="javascript:mensaje_status('Verficar la solicitud');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<center><IMG src="images/lupa.gif" border="0"></center>
					</a></td-->
					<a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>, 'REG');" onmouseover="javascript:mensaje_status('Expedir la solicitud');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<IMG src="images/btn_expedir.gif" border="0">
					</a>
				</logic:equal>		
				<logic:notEqual name="ResultBusq" property="accion" value="<%= Constantes.REGIS_EMISOR%>">
					<logic:notEqual name="ResultBusq" property="accion" value="<%= Constantes.REGIS_VERIFICADOR%>">
					<a href="javascript:Exportar(<bean:write name="ResultBusq" property="solicitud_id"/>);" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_exportar.gif" width=83 align=absMiddle vspace=5 border=0></a>
					</logic:notEqual>		
				</logic:notEqual>		
			 </td>
		  </tr>  
		</logic:iterate>
	</table>
	<!--br>
	  Mostrando Partidas del X al Y<br>
	
	<br>
	<table class=tablasinestilo>
	  <tr>
	  	<td width="33%" align="left">
	  	<div id="HOJA2"> 
	  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
	  	</div></td>
		<td width="33%" align="certer">
	  	<div id="HOJA4"> 
	            <div align="center"><a href="../publicidad/certificada/exportado.txt" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_exportar.gif" width=83 align=absMiddle vspace=5 border=0></a> 
	            </div>
	          </div></td>
		<td width="33%" align="right">
		<div id="HOJA3">	
		<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
		</div></td>
	  </tr>
	</table-->
</logic:notPresent>

<logic:present name="cboEnvio" scope="request">

<br>
  Se encontraron <bean:write name="tam" scope="request"/> registros.<br>
<br>
<logic:notEqual name="tam" value="0" scope="request">
    
    <table class=grilla>
      <tr> 
        <th width="30">No.</th>
        <th width="150">Nombre/Raz&oacute;n Social</th>
        <th width="20">Medio de Env&iacute;o</th>
        <th width="200">Domiciliio</th>
        <th width="100">Estado</th>
        <th width="40">Ver Detalle</th>
        <th width="10"> Acci&oacute;n </th>
      </tr>
		<logic:iterate name="arrResultado" id="ResultBusq" scope="request">
		<tr>		
		<td><input name="solId" type="hidden" value="<bean:write name="ResultBusq" property="solicitud_id"/>"><bean:write name="ResultBusq" property="solicitud_id"/> &nbsp;</td>
		<td><bean:write name="ResultBusq" property="destiNombre"/>&nbsp;</td>
		<td><bean:write name="ResultBusq" property="destiEnvio"/>&nbsp;</td>
		<td><bean:write name="ResultBusq" property="destiDire"/> <bean:write name="ResultBusq" property="destiDpto"/>&nbsp;</td>
				
		<td>POR DESPACHAR</td>				
		<td>
		  <center>
		    <a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>,'DET');" onmouseover="javascript:mensaje_status('Ver detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
			  <IMG src="images/lupa.gif" border="0">
			</a>
		  </center>
		</td>
		<%-- hphp --%>
		<td>
		<a href="javascript:Exportar(<bean:write name="ResultBusq" property="solicitud_id"/>);" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_exportar.gif" width=83 align=absMiddle vspace=5 border=0></a>
		</td>
		</tr>  
		</logic:iterate>
		
</table>
<br>
<table class=tablasinestilo>
  <tr>
  	<td width="25%" align="left">
  	<div id="HOJA2"> 
  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	</div></td>
	<td width="50%" align="center">
  	  <div align="center" id="HOJA4">
  	    <%-- 
  	    <a href="javascript:Exportar();" onmouseover="javascript:mensaje_status('Exportar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_exportar.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	    --%>
  	    <a href="javascript:Despachar();" onmouseover="javascript:mensaje_status('Despachar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_despachar.gif" width=83 align=absMiddle vspace=5 border=0></a> 
      </div>
    </td>
	<td width="25%" align="right">
	<div id="HOJA3">	
	<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div></td>
  </tr>
</table>

</logic:notEqual>
</logic:present>

</logic:present>
</logic:present>

</logic:notPresent>
<!-- ************************ P A G I N A C I O N ******************************* -->

<logic:present name="encontro">
	<logic:present name="pagdetalle">
		<bean:write name="pagdetalle"/>
	</logic:present>
</logic:present>
<center>
<table  class="tablasinestilo">
<tr>
<td>
<logic:present name="previous">
	<a href="javascript:ShowReport(<bean:write name="previous"/>);" onmouseover="javascript:mensaje_status('Anteriores');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Anteriores</a>
</logic:present>
</td>
<td>
<logic:present name="next">
	<a href="javascript:ShowReport(<bean:write name="next"/>);" onmouseover="javascript:mensaje_status('Siguientes');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Siguientes</a>
</logic:present>
</td>
</tr>
</table>
</center>
<!-- ************************ P A G I N A C I O N ******************************* -->

</form>
<SCRIPT LANGUAGE="JavaScript">

<% if (request.getAttribute("sRangoFecha")!=null){%>HabilitaComboFecha();<%}%>

<% if (request.getAttribute("sRefrescaAuto")!=null){%>refrescarCargaLaboral()<%}%>

</SCRIPT>
	<logic:present name="mensaje">
		<bean:write name="mensaje"/>
	</logic:present>

</BODY>
</HTML>
