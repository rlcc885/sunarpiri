<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<html>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>
<script LANGUAGE="JavaScript">
function Validar(){
	if (esVacio(document.frm1.txtPlaca.value))
	{
		alert("Debe ingresar el numero de placa");
		document.frm1.txtPlaca.focus();
		return
	}
	
	<% SolicitudInscripcion solic = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
	   
	   ArrayList listaVehiculos3 = solic.getVehiculos();

	   if (listaVehiculos3!=null) {   
		   Vehiculo vehiculo3 = (Vehiculo)listaVehiculos3.get(Integer.parseInt((String)request.getAttribute("indiceVehiculo")));
	   
		   java.util.ArrayList lista1 = (java.util.ArrayList)vehiculo3.getCompradoresPersonaNatural();
		   java.util.ArrayList lista2 = (java.util.ArrayList)vehiculo3.getCompradoresPersonaJuridica();
	   
		   int si1=0;
		   int si2=0;
		   	if (lista1!=null)
		   		si1 = lista1.size();
			if (lista2!=null)
		   		si2 = lista2.size();	   
		   	   		   
   		    if  ( (si1==0) && (si2==0) ) {	
    %>
				alert("Debe ingresar por lo menos un Comprador");
				return

	<%      }
	
	  } else { %>
	
			alert("Debe ingresar por lo menos un Comprador");
			return
	
	<% } %>

	
	
		
	document.frm1.action="/iri/TransferenciaVehicular.do?state=agregarVehiculo";		
	document.frm1.submit();
}
function Nuevo(){

	document.frm1.action="/iri/TransferenciaVehicular.do?state=obtenerNuevoParticipante";
	document.frm1.submit();
	   
}
function Borrar(){
	if (confirm("Esta seguro de Borrar el o los registro(s) seleccionados?")) {
		document.frm1.action="/iri/TransferenciaVehicular.do?state=borrarParticipante";
		document.frm1.submit();
	}
}
function modificarParticipante(indice, tipoPersona)
{
    document.frm1.hidIndiceMod.value = indice;
	document.frm1.hidTipoPersona.value = tipoPersona;
    document.frm1.action="/iri/TransferenciaVehicular.do?state=obtenerParticipante";		
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

function Saldo() {
 //alert("entre a saldo");
 var param1= Math.round(document.frm1.txtMonto.value*100)/100;
 var param2= Math.round(document.frm1.txtPagado.value*100)/100;

 var resta;
 resta=0;
	if ( (param1 > 0) && (param2 > 0) ) { 
		resta=Math.round( (param1 - param2)*100)/100;
	
	}
	else {
		if ( (param1 > 0) && (param2 = 0) ) {
			resta=param1;
		}
		else {
			if ( (param2 > 0) && (param1 = 0) )
			{ 
				alert('La cantidad Pagada no puede exceder al Monto');
				return;
			}
			
		}
	}
	 
	if ( param2 > param1 ) { 
		alert('La cantidad Pagada no puede exceder al Monto');
				
		document.frm1.txtPagado.value = "0";
		document.frm1.txtSaldo.value = "0";
		return;
	} 
	 
	document.frm1.txtSaldo.value = resta;
	
	return;
} 
function Cancelar(){
	document.frm1.action="/iri/TransferenciaVehicular.do?state=regresarATransVehicular2";
	document.frm1.submit();
}
function completaVehiculo(){

	if (esVacio(document.frm1.txtPlaca.value))
	{
		//alert("Debe ingresar el numero de placa");
		//document.frm1.txtSolLugar.focus();
		return
	}
	
	document.frm1.action="/iri/TransferenciaVehicular.do?state=llenaVehiculo";
	document.frm1.submit();
	
}
</script>
<body><B></B>
<br>
	<table cellspacing=0 class=titulo>
  		<tr>
			<td>
				<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Transferencia Vehicular</FONT>
			</td>
  		</tr>
	</table>
	<br>
	<form name="frm1" method="POST" class="formulario">
  	
  	<% if (request.getAttribute("indiceVehiculo")!=null) { %>
	<input type="hidden" name="indVehiculo" value="<%=request.getAttribute("indiceVehiculo")%>">
	<% } else  {%>
	<input type="hidden" name="indVehiculo" value="">
  	<% } %>
  	
  	<% if (request.getAttribute("indiceVehiculo2")!=null) { %>
	<input type="hidden" name="hidIndiceMod2" value="<%=request.getAttribute("indiceVehiculo2")%>">
	<% } else  {%>
	<input type="hidden" name="hidIndiceMod2" value="">
  	<% } %>
  	<input type="hidden" name="hidTipoPersona" value="">
	<input type="hidden" name="hidIndiceMod" value="">
	<table class=tablasinestilo>
    	<tr>
      		<th colspan="2"><strong>DATOS DE VEHICULO</strong></th>
    	</tr>
		<tr>
      		<td width="30%"><strong>PLACA <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="txtPlaca" size="20" maxlength="130" style="width:180" onblur="sololet(this);completaVehiculo()" ></td>
		</tr>
    	<tr>
      		<td width="30%"><strong>SERIE</strong></td>
      		<td width="80%"><input type="text" name="txtSerie" size="20" maxlength="130" style="width:180" readonly></td>
    	</tr>
		<tr>
      		<td width="30%"><strong>MOTOR</strong></td>
      		<td width="80%"><input type="text" name="txtMotor" size="20" maxlength="130" style="width:180" readonly></td>
		</tr>
		<tr>
      		<td width="30%"><strong>OFICINA REGISTRAL</strong></td>
      		<td width="70%">
      			<select size="1" name="cboOficinas" onChange="frm1.hidOfic.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text" style="width:180">
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
				        <option value="07|05">Puno</option><U>
				        <option value="06|04">Quillabamba</option></U>
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
      		<td width="70%"><input type="text" name="txtPartida" size="20" maxlength="130" style="width:180" readonly></td>
    	</tr>
		<tr>
      		<td width="30%"><strong>SUBACTO <font color="900000">*</font></strong></td>
      		<td width="70%">
      			<select size="1" name="cboSubActo" onChange="" style="width:180">
	            	<option value="00">&nbsp;</option>
	            	<option value="V0012">COMPRA - VENTA</option>
	            </select>
	        </td>
		</tr>
		<tr>
      		<td width="30%"><strong>FORMA PAGO <font color="900000">*</font></strong></td>
      		<td width="70%">
      			<select size="1" name="cboFormaPago" onChange="" style="width:180">
	            	<option>Contado</option>
	            	<option>Credito</option>
	        	</select>
				
	        </td>
		</tr>
		<tr>
      		<td width="30%"><strong>MONEDA <font color="900000">*</font></strong></td>
      		<td width="70%">
      			<select name="cboMoneda" size="1" style="width:180">
            	<option value="00">&nbsp;</option>
            	<option value="03">NUEVOS SOLES</option>
            	<option value="06">D&OacuteLARES</option>
            	<option value="99">OTROS</option>
        	</select>
        	<input type="hidden" name="hidMoneda" value="Nuevos Soles">
        	</td>
		</tr>
    	<tr>
      		<td width="30%"><strong>MONTO <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="txtMonto" size="20" maxlength="130" style="width:180" onblur="Saldo()"></td>
    	</tr>
    	<tr>
      		<td width="30%"><strong>PAGADO <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="txtPagado" size="20" maxlength="130" style="width:180" onblur="Saldo()"></td>
    	</tr>
    	<tr>
      		<td width="30%"><strong>SALDO <font color="900000">*</font></strong></td>
      		<td width="70%"><input type="text" name="txtSaldo" size="20" maxlength="130" style="width:180" readonly></td>
    	</tr>
    	<tr>
      		<td width="30%"><strong>OBSERVACIONES</strong></td>
      		<td width="70%"><textarea name="txtObservaciones" rows="4" cols="50"></textarea></td>
    	</tr>
    	<%-- <tr>
      		<td width="30%"><strong>COMPRADOR <font color="900000">*</font></strong></td>
      		<td width="70%">
      			<select size="1" name="comprador" onChange="#" style="width:180">
	            	<option>Participante 1</option>
	            	<option>Participante 2</option>
	        	</select>
	        </td>
		</tr> --%>
		<tr>
			<td colspan=2>
				<table>
					<tr>
						<th colspan=5><strong>VENDEDORES</strong></th>
					</tr>
					<tr>
						<td colspan="5">
							<table width="100%" border="0" class="grilla" cellspacing="0">
								<tr>
									<td colspan="6"></td>
								</tr>
								<tr>
									<th width="2%" align="center"><div align="center">NRO.</div></th>
									<th width="7%" align="center" height="12"><div align="center">TIPO PERSONA</div></th>
									<th width="7%" align="center" height="12"><div align="center">TIPO DOC</div></th>
									<th width="7%" align="center" height="12"><div align="center">NUMERO DOC</div></th>
									<th width="13%" align="center" height="12"><div align="center">NOMBRE/RAZON SOCIAL</div></th>
								</tr>
								
								<tr>
									<% 
									PersonaNatural vpersonaNatural = null;
									PersonaJuridica vpersonaJuridica = null;									
									System.out.println("********11111111");
									java.util.ArrayList listaVendedorNatural = null;
									if (request.getAttribute("vendedoresNaturales")!=null)
										listaVendedorNatural = (java.util.ArrayList)request.getAttribute("vendedoresNaturales");
										
									int size11=0;
 						    	    if (listaVendedorNatural!=null) {
								        size11= listaVendedorNatural.size();
							    	 	if (size11>0) {
								    	   System.out.println("numero de vendedores::::" + size11);
								    	   for (int i=0; i<size11; i++) {
								    	  	   vpersonaNatural = (PersonaNatural)listaVendedorNatural.get(i);
									%>
		      						<td><%=i+1%></td>		      		
		      						<td align="center">Natural</td>
		      						<td><%= vpersonaNatural.getDescripcionTipoDocumento()%></td>
		      						<td><%= vpersonaNatural.getNumeroDocumento()%></td>
		      						<td><%=(vpersonaNatural.getApellidoPaterno()+ " " +vpersonaNatural.getApellidoMaterno()+ " " + vpersonaNatural.getNombre())%></td>
		      						
								</tr>
								<%
										  }
									   } 
							     	}
									System.out.println("2");
									java.util.ArrayList listaVendedorJuridica = null;
									if (request.getAttribute("vendedoresJuridicas")!=null)
										listaVendedorJuridica = (java.util.ArrayList)request.getAttribute("vendedoresJuridicas");
									System.out.println("3::"+listaVendedorJuridica);
									int size22=0;
									if (listaVendedorJuridica!=null) {
										System.out.println("4");
								    	size22= listaVendedorJuridica.size();
							    	 	if (size22>0) {
							    	 		System.out.println("5::"+size22);
								    	    for (int i=0; i<size22; i++) {
								    	   	   vpersonaJuridica = (PersonaJuridica)listaVendedorJuridica.get(i);
								%>
								<tr>
								
		      						<td><%=i+1%></td>
		      						<td align="center">Juridica</td>
		      						<td><%= vpersonaJuridica.getDescripcionTipoDocumento()%></td>
		      						<td><%= vpersonaJuridica.getNumeroDocumento()%></td>
		      						<td><%= vpersonaJuridica.getRazonSocial()%>></td>
		      						
		    					</tr>
		    					<%
										   }
									   }
								    }
								    System.out.println("3");
								%>
								<tr>
									<td colspan=6></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
    	</tr>
		<tr>
			<td colspan=2>
				<table>
					<tr>
						<th colspan=5><strong>COMPRADORES</strong></th>
					</tr>
					<tr>
						<td colspan="5">
							<table width="100%" border="0" class="grilla" cellspacing="0">
								<tr>
									<td colspan="6"></td>
								</tr>
								<tr>
									<th width="2%" align="center" height="12"><div align="center"><input type="checkbox"></div></th>
									<th width="2%" align="center"><div align="center">NRO.</div></th>
									<th width="7%" align="center" height="12"><div align="center">TIPO PERSONA</div></th>
									<th width="7%" align="center" height="12"><div align="center">TIPO DOC</div></th>
									<th width="7%" align="center" height="12"><div align="center">NUMERO DOC</div></th>
									<th width="13%" align="center" height="12"><div align="center">NOMBRE/RAZON SOCIAL</div></th>
								</tr>
								<% 
								   Vehiculo vehiculo = null;
								   ArrayList listaVehiculos = null;
								   PersonaNatural personaNatural = null;
						    	   PersonaJuridica personaJuridica = null;
						    	   SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
						    	   listaVehiculos = solicitudInscripcion.getVehiculos();

							       if (listaVehiculos!=null) {
							    	   vehiculo=(Vehiculo)listaVehiculos.get(Integer.parseInt((String)request.getAttribute("indiceVehiculo") ) );						    	   					    	   
							    	   java.util.ArrayList listaPersonasNatural = (java.util.ArrayList)vehiculo.getCompradoresPersonaNatural();
							    	   java.util.ArrayList listaPersonasJuridica = (java.util.ArrayList)vehiculo.getCompradoresPersonaJuridica();
							    	   
							    	   int size1=0;
							    	   int size2=0;
							    	   if (listaPersonasNatural!=null) {
								    	   size1= listaPersonasNatural.size();
							    	 	   if (size1>0) {
								    	   	   for (int i=0; i<size1; i++) {
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
						    	<% 		      }
						    	           }
   					    		       }
			
							    	   if (listaPersonasJuridica!=null) {
								    	   size2= listaPersonasJuridica.size();
								    	    System.out.println("size2::"+size2);  
							    	 	   if (size2>0) {
								    	 	  for (int i=0; i<size2; i++) {
								    	   	   	   personaJuridica = (PersonaJuridica)listaPersonasJuridica.get(i);	
							    	   	   	  
						    	%>
								<tr>
						      		<td align="center"><input type="checkbox"name="indicesListaPJ" value="<%=i%>"></td>
						      		<td align="center"><%=i+1+(size1)%></td>
						      		<td>Juridica</td>
						      		<td><%=personaJuridica.getDescripcionTipoDocumento()%></td>
						      		<td><A href="javascript:modificarParticipante(<%=i%>,'PJ')"><%=personaJuridica.getNumeroDocumento()%></A></td>
						      		<td><%=personaJuridica.getRazonSocial()%></td>
						    	</tr>
						    	<% 		     }
						    	           }
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
				</table>
			</td>
		</tr>
    	<tr>
      		<td colspan="2">&nbsp;</td>
    	</tr>
    	<tr>
    		<td colspan="2" width="100%" align="center"> 
    		    <A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Grabar Vehiculo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
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
	<% 
	  
	  SolicitudInscripcion solicitudInscripcion2 = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
   	  java.util.ArrayList vehiculos = solicitudInscripcion2.getVehiculos();
	  if (vehiculos!=null)  {
		  Vehiculo vehiculo2 = (Vehiculo)vehiculos.get(Integer.parseInt((String)request.getAttribute("indiceVehiculo") ) );
		%>
		
		<% if (vehiculo2.getPlaca()!=null) { %>
			document.frm1.txtPlaca.value = "<%=vehiculo2.getPlaca()%>";
		<% } %>
		//(!instrumentoPublico2.getFecha.equals(""))no lo pongo por q sale errorrrrrrrrr********
	    <% if  (vehiculo2.getSerie()!=null) { %>
			document.frm1.txtSerie.value = "<%=vehiculo2.getSerie() %>";
		<% } %>
	
		<% if (vehiculo2.getMotor()!=null) { %>
			document.frm1.txtMotor.value = "<%=vehiculo2.getMotor()%>";
		<% } %>
			
		doCambiaCombo(document.frm1.cboOficinas, "<%=vehiculo2.getCodigoZonaRegistral() + "|" + vehiculo2.getCodigoOficinaRegistral()%>");
		
		<% if (vehiculo2.getNumeroPartida()!=null) { %>
			document.frm1.txtPartida.value = "<%=vehiculo2.getNumeroPartida()%>";
		<% } %>
		
		<% if (vehiculo2.getCodigoSubActo()!=null) { %>
			doCambiaCombo(document.frm1.cboSubActo,"<%=vehiculo2.getCodigoSubActo()%>");
		<% } %>
		
		<% if (vehiculo2.getCodigoSubActo()!=null) { %>
			doCambiaCombo(document.frm1.cboSubActo,"<%=vehiculo2.getCodigoSubActo()%>");
		<% } %>
		
		doCambiaCombo(document.frm1.cboFormaPago,"<%=vehiculo2.getCodigoFormaPago()%>");
		doCambiaCombo(document.frm1.cboMoneda,"<%=vehiculo2.getCodigoMoneda()%>" );
		
		<% if (vehiculo2.getMonto()!=null) { %>
			document.frm1.txtMonto.value = "<%=vehiculo2.getMonto()%>";
		<% } %>
		
		<% if (vehiculo2.getPagado()!=null) { %>
			document.frm1.txtPagado.value = "<%=vehiculo2.getPagado()%>";
		<% } %>
		
		<% if (vehiculo2.getSaldo()!=null) { %>
			document.frm1.txtSaldo.value = "<%=vehiculo2.getSaldo()%>";
		<% } %>
	
		<% if (vehiculo2.getObservaciones()!=null) { %>
			document.frm1.txtObservaciones.value = "<%=vehiculo2.getObservaciones()%>";
		<% } %>	
   <% } %>
   //document.frm1.txtSaldo.disabled = true; si lo pongo sale error

</SCRIPT>
</BODY>
</HTML>