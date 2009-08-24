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
function Siguiente()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Devolucion.do?state=muestraConstanciaSegEstado";
  document.form1.submit();
}
</script>
</head>
<body ><!--onload="CargaDatos()"-->
<br>

<table width="600" border="0" cellpadding="0" cellspacing="2">
  <tr> 
	<td>
	    <b>SERVICIOS <font size="1">&gt;&gt;</font>Devoluciones&gt;&gt;<font color="#993300"> Reimpresi&oacute;n de comprobante</font></b>
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
        Publicidad Certificada en Linea
    </td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">OFICINA</th>
    <td width="40%" height="14" colspan="2">WEB</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2"><b>SOLICITUD No.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="solicitudId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2"><b>DESCRIPCION.</b></th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="solDesc" filter="false"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">CAJERO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="cajero"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
 
  <tr>
    <th width="40%" height="14" colspan="2">FECHA/HORA</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="fecha_hora"/> HRS</td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">MONTO PAGADO</th>
    <td width="40%" height="14" colspan="2">S/. <bean:write scope="request" name="comprobante" property="monto"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">USUARIO ID</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="userId"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">NOMBRE/RAZON SOCIAL</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="nombreEntidad"/></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <td width="40%" height="14" colspan="2"></td>
    <td width="40%" height="14" colspan="2"></td>
    <td width="20%" height="14" colspan="2"></td>
  </tr>
  <tr>
    <th width="40%" height="14" colspan="2">TIPO DE PAGO</th>
    <td width="40%" height="14" colspan="2"><bean:write scope="request" name="comprobante" property="tipoPago"/></td>
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
    <td width="20%" height="14" colspan="2"><input name="solicitudId" type="hidden" value="<bean:write scope="request" name="comprobante" property="solicitudId"/>"></td>
  </tr>
</table>

<br>
<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;"> 
  <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    <IMG height=25 hspace=4 src="images/btn_print.gif" width=83 vspace=5 border=0>
  </a>
</div>

<br>

<div id="HOJA3" style="position:absolute; left:485px; top:95px; visibility: visible;">
	
	<logic:notPresent name="ocultaSiguiente">
		<a href="javascript:Siguiente();">
			<img src="images/btn_sig.gif">
		</a>
		<br>
	</logic:notPresent>
	
	<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		<img src="images/btn_regresa.gif" >
	</a>
</div>

</form>
</body>
</html>