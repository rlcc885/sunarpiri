<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<html>
<head>
<title>FrmConSol</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
<LINK REL="stylesheet" type="text/css" href="../../styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>
<script>
function buscar(){
	document.frm1.action="/iri/Denominacion.do?state=consultaEstadoSolic";		
	document.frm1.submit();
}
</script>
<body>
<br>
  <table cellspacing=0 class=titulo>
	<tr>
		<td><FONT COLOR="black">SERVICIOS <font size="1">&gt;&gt;</font></FONT><font
			color="900000"> Consulta de estado de solicitudes <font size="1">&gt;&gt;</font>
		Reserva de Estado de solicitudes</FONT></td>
	</tr>
</table>
<br>
  <table class=cabeceraformulario  cellspacing=0>      
    <tr> 
      <td bordercolor="AFAFAF" bgcolor="D7D7D7"><strong><font color="132E4C">Por 
        N&uacute;mero de Hoja de Presentacion y A&ntilde;o</font></strong></td>
    </tr>
  </table>
  <form name="frm1" method="post" class="formulario">
  <table class=formulario cellspacing=0>      
    <tr> 
      <td align="center" bordercolor="AFAFAF" bgcolor="F6F6F6" ><table width="95%" border="0" cellspacing="2" cellpadding="1">
          <tr bordercolor="#0000FF"> 
            <td align="center">A&ntilde;o :</td>
            <td>
              <select size="1" name="cboAnio">
                
                <option value="2003">2003</option>
                <option value="2004">2004</option>
                <option value="2005">2005</option>
                <option value="2006">2006</option>
                <!-- Inicio:rbahamonde:30/12/2008 Cambio de Año -->
                <option value="2007">2007</option>
                <option value="2008">2008</option>
                <option value="2009" selected>2009</option>
                <!-- Fin:rbahamonde -->
              </select>
            </td>
            <td align="right">&nbsp; N&uacute;mero de   Hoja de Presentaci&oacute;n (ejm V0000100):</td>
            <td><input type="text" maxlength="8" name="numSolic" onblur="sololet(this)"></td>
            <td align="center">
            <a href="javascript:buscar();"><IMG src="<%=request.getContextPath()%>/images/btn_buscar.gif" border="0"></A>
            </td>
          </tr>
        </table>
    
        </td>
    </tr>
  </table>
  </form>
</body>
</html>