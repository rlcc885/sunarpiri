<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>

<html>
<head>
	<title>Formulario Reserva de Preferencia Registral - Datos Reserva</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head> 
<script language="javascript">
function mostrarArticulos(){
	if (articulos.style.visibility=="hidden") {
		articulos.style.visibility="visible";
		articulos.style.display="";
	}
	else {
		articulos.style.visibility="hidden";
		articulos.style.display="none";
	}
}

function Nuevo(){
	document.frm1.hidTipoVia.value = document.frm1.cboEnvTipoVia.options[document.frm1.cboEnvTipoVia.selectedIndex].text
		
	document.frm1.action="/iri/ReservaNombre.do?state=obtenerNuevoParticipante";
	document.frm1.submit();
}

function Borrar(){
	if (confirm("Esta seguro de Borrar el o los registro(s) seleccionados?")) {
		document.frm1.action="/iri/ReservaNombre.do?state=borrarParticipante";
		document.frm1.submit();
	}
	return;
}

function Regresar(){
	document.frm1.action="/iri/SolicitudInscripcion.do?state=regresarASolicitudInscripcion";
	document.frm1.submit();
}

function Validar() 
{

	if (esVacio(document.frm1.txtSolRazonSocial.value))
	{
		alert("Debe ingresar la Denominación y/o Razón Social");
		document.frm1.txtSolRazonSocial.focus();
		return
	}
	<% if ( session.getAttribute("flagTipoSociedad")=="S" ) { %>
		if (document.frm1.cboTipoSociedad.options[document.frm1.cboTipoSociedad.selectedIndex].value == "0")
		{
			alert("Debe seleccionar el Tipo de Sociedad Anónima");
			document.frm1.cboTipoSociedad.focus();
			return;
		}	
	<% } %>
	if (document.frm1.cboEnvTipoVia.options[document.frm1.cboEnvTipoVia.selectedIndex].value == "  ")
	{
		alert("Debe seleccionar el Tipo de Vía");
		document.frm1.cboEnvTipoVia.focus();
		return;
	}	
	if (document.frm1.txtSolDireccion.value.length<2)
	{
		alert("Debe ingresar la Dirección");					
		document.frm1.txtSolDireccion.focus();
		return;
	}
	
	document.frm1.hidTipoVia.value = document.frm1.cboEnvTipoVia.options[document.frm1.cboEnvTipoVia.selectedIndex].text
	
	document.frm1.action="/iri/ReservaNombre.do?state=obtenerResumenAntesPago";		
	document.frm1.submit();

}

function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}


function modificarParticipante(indice, tipoPersona)
{

	document.frm1.hidIndiceMod.value = indice;
	document.frm1.hidTipoPersona.value = tipoPersona;	
	document.frm1.action="/iri/ReservaNombre.do?state=obtenerParticipante";		
	document.frm1.submit();
}

function seleccionaTodos(){
	for (var i=0;i < document.frm1.elements.length;i++)
	{
		var elemento = document.frm1.elements[i];
		if (elemento.type == "checkbox")
		{
			if (document.frm1.todos.checked == true) {
				elemento.checked = true;
			}
			else {
				elemento.checked = false;
			}
		}
	}
}

</script>
<body>
	<br>
	<table cellspacing=0 class=titulo>
		<tr>
			<td><FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Reserva de Preferencia Registral
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 1 DE 3
			</FONT>
			</td>
  		</tr> 
	</table>
	<br>
	<form name="frm1" method="post" class="formulario">
	<input type="hidden" name="hidIndiceMod" value="">
	<input type="hidden" name="hidTipoPersona" value="">
	<input type="hidden" name="hidTipoVia" value=""> 
	<table class="tablasinestilo">
    	<tr>
        	<th colspan="5">DATOS DE LA PERSONA JUR&Iacute;DICA A RESERVAR</th>
    	</tr>
    	<tr>
    		<td width="5">&nbsp;</td>
    		<td width="150"><b>DENOMINACI&Oacute;N Y/O RAZ&Oacute;N SOCIAL <font color="900000">*</font></b></td>
      		<td><input type="text" name="txtSolRazonSocial" maxlength="100" style="width:350" onBlur="sololet(this)">&nbsp;<b>(*)</b></td>
        	<td width="65" colspan="2"></td>
    	</tr>
  		<%-- <tr> 
    		<td colspan="5">&nbsp;</td>
  		</tr> --%>
    	<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>SIGLAS</b></td>
        	<td><input type="text" name="txtSolSiglas" maxlength="10" style="width:210" onBlur="sololet(this)"></td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<%-- <tr> 
    		<td colspan="5">&nbsp;</td>
  		</tr> --%>
  		<tr>
        	<td>&nbsp;</td>
      		<td width="150"><b>TIPO DE SOCIEDAD <font color="900000">*</font></b></td>
      		<td>
      		<% 
      			if (session.getAttribute("flagTipoSociedad")=="S") { %>
				<select size="1" name="cboTipoSociedad" style="width:210">
					<option value="0">&nbsp;</option>
			   		<option value="A">SOCIEDAD AN&Oacute;NIMA ABIERTA</option>
			        <option value="C">SOCIEDAD AN&Oacute;NIMA CERRADA</option>
			    </select>			
			<% } else { %>
				<%=session.getAttribute("labelTipoSociedad")%>
			<% }%>
			</td>
  			<td colspan="2">&nbsp;</td>
  		</tr>
  		<%-- <tr> 
    		<td colspan="5">&nbsp;</td>
  		</tr> --%>
  		<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>DIRECCI&Oacute;N <font color="900000">*</font></b></td>
        	<td>
	        	<select name="cboEnvTipoVia" style="width:80">
					<option value="  ">&nbsp;</option>
						<logic:present name="arrTipoVia">
							<logic:iterate name="arrTipoVia" id="item3" scope="session">
								<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
							</logic:iterate>
						</logic:present>						        
			    </select><input type="text" name="txtSolDireccion" maxlength="100" style="width:270" onBlur="sololet(this)">
			</td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<%-- <tr> 
    		<td colspan="5">&nbsp;</td>
  		</tr> --%>
  		<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>FECHA DE SOLICITUD</b></td>
        	<td><%=FechaUtil.getCurrentDate()%></td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
	<tr>
		<th colspan=5><strong>PARTICIPANTES Y/O CONTRATANTES</strong></th>
	</tr>
	<tr>
		<td colspan="5">
			<table border="0" class="grilla" cellspacing="0">
				<tr>
		      		<td colspan="6"></td>
		    	</tr>
		    	<tr>
		      		<th width="2%" align="center" height="12"><div align="center"><input type="checkbox" name="todos" onclick="javascript:seleccionaTodos()"></div></th>
		      		<th width="2%" align="center"><div align="center">NRO.</div></th>
		      		<th align="center" height="12"><div align="center">TIPO PERSONA</div></th>
		      		<th align="center" height="12"><div align="center">TIPO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NUMERO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NOMBRE/RAZ&Oacute;N SOCIAL</div></th>
		      		<%--<th width="11%" align="center" height="12"><div align="center">TIPO PARTICIPANTE</div></th>--%>
		    	</tr>
		    	<% PersonaNatural personaNatural = null;
		    	   PersonaJuridica personaJuridica = null;
		    	   SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
		    	   java.util.ArrayList listaPersonasNatural = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
		    	   java.util.ArrayList listaPersonasJuridica = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
		    	   int size1=0;
		    	   int size2=0;
		    	   if (listaPersonasNatural!=null) {
			    	   size1= listaPersonasNatural.size();
		    	 	   if (size1>0) {
			    	 	   personaNatural = (PersonaNatural)listaPersonasNatural.get(0);	    	   		
			    %>
				<tr>
		      		<td align="center">&nbsp;</td>
		      		<td align="center">1</td>
		      		<td>Natural</td>
		      		<td><%=personaNatural.getDescripcionTipoDocumento()%></td>
		      		<td><%=personaNatural.getNumeroDocumento()%></td>
		      		<td><%=(personaNatural.getApellidoPaterno()+ " " +personaNatural.getApellidoMaterno()+ " " + personaNatural.getNombre())%></td>		      		
		    	</tr>
		    	<%	 
			    	   	   for (int i=1; i<size1; i++) {
			    	   	   	   personaNatural = (PersonaNatural)listaPersonasNatural.get(i);	    	   		
		    	%>
				<tr>
		      		<td align="center"><input type="checkbox" name="indicesListaPN" value="<%=i%>"></td>
		      		<td align="center"><%=i+1%></td>
		      		<td>Natural</td>
		      		<td><%=personaNatural.getDescripcionTipoDocumento()%></td>
		      		<td><A href="javascript:modificarParticipante(<%=i%>,'PN')"><%=personaNatural.getNumeroDocumento()%></A></td>
		      		<td><%=(personaNatural.getApellidoPaterno()+ " " +personaNatural.getApellidoMaterno()+ " " + personaNatural.getNombre())%></td>		      		
		    	</tr>
		    	<% 		   }
		    	        }
		    		}

		    	   if (listaPersonasJuridica!=null) {
			    	   size2= listaPersonasJuridica.size();
		    	 	   if (size2>0) {
			    	 	  for (int i=0; i<size2; i++) {
			    	   	   	   personaJuridica = (PersonaJuridica)listaPersonasJuridica.get(i);	    	   		
		    	%>
				<tr>
		      		<td align="center"><input type="checkbox" name="indicesListaPJ" value="<%=i%>"></td>
		      		<td align="center"><%=i+1+(size1)%></td>
		      		<td>Jur&iacute;dica</td>
		      		<td><%=personaJuridica.getDescripcionTipoDocumento()%></td>
		      		<td><A href="javascript:modificarParticipante(<%=i%>,'PJ')"><%=personaJuridica.getNumeroDocumento()%></A></td>
		      		<td><%=personaJuridica.getRazonSocial()%></td>
		    	</tr>
		    	<% 		   }
		    	        }
		    		}
				%>
			 	<tr>
		      		<td colspan=6></td>
		    	</tr>
			</table>
		</td>
	</tr>			
	<tr>
		<td colspan="5">
			<table cellspacing="0" align="center">
				<tr align="center">
					<td>
						<table>
							<tr>
								<td>
									<A href="javascript:Nuevo();" onmouseover="javascript:mensaje_status('Nuevo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
										<IMG src="images/btn_nuevo.gif" border="0">
									</A>
								</td>
								<td>
									<A href="javascript:Borrar();" onmouseover="javascript:mensaje_status('Borrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
										<IMG src="images/btn_borrar.gif" border="0">										
									</A>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>  	
		</td>
	</tr>
    <tr>
        <td colspan="5">&nbsp;</td> 
    </tr>	  		
    	<tr>
    		<td align="right" colspan="5">
    		    <A href="javascript:Regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    		    	<IMG src="images/btn_regresa.gif" border="0">
    			</A>
    			<A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    				<IMG src="images/btn_continuar.gif" border="0">
    			</A>
    		</td>
    	</tr>
  	</table>
	</form>
	<br>
    <table>
    	<tr>
     		<td width="596"><font size="1"><b>(*)&nbsp;Nota:&nbsp;La&nbsp;Raz&oacute;n&nbsp;Social&nbsp;no&nbsp;debe&nbsp;de&nbsp;incluir&nbsp;el&nbsp;tipo&nbsp;de&nbsp;Sociedad&nbsp;(ej.&nbsp;ABC&nbsp;en&nbsp;lugar&nbsp;de&nbsp;ABC&nbsp;S.A.)</b></font></td>
        </tr>
	</table>
<br>
<table>
	<tr>
		<td><strong><font color="900000">* Los datos son obligatorios</font></strong></td>
	</tr>
</table>
<% 
   SolicitudInscripcion solicitudInscripcion2 = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
   PersonaJuridica personaJuridica2 = solicitudInscripcion2.getPersonaJuridica();
   
   if (personaJuridica2!=null) {
%>
<script LANGUAGE="JavaScript">
	<% if (personaJuridica2.getRazonSocial()!=null) { %>
		document.frm1.txtSolRazonSocial.value       = "<%=personaJuridica2.getRazonSocial()%>";
	<% } %>		
	<% if (personaJuridica2.getSiglas()!=null) { %>
		document.frm1.txtSolSiglas.value            = "<%=personaJuridica2.getSiglas()%>";
	<% } %>
	<% if (session.getAttribute("flagTipoSociedad")=="S")  { %>
		doCambiaCombo(document.frm1.cboTipoSociedad, "<%=personaJuridica2.getCodigoTipoSociedadAnonima()%>");
	<% } %>
	doCambiaCombo(document.frm1.cboEnvTipoVia, "<%=personaJuridica2.getCodigoTipoVia()%>");
	<% if (personaJuridica2.getDireccion()!=null) { %>
		document.frm1.txtSolDireccion.value            = "<%=personaJuridica2.getDireccion()%>";
	<% } %>
</script>
<% } %>
<br>
<table>
	<tr>
	    <td>
	       <a href="javascript:mostrarArticulos()"><font color="900000"><strong>La aceptación de reserva esta supeditada al cumplimiento del Reglamento de Sociedades<BR>
            Resolución Nº 200-2001-SUNARP/SN</strong></font></a>
	    </td>
	</tr>
</table>
<div id="articulos" style="visibility:hidden;display:none">
<table>
   	<tr>
       <td width="596" align="justify">
			Artículo 15.- Denominación y razón social No es inscribible la sociedad que adopte una denominación completa o abreviada o una razón social igual a la de otra preexistente en el índice. Tampoco es inscribible la sociedad que adopte una denominación abreviada que no esté compuesta por palabras, primeras letras o sílabas de la denominación completa. No es exigible la inclusión de siglas de la forma societaria en la denominación abreviada, salvo mandato legal en contrario.<BR><BR>
			Artículo 16.- Igualdad de denominación o de razón social Se entiende que existe igualdad cuando hay total coincidencia entre una denominación o una razón social con otra preexistente en el Índice, cualquiera sea la forma societaria adoptada. También existe igualdad, en las variaciones de matices de escasa significación tales como el uso de las mismas palabras con la adición o supresión de artículos, espacios, preposiciones, conjunciones, acentos, guiones o signos de puntuación; el uso de las mismas palabras en diferente orden, así como del singular y plural.<BR><BR>
			Artículo 18.- Reserva de preferencia registral La reserva de preferencia registral salvaguarda una denominación completa y, en caso de ser solicitada, su denominación abreviada, o una razón social, durante el proceso de constitución de una sociedad o de modificación del pacto social.<BR><BR>
			Artículo 19.- Personas legitimadas para solicitar la reserva La solicitud de Reserva puede ser presentada por uno o varios socios, el abogado o el Notario interviniente en la constitución de una sociedad o en la modificación del pacto social, o por la persona autorizada por la propia sociedad, si ésta estuviera constituida.
	   </td>
	</tr>
</table>
</div>		
</html>

