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
	 <title>Formulario Solicitud Inscripci&oacute;n - Datos B&oaacute;sicos</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head>

<script language="javascript">
<%
  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  long perfilusuarioid = usuarioBean.getPerfilId();
%>

Navegador();
//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;

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

	if (document.frm1.cboActo.value=='22000|009|00249') {
		frm1.hidArea.value  = "REGISTRO DE PERSONAS JURIDICAS";
		frm1.hidLibro.value = "SOCIEDADES ANONIMAS";
		frm1.hidActo.value  = "CONSTITUCION DE SOCIEDAD ANONIMA";
		//alert("Opción no implementada");
		//return;
		document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerDatosConstitucion";
	}
	else if (document.frm1.cboActo.value=='22000|015|00284') {
		frm1.hidArea.value  = "REGISTRO DE PERSONAS JURIDICAS";
		frm1.hidLibro.value = "EMPRESAS INDIVIDUALES DE RESPONSABILIDAD LIMITADA";
		frm1.hidActo.value  = "CONSTITUCION DE E.I.R.L";
		//alert("Opción no implementada");
		//return;
		document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerDatosConstitucion";	
	}
	else if (document.frm1.cboActo.value=='22000|010|00256') {
		frm1.hidArea.value  = "REGISTRO DE PERSONAS JURIDICAS";
		frm1.hidLibro.value = "SOCIEDADES COMERCIALES DE RESPONSABILIDAD LIMITADA";
		frm1.hidActo.value  = "CONSTITUCION DE SOCIEDAD COMERCIAL DE RESPONSABILIDAD LIMITADA";
		//alert("Opción no implementada");
		//return;
		document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerDatosConstitucion";	
	}	
	else if (document.frm1.cboActo.value=='24000|088|V0170') {
		frm1.hidArea.value  = "REGISTRO DE PROP. VEHICULAR";
		frm1.hidLibro.value = "REGISTRO DE PROPIEDAD VEHICULAR";
		frm1.hidActo.value  = "TRANSFERENCIA DE PROPIEDAD VEHICULAR";
		//alert("Opción no implementada");
		//return;
		document.frm1.action="/iri/TransferenciaVehicular.do?state=obtenerDatosTransferencia";	
	}
	else if (document.frm1.cboActo.value=='21000|001|00172') {
		frm1.hidArea.value  = "REGISTRO DE PROPIEDAD INMUEBLE";
		frm1.hidLibro.value = "PROPIEDAD INMUEBLE";
		frm1.hidActo.value  = "BLOQUEO";
		//alert("Opción no implementada");
		//return;		
		document.frm1.action="/iri/BloqueoInmueble.do?state=obtenerDatosBloqueo";	
	}
    else if (document.frm1.cboActo.value=='22000|009|00821') {
		frm1.hidArea.value  = "REGISTRO DE PERSONAS JURIDICAS";
		frm1.hidLibro.value = "SOCIEDADES ANONIMAS";
		frm1.hidActo.value  = "RESERVA NOMBRE SOCIEDAD ANONIMA";
		document.frm1.action="/iri/ReservaNombre.do?state=obtenerDatosReservaParticipantes";	
	}
	else if (document.frm1.cboActo.value=='22000|015|01132') {
		frm1.hidArea.value  = "REGISTRO DE PERSONAS JURIDICAS";
		frm1.hidLibro.value = "EMPRESAS INDIVIDUALES DE RESPONSABILIDAD LIMITADA";
		frm1.hidActo.value  = "RESERVA NOMBRE DE E.I.R.L";
		document.frm1.action="/iri/ReservaNombre.do?state=obtenerDatosReservaParticipantes";	
	}
	else if (document.frm1.cboActo.value=='22000|010|01121') {
		frm1.hidArea.value  = "REGISTRO DE PERSONAS JURIDICAS";
		frm1.hidLibro.value = "SOCIEDADES COMERCIALES DE RESPONSABILIDAD LIMITADA";
		frm1.hidActo.value  = "RESERVA NOMBRE DE SOCIEDAD COMERCIAL DE RESPONSABILIDAD LIMITADA";
		document.frm1.action="/iri/ReservaNombre.do?state=obtenerDatosReservaParticipantes";		
	}
    else
	{
		alert("Por favor seleccione el Tipo de Acto.");
		document.frm1.cboActo.focus();
		return;
	}

	if (document.frm1.hidValidar.value == "SI") {

		if (esVacio(document.frm1.txtSolApPa.value) && esVacio(document.frm1.txtSolNom.value))
		{
			alert("Debe ingresar los datos del Presentante");
			document.frm1.txtSolApPa.focus();
			return
		}
		if (document.frm1.txtSolApPa.value.length<2)
		{
			alert("El Apellido Paterno del Presentante debe tener al menos 2 caracteres");					
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
		if (esVacio(document.frm1.txtEnvCorreoElectronico.value))
		{	
			alert("Ingrese el Correo Electrónico del Presentante");
			document.frm1.txtEnvCorreoElectronico.focus();
			return;
		}
		if(tieneCaracterNoValido(document.frm1.txtEnvDire.value))
		{
			alert("Por favor, no ingrese caracteres no válidos");
			document.frm1.txtEnvDire.focus();
			return;
		}

	}
	else{
	
		if (document.frm1.cboPresentante.value=='00') {
			alert("Por favor seleccione el Presentante.");
			document.frm1.cboPresentante.focus();
			return;
		}
	}

	
	document.frm1.txtSolApPa.disabled           = false;
	document.frm1.txtSolApMa.disabled           = false;
	document.frm1.txtSolNom.disabled            = false;
	document.frm1.cboSolTipDoc.disabled         = false;;
	document.frm1.txtSolNumDoc.disabled         = false;
	document.frm1.cboEnvDpto.disabled         = false;
	document.frm1.cboEnvProv.disabled         = false;
	document.frm1.cboEnvDist.disabled         = false;
	document.frm1.cboEnvTipoVia.disabled         = false;
	document.frm1.txtEnvDire.disabled         = false;
	document.frm1.txtEnvCodPost.disabled         = false;
	document.frm1.txtEnvCorreoElectronico.disabled         = false;
	document.frm1.txtSolCodInstitucion.disabled         = false;
	document.frm1.txtSolNomInstitucion.disabled         = false;
				
	document.frm1.submit();
	
}

function obtieneDatos(){
	document.frm1.action="/iri/SolicitudInscripcion.do?state=obtenerPresentante";
	document.frm1.submit();
}

function llenaComboProvincia(){
	document.frm1.action="/iri/SolicitudInscripcion.do?state=obtenerProvincia";
	document.frm1.submit();
}

function llenaComboDistrito(){
	document.frm1.action="/iri/SolicitudInscripcion.do?state=obtenerDistrito";
	document.frm1.submit();
}

function mostrarInstitucion(){
		areaInstitucion.style.visibility="visible";
		areaInstitucion.style.display="";
}

function ocultarInstitucion(){
		areaInstitucion.style.visibility="hidden";
		areaInstitucion.style.display="none";
}

function mostrarPresentante(){
		areaPresentante.style.visibility="visible";
		areaPresentante.style.display="";
}

function ocultarPresentante(){
		areaPresentante.style.visibility="hidden";
		areaPresentante.style.display="none";
}
</script>
<body>

<br>
	<table cellspacing=0 class=titulo>
	  <tr>
		<td>
			<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n</FONT>
		</td>
	  </tr>
	</table>
<br>
<form class="formulario" name="frm1" method="post">
<input type="hidden" name="hidDep" value="">
<input type="hidden" name="hidProv" value="">
<input type="hidden" name="hidDist" value="">
<input type="hidden" name="hidTipDoc" value="">
<input type="hidden" name="hidArea" value="">
<input type="hidden" name="hidLibro" value="">
<input type="hidden" name="hidActo" value="">
<input type="hidden" name="hidTipoVia" value="">
<input type="hidden" name="hidCodNotaria" value="<%=request.getAttribute("codigoNotaria")%>" >
<input type="hidden" name="hidNomNotaria" value="<%=request.getAttribute("descripcionNotaria")%>" > 
<input type="hidden" name="hidValidar" value="">
	<table class=tablasinestilo>
	    <tr>
	        <th colspan=5>DATOS BASICOS DE LA SOLICITUD</th>
	    </tr>
	    <tr>
	    	<td width="5">&nbsp;</td>
	    	<td width="150">
	    		<strong>ACTO <font color="900000">*</font></strong>
	    	</td>
	      	<td>
	      		<select size="1" name="cboActo" style="width:410">
	            	<option value="00">&lt;&lt; Seleccione el Tipo de Acto &gt;&gt;</option>
	            	<option value="22000|009|00249">Constituci&oacute;n Sociedades An&oacute;nimas</option>
	            	<option value="22000|015|00284">Constituci&oacute;n Empresas Individuales de Responsabilidad Limitada</option>
	            	<option value="22000|010|00256">Constituci&oacute;n Sociedades Comerciales de Responsabilidad Limitada</option>
	            	<option value="24000|088|V0170">Transferencia Propiedad Vehicular</option>
	            	<option value="21000|001|00172">Bloqueo Propiedad Inmueble</option>	            	
	            	<option value="22000|009|00821">Reserva Nombre Sociedades An&oacute;nimas</option>
	            	<option value="22000|015|01132">Reserva Nombre Empresas Individuales de Responsabilidad Limitada</option>
	            	<option value="22000|010|01121">Reserva Nombre Sociedades Comerciales de Responsabilidad Limitada</option>
	        	</select>      
	        </td>
	        <td width="65">
		        <A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Solicitar Solicitud de Inscripción');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		          <IMG src="images/btn_solic.gif" border="0">
		        </A>
		    </td>
		    <td>&nbsp;</td>
	    </tr>
	    <tr>
	        <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
	    </tr>
	    <tr>
	        <td>&nbsp;</td>
	        <td width="150">
	          <strong>OFICINA REGISTRAL <font color="900000">*</font></strong>
	        </td>
	        <td>
	      		<select size="1" name="cboOficinas" onChange="frm1.hidOfic.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text">
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
			    <a href="javascript:Abrir_Ventana('acceso/mapas/MAPA1.htm','Oficinas_Registrales','',500,600)" onmouseover="javascript:mensaje_status('Identifique su Oficina Resgistral');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
			        Identifique su Oficina Registral
			    </a>
			</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	    </tr>
	  	<tr>
	    	<td colspan="5">&nbsp;</td>
	  	</tr>
		<tr>
			<th colspan="5">DATOS DEL PRESENTANTE</th>
		</tr>
	    <tr>
	        <td colspan="5">
	        <div id="areaPresentante" style="visibility:visible;display:">  
	           <table width="100%">
				    <tr>
				      <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
				    </tr>
				    <tr> 
				      <td width="5">&nbsp;</td>
				      <td width="105"><strong>PRESENTANTE <font color="900000">*</font></strong></td>
				      <td width="220"> 
				      		<select name="cboPresentante" onchange=obtieneDatos(); style="width:210">
				      			<option value="00">&lt;&lt; Seleccione el Presentante &gt;&gt;</option>
				      			<logic:present name="arrRepr">
					        		<logic:iterate name="arrRepr" id="item1" scope="session">
								  		<option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
									</logic:iterate>
								</logic:present>
							</select>
				      </td>
				      <td width="85">&nbsp;</td>
				      <td width="150">&nbsp;</td>			      
			        </tr>
		       </table>
	         </div>  
	         </td>
	    </tr>        
	    <tr>
	        <td colspan="5">
	        <div id="areaNatu" style="visibility:visible;display:">  
		       <table>
			        <tr>
			          <td width="5">&nbsp;</td>
			          <td width="150"><strong>APELLIDOS Y NOMBRES <br>Nombre de qui&eacute;n presenta el tr&aacute;mite</strong></td>
			          <td>
			            <table>
			              <tr>
			                <td>
			                  <input type="text" name="txtSolApPa" size="20" maxlength="50" style="width:133" onblur="sololet(this)">
			                </td>
			                <td>
			                  <input type="text" name="txtSolApMa" size="20" maxlength="40" style="width:133" onblur="sololet(this)">
			                </td>
			                <td>
			                  <input type="text" name="txtSolNom" size="20" maxlength="40" style="width:133" onblur="sololet(this)">
			                </td>
			              </tr>
			              <tr>
			                <td>
			                  &nbsp;Apellido Paterno <font color="900000">*</font> 
			                </td>
			                <td>
			                  &nbsp;Apellido Materno 
			                </td>
			                <td>
			                  &nbsp;Nombres <font color="900000">*</font>
			                </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
		      </table>
	        </div>  
	        </td>
	    </tr>
	    
	    <tr>
		    <td colspan="5">
				  <div id="areaInstitucion" style="visibility:visible;display:">  	
			      <table>
				  <tr>
				      <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
				  </tr>  
				  <tr>
				      <td>&nbsp;</td>
					  <td width="110"><strong>INSTITUCION</strong></td>
					  <td width="320" colspan="2">
					  		<input name="txtSolCodInstitucion" type="text" onBlur="sololet(this)" maxlength="90" style="width:45"><input name="txtSolNomInstitucion" type="text" onBlur="sololet(this)" maxlength="90" style="width:235">
					  </td>
					  <td width="150"></td>
				  </tr>
				  </table>
				  </div>
				  <table>
   			      <tr>
				      <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
				  </tr>
				  
				    <tr> 
				      <td>&nbsp;</td>
				      <td width="150"><strong>TIPO DE<BR>
	                        DOCUMENTO <font color="900000">*</font></strong></td>
				      <td> 
					    <select name=cboSolTipDoc width="187" style="width:187"> 
							<option value="00">&nbsp;</option>
							<logic:present name="arrDocu">
								<logic:iterate name="arrDocu" id="item1" scope="session">
								  <option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
								</logic:iterate>
							</logic:present>
						</select>     
					  </td>
				      <td width="133"> <strong>NUMERO DE DOCUMENTO <font color="900000">*</font></strong> </td>
				      <td width="133"> <input type="text" name="txtSolNumDoc" size="12" maxlength="11" style="width:87" onBlur="sololet(this)" value=""> 
				      </td>
			        </tr>
				  		<tr>
				    		<td colspan="5">&nbsp;</td>
				  		</tr>  
				  		<tr>
				    		<td width="5"><input type="hidden" name="hidEnvPais" value="01"></td>
					  		<td width="150"><strong>DEPARTAMENTO</strong></td>
					  		<td>
							    <SELECT name=cboEnvDpto onchange=llenaComboProvincia(); width="187" style="width:187" >
									<option value="  |  ">&nbsp;</option>
									<logic:present name="arr3">
										<logic:iterate name="arr3" id="item3" scope="request">
											<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
										</logic:iterate>
									</logic:present>
								</SELECT>
				   	  		</td>
					  		<td width="65"><!--strong>OTRO</strong--></td>
					  		<td><input type="hidden" name="hidEnvOtro" value=""><!--input type="text" name="txtEnvOtro" onBlur="sololet(this)"--></td>
				  		</tr>
				  		<tr>
				      		<td colspan="5">&nbsp;</td>
				  		</tr>
				  		<tr>
				      		<td width="5">&nbsp;</td>
					  		<td width="150"><strong>PROVINCIA</strong></td>
					  		<td> 
						        <select name="cboEnvProv" onchange=llenaComboDistrito(); width="187" style="width:187">
									<option value="  |  |  ">&nbsp;</option>
									<logic:present name="arr_hijo1">
										<logic:iterate name="arr_hijo1" id="item3" scope="request">
											<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
										</logic:iterate>
									</logic:present>						        
							    </select>
					  		</td>
					  		<td width="150"><strong>DISTRITO</strong></td>
					  		<td>
					  			<%-- <input type="text" name="txtEnvDist" onBlur="sololet(this)" maxlength="90" style="width:180"> --%>
						        <select name="cboEnvDist" width="187" style="width:187">
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
				    		<td colspan="5">&nbsp;</td>
				  		</tr>
				  		<tr>
				      		<td>&nbsp;</td>
					  		<td width="150"><strong>DIRECCION</strong></td>
					  		<td width="430" colspan="3">
					  			<select name="cboEnvTipoVia" style="width:80">
									<option value="  ">&nbsp;</option>
									<logic:present name="arrTipoVia">
										<logic:iterate name="arrTipoVia" id="item3" scope="session">
											<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
										</logic:iterate>
									</logic:present>						        
							    </select><input name="txtEnvDire" type="text" onBlur="sololet(this)" maxlength="90" style="width:210"></td>
				  		</tr>			    
				  		<tr>
				    		<td colspan="5">&nbsp;</td>
				  		</tr>
				  		<tr>
				      		<td>&nbsp;</td>
					  		<td width="150"><strong>COD POSTAL</strong></td>
					  		<td width="180"><input name="txtEnvCodPost" type="text" onBlur="sololet(this)" maxlength="90" style="width:180"></td>
					  		<td width="100"><strong>CORREO<BR>
	                        ELECTRONICO <font color="900000">*</font></strong></td>
					  		<td width="150"><input name="txtEnvCorreoElectronico" type="text" onBlur="sololet(this)" maxlength="90" style="width:180"></td>
				  		</tr>
				</table>
			 </td>
	    </tr>
	  	<tr>
	    	<td colspan="5">&nbsp;</td>
	  	</tr>
	    <%--<tr>
	      <td align="right" colspan="5"><input type="image" src="../images/btn_continuar.gif" onmouseover="#" onmouseOut="#" onClick="#"></td>
	    </tr>--%>
	</table>
<br>
</form>
<script LANGUAGE="JavaScript">
	//llenaComboHijo();
	window.top.frames[0].location.reload();
</script>
<% 
   DatosUsuarioBean datosUsuarioBean = (DatosUsuarioBean) session.getAttribute("DATOS_FORMULARIO");
   Presentante presentante = (Presentante) session.getAttribute("presentante");
   
   // EN CASO EL USUARIO LOGUEADO NO PERTENEZCA A UNA NOTARIA
   if (datosUsuarioBean!=null) {
%>
<script LANGUAGE="JavaScript">
	document.frm1.txtSolApPa.value              = "<%=datosUsuarioBean.getApellidoPaterno()%>";
	document.frm1.txtSolApMa.value              = "<%=datosUsuarioBean.getApellidoMaterno()%>";
	document.frm1.txtSolNom.value               = "<%=datosUsuarioBean.getNombres()%>";
	doCambiaCombo(document.frm1.cboSolTipDoc, "<%=datosUsuarioBean.getTipoDocumento()%>");
	document.frm1.txtSolNumDoc.value            = "<%=datosUsuarioBean.getNumDocumento()%>";
	document.frm1.hidEnvPais.value              =      "<%=datosUsuarioBean.getPais()%>";
	doCambiaCombo(document.frm1.cboEnvDpto, "<%=datosUsuarioBean.getPais()%>|<%=datosUsuarioBean.getDepartamento()%>");
	doCambiaCombo(document.frm1.cboEnvProv, "<%=datosUsuarioBean.getPais()%>|<%=datosUsuarioBean.getDepartamento()%>|<%=datosUsuarioBean.getProvincia()%>");
	doCambiaCombo(document.frm1.cboEnvDist, "<%=datosUsuarioBean.getPais()%>|<%=datosUsuarioBean.getDepartamento()%>|<%=datosUsuarioBean.getProvincia()%>|<%=datosUsuarioBean.getDistrito()%>");
	document.frm1.txtEnvDire.value              = "<%=datosUsuarioBean.getDireccion()%>";
	document.frm1.txtEnvCodPost.value           = "<%=datosUsuarioBean.getCodPostal()%>";
	document.frm1.txtEnvCorreoElectronico.value = "<%=datosUsuarioBean.getEmail()%>";
	doCambiaCombo(document.frm1.cboOficinas, "<%=request.getAttribute("regPubId")%>|<%=request.getAttribute("oficRegId")%>");
	doCambiaCombo(document.frm1.cboActo,"<%=request.getAttribute("acto")%>");
	
	document.frm1.hidValidar.value              = "SI";

	document.frm1.hidDep.value = document.frm1.cboEnvDpto.options[document.frm1.cboEnvDpto.selectedIndex].text;
	document.frm1.hidProv.value = document.frm1.cboEnvProv.options[document.frm1.cboEnvProv.selectedIndex].text;
	document.frm1.hidDist.value = document.frm1.cboEnvDist.options[document.frm1.cboEnvDist.selectedIndex].text;
	document.frm1.hidTipDoc.value = document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].text;
	document.frm1.hidTipoVia.value = document.frm1.cboEnvTipoVia.options[document.frm1.cboEnvTipoVia.selectedIndex].text;
	
	ocultarInstitucion();
	ocultarPresentante();
</script>
<% } else { 

	// EN CASO EL USUARIO LOGUEADO PERTENEZCA A UNA NOTARIA
	if (presentante!=null) {	
		System.out.println("PRESENTANTE::"+presentante);
	%>
<script LANGUAGE="JavaScript">
		document.frm1.txtSolApPa.value              = "<%=presentante.getApellidoPaterno()%>";
		document.frm1.txtSolApMa.value              = "<%=presentante.getApellidoMaterno()%>";
		document.frm1.txtSolNom.value               = "<%=presentante.getNombre()%>";
		document.frm1.txtSolCodInstitucion.value    = "<%=presentante.getCodigoInstitucion()%>";
		document.frm1.txtSolNomInstitucion.value    = "<%=presentante.getDescripcionInstitucion()%>";		
		doCambiaCombo(document.frm1.cboSolTipDoc, "<%=presentante.getCodigoTipoDocumento()%>");
		document.frm1.txtSolNumDoc.value            = "<%=presentante.getNumeroDocumento()%>";
	    document.frm1.hidEnvPais.value              = "<%=presentante.getCodigoPais()%>";
		doCambiaCombo(document.frm1.cboEnvDpto, "<%=presentante.getCodigoPais()%>|<%=presentante.getCodigoDepartamento()%>");
		doCambiaCombo(document.frm1.cboEnvProv, "<%=presentante.getCodigoPais()%>|<%=presentante.getCodigoDepartamento()%>|<%=presentante.getCodigoProvincia()%>");
	    doCambiaCombo(document.frm1.cboEnvDist, "<%=presentante.getCodigoPais()%>|<%=presentante.getCodigoDepartamento()%>|<%=presentante.getCodigoProvincia()%>|<%=presentante.getCodigoDistrito()%>");
		document.frm1.txtEnvDire.value              = "<%=presentante.getDireccion()%>";
		document.frm1.txtEnvCodPost.value           = "<%=presentante.getCodigoPostal()%>";
		document.frm1.txtEnvCorreoElectronico.value = "<%=presentante.getCorreoElectronico()%>";
		doCambiaCombo(document.frm1.cboOficinas, "<%=request.getAttribute("regPubId")%>|<%=request.getAttribute("oficRegId")%>");
	    doCambiaCombo(document.frm1.cboPresentante, "<%=request.getAttribute("codRepr")%>");
	    doCambiaCombo(document.frm1.cboActo,"<%=request.getAttribute("acto")%>");

		document.frm1.hidValidar.value              = "NO";
</script>
<%  } %>
<script LANGUAGE="JavaScript">
	document.frm1.hidDep.value = document.frm1.cboEnvDpto.options[document.frm1.cboEnvDpto.selectedIndex].text;
	document.frm1.hidProv.value = document.frm1.cboEnvProv.options[document.frm1.cboEnvProv.selectedIndex].text;
	document.frm1.hidDist.value = document.frm1.cboEnvDist.options[document.frm1.cboEnvDist.selectedIndex].text;
	document.frm1.hidTipDoc.value = document.frm1.cboSolTipDoc.options[document.frm1.cboSolTipDoc.selectedIndex].text;
	document.frm1.hidTipoVia.value = document.frm1.cboEnvTipoVia.options[document.frm1.cboEnvTipoVia.selectedIndex].text;

	document.frm1.txtSolApPa.disabled           = true;
	document.frm1.txtSolApMa.disabled           = true;
	document.frm1.txtSolNom.disabled            = true;
	document.frm1.cboSolTipDoc.disabled         = true;;
	document.frm1.txtSolNumDoc.disabled         = true;
	document.frm1.cboEnvDpto.disabled         = true;
	document.frm1.cboEnvProv.disabled         = true;
	document.frm1.cboEnvDist.disabled         = true;
	document.frm1.cboEnvTipoVia.disabled         = true;
	document.frm1.txtEnvDire.disabled         = true;
	document.frm1.txtEnvCodPost.disabled         = true;
	document.frm1.txtEnvCorreoElectronico.disabled         = true;
	document.frm1.txtSolCodInstitucion.disabled         = true;
	document.frm1.txtSolNomInstitucion.disabled         = true;
			
	mostrarInstitucion();
	mostrarPresentante();
</script>
<%  } %>
<script LANGUAGE="JavaScript">
	frm1.hidOfic.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text;	
</script>
<table>
	<tr>
		<td><strong><font color="900000">* Los datos son obligatorios</font></strong></td>
	</tr>
</table>
<br>
<table>
	<tr>
	    <td>
	       <font color="900000"><strong>La aceptación de reserva esta supeditada al cumplimiento del Reglamento de Sociedades<BR>
            Resolución Nº 200-2001-SUNARP/SN</strong></font>
	    </td>
	</tr>
</table>
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
</body>
</html>