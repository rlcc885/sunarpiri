<%@page contentType="text/html;charset=UTF-8"%>
<HTML>
<HEAD>
<TITLE>Inputs</TITLE>
</HEAD>
<BODY>
<H1>Inputs</H1>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

boolean valid = true;

if(methodID != -1) methodID = Integer.parseInt(method);
switch (methodID){ 
case 2:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">xmlForm:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="xmlForm5" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">xmlHash:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="xmlHash7" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">rtfFile:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="rtfFile9" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 11:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">datosXML:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="datosXML152" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 154:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">solicitudInscripcion:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionServicio297" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoUsuario299" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral301" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionOficinaRegistral303" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoActo305" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cuo307" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral309" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">partidas:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">presentante:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoPresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoPresentante317" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoInstitucion319" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionInstitucion321" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anho327" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionArea329" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionLibro331" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="documentoEscrituraPublica335" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="nombreArchivo337" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">vehiculos:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">personaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedadAnonima345" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRUC347" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRepresentante349" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroPartida351" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedad353" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoParticipantePJSUNAT355" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedadAnonima357" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="razonSocial359" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral361" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral363" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="siglas365" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedad367" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ipRemota369" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="secuencialOperacion371" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="porcentajeCancelado375" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numero377" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="montoCapital379" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionMoneda381" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="valor383" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoCancelacionCapital385" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionCancelacionCapital387" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoMoneda389" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaSolicitud391" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoLibro393" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoArea395" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">instrumentoPublico:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">reservaMercantil:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroTitulo403" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anhoTitulo405" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroHoja407" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoServicio413" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionActo415" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="costoTotalServicio419" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="horaPago421" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroOperacion423" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoPago425" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoFormaPago427" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoPago429" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaPago431" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionFormaPago433" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">datosXML:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="datosXML435" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">doc:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 589:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">solicitudInscripcion:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionServicio732" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoUsuario734" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral736" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionOficinaRegistral738" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoActo740" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cuo742" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral744" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">partidas:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">presentante:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoPresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoPresentante752" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoInstitucion754" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionInstitucion756" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anho762" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionArea764" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionLibro766" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="documentoEscrituraPublica770" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="nombreArchivo772" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">vehiculos:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">personaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedadAnonima780" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRUC782" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRepresentante784" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroPartida786" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedad788" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoParticipantePJSUNAT790" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedadAnonima792" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="razonSocial794" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral796" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral798" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="siglas800" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedad802" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ipRemota804" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="secuencialOperacion806" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="porcentajeCancelado810" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numero812" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="montoCapital814" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionMoneda816" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="valor818" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoCancelacionCapital820" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionCancelacionCapital822" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoMoneda824" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaSolicitud826" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoLibro828" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoArea830" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">instrumentoPublico:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">reservaMercantil:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroTitulo838" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anhoTitulo840" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroHoja842" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoServicio848" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionActo850" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="costoTotalServicio854" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="horaPago856" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroOperacion858" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoPago860" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoFormaPago862" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoPago864" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaPago866" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionFormaPago868" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">datosXML:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="datosXML870" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">doc:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1024:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">solicitudInscripcion:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionServicio1167" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoUsuario1169" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral1171" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionOficinaRegistral1173" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoActo1175" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cuo1177" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral1179" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">partidas:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">presentante:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoPresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoPresentante1187" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoInstitucion1189" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionInstitucion1191" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anho1197" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionArea1199" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionLibro1201" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="documentoEscrituraPublica1205" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="nombreArchivo1207" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">vehiculos:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">personaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedadAnonima1215" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRUC1217" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRepresentante1219" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroPartida1221" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedad1223" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoParticipantePJSUNAT1225" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedadAnonima1227" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="razonSocial1229" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral1231" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral1233" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="siglas1235" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedad1237" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ipRemota1239" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="secuencialOperacion1241" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="porcentajeCancelado1245" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numero1247" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="montoCapital1249" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionMoneda1251" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="valor1253" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoCancelacionCapital1255" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionCancelacionCapital1257" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoMoneda1259" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaSolicitud1261" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoLibro1263" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoArea1265" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">instrumentoPublico:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">reservaMercantil:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroTitulo1273" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anhoTitulo1275" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroHoja1277" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoServicio1283" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionActo1285" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="costoTotalServicio1289" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="horaPago1291" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroOperacion1293" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoPago1295" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoFormaPago1297" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoPago1299" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaPago1301" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionFormaPago1303" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">datosXML:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="datosXML1305" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">doc:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1459:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">solicitudInscripcion:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionServicio1602" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoUsuario1604" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral1606" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionOficinaRegistral1608" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoActo1610" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cuo1612" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral1614" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">partidas:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">presentante:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoPresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoPresentante1622" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoInstitucion1624" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionInstitucion1626" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anho1632" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionArea1634" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionLibro1636" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="documentoEscrituraPublica1640" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="nombreArchivo1642" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">vehiculos:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">personaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedadAnonima1650" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRUC1652" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="indicadorRepresentante1654" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroPartida1656" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoSociedad1658" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoParticipantePJSUNAT1660" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedadAnonima1662" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="razonSocial1664" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoOficinaRegistral1666" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoZonaRegistral1668" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="siglas1670" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoSociedad1672" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ipRemota1674" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="secuencialOperacion1676" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="porcentajeCancelado1680" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numero1682" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="montoCapital1684" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionMoneda1686" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="valor1688" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoCancelacionCapital1690" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionCancelacionCapital1692" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoMoneda1694" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaSolicitud1696" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoLibro1698" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoArea1700" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">instrumentoPublico:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">reservaMercantil:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroTitulo1708" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="anhoTitulo1710" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroHoja1712" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoServicio1718" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionActo1720" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="costoTotalServicio1724" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="horaPago1726" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numeroOperacion1728" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionTipoPago1730" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoFormaPago1732" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="codigoTipoPago1734" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="fechaPago1736" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="descripcionFormaPago1738" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">datosXML:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="datosXML1740" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">doc:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1894:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">datosXML:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="datosXML1933" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1935:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">strByteAux:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="strByteAux1938" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1111111111:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">URLString:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="url1111111111" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1111111112:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
}
if (valid) {
%>
Select a method to test.
<%
    return;
}
%>

</BODY>
</HTML>
