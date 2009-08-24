<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>

<head>
	 <title>Formulario Calculadora Registral - Inicio</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head>

<script language="javascript">
function Validar() 
{

	if (esVacio(document.frm1.txtTipoCambioDia.value))
	{
		alert("Debe ingresar el Tipo de Cambio");
		document.frm1.txtTipoCambioDia.focus();
		return
	}
	if (esVacio(document.frm1.txtMontoUIT.value))
	{
		alert("Debe ingresar el monto U.I.T");
		document.frm1.txtMontoUIT.focus();
		return
	}
	if (!esDecimal(document.frm1.txtTipoCambioDia.value))
	{
		alert("Debe ingresar un valor numérico en el Tipo de Cambio");					
		document.frm1.txtTipoCambioDia.focus();
		return;
	}
	if (!esDecimal(document.frm1.txtMontoUIT.value))
	{
		alert("Debe ingresar un valor numérico en el Monto U.I.T");					
		document.frm1.txtMontoUIT.focus();
		return;
	}	
			
	document.frm1.action="/iri/CalculadoraRegistral.do?state=mostrarDatosCalculadora";		
	document.frm1.submit();

}

</script>

<BODY>
<BR>

<TABLE cellspacing=0 class=titulo>
    <TBODY>
        <TR>
            <TD width="258">Calculadora Registral</TD>
        </TR>
    </TBODY>
</TABLE>
<BR>
<FORM name="frm1" method="POST" class="formulario">
  	<table class=tablasinestilo>
    	<tr>
      		<th colspan="2">INICIALIZACI&Oacute;N DE VARIABLES</th>
    	</tr>
    	<tr>
    		<td width="30%"><strong>TIPO DE CAMBIO DEL D&Iacute;A</strong></td>
      		<td width="75%"><input type="text" name="txtTipoCambioDia" size="20" maxlength="130" style="width:180"></td>
    	</tr>
    	<tr>
    		<td width="30%"><strong>MONTO DE LA  U.I.T</strong></td>
      		<td width="75%"><input type="text" name="txtMontoUIT" size="20" maxlength="130" style="width:180"></td>
    	</tr>
    	<tr>
    		<td colspan="2" width="100%" align="center">
    			<A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<IMG src="images/btn_aceptar.gif" border="0">
				</A>
	  		</td>  		
    	</tr>    	
    	</table>
    	
</FORM>

</BODY>
</HTML>