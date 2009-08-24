<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--//Inicio:jascencio:05/06/2007
	//SUNARP-REGMOBCOM:
-->

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*"%>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<html>
<head>
<title>SolicitudDatosRJB</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<script Language="javaScript">
	
	var arrRegistro = new Array();
	var contador=0;
	var flagActivo=1;
	var flagInactivo=0;
	
	function dominio()
	{

		areaDominio.style.visibility="visible";
		areaDominio.style.display="";
	}
	function noDominio(){
		areaDominio.style.visibility="hidden";
		areaDominio.style.display="none";
	}
	function buscar()
	{

		areaBuscar.style.visibility="visible";
		areaBuscar.style.display="";
	}
	function noBuscar(){
		areaBuscar.style.visibility="hidden";
		areaBuscar.style.display="none";
	}
	function serie()
	{

		areaSerie.style.visibility="visible";
		areaSerie.style.display="";
	}
	function noSerie(){
		areaSerie.style.visibility="hidden";
		areaSerie.style.display="none";
	}
	function matricula()
	{

		areaMatri.style.visibility="visible";
		areaMatri.style.display="";
	}
	function noMatricula(){
		areaMatri.style.visibility="hidden";
		areaMatri.style.display="none";
	}		
	
	
	
	function cambioArea()
	{	
		<logic:iterate name="arrCertificados" id="item22" scope="request">
		if(document.frm1.cboArea.options[document.frm1.cboArea.selectedIndex].value==<bean:write name="item22" property="codigo"/>)
		{
			document.frm1.hidGLA.value=<bean:write name="item22" property="atributo1"/>;
			
		}
		</logic:iterate>

		if(document.frm1.cboArea.value==29 || document.frm1.cboArea.value==25){//registro vehicular
			buscar();
			noSerie();
			noMatricula();	
		}
		else{
			if(document.frm1.cboArea.value==30 || document.frm1.cboArea.value==32 || document.frm1.cboArea.value==26 || document.frm1.cboArea.value==28)//embarcaciones pesqueras o el registro de buques
			{
				matricula();
				noSerie();
				noBuscar();
				
				/**** inicio: jrosas 06-09-2007 **/
				document.frm1.txtNumero.value="";
				if (document.frm1.txtNumMat.value.length > 0){
					document.frm1.txtSerie.value="";
					document.frm1.txtPartida.value="";
				}else{
					if (document.frm1.txtPartida.value.length > 0){
						document.frm1.txtSerie.value="";
						document.frm1.txtNumMat.value="";
					}
				}
				/**** fin: jrosas 06-09-2007 **/
			}
			else{
				if(document.frm1.cboArea.value==27 || document.frm1.cboArea.value==31)//aeronaves
				{	
					serie();
					noBuscar();
					matricula();

					/**** inicio: jrosas 06-09-2007 **/
					document.frm1.txtNumero.value="";
					if (document.frm1.txtPartida.value.length > 0){
						document.frm1.txtSerie.value="";
						document.frm1.txtNumMat.value="";
					}else{
						if (document.frm1.txtSerie.value.length > 0){
							document.frm1.txtPartida.value="";
							if (document.frm1.txtNumMat.value.length = 0){
								document.frm1.txtNumMat.value="";
							}
						}else{
							document.frm1.txtPartida.value="";
							document.frm1.txtSerie.value="";
							if (document.frm1.txtNumMat.value.length = 0){
								document.frm1.txtNumMat.value="";
							}
						}					
					}
					/**** fin: jrosas 06-09-2007 **/
				}
			}
			
		}
		document.frm1.hidTipoReg.value=document.frm1.cboArea.options[document.frm1.cboArea.selectedIndex].text;
	}
	function validar(){
	
		if(document.frm1.cboArea.value==25 ||document.frm1.cboArea.value==29){//registro vehicular
			if(document.frm1.radBusca[0].checked==true){
				if(!isAlphaNum(document.frm1.txtNumero.value)){
					alert("Por favor ingrese el numero de placa sin guiones ni espacios en blanco");
					return;
				}
				else{
					if(document.frm1.txtNumero.value.length>7){
						alert("Por favor ingrese correctamente el Número de Placa.  El Número de Placa admite como máximo 7 dígitos");
						return;
					}
				}
			}
			else{
				if(!isNumericNoSpaces(document.frm1.txtNumero.value)){
					alert("Por favor ingrese correctamente el Número de Partida.  El Número de Partida admite como máximo 8 dígitos");
					return;
				}
				else{
					if(document.frm1.txtNumero.value.length>8){
						alert("Por favor ingrese correctamente el Número de Partida.  El Número de Partida admite como máximo 8 dígitos");
						return;
					}
				}
			}
		}
		else{
			if(document.frm1.cboArea.value==26 || document.frm1.cboArea.value==28 ||document.frm1.cboArea.value==30 || document.frm1.cboArea.value==32)//embarcaciones pesqueras o el registro de buques
			{
				if(document.frm1.txtNumMat.value.length>0 && document.frm1.txtPartida.value.length>0){
					alert("Solo debe ingresar uno de los datos a la vez.");
					return;
				}
				else{
					if(document.frm1.txtNumMat.value=="" && document.frm1.txtPartida.value==""){
						alert("Por favor ingrese uno de los datos.");
						return;
					}
					else{
						if(document.frm1.txtNumMat.value!="" ){
							if(isCharacter(document.frm1.txtNumMat.value,"%")){
								alert("Por favor no ingrese caracteres no válidos");
								return;
							}
						}
						else{
							if(document.frm1.txtPartida.value!=""){
								if(isCharacter(document.frm1.txtPartida.value,"%")){
									alert("Por favor no ingrese caracteres no válidos");
									return;
								}
								else{

									if(!isNumericNoSpaces(document.frm1.txtPartida.value)){
										alert("Por favor ingrese correctamente el Número de Partida. El Número de Partida admite como máximo 8 dígitos");
										return;
									}
								}
							}
						}
					}
				}
			}
			else{
				if(document.frm1.cboArea.value==27 || document.frm1.cboArea.value==31)//aeronaves
				{	
					if(document.frm1.txtPartida.value.length<=0 && document.frm1.txtNumMat.value.length<=0 && document.frm1.txtSerie.value.length<=0){
						alert("Por favor ingrese uno de los datos.");
						return;
					}
					else{
					//
							if(document.frm1.txtPartida.value!="" && document.frm1.txtNumMat.value!=""){
								alert("Solo se puede ingresar Serie y Número de Matrícula en combinación.");
								return;
							}
							else{
								if(document.frm1.txtPartida.value!="" && document.frm1.txtSerie.value!=""){
									alert("Solo se puede ingresar Serie y Número de Matrícula en combinación.");
									return;
								}
								else{
									if(document.frm1.txtPartida.value!=""){
										if(isCharacter(document.frm1.txtPartida.value,"%")){
											alert("Por favor no ingrese caracteres no válidos");
											return;
										}
										else{
		
											if(!isNumericNoSpaces(document.frm1.txtPartida.value)){
												alert("Por favor ingrese correctamente el Número de Partida. El Número de Partida admite como máximo 8 dígitos");
												return;
											}
										}
									}
									else{
										if(document.frm1.txtNumMat.value!="" ){
											if(isCharacter(document.frm1.txtNumMat.value,"%")){
												alert("Por favor no ingrese caracteres no válidos");
												return;
											}
										}
										else{
											if(document.frm1.txtSerie.value!=""){
												if(isCharacter(document.frm1.txtSerie.value,"%")){
													alert("Por favor no ingrese caracteres no válidos");
													return;
												}		
											}
											else{
												if(document.frm1.txtNumMat.value.length<0 && document.frm1.txtSerie.value.length<0){
													alert("Por favor ingrese uno de los datos.");
													return;
												}
											
											}
										}
									}
								}
							}
					//
					}
					
				}
				
			}
			
		}
		document.frm1.hidOfic.value=document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text;
		document.frm1.action="/iri/Certificados.do?state=guardarDatosBasicos";
		document.frm1.submit();
	}
	
	function isAlphaNum( str ) {

	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
		return false;

	var isValid = true;
	str += ""; 

	for (i = 0; i < str.length; i++)
	{

		if (!(((str.charAt(i) >= "0") && (str.charAt(i) <= "9")) || 
		((str.charAt(i) >= "a") && (str.charAt(i) <= "z")) ||
		((str.charAt(i) >= "A") && (str.charAt(i) <= "Z"))))
		{
		isValid = false;
		break;
		} 
	}
	
	return isValid;
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
	
	
//la funcion isCharacter recibe 2 parametros, el str debe ser diferente de vacio o nulo
	function isCharacter(str,char){
	
	if (char == "undefined" || char == "null" ||char == "")
		return false;

	var bandera=false;
	str += ""; 

	for (i = 0; i < str.length; i++)
	{

		if (!(str.charAt(i) != char))
		{
		bandera = true;
		break;
		} 
	}
	return bandera;
	}
	function mayuscula(objeto){
		objeto.value=objeto.value.toUpperCase();
	}
	
</script>
</head>
<body onload="cambioArea()">
	<div id="maincontent">
		<div class="innertube">
			<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Solicitud de Certificados</b>
			<form name="frm1" method="post">
			<br>
			<table cellspacing=0>
			     <tr> 
			   	 	<td width="685" colspan="8" >
			   			<p align="left"><font color="#949400" size="2"><strong>1. DATOS DEL SOLICITANTE</strong></font></p>
			   		</td>
			     </tr>
			</table>
			<input type="hidden" name="hidTipo" value="<%=request.getAttribute("tipoCertificado") %>">
			<input type="hidden" name="hidTipoReg">
			<input type="hidden" name="hidOfic">
			<input type="hidden" name="hidGLA">		
			<table class=formulario cellspacing=0>
				<tr>
					<td>&nbsp;</td>
					<td>Area</td>	
					<td>&nbsp;</td>	
					<td colspan="2">
						<select name="cboArea" onchange="cambioArea()">
									
							<logic:iterate name="arrCertificados" id="item2" scope="request">
								<option value="<bean:write name="item2" property="codigo"/>">
								<bean:write name="item2" property="descripcion" /></option>
							</logic:iterate>				
						</select>
					</td>
					
					<td>&nbsp;</td>			
				</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Oficina Registral</td>	
		<td>&nbsp;</td>	
		<td colspan="2">
			<select name="cboOficinas" >
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
					<option value="13|01">Coronel portillo</option>
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
					<option value="01|01" selected>Lima</option>
					<option value="06|03">Madre de dios</option>
					<option value="07|04">Moquegua</option>
					<option value="12|01">Moyobamba</option>
					<option value="10|04">Nazca</option>
					<option value="08|04">Otuzco</option>
					<option value="02|04">Pasco</option>
					<option value="10|03">Pisco</option>
					<option value="05|01">Piura</option>
					<option value="07|05">Puno</option>
					<option value="06|04">Quillabamba</option>
					<option value="08|05">San pedro de lloc</option>
					<option value="02|05">Satipo</option>
					<option value="02|06">Selva Central</option>
					<option value="06|05">Sicuani</option>
					<option value="05|02">Sullana</option>
					<option value="07|01">Tacna</option>
					<option value="12|02">Tarapoto</option>
					<option value="02|07">Tarma</option>
					<option value="02|08">Tingo Mar&iacute;a</option>
					<option value="08|01">Trujillo</option>
					<option value="05|03">Tumbes</option>
					<option value="09|02">Yurimaguas</option>
			</select>
			
		</td>
		 
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
	<!-- inicio: jrosas 03-09-07 -->
	<%boolean eval = false; %>
	<logic:iterate name="arrCertificados" id="item22" scope="request">
		<%if (eval!= true){ %>
		<logic:greaterEqual name="item22" property="codigo" value="25">
			<logic:lessEqual name="item22" property="codigo" value="28">
				<% eval = true; %>
				<tr>
					<td colspan="6">
						<div id="areaDominio" style="visibility:hidden;display:none">
							<tr>
								<td>&nbsp;</td>
								<td>Tipo de Información de Dominio</td>	
								<td>&nbsp;</td>	
								<td><input type="radio" name="radTipInfoDominio" checked="checked"  value="C" >Completa</td>
								<td><input type="radio" name="radTipInfoDominio" value="U">Último Propietario</td>
								<td>&nbsp;</td>			
							</tr>
					
					
			
			</logic:lessEqual>
		</logic:greaterEqual>
		<%} %>
	</logic:iterate>	
	<!-- fin: jrosas 03-09-07 -->	
	<tr>
		<td colspan="6">
			<div id="areaBuscar" style="visibility:hidden;display:none">
			<table>
				<tr>
					<td>&nbsp;</td>
					<td>Buscar por:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
					<td><input type="radio" name="radBusca" checked="checked" value="Placa">Placa</td>
					<td><input type="radio" name="radBusca">Partida</td>
					<td>&nbsp;</td>			
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>Número</td>	
					<td>&nbsp;</td>	
					<td><input type="text" name="txtNumero" maxlength="8" onblur="mayuscula(this)"></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>			
				</tr>
							
			</table>
			</div>
		</td>	
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
		<td colspan="6">
			<div id="areaSerie" style="visibility:hidden;display:none">
				<table>
				<tr>
					<td>&nbsp;</td>
					<td>Serie&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
					<td><input type="text" name="txtSerie" maxlength="20" onblur="mayuscula(this)"></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>			
				</tr>	
				</table>		
			</div>
		</td>
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
		<td colspan="6">
			<div id="areaMatri" style="visibility:hidden;display:none">
				<table>
					<tr>
						<td>&nbsp;</td>
						<td>Número de matrícula&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input type="text" name="txtNumMat" maxlength="15" onblur="mayuscula(this)"></td>
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
						<td>Partida</td>
						<td>&nbsp;</td>
						<td><input type="text" name="txtPartida" maxlength="8" onblur="mayuscula(this)"></td>
						<td>&nbsp;</td>		
						<td>&nbsp;</td>			
					</tr>										
				</table>
			</div>
		</td>
	
	</tr>
	

	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td><a href="javascript:validar();"
			onmouseover="javascript:mensaje_status(' ');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_solic.gif" border="0">
		</a></td>		
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

</body>
</html>
<!--//Fin:jascencio-->
