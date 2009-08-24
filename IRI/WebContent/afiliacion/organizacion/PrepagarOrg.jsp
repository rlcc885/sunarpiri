<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>

<title>Seleccione forma de Pago</title>
<script language="JavaScript">
function TipoPago()
{
	for(i=0;document.frm1.rad.length;i++)
	{
		if (document.frm1.rad[i].checked)
		{
		document.frm1.action="/iri/AbonoVentanilla.do";	
		document.frm1.submit();
		}else{
			if (document.frm1.rad[i].checked)
			{
			document.frm1.action="/iri/IncrementarSaldo.do";	
			document.frm1.submit();
			}
		}
	}
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<center>
<h2 align="center">Afiliaci&oacute;n de Usuario Individual (Paso 3 de 3)</h2>
<form name="frm1" method="POST">
<table border="0" width="680">
  <tr>
    <td width="672" colspan="3" bgcolor="#993300">
      <p align="center"><b><font color="#FFFFFF">FELICITACIONES !</font></b></td>
  </tr>
  <tr>
    <td width="103"></td>
    <td width="454">
	  <p>
      Se ha creado satisfactoriamente la cuenta para el
      usuario <b><font color="#990000"><%=request.getAttribute("usuario")%></font></b>.
      correspondiente al administrador  de la organizaci&oacute;n <%=request.getAttribute("organizacion")%>
      <p>
      Su saldo actual disponible es de <font color="#990000"><b>0 </b></font>nuevos soles.
      <p>
      Para realizar su primer abono escoja una de las siguientes formas de pago :     
    </td>
    <td width="103"></td>		
  </tr>
  <tr>
    <td width="103"></td>
    <td width="454">
      <table border="0" width="100%">
        <tr>
          <td width="26%"></td>
          <td width="74%"><input type="radio" value="Visa"  name="rad" checked>On
			Line usando Tarjeta de Cr&eacute;dito o Tarjeta de D&eacute;bito</td>
        </tr>
.       <tr>
          <td width="26%">&nbsp;</td>
          <td width="74%"><input type="radio" value="Ventanilla"  name="rad">Pagar
            luego en nuestras ventanillas</td>
        </tr>
        <tr>
          <td width="26%"></td>
          <td width="74%">&nbsp;
            <p><input type="reset" value="Cancelar"><input type="button" value="Continuar" onClick="TipoPago()"></p>
          </td>
        </tr>
      </table>    
    </td>
    <td width="103"></td>
  </tr>
</table>
</form>
</center>
</body>
</html>