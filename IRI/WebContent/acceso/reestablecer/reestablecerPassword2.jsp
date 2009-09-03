<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="Javascript">
function Restablecer(){
	if(validarformulario())
	{
	document.form1.respuesta.value= document.form1.respuesta.value.toUpperCase();
	document.form1.npass.value=document.form1.npass.value.toUpperCase();
	document.form1.cpass.value=document.form1.cpass.value.toUpperCase();
	document.form1.method="POST";
	document.form1.action="/iri/ReestablecerContrasena.do?state=reestablecePassword";
	document.form1.submit();
	return true;
	}
	return false;
}
function validarformulario()
{
	if(esVacio(document.form1.respuesta.value)){
		alert("Por favor ingrese correctamente la Respuesta Secreta.");
		document.form1.respuesta.focus();
		return false;
	
	}
	if(esVacio(document.form1.npass.value)){
		alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numerico");
		document.form1.npass.focus();
		return false;
	
	}	
	if(!contieneCarateresValidos(document.form1.npass.value,"usuariopassword")){
		alert("Por favor ingrese correctamente la Contraseña.\nLa Contrasena debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico");
		document.form1.npass.focus();
		return;		
	}
	if (document.form1.npass.value.length<6 || document.form1.npass.value.length>10)
	{
		alert("Por favor ingrese correctamente la Contraseña.\nLa Contrasena debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico");
		document.form1.npass.focus();
		return false;
	}
	
	if(!contieneNumero(document.form1.npass)){
		alert("Por favor ingrese correctamente la Contraseña.\nLa Contrasena debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico");
		document.form1.npass.focus();
		return;		
	}	
	
	if(document.form1.cpass.value.length<6 || document.form1.cpass.value.length>10)
	{
		alert("La confirmación de contraseña debe tener una longitud entre 6 y 10 caracteres");
		document.form1.cpass.focus();
		return false;
	}
	
	if (document.form1.npass.value.toUpperCase() != document.form1.cpass.value.toUpperCase())
	{	
		alert("La contraseña no está confirmada");
		document.form1.cpass.focus();
		return false;		
	}	
		
	return true;	
}

</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table class="titulo"><tr><td>Contrase&ntilde;a</td></tr></table>
<br>
<form name="form1" class="formulario">
<table width="100%"  border="0" class="tablasinestilo">
  <tr> 
    <td width="71%" colspan="5" height="32" bgcolor="#993300">
    <p align="center">
    <font style="font-family:Arial,Verdana,Tahoma,Geneva; font-weight: bold;font-size: 12px;color:white;text-align:center;vertical-align:middle">RESTABLECIMIENTO DE CONTRASE&Ntilde;A (Paso 2/2)
    </font>
    </p>     
	</td>
  </tr>
  <tr> 
    <td width="71%" height="12" colspan="5">Responda a la pregunta secreta que suministr&oacute; durante su inscripci&oacute;n</td>
  </tr>
  <tr> 
    <td width="14%" height="13">&nbsp;</td>
    <td width="17%" height="13" align="right">Pregunta Secreta</td>
    <td width="4%" height="13">&nbsp;</td>
    <td width="21%" height="13"><%=request.getAttribute("pregSecreta")%></td>
    <td width="15%" height="13">&nbsp;</td>
  </tr>
  <tr> 
    <td width="14%" height="13">&nbsp;</td>
    <td width="17%" height="13" align="right">Respuesta</td>
    <td width="4%" height="13">&nbsp;</td>
    <td width="21%" height="13"><input name="respuesta" size="20" onblur="sololet(this)" style="width:80"></td>
    <td width="15%" height="13">&nbsp;</td>
  </tr>
  <tr> 
    <td width="14%" height="13">&nbsp;</td>
    <td width="17%" height="13" align="right">Nueva Contrase&ntilde;a</td>
    <td width="4%" height="13">&nbsp;</td>
    <td width="21%" height="13"><input type="password" name="npass" size="10" maxlength=10 style="width:80" onblur="sololet(this)"></td>
    <td width="15%" height="13">&nbsp;</td>
  </tr>
  <tr> 
    <td width="14%" height="13">&nbsp;</td>
    <td width="17%" height="13" align="right">Confimar Contrase&ntilde;a</td>
    <td width="4%" height="13">&nbsp;</td>
    <td width="21%" height="13"><input type="password" name="cpass" size="10" maxlength=10 style="width:80" onblur="sololet(this)"></td>
    <td width="15%" height="13">&nbsp;</td>
  </tr>
  <tr> 
    <td width="14%" height="12">&nbsp;</td>
    <td width="42%" height="12" colspan="3" align="center"><br>
	  <input type="image" src="<%=request.getContextPath()%>/images/btn_continuar.gif" style="border:0" onClick="return Restablecer()">    
    </td>
    <td width="15%" height="12">&nbsp;</td>
  </tr>
</table>
<input type="hidden" name="userId" value="<%=request.getAttribute("userId")%>">
</form>
</body>
</html>