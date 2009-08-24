<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*"%>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>

<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head>
<title>Solicitud de Datos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">

//arreglo 2 empieza vacio:



function doSubmit(){
	doSendChildren();
}

	
</SCRIPT>
<script Language="JavaScript">
	function VentanaFlotante(pag)
	{
		var ancho= 500;
		var alto= 563;
		NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
	}
	
	function Natural()
	{
		areaNatu.style.visibility="visible";
		areaNatu.style.display="";
		areaJuri.style.visibility="hidden";
		areaJuri.style.display="none";
	}
	function Juridica()
	{
		areaJuri.style.visibility="visible";
		areaJuri.style.display="";
		areaNatu.style.visibility="hidden";
		areaNatu.style.display="none";
	}
	
	function CambioCert()
	{
		frm1.hidCert.value = document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].text;
		//Tarifario
		<logic:iterate name="arrCertificados" id="item22" scope="request">
		if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value==<bean:write name="item22" property="codigo"/>)
		{
			document.frm1.hidGLA.value=<bean:write name="item22" property="atributo1"/>;
			document.frm1.hidArea.value=<bean:write name="item22" property="atributo2"/>;
		}
		</logic:iterate>
		//Fin Tarifario

		//Opcion "REGISTRO MOBILIARIOS DE CONTRATOS"
		if(frm1.cboTipoCert.value==18){
			certiRMC();	
		}
		else{
			certiVarios();
		}
		
	
	}
	function validaServicio()
	{
		if(document.frm1.radTipoPers[0].checked==true)
		{
			if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value == "4")
			{
				alert("El servicio elegido no está disponible para Persona Natural.");
				return true;
			}
		}
		else
		{
			if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value == "1" || document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value == "2" || document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value == "3")
			{
				alert("El servicio elegido no está disponible para Persona Jurídica.");
				return true;
			}
		}
		if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value == "6" && (!(
		document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].value == "01|01"
		|| document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].value == "10|01"
		)))
		{
			alert("La oficina elegida no está disponible para búsquedas de Propiedad Vehicular");
			return true;
		}
		return false;
	}
	function Validar()
	{
		if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value=="")
		{
			alert("Seleccione un tipo de Certificado.");
			return;
		}
		else
		{
			if(validaServicio())
			return;
			//Inicio: jascencio:29/05/2007
			//CC:SUNARP-REGMOBCOM
			if(frm1.cboTipoCert.value==18)
			{
				
				//************************************************************************************
				//inicio:dbravo:02/10/2007
				//descripcion: cambio para validar acceso a nuevos recursos
				<%
				  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
				  long perfilusuarioid =usuarioBean.getPerfilId();
				%>  
				
				if(noTieneAccesoRecursoRMC(10, <%=usuarioBean.getPerfilId()%>, '<%=usuarioBean.getUserId()%>')){
					return;	
				}
				//fin:dbravo:02/10/2007
				//************************************************************************************
				
				//otra forma
				if(document.frm1.txtNumPlaca.value.length>0){
					if(document.frm1.txtNumMatricula.value.length>0){
						alert("Sólo puede ingresar en combinación nombre y número de matrícula");
						return;
					}
					else{
						if(document.frm1.txtNombreBien.value.length>0){
							alert("Sólo puede ingresar en combinación nombre y número de matrícula");
							return;
						}
						else{
							if(document.frm1.txtNumSerie.value.length>0){
								alert("Sólo puede ingresar en combinación nombre y número de matrícula");
								return;
							}
							else{
								
								if(!isAlphaNum(document.frm1.txtNumPlaca.value)){

								  alert("Por favor ingrese correctamente el Numero de Placa.");
									frm1.txtNumPlaca.focus();
								  return;
								  }else 
  								if(document.frm1.txtNumPlaca.value.length<6){
  								 alert("Por favor ingrese correctamente el Numero de Placa.");
									frm1.txtNumPlaca.focus();
								  return;
								}
							}
						
						}
						
					}
				}
				else{
					if(document.frm1.txtNumMatricula.value.length>0){
						if(document.frm1.txtNumSerie.value.length>0){
							alert("Sólo puede ingresar en combinación nombre y número de matrícula");
							return;
						}
						else{
						
						 	if(isCharacter(document.frm1.txtNumMatricula.value,"%")){
								alert("Por favor no ingrese caracteres no válidos");
								return;
							}
						}
					}
					else{
						if(document.frm1.txtNumSerie.value.length>0){
							if(document.frm1.txtNombreBien.value.length>0){
								alert("Sólo puede ingresar en combinación nombre y número de matrícula");
								return;
							}
							else{
								
								if(isCharacter(frm1.txtNumSerie.value,"%")){
									alert("Por favor no ingrese caracteres no válidos");
									return;
								}
							}
						}
						else{
						 
							if(document.frm1.txtNombreBien.value.length>0){
								alert("No se puede solicitar el certificado únicamente con el nombre, por favor ingrese también el número de matrícula");
								return;
							}
							else{
								alert("Ingrese los campos requeridos");
								return;
							}
						
						}
					}
				
				}
				
				document.frm1.hidCboOficinas.value="00|00";
				document.frm1.hidOfic.value="Web";
				//fin de otra forma
				
			
			}
			//si no se escoge la opcion de REGISTRO MOBILIARIO DE CONTRATOS
			//Fin: jascencio: 29/05/2007
			else
			{
				if(document.frm1.radTipoPers[0].checked==true)
			//if(frm1.hidTipPers.value=="Jurídica")
			{
				if(frm1.txtApPa.value=="" && frm1.txtNom.value=="")
				{
					alert("Ingrese apellidos y nombres.");
					frm1.txtApPa.focus();
					return;
				}
				else
				{
					if(frm1.txtApPa.value.length<2)
					{
						alert("El apellido paterno debe tener al menos dos letras.");
						frm1.txtApPa.focus();
						return;
					}
					if(frm1.txtApMa.value.length==1)
					{
						alert("El apellido materno debe tener al menos dos letras.");
						frm1.txtApMa.focus();
						return;
					}
					if(frm1.txtNom.value.length<2)
					{
						alert("El nombre debe tener al menos dos letras.");
						frm1.txtNom.focus();
						return;
					}
				}
				frm1.hidTipPers.value="Natural";
			}
			else
			{
				if(frm1.txtRazSoc.value=="")
				{
					alert("Ingrese la razon social.");
					frm1.txtRazSoc.focus();
					return;
				}
				else
				{
					if(frm1.txtRazSoc.value.length<3)
					{
						alert("Debe ingresar al menos tres caracteres.");
						frm1.txtRazSoc.focus();
						return;
					}
				}
				frm1.hidTipPers.value="Jurídica";
				
			}
				
			document.frm1.hidCboOficinas.value=document.frm1.cboOficinas.value;
			}
			
		 }
		
		document.frm1.action="/iri/CertificadosIRI.do?state=guardarDatosBasicos";
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
	
	//la funcion isCharacter recibe 2 parametros, el str debe ser diferente de vacio y nulo
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
	
	function validar2()
	{
		if(isCharacter(frm1.txtNumMatricula.value,"%"))
		alert("si esta");
	}
	
	function certiVarios()
	{
		certiMas.style.visibility="visible";
		certiMas.style.display="";
		certi.style.visibility="hidden";
		certi.style.display="none";
	}
	function certiRMC()
	{
		certi.style.visibility="visible";
		certi.style.display="";
		certiMas.style.visibility="hidden";
		certiMas.style.display="none";
	}
	function mayuscula(objeto){
		objeto.value=objeto.value.toUpperCase();
	}
	
</script>
<script language="JavaScript">
<!--
	var startTime = new Date();

	// -->
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY onLoad="CambioCert()">
	<div id="maincontent">
		<div class="innertube">
			<form name="frm1" method="post"><br>
				<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Solicitud de Certificados Positivos / Negativos</b>
				<br>
				<table cellspacing=0>
			      <tr> 
			    	<td width="685" colspan="8" >
			    		<p align="left"><font color="#949400" size="2"><strong>DATOS BASICOS DE LA SOLICITUD</strong></font></p>
			    	</td>
			      </tr>
				</table>
				
				<table class="punteadoTablaTop" width="600">
				    <tr>
				        <td width="160">TIPO DE CERTIFICADO</td>
				        <td>
				        	<input type="hidden" name="hidGLA">
							<input type="hidden" name="hidArea">
							<select  name="cboTipoCert" size="1" onChange="CambioCert()">
								<option value="">&lt;&lt; Seleccione el Tipo de Certificado&gt;&gt;</option>
								<logic:iterate name="arrCertificados" id="item2" scope="request">
									<option value="<bean:write name="item2" property="codigo"/>">
									<bean:write name="item2" property="descripcion" /></option>
								</logic:iterate>
							</select>
							<input type="hidden" name="hidCert"> 
							<input type="hidden" name="hidTipo" value="N">
				        </td>
				    </tr>
			    </table>
						<div id="certiMas" style="visibility:visible;display:">
							<table class="punteadoTabla" width="600">
								<tr>
									<td width="150">
										TIPO DE PERSONA
									</td>
									<td>
										<input type="radio" name="radTipoPers" value="N" onClick="javascript:Natural();" checked>Persona Natural &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
										<input type="radio" name="radTipoPers" value="J" onClick="javascript:Juridica();">Persona Jur&iacute;dica
										<input type="hidden" name="hidTipPers" value="Natural">
									</td>
								</tr>
							</table>
						<table width="600">
							<tr>
								<td colspan="2" border="0">
									<div id="areaNatu" style="visibility:visible;display:">
										<table border="0">
											<tr>
												<td width="150">
													APELLIDOS Y NOMBRES<br>A nombre de quien saldrá el certificado
												</td>
												<td>
													<table border="0">
														<tr>
															<td>
																<input type="text" name="txtApPa" size="20"
																maxlength="50" style="width:133" onblur="sololet(this)">
															</td>
															<td><input type="text" name="txtApMa" size="20"
																maxlength="40" style="width:133" onblur="sololet(this)">
															</td>
															<td><input type="text" name="txtNom" size="20"
																maxlength="40" style="width:133" onblur="sololet(this)">
															</td>
														</tr>
														<tr>
															<td>&nbsp;Apellido Paterno</td>
															<td>&nbsp;Apellido Materno</td>
															<td>&nbsp;Nombres</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>
									<div id="areaJuri" style="visibility:hidden;display:none">
										<table>
											<tr>
												<td width="150">RAZON SOCIAL<br>A nombre de quien saldrá el certificado</td>
												<td><input type="text" name="txtRazSoc" size="60"
													maxlength="120" style="width:407" onblur="sololet(this)"></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
						
						<table class="punteadoTablaTop" width="600px">
						<tr> 
					      <td  align="left" width="150">Oficina Registral </td>
					      <td>
					      	<input type="hidden" name="hidCboOficinas"	value="00|00">
					        <select size="1" name="cboOficinas"
								onChange="document.frm1.hidOfic.value=document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text">
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
					      <td align="left">
					      	<input type="hidden" name="hidOfic" value="Lima"> 
							<a class="linkInicio" href="javascript:Abrir_Ventana('acceso/mapas/MAPA1.htm','Oficinas_Registrales','',500,600)"
								onmouseover="javascript:mensaje_status('Identifique su Oficina Resgistral');return true;"
								onmouseOut="javascript:mensaje_status(' ');return true;">
							Identifique su Oficina Registral </a>
						  </td>
					    </tr>
					    </table>
		    		</div>
					<div id="certi" style="visibility:hidden;display:none">
						<table class="punteadoTabla" width="600px">
							<tr>
								<td width="130">Placa(vehículos)</td>
								<td><input type="text" name="txtNumPlaca" maxlength="7" onblur="mayuscula(this)"/></td>
							</tr>
							<tr>
								<td>Nro. Matrícula(Embarcaciones pesqueras, buques y aeronaves)</td>
								<td><input type="text" name="txtNumMatricula" maxlength="35" onblur="mayuscula(this)" ></td>
							</tr>
							<tr>
								<td>Nombre(Embarcaciones pesqueras y buques)</td>
								<td><input type="text" name="txtNombreBien" maxlength="254" onblur="mayuscula(this)" ></td>
							</tr>
							<tr>
								<td>Nro. Serie(Motores, aeronaves y otros)</td>
								<td><input type="text" name="txtNumSerie" maxlength="35" onblur="mayuscula(this)" ></td>
							</tr>
						</table>
					</div>
					<table>
						<tr>
					        <td colspan="2">
					        	<input type="button" class="formbutton" value="Solicitar" onclick="javascript:Validar();" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
					        </td>
					    </tr>
					</table>
				<br>
			  </form>
			<br>
		</div>
	</div>
</BODY>
</HTML>
