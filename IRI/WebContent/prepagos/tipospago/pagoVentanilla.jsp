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

function validarformulario(){
	
	if(esVacio(document.form1.monto.value) || !esEntero(document.form1.monto.value) || !esEnteroMayor(document.form1.monto.value,1)){
		alert("Por favor ingrese correctamente el Monto");
		document.form1.monto.focus();
		return false;
	}	
	
	if(document.form1.tipopago[1].checked==true){
		// modificado jbugarin observaciones sunarp
		if(esVacio(document.form1.numCheque.value) || !esMayor(document.form1.numCheque.value,9)){
			alert("Por favor ingrese correctamente el Número de Cheque");
			document.form1.numCheque.focus();
			return false;
		}	
	}
	/**** AGREGADO JBUGARIN DESCAJ 08/01/07****/
	
	<% 

	if ( (((Boolean)request.getAttribute("flagActivo") ).booleanValue() == false) || (((String)request.getAttribute("estadoLineaPrepago") ).equals("0"))){
	
	%>
	
	sino2 = confirm("La cuenta o linea prepago que desea abonar NO se encuentra ACTIVA. Esta seguro que desea activar la cuenta y registrar un prepago de "+form1.monto.value+" ?"+" CAJA: "+"<%=request.getAttribute("userId")%>")
	if (sino2 == false)
	{
	
		alert("Operación Cancelada");
		return false
	}
	<% } else { %>
	/**** JBUGARIN FIN DESCAJ 08/01/07 ****/
	sino = confirm("¿Está seguro que desea registrar un prepago de "+form1.monto.value+" a la cuenta ?"+" CAJA:"+"<%=request.getAttribute("userId")%>"); //Your question.
	if (sino == false)
	{
		alert("Operación Cancelada");
		return false
	}
	
	<% } %>
	
	return true;
}

var clicked = false;

function Aceptar()
{  
	
	if( (clicked == false) && (validarformulario()) ){   
		document.form1.method = "POST";
		document.form1.action = "/iri/Ventanilla.do?state=resultadoAbonoVentanilla";
		clicked = true;
		return true;
	}
	return false; 
}
function Salir()
{ 
	
window.open("../bienvenida.html", target="_self")

}

function HideCheques()
{ 

cheques.style.visibility="hidden"
}

function ShowCheques(valor)
{  

if (valor=="C|Cheque")
 cheques.style.visibility="visible"
   if (valor=="E|Efectivo")
   cheques.style.visibility="hidden"
}


</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body >
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>CAJA &gt;&gt; </font>  Comprobante de Pago</td>
	</tr>
</table><br>
<form name="form1" onSubmit="return Aceptar();" class=formulario>
<!-- AGREGADO JBUGARIN INICIO DESCAJ 09/01/07  -->
<input type="hidden" name="flgActivo" value="<%= String.valueOf(((Boolean)request.getAttribute("flagActivo")).booleanValue())%>" >
<input type="hidden" name="userUpdate" value="<%=request.getAttribute("usuarioUpdate")%>" >
<!-- OBSERVACIONES SUNARP 21/02/2007-->
<input type="hidden" name="estadoLineaPrepago" value="<%=request.getAttribute("estadoLineaPrepago")%>" >
<input type="hidden" name="cuentaIdActualizar" value="<%=request.getAttribute("cuentaIdActualizar")%>" >
<!-- AGREGADO JBUGARIN FIN DESCAJ 09/01/07  -->
<table class=tablasinestilo>
  <tr>
    <th width="20%" height="27" colspan="2">FECHA/HORA ABONO</th>
    <td width="20%" height="27" colspan="2"><input type="text" name="T2" size="13" disabled="true"></td>
    <th width="20%" height="27" colspan="2"><b>NUMERO COMPROBANTE</b></th>
    <td width="20%" height="27" colspan="2"><b></b></td>
    <td width="20%" height="27" colspan="2"><span id=guardar><input type="image" src="images/btn_aplicar.gif" border="0" ></span>
    </td>
  </tr>
  <tr>
    <th width="20%" height="27" colspan="2">USUARIOID<input type="hidden" name="usuario" value="<bean:write name="comprobante" property="userId"/>"></th>
    <td width="20%" height="27" colspan="2"><b><bean:write name="comprobante" property="userId"/></b></td>
    <th width="20%" height="27" colspan="2">NOMBRE/RAZON SOCIAL<input type="hidden" name="nombre" value="<bean:write name="comprobante" property="nombreEntidad"/>"></th>
    <td width="20%" height="27" colspan="2"><b><bean:write name="comprobante" property="nombreEntidad"/></b></td>
    <td width="20%" height="27" colspan="2"><span id=imprime><a href="javascript:window.print();" disabled="true" ><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a></span></td>
  </tr>
  <tr>
    <th width="20%" height="27" colspan="2">ABONAR AL CONTRATO<input type="hidden" name="contratoId" value="<bean:write name="comprobante" property="contratoId"/>"></th>
    <td width="20%" height="27" colspan="2"><b><bean:write name="comprobante" property="contratoId"/></b></td>
    <th width="20%" height="27" colspan="2">MONTO</th>
    <td width="20%" height="27" colspan="2">&nbsp;<input type="text" name="monto" maxlength=5 size="5"></td>
    <td width="20%" height="27" colspan="2"><a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif"></a></td>
  </tr>
  <tr>
    <th width="20%" height="11" colspan="2">TIPO DE PAGO</th>
    <td width="20%" height="11" colspan="2"><input type="radio" value="E|Efectivo" name="tipopago" checked onClick="ShowCheques(this.value)">Efectivo&nbsp; <input type="radio" value="C|Cheque" name="tipopago" onClick="ShowCheques(this.value)">Cheque&nbsp;</td>
    <td width="20%" height="11" colspan="2"><p align="left">&nbsp;</p></td>
    <td width="20%" height="11" colspan="2">&nbsp;</td>
    <td width="20%" height="11" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2">&nbsp;</td>
  </tr>
</table>
<input type="hidden" name="linea" value="<bean:write name="linea"/>">
<div id="cheques" style="visibility:hidden">
<table class="formulario">
  <tr>
    <th width="19%">BANCO</th>
    <td width="34%">
    <select size="1" name="bancoId">
		<logic:iterate name="listaBancos" id="item" scope="request">
	          <option value="<bean:write name="item" property="id"/>|<bean:write name="item" property="descripcion"/>"><bean:write name="item" property="descripcion"/></option>
		</logic:iterate>
    </select>
    </td>
    <th width="13%">TIPO DE CHEQUE</th>
    <td width="12%">
    <select size="1" name="tipocheque">
        <option value="G|Gerencia" selected>Gerencia</option>
        <option value="S|Simple" >Simple</option>
        <option value="C|Certificado" >Certificado</option>
    </select>
    </td>
    <td width="30%"></td>
  </tr>
  <tr>
    <th width="19%">NUMERO DE CHEQUE</th>
    <!-- modificado jbugarin observaciones sunarp-->
    <td width="34%"><input type="text" name="numCheque"  maxlength="15" size="20"></td>
    <!-- modificado fin -->
    <td width="13%"></td>
    <td width="12%"></td>
    <td width="30%"></td>
  </tr>
</table>
</div>
<br>
</form>
</body>
</html>