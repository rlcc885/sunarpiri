<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.caja.*" %>

<HTML>
<head>
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<BODY>
<script>
function ShowReport(pagina){ 
	document.frm1.method="POST";
	document.frm1.action="/iri/ExtornoPago.do?state=inicial&pagina=" + pagina;
	document.frm1.submit();
}
function BuscaNroAbono(){
	
	
	if (esVacio(document.frm1.txtNroAbono.value))
	{
		alert("Ingrese el numero de abono");
		document.frm1.txtNroAbono.focus();
		return
	}
	
	document.frm1.action="/iri/ExtornoPago.do?state=buscaNroAbono";
	document.frm1.submit();
	   
}
function BuscaFecha(){
		
	document.frm1.action="/iri/ExtornoPago.do?state=buscaFecha";
	document.frm1.submit();
	   
}
function Anular(str,str2,str3){
	document.frm1.hid1.value= str;
	document.frm1.hid2.value= str2;
	document.frm1.hid3.value= str3;
	document.frm1.action="/iri/ExtornoPago.do?state=solicitaExtornarAbono";
	document.frm1.submit();
}
</script>
  <br>
<table cellspacing=0 class=titulo>

  <tr>
	<td>
		<FONT COLOR="black">TESORERIA <font size="1">&gt;&gt;</font></FONT><font color="900000">Extornos de Pago</FONT>
	</td>
  </tr>
  
</table>
<br>
<form name="frm1" method="post">
<input type="hidden" name="hid1" value="">
<input type="hidden" name="hid2" value="">
<input type="hidden" name="hid3" value="">
<table class="formulario" width="602">
  <tr>
    <td nowrap></td>
    <td nowrap>N&uacute;mero Abono</td>
    <td nowrap valign="middle">D&iacute;a&nbsp;</td>
    <td nowrap valign="middle">Mes&nbsp;</td>
    <td nowrap valign="middle">A&ntilde;o&nbsp;</td>
    <td nowrap valign="middle"></td>
    <td></td>
    <td></td>
  </tr>
  <tr>


  	<td nowrap></td>
	
	<% if(request.getAttribute("numeroAbono")!=null) {%>
	  	<td nowrap>
	  		<table>
	  			<tr>
	  				<td style="border: 0px;" valign="middle">
					  	<INPUT type="text" name="txtNroAbono" value="<%=request.getAttribute("numeroAbono")%>" size="20">
	  				</td>
	  				<td  valign="middle">
					  	<A href="javascript:BuscaNroAbono();"><IMG src="images/btn_buscar2.gif" border="0"></A>
	  				</td>
	  			</tr>
	  		</table>
		</td>
  	<%} else {%>
	  	<td nowrap>
	  		<table>
	  			<tr>
	  				<td style="border: 0px;" valign="middle">
					  	<INPUT type="text" name="txtNroAbono" size="20">
	  				</td>
	  				<td style="border: 0px;" valign="middle">
					  	<IMG src="images/btn_buscar2.gif" border="0" onclick="BuscaNroAbono()">
	  				</td>
	  			</tr>
	  		</table>
	  	</td>
  	<% }%>


  	<td><SELECT size="1" name="dia">
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="01">selected</logic:equal></logic:present>="">01</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="02"></logic:equal></logic:present>="">02</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="03"></logic:equal></logic:present>="">03</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="04"></logic:equal></logic:present>="">04</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="05"></logic:equal></logic:present>="">05</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="06"></logic:equal></logic:present>="">06</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="07"></logic:equal></logic:present>="">07</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="08"></logic:equal></logic:present>="">08</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="09"></logic:equal></logic:present>="">09</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="10"></logic:equal></logic:present>="">10</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="11"></logic:equal></logic:present>="">11</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="12"></logic:equal></logic:present>="">12</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="13"></logic:equal></logic:present>="">13</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="14"></logic:equal></logic:present>="">14</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="15"></logic:equal></logic:present>="">15</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="16"></logic:equal></logic:present>="">16</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="17"></logic:equal></logic:present>="">17</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="18"></logic:equal></logic:present>="">18</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="19"></logic:equal></logic:present>="">19</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="20"></logic:equal></logic:present>="">20</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="21"></logic:equal></logic:present>="">21</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="22"></logic:equal></logic:present>="">22</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="23"></logic:equal></logic:present>="">23</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="24"></logic:equal></logic:present>="">24</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="25"></logic:equal></logic:present>="">25</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="26"></logic:equal></logic:present>="">26</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="27"></logic:equal></logic:present>="">27</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="28"></logic:equal></logic:present>="">28</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="29"></logic:equal></logic:present>="">29</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="30"></logic:equal></logic:present>="">30</OPTION>
			<OPTION
				<logic:present name="dia"><logic:equal name="dia" value="31"></logic:equal></logic:present>="">31</OPTION>
		</SELECT></td>
  	<td><SELECT size="1" name="mes">
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="01">selected</logic:equal></logic:present>="">01</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="02"></logic:equal></logic:present>="">02</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="03"></logic:equal></logic:present>="">03</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="04"></logic:equal></logic:present>="">04</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="05"></logic:equal></logic:present>="">05</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="06"></logic:equal></logic:present>="">06</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="07"></logic:equal></logic:present>="">07</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="08"></logic:equal></logic:present>="">08</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="09"></logic:equal></logic:present>="">09</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="10"></logic:equal></logic:present>="">10</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="11"></logic:equal></logic:present>="">11</OPTION>
			<OPTION
				<logic:present name="mes"><logic:equal name="mes" value="12"></logic:equal></logic:present>="">12</OPTION>
		</SELECT></td>
  	<td><SELECT size="1" name="ano">
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2003"></logic:equal></logic:present>="">2003</OPTION>
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2004"></logic:equal></logic:present>="">2004</OPTION>
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2005"></logic:equal></logic:present>="">2005</OPTION>
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2006"></logic:equal></logic:present>="">2006</OPTION>
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2007"></logic:equal></logic:present>="">2007</OPTION>
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2008"></logic:equal></logic:present>="">2008</OPTION>
			<OPTION
				<logic:present name="ano"><logic:equal name="ano" value="2009">selected</logic:equal></logic:present>="">2009</OPTION>
		</SELECT></td>
  	<td><A href="javascript:BuscaFecha();"><IMG
			src="images/btn_buscar2.gif" border="0"></A></td>
  </tr>
	<tr></tr>
</table>
</form>
<table class="grilla">
				<tr>
		      		<th align="center" height="19" width="15%"><div align="center">Sec</div></th>
		      		<th align="center" height="19" width="18%"><div align="center"> Nro. Abono </div></th>
		      		<th align="center" height="19" width="26%"><div align="center">Fecha/Hora</div></th>
		      		<th align="center" height="19" width="40%"><div align="center">Nombre/Raz&oacute;n Social</div></th>
		      		<th align="center" height="19" width="46%"><div align="center">Monto</div></th>
		      		<th align="center" height="19" width="46%"><div align="center"></div></th>
		      	</tr>
		      	<% 
				java.util.ArrayList lista = null;
				
				gob.pe.sunarp.extranet.prepago.bean.DiarioRecaudaDetalleBean detalle = null;
				if (request.getAttribute("listadoConsulta")!=null)
										lista = (java.util.ArrayList)request.getAttribute("listadoConsulta");
				int size = 0;
				if(lista!=null){
				size = lista.size();
					
					if(size>0){
						 for (int i=0; i<size; i++) {
						 	int a=i+1;
						 	detalle = (gob.pe.sunarp.extranet.prepago.bean.DiarioRecaudaDetalleBean)lista.get(i);
				%>
		      	<tr>
		      		<td><%=a%></td>
		      		<td width="101"><%=detalle.getIdAbono()%></td>
		      		<td width="145"><%=detalle.getHora()%> </td>
		      		<td><%=detalle.getNombre()%></td>
		      		<td><%=detalle.getMonto()%></td>
		      		<%if(detalle.getEsAnulado().equals("0")){ %>
		      		<td><a><IMG src="images/btn_anulada.gif"></a></td>
					<%} else { %>
					<td><A href="javascript:Anular('<%=detalle.getIdAbono()%>','<%=detalle.getTipoAbono()%>','<%=detalle.getMonto()%>');"><IMG src="images/btn_anular.gif"
					border="0"></A></td>
					<% } %>
		      	</tr>
		      	<% }
		      	}
		      	}
		      	%>
</table>
<!-- ************************ P A G I N A C I O N ******************************* -->
<center>
<table  class=tablasinestilo>
<tr>
<td>
<logic:present name="previous">
	<a href="javascript:ShowReport(<bean:write name="previous"/>);">Anterior</a>
</logic:present>
</td>
<td>
<logic:present name="next">
	<a href="javascript:ShowReport(<bean:write name="next"/>);">Siguiente</a>
</logic:present>
</td>
</tr>
</table>
</center>
<!-- ************************ P A G I N A C I O N ******************************* -->
<center>

</center>		      	
</BODY>
</HTML>
