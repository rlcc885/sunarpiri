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
function Nuevo(){

	document.frm1.action="/iri/TransferenciaVehicular.do?state=obtenerNuevoVehiculo";
	document.frm1.submit();
	   
}
function modificarVehiculo(indice)
{
    document.frm1.hidIndiceMod2.value = indice;
	document.frm1.action="/iri/TransferenciaVehicular.do?state=obtenerVehiculo";		
	document.frm1.submit();
}

function Regresar(){
	
	document.frm1.action="/iri/SolicitudInscripcion.do?state=regresarASolicitudInscripcion4";
	document.frm1.submit();
}
function Borrar(){
	if (confirm("Esta seguro de Borrar el o los registro(s) seleccionados?")) {
		document.frm1.action="/iri/TransferenciaVehicular.do?state=borrarVehiculo";
		document.frm1.submit();
	}
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
	   java.util.ArrayList lista1 = (java.util.ArrayList)solic.getVehiculos();
	   
	   int si1=0;
	   	if (lista1!=null)
	   		si1 = lista1.size();
	   		   if (si1==0)  {	
    %>
		alert("Debe ingresar por lo menos un Vehiculo");
		return

						 <% } %>
		
	document.frm1.action="/iri/TransferenciaVehicular.do?state=obtenerResumenAntesPago";		
	document.frm1.submit();
}
</script>
<body><I></I>

<br>
<table cellspacing=0 class=titulo>
  <tr>
	<td>
		<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Transferencia Vehicular
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 1 DE 3</FONT>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario" enctype="multipart/form-data">
<input type="hidden" name="indVehiculo" value="">
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
		          <td width="100"><strong>ACTA NOTARIAL</strong></td>
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
		<th colspan=5><strong>VEHICULOS</strong></th>
	</tr>
	<tr>
		<td colspan="5">
			<table width="100%" border="0" class="grilla" cellspacing="0">
				<tr>
		      		<td colspan="6"></td>
		    	</tr>
		    	<tr>
		      		<th width="2%" align="center" height="12"><div align="center"><input type="checkbox"></div></th>
		      		<th width="7%" align="center"><div align="center">SEC.</div></th>
		      		<th width="7%" align="center"><div align="center">PLACA</div></th>
		      		<th width="11%" align="center" height="12"><div align="center">SERIE</div></th>
		      		<th width="11%" align="center" height="12"><div align="center">MOTOR</div></th>
		      		<th width="11%" align="center" height="12"><div align="center">OFICINA REGISTRAL</div></th>
		      		<th width="11%" align="center" height="12"><div align="center">PARTIDA</div></th>
		    	</tr>
				<%
					
			   		Vehiculo vehiculo = null;
		    	    SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
		    	    java.util.ArrayList listaVehiculos = (java.util.ArrayList)solicitudInscripcion.getVehiculos();
		    	    
		    	    int size1=0;
		    	    
		    	    if (listaVehiculos!=null) {
			    	   size1= listaVehiculos.size();
		    	 	   
		    	 	   	if (size1>0) {
			    	   	   for (int i=0; i<size1; i++) {
			    	   	   	   vehiculo = (Vehiculo)listaVehiculos.get(i);	    	   		
				
				%>
				<tr>
		      		<td align="center"><input type="checkbox" name="indicesListaVehiculo" value="<%=i%>"></td>
		      		<td align="center"><%=i+1%></td>
		      		<td align="center"><A href="javascript:modificarVehiculo(<%=i%>)"><%= vehiculo.getPlaca()%></A></td>
		      		<td><%= vehiculo.getSerie()%></td>
		      		<td><%= vehiculo.getMotor()%></td>
		      		<td><%= vehiculo.getDescripcionOficinaRegistral()%></td>
		      		<td><%= vehiculo.getNumeroPartida()%></td>
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
<br>
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
</body>
</html>

