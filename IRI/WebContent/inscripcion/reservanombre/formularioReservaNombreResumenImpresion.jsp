<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<html>

<head>
	 <title>Formulario de Reserva de Preferencia Registral - Resumen</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>

<script language="JavaScript">
	window.top.frames[0].location.reload();

function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";
}

function Continuar(){
	document.frm1.action="/iri/ReservaNombre.do?state=obtenerImpresion2";
	document.frm1.submit();
}

</script>

<body>
	<br>
	<form name="frm1" method="POST" class="formulario">
	<% SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion"); %>
	<table class="tablasinestilo">
		 <tr>
		   <td align="center">
   				<IMG src="images/logosunarp.gif" border="0">
    	   </td>
    	   <td width="60%">
			<table cellspacing="0" border=1 width="100%">
				<tr>
                   <td align="center" style="black;background-color: D7D7D7;" ><strong><font size="3"><br>SOLICITUD DE INSCRIPCI&Oacute;N DE T&Iacute;TULO<br><br></font></strong></td>
                </tr>
			</table>
		  </td>		
    	 </tr>
        <tr>
      		<td colspan="2">&nbsp;</td>
        </tr>
		<tr>
		  <td width="40%">
			<table cellspacing="0" border=1 width="100%">
				<tr>
                   <td align="center" style="black;background-color: D7D7D7;" ><strong><font size="3"><%=solicitudInscripcion.getAnho()+"-"+solicitudInscripcion.getNumeroHoja()%></font></strong></td>
                </tr>
			</table>		  		  
		  </td>
		  <td></td>
		</tr>
	</table>
	<br>
	<table class="tablasinestilo">
    	<tr>
    		<th colspan="4"><strong>1. </strong></th>
    	</tr>
    	<tr>
            <td width="186" valign="top"><input type="checkbox" disabled>&nbsp;Propiedad<BR>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Inmueble</td>
            <td width="186" valign="top"><input type="checkbox" disabled checked="true">&nbsp;Personas<BR>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Juridicas</td>
            <td width="186" valign="top"><input type="checkbox" disabled>&nbsp;Personas<BR>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Naturales</td>
            <td width="186" valign="top"><input type="checkbox" disabled>&nbsp;Bienes<BR>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Muebles(S&oacute;lo<BR clear="">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prendas Especiales)</td>
        </tr>
      	<tr>
      		<td>&nbsp;</td>
        </tr>
    	<tr>
            <td colspan="4" width="100%">Se�or Registrador P&uacute;blico de la Oficina Registral de <strong>LIMA</strong> :</td>
  	    </tr>
    </table>
    <table class="tablasinestilo">
        <tr>
    		<th><strong>2. </strong></th>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo" border="1">
      				<tr>
			            <td width="200" align="center"><strong><%=solicitudInscripcion.getPresentante().getApellidoPaterno()%></strong></td>
			            <td width="200" align="center"><strong><%=solicitudInscripcion.getPresentante().getApellidoMaterno()%></strong></td>
			            <td width="200" align="center"><strong><%=solicitudInscripcion.getPresentante().getNombre()%></strong></td>            
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="200" align="center">Apellido Paterno</td>
			            <td width="200" align="center">Apellido Materno</td>
			            <td width="200" align="center">Nombre(s)</td>            
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td>&nbsp;</td>
        </tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="130px">Identificado(a) con:</td>
			            <td width="60px"><input type="checkbox" <% if (solicitudInscripcion.getPresentante().getCodigoTipoDocumento().equals("09")) { %> checked="true" <%}%> disabled>&nbsp;D.N.I</td>
			            <td width="60px"><input type="checkbox" <% if (solicitudInscripcion.getPresentante().getCodigoTipoDocumento().equals("01")) { %> checked="true" <%}%> disabled>&nbsp;L.E</td>            
			            <td width="60px"><input type="checkbox" <% if (solicitudInscripcion.getPresentante().getCodigoTipoDocumento().equals("04")) { %> checked="true" <%}%> disabled>&nbsp;C.I</td> 
			            <td width="60px"><input type="checkbox" <% if (solicitudInscripcion.getPresentante().getCodigoTipoDocumento().equals("03")) { %> checked="true" <%}%> disabled>&nbsp;C.E</td> 			            
			            <td width="20px">No.</td> 			            
			            <td width="210px">
			            	<table border=1 width="100%">
			            		<tr>
			            			<td align="center"><strong><%=solicitudInscripcion.getPresentante().getNumeroDocumento()%></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td>&nbsp;</td>
        </tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="130px">Domiciliado(a) en:</td>
			            <td width="470px">
			            	<table border=1 width="100%">
			            		<tr>
			            			<td align="center"><strong><%=solicitudInscripcion.getPresentante().getDescripcionTipoVia()+ " " + solicitudInscripcion.getPresentante().getDireccion() + "\t-" + "\t" + solicitudInscripcion.getPresentante().getDescripcionDistrito() + "\t-" + "\t" + solicitudInscripcion.getPresentante().getDescripcionProvincia() %></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td>&nbsp;</td>
        </tr>
    	<tr>
            <td width="100%">Solicito:</td>
  	    </tr>
    </table>
    <table class="tablasinestilo">
        <tr>
    		<th><strong>3. </strong></th>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>La inscripci&oacute;n de (acto o derecho):</td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="600px">
			            	<table border=1 width="100%">
			            		<tr>
			            			<td align="center"><strong>RESERVA DE PREFERENCIA REGISTRAL</strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td>&nbsp;</td>
        </tr>        
    </table>
    <table class="tablasinestilo">
        <tr>
    		<th><strong>4. </strong></th>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>Intervinientes:</td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo" border=1>
		    	<% PersonaNatural personaNatural = null;
		    	   PersonaJuridica personaJuridica = null;
		    	   java.util.ArrayList listaPersonasNatural = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
		    	   java.util.ArrayList listaPersonasJuridica = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
		    	   int size1=0;
		    	   int size2=0;
		    	   if (listaPersonasNatural!=null) {
			    	   size1= listaPersonasNatural.size();
			    	   for (int i=0; i<size1; i++) {
			    	   	  personaNatural = (PersonaNatural)listaPersonasNatural.get(i);	    	   		
			    %>
      			    <tr>
            			<td width="260"><strong><%=personaNatural.getApellidoPaterno() + " " + personaNatural.getApellidoMaterno() + " " + personaNatural.getNombre()%></strong></td>
            			<td><strong><%=personaNatural.getDescripcionTipoDocumento() + " " + personaNatural.getNumeroDocumento()%></strong></td>
			        </tr>
			   <%      }
			       } %>
		       <%  if (listaPersonasJuridica!=null) {
			    	   size2= listaPersonasJuridica.size();
			    	   for (int i=0; i<size2; i++) {
			    	   	  personaJuridica = (PersonaJuridica)listaPersonasJuridica.get(i);	    	   		
			    %>
				    <tr>
			      		<td><strong><%=personaJuridica.getRazonSocial()%></strong></td>
			      		<td><strong><%=personaJuridica.getDescripcionTipoDocumento() + " " + personaJuridica.getNumeroDocumento()%></strong></td>
			      	</tr>
			   <%      }
			       } %>
      			</table>
      		</td>
        </tr>
        <tr>
      		<td>&nbsp;</td>
        </tr>
        <tr>
      		<td>&nbsp;</td>
        </tr>
    </table> 
	<table class="tablasinestilo">
		<tr>
		  <td width="65%">&nbsp;</td>
		  <td width="35%">
			<table cellspacing="0" border=1 width="100%">
				<tr>
                   <td align="center" style="black;background-color: D7D7D7;" ><strong>&nbsp;</strong><BR>
                        <BR>
                        <BR>
                        <BR>
                   </td>
                </tr>
			</table>
		  </td>
		</tr>
		<tr>
      		<td width="65%">&nbsp;</td>
      		<td width="35%" align="center">Firma o huella digital del presentante</td>
        </tr>
        <tr>
      		<td colspan="2">&nbsp;</td>
        </tr>
	</table>
<br>
<table class=tablasinestilo width="634">
  <tr>
  	<td width="96%" align="right">
  	  <div id="HOJA2"> 
  	    <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
  	      <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
  	    </a>
  	  </div>
  	</td>
    <td align="right" width="4%">
	  <div id="HOJA3">	
	    <a href="javascript:Continuar();" onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	      <IMG height=25 hspace=4 src="images/btn_continuar.gif" width=83 align=absMiddle vspace=5 border=0>
	    </a>
	  </div>
	</td>
        </tr>
</table>
</form>
<br>
</html>