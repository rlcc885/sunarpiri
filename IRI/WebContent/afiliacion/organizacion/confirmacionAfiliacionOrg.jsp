<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title>Seleccione forma de Pago</title>

<script language="JavaScript">
function IrPaginaPrincipal()
{
top.location.href = "/iri/acceso/displayLogin.html"
}
</script>

</head>

<body>
<br>
<table class="titulo">
  <tr>
    <td>Afiliaci&oacute;n de Organizaci&oacute;n</td>
  </tr>
</table>
<br>
<form name=form1 class="formulario">

<table class="tablasinestilo" >
  <tr>
    <td width="672" colspan="3" bgcolor="#993300">
    <p align="center">
    <font style="font-family:Arial,Verdana,Tahoma,Geneva; font-weight: bold;font-size: 13px;color:white;text-align:center;vertical-align:middle">
	FELICITACIONES !
    </font>
    </p>    
    </td>
  </tr>
  <tr>
    <td width="59"> 
    &nbsp;
    </td>  
    <td width="554" > 
      <br>
      <br>      
      Se ha creado satisfactoriamente la cuenta <font color="#990000"><b><%=request.getAttribute("usuario")%></b></font> correspondiente al administrador de la organizaci&oacute;n <b><font color="#990000"><%=request.getAttribute("organizacion")%></font>.</b>
      <br>  
      <br>            
      El saldo actual disponible para su organizaci&oacute;n es de <font color="#990000"><b>0</b></font> nuevos soles.
      <br>  
      <br>                    
      Para entrar al Sistema por favor presione en continuar para ir a la pagina principal del sistema e identif&iacute;quese con el usuario y clave que recien ha creado.
      <br>
      <br>
   </td>
    <td width="59"> 
    &nbsp;    
    </td>  
  </tr>
</table>

<center>

            <table width="100%" border="0">
              <tr> 
                <td width="74%"><div align="center">&nbsp; 
                    <a href="javascript:IrPaginaPrincipal();"><img src="images/btn_continuar.gif"  border=0 onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
                 </td>
              </tr>
            </table>

</center>
</form>
</body>
</html>