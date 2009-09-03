<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="javascript/util.js"></script>
	<title>Formulario D&iacute;as no laborables - Datos a Ingresar</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<script>
function seleccionDni(){
	if(document.frm1.dni.checked) {
	document.frm1.ruc.checked=false;
	document.frm1.apePaterno.disabled=false;
	document.frm1.apeMaterno.disabled=false;
	document.frm1.nombres.disabled=false;
	document.frm1.numDocu.disabled=false;
	document.frm1.cboTipoDocu.disabled=false;
	document.frm1.razonSoc.disabled=true;
	document.frm1.txtRuc.disabled=true;
	document.frm1.razonSoc.value="";
	document.frm1.txtRuc.value="";
	}
}
function seleccionRuc(){
	if(document.frm1.ruc.checked) {
		document.frm1.apePaterno.disabled=true;
		document.frm1.apeMaterno.disabled=true;
		document.frm1.nombres.disabled=true;
		document.frm1.numDocu.disabled=true;
		document.frm1.cboTipoDocu.disabled=true;
		document.frm1.razonSoc.disabled=false;
		document.frm1.txtRuc.disabled=false;
		document.frm1.apePaterno.value="";
		document.frm1.apeMaterno.value="";
		document.frm1.nombres.value="";
		document.frm1.numDocu.value="";
		document.frm1.dni.checked=false;
	}
	
}
function validar(){
	if(!document.frm1.dni.checked && !document.frm1.ruc.checked) {			
		alert("Seleccione si es Persona natural o jurídica.");
	}else{
		if (document.frm1.dni.checked){
			if(document.frm1.apePaterno.value==""){
				alert("Ingrese el apellido paterno");
			}else{
				if(document.frm1.apeMaterno.value==""){
					alert("Ingrese el apellido materno");
				}else{
					if(document.frm1.nombres.value==""){
						alert("Ingrese el nombre");
					}else{
						if(document.frm1.cboTipoDocu.value=="00"){
						alert("Seleccione el tipo de documento");
						}else{
							if(document.frm1.numDocu.value==""){
								alert("Ingrese el número de documento");
							}else{
								document.frm1.action="/iri/DenominacionIRI.do?state=grabarParticipante";
								document.frm1.submit();
							}
						}
					}
				}
			}
		}
		else{
			if(document.frm1.razonSoc.value==""){
				alert("Ingrese la razón social");
			}else{
				if(document.frm1.txtRuc.value==""){
					alert("Ingrese el RUC");
				}else{
					//alert("url");
					document.frm1.action="/iri/DenominacionIRI.do?state=grabarParticipante";		
					document.frm1.submit();
				}	
			}
			
		}
	}
}
function cancelar(){
	document.frm1.action="/iri/DenominacionIRI.do?state=cancelarParticipante";		
	document.frm1.submit();
}
</script>
<body>
<div id="maincontent">
		<div class="innertube">

<table width="600px"><tr><td>

		<b><FONT COLOR="#949400">SOLICITUDES <font size="1">&gt;&gt; </font></FONT>Solicitud     de inscripcion
	 <font color="black"> <font size="1">&gt;&gt; </font></font>Reserva de preferencia registral  <font
			color="black"> <font size="1">&gt;&gt; </font></font>
		</b>	
			<font color="#949400"> Ingreso participantes </FONT>

<br>
<form name="frm1" method="POST">
  <table class="punteadoTablaTop" class="tablasinestilo">
    <tr>
        <td colspan=5><b><FONT COLOR="#949400">DATOS PARA EL INGRESO DE PARTICIPANTES</FONT></b></td>
    </tr>
   
        
        <tr>
	        <td>&nbsp;</td>
		<td width="214">&nbsp;Persona:</td>
		<td width="364">Natural&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="dni" onClick="javascript:seleccionDni()"
			value="dni">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Juridica&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="ruc" onClick="javascript:seleccionRuc()" value="ruc"></td>
		<td width="18">&nbsp;</td>
		<td>&nbsp;</td>
    	</tr>
    	
    
	<tr>
        <td colspan=5><b><FONT COLOR="#949400">DATOS PARA EL INGRESO DE PARTICIPANTES NATURALES</FONT></b></td>
    </tr>

 
    <tr>
        <td>&nbsp;</td>
        <td width="214">
          Apellido Paterno:<br>
          <input type="text" name="apePaterno"  style="width:200" onblur="sololet(this)">
          <font color="#949400">*</font></td><td width="364">Apellido Materno:<br>
          <input type="text" name="apeMaterno"  style="width:200" onblur="sololet(this)"><font
			color="#949400">*</font></td><td width="278">Nombres: <br><input
			type="text" name="nombres" style="width:200" onblur="sololet(this)">&nbsp;<font
			color="#949400">*</font></td>
      
        <td width="18">&nbsp;</td></tr>
        <tr>
        <td>&nbsp;</td>
	<td width="214">&nbsp;Tipo Documento:<select size="0" name="cboTipoDocu"
			width="210">
			<option value="00">Seleccionar</option>
			<option value="09D.N.I.">DOCUMENTO NACIONAL DE INDENTIDAD</option>
			<option value="04C.I.">CARNET DE IDENTIDAD</option>
			<option value="08PS">PASAPORTE</option>
			<option value="03C.E.">CARNET DE EXTRANJERIA</option>
		</select><font
			color="#949400">*</font></td>
	<td width="364">&nbsp;N&uacute;mero Documento:<input type="text" name="numDocu" maxlength="8" style="width:210"><font
			color="#949400">*</font></td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td
    ></tr>
    
  
       <tr>
        <td colspan=5><b><FONT COLOR="#949400">DATOS PARA EL INGRESO DE PARTICIPANTES JURIDICAS</FONT></b></td>
    	</tr> 
        <tr>
        <td>&nbsp;</td>
        <td width="214" colspan="3">
          Razon Social:<br>
		&nbsp;<input type="text" name="razonSoc"  style="width:210" size="35" onblur="sololet(this)"><font color="#949400">*</font></td>
		<td width="278">&nbsp;</td>

         <td>&nbsp;</td></tr>
        <tr>
        <td>&nbsp;</td>
	<td width="214">&nbsp;Ruc:<input type="text" name="txtRuc" maxlength="11" style="width:210" onkeypress="return soloNumero(event)"><font
			color="#949400">*</font></td>
	<td width="364">&nbsp;</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td>
    </tr>
       
        <tr>
        <td>&nbsp;</td>
        
	<td width="214">&nbsp;
	
	<input class="formbutton" value="Grabar" type="button" onclick="javascript:validar();" />
	
			</td>
	<td width="364">&nbsp;
	<input class="formbutton" value="Cancelar" type="button" onclick="javascript:cancelar();" />
	</td>
	<td width="18">&nbsp;</td>
	<td>&nbsp;</td
    ></tr>

        
  	<tr>
    	<td colspan="5">&nbsp;</td>
  	</tr>
	</table>
	
</form>
<table>
	<tr>
		<td><strong><font color="#949400">* Los datos son obligatorios</font></strong></td>
	</tr>
</table>

</td></tr></table>
</div>
</div>
</body>
</html>