<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<html>
<head>
	 <title>Formulario Solicitud Inscripci&oacute;n - Participantes - Personas Naturales</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head>
<script>
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

function mostrarDatos(){
	if (document.frm1.cboTipoPersona.value=="PN") {
		areaParticipanteNatural.style.visibility="visible";
		areaParticipanteNatural.style.display="";
		areaParticipanteComun.style.visibility="visible";
		areaParticipanteComun.style.display="";
		areaParticipanteJuridico.style.visibility="hidden";
		areaParticipanteJuridico.style.display="none";
	}
	else if (document.frm1.cboTipoPersona.value=="PJ") {
		areaParticipanteJuridico.style.visibility="visible";
		areaParticipanteJuridico.style.display="";
		areaParticipanteComun.style.visibility="visible";
		areaParticipanteComun.style.display="";
		areaParticipanteNatural.style.visibility="hidden";
		areaParticipanteNatural.style.display="none";
	}
	else {
		areaParticipanteJuridico.style.visibility="hidden";
		areaParticipanteJuridico.style.display="none";
		areaParticipanteComun.style.visibility="hidden";
		areaParticipanteComun.style.display="none";
		areaParticipanteNatural.style.visibility="hidden";
		areaParticipanteNatural.style.display="none";	
	}
}

Navegador();

function CambioOficinaRegistral(opcionSeleccionada) {
	var registroPublico;
	var o = parseInt(opcionSeleccionada);
	switch (o) {
		case  1 : registroPublico = "05"; break;
		case  2 : registroPublico = "11"; break;
		case  3 : registroPublico = "12"; break;
		case  4 : registroPublico = "09"; break;
		case  5 : registroPublico = "08"; break;
		case  6 : registroPublico = "13"; break;
		case  7 : registroPublico = "04"; break;
		case  8 : registroPublico = "02"; break;
		case  9 : registroPublico = "01"; break;
		case 10 : registroPublico = "06"; break;
		case 11 : registroPublico = "10"; break;
		case 12 : registroPublico = "03"; break;
		case 13 : registroPublico = "07"; break;
		case 35 : registroPublico = "01"; break;	//Chequear esto hp
		default: alert("No tiene registro Publico");
	}
	return registroPublico;
}


function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}

function Validar() 
{
	 
	 document.frm1.cboTipoPersona.disabled = false;

	 if ( (areaParticipanteJuridico.style.visibility=="hidden") && (areaParticipanteJuridico.style.display=="none") &&
		  (areaParticipanteComun.style.visibility=="hidden") && (areaParticipanteComun.style.display=="none") &&
		  (areaParticipanteNatural.style.visibility=="hidden") && (areaParticipanteNatural.style.display=="none") ) {
		
		alert("Seleccione un Tipo de Persona");
		document.frm1.cboTipoPersona.focus();
		return;
		
	 }



	 if (document.frm1.cboTipoPersona.value == "PN") {

			if (document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].value == "00")
			{
				alert("Por favor seleccione un Tipo de Documento");
				document.frm1.cboSolTipDoc.focus();
				return;
			}	
			if (document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].value == "05")
			{
				alert("El Tipo de Documento seleccionado no está disponible para Persona Natural");
				doCambiaCombo(document.frm1.cboSolTipDoc,"09");
				document.frm1.txtSolNumDoc.focus();
				return;
			}
			if (esVacio(document.frm1.txtSolNumDoc.value)|| !esEntero(document.frm1.txtSolNumDoc.value) || !esEnteroMayor(document.frm1.txtSolNumDoc.value,1))
			{	
				alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
				document.frm1.txtSolNumDoc.focus();
				return;
			}
			if(tieneCaracterNoValido(document.frm1.txtSolNumDoc.value))
			{
				alert("Por favor, no ingrese caracteres no válidos");
				document.frm1.txtSolNumDoc.focus();
				return;
			}
			if (document.frm1.txtSolNumDoc.value.length<8)
			{	
				alert("El Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
				document.frm1.txtSolNumDoc.focus();
				return;
			}
			if (esVacio(document.frm1.txtSolApPa.value) && esVacio(document.frm1.txtSolNom.value))
			{
				alert("Debe ingresar los datos del Participante");
				document.frm1.txtSolApPa.focus();
				return
			}
			if (document.frm1.txtSolApPa.value.length<2)
			{
				alert("El Apellido Paterno del Participante debe tener al menos 2 caracteres");					
				document.frm1.txtSolApPa.focus();
				return;
			}
			if (document.frm1.txtSolApMa.value.length==1)
			{
				alert("El Apellido Materno del Presentante debe tener al menos 2 caracteres");
				document.frm1.txtSolApMa.focus();
				return;
			}
			if (document.frm1.txtSolNom.value.length<2)
			{
				alert("Los Nombres del Presentante deben tener al menos 2 caracteres");
				document.frm1.txtSolNom.focus();
				return;
			}
			if(tieneCaracterNoValido(document.frm1.txtEnvDire.value))
			{
				alert("Por favor, no ingrese caracteres no válidos");
				document.frm1.txtEnvDire.focus();
				return;
			}
	
	} else {

			if (esVacio(document.frm1.txtSolRazonSocial.value))
			{
				alert("Debe ingresar la Razón Social del Participante");
				document.frm1.txtSolRazonSocial.focus();
				return
			}	
			if (esVacio(document.frm1.txtSolRUC.value) || !esEntero(document.frm1.txtSolRUC.value) || !esLongitudMayor(document.frm1.txtSolRUC.value,8) || !esEnteroMayor(document.frm1.txtSolRUC.value,1))
			{	
				alert("Por favor ingrese correctamente el Numero de Documento");
				document.frm1.txtSolRUC.focus();
				return;
			}	
			if(tieneCaracterNoValido(document.frm1.txtSolRUC.value)){
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.txtSolRUC.focus();
				return;
			}			
	
	}

	frm1.hidDep.value = document.frm1.cboEnvDpto.options[document.frm1.cboEnvDpto.selectedIndex].text
	frm1.hidProv.value = document.frm1.cboEnvProv.options[document.frm1.cboEnvProv.selectedIndex].text
	frm1.hidDist.value = document.frm1.cboEnvDist.options[document.frm1.cboEnvDist.selectedIndex].text
	frm1.hidTipDoc.value = document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].text
	frm1.hidEstadoCivil.value = document.frm1.cboSolEstadoCivil.options[document.frm1.cboSolEstadoCivil.selectedIndex].text
	frm1.hidNacionalidad.value = document.frm1.cboSolNacionalidad.options[document.frm1.cboSolNacionalidad.selectedIndex].text
	frm1.hidTipoVia.value = document.frm1.cboEnvTipoVia.options[document.frm1.cboEnvTipoVia.selectedIndex].text
	
	document.frm1.action="/iri/ReservaNombre.do?state=agregarParticipante";		
	document.frm1.submit();
	
}

function llenaComboProvincia(){
	document.frm1.cboTipoPersona.disabled = false;

	document.frm1.action="/iri/ReservaNombre.do?state=obtenerProvincia";
	document.frm1.submit();
}

function llenaComboDistrito(){
	document.frm1.cboTipoPersona.disabled = false;
	
	document.frm1.action="/iri/ReservaNombre.do?state=obtenerDistrito";
	document.frm1.submit();
}

function Cancelar(){
	document.frm1.action="/iri/ReservaNombre.do?state=regresarADatosReserva";
	document.frm1.submit();
}
</script>
<body>
<br>
	<table cellspacing=0 class=titulo>
  		<tr>
			<td>
				<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Reserva de Preferencia Registral</FONT>
			</td>
  		</tr>
	</table>
	<br>
	<form name="frm1" method="POST" class="formulario">
	<input type="hidden" name="hidDep" value="">
	<input type="hidden" name="hidProv" value="">
	<input type="hidden" name="hidDist" value="">
	<input type="hidden" name="hidTipDoc" value="">
	<input type="hidden" name="hidTipoVia" value="">
	<input type="hidden" name="hidEstadoCivil" value="">
	<input type="hidden" name="hidNacionalidad" value="">
	<% if (request.getAttribute("indice")!=null) { %>
	<input type="hidden" name="hidIndiceMod" value="<%=request.getAttribute("indice")%>">
	<% } else  {%>
	<input type="hidden" name="hidIndiceMod" value="">
  	<% } %>
  	<table class=tablasinestilo>
    	<tr>
      		<th colspan="2"><strong>DATOS DE PARTICIPANTE Y/O CONTRATANTE</strong></th>
    	</tr>
   		<tr>
      		<td width="30%"><strong>TIPO DE PERSONA</strong></td>
      		<td width="70%">
      			<select size="1" name="cboTipoPersona" style="width:230" onchange="javascript:mostrarDatos();">
          			<option value="00">&lt;&lt; Seleccione el Tipo de Persona &gt;&gt;</option>
          			<option value="PN">PERSONA NATURAL</option>
          			<% if (!session.getAttribute("labelTipoSociedad").equals("E.I.R.L")) { %>
          			<option value="PJ">PERSONA JUR&Iacute;DICA</option>
          			<% } %>
      			</select>
      		</td>
	    </tr>
    	<tr>
	    	<td colspan=2>
	    	<div id="areaParticipanteNatural" style="visibility:hidden;display:none">  
		    <table width="100%">
	   	 	<tr>
	      		<td width="30%"><strong>TIPO DOC.IDENTIDAD <font color="900000">*</font></strong></td>
	      		<td width="70%">
					<select name=cboSolTipDoc style="width:230">
						<option value="00">&nbsp;</option>
						<logic:iterate name="arrDocu" id="item1" scope="session">
						  <option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
						</logic:iterate>
					</select> 
	      		</td>
	    	</tr>
			<tr>
	      		<td width="30%"><strong>NRO. DOC. IDENTIDAD <font color="900000">*</font></strong></td>
	      		<td width="70%"><input type="text" name="txtSolNumDoc" size="20" maxlength="130" style="width:230"></td>		
			</tr>
	    	<tr>
	      		<td width="30%"><strong>ESTADO CIVIL</strong></td>
	      		<td width="70%">
	      			<select name="cboSolEstadoCivil" style="width:230">
						<option value="  ">&nbsp;</option>
						<logic:iterate name="arrEstadoCivil" id="item1" scope="session">
						  <option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
						</logic:iterate>
	      			</select>
	      		</td>
	    	</tr>
	    	<tr>
	      		<td width="30%"><strong>APELLIDO PATERNO <font color="900000">*</font></strong></td>
	      		<td width="70%"><input type="text" onBlur="sololet(this)" name="txtSolApPa" size="20" maxlength="130" style="width:230"></td>
	    	</tr>
	        <tr>
	      		<td width="30%"><strong>APELLIDO MATERNO</strong></td>
	      		<td width="70%"><input type="text" onBlur="sololet(this)" name="txtSolApMa" size="20" maxlength="130" style="width:230"></td>
	        </tr>
	    	<tr>
	      		<td width="30%"><strong>NOMBRES <font color="900000">*</font></strong></td>
	      		<td width="70%"><input type="text" onBlur="sololet(this)" name="txtSolNom" size="20" maxlength="130" style="width:230"></td>
	      	</tr>
	      	<tr>
	      		<td width="30%"><strong>CORREO ELECTRONICO</strong></td>
	      		<td width="70%"><input type="text" name="txtEnvCorreoElectronico" size="20" maxlength="130" style="width:230" onBlur="sololet(this)"></td>
	      	</tr>
	       </table>
	    </div>
	    <div id="areaParticipanteJuridico" style="visibility:hidden;display:none">  
		    <table width="100%">
		   	<tr>
	      		<td width="30%"><strong>RAZON SOCIAL <font color="900000">*</font></strong></td>
	      		<td width="70%"><input type="text" name="txtSolRazonSocial" onBlur="sololet(this)" size="20" maxlength="130" style="width:230"></td>
	    	</tr>
	    	<tr>
	      		<td width="30%"><strong>SIGLAS</strong></td>
	      		<td width="70%"><input type="text" name="txtSolSiglas" onBlur="sololet(this)" size="20" maxlength="130" style="width:230"></td>
	      	</tr>
			<tr>
	      		<td width="30%"><strong>RUC <font color="900000">*</font></strong></td>
	      		<td width="70%"><input type="text" name="txtSolRUC" size="20" maxlength="130" style="width:230"></td>		
			</tr>
		    <tr>
		        <td width="30%"><strong>OFICINA REGISTRAL</strong></td>
		        <td width="70%">
		      		<select size="1" name="cboOficinas" onChange="frm1.hidOfic.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text" style="width:230">
		        		<option value="10|07">Andahuaylas</option>
		        		<option value="06|02">Apurimac</option>
		        		<option value="03|01">Arequipa</option>
				        <option value="10|05">Ayacucho</option>
				        <option value="01|06">Barranca</option>
				        <option value="11|04">Bagua</option>
				        <option value="01|02">Callao</option>
				        <option value="11|02">Cajamarca</option>
				        <option value="03|02">Caman&aacute;</option>
				        <option value="01|05">Ca&ntilde;ete</option>
				        <option value="04|02">Casma</option>
				        <option value="03|03">Castilla</option>
				        <option value="11|05">Chachapoyas</option>
				        <option value="08|02">Chep&eacute;n</option>
				        <option value="11|01">Chiclayo</option>
				        <option value="04|03">Chimbote</option>
				        <option value="10|02">Chincha</option>
				        <option value="11|06">Chota</option>
				        <!--option value="13|01">Coronel portillo</option-->
				        <option value="06|01">Cusco</option>
				        <option value="01|04">Huacho</option>
				        <option value="08|03">Huamachuco</option>
				        <option value="10|08">Huancavelica</option>
				        <option value="02|01">Huancayo</option>
				        <option value="10|06">Huanta</option>
				        <option value="02|02">Hu&aacute;nuco</option>
				        <option value="01|03">Huaral</option>
				        <option value="04|01">Huaraz</option>
				        <option value="10|01">Ica</option>
				        <option value="07|02">Ilo</option>
				        <option value="09|01">Iquitos</option>
				        <option value="03|04">Islay</option>
				        <option value="11|03">Ja&eacute;n</option>
				        <option value="12|03">Juanju&iacute;</option>
				        <option value="07|03">Juliaca</option>
				        <option value="02|06">La Merced</option>        
				        <option value="01|01" selected>Lima</option>
				        <option value="06|03">Madre de dios</option>
				        <option value="07|04">Moquegua</option>
				        <option value="12|01">Moyobamba</option>
				        <option value="10|04">Nazca</option>
				        <option value="08|04">Otuzco</option>
				        <option value="02|04">Pasco</option>
				        <option value="10|03">Pisco</option>
				        <option value="05|01">Piura</option>
				        <option value="13|01">Pucallpa</option>
				        <option value="07|05">Puno</option>
				        <option value="06|04">Quillabamba</option>
				        <option value="08|05">San pedro de lloc</option>
				        <option value="02|05">Satipo</option>
				        <!--option value="02|06">Selva Central</option-->
				        <option value="06|05">Sicuani</option>
				        <option value="05|02">Sullana</option>
				        <option value="07|01">Tacna</option>
				        <option value="12|02">Tarapoto</option>
				        <option value="02|07">Tarma</option>
				        <option value="02|08">Tingo Mar&iacute;a</option>
				        <option value="08|01">Trujillo</option>
				        <option value="05|03">Tumbes</option>
				        <option value="09|02">Yurimaguas</option>
				    </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="hidden" name="hidOfic" value="Lima">
				    
				</td>
		    </tr>      	
	
			<tr>
	      		<td width="30%"><strong>NUMERO PARTIDA</strong></td>
	      		<td width="70%"><input type="text" name="txtSolNumPartida" size="20" maxlength="130" style="width:230"></td>		
			</tr>
	    </table>
	    </div>
	    <div id="areaParticipanteComun" style="visibility:hidden;display:none">
	    <table width="100%">
	      	<tr>
	            <td width="30%"><strong>DEPARTAMENTO</strong></td>
	            <td width="70%">
					<SELECT name=cboEnvDpto onchange=llenaComboProvincia(); width="187" style="width:230" >
						<option value="  |  ">&nbsp;</option>
						<logic:present name="arr3">
							<logic:iterate name="arr3" id="item3" scope="request">
								<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
							</logic:iterate>
						</logic:present>
					</SELECT>
	            </td>
	        </tr>
	      	<tr>
	            <td width="30%"><strong>PROVINCIA</strong></td>
	            <td width="70%">
					<select name="cboEnvProv" onchange=llenaComboDistrito(); width="187" style="width:230">
						<option value="  |  |  ">&nbsp;</option>
						<logic:present name="arr_hijo1">
							<logic:iterate name="arr_hijo1" id="item3" scope="request">
								<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
							</logic:iterate>
						</logic:present>						        
					</select>
	            </td>
	        </tr>
	      	<tr>
	            <td width="30%"><strong>DISTRITO<strong></td>
	            <td width="70%">
	            	<%-- <input type="text" name="txtEnvDist" onBlur="sololet(this)" maxlength="90" style="width:180"> --%>
					<select name="cboEnvDist" width="187" style="width:230">
						<option value="  |  |  |  ">&nbsp;</option>
						<logic:present name="arr_hijo2">
							<logic:iterate name="arr_hijo2" id="item3" scope="request">
								<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
							</logic:iterate>
						</logic:present>						        
				    </select>
	            </td>
	        </tr>
		    <tr>
		  		<td width="30%"><strong>DIRECCION</strong></td>
				<td width="70%">
					<select name="cboEnvTipoVia" style="width:80">
						<option value="  ">&nbsp;</option>
						<logic:present name="arrTipoVia">
							<logic:iterate name="arrTipoVia" id="item3" scope="session">
								<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
							</logic:iterate>
						</logic:present>
					</select>						        
					<input name="txtEnvDire" type="text" onBlur="sololet(this)" maxlength="90" style="width:147">
				</td>
	        </tr>
	        <tr>
			    <td width="30%"><strong>COD POSTAL</strong></td>
				<td width="70%"><input name="txtEnvCodPost" type="text" onBlur="sololet(this)" maxlength="90" style="width:230"></td>
			</tr>
	    	<tr>
	      		<td width="30%"><strong>NACIONALIDAD</strong></td>
	      		<td width="70%">
	        		<select name=cboSolNacionalidad style="width:230">
						<option value="  ">&nbsp;</option>
						<logic:present name="arrNacionalidad">
							<logic:iterate name="arrNacionalidad" id="item1" scope="session">
							  <option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="atributo1"/></option>
							</logic:iterate>
						</logic:present>
					</select> 
	        	</td>
	        </tr>
	      	<tr>
	      		<td colspan="2">&nbsp;</td>
	      	</tr>
			</table>
		</div>	
    	<tr>
    		<td colspan="2" width="100%" align="center">
    			<A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Grabar Participante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<IMG src="images/btn_grabar.gif" border="0">
				</A>
    			<A href="javascript:Cancelar();" onmouseover="javascript:mensaje_status('Cancelar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<IMG src="images/btn_cancelar.gif" border="0">
				</A>
	  		</td>  		
    	</tr>
  	</table>
	<br>
	</form>
	<table>
	<tr>
		<td><strong><font color="900000">* Los datos son obligatorios</font></strong></td>
	</tr>
</table>
<br>
<script LANGUAGE="JavaScript">
	//llenaComboHijo();
	window.top.frames[0].location.reload();
</script>
<table>
	<tr>
	    <td>
	       <a href="javascript:mostrarArticulos()"><font color="900000"><strong>La aceptación de reserva esta supeditada al cumplimiento del Reglamento de Sociedades<BR>
            Resolución Nº 200-2001-SUNARP/SN</strong></font></a>
	    </td>
	</tr>
</table>

<% if ( (request.getAttribute("codTipoPersona")!=null) && (request.getAttribute("codTipoPersona").equals("PN")) ) { %>
<script LANGUAGE="JavaScript">

	doCambiaCombo(document.frm1.cboTipoPersona, "<%=request.getAttribute("codTipoPersona")%>");

	doCambiaCombo(document.frm1.cboSolTipDoc, "<%=request.getAttribute("codTipoDoc")%>");

	<% if (request.getAttribute("numDoc")!=null) %>
		document.frm1.txtSolNumDoc.value = "<%=request.getAttribute("numDoc")%>";

	doCambiaCombo(document.frm1.cboSolEstadoCivil, "<%=request.getAttribute("codEstadoCivil")%>");

	<% if (request.getAttribute("apePat")!=null) %>
		document.frm1.txtSolApPa.value = "<%=request.getAttribute("apePat")%>";
	
	<% if (request.getAttribute("apeMat")!=null) %>
		document.frm1.txtSolApMa.value = "<%=request.getAttribute("apeMat")%>";
	
	<% if (request.getAttribute("nom")!=null) %>
		document.frm1.txtSolNom.value = "<%=request.getAttribute("nom")%>";	
	
	doCambiaCombo(document.frm1.cboEnvDpto, "01|<%=request.getAttribute("codDepartamento")%>");
	
	doCambiaCombo(document.frm1.cboEnvProv, "01|<%=request.getAttribute("codDepartamento")%>|<%=request.getAttribute("codProvincia")%>");
	
	doCambiaCombo(document.frm1.cboEnvDist, "01|<%=request.getAttribute("codDepartamento")%>|<%=request.getAttribute("codProvincia")%>|<%=request.getAttribute("codDistrito")%>");

	doCambiaCombo(document.frm1.cboEnvTipoVia, "<%=request.getAttribute("tipoVia")%>");	
	
	<% if (request.getAttribute("direccion")!=null) %>
		document.frm1.txtEnvDire.value = "<%=request.getAttribute("direccion")%>"; 
	
	<% if (request.getAttribute("codPostal")!=null) %>
		document.frm1.txtEnvCodPost.value = "<%=request.getAttribute("codPostal")%>";	

	doCambiaCombo(document.frm1.cboSolNacionalidad, "<%=request.getAttribute("codNacionalidad")%>");
	
	<% if (request.getAttribute("correoElec")!=null) %>
		document.frm1.txtEnvCorreoElectronico.value = "<%=request.getAttribute("correoElec")%>";

	mostrarDatos();
</script>
<%  } else if ( (request.getAttribute("codTipoPersona")!=null) && (request.getAttribute("codTipoPersona").equals("PJ")) ) {  %>
<script LANGUAGE="JavaScript">
	doCambiaCombo(document.frm1.cboTipoPersona, "<%=request.getAttribute("codTipoPersona")%>");

	<% if (request.getAttribute("numeroPartida")!=null) %>
		document.frm1.txtSolNumPartida.value = "<%=request.getAttribute("numeroPartida")%>";	

	<% if (request.getAttribute("razonSocial")!=null) %>
		document.frm1.txtSolRazonSocial.value = "<%=request.getAttribute("razonSocial")%>";

	<% if (request.getAttribute("siglas")!=null) %>
		document.frm1.txtSolSiglas.value = "<%=request.getAttribute("siglas")%>";

	<% if (request.getAttribute("ruc")!=null) %>
		document.frm1.txtSolRUC.value = "<%=request.getAttribute("ruc")%>";

	doCambiaCombo(document.frm1.cboOficinas, "<%=request.getAttribute("codZonaOficina")%>");

	doCambiaCombo(document.frm1.cboEnvDpto, "01|<%=request.getAttribute("codDepartamento")%>");
	
	doCambiaCombo(document.frm1.cboEnvProv, "01|<%=request.getAttribute("codDepartamento")%>|<%=request.getAttribute("codProvincia")%>");
	
	doCambiaCombo(document.frm1.cboEnvDist, "01|<%=request.getAttribute("codDepartamento")%>|<%=request.getAttribute("codProvincia")%>|<%=request.getAttribute("codDistrito")%>");

	doCambiaCombo(document.frm1.cboEnvTipoVia, "<%=request.getAttribute("tipoVia")%>");	
	
	<% if (request.getAttribute("direccion")!=null) %>
		document.frm1.txtEnvDire.value = "<%=request.getAttribute("direccion")%>"; 
	
	<% if (request.getAttribute("codPostal")!=null) %>
		document.frm1.txtEnvCodPost.value = "<%=request.getAttribute("codPostal")%>";	

	doCambiaCombo(document.frm1.cboSolNacionalidad, "<%=request.getAttribute("codNacionalidad")%>");
	
	mostrarDatos();	
</script>
<%  } %>
<% if ( (request.getAttribute("indice")!=null) && (!request.getAttribute("indice").equals("")) ) { %>								
<script LANGUAGE="JavaScript">
	document.frm1.cboTipoPersona.disabled = true;
</script>
<%  } %>
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
</body>
</html>
