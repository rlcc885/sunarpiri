<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
function Aceptar()
{  
  document.form1.method = "POST";
	 document.form1.action = "/iri/";
  <logic:present name="pagsiguiente" scope="request">
	 document.form1.action = "/iri/<bean:write name="pagsiguiente"/>";
  </logic:present>
  <logic:present name="anterior" scope="request">
	window.history.back();
	 return true;
  </logic:present>
	 document.form1.submit();
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";	
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body >

<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>CAJA &gt;&gt; Afiliaci&oacute;n de Organizaci&oacute;n &gt;&gt;</font>  Comprobante de Pago</td>
	</tr>
</table><br>
<form name="form1">
<table class=formulario>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="2">
      <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="80%" height="14" colspan="4"><b>SUPERINTENDENCIA NACIONAL DE REGISTROS PUBLICOS</b></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">RUC</th>
    <td width="40%" height="14" colspan="2">20267073580</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">SERVICIO</th>
    <td width="40%" height="14" colspan="2">Publicidad Registral en Linea</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">OFICINA</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="oficina"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2"><b>RECIBO No.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="comprobanteId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
    <tr>
    <th width="40%" height="14" colspan="2"><b>ABONO No.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="abono_id"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">CAJERO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="cajero"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">FECHA/HORA</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="fecha_hora"/> HRS</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">MONTO PAGADO</th>
    <td width="40%" height="14" colspan="2">S/. <bean:write scope="session" name="comprobante" property="monto"/>0</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">USUARIO ID</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="userId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
   <tr>
    <th width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="documento"/></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="numeroDoc"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">NOMBRE/RAZON SOCIAL</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="nombreEntidad"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">ABONAR AL CONTRATO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="contratoId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">TIPO DE PAGO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="tipoPago"/></td>
    <td width="20%" height="14" colspan="2"><p align="left">&nbsp;</p></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <logic:present name="cheq" scope="request">
  <tr>
    <th width="40%" height="14" colspan="2">BANCO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="banco"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">TIPO DE CHEQUE</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="tipoCheque"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">NUMERO DE CHEQUE</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="numcheque"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:present>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2">PARA: Usuario</td>
    <td width="20%" height="14" colspan="2"><p align="left">&nbsp;</p></td>
    
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="40%" height="14" colspan="2">&nbsp;</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
</table>

<br>
<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;">
<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a>
<br>
<a href="javascript:Aceptar();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif"></a>
</div>
</form>
</body>
</html>