
<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>

<script language="javascript">
var timeDelay = <logic:present name="refreshTime"><bean:write name="refreshTime"/></logic:present><logic:notPresent name="refreshTime">120000</logic:notPresent>;
function validarformulario()
{
	if(document.form1.radio[0].checked==true){
		if(esVacio(document.form1.userId.value)){
			alert("Por favor ingrese el Usuario");
			document.form1.userId.focus();
			return false;
		}
		if(esEntero(document.form1.userId.value.substring(document.form1.userId.value.length-3,document.form1.userId.value.length)))
		{
			//alert("El usuario que ud.está buscando pertenece a una organización. Use el menú de organizaciones para su búsqueda.");
			//document.form1.userId.focus();
			//return false;
		}
	}	
	if(document.form1.radio[1].checked==true)
	{
	/*
			if(esVacio(document.form1.apepat.value) && esVacio(document.form1.nombres.value)){
				alert("Por favor ingrese al menos el Apellido Paterno y/o el Nombre");
				document.form1.apepat.focus();		
				return false;
			}
			if (!esVacio(document.form1.apepat.value)  && !contieneCarateresValidos(document.form1.apepat.value,"nombre"))
				{	
					alert("Por favor ingrese correctamente el Apellido Paterno");
					document.form1.apepat.focus();					
					return false;
				}	
			if (!esVacio(document.form1.apemat.value)  && !contieneCarateresValidos(document.form1.apemat.value,"nombre"))
				{	
					alert("Por favor ingrese correctamente el Apellido Materno");
					document.form1.apemat.focus();
					return false;
				}	
				
			if (!esVacio(document.form1.nombres.value) && !contieneCarateresValidos(document.form1.nombres.value,"nombre"))
				{	
					alert("Por favor ingrese correctamente el Nombre del Representante");
					document.form1.nombres.focus();
					return false;
				}	
	*/
			
	}		
	if(document.form1.radio[2].checked==true){
			   
			if(esVacio(document.form1.numdoc.value) || !esEntero(document.form1.numdoc.value) || !esMayor(document.form1.numdoc.value,8) || !esEnteroMayor(document.form1.numdoc.value,1)){
				alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
				document.form1.numdoc.focus();		
				return false;
			}			
	}
			
	if(document.form1.radio[3].checked==true){
	/*
			if(esVacio(document.form1.razsoc.value) && esVacio(document.form1.ruc.value)){
				alert("Por favor ingrese al menos la Razón Social o el Número del Documento");
				document.form1.razsoc.focus();		
				return false;
			}
			if(!esVacio(document.form1.ruc.value)){
			if(!esEntero(document.form1.ruc.value) || !esMayor(document.form1.ruc.value,8) || !esEnteroMayor(document.form1.ruc.value,1)){
				alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 11 caracteres numéricos (0-9)");
				document.form1.ruc.focus();		
				return false;
			}
	}		}
	if(document.form1.radio[4].checked==true){
	*/
			if(esVacio(document.form1.tiempo.value) || !contieneCarateresValidos(document.form1.tiempo.value,"numeroneg") || !esEnteroMayor(document.form1.tiempo.value,-1)){
				alert("Por Favor ingrese correctamente el Tiempo de Inactividad");
				document.form1.tiempo.focus();		
				return false;
			}

	}	
	return true;	
}


function doPrepago(linea)
{
	document.form1.method = "POST";
	document.form1.action = "/iri/Ventanilla.do?state=muestraVentanilla&lineaPrepago=" + linea;
	document.form1.submit();

}
function doCambioClave(id)
{
	var p = confirm("¿Está seguro que desea generar una nueva clave para el usuario?");
	if (p == true)
		location.href="/iri/Mantenimiento.do?state=cambioClave&param1="+id;

}

function doCambiaRadio(obj, valor)
{ 
for (var rr = 0; rr < obj.length; rr++)
	{
		var xvlr = obj[rr].value;
		if (xvlr == valor)
			obj[rr].checked=true;
	}
}

function MuestraResultados(){ 

	if(validarformulario()){
		obj_mayuscula(document.form1.userId);
		obj_mayuscula(document.form1.apepat);
		obj_mayuscula(document.form1.apemat);
		obj_mayuscula(document.form1.nombres);	
		//obj_mayuscula(document.form1.razsoc);
		document.form1.method = "POST";
		document.form1.action = "/iri/Mantenimiento.do?state=mantenimientoUsuario";
		document.form1.submit();
		return true;
	}
	return false;
}

function UsuarioNuevo()
{ 
		document.form2.method = "POST";
		document.form2.action = "/iri/CrearUsuario.do";
		document.form2.submit();
}
//jbugarin modificacion descaj
function refrescarAutomaticamente() {

	var valueSelected = 0
	for (i=0;i<document.form1.radio.length;i++) {
		if (document.form1.radio[i].checked) {
			valueSelected = document.form1.radio[i].value;
		}
	}

	if (valueSelected != 5) {
		if(document.form1.chkRefrescaAuto.checked) {
			timeOut = setTimeout("MuestraResultados();",timeDelay);
		} else {
			clearTimeout(timeOut);
		}
	}
}
//fin	
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<form name="form2">
</form>
<br>
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>CAJA &gt;&gt; Usuarios &gt;&gt; </font> Registro de Usuarios Individuales
		</td>
	</tr>
</table>
<br>

<!--<form name="form1" class=formulario onSubmit="if (!MuestraResultados()) return false;">-->

<form name="form1" class=formulario>


<table class="tablasinestilo">
  <tr>
    <td width="33%">
      <p align="left">
      <b><font size="2">B&uacute;squeda</font></b></p>
    </td>
    <td width="20%"></td>
    <td width="32%"></td>
    <td width="15%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="left">
     <% /******* MODIFICADO JBUGARIN DESCAJ INICIO 05/01/2007 ***********/
    	String valorRB1 = (String)request.getAttribute("valorRB");
    	
    %>

    <% if ((valorRB1 == null)) { %>
      <input type="radio" value="1" name="radio" checked><b>Directa por ID</b>
    <% } else if ( (valorRB1!=null) && (valorRB1.equals("1")) ) { %>
      <input type="radio" value="1" name="radio" checked><b>Directa por ID</b>
    <% } else {%>
    <input type="radio" value="1" name="radio" ><b>Directa por ID</b>
    <% }%>
    </td>
    <td width="20%" align="left">Usuario ID</td>
    <% if (request.getAttribute("buscarUsuarioId")!=null) { %>
    <td width="32%"><input type="text" name="userId" value="<%=request.getAttribute("buscarUsuarioId")%>" size="20" maxlength="13" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'1');"></td>
    <% } else { %>
    <td width="32%"><input type="text" name="userId" size="20" maxlength="13" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'1');"></td>
    <% } %>
    <td width="15%"><input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados();" onmouseover="javascript:mensaje_status('Buscar Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
  </tr>
  <tr>
    <% 
    	String valorRB2 = (String)request.getAttribute("valorRB");
    	
    %>
    <% if ( (valorRB2!=null) && (valorRB2.equals("2")) ) { %>
    <td width="33%" align="left"><input type="radio" value="2" checked name="radio"><b>Por Apellidos y Nombres</b></td>
    <% } else { %>
    <td width="33%" align="left"><input type="radio" value="2" name="radio"><b>Por Apellidos y Nombres</b></td>
    <% } %>
    
    <td width="20%" align="left">Apellido Paterno</td>
    <% if (request.getAttribute("buscarApellidoPaterno")!=null) { %>
    <td width="32%"><input type="text" name="apepat" value="<%=request.getAttribute("buscarApellidoPaterno")%>" size="20" maxlength="30" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <% } else { %>
    <td width="32%"><input type="text" name="apepat" size="20" maxlength="30" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <% } %>
    <td width="15%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="left"></td>
    <td width="20%" align="left">Apellido Materno</td>
    <% if (request.getAttribute("buscarApellidoMaterno")!=null) { %>
    <td width="32%"><input type="text" name="apemat" value="<%=request.getAttribute("buscarApellidoMaterno")%>" size="20" maxlength="30" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <% } else { %>
    <td width="32%"><input type="text" name="apemat" size="20" maxlength="30" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <% }%>
    <td width="15%"></td>
  </tr>
  <tr>
    <td width="33%" align="left"></td>
    <td width="20%" align="left">Nombres</td>
    <% if (request.getAttribute("buscarNombre")!=null) { %>
    <td width="32%"><input type="text" name="nombres" value="<%=request.getAttribute("buscarNombre")%>" size="20" maxlength="40" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <% } else { %>
    <td width="32%"><input type="text" name="nombres" size="20" maxlength="40" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'2');"></td>
    <% } %>
    <td width="15%"></td>
  </tr>
  <tr>
    <% if ( (valorRB1!=null) && (valorRB1.equals("3")) ) { %>
    <td width="33%" align="left"><input type="radio" value="3" checked name="radio"><b>Por Documento de Identidad</b></td>
    <% } else { %>
    <td width="33%" align="left"><input type="radio" value="3" name="radio"><b>Por Documento de Identidad</b></td>
    <% } %>
    <td width="20%" align="left">Tipo</td>
    <td width="32%" colspan=2>
	<% 
	   String buscarTipoDocu =  (String)request.getAttribute("buscarTipoDocu");
	   if(buscarTipoDocu==null) buscarTipoDocu = "";
	%>
	<%--logic:present name="buscarTipoDocu"--%>
	<select size="1" name="numopt" >
		<logic:iterate name="listaDocsId" id="item" scope="request">
			<option value="<bean:write name="item" property="tipoDoc"/>" <logic:equal name="item" property="tipoDoc" value="<%=buscarTipoDocu%>">selected</logic:equal>  >
			   <bean:write name="item" property="nomAbre"/>
			</option>
		</logic:iterate>
     </select>
     &nbsp;Numero 
     <% if (request.getAttribute("buscarNuDocu")!=null) { %>
     <input type="text" size="12" maxlength="15" value="<%=request.getAttribute("buscarNuDocu")%>"style="width:133" name="numdoc" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'3');">
     <% } else { %>
     <input type="text" size="12" maxlength="15" style="width:133" name="numdoc" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'3');">
     <% } %>
    </td>
  </tr>
  

  
  <tr>
     <% 
    	String valorRB5 = (String)request.getAttribute("valorRB");
    	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxx "+valorRB5);
    %>
    <% if ( (valorRB5!=null) && (valorRB5.equals("5")) ) { %>
    <td width="33%"><input type="radio" value="5" checked name="radio"><b>Tiempo Inactividad mayor a</b></td>
    <% } else { %>
    <td width="33%"><input type="radio" value="5" name="radio"><b>Tiempo Inactividad mayor a</b></td>
    <% }%> 
    <td width="20%" align="right">
      <% if (request.getAttribute("buscarTiempo")!=null) { %>
      <p align="left"><input type="text" size="3" maxlength="3" value="<%=request.getAttribute("buscarTiempo")%>"style="width:30" name="tiempo" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'5');">dias</td>
	  <% } else { %>
	  <p align="left"><input type="text" size="3" maxlength="3" style="width:30" name="tiempo" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'5');">dias
	  <% }%> 
	<td width="32%"><input type="checkbox" name="chkRefrescaAuto" value="checkbox" <% if (request.getAttribute("sRefrescaAuto")!=null){%>checked <%}%> onclick="javascript:refrescarAutomaticamente()">
  Refrescar Automaticamente</td>
  <!-- fin -->
    <td width="15%"></td>
  </tr>
  <tr>
    <td width="100%" colspan="4">
      <p align="center"><br>
      <A href="javascript:UsuarioNuevo()" onmouseover="javascript:mensaje_status('Nuevo Usuario');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img style="border:0" src="<%=request.getContextPath()%>/images/btn_nuevo.gif"></a>
      <!--<A href="javascript:history.back();"><img style="border:0" src="/iri/images/btn_regresa.gif"></a>-->
    </td>
  </tr>
</table>
<br>
<!-- ******************************************************************************************************* -->

<logic:present name="listaMantUsuario">
<table class="grilla" width="600">
  <tr class=grilla2>
	    <th width="89">USUARIO</th>
	    <th width="221">APELLIDOS Y NOMBRES</th>
	    <th width="66">TPO DOC</th>
	    <th width="111">NUM DOCUM</th>
	    <th width="8%">FECHA DE AFILIACION</th>
	    <th width="8%">ULTIMO ACCESO</th>
	    <th width="8%">DIAS DESDE ULTIMO ACCESO</th>    		    
	    <th >SALDO</th>
	    <th width="71" align="center">PREPAGO</th>
	    <th width="64" align="center">CLAVE</th>
    </tr>
      <%
	  	//atributos de diseno
	  	boolean flag1=false;
	  	String dFila, dColumna;
	  %>
		<logic:iterate name="listaMantUsuario" id="item" scope="request">
		<% if (flag1==false)
					{
						dFila="";
						dColumna="bgcolor='#e2e2e2'";
					}
				else
					{
						dFila="class=grilla2";
						dColumna="";
					}
				flag1 = !flag1; %>
	        <tr <%=dFila%>> 
	          <td <%=dColumna%> width="89"  height="27"><bean:write name="item" property="usuario"/></td>
	          <td <%=dColumna%> width="221" height="27"><bean:write name="item" property="ape_Nom"/></td>
	          <td <%=dColumna%> width="66"  height="27" align="center"><bean:write name="item" property="tipoDocumentoDesc"/></td>
	          <td <%=dColumna%> width="111" height="27" align="center"><bean:write name="item" property="numeroDocumento"/></td>
	          
	          <td <%=dColumna%> width="8%" height="27"><bean:write name="item" property="fechaAfiliacion"/></td>
	          <td <%=dColumna%> width="8%" height="27"><bean:write name="item" property="fechaUltimoAcceso"/></td>
	          <td <%=dColumna%> width="8%" height="27"><bean:write name="item" property="diasDesdeUltimoAcceso"/></td>
	          <td <%=dColumna%> height="27"><bean:write name="item" property="saldo"/></td>
	          
	          	          
	          <td <%=dColumna%> width="71"  height="27" align="center"><a href="javascript:doPrepago('<bean:write name="item" property="lineaPrepago"/>');" onmouseover="javascript:mensaje_status('Linea Prepago');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="images/ico_prepago.gif"></a></td>
	          <td <%=dColumna%> width="64" height="27" align="center"><a href="javascript:doCambioClave('<bean:write name="item" property="usuario"/>');" onmouseover="javascript:mensaje_status('Cambiar Contrasena');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="images/ico_clave.gif"></a></td>
	        </tr>
		</logic:iterate>
</table>
</logic:present>

</form>

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
		document.form1.<%=foco%>.focus();
	<% } %>
</script>
<% } %>
<SCRIPT LANGUAGE="JavaScript">
<% if (request.getAttribute("sRefrescaAuto")!=null){%>refrescarAutomaticamente()<%}%>
</SCRIPT>

</body>
</html>