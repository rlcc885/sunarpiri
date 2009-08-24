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
<BODY>
<script language="javascript">
function calcula(){
	if (document.frm1.cboMoneda.options[document.frm1.cboMoneda.selectedIndex].value == "01")
	{
		valor = document.frm1.txtTranSoles.value;
		document.frm1.txtTranDolar.value = Math.round((document.frm1.txtTranSoles.value/document.frm1.txtTipoCambio.value)*100)/100;
	
	} else {
		document.frm1.txtTranSoles.value = Math.round((document.frm1.txtTranDolar.value*document.frm1.txtTipoCambio.value)*100)/100;
		valor = document.frm1.txtTranSoles.value;
	}
	nveces = document.frm1.txtNroVeces.value;
	
	formula = new String("<%=request.getAttribute("tasa")%>");
	
	indice_div = formula.indexOf("/"); 
	indice_mul = formula.indexOf("*");
	
	if (indice_div < indice_mul) {
		num1 = formula.substring(0,indice_div);
		num2 = formula.substring(indice_div+1,indice_mul);
		res = (num1/num2)*valor*nveces;
	}	
	else {
		res = 0.00;
	}

	valorMinimo = <%=(String)request.getAttribute("valorMinimo")%>;  

	if ( res  < valorMinimo )
		res = valorMinimo;

	document.frm1.txtDerechoInscripcion.value = Math.round(res*100)/100;
	
	return;
}

function cambiaMoneda(){
	if (document.frm1.cboMoneda.options[document.frm1.cboMoneda.selectedIndex].value == "01")
	{
		document.frm1.txtTranDolar.value= "0.00";
		document.frm1.txtTranDolar.disabled = true;
		document.frm1.txtTranSoles.disabled = false;	
		document.frm1.txtTranSoles.value = "";
		document.frm1.txtTranSoles.focus();
	}else{
		document.frm1.txtTranSoles.value= "0.00";
		document.frm1.txtTranSoles.disabled = true;	
		document.frm1.txtTranDolar.disabled = false;
		document.frm1.txtTranDolar.value = "";	
		document.frm1.txtTranDolar.focus();
	}
	
	document.frm1.txtDerechoInscripcion.value = "";
}

function Aceptar(){
	document.frm1.hidMoneda.value = document.frm1.cboMoneda.options[document.frm1.cboMoneda.selectedIndex].text;

	document.frm1.txtTranSoles.disabled = false;	
	document.frm1.txtTranDolar.disabled = false;
			
	document.frm1.action="/iri/CalculadoraRegistral.do?state=aceptarCalculo";		
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
</script>
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
<input type="hidden" name="hidIndice" value="<%=request.getAttribute("indice")%>">
<input type="hidden" name="hidCodArea" value="<%=request.getAttribute("codArea")%>">
<input type="hidden" name="hidCodLibro" value="<%=request.getAttribute("codLibro")%>">
<input type="hidden" name="hidMoneda" value="">

  	<table class=tablasinestilo>
    	<tr>
      		<th colspan="2">INGRESO DE DATOS PARA EL C&Aacute;LCULO DEL DERECHO DE INSCRIPCI&Oacute;N </th>
    	</tr>

    	<tr>
            <td colspan=2 height="28">
	            <TABLE border="0">
	                <TBODY>
    	
				    	<tr>
				            <td width="230"><strong>TASA POR DERECHO DE INSCRIPCI&Oacute;N</td>
				            <td width="250"><input type="text" name="txtTasaDerechoInscripcion" size="20" maxlength="130" style="width:225" value="<%=request.getAttribute("tasa")%>" disabled></td>
				        </tr>
				    	<tr>
				            <td width="230"><strong>MONEDA EN QUE SE EFECT&Uacute;A LA TRANSACCI&Oacute;N</td>
				            <td width="250">
					      		<select name="cboMoneda" size="1" style="width:225" onchange="javascript:cambiaMoneda();">
					            		<option value="01">NUEVOS SOLES</option>
					            		<option value="02">DOLARES</option>
					            </select>
				      		</td>
				        </tr>
	                </TBODY>
	            </TABLE>
            </td>
		</tr>     
        
    	<tr>
            <td height="28" width="250">
	            <TABLE border="0">
	                <TBODY>
	                    <TR>
	                        <TD width="105"><strong>TIPO DE CAMBIO</strong></TD>
	                        <TD><INPUT type="text" name="txtTipoCambio" size="20" maxlength="130" style="width:120" value="<%=request.getAttribute("tipoCambio")%>"></TD>
	                    </TR>
	                </TBODY>
	            </TABLE>
            </td>
            <td height="28" width="250">
	            <TABLE border="0">
	                <TBODY>
	                    <TR>
	                        <TD width="115"><strong>TRANSACCI&Oacute;N EN $</TD>
	                        <TD><INPUT type="text" name="txtTranDolar" size="20" maxlength="130" style="width:120" onblur="javascript:calcula()"></TD>
	                    </TR>
	                </TBODY>
	            </TABLE>
            </td>
        </tr>
    	<tr>
            <td height="28" width="250">
	            <TABLE border="0">
	                <TBODY>
	                    <TR>
	                        <TD width="105"><strong>U.I.T</strong></TD>
	                        <TD><INPUT type="text" name="txtUIT" size="20" maxlength="130" style="width:120" value="<%=request.getAttribute("uit")%>" disabled></TD>
	                    </TR>
	                </TBODY>
	            </TABLE>
            </td>
            <td height="28" width="250">
	            <TABLE border="0">
	                <TBODY>
	                    <TR>
	                        <TD width="115"><strong>TRANSACCI&Oacute;N S/</TD>
	                        <TD><INPUT type="text" name="txtTranSoles" size="20" maxlength="130" style="width:120" onblur="javascript:calcula()"></TD>
	                    </TR>
	                </TBODY>
	            </TABLE>
            </td>
        </tr>
    	<tr>
            <td width="250">
	            <TABLE border="0">
	                <TBODY>
	                    <TR>
	                        <TD width="105"><strong>NRO. VECES</strong></TD>
	                        <TD><INPUT type="text" name="txtNroVeces" size="20" maxlength="130" style="width:120" value="<%=request.getAttribute("nroveces")%>" onblur="javascript:calcula()"></TD>
	                    </TR>
	                </TBODY>
	            </TABLE>
            </td>
            <td width="250">
	            <TABLE border="0">
	                <TBODY>
	                    <TR>
	                        <TD width="115"><font color="900000"><strong>DERECHO DE INSCRIPCI&Oacute;N<strong></font></TD>
	                        <TD><INPUT type="text" name="txtDerechoInscripcion" size="20" maxlength="130" style="width:120" readonly></TD>
	                    </TR>
	                </TBODY>
	            </TABLE>
            </td>
        </tr>
    	<tr>
      		<td colspan="2"></td>
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
<script language="javascript">
	
	doCambiaCombo(document.frm1.cboMoneda, "<%=request.getAttribute("moneda")%>");

	cambiaMoneda();	

	<% if (request.getAttribute("montoDolar")!=null) { %>
			document.frm1.txtTranDolar.value = "<%=request.getAttribute("montoDolar")%>";
	<% } %>

	<% if (request.getAttribute("montoSoles")!=null) { %>
			document.frm1.txtTranSoles.value = "<%=request.getAttribute("montoSoles")%>";
	<% } %>

	calcula();
			
</script>
</BODY>
</HTML>
