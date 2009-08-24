<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="gob.pe.sunarp.extranet.reportes.controller.*" %>
<HTML>
<HEAD>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
	<script language="JavaScript" src="javascript/util.js"></script>
	<title>Impresion Balance de Cajas</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
	
<script language="JavaScript">

	function regresar(){
	
		document.form1.method="POST";
		document.form1.action="/iri/BalanceCajaTesorero.do?state=muestra";
		document.form1.submit();
	}

	function imprimir(){
		botones.style.visibility="hidden";
		window.print();
		botones.style.visibility="visible";
	}

</script>		
	
</HEAD>
<BODY>
<FORM name="form1"></FORM>
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="237"><IMG border="0" src="images/orlclogo.gif"
				width="235" height="92"></TD>
			<td align="center" style="black" width="228"><strong><font size="3">BALANCE DE CAJA</font></strong></td>
			<TD width="151">
			<TABLE border="0">
				<TBODY>
					<TR>
						<TD width="96">Fecha</TD>
						<TD width="102"><bean:write name="fechaActual"/></TD>
					</TR>
					<TR>
						<TD width="96">Hora </TD>
						<TD width="102"><bean:write name="horaActual"/></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="139">Edificio</TD>
			<TD width="476"><bean:write name="descEdificio"/></TD>
		</TR>
		<TR>
			<TD width="139">Descripci&oacute;n</TD>
			<TD width="476">Caja</TD>
		</TR>
		<TR>
			<TD width="139">Cajero(a)</TD>
			<TD width="476"><bean:write name="userIdCajero"/> - <bean:write name="nombres"/> <bean:write name="apPaterno"/> <bean:write name="apMaterno"/></TD>
		</TR>
		<TR>
			<TD width="139">Fecha/Hora Apertura</TD>
			<TD width="476"><bean:write name="fechaAperturaCaja"/></TD>
		</TR>
		<TR>
			<TD width="139">Fecha/Hora Cierre</TD>
			<TD width="476"><bean:write name="fechaCierreCaja"/></TD>
		</TR>
	</TBODY>
</TABLE>
<table cellspacing=0 class=titulo width="622">
    <tr>
	<td></td>
  	</tr>
</table>
<table class=titulo>
<tr><td>
       <div align="center"> <P><FONT color=black>Detalle del Efectivo</FONT></P></DIV>
</td></tr>
</table>
<TABLE border="0" >
	<TBODY>
		<TR>
			<TD width="596" height="34"><div align="center">Saldo Total: S/. <bean:write name="totalAbonos"/></div></TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE cellspacing=0 width="610">
    <tr>
		<td colspan ="4">&nbsp;</td>
  	</tr>
	<tr>
		<td colspan="4">
		  	<hr>
		<td>
	</tr>  	
  	<tr>
  		<td>&nbsp;</td>
  		<td><strong>MONEDAS</strong></td>
  		<td><div align = "center"><strong> Unidades</strong></div></td>
  		<td><div align = "center"><strong>Monto Total</strong></div></td>
  	</tr>
	<tr>
		<td colspan="4">
		  	<hr>
		<td>
	</tr>  	
 
	<logic:iterate name="listaMonedas" id="moneda">
	  	<tr>
		  	<td><bean:write name="moneda" property="codigo"/></td>
		  	<td><bean:write name="moneda" property="descripcion"/></td>
		  	<td><div align = "right"><bean:write name="moneda" property="unidades"/></div></td>
		  	<td><div align = "right"><bean:write name="moneda" property="montoAsString"/></div></td>
	  	</tr>
	</logic:iterate> 

	<tr>
		<td colspan="4">
		  	<hr>
		<td>
	</tr>  	
  	<tr>
	  	<td>&nbsp;</td>
	  	<td><strong>BILLETES</strong></td>
	  	<td><div align = "center"><strong> Unidades</strong></div></td>
	  	<td><div align = "center"><strong>Monto Total</strong></div></td>
  	</tr>
	<tr>
		<td colspan="4">
		  	<hr>
		<td>
	</tr>  	
	<logic:iterate name="listaBilletes" id="billete">
	  	<tr>
		  	<td><bean:write name="billete" property="codigo"/></td>
		  	<td><bean:write name="billete" property="descripcion"/></td>
		  	<td><div align = "right"><bean:write name="billete" property="unidades"/></div></td>
		  	<td><div align = "right"><bean:write name="billete" property="montoAsString"/></div></td>
	  	</tr>
	</logic:iterate> 
	<tr>
		<td colspan="4">
		  	<hr>
		<td>
	</tr>  	
  	<tr>
	  	<td >&nbsp;</td>
	  	<td align="right"><strong>Total Efectivo:&nbsp;&nbsp;</strong></td>
	  	<td></td>
	  	<td align="right">S/. <bean:write name="montoTotalBilletaje"/></td>
  	</tr>
  	<tr>
		<td colspan="4" height="35"></td>
	</tr>
  	<tr>
		<td colspan="2" height="6">Importe Total Recaudado por Caja S./</td>
		<td height="6"></td>
		<td height="6"></td>
	</tr>
	<tr>
		<td colspan="2">
		  	<hr>
		<td>
		<td colspan="2"><td>
	</tr>  	
  	<tr>
	  	<td>Efectivo:</td>
	  	<td align="right"><bean:write name="totalEfectivo"/></td>
	  	<td></td>
	  	<td></td>
  	</tr>
  	<tr>
	  	<td>Total cheque:</td>
	  	<td align="right"><bean:write name="totalCheque"/></td>
	  	<td></td>
	  	<td></td>
  	</tr>
	<tr>
		<td colspan="2">
		  	<hr>
		<td>
		<td colspan="2"><td>
	</tr>  	
  	<tr>
	  	<td>Total:</td>
	  	<td align="right">S/. <bean:write name="totalAbonos"/></td>
	  	<td></td>
	  	<td></td>
  	</tr>
  	<tr>
		<td colspan="4" height="37"></td>
	</tr>
  	<tr>
	  	<td height="4">Observaciones</td>
		<td colspan="3"><hr></td>
	</tr>
  	<tr>
	  	<td></td>
  		<td colspan="3"><hr></td>
  	</tr>
  	<tr>
	  	<td></td>
		<td colspan="3"><hr></td>
	</tr>
  	<tr>
	  	<td></td>
		<td colspan="3"><hr></td>
	</tr>

  	<tr>
		<td colspan="4" height="33">&nbsp;</td>
	</tr>
  	<tr>
		<td colspan="2" height="12">
			<div align="center">____________________</div>
		</td>
		<td colspan="2" height="12">
			<div align="center">____________________</div>
		</td>
	</tr>
  	<tr>
	  	<td colspan="2"><div align="center">Cajero(a)</div></td>
	  	<td colspan="2"><div align="center">Recaudador</div></td>
  	</tr>
  	<tr>
		<td colspan="4" height="33"></td>
	</tr>
  	<tr>
		<td colspan="3" height="14"></td>
		<td height="14">
			<DIV id="botones">
				<A href="javascript:regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		  				<IMG border="0" src="images/btn_regresa.gif" width="83" height="25"></A>
				<A href="javascript:imprimir();">
						<IMG border="0" src="images/btn_print.gif" width="83" height="25"></A>	
			</DIV>
		</td>
  	</tr>
</table>
</BODY>
</HTML>
