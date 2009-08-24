<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*" %>


<html>
<head>
	 <title>Formulario Bloqueo Inmueble - Datos Generales</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>


<script>
function cambiarArchivo(){

	archivoSinAdjuntar.style.visibility="visible";
	archivoSinAdjuntar.style.display="";
	archivoAdjuntado.style.visibility="hidden";
	archivoAdjuntado.style.display="none";

}
function Nuevo(){

	document.frm1.action="/iri/BloqueoInmueble.do?state=obtenerNuevoParticipante";
	document.frm1.submit();
	   
}
function Borrar(){
	if (confirm("Esta seguro de Borrar el o los registro(s) seleccionados?")) {
		document.frm1.action="/iri/BloqueoInmueble.do?state=borrarParticipante";
		document.frm1.submit();
	}
	return;
}
function Nuevo2(){

	document.frm1.action="/iri/BloqueoInmueble.do?state=obtenerNuevaPartida";
	document.frm1.submit();
	   
}
function Borrar2(){
	if (confirm("Esta seguro de Borrar el o los registro(s) seleccionados?")) {
		document.frm1.action="/iri/BloqueoInmueble.do?state=borrarPartida";
		document.frm1.submit();
	}
	return;
}
function Regresar(){
	
	document.frm1.action="/iri/SolicitudInscripcion.do?state=regresarASolicitudInscripcion3";
	document.frm1.submit();
}
function modificarParticipante(indice, tipoPersona)
{
    document.frm1.hidIndiceMod.value = indice;
	document.frm1.hidTipoPersona.value = tipoPersona;
    document.frm1.action="/iri/BloqueoInmueble.do?state=obtenerParticipante";		
	document.frm1.submit();
}
function modificarPartida(indice)
{
    document.frm1.hidIndiceMod2.value = indice;
	document.frm1.action="/iri/BloqueoInmueble.do?state=obtenerPartida";		
	document.frm1.submit();
}
function Validar(){

	if (esVacio(document.frm1.txtSolLugar.value))
	{
		alert("Debe ingresar el Lugar ");
		document.frm1.txtSolLugar.focus();
		return
	}
	
	if (esVacio(document.frm1.txtSolFecha.value))
	{
		alert("Debe ingresar la Fecha ");
		document.frm1.txtSolFecha.focus();
		return
	}
	
	if (archivoAdjuntado.style.visibility=="hidden") {
	
 		if (esVacio(document.frm1.txtSolDocumento.value))
			{
				alert("Debe adjuntar el archivo");					
				document.frm1.txtSolDocumento.focus();
				return;
			}
	}

	<% SolicitudInscripcion solic = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
	   java.util.ArrayList lista1 = (java.util.ArrayList)solic.getParticipantesPersonaNatural();
	   java.util.ArrayList lista2 = (java.util.ArrayList)solic.getParticipantesPersonaJuridica();
	   java.util.ArrayList lista3 = (java.util.ArrayList)solic.getPartidas();
	   int si1=0;
	   int si2=0;
	   int si3=0;
	   
	   if (lista1!=null)
	   	si1 = lista1.size();
	   	
	   if (lista2!=null)
	   	si2 = lista2.size();
	   
	   if (lista3!=null)
	   	si3 = lista3.size();
	   		
	   if ( (si1==0) && (si2==0)) {	
    %>
		alert("Debe ingresar por lo menos un Participante");
		return

	<% } %>
	
	<%  if ( (si3==0) ) {	
    %>
		alert("Debe ingresar por lo menos una Partida");
		return

	<% } %>
	

	
	document.frm1.action="/iri/BloqueoInmueble.do?state=obtenerResumenAntesPago";		
	document.frm1.submit();
}
function cambiarArchivo(){

	archivoSinAdjuntar.style.visibility="visible";
	archivoSinAdjuntar.style.display="";
	archivoAdjuntado.style.visibility="hidden";
	archivoAdjuntado.style.display="none";

}

function fechas(caja)
{ 
   if (caja)
   {  
      borrar = caja;
      if ((caja.substr(2,1) == "/") && (caja.substr(5,1) == "/"))
      {      
         for (i=0; i<10; i++)
	     {	
            if (((caja.substr(i,1)<"0") || (caja.substr(i,1)>"9")) && (i != 2) && (i != 5))
			{
               borrar = "";
               break;  
			}  
         }
	     if (borrar)
	     { 
	        a = caja.substr(6,4);
		    m = caja.substr(3,2);
		    d = caja.substr(0,2);
		    if((a < 1900) || (a > 2050) || (m < 1) || (m > 12) || (d < 1) || (d > 31))
		       borrar = "";
		    else
		    {
		       if((a%4 != 0) && (m == 2) && (d > 28))	   
		          borrar = ""; // Año no viciesto y es febrero y el dia es mayor a 28
			   else	
			   {
		          if ((((m == 4) || (m == 6) || (m == 9) || (m==11)) && (d>30)) || ((m==2) && (d>29)))
			         borrar = "";	      				  	 
			   }  // else
		    } // fin else
         } // if (error)
      } // if ((caja.substr(2,1) == \"/\") && (caja.substr(5,1) == \"/\"))			    			
	  else
	     borrar = "";
	  if (borrar == "") {
	     alert("Debe Ingresar la fecha con el formato dd/mm/aaaa");
   	  	 document.frm1.txtSolFecha.focus();
   	  	 return;
   	  }
   } // if (caja)   
} // FUNCION

</script>
<body>

<br>
<table cellspacing=0 class=titulo>
  <tr>
	<td>
		<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Bloqueo de Propiedad Inmueble
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 1 DE 3</FONT>

	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario" enctype="multipart/form-data">
<input type="hidden" name="hidTipoPersona" value="">
<input type="hidden" name="hidIndiceMod" value="">
<input type="hidden" name="hidIndiceMod2" value="">
  <table class=tablasinestilo>
  	
        <TR>
           <th colspan="5"><strong>DATOS DE INSTRUMENTO PUBLICO</strong><font color="900000"><BR>
            (Deber&aacute; adjuntarse el archivo rtf correspondiente al acto registral seleccionado)</font></th>
   	</tr>
   	
    <tr>
        <td colspan="5">
            <table width="100%">
		        <tr>
		          <td width="5">&nbsp;</td>
		          <td width="100"><strong>BOLETA NOTARIAL</strong></td>
		          <td>
		            <table width="100%">
		              <tr>
		                <td width="120">
		                  <input type="text" name="txtSolLugar" size="20" maxlength="120" style="width:120" onBlur="sololet(this)">
		                </td>
		                <td width="120">
		                  <input type="text" name="txtSolFecha" size="20" maxlength="120" style="width:120" onBlur="fechas(this.value);">
		                </td>
		                <% 
				   			SolicitudInscripcion sol = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
				   			EscrituraPublica escritura = sol.getEscrituraPublica();
							String nombreArchivo = "";
							if (escritura!=null)
								nombreArchivo = escritura.getNombreArchivo();
						%>

		                <td>
		                  <div id="archivoSinAdjuntar" style="visibility:hidden;display:none">
		                  <table width="100%">
		                  	<tr>
		                  		<td>
				                  <input type="file" name="txtSolDocumento" size="20" maxlength="135" style="width:217" onBlur="sololet(this)">
								</td>
							</tr>
						  </table>
						  </div>
						  <div id="archivoAdjuntado" style="visibility:hidden;display:none">
		                  <table width="100%">
		                  	<tr>
		                  		<td align="center" width="90%">
				                    <%=nombreArchivo%>
								</td>
								<td align="center" width="10%">
									<A href="javascript:cambiarArchivo();">Cambiar</A>
								</td>
							</tr>
						  </table>
						  </div>
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  &nbsp;Lugar <font color="900000">*</font> 
		                </td>
		                <td>
		                  &nbsp;Fecha <font color="900000">*</font>
		                </td>
		                <td>
		                  &nbsp;Archivo <font color="900000">*</font>
		                </td>
		              </tr>
		            </table>
		          </td>
		        </tr>
	      </table>  
        </td>
    </tr>
    <tr>
    	<td width="1%"></td>
        <td width="18%"><strong>OTROS</strong></td>
      	<td width="81%" colspan="3"><textarea name="txtSolOtros" rows="4" cols="50" onBlur="sololet(this)"></textarea></td>
  	</tr>
    <tr>
        <td colspan="5">&nbsp;</td> 
    </tr>
   
	<tr>
		<th colspan=5><strong>PARTICIPANTES Y/O CONTRATANTES</strong></th>
	</tr>
	<tr>
		<td colspan="5">
			<table border="0" class="grilla" cellspacing="0">
				<tr>
		      		<td colspan="6"></td>
		    	</tr>
		    	<tr>
		      		<th width="2%" align="center" height="12"><div align="center"><input type="checkbox"></div></th>
		      		<th width="2%" align="center"><div align="center">NRO.</div></th>
		      		<th align="center" height="12"><div align="center">TIPO PERSONA</div></th>
		      		<th align="center" height="12"><div align="center">TIPO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NUMERO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NOMBRE/RAZON SOCIAL</div></th>
		      		<%--<th width="11%" align="center" height="12"><div align="center">TIPO PARTICIPANTE</div></th>--%>
		    	</tr>
				<% 
				   PersonaNatural personaNatural = null;
		    	   PersonaJuridica personaJuridica = null;
		    	   SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
		    	   java.util.ArrayList listaPersonasNatural = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
		    	   java.util.ArrayList listaPersonasJuridica = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
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
		    	<% 		   }
		    	        }
		    		}

		    	   if (listaPersonasJuridica!=null) {
			    	   size2= listaPersonasJuridica.size();
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
		    	<% 		   }
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
	<tr>
		<th colspan=5><strong>PARTIDAS</strong></th>
	</tr>
	<tr>
		<td colspan="5">
			<table border="0" class="grilla" cellspacing="0">
				<tr>
		      		<td colspan="7"></td>
		    	</tr>
		    	<tr>
		      		<th width="4%" align="center" height="12"><div align="center"><input type="checkbox"></div></th>
		      		<th width="2%" align="center"><div align="center">NRO.</div></th>
		      		<th width="25%" align="center"><div align="center">OFICINA REGISTRAL</div></th>
		      		<th width="15%" align="center"><div align="center">PARTIDA</div></th>
		      		<th width="15%" align="center" height="12"><div align="center">FICHA</div></th>
		      		<th width="15%" align="center" height="12"><div align="center">TOMA</div></th>
		      		<th width="15%" align="center" height="12"><div align="center">FOJA</div></th>
		      		<th width="15%" align="center" height="12"><div align="center">DISTRITO</div></th>
		    	</tr>
				<% 
		    	   Partida partida = null;
		    	   java.util.ArrayList listaPartidas = (java.util.ArrayList)solicitudInscripcion.getPartidas();
		    	   if (listaPartidas!=null) {
			    	   size1= listaPartidas.size();
		    	 	   if (size1>0) {
			    	   	   for (int i=0; i<size1; i++) {
			    	   	   	   partida = (Partida)listaPartidas.get(i);	
				 %>
				<tr>
		      		<td align="center"><input type="checkbox" name="indicesListaPartida" value="<%=i%>"></td>
		      		<td align="center"><%=i+1%></td>
		      		<td><%= partida.getDescripcionOficinaRegistral() %></td>
		      		<td><A href="javascript:modificarPartida(<%=i%>)"><%=partida.getNumeroPartida()%></A></td>
		      		<% if ( (partida.getFicha()!=null) && (!partida.getFicha().equals("")) ) { %>
		      		<td><%=partida.getFicha()%></td>
		      		<% } else {%>
		      		<td align="center">--</td>
		      		<% } %>
		      		<% if ( (partida.getTomo()!=null) && (!partida.getTomo().equals("")) ) { %>
		      		<td><%=partida.getTomo()%></td>
		      		<% } else {%>
		      		<td align="center">--</td>
		      		<% } %>
		      		<% if ( (partida.getFoja()!=null) && (!partida.getFoja().equals("")) ) { %>
		      		<td><%=partida.getFoja()%></td>
		      		<% } else {%>
		      		<td align="center">--</td>
		      		<% } %>
		      		<% if ( (partida.getDescripcionDistritoPartida()!=null) && (!partida.getDescripcionDistritoPartida().equals("")) ) { %>
		      		<td><%=partida.getDescripcionDistritoPartida()%></td>
		      		<% } else {%>
		      		<td align="center">--</td>
		      		<% } %>
		      		
		      	</tr>
				<% 		   }
		    	        }
		    		}
				%>
			 	<tr>
		      		<td colspan=7></td>
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
									<A href="javascript:Nuevo2();" onmouseover="javascript:mensaje_status('Nuevo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
										<IMG src="images/btn_nuevo.gif" border="0">
									</A>
								</td>
								<td>
									<A href="javascript:Borrar2();" onmouseover="javascript:mensaje_status('Borrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
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
   	<tr>
   		<td align="right" colspan="5">
   		    <A href="javascript:Regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
   		    	<IMG src="images/btn_regresa.gif" border="0">
   			</A>
   			<A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
   				<IMG src="images/btn_continuar.gif" border="0">
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
<% 
   SolicitudInscripcion solicitudInscripcion2 = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
   java.util.ArrayList instrumentosPublico2 = solicitudInscripcion2.getInstrumentoPublico();
   InstrumentoPublico instrumentoPublico2 = null;
   EscrituraPublica escrituraPublica2 = solicitudInscripcion2.getEscrituraPublica();
 
%>
<%  
   if (instrumentosPublico2!=null) {
	   instrumentoPublico2 = (InstrumentoPublico)instrumentosPublico2.get(0);
%>
<script LANGUAGE="JavaScript">
	
	<% if (instrumentoPublico2.getLugar()!=null) { %>
		document.frm1.txtSolLugar.value = "<%=instrumentoPublico2.getLugar()%>";
	<% } %>
	//(!instrumentoPublico2.getFecha.equals(""))no lo pongo por q sale errorrrrrrrrr********
    <% if ( (instrumentoPublico2.getFecha()!=null) && (!instrumentoPublico2.getFecha().equals("")) ) { %>
		document.frm1.txtSolFecha.value = "<%=instrumentoPublico2.getFecha().substring(6,8)+"/"+instrumentoPublico2.getFecha().substring(4,6)+"/"+instrumentoPublico2.getFecha().substring(0,4)%>";
	<% } %>

	<% if (instrumentoPublico2.getOtros()!=null) { %>
		document.frm1.txtSolOtros.value = "<%=instrumentoPublico2.getOtros()%>";
	<% } %>

</script>
<% } 
   if (escrituraPublica2!=null) {
%>
<script LANGUAGE="JavaScript">
	<% if ( (escrituraPublica2.getDocumentoEscrituraPublica()!=null) && (!escrituraPublica2.getDocumentoEscrituraPublica().equals("")) ) { %>
		archivoSinAdjuntar.style.visibility="hidden";
		archivoSinAdjuntar.style.display="none";
		archivoAdjuntado.style.visibility="visible";
		archivoAdjuntado.style.display="";
	<% } else {%>	
		archivoSinAdjuntar.style.visibility="visible";
		archivoSinAdjuntar.style.display="";
		archivoAdjuntado.style.visibility="hidden";
		archivoAdjuntado.style.display="none";
	<% } %>
</script>
<% } else {%>
<script LANGUAGE="JavaScript">
		archivoSinAdjuntar.style.visibility="visible";
		archivoSinAdjuntar.style.display="";
		archivoAdjuntado.style.visibility="hidden";
		archivoAdjuntado.style.display="none";
</script>
<% } %>

<br>
</body>
</html>



