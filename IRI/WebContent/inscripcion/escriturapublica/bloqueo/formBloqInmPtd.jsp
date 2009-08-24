<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=ISO-8859-1"%>


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*" %>

<html>
<head>
	<head>
	 <title>Formulario Bloqueo Inmueble - Nueva Partida</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
</head>
<script language="JavaScript" src="javascript/util.js"></script>
<script>
function Cancelar(){
	document.frm1.action="/iri/BloqueoInmueble.do?state=regresarABloqueoInmueble";
	document.frm1.submit();
}
function Validar(){
	
	if(document.frm1.rbPartida[0].checked == true){
	
		if (esVacio(document.frm1.txtPartida.value) )
			{
				alert("Debe ingresar el Numero de Partida");
				document.frm1.txtPartida.focus();
				return
			}

		document.frm1.cboOficinas.disabled=false;
		document.frm1.txtPartida.disabled=false;
	
		document.frm1.action="/iri/BloqueoInmueble.do?state=agregarPartida";
		document.frm1.submit();
					
	} else if(document.frm1.rbPartida[1].checked == true){
		if (esVacio(document.frm1.partidaDesde.value))
		{
			alert("Debe ingresar el numero de partida inicial");
			document.frm1.partidaDesde.focus();
			return
		}
		if (esVacio(document.frm1.partidaHasta.value))
		{
			alert("Debe ingresar el numero de partida Final");
			document.frm1.partidaHasta.focus();
			return
		}
		
		document.frm1.action="/iri/BloqueoInmueble.do?state=llenaPartidaRango";
		document.frm1.submit();

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
function mostrarDatos(){

}
function completaPartida(){

	if (esVacio(document.frm1.txtPartida.value))
	{
		//alert("Debe ingresar el numero de placa");
		//document.frm1.txtSolLugar.focus();
		return
	}
	
	document.frm1.action="/iri/BloqueoInmueble.do?state=llenaPartida";
	document.frm1.submit();
	
}

</script>
<body>
<br>
	<table cellspacing=0 class=titulo>
  		<tr>
			<td>
				<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Bloqueo de Propiedad Inmbueble</FONT>
			</td>
  		</tr>
	</table>
	<br>
	<form name="frm1" method="POST" class="formulario">
  	<% if (request.getAttribute("indice")!=null) { %>
	<input type="hidden" name="hidIndiceMod2" value="<%=request.getAttribute("indice")%>">
	<% } else  {%>
	<input type="hidden" name="hidIndiceMod2" value="">
  	<% } %>
  	<table class=tablasinestilo>
    	<tr>
      		<th colspan="4"><strong>DATOS DE PARTIDA</strong></th>
    	</tr>
		<tr>
			<td>&nbsp;</td>
      		<td width="30%" colspan="2"><input type="radio" name="rbPartida" value="numeroPartida" checked><strong>POR PARTIDA</strong></td>
      		<td width="70%"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
      		<td width="25%"><strong>NUMERO PARTIDA <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="txtPartida" size="20" maxlength="130" style="width:180" <%if (request.getAttribute("indice")!=null) { %> disabled <% } %> onblur="sololet(this);completaPartida()"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td width="25%">
	          <strong>OFICINA REGISTRAL <font color="900000">*</font></strong>
	        </td>
	        <td width="75%">
	      		<select size="1" name="cboOficinas" onChange="frm1.hidOfic.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text" style="width:180"  disabled >
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
	        
	    </tr>
    	<tr>
			<td>&nbsp;</td>    	
      		<td>&nbsp;</td>
      		<td width="25%"><strong>FICHA</strong></td>
      		<td width="70%"><input type="text" name="txtFicha" size="20" maxlength="130" style="width:180" onBlur="sololet(this)" readonly></td>
    	</tr>			
    	<tr>
			<td>&nbsp;</td>    	
      		<td>&nbsp;</td>
      		<td width="25%"><strong>TOMO</strong></td>
      		<td width="70%"><input type="text" name="txtTomo" size="20" maxlength="130" style="width:180" onBlur="sololet(this)" readonly></td>
    	</tr>
    	<tr>
			<td>&nbsp;</td>    	
      		<td>&nbsp;</td>
      		<td width="25%"><strong>FOJA</strong></td>
      		<td width="70%"><input type="text" name="txtFoja" size="20" maxlength="130" style="width:180" onBlur="sololet(this)" readonly></td>
    	</tr>
    	<tr>
			<td>&nbsp;</td>    	
      		<td>&nbsp;</td>
      		<td width="25%"><strong>DISTRITO</strong></td>
      		<td width="70%"><input type="text" name="txtDistrito" size="20" maxlength="130" style="width:180" onBlur="sololet(this)" readonly></td>
	    </tr>
		<tr>
			<td>&nbsp;</td>
      		<td width="30%" colspan="2"><input type="radio" name="rbPartida" value="rangoPartida"><strong>POR RANGO DE PARTIDAS</strong></td>
      		<td width="70%"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
      		<td width="25%"><strong>DESDE <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="partidaDesde" size="20" maxlength="130" style="width:180" onBlur="sololet(this)"></td>
		</tr>		
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
      		<td width="25%"><strong>HASTA <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="partidaHasta" size="20" maxlength="130" style="width:180" onBlur="sololet(this)"></td>
		</tr>	    	
    	<tr>
      		<td colspan="4">&nbsp;</td>
    	</tr>
    	<tr>
    		<td colspan="4" width="100%" align="center">
    		    <A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Grabar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
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
<script LANGUAGE="JavaScript">
	//llenaComboHijo();
	window.top.frames[0].location.reload();
</script>
<script LANGUAGE="JavaScript">
	doCambiaCombo(document.frm1.cboOficinas, "<%=request.getAttribute("regPubId")%>|<%=request.getAttribute("oficRegId")%>");
	<% if (request.getAttribute("numeroPartida")!=null){ %>
		document.frm1.txtPartida.value = "<%=request.getAttribute("numeroPartida")%>";
	<% } %>
	<% if (request.getAttribute("Ficha")!=null) {%>
		document.frm1.txtFicha.value = "<%=request.getAttribute("Ficha")%>";
	<% } %>
	<% if (request.getAttribute("Tomo")!=null) {%>
		document.frm1.txtTomo.value = "<%=request.getAttribute("Tomo")%>";
	<% } %>
	<% if (request.getAttribute("Foja")!=null) {%>
		document.frm1.txtFoja.value = "<%=request.getAttribute("Foja")%>";
	<% } %>
	<% if (request.getAttribute("Distrito")!=null) {%>
		document.frm1.txtDistrito.value = "<%=request.getAttribute("Distrito")%>";
	<% } %>
</script>
</html>

