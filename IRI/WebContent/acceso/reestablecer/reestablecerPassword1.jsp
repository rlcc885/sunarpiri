<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="Javascript" >
function Paso2(){ 
	if(validarformulario())
	{
	document.form1.method="POST";
	document.form1.action="/iri/ReestablecerContrasena.do?state=identificacionCuenta";
	document.form1.submit();
	return true;
	}
	return false;
}
function validarformulario()
{
	if(esVacio(document.form1.user.value.length))
	{
		alert("Por favor ingrese el Nombre de Usuario");
		document.form1.user.focus();
		return false;
	}

	if(esVacio(document.form1.DNI.value) || !esEntero(document.form1.DNI.value) || !esLongitudMayor(document.form1.DNI.value,8)){
		alert("Por favor ingrese el Número de Documento de Identidad.");
		document.form1.DNI.focus();
		return false;
	
	}
	if(esVacio(document.form1.ApePat.value)){
		alert("Por favor ingrese el Apellido Paterno.");
		document.form1.ApePat.focus();
		return false;
	
	}
	if(esVacio(document.form1.Nombres.value)){
		alert("Por favor ingrese el Ingrese el Nombre.");
		document.form1.Nombres.focus();
		return false;
	}
	if(esVacio(document.form1.email.value) || !esEmail(document.form1.email.value) || !contieneCarateresValidos(document.form1.email.value,"correo")){
		alert("Por favor ingrese el Correo Electrónico.");
		document.form1.email.focus();
		return false;
	}	
	document.form1.user.value= mayuscula(document.form1.user);
	document.form1.ApePat.value = mayuscula(document.form1.ApePat);
	document.form1.ApeMat.value = mayuscula(document.form1.ApeMat);		
	document.form1.Nombres.value = mayuscula(document.form1.Nombres);
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
<table width="100%" height="76" border="0" class="tablasinestilo">
  <tr> 
    <td colspan="4" height="32" bgcolor="#993300" >
    <p align="center">
    <font style="font-family:Arial,Verdana,Tahoma,Geneva; font-weight: bold;font-size: 12px;color:white;text-align:center;vertical-align:middle">
    RESTABLECIMIENTO DE CONTRASE&Ntilde;A (Paso 1/2)
    </font>
    </p>    
    </td>
  </tr>
  <tr> 
    <td height="14" colspan="4">Ingrese los siguientes datos tal cual fueron ingresados en el formulario de Inscripci&oacute;n al servicio de la Extranet<br></td>
  </tr>
  <tr> 
    <td width="112" height="14" >Usuario ID</td>
    <td width="164" height="14" > <input type="text" name="user" size="20" maxlength="13" value="" style="width:130" onblur="solonumlet(this)"></td>
    <td width="164" height="14">Num Doc Identidad</td>
    <td height="14"><input type="text" name="DNI" size="16" maxlength="13" value="" style="width:100" onkeydown="solonum(this)" ></td>
  </tr>
  <tr> 
    <td width="112" height="14">Apellido Paterno</td>
    <td width="164" height="14" ><input type="text" name="ApePat" size="20" maxlength="30" value="" style="width:130" onblur="sololet(this)" ></td>
    <td width="164" height="14">Apellido Materno</td>
    <td width="146" height="14"><input type="text" name="ApeMat" size="20" maxlength="30" value="" style="width:130" onblur="sololet(this)" ></td>
  </tr>
  <tr> 
    <td width="112" height="14">Nombres</td>
    <td width="164" height="14" ><input type="text" name="Nombres" size="20" maxlength="40" value="" style="width:130" onblur="obj_mayuscula(this)"></td>
    <td width="164" height="14">Correo Electr&oacute;nico</td>
    <td height="14"><input type="text" name="email" size="20" maxlength=40" value="" style="width:130"></td>
  </tr>
  <tr> 
    <td height="14" align=center colspan=4>
    <input type="image" src="<%=request.getContextPath()%>/images/btn_continuar.gif" style="border:0" onClick="return Paso2()">
	</td>
  </tr>
</table>
</form>

</body>
</html>