<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>

<title>Seleccione forma de Pago</title>

<script language="JavaScript">
function TipoPago()
{
	var x2 = document.frm1.rad[2].checked;
	if (x2==true)
	{
	document.frm1.action="/iri/afiliacion/ventanilla.jsp";	
	document.frm1.submit();
	}
}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<h2 align="center">Afiliaci&oacute;n de Usuario Individual (Paso 3 de 3)</h2>

<form name="frm1" method="POST">

  <center>
<table border="0" width="680">
  <tr>
    <td width="672" colspan="3" bgcolor="#993300">
      <p align="center"><b><font color="#FFFFFF">FELICITACIONES !</font></b></td>
  </tr>
  <tr>
    <td width="103"></td>
    <td width="454">
      <p align="justify">&nbsp;</p>
      <p align="justify">Se ha creado satisfactoriamente la cuenta para el
      usuario individual <b><font color="#990000"><%=request.getAttribute("usr")%></font></b>.</p>
      <p align="justify">Su saldo actual disponible es de <font color="#990000"><b>0</b></font>
      nuevos soles. Para realizar su primer abono escoja una de las siguientes
      formas de pago :</p>
      <table border="0" width="100%">
        <tr>
          <td width="26%"></td>
          <td width="74%"><input type="radio" value="Visa"  name="rad" checked>On
            Line usando Tarjeta de Cr&eacute;dito</td>
        </tr>
        <tr>
          <td width="26%"></td>
          <td width="74%"><input type="radio" value="Wiese"  name="rad">On
            Line usando Tarjeta de D&eacute;bito</td>
        </tr>
        <tr>
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
</center>
</form>
</body>
</html>