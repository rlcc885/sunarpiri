<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.acceso.bean.*" %>
<%
  
  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  long perfilusuarioid =usuarioBean.getPerfilId();
  boolean certi=true;
  if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
  			 || usuarioBean.getExonPago()))
  {
  	certi=false;
  }

%>

<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE></TITLE>
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">
function Abrir_Ventana(dirURL,titulo,propiedad,ancho,alto)
	{
		if(screen.width){
			var venleft = (screen.availWidth-ancho)/2;
			var ventop = (screen.availHeight-alto)/2;
		}else{
			venleft = 0;
			ventop =0;
		}
		if (venleft < 0) venleft = 0;
		if (ventop < 0) ventop = 0;
		var titulo=titulo;
		var propiedad=propiedad;
		var propiedades="toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,";
		if(propiedad==""){
			propiedades= propiedades + "top=" + ventop + ",left=" + venleft + ",width=" + ancho + ",height=" +alto;
		}else{
			propiedades= propiedad + ",top=" + ventop + ",left=" + venleft + ",width=" + ancho + ",height=" +alto;
		}
		window.open(dirURL,titulo,propiedades);
	}
function Regresa()
{ 
	/*document.form1.method = "POST";
	document.form1.action = "/iri/BusquedaTituloVehicular.do";
	document.form1.submit();
	*/
	history.back();
}
function VerPartida(refnum_Part)
{
	ventana=window.open('/iri/Publicidad.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0')
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";	
}

function Cambio()
{
 	if(chkGravamenes.checked == true)
 	{
		//narea1.visibility="visible";
		area1.style.visibility="visible";
		area1.style.display="";
	}	
	else
	{
		//narea1.visibility="hidden";
		area1.style.visibility="hidden";
		area1.style.display="none";

	}
			
}

</script>
</head>

<body background="<%=request.getContextPath()%>/images/trama.gif">
	<br>
	<table class=titulo>
	  <tr> 
	    <td><font color="black">SERVICIOS &gt;&gt;B&uacute;squeda Directa de Partidas&gt;&gt; </font>Detalle</td>
	  </tr>
	</table>
	<br>
	<br>
	<!--<table class="grilla" border=0>-->
	<table border=0 width=600>
	  <tr> 
	    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<%=request.getAttribute("tarifa")%></td>
	    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=request.getAttribute("usuaEtiq")%></td>
	    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><%=request.getAttribute("fechaAct")%></td>
	    <td vAlign=top align=left width = 55%><font color="black">Ahora tenemos un nuevo medio de publicidad registral, de libre acceso para todos: www.sunarp.gob.pe.</font></td>
	  </tr>
	</table>	
	<br>	
<!--<DIV id=tramado style="Z-INDEX: 4; LEFT: 0; POSITION: absolute; TOP: 50" name="tramado">
<DD><IMG src="/iri/images/trama.gif" width=580 border=1> </DD></DIV>-->

<DIV align=left>
<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <Th align=left width=100% bgColor=#800000 height=18>
      A.- DATOS GENERALES DEL VEHICULO - PLACA No. <%=request.getAttribute("placa")%>
    </Th>
    
  </TR>
  <!--<TR>
    <Td align=left width=80% height=18 bgColor=#f6f6f6 borderColor=#000000 borderColorLight=#000000 bordercolordark=#000000>
      &nbsp;
    </Td>
    
  </TR>-->
  </TBODY>
</TABLE>
<TABLE class="grilla">
  <TBODY>
  
  
  <tr> 
    <td width="50%"> 
      <table width="100%" border="1" cellspacing="0">
        <tr>
          		<td height="90" colspan="2"> 
          			<div align="center">
          	  <img src="<%=request.getContextPath()%>/images/orlclogo.gif">
          	  <font size="1" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<br>
          	  			</font>
          			</div>
          		</td>
        </tr>
        <tr> 
          <td height="30" colspan="2" align='center'>
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">
            	<bean:write name="bDatos" property="nomOfi"/>
            </font>
          </td>
        </tr>
        <tr> 
          <td height="30">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">PLACA:</font>
          </td>
          <td>
          	<strong>
            <font size="2" face="Geneva, Arial, Helvetica, sans-serif"><b>&nbsp;<%=request.getAttribute("placa")%></b></font>
			</strong>	
          </td>
        </tr>
        <tr> 
          <td height="30">
   			<font size="1" face="Geneva, Arial, Helvetica, sans-serif">ESTADO:</font>
          </td>
          <td>
          	<strong>
            <font size="2" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<bean:write name="bDatos" property="estado"/></font>
			</strong>
          </td>
        </tr>
        <tr> 
          <td height="20">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">FECHA DE INSCRIPCION:</font>
          </td>
          <td>
            <strong>
              <font size="2" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<bean:write name="bDatos" property="fecIns"/></font>
            </strong>
          </td>
        </tr>
        <tr> 
          <td height="20">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">FECHA DE PROPIEDAD:</font>
          </td>
          <td>
            <strong>
              <font size="2" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<%=request.getAttribute("fec_prop")%></font>
            </strong>
          </td>
        </tr>
        <logic:notEqual name="bDatos" property="cetico" value = "">
        <tr>
          <td height="20">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">CETICO:</font>
          </td>
          <td>
            <strong>
              <font size="2" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<bean:write name="bDatos" property="cetico"/></font>
            </strong>
          </td>
        </tr>
        </logic:notEqual>
        <tr> 
          <td height="20">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">CONDICION:</font>
          </td>
          <td>
            <strong>
              <font size="2" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<bean:write name="bDatos" property="condic"/></font>
            </strong>
          </td>
        </tr>
        <tr> 
          <td height="20">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">PARTIDA REGISTRAL:</font>
          </td>
          <td>
            <strong>
              <font size="2" face="Geneva, Arial, Helvetica, sans-serif">&nbsp;<%=request.getAttribute("partida")%></font>
            </strong>
          </td>
        </tr>
        <tr> 
          <td height="30">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">VISUALIZAR ASIENTOS:</font>
          </td>
          <td align="center" height="30">
            <input name="image2" type="image" onclick="VerPartida('<%=request.getAttribute("refnum_part")%>')" value="Visualizar" src="images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
          </td>
        </tr>
        <%if(certi){%>
        <tr> 
          <td height="30">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">SOLICITAR CERTIFICADO:</font>
          </td>
          <td align="center" height="30">
            <a href="/iri/Certificados.do?state=guardarDatosBasicos&refnum_part=<%=request.getAttribute("refnum_part")%>&noPartida=<%=request.getAttribute("partida")%>&hidOfic=<bean:write name="bDatos" property="nomOfi"/>&area=24000&hidTipo=L">
              <image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
            </a>
          </td>
        </tr>
        <%}%>
      </table>
    </td>
    <td width="50%">
	  <table width="100%" border="1" cellspacing="0">
        <tr> 
          <td height="40" colspan="2"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">CLASE<br>
            &nbsp;<font size="2"><strong>&nbsp;<bean:write name="bDatos" property="clase"/></strong></font></font></td>
          <td  colspan="2"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">MARCA<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="marca"/></font></strong></font></td>
          <td><font size="1" face="Geneva, Arial, Helvetica, sans-serif">A&Ntilde;O 
              FAB<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="yearFa"/></font></strong></font></td>
        </tr>
        <tr> 
          <td height="40" colspan="3"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">MODELO<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="modelo"/></font></strong></font></td>
          <td width="25%" colspan="2"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">COMBUSTIBLE<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="combus"/></font></strong></font></td>
        </tr>
        <tr> 
          <td height="40" colspan="4"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">CARROCERIA<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="carroc"/></font></strong></font></td>
          <td width="15%"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">EJES<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="noEjes"/></font></strong></font></td>
        </tr>
        <tr> 
          <td height="40" colspan="5"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">COLORES<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="color"/></font></strong></font></td>
        </tr>
        <tr> 
          <td height="40" colspan="4"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">N 
            MOTOR<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="noMoto"/></font></strong></font></td>
          <td><font size="1" face="Geneva, Arial, Helvetica, sans-serif">CILINDROS<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="cilind"/></font></strong></font></td>
        </tr>
        <tr> 
          <td height="40" colspan="4">
            <font size="1" face="Geneva, Arial, Helvetica, sans-serif">N SERIE<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="noSeri"/></font></strong></font></td>
          <td><font size="1" face="Geneva, Arial, Helvetica, sans-serif">RUEDAS<br>
            &nbsp;<font size="2"><strong>&nbsp;<bean:write name="bDatos" property="noRued"/></strong></font></font></td>
        </tr>
        <tr> 
          <td width="20%" height="40"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">PASAJEROS<br>
            &nbsp;<font size="2"><strong>&nbsp;<bean:write name="bDatos" property="pasaje"/></strong></font></font></td>
          <td width="17%"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">ASIENTOS<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="asient"/></font></strong></font></td>
          <td width="35%"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">PESO 
              SECO<br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="psSeco"/></font></strong></font></td>
          <td colspan="2"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">PESO 
              BRUTO</font><br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="psBrut"/></font></strong></td>
        </tr>
        <tr> 
          <td height="40"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">LONGITUD</font><br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="longit"/></font></strong></td>
          <td height="40"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">ALTURA</font><br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="altura"/></font></strong></td>
          <td height="35"> <font size="1" face="Geneva, Arial, Helvetica, sans-serif">ANCHO</font><br>
            &nbsp;<strong><font size="2">&nbsp;<bean:write name="bDatos" property="ancho"/></font></strong></td>
          <td height="40" colspan="2"><font size="1" face="Geneva, Arial, Helvetica, sans-serif">CARGA 
            UTIL</font><br>
            &nbsp;<strong><font size="2"></font></strong></td>
        </tr>
        
      </table>
      </td>
    </tr>
  
  <TR>
    <TD align=left width=780 colSpan=4>
    </TD>
  </TR>
 <logic:present name="nuevaPlaca">
  <TR>
    <TD align=left width=780 colSpan=4>
      La Placa mostrada ya no se encuentra en circulaci&oacute;n.<br>La nueva Placa es: <%=request.getAttribute("nuevaPlaca")%>&nbsp;
    </TD>
  </TR>
  <TR>
    <TD align=left width=780 colSpan=4>
    </TD>
  </TR>
 </logic:present>
  
  </TBODY>
</TABLE>
<DIV align=left>

<logic:notPresent name="nuevaPlaca">
<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <TD width=780></TD></TR>
  <TR>
    <TD width=780></TD></TR>
  <TR>
    <TH align=left width=38% bgColor=#800000>B.- PROPIETARIO (S) </TH>
    <TH align="center" width=38% bgColor=#800000>DIRECCION </TH>
    <TH align="center" width=14% bgColor=#800000>TITULO </TH>
    <!--<TH align="center" width=10% bgColor=#800000>UBIGEO </TH>-->
  </TR>
  </TBODY>
</TABLE>

<TABLE class="grilla" border=0>
  <TBODY>
   <logic:notPresent name="lPropi">
      <TR>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=38% bgColor=#f6f6f6 borderColorDark=#000000>
          PROPIETARIOS NO DISPONIBLES.
        </TD>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=38% bgColor=#f6f6f6 borderColorDark=#000000>
          &nbsp;
        </TD>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=14% bgColor=#f6f6f6 borderColorDark=#000000>
          &nbsp;
        </TD>
        <!--<TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=10% bgColor=#f6f6f6 borderColorDark=#000000>
          &nbsp;
        </TD>-->
        
      </TR>
   </logic:notPresent> 
   <logic:present name="lPropi"> 
    <logic:iterate name="lPropi" id="item2">
      <TR>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=38% bgColor=#f6f6f6 borderColorDark=#000000>
          <bean:write name="item2" property="propietario"/>
        </TD>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=38% bgColor=#f6f6f6 borderColorDark=#000000>
          <bean:write name="item2" property="direccion"/> &nbsp;  &nbsp; <bean:write name="item2" property="ubigeo"/>
        </TD>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=14% bgColor=#f6f6f6 borderColorDark=#000000>
          <bean:write name="item2" property="expediente"/>
        </TD>
        <!--<TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=10% bgColor=#f6f6f6 borderColorDark=#000000>-->
          <%--<bean:write name="item2" property="ubigeo"/>--%>
        <!--</TD>-->
        
      </TR>
    </logic:iterate>
   </logic:present> 
  </TBODY>
</TABLE>

<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <TD width=780 height=18></TD></TR>
  <TR>
    <Th align=left width=60% bgColor=#800000 height=18>
      C.- RELACION DE GRAVAMENES VIGENTES
    </Th>
    <Th align=left width=40% bgColor=#800000 height=18><input type="checkbox" name="chkGravamenes"  onClick= "javascript:Cambio();">Ver Gravámenes Levantados
    </Th>    
  </TR>
  </TBODY>
</TABLE>
<logic:notPresent name="lGrava">
<TABLE class="grilla" border=0>
  <TBODY>
    
      <TR>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=690 bgColor=#f6f6f6 borderColorDark=#000000>
          <logic:equal name="noGrav" value="0">     
          	NO SE ENCONTRARON GRAVAMENES VIGENTES.
         </logic:equal>          
        </TD>
      </TR>
    
  </TBODY>
</TABLE>
</logic:notPresent>
<%
	int nGrav = 0;
%>
<logic:present name="lGrava">
<TABLE class="grilla" border=0>
  <TBODY>  
  <logic:iterate name="lGrava" id="item3">
  <% nGrav++;%>
  <TR>
  <Th vAlign=top align=left bgColor=#800000>GRAVAMEN <%=nGrav%></Th>
  </TR>
  <TR>
    <Th vAlign=top align=left bgColor=#800000>Estado</Th>
    <Th vAlign=top align=left bgColor=#800000>Afectaci&oacute;n</Th>
    <Th vAlign=top align=left bgColor=#800000>Fecha<BR>Afectaci&oacute;n</Th>
    <Th vAlign=top align=left bgColor=#800000>No.<BR>Doc.</Th>
    <Th vAlign=top align=left bgColor=#800000>Título</Th>
    <Th vAlign=top align=left bgColor=#800000>Juzgado</Th>
    <Th vAlign=top align=left bgColor=#800000>Causa<BR>Afectaci&oacute;n</Th>
  </TR>
  <TR>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="estado"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="afecta"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="fecAfe"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="noDocu"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="titulo"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="juzgad"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="cauAfe"/>&nbsp;</TD>
  </TR>
  <TR>
    <Th vAlign=top align=left bgColor=#800000>Juez</Th>
    <Th vAlign=top align=left bgColor=#800000>Secretario</Th>
    <Th vAlign=top align=left bgColor=#800000>Modificaci&oacute;n</Th>
    <Th vAlign=top align=left bgColor=#800000>Juez<BR>Descargo</Th>
    <Th vAlign=top align=left bgColor=#800000>Secretario<BR>Descargo</Th>
    <Th vAlign=top align=left bgColor=#800000>Fecha<BR>Descargo</Th>
    <Th vAlign=top align=left bgColor=#800000>No. Exp.<BR>Descargo</Th>
  </TR>    
  <TR>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="juez"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="secre"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="modifi"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="juezDs"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="secrDs"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="fecDes"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item3" property="noXpDs"/></TD>
  </TR>
  <TR>
    <Th colspan="5" vAlign=top align=left bgColor=#800000>Participantes del Gravamen</Th>
    <Th colspan="2" vAlign=top align=center bgColor=#800000>Tipo de Participación</Th>
  </TR>  
	  <logic:iterate  name="item3" property="listbeanPartGrava" id="itemX">  
		  <TR>
		    <TD colspan="5" vAlign=top align=left bgColor=#f6f6f6><bean:write name="itemX" property="nombres"/></TD>
		    <TD colspan="2" vAlign=top align=center bgColor=#f6f6f6><bean:write name="itemX" property="descripcion"/></TD>
		  </TR>  
	  </logic:iterate>  
  </logic:iterate>
    
  </TBODY>
</TABLE>
</logic:present>



<div id="area1" style="visibility:hidden;display:none">

<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <TD width=780 height=18></TD></TR>
  <TR>
    <Th align=left width=780 bgColor=#800000 height=18>
      &nbsp;&nbsp;&nbsp;&nbsp;RELACION DE GRAVAMENES LEVANTADOS
    </Th>
  </TR>
  </TBODY>
</TABLE>

<logic:notPresent name="lGrava2">
<TABLE class="grilla" border=0>
  <TBODY>
    
      <TR>
        <TD vAlign=top borderColor=#000000 borderColorLight=#000000 width=690 bgColor=#f6f6f6 borderColorDark=#000000>
          <logic:equal name="noGrav2" value="0">     
          	NO SE ENCONTRARON GRAVAMENES LEVANTADOS.
         </logic:equal>          
        </TD>
      </TR>
    
  </TBODY>
</TABLE>
</logic:notPresent>

<%
	int nGrav2 = 0;
%>
<logic:present name="lGrava2">
<TABLE class="grilla" border=0>
  <TBODY>  
  <logic:iterate name="lGrava2" id="item13">
  <% nGrav2++;%>
  <TR>
  <Th vAlign=top align=left bgColor=#800000>GRAVAMEN <%=nGrav2%></Th>
  </TR>
  <TR>
    <Th vAlign=top align=left bgColor=#800000>Estado</Th>
    <Th vAlign=top align=left bgColor=#800000>Afectaci&oacute;n</Th>
    <Th vAlign=top align=left bgColor=#800000>Fecha<BR>Afectaci&oacute;n</Th>
    <Th vAlign=top align=left bgColor=#800000>No.<BR>Doc.</Th>
    <Th vAlign=top align=left bgColor=#800000>Título</Th>
    <Th vAlign=top align=left bgColor=#800000>Juzgado</Th>
    <Th vAlign=top align=left bgColor=#800000>Causa<BR>Afectaci&oacute;n</Th>
  </TR>
  <TR>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="estado"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="afecta"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="fecAfe"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="noDocu"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="titulo"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="juzgad"/>&nbsp;</TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="cauAfe"/>&nbsp;</TD>
  </TR>
  <TR>
    <Th vAlign=top align=left bgColor=#800000>Juez</Th>
    <Th vAlign=top align=left bgColor=#800000>Secretario</Th>
    <Th vAlign=top align=left bgColor=#800000>Modificaci&oacute;n</Th>
    <Th vAlign=top align=left bgColor=#800000>Juez<BR>Descargo</Th>
    <Th vAlign=top align=left bgColor=#800000>Secretario<BR>Descargo</Th>
    <Th vAlign=top align=left bgColor=#800000>Fecha<BR>Descargo</Th>
    <Th vAlign=top align=left bgColor=#800000>No. Exp.<BR>Descargo</Th>
  </TR>    
  <TR>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="juez"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="secre"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="modifi"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="juezDs"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="secrDs"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="fecDes"/></TD>
    <TD vAlign=top align=left bgColor=#f6f6f6><bean:write name="item13" property="noXpDs"/></TD>
  </TR>
  <TR>
    <Th colspan="5" vAlign=top align=left bgColor=#800000>Participantes del Gravamen</Th>
    <Th colspan="2" vAlign=top align=center bgColor=#800000>Tipo de Participación</Th>
  </TR>  
	  <logic:iterate  name="item13" property="listbeanPartGrava" id="itemX13">  
		  <TR>
		    <TD colspan="5" vAlign=top align=left bgColor=#f6f6f6><bean:write name="itemX13" property="nombres"/></TD>
		    <TD colspan="2" vAlign=top align=center bgColor=#f6f6f6><bean:write name="itemX13" property="descripcion"/></TD>
		  </TR>  
	  </logic:iterate>  
  </logic:iterate>
    
  </TBODY>
</TABLE>
</logic:present>

</div>


<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <TD width=780 height=15></TD></TR>
  <TR>
    <Th align=left width=80% bgColor=#800000 height=18>
      D.- TITULOS PENDIENTES
    </Th>
    <Th align=center width=20% bgColor=#800000 height=18>
      Ver Detalle
    </Th>
  </TR>
  </TBODY>
</TABLE>

<TABLE class="grilla" border=0>
  <TBODY>
    
    <logic:notPresent name="titupend">
      <TR>
        <TD vAlign=center borderColor=#000000 borderColorLight=#000000 width=80% bgColor=#f6f6f6 borderColorDark=#000000>
     <logic:notPresent name="mensajetitupend">
          NO SE ENCONTRARON TITULOS PENDIENTES.
     </logic:notPresent>
     <logic:present name="mensajetitupend">
          SE ENCONTRARON TITULOS PENDIENTES; PERO NO ESTAN DISPONIBLES.<br>SIRVASE ACERCARSE A LA OFICINA REGISTRAL MAS CERCANA.
     </logic:present>
        </TD>
        <TD align=center vAlign=center borderColor=#000000 borderColorLight=#000000 width=20% bgColor=#f6f6f6 borderColorDark=#000000>
          &nbsp;
        </TD>
      </TR>
    </logic:notPresent>
    <logic:present name="titupend">
      <logic:iterate name="titupend" id="item4">
      <TR>
        <TD vAlign=center borderColor=#000000 borderColorLight=#000000 width=80% bgColor=#f6f6f6 borderColorDark=#000000>
          N&uacute;mero de T&iacute;tulo: <bean:write name="item4" property="numTitulo"/> &nbsp;-&nbsp; <bean:write name="item4" property="ano"/>
        </TD>
        <TD align=center vAlign=center borderColor=#000000 borderColorLight=#000000 width=20% bgColor=#f6f6f6 borderColorDark=#000000>
          <a href="/iri/BusquedaTitulo.do?state=buscarXNroTituloDet&ano=<bean:write name="item4" property="ano"/>&numtitu=<bean:write name="item4" property="numTitulo"/>&oficinas=<bean:write name="item4" property="regPubId"/>%7C<bean:write name="item4" property="oficRegId"/>&txt_oficinas=Lima&areareg=<bean:write name="item4" property="areaRegistralId"/>">
            <image src="/iri/images/lupa.gif" onmouseover="javascript:mensaje_status('Visualizar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
          </a>
        </TD>
      </TR>
      </logic:iterate>
    </logic:present>
  </TBODY>
</TABLE>
<br>

<%if ((perfilusuarioid!=Constantes.PERFIL_INDIVIDUAL_EXTERNO) && (perfilusuarioid!=Constantes.PERFIL_AFILIADO_EXTERNO) && (perfilusuarioid!=Constantes.PERFIL_ADMIN_ORG_EXT))
{%>
<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <Th align=left width=80% bgColor=#800000 height=18>
      E.- MOTORES Y NUMEROS DE SERIE ANTERIORES
    </Th>
  </TR>
  </TBODY>
</TABLE>
  
<logic:present name="bmotor">    
<TABLE class="grilla" border=0>
  <TBODY>
  <tr> 
    <th vAlign=top align=left bgColor=#800000 width="25%">
      <b>PLACA</b>
    </th>
    <th vAlign=top align=left bgColor=#800000 width="25%">
      <b>SECUENCIAL</b>
    </th>
    <th vAlign=top align=left bgColor=#800000 width="25%">
      <b>SERIE</b>
    </th>
    <th vAlign=top align=left bgColor=#800000 width="25%">
      <b>MOTOR</b>
    </th>
  </tr>
    <logic:iterate name="bmotor" id="item1">
  <tr> 
    <td>&nbsp;<bean:write name="item1" property="placa"/></td>
    <td>&nbsp;<bean:write name="item1" property="secuencial"/></td>
    <td>&nbsp;<bean:write name="item1" property="serie"/></td>
    <td>&nbsp;<bean:write name="item1" property="motor"/></td>
  </tr>
    </logic:iterate>
  </TBODY>
</TABLE>    
</logic:present>
  <logic:notPresent name="bmotor">
<TABLE class="grilla" border=0>
  <TBODY>  
  <tr> 
    <td colspan="4">&nbsp;NO SE ENCONTRARON DATOS HISTORICOS.</td>   
  </tr>
  </TBODY>
</TABLE>      
  </logic:notPresent>  
<br>
<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <Th align=left width=80% bgColor=#800000 height=18>
      F.- PROPIETARIOS ANTERIORES
    </Th>
  </TR>
 </TBODY>
</TABLE>

  <logic:notPresent name="bpropi">
<TABLE class="grilla" border=0>
  <TBODY>
  
  <tr> 
    <td colspan="4">&nbsp;NO SE ENCONTRARON PROPIETARIOS HISTORICOS.</td>    
  </tr>
 </TBODY>
</TABLE>    
  </logic:notPresent>
  
  <logic:present name="bpropi">
<TABLE class="grilla" border=0>
  <TBODY>
  <tr> 
    <th width="25%">
      <b>NOMBRE DEL PROPIETARIO</b>
    </th>
    <th width="25%">
      <b>TIPO Y NUMERO DE DOCUMENTO</b>
    </th>
    <th width="25%">
      <b>DIRECCIONES</b>
    </th>
    <th width="25%">
      <b>FECHA DE INSCRIPCION</b>
    </th>
  </tr>  
    <logic:iterate name="bpropi" id="item2">
  <tr> 
    <td>&nbsp;<bean:write name="item2" property="nombre"/></td>
    <td>&nbsp;<bean:write name="item2" property="documentos"/></td>
    <td>&nbsp;<bean:write name="item2" property="direcciones"/></td>
    <td>&nbsp;<bean:write name="item2" property="fechaInsc"/></td>
  </tr>
    </logic:iterate>
 </TBODY>
</TABLE>    
  </logic:present>

<br>    
<TABLE class="grilla" border=0>
  <TBODY>
  <TR>
    <Th align=left bgColor=#800000 height=18>
      G.- PLACAS ANTERIORES
    </Th>
    
  </TR>
  <TR>
    <Td align=left width=80% height=18 bgColor=#f6f6f6 borderColor=#000000 borderColorLight=#000000 bordercolordark=#000000>
      <%=request.getAttribute("listaPlac")%>&nbsp;
    </Td>
    
  </TR>
  </TBODY>
</TABLE>
<br>    
<%
}%>

<logic:present name="mensajeAdm">
  <script language="javascript">
    alert("<%=request.getAttribute("mensajeAdm")%>");
  </script>
</logic:present>
</logic:notPresent>

<table class=tablasinestilo>
   	<tr> 
    	<td align=right>&nbsp;</td>
	</tr> 
   	<tr> 
    	<td align=right>
            <!--<div id="HOJA2" style="position:absolute; left : 400px; top : 1000px; visibility: visible;">-->
         <div id="HOJA2">
    	  <a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    	    <img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25" hspace="4">
    	  </a>
    	  <a href="javascript:Regresa();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
    	    <img src="<%=request.getContextPath()%>/images/btn_regresa.gif" width="83" height="25" hspace="4" border="0"></a>
    	 </div>
            </td>
	</tr>
</table>

<script>
window.top.frames[0].location.reload();
</script>

</body>
</html>