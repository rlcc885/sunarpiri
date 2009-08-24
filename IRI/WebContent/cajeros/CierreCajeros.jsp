<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.acceso.bean.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
<head>
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
</head>
<script>
function Cerrar(){
	
	
		var recaudEfectivo= Math.round(document.frm1.txtRecaudoEfectivo.value*100)/100;
		var monto= Math.round(document.frm1.txtMontoBilletaje.value*100)/100;
			if(recaudEfectivo != monto){
				alert("La cantidad del Recaudo Efectivo no coincide con el Monto del Billetaje");
			}else {
			var p = confirm("¿Esta seguro que desea Cerrar la caja\t"+"<%=request.getAttribute("nombreCaja")%>"+"?");
			if (p == true)
			document.frm1.action="/iri/CierreCajero.do?state=cierreCaja";
			document.frm1.submit();
			}
}

 function Calculos() {
 //Para billetes de 200
 var param200= Math.round(document.frm1.txtDoscientos.value*100)/100;
 var montoDoscientos = Math.round( (param200 * 200)*100)/100;
 document.frm1.txtMontoBillete1.value = montoDoscientos;
 
 //Para billetes de 100
 var param100= Math.round(document.frm1.txtCien.value*100)/100;
 var montoCien = Math.round( (param100 * 100)*100)/100;
 document.frm1.txtMontoBillete2.value = montoCien;
 
 //Para billetes de 50
 var param50= Math.round(document.frm1.txtCincuenta.value*100)/100;
 var montoCincuenta = Math.round( (param50 * 50)*100)/100;
 document.frm1.txtMontoBillete3.value = montoCincuenta;
 
 //Para billetes de 20
 var param20 = Math.round(document.frm1.txtVeinte.value*100)/100;
 var montoVeinte = Math.round( (param20 * 20)*100)/100;
 document.frm1.txtMontoBillete4.value = montoVeinte;
 
 //Para billetes de 10
 var param10 = Math.round(document.frm1.txtDiez.value*100)/100;
 var montoDiez = Math.round( (param10 * 10)*100)/100;
 document.frm1.txtMontoBillete5.value = montoDiez;
 
 //para el total de billetes
 var totalBilletes = montoDoscientos + montoCien + montoCincuenta + montoVeinte + montoDiez ;
 document.frm1.txtTotalBilletes.value = totalBilletes;
 
 //para monedas de 5 soles
 var mone5 = Math.round(document.frm1.txtCincons.value*100)/100;
 var montCinco = Math.round( (mone5 * 5)*100)/100;
 document.frm1.txtMontoMoneda1.value = montCinco;
 
 //para monedas de 2 soles
 var mone2 = Math.round(document.frm1.txtDosns.value*100)/100;
 var montDos = Math.round( (mone2 * 2)*100)/100;
 document.frm1.txtMontoMoneda2.value = montDos;
 
 //para monedas de Un nuevo soles
 var mone1 = Math.round(document.frm1.txtUnns.value*100)/100;
 var montUnns = Math.round( (mone1 * 1)*100)/100;
 document.frm1.txtMontoMoneda3.value = montUnns;
 
 //para monedas de 50 centimos
 var mone50c = Math.round(document.frm1.txtCincuentac.value*100)/100;
 var mont50c = Math.round( (mone50c * 0.50)*100)/100;
 document.frm1.txtMontoMoneda4.value = mont50c;
 
 //para monedas de 20 centimos
 var mone20c = Math.round(document.frm1.txtVeintec.value*100)/100;
 var mont20c = Math.round( (mone20c * 0.20)*100)/100;
 document.frm1.txtMontoMoneda5.value = mont20c;
 
 //para monedas de 10 centimos
 var mone10c = Math.round(document.frm1.txtDiezc.value*100)/100;
 var mont10c = Math.round( (mone10c * 0.10)*100)/100;
 document.frm1.txtMontoMoneda6.value = mont10c;
 
 //para monedas de 5 centimos
 var mone5c = Math.round(document.frm1.txtCincoc.value*100)/100;
 var mont5c = Math.round( (mone5c * 0.05)*100)/100;
 document.frm1.txtMontoMoneda7.value = mont5c;
 
 //para el total de monedas
 var totalMonedas = Math.round( (montCinco + mont10c + mont50c + mont20c + montUnns + montDos + mont5c )*100 )/100;
 document.frm1.txtTotalMonedas.value = totalMonedas;
 
 //para el resumen
 var recaudEfectivo = Math.round(document.frm1.txtRecaudoEfectivo.value*100)/100;
 var recaudCheque = Math.round(document.frm1.txtRecaudoCheque.value*100)/100;
 var saldoFinal = recaudEfectivo + recaudCheque;
 document.frm1.txtSaldoFinal.value = recaudEfectivo;
 document.frm1.txtTotalEfectivo.value = saldoFinal;
 
 //para el total del monto del billetaje
 var totalBilletaje = Math.round( (totalBilletes + totalMonedas)*100)/100;
 document.frm1.txtMontoBilletaje.value = totalBilletaje;

}
function esNumeroEntero(str){
	var regex=/^-?[0-9]+$/;
	
	if(str!=null)	
		  return regex.test(str);
	else
		return false;
}
function Validacion()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtDoscientos.value)==false)
{
document.frm1.txtDoscientos.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtDoscientos.value = 0;
document.frm1.txtMontoBillete1.value = 0;
return false;
}
}
return true;
}
function Validacion2()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtCien.value)==false)
{
document.frm1.txtCien.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtCien.value = 0;
document.frm1.txtMontoBillete2.value = 0;
return false;
}
}
return true;
}
function Validacion3()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtCincuenta.value)==false)
{
document.frm1.txtCincuenta.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtCincuenta.value = 0;
document.frm1.txtMontoBillete3.value = 0;
return false;
}
}
return true;
}
function Validacion4()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtVeinte.value)==false)
{
document.frm1.txtCincuenta.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtVeinte.value = 0;
document.frm1.txtMontoBillete4.value = 0;
return false;
}
}
return true;
}
function Validacion5()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtDiez.value)==false)
{
document.frm1.txtDiez.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtDiez.value = 0;
document.frm1.txtMontoBillete5.value = 0;
return false;
}
}
return true;
}
//validaciones para las monedas no permita decimales
function Validacion6()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtCincons.value)==false)
{
document.frm1.txtCincons.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtCincons.value = 0;
document.frm1.txtMontoMoneda1.value = 0;
return false;
}
}
return true;
}
function Validacion7()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtDosns.value)==false)
{
document.frm1.txtDosns.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtDosns.value = 0;
document.frm1.txtMontoMoneda2.value = 0;
return false;
}
}
return true;
}
function Validacion8()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtUnns.value)==false)
{
document.frm1.txtUnns.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtUnns.value = 0;
document.frm1.txtMontoMoneda3.value = 0;
return false;
}
}
return true;
}
function Validacion9()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtCincuentac.value)==false)
{
document.frm1.txtCincuentac.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtCincuentac.value = 0;
document.frm1.txtMontoMoneda4.value = 0;
return false;
}
}
return true;
}
function Validacion10()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtVeintec.value)==false)
{
document.frm1.txtVeintec.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtVeintec.value = 0;
document.frm1.txtMontoMoneda5.value = 0;
return false;
}
}
return true;
}
function Validacion11()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtDiezc.value)==false)
{
document.frm1.txtDiezc.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtDiezc.value = 0;
document.frm1.txtMontoMoneda6.value = 0;
return false;
}
}
return true;
}
function Validacion12()
{
//Si el valor no es un enter, es suspen el Submit i es crida el Input
if (esNumero(document.frm1.txtCincoc.value)==false)
{
document.frm1.txtCincoc.focus();
return false;
}
}
function esNumero(cadena)
{
k="0123456789 ";
for (j=0;j<cadena.length;j++)
{
if (k.indexOf(cadena.charAt(j)) == -1)
{
alert(" Por favor escriba un número entero.");
document.frm1.txtCincoc.value = 0;
document.frm1.txtMontoMoneda7.value = 0;
return false;
}
}
return true;
}
</script>

<br>
<table cellspacing=0 class=titulo>
   <tr>
	<td>
		<FONT COLOR="black">CAJA <font size="1">&gt;&gt;</font></FONT><font color="900000">Cerrar Caja</FONT></td>
  </tr>
</table>
<br>
<table>
	<tr>
		<td><strong>Fecha Apertura:</strong></td>
		<% if( request.getAttribute("fechaAperturaCajero")!=null){%>
		<td><%= request.getAttribute("fechaAperturaCajero")%></td>
		<% }%>
	</tr>
</table>
<form name="frm1" method="POST" class="formulario">
<table  class="grilla" >
				<!--<tr>
		      		<td colspan="6"></td>
		    	</tr>-->
		    	
		    	<tr>
		      		<th width="95" height="11"><div align="center">BILLETE</div></th>
		      		<th width="80" height="11"><div align="center">CANTIDAD</div></th>
		      		<th width="30" height="11"><div align="center">MONTO</div></th>
		      		<th width="95" height="11"><div align="center">MONEDA</div></th>
		      		<th width="30" height="11"><div align="center">CANTIDAD</div></th>
		      		<th width="30" height="11"><div align="center">MONTO</div></th>
		    	</tr>
		    	<tr>
		    	<td width="95">Doscientos  Soles</td>
		    	<td width="80"><INPUT type="text" name="txtDoscientos" size="15"
				onblur="Calculos(); Validacion()" value="0"></td>
		    	<td width="30"><INPUT type="text" name="txtMontoBillete1" size="15" readonly value="0"></td>
		    	<td width="95">Cinco  Soles</td>
		    	<td width="30"><INPUT type="text" name="txtCincons" size="15" onblur="Calculos();Validacion6()" value="0"></td>
		    	<td width="30"><INPUT type="text" name="txtMontoMoneda1" size="15" readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td width="95">Cien  Soles</td>
		    	<td width="80"><INPUT type="text" name="txtCien" size="15" onblur="Calculos(); Validacion2()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoBillete2" size="15" readonly value="0"></td>
		    	<td width="95">Dos  Soles</td>
		    	<td width="106"><INPUT type="text" name="txtDosns" size="15" onblur="Calculos();Validacion7()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoMoneda2" size="15" readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td width="95">Cincuenta  Soles</td>
		    	<td width="80"><INPUT type="text" name="txtCincuenta" size="15" onblur="Calculos(); Validacion3()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoBillete3" size="15" readonly value="0"></td>
		    	<td>Un Sol</td>
		    	<td width="106"><INPUT type="text" name="txtUnns" size="15" onblur="Calculos();Validacion8()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoMoneda3" size="15" readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td width="95">Veinte  Soles</td>
		    	<td width="80"><INPUT type="text" name="txtVeinte" size="15" onblur="Calculos();Validacion4()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoBillete4" size="15" readonly value="0"></td>
		    	<td width="95">Cincuenta Centimos</td>
		    	<td width="106"><INPUT type="text" name="txtCincuentac" size="15" onblur="Calculos();Validacion9()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoMoneda4" size="15" readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td width="95">Diez  Soles</td>
		    	<td width="80"><INPUT type="text" name="txtDiez" size="15" onblur="Calculos();Validacion5()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoBillete5" size="15" readonly value="0"></td>
		    	<td width="95">Veinte Centimos</td>
		    	<td width="106"><INPUT type="text" name="txtVeintec" size="15" onblur="Calculos();Validacion10()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoMoneda5" size="15" readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td colspan ="3">&nbsp;</td>
		    	<td width="95">Diez Centimos</td>
		    	<td width="80"><INPUT type="text" name="txtDiezc" size="15" onblur="Calculos();Validacion11()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoMoneda6" size="15" readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td colspan ="3">&nbsp;</td>
		    	<td width="95">Cinco Centimos</td>
		    	<td width="80"><INPUT type="text" name="txtCincoc" size="15" onblur="Calculos();Validacion12()" value="0"></td>
		    	<td><INPUT type="text" name="txtMontoMoneda7" size="15"readonly value="0"></td>
		    	</tr>
		    	<tr>
		    	<td width="66">&nbsp;</td>
		    	<td width="80">Total Billetes</td>
		    	<td background="0"><INPUT type="text" name="txtTotalBilletes" size="15" readonly value="0.00"></td>
		    	<td width="116">&nbsp;</td>
		    	<td width="106">Total Monedas</td>
		    	<td><INPUT type="text" name="txtTotalMonedas" size="15" readonly value="0.00"></td>
		    	</tr>
		    	<tr>
		    	<td colspan="6">&nbsp;</td>
		    	</tr>
		    	<tr>
		    	<td width="95">Saldo Inicial</td>
		    	<td width="107"><INPUT type="text" value="0.00"name="txtSaldoInicial"size="15" readonly>
		    	</td>
		    	<td>Total Efectivo</td>
		    	<td width="116"><INPUT type="text" name="txtTotalEfectivo" size="15" readonly value="0"></td>
		    	<td width="106"></td>
		    	<td></td>
		    	</tr>
		    	<tr>
		    	<td width="95">Recaudo Efectivo</td>
		    	<%if (request.getAttribute("efectivo")!=null) {%>
		    	<td width="30"><INPUT type="text" value="<%=request.getAttribute("efectivo")%>" name="txtRecaudoEfectivo" size="15" readonly></td>
		    	<%} else {%>
		    	<td><INPUT type="text" name="txtRecaudoEfectivo" size="15"></td>
		    	<% }%>
		    	<td>Monto Billetaje</td>
		    	<td><INPUT type="text" value="0.00" name="txtMontoBilletaje" size="15"></td>
		    	<td colspan="2"></td>
		    	</tr>
		    	<tr>
		    	<td width="95">Recaudo Cheque</td>
		    	<%if (request.getAttribute("cheque")!=null) {%>
		    	<td width="30"><INPUT type="text" value="<%=request.getAttribute("cheque") %>" name="txtRecaudoCheque" size="15" readonly></td>
		    	<%} else {%>
		    	<td><INPUT type="text" value="0.00" name="txtRecaudoCheque" size="15"></td>
		    	<% }%>
		    	<td colspan="4"></td>
		    	</tr>
		    	<tr>
		    	<td colspan = "6">&nbsp;</td>
		    	</tr>
		    	<tr>
		    	<td width="95">Saldo Final</td>
		    	<td width="30"><INPUT type="text" name="txtSaldoFinal" size="15" readonly></td>
		    	</tr>
		    	<tr>
		    	<td colspan="6">&nbsp;</td>
		    	</tr>
		    	<tr>
		    	<td colspan="6"><div align="center">
		    	<A href="javascript:Cerrar();" onmouseover="javascript:mensaje_status('Cerrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		    	<IMG border="0" src="images/btn_cerrar.gif" ></div></td>
		    	</tr>		    	

</table>
</form>

</HTML>
