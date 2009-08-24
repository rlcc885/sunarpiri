<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="JavaScript">
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
</script>
</head>
<body ><!--onload="CargaDatos()"-->
<br>
<table width="600" border="0" cellpadding="0" cellspacing="2">
  <tr> 
	<td>
	<logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
        <b>PREPAGOS<font size="1">>></font><font color="#993300">Incremento de Saldo</font></b>
    </logic:equal>
    <logic:notEqual name="comprobante" property="tipoPub" value="S" scope="session">
        <b>PUBLICIDAD CERTIFICADA <font size="1">>></font><font color="#993300"> Comprobante</font></b>
    </logic:notEqual>  
	  
	</td>
  </tr> 
  <tr> 
	<td bgcolor="#000000"><img src="<%=request.getContextPath()%>/images/space.gif" width="5" height="1"></td>
  </tr>
</table>
<br>

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
    <td width="80%" height="14" colspan="4">
      <logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
        Su pago se ha procesado con &eacute;xito.
      </logic:equal>
      <logic:equal name="comprobante" property="tipoPub" value="C" scope="session">
        Su transacci&oacute;n se ha procesado con &eacute;xito.
      </logic:equal>
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
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
    <td width="40%" height="14" colspan="2">
      <logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
        Publicidad Registral en Linea
      </logic:equal>
      <logic:notEqual name="comprobante" property="tipoPub" value="S" scope="session">
        Publicidad Certificada en Linea
      </logic:notEqual>
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">OFICINA</th>
    <td width="40%" height="14" colspan="2">WEB</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <logic:present name="comprobante" property="comprobanteId" scope="session">
  <tr>
    <th width="40%" height="14" colspan="2"><b>RECIBO No.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="comprobanteId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:present>
  <logic:present name="comprobante" property="solicitudId" scope="session">
  <tr>
    <th width="40%" height="14" colspan="2"><b>SOLICITUD No.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="solicitudId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <logic:present name="comprobante" property="solDesc" scope="session">
  <logic:notEqual name="comprobante" property="solDesc" scope="session" value="">
  <tr>
    <th width="40%" height="14" colspan="2"><b>DESCRIPCION.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="solDesc" filter="false"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:notEqual>
  </logic:present>
  
  </logic:present>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <logic:present name="comprobante" property="cajero" scope="session">
  <tr>
    <th width="40%" height="14" colspan="2">CAJERO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="cajero"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:present>
  <logic:notPresent name="comprobante" property="cajero" scope="session">
  <tr>
    <th width="40%" height="14" colspan="2">CAJERO</th>
    <td width="40%" height="14" colspan="2">Pago en L&iacute;nea</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:notPresent>
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
  <logic:present name="comprobante" property="userId" scope="session">
  <tr>
    <th width="40%" height="14" colspan="2">USUARIO ID</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="userId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:present>
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
  <logic:present name="comprobante" property="contratoId" scope="session">
  <tr>
    <th width="40%" height="14" colspan="2">ABONAR AL CONTRATO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="session" name="comprobante" property="contratoId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  </logic:present>
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
  <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
  </a>
</div>

  
  <br>
<div id="HOJA3" style="position:absolute; left:485px; top:95px; visibility: visible;">
	
	<logic:notEqual name="comprobante" property="tipoPub" value="X" scope="session">
	
		<logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
		  <a href="/iri/IncrementarSaldo.do">
		</logic:equal>
		<logic:equal name="comprobante" property="tipoPub" value="C" scope="session">
		  <a href="<%=request.getContextPath()%>/Certificados.do">
		</logic:equal>
		    <img src="<%=request.getContextPath()%>/images/btn_regresa.gif">
		
	
	</logic:notEqual>
		 
		  
</div>
</form>


<script>
window.top.frames[0].location.reload();
</script>
</body>
</html>