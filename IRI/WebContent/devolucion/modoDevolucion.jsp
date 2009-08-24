
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
	
function generarSolicitud(){	
		document.form1.action="/iri/Devolucion.do?state=muestraSolicitudDevolucion";
		document.form1.submit();
		
}
function validar(){
		
		if(document.form1.radTipDev[0].checked==true){
			
			document.form1.cboOficinas.disabled=false;
			return false
		}
		if(document.form1.radTipDev[1].checked==true){
			
			document.form1.cboOficinas.disabled=true;
			return false
		}
	
}


</SCRIPT>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY text="#ffffff">
<form name="form1" method="post">
<br>
<table class=titulo cellspacing=0>
  <tr> 
      <td><font color=black>Servicios&gt;&gt;</font> Devoluciones</td>
	</tr>
</table>
<br>
  

<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>MODO DEVOLUCION</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0 border="0"> 
    
        <TR>
            <TD width="8">&nbsp;</TD>
            <TD width="250">&nbsp;</TD>
           
            <TD width="182">&nbsp;</TD>
            <TD width="134">&nbsp;</TD>
            <TD width="64">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="250" valign="middle">
              <input type="hidden" name="solicitudId"  value="<bean:write name="solicitudId" scope="request"/>">	    
              <INPUT type="radio" name="radTipDev" value="E" checked="checked" onclick="validar()">
              <STRONG>Devoluci&oacute;n en efectivo</STRONG> 
            </TD>
                       
            <TD width="182" valign="middle"><b>Seleccione la oficina en la que va a presentar los documentos</b></TD>
            <TD width="134" align="right" valign="middle">

        <input type="hidden" name="oficinas">
        <select size="1" name="cboOficinas" >
          <option value="10|07" <logic:present name="odev"><logic:equal name="odev" value="0">selected</logic:equal></logic:present>>Andahuaylas</option>
          <option value="06|02" <logic:present name="odev"><logic:equal name="odev" value="1">selected</logic:equal></logic:present>>Apurimac</option>
          <option value="03|01" <logic:present name="odev"><logic:equal name="odev" value="2">selected</logic:equal></logic:present>>Arequipa</option>
          <option value="10|05" <logic:present name="odev"><logic:equal name="odev" value="3">selected</logic:equal></logic:present>>Ayacucho</option>
          <option value="01|06" <logic:present name="odev"><logic:equal name="odev" value="4">selected</logic:equal></logic:present>>Barranca</option>
          <option value="11|04" <logic:present name="odev"><logic:equal name="odev" value="5">selected</logic:equal></logic:present>>Bagua</option>
          <option value="01|02" <logic:present name="odev"><logic:equal name="odev" value="7">selected</logic:equal></logic:present>>Callao</option>
          <option value="11|02" <logic:present name="odev"><logic:equal name="odev" value="8">selected</logic:equal></logic:present>>Cajamarca</option>
          <option value="03|02" <logic:present name="odev"><logic:equal name="odev" value="9">selected</logic:equal></logic:present>>Caman&aacute;</option>
          <option value="01|05" <logic:present name="odev"><logic:equal name="odev" value="10">selected</logic:equal></logic:present>>Ca&ntilde;ete</option>
          <option value="04|02" <logic:present name="odev"><logic:equal name="odev" value="11">selected</logic:equal></logic:present>>Casma</option>
          <option value="03|03" <logic:present name="odev"><logic:equal name="odev" value="12">selected</logic:equal></logic:present>>Castilla</option>
          <option value="11|05" <logic:present name="odev"><logic:equal name="odev" value="13">selected</logic:equal></logic:present>>Chachapoyas</option>
          <option value="08|02" <logic:present name="odev"><logic:equal name="odev" value="14">selected</logic:equal></logic:present>>Chep&eacute;n</option>
          <option value="11|01" <logic:present name="odev"><logic:equal name="odev" value="46">selected</logic:equal></logic:present>>Chiclayo</option>
          <option value="04|03" <logic:present name="odev"><logic:equal name="odev" value="15">selected</logic:equal></logic:present>>Chimbote</option>
          <option value="10|02" <logic:present name="odev"><logic:equal name="odev" value="16">selected</logic:equal></logic:present>>Chincha</option>
          <option value="11|06" <logic:present name="odev"><logic:equal name="odev" value="17">selected</logic:equal></logic:present>>Chota</option>
          <!--option value="13|01" <logic:present name="odev"><logic:equal name="odev" value="18">selected</logic:equal></logic:present>>Coronel portillo</option-->
          <option value="06|01" <logic:present name="odev"><logic:equal name="odev" value="19">selected</logic:equal></logic:present>>Cusco</option>
          <option value="01|04" <logic:present name="odev"><logic:equal name="odev" value="20">selected</logic:equal></logic:present>>Huacho</option>
          <option value="08|03" <logic:present name="odev"><logic:equal name="odev" value="20">selected</logic:equal></logic:present>>Huamachuco</option>
          <option value="10|08" <logic:present name="odev"><logic:equal name="odev" value="21">selected</logic:equal></logic:present>>Huancavelica</option>
          <option value="02|01" <logic:present name="odev"><logic:equal name="odev" value="22">selected</logic:equal></logic:present>>Huancayo</option>
          <option value="10|06" <logic:present name="odev"><logic:equal name="odev" value="24">selected</logic:equal></logic:present>>Huanta</option>
          <option value="02|02" <logic:present name="odev"><logic:equal name="odev" value="23">selected</logic:equal></logic:present>>Hu&aacute;nuco</option>
          <option value="01|03" <logic:present name="odev"><logic:equal name="odev" value="01|03">selected</logic:equal></logic:present>>Huaral</option>
          <option value="04|01" <logic:present name="odev"><logic:equal name="odev" value="04|01">selected</logic:equal></logic:present>>Huaraz</option>
          <option value="10|01" <logic:present name="odev"><logic:equal name="odev" value="10|01">selected</logic:equal></logic:present>>Ica</option>
          <option value="07|02" <logic:present name="odev"><logic:equal name="odev" value="07|02">selected</logic:equal></logic:present>>Ilo</option>
          <option value="09|01" <logic:present name="odev"><logic:equal name="odev" value="29">selected</logic:equal></logic:present>>Iquitos</option>
          <option value="03|04" <logic:present name="odev"><logic:equal name="odev" value="03|04">selected</logic:equal></logic:present>>Islay</option>
          <option value="11|03" <logic:present name="odev"><logic:equal name="odev" value="11|03">selected</logic:equal></logic:present>>Ja&eacute;n</option>
          <option value="12|03" <logic:present name="odev"><logic:equal name="odev" value="12|03">selected</logic:equal></logic:present>>Juanju&iacute;</option>
          <!--option value="02|03" <logic:present name="odev"><logic:equal name="odev" value="02|03">selected</logic:equal></logic:present>>La Unión</option-->
          <option value="07|03" <logic:present name="odev"><logic:equal name="odev" value="07|03">selected</logic:equal></logic:present>>Juliaca</option>
          <option value="02|06" <logic:present name="odev"><logic:equal name="odev" value="02|06">selected</logic:equal></logic:present>>La Merced</option>
          <option value="01|01" <logic:present name="odev"><logic:equal name="odev" value="01|01">selected</logic:equal></logic:present>>Lima</option>
          <option value="06|03" <logic:present name="odev"><logic:equal name="odev" value="06|03">selected</logic:equal></logic:present>>Madre de dios</option>
          <option value="07|04" <logic:present name="odev"><logic:equal name="odev" value="07|04">selected</logic:equal></logic:present>>Moquegua</option>
          <option value="12|01" <logic:present name="odev"><logic:equal name="odev" value="12|01">selected</logic:equal></logic:present>>Moyobamba</option>
          <option value="10|04" <logic:present name="odev"><logic:equal name="odev" value="10|04">selected</logic:equal></logic:present>>Nazca</option>
          <option value="08|04" <logic:present name="odev"><logic:equal name="odev" value="08|04">selected</logic:equal></logic:present>>Otuzco</option>
          <option value="02|04" <logic:present name="odev"><logic:equal name="odev" value="02|04">selected</logic:equal></logic:present>>Pasco</option>
          <option value="10|03" <logic:present name="odev"><logic:equal name="odev" value="10|03">selected</logic:equal></logic:present>>Pisco</option>
          <option value="05|01" <logic:present name="odev"><logic:equal name="odev" value="42">selected</logic:equal></logic:present>>Piura</option>
          <option value="13|01" <logic:present name="odev"><logic:equal name="odev" value="13|01">selected</logic:equal></logic:present>>Pucallpa</option>
          <option value="07|05" <logic:present name="odev"><logic:equal name="odev" value="07|05">selected</logic:equal></logic:present>>Puno</option>
          <option value="06|04" <logic:present name="odev"><logic:equal name="odev" value="06|04">selected</logic:equal></logic:present>>Quillabamba</option>
          <option value="08|05" <logic:present name="odev"><logic:equal name="odev" value="08|05">selected</logic:equal></logic:present>>San pedro de lloc</option>
          <option value="02|05" <logic:present name="odev"><logic:equal name="odev" value="02|05">selected</logic:equal></logic:present>>Satipo</option>
          <!--option value="02|06" <logic:present name="odev"><logic:equal name="odev" value="02|06">selected</logic:equal></logic:present>>Selva Central</option-->
          <option value="06|05" <logic:present name="odev"><logic:equal name="odev" value="06|05">selected</logic:equal></logic:present>>Sicuani</option>
          <option value="05|02" <logic:present name="odev"><logic:equal name="odev" value="05|02">selected</logic:equal></logic:present>>Sullana</option>
          <option value="07|01" <logic:present name="odev"><logic:equal name="odev" value="07|01">selected</logic:equal></logic:present>>Tacna</option>
          <option value="12|02" <logic:present name="odev"><logic:equal name="odev" value="12|02">selected</logic:equal></logic:present>>Tarapoto</option>
          <option value="02|07" <logic:present name="odev"><logic:equal name="odev" value="02|07">selected</logic:equal></logic:present>>Tarma</option>
          <option value="02|08" <logic:present name="odev"><logic:equal name="odev" value="02|08">selected</logic:equal></logic:present>>Tingo Mar&iacute;a</option>
          <option value="08|01" <logic:present name="odev"><logic:equal name="odev" value="08|01">selected</logic:equal></logic:present>>Trujillo</option>
          <option value="05|03" <logic:present name="odev"><logic:equal name="odev" value="05|03">selected</logic:equal></logic:present>>Tumbes</option>
          <option value="09|02" <logic:present name="odev"><logic:equal name="odev" value="09|02">selected</logic:equal></logic:present>>Yurimaguas</option>
        </select>
      </TD>
            <TD width="64"></TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="250">
              <INPUT type="radio" name="radTipDev" value="A" onclick="validar()">
              <STRONG>Abono a Cuenta</STRONG> 
            </TD>
            
            
            <TD width="182">&nbsp;</TD>
            <TD width="134">&nbsp;</TD>
            <TD width="64"></TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD width="182">&nbsp;</TD>
            <TD width="134">&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>
         <TR>
            
            <TD colspan="5" align="center"><A href="javascript:generarSolicitud();" ><IMG src="images/btn_aceptar.gif" border="0"></A></TD>
          
        </TR>
    
    
</table>	
</form>
<table>
	<tr>
		<td><strong><font color="900000">Nota: Revise sus datos de suscripci&oacute;n para generar los documentos de devoluci&oacute;n.</font></strong></td>
	</tr>
</table>
</BODY>
</HTML>

