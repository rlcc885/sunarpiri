<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<title></title>
	<script language="javascript">
		function ShowReport(){
			document.form1.method="POST";
			document.form1.action="/iri/ConsolaCentral.do?state=busca";
			document.form1.submit();
			return true;
		}
		function sel_ActivarTodos(obj) {
			for (var i=0; i<obj.options.length; i++){
				obj.options[i].selected = true;
			}
		}		
	</script>
</head>

<body>
<form name="form1" method="POST" >
<br>

<table class=titulo>
	<tr> 
	    <td><font color="black">Consola Central &gt;&gt;</font></td>
	</tr>
</table>

<table class=formulario cellspacing=0 height="39" width="612">
   <tr> 
      <td align="right" width="168" height="24">Oficina Registral&nbsp;&nbsp;&nbsp; 
      </td>
      
      <td align="right" width="296" height="24"><p align="center">&nbsp;&nbsp;&nbsp;&nbsp; 
        <select size="7" name="oficinas" multiple>
	        <logic:iterate name="listaOficinas" id="item1">
	        	<option value="<bean:write name="item1" property="regPubId"/><bean:write name="item1" property="oficRegId"/>"><bean:write name="item1" property="nombre"/></option>
	        </logic:iterate>
        </select></p>
      </td>
      
      <td width="140" height="24">
        <input type="button" value="Seleccionar todas" name="selec" onclick="sel_ActivarTodos(form1.oficinas)">
        <p>
        <input type="button" value="VER" name="ver" onclick="ShowReport()"></p>
      </td>
   </tr>
   <tr> 
      <td align="right" width="168" height="11"> Estado :&nbsp; 
      </td>
      <td align="right" width="296" height="11"><p align="left">
        	<input type="checkbox" name="E1" <logic:present name="E1">checked</logic:present>>Nulo&nbsp; 
        	<input type="checkbox" name="E2" <logic:present name="E2">checked</logic:present>>PT&nbsp;
        	<input type="checkbox" name="E3" <logic:present name="E3">checked</logic:present>>TX&nbsp; 
        	<input type="checkbox" name="E4" <logic:present name="E4">checked</logic:present>>A1&nbsp;
        	<input type="checkbox" name="E5" <logic:present name="E5">checked</logic:present>>A2&nbsp;
      </td>
      <td width="140" height="11">
      </td>
   </tr>
   <tr> 
      <td align="right" width="168" height="11"> Error :&nbsp; 
      </td>
      <td align="right" width="296" height="11"> <p align="left">
      		<input type="radio" value="Error1" name="error" <logic:present name="Error1">checked</logic:present>>Todos
      		<input type="radio" value="Error2" name="error" <logic:present name="Error2">checked</logic:present>>Errores
			<input type="radio" value="Error3" name="error" <logic:present name="Error3">checked</logic:present>>Errores no manejados 
	  </td>
      <td width="140" height="11">
      </td>
    </tr>
<tr> 
      <td align="right" width="168" height="11"> Tipo Entidad 
      </td>
      <td align="right" width="296" height="11"> <p align="left">
      		<input type="checkbox" name="TE1" <logic:present name="TE1">checked</logic:present>>TITU&nbsp; 
      		<input type="checkbox" name="TE2" <logic:present name="TE2">checked</logic:present>>PACO
      		<input type="checkbox" name="TE3" <logic:present name="TE3">checked</logic:present>>PART
      		<input type="checkbox" name="TE4" <logic:present name="TE4">checked</logic:present>>ASIE
      </td>
      <td width="140" height="11">
      </td>
    </tr>
<tr> 
	  <td align="right" width="168" height="11"> TS_CREA &nbsp;
      </td>
      <td align="right" height="11"><p align="left"> &nbsp;&nbsp;&nbsp;&nbsp;Desde <select size="1" name="diainicio">
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
      </select>
      <select size="1" name="mesinicio">
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
      </select>
      <select size="1" name="anoinicio">
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2003">selected</logic:equal></logic:present>>2003</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2004">selected</logic:equal></logic:present>>2004</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2005">selected</logic:equal></logic:present>>2005</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2006">selected</logic:equal></logic:present>>2006</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2007">selected</logic:equal></logic:present>>2007</option>
      </select> &nbsp;&nbsp;&nbsp;&nbsp;
      Hasta 
      </td>
      <td>
      <select size="1" name="diafin">
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
      </select>
      <select size="1" name="mesfin">
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
      <select size="1" name="anofin">
        <option <logic:present name="af1"><logic:equal name="af1" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2003">selected</logic:equal></logic:present>>2003</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2004">selected</logic:equal></logic:present>>2004</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2005">selected</logic:equal></logic:present>>2005</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2006">selected</logic:equal></logic:present>>2006</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2007">selected</logic:equal></logic:present>>2007</option>
      </select>
    </td>
  </tr>
</table>
<BR><BR>
<logic:present name="servlet">
	<%
		pageContext.getOut().flush();
		application.getRequestDispatcher("ConsolaCentralServlet").include(request, response);
	%>
</logic:present>

<logic:present name="faltaOficina">
	<p>
	<font color="red" size="3"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DEBE SELECCIONAR POR LO MENOS UNA OFICINA </font>
</logic:present>
</body>
</html>