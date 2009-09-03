<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso-bean.tld" prefix="bean"%>
<!-- saved from url=(0099)https://wiesenet2.wiese.com.pe/cclwtim.nsf/tim+input?OpenForm&D0=218120&D1=1&D2=20.00&D3=1&D4=00011 -->
<HTML>
<HEAD>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
function validarformulario(){
	if(esVacio(document.form1.numero.value)){
		alert("Datos Incorrectos. Ingrese el Número de la Tarjeta de Crédito");
		document.form1.numero.focus();
		return false;
	}

	if(isNaN(document.form1.numero.value)){
		alert("Datos Incorrectos. El Número de la Tarjeta de Crédito es de 16 Digitos");
		document.form1.numero.focus();
		return false;
	}
	
	if(document.form1.numero.value.indexOf(".")>=0){
		alert("Datos Incorrectos. El Número de la Tarjeta de Crédito es de 16 Digitos");
		document.form1.numero.focus();
		return false;
	}
		
	if(document.form1.numero.value.length!=16){
		alert("Datos Incorrectos. El Número de la Tarjeta de Crédito es de 16 Digitos");
		document.form1.numero.focus();
		return false;
	}
	
	return true;
}
function Regresar()
{
history.back()
}


function Resultado(){
	if(validarformulario()){
		document.form1.method="POST";
		document.form1.action="/iri/IncrementarSaldoIRI.do?state=iniciaCredito";
		document.form1.submit();
   	//return true;
    }
    //return false
}
</script>
<SCRIPT language="JavaScript">
  function popVisa() {
    window.open('http://www.visanet.com.pe/visa.htm','3DSecure','scrollbars=yes,Left=0,Top=120,Width=606,Height=402');
  }
</SCRIPT>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
<BODY text=#000000 bgColor=#ffffff>
	<div id="maincontent">
		<div class="innertube">
			<FORM name="form1">
				<bean:define id="gd" property="medioId" type="java.lang.String"/>
				<b><font color="#949400">PREPAGOS&gt;&gt;</font><font color="#666666">Incremento de Saldo</font></b>
				<table width="550" >
				  <tr align="center"> 
					<td>
					  	<img border="0" src="images/foto3.jpg" width="385">
						<a href="javascript:popVisa();">
				          <img src="images/verVisa.gif" border="0">
				        </a>
					</td>
				  </tr>
				</table>  
			<table width="550">
			  <tr align="center"> 
				<td>
					<b><font face="Verdana" color="#949400">Pasarela de Pagos Tarjeta VISA</font></b>
				</td>
			  </tr>
			</table>
			<table width="500" border="0" bordercolor="#949400">
			  <tr align="center" > 
				<td>
					<table border="0" width="100%" >
			          <tr> 
			            <td width="30%" align="center">
			              <a href = "http://www.visanet.com.pe" target="_blank">
						    <img src="images/visagrande.jpg" width="109" height="70" vspace="2" border="0">
						  </a>
			            </td>
			            <td width="70%">
			              <TABLE width="100%" border=0 cellSpacing=0>
			                <TBODY>
			                  <TR>
			                     <TD width="20%"> <FONT face=Verdana size=1>Monto :</FONT></TD>
			                     <TD width="30%"><INPUT maxLength=8 size=8 readonly="true" name="monto" value="<bean:write property="monto"/>"></TD>
			                     <TD width="10%"> <FONT face=Verdana size=1>Fecha:</FONT></TD>
			                     <TD width=40%><bean:write property="fecha"/></TD>
			                  </TR>
			                  <tr> 
					            <td colspan="4"><FONT face=Verdana size=1>Ingrese el n&uacute;mero de su tarjeta de cr&eacute;dito:</FONT></td>
					          </tr>
					          <tr> 
					            <td colspan="4"><INPUT maxLength=16 size=20 name="numero"></td>
					          </tr>
			                </TBODY>
			              </TABLE>
			            </td>
			          </tr>
			          <tr> 
			            <td align="center">&nbsp;</td>
			            <td><font face=Verdana size=1>Ingrese la Fecha de Vencimiento&nbsp;:</font></td>
			          </tr>
			          <tr> 
			            <td align="center">&nbsp;</td>
			            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			                <tr> 
			                  <td align="right" width="20%"><font face="Verdana" size="1">Mes :</font></td>
			                  <td width="30%"><font face=Verdana color=#ffffff size=1><b> 
			                    <select name="mes">
			                      <option value="01">Enero</option>
			                      <option value="02">Febrero</option>
			                      <option value="03">Marzo</option>
			                      <option value="04">Abril</option>
			                      <option value="05">Mayo</option>
			                      <option value="06">Junio</option>
			                      <option value="07">Julio</option>
			                      <option value="08">Agosto</option>
			                      <option value="09">Setiembre</option>
			                      <option value="10">Octubre</option>
			                      <option value="11">Noviembre</option>
			                      <option value="12">Diciembre</option>
			                    </select>
			                    </b></font></td>
			                  <td align="center" width="10%">A&ntilde;o :</td>
			                  <td width="40%"><font face=Verdana color=#ffffff size=1><b> 
			                    <select name="ano" maxlength="4">
			                      <!-- Inicio:rbahamonde:30/12/2008 -->
			                      <!-- se quito el año 2008 y se agregó el año 2014 -->
			                      <option value="2009" selected >2009</option>
			                      <option value="2010">2010</option>
			                      <option value="2011">2011</option>
			                      <option value="2012">2012</option>
			                      <option value="2013">2013</option>
			                      <option value="2014">2014</option>
			                      <!-- Fin:rbahamonde -->
			                    </select>
			                    </b></font></td>
			                </tr>
			              </table></td>
			          </tr>
			        </table></td>
			        </tr>
			        </table>
			        <table>
			        <tr align="left"> 
						<td> 
							<input type="button" class="formbutton" value="Regresar" onclick="javascript:onclick=Regresar()" />
							<input type="button" class="formbutton" value="Continuar" onclick="javascript:onclick=Resultado()" />
			         	</td>
			        </tr>
			      </table>
			  	  <input type="hidden" name="medioId" value="<%=gd%>">
			 </FORM>
		</div>
	</div>
 </BODY>
 </HTML>