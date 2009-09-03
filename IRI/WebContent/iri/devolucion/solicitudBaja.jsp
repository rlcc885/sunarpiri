<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
function Siguiente()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Baja.do?state=muestraReporteSaldo";
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
<body>
	<div id="maincontent">
		<div class="innertube">
			<form name="form1">
			<logic:present name="hidRegisPubId">
			<input type="hidden" name="hidRegisPubId" value="<bean:write name="hidRegisPubId"/>">
			</logic:present>
			<logic:present name="hidOficRegId">
			<input type="hidden" name="hidOficRegId" value="<bean:write name="hidOficRegId"/>">
			</logic:present>
			<table width="600">
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
			    <td width="100%" height="14" colspan="4">
			   		<P align="center" class="tituloSolicitud"> DEVOLUCION DE MAYOR DERECHO</P>
			   	</td>
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
			    <td width="10%" height="14" >	 </td>
			    <td width="80%" height="14" colspan="2">
			    <p align="justify" class="textoSolicitud">Yo <bean:write name="solicitante" property="nombres"/>&nbsp;<bean:write name="solicitante" property="ape_pat"/>&nbsp;<bean:write name="solicitante" property="ape_mat"/>
			    <logic:present name="solicitante" property="raz_soc">
			       en representaci&oacute;n de <bean:write name="solicitante" property="raz_soc"/>,
			    </logic:present>
			    &nbsp;con domicilio en 
			    <bean:write name="solicitante" property="direccion"/>, como usuario del servicio de Publicidad en L&iacute;nea me dirijo a usted para solicitar la devoluci&oacute;n de mayor derecho por la suma de S/. <bean:write name="solicitante" property="saldo"/> Nuevos Soles, 
				  para lo cual adjunto:
			  
			    </p>
			    </td>
			    <td width="10%" height="14" ></td>
			  </tr>
			  <tr>
			    <td width="10%" height="14" ></td>
			    <td width="80%" height="14" colspan="2" class="textoSolicitud">
			    	<ul>
			  			<li>Copia simple legible de mi documento nacinal de identidad (DNI), vigente a la fecha.</li>
			  			<li>Documento que acredita el pago en exceso (Recibo).  </li>
			  			<li>................................................</li>
			 			 <li>Nº de Folios .......................... </li>
					</ul>
					<br>
					<br>
					Atentamente,
			    </td>
			    <td width="10%" height="14" ></td>
			  </tr>
			  <tr>
			    <td width="10%" height="14" >	 </td>
			    <td width="80%" height="14" colspan="2" class="textoSolicitud">
			    	....................................<br><br><br>Firma
			    </td>
			    <td width="10%" height="14" ></td>
			  </tr>
			  <tr>
			    <td width="10%" height="14" ></td>
			    <td width="80%" height="14" colspan="2" class="textoSolicitud">
			    	D.N.I.  Nº <bean:write name="solicitante" property="num_doc_iden"/>
			    </td>
			    <td width="10%" height="14" ></td>
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
			<div id="HOJA2" style="position:absolute; left:480px; top:40px; visibility: visible;">
				<input type="button" class="formbutton" onclick="javascript:Imprimir()" value="Imprimir"/>
			<br>
			<logic:present name="siguiente">
				<input type="button" class="formbutton" onclick="javascript:Siguiente();" value="Siguiente"/>
			</logic:present>
			<logic:notPresent name="siguiente">
				<input type="button" class="formbutton" onclick="javascript:history.back();" value="Regresar"/>
			</logic:notPresent>
			</div>
			</form>
		</div>
	</div>
</body>
</html>