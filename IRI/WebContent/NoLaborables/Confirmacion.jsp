<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
	<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
	<title>Confirmaci&oacute;n D&iacute;as no laborables</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<script type="text/javascript">
function IrPaginaPrincipal()
{
	history.go(-2)
}
</script>
<body>
<br>
<table class="titulo">
  <tr>
    <td>Dias no laborables</td>
  </tr>
</table>
<br>
<form name=form1 class="formulario">

<table class="tablasinestilo" >
  <tr>
    <td width="672" colspan="3" bgcolor="#993300">
         <p align="center">
    <font style="font-family:Arial,Verdana,Tahoma,Geneva; font-weight: bold;font-size: 13px;color:white;text-align:center;vertical-align:middle">
	CONFIRMACION !
    </font>
    </p> 
  </tr>
  <tr>
    <td width="59"> 
    &nbsp;
    </td>    
    <td width="554" > 
      <br>
      <br>   
      Se ha ingresado satisfactoriamente el dia laboral
      <b><font color="#990000"></font></b>.
      <br>  
      <br>        
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
                    <a href="javascript:IrPaginaPrincipal();"><img src="<%=request.getContextPath()%>/images/btn_continuar.gif"  border=0 onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
                 </td>
              </tr>
            </table>

</center>
</form>
</body>
</html>

