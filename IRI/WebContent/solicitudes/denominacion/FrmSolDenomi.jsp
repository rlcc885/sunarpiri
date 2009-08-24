<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=iso-8859-1"%>
<%@page
	import="gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion"%>
<%@page
	import="gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica"%>
<%@page
	import="gob.pe.sunarp.extranet.solicitud.denominacion.beans.RazonSocial"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<title>Formulario Reserva de Preferencia Registral - Datos
Reserva</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">

<script language="JavaScript" src="javascript/util.js"></script>
<style type="text/css">
<!--
a:link {
	color: #900000;
}
-->
</style></head>
<script>
alert("Para evitar observaciones en tu solicitud, registra tus 5 nombres de Denominación y/o Razón Social.");
function cancelar(){
	document.frm1.action="/iri/Publicidad.do";
	document.frm1.submit();
}
function seleccionConstitucion(id){
	if(document.frm1.constitucion.checked) {
		if (document.getElementById){ //se obtiene el id
			var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
			el.style.display = (el.style.display == 'none') ? 'block' : 'none'; //damos un atributo display:none que oculta el div
		}
		document.frm1.modificacion.checked=false;
		document.getElementById('divModificacion').style.display = "none";
		document.frm1.nomModif.value="";
		document.frm1.partida.value="";
		document.frm1.ficha.value="";
	}
}
function muestra_ocultaDenominacion(id){
	if(document.frm1.razonSoc.checked) {
		if (document.getElementById){ //se obtiene el id
			var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
			el.style.display = (el.style.display == 'none') ? 'block' : 'none'; //damos un atributo display:none que oculta el div
		}
		document.frm1.denominacion.checked=false;
		document.frm1.denoAbre1.value="";
		document.frm1.denoAbre2.value="";
		document.frm1.denoAbre3.value="";
		document.frm1.denoAbre4.value="";
		document.frm1.denoAbre5.value="";
		document.frm1.deno1.value="";
		document.frm1.deno2.value="";
		document.frm1.deno3.value="";
		document.frm1.deno4.value="";
		document.frm1.deno5.value="";
    	document.getElementById('divDenominacion').style.display = "none";
	}
}
window.onload = function(){
muestra_ocultaDenominacion('divDenominacion');

}
function muestra_ocultaRazonSoc(id){
	if(document.frm1.denominacion.checked) {
		if (document.getElementById){ 
			var el = document.getElementById(id); 
			el.style.display = (el.style.display == 'none') ? 'block' : 'none'; 
		}
		document.frm1.razonSoc.checked=false;
		document.frm1.Raz1.value="";
		document.frm1.Raz2.value="";
		document.frm1.Raz3.value="";
		document.frm1.Raz4.value="";
		document.frm1.Raz5.value="";
		document.getElementById('divRazonSocial').style.display = "none";
	}
}
window.onload = function(){
muestra_ocultaRazonSoc('divRazonSocial');
}
function muestra_ocultaModificacion(id){
	if(document.frm1.modificacion.checked) {
		if (document.getElementById){ 
			var el = document.getElementById(id); 
			el.style.display = (el.style.display == 'none') ? 'block' : 'none';
		}
		document.frm1.constitucion.checked=false;
	}
}
window.onload = function(){
muestra_ocultaModificacion('divModificacion');
}
function validar(){
	if(!document.frm1.constitucion.checked && !document.frm1.modificacion.checked) {			
		alert("Seleccione si es constitución o modificaciòn");
	}else{
		if(!document.frm1.denominacion.checked && !document.frm1.razonSoc.checked) {			
			alert("Seleccione si ingresara la Denominación o la Razón Social");
		}else{
			if(document.frm1.deno1.value=="" && document.frm1.denominacion.checked && !document.frm1.razonSoc.checked ){
				alert("Ingrese una denominación como mínimo");
				document.frm1.deno1.focus();
				return;
			}else{
				if(document.frm1.cboTipoSociedad.value=="00" && document.frm1.denominacion.checked){
					alert("Seleccione el tipo de persona jurídica");
				}else{
					if (document.frm1.modificacion.checked  && document.frm1.denominacion.checked){
						if(document.frm1.nomModif.value==""){
							alert("Ingrese su denominación o razón social en caso de modificar estatutos")
							}else{
									if(document.frm1.cboDepartamento.value=="00" ){
										alert("yuju denominacion");
										alert("Seleccione el departamento");
									}else{
										if(document.frm1.cboProvincia.value=="00" ){
										alert("Seleccione la provincia");	
										}else{
											document.frm1.action="/iri/DenominacionPublico.do?state=muestraForm2";		
											document.frm1.submit();
										}
									}
							}
						}
					if (document.frm1.constitucion.checked && document.frm1.denominacion.checked){
						if(document.frm1.cboDepartamento.value=="00"){
							alert("Seleccione el departamento");
						}else{
							if(document.frm1.cboProvincia.value=="00"){
							alert("Seleccione la provincia");
							}else{

								document.frm1.action="/iri/DenominacionPublico.do?state=muestraForm2";		
								document.frm1.submit();
							}
						}
					}					
				}
			}
			if(document.frm1.Raz1.value=="" && document.frm1.razonSoc.checked && !document.frm1.denominacion.checked){
				alert("Ingrese una razón social como mínimo");
				//document.frm1.deno1.value="";
				//return ;
			}else{
				if(document.frm1.cboTipoSociedad.value=="00" && document.frm1.razonSoc.checked){
					
					alert("Seleccione el tipo de persona jurídica");
				}else{
					if (document.frm1.modificacion.checked && document.frm1.razonSoc.checked){
						if(document.frm1.nomModif.value==""){
							alert("Ingrese su denominación o razón social en caso de modificar estatutos")
							}else{
									if(document.frm1.cboDepartamento.value=="00"){
										alert("Seleccione el departamento");
									}else{
										if(document.frm1.cboProvincia.value=="00"){
										alert("Seleccione la provincia");	
										}else{
											document.frm1.action="/iri/DenominacionPublico.do?state=muestraForm2";		
											document.frm1.submit();
										}
									}
							}
						}
					if (document.frm1.constitucion.checked && document.frm1.razonSoc.checked){
						if(document.frm1.cboDepartamento.value=="00"){
							alert("Seleccione el departamento");
						}else{
							if(document.frm1.cboProvincia.value=="00"){
							alert("Seleccione la provincia");
							}else{

								document.frm1.action="/iri/DenominacionPublico.do?state=muestraForm2";		
								document.frm1.submit();
							}
						}
					}					
				}
			}
		}
	}
}
function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}
var ajax;

function funcionCallback()
{
	
	// Comprobamos si la peticion se ha completado (estado 4)
	if( ajax.readyState == 4 )
	{
		// Comprobamos si la respuesta ha sido correcta (resultado HTTP 200)
		
		if( ajax.status == 200 )
		{
			// Escribimos el resultado en la pagina HTML mediante DHTML
			
			document.all.salida.innerHTML = "<b>"+ajax.responseText+"</b>";	
		}
	}
	
}
function recuperaProvincia()
{
	
	if( window.XMLHttpRequest ){
		ajax = new XMLHttpRequest(); // No Internet Explorer
	}else{
		ajax = new ActiveXObject("Microsoft.XMLHTTP"); // Internet Explorer
	}
	ajax.onreadystatechange = funcionCallback;
	ajax.open( "GET", "/iri/solicitudes/denominacion/AjaxProvincias.jsp?cboDepartamento="+document.all.cboDepartamento.value, true );
	ajax.send( "" );
}
function alertaSA(){
	
	if	(document.frm1.cboTipoSociedad.value=="00815009"){
		alert("Si quieres reservar el nombre de una Sociedad Anónima Abierta o Cerrada, debes de consignar en tu nombre de Denominación y/o Razón social como SAC o SAA, segun corresponda.");
	}
}
</script>
<body>
<table cellspacing=0 class=titulo>
	<tr>
		<td><FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font
			color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font>
		Reserva de Preferencia Registral
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO
		1 DE 3 </FONT></td>
	</tr>
</table>
<a href="<%=request.getContextPath()%>/solicitudes/denominacion/pdf/ConPrac.pdf" target="_blank" >[Ver - Consejos]</a><br>
<form name="frm1" method="post" class="formulario">
<table class="tablasinestilo">
	<tr>
		<th colspan="5">RESERVA DE NOMBRE DE PERSONA JURIDICA PARA
		(seleccionar el que corresponda)</th>

	</tr>
	<tr>
		<td width="250"><input type="checkbox" name="constitucion"
			onClick="javascript:seleccionConstitucion('divModificacion')"
			value="01" checked="checked"><b>Constituci&oacute;n</b></td>
		<td width="250"><input type="checkbox" name="modificacion"
			onClick="javascript:muestra_ocultaModificacion('divModificacion')"
			value="02"><b>Modificaci&oacute;n de Estatuto</b></td>
		<td><b></b></td>

	</tr>
</table>
<table class="tablasinestilo">
	<tr>
		<th colspan="5"><strong>DENOMINACION o RAZON SOCIAL cuya
		reserva solicita</strong></th>
	</tr>
</table>
<table>
	<tr>
		<td width="250"><input type="checkbox" name="denominacion"
			onClick="javascript:muestra_ocultaRazonSoc('divDenominacion')"
			value="01"><b>Denominaci&oacute;n</b></td>
		<td width="326"><input type="checkbox" name="razonSoc"
			onclick="javascript:muestra_ocultaDenominacion('divRazonSocial')"
			value="02"><b>Raz&oacute;n Social</b></td>
	<tr>
</table>
<div id="divRazonSocial" style="display:none">
<table>

	<tr>
	<tr>
		<td>1.<input type="text" name="Raz1" style="width:210"
			onblur="sololet(this)"><font color="900000">*</font></td>
	</tr>
	<tr>
		<td>2.<input type="text" name="Raz2" style="width:210"
			onblur="sololet(this)"></td>
	</tr>
	<tr>
		<td>3.<input type="text" name="Raz3" style="width:210"
			onblur="sololet(this)"></td>
	</tr>
	<tr>
		<td>4.<input type="text" name="Raz4" style="width:210"
			onblur="sololet(this)"></td>
	</tr>
	<tr>
		<td>5.<input type="text" name="Raz5" style="width:210"
			onblur="sololet(this)"></td>
	</tr>
	
</table>
</div>
<div id="divDenominacion" style="display:none">
<table>
	<tr>
		<td align="right" colspan="2">Denominaci&oacute;n Abreviada.</td>
	<tr>
		<td>1.<input type="text" name="deno1" style="width:210"
			onblur="sololet(this)"><font color="900000">*</font></td>
		<td>&nbsp;&nbsp;1.<input type="text" name="denoAbre1"
			style="width:210" onBlur="sololet(this)"></td>
	</tr>
	<tr>
		<td>2.<input type="text" name="deno2" style="width:210"
			onblur="sololet(this)"></td>
		<td>&nbsp;&nbsp;2.<input type="text" name="denoAbre2"
			style="width:210" onBlur="sololet(this)"></td>
	</tr>

	<tr>
		<td>3.<input type="text" name="deno3" style="width:210"
			onblur="sololet(this)"></td>
		<td>&nbsp;&nbsp;3.<input type="text" name="denoAbre3"
			style="width:210" onBlur="sololet(this)"></td>
	</tr>
	<tr>
		<td>4.<input type="text" name="deno4" style="width:210"
			onblur="sololet(this)"></td>
		<td>&nbsp;&nbsp;4.<input type="text" name="denoAbre4"
			style="width:210" onBlur="sololet(this)"></td>
	</tr>
	<tr>
		<td>5.<input type="text" name="deno5" style="width:210"
			onblur="sololet(this)"></td>
		<td>&nbsp;&nbsp;5.<input type="text" name="denoAbre5"
			style="width:210" onBlur="sololet(this)"></td>
	</tr>

	
</table>
</div>
<table class="tablasinestilo">
	<tr>
		<th colspan="5"><strong>TIPO DE PERSONA JURIDICA</strong></th>
	</tr>
	<tr>
		<td width="517">&nbsp;<select size="1" name="cboTipoSociedad"
			style="width:400" onchange="alertaSA()">
			<option value="00">Seleccionar</option>
			<option value="00816002">ASOCIACIONES</option>
			<option value="00817003">FUNDACIONES</option>
			<option value="00818004">COMITES</option>
			<option value="00819005">COMUNIDADES CAMPESINAS Y NATIVAS</option>
			<option value="00820006">COOPERATIVAS</option>
			<option value="00822007">EMPRESAS DE PROPIEDAD SOCIAL</option>
			<option value="00823008">EMPRESAS DE DERECHO PUBLICO</option>
			<option value="00815009">SOCIEDADES ANONIMAS</option>
			<option value="00824010">SOCIEDADES COMERCIALES DE
			RESPONSABILIDAD LIMITADA</option>
			<option value="00825011">SOCIEDADES CIVILES</option>
			<option value="00826012">SOCIEDADES MERCANTILES/COLECTIVAS</option>
			<option value="00827013">SOCIEDADES MERCANTILES/EN COMANDITA
			SIMPLE</option>
			<option value="00828015">EMPRESAS INDIVIDUALES DE
			RESPONSABILIDAD LIMITADA</option>
			<option value="03189016">SOCIEDADES MERCANTILES/SUCURSALES</option>
			<option value="00829024">ORGANIZACIONES SOCIALES DE BASE</option>
			<option value="00830025">SOCIEDADES MERCANTILES/EN COMANDITA
			POR ACCION</option>
			<option value="05856057">MINERO PARTICULARES</option>
			<option value="05898058">SOCIEDADES LEGALES</option>
		</select><font color="900000">*</font></td>
	<tr>
</table>
<div id="divModificacion" style="display:none">
<table class="tablasinestilo">
	<tr>
		<th colspan="5"><strong>NOMBRE DE LA PERSONA JURIDICA EN
		CASO DE MODIFICACION DE ESTATUTOS</strong></th>
	</tr>
	<tr>
	<tr>
		<td colspan="2">Denominaci&oacute;n o Raz&oacute;n Social: <br>
		&nbsp;<input type="text" name="nomModif" size="89"
			onblur="sololet(this)"><font color="900000">*</font></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Partida:<br>
		&nbsp;<input type="text" name="partida" style="width:210"
			onkeypress="return soloNumero(event)"></td>
		<td>Ficha: <br>
		&nbsp;<input type="text" name="ficha" style="width:210"
			onkeypress="return soloNumero(event)"></td>
	</tr>
	<td width="150"></td>
	<b></b>
	<td width="65" colspan="2">&nbsp;</td>

	<td width="65" colspan="2">&nbsp;</td>

</table>
</div>
<table class="tablasinestilo">
	<tr>
		<th colspan="5"><strong>DOMICILIO DE LA PERSONA JURIDICA</strong></th>
	</tr>
	<tr>
	<tr>
		<td>Departamento &nbsp;<select size="1" name="cboDepartamento"
			style="width:210" onChange="recuperaProvincia()">

			<option value="00">Seleccionar</option>
			<option value="01">Amazonas</option>
			<option value="02">Ancash</option>
			<option value="03">Apurimac</option>
			<option value="04">Arequipa</option>
			<option value="05">Ayacucho</option>
			<option value="06">Cajamarca</option>
			<option value="08">Cusco</option>
			<option value="09">Huancavelica</option>
			<option value="10">Huanuco</option>
			<option value="11">Ica</option>
			<option value="12">Junin</option>
			<option value="13">La Libertad</option>
			<option value="14">Lambayeque</option>
			<option value="15">Lima</option>
			<option value="16">Loreto</option>
			<option value="17">Madre de Dios</option>
			<option value="18">Moquegua</option>
			<option value="19">Pasco</option>
			<option value="20">Piura</option>
			<option value="21">Puno</option>
			<option value="22">San Martin</option>
			<option value="23">Tacna</option>
			<option value="24">Tumbes</option>
			<option value="25">Ucayali</option>

		</select><font color="900000">*</font></td>
		<td>&nbsp;&nbsp;Provincia &nbsp; <span id="salida"></span> <font
			color="900000">*</font></td>
	</tr>
	<tr></tr>
	<tr>
		<td colspan=6 align="right"><A href="javascript:cancelar();"
			onMouseOver="javascript:mensaje_status('Grabar Vehiculo');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"> <IMG
			src="images/btn_cancelar2.gif" border="0"> </A> <A
			href="javascript:validar();"
			onMouseOver="javascript:mensaje_status('Cancelar');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"> <IMG
			src="images/btn_continuar.gif" border="0"> </A></td>
	</tr>
</table>
</form>
<table>
	<tr>
		<td><strong><font color="900000">* Los datos son
		obligatorios</font></strong></td>
	</tr>
</table>
<br>

<table>
	<tr>
		<td><font color="900000"><strong>Estimado
		solicitante, UD. podrá llenar desde 01 hasta 05 denominaciones o
		razones sociales.<BR>
		Solo se concederá la reserva de una de ellas, en orden excluyente, y
		sólo si resulta procedente de la<br>
		calificación registral. </strong></font></td>
	</tr>
</table>
<script LANGUAGE="JavaScript">
	<%if(request.getAttribute("regresa")!=null && !request.getAttribute("regresa").equals("")){%>
<% 
   	Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");
	Juridica juri =  deno.getPersonaJuridica();
	RazonSocial raz = new RazonSocial();
	ArrayList array = deno.getListaDenominaciones();
	
%>
	<%if ("01".equals(deno.getIndicadorSeleccion())){%>
		document.frm1.constitucion.checked=true; 
	<%}%>
	<%if ("02".equals(deno.getIndicadorSeleccion())){%>
		document.frm1.modificacion.checked=true; 
	<%}%>
	<% if ("01".equals(deno.getInidcadorDenominacion())){%>
		document.frm1.denominacion.checked=true; 
		document.getElementById("divDenominacion").style.display = "block";
	<%}%>
	<%if ("02".equals(deno.getInidcadorDenominacion())){%>
		document.frm1.razonSoc.checked=true; 
		document.getElementById("divRazonSocial").style.display = "block";
	<%}%>
	<%if ( (juri.getRazonSocial()!=null) && (!juri.getRazonSocial().equals(""))){%>
		document.frm1.nomModif.value="<%= juri.getRazonSocial()%> ";
	<%}%>
	<%if ( (juri.getPartida()!=null) && (!juri.getPartida().equals(""))){%>
		document.frm1.partida.value="<%= juri.getPartida()%> ";
	<%}%>
	<%if ( (juri.getFicha()!=null) && (!juri.getFicha().equals(""))){%>
		document.frm1.ficha.value="<%= juri.getFicha()%> ";
	<%}%>
	<%if ( (juri.getDepartamento()!=null) && (!juri.getDepartamento().equals(""))){%>
		doCambiaCombo(document.frm1.cboDepartamento, "<%=juri.getDepartamento()%>");
		document.frm1.cboDepartamento.value="<%=juri.getDepartamento()%>";
		recuperaProvincia();
		doCambiaCombo(document.frm1.cboProvincia, "<%=juri.getDepartamento()+juri.getProvincia()%>");
	<%}%>
	<%if ( (deno.getCodigoActo()!=null) && (deno.getCodigoLibro()!=null)){%>
		doCambiaCombo(document.frm1.cboTipoSociedad, "<%=deno.getCodigoActo()%><%=deno.getCodigoLibro()%>");
	<%}%>
	
	<% if (array.size()>0){%>
		
		<%for (int i=0;i<array.size();i++){ %>
			<%raz = (RazonSocial)array.get(i);%>
			<% if(deno.getInidcadorDenominacion().equals("01")){ %> 
			document.frm1.deno<%=i+1%>.value="<%= raz.getDenominacion()%>";
			document.frm1.denoAbre<%=i+1%>.value="<%= raz.getDenoAbrev()%>";
			<%}else{%>
			document.frm1.Raz<%=i+1%>.value="<%= raz.getDenominacion()%>";
			<%}%> 
		<%}%>
	
	<%}%>
<%}%>
</script>
</body>
</html>
