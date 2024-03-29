<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="../javascript/util.js"></script>
	<title>Formulario Reserva de Preferencia Registral - Datos Reserva</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<script language="javascript">
	function validarFormulario() {
		if (document.frm.razonSocial.value == "") {
			alert("Datos Incorrectos. Debe de llenar el campo Raz�n Sosial.");
			document.frm.razonSocial.focus();
			return false;
		}
		if (document.frm.siglas.value == "") {
			alert("Datos Incorrectos. Debe de llenar el campo Siglas.");
			document.frm.siglas.focus();
			return false;
		}
		if (document.frm.direccion.value == "") {
			alert("Datos Incorrectos. Debe de llenar el campo Direcci�n.");
			document.frm.direccion.focus();
			return false;
		}
		if (document.frm.tipoSociedad.value == "9999") {
			alert("Datos Incorrectos. Debe de seleccionar el Tipo de Sociedad.");
			document.frm.tipoSociedad.focus();
			return false;
		}
		
		return true;
	}

	function continuar() {
		if(validarFormulario()){
			document.frm.method="POST";
			document.frm.action="/iri/SolicitudInscripcion.do?state=almacenaDatosReserva";
			document.frm.submit();
	    }
	}
</script>
<body>
	<br>
	<table cellspacing="0" class="titulo">
		<tr>
			<td><FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n</FONT></td>
  		</tr>
	</table>
	<br>
	<form name="frm" method="POST" class="formulario">
	<table class="tablasinestilo">
    	<tr>
        	<th colspan="5">DATOS DE LA PERSONA JUR&Iacute;DICA A RESERVAR</th>
    	</tr>
    	<tr>
    		<td width="5">&nbsp;</td>
    		<td width="150"><b>DENOMINACI&Oacute;N Y O RAZ&Oacute;N SOCIAL</b></td>
      		<td><input type="text" name="razonSocial" maxlength="100" style="width:350">&nbsp;<b>(*)</b></td>
        	<td width="65" colspan="2"></td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>SIGLAS</b></td>
        	<td><input type="text" name="siglas" maxlength="10" style="width:80"></td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
        	<td>&nbsp;</td>
      		<td width="150" height="38"><b>TIPO DE SOCIEDAD</b></td>
      		<td>
				<select size="1" name="tipoSociedad" style="width:180">
			   		<option value="9999">Tipo 1</option>
			   		<option>Tipo 1</option>
			        <option>Tipo 2</option>
			        <option>Tipo 3</option>
			        <option>Tipo 4</option>
			    </select>			
			</td>
  			<td colspan="2">&nbsp;</td>
  		</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>DIRECCI&Oacute;N</b></td>
        	<td><input type="text" name="direccion" maxlength="100" style="width:350"></td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>FECHA DE SOLICITUD</b></td>
        	<td>17/08/2006<input type="hidden" name="fechaSolicitud"></td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
    		<td align="right" colspan="5"><a href=""><image src="../images/btn_continuar.gif"></a></td>
    	</tr>
  	</table>
	</form>
	<br>
    <table>
    	<tr>
     		<td width="596"><font size="1"><b>(*)&nbsp;Nota:&nbsp;La&nbsp;Raz&oacute;n&nbsp;Social&nbsp;no&nbsp;debe&nbsp;de&nbsp;incluir&nbsp;el&nbsp;tipo&nbsp;de&nbsp;Sociedad&nbsp;(ej.&nbsp;ABC&nbsp;en&nbsp;lugar&nbsp;de&nbsp;ABC&nbsp;S.A.)</b></font></td>
        </tr>
	</table>
</html>

