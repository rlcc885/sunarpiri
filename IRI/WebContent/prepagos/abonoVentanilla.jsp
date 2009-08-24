<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
var timeDelay = <logic:present name="refreshTime"><bean:write name="refreshTime"/></logic:present><logic:notPresent name="refreshTime">120000</logic:notPresent>;
function validarformulario(){
	if(document.form1.radio[0].checked==true){
		if(esVacio(document.form1.userId.value)){
			alert("Datos Incorrectos. Ingrese el Usuario");
			document.form1.userId.focus();
			return false;
		}	
	}	
	if(document.form1.radio[1].checked==true){
//		if(document.form1.radio[3].checked){
			if(esVacio(document.form1.razsoc.value) && esVacio(document.form1.ruc.value)){
				alert("Ingrese al menos la Razón Social o el Número del Documento");
				document.form1.razsoc.focus();		
				return false;
			}
			if(!esVacio(document.form1.ruc.value)){
			if(!esNumero(document.form1.ruc.value) || !esMayor(document.form1.ruc.value,8)){
				alert("Ingrese correctamente el Número del Documento");
				document.form1.ruc.focus();		
				return false;
			}
	}		}

	if(document.form1.radio[2].checked==true){
//		if(document.form1.radio[1].checked){
			if(esVacio(document.form1.apepat.value) && esVacio(document.form1.nombres.value)){
				alert("Ingrese al menos el Apellido Paterno y/o el Nombre");
				document.form1.apepat.focus();		
				return false;
			}
	}		
	if(document.form1.radio[3].checked==true){
//		if(document.form1.radio[2].checked){
			if(esVacio(document.form1.numdoc.value) || !esNumero(document.form1.numdoc.value) || !esMayor(document.form1.numdoc.value,8)){
				alert("Ingrese correctamente el Número del Documento");
				document.form1.numdoc.focus();		
				return false;
			}
	}		
	return true;	
}


function MuestraResultados(){ 
	if(validarformulario()){
		document.form1.method = "POST";
		document.form1.action = "/iri/Ventanilla.do?state=mantenimientoUsuario";
		document.form1.submit();
		return true;
	}
	return false;		
}
function Prepagos(linea){ 

	document.form2.method = "POST";
	document.form2.action = "/iri/Ventanilla.do?state=muestraVentanilla&lineaPrepago=" + linea;
	document.form2.submit();
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
//jbugarin modificacion descaj
function refrescarAutomaticamente()
	{
	  if(document.form1.chkRefrescaAuto.checked) {
	  	timeOut = setTimeout("MuestraResultados();",timeDelay);
	  } else {
	  	clearTimeout(timeOut);
	  }
	}
//fin	
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>

<br>
<table border="0" width="100%" class="titulo">
	<tr>
		<td><font color=black>CAJA &gt;&gt; Prepagos &gt;&gt;</font> Mantenimiento de Saldos </td>
	</tr>
</table><br>
<form name="form1" class="formulario">
<table class=tablasinestilo >
  <tr>
    <td width="584">
      <b><font size="2">B&uacute;squeda</font></b>
    </td>
  </tr>
  <tr>
    <td width="592">
      <table width="592" border=0>
        <tr>
          <% /******* MODIFICADO JBUGARIN DESCAJ INICIO 05/01/2007 ***********/
    		String valorRB1 = (String)request.getAttribute("valorRB");
    		
    	  %>
    	  <% if ( (valorRB1!=null) && (valorRB1.equals("1")) ) { %>
          <td width="152" ><input type="radio" value="1" name="radio" checked>Usuario ID</td>
          <% } else { %>
          <td width="152" ><input type="radio" value="1" name="radio" >Usuario ID</td>
          <% } %>
          <% if (request.getAttribute("buscarUsuarioId")!=null) { %>
          <td width="163"><input type="text" name="userId" value="<%=request.getAttribute("buscarUsuarioId")%>" size="20" style="width:133" maxlength=13 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'1');"></td>
          <% } else { %>
         <td width="163"><input type="text" name="userId" size="20" style="width:133" maxlength=13 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'1');"></td>
          <% } %>
          <td width="71" ></td>
          <td width="132" align=center ><input type="image" src="images/btn_buscar.gif" border="0" onClick="return MuestraResultados();"></td>
        </tr>
        <tr>
        
          <% if ( (valorRB1!=null) && (valorRB1.equals("2")) ) { %>
          <td width="152" ><input type="radio" value="2" checked name="radio">Organizaciones</td>
          <% } else {%>
          <td width="152" ><input type="radio" value="2" name="radio">Organizaciones</td>
          <% } %>
          <td width="163">Raz&oacute;n Social
           <% if (request.getAttribute("buscarRazSoc")!=null) { %>
            <input type="text" size="20" value="<%=request.getAttribute("buscarRazSoc")%>" style="width:133" maxlength=150 name="razsoc" onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'2');"></td>
          <% } else { %>
          <input type="text" size="20" style="width:133" maxlength=150 name="razsoc" onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'2');">
          <% } %>
          <td width="71" >RUC
           <% if (request.getAttribute("buscarRuc")!=null) { %>
           <input type="text" size="12" value="<%=request.getAttribute("buscarRuc")%>" style="width:133" maxlength=11 name="ruc" onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'2');"></td>
           <% } else {%>
           <input type="text" size="12" style="width:133" maxlength=11 name="ruc" onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'2');">
           <% }%>
          <td width="132" ></td>
        </tr>
        <tr>
          <% if ( (valorRB1!=null) && (valorRB1.equals("3")) ) { %>
          <td  width="152"><input type="radio" value="3" checked name="radio">UsuariosIndividuales</td>
          <% } else { %>
          <td  width="152"><input type="radio" value="3" name="radio">UsuariosIndividuales</td>  
          <% }%> 
          <% if (request.getAttribute("buscarApellidoPaterno")!=null) { %>
          <td width="135">Apellido Paterno&nbsp;&nbsp;&nbsp; <input type="text" name="apepat" value="<%=request.getAttribute("buscarApellidoPaterno")%>" size="20" style="width:133" maxlength=30 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'3');"></td>
          <% } else { %>
          <td width="135">Apellido Paterno&nbsp;&nbsp;&nbsp; <input type="text" name="apepat" size="20" style="width:133" maxlength=30 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'3');"></td>
          <% } %>
          <% if (request.getAttribute("buscarApellidoMaterno")!=null) { %>
          <td width="136">Apellido Materno <input type="text" name="apemat" value="<%=request.getAttribute("buscarApellidoMaterno")%>" size="20" style="width:133" maxlength=30 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'3');"></td>
          <% } else {%>
          <td width="136">Apellido Materno <input type="text" name="apemat" size="20" style="width:133" maxlength=30 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'3');"></td>
          <% } %>
          <% if (request.getAttribute("buscarNombre")!=null) { %>
          <td width="67">Nombres<br><input type="text" name="nombres" value="<%=request.getAttribute("buscarNombre")%>" size="20" style="width:133" maxlength=40 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'3');"></td>
		  <% } else { %>	
		  <td width="67">Nombres<br><input type="text" name="nombres" size="20" style="width:133" maxlength=40 onblur="sololet(this)" onfocus="doCambiaRadio(document.form1.radio,'3');"></td>
		  <% } %>
		  
        </tr>
        <tr>
           <% if ( (valorRB1!=null) && (valorRB1.equals("4")) ) { %>
          	<td  width="152"><input type="radio" value="4" checked name="radio">Por Documento de Identidad</td>
           <% } else { %>
          	<td  width="152"><input type="radio" value="4" name="radio">Por Documento de Identidad</td>  
           <% }%> 
          	<td width="135" >Tipo&nbsp;&nbsp; 
		<%  
	   		String buscarTipoDocu =  (String)request.getAttribute("buscarTipoDocu");
	   		if(buscarTipoDocu==null)
	   		 buscarTipoDocu = "";
	   		 
	   		 String buscarNuDocu = (String)request.getAttribute("buscarNuDocu");
	   		 if(buscarNuDocu==null)
	   		 buscarNuDocu = "";
	   		 
		%>
		<select size="1" name="numopt" >
		<logic:iterate name="listaDocsId" id="item" scope="request">
			<option value="<bean:write name="item" property="tipoDoc"/>" <logic:equal name="item" property="tipoDoc" value="<%= buscarTipoDocu %>">selected</logic:equal>  >
			   <bean:write name="item" property="nomAbre"/>
			</option>
		</logic:iterate>
     	</select>
          <td width="136" >N&uacute;mero&nbsp;&nbsp;&nbsp; 
          	<input type="text" size="12" maxlength="15" value="<%= buscarNuDocu %>" style="width:133" name="numdoc" onblur="sololet(this)" onFocus="doCambiaRadio(document.form1.radio,'4');"></td>
    	  <td width="67" ><INPUT type="checkbox" name="chkRefrescaAuto"	value="checkbox" <% if (request.getAttribute("sRefrescaAuto")!=null){%> checked<%}%> onclick="javascript:refrescarAutomaticamente()">Refrescar Automaticamente</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
<form name="form2">
<INPUT type=hidden name="nombreRuc" value="R.U.C.">

<logic:present name="listaUsuarios">
<br><br>
<table class=formulario cellspacing=0>
  <th colspan=5>Resultados de la B&uacute;squeda</th>	
  <tr>
    <logic:notPresent name="organi"><th width="15%" height="11">USUARIO ID</th></logic:notPresent>
    <th width="25%" height="11">NOMBRE / RAZON SOCIAL</th>
    <th width="8%" height="11">TPO DOC</th>
    <th width="13%" height="11">NUM DOC</th>
    <!--<th width="20%" height="11">AFILIADO A ORGANIZACION</th>-->
    <td width="19%" height="11">&nbsp;</td>
  </tr>
		<logic:iterate name="listaUsuarios" id="item" scope="request">  
  <tr>
    <logic:notPresent name="organi"><td width="15%" height="12" align="center"><bean:write name="item" property="usuarioId"/></td></logic:notPresent>
    <td width="25%" height="12" align="center"><bean:write name="item" property="nombre"/></td>
    <td width="8%" height="12" align="center"><bean:write name="item" property="tipo_doc"/></td>
    <td width="13%" height="12" align="center"><bean:write name="item" property="num_doc"/></td>
    <!--<td width="20%" height="12" align="center"><bean:write name="item" property="afil_organiz"/></td>-->
    <td width="19%" height="12"><a href="javascript:Prepagos('<bean:write name="item" property="lineaPrepago"/>')"><img src="images/btn_prepagar.gif"></a>
    <!--input type="button" value="Prepagar" name="B3" onclick="Prepagar()"-->
    </td>
  </tr>
		</logic:iterate>  
  </table>
</logic:present>
<logic:notPresent name="listaUsuarios">
<logic:notPresent name="prima">
<center><br><h3><font color="red">No se encontr&oacute; registro alguno.</font></h3></center>
</logic:notPresent>
</logic:notPresent>
</form>
<SCRIPT LANGUAGE="JavaScript">
<% if (request.getAttribute("sRefrescaAuto")!=null){%>refrescarAutomaticamente()<%}%>
</SCRIPT>
</body>
</html>
