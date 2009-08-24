<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso.tld" prefix="expresso"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="javascript"> 
function Guardar()
	{
	 <logic:equal name="mensaje"  value="vencio" >
		if(esVacio(document.form1.actualPass.value))
		{
			alert("Por favor ingrese la contraseña actual");
			document.form1.actualPass.focus();
			return;		
		}
	 </logic:equal>
	if(esVacio(document.form1.newPass.value) || 
	   !contieneCarateresValidos(document.form1.newPass.value,"usuariopassword") || 
	   !esLongitudEntre(document.form1.newPass.value,6,10) || 
	   !contieneNumero(document.form1.newPass))
	{
		alert("Por favor ingrese correctamente la Contrasena.\nLa Contrasena debe contener entre 6 y 10 caracteres.\nLa Contrasena debe contener al menos un caracter numerico (0-9)");
		document.form1.newPass.focus();
		return;		
	}
	
		
	if (document.form1.newPass.value!=document.form1.conPass.value)
		{	
		alert("La Contrasena no esta verificada");
		document.form1.conPass.focus();
		return;		
	}		
	if (document.form1.pregSecret.value=="0")
		{	
			alert("Seleccione una Pregunta Secreta");
			document.form1.pregSecret.focus();
			return;		
		}

	if(esVacio(document.form1.rpta.value))
		{
			alert("Ingrese por favor la Respuesta Secreta");
			document.form1.rpta.focus();
			return;		
		}	
	if (!esLongitudEntre(document.form1.rpta.value,5,30))
		{	
			alert("La Respuesta Secreta debe contener entre 5 y 30 caracteres");
			document.form1.rpta.focus();
			return;		
		}
 <%String strNPerfil=(String) request.getAttribute("nperfil");
 if(strNPerfil!=null && strNPerfil.equalsIgnoreCase("externo")){%>
 if(document.form1.cboCaducidad.value == -1){
		alert("Debe seleccionar la caducidad de la contraseña");
		document.frm1.cboCaducidad.focus();
		return;
	}
 <%}%>
  <logic:equal name="mensaje"  value="vencio" >
  if (document.form1.newPass.value==document.form1.actualPass.value)
		{	
		alert("La nueva contraseña debe ser diferente a la contraseña actual");
		document.form1.conPass.focus();
		return;		
	}
	obj_mayuscula(document.form1.actualPass);
   </logic:equal>
   
   
	obj_mayuscula(document.form1.rpta);
	obj_mayuscula(document.form1.conPass);
	obj_mayuscula(document.form1.newPass);
	document.form1.submit();
} 
</script>

</head>
<BODY>
<br>
<form name="form1" action="/iri/Ingreso.do" METHOD=post>
<table border="0" width="100%" class="tablasinestilo">
  <tr>
    <td width="5%">&nbsp;</td>
    <th width="60%" colspan="3" style="background-color:dddddd">CAMBIO DE CONTRASEÑA</th>
    <td width="5%">&nbsp;</td>
  </tr>
  <tr>
    <td width="5%">&nbsp;</td>
    <td width="60%" colspan="3">
    <logic:equal name="mensaje"  value="primera" >
    
    <span lang="ES-PE" style="mso-fareast-font-family: Times New Roman; mso-ansi-language: ES-PE; mso-fareast-language: EN-US; mso-bidi-language: AR-SA">Se ha detectado que
      esta es la primera sesi&oacute;n que ud. inicia en la extranet. Por razones
      de seguridad se le solicitar&aacute; que cambie su contrase&ntilde;a, e ingrese una
      pregunta y respuesta secreta en caso de olvido.</span>
      </logic:equal>
    <logic:equal name="mensaje"  value="vencio" >
    <span lang="ES-PE" style="mso-fareast-font-family: Times New Roman; mso-ansi-language: ES-PE; mso-fareast-language: EN-US; mso-bidi-language: AR-SA">Se ha detectado que
      su contraseña ha caducado. Por razones
      de seguridad se le solicitar&aacute; que cambie su contrase&ntilde;a, e ingrese una
      pregunta y respuesta secreta en caso de olvido.</span>
    </logic:equal>
    </td>
    <td width="5%">&nbsp;</td>
  </tr>
  <logic:equal name="mensaje"  value="vencio" >
    <tr>
    <td width="5%">&nbsp;</td>
    <td width="28%" align="right">CONTRASE&Ntilde;A ACTUAL</td>
    <td width="2%">&nbsp;</td>
    <td width="30%"><input type="password" name="actualPass" maxlength="10" size="10" style="width:133" onblur="sololet(this)">
    <logic:present name="nperfil" >
  	 <logic:equal name="nperfil"  value="interno" >    
	   <span lang="ES-PE" class="textorojoclaro"> * </span>
	 </logic:equal>
	</logic:present>
    </td>
    <td width="5%">&nbsp;</td>
  </tr>
  </logic:equal>
  <tr>
    <td width="5%">&nbsp;</td>
    <td width="28%" align="right">NUEVA CONTRASE&Ntilde;A</td>
    <td width="2%">&nbsp;</td>
    <td width="30%"><input type="password" name="newPass" maxlength="10" size="10" style="width:133" onblur="sololet(this)">
    <logic:present name="nperfil" >
  	 <logic:equal name="nperfil"  value="interno" >    
	   <span lang="ES-PE" class="textorojoclaro"> * </span>
	 </logic:equal>
	</logic:present>
    </td>
    <td width="5%">&nbsp;</td>
  </tr>
  <tr>
    <td width="5%">&nbsp;</td>
    <td width="28%" align="right">CONFIRME CONTRASE&Ntilde;A</td>
    <td width="2%">&nbsp;</td>
    <td width="30%"><input type="password" name="conPass" maxlength="10" size="10" style="width:133" onblur="sololet(this)">
     <logic:present name="nperfil" >
  	 <logic:equal name="nperfil"  value="interno" >    
	   <span lang="ES-PE" class="textorojoclaro"> * </span>
	 </logic:equal>
	</logic:present>
    </td>
    <td width="5%">&nbsp;</td>
  </tr>
  <tr>
    <td width="5%"></td>
    <td width="28%" align="right">Seleccione una preguna secreta :</td>
    <td width="2%"></td>
    <td width="30%"><select size="1" name="pregSecret" style="width:187">
        <option selected value="0">Seleccione una pregunta</option>
        <expresso:ElementCollection type="output">
   		<expresso:ElementIterator>
	        <expresso:OutputTag name="PregSec"> 
	            <option value= "<expresso:AttributeTag name="ID"/>"><expresso:AttributeTag name="Desc"/></option>
	        </expresso:OutputTag> 
     	</expresso:ElementIterator>
     	</expresso:ElementCollection>
      </select></td>
    <td width="5%"></td>
  </tr>
  <tr>
    <td width="5%"></td>
    <td width="28%" align="right">Respuesta a la Pregunta Secreta :</td>
    <td width="2%"></td>
    <td width="30%"><input name="rpta" size="25" maxlength="30" style="width:133" onblur="sololet(this)"></td>
    <td width="5%"></td>
  </tr>
  <tr>
 
<logic:present name="nperfil" >
   <logic:equal name="nperfil"  value="externo" >
    <td width="5%"></td>
    <td width="28%" align="right">Caducidad de la contraseña :</td>
    <td width="2%"></td>
    <td width="30%">
    <select size="1" name="cboCaducidad" style="width:133">
    	<option selected value="-1">  </option>
      	<logic:iterate name="arrCaducidad" id="caducidad" scope="request">
        	<option value="<bean:write name="caducidad" property="atributo1"/>"><bean:write name="caducidad" property="descripcion"/></option>
        </logic:iterate>
	</select>  
    </td>
    <td width="5%"></td>
   </logic:equal>
   <logic:equal name="nperfil"  value="interno" >
    <td width="5%"></td>
    <td colspan="3" align="left">
   <span lang="ES-PE" class="textorojoclaro"> (*) La contraseña modificada tiene una vigencia de <bean:write name="nDiasCad"/> días </span></td>
    <td width="5%"><input type="hidden" name="ndias" value="<bean:write name="nDiasCad"/>"></td>
   </logic:equal>
</logic:present>
  </tr>
  <tr>
    <td width="5%"></td>
    <td width="28%" align="right"></td>
    <td width="2%"></td>
    <td width="30%"></td>
    <td width="5%"></td>
  </tr>
  <tr>
    <td width="5%"></td>
    <td width="28%" align="right"></td>
    <td width="2%"><a href="javascript:Guardar()"><img src="images/btn_aceptar.gif" border=0></a><!--<input type="button" value="Aceptar" name="boton1" onclick="Guardar()">--></td>
    <td width="30%"></td>
    <td width="5%"></td>
  </tr>
</table>
<input type="hidden" name="state" value="cambiaPassword">

</form>
</BODY>
</html>
