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

<script>
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
}
</script>
<body>
	<br>
	<form name="frm" method="POST" class="formulario">
	<% SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
	 %>	
	<table class="tablasinestilo">
		 <tr>
		   <td align="center">
   				<IMG src="images/orlclogo.gif" border="0">
    	   </td>
    	   <td width="60%">
			<table cellspacing="0" width="100%">
				<tr>
                   <td align="center" style="black;background-color: D7D7D7;" ><strong><font size="2">ZONA REGISTRAL Nro. IX - Sede Lima</font></strong></td>
                </tr>
			</table>
		  </td>		
    	 </tr>
        <tr>
      		<td colspan="2">&nbsp;</td>
        </tr>
    </table>
    <table class="tablasinestilo" align="center">
		<tr>
		  <td width="100%">
			<table cellspacing="0" border=1 width="100%">
				<tr>
                   <td align="center" style="black;background-color: D7D7D7;" ><strong><font size="4">SOLICITUD DE RESERVA DE NOMBRE DE<BR>PERSONA JURIDICA</font></strong></td>
                </tr>
			</table>		  		  
		  </td>
		  <td></td>
		</tr>
	</table>
	<br>
	<table class="tablasinestilo">
    	<tr>
            <td width="100%"><strong><font size="2">SEÑOR REGISTRADOR DE PERSONAS JURIDICAS</font></strong></td>
  	    </tr>
    </table>
    <table class="tablasinestilo">
      	<tr>
      		<td colspan="2">
      		  <font size="2">
      		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			Yo, <strong><%=solicitudInscripcion.getPresentante().getApellidoPaterno() + " " + solicitudInscripcion.getPresentante().getApellidoMaterno() + " " + solicitudInscripcion.getPresentante().getNombre()%></strong>
      			<BR>
      			con <strong><%=solicitudInscripcion.getPresentante().getDescripcionTipoDocumento()%></strong> No. <strong><%=solicitudInscripcion.getPresentante().getNumeroDocumento()%></strong> en mi calidad de <strong>
      			&nbsp;&nbsp;&nbsp;&nbsp;REPRESENTANTE&nbsp;&nbsp;&nbsp;&nbsp;</strong>
      			<BR>
      			domiciliado en <strong><%=solicitudInscripcion.getPresentante().getDescripcionTipoVia() + " " + solicitudInscripcion.getPresentante().getDireccion()%></strong> distrito de
      			<strong><%=solicitudInscripcion.getPresentante().getDescripcionDistrito() + " "%></strong>
      			provincia de <strong><%=solicitudInscripcion.getPresentante().getDescripcionProvincia() + " "%></strong>ante Ud. con el debido respeto me presento y digo:
      		  </font>
      		</td>
        </tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
        </tr> 
      	<tr>
      		<td colspan=2>
      		  <font size="2">
      		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			Solicito, <strong>RESERVA DE NOMBRE DE PERSONAS JURIDICAS PARA:</strong>
      		  </font>
      		</td>
      	</tr>
      	<tr>      	
      		<td colspan=2>
      			<table class="tablasinestilo" border=1 width="100%">
      				<tr>
			            <td><input type="checkbox" disabled checked="true">&nbsp;CONSTITUCION</td>
			            <td><input type="checkbox" disabled>&nbsp;MODIFICACION DE ESTATUTOS</td>            
			            <td><input type="checkbox" disabled>&nbsp;PYME</td> 
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
        </tr>
      	<tr>
      		<td colspan=2>
      		  <font size="2"><strong>NOMBRE DE LA PERSONA JURIDICA</strong></font>
      		</td>
      	</tr> 
      	<tr>
      		<td colspan=2>
     			<table class="tablasinestilo" border=1 width="100%">
      				<tr>
			            <td align="center"><font size="2"><%=solicitudInscripcion.getPersonaJuridica().getRazonSocial()%></font></td>
			        </tr> 
			    </table>
      		</td>
      	</tr>       	      
      	<tr>
      		<td colspan=2>&nbsp;</td>
        </tr>
      	<tr>
      		<td colspan=2>
      		  <font size="2"><strong>ABREVIATURA(OPCIONAL)</strong></font>
      		</td>
      	</tr> 
      	<tr>
      		<td colspan=2>
     			<table class="tablasinestilo" border=1 width="100%">
      				<tr>
			            <td align="center"><font size="2"><%=solicitudInscripcion.getPersonaJuridica().getSiglas()%></font></td>
			        </tr> 
			    </table>
      		</td>
      	</tr>        
    	<tr>
            <td colspan=2>&nbsp;</td>
  	    </tr>
      	<tr>
      		<td colspan=2>
      		  <font size="2"><strong>TIPO SOCIETARIO</strong></font>
      		</td>
      	</tr>
      	<tr>      	
      		<td colspan=2>
      			<table class="tablasinestilo" border=1 width="100%">
      				<tr>
			            <td><input type="checkbox" disabled <% if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedadAnonima().equals("A")) { %> checked="true" <%}%>>&nbsp;S.A.A</td>
			            <td><input type="checkbox" disabled <% if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedadAnonima().equals("C")) { %> checked="true" <%}%>>&nbsp;S.A.C</td>            
			            <td><input type="checkbox" disabled <% if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("010")) { %> checked="true" <%}%>>&nbsp;S.C.R.L</td> 
					</tr>
					<tr>
			            <td><input type="checkbox" disabled <% if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("015")) { %> checked="true" <%}%>>&nbsp;E.I.R.L</td>
			            <td colspan=2>&nbsp;</td>            
					</tr>					
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>         
      	<tr>
      		<td colspan=2>
      		  <font size="2"><strong>DOMICILIO (AV.,CALLE,DISTRITO,PROVINCIA)</strong></font>
      		</td>
      	</tr> 
      	<tr>
      		<td colspan=2>
     			<table class="tablasinestilo" border=1 width="100%">
      				<tr>
			            <td align="center"><font size="2"><%=solicitudInscripcion.getPersonaJuridica().getDescripcionTipoVia() + " " + solicitudInscripcion.getPersonaJuridica().getDireccion()%></font></td>
			        </tr> 
			    </table>
      		</td>
      	</tr> 
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>         
      	<tr>
      		<td colspan=2>
      		  <font size="2"><strong>NOMBRE(S) Y APELLIDOS DE LOS INTEGRANTES DE LA PERSONA JURIDICA EN
      		  CONSTITUCION O NOMBRE DE LA PERSONA JURIDICA CONSTITUIDA EN CASO DE MODIFICACION DE ESTATUS</strong></font>
      		</td>
      	</tr> 
      	<tr>
      		<td colspan=2>
     			<table class="tablasinestilo" border=1 width="100%">
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
			            <td align="center"><font size="2"><%=personaNatural.getApellidoPaterno() + " " + personaNatural.getApellidoMaterno() + " " + personaNatural.getNombre()%></font></td>
			        </tr>
			   <%      }
			       } %>
		       <%  if (listaPersonasJuridica!=null) {
			    	   size2= listaPersonasJuridica.size();
			    	   for (int i=0; i<size2; i++) {
			    	   	  personaJuridica = (PersonaJuridica)listaPersonasJuridica.get(i);	    	   		
			    %>
			        
      				<tr>
			            <td align="center"><font size="2"><%=personaJuridica.getRazonSocial()%></font></td>
			        </tr>
			   <%      }
			       } %>			        
			    </table>
      		</td>
      	</tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>
      	<tr>
      		<td colspan="2">
      		  <font size="2">
      			A usted, Señor Registrador, solicito acceder a mi petici&oacute;n por ser de justicia.
      		  </font>
      		</td>
        </tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>
      	<tr>
      		<td colspan="2" align="right">
      		  <font size="2">
      			<%=request.getAttribute("fechaActual")%>
      		  </font>
      		</td>
        </tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>
      	<tr>
      		<td colspan=2>&nbsp;</td>
      	</tr>
      	<tr>
      	    <td colspan=2>
      	    	<table class="tablasinestilo" width="100%">
      				<tr>
      					<td width="70%">&nbsp;</td>
      	    			<td align="center">
			      		  <font size="2">
			      			<strong>____________________</strong>
			      		  </font>
						</td>
      			    </tr>
      			</table>
      		</td>
        </tr>      	
      	<tr>
      	    <td colspan=2>
      	    	<table class="tablasinestilo" width="100%">
      				<tr>
      					<td width="70%">&nbsp;</td>
      	    			<td align="center">
			      		  <font size="2">
			      			<strong>FIRMA</strong>
			      		  </font>
						</td>
      			    </tr>
      			</table>
      		</td>
        </tr>
      	       	              	  	    
    </table>
<br>
<br>
<table class=tablasinestilo width="634">
  <tr>
  	<td width="100%" align="right">
  	  <div id="HOJA2"> 
  	    <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
  	      <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
  	    </a>
  	  </div>
  	</td>
  </tr>
</table>          
</form>
<br>
</html>