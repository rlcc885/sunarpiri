<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>


<link rel="stylesheet" href="styles/global.css">

<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>

<script language="javascript">
function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}
function Valida()
{ 
	if (document.form1.pwTesorero.value.length==0)
	{
		alert("Ingrese su contraseña");
		document.form1.pwTesorero.focus();
		return;
	}
	if (document.form1.glosa.value.length==0)
	{
		alert("Ingrese una glosa");
		document.form1.glosa.focus();
		return;
	}					
	document.form1.method="POST";
	document.form1.action="/iri/DiarioRecaudaEnLinea.do?state=completaPagoEnLinea";
	document.form1.submit();
}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>




<body>
<form name="form1">
<font face="Verdana">
<br>
</font>
<input type="hidden" name="nroPago" value="<bean:write name="nroPago"/>">
<input type="hidden" name="monto" value="<bean:write name="monto"/>">
<logic:present name="solicitudId">
<input type="hidden" name="solicitudId" value="<bean:write name="solicitudId"/>">
</logic:present>
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
			<font color=black>TESORERIA &gt;&gt; </font> Diario de Recaudación En Línea &gt;&gt; Completar Pago
		</td>
	</tr>
</table>
<br>


<table class=formulario width="600">
  <tr>
    <td width="35">
      <p align="right">
        <font face="Verdana">&nbsp;&nbsp;</font>
      </p>
    </td>
    <td width="219">
    
    </td>
    <td width="112">
    
    </td>
    <td width="100">
      <p align="right">
        <font face="Verdana">&nbsp;</font>
      </p>
    </td>
  </tr>
  <tr>
    <td width="35">
      <font face="Verdana">&nbsp;</font>
    </td>
    <td width="219">
    </td>
    <td width="112">
      <p align="right">
        <font face="Verdana">
          <input type="hidden" size="5" name="monto" value="0">
        </font>
      </p>
    </td>
  </tr>
  <tr>
    <td width="35">
    </td>
    <td width="50">
      <b>
        <font size="2" face="Verdana">Pago</font>
      </b>
    </td>
    <td width="50">
      : <bean:write name="nroPago"/>
    </td>
    
    <td>
      <a href="javascript:window.print();">
        <img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25">
      </a>
    </td>
    
    
  </tr>  
<logic:present name="solicitudId">
<logic:notEqual name="solicitudId" value="">
  <tr>
    <td width="35">
    </td>
    <td width="50">
      <b>
        <font size="2" face="Verdana">Solicitud</font>
      </b>
    </td>
    <td width="50">
      : <%=request.getAttribute("solicitudId")%>
    </td>
    <td>
      &nbsp;
    </td>
  </tr>
</logic:notEqual>
</logic:present>

  <tr>
    <td width="35">
    </td>
    <td width="50">
      <b>
        <font size="2" face="Verdana">Monto</font>
      </b>
    </td>
    <td width="50">
      : <%=request.getAttribute("monto_formateado")%>
    </td>
    <td>
      <a href="javascript:history.back();">
        <img src="<%=request.getContextPath()%>/images/btn_cancelar.gif" width="83" height="24">
      </a>
    </td>
  </tr> 
  
  <tr>
    <td width="35">
    </td>
    <td colspan="2">
      <b>
        <font size="2" face="Verdana">Ingrese la fecha y hora de la transacci&oacute;n:</font>
      </b>
    </td>
    <td>
      &nbsp;
    </td>
  </tr> 
  <tr>
    <td width="35">
    </td>
    <td width="219">
    &nbsp;
    </td>
    <td width="112">
    </td>
    <td width="100">
    </td>
  </tr>
  <tr>
    <td width="35">
    </td>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="12%">
          </td>
          <td width="22%">D&iacute;a:</td>
          <td width="22%">Mes</td>
          <td width="22%">A&ntilde;o:</td>
          <td width="22%">&nbsp;</td>
        </tr>
        <tr>
          <td>
          </td>
          <td>
            <select size="1" name="day">
              <option value="01">01</option>
              <option value="02">02</option>
              <option value="03">03</option>
              <option value="04">04</option>
              <option value="05">05</option>
              <option value="06">06</option>
              <option value="07">07</option>
              <option value="08">08</option>
              <option value="09">09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
              <option value="17">17</option>
              <option value="18">18</option>
              <option value="19">19</option>
              <option value="20">20</option>
              <option value="21">21</option>
              <option value="22">22</option>
              <option value="23">23</option>
              <option value="24">24</option>
              <option value="25">25</option>
              <option value="26">26</option>
              <option value="27">27</option>
              <option value="28">28</option>
              <option value="29">29</option>
              <option value="30">30</option>
              <option value="31">31</option>
            </select>
          </td>
          <td>
            <select name="month" size="1">
              <option value="01">01</option>
              <option value="02">02</option>
              <option value="03">03</option>
              <option value="04">04</option>
              <option value="05">05</option>
              <option value="06">06</option>
              <option value="07">07</option>
              <option value="08">08</option>
              <option value="09">09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
            </select>
          </td>
          <td>
            <select name="year" size="1">
              <option value="2000">2000</option>
              <option value="2001">2001</option>
              <option value="2002">2002</option>
              <option value="2003">2003</option>
              <option value="2004">2004</option>
              <option value="2005">2005</option>
              <option value="2006">2006</option>
              <option value="2007">2007</option>
              <option value="2008">2008</option>
              <option value="2009">2009</option>
              <option value="2010">2010</option>
              <option value="2011">2011</option>
            </select>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
          </td>
          <td>Hora:</td>
          <td>Minutos:</td>
          <td>Segundos:</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
          </td>
          <td>
            <select size="1" name="hora">
              <option value="01">01</option>
              <option value="02">02</option>
              <option value="03">03</option>
              <option value="04">04</option>
              <option value="05">05</option>
              <option value="06">06</option>
              <option value="07">07</option>
              <option value="08">08</option>
              <option value="09" selected>09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
            </select>
          </td>
          <td>
            <select size="1" name="minutos">
              <option value="00" selected>00</option>
              <option value="01">01</option>
              <option value="02">02</option>
              <option value="03">03</option>
              <option value="04">04</option>
              <option value="05">05</option>
              <option value="06">06</option>
              <option value="07">07</option>
              <option value="08">08</option>
              <option value="09">09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
              <option value="17">17</option>
              <option value="18">18</option>
              <option value="19">19</option>
              <option value="20">20</option>
              <option value="21">21</option>
              <option value="22">22</option>
              <option value="23">23</option>
              <option value="24">24</option>
              <option value="25">25</option>
              <option value="26">26</option>
              <option value="27">27</option>
              <option value="28">28</option>
              <option value="29">29</option>
              <option value="30">30</option>
              <option value="31">31</option>
              <option value="32">32</option>
              <option value="33">33</option>
              <option value="34">34</option>
              <option value="35">35</option>
              <option value="36">36</option>
              <option value="37">37</option>
              <option value="38">38</option>
              <option value="39">39</option>
              <option value="40">40</option>
              <option value="41">41</option>
              <option value="42">42</option>
              <option value="43">43</option>
              <option value="44">44</option>
              <option value="45">45</option>
              <option value="46">46</option>
              <option value="47">47</option>
              <option value="48">48</option>
              <option value="49">49</option>
              <option value="50">50</option>
              <option value="51">51</option>
              <option value="52">52</option>
              <option value="53">53</option>
              <option value="54">54</option>
              <option value="55">55</option>
              <option value="56">56</option>
              <option value="57">57</option>
              <option value="58">58</option>
              <option value="59">59</option>
            </select>
          </td>
          <td>
            <select size="1" name="segundos">
              <option value="00" selected>00</option>
              <option value="01">01</option>
              <option value="02">02</option>
              <option value="03">03</option>
              <option value="04">04</option>
              <option value="05">05</option>
              <option value="06">06</option>
              <option value="07">07</option>
              <option value="08">08</option>
              <option value="09">09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
              <option value="17">17</option>
              <option value="18">18</option>
              <option value="19">19</option>
              <option value="20">20</option>
              <option value="21">21</option>
              <option value="22">22</option>
              <option value="23">23</option>
              <option value="24">24</option>
              <option value="25">25</option>
              <option value="26">26</option>
              <option value="27">27</option>
              <option value="28">28</option>
              <option value="29">29</option>
              <option value="30">30</option>
              <option value="31">31</option>
              <option value="32">32</option>
              <option value="33">33</option>
              <option value="34">34</option>
              <option value="35">35</option>
              <option value="36">36</option>
              <option value="37">37</option>
              <option value="38">38</option>
              <option value="39">39</option>
              <option value="40">40</option>
              <option value="41">41</option>
              <option value="42">42</option>
              <option value="43">43</option>
              <option value="44">44</option>
              <option value="45">45</option>
              <option value="46">46</option>
              <option value="47">47</option>
              <option value="48">48</option>
              <option value="49">49</option>
              <option value="50">50</option>
              <option value="51">51</option>
              <option value="52">52</option>
              <option value="53">53</option>
              <option value="54">54</option>
              <option value="55">55</option>
              <option value="56">56</option>
              <option value="57">57</option>
              <option value="58">58</option>
              <option value="59">59</option>
            </select>
          </td>
          <td>
            <select size="1" name="mm">
              <option value="AM">AM</option>
              <option value="PM">PM</option>
            </select>
          </td>
        </tr>
      </table>
    </td>
    <td>
    </td>
    <td width="100">
    </td>
  <tr>
  <tr>
    <td width="35">
    </td>
    <td width="219">
      &nbsp;
    </td>
    <td width="112">
    </td>
    <td width="100">
    </td>
  </tr>
  
  <tr>
    <td width="35">
    </td>
    <td width="219">
      <b>
        <font size="2" face="Verdana">GLOSA</font>
      </b>
    </td>
    <td width="112">
    </td>
    <td width="100">
    </td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="439" colspan="3">
      <p align="left">
        <font face="Verdana">
          <textarea rows="5" name="glosa" cols="50"></textarea>
        </font>
      </p>
    </td>
  </tr>
  <tr>
    <td width="35">
    </td>
    <td width="219">
      <b>
        <font size="2" face="Verdana">
          INGRESAR CONTRASE&Ntilde;A<BR>
        </font>
      </b>
    </td>
    <td width="112">
      <p align="right">
        <font face="Verdana">
          <input type="password" size="10" name="pwTesorero" onblur="sololet(this)">
        </font>
      </p>
    </td>
    <td width="100">
      <a href="javascript:Valida();">
        <img src="<%=request.getContextPath()%>/images/btn_aplicar.gif" width="84" height="25">
      </a>
    </td>
  </tr>
  <tr>
    <td width="35">
    </td>
    <td width="219">
    </td>
    <td width="112">
    </td>
    <td width="100">
    </td>
  </tr>
</table>
</form>
<script LANGUAGE="JavaScript">

	doCambiaCombo(document.form1.day, "<%=request.getAttribute("day")%>");
	doCambiaCombo(document.form1.month, "<%=request.getAttribute("month")%>");
	doCambiaCombo(document.form1.year, "<%=request.getAttribute("year")%>");
</script>

</body>
</html>