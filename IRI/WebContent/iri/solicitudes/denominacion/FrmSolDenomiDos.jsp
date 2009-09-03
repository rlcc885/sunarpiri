<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@page import="gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.pe.sunarp.extranet.solicitud.denominacion.beans.Participantes"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<html>
<head>
	<title>Formulario Reserva de Preferencia Registral - Datos Reserva</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">

<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
	 <script language="JavaScript" src="javascript/util.js"></script>
</head> 
<script>
function validar(){
	
	<% Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion"); %>
	<% ArrayList listaPart = deno.getListaParticipantes(); %>
	
	<% //if ((listaPart==null) || (listaPart.size()==0) ){%>
	<% if ((listaPart==null) && deno.getIndicadorSeleccion().equals("01") ){%>
		alert("Por lo menos debe de ingresar un integrante de persona jurídica. Para ingresar integrantes, hacer clic en el boton nuevo.");
	<%} else { %>
	if(document.frm1.apePaterno.value==""){
		alert("Ingrese el apellido paterno");
	}else{
		if(document.frm1.apeMaterno.value==""){
			alert("Ingrese el apellido materno");
		}else{
			if(document.frm1.nombres.value==""){
				alert("Ingrese el nombre");
			}else{
				if(document.frm1.cboTipoDocu.value=="00"){
					alert("Seleccione el tipo de documento");
				}else{
					if(document.frm1.numDocu.value==""){
						alert("Ingrese el numero de documento");
					}else{
						if(document.frm1.cboParticipacion.value=="00"){
							alert("Seleccione el tipo de participacion")
						}else{
							if(document.frm1.email.value==""){
								alert("Ingrese su correo electorico");
							}else{
								var answer = confirm("Verifique que los datos de los Integrantes de la Persona Jurídica son Correctos. Si estos son correctos de clic en OK para continuar.")
								if (answer){
									document.frm1.action="/iri/DenominacionIRI.do?state=comprobante";		
									document.frm1.submit();
								}
								
																
							}
						}
					}
				}
			}
		}
	}<%}%>
}
function validarEmail(cadena) {
	var a = cadena.value;
	var filter=/^[A-Za-z_.][A-Za-z0-9_.]*@[A-Za-z0-9_]+.[A-Za-z0-9_.]+[A-za-z]$/;
	if (a.length == 0 )
	return true;
	if (filter.test(a))
	return true;
	else
	alert("Por favor, debe ingresar una dirección de correo válida");
	cadena.focus();
	return false;
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

function nuevoParticipante(){
	document.frm1.action="/iri/DenominacionIRI.do?state=nuevoParticipante";		
	document.frm1.submit();
}
function Borrar(){
	<% if ((listaPart==null) && deno.getIndicadorSeleccion().equals("01") ){%>
		alert("Por lo menos debe de ingresar un participante para poder borrarlo.");
	<%} else { %>
	document.frm1.action="/iri/DenominacionIRI.do?state=borrarParticipante";		
	document.frm1.submit();
	<%}%>
}
function Regresar(){
	document.frm1.action="/iri/DenominacionIRI.do?state=regresarMuestraForm";		
	document.frm1.submit();
}
</script>
<body>
<div id="maincontent">
		<div class="innertube">
<b><font color="#949400">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="666666"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Reserva de Preferencia Registral
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 2 DE 3
			</FONT></b>
			
	<br>
	<form name="frm1" method="post" >
	<table class="punteadoTablaTop" >
	<logic:equal name="solicitudDenominacion" property="indicadorSeleccion" value="01">

	<tr>
		<td colspan="5" align="left"><b><font color="#949400">NOMBRE DE LOS INTEGRANTES DE LA PERSONA JURIDICA</font></b></td>
	</tr>
	<tr>
		<td colspan="5">
			<table border="0" class="punteadoTablaTop" class="grilla" cellspacing="0" width="100%">
				
		    	<tr>
		      		<td width="2%" align="center" height="12"><div align="center"><input type="checkbox" name="todos" onclick="javascript:seleccionaTodos()"></div></td>
		      		<td width="2%" align="center"><div align="center">NRO.</div></td>
		      		<td align="center" height="12"><div align="center">APELLIDO PATERNO/DENOMINACION/RAZON SOCIAL</div></td>
		      		<td align="center" height="12"><div align="center">APELLIDO MATERNO</div></td>
		      		<td align="center" height="12"><div align="center">NOMBRES</div></td>
		      		<td align="center" height="12"><div align="center">TIPO DOC</div></td>
		      		<td align="center" height="12"><div align="center">NUM DOC</div></td>
		      		
		    	</tr>
		    	
		    	<%if ( (request.getAttribute("indicaParticipantes")!=null) && (!request.getAttribute("indicaParticipantes").equals("")) ){ %>
				<%
				Denominacion denominacion = (Denominacion)session.getAttribute("solicitudDenominacion");
				
				ArrayList listaParticipantes = null;
				listaParticipantes = denominacion.getListaParticipantes();
					
					if(listaParticipantes!=null && listaParticipantes.size()>0){
						for (int i=0; i<listaParticipantes.size(); i++) {
						Participantes participante = (Participantes)listaParticipantes.get(i);
						if (!"RUC".equals(participante.getTipoDocu())){
					%>
					<tr>
			      		<td align="center"><input type="checkbox" name="indicesListaParticipante" value="<%=i+1%>"></td>
			      		<td align="center"><%= i + 1 %></td>
			      		<td><%=participante.getApePaterno()%></td>
			      		<td><%=participante.getApeMaterno()%></td>
			      		<td><%=participante.getNombre()%></td>
			      		<td><%=participante.getDescDocu()%></td>		      		
			      		<td><%=participante.getNumDocu()%></td>		      		
			    	</tr>
			    		<%}%>
			    		<%if ("RUC".equals(participante.getTipoDocu())){%>
			    		<tr>
			      		<td align="center"><input type="checkbox" name="indicesListaParticipante" value="<%=i+1%>"></td>
			      		<td align="center"><%= i + 1 %></td>
			      		<td><%=participante.getRazonSocial()%></td>
			      		<td>--</td>
			      		<td>--</td>
			      		<td><%=participante.getTipoDocu()%></td>		      		
			      		<td><%=participante.getNumDocu()%></td>		      		
			    	</tr>	
			    		<%}%>
			    	<%}%>
		    	<%} } else{ %>
		    	
		    	<tr>
		      		<td align="center">--</td>
		      		<td align="center">--</td>
		      		<td>--</td>
		      		<td>--</td>
		      		<td>--</td>
		      		<td>--</td>		      		
		      		<td>--</td>		      		
		    	</tr>
			 	<%} %>
			 	
			</table>
			
		</td>
	</tr>			
	<tr>
		<td colspan="5" align="center">
			<input class="formbutton" type="button" value="Nuevo"  onclick="javascript:nuevoParticipante();" />
			<input class="formbutton" type="button" value="Borrar"  onclick="javascript:Borrar();" />
				
		</td>
	</tr>
    </logic:equal>
	<tr>
		<td colspan="5" align="center"><b>
		<font color="#949400">DATOS DEL SOLICITANTE</font> (Presentante)</b></td>
	</tr>
  		<tr>
  		<td>Apellido Paterno:<br> &nbsp;<input type="text" name="apePaterno"  style="width:210" onblur="sololet(this)"><font color="#949400">*</font></td>
  		<td>Apellido Materno:<br> &nbsp;<input type="text" name="apeMaterno"  style="width:210" onblur="sololet(this)">
  		<font color="#949400">*</font></td>
  		<td width="278">Nombres:<br> &nbsp;<input type="text" name="nombres"  style="width:210" onblur="sololet(this)">
  		<font color="#949400">*</font></td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  		</tr>
	    <tr>
	    <td>Tipo Documento: <br>
		 <select size="0" name="cboTipoDocu">
			<option value="00">Seleccionar</option>
			<option value="09">DOCUMENTO NACIONAL DE INDENTIDAD</option>
			<option value="04">CARNET DE IDENTIDAD</option>
			<option value="08">PASAPORTE</option>
			<option value="03">CARNET DE EXTRANJERIA</option>
			<option value="05">REGISTRO UNICO DEL CONTRIBUYENTE</option>
			<option value="07">COLEGIO DE ABOGADOS DE LIMA C.A.L</option>           
		</select>
		<font color="#949400">*</font></td>
		<td>N&uacute;mero: <br><input type="text" name="numDocu" maxlength="8" style="width:210" ><font
			color="#949400">*</font></td>
			<td>Participaci&oacute;n:<br>
		<select size="1" name="cboParticipacion">
			<option value="00">Seleccionar</option>
			<option value="1">TITULAR</option>
			<option value="2">SOCIO</option>
			<option value="3">ABOGADO</option>
			<option value="4">NOTARIO</option>
			<logic:equal name="solicitudDenominacion" property="indicadorSeleccion" value="02">
			<option value="4">REPRESENTANTE</option>
			</logic:equal>
		</select><font color="#949400">*</font></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		</tr>
	    <tr><td>Correo Electr&oacute;nico: <br><input type="text" name="email" onblur="validarEmail(this)" style="width:210"  onblur="sololet(this)"><font
			color="#949400">*</font></td><td colspan="2">Direcci&oacute;n Completa:<br>
		&nbsp;<input type="text" name="direccion" size="78" onblur="sololet(this)"></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
		</tr>
    	<tr>
		<td colspan="5" ><strong><font color="#949400">DATOS DE PAGO</font></strong></td>
	</tr>
   		<tr>
	    		<td>Monto:</td><td>S./ <bean:write name="solicitudDenominacion" property="monto"/>0 nuevos soles.</td>
	    		<td>&nbsp;</td>
	    		<td>&nbsp;</td>
	    		<td>&nbsp;</td>
	    		</tr>
	    
	   	    		<logic:equal name="solicitudDenominacion" property="indicadorRegistrado" value="N">
       <tr><td colspan="3">Con el formato de presentaci&oacute;n que aparecer&aacute; al presionar el boton continuar, apers&oacute;nese a la Oficina Registral para presentarlo y efectuar el pago correspondiente</td></tr>
		         </logic:equal>
	    		
	<tr>        	
    	
    		<td align="right" colspan="5">
    		
    		<input class="formbutton" type="button" value="Regresar"  onclick="javascript:Regresar();" />
    			<logic:equal name="solicitudDenominacion" property="indicadorRegistrado" value="N">
			<input class="formbutton" type="button" value="Continuar"  onclick="javascript:validar();" />
    			</logic:equal>
    			<logic:equal name="solicitudDenominacion" property="indicadorRegistrado" value="S">
  		   <input class="formbutton" type="button" value="Validar"  onclick="javascript:validar();" />
    			</logic:equal>
    		</td>
    </tr>	
  	</table>
	</form>
	<table>
	<tr>
		<td><strong><font color="#949400">* Los datos son obligatorios</font></strong></td>
	</tr>
	</table>
	<br>
	<table>
	<tr>
	    <td><font color="#949400">
	       <strong>Las notificaciones derivadas del trámite de atención en línea de la solicitud de reserva se efectuarán<br>
		al correo electrónico ingresado anteriormente.
            </strong></font>
	    </td>
	</tr>
	</table> 
	<br>
    <br>
<br>
</html>