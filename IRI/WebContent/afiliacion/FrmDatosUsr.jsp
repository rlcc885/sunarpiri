<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- 
	PANTALLA PARA AFILIACION de usuarios
--%>

<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.afiliacion.bean.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>

<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>

<script language="javascript">
/*
arreglo hijo (provincia, que depende del combo padre DEPARTAMENTO)

	Id Provincia,  Descripcion Provincia,  Id Departamento
*/
var arr2 = new Array();

<% int k = 0; %>
<logic:iterate name="arr_hijo1" id="itemp" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="itemp" property="value01"/>"; //id provincia
	arrx[1]="<bean:write name="itemp" property="value02"/>"; //descripcion provincia
	arrx[2]="<bean:write name="itemp" property="value03"/>"; //id departamento
	arr2[<%=k%>]=arrx;
	<%  k++; %>
</logic:iterate>

function llenaDepProv()
{
obj1 = document.frm1.combo2;
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPais=obj1.options[i].value;			
				break;
			}
	}
	
if (codigoPais=="01"){
document.frm1.combo3.disabled=false;
document.frm1.combo4.disabled=false;
document.frm1.txt5.disabled=true;
}else{
document.frm1.combo3.disabled=true;
document.frm1.combo4.disabled=true;
document.frm1.txt5.disabled=false;
}	

}
function llenaComboHijo()
{

var obj1;
var obj2;

obj1 = document.frm1.combo3;  //papa
obj2 = document.frm1.combo4;  //hijo

//obtener codigo de papa
var codigoPapa ="";
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa=obj1.options[i].value;
				break;
			}
	}

//limpiar combo hijo
if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length ; ++i)
			{
				obj2.options[i]=null;
						--i;
			}
    }
    
//llenar combo hijo con informacion de acuerdo al Id de combo padre
		//TUTTI!!!!!!			objeto.options[objeto.options.length] = new Option("<TUTTI>","<TUTTI>");
var x0;
var x1;
var x2;			

for (var j=0; j<arr2.length; j++)
		{
			x0 = arr2[j][0];
			x1 = arr2[j][1];
			x2 = arr2[j][2];
			if (x2 == codigoPapa)
				obj2.options[obj2.options.length] = new Option(x1,x0);
		}

} // function llenaComboHijo	




function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}













function cancela()
{
	window.open ("/iri/acceso/displayLogin.html", target="_top");
}

function continua()
{
//validaciones
if (esVacio(document.frm1.txt1.value) || !contieneCarateresValidos(document.frm1.txt1.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Apellido Paterno");
		document.frm1.txt1.focus();
		return;
	}	
if (!esVacio(document.frm1.txt2.value)  && !contieneCarateresValidos(document.frm1.txt2.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Apellido Materno");
		document.frm1.txt2.focus();
		return;
	}	

if (esVacio(document.frm1.txt3.value) || !contieneCarateresValidos(document.frm1.txt3.value,"nombre"))
	{	
		alert("Por favor ingrese correctamente el Nombre");
		document.frm1.txt3.focus();
		return;
	}
if (esVacio(document.frm1.txt4.value) || !esEntero(document.frm1.txt4.value) || !esLongitudMayor(document.frm1.txt4.value,8) || !esEnteroMayor(document.frm1.txt4.value,1))
	{	
		alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
		document.frm1.txt4.focus();
		return;
	}	

		
//if (esLongitudEntre(document.frm1.txt4.value,8,))
//	{	
//		alert("Por favor ingrese correctamente el numero de documento");
//		document.frm1.txt4.focus();
//		return;
//	}	

if(document.frm1.combo3.value==""){
	if (esVacio(document.frm1.txt5.value))
		{	
			alert("Por favor ingrese el Departamento");
			document.frm1.txt5.focus();
			return;
		}
}
	
/*
29oct
distrito es requerido solamente si el pais es peru
*/	
if (document.frm1.combo2.value=="01")
{
	if (esVacio(document.frm1.txt7.value) || !contieneCarateresValidos(document.frm1.txt7.value,"numeronombre"))
		{	
			alert("Por favor ingrese correctamente el Distrito");
			document.frm1.txt7.focus();
			return;
		}	
}
if (esVacio(document.frm1.txt8.value) )
	{	
		alert("Por favor ingrese correctamente la Dirección completa");
		document.frm1.txt8.focus();
		return;
	}	
	

if(!esVacio(document.frm1.txt10.value)){	
	if(!contieneCarateresValidos(document.frm1.txt10.value,"telefono") || !esLongitudEntre(document.frm1.txt10.value,6,30))	
	{
			alert("Por favor ingrese correctamente el Número de Teléfono.\nEl Número de Teléfono debe contener mas de 6 caracteres.\nEl Número de Teléfono puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.txt10.focus();
			return;
	
	}
}


if(!esVacio(document.frm1.anexo.value)){	
	if(!contieneCarateresValidos(document.frm1.anexo.value,"telefono"))	
	{
			alert("Por favor ingrese correctamente el Número de Anexo.\nEl Número de Anexo puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.anexo.focus();
			return;
	
	}
}

if(!esVacio(document.frm1.txt11.value)){		
	if(!contieneCarateresValidos(document.frm1.txt11.value,"telefono") || !esLongitudMayor(document.frm1.txt11.value,6))	
	{
			alert("Por favor ingrese correctamente el Número de Fax.\nEl Número de Fax debe contener mas de 6 caracteres.\nEl Numero de Fax puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.txt11.focus();
			return;
	
	}
}		
if (esVacio(document.frm1.txt12.value) || !esEmail(document.frm1.txt12.value) || !contieneCarateresValidos(document.frm1.txt12.value,"correo"))
	{	
		alert("Por favor ingrese correctamente el Correo Electrónico.\nEl Correo Electrónico puede contener caracteres alfanuméricos (A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
		document.frm1.txt12.focus();
		return;
	}	

if (!esLongitudEntre(document.frm1.txt13.value,6,13) || !contieneCarateresValidos(document.frm1.txt13.value,"usuariopassword") || esEntero(extraeDer(document.frm1.txt13.value,3)))

	{	
		alert("Por favor ingrese correctamente el Usuario.\nEl Usuario debe contener entre 6 y 13 caracteres.\nLos 3 últimos caracteres del Usuario no deben ser numéricos (0-9)");
		document.frm1.txt13.focus();
		return;		
	}

//valida password
if(esVacio(document.frm1.txt14.value) || !esLongitudEntre(document.frm1.txt14.value,6,10) || !contieneNumero(document.frm1.txt14) || !contieneCarateresValidos(document.frm1.txt14.value,"usuariopassword")){
		alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico (0-9)");
		document.frm1.txt14.focus();
		return;		
}

if (document.frm1.txt14.value.toUpperCase() == document.frm1.txt13.value.toUpperCase())
	{	
		alert("La Contraseña no puede ser igual al campo Usuario");
		document.frm1.txt14.focus();
		return;		
	}

if (document.frm1.txt15.value.toUpperCase() != document.frm1.txt14.value.toUpperCase())
	{	
		alert("La Contraseña no esta verificada");
		document.frm1.txt15.focus();
		return;		
	}
		
//validar respuesta secreta
if (!esLongitudEntre(document.frm1.txt16.value,5,30) || !contieneCarateresValidos(document.frm1.txt16.value,"numeronombrebas"))
	{	
		alert("Por favor ingrese correctamente la Respuesta Secreta.\nLa Respuesta Secreta debe contener entre 5 y 30 caracteres");
		document.frm1.txt16.focus();
		return;		
	}

if (document.frm1.txt16.value.toUpperCase() == document.frm1.txt13.value.toUpperCase())
	{	
		alert("La Respuesta Secreta no puede ser igual a campo Usuario");
		document.frm1.txt16.focus();
		return;		
	}
	obj_mayuscula(document.frm1.txt1);
	obj_mayuscula(document.frm1.txt2);
	obj_mayuscula(document.frm1.txt3);
	//if(trim(document.frm1.txt3)){
	//}
	//obj_mayuscula(document.frm1.txt5);
	obj_mayuscula(document.frm1.txt7);
	obj_mayuscula(document.frm1.txt8);
	obj_mayuscula(document.frm1.txt13);		
	obj_mayuscula(document.frm1.txt14);		
	obj_mayuscula(document.frm1.txt15);		
	obj_mayuscula(document.frm1.txt16);	
	document.frm1.action="/iri/Afiliacion.do?state=mostrarContrato";	
	document.frm1.submit();
}

</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<table class="titulo">
  <tr>
    <td>Afiliaci&oacute;n de Usuario (Paso 1 de 3)</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario" >
<table border="0" class="tablasinestilo" >
  <tr>
    <th width="100%" colspan="4" bgcolor="#990000">DATOS DEL USUARIO</th>
  </tr>
  <tr>
    <td width="114" height="19" valign="middle">APELLIDO PATERNO</td>
    <td width="187" height="19" valign="top"><input type="text" name="txt1" size="11" maxlength="30" style="width:133" onblur="sololet(this)"></td><!-- onblur="contieneCarateresValidos(this.value,'nombre')" -->
    <td width="150" height="19" valign="middle">APELLIDO MATERNO</td>
    <td width="149" height="19" valign="top"><input type="text" name="txt2" size="11" maxlength="30" style="width:133" onblur="sololet(this)" ></td>
  </tr>
  <tr>
    <td width="114" height="19" valign="middle">NOMBRES</td>
    <td height="19" valign="middle" colspan=3><input type="text" name="txt3" size="11" maxlength="40" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <tr>
    <td width="114" height="19" valign="middle">TIPO DOCUMENTO</td>
    <td width="187" height="19" valign="middle">
      <select name="combo1">
		<% int m = 0; %>
		<logic:iterate name="arr1" id="item1" scope="request">
		<option value="<bean:write name="item1" property="codigo"/>" <%if (m==0) {%>selected<%}%>><bean:write name="item1" property="descripcion"/></option>
		<%  m++; %>
		</logic:iterate>
      </select>
    </td>
    <td width="150" height="19" valign="middle">N&Uacute;MERO DOCUMENTO</td>
    <td height="19" valign="top"><input type="text" name="txt4" size="11" maxlength="15" style="width:133" onblur="solonum(this)"></td>
  </tr>
  <TR>
	<TD width="114" height=18>PA&Iacute;S</TD>
	<TD width="187" height=18>
		<SELECT name=combo2 onchange=llenaDepProv() width="187" style="width:187" >
		<logic:iterate name="arr2" id="item2" scope="request">
		<logic:equal name="item2" property="codigo" value="01">
			<option value="<bean:write name="item2" property="codigo"/>" selected> <bean:write name="item2" property="descripcion"/> </option>
		</logic:equal>
		<logic:notEqual name="item2" property="codigo" value="01">
			<option value="<bean:write name="item2" property="codigo"/>"> <bean:write name="item2" property="descripcion"/> </option>
		</logic:notEqual>
		</logic:iterate>
		 </SELECT> 
	</TD>
	<TD width="150" height=19 vAlign="middle" ></TD>
	<TD height=19 vAlign="middle" nowrap>
	</TD>
  </TR>
  <TR>
	<TD width="114" height=18 vAlign="middle">DEPARTAMENTO</TD>
	<TD width="187" height=18 vAlign="middle" align="left">
	 	<SELECT name=combo3 onchange=llenaComboHijo(); width="187" style="width:187" >
		<logic:iterate name="arr3" id="item3" scope="request">
		<option value="<bean:write name="item3" property="codigo"/>" ><bean:write name="item3" property="descripcion"/></option>
		</logic:iterate>
		</SELECT>
	</TD>
	<TD width="140" height=19 vAlign="middle" >OTRO</TD>
	<TD height=19 vAlign="middle" nowrap><INPUT size="11" name="txt5" maxlength=30 style="width:133" onblur="solonumlet(this)" disabled=true> 
	</TD>
  </TR>
  
  <TR>
<%-- 28 agosto - se saca combo Oficina y se pone combo Provincia que depende de combo departamento--%>
	<TD width="114" height=18>PROVINCIA</TD>
	<TD width="187" height=18>
		<select name="combo4" width="187" style="width:187">
	    </select>
	</TD>
	<TD width="150" height=19 vAlign=top >DISTRITO</TD>
	<TD height=19 vAlign=top ><INPUT name="txt7" size="11" maxlength=40 style="width:133" onblur="solonumlet(this)" ></TD>
  </TR>
  <TR>
	<TD width="114" height=18 vAlign=top >AV/CALLE/JR Y NRO</TD>
	<TD width="187" height=18 vAlign=top ><INPUT name="txt8" size="11" maxlength=40 style="width:133"  onblur="solonumlet(this)" ></TD>
	<TD width="150" height=19 vAlign=top >COD POSTAL</TD>
	<TD height=19 vAlign=top ><INPUT size=11 name="txt9" maxlength="12" onblur="solonum(this)" style="width:133"></TD>
  </TR>
  <tr>
	<td width="114" height="19" valign="top">TEL&Eacute;FONO</td>
	<td width="187" height="19" valign="top"><input type="text" name="txt10" size="11" maxlength=30 style="width:133" onblur="solonum(this)" ></td>
	<td width="150" height="18" valign="middle">ANEXO</td>
	<td height="18" valign="middle"><input type="text" name="anexo" size="3" maxlength=10 style="width:133" onblur="solonum(this)" ></td>
  </tr>
  <tr>
	<td width ="114" height="18" valign="middle">FAX</td>
	<td height="18" valign="middle"><input type="text" name="txt11" size="11" maxlength=30 style="width:133" onblur="solonum(this)" ></td>
	<td></td>
	<td></td>
  </tr>  
  <tr>
    <td width="114" height="19" valign="top">CORREO ELECTR&Oacute;NICO</td>
    <td height="19" valign="top"  colspan=3><input type="text" name="txt12" size="11" maxlength=40 style="width:133"></td>
  </tr>
  <tr>
    <th width="100%" colspan="4" bgcolor="#990000" valign="middle"><b>DATOS DE LA CUENTA</b></th>
  </tr>
  <tr>
    <td width="114" height="19" valign="top">USUARIO</td>
    <td height="19" valign="top" colspan=3><input type="text" name="txt13" maxlength="13" size="11" style="width:133" onblur="solonumlet(this)"  ></td>
  </tr>
  <tr>
    <td width="114" height="19" valign="top">CONTRASE&Ntilde;A</td>
    <td width="187" height="19" valign="top"><input type="password" name="txt14" size="11" maxlength="10" style="width:90"  onblur="solonumlet(this)" ></td>
    <td width="150" height="19" valign="top">CONFIRME CONTRASE&Ntilde;A</td>
    <td height="19" valign="top"><input type="password" name="txt15" size="11" maxlength="10" style="width:90"  onblur="solonumlet(this)"></td>
  </tr>
  <tr>
    <td width="114" height="19" valign="middle">PREGUNTA SECRETA</td>
    <td width="187" height="19" valign="top">
      <select  name="combo5" size="1" width="187" style="width:187">
		<logic:iterate name="arr5" id="item5" scope="request">
		<option value="<bean:write name="item5" property="codigo"/>"><bean:write name="item5" property="descripcion"/></option>
		</logic:iterate>
      </select>
    </td>
    <td width="150" height="19" valign="middle">RESPUESTA</td>
    <td height="19" valign="top"><input type="text" name="txt16" size="11" maxlength="30" style="width:133"  onblur="solonumlet(this)" ></td>
  </tr>
 <tr>
 	<td height="19" valign="middle" align=right colspan="2"></td>
    <td height="19" valign="middle" align=left colspan="2"><input type="checkbox" name="chk1" value="1"> Quiero recibir mensajes en mi correo</td>
  </tr>
  <tr>
    <td width="100%" height="19" colspan="4" >&nbsp;</td>
  </tr>
  <tr>
    <td width="100%" height="19" align="center" valign="middle" colspan="4">
	  <A href="javascript:continua()"><IMG src="images/btn_continuar.gif" style="border:0" onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
	  <A href="javascript:cancela();"><IMG src="images/btn_cancelar.gif" style="border:0" onmouseover="javascript:mensaje_status('Cancelar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
	</td>
  </tr>
</table>
</form>
<script LANGUAGE="JavaScript">
	llenaComboHijo();	
</script>




<%-- llenar pantalla con datos anteriores --%>
<% 
DatosUsuarioBean bean1 = (DatosUsuarioBean) request.getAttribute("DATOS_FORMULARIO");
if (bean1!=null)
{
%>
<script LANGUAGE="JavaScript">
	document.frm1.txt1.value="<%=bean1.getApellidoPaterno()%>";
	document.frm1.txt2.value="<%=bean1.getApellidoMaterno()%>";		
	document.frm1.txt3.value="<%=bean1.getNombre()%>";
	doCambiaCombo(document.frm1.combo1, "<%=bean1.getTipoDocumento()%>");
	document.frm1.txt4.value="<%=bean1.getNumeroDocumento()%>";
	doCambiaCombo(document.frm1.combo2, "<%=bean1.getPais()%>");
	llenaDepProv();
	
		
	document.frm1.txt5.value="<%=bean1.getOtro()%>";
	doCambiaCombo(document.frm1.combo3, "<%=bean1.getDepartamento()%>");
	llenaComboHijo();
	doCambiaCombo(document.frm1.combo4, "<%=bean1.getProvinciaId()%>");
	
	document.frm1.txt7.value ="<%=bean1.getDistrito()%>";
	document.frm1.txt8.value ="<%=bean1.getCalle()%>";
	document.frm1.txt9.value ="<%=bean1.getCodigoPostal()%>";
	document.frm1.txt10.value="<%=bean1.getTelefono()%>";
	document.frm1.anexo.value="<%=bean1.getAnexo()%>";
	document.frm1.txt11.value="<%=bean1.getFax()%>";
	document.frm1.txt12.value="<%=bean1.getEmail()%>";
	document.frm1.txt13.value="<%=bean1.getUsuario()%>";
	doCambiaCombo(document.frm1.combo5, "<%=bean1.getPreguntaSecreta()%>");
	document.frm1.txt16.value="<%=bean1.getRespuestaSecreta()%>";
			
	<%if (bean1.getRecibirMail()==true) {%>
		document.frm1.chk1.checked = "true";
	<%  } %>
</script>
<%}%>

<%-- mostrar mensaje de error de validacion, si hubiera --%>
<%
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
%>
	<script LANGUAGE="JavaScript">
		alert("<%=validacionException.getMensaje()%>");
		<% 
		String foco = validacionException.getFocus();
		if (foco.trim().length() > 0) {%>
			document.frm1.<%=foco%>.focus();
		<% } %>
	</script>
<% } %>
</body>
</html>