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
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
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
									document.frm1.action="/iri/Denominacion.do?state=comprobante";		
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
	document.frm1.action="/iri/Denominacion.do?state=nuevoParticipante";		
	document.frm1.submit();
}
function Borrar(){
	<% if ((listaPart==null) && deno.getIndicadorSeleccion().equals("01") ){%>
		alert("Por lo menos debe de ingresar un participante para poder borrarlo.");
	<%} else { %>
	document.frm1.action="/iri/Denominacion.do?state=borrarParticipante";		
	document.frm1.submit();
	<%}%>
}
function Regresar(){
	document.frm1.action="/iri/Denominacion.do?state=regresarMuestraForm";		
	document.frm1.submit();
}
</script>
<body>
	<br>
	<table cellspacing=0 class=titulo>
		<tr>
			<td><FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Reserva de Preferencia Registral
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 2 DE 3
			</FONT>
			</td>
  		</tr> 
	</table>
	<br>
	<form name="frm1" method="post" class="formulario">
	<table class="tablasinestilo">
	<logic:equal name="solicitudDenominacion" property="indicadorSeleccion" value="01">

	<tr>
		<th colspan="5" ><strong>NOMBRE DE LOS INTEGRANTES DE LA PERSONA JURIDICA</strong></th>
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
		      		<th align="center" height="12"><div align="center">APELLIDO PATERNO/DENOMINACION/RAZON SOCIAL</div></th>
		      		<th align="center" height="12"><div align="center">APELLIDO MATERNO</div></th>
		      		<th align="center" height="12"><div align="center">NOMBRES</div></th>
		      		<th align="center" height="12"><div align="center">TIPO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NUM DOC</div></th>
		      		
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
									<A href="javascript:nuevoParticipante();" >
										<IMG src="images/btn_nuevo.gif" border="0">
									</A>
								</td>
								<td>
									<A href="javascript:Borrar();" >
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
    </tr>	  		</logic:equal>
	<tr>
		<th colspan="5" ><strong>DATOS DEL SOLICITANTE</strong> (Presentante)</th>
	</tr>
    	<td colspan="5">
			<tr>
	    		<tr><td>Apellido Paterno:<br> &nbsp;<input type="text" name="apePaterno"  style="width:210" onblur="sololet(this)"><font color="900000">*</font></td><td>Apellido Materno: &nbsp;<input type="text" name="apeMaterno"  style="width:210" onblur="sololet(this)"><font
			color="900000">*</font></td><td width="278">Nombres: &nbsp;<input type="text" name="nombres"  style="width:210" onblur="sololet(this)"><font
			color="900000">*</font></td></tr>
	    		<tr><td>Tipo Documento: <br>&nbsp;
		 <select size="0" name="cboTipoDocu" width="210">
			<option value="00">Seleccionar</option>
			<option value="09">DOCUMENTO NACIONAL DE INDENTIDAD</option>
			<option value="04">CARNET DE IDENTIDAD</option>
			<option value="08">PASAPORTE</option>
			<option value="03">CARNET DE EXTRANJERIA</option>
			<option value="05">REGISTRO UNICO DEL CONTRIBUYENTE</option>
			<option value="07">COLEGIO DE ABOGADOS DE LIMA C.A.L</option>           
		</select>
		<font color="900000">*</font></td><td>N&uacute;mero: &nbsp;<input type="text" name="numDocu" maxlength="8" style="width:210" ><font
			color="900000">*</font></td><td>Participaci&oacute;n:<br>
		<select size="1" name="cboParticipacion">
			<option value="00">Seleccionar</option>
			<option value="1">TITULAR</option>
			<option value="2">SOCIO</option>
			<option value="3">ABOGADO</option>
			<option value="4">NOTARIO</option>
			<logic:equal name="solicitudDenominacion" property="indicadorSeleccion" value="02">
			<option value="4">REPRESENTANTE</option>
			</logic:equal>
		</select><font color="900000">*</font></td></tr>
	    		<tr><td>Correo Electr&oacute;nico: &nbsp;<input type="text" name="email" onblur="validarEmail(this)" style="width:210"  onblur="sololet(this)"><font
			color="900000">*</font></td><td colspan="2">Direcci&oacute;n Completa:<br>
		&nbsp;<input type="text" name="direccion" size="78" onblur="sololet(this)"></td><td width="278"></td></tr>
	    		<td width="150">
			</td>
	      		<td><b></b></td>
	        	<td width="65" colspan="2">&nbsp;</td>
	        	<td width="65" colspan="2">&nbsp;</td>
	    	
    	<tr>
		<th colspan="5" ><strong>DATOS DE PAGO</strong></th>
	</tr>
    	<td colspan="5" >
			<tr>
	    		<tr><td>Monto:</td><td>S./ <bean:write name="solicitudDenominacion" property="monto"/>0 nuevos soles.</td><td></td></tr>
	    		<tr><td></td></tr>
	    		<tr><td colspan="3"></td></tr>
	    		<logic:equal name="solicitudDenominacion" property="indicadorRegistrado" value="N">
		    		<tr><td colspan="3">Con el formato de presentaci&oacute;n que aparecer&aacute; al presionar el boton continuar, apers&oacute;nese a la Oficina Registral para presentarlo y efectuar el pago correspondiente</td></tr>
		         </logic:equal>
	    		<td width="150">
			</td>
	      		<td><b></b></td>
	        	<td width="65" colspan="2">&nbsp;</td>
	        	<td width="65" colspan="2">&nbsp;</td>
	        	
    	
    		<td align="right" colspan="5">
    		    <A href="javascript:Regresar();" >
    		    	<IMG src="images/btn_regresa.gif" border="0">
    			</A>
    			<logic:equal name="solicitudDenominacion" property="indicadorRegistrado" value="N">
    			<A href="javascript:validar();" >
    				<IMG src="images/btn_continuar.gif" border="0">
    			</A>
    			</logic:equal>
    			<logic:equal name="solicitudDenominacion" property="indicadorRegistrado" value="S">
    			<A href="javascript:validar();" >
    				<IMG src="images/btn_pagar.gif" border="0">
    			</A>
    			</logic:equal>
    		</td>
    	
  	</table>
	</form>
	<table>
	<tr>
		<td><strong><font color="900000">* Los datos son obligatorios</font></strong></td>
	</tr>
	</table>
	<br>
	<table>
	<tr>
	    <td>
	       <font color="900000"><strong>Las notificaciones derivadas del trámite de atención en línea de la solicitud de reserva se efectuarán<br>
		al correo electrónico ingresado anteriormente.
            </strong></font>
	    </td>
	</tr>
	</table> 
	<br>
    <br>
<br>
</html>