<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso.tld" prefix="expresso"%>
<%@ taglib uri="/WEB-INF/tld/expresso-logic.tld" prefix="logic"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">

<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>

<script language="Javascript">
function Redondear(dblNumero, intDecimales)
{	
	if (isNaN(intDecimalesT) == true)
		{ intDecimalesT = 0};

	if (isNaN(dblNumeroT) == true)
		{ dblNumeroT = 0};
		
	var intDecimalesT = parseInt(intDecimales);
	var dblNumeroT = parseFloat(dblNumero);

	if (intDecimalesT > 0)
		{
			if (dblNumeroT != 0)
				{	
					dblNumeroT = 	Math.round(dblNumeroT * Math.pow(10, intDecimalesT)) / (Math.pow(10, intDecimalesT));					
				}
		}
	return dblNumeroT;	
}

function FormatDecimal(dblNumero, intNumDec)
{
	var dblNumeroT = parseFloat(dblNumero);
		
	if (isNaN(dblNumeroT) == true)
		{	dblNumeroT = "0.00";}
	else
		{			
			dblNumeroT = dblNumeroT.toString();
			var intPuntoDecimal = dblNumeroT.indexOf(".");
			var intLimite = 0; 
			
			intNumDec = intNumDec;

			if (intPuntoDecimal > -1)
				{	intLimite = intNumDec + 1 - (dblNumeroT.length - intPuntoDecimal);}
			else
				{	intLimite = intNumDec;
					dblNumeroT = dblNumeroT + "."};
			
			for (var i = 0; i < intLimite; i++)
				{	dblNumeroT = dblNumeroT + "0";	}
		}
	return dblNumeroT;
}

function RecalcularPrecios(){

	if(isNaN(document.form1.valorUIT.value) || esVacio(document.form1.valorUIT.value))
	{
		alert("Valor de UIT incorrecto.El valor de la IUT debe ser numérico");
		document.form1.valorUIT.focus();
		return;
	}
	
	if(document.form1.valorUIT.value.indexOf(".")>=0)
	{
		alert("Valor de UIT incorrecto.El valor de la IUT debe ser numérico entero");
		document.form1.valorUIT.focus();
		return;		
	}
	
	var obj = document.form1;
	var valorActualUIT = obj.valorUIT.value
	var porcUIT = obj.porc_uit
	
	for(var i = 0 ; i < porcUIT.length ; i++){	
	
		if(isNaN(porcUIT[i].value) || esVacio(porcUIT[i].value))
		{
			alert("Porcentaje de UIT incorrecto. El valor del Porcentaje de UIT debe ser numérico");
			document.form1.porc_uit[i].focus();
			return;
		}
		var nuevoValor = (porcUIT[i].value/100)*valorActualUIT;
		obj.pre_calc[i].value = FormatDecimal(Redondear(nuevoValor,2),2);
		obj.pre_oficial[i].value = FormatDecimal(Redondear(nuevoValor,2),2);
	}
	return;
}

function ()
{
  if(validarformulario())
  {

  }
}

function grabarNuevosDatos()
{
	if(isNaN(document.form1.valorUIT.value) || esVacio(document.form1.valorUIT.value))
	{
		alert("Valor de UIT incorrecto. El valor de la IUT debe ser numérico");
		document.form1.user.focus();
		return;
	}

	var obj = document.form1;
	var valorActualUIT = obj.valorUIT.value
	var pre_oficial = obj.pre_oficial
	var porcUIT = obj.porc_uit

	for(var i = 0 ; i < porcUIT.length ; i++){	
		if(isNaN(porcUIT[i].value) || esVacio(porcUIT[i].value))
		{
			alert("Porcentaje de UIT incorrecto. El valor del Porcentaje de UIT debe ser numérico");
			document.form1.porc_uit[i].focus();
			return;
		}
	}
	
	
	//validacion de precios oficiales
	for(var i = 0 ; i < pre_oficial.length ; i++)
	{	
		if(isNaN(pre_oficial[i].value) || esVacio(pre_oficial[i].value))
		{
			alert("Precio Oficial incorrecto. El valor del Precio debe ser numérico");
			document.form1.pre_oficial[i].focus();
			return;
		}
		
		//no puede ser negativo
		var po = pre_oficial[i].value;
		if (po<0)
		{
			alert("Precio Oficial incorrecto. El valor no puede ser negativo");
			document.form1.pre_oficial[i].focus();
			return;		
		}
	}
	
	if(isNaN(document.form1.c_cre.value) || esVacio(document.form1.c_cre.value))
	{
		alert("Valor de Costo Administrativo Pagos c/ Tarjeta Crédito (sobre el monto) incorrecto.\nEl valor debe ser numérico");
		document.form1.c_cre.focus();
		return;
	}
	
	if(isNaN(document.form1.c_debit.value) || esVacio(document.form1.c_debit.value))
	{
		alert("Valor de Costo Administrativo Pagos c/ Tarjeta Débito (sobre el monto) incorrecto.\nEl valor debe ser numérico");
		document.form1.c_debit.focus();
		return;
	}

  	document.form1.method = "POST";
	document.form1.action = "/iri/RecalcularTarifas.do?state=recalcularTarifas";
	document.form1.submit();
}

</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table class=titulo>
<tr><td><FONT COLOR=BLACK>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt; </FONT>Servicios y Tarifas de la Extranet</td></tr>
</table>
<br>
<form name = "form1" method="post" onSubmit="if (!grabarNuevosDatos()) return false;" class=formulario>
<table style="width: 600px;background-color : D6D6D6" cellpadding=1 cellspacing=0 border=0>
  <tr >
    <td width="240">Valor actual UIT</td>
    <td width="90" align="center" >
      <input type="text" name="valorUIT" size="5" value="<%= (String)request.getAttribute("V_A_UIT") %>" >
    </td>
    <td width="90">&nbsp;
    </td>
    <td width="80" align="center">
	<A href="javascript:RecalcularPrecios()" onmouseover="javascript:mensaje_status('Recalcular');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="<%=request.getContextPath()%>/images/btn_recalcula.gif"></a>      
    </td>
  </tr>
</table>  
<table class=tablasinestilo>  
  <tr>
    <td width="240">Nombre del Servicio</td>
    <td width="90" align="center">(%) de UIT</td>
    <td width="90" align="center">&nbsp;Precio Calculado (S/.)</td>
    <td width="80" align="center">Precio Oficial</td>
  </tr>
  <tr>  
  	<td colspan="4">  
  	<hr>
  	</td>
  </tr>  
  <expresso:ElementCollection type="output">		
  <expresso:ElementIterator>
  <expresso:OutputTag name="servTarifas"> 
  <tr>
	<input type="hidden" name="id_servicio" value="<expresso:AttributeTag name="ID_SERVICIO"/>">
	<!--Tarifario-->
	<input type="hidden" name="id_tarifa" value="<expresso:AttributeTag name="ID_TARIFA"/>">
	<td width="240"><expresso:AttributeTag name="NOMBRE"/></td>
	<td width="90" align="center"><input type="text" name="porc_uit" size="5" value="<expresso:AttributeTag name="PORC_UIT"/>" ></td>
    <td width="90" align="center"><input type="text" name="pre_calc" size="5" value="<expresso:AttributeTag name="PRECIO_CALCULADO"/>" disabled="true"></td>
    <td width="80" align="center"><input type="text" name="pre_oficial" size="5" value="<expresso:AttributeTag name="PRECIO_OFICIAL"/>"></td>
  </tr>
  </expresso:OutputTag> 
  </expresso:ElementIterator>
  </expresso:ElementCollection>  

  <tr>
            <td colspan=4><hr></td>
  </tr>

  <tr>
            <td width="240">Costo Administrativo (% sobre el monto)</td>
            <td width="180" colspan=2>Pagos c/ Tarjeta Cr&eacute;dito</td>
            <td width="80" align="center"><input type="text" name="c_cre" size="5" value="<%= (String)request.getAttribute("C_CRED") %>" ></td>
  </tr>
  <tr>
            <td width="240"></td>
            <td width="180" colspan=2>Pagos c/ Tarjeta D&eacute;bito</td>
            <td width="80" align="center"><input type="text" name="c_debit" size="5" value="<%= (String)request.getAttribute("C_DEBIT") %>" ></td>
  </tr> 
  <tr>
            <td colspan=4><hr></td>
  </tr>
  
  <tr>
    <td width="240">&nbsp;</td>
    <td width="90">&nbsp;</td>
    <td width="90">&nbsp;</td>
    <td width="80" align="center">
    
	<A href="javascript:grabarNuevosDatos()" onmouseover="javascript:mensaje_status('Grabar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="<%=request.getContextPath()%>/images/btn_grabar.gif"></a>      
	
    </td>
  </tr>
</table> 
</form>
</body>
</html>