<%@ page contentType="text/html;charset=ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<script language="JavaScript" src="javascript/util.js">
</script>

<link rel="stylesheet" href="styles/global.css">

<script language="javascript">

function verSolDev(solDev,solId)
{
	document.form1.hidNumSolDev.value=solDev;
	document.form1.solicitudId.value=solId;
	document.form1.method="POST";
	document.form1.action="/iri/Devolucion.do?state=muestraSolUsrDev";
	document.form1.submit();
}
function verSolDevBaja(solDev,cuentaId)
{
	document.form1.hidNumSolDev.value=solDev;
	document.form1.hidCuentaId.value=cuentaId;
	document.form1.method="POST";
	document.form1.action="/iri/Baja.do?state=muestraSolBaja";
	document.form1.submit();
	
}
function verSaldo(solDev,cuentaId)
{
	document.form1.hidMuestraRegresar.value="true";
	document.form1.hidNumSolDev.value=solDev;
	document.form1.hidCuentaId.value=cuentaId;
	document.form1.method="POST";
	document.form1.action="/iri/Baja.do?state=reporteSaldoUsrDevolucion";
	document.form1.submit();
	
}
function verSegEstado(solId)
{
	document.form1.solicitudId.value=solId;
	document.form1.method="POST";
	document.form1.action="/iri/Devolucion.do?state=muestraConstanciaSegEstado";
	document.form1.submit();
	
}
function verInforme(solDev,solId)
{
	document.form1.hidNumSolDev.value=solDev;
	document.form1.solicitudId.value=solId;
	document.form1.method="POST";
	document.form1.action="/iri/Devolucion.do?state=muestraInformeDevolucion";
	document.form1.submit();
}
function verInformeBaja(solDev)
{
	document.form1.hidNumSolDev.value=solDev;
	document.form1.method="POST";
	document.form1.action="/iri/Baja.do?state=muestraInformeBaja";
	document.form1.submit();
}
function verResolucion(solDev,solId)
{
	document.form1.hidNumSolDev.value=solDev;
	document.form1.solicitudId.value=solId;
	document.form1.method="POST";
	document.form1.action="/iri/Devolucion.do?state=muestraResolucion";
	document.form1.submit();
}
function verResolucionBaja(solDev)
{
	document.form1.hidNumSolDev.value=solDev;
	document.form1.method="POST";
	document.form1.action="/iri/Baja.do?state=muestraResolucionBaja";
	document.form1.submit();
}
function verRecibo(solId)
{
	document.form1.hidOcultaSiguiente.value="true";
	document.form1.solicitudId.value=solId;
	document.form1.method="POST";
	document.form1.action="/iri/Devolucion.do?state=reimprimeRecibo";
	document.form1.submit();
}

function Buscar() {
	document.form1.method="POST";
	document.form1.action="/iri/DevolucionesAdministrador.do?state=buscaSolicitudDev";
	document.form1.submit();
	
}


</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head> 


<body>
<form name="form1">
<br>
<table class=titulo>
  <tr> 
       <td><font color=black>SERVICIOS &gt;&gt;</font> Consulta Devoluciones</td>
  </tr>
</table>
<br>
<table class=formulario width="626" cellspacing=0 border="0">
  <tr>
    <td  valign="middle" colspan="1" height="14" width="69"></td>
    <td  valign="middle" colspan="2" height="14" width="291"><b>Desde</b></td>
    <td valign="middle" width="220"><b>Hasta</b></td>
    <td  valign="middle" colspan="1" height="14" width="46"></td>
    </tr>
  <tr>
      <td  valign="middle" colspan="1" height="14" width="69"></td>
    <td  valign="middle" nowrap colspan="2" width="291">d&iacute;a<select size="1" name="diainicio">
        <option <logic:present name="di1"><logic:equal name="di1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="31">selected</logic:equal></logic:present>>31</option>
      </select>mes<select size="1" name="mesinicio">
        <option <logic:present name="mi1"><logic:equal name="mi1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="12">selected</logic:equal></logic:present>>12</option>
      </select>a&ntilde;o<select size="1" name="anoinicio">
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2003">selected</logic:equal></logic:present>>2003</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2004">selected</logic:equal></logic:present>>2004</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2005">selected</logic:equal></logic:present>>2005</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2006">selected</logic:equal></logic:present>>2006</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2007">selected</logic:equal></logic:present>>2007</option>
        
      </select></td>
    <td valign="middle" nowrap colspan="1" width="220">
      &nbsp;d&iacute;a <select size="1" name="diafin">
        <option <logic:present name="df1"><logic:equal name="df1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="31">selected</logic:equal></logic:present>>31</option>
      </select>mes&nbsp; <select size="1" name="mesfin">
        <option <logic:present name="mf1"><logic:equal name="mf1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="12">selected</logic:equal></logic:present>>12</option>      
      </select>
      a&ntilde;o<select size="1" name="anofin">
        <option <logic:present name="af1"><logic:equal name="af1" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2003">selected</logic:equal></logic:present>>2003</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2004">selected</logic:equal></logic:present>>2004</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2005">selected</logic:equal></logic:present>>2005</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2006">selected</logic:equal></logic:present>>2006</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2007">selected</logic:equal></logic:present>>2007</option>
        
      </select>
    </td>
    <td  valign="middle" colspan="1" height="14" width="46"></td>
  </tr>
  <tr>
      <td  valign="middle" colspan="1" height="14" width="69"></td>
    <td valign="middle"><b>Estado</b></td>
    <td valign="middle"><select size="1" name="estado">
  		<option <logic:present name="estado"><logic:equal name="estado" value="T"> selected</logic:equal></logic:present> value="T">TODAS</option>
		<option <logic:present name="estado"><logic:equal name="estado" value="1"> selected</logic:equal></logic:present> value="1">CON INFORME</option>
		<option <logic:present name="estado"><logic:equal name="estado" value="2"> selected</logic:equal></logic:present> value="2">CON RESOLUCION</option>
		<option <logic:present name="estado"><logic:equal name="estado" value="3"> selected</logic:equal></logic:present> value="3">PAGADO</option>
	  </select></td>
    <td valign="middle" colspan="1" align="right" width="220">  <input type="image" src="images/btn_buscar.gif" border="0" onClick="return Buscar();"></td>
    <td  valign="middle" colspan="1" height="14" width="46"></td>
  </tr>

 
</table>
<br>
<table width="626">
	<tr>
		<td>
			<logic:present name="contador0">
				<center><font color="red"><b>No se han encontrado solicitudes de devoluci&oacute;n</b></font></center>
			</logic:present>
		</td>
	</tr>
</table>

<logic:present name="contador1">

<table class=grilla>
<tr>
    <td width="5%" align="center" height="19"></td>
    <td width="20%" align="center" height="19"></td>
    <td width="5%" align="center" height="19"></td>
    <td width="5%" align="center" height="19"></td>
    <td width="5%" align="center" height="19"></td>
    <td width="20%" align="center" height="19"></td>
    <td width="15%" align="center" height="19"></td>
    <td width="10%" align="center" height="19"></td>
    <td width="10%" align="center" height="19"></td>
  </tr>

  <tr>
    <th width="5%" align="center" height="12">Sec</th>
    <th width="10%" align="center" height="12">Solicitante</th>
    <th width="15%" align="center" height="12">Hoja Tr&aacute;mite/Fecha</th>
    <th width="5%" align="center" height="12">Ver Detalle Sol. Dev </th>
    <th width="5%" align="center" height="12">Ver Detalle Recibo Pago</th>
    <th width="5%" align="center" height="12">Ver Detalle Seg. Estado Solicitud</th>
    <th width="5%" align="center" height="12">Ver Detalle Saldo Disp.</th>
    <th width="20%" align="center" height="12">Informe de Devoluci&oacute;n
			</th>
    <th width="15%" align="center" height="12">Resoluci&oacute;n
			</th>
    <th width="10%" align="center" height="12">Fecha Registro</th>
    <th width="10%" align="center" height="12">Estado</th>
  </tr>
  <%int i=0;%>
	<logic:iterate name="lstSolDevolucion" id="item" scope="request" >
	  <tr class=grilla2>
	    <td width="5%" align="center" height="12"><bean:write name="item" property="idSolicitudDev"/></td>
	    <td width="10%" align="center" height="12"><bean:write name="item" property="nombre"/></td>
	    <td width="15%" align="center" height="12">
	    	<bean:write name="item" property="anoHojaTramite"/>-<bean:write name="item" property="numHojaTramite"/> <bean:write name="item" property="fechaTramite"/> 
	    </td>
	    <td width="5%" align="center" height="12">
		    <logic:notPresent name="item" property="cuentaId">
		    <a href="javascript:verSolDev('<bean:write name="item" property="idSolicitudDev"/>','<bean:write name="item" property="solicitudId"/>');"><img src="images/lupa.gif"></a>
		    </logic:notPresent>
		    <logic:present name="item" property="cuentaId">
		    <a href="javascript:verSolDevBaja('<bean:write name="item" property="idSolicitudDev"/>','<bean:write name="item" property="cuentaId"/>');"><img src="images/lupa.gif"></a>
		    </logic:present>
	    </td>
	    <td width="5%" align="center" height="12">
		    <logic:notPresent name="item" property="cuentaId">
		    <a href="javascript:verRecibo('<bean:write name="item" property="solicitudId"/>');"><img src="images/lupa.gif"></a>
		    </logic:notPresent>
		    <logic:present name="item" property="cuentaId">
		    &nbsp;
		    </logic:present>
	    </td>
	    <td width="5%" align="center" height="12">
		    <logic:notPresent name="item" property="cuentaId">
			<a href="javascript:verSegEstado('<bean:write name="item" property="solicitudId"/>');"><img src="images/lupa.gif"></a>
		    </logic:notPresent>
		    <logic:present name="item" property="cuentaId">
		    &nbsp;
		    </logic:present>
	    </td>
	    <td width="5%" align="center" height="12">
	    	<logic:notPresent name="item" property="cuentaId">
			&nbsp;
		    </logic:notPresent>
		 <logic:present name="item" property="cuentaId">
		    <a href="javascript:verSaldo('<bean:write name="item" property="idSolicitudDev"/>','<bean:write name="item" property="cuentaId"/>');"><img src="images/lupa.gif"></a>
		    </logic:present>   
		</td>
	    <td width="20%" align="center" height="12" nowrap="nowrap">
	    	<logic:present name="item" property="numInforme">
		    	<bean:write name="item" property="numInforme"/>
		    	<logic:notPresent name="item" property="cuentaId">
	               <a href="javascript:verInforme('<bean:write name="item" property="idSolicitudDev"/>','<bean:write name="item" property="solicitudId"/>');"><img src="images/lupa.gif">
	            </logic:notPresent>
			    <logic:present name="item" property="cuentaId">
			    	<a href="javascript:verInformeBaja('<bean:write name="item" property="idSolicitudDev"/>');"><img src="images/lupa.gif">
			    </logic:present> 
	    	</logic:present>
	    </td>
        <td width="15%" align="center" height="12" nowrap="nowrap">
	        <logic:present name="item" property="numInforme">
		    	<bean:write name="item" property="numResolucion"/>
		        <logic:notPresent name="item" property="cuentaId">
		           <a href="javascript:verResolucion('<bean:write name="item" property="idSolicitudDev"/>','<bean:write name="item" property="solicitudId"/>');"><img src="images/lupa.gif"></a>
		        </logic:notPresent>
			    <logic:present name="item" property="cuentaId">
			    	<a href="javascript:verResolucionBaja('<bean:write name="item" property="idSolicitudDev"/>');"><img src="images/lupa.gif"></a>
			    </logic:present>   
	        </logic:present>
        </td>
	    <td width="10%" align="center" height="12">
	    	<bean:write name="item" property="fechaSolicitud"/>
	    </td>
  	    <td width="10%" align="center" height="12" nowrap="nowrap">
  	    	<bean:write name="item" property="estado"/>
  	    </td>
	  </tr>
	  <%i++;%>
	</logic:iterate>  
</table>
</logic:present>

<input type="hidden" name="hidNumSolDev">
<input type="hidden" name="solicitudId">
<input type="hidden" name="hidCuentaId">
<input type="hidden" name="hidOcultaSiguiente">
<input type="hidden" name="hidMuestraRegresar">



</form>



</body>
</html>