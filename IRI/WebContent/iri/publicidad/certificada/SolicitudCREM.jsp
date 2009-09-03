<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title>SolicitudCREM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js"></script>
<script Language="javaScript">
	
	var arrRegistro = new Array();
	var contador=0;
	var flagActivo=1;
	var flagInactivo=0;
	
	function natural()
	{

		areaNatu.style.visibility="visible";
		areaNatu.style.display="";
		areaJuri.style.visibility="hidden";
		areaJuri.style.display="none";
	}
	function juridica()
	{

		areaJuri.style.visibility="visible";
		areaJuri.style.display="";
		areaNatu.style.visibility="hidden";
		areaNatu.style.display="none";
	}
	
	
	function CambioCert()
	{	
		frm1.hidCert.value = document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].text;
	
		<logic:iterate name="arrCertificados" id="item22" scope="request">
		if(document.frm1.cboTipoCert.options[document.frm1.cboTipoCert.selectedIndex].value==<bean:write name="item22" property="codigo"/>)
		{
			document.frm1.hidGLA.value=<bean:write name="item22" property="atributo1"/>;
			document.frm1.hidArea.value=<bean:write name="item22" property="atributo2"/>;
		}
		</logic:iterate>
		

		//Opcion "REGISTRO MOBILIARIOS DE CONTRATOS"
		if(frm1.cboTipoCert.value==23){
			document.frm1.hidTipo.value="C";
			areaRegi.style.visibility="visible";
			areaRegi.style.display="";
		}
		else{
			areaRegi.style.visibility="hidden";
			areaRegi.style.display="none";
			
			if(frm1.cboTipoCert.value==21){
				document.frm1.hidTipo.value="A";
			}
			else{
				if(frm1.cboTipoCert.value==22){
					document.frm1.hidTipo.value="H";
				}
			}
		}
		
		
	
	}
	function validar(){
		
		if(frm1.cboTipoCert.value==""){
			alert("Seleccionar un tipo de Certificado");	
			return;
		}
		else{
			
			if(document.frm1.radTipoPers[0].checked==true){
				
				if(document.frm1.txtApPa.value.length<2){
					alert("El apellido paterno debe de tener al menos 2 letras");
					return;
				}
				else{
					if(document.frm1.txtApMa.value.length<2){
						alert("El apellido materno debe de tener al menos 2 letras");
						return;
					}
					else{
						if(document.frm1.txtNom.value.length<2){
							alert("El nombre debe de tener al menos 2 letras");
							return;
						}
						else{
							if(document.frm1.cboTipoParticipante.value==""){
								alert("Debe elegir un tipo de participación");
								return;
							}
							else{
								document.frm1.hidDesTipoPar.value=document.frm1.cboTipoParticipante[document.frm1.cboTipoParticipante.selectedIndex].text;
							}					
						}
					}
				}
				document.frm1.hidTipPers.value="Natural";
			}
			else{
	
				
					if(document.frm1.txtRazSoc.value.length<3){
						alert("La razón social debe tener al menos 3 caracteres");
						return;
					}
					else{
						if(document.frm1.cboTipoParticipante.value==""){
							alert("Debe elegir un tipo de participación");
							return;
						}
						else{
							document.frm1.hidDesTipoPar.value=document.frm1.cboTipoParticipante[document.frm1.cboTipoParticipante.selectedIndex].text;
						}
					}
					document.frm1.hidTipPers.value="Jurídica";
	
			}
			if(document.frm1.cboTipoCert.value==23){//crem
			
				if(document.frm1.txtAreaTipoRegistroDestino.length==0){
					alert("Seleccionar un tipo de registro");
					return;
				}
				else{
					concatena();
					if(document.frm1.txtFechaInscripAsientoDesde.value.length>0 ){
					
						if(!validFechaDDMMAAAA(document.frm1.txtFechaInscripAsientoDesde.value)){
							alert("La fecha desde no es correcta. Seguir el formato dd/mm/aaaa");
							return;
						}
						if(document.frm1.txtFechaInscripAsientoHasta.value.length>0){
							if(!validFechaDDMMAAAA(document.frm1.txtFechaInscripAsientoHasta.value)){
								alert("La fecha hasta no es correcta. Seguir el formato dd/mm/aaaa");
								return;
							}
							else{
								if(!mayor(document.frm1.txtFechaInscripAsientoHasta.value,document.frm1.txtFechaInscripAsientoDesde.value)){
								alert("La fecha hasta debe ser mayor o igual a la fecha desde");
								return;
								}
							
							}
						}
					}
					
				}
				

			}

			
		}
		
		document.frm1.action="/iri/CertificadosIRI.do?state=guardarDatosBasicos";
		document.frm1.submit();
	}
	
	function validFechaDDMMAAAA(pisFecha){

	var fecha = "";
	var cadena = /^[0-9]+[-/][0-9]+[-/][0-9]{4}$/;

	if(!cadena.test(pisFecha)) return false;
	if(pisFecha.lastIndexOf("/")>0) fecha = pisFecha.split("/");
	if(pisFecha.lastIndexOf("-")>0) fecha = pisFecha.split("-");

	var dia = fecha[0];
	var mes = fecha[1];
	var anho = fecha[2];

	var ndia  = parseInt(dia,10);
	var nmes  = parseInt(mes,10);
	var nanho = parseInt(anho,10);

	if(nmes>12) return false;

	var daysInMonth = Array(12);
	daysInMonth[1] = 31;
	daysInMonth[2] = 29;
	daysInMonth[3] = 31;
	daysInMonth[4] = 30;
	daysInMonth[5] = 31;
	daysInMonth[6] = 30;
	daysInMonth[7] = 31;
	daysInMonth[8] = 31;
	daysInMonth[9] = 30;
	daysInMonth[10] = 31;
	daysInMonth[11] = 30;
	daysInMonth[12] = 31;


	if (ndia > daysInMonth[nmes]) return false;
	if ((nmes == 2) && (ndia > daysInFebruary(nanho))) return false;

return true;
}

function daysInFebruary (year)
{
	return (  ((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0) ) ) ? 29 : 28 );
}
	
	function mayor(fecha, fecha2){ 
	
		var xMes=fecha.substring(3, 5);
		var xDia=fecha.substring(0, 2);
		var xAnio=fecha.substring(6,10); 
		var yMes=fecha2.substring(3, 5);
		var yDia=fecha2.substring(0, 2);
		var yAnio=fecha2.substring(6,10); 
		if (xAnio > yAnio)
			return true;
		else 
			if (xAnio == yAnio)
			{ 	if (xMes > yMes)
					return true;
				else
				{ 	if (xMes == yMes){
						if (xDia >= yDia)
							return true;
						else
							return false;
					}
					else
						return false; 
				}
			} 
			else
				return false;
	}
	
	function doAdd1()
	{
		var objeto = document.frm1.txtAreaTipoRegistroOrigen;
		
		if (objeto.length != 0)
		{ 
			if (objeto.selectedIndex != -1)
			{
				
				for(var i=0; i < objeto.options.length ;i++)
				{
					if (objeto.options[i].selected)
					{
						var c = objeto.options[i].value;
						var t = objeto.options[i].text;

						var bandera= false;
						
						for (var j = 0; j < arrRegistro.length ; j++)
						{
							var codigo = arrRegistro[j][0];
							
								if (codigo == c)
								{
									bandera= true;
									arrRegistro[j][2]=flagActivo;
								}
						} 
						
						if (bandera == false)
						{
							var arrx = new Array();
							
							arrx[0]=c;
							arrx[1]=t;
							arrx[2]=flagActivo;
							arrRegistro[contador] = arrx;
							contador++;
							
						}
					}
				} 
			}
		}
		
		doFill1();
	} 
	
	
	function doRemove1()
	{
	var objeto = document.frm1.txtAreaTipoRegistroDestino;
	
	if (objeto.length != 0)
	  { 
	     if (objeto.selectedIndex != -1)
	     {
			for(var i=0; i < objeto.length ;i++)
			{
		   		if (objeto.options[i].selected)
		   		{
		   			var g = objeto.options[i].value;
		   			
		   			
		   			for(var z=0; z < arrRegistro.length ; z++ ){
		   				
		   				var aux=arrRegistro[z][0];
		   				if(g == aux){
		   					arrRegistro[z][2]=flagInactivo;		
				       		
		   				}
		   			
		   			}
		   		}
	        }           
	     }
	  }   
	  
	doFill1();
	} 
	
	function doFill1()
	{
		var	cont=0;
		var objeto = document.frm1.txtAreaTipoRegistroDestino;
		if (objeto.length != 0)
		{ 	
				for(var i=0; i<objeto.options.length ;i++)
					{
						objeto.options[i]=null;
						i--;
					}
		}
		for (var j=0; j < arrRegistro.length; j++)
		{

			var xflag  = arrRegistro[j][2];
			if(xflag == flagActivo){
				var xTexto = arrRegistro[j][1];
				var xCode  = arrRegistro[j][0];
				
				objeto.options[cont] = new Option(xTexto,xCode);
				cont++;
			}
			
			
		}
	}
	
	function selectAllOptions(obj) {
		for (var i=0; i<obj.options.length; i++) {
			obj.options[i].selected = true;
		}
		doAdd1();
	}
	function removeAllOptions(obj) {
		for (var i=0; i<obj.options.length; i++) {
			obj.options[i].selected = true;
		}
		doRemove1();
	}
	
	function concatena(){
		var cadena="";
		var cadena2="";
		for(var i=0;i<document.frm1.txtAreaTipoRegistroDestino.length;i++){ 
			cadena=cadena+document.frm1.txtAreaTipoRegistroDestino[i].value;
			cadena2=cadena2+document.frm1.txtAreaTipoRegistroDestino[i].text;
			if(i!=(document.frm1.txtAreaTipoRegistroDestino.length-1)){
				cadena=cadena+",";
				cadena2=cadena2+",";
			}
		}
		document.frm1.hidTipoRegistro.value=cadena;
		document.frm1.hidDesTipoRegistro.value=cadena2;
	}
		 
</script>
</head>
<body onload="CambioCert()">
	<div id="maincontent">
		<div class="innertube">
			<form name="frm1" method="post"><br>
			<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Solicitud de Certificados</b>
			<br>
			<table>
				<tr>
					<td><strong><font color="#949400" size="2">DATOS BASICOS DE LA SOLICITUD</font></strong></td>
				</tr>
			</table>
			<input type="hidden" name="cboOficinas" value="00|00">
			<input type="hidden" name="hidOfic" value="Web">
			<input type="hidden" name="hidDesTipoPar" value="Web">
			<table class="punteadoTablaTop" width="600px">
				<tr>
					<td align=left width="130">TIPO DE CERTIFICADO
					</td>
					<td><input type="hidden" name="hidGLA">
						<input type="hidden" name="hidArea"> 
						<select	name="cboTipoCert" size="1" onChange="CambioCert()">
							<option value="">&lt;&lt; Seleccione el Tipo de	Certificado&gt;&gt;</option>
							<logic:iterate name="arrCertificados" id="item2" scope="request">
								<option value="<bean:write name="item2" property="codigo"/>">
								<bean:write name="item2" property="descripcion" /></option>
							</logic:iterate>
						</select>
						<input type="hidden" name="hidCert">
						<input type="hidden" name="hidTipo">
					</td>
					<td align="center">
						<input type="button" class="formbutton" onclick="javascript:onclick=validar();" value="Buscar" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
					</td>
				</tr>
			</table>
			<table class="punteadoTabla" width="600px">
				<tr>
					<td align=left width="130">TIPO DE PERSONA
					</td>
					<td>
						<input type="radio" name="radTipoPers" value="N" onClick="javascript:natural();" checked>
						<strong>Persona Natural</strong> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
						<input type="radio" name="radTipoPers" value="J" onClick="javascript:juridica();">
						<strong>Persona Jur&iacute;dica</strong>
						<input type="hidden" name="hidTipPers" value="Natural">
					</td>
				</tr>
			</table>
			<div id="areaNatu" style="visibility:visible;display:">
				<table class="punteadoTablaOnlyBottom" width="600px">
		        <tr>
		            <td width="120">APELLIDOS Y NOMBRES<br>de quien saldrá el certificado</td>
		            <td width="130"> 
		              <input type="text" name="txtApPa" size="20" maxlength="50" style="width:133" onblur="sololet(this)">
		      	  </td>
		      	  <td width="130"> 
		              <input type="text" name="txtApMa" size="20" maxlength="40" style="width:133" onblur="sololet(this)"/>
		      	  </td>
		      	  <td width="160"> 
		              <input type="text" name="txtNom" size="20" maxlength="40" style="width:133" onblur="sololet(this)">
		      	  </td>
		        </tr>
		        <tr>
		            <td>&nbsp;</td>
		            <td>
		              &nbsp;Apellido Paterno
		            </td>
		            <td>
		              &nbsp;Apellido Materno
		            </td>
		            <td>
		              &nbsp;Nombres
		            </td>
		          </tr>
		      </table>
					
			</div>
			<div id="areaJuri" style="visibility:hidden;display:none">
				<table class="punteadoTablaTop" width="600px">
					<tr>A nombre de quien saldrá el certificado</tr>
					<tr>
						<td align=left width="130">RAZON SOCIAL</td>
						<td><input type="text" name="txtRazSoc" size="18" maxlength="120" style="width:407" onblur="sololet(this)"></td>
					</tr>
				</table>
			</div>
					
					
					<div id="areaRegi" style="visibility:hidden;display:none">
					<table cellspacing=0>
						<tr>
							<td>&nbsp;</td>
							<td>TIPO DE REGISTRO</td>
							<td>&nbsp;</td>				
							<td>&nbsp;</td>				
							<input type="hidden" name="hidTipoRegistro">
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><select multiple  name="txtAreaTipoRegistroOrigen" size="10" width="260px"
								style="width: 260px">
								<option value="RMC">Registro Mobiliario de Contratos</option>
								<option value="VEH">Propiedad Vehicular</option>
								<option value="EMB">Propiedad Embarcacion Pesquera</option>
								<option value="BUQ">Propiedad Buques</option>
								<option value="AER">Propiedad Aeronaves</option>
								<option value="PEJ">Personas Juridicas(Participaciones)</option>
							</select>
							</td>
							<td align=center>
								<input type="button" name="todos" value="&gt;&gt;" onclick="selectAllOptions(document.frm1.txtAreaTipoRegistroOrigen)"
								title="Seleccionar Todas las Oficinas Registrales" style="width: 25px"><br>
								<input type="button" name="uno" value="&gt;"  onclick="doAdd1()"
								title="Seleccionar Oficina Registral" style="width: 25px"><br>
								<input type="button" name="runo"  value="&lt;" onclick="doRemove1()"
								title="Retirar de la Seleccion la Oficina Registral" style="width: 25px"><br>
								<input type="button" name="rtodos" value="&lt;&lt;" onclick="removeAllOptions(document.frm1.txtAreaTipoRegistroDestino)"
								title="Retirar de la Seleccion Todas las Oficinas Registrales" style="width: 25px"><br>
							</td>
							<td align=center><select name="txtAreaTipoRegistroDestino" multiple size="10"
								style="width: 260px" width="260px" >
							</select></td>
							<input type="hidden" name="hidDesTipoRegistro">
							
						</tr>
						<tr>
							 <table>
								<tr>
									<td>&nbsp;</td>
									<td>FECHA DE INSCRIPCION <br> DE ASIENTO</td>
									<td>desde</td>
									<td><input type="text" name="txtFechaInscripAsientoDesde" maxlength="10"></td>
									<td>hasta</td>
									<td><input type="text" name="txtFechaInscripAsientoHasta" maxlength="10"></td>
								</tr>	
								<tr>
									<td>&nbsp;</td>
									<td>Histórico</td>
									<td>&nbsp;</td>
									<td><input type="radio" name="rdbHistorico" checked value="1" >Sí <input type="radio" name="rdbHistorico" value="0">No</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
						</tr> 		
							 </table>
						</tr>
					</table>
					</div>
					<table>
						<tr>
					   		<td>&nbsp;</td>
								<td>TIPO DE PARTICIPACION*</td>
								<td>
									<select name="cboTipoParticipante">
										<option value="">Elija un tipo</option>
										<option value="1">Deudor</option>
										<option value="2">Acreedor</option>
										<option value="3">Representante</option>
										<option value="4">Otros tipos de participacion</option>
									</select>
								</td>
								<td>&nbsp;</td>
					  </tr>
					</table>
					
					
					<!-- /td>
					</tr>
				
			</table-->
<br>
</form>
<br>
</body>
</html>
<!--//Fin:jascencio-->
