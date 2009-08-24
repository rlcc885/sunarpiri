
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>

<html>
<head><title>Consulta de Estado de Solicitudes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script>
<%
  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  long perfilusuarioid = usuarioBean.getPerfilId();
%>

<script language="javascript">
Navegador();
//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;

function chequeaRad(indice)
{
  <%if(perfilusuarioid!=Constantes.PERFIL_ADMIN_ORG_EXT && perfilusuarioid!=Constantes.PERFIL_INDIVIDUAL_EXTERNO && perfilusuarioid!=Constantes.PERFIL_AFILIADO_EXTERNO){%>
	document.frm1.radTipBusq[indice].checked=true;
  <%}%>
}

function BuscarSolicitud()
{	
	flag=0;
  <%if(perfilusuarioid!=Constantes.PERFIL_ADMIN_ORG_EXT && perfilusuarioid!=Constantes.PERFIL_INDIVIDUAL_EXTERNO && perfilusuarioid!=Constantes.PERFIL_AFILIADO_EXTERNO){%>
	if(document.frm1.radTipBusq[0].checked==true)
	{
  <%}%>
		if (esVacio(document.frm1.txtnumSol.value) || !esEntero(document.frm1.txtnumSol.value) || !esEnteroMayor(document.frm1.txtnumSol.value,1))
		{	
			alert("Por favor ingrese correctamente el Número de Solicitud");
			document.frm1.txtnumSol.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtnumSol.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtnumSol.focus();
			return;
		}
		flag=1;
  <%if(perfilusuarioid!=Constantes.PERFIL_ADMIN_ORG_EXT && perfilusuarioid!=Constantes.PERFIL_INDIVIDUAL_EXTERNO && perfilusuarioid!=Constantes.PERFIL_AFILIADO_EXTERNO){%>		
	}
  
	if(document.frm1.radTipBusq[1].checked==true)
	{
		
		if (esVacio(document.frm1.txtApePat.value) && esVacio(document.frm1.txtApeMat.value) && esVacio(document.frm1.txtNombre.value))
		{
			alert("Debe ingresar los datos del Solicitante");
			document.frm1.txtApePat.focus();
			return
		}
		if (document.frm1.txtApePat.value.length<2)
		{
			alert("El Apellido Paterno debe tener al menos 2 caracteres");					
			document.frm1.txtApePat.focus();
			return;
		}
		if (document.frm1.txtApeMat.value.length==1)
		{
			alert("El Apellido Materno debe tener al menos 2 caracteres");					
			document.frm1.txtApeMat.focus();
			return;
		}
		if (document.frm1.txtNombre.value.length<2)
		{
			alert("Los Nombres deben tener al menos 2 caracteres");					
			document.frm1.txtNombre.focus();
			return;
		}
		flag=1;
	}
	if(document.frm1.radTipBusq[2].checked==true)
	{
		if (esVacio(document.frm1.txtRazonSocial.value))
		{	
			alert("Ingrese la Razón Social");
			document.frm1.txtRazonSocial.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtRazonSocial.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtRazonSocial.focus();
			return;
		}
		if (document.frm1.txtRazonSocial.value.length<3)
		{	
			alert("La Razón Social debe tener al menos 3 caracteres");
			document.frm1.txtRazonSocial.focus();
			return;
		}
		
		flag=1;
	}
	if(document.frm1.radTipBusq[3].checked==true)
	{
		//alert("Por rango de fechas");
		flag=1;
	}
	if(flag==0)
	{
		alert("Elija alguno de los criterios de búsqueda.");
		return;
	}
  <%}%>
	document.frm1.action="/iri/BusquedaSolicitud.do?state=muestraEstadoSolicitud&pagina=1&tamano=1";
	document.frm1.submit();
	
}	
	
function DetalleSolicitud(sol_id, Registrador)
	{
		document.frm1.solicitud.value = sol_id;
		document.frm1.action="/iri/CargaLaboral.do?state=detalleSolicitud&registrador="+Registrador;
		document.frm1.submit();
	
	}
	
function ShowReport(pagina){	
		document.frm1.method="POST";
		document.frm1.action="/iri/BusquedaSolicitud.do?state=muestraEstadoSolicitud&pagina="+pagina;
		document.frm1.submit();
					
}

</SCRIPT>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<script language="JavaScript">
<!--
	var startTime = new Date();

	// -->
</script>
<form name="frm1" method="post">
<br>
<table class=titulo cellspacing=0>
  <tr> 
      <td><font color=black>Servicios&gt;&gt; </font>Estado de Solicitud</td>
	</tr>
</table>
<br>
  

<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>1. DATOS DE LA SOLICITUD</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0>
      
        <TR>
            <TD width="8">&nbsp;</TD>
            <TD width="153">&nbsp;</TD>
            <TD width="152">&nbsp;</TD>
            <TD width="135">&nbsp;</TD>
            <TD width="150">&nbsp;</TD>
            <TD width="114">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="153">
              <INPUT type="radio" name="radTipBusq" value="NU" <% if (request.getAttribute("stipobusq")==null|| request.getAttribute("stipobusq").equals("NU")){%> checked <%}%> >
              <STRONG>Por N&uacute;mero</STRONG> 
            </TD>
            <TD width="152">
              <INPUT type="text" name="txtnumSol" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value="" onclick="chequeaRad(0)"> 
            </TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD width="114"><A href="javascript:BuscarSolicitud();" onmouseover="javascript:mensaje_status('Buscar solicitud');return true;" onmouseout="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_buscar.gif" border="0"></A></TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>
     <%if(perfilusuarioid!=Constantes.PERFIL_ADMIN_ORG_EXT && perfilusuarioid!=Constantes.PERFIL_INDIVIDUAL_EXTERNO && perfilusuarioid!=Constantes.PERFIL_AFILIADO_EXTERNO){%>
        <TR>
            <TD>&nbsp;</TD>
            <TD colspan="2">
              <INPUT type="radio" name="radTipBusq" value="PN" <% if (request.getAttribute("stipobusq")!=null&& request.getAttribute("stipobusq").equals("PN")){%> checked <%}%>> 
              <STRONG>Por solicitante Persona Natural</STRONG> 
            </TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD width="114">&nbsp;</TD>
        </TR>        
	    <tr>
	    	<td>&nbsp;</td>
            <td width="153">Apellido Paterno</td>
            <td width="152">
              <input type ="text" name="txtApePat" style="width:130" maxlength="30" onblur="sololet(this)" value="<logic:present name="sApePat"><bean:write name="sApePat"/></logic:present>" onclick="chequeaRad(1)">
            </td>
            <td width="135">Apellido Materno</td>
            <td width="150">
              <input type ="text" name="txtApeMat" style="width:130" maxlength="30" onblur="sololet(this)" value="<logic:present name="sApeMat"><bean:write name="sApeMat"/></logic:present>" onclick="chequeaRad(1)">
            </td>
            <td width="114">&nbsp;</td>
        </tr>
        <tr>
        	 <td>&nbsp;</td>
            <td width="153">Nombres</td>
            <td width="152">
              <input type="text" name="txtNombre" style="width:130" maxlength="40" onblur="sololet(this)" value="<logic:present name="sNombre"><bean:write name="sNombre"/></logic:present>" onclick="chequeaRad(1)">
            </td>
            <td width="135">&nbsp;</td>
            <td width="150">&nbsp;</td>
            <td width="114"></td>
        </tr>
        
        <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD colspan="2">
              <INPUT type="radio" name="radTipBusq" value="PJ" <% if (request.getAttribute("stipobusq")!=null&& request.getAttribute("stipobusq").equals("PJ")){%> checked <%}%>> 
              <STRONG>Por solicitante Persona Juridica</STRONG> 
            </TD>
            <TD width="135"></TD>
            <TD width="150"></TD>
        </tr>
        
        <tr>
        	 <TD>&nbsp;</TD>
            <td width="153">Raz&oacute;n Social</td>
            <td width="152">
              <input type="text" name="txtRazonSocial" style="width:130" maxlength="100" onblur="solonumlet(this)" value="<logic:present name="sRazonSoc"><bean:write name="sRazonSoc"/></logic:present>" onclick="chequeaRad(2)">
            </td>
            <td width="135">&nbsp;</td>
            <td width="150">&nbsp;</td>
            <td width="114">&nbsp;</td>
        </tr>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="153">&nbsp;</TD>
            <TD width="152">&nbsp;</TD>
            <TD width="135">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD colspan="2">
              <INPUT type="radio" name="radTipBusq" value="RF" <% if (request.getAttribute("stipobusq")!=null&& request.getAttribute("stipobusq").equals("RF")){%> checked <%}%>> 
              <STRONG>Por Rango de Fecha de Presentación</STRONG> 
            </TD>
            <TD width="135"></TD>
            <TD width="150"></TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="153"><B>&nbsp;</B></TD>
            <TD width="152"><B>&nbsp;</B></TD>
            <TD width="135">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD colspan="2"><B>Desde</B></TD>
            <TD colspan="2"><B>Hasta</B></TD>
            <td width="114"></td>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD colspan="2">dia&nbsp; 
             <select size="1" name="cbodiainicio" onclick="chequeaRad(3)">
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
              mes&nbsp; 
              <select size="1" name="cbomesinicio" onclick="chequeaRad(3)">
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
              <select size="1" name="cboanoinicio" onclick="chequeaRad(3)">
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
              </select>
            </TD>
            <TD colspan="2">dia&nbsp; 
            <select size="1" name="cbodiafin" onclick="chequeaRad(3)">
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
              <select size="1" name="cbomesfin" onclick="chequeaRad(3)">

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
			  <select size="1" name="cboanofin" onclick="chequeaRad(3)">
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
              </select>
            </TD>
            <TD width="114">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="153">&nbsp;</TD>
            <TD colspan="2"></TD>
        </TR>
      <%}%>  

   
</table>	
  
  <input type="hidden" name="solicitud" value="">

<!-- Para la paginacion-->

<logic:present name="encontro">
	<logic:present name="tamano">
		<input type="hidden" name="tamano" value="<bean:write name="tamano"/>">
	</logic:present>
	<br>
	<logic:present name="numeroderegistros">
  		Total de registros encontrados : <bean:write name="numeroderegistros"/><br>
	</logic:present>
	<br>
	<!-- inicio: jrosas 31-08-07 -->
 	<logic:present name="arrResultado" scope="request">
	    <table class=grilla>
	      <tr> 
	        <th width="23">No.</th>
	        <th width="133">Tipo de Certificado</th>
	        <th colspan="2">Objeto del Certificado</th>
	        <th width="57">Solicitante</th>
	        <th width="57">Ofic.Reg.</th>
	        <th width="94">Estado</th>
	        <th width="43">Ver Detalle</th>
	        <!--th width="10"> Acci&oacute;n </th-->
	      </tr>			
			<logic:iterate name="arrResultado" id="ResultBusq" scope="request">
				<tr>		
				<td><bean:write name="ResultBusq" property="solicitud_id"/> </td>
				<td><bean:write name="ResultBusq" property="nombre_Cert"/></td>			
				<!-- inicio:jrosas 31-05-2007
					   SUNARP-REGMOBCOM: Setear la descripcion de Objeto de Certificado dependiendo del tipo de certificado  -->
				<!-- ****** Inicio:jascencio:25/06/07 -->		
				
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
				<!-- Para certificado de Gravamen -->		
				<logic:equal name="ResultBusq" property="certificado_id" value="G">	
					<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
				</logic:equal>
				<!-- Para certificado RJB: Gravamen, Dominial -->		
				<logic:equal name="ResultBusq" property="certificado_id" value="D">	
					<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
				</logic:equal>
				<!-- Para certificado de Vigencia o de Gravamen -->		
				<logic:equal name="ResultBusq" property="certificado_id" value="R">	
					<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
				</logic:equal>
				<!-- Inicio:mgarate:13/06/2007 -->
				<logic:equal name="ResultBusq" property="certificado_id" value="B">	
					<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
				</logic:equal>
				<!-- Fin:mgarate:13/06/2007 -->
				<!-- Para certificado CREM: actos vigentes, historico, condicionado -->		
				<logic:equal name="ResultBusq" property="certificado_id" value="C">	
					<td colspan="2"><bean:write name="ResultBusq" property="descripcionObjetoCertificado"/>&nbsp;</td>
				</logic:equal>							
				<td>
					<logic:equal name="ResultBusq" property="tpo_pers_solicitante" value="N">
						<bean:write name="ResultBusq" property="solicitante_PN"/>
					</logic:equal>
					<logic:equal name="ResultBusq" property="tpo_pers_solicitante" value="J">
						<bean:write name="ResultBusq" property="solicitante_PJ"/>
					</logic:equal>
				</td>
				<td><CENTER><bean:write name="ResultBusq" property="ofic_registral"/></CENTER></td>
				<%if(perfilusuarioid!=Constantes.PERFIL_ADMIN_ORG_EXT && perfilusuarioid!=Constantes.PERFIL_INDIVIDUAL_EXTERNO && perfilusuarioid!=Constantes.PERFIL_AFILIADO_EXTERNO){%>
				<td><CENTER><bean:write name="ResultBusq" property="estado_sol"/></CENTER></td>
				<%}else{%>
				<td><center>
					<logic:equal name="ResultBusq" property="flagPagoCrem" value="0">
						Liquidado
					</logic:equal>
					<logic:equal name="ResultBusq" property="flagPagoCrem" value="1">
						<bean:write name="ResultBusq" property="estado_ext_sol"/>
					</logic:equal>
				   </center>
				</td>
				<%}%>
				<td><center><a href="javascript:DetalleSolicitud(<bean:write name="ResultBusq" property="solicitud_id"/>,'DET');" onmouseover="javascript:mensaje_status('Verficar la solicitud');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				<IMG src="images/lupa.gif" border="0"></center></td>	
				</tr>  
			</logic:iterate>
		 </table>
	     <br>
		<!--table class=tablasinestilo>
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
  	</logic:present>
	<!-- fin: jrosas 31-08-07 -->
</logic:present>

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
<table>

<logic:present name="mensaje" scope="request">
	<tr>
		<td><bean:write name="mensaje"/></td>
	</tr>
</logic:present>

	<%--<logic:notPresent name="solicitud" scope="request">
	<tr>
		<td>Debe ingresar el número de la Solicitud.</td>
	</tr>	
	</logic:notPresent>--%>
</table>
</form>
</BODY>
</HTML>

