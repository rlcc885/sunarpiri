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
function Siguiente()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Devolucion.do?state=reimprimeRecibo";
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

<form name="form1">
<table class=formularioimpresion>
  <tr>
    <td width="10%" height="14"></td>
    <td width="25%" height="14"></td>
    <td height="14" width="37%"></td>
    <td height="14" width="13%"></td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td height="14" colspan="4">
      
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td height="150" colspan="5"></td>
  </tr>
	<tr>
    <td height="80" colspan="5"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ></td>
    <td height="14" colspan="3">
    <p align="justify" class="textoSolicitud">
     Vista, la solicitud presentada por el(la) usuario(a) requiriendo la devoluci&oacute;n de Derechos Registrales mediante 
     la hoja de tr&aacute;mite Nº.<bean:write name="solDev" property="anoHojaTramite"/>-<bean:write name="solDev" property="numHojaTramite"/> (<bean:write name="solDev" property="fechaTramite"/>)
     por <B>Pago Indebido de Publicidad Registral en L&iacute;nea</B>,
    </p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td height="20" colspan="5"></td>
  </tr>
   <tr>
    <td width="10%" height="14" ></td>
    <td height="14" colspan="3">
    <p align="justify" class="textoSolicitud">
     <B>CONSIDERANDO: </B>
     <BR>
     <BR>
      Que, mediante el documento del visto el (la) recurrente en su condici&oacute;n de presentante solicita la devoluci&oacute;n de dinero 
      pagado a la <bean:write name="zona"/>, acompañan el recibo adquirido como constancia de pago efectuado;
     </p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td height="20" colspan="5"></td>
  </tr>
   <tr>
    <td width="10%" height="14" ></td>
    <td height="14" colspan="3">
    <p align="justify" class="textoSolicitud">
      <br>
      Que, estando a lo opinado por la Sub-Gerencia de Contabilidad y Finanzas con   informe
       Nº<bean:write name="solDev" property="numInforme"/> de <bean:write name="solDev" property="fechaInforme"/> 
       y con la visaci&oacute;n de la Oficina Legal, en ejercicio de las atribuciones conferidas por el art&iacute;culo 98
       del Reglamento de Organizaci&oacute;n y Funciones de la Superintendencia Nacional de los Registros P&uacute;blicos
       aprobada por Resoluci&oacute;n Suprema Nº 139-2002-JUS&nbsp;<logic:present name="resolucion"><bean:write name="resolucion" /></logic:present>;
    </p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
   <tr>
    <td height="20" colspan="5"></td>
  </tr>
   <tr>
    <td width="10%" height="14" ></td>
    <td height="14" colspan="3" class="textoSolicitud">
    <B>SE RESUELVE:</B>
    </td>
   </tr>
   <tr>
    <td width="10%" height="14" ></td>
    <td height="14" colspan="3">
    <p align="justify" class="textoSolicitud">
     <br>
      <B>Art&iacute;culo 1&ordm;</B> Autorizar a la Oficina de Tesorer&iacute;a de la Sub-Gerencia de Contabilidad y Finanzas la Devoluci&oacute;n
       de Derechos Registrales por Pago Indebido de Publicidad Registral en L&iacute;nea:    </p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
   <tr>
    <td height="20" colspan="5"></td>
  </tr>
  
  <tr>
  <td width="10%" height="14" ></td>
    <th width="25%" height="14" align="center" class="textoSolicitud"><b>Recibo</b></th>
    <th height="14" align="center" class="textoSolicitud" width="37%"><b>Interesado</b></th>
    <th height="14" align="center" class="textoSolicitud" width="13%"><b>Importe </b></th>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
  <td width="10%" height="14" ></td>
    <td width="25%" height="14" align="center" class="textoSolicitud"><bean:write name="comprobante" property="numeroDoc"/>(<bean:write name="comprobante" property="fecha_hora"/>)</td>
    <td height="14" class="textoSolicitud" align="center" width="37%"><bean:write name="comprobante" property="nombreEntidad"/></td>   
    <td height="14" align="center" class="textoSolicitud" width="13%">S/.<u><bean:write name="comprobante" property="monto"/></u></td> 
    <td width="10%" height="14" ></td>
  </tr>
   <tr>
    <td height="10" colspan="4"></td>
  </tr>
  <tr>
    <td height="10" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ></td>
    <td height="14"colspan="3" align="left" class="textoSolicitud"> 
    	SON: <bean:write name="montoPalabra"/> Y 00/100 NUEVOS SOLES 
    </td>
  <tr>
  <tr>
    <td height="30" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ></td>
    <td height="14" colspan="3">
    <p align="justify" class="textoSolicitud">
      <B>Art&iacute;culo 2&ordm;</B> Disponer que las devoluciones se atiendan con cargo a cuenta corriente Nº 00-000-304808
      del Banco de la Naci&oacute;n y al rubro de Ingresos por Comisiones - Tasa Registrales del Presupuesto Vigente.</p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td height="20" colspan="5"></td>
  </tr> 
  <tr>
   
    <td height="14"colspan="4" align="right" class="textoSolicitud" > 
    	Reg&iacute;strese, comun&iacute;quese y arch&iacute;vese
    </td>
   <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td height="155" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="1" ></td>
    <td height="1" colspan="2" >
       
	</td>
	 <td height="14" width="13%"></td>
    <td width="10%" height="1" ></td>
  </tr>
 
 
</table>

<br>
<div id="HOJA2" style="position:absolute; left:480px; top:30px; visibility: visible;">
<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a><br>
<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG src="images/btn_regresa.gif" border=0></a>
</div>
</form>
</body>
</html>