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
	 <title>Formulario Solicitud Inscripci&oacute;n - Resumen</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>

<script>
function mostrarArticulos(){
	if (articulos.style.visibility=="hidden") {
		articulos.style.visibility="visible";
		articulos.style.display="";
	}
	else {
		articulos.style.visibility="hidden";
		articulos.style.display="none";
	}
}

function Regresar(){
	document.frm1.action="/iri/ConstitucionEmpresa.do?state=regresarADatosConstitucion";
	document.frm1.submit();
}

function Continuar(){
	document.frm1.action="/iri/ConstitucionEmpresa.do?state=procesarSolicitud";
	document.frm1.submit();
}

</script>
<body>
	<br>
	<table cellspacing="0" class="titulo">
  		<tr>
			<td><FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Reserva de Preferencia Registral<font size="1"> &gt;&gt;</font> Resumen </FONT></td>
  		</tr>
	</table>
	<br>
	<% SolicitudInscripcion solicitudInscripcion = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
	   java.util.ArrayList listaIntrumentos = (java.util.ArrayList)solicitudInscripcion.getInstrumentoPublico();
	   InstrumentoPublico instrumentoPublico = (InstrumentoPublico)listaIntrumentos.get(0);
	 %>

	<form name="frm1" method="POST" class="formulario">
	<table class="tablasinestilo">
    	<tr>
    		<th colspan="3"><strong>DATOS GENERALES</strong></th>
    	</tr>
    	<tr>
            <td width="186"><strong>ACTO</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getDescripcionActo()%></td>
        </tr>
      	<tr>
            <td width="186"><strong>OFICINA REGISTRAL</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getDescripcionOficinaRegistral()%></td>
        </tr>    	        
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
		<tr>
			<th colspan="3"><strong>DATOS DEL PRESENTANTE</strong></th>
		</tr>
  		<tr valign="middle">
            <td width="186"><strong>NOMBRES</strong></td>
            <td><%=solicitudInscripcion.getPresentante().getApellidoPaterno() + " " + solicitudInscripcion.getPresentante().getApellidoMaterno() + " " +solicitudInscripcion.getPresentante().getNombre()%></td>
            <td>&nbsp;</td>
    	</tr>
  		<tr valign="middle">
            <td width="186"><strong>TIPO DOCUMENTO</strong></td>
            <td><%=solicitudInscripcion.getPresentante().getDescripcionTipoDocumento()%></td>
            <td>&nbsp;</td>
    	</tr>
  		<tr valign="middle">
            <td width="186"><strong>NUMERO</strong></td>
            <td><%=solicitudInscripcion.getPresentante().getNumeroDocumento()%></td>
            <td>&nbsp;</td>
    	</tr>
  		<tr valign="middle">
            <td width="186"><strong>DIRECCION</strong></td>
            <td><%=solicitudInscripcion.getPresentante().getDireccion()%></td>
            <td>&nbsp;</td>
    	</tr>
  		<tr valign="middle">
            <td width="186"><strong>DEPARTAMENTO<strong></td>
            <td><%=solicitudInscripcion.getPresentante().getDescripcionDepartamento()%></td>
            <td>&nbsp;</td>
    	</tr>
  		<tr valign="middle">
            <td width="186"><strong>PROVINCIA<strong></td>
            <td><%=solicitudInscripcion.getPresentante().getDescripcionProvincia()%></td>
            <td>&nbsp;</td>
    	</tr>
  		<tr valign="middle">
            <td width="186"><strong>DISTRITO<strong></td>
            <td><%=solicitudInscripcion.getPresentante().getDescripcionDistrito()%></td>
            <td>&nbsp;</td>
    	</tr>    	
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
    	<tr>
      		<th colspan="3"><strong>DATOS DE LA PERSONA JURIDICA A CONSTITUIR</strong></th>
    	</tr>
    	<tr>
            <td width="186"><strong>DENOMINACION Y/O RAZON SOCIAL</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getPersonaJuridica().getRazonSocial()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>SIGLAS</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getPersonaJuridica().getSiglas()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>TIPO SOCIEDAD</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedad()%></td>
        </tr>
        <% if ( solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("009") ) { %>
        <tr>
            <td width="186"><strong>TIPO SOCIEDAD AN&Oacute;NIMA</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedadAnonima()%></td>
        </tr>
		<% } %>
    	<tr>
            <td width="186"><strong>TRAMITE ELECTRONICO CON SUNAT</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getPersonaJuridica().getIndicadorRUC()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>DOCUMENTOS</strong></td>
            <td colspan="2" width="408">SE ADJUNT&Oacute; DOCUMENTO DE ESCRITURA P&Uacute;BLICA.</td>
        </tr>
    	<tr>
            <td width="186"><strong>FECHA DE SOLICITUD<strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getFechaSolicitud()%></td>
        </tr>
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
    	<tr>
      		<th colspan="3"><strong>CAPITAL</strong></th>
    	</tr>
    	<tr>
            <td width="186"><strong>MONEDA</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getCapital().getDescripcionMoneda()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>MONTO CAPITAL</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getCapital().getMontoCapital()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>VALOR ACCION</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getCapital().getValor()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>NUMERO ACCIONES</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getCapital().getNumero()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>CANCELACION CAPITAL</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getCapital().getDescripcionCancelacionCapital()%></td>
        </tr>
        <% if (solicitudInscripcion.getCapital().getPorcentajeCancelado()!=null) { %>
    	<tr>
            <td width="186"><strong>% CANCELADO</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getCapital().getPorcentajeCancelado()%></td>
        </tr>
        <% } %>
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
    	<% 
    		
    		if (solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedad()!="EMPRESAS INDIVIDUALES DE RESPONSABILIDAD LIMITADA") {
    	%>
    	
    	<tr>
      		<th colspan="3"><strong>DATOS DE RESERVA DE DENOMINACION</strong></th>
    	</tr>
    	<tr>
            <td width="186"><strong>AÑO DE TITULO</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getReservaMercantil().getAnhoTitulo()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>NUMERO DE TITULO</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getReservaMercantil().getNumeroTitulo()%></td>
        </tr>
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
		   <% 
		   }
		   %> 
    	<tr>
      		<th colspan="3"><strong>DATOS DE INSTRUMENTO PUBLICO</strong></th>
    	</tr>
    	<tr>
            <td width="186"><strong>LUGAR</strong></td>
            <td colspan="2" width="408"><%=instrumentoPublico.getLugar()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>FECHA</strong></td>
            <td colspan="2" width="408"><%=instrumentoPublico.getFecha().substring(6,8)+"/"+instrumentoPublico.getFecha().substring(4,6)+"/"+instrumentoPublico.getFecha().substring(0,4)%></td>
        </tr>
    	<tr>
            <td width="186"><strong>ARCHIVO</strong></td>
            <td colspan="2" width="408"><%=solicitudInscripcion.getEscrituraPublica().getNombreArchivo()%></td>
        </tr>
    	<tr>
            <td width="186"><strong>OTROS</strong></td>
            <td colspan="2" width="408"><%=instrumentoPublico.getOtros()%></td>
        </tr>

  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
    	<tr>
      		<th colspan="3"><strong>DATOS DE LOS PARTICIPANTES</strong></th>
    	</tr>
    	<tr>
			<td colspan="3">
    			<table class="grilla" cellpadding="0" cellspacing="0">
    				<tr>
    					<th>TIPO DE PERSONA</th>
    					<th>NOMBRE / RAZON SOCIAL</th>
    				</tr>
    				<%  java.util.ArrayList listaPersonasNatural = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
		    	   		java.util.ArrayList listaPersonasJuridica = (java.util.ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
		    	   		PersonaNatural personaNatural = null;
		    	   		PersonaJuridica personaJuridica = null;
		    	   		int size1=0;
		    	   		if (listaPersonasNatural!=null) {
			    	   		size1= listaPersonasNatural.size();
			    	   	    for (int i=0; i<size1; i++) {
			    	   	   	   personaNatural = (PersonaNatural)listaPersonasNatural.get(i);	    	   		
			    	%> 	   
    				<tr>
    					<td>Persona Natural</td>
    					<td><%=personaNatural.getApellidoPaterno() + " " + personaNatural.getApellidoMaterno() + " " + personaNatural.getNombre()%></td>
    				</tr>
    				<%      }
    				    }
    				    
    				    if (listaPersonasJuridica!=null) {
			    	   		size1= listaPersonasJuridica.size();
			    	   	    for (int i=0; i<size1; i++) {
			    	   	   	   personaJuridica = (PersonaJuridica)listaPersonasJuridica.get(i);
    				 %>
    				<tr>
   	 					<td>Persona Jur&iacute;dica</td>
  	  					<td><%=personaJuridica.getRazonSocial()%></td>
 	   				</tr>
    				<%      }
    				    }
    				 %> 	   				
	    		</table>    		
	    	</td>
  		</tr>
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>
    	<tr>
      		<th colspan="3"><strong>DATOS DEL PAGO</strong></th>
    	</tr>
    	<tr>
			<td colspan="3">
		    	<table class=formulario cellspacing=0>
				    <tr> 
				      <td width="5">&nbsp;</td>
				      <td width="200">&nbsp;</td>
				      <td width="380">&nbsp;</td>
				    </tr>
				    <tr> 
				      <td width="5">&nbsp;</td>
				      <td width="200"><strong>COSTO DEL SERVICIO</strong></td>
				      <td width="380" align="left">
				        S/. <input type="text" name="txtDisServ" value="<%=request.getAttribute("tarifa")%>" size="12" maxlength="12" readonly>
				      </td>
				    </tr>
				    <tr>
				       <td colspan="3">&nbsp;</td>
				    </tr>
				    <tr>
				      <td width="5">&nbsp;</td>
				      <td width="200"><strong>FORMA DE PAGO</strong></td> 
				      <td width="380"><strong>EN LINEA CON MI SALDO DISPONIBLE</strong> </td>
				    </tr>
				    <tr>
				       <td colspan="3">&nbsp;</td>
				    </tr>
	    		</table>    		
	    	</td>
  		</tr>
  		<tr>
    		<td colspan="3">&nbsp;</td>
  		</tr>

    	<tr>
      		<td align="right" colspan="3">
      		   	<A href="javascript:Regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    				<IMG src="images/btn_regresa.gif" border="0">
    			</A>
      		   	<A href="javascript:Continuar();" onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    				<IMG src="images/btn_continuar.gif" border="0">
    			</A>
    		</td>      		
    	</tr>
  	</table>
	</form>
<br>
</html>