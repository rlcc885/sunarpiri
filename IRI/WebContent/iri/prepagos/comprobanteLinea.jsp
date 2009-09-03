<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
	<script language="JavaScript" src="javascript/util.js">
	</script>
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<script language="JavaScript">
	function Imprimir()
	{
		//HOJA2.style.visibility="hidden";
		//HOJA3.style.visibility="hidden";
		window.print();
		//HOJA2.style.visibility="visible";
		//HOJA3.style.visibility="visible";	
	}
	</script>
</head>
<body >
	<div id="maincontent">
		<div class="innertube">
			<br>
				<logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
			        <b><font color="#949400">PREPAGOS &gt;&gt;</font><font color="#666666">Incremento de Saldo</b>
			    </logic:equal>
			    <logic:notEqual name="comprobante" property="tipoPub" value="S" scope="session">
			    	<b><font color="#949400">PUBLICIDAD CERTIFICADA &gt;&gt;</font><font color="#666666">Comprobante</b>
			    </logic:notEqual>  
			<br>

			<form name="form1">
			<table width="600px">
			  <tr>
			    <td width="20%" colspan="2">
			      <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
			    </td>
			  </tr>
			  </table>
			  <table class="punteadoTablaTop" width="600px">
			  <tr>
			    <td width="20%" colspan="2"><b>SUPERINTENDENCIA NACIONAL DE REGISTROS PUBLICOS</b></td>
			  </tr>
			  <tr>
			    <td width="20%" colspan="2">
			      <logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
			        Su pago se ha procesado con &eacute;xito.
			      </logic:equal>
			      <logic:equal name="comprobante" property="tipoPub" value="C" scope="session">
			        Su transacci&oacute;n se ha procesado con &eacute;xito.
			      </logic:equal>
			    </td>
			  </tr>
			  <tr>
			    <td width="20%"  >RUC</td>
			    <td >20267073580</td>
			  </tr>
			  <tr>
			    <td width="20%"  >SERVICIO</td>
			    <td >
			      <logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
			        Publicidad Registral en Linea
			      </logic:equal>
			      <logic:notEqual name="comprobante" property="tipoPub" value="S" scope="session">
			        Publicidad Certificada en Linea
			      </logic:notEqual>
			    </td>
			  </tr>
			  <tr>
			    <td width="20%"  >OFICINA</td>
			    <td  >WEB</td>
			  </tr>
			  <logic:present name="comprobante" property="comprobanteId" scope="session">
			  <tr>
			    <td width="20%"  >RECIBO No.</td>
			    <td  ><bean:write scope="session" name="comprobante" property="comprobanteId"/></td>
			  </tr>
			  </logic:present>
			  <logic:present name="comprobante" property="solicitudId" scope="session">
			  <tr>
			    <td width="20%"  >SOLICITUD No.</td>
			    <td  ><bean:write scope="session" name="comprobante" property="solicitudId"/></td>
			  </tr>
			  <logic:present name="comprobante" property="solDesc" scope="session">
			  <logic:notEqual name="comprobante" property="solDesc" scope="session" value="">
			  <tr>
			    <td width="20%"  >DESCRIPCION.</td>
			    <td ><bean:write scope="session" name="comprobante" property="solDesc" filter="false"/></td>
			  </tr>
			  </logic:notEqual>
			  </logic:present>
			  </logic:present>
			  <logic:present name="comprobante" property="cajero" scope="session">
			  <tr>
			    <td width="20%" >CAJERO</td>
			    <td  ><bean:write scope="session" name="comprobante" property="cajero"/></td>
			  </tr>
			  </logic:present>
			  <logic:notPresent name="comprobante" property="cajero" scope="session">
			  <tr>
			    <td width="20%" >CAJERO</td>
			    <td  >Pago en L&iacute;nea</td>
			  </tr>
			  </logic:notPresent>
			  <tr>
			    <td width="20%" >FECHA/HORA</td>
			    <td  ><bean:write scope="session" name="comprobante" property="fecha_hora"/> HRS</td>
			  </tr>
			  <tr>
			    <td width="20%"  >MONTO PAGADO</td>
			    <td  >S/. <bean:write scope="session" name="comprobante" property="monto"/>0</td>
			  </tr>
			  <logic:present name="comprobante" property="userId" scope="session">
			  <tr>
			    <td width="20%"  >USUARIO ID</td>
			    <td  ><bean:write scope="session" name="comprobante" property="userId"/></td>
			  </tr>
			  </logic:present>
			  <tr>
			    <td width="20%"  >NOMBRE/RAZON SOCIAL</td>
			    <td  ><bean:write scope="session" name="comprobante" property="nombreEntidad"/></td>
			  </tr>
			  <logic:present name="comprobante" property="contratoId" scope="session">
			  <tr>
			    <td width="20%"  >ABONAR AL CONTRATO</td>
			    <td  ><bean:write scope="session" name="comprobante" property="contratoId"/></td>
			  </tr>
			  </logic:present>
			  <tr>
			    <td width="20%"  >TIPO DE PAGO</td>
			    <td  ><bean:write scope="session" name="comprobante" property="tipoPago"/></td>
			  </tr>
			  <tr>
			    <td width="20%"  >PARA:</td>
			    <td  ><p align="left">&nbsp;Usuario</p></td>
			  </tr>
			</table>
			
			<br>
			<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;"> 
				<table>
					<tr>
						<td>
						  <input type="button" class="formbutton" value="Imprimir" onclick="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;" />
						</td>
					</tr>
				</table>
			</div>	  
			<div id="HOJA3" style="position:absolute; left:480px; top:95px; visibility: visible;">
				<table>
					<tr>
						<td>
							<logic:notEqual name="comprobante" property="tipoPub" value="X" scope="session">
								<logic:equal name="comprobante" property="tipoPub" value="S" scope="session">
								  <a href="<%=request.getContextPath()%>/IncrementarSaldoIRI.do">
								</logic:equal>
								<logic:equal name="comprobante" property="tipoPub" value="C" scope="session">
								  <a href="<%=request.getContextPath()%>/CertificadosIRI.do">
								</logic:equal>
								<input type="button" class="formbutton" value="Regresar"/>
							</logic:notEqual>
						</td>
					</tr>
				</table>
			</div>
			
			</form>

			<script>
			window.top.frames[0].location.reload();
			</script>
		</div>
	</div>
</body>
</html>