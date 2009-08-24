<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<title>Seleccione forma de Pago</title>
<script language="JavaScript">
function validarformulario(){
	if(esVacio(document.form1.monto.value)){
		alert("Datos Incorrectos. Ingrese el Monto en Nuevos Soles");
		document.form1.monto.focus();
		return false;
	}

	if(isNaN(document.form1.monto.value)){
		alert("Datos Incorrectos. Ingrese el Monto en Nuevos Soles");
		document.form1.monto.focus();		
		return false;
	}
	
	if(document.form1.monto.value.indexOf(".")>=0){
		alert("Datos Incorrectos. Ingrese el Monto en Nuevos Soles sin centavos");
		document.form1.monto.focus();		
		return false;
	}
	
	if(document.form1.monto.value<4){
		alert("Datos Incorrectos. Ingrese un Monto superior a S/. 4.00");
		document.form1.monto.focus();		
		return false;
	}
	return true;
}
function TipoPago(){ 
	if(validarformulario()){
	document.form1.method = "POST";
   	document.form1.action = "/iri/IncrementarSaldo.do?state=solicitaDatos";
   	document.form1.submit();
    }
}

function Cancela()
{
	 window.open ("../bienvenida.html",target="_self")
}
function nueva()
{
  window.open("http://www.visanet.com.pe/promovbv/bancos.html","nueva","status=yes resizable=yes");
}

</script>
</head>

<body>
<table width="600" border="0" cellpadding="0" cellspacing="2">
  <tr> 
    <td><b>PREPAGOS<font size="1"></font> <font color="#90000">Incremento de Saldo</font></b></td>
  </tr>
  <tr> 
    <td bgcolor="#000000"><img src="../images/space.gif" width="5" height="1"></td>
  </tr>
</table>
<br>
<form name="form1" onSubmit="return TipoPago()">
<table width="500" border="1" bordercolor="#FFFFFF">
  <tr align="center"> 
	<td width="65" rowspan=3>&nbsp;</td>
	<td align="left">
	  <a href="<%=request.getContextPath()%>/prepagos/verVisa.html" target="_blank">
	    <img src="images/verVisa.gif">
	  </a>
    </td>
  </tr>
  <tr align="center"> 
	
	<td><!--Para realizar un Prepago en linea <br> escoja una de los siguientes tarjetas:--><b>Formas de Pago<br></b></td>
  </tr>
  <tr align="center" bordercolor="C4C9CD" bgcolor="F0F0F0"> 
	<td>
	  <table border="0" width="80%">
		  <tr> 
			<td width="26%" align="center">
			  <a href = "http://www.visanet.com.pe" target="_blank">
			    <img src="images/visagrande.jpg" width="109" height="70" vspace="6">
			  </a>
			</td>
			<td width="74%"><input type="radio" value="11"  name="R1" checked>Tarjeta de Cr&eacute;dito VISA </td>
		  </tr>
		  <tr>
			<td width="26%">&nbsp;Ingrese el Monto </td>
			<td width="74%"><input name="monto" maxlength="8"> Nuevos Soles.</td>
		  </tr>              
		</table>
	</td>
  </tr>
<table>
<font face="Verdana" size="1">Para mayor seguridad, adicione una contraseña personal al pago de nuestro servicio y evite el uso </br> 
no autorizado de su tarjeta. Afilie su tarjeta a <a href="http://www.visanet.com.pe/promovbv/bancos.html"> Verified by Visa. </font>
</table>
  <tr align="right">
	<td></td>
	<td>
	  <br>
	    <a href="javascript:TipoPago()">
	      <img src="images/btn_continuar.gif" border="0">	
	    </a>
	</td>
 </tr>
</table>
</form>
</body>
</html>