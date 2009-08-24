<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*"%>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<html>
<head>
<title>SolicitudDatosRMC</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js"></script>

<script Language="javascript">
	
	
	
	function CambioCert()
	{
		frm0.hidCert.value = document.frm0.cboTipoCert.options[document.frm0.cboTipoCert.selectedIndex].text;
		
		//Tarifario
		<logic:iterate name="arrCertificados" id="item22" scope="request">
		if(document.frm0.cboTipoCert.options[document.frm0.cboTipoCert.selectedIndex].value==<bean:write name="item22" property="codigo"/>)
		{
			document.frm0.hidGLA.value=<bean:write name="item22" property="atributo1"/>;
			document.frm0.hidArea.value=<bean:write name="item22" property="atributo2"/>;
		}
		</logic:iterate>
		//Fin Tarifario

		//Opcion "REGISTRO MOBILIARIOS DE CONTRATOS"
		if(document.frm0.hidGLA.value==22){
						
			noParticipante();
		}
		else{
			if(document.frm0.hidGLA.value==23){
				
				participante();			
			}
			
		}
		
		
	}
	
	
	function participante()
	{
		areaParti1.style.visibility="visible";
		areaParti1.style.display="";
		areaParti2.style.visibility="visible";
		areaParti2.style.display="";
		areaParti3.style.visibility="visible";
		areaParti3.style.display="";
		

	}	
	function noParticipante()
	{
		areaParti1.style.visibility="hidden";
		areaParti1.style.display="none";
		areaParti2.style.visibility="hidden";
		areaParti2.style.display="none";
		areaParti3.style.visibility="hidden";
		areaParti3.style.display="none";
	}
	
	function validar(val){
	
		if(val==1){
			if(document.frm1.txtApPa.value.length<2){
				alert("El apellido paterno debe tener al menos dos letras.");
				frm1.txtApPa.focus();
				return;
			}
			else{
				if(document.frm1.txtNom.value.length<2){
					alert("El nombre debe tener al menos dos letras.");
					frm1.txtNom.focus();
					return;
				}
				else{
					if(document.frm1.txtApMa.value!="" && document.frm1.txtApMa.value.length<2){
						alert("El apellido materno debe tener al menos dos letras.");
						frm1.txtApMa.focus();
						return;
					}
					else{
						if(document.frm0.cboTipoCert.value==20){// tipo de certificado de gravamen
						 	if(document.frm1.cboTipoParticipante.value==""){
						 		alert("Debe elegir un tipo de participación");
						 		return;
						 	}	
						 }
					}
				
				}
			}
		document.frm1.hidGLA.value=document.frm0.hidGLA.value;
		document.frm1.hidArea.value=document.frm0.hidArea.value;
		document.frm1.hidCert.value=document.frm0.hidCert.value;
		document.frm1.cboTipoCert.value=document.frm0.cboTipoCert.value;
		document.frm1.cboOficinas.value="00|00";
		document.frm1.hidOfic.value="Web";	
		document.frm1.hidDesTipoPar.value=document.frm1.cboTipoParticipante[document.frm1.cboTipoParticipante.selectedIndex].text;
		document.frm1.action="/iri/Certificados.do?state=guardarDatosBasicos";
		document.frm1.submit();

		}
		if(val==2){
		
			if(document.frm2.txtRazSoc.value!=""){
				if(document.frm2.txtRazSoc.value.length<3){
					alert("Debe ingresar al menos tres caracteres en la Razón Social");	
					return;
				}
				else{
					if(document.frm2.txtSiglas.value!=""){
							if(document.frm2.txtSiglas.value.length<2){
									alert("Por favor ingrese correctamente las Siglas de la Persona Jurídica. Las Siglas de la Persona Jurídica deben contener al menos 2 caracteres.");
									frm2.txtSiglas.focus();
									return;
							}
							else{
								if(document.frm0.cboTipoCert.value==20){
									if(document.frm2.cboTipoParticipante.value==""){
							 		alert("Debe elegir un tipo de participación");
							 		return;
							 		}
								}
								
							}
							
					}
					else{						
						if(document.frm0.cboTipoCert.value==20){
						 	if(document.frm2.cboTipoParticipante.value==""){
						 		alert("Debe elegir un tipo de participación");
						 		return;
						 	}
						}
					}
				}
			}
			else{
				if(document.frm2.txtSiglas.value!=""){
					if(document.frm2.txtSiglas.value.length<2){
							alert("Por favor ingrese correctamente las Siglas de la Persona Jurídica. Las Siglas de la Persona Jurídica deben contener al menos 2 caracteres.");
							frm2.txtSiglas.focus();
							return;
					}
					else{
						if(document.frm0.cboTipoCert.value==20){	
						 	if(document.frm2.cboTipoParticipante.value==""){
						 		alert("Debe elegir un tipo de participación");
						 		return;
						 	}
						 }	
					}
				}
				else{
					if(document.frm0.cboTipoCert.value==20){
							if(document.frm2.txtSiglas.value.length<=0 && document.frm2.txtRazSoc.value.length<=0){
								alert("Debe ingresar un campo como mínimo");
					 			return;
							}
							else{
								if(document.frm0.cboTipoCert.value==20){
									if(document.frm2.cboTipoParticipante.value==""){
								 		alert("Debe elegir un tipo de participación");
								 		return;
								 	}	
								 }	
							}
							 	
					}
					else{
						alert("Debe ingresar un campo como mínimo");
						return;
					}
					
				}
				
			}
		
		document.frm2.hidGLA.value=document.frm0.hidGLA.value;
		document.frm2.hidArea.value=document.frm0.hidArea.value;		
		document.frm2.hidCert.value=document.frm0.hidCert.value;
		document.frm2.cboTipoCert.value=document.frm0.cboTipoCert.value;				
		document.frm2.cboOficinas.value="00|00";
		document.frm2.hidOfic.value="Web";		
		document.frm2.hidDesTipoPar.value=document.frm2.cboTipoParticipante[document.frm2.cboTipoParticipante.selectedIndex].text;
		document.frm2.action="/iri/Certificados.do?state=guardarDatosBasicos";
		document.frm2.submit();
		
		}
		if(val==3){
			if(document.frm3.cboTipoDocumento.value==""){
					alert("Debe ingresar el tipo de documento");
					return;
				}
				else{
						if(!isNumericNoSpaces(document.frm3.txtNumeroDocumento.value)){
								alert("Por favor ingrese correctamente el Número del Documento. El Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
								return;
						}
						else{
							if(document.frm3.txtNumeroDocumento.value.length<8){
								alert("Por favor ingrese correctamente el Número del Documento. El Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
								frm2.txtNumeroDocumento.focus();
								return;
							}
							else{
								if(document.frm0.cboTipoCert.value==20){
								 	if(document.frm3.cboTipoParticipante.value==""){
								 		alert("Debe elegir un tipo de participación");
								 		return;
								 	}	
								 }
							}
							 
						}
					
				}
		document.frm3.hidDesTipoDoc.value=document.frm3.cboTipoDocumento.options[document.frm3.cboTipoDocumento.selectedIndex].text
		document.frm3.hidGLA.value=document.frm0.hidGLA.value;
		document.frm3.hidArea.value=document.frm0.hidArea.value;				
		document.frm3.hidCert.value=document.frm0.hidCert.value;
		document.frm3.cboTipoCert.value=document.frm0.cboTipoCert.value;		
		document.frm3.cboOficinas.value="00|00";		
 		document.frm3.hidOfic.value="Web";		
		document.frm3.hidDesTipoPar.value=document.frm3.cboTipoParticipante[document.frm3.cboTipoParticipante.selectedIndex].text;
		document.frm3.action="/iri/Certificados.do?state=guardarDatosBasicos";
		document.frm3.submit();
			
		}
	
	}
	
	function isNumericNoSpaces( str ) {

		if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
			return false;
	
		var isValid = true;
		str += ""; 
	
		for (i = 0; i < str.length; i++)
		{
	
			if (!((str.charAt(i) >= "0") && (str.charAt(i) <= "9")))
			{
			isValid = false;
			break;
			} 
		}
		
		return isValid;
	} 
	
	function mayuscula(objeto){
		objeto.value=objeto.value.toUpperCase();
	}
			
	
</script>


</head>
<body onload="CambioCert()">
<br>
<table class=titulo cellspacing=0>
	<tr>
		<td><font color=black>SERVICIOS &gt;&gt;</font>Solicitud de
		Certificados</td>
	</tr>
</table>
<br>
<!--<span id="presentante">-->
<table class=cabeceraformulario cellspacing=0>
	<tr>
		<td><strong>DATOS BASICOS DE LA SOLICITUD</strong></td>
	</tr>
</table>
<form name="frm0" method="post">
<table class=formulario cellspacing=0>
	<input type="hidden" name="hidGLA">
	<input type="hidden" name="hidArea">
	<input type="hidden" name="hidCert"> 
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><strong>Tipo de Certificado:</strong></td>
		<td><select name="cboTipoCert" size="1" disabled="disabled">
			<option value="">Seleccione el Tipo de Certificado</option>
			<logic:iterate name="arrCertificados" id="item2" scope="request">
				<logic:equal name="tipoGravVigRMC" value="RV">
					<logic:equal name="item2" property="codigo" value="19" >
						<option value="<bean:write name="item2" property="codigo"/>" selected="selected">
							<bean:write name="item2" property="descripcion" /></option>
					</logic:equal>
				</logic:equal>	
				<logic:equal name="tipoGravVigRMC" value="RG">
					<logic:equal name="item2" property="codigo" value="20" >
						<option value="<bean:write name="item2" property="codigo"/>" selected="selected">
							<bean:write name="item2" property="descripcion" /></option>
					</logic:equal>
				</logic:equal>						
			</logic:iterate>

		</select></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

</table>
</form>

<form name="frm1" method="post">
	<input type="hidden" name="hidGLA">
	<input type="hidden" name="hidArea">
	<input type="hidden" name="hidCert">
	<input type="hidden" name="hidTipo" value="R">  
	<input type="hidden" name="radTipoPers" value="N">
	<input type="hidden" name="hidTipPers" value="Natural">
	<input type="hidden" name="cboTipoCert">	
	<input type="hidden" name="cboOficinas">	
	<input type="hidden" name="hidOfic">	
	<input type="hidden" name="hidDesTipoPar">
			
		
	
<table class=formulario cellspacing=0>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>		
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2"><strong>Persona Natural</strong></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>		
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Apellido Paterno&nbsp;&nbsp;&nbsp;</td>
		<td><input type="text" name="txtApPa" maxlength="18" onblur="mayuscula(this)"></td>
		<td>Apellido Materno</td>
		<td><input type="text" name="txtApMa" maxlength="18" onblur="mayuscula(this)"></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Nombres</td>
		<td><input type="text" name="txtNom" onblur="mayuscula(this)" maxlength="40"></td>
		<td colspan="2">
		<div id="areaParti1" style="visibility:hidden;display:none">
		<table>
			<tr>
				<td>Tipo Participante:</td>
				<td><select name="cboTipoParticipante">
					<option value="">Elija el tipo</option>
					<option value="1">Deudor</option>
					<option value="2">Acreedor</option>
					<option value="3">Representante</option>
					<option value="4">Otros</option>
					</select>
				</td>
			</tr>
		</table>
		</div>
		</td>
		<td><a href="javascript:validar(1);"
			onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_solic.gif" border="0">
		</a></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>

</form>

<form name="frm2" method="post">

	<input type="hidden" name="hidGLA">
	<input type="hidden" name="hidArea">
	<input type="hidden" name="hidCert">
	<input type="hidden" name="hidTipo" value="R">  	
	<input type="hidden" name="radTipoPers" value="J">
	<input type="hidden" name="hidTipPers" value="Jurídica">	
	<input type="hidden" name="cboTipoCert">	
	<input type="hidden" name="cboOficinas">		
	<input type="hidden" name="hidOfic">		
	<input type="hidden" name="hidDesTipoPar">	
				
<table class=formulario cellspacing=0>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2"><strong>Persona Jurídica</strong></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
		<td><label></label>Razon Social </td>
		<td><input type="text" name="txtRazSoc" maxlength="50" onblur="mayuscula(this)"></td>
		<td>Siglas</td>
		<td><input type="text" name="txtSiglas" maxlength="18" onblur="mayuscula(this)"></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2">
		<div id="areaParti2" style="visibility:hidden;display:none">
		<table>
			<tr>
				<td>Tipo Participante:</td>
				<td><select name="cboTipoParticipante">
					<option value="">Elija el tipo</option>
					<option value="1">Deudor</option>
					<option value="2">Acreedor</option>
					<option value="3">Representante</option>
					<option value="4">Otros</option>
					</select>
				</td>
			</tr>
		</table>
		</div>
		</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td><a href="javascript:validar(2);"
			onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Juridica');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_solic.gif" border="0">
		</a></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
</table>

</form>
<form name="frm3" method="post">
	<input type="hidden" name="hidGLA">
	<input type="hidden" name="hidArea">
	<input type="hidden" name="hidCert">
	<input type="hidden" name="hidTipo" value="R">  	
	<input type="hidden" name="radTipoPers" value="T">
	<input type="hidden" name="hidTipPers">
	<input type="hidden" name="cboTipoCert">
	<input type="hidden" name="hidDesTipoDoc">
	<input type="hidden" name="cboOficinas">	
	<input type="hidden" name="hidOfic">			
	<input type="hidden" name="hidDesTipoPar">		
<table class=formulario cellspacing=0>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2"><strong>Tipo y Número de documento</strong></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Tipo</td>
		<td><select name="cboTipoDocumento">
			<option value="">&nbsp;--&nbsp;</option>
			<logic:iterate name="arrDocu" id="item1" scope="request">
				  <option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
			</logic:iterate>
			</select></td>
		<td>Número de Documento</td>
		<td><input type="text" name="txtNumeroDocumento" maxlength="15"></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2">
		<div id="areaParti3" style="visibility:hidden;display:none;">
		<table>
			<tr>
				<td>Tipo Participante:</td>
				<td><select name="cboTipoParticipante">
					<option value="">Elija el tipo</option>
					<option value="1">Deudor</option>
					<option value="2">Acreedor</option>
					<option value="3">Representante</option>
					<option value="4">Otros</option>
					</select>
				</td>
			</tr>
		</table>
		</div>		
		</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td><a href="javascript:validar(3);"	onmouseover="javascript:mensaje_status('Buscar por Tipo y Numero de Documento');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_solic.gif" border="0"/>
		</A></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	

</table>

</form>
</body>
</html>
