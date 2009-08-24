<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.calculadora.bean.*" %>
<HTML>
 
<head>
	 <title>Calculadora Registral - Datos</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	 <LINK REL="stylesheet" type="text/css" href="styles/global.css">
	 <script language="JavaScript" src="javascript/util.js"></script>
</head>

<script language="javascript">
function mostrarLibro() 
{
	document.frm1.action="/iri/CalculadoraRegistral.do?state=obtenerLibro";		
	document.frm1.submit();
}

function mostrarActos() 
{
	document.frm1.action="/iri/CalculadoraRegistral.do?state=obtenerActos";		
	document.frm1.submit();
}

function doCambiaCombo(combo, valor)
{ 
	for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}

function mostrarCalculo(indice)
{
	document.frm1.hidIndice.value = indice;
	document.frm1.action="/iri/CalculadoraRegistral.do?state=mostrarCalculo";		
	document.frm1.submit();
}

function Aceptar()
{
	document.frm1.action="/iri/CalculadoraRegistral.do?state=aceptarCalculoPrincipal";		
	document.frm1.submit();
}
</script>

<BODY>
<BR>
<TABLE cellspacing=0 class=titulo>
    <TBODY>
        <TR>
            <TD width="258">Calculadora Registral</TD>
        </TR>
    </TBODY>
</TABLE>
<BR>
<FORM name="frm1" method="POST" class="formulario">
<input type="hidden" name="hidIndice" value="">
  	<table class=tablasinestilo>
    	<tr>
            <th>Calculadora Registral</th>
        </tr>
    	<tr>
            <td>
            <TABLE border="0" width="100%">
                <TBODY>
                    <TR>
                        <TD>
                        	<TABLE border="0" width="100%">
                            	<TBODY>
                             	   <TR>
	                                    <TD width="165"><strong>AREA </strong></TD>
	                                    <TD>
	                                    	<SELECT name="cboArea" size="1" style="width:380" onchange="javascript:mostrarLibro()">
							            		<option value="">&nbsp;</option>
							            		<logic:present name="arrArea">
													<logic:iterate name="arrArea" id="item1" scope="session">
														<option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
													</logic:iterate>
												</logic:present>
								            </SELECT>
								        </TD>
								   </TR>
								   <TR>
	                                    <TD width="155"><strong>LIBRO REGISTRAL </strong></TD>
	                                    <TD>
	                                    	<SELECT name="cboLibro" size="1" style="width:380" onchange="javascript:mostrarActos()">
								            	<OPTION value="">&nbsp;</option>
								            	<logic:present name="arrLibro">
													<logic:iterate name="arrLibro" id="item2" scope="session">
														<option value="<bean:write name="item2" property="codigo"/>" ><bean:write name="item2" property="descripcion"/></option>
													</logic:iterate>
												</logic:present>
								            </SELECT>
								        </TD>
                                  </TR>
	                            </TBODY>
	                        </TABLE>
                        </TD>
                    </TR>
                 </TBODY>
             </TABLE>
             </td>
        </tr>
    	<tr>
            <th>ACTOS REGISTRALES</th>
        </tr>
        <tr>
            <td width="70%"></td>
            </TR>
        <tr>
            <td width="258">
            <TABLE width="100%" border="0" class="grilla" cellspacing="0">
                <TBODY>
                    <TR>
                        <TH width="2%" align="center" height="12">
                        <DIV align="center"><INPUT type="checkbox"></DIV>
                        </TH>
                        <TH width="15%" align="center">
                        <DIV align="center">DESCRIPCI&Oacute;N</DIV>
                        </TH>
                        <TH width="9%" align="center" height="12">
                        <DIV align="center">PRESENTACI&Oacute;N</DIV>
                        </TH>
                        <TH width="11%" align="center" height="12">
                        <DIV align="center">INSCRIPCI&Oacute;N</DIV>
                        </TH>
                        <TH width="11%" align="center" height="12">
                        <DIV align="center">NRO. DE VECES</DIV>
                        </TH>
                        <TH width="9%" align="center">
                        <DIV align="center">TOTAL</DIV>
                        </TH>
                        <TH width="9%" align="center">
                        <DIV align="center">VALOR MIN.</DIV>
                        </TH>
                    </TR>
                    <% java.util.ArrayList listaActos = (java.util.ArrayList)session.getAttribute("listaActos");
                       int size = 0;
                       if (listaActos!=null)
                          size = listaActos.size();
                       ActoCalculadoraRegistral acto = null;
                       for (int i=0; i<size; i++) { 
                       		acto = (ActoCalculadoraRegistral)listaActos.get(i);
                    %>
                    <TR>
                        <TD align="center"><INPUT type="checkbox" name="chkActo" value="<%=i%>" <% if (acto.getSeleccionado() == true ){ %> checked <% } %> ></TD>
                        <TD align="center"><A href="javascript:mostrarCalculo(<%=i%>)"><%=acto.getDescripcionActo()%></A></TD>
                        <TD align="right"><%=acto.getMontoPresentacion()%></TD>
                        <TD align="right"><%=acto.getMontoInscripcion()%></TD>
                        <TD align="right"><%=acto.getNroVeces()%></TD>
                        <TD align="right"><%=acto.getTotal()%></TD>
                        <TD align="right"><%=acto.getValorMinimo()%></TD>
                    </TR>
					<%}%>
                </TBODY>
            </TABLE>
            <TABLE border="0" class="grilla">
                <TBODY>
                    <TR>

                    </TR>
                </TBODY>
            </TABLE>
            </td>
        </tr>

        <tr>
            <td width="100%">

            <TABLE width="100%">
                <TBODY>
                    <TR>
                        <TD align="center">
                        <TABLE width="100%">
                            <TBODY>
                                <TR>
                                	<TD align="center">
	                          			<A href="javascript:Aceptar();" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
											<IMG src="images/btn_aceptar.gif" border="0">
										</A>
									</TD>
                                </TR>
                            </TBODY>
                        </TABLE>
                        </TD>
                    </TR>
                </TBODY>
            </TABLE>
        </tr>



        <tr>
            <td width="100%">
            <TABLE border="0">
                <TBODY>
                    <TR>
                        <TD width="165"><strong>TOTAL PRESENTACI&Oacute;N</strong></TD>
                        <TD><INPUT type="text" name="totalPresentacion" size="20" maxlength="130" style="width:120" <% if (request.getAttribute("totalPresentacion")!=null) { %> value="<%=request.getAttribute("totalPresentacion")%>" <%}%> readonly></TD>
                    </TR>
                    <TR>
                        <TD><strong>TOTAL INSCRIPCI&Oacute;N</strong></TD>
                        <TD><INPUT type="text" name="totalInscripcion" size="20" maxlength="130" style="width:120" <% if (request.getAttribute("totalInscripcion")!=null) { %> value="<%=request.getAttribute("totalInscripcion")%>" <%}%> readonly></TD>
                    </TR>
                    <TR>
                        <TD><strong>TOTAL DERECHOS A PAGAR</strong></TD>
                        <TD><INPUT type="text" name="totalDerechosPagar" size="20" maxlength="130" style="width:120" <% if (request.getAttribute("totalDerechoPagar")!=null) { %> value="<%=request.getAttribute("totalDerechoPagar")%>" <%}%> readonly></TD>
                    </TR>
                </TBODY>
            </TABLE>
            </td>
        </tr>
        <tr>
            <td width="100%">
            <TABLE border="0" width="100%">
                <TBODY>
                    <TR>
                        <TD>
                        <TABLE align="center" width="100%" border="0">
                            <TBODY>
                                <TR>
                                    <TD width="250" align="right"><strong>TIPO CAMBIO: </strong></TD>
                                    <TD width="40" align="left"><strong><%=(String)session.getAttribute("tipoCambioDia")%></strong></TD>
                                    <TD width="40" align="right"><strong>U.I.T.: </strong></TD>
                                    <TD width="250" align="left"><strong><%=(String)session.getAttribute("montoUIT")%></strong></TD>
                                </TR>
                            </TBODY>
                        </TABLE>
                        </TD>
                        <%--<TD>
                        <TABLE border="0">
                            <TBODY>
                                <TR>
                                    <TD width="129">17/09/2003</TD>
                                    <TD width="142">9:00 am</TD>
                                </TR>
                            </TBODY>
                        </TABLE>
                        </TD>--%>
                    </TR>
                </TBODY>
            </TABLE>
            </td>
        </tr>
<TABLE>
</TABLE>
</table>

</FORM>
<script LANGUAGE="JavaScript">
	doCambiaCombo(document.frm1.cboArea, "<%=request.getAttribute("codArea")%>");
	doCambiaCombo(document.frm1.cboLibro, "<%=request.getAttribute("codLibro")%>");
</script>
</BODY>
</HTML>
