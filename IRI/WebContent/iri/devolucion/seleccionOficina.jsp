
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
	
function generarSolicitud(){	
		document.form1.action="/iri/Baja.do?state=muestraSolicitud";
		document.form1.submit();
		
}
function Cancelar(){
		document.form1.action="/iri/EditarDatosPersonales.do?state=verFormulario";
		document.form1.submit();
}

</SCRIPT>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<form name="form1" method="post">
<b><font color="#949400">Servicios &gt;&gt;</font><font color="#666666">Dar de baja cuenta</font></b>
<br>
  
	<table cellspacing=0>
      <tr> 
    	<td width="685" colspan="8">
    		<p align="left"><font color="#949400" size="2"><strong>MODO DEVOLUCION</strong></font></p>
    	</td>
      </tr>
	</table>
	

  <table cellspacing=0 border="0"> 
    
        <TR>
            <TD width="8">&nbsp;</TD>
            <TD width="14">&nbsp;</TD>
           
            <TD width="483">&nbsp;</TD>
            <TD width="31">&nbsp;</TD>
            <TD width="64">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD valign="middle" width="14"></TD>
                       
            <TD valign="middle" width="483"><b>Usted ha seleccionado la opci&oacute;n de darse de baja de nuestro servicio,si es correcta la opci&oacute;n elija la oficina en la que solicitar&aacute; la devoluci&oacute;n de su saldo, en caso contrario presione cancelar.</b></TD>
            <TD align="right" valign="middle" width="31">
		
        <input type="hidden" name="oficinas">
      </TD>
            <TD width="64"></TD>
        </TR>
        <TR>
            <TD width="8">&nbsp;</TD>
            <TD width="14">&nbsp;</TD>
           
            <TD width="483">&nbsp;</TD>
            <TD width="31">&nbsp;</TD>
            <TD width="64">&nbsp;</TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="14"></TD>
            
            
            <TD width="483">&nbsp;<SELECT size="1" name="cboOficinas">
			<OPTION value="10|07"
				<logic:present name="odev"><logic:equal name="odev" value="0">selected</logic:equal></logic:present>="">Andahuaylas</OPTION>
			<OPTION value="06|02"
				<logic:present name="odev"><logic:equal name="odev" value="1">selected</logic:equal></logic:present>="">Apurimac</OPTION>
			<OPTION value="03|01"
				<logic:present name="odev"><logic:equal name="odev" value="2">selected</logic:equal></logic:present>="">Arequipa</OPTION>
			<OPTION value="10|05"
				<logic:present name="odev"><logic:equal name="odev" value="3">selected</logic:equal></logic:present>="">Ayacucho</OPTION>
			<OPTION value="01|06"
				<logic:present name="odev"><logic:equal name="odev" value="4">selected</logic:equal></logic:present>="">Barranca</OPTION>
			<OPTION value="11|04"
				<logic:present name="odev"><logic:equal name="odev" value="5">selected</logic:equal></logic:present>="">Bagua</OPTION>
			<OPTION value="01|02"
				<logic:present name="odev"><logic:equal name="odev" value="7">selected</logic:equal></logic:present>="">Callao</OPTION>
			<OPTION value="11|02"
				<logic:present name="odev"><logic:equal name="odev" value="8">selected</logic:equal></logic:present>="">Cajamarca</OPTION>
			<OPTION value="03|02"
				<logic:present name="odev"><logic:equal name="odev" value="9">selected</logic:equal></logic:present>="">Caman&aacute;</OPTION>
			<OPTION value="01|05"
				<logic:present name="odev"><logic:equal name="odev" value="10">selected</logic:equal></logic:present>="">Ca&ntilde;ete</OPTION>
			<OPTION value="04|02"
				<logic:present name="odev"><logic:equal name="odev" value="11">selected</logic:equal></logic:present>="">Casma</OPTION>
			<OPTION value="03|03"
				<logic:present name="odev"><logic:equal name="odev" value="12">selected</logic:equal></logic:present>="">Castilla</OPTION>
			<OPTION value="11|05"
				<logic:present name="odev"><logic:equal name="odev" value="13">selected</logic:equal></logic:present>="">Chachapoyas</OPTION>
			<OPTION value="08|02"
				<logic:present name="odev"><logic:equal name="odev" value="14">selected</logic:equal></logic:present>="">Chep&eacute;n</OPTION>
			<OPTION value="11|01"
				<logic:present name="odev"><logic:equal name="odev" value="46">selected</logic:equal></logic:present>="">Chiclayo</OPTION>
			<OPTION value="04|03"
				<logic:present name="odev"><logic:equal name="odev" value="15">selected</logic:equal></logic:present>="">Chimbote</OPTION>
			<OPTION value="10|02"
				<logic:present name="odev"><logic:equal name="odev" value="16">selected</logic:equal></logic:present>="">Chincha</OPTION>
			<OPTION value="11|06"
				<logic:present name="odev"><logic:equal name="odev" value="17">selected</logic:equal></logic:present>="">Chota</OPTION>
			<!--option value="13|01" <logic:present name="odev"><logic:equal name="odev" value="18">selected</logic:equal></logic:present>>Coronel portillo</option-->
			<OPTION value="06|01"
				<logic:present name="odev"><logic:equal name="odev" value="19">selected</logic:equal></logic:present>="">Cusco</OPTION>
			<OPTION value="01|04"
				<logic:present name="odev"><logic:equal name="odev" value="20">selected</logic:equal></logic:present>="">Huacho</OPTION>
			<OPTION value="08|03"
				<logic:present name="odev"><logic:equal name="odev" value="20">selected</logic:equal></logic:present>="">Huamachuco</OPTION>
			<OPTION value="10|08"
				<logic:present name="odev"><logic:equal name="odev" value="21">selected</logic:equal></logic:present>="">Huancavelica</OPTION>
			<OPTION value="02|01"
				<logic:present name="odev"><logic:equal name="odev" value="22">selected</logic:equal></logic:present>="">Huancayo</OPTION>
			<OPTION value="10|06"
				<logic:present name="odev"><logic:equal name="odev" value="24">selected</logic:equal></logic:present>="">Huanta</OPTION>
			<OPTION value="02|02"
				<logic:present name="odev"><logic:equal name="odev" value="23">selected</logic:equal></logic:present>="">Hu&aacute;nuco</OPTION>
			<OPTION value="01|03"
				<logic:present name="odev"><logic:equal name="odev" value="01|03">selected</logic:equal></logic:present>="">Huaral</OPTION>
			<OPTION value="04|01"
				<logic:present name="odev"><logic:equal name="odev" value="04|01">selected</logic:equal></logic:present>="">Huaraz</OPTION>
			<OPTION value="10|01"
				<logic:present name="odev"><logic:equal name="odev" value="10|01">selected</logic:equal></logic:present>="">Ica</OPTION>
			<OPTION value="07|02"
				<logic:present name="odev"><logic:equal name="odev" value="07|02">selected</logic:equal></logic:present>="">Ilo</OPTION>
			<OPTION value="09|01"
				<logic:present name="odev"><logic:equal name="odev" value="29">selected</logic:equal></logic:present>="">Iquitos</OPTION>
			<OPTION value="03|04"
				<logic:present name="odev"><logic:equal name="odev" value="03|04">selected</logic:equal></logic:present>="">Islay</OPTION>
			<OPTION value="11|03"
				<logic:present name="odev"><logic:equal name="odev" value="11|03">selected</logic:equal></logic:present>="">Ja&eacute;n</OPTION>
			<OPTION value="12|03"
				<logic:present name="odev"><logic:equal name="odev" value="12|03">selected</logic:equal></logic:present>="">Juanju&iacute;</OPTION>
			<!--option value="02|03" <logic:present name="odev"><logic:equal name="odev" value="02|03">selected</logic:equal></logic:present>>La Unión</option-->
			<OPTION value="07|03"
				<logic:present name="odev"><logic:equal name="odev" value="07|03">selected</logic:equal></logic:present>="">Juliaca</OPTION>
			<OPTION value="02|06"
				<logic:present name="odev"><logic:equal name="odev" value="02|06">selected</logic:equal></logic:present>="">La
			Merced</OPTION>
			<OPTION value="01|01"
				<logic:present name="odev"><logic:equal name="odev" value="01|01">selected</logic:equal></logic:present>="">Lima</OPTION>
			<OPTION value="06|03"
				<logic:present name="odev"><logic:equal name="odev" value="06|03">selected</logic:equal></logic:present>="">Madre
			de dios</OPTION>
			<OPTION value="07|04"
				<logic:present name="odev"><logic:equal name="odev" value="07|04">selected</logic:equal></logic:present>="">Moquegua</OPTION>
			<OPTION value="12|01"
				<logic:present name="odev"><logic:equal name="odev" value="12|01">selected</logic:equal></logic:present>="">Moyobamba</OPTION>
			<OPTION value="10|04"
				<logic:present name="odev"><logic:equal name="odev" value="10|04">selected</logic:equal></logic:present>="">Nazca</OPTION>
			<OPTION value="08|04"
				<logic:present name="odev"><logic:equal name="odev" value="08|04">selected</logic:equal></logic:present>="">Otuzco</OPTION>
			<OPTION value="02|04"
				<logic:present name="odev"><logic:equal name="odev" value="02|04">selected</logic:equal></logic:present>="">Pasco</OPTION>
			<OPTION value="10|03"
				<logic:present name="odev"><logic:equal name="odev" value="10|03">selected</logic:equal></logic:present>="">Pisco</OPTION>
			<OPTION value="05|01"
				<logic:present name="odev"><logic:equal name="odev" value="42">selected</logic:equal></logic:present>="">Piura</OPTION>
			<OPTION value="13|01"
				<logic:present name="odev"><logic:equal name="odev" value="13|01">selected</logic:equal></logic:present>="">Pucallpa</OPTION>
			<OPTION value="07|05"
				<logic:present name="odev"><logic:equal name="odev" value="07|05">selected</logic:equal></logic:present>="">Puno</OPTION>
			<OPTION value="06|04"
				<logic:present name="odev"><logic:equal name="odev" value="06|04">selected</logic:equal></logic:present>="">Quillabamba</OPTION>
			<OPTION value="08|05"
				<logic:present name="odev"><logic:equal name="odev" value="08|05">selected</logic:equal></logic:present>="">San
			pedro de lloc</OPTION>
			<OPTION value="02|05"
				<logic:present name="odev"><logic:equal name="odev" value="02|05">selected</logic:equal></logic:present>="">Satipo</OPTION>
			<!--option value="02|06" <logic:present name="odev"><logic:equal name="odev" value="02|06">selected</logic:equal></logic:present>>Selva Central</option-->
			<OPTION value="06|05"
				<logic:present name="odev"><logic:equal name="odev" value="06|05">selected</logic:equal></logic:present>="">Sicuani</OPTION>
			<OPTION value="05|02"
				<logic:present name="odev"><logic:equal name="odev" value="05|02">selected</logic:equal></logic:present>="">Sullana</OPTION>
			<OPTION value="07|01"
				<logic:present name="odev"><logic:equal name="odev" value="07|01">selected</logic:equal></logic:present>="">Tacna</OPTION>
			<OPTION value="12|02"
				<logic:present name="odev"><logic:equal name="odev" value="12|02">selected</logic:equal></logic:present>="">Tarapoto</OPTION>
			<OPTION value="02|07"
				<logic:present name="odev"><logic:equal name="odev" value="02|07">selected</logic:equal></logic:present>="">Tarma</OPTION>
			<OPTION value="02|08"
				<logic:present name="odev"><logic:equal name="odev" value="02|08">selected</logic:equal></logic:present>="">Tingo
			Mar&iacute;a</OPTION>
			<OPTION value="08|01"
				<logic:present name="odev"><logic:equal name="odev" value="08|01">selected</logic:equal></logic:present>="">Trujillo</OPTION>
			<OPTION value="05|03"
				<logic:present name="odev"><logic:equal name="odev" value="05|03">selected</logic:equal></logic:present>="">Tumbes</OPTION>
			<OPTION value="09|02"
				<logic:present name="odev"><logic:equal name="odev" value="09|02">selected</logic:equal></logic:present>="">Yurimaguas</OPTION>
		</SELECT></TD>
            <TD width="31">&nbsp;</TD>
            <TD width="64"></TD>
        </TR>
        <TR>
            <TD>&nbsp;</TD>
            <TD width="14">&nbsp;</TD>
            <TD width="483">&nbsp;</TD>
            <TD width="31">&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>
         <TR>
            
            <TD colspan="5" align="center">
            	<input type="button" class="formbutton" value="Aceptar" onclick="javascript:generarSolicitud();" />
            	<input type="button" class="formbutton" value="Cancelar" onclick="javascript:Cancelar();" />
            </TD>

        </TR>
    
    
</table>	
</form>
</BODY>
</HTML>

