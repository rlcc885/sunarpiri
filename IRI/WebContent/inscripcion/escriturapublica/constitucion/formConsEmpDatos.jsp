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
	 <title>Formulario Constituci&oacute;n de Empresa - Datos Generales</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head>
<script>
function Nuevo(){

  	<% 
   	   SolicitudInscripcion solicitudIns = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
   	   java.util.ArrayList lista = (java.util.ArrayList)solicitudIns.getParticipantesPersonaNatural();
	   int sizeLista = 0;
	   if (lista!=null)
	   		sizeLista = lista.size();
	   
	   if (!session.getAttribute("labelTipoSociedad").equals("E.I.R.L")) { %>

			document.frm1.hidMoneda.value = document.frm1.cboSolMoneda.options[document.frm1.cboSolMoneda.selectedIndex].text;
		
			document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerNuevoParticipante";
			document.frm1.submit();
	   
	<% } else {
	   
		   if (sizeLista<1) { %>
	
			document.frm1.hidMoneda.value = document.frm1.cboSolMoneda.options[document.frm1.cboSolMoneda.selectedIndex].text;
		
			document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerNuevoParticipante";
			document.frm1.submit();
	
	<%     } else { %>
		
			alert("Para Constitución de Empresas E.I.R.L solo está permitido ingresar un Participante");
			return;
			
	<%     }
	   
	   } %>
}

function Borrar(){
	document.frm1.hidMoneda.value = document.frm1.cboSolMoneda.options[document.frm1.cboSolMoneda.selectedIndex].text;

	if (confirm("Esta seguro de Borrar el o los registro(s) seleccionados?")) {
		document.frm1.action="/iri/ConstitucionEmpresa.do?state=borrarParticipante";
		document.frm1.submit();
	}
	return;
}

function Regresar(){
	document.frm1.action="/iri/SolicitudInscripcion.do?state=regresarASolicitudInscripcion2";
	document.frm1.submit();
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

function Validar() 
{

   	<% SolicitudInscripcion solic = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
	   java.util.ArrayList lista1 = (java.util.ArrayList)solic.getParticipantesPersonaNatural();
	   java.util.ArrayList lista2 = (java.util.ArrayList)solic.getParticipantesPersonaJuridica();
	   int si1=0;
	   int si2=0;
	   
	   if (lista1!=null)
	   	si1 = lista1.size();
	   	
	   if (lista2!=null)
	   	si2 = lista2.size();
	   	
	   if ( (si1==0) && (si2==0) ) {	
    %>
		alert("Debe ingresar por lo menos un Participante");
		return

	<% } %>

	if (esVacio(document.frm1.txtSolRazonSocial.value))
	{
		alert("Debe ingresar la Denominación y/o Razón Social");
		document.frm1.txtSolRazonSocial.focus();
		return
	}
	if (esVacio(document.frm1.txtSolSiglas.value))
	{
		alert("Debe ingresar las Siglas");
		document.frm1.txtSolSiglas.focus();
		return
	}
	<% if ( session.getAttribute("flagTipoSociedad")=="S" ) { %>
		if (document.frm1.cboTipoSociedad.options[document.frm1.cboTipoSociedad.selectedIndex].value == "0")
		{
			alert("Debe seleccionar el Tipo de Sociedad Anónima");
			document.frm1.cboTipoSociedad.focus();
			return;
		}	
	<% } %>
	if (document.frm1.cboSolMoneda.options[document.frm1.cboSolMoneda.selectedIndex].value == "00")
	{
		alert("Debe seleccionar la Moneda del Capital");
		document.frm1.cboSolMoneda.focus();
		return;
	}
	if (esVacio(document.frm1.txtSolMonto.value))
	{
		alert("Debe ingresar el Monto del Capital");					
		document.frm1.txtSolMonto.focus();
		return;
	}
	if (!esDecimal(document.frm1.txtSolMonto.value))
	{
		alert("Debe ingresar un valor numérico en el Monto del Capital");					
		document.frm1.txtSolMonto.focus();
		return;
	}
	
	<% if (!session.getAttribute("labelTipoSociedad").equals("E.I.R.L")) { %>

		if (esVacio(document.frm1.txtSolValor.value))
		{
			alert("Debe ingresar el Valor");					
			document.frm1.txtSolValor.focus();
			return;
		}		
		if (!esDecimal(document.frm1.txtSolValor.value))
		{
			alert("Debe ingresar un valor numérico en el Valor");					
			document.frm1.txtSolValor.focus();
			return;
		}
		if (esVacio(document.frm1.txtNumeroAccion.value))
		{
			alert("Debe ingresar el Número");					
			document.frm1.txtNumeroAccion.focus();
			return;
		}

		if (!esEntero(document.frm1.txtNumeroAccion.value))
		{
			alert("Debe ingresar un valor numérico en el Número");					
			document.frm1.txtNumeroAccion.focus();
			return;
		}

	<% } %>
	
	if (esVacio(document.frm1.txtSolLugar.value))
	{
		alert("Debe ingresar el Lugar");					
		document.frm1.txtSolLugar.focus();
		return;
	}
	if (esVacio(document.frm1.txtSolFecha.value))
	{
		alert("Debe ingresar la Fecha");					
		document.frm1.txtSolFecha.focus();
		return;
	}


	if (archivoAdjuntado.style.visibility=="hidden") {
	
 		if (esVacio(document.frm1.txtSolDocumento.value))
			{
				alert("Debe adjuntar el archivo");					
				document.frm1.txtSolDocumento.focus();
				return;
			}
	}
	
	<% if (!session.getAttribute("labelTipoSociedad").equals("E.I.R.L")) { %>
		
		var numAcc = (document.frm1.txtSolMonto.value)/(document.frm1.txtSolValor.value)
		
		if (document.frm1.txtNumeroAccion.value != numAcc) {
		
		<% if (!session.getAttribute("labelTipoSociedad").equals("S.C.R.L")) { %>		

			if (confirm("El número de acciones ingresado no es consistente con el monto capital y el valor de la accion. Desea Corregir?")) {
				document.frm1.txtSolMonto.value = "";
				document.frm1.txtSolValor.value = "";
				document.frm1.txtNumeroAccion.value = "";
				document.frm1.txtSolMonto.focus();
				return;
			}
			
		<% } else { %>

			if (confirm("El número de participaciones ingresado no es consistente con el monto capital y valor de la participación. Desea Corregir?")) {
				document.frm1.txtSolMonto.value = "";
				document.frm1.txtSolValor.value = "";
				document.frm1.txtNumeroAccion.value = "";
				document.frm1.txtSolMonto.focus();
				return;
			}
		
		
		<% } %>			
		}
		
	<% }%>	

	document.frm1.hidMoneda.value = document.frm1.cboSolMoneda.options[document.frm1.cboSolMoneda.selectedIndex].text;
			
	document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerResumenAntesPago";		
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


function modificarParticipante(indice, tipoPersona)
{

	document.frm1.hidMoneda.value = document.frm1.cboSolMoneda.options[document.frm1.cboSolMoneda.selectedIndex].text;

	document.frm1.hidIndiceMod.value = indice;
	document.frm1.hidTipoPersona.value = tipoPersona;	
	document.frm1.action="/iri/ConstitucionEmpresa.do?state=obtenerParticipante";		
	document.frm1.submit();
}

function mostrarPorcentaje(){
	if (document.frm1.rdoSolCancelacion[0].checked==true) {
		document.frm1.txtPorCancelado.disabled = true;		
	}
	else {
		document.frm1.txtPorCancelado.disabled = false;	
		document.frm1.txtPorCancelado.focus();	
	}
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
		<FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Constituci&oacute;n de Empresa
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 1 DE 3</FONT>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario" enctype="multipart/form-data">
<input type="hidden" name="hidIndiceMod" value="">
<input type="hidden" name="hidTipoPersona" value="">
<input type="hidden" name="hidMoneda" value="">
	 
 <table class=tablasinestilo>
    <tr>
      	<th colspan=5><strong>DATOS DE LA PERSONA JURIDICA A CONSTITUIR</strong></th>
    </tr>
    <tr>
    	<td>&nbsp;</td>
      	<td width="114"><strong>DENOMINACI&Oacute;N<BR>
            Y/O<BR>
            RAZ&Oacute;N SOCIAL <font color="900000">*</font></strong></td>
      	<td width="187" colspan="3"><input type="text" name="txtSolRazonSocial" size="20" maxlength="200" style="width:280" onBlur="sololet(this)"></td>
    </tr>
    <tr>
    	<td>&nbsp;</td>
      	<td width="114"><strong>SIGLAS <font color="900000">*</font></strong></td>
      	<td width="187" colspan="3"><input type="text" name="txtSolSiglas" size="20" maxlength="40" style="width:210" onBlur="sololet(this)"></td>
    </tr>
    <tr>
    	<td>&nbsp;</td>
      	<td width="114"><strong>TIPO SOCIEDAD <font color="900000">*</font></strong></td>
      	<td width="187" colspan="3">
      		<% 
      			if (session.getAttribute("flagTipoSociedad")=="S") { %>
				<select size="1" name="cboTipoSociedad" style="width:210">
					<option value="0">&nbsp;</option>
			   		<option value="A">SOCIEDAD AN&Oacute;NIMA ABIERTA</option>
			        <option value="C">SOCIEDAD AN&Oacute;NIMA CERRADA</option>
			    </select>			
			<% } else { %>
				<%=session.getAttribute("labelTipoSociedad")%>
			<% }%>
        </td>
    </tr>
  	<tr>
  		<td  colspan="5"><input type="checkbox" name="txtSolRequiereRUC" value="SI"><strong>TRAMITE ELECTRONICO CON SUNAT</strong></td>
  	</tr>
	<tr>
    	<td colspan="5">&nbsp;</td>
  	</tr>
    <tr>
      	<th colspan=5><strong>CAPITAL</strong></th>
    </tr>
    <tr>
    	<td>&nbsp;</td>
      	<td width="114"><strong>MONEDA <font color="900000">*</font></strong></td>
      	<td width="187">
      		<select name="cboSolMoneda" size="1" style="width:180">
            	<option value="00">&nbsp;</option>
            	<option value="03">NUEVOS SOLES</option>
            	<option value="06">D&OacuteLARES</option>
            	<option value="99">OTROS</option>
        	</select>
        </td>
      	<td width="145"><strong>MTO. CAPITAL <font color="900000">*</font></strong></td>
      	<td width="187"><input type="text" name="txtSolMonto" size="20" maxlength="130" style="width:180"></td>
    </tr>
    <% if (!session.getAttribute("labelTipoSociedad").equals("E.I.R.L")) { %>
    <tr>
    	<td>&nbsp;</td>
      	<% if (session.getAttribute("labelTipoSociedad").equals("S.C.R.L")) { %>
      		<td width="114"><strong>VALOR PARTICIPACI&Oacute;N <font color="900000">*</font></strong></td>
      	<% } else { %>
      		<td width="114"><strong>VALOR ACCION <font color="900000">*</font></strong></td>
      	<% } %>
      	<td width="187"><input type="text" name="txtSolValor" size="20" maxlength="130" style="width:180"></td>
      	<% if (session.getAttribute("labelTipoSociedad").equals("S.C.R.L")) { %>
      		<td width="145"><strong>NO. PARTICIPACIONES <font color="900000">*</font></strong></td>
      	<% } else { %>      		
      		<td width="145"><strong>NO. ACCIONES <font color="900000">*</font></strong></td>
      	<% } %>      		
      	<td width="187"><input type="text" name="txtNumeroAccion" size="20" maxlength="130" style="width:180"></td>
    </tr>
    <% } %>
    <tr>
    	<td>&nbsp;</td>
      	<td width="114"><strong>CANCELACION CAPITAL</strong></td>
      	<td width="187"><input type="radio" name="rdoSolCancelacion" checked value="01" onclick="javascript:mostrarPorcentaje();"><strong>TOTAL</strong>&nbsp;<input type="radio" name="rdoSolCancelacion" value="02" onclick="javascript:mostrarPorcentaje();"><strong>PARCIAL</strong></td>
      	<td width="145"><strong>% CANCELADO</strong></td>
      	<td width="187"><input type="text" name="txtPorCancelado" size="20" maxlength="130" style="width:180" disabled="true"></td>
    </tr>
	<tr>
		<td colspan="5">&nbsp;</td>
  	</tr>
  	<% if (!session.getAttribute("labelTipoSociedad").equals("E.I.R.L")) { %>
    <tr>
    	<th colspan="5"><strong>DATOS DE RESERVA DE DENOMINACI&Oacute;N</strong></th>
    </tr>
    <tr>
    	<td>&nbsp;</td>
      	<td width="114"><strong>AÑO DE TITULO</strong></td>
      	<td width="187"><input type="text" name="txtSolAnhoTitulo" size="20" maxlength="130" style="width:180" onBlur="sololet(this)"></td>
      	<td width="145"><strong>NUMERO DE TITULO</strong></td>
      	<td width="187"><input type="text" name="txtSolNumeroTitulo" size="20" maxlength="130" style="width:180" onBlur="sololet(this)"></td>
    </tr>
	<tr>
    	<td colspan="5">&nbsp;</td>
  	</tr>
  	<% } %>
   	<tr>
   		<th colspan="5"><strong>DATOS DE INSTRUMENTO PUBLICO</strong><font color="900000"><BR>
            (Deber&aacute; adjuntarse el archivo rtf correspondiente al acto registral seleccionado)</font></th>
   	</tr>
   	
    <tr>
        <td colspan="5">
            <table width="100%">
		        <tr>
		          <td width="5">&nbsp;</td>
		          <td width="100"><strong>ESCRITURA PUBLICA</strong></td>
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
		                  		<td align="center" width="65%">
				                    <%=nombreArchivo%>
								</td>
								<td align="center" width="35%">
									<A href="javascript:cambiarArchivo();">Cambiar</A>
								</td>
							</tr>
						  </table>
						  </div>
		                </td>
		                <%-- <td>
  		                  <input type="image" src="images/btn_continuar.gif" onmouseover="#" onmouseOut="#" onClick="#">
		                </td> --%>
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
    	<td>&nbsp;</td>
        <td width="114"><strong>OTROS</strong></td>
      	<td width="187" colspan="3"><textarea name="txtSolOtros" rows="4" cols="50" onBlur="sololet(this)"></textarea></td>
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
		      		<th width="2%" align="center" height="12"><div align="center"><input type="checkbox" name="todos" onclick="javascript:seleccionaTodos()"></div></th>
		      		<th width="2%" align="center"><div align="center">NRO.</div></th>
		      		<th align="center" height="12"><div align="center">TIPO PERSONA</div></th>
		      		<th align="center" height="12"><div align="center">TIPO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NUMERO DOC</div></th>
		      		<th align="center" height="12"><div align="center">NOMBRE/RAZ&Oacute;N SOCIAL</div></th>
		      		<%--<th width="11%" align="center" height="12"><div align="center">TIPO PARTICIPANTE</div></th>--%>
		    	</tr>
		    	<% PersonaNatural personaNatural = null;
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
		      		<td align="center"><input type="checkbox" name="indicesListaPJ" value="<%=i%>"></td>
		      		<td align="center"><%=i+1+(size1)%></td>
		      		<td>Jur&iacute;dica</td>
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
   PersonaJuridica personaJuridica2 = solicitudInscripcion2.getPersonaJuridica();
   Capital capital2 = solicitudInscripcion2.getCapital();
   ReservaMercantil reservaMercantil2 = solicitudInscripcion2.getReservaMercantil();
   java.util.ArrayList instrumentosPublico2 = solicitudInscripcion2.getInstrumentoPublico();
   InstrumentoPublico instrumentoPublico2 = null;
   EscrituraPublica escrituraPublica2 = solicitudInscripcion2.getEscrituraPublica();

   if (personaJuridica2!=null) {
%>
<script LANGUAGE="JavaScript">
	<% if (personaJuridica2.getRazonSocial()!=null) { %>
		document.frm1.txtSolRazonSocial.value       = "<%=personaJuridica2.getRazonSocial()%>";
	<% } %>
	<% if (personaJuridica2.getSiglas()!=null) { %>
		document.frm1.txtSolSiglas.value            = "<%=personaJuridica2.getSiglas()%>";
	<% } %>
	<% if (session.getAttribute("flagTipoSociedad")=="S")  {%>
		doCambiaCombo(document.frm1.cboTipoSociedad, "<%=personaJuridica2.getCodigoTipoSociedadAnonima()%>");
	<% } %>
	<% if ( (personaJuridica2.getIndicadorRUC()!=null) && (personaJuridica2.getIndicadorRUC().equals("SI")) ) { %>
		document.frm1.txtSolRequiereRUC.checked     = true;
	<% } %>
</script>
<% } 
   if (capital2!=null) {
%>
<script LANGUAGE="JavaScript">
	doCambiaCombo(document.frm1.cboSolMoneda, "<%=capital2.getCodigoMoneda()%>");
	
	<% if (capital2.getMontoCapital()!=null) { %>
		document.frm1.txtSolMonto.value = "<%=capital2.getMontoCapital()%>";
	<% } %>

	<% if (capital2.getValor()!=null) { %>
		document.frm1.txtSolValor.value = "<%=capital2.getValor()%>";
	<% } %>

	<% if (capital2.getNumero()!=0) { %>
		document.frm1.txtNumeroAccion.value = "<%=capital2.getNumero()%>";
	<% } %>
	
	<% if ( (capital2.getCodigoCancelacionCapital()!=null) && (capital2.getCodigoCancelacionCapital().equals("01")) ) { %>
		document.frm1.rdoSolCancelacion[0].checked = true;
	<% } else { %>
		document.frm1.rdoSolCancelacion[1].checked = true;
	<% } %>		

	if (document.frm1.rdoSolCancelacion[0].checked==true) {
		document.frm1.txtPorCancelado.disabled = true;		
	}
	else {
		document.frm1.txtPorCancelado.disabled = false;	
	}
	
	<% if (capital2.getPorcentajeCancelado()!=null) { %>
		document.frm1.txtPorCancelado.value = "<%=capital2.getPorcentajeCancelado()%>";
	<% } %>
</script>
<% } 
   if (reservaMercantil2!=null) {
%>
<script LANGUAGE="JavaScript">
	
	<% if (reservaMercantil2.getAnhoTitulo()!=null) { %>
		document.frm1.txtSolAnhoTitulo.value = "<%=reservaMercantil2.getAnhoTitulo()%>";
	<% } %>

	<% if (reservaMercantil2.getNumeroTitulo()!=null) { %>
		document.frm1.txtSolNumeroTitulo.value = "<%=reservaMercantil2.getNumeroTitulo()%>";
	<% } %>

</script>
<% } 
   if (instrumentosPublico2!=null) {
	   instrumentoPublico2 = (InstrumentoPublico)instrumentosPublico2.get(0);
%>
<script LANGUAGE="JavaScript">
	
	<% if (instrumentoPublico2.getLugar()!=null) { %>
		document.frm1.txtSolLugar.value = "<%=instrumentoPublico2.getLugar()%>";
	<% } %>

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
<body>
</html>