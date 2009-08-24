<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
String x = (String) request.getAttribute("perfilId");
long perfilId = Long.parseLong(x);

Value05Bean beanFormulario = (Value05Bean) request.getAttribute("DATOS_FORMULARIO");
	
// modo, valores:
//     0 : MOSTRAR PANTALLA DE BUSQUEDA
//     1 : MOSTRAR PANTALLA DE BUSQUEDA; pero con datos
//                    pre cargados porque el usuario de equivoco en algun dato
//                    y se lo mostramos de nuevo

//x = (String) request.getAttribute("modo");
//int modo = Integer.parseInt(x);

int modo=0;
if (beanFormulario != null)
		 modo=1;
%>

<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<title></title>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">
var timeDelay = <logic:present name="refreshTime"><bean:write name="refreshTime"/></logic:present><logic:notPresent name="refreshTime">120000</logic:notPresent>;
//_modo=<%=modo%>_
function validarformulario(){
	if(document.frm1.rad1[0].checked==true){
		if(esVacio(document.frm1.razsoc.value)){
			alert("Por favor ingrese correctamente la Razón Social");
			document.frm1.razsoc.focus();		
			return false;
		}
	}

	if(document.frm1.rad1[1].checked==true){	
		if((esVacio(document.frm1.ruc.value)) || (!esEntero(document.frm1.ruc.value) || !esLongitudMayor(document.frm1.ruc.value,11))){
			alert("Por favor ingrese correctamente el Número del RUC.\nEl Número del Documento requiere al menos 11 caracteres numéricos (0-9)");
			document.frm1.ruc.focus();		
			return false;
		}
	}		

	if(document.frm1.rad1[2].checked==true){
			if(esVacio(document.frm1.tiempo.value) || !contieneCarateresValidos(document.frm1.tiempo.value,"numeroneg") || !esEnteroMayor(document.frm1.tiempo.value,-1)){
				alert("Por favor ingrese correctamente el Tiempo de Inactividad");
				document.frm1.tiempo.focus();		
				return false;
			}
	}		
	return true;	
}

function OrgNueva()
{ 
	//location.href="/iri/CrearOrganizacion.do";
	document.frm1.method="post";
	document.frm1.action = "/iri/CrearOrganizacion.do";
	document.frm1.submit();
	return true;	
}

<% if (perfilId != Constantes.PERFIL_CAJERO) 
    {%>
	    function doActiva(id)
		{
			var p = confirm("¿Está seguro que desea activar la organización?");
			if (p == true)
				location.href="/iri/MantenimientoOrg.do?state=activacion&param1=1&param2="+id;
		
		}
		function doDesactiva(id)
		{
			var p = confirm("¿Está seguro que desea desactivar la organización?");
			if (p == true)
				location.href="/iri/MantenimientoOrg.do?state=activacion&param1=0&param2="+id;
		
		}
		
		function doExonera(id)
		{
			var p = confirm("¿Está seguro que desea exonerar de pago a la organización?");
			if (p == true)
				location.href="/iri/MantenimientoOrg.do?state=exoneracion&param1=1&param2="+id;
		
		}
		function doExonera2(id)
		{
			var p = confirm("¿Está seguro que desea activar el pago de la organización?");
			if (p == true)
				location.href="/iri/MantenimientoOrg.do?state=exoneracion&param1=0&param2="+id;
		}
		
		function Editar(codOrg)
		{ 
			//document.frm1.method="post";
			//document.frm1.action = "/iri/CrearOrganizacion.do";
			//document.frm1.submit();
			//return true;		
			
			document.frm1.method="post";
			document.frm1.codOrg.value = codOrg
			//location.href="/iri/EditarOrganizacion.do?codOrg="+codOrg;
			document.frm1.action = "/iri/EditarOrganizacion.do?codOrg="+codOrg;
			document.frm1.submit();
			return true;		
			
		}
<%  } %>



<% if (perfilId == Constantes.PERFIL_CAJERO)  {%>
function doPrepago(linea)
{
	document.frm1.method = "POST";
	document.frm1.action = "/iri/Ventanilla.do?state=muestraVentanilla&lineaPrepago=" + linea;
	document.frm1.submit();

}
	function doCambioClave(id)
	{
		var p = confirm("¿Está seguro que desea generar una nueva clave para la organización?");
		if (p == true)
			location.href="/iri/Mantenimiento.do?state=cambioClave&param1="+id;
	}
<% } %>


function MuestraResultados()
{
	if(validarformulario())
	{
		obj_mayuscula(document.frm1.razsoc);	
		document.frm1.method="post";
		document.frm1.action = "/iri/MantenimientoOrg.do?state=buscar";
		document.frm1.submit();
		return true;	
	}
	return false;	
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
function refrescarAutomaticamente() {
	
	var valueSelected = 0
	for (i=0;i<document.frm1.rad1.length;i++) {
		if (document.frm1.rad1[i].checked) {
			valueSelected = document.frm1.rad1[i].value;
		}
	}
	
	if (valueSelected != 3) {
		if(document.frm1.chkRefrescaAuto.checked) {
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
<body >

<br>
<table class=titulo>
<tr><td>
<% if (perfilId == Constantes.PERFIL_ADMIN_GENERAL) {%>
<FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; Organizaciones &gt;&gt; </FONT> Mantenimiento de Organizaciones
<% }%>
<% if (perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) {%>
<FONT color=black>ADMINISTRACI&Oacute;N JURISDICCI&Oacute;N &gt;&gt; Organizaciones &gt;&gt; </FONT> Mantenimiento de Organizaciones
<% }%>
<% if (perfilId == Constantes.PERFIL_CAJERO) {%>
<FONT color=black>CAJERO &gt;&gt; Organizaciones &gt;&gt; </FONT> Mantenimiento de Organizaciones
<% }%>
</td></tr>
</table>
<br>
<form name="frm1" class=formulario>
<input type="hidden" name="codOrg">
<table class=tabalasinestilo>

  <tr>
    <td width="18%" align="left"><p align="left">
      <b><font size="2">B&uacute;squeda</font></b></p></td>
    <% /******* MODIFICADO JBUGARIN DESCAJ INICIO ***********/
    	String valorRB1 = (String)request.getAttribute("valorRB");
    %>
    <% if ( (valorRB1!=null) && (valorRB1.equals("1")) ) { %>
    <td width="35%" align="left"><input type="radio" value="1" checked name="rad1">Por Raz&oacute;n Social</td>
    <% } else { %>
    <td width="35%" align="left"><input type="radio" value="1" name="rad1">Por Raz&oacute;n Social</td>
    <% } %>
    
    <% if (request.getAttribute("buscarRazonSocial")!=null) { %>
    <td width="32%"><input type="text" name="razsoc" value="<%=request.getAttribute("buscarRazonSocial")%>" size="20" maxlength="" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.frm1.rad1,'1');"></td>
    <% } else { %>
    <td width="32%"><input type="text" name="razsoc" size="20" maxlength="" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.frm1.rad1,'1');"></td>
    <% } %>
    <td width="15%">
    <input type="image" src="<%=request.getContextPath()%>/images/btn_buscar.gif" style="border:0" onClick="return MuestraResultados()" onmouseover="javascript:mensaje_status('Buscar Organizacion');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    </td>
  </tr>
  
  <tr>
    <td width="18%" align="left"></td>
    <% 
    	String valorRB2 = (String)request.getAttribute("valorRB");
    %>
    <% if ( (valorRB2!=null) && (valorRB2.equals("2"))) { %>
    <td width="35%" align="left"><input type="radio" value="2" checked name="rad1">Por RUC</td>
    <% } else { %>
    <td width="35%" align="left"><input type="radio" value="2" name="rad1">Por RUC</td>
    <% } %>
    <% if (request.getAttribute("buscarRuc")!=null) { %>
    <td width="32%"><input type="text" name="ruc" value="<%=request.getAttribute("buscarRuc")%>" maxlength="11" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.frm1.rad1,'2');"></td>
    <% } else { %>
    <td width="32%"><input type="text" name="ruc" maxlength="11" style="width:133" onblur="sololet(this)" onFocus="doCambiaRadio(document.frm1.rad1,'2');"></td>
    <% } %>
    <td width="15%">&nbsp;</td>
  </tr>
  <tr>
    <td width="18%" align="left"></td>
    <% if ( (valorRB1!=null) && (valorRB1.equals("3")) ) { %>
    <td width="35%" align="left"><input type="radio" value="3" checked name="rad1">Por Tiempo Inactividad mayor a</td>
	<%} else{%>
	<td width="35%" align="left"><input type="radio" value="3" name="rad1">Por Tiempo Inactividad mayor a</td>
	<% }%>
	<% if (request.getAttribute("buscarTiempo")!=null){%>
	<td width="32%"><input type="text" value="<%=request.getAttribute("buscarTiempo") %>"name="tiempo" size="3" name="tiempo" maxlength="3" style="width:30" onblur="sololet(this)" onFocus="doCambiaRadio(document.frm1.rad1,'3');"> dias </td>
    <%} else {%>
    <td width="32%"><input type="text" name="tiempo" size="3" name="tiempo" maxlength="3" style="width:30" onblur="sololet(this)" onFocus="doCambiaRadio(document.frm1.rad1,'3');"> dias </td>
    <% }%>
    <td width="15%"><input type="checkbox" name="chkRefrescaAuto" value="checkbox" <% if (request.getAttribute("sRefrescaAuto")!=null){%> checked<%}%> onclick="javascript:refrescarAutomaticamente()">
  Refrescar Automaticamente</tr>
  <% /********** FIN DESCAJ *********/ %>
  <tr>
    <td width="100%" colspan="4">
      <center><br>
	 	    <A href="javascript:OrgNueva()" onmouseover="javascript:mensaje_status('Nueva Organizacion');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="<%=request.getContextPath()%>/images/btn_nuevo.gif" ></a> 
      </center>
      </td>
  </tr>
</table>

<!-- ************************************************************************************************************ -->
<logic:present name="listaMantOrg">
	<table class=grilla>
	  <tr class=grilla2>
	    <th width="11%" height="14">RUC</th>
	    <th width="8%"  height="14">USUARIO ADM</th>
	    <th width="16%" height="14">RAZON SOCIAL</th>
	    <%-- <th width="4%"  height="14">SIGLAS</th> removido 18sep --%>
	    <th width="18%" height="14">DIRECCION</th>
	    <%-- 22oct fechas --%>
	    <th width="8%" height="14">FECHA DE AFILIACION</th>
	    <th  height="14"><b>SALDO</b></th>
	    <th width="8%" height="14">ULTIMO ACCESO</th>
	    <th width="8%" height="14">DIAS DESDE ULTIMO ACCESO</th>
	    
		<% if (perfilId != Constantes.PERFIL_CAJERO) {%>	    
	    <th width="8%"  height="14">NRO USUARIOS</th>
	    <th height="14">Edici&oacute;n</th>
	    <th height="14">Estado</th>
	    <th height="14">Pago</th>
   	    <%  }  %>
   	    
   	    <% if (perfilId == Constantes.PERFIL_CAJERO) {%>
   	    <th width="7%" height="14"><b>PREPAGO</b></th>
	    <th width="7%" height="14"><b>CLAVE</b></th>  	    	
   	    <%  }  %>
	  </tr>
	  <%
	  	//atributos de diseno
	  	boolean flag1=false;
	  	String dFila, dColumna;
	  %>
		<logic:iterate name="listaMantOrg" id="item" scope="request">
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
	          <td <%=dColumna%> width="11%" height="27" align="center"><bean:write name="item" property="ruc"/></td>
	          <td <%=dColumna%> width="8%"  height="27"               ><bean:write name="item" property="usuarioId"/></td>
	          <td <%=dColumna%> width="16%" height="27"               ><bean:write name="item" property="razsoc"/></td>
	          <%-- removido 18sep <td <%=dColumna%> width="4%"  height="27"               ><bean:write name="item" property="siglas"/></td> --%>
	          <td <%=dColumna%> width="18%" height="27"               ><bean:write name="item" property="direccion"/></td>
	          
	          <td <%=dColumna%> width="8%" height="27"               ><bean:write name="item" property="fechaAfiliacion"/></td>
	          <td <%=dColumna%> height="27" align="center">S/. <bean:write name="item" property="saldo"/></td>
	          <td <%=dColumna%> width="8%" height="27"               ><bean:write name="item" property="fechaUltimoAcceso"/></td>
	          <td <%=dColumna%> width="8%" height="27" align="center"><bean:write name="item" property="diasDesdeUltimoAcceso"/></td>
	          
	          <% if (perfilId != Constantes.PERFIL_CAJERO) {%>	    
	          <td <%=dColumna%> width="8%"  height="27" align="center"><bean:write name="item" property="num_usu"/></td>
	          <!-- boton Edicion -->
	          <td <%=dColumna%>             height="27" align="center">
	          	<a href="javascript:Editar('<bean:write name="item" property="cod_org"/>')" onmouseover="javascript:mensaje_status('Editar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Editar</a>
	          </td>
	          
	          <!-- boton Estado -->
	          <td <%=dColumna%>             height="27" align="center">
	          	
				<logic:equal name="item" property="flagActivo" value="0">
						<a href="Javascript:doActiva('<bean:write name="item" property="cod_org"/>')" onmouseover="javascript:mensaje_status('Activar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Activar</a>
				</logic:equal>	          
				<logic:equal name="item" property="flagActivo" value="1">
						<a href="Javascript:doDesactiva('<bean:write name="item" property="cod_org"/>')" onmouseover="javascript:mensaje_status('Desactivar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Desactivar</a>
				</logic:equal>		
			  </td>
			  
			  <!-- boton Pago -->
	          <td <%=dColumna%>             height="27" align="center">
			<logic:equal name="item" property="tipoOrg" value="EXTERNO">				
				<logic:equal name="item" property="flagExonPago" value="0">
						<a href="Javascript:doExonera('<bean:write name="item" property="cod_org"/>')" onmouseover="javascript:mensaje_status('Exonerar de Pago');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Exonerar</a>
				</logic:equal>	          
				<logic:equal name="item" property="flagExonPago" value="1">
						<a href="Javascript:doExonera2('<bean:write name="item" property="cod_org"/>')" onmouseover="javascript:mensaje_status('Activar Pago');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Activar Pago</a>
				</logic:equal>	
			</logic:equal>
	          </td>
			  <%  }  %>
	          
	   	    <% if (perfilId == Constantes.PERFIL_CAJERO) {%>
	          <td <%=dColumna%> width="7%"  height="27" align="center"><a href="javascript:doPrepago('<bean:write name="item" property="lineaPrepago"/>');" onmouseover="javascript:mensaje_status('Prepagar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="images/ico_prepago.gif"></a></td>
	          <td <%=dColumna%> width="7%" height="27"  align="center"><a href="javascript:doCambioClave('<bean:write name="item" property="usuarioId"/>');" onmouseover="javascript:mensaje_status('Cambiar Contrasena');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="images/ico_clave.gif"></a></td>   	    	
	   	    <%  }  %>          
	        </tr>
		</logic:iterate>
	</table>
</logic:present>	

</form>


<% if (modo == 1) 
   {%>
<script LANGUAGE="JavaScript">
	doCambiaRadio(document.frm1.rad1,'<%=beanFormulario.getValue01()%>');
	document.frm1.razsoc.value     = '<%=beanFormulario.getValue02()%>';
	document.frm1.ruc.value        = '<%=beanFormulario.getValue03()%>';
	document.frm1.tiempo.value     = '<%=beanFormulario.getValue04()%>';
</script>
<% } %>

<%
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
%>
<br>
<script LANGUAGE="JavaScript">
	alert("<%=validacionException.getMensaje()%>");
	<% 
	String foco = validacionException.getFocus();
	if (foco.trim().length() > 0) {%>
		document.frm1.<%=foco%>.focus();
	<% } %>
</script>
<% } %>
<SCRIPT LANGUAGE="JavaScript">
<% if (request.getAttribute("sRefrescaAuto")!=null){%>refrescarAutomaticamente()<%}%>
</SCRIPT>

</body>
</html>