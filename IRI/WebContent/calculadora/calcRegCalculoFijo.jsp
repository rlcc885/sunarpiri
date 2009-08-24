<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.calculadora.bean.*" %>
<HTML>
<head>
	 <title>Calculadora Registral - Nro. Veces</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head>
<script language="javascript">
function Aceptar(){		
	document.frm1.action="/iri/CalculadoraRegistral.do?state=aceptarCalculo";		
	document.frm1.submit();
}
</script>
<BODY>
<br>
<TABLE cellspacing=0 class=titulo>
    <TBODY>
        <TR>
            <TD width="258">Calculadora Registral</TD>
        </TR>
    </TBODY>
</TABLE>
<BR>
<FORM name="frm1" method="POST" class="formulario">
<input type="hidden" name="hidIndice" value="<%=request.getAttribute("indice")%>">
<input type="hidden" name="hidCodArea" value="<%=request.getAttribute("codArea")%>">
<input type="hidden" name="hidCodLibro" value="<%=request.getAttribute("codLibro")%>">
  	<table class=tablasinestilo>
    	<tr>
      		<th colspan="2">CAMBIO DE N&Uacute;MERO DE VECES</th>
    	</tr>
    	<tr>
    	<td width="30%">NRO. DE VECES</td>
      		<td width="70%"><input type="text" name="txtNroVeces" size="20" maxlength="130" style="width:180" value="<%=request.getAttribute("nroveces")%>">
      	</td>
    	</tr>
    	<tr>
    		<td colspan="2" align="center">
    			<A href="javascript:Aceptar();" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
					<IMG src="images/btn_aceptar.gif" border="0">
				</A>
  	    	</td>
    	</tr>
    	</table>
    	
</FORM>
</BODY>
</HTML>
