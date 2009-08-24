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
    <td width="30%" height="14"></td>
    <td width="50%" height="14" ></td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="90%" height="14" colspan="3">
      <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="90%" height="14"colspan="3" align="right" class="textoSolicitud"> 
    	<b><bean:write name="zonReg"/></b>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="100%" height="30" colspan="4"></td>
  </tr>
 
  <tr>
    <td width="100%" height="14" colspan="4">
   		<P align="center" class="tituloSolicitud"> DEVOLUCION DE MAYOR DERECHO</P>
   	</td>
  </tr>
  <tr>
    <td width="100%" height="20" colspan="4"></td>
  </tr>
 
   <tr>
    <td width="10%" height="14" ></td>
    <td width="90%" height="14" colspan="3" class="textoSolicitud">
   	   <bean:write name="departamento"/>, <bean:write name="fecHoy"/>
   	</td>
  </tr>
  <tr>
    <td width="100%" height="20" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="12" ></td>
    <td width="90%" height="20" colspan="3" class="textoSolicitud">
    Se&ntilde;ores<br>
    <b>Oficina de Devoluciones - SGCF</b><br>
      <b><bean:write name="zonReg"/></b><br>
      Presente.-</td>
  </tr> 
  
  
   <tr>
    <td width="100%" height="40" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ><input type="hidden" name="solicitudId" value="<bean:write name="solicitud" property="solicitud_id"/>">	 </td>
    <td width="80%" height="14" colspan="2">
    <p align="justify" class="textoSolicitud">Yo <bean:write name="solicitante" property="nombres"/>&nbsp;<bean:write name="solicitante" property="ape_pat"/>&nbsp;<bean:write name="solicitante" property="ape_mat"/>
    <logic:present name="tipo">
    <logic:equal name="tipo" value="O">
    en representacion de <bean:write name="solicitante" property="raz_soc"/>,
    </logic:equal>
    </logic:present>
    &nbsp;con domicilio en 
    <bean:write name="solicitante" property="direccion"/>, como presentante de la solicitud  Nº <bean:write name="solicitud" property="solicitud_id"/>&nbsp; de fecha <bean:write name="solicitud" property="ts_crea"/>&nbsp; correspondiente al registro de 
    <logic:present name="solicitud" property="objetoSolicitudLista">
    <logic:iterate id="objsol" name="solicitud" property="objetoSolicitudLista" scope="request">		
		<bean:write name="objsol" property="objetoSolicitudLista" property="certificado_desc"/>		
	  </logic:iterate></logic:present>, me dirijo a usted para solicitar la devoluci&oacute;n de mayor derecho por la suma de S/. <bean:write name="solicitud" property="total"/> Nuevos Soles, 
	  para lo cual adjunto:  
    </p>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="100%" height="20" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ><input type="hidden" name="solicitudId" value="<bean:write name="solicitud" property="solicitud_id"/>">	 </td>
    <td width="80%" height="14" colspan="2" class="textoSolicitud">
    	<ul>
  			<li>Copia simple legible de mi documento nacinal de identidad (DNI), vigente a la fecha.</li>
  			<li>Documento que acredita el pago en exceso (Recibo).  </li>
  			<li>................................................</li>
 			 <li>Nº de Folios .......................... </li>
		</ul>
		<br>
		<br><br><br>
		Atentamente,
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="100%" height="70" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="14" ><input type="hidden" name="solicitudId" value="<bean:write name="solicitud" property="solicitud_id"/>">	 </td>
    <td width="80%" height="14" colspan="2" class="textoSolicitud">
    	....................................<br>Firma
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="10%" height="14" ><input type="hidden" name="solicitudId" value="<bean:write name="solicitud" property="solicitud_id"/>">	 </td>
    <td width="80%" height="14" colspan="2" class="textoSolicitud">
    	D.N.I.  Nº <bean:write name="solicitante" property="num_doc_iden"/>
    </td>
    <td width="10%" height="14" ></td>
  </tr>
  <tr>
    <td width="100%" height="140" colspan="4"></td>
  </tr>
  <tr>
    <td width="10%" height="1" ></td>
    <td width="90%" height="1" colspan="2" >
        <HR>
	</td>
	 <td width="10%" height="14" ></td>
    <td width="10%" height="1" ></td>
  </tr>
   <tr>
    <td width="10%" height="1" ></td>
    <td width="9%" height="1" colspan="2" >
    <p align="justify">Nota: Las devoluciones se efect&uacute;an solo los d&iacute;as martes a las 15:00 horas, 
    en la oficina de egresos, desp&uacute;es de 15 d&iacute;as 
    de presentada su solicitud, mostrando su cargo original y documento de identidad. </p>
    </td>
    <td width="10%" height="1" ></td>
  </tr>
 
</table>

<br>
<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;">
<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a>
<br>
<logic:present name="siguiente">
<a href="javascript:Siguiente();"><img src="<%=request.getContextPath()%>/images/btn_sig.gif"></a><br>
</logic:present>
<logic:notPresent name="siguiente">
<a href="javascript:history.back();" ><img  src="images/btn_regresa.gif"  border=0></a>
</logic:notPresent>
</div>
</form>
</body>
</html>