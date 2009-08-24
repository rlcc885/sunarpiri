<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%
			String[] arrDays = (String[]) request.getAttribute("arrDays");
			String[] arrMonths = (String[]) request.getAttribute("arrMonths");
			String[] arrYears = (String[]) request.getAttribute("arrYears");
			String selectedDay = (String) request.getAttribute("selectedDay");
			String selectedMonth = (String) request.getAttribute("selectedMonth");
			String selectedYear = (String) request.getAttribute("selectedYear");
%>
<html>
<head>
	<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
		<link href="styles/global.css" type="text/css" rel="stylesheet">
		
	<title>Formulario D&iacute;as no laborables - Datos a Ingresar</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<script type="text/javascript">
function Validar(){
	cboOficina = document.frm1.cboOficinas.value;
	cboDia = document.frm1.dia.value;
	cboMes = document.frm1.mes.value;
	cboAnio = document.frm1.anio.value;
	cboEstado = document.frm1.cboEstado.value;
		if(cboOficina == "00"){
			alert('Seleccione una oficina.');
		}else{
			if(cboDia == "00" || cboMes == "00" || cboAnio == "00" ){
			alert('Ingrese la fecha correctamente.');
			}else{
				if(document.getElementById('txtDesc').value== ""){
					alert('Ingrese una descripcion.');
				}else{
					if(document.getElementById('txtDocSust').value== ""){
					alert('Ingrese un documento de sustento.');
					}else{
						if (cboEstado=="00"){
							alert("Seleccione el Estado.");
						}
						else{
						document.frm1.action="/iri/DiaNoLabo.do?state=grabarDia";		
						document.frm1.submit();
						}
					}
				}
			}
		}
}
function Cancelar (){
	history.go(-1)
}
</script>
<body>
<br>
<table cellspacing=0 class=titulo>
  <tr>
	<td>
		<FONT COLOR="black">DIAS NO LABORABLES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Ingreso de d&iacute;as no laborables </FONT>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
  <table class=tablasinestilo cellspacing=0 border="0">
    <tr>
        <th colspan=5>DATOS PARA EL INGRESO DE DIAS NO LABORABLES</th>
    </tr>
    <tr>
    	<td width="5">&nbsp;</td>
    	<td width="150">
    		<strong>Oficina Registral  <font color="900000">*</font></strong>
    	</td>
      	<td width="233">
      		<select size="1" name="cboOficinas" style="width:180" >
	        		<option value="00" selected>Seleccionar</option>
	        		<option value="10|07">Andahuaylas</option>
	        		<option value="06|02">Apurimac</option>
	        		<option value="03|01">Arequipa</option>
			        <option value="10|05">Ayacucho</option>
			        <option value="01|06">Barranca</option>
			        <option value="11|04">Bagua</option>
			        <option value="01|02">Callao</option>
			        <option value="11|02">Cajamarca</option>
			        <option value="03|02">Caman&aacute;</option>
			        <option value="01|05">Ca&ntilde;ete</option>
			        <option value="04|02">Casma</option>
			        <option value="03|03">Castilla</option>
			        <option value="11|05">Chachapoyas</option>
			        <option value="08|02">Chep&eacute;n</option>
			        <option value="11|01">Chiclayo</option>
			        <option value="04|03">Chimbote</option>
			        <option value="10|02">Chincha</option>
			        <option value="11|06">Chota</option>
			        <!--option value="13|01">Coronel portillo</option-->
			        <option value="06|01">Cusco</option>
			        <option value="01|04">Huacho</option>
			        <option value="08|03">Huamachuco</option>
			        <option value="10|08">Huancavelica</option>
			        <option value="02|01">Huancayo</option>
			        <option value="10|06">Huanta</option>
			        <option value="02|02">Hu&aacute;nuco</option>
			        <option value="01|03">Huaral</option>
			        <option value="04|01">Huaraz</option>
			        <option value="10|01">Ica</option>
			        <option value="07|02">Ilo</option>
			        <option value="09|01">Iquitos</option>
			        <option value="03|04">Islay</option>
			        <option value="11|03">Ja&eacute;n</option>
			        <option value="12|03">Juanju&iacute;</option>
			        <option value="07|03">Juliaca</option>
			        <option value="02|06">La Merced</option>        
			        <option value="01|01" >Lima</option>
			        <option value="06|03">Madre de dios</option>
			        <option value="07|04">Moquegua</option>
			        <option value="12|01">Moyobamba</option>
			        <option value="10|04">Nazca</option>
			        <option value="08|04">Otuzco</option>
			        <option value="02|04">Pasco</option>
			        <option value="10|03">Pisco</option>
			        <option value="05|01">Piura</option>
			        <option value="13|01">Pucallpa</option>
			        <option value="07|05">Puno</option>
			        <option value="06|04">Quillabamba</option>
			        <option value="08|05">San pedro de lloc</option>
			        <option value="02|05">Satipo</option>
			        <!--option value="02|06">Selva Central</option-->
			        <option value="06|05">Sicuani</option>
			        <option value="05|02">Sullana</option>
			        <option value="07|01">Tacna</option>
			        <option value="12|02">Tarapoto</option>
			        <option value="02|07">Tarma</option>
			        <option value="02|08">Tingo Mar&iacute;a</option>
			        <option value="08|01">Trujillo</option>
			        <option value="05|03">Tumbes</option>
			        <option value="09|02">Yurimaguas</option>
		</select>
        </td>
        <tr>
	        <td>&nbsp;</td>
		<td>&nbsp;</td>
		<td width="233">&nbsp;</td>
		<td width="18">&nbsp;</td>
		<td>&nbsp;</td>
    	</tr>
    	<td>&nbsp;</td>
    	<td width="150">
    		<strong>Dia  <font color="900000">*</font></strong>
    	</td>
      	<td width="250">d&iacute;a
      		<select size="1" name="dia">
				<option value="00">00</option>
				<% for (int w = 0; w < arrDays.length; w++ ) { %>
		    		<option value ="<%=arrDays[w]%>" <%if (arrDays[w].equals(selectedDay)){%>selected<%}%>><%=arrDays[w]%></option>
		    	<% } %>
			</select> mes <select size="1" name="mes">
				<option value="00">00</option>
				<% for (int w = 0; w < arrMonths.length; w++ ) { %>
			    		<option value ="<%=arrMonths[w]%>" <%if (arrMonths[w].equals(selectedMonth)){%>selected<%}%>><%=arrMonths[w]%></option>
			    <% } %>
			</select> año<select size="1" name="anio">
				<option value="00">0000</option>
				<% for (int w = 0; w < arrYears.length; w++ ) { %>
			    		<option value ="<%=arrYears[w]%>" <%if (arrYears[w].equals(selectedYear)){%>selected<%}%>><%=arrYears[w]%></option>
			    <% } %>
			</select> 
	<td width="25" align="center"></td>
		    <td width="25">
		    </td>
		    <td align="center" width="25"></td>
		    <td width="25">
		    </td>
		    <td align="center" width="25"></td>
		    <td width="25">
			</td>
        
        <td>&nbsp;</td>

    <tr>
        <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td width="233">&nbsp;</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td width="150">
          <strong>Descripcion  <font color="900000">*</font></strong>
        </td>
        <td width="233">
      	    <input name="txtDesc" id ="txtDesc" type="text"  style="width:180" onblur="sololet(this)">		
	</td>
	<td width="18">&nbsp;</td>
        <tr>
        <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td width="233">&nbsp;</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td>
    </tr>
    
    <tr>
        <td>&nbsp;</td>
        <td width="150">
          <strong>Doc. Sustento <font color="900000">*</font></strong>
        </td>
        <td width="233">
      	    <input name="txtDocSust" id="txtDocSust" type="text" style="width:180" onblur="sololet(this)">		
	</td>
        <td width="18">&nbsp;</td>
        <tr>
        <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td width="233">&nbsp;</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
    <tr>
        <td>&nbsp;</td>
        <td width="150">
          <strong>Estado  <font color="900000">*</font></strong>
        </td>
        <td width="233"><select size="1" name="cboEstado" onChange="frm1.hidOfic.value = document.frm1.cboOficinas.options[document.frm1.cboOficinas.selectedIndex].text" style="width:180" >
	        		<option value="00">Seleccionar</option>
	        		<option value="A">Activo</option>
	        		<option value="I">Inactivo</option>
	        		</select>		
	</td>
        <td width="18">&nbsp;</td>
        <tr>
        <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td width="233">&nbsp;</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td width="150">
          <A href="javascript:Validar();" onmouseover="javascript:mensaje_status('Grabar dia no laborable');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	      <IMG src="<%=request.getContextPath()%>/images/btn_grabar.gif" border="0">
	  </A>
        </td>
        <td width="233">
      	    <A href="javascript:Cancelar();" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	      <IMG src="<%=request.getContextPath()%>/images/btn_cancelar2.gif" border="0">
	  </A>
	</td>
        <td width="18">&nbsp;</td>
        <tr>
        <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td width="233">&nbsp;</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td>
    </tr>
  	<td>&nbsp;</td>
  	<td width="150" colspan="2">
         </td>
  	<tr>
    	<td colspan="5">&nbsp;</td>
  	</tr>
	</table>
</form>
<table>
	<tr>
		<td><strong><font color="900000">* Los datos son obligatorios</font></strong></td>
	</tr>
</table>
</body>
</html>

