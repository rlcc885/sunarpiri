<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso.tld" prefix="expresso"%>
<%@ taglib uri="/WEB-INF/tld/expresso-logic.tld" prefix="logic"%>
<html>
<head>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="javascript"> 
function Guardar(){ 
	alert("Se ha actualizado con exito la contrasena")
	document.form1.submit();
} 
</script>

</head>
<BODY>
<form name="form1" action="/iri/Ingreso.do">
<table border="0" width="100%">
  <tr>
    <td width="20%">&nbsp;</td>
    <th width="60%" colspan="3">PRIMER INICIO DE SESI&oacute;N</th>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%">&nbsp;</td>
    <td width="60%" colspan="3">
    	<span lang="ES-PE" style="mso-fareast-font-family: Times New Roman; mso-ansi-language: ES-PE; mso-fareast-language: EN-US; mso-bidi-language: AR-SA">Se ha detectado que
    		esta esta es la primera sesi&oacute;n que ud. inicia en la extranet. Por razones de seguridad se le solicitar&aacute; que cambie su password, e ingrese una pregunta y respuesta secreta en caso de olvido.
    	</span></td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%">&nbsp;</td>
    <td width="28%" align="right">NUEVA CONTRASE&Ntilde;A</td>
    <td width="2%">&nbsp;</td>
    <td width="30%"><input type="password" name="newPass" size="10"></td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%">&nbsp;</td>
    <td width="28%" align="right">CONFIRME NUEVA CONTRASE&Ntilde;</td>
    <td width="2%">&nbsp;</td>
    <td width="30%"><input type="password" name="conPass" size="10"></td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%"></td>
    <td width="28%" align="right">Seleccione una preguna secreta :</td>
    <td width="2%"></td>
    <td width="30%"><select size="1" name="pregSecret">
        <option selected value="0">Seleccione una pregunta</option>
        <expresso:ElementCollection type="output">
   		<expresso:ElementIterator>
	        <expresso:OutputTag name="PregSec"> 
	            <option value= "<expresso:AttributeTag name="ID"/>"><expresso:AttributeTag name="Desc"/></option>
	        </expresso:OutputTag> 
     	</expresso:ElementIterator>
     	</expresso:ElementCollection>
      </select></td>
    <td width="20%"></td>
  </tr>
  <tr>
    <td width="20%"></td>
    <td width="28%" align="right">Respuesta a la Pregunta Secreta :</td>
    <td width="2%"></td>
    <td width="30%"><input name="rpta" size="25"></td>
    <td width="20%"></td>
  </tr>
  <tr>
    <td width="20%"></td>
    <td width="28%" align="right"></td>
    <td width="2%"></td>
    <td width="30%"></td>
    <td width="20%"></td>
  </tr>
  <tr>
    <td width="20%"></td>
    <td width="28%" align="right"></td>
    <td width="2%"><input type="button" value="Aceptar" name="boton1" onclick="Guardar()"></td>
    <td width="30%"></td>
    <td width="20%"></td>
  </tr>
</table>
<input type="hidden" name="state" value="cambiaPassword">
</form>
</BODY>
</html>
