<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleSunarpServiceProviderid" scope="session" class="gob.pe.sunarp.extranet.webservices.SunarpServiceProvider" />

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        String xmlForm_0id=  request.getParameter("xmlForm5");
        java.lang.String xmlForm_0idTemp  = xmlForm_0id;
        String xmlHash_1id=  request.getParameter("xmlHash7");
        java.lang.String xmlHash_1idTemp  = xmlHash_1id;
        String rtfFile_2id=  request.getParameter("rtfFile9");
        java.lang.String rtfFile_2idTemp  = rtfFile_2id;
        java.lang.String setNewRegisterRequest2mtemp = sampleSunarpServiceProviderid.setNewRegisterRequest(xmlForm_0idTemp,xmlHash_1idTemp,rtfFile_2idTemp);
if(setNewRegisterRequest2mtemp == null){
%>
<%=setNewRegisterRequest2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(setNewRegisterRequest2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 11:
        gotMethod = true;
        String datosXML_3id=  request.getParameter("datosXML152");
        java.lang.String datosXML_3idTemp  = datosXML_3id;
        gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion loadXMLtoBean11mtemp = sampleSunarpServiceProviderid.loadXMLtoBean(datosXML_3idTemp);
if(loadXMLtoBean11mtemp == null){
%>
<%=loadXMLtoBean11mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typedescripcionServicio14 = loadXMLtoBean11mtemp.getDescripcionServicio();
        String tempResultdescripcionServicio14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionServicio14));
        %>
        <%= tempResultdescripcionServicio14 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoUsuario16 = loadXMLtoBean11mtemp.getCodigoUsuario();
        String tempResultcodigoUsuario16 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario16));
        %>
        <%= tempResultcodigoUsuario16 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoOficinaRegistral18 = loadXMLtoBean11mtemp.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral18 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral18));
        %>
        <%= tempResultcodigoOficinaRegistral18 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typedescripcionOficinaRegistral20 = loadXMLtoBean11mtemp.getDescripcionOficinaRegistral();
        String tempResultdescripcionOficinaRegistral20 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionOficinaRegistral20));
        %>
        <%= tempResultdescripcionOficinaRegistral20 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoActo22 = loadXMLtoBean11mtemp.getCodigoActo();
        String tempResultcodigoActo22 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoActo22));
        %>
        <%= tempResultcodigoActo22 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecuo24 = loadXMLtoBean11mtemp.getCuo();
        String tempResultcuo24 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo24));
        %>
        <%= tempResultcuo24 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoZonaRegistral26 = loadXMLtoBean11mtemp.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral26 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral26));
        %>
        <%= tempResultcodigoZonaRegistral26 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBean11mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoPresentante34 = tebece0.getCodigoPresentante();
        String tempResultcodigoPresentante34 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoPresentante34));
        %>
        <%= tempResultcodigoPresentante34 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBean11mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoInstitucion36 = tebece0.getCodigoInstitucion();
        String tempResultcodigoInstitucion36 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoInstitucion36));
        %>
        <%= tempResultcodigoInstitucion36 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBean11mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typedescripcionInstitucion38 = tebece0.getDescripcionInstitucion();
        String tempResultdescripcionInstitucion38 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionInstitucion38));
        %>
        <%= tempResultdescripcionInstitucion38 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typeanho44 = loadXMLtoBean11mtemp.getAnho();
        String tempResultanho44 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanho44));
        %>
        <%= tempResultanho44 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typedescripcionArea46 = loadXMLtoBean11mtemp.getDescripcionArea();
        String tempResultdescripcionArea46 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionArea46));
        %>
        <%= tempResultdescripcionArea46 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typedescripcionLibro48 = loadXMLtoBean11mtemp.getDescripcionLibro();
        String tempResultdescripcionLibro48 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionLibro48));
        %>
        <%= tempResultdescripcionLibro48 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBean11mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typedocumentoEscrituraPublica52 = tebece0.getDocumentoEscrituraPublica();
        String tempResultdocumentoEscrituraPublica52 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedocumentoEscrituraPublica52));
        %>
        <%= tempResultdocumentoEscrituraPublica52 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBean11mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typenombreArchivo54 = tebece0.getNombreArchivo();
        String tempResultnombreArchivo54 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombreArchivo54));
        %>
        <%= tempResultnombreArchivo54 %>
        <%
}}%>
</TD>
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
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedadAnonima62 = tebece0.getDescripcionTipoSociedadAnonima();
        String tempResultdescripcionTipoSociedadAnonima62 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedadAnonima62));
        %>
        <%= tempResultdescripcionTipoSociedadAnonima62 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRUC64 = tebece0.getIndicadorRUC();
        String tempResultindicadorRUC64 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRUC64));
        %>
        <%= tempResultindicadorRUC64 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRepresentante66 = tebece0.getIndicadorRepresentante();
        String tempResultindicadorRepresentante66 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRepresentante66));
        %>
        <%= tempResultindicadorRepresentante66 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typenumeroPartida68 = tebece0.getNumeroPartida();
        String tempResultnumeroPartida68 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroPartida68));
        %>
        <%= tempResultnumeroPartida68 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedad70 = tebece0.getDescripcionTipoSociedad();
        String tempResultdescripcionTipoSociedad70 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedad70));
        %>
        <%= tempResultdescripcionTipoSociedad70 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoParticipantePJSUNAT72 = tebece0.getCodigoTipoParticipantePJSUNAT();
        String tempResultcodigoTipoParticipantePJSUNAT72 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoParticipantePJSUNAT72));
        %>
        <%= tempResultcodigoTipoParticipantePJSUNAT72 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedadAnonima74 = tebece0.getCodigoTipoSociedadAnonima();
        String tempResultcodigoTipoSociedadAnonima74 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedadAnonima74));
        %>
        <%= tempResultcodigoTipoSociedadAnonima74 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typerazonSocial76 = tebece0.getRazonSocial();
        String tempResultrazonSocial76 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazonSocial76));
        %>
        <%= tempResultrazonSocial76 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoOficinaRegistral78 = tebece0.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral78 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral78));
        %>
        <%= tempResultcodigoOficinaRegistral78 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoZonaRegistral80 = tebece0.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral80 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral80));
        %>
        <%= tempResultcodigoZonaRegistral80 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typesiglas82 = tebece0.getSiglas();
        String tempResultsiglas82 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesiglas82));
        %>
        <%= tempResultsiglas82 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBean11mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedad84 = tebece0.getCodigoTipoSociedad();
        String tempResultcodigoTipoSociedad84 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedad84));
        %>
        <%= tempResultcodigoTipoSociedad84 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typeipRemota86 = loadXMLtoBean11mtemp.getIpRemota();
        String tempResultipRemota86 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeipRemota86));
        %>
        <%= tempResultipRemota86 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typesecuencialOperacion88 = loadXMLtoBean11mtemp.getSecuencialOperacion();
        String tempResultsecuencialOperacion88 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesecuencialOperacion88));
        %>
        <%= tempResultsecuencialOperacion88 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typeporcentajeCancelado92 = tebece0.getPorcentajeCancelado();
        String tempResultporcentajeCancelado92 = org.eclipse.jst.ws.util.JspUtils.markup(typeporcentajeCancelado92.toString());
        %>
        <%= tempResultporcentajeCancelado92 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
%>
<%=tebece0.getNumero()
%><%}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typemontoCapital96 = tebece0.getMontoCapital();
        String tempResultmontoCapital96 = org.eclipse.jst.ws.util.JspUtils.markup(typemontoCapital96.toString());
        %>
        <%= tempResultmontoCapital96 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionMoneda98 = tebece0.getDescripcionMoneda();
        String tempResultdescripcionMoneda98 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionMoneda98));
        %>
        <%= tempResultdescripcionMoneda98 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typevalor100 = tebece0.getValor();
        String tempResultvalor100 = org.eclipse.jst.ws.util.JspUtils.markup(typevalor100.toString());
        %>
        <%= tempResultvalor100 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoCancelacionCapital102 = tebece0.getCodigoCancelacionCapital();
        String tempResultcodigoCancelacionCapital102 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoCancelacionCapital102));
        %>
        <%= tempResultcodigoCancelacionCapital102 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionCancelacionCapital104 = tebece0.getDescripcionCancelacionCapital();
        String tempResultdescripcionCancelacionCapital104 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionCancelacionCapital104));
        %>
        <%= tempResultdescripcionCancelacionCapital104 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBean11mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoMoneda106 = tebece0.getCodigoMoneda();
        String tempResultcodigoMoneda106 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoMoneda106));
        %>
        <%= tempResultcodigoMoneda106 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typefechaSolicitud108 = loadXMLtoBean11mtemp.getFechaSolicitud();
        String tempResultfechaSolicitud108 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaSolicitud108));
        %>
        <%= tempResultfechaSolicitud108 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoLibro110 = loadXMLtoBean11mtemp.getCodigoLibro();
        String tempResultcodigoLibro110 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoLibro110));
        %>
        <%= tempResultcodigoLibro110 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoArea112 = loadXMLtoBean11mtemp.getCodigoArea();
        String tempResultcodigoArea112 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoArea112));
        %>
        <%= tempResultcodigoArea112 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBean11mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typenumeroTitulo120 = tebece0.getNumeroTitulo();
        String tempResultnumeroTitulo120 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroTitulo120));
        %>
        <%= tempResultnumeroTitulo120 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBean11mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typeanhoTitulo122 = tebece0.getAnhoTitulo();
        String tempResultanhoTitulo122 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanhoTitulo122));
        %>
        <%= tempResultanhoTitulo122 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typenumeroHoja124 = loadXMLtoBean11mtemp.getNumeroHoja();
        String tempResultnumeroHoja124 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroHoja124));
        %>
        <%= tempResultnumeroHoja124 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typecodigoServicio130 = loadXMLtoBean11mtemp.getCodigoServicio();
        String tempResultcodigoServicio130 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoServicio130));
        %>
        <%= tempResultcodigoServicio130 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
java.lang.String typedescripcionActo132 = loadXMLtoBean11mtemp.getDescripcionActo();
        String tempResultdescripcionActo132 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionActo132));
        %>
        <%= tempResultdescripcionActo132 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.math.BigDecimal typecostoTotalServicio136 = tebece0.getCostoTotalServicio();
        String tempResultcostoTotalServicio136 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio136.toString());
        %>
        <%= tempResultcostoTotalServicio136 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typehoraPago138 = tebece0.getHoraPago();
        String tempResulthoraPago138 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago138));
        %>
        <%= tempResulthoraPago138 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typenumeroOperacion140 = tebece0.getNumeroOperacion();
        String tempResultnumeroOperacion140 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion140));
        %>
        <%= tempResultnumeroOperacion140 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionTipoPago142 = tebece0.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago142 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago142));
        %>
        <%= tempResultdescripcionTipoPago142 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoFormaPago144 = tebece0.getCodigoFormaPago();
        String tempResultcodigoFormaPago144 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago144));
        %>
        <%= tempResultcodigoFormaPago144 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoTipoPago146 = tebece0.getCodigoTipoPago();
        String tempResultcodigoTipoPago146 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago146));
        %>
        <%= tempResultcodigoTipoPago146 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typefechaPago148 = tebece0.getFechaPago();
        String tempResultfechaPago148 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago148));
        %>
        <%= tempResultfechaPago148 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(loadXMLtoBean11mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBean11mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionFormaPago150 = tebece0.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago150 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago150));
        %>
        <%= tempResultdescripcionFormaPago150 %>
        <%
}}%>
</TD>
</TABLE>
<%
}
break;
case 154:
        gotMethod = true;
        String descripcionServicio_5id=  request.getParameter("descripcionServicio297");
        java.lang.String descripcionServicio_5idTemp  = descripcionServicio_5id;
        String codigoUsuario_6id=  request.getParameter("codigoUsuario299");
        java.lang.String codigoUsuario_6idTemp  = codigoUsuario_6id;
        String codigoOficinaRegistral_7id=  request.getParameter("codigoOficinaRegistral301");
        java.lang.String codigoOficinaRegistral_7idTemp  = codigoOficinaRegistral_7id;
        String descripcionOficinaRegistral_8id=  request.getParameter("descripcionOficinaRegistral303");
        java.lang.String descripcionOficinaRegistral_8idTemp  = descripcionOficinaRegistral_8id;
        String codigoActo_9id=  request.getParameter("codigoActo305");
        java.lang.String codigoActo_9idTemp  = codigoActo_9id;
        String cuo_10id=  request.getParameter("cuo307");
        java.lang.String cuo_10idTemp  = cuo_10id;
        String codigoZonaRegistral_11id=  request.getParameter("codigoZonaRegistral309");
        java.lang.String codigoZonaRegistral_11idTemp  = codigoZonaRegistral_11id;
        %>
        <jsp:useBean id="java1util1ArrayList_12id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoPresentante_14id=  request.getParameter("codigoPresentante317");
        java.lang.String codigoPresentante_14idTemp  = codigoPresentante_14id;
        String codigoInstitucion_15id=  request.getParameter("codigoInstitucion319");
        java.lang.String codigoInstitucion_15idTemp  = codigoInstitucion_15id;
        String descripcionInstitucion_16id=  request.getParameter("descripcionInstitucion321");
        java.lang.String descripcionInstitucion_16idTemp  = descripcionInstitucion_16id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_13id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_13id.setCodigoPresentante(codigoPresentante_14idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_13id.setCodigoInstitucion(codigoInstitucion_15idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_13id.setDescripcionInstitucion(descripcionInstitucion_16idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_17id" scope="session" class="java.util.ArrayList" />
        <%
        String anho_18id=  request.getParameter("anho327");
        java.lang.String anho_18idTemp  = anho_18id;
        String descripcionArea_19id=  request.getParameter("descripcionArea329");
        java.lang.String descripcionArea_19idTemp  = descripcionArea_19id;
        String descripcionLibro_20id=  request.getParameter("descripcionLibro331");
        java.lang.String descripcionLibro_20idTemp  = descripcionLibro_20id;
        String documentoEscrituraPublica_22id=  request.getParameter("documentoEscrituraPublica335");
        java.lang.String documentoEscrituraPublica_22idTemp  = documentoEscrituraPublica_22id;
        String nombreArchivo_23id=  request.getParameter("nombreArchivo337");
        java.lang.String nombreArchivo_23idTemp  = nombreArchivo_23id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_21id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_21id.setDocumentoEscrituraPublica(documentoEscrituraPublica_22idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_21id.setNombreArchivo(nombreArchivo_23idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_24id" scope="session" class="java.util.ArrayList" />
        <%
        String descripcionTipoSociedadAnonima_26id=  request.getParameter("descripcionTipoSociedadAnonima345");
        java.lang.String descripcionTipoSociedadAnonima_26idTemp  = descripcionTipoSociedadAnonima_26id;
        String indicadorRUC_27id=  request.getParameter("indicadorRUC347");
        java.lang.String indicadorRUC_27idTemp  = indicadorRUC_27id;
        String indicadorRepresentante_28id=  request.getParameter("indicadorRepresentante349");
        java.lang.String indicadorRepresentante_28idTemp  = indicadorRepresentante_28id;
        String numeroPartida_29id=  request.getParameter("numeroPartida351");
        java.lang.String numeroPartida_29idTemp  = numeroPartida_29id;
        String descripcionTipoSociedad_30id=  request.getParameter("descripcionTipoSociedad353");
        java.lang.String descripcionTipoSociedad_30idTemp  = descripcionTipoSociedad_30id;
        String codigoTipoParticipantePJSUNAT_31id=  request.getParameter("codigoTipoParticipantePJSUNAT355");
        java.lang.String codigoTipoParticipantePJSUNAT_31idTemp  = codigoTipoParticipantePJSUNAT_31id;
        String codigoTipoSociedadAnonima_32id=  request.getParameter("codigoTipoSociedadAnonima357");
        java.lang.String codigoTipoSociedadAnonima_32idTemp  = codigoTipoSociedadAnonima_32id;
        String razonSocial_33id=  request.getParameter("razonSocial359");
        java.lang.String razonSocial_33idTemp  = razonSocial_33id;
        String codigoOficinaRegistral_34id=  request.getParameter("codigoOficinaRegistral361");
        java.lang.String codigoOficinaRegistral_34idTemp  = codigoOficinaRegistral_34id;
        String codigoZonaRegistral_35id=  request.getParameter("codigoZonaRegistral363");
        java.lang.String codigoZonaRegistral_35idTemp  = codigoZonaRegistral_35id;
        String siglas_36id=  request.getParameter("siglas365");
        java.lang.String siglas_36idTemp  = siglas_36id;
        String codigoTipoSociedad_37id=  request.getParameter("codigoTipoSociedad367");
        java.lang.String codigoTipoSociedad_37idTemp  = codigoTipoSociedad_37id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setDescripcionTipoSociedadAnonima(descripcionTipoSociedadAnonima_26idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setIndicadorRUC(indicadorRUC_27idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setIndicadorRepresentante(indicadorRepresentante_28idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setNumeroPartida(numeroPartida_29idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setDescripcionTipoSociedad(descripcionTipoSociedad_30idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setCodigoTipoParticipantePJSUNAT(codigoTipoParticipantePJSUNAT_31idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setCodigoTipoSociedadAnonima(codigoTipoSociedadAnonima_32idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setRazonSocial(razonSocial_33idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setCodigoOficinaRegistral(codigoOficinaRegistral_34idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setCodigoZonaRegistral(codigoZonaRegistral_35idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setSiglas(siglas_36idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id.setCodigoTipoSociedad(codigoTipoSociedad_37idTemp);
        String ipRemota_38id=  request.getParameter("ipRemota369");
        java.lang.String ipRemota_38idTemp  = ipRemota_38id;
        String secuencialOperacion_39id=  request.getParameter("secuencialOperacion371");
        java.lang.String secuencialOperacion_39idTemp  = secuencialOperacion_39id;
        String porcentajeCancelado_41id=  request.getParameter("porcentajeCancelado375");
        java.math.BigDecimal porcentajeCancelado_41idTemp  = new java.math.BigDecimal(porcentajeCancelado_41id);
        String numero_42id=  request.getParameter("numero377");
        int numero_42idTemp  = Integer.parseInt(numero_42id);
        String montoCapital_43id=  request.getParameter("montoCapital379");
        java.math.BigDecimal montoCapital_43idTemp  = new java.math.BigDecimal(montoCapital_43id);
        String descripcionMoneda_44id=  request.getParameter("descripcionMoneda381");
        java.lang.String descripcionMoneda_44idTemp  = descripcionMoneda_44id;
        String valor_45id=  request.getParameter("valor383");
        java.math.BigDecimal valor_45idTemp  = new java.math.BigDecimal(valor_45id);
        String codigoCancelacionCapital_46id=  request.getParameter("codigoCancelacionCapital385");
        java.lang.String codigoCancelacionCapital_46idTemp  = codigoCancelacionCapital_46id;
        String descripcionCancelacionCapital_47id=  request.getParameter("descripcionCancelacionCapital387");
        java.lang.String descripcionCancelacionCapital_47idTemp  = descripcionCancelacionCapital_47id;
        String codigoMoneda_48id=  request.getParameter("codigoMoneda389");
        java.lang.String codigoMoneda_48idTemp  = codigoMoneda_48id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setPorcentajeCancelado(porcentajeCancelado_41idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setNumero(numero_42idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setMontoCapital(montoCapital_43idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setDescripcionMoneda(descripcionMoneda_44idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setValor(valor_45idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setCodigoCancelacionCapital(codigoCancelacionCapital_46idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setDescripcionCancelacionCapital(descripcionCancelacionCapital_47idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id.setCodigoMoneda(codigoMoneda_48idTemp);
        String fechaSolicitud_49id=  request.getParameter("fechaSolicitud391");
        java.lang.String fechaSolicitud_49idTemp  = fechaSolicitud_49id;
        String codigoLibro_50id=  request.getParameter("codigoLibro393");
        java.lang.String codigoLibro_50idTemp  = codigoLibro_50id;
        String codigoArea_51id=  request.getParameter("codigoArea395");
        java.lang.String codigoArea_51idTemp  = codigoArea_51id;
        %>
        <jsp:useBean id="java1util1ArrayList_52id" scope="session" class="java.util.ArrayList" />
        <%
        String numeroTitulo_54id=  request.getParameter("numeroTitulo403");
        java.lang.String numeroTitulo_54idTemp  = numeroTitulo_54id;
        String anhoTitulo_55id=  request.getParameter("anhoTitulo405");
        java.lang.String anhoTitulo_55idTemp  = anhoTitulo_55id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_53id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_53id.setNumeroTitulo(numeroTitulo_54idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_53id.setAnhoTitulo(anhoTitulo_55idTemp);
        String numeroHoja_56id=  request.getParameter("numeroHoja407");
        java.lang.String numeroHoja_56idTemp  = numeroHoja_56id;
        %>
        <jsp:useBean id="java1util1ArrayList_57id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoServicio_58id=  request.getParameter("codigoServicio413");
        java.lang.String codigoServicio_58idTemp  = codigoServicio_58id;
        String descripcionActo_59id=  request.getParameter("descripcionActo415");
        java.lang.String descripcionActo_59idTemp  = descripcionActo_59id;
        String costoTotalServicio_61id=  request.getParameter("costoTotalServicio419");
        java.math.BigDecimal costoTotalServicio_61idTemp  = new java.math.BigDecimal(costoTotalServicio_61id);
        String horaPago_62id=  request.getParameter("horaPago421");
        java.lang.String horaPago_62idTemp  = horaPago_62id;
        String numeroOperacion_63id=  request.getParameter("numeroOperacion423");
        java.lang.String numeroOperacion_63idTemp  = numeroOperacion_63id;
        String descripcionTipoPago_64id=  request.getParameter("descripcionTipoPago425");
        java.lang.String descripcionTipoPago_64idTemp  = descripcionTipoPago_64id;
        String codigoFormaPago_65id=  request.getParameter("codigoFormaPago427");
        java.lang.String codigoFormaPago_65idTemp  = codigoFormaPago_65id;
        String codigoTipoPago_66id=  request.getParameter("codigoTipoPago429");
        java.lang.String codigoTipoPago_66idTemp  = codigoTipoPago_66id;
        String fechaPago_67id=  request.getParameter("fechaPago431");
        java.lang.String fechaPago_67idTemp  = fechaPago_67id;
        String descripcionFormaPago_68id=  request.getParameter("descripcionFormaPago433");
        java.lang.String descripcionFormaPago_68idTemp  = descripcionFormaPago_68id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setCostoTotalServicio(costoTotalServicio_61idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setHoraPago(horaPago_62idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setNumeroOperacion(numeroOperacion_63idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setDescripcionTipoPago(descripcionTipoPago_64idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setCodigoFormaPago(codigoFormaPago_65idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setCodigoTipoPago(codigoTipoPago_66idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setFechaPago(fechaPago_67idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id.setDescripcionFormaPago(descripcionFormaPago_68idTemp);
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setDescripcionServicio(descripcionServicio_5idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoUsuario(codigoUsuario_6idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoOficinaRegistral(codigoOficinaRegistral_7idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setDescripcionOficinaRegistral(descripcionOficinaRegistral_8idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoActo(codigoActo_9idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCuo(cuo_10idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoZonaRegistral(codigoZonaRegistral_11idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setPartidas(java1util1ArrayList_12id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setPresentante(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_13id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setParticipantesPersonaNatural(java1util1ArrayList_17id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setAnho(anho_18idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setDescripcionArea(descripcionArea_19idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setDescripcionLibro(descripcionLibro_20idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setEscrituraPublica(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_21id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setVehiculos(java1util1ArrayList_24id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setPersonaJuridica(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_25id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setIpRemota(ipRemota_38idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setSecuencialOperacion(secuencialOperacion_39idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCapital(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_40id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setFechaSolicitud(fechaSolicitud_49idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoLibro(codigoLibro_50idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoArea(codigoArea_51idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setInstrumentoPublico(java1util1ArrayList_52id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setReservaMercantil(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_53id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setNumeroHoja(numeroHoja_56idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setParticipantesPersonaJuridica(java1util1ArrayList_57id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setCodigoServicio(codigoServicio_58idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setDescripcionActo(descripcionActo_59idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id.setDatosPago(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_60id);
        String datosXML_69id=  request.getParameter("datosXML435");
        java.lang.String datosXML_69idTemp  = datosXML_69id;
        %>
        <jsp:useBean id="org1w3c1dom1Document_70id" scope="session" class="org.w3c.dom.Document" />
        <%
        gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion loadXMLtoBeanConstitucionEmpresa154mtemp = sampleSunarpServiceProviderid.loadXMLtoBeanConstitucionEmpresa(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_4id,datosXML_69idTemp,org1w3c1dom1Document_70id);
if(loadXMLtoBeanConstitucionEmpresa154mtemp == null){
%>
<%=loadXMLtoBeanConstitucionEmpresa154mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typedescripcionServicio157 = loadXMLtoBeanConstitucionEmpresa154mtemp.getDescripcionServicio();
        String tempResultdescripcionServicio157 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionServicio157));
        %>
        <%= tempResultdescripcionServicio157 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoUsuario159 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoUsuario();
        String tempResultcodigoUsuario159 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario159));
        %>
        <%= tempResultcodigoUsuario159 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoOficinaRegistral161 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral161 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral161));
        %>
        <%= tempResultcodigoOficinaRegistral161 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typedescripcionOficinaRegistral163 = loadXMLtoBeanConstitucionEmpresa154mtemp.getDescripcionOficinaRegistral();
        String tempResultdescripcionOficinaRegistral163 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionOficinaRegistral163));
        %>
        <%= tempResultdescripcionOficinaRegistral163 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoActo165 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoActo();
        String tempResultcodigoActo165 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoActo165));
        %>
        <%= tempResultcodigoActo165 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecuo167 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCuo();
        String tempResultcuo167 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo167));
        %>
        <%= tempResultcuo167 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoZonaRegistral169 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral169 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral169));
        %>
        <%= tempResultcodigoZonaRegistral169 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoPresentante177 = tebece0.getCodigoPresentante();
        String tempResultcodigoPresentante177 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoPresentante177));
        %>
        <%= tempResultcodigoPresentante177 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoInstitucion179 = tebece0.getCodigoInstitucion();
        String tempResultcodigoInstitucion179 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoInstitucion179));
        %>
        <%= tempResultcodigoInstitucion179 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typedescripcionInstitucion181 = tebece0.getDescripcionInstitucion();
        String tempResultdescripcionInstitucion181 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionInstitucion181));
        %>
        <%= tempResultdescripcionInstitucion181 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typeanho187 = loadXMLtoBeanConstitucionEmpresa154mtemp.getAnho();
        String tempResultanho187 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanho187));
        %>
        <%= tempResultanho187 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typedescripcionArea189 = loadXMLtoBeanConstitucionEmpresa154mtemp.getDescripcionArea();
        String tempResultdescripcionArea189 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionArea189));
        %>
        <%= tempResultdescripcionArea189 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typedescripcionLibro191 = loadXMLtoBeanConstitucionEmpresa154mtemp.getDescripcionLibro();
        String tempResultdescripcionLibro191 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionLibro191));
        %>
        <%= tempResultdescripcionLibro191 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typedocumentoEscrituraPublica195 = tebece0.getDocumentoEscrituraPublica();
        String tempResultdocumentoEscrituraPublica195 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedocumentoEscrituraPublica195));
        %>
        <%= tempResultdocumentoEscrituraPublica195 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typenombreArchivo197 = tebece0.getNombreArchivo();
        String tempResultnombreArchivo197 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombreArchivo197));
        %>
        <%= tempResultnombreArchivo197 %>
        <%
}}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedadAnonima205 = tebece0.getDescripcionTipoSociedadAnonima();
        String tempResultdescripcionTipoSociedadAnonima205 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedadAnonima205));
        %>
        <%= tempResultdescripcionTipoSociedadAnonima205 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRUC207 = tebece0.getIndicadorRUC();
        String tempResultindicadorRUC207 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRUC207));
        %>
        <%= tempResultindicadorRUC207 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRepresentante209 = tebece0.getIndicadorRepresentante();
        String tempResultindicadorRepresentante209 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRepresentante209));
        %>
        <%= tempResultindicadorRepresentante209 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typenumeroPartida211 = tebece0.getNumeroPartida();
        String tempResultnumeroPartida211 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroPartida211));
        %>
        <%= tempResultnumeroPartida211 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedad213 = tebece0.getDescripcionTipoSociedad();
        String tempResultdescripcionTipoSociedad213 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedad213));
        %>
        <%= tempResultdescripcionTipoSociedad213 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoParticipantePJSUNAT215 = tebece0.getCodigoTipoParticipantePJSUNAT();
        String tempResultcodigoTipoParticipantePJSUNAT215 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoParticipantePJSUNAT215));
        %>
        <%= tempResultcodigoTipoParticipantePJSUNAT215 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedadAnonima217 = tebece0.getCodigoTipoSociedadAnonima();
        String tempResultcodigoTipoSociedadAnonima217 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedadAnonima217));
        %>
        <%= tempResultcodigoTipoSociedadAnonima217 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typerazonSocial219 = tebece0.getRazonSocial();
        String tempResultrazonSocial219 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazonSocial219));
        %>
        <%= tempResultrazonSocial219 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoOficinaRegistral221 = tebece0.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral221 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral221));
        %>
        <%= tempResultcodigoOficinaRegistral221 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoZonaRegistral223 = tebece0.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral223 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral223));
        %>
        <%= tempResultcodigoZonaRegistral223 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typesiglas225 = tebece0.getSiglas();
        String tempResultsiglas225 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesiglas225));
        %>
        <%= tempResultsiglas225 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedad227 = tebece0.getCodigoTipoSociedad();
        String tempResultcodigoTipoSociedad227 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedad227));
        %>
        <%= tempResultcodigoTipoSociedad227 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typeipRemota229 = loadXMLtoBeanConstitucionEmpresa154mtemp.getIpRemota();
        String tempResultipRemota229 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeipRemota229));
        %>
        <%= tempResultipRemota229 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typesecuencialOperacion231 = loadXMLtoBeanConstitucionEmpresa154mtemp.getSecuencialOperacion();
        String tempResultsecuencialOperacion231 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesecuencialOperacion231));
        %>
        <%= tempResultsecuencialOperacion231 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typeporcentajeCancelado235 = tebece0.getPorcentajeCancelado();
        String tempResultporcentajeCancelado235 = org.eclipse.jst.ws.util.JspUtils.markup(typeporcentajeCancelado235.toString());
        %>
        <%= tempResultporcentajeCancelado235 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
%>
<%=tebece0.getNumero()
%><%}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typemontoCapital239 = tebece0.getMontoCapital();
        String tempResultmontoCapital239 = org.eclipse.jst.ws.util.JspUtils.markup(typemontoCapital239.toString());
        %>
        <%= tempResultmontoCapital239 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionMoneda241 = tebece0.getDescripcionMoneda();
        String tempResultdescripcionMoneda241 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionMoneda241));
        %>
        <%= tempResultdescripcionMoneda241 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typevalor243 = tebece0.getValor();
        String tempResultvalor243 = org.eclipse.jst.ws.util.JspUtils.markup(typevalor243.toString());
        %>
        <%= tempResultvalor243 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoCancelacionCapital245 = tebece0.getCodigoCancelacionCapital();
        String tempResultcodigoCancelacionCapital245 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoCancelacionCapital245));
        %>
        <%= tempResultcodigoCancelacionCapital245 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionCancelacionCapital247 = tebece0.getDescripcionCancelacionCapital();
        String tempResultdescripcionCancelacionCapital247 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionCancelacionCapital247));
        %>
        <%= tempResultdescripcionCancelacionCapital247 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoMoneda249 = tebece0.getCodigoMoneda();
        String tempResultcodigoMoneda249 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoMoneda249));
        %>
        <%= tempResultcodigoMoneda249 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typefechaSolicitud251 = loadXMLtoBeanConstitucionEmpresa154mtemp.getFechaSolicitud();
        String tempResultfechaSolicitud251 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaSolicitud251));
        %>
        <%= tempResultfechaSolicitud251 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoLibro253 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoLibro();
        String tempResultcodigoLibro253 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoLibro253));
        %>
        <%= tempResultcodigoLibro253 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoArea255 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoArea();
        String tempResultcodigoArea255 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoArea255));
        %>
        <%= tempResultcodigoArea255 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typenumeroTitulo263 = tebece0.getNumeroTitulo();
        String tempResultnumeroTitulo263 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroTitulo263));
        %>
        <%= tempResultnumeroTitulo263 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typeanhoTitulo265 = tebece0.getAnhoTitulo();
        String tempResultanhoTitulo265 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanhoTitulo265));
        %>
        <%= tempResultanhoTitulo265 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typenumeroHoja267 = loadXMLtoBeanConstitucionEmpresa154mtemp.getNumeroHoja();
        String tempResultnumeroHoja267 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroHoja267));
        %>
        <%= tempResultnumeroHoja267 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typecodigoServicio273 = loadXMLtoBeanConstitucionEmpresa154mtemp.getCodigoServicio();
        String tempResultcodigoServicio273 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoServicio273));
        %>
        <%= tempResultcodigoServicio273 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
java.lang.String typedescripcionActo275 = loadXMLtoBeanConstitucionEmpresa154mtemp.getDescripcionActo();
        String tempResultdescripcionActo275 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionActo275));
        %>
        <%= tempResultdescripcionActo275 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.math.BigDecimal typecostoTotalServicio279 = tebece0.getCostoTotalServicio();
        String tempResultcostoTotalServicio279 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio279.toString());
        %>
        <%= tempResultcostoTotalServicio279 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typehoraPago281 = tebece0.getHoraPago();
        String tempResulthoraPago281 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago281));
        %>
        <%= tempResulthoraPago281 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typenumeroOperacion283 = tebece0.getNumeroOperacion();
        String tempResultnumeroOperacion283 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion283));
        %>
        <%= tempResultnumeroOperacion283 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionTipoPago285 = tebece0.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago285 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago285));
        %>
        <%= tempResultdescripcionTipoPago285 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoFormaPago287 = tebece0.getCodigoFormaPago();
        String tempResultcodigoFormaPago287 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago287));
        %>
        <%= tempResultcodigoFormaPago287 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoTipoPago289 = tebece0.getCodigoTipoPago();
        String tempResultcodigoTipoPago289 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago289));
        %>
        <%= tempResultcodigoTipoPago289 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typefechaPago291 = tebece0.getFechaPago();
        String tempResultfechaPago291 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago291));
        %>
        <%= tempResultfechaPago291 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanConstitucionEmpresa154mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanConstitucionEmpresa154mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionFormaPago293 = tebece0.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago293 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago293));
        %>
        <%= tempResultdescripcionFormaPago293 %>
        <%
}}%>
</TD>
</TABLE>
<%
}
break;
case 589:
        gotMethod = true;
        String descripcionServicio_72id=  request.getParameter("descripcionServicio732");
        java.lang.String descripcionServicio_72idTemp  = descripcionServicio_72id;
        String codigoUsuario_73id=  request.getParameter("codigoUsuario734");
        java.lang.String codigoUsuario_73idTemp  = codigoUsuario_73id;
        String codigoOficinaRegistral_74id=  request.getParameter("codigoOficinaRegistral736");
        java.lang.String codigoOficinaRegistral_74idTemp  = codigoOficinaRegistral_74id;
        String descripcionOficinaRegistral_75id=  request.getParameter("descripcionOficinaRegistral738");
        java.lang.String descripcionOficinaRegistral_75idTemp  = descripcionOficinaRegistral_75id;
        String codigoActo_76id=  request.getParameter("codigoActo740");
        java.lang.String codigoActo_76idTemp  = codigoActo_76id;
        String cuo_77id=  request.getParameter("cuo742");
        java.lang.String cuo_77idTemp  = cuo_77id;
        String codigoZonaRegistral_78id=  request.getParameter("codigoZonaRegistral744");
        java.lang.String codigoZonaRegistral_78idTemp  = codigoZonaRegistral_78id;
        %>
        <jsp:useBean id="java1util1ArrayList_79id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoPresentante_81id=  request.getParameter("codigoPresentante752");
        java.lang.String codigoPresentante_81idTemp  = codigoPresentante_81id;
        String codigoInstitucion_82id=  request.getParameter("codigoInstitucion754");
        java.lang.String codigoInstitucion_82idTemp  = codigoInstitucion_82id;
        String descripcionInstitucion_83id=  request.getParameter("descripcionInstitucion756");
        java.lang.String descripcionInstitucion_83idTemp  = descripcionInstitucion_83id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_80id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_80id.setCodigoPresentante(codigoPresentante_81idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_80id.setCodigoInstitucion(codigoInstitucion_82idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_80id.setDescripcionInstitucion(descripcionInstitucion_83idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_84id" scope="session" class="java.util.ArrayList" />
        <%
        String anho_85id=  request.getParameter("anho762");
        java.lang.String anho_85idTemp  = anho_85id;
        String descripcionArea_86id=  request.getParameter("descripcionArea764");
        java.lang.String descripcionArea_86idTemp  = descripcionArea_86id;
        String descripcionLibro_87id=  request.getParameter("descripcionLibro766");
        java.lang.String descripcionLibro_87idTemp  = descripcionLibro_87id;
        String documentoEscrituraPublica_89id=  request.getParameter("documentoEscrituraPublica770");
        java.lang.String documentoEscrituraPublica_89idTemp  = documentoEscrituraPublica_89id;
        String nombreArchivo_90id=  request.getParameter("nombreArchivo772");
        java.lang.String nombreArchivo_90idTemp  = nombreArchivo_90id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_88id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_88id.setDocumentoEscrituraPublica(documentoEscrituraPublica_89idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_88id.setNombreArchivo(nombreArchivo_90idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_91id" scope="session" class="java.util.ArrayList" />
        <%
        String descripcionTipoSociedadAnonima_93id=  request.getParameter("descripcionTipoSociedadAnonima780");
        java.lang.String descripcionTipoSociedadAnonima_93idTemp  = descripcionTipoSociedadAnonima_93id;
        String indicadorRUC_94id=  request.getParameter("indicadorRUC782");
        java.lang.String indicadorRUC_94idTemp  = indicadorRUC_94id;
        String indicadorRepresentante_95id=  request.getParameter("indicadorRepresentante784");
        java.lang.String indicadorRepresentante_95idTemp  = indicadorRepresentante_95id;
        String numeroPartida_96id=  request.getParameter("numeroPartida786");
        java.lang.String numeroPartida_96idTemp  = numeroPartida_96id;
        String descripcionTipoSociedad_97id=  request.getParameter("descripcionTipoSociedad788");
        java.lang.String descripcionTipoSociedad_97idTemp  = descripcionTipoSociedad_97id;
        String codigoTipoParticipantePJSUNAT_98id=  request.getParameter("codigoTipoParticipantePJSUNAT790");
        java.lang.String codigoTipoParticipantePJSUNAT_98idTemp  = codigoTipoParticipantePJSUNAT_98id;
        String codigoTipoSociedadAnonima_99id=  request.getParameter("codigoTipoSociedadAnonima792");
        java.lang.String codigoTipoSociedadAnonima_99idTemp  = codigoTipoSociedadAnonima_99id;
        String razonSocial_100id=  request.getParameter("razonSocial794");
        java.lang.String razonSocial_100idTemp  = razonSocial_100id;
        String codigoOficinaRegistral_101id=  request.getParameter("codigoOficinaRegistral796");
        java.lang.String codigoOficinaRegistral_101idTemp  = codigoOficinaRegistral_101id;
        String codigoZonaRegistral_102id=  request.getParameter("codigoZonaRegistral798");
        java.lang.String codigoZonaRegistral_102idTemp  = codigoZonaRegistral_102id;
        String siglas_103id=  request.getParameter("siglas800");
        java.lang.String siglas_103idTemp  = siglas_103id;
        String codigoTipoSociedad_104id=  request.getParameter("codigoTipoSociedad802");
        java.lang.String codigoTipoSociedad_104idTemp  = codigoTipoSociedad_104id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setDescripcionTipoSociedadAnonima(descripcionTipoSociedadAnonima_93idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setIndicadorRUC(indicadorRUC_94idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setIndicadorRepresentante(indicadorRepresentante_95idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setNumeroPartida(numeroPartida_96idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setDescripcionTipoSociedad(descripcionTipoSociedad_97idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setCodigoTipoParticipantePJSUNAT(codigoTipoParticipantePJSUNAT_98idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setCodigoTipoSociedadAnonima(codigoTipoSociedadAnonima_99idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setRazonSocial(razonSocial_100idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setCodigoOficinaRegistral(codigoOficinaRegistral_101idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setCodigoZonaRegistral(codigoZonaRegistral_102idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setSiglas(siglas_103idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id.setCodigoTipoSociedad(codigoTipoSociedad_104idTemp);
        String ipRemota_105id=  request.getParameter("ipRemota804");
        java.lang.String ipRemota_105idTemp  = ipRemota_105id;
        String secuencialOperacion_106id=  request.getParameter("secuencialOperacion806");
        java.lang.String secuencialOperacion_106idTemp  = secuencialOperacion_106id;
        String porcentajeCancelado_108id=  request.getParameter("porcentajeCancelado810");
        java.math.BigDecimal porcentajeCancelado_108idTemp  = new java.math.BigDecimal(porcentajeCancelado_108id);
        String numero_109id=  request.getParameter("numero812");
        int numero_109idTemp  = Integer.parseInt(numero_109id);
        String montoCapital_110id=  request.getParameter("montoCapital814");
        java.math.BigDecimal montoCapital_110idTemp  = new java.math.BigDecimal(montoCapital_110id);
        String descripcionMoneda_111id=  request.getParameter("descripcionMoneda816");
        java.lang.String descripcionMoneda_111idTemp  = descripcionMoneda_111id;
        String valor_112id=  request.getParameter("valor818");
        java.math.BigDecimal valor_112idTemp  = new java.math.BigDecimal(valor_112id);
        String codigoCancelacionCapital_113id=  request.getParameter("codigoCancelacionCapital820");
        java.lang.String codigoCancelacionCapital_113idTemp  = codigoCancelacionCapital_113id;
        String descripcionCancelacionCapital_114id=  request.getParameter("descripcionCancelacionCapital822");
        java.lang.String descripcionCancelacionCapital_114idTemp  = descripcionCancelacionCapital_114id;
        String codigoMoneda_115id=  request.getParameter("codigoMoneda824");
        java.lang.String codigoMoneda_115idTemp  = codigoMoneda_115id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setPorcentajeCancelado(porcentajeCancelado_108idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setNumero(numero_109idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setMontoCapital(montoCapital_110idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setDescripcionMoneda(descripcionMoneda_111idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setValor(valor_112idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setCodigoCancelacionCapital(codigoCancelacionCapital_113idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setDescripcionCancelacionCapital(descripcionCancelacionCapital_114idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id.setCodigoMoneda(codigoMoneda_115idTemp);
        String fechaSolicitud_116id=  request.getParameter("fechaSolicitud826");
        java.lang.String fechaSolicitud_116idTemp  = fechaSolicitud_116id;
        String codigoLibro_117id=  request.getParameter("codigoLibro828");
        java.lang.String codigoLibro_117idTemp  = codigoLibro_117id;
        String codigoArea_118id=  request.getParameter("codigoArea830");
        java.lang.String codigoArea_118idTemp  = codigoArea_118id;
        %>
        <jsp:useBean id="java1util1ArrayList_119id" scope="session" class="java.util.ArrayList" />
        <%
        String numeroTitulo_121id=  request.getParameter("numeroTitulo838");
        java.lang.String numeroTitulo_121idTemp  = numeroTitulo_121id;
        String anhoTitulo_122id=  request.getParameter("anhoTitulo840");
        java.lang.String anhoTitulo_122idTemp  = anhoTitulo_122id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_120id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_120id.setNumeroTitulo(numeroTitulo_121idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_120id.setAnhoTitulo(anhoTitulo_122idTemp);
        String numeroHoja_123id=  request.getParameter("numeroHoja842");
        java.lang.String numeroHoja_123idTemp  = numeroHoja_123id;
        %>
        <jsp:useBean id="java1util1ArrayList_124id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoServicio_125id=  request.getParameter("codigoServicio848");
        java.lang.String codigoServicio_125idTemp  = codigoServicio_125id;
        String descripcionActo_126id=  request.getParameter("descripcionActo850");
        java.lang.String descripcionActo_126idTemp  = descripcionActo_126id;
        String costoTotalServicio_128id=  request.getParameter("costoTotalServicio854");
        java.math.BigDecimal costoTotalServicio_128idTemp  = new java.math.BigDecimal(costoTotalServicio_128id);
        String horaPago_129id=  request.getParameter("horaPago856");
        java.lang.String horaPago_129idTemp  = horaPago_129id;
        String numeroOperacion_130id=  request.getParameter("numeroOperacion858");
        java.lang.String numeroOperacion_130idTemp  = numeroOperacion_130id;
        String descripcionTipoPago_131id=  request.getParameter("descripcionTipoPago860");
        java.lang.String descripcionTipoPago_131idTemp  = descripcionTipoPago_131id;
        String codigoFormaPago_132id=  request.getParameter("codigoFormaPago862");
        java.lang.String codigoFormaPago_132idTemp  = codigoFormaPago_132id;
        String codigoTipoPago_133id=  request.getParameter("codigoTipoPago864");
        java.lang.String codigoTipoPago_133idTemp  = codigoTipoPago_133id;
        String fechaPago_134id=  request.getParameter("fechaPago866");
        java.lang.String fechaPago_134idTemp  = fechaPago_134id;
        String descripcionFormaPago_135id=  request.getParameter("descripcionFormaPago868");
        java.lang.String descripcionFormaPago_135idTemp  = descripcionFormaPago_135id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setCostoTotalServicio(costoTotalServicio_128idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setHoraPago(horaPago_129idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setNumeroOperacion(numeroOperacion_130idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setDescripcionTipoPago(descripcionTipoPago_131idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setCodigoFormaPago(codigoFormaPago_132idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setCodigoTipoPago(codigoTipoPago_133idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setFechaPago(fechaPago_134idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id.setDescripcionFormaPago(descripcionFormaPago_135idTemp);
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setDescripcionServicio(descripcionServicio_72idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoUsuario(codigoUsuario_73idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoOficinaRegistral(codigoOficinaRegistral_74idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setDescripcionOficinaRegistral(descripcionOficinaRegistral_75idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoActo(codigoActo_76idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCuo(cuo_77idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoZonaRegistral(codigoZonaRegistral_78idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setPartidas(java1util1ArrayList_79id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setPresentante(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_80id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setParticipantesPersonaNatural(java1util1ArrayList_84id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setAnho(anho_85idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setDescripcionArea(descripcionArea_86idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setDescripcionLibro(descripcionLibro_87idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setEscrituraPublica(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_88id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setVehiculos(java1util1ArrayList_91id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setPersonaJuridica(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_92id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setIpRemota(ipRemota_105idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setSecuencialOperacion(secuencialOperacion_106idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCapital(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_107id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setFechaSolicitud(fechaSolicitud_116idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoLibro(codigoLibro_117idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoArea(codigoArea_118idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setInstrumentoPublico(java1util1ArrayList_119id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setReservaMercantil(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_120id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setNumeroHoja(numeroHoja_123idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setParticipantesPersonaJuridica(java1util1ArrayList_124id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setCodigoServicio(codigoServicio_125idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setDescripcionActo(descripcionActo_126idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id.setDatosPago(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_127id);
        String datosXML_136id=  request.getParameter("datosXML870");
        java.lang.String datosXML_136idTemp  = datosXML_136id;
        %>
        <jsp:useBean id="org1w3c1dom1Document_137id" scope="session" class="org.w3c.dom.Document" />
        <%
        gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion loadXMLtoBeanReservaNombre589mtemp = sampleSunarpServiceProviderid.loadXMLtoBeanReservaNombre(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_71id,datosXML_136idTemp,org1w3c1dom1Document_137id);
if(loadXMLtoBeanReservaNombre589mtemp == null){
%>
<%=loadXMLtoBeanReservaNombre589mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typedescripcionServicio592 = loadXMLtoBeanReservaNombre589mtemp.getDescripcionServicio();
        String tempResultdescripcionServicio592 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionServicio592));
        %>
        <%= tempResultdescripcionServicio592 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoUsuario594 = loadXMLtoBeanReservaNombre589mtemp.getCodigoUsuario();
        String tempResultcodigoUsuario594 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario594));
        %>
        <%= tempResultcodigoUsuario594 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoOficinaRegistral596 = loadXMLtoBeanReservaNombre589mtemp.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral596 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral596));
        %>
        <%= tempResultcodigoOficinaRegistral596 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typedescripcionOficinaRegistral598 = loadXMLtoBeanReservaNombre589mtemp.getDescripcionOficinaRegistral();
        String tempResultdescripcionOficinaRegistral598 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionOficinaRegistral598));
        %>
        <%= tempResultdescripcionOficinaRegistral598 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoActo600 = loadXMLtoBeanReservaNombre589mtemp.getCodigoActo();
        String tempResultcodigoActo600 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoActo600));
        %>
        <%= tempResultcodigoActo600 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecuo602 = loadXMLtoBeanReservaNombre589mtemp.getCuo();
        String tempResultcuo602 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo602));
        %>
        <%= tempResultcuo602 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoZonaRegistral604 = loadXMLtoBeanReservaNombre589mtemp.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral604 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral604));
        %>
        <%= tempResultcodigoZonaRegistral604 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanReservaNombre589mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoPresentante612 = tebece0.getCodigoPresentante();
        String tempResultcodigoPresentante612 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoPresentante612));
        %>
        <%= tempResultcodigoPresentante612 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanReservaNombre589mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoInstitucion614 = tebece0.getCodigoInstitucion();
        String tempResultcodigoInstitucion614 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoInstitucion614));
        %>
        <%= tempResultcodigoInstitucion614 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanReservaNombre589mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typedescripcionInstitucion616 = tebece0.getDescripcionInstitucion();
        String tempResultdescripcionInstitucion616 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionInstitucion616));
        %>
        <%= tempResultdescripcionInstitucion616 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typeanho622 = loadXMLtoBeanReservaNombre589mtemp.getAnho();
        String tempResultanho622 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanho622));
        %>
        <%= tempResultanho622 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typedescripcionArea624 = loadXMLtoBeanReservaNombre589mtemp.getDescripcionArea();
        String tempResultdescripcionArea624 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionArea624));
        %>
        <%= tempResultdescripcionArea624 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typedescripcionLibro626 = loadXMLtoBeanReservaNombre589mtemp.getDescripcionLibro();
        String tempResultdescripcionLibro626 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionLibro626));
        %>
        <%= tempResultdescripcionLibro626 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanReservaNombre589mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typedocumentoEscrituraPublica630 = tebece0.getDocumentoEscrituraPublica();
        String tempResultdocumentoEscrituraPublica630 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedocumentoEscrituraPublica630));
        %>
        <%= tempResultdocumentoEscrituraPublica630 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanReservaNombre589mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typenombreArchivo632 = tebece0.getNombreArchivo();
        String tempResultnombreArchivo632 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombreArchivo632));
        %>
        <%= tempResultnombreArchivo632 %>
        <%
}}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedadAnonima640 = tebece0.getDescripcionTipoSociedadAnonima();
        String tempResultdescripcionTipoSociedadAnonima640 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedadAnonima640));
        %>
        <%= tempResultdescripcionTipoSociedadAnonima640 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRUC642 = tebece0.getIndicadorRUC();
        String tempResultindicadorRUC642 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRUC642));
        %>
        <%= tempResultindicadorRUC642 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRepresentante644 = tebece0.getIndicadorRepresentante();
        String tempResultindicadorRepresentante644 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRepresentante644));
        %>
        <%= tempResultindicadorRepresentante644 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typenumeroPartida646 = tebece0.getNumeroPartida();
        String tempResultnumeroPartida646 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroPartida646));
        %>
        <%= tempResultnumeroPartida646 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedad648 = tebece0.getDescripcionTipoSociedad();
        String tempResultdescripcionTipoSociedad648 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedad648));
        %>
        <%= tempResultdescripcionTipoSociedad648 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoParticipantePJSUNAT650 = tebece0.getCodigoTipoParticipantePJSUNAT();
        String tempResultcodigoTipoParticipantePJSUNAT650 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoParticipantePJSUNAT650));
        %>
        <%= tempResultcodigoTipoParticipantePJSUNAT650 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedadAnonima652 = tebece0.getCodigoTipoSociedadAnonima();
        String tempResultcodigoTipoSociedadAnonima652 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedadAnonima652));
        %>
        <%= tempResultcodigoTipoSociedadAnonima652 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typerazonSocial654 = tebece0.getRazonSocial();
        String tempResultrazonSocial654 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazonSocial654));
        %>
        <%= tempResultrazonSocial654 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoOficinaRegistral656 = tebece0.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral656 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral656));
        %>
        <%= tempResultcodigoOficinaRegistral656 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoZonaRegistral658 = tebece0.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral658 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral658));
        %>
        <%= tempResultcodigoZonaRegistral658 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typesiglas660 = tebece0.getSiglas();
        String tempResultsiglas660 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesiglas660));
        %>
        <%= tempResultsiglas660 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanReservaNombre589mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedad662 = tebece0.getCodigoTipoSociedad();
        String tempResultcodigoTipoSociedad662 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedad662));
        %>
        <%= tempResultcodigoTipoSociedad662 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typeipRemota664 = loadXMLtoBeanReservaNombre589mtemp.getIpRemota();
        String tempResultipRemota664 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeipRemota664));
        %>
        <%= tempResultipRemota664 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typesecuencialOperacion666 = loadXMLtoBeanReservaNombre589mtemp.getSecuencialOperacion();
        String tempResultsecuencialOperacion666 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesecuencialOperacion666));
        %>
        <%= tempResultsecuencialOperacion666 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typeporcentajeCancelado670 = tebece0.getPorcentajeCancelado();
        String tempResultporcentajeCancelado670 = org.eclipse.jst.ws.util.JspUtils.markup(typeporcentajeCancelado670.toString());
        %>
        <%= tempResultporcentajeCancelado670 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
%>
<%=tebece0.getNumero()
%><%}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typemontoCapital674 = tebece0.getMontoCapital();
        String tempResultmontoCapital674 = org.eclipse.jst.ws.util.JspUtils.markup(typemontoCapital674.toString());
        %>
        <%= tempResultmontoCapital674 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionMoneda676 = tebece0.getDescripcionMoneda();
        String tempResultdescripcionMoneda676 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionMoneda676));
        %>
        <%= tempResultdescripcionMoneda676 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typevalor678 = tebece0.getValor();
        String tempResultvalor678 = org.eclipse.jst.ws.util.JspUtils.markup(typevalor678.toString());
        %>
        <%= tempResultvalor678 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoCancelacionCapital680 = tebece0.getCodigoCancelacionCapital();
        String tempResultcodigoCancelacionCapital680 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoCancelacionCapital680));
        %>
        <%= tempResultcodigoCancelacionCapital680 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionCancelacionCapital682 = tebece0.getDescripcionCancelacionCapital();
        String tempResultdescripcionCancelacionCapital682 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionCancelacionCapital682));
        %>
        <%= tempResultdescripcionCancelacionCapital682 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanReservaNombre589mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoMoneda684 = tebece0.getCodigoMoneda();
        String tempResultcodigoMoneda684 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoMoneda684));
        %>
        <%= tempResultcodigoMoneda684 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typefechaSolicitud686 = loadXMLtoBeanReservaNombre589mtemp.getFechaSolicitud();
        String tempResultfechaSolicitud686 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaSolicitud686));
        %>
        <%= tempResultfechaSolicitud686 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoLibro688 = loadXMLtoBeanReservaNombre589mtemp.getCodigoLibro();
        String tempResultcodigoLibro688 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoLibro688));
        %>
        <%= tempResultcodigoLibro688 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoArea690 = loadXMLtoBeanReservaNombre589mtemp.getCodigoArea();
        String tempResultcodigoArea690 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoArea690));
        %>
        <%= tempResultcodigoArea690 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanReservaNombre589mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typenumeroTitulo698 = tebece0.getNumeroTitulo();
        String tempResultnumeroTitulo698 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroTitulo698));
        %>
        <%= tempResultnumeroTitulo698 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanReservaNombre589mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typeanhoTitulo700 = tebece0.getAnhoTitulo();
        String tempResultanhoTitulo700 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanhoTitulo700));
        %>
        <%= tempResultanhoTitulo700 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typenumeroHoja702 = loadXMLtoBeanReservaNombre589mtemp.getNumeroHoja();
        String tempResultnumeroHoja702 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroHoja702));
        %>
        <%= tempResultnumeroHoja702 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typecodigoServicio708 = loadXMLtoBeanReservaNombre589mtemp.getCodigoServicio();
        String tempResultcodigoServicio708 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoServicio708));
        %>
        <%= tempResultcodigoServicio708 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
java.lang.String typedescripcionActo710 = loadXMLtoBeanReservaNombre589mtemp.getDescripcionActo();
        String tempResultdescripcionActo710 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionActo710));
        %>
        <%= tempResultdescripcionActo710 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.math.BigDecimal typecostoTotalServicio714 = tebece0.getCostoTotalServicio();
        String tempResultcostoTotalServicio714 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio714.toString());
        %>
        <%= tempResultcostoTotalServicio714 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typehoraPago716 = tebece0.getHoraPago();
        String tempResulthoraPago716 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago716));
        %>
        <%= tempResulthoraPago716 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typenumeroOperacion718 = tebece0.getNumeroOperacion();
        String tempResultnumeroOperacion718 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion718));
        %>
        <%= tempResultnumeroOperacion718 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionTipoPago720 = tebece0.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago720 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago720));
        %>
        <%= tempResultdescripcionTipoPago720 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoFormaPago722 = tebece0.getCodigoFormaPago();
        String tempResultcodigoFormaPago722 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago722));
        %>
        <%= tempResultcodigoFormaPago722 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoTipoPago724 = tebece0.getCodigoTipoPago();
        String tempResultcodigoTipoPago724 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago724));
        %>
        <%= tempResultcodigoTipoPago724 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typefechaPago726 = tebece0.getFechaPago();
        String tempResultfechaPago726 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago726));
        %>
        <%= tempResultfechaPago726 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanReservaNombre589mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanReservaNombre589mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionFormaPago728 = tebece0.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago728 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago728));
        %>
        <%= tempResultdescripcionFormaPago728 %>
        <%
}}%>
</TD>
</TABLE>
<%
}
break;
case 1024:
        gotMethod = true;
        String descripcionServicio_139id=  request.getParameter("descripcionServicio1167");
        java.lang.String descripcionServicio_139idTemp  = descripcionServicio_139id;
        String codigoUsuario_140id=  request.getParameter("codigoUsuario1169");
        java.lang.String codigoUsuario_140idTemp  = codigoUsuario_140id;
        String codigoOficinaRegistral_141id=  request.getParameter("codigoOficinaRegistral1171");
        java.lang.String codigoOficinaRegistral_141idTemp  = codigoOficinaRegistral_141id;
        String descripcionOficinaRegistral_142id=  request.getParameter("descripcionOficinaRegistral1173");
        java.lang.String descripcionOficinaRegistral_142idTemp  = descripcionOficinaRegistral_142id;
        String codigoActo_143id=  request.getParameter("codigoActo1175");
        java.lang.String codigoActo_143idTemp  = codigoActo_143id;
        String cuo_144id=  request.getParameter("cuo1177");
        java.lang.String cuo_144idTemp  = cuo_144id;
        String codigoZonaRegistral_145id=  request.getParameter("codigoZonaRegistral1179");
        java.lang.String codigoZonaRegistral_145idTemp  = codigoZonaRegistral_145id;
        %>
        <jsp:useBean id="java1util1ArrayList_146id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoPresentante_148id=  request.getParameter("codigoPresentante1187");
        java.lang.String codigoPresentante_148idTemp  = codigoPresentante_148id;
        String codigoInstitucion_149id=  request.getParameter("codigoInstitucion1189");
        java.lang.String codigoInstitucion_149idTemp  = codigoInstitucion_149id;
        String descripcionInstitucion_150id=  request.getParameter("descripcionInstitucion1191");
        java.lang.String descripcionInstitucion_150idTemp  = descripcionInstitucion_150id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_147id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_147id.setCodigoPresentante(codigoPresentante_148idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_147id.setCodigoInstitucion(codigoInstitucion_149idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_147id.setDescripcionInstitucion(descripcionInstitucion_150idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_151id" scope="session" class="java.util.ArrayList" />
        <%
        String anho_152id=  request.getParameter("anho1197");
        java.lang.String anho_152idTemp  = anho_152id;
        String descripcionArea_153id=  request.getParameter("descripcionArea1199");
        java.lang.String descripcionArea_153idTemp  = descripcionArea_153id;
        String descripcionLibro_154id=  request.getParameter("descripcionLibro1201");
        java.lang.String descripcionLibro_154idTemp  = descripcionLibro_154id;
        String documentoEscrituraPublica_156id=  request.getParameter("documentoEscrituraPublica1205");
        java.lang.String documentoEscrituraPublica_156idTemp  = documentoEscrituraPublica_156id;
        String nombreArchivo_157id=  request.getParameter("nombreArchivo1207");
        java.lang.String nombreArchivo_157idTemp  = nombreArchivo_157id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_155id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_155id.setDocumentoEscrituraPublica(documentoEscrituraPublica_156idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_155id.setNombreArchivo(nombreArchivo_157idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_158id" scope="session" class="java.util.ArrayList" />
        <%
        String descripcionTipoSociedadAnonima_160id=  request.getParameter("descripcionTipoSociedadAnonima1215");
        java.lang.String descripcionTipoSociedadAnonima_160idTemp  = descripcionTipoSociedadAnonima_160id;
        String indicadorRUC_161id=  request.getParameter("indicadorRUC1217");
        java.lang.String indicadorRUC_161idTemp  = indicadorRUC_161id;
        String indicadorRepresentante_162id=  request.getParameter("indicadorRepresentante1219");
        java.lang.String indicadorRepresentante_162idTemp  = indicadorRepresentante_162id;
        String numeroPartida_163id=  request.getParameter("numeroPartida1221");
        java.lang.String numeroPartida_163idTemp  = numeroPartida_163id;
        String descripcionTipoSociedad_164id=  request.getParameter("descripcionTipoSociedad1223");
        java.lang.String descripcionTipoSociedad_164idTemp  = descripcionTipoSociedad_164id;
        String codigoTipoParticipantePJSUNAT_165id=  request.getParameter("codigoTipoParticipantePJSUNAT1225");
        java.lang.String codigoTipoParticipantePJSUNAT_165idTemp  = codigoTipoParticipantePJSUNAT_165id;
        String codigoTipoSociedadAnonima_166id=  request.getParameter("codigoTipoSociedadAnonima1227");
        java.lang.String codigoTipoSociedadAnonima_166idTemp  = codigoTipoSociedadAnonima_166id;
        String razonSocial_167id=  request.getParameter("razonSocial1229");
        java.lang.String razonSocial_167idTemp  = razonSocial_167id;
        String codigoOficinaRegistral_168id=  request.getParameter("codigoOficinaRegistral1231");
        java.lang.String codigoOficinaRegistral_168idTemp  = codigoOficinaRegistral_168id;
        String codigoZonaRegistral_169id=  request.getParameter("codigoZonaRegistral1233");
        java.lang.String codigoZonaRegistral_169idTemp  = codigoZonaRegistral_169id;
        String siglas_170id=  request.getParameter("siglas1235");
        java.lang.String siglas_170idTemp  = siglas_170id;
        String codigoTipoSociedad_171id=  request.getParameter("codigoTipoSociedad1237");
        java.lang.String codigoTipoSociedad_171idTemp  = codigoTipoSociedad_171id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setDescripcionTipoSociedadAnonima(descripcionTipoSociedadAnonima_160idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setIndicadorRUC(indicadorRUC_161idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setIndicadorRepresentante(indicadorRepresentante_162idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setNumeroPartida(numeroPartida_163idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setDescripcionTipoSociedad(descripcionTipoSociedad_164idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setCodigoTipoParticipantePJSUNAT(codigoTipoParticipantePJSUNAT_165idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setCodigoTipoSociedadAnonima(codigoTipoSociedadAnonima_166idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setRazonSocial(razonSocial_167idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setCodigoOficinaRegistral(codigoOficinaRegistral_168idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setCodigoZonaRegistral(codigoZonaRegistral_169idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setSiglas(siglas_170idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id.setCodigoTipoSociedad(codigoTipoSociedad_171idTemp);
        String ipRemota_172id=  request.getParameter("ipRemota1239");
        java.lang.String ipRemota_172idTemp  = ipRemota_172id;
        String secuencialOperacion_173id=  request.getParameter("secuencialOperacion1241");
        java.lang.String secuencialOperacion_173idTemp  = secuencialOperacion_173id;
        String porcentajeCancelado_175id=  request.getParameter("porcentajeCancelado1245");
        java.math.BigDecimal porcentajeCancelado_175idTemp  = new java.math.BigDecimal(porcentajeCancelado_175id);
        String numero_176id=  request.getParameter("numero1247");
        int numero_176idTemp  = Integer.parseInt(numero_176id);
        String montoCapital_177id=  request.getParameter("montoCapital1249");
        java.math.BigDecimal montoCapital_177idTemp  = new java.math.BigDecimal(montoCapital_177id);
        String descripcionMoneda_178id=  request.getParameter("descripcionMoneda1251");
        java.lang.String descripcionMoneda_178idTemp  = descripcionMoneda_178id;
        String valor_179id=  request.getParameter("valor1253");
        java.math.BigDecimal valor_179idTemp  = new java.math.BigDecimal(valor_179id);
        String codigoCancelacionCapital_180id=  request.getParameter("codigoCancelacionCapital1255");
        java.lang.String codigoCancelacionCapital_180idTemp  = codigoCancelacionCapital_180id;
        String descripcionCancelacionCapital_181id=  request.getParameter("descripcionCancelacionCapital1257");
        java.lang.String descripcionCancelacionCapital_181idTemp  = descripcionCancelacionCapital_181id;
        String codigoMoneda_182id=  request.getParameter("codigoMoneda1259");
        java.lang.String codigoMoneda_182idTemp  = codigoMoneda_182id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setPorcentajeCancelado(porcentajeCancelado_175idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setNumero(numero_176idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setMontoCapital(montoCapital_177idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setDescripcionMoneda(descripcionMoneda_178idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setValor(valor_179idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setCodigoCancelacionCapital(codigoCancelacionCapital_180idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setDescripcionCancelacionCapital(descripcionCancelacionCapital_181idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id.setCodigoMoneda(codigoMoneda_182idTemp);
        String fechaSolicitud_183id=  request.getParameter("fechaSolicitud1261");
        java.lang.String fechaSolicitud_183idTemp  = fechaSolicitud_183id;
        String codigoLibro_184id=  request.getParameter("codigoLibro1263");
        java.lang.String codigoLibro_184idTemp  = codigoLibro_184id;
        String codigoArea_185id=  request.getParameter("codigoArea1265");
        java.lang.String codigoArea_185idTemp  = codigoArea_185id;
        %>
        <jsp:useBean id="java1util1ArrayList_186id" scope="session" class="java.util.ArrayList" />
        <%
        String numeroTitulo_188id=  request.getParameter("numeroTitulo1273");
        java.lang.String numeroTitulo_188idTemp  = numeroTitulo_188id;
        String anhoTitulo_189id=  request.getParameter("anhoTitulo1275");
        java.lang.String anhoTitulo_189idTemp  = anhoTitulo_189id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_187id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_187id.setNumeroTitulo(numeroTitulo_188idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_187id.setAnhoTitulo(anhoTitulo_189idTemp);
        String numeroHoja_190id=  request.getParameter("numeroHoja1277");
        java.lang.String numeroHoja_190idTemp  = numeroHoja_190id;
        %>
        <jsp:useBean id="java1util1ArrayList_191id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoServicio_192id=  request.getParameter("codigoServicio1283");
        java.lang.String codigoServicio_192idTemp  = codigoServicio_192id;
        String descripcionActo_193id=  request.getParameter("descripcionActo1285");
        java.lang.String descripcionActo_193idTemp  = descripcionActo_193id;
        String costoTotalServicio_195id=  request.getParameter("costoTotalServicio1289");
        java.math.BigDecimal costoTotalServicio_195idTemp  = new java.math.BigDecimal(costoTotalServicio_195id);
        String horaPago_196id=  request.getParameter("horaPago1291");
        java.lang.String horaPago_196idTemp  = horaPago_196id;
        String numeroOperacion_197id=  request.getParameter("numeroOperacion1293");
        java.lang.String numeroOperacion_197idTemp  = numeroOperacion_197id;
        String descripcionTipoPago_198id=  request.getParameter("descripcionTipoPago1295");
        java.lang.String descripcionTipoPago_198idTemp  = descripcionTipoPago_198id;
        String codigoFormaPago_199id=  request.getParameter("codigoFormaPago1297");
        java.lang.String codigoFormaPago_199idTemp  = codigoFormaPago_199id;
        String codigoTipoPago_200id=  request.getParameter("codigoTipoPago1299");
        java.lang.String codigoTipoPago_200idTemp  = codigoTipoPago_200id;
        String fechaPago_201id=  request.getParameter("fechaPago1301");
        java.lang.String fechaPago_201idTemp  = fechaPago_201id;
        String descripcionFormaPago_202id=  request.getParameter("descripcionFormaPago1303");
        java.lang.String descripcionFormaPago_202idTemp  = descripcionFormaPago_202id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setCostoTotalServicio(costoTotalServicio_195idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setHoraPago(horaPago_196idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setNumeroOperacion(numeroOperacion_197idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setDescripcionTipoPago(descripcionTipoPago_198idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setCodigoFormaPago(codigoFormaPago_199idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setCodigoTipoPago(codigoTipoPago_200idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setFechaPago(fechaPago_201idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id.setDescripcionFormaPago(descripcionFormaPago_202idTemp);
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setDescripcionServicio(descripcionServicio_139idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoUsuario(codigoUsuario_140idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoOficinaRegistral(codigoOficinaRegistral_141idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setDescripcionOficinaRegistral(descripcionOficinaRegistral_142idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoActo(codigoActo_143idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCuo(cuo_144idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoZonaRegistral(codigoZonaRegistral_145idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setPartidas(java1util1ArrayList_146id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setPresentante(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_147id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setParticipantesPersonaNatural(java1util1ArrayList_151id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setAnho(anho_152idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setDescripcionArea(descripcionArea_153idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setDescripcionLibro(descripcionLibro_154idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setEscrituraPublica(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_155id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setVehiculos(java1util1ArrayList_158id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setPersonaJuridica(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_159id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setIpRemota(ipRemota_172idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setSecuencialOperacion(secuencialOperacion_173idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCapital(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_174id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setFechaSolicitud(fechaSolicitud_183idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoLibro(codigoLibro_184idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoArea(codigoArea_185idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setInstrumentoPublico(java1util1ArrayList_186id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setReservaMercantil(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_187id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setNumeroHoja(numeroHoja_190idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setParticipantesPersonaJuridica(java1util1ArrayList_191id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setCodigoServicio(codigoServicio_192idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setDescripcionActo(descripcionActo_193idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id.setDatosPago(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_194id);
        String datosXML_203id=  request.getParameter("datosXML1305");
        java.lang.String datosXML_203idTemp  = datosXML_203id;
        %>
        <jsp:useBean id="org1w3c1dom1Document_204id" scope="session" class="org.w3c.dom.Document" />
        <%
        gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion loadXMLtoBeanBloqueoInmueble1024mtemp = sampleSunarpServiceProviderid.loadXMLtoBeanBloqueoInmueble(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_138id,datosXML_203idTemp,org1w3c1dom1Document_204id);
if(loadXMLtoBeanBloqueoInmueble1024mtemp == null){
%>
<%=loadXMLtoBeanBloqueoInmueble1024mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typedescripcionServicio1027 = loadXMLtoBeanBloqueoInmueble1024mtemp.getDescripcionServicio();
        String tempResultdescripcionServicio1027 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionServicio1027));
        %>
        <%= tempResultdescripcionServicio1027 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoUsuario1029 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoUsuario();
        String tempResultcodigoUsuario1029 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario1029));
        %>
        <%= tempResultcodigoUsuario1029 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoOficinaRegistral1031 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral1031 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral1031));
        %>
        <%= tempResultcodigoOficinaRegistral1031 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typedescripcionOficinaRegistral1033 = loadXMLtoBeanBloqueoInmueble1024mtemp.getDescripcionOficinaRegistral();
        String tempResultdescripcionOficinaRegistral1033 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionOficinaRegistral1033));
        %>
        <%= tempResultdescripcionOficinaRegistral1033 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoActo1035 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoActo();
        String tempResultcodigoActo1035 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoActo1035));
        %>
        <%= tempResultcodigoActo1035 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecuo1037 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCuo();
        String tempResultcuo1037 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo1037));
        %>
        <%= tempResultcuo1037 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoZonaRegistral1039 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral1039 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral1039));
        %>
        <%= tempResultcodigoZonaRegistral1039 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoPresentante1047 = tebece0.getCodigoPresentante();
        String tempResultcodigoPresentante1047 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoPresentante1047));
        %>
        <%= tempResultcodigoPresentante1047 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoInstitucion1049 = tebece0.getCodigoInstitucion();
        String tempResultcodigoInstitucion1049 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoInstitucion1049));
        %>
        <%= tempResultcodigoInstitucion1049 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typedescripcionInstitucion1051 = tebece0.getDescripcionInstitucion();
        String tempResultdescripcionInstitucion1051 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionInstitucion1051));
        %>
        <%= tempResultdescripcionInstitucion1051 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typeanho1057 = loadXMLtoBeanBloqueoInmueble1024mtemp.getAnho();
        String tempResultanho1057 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanho1057));
        %>
        <%= tempResultanho1057 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typedescripcionArea1059 = loadXMLtoBeanBloqueoInmueble1024mtemp.getDescripcionArea();
        String tempResultdescripcionArea1059 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionArea1059));
        %>
        <%= tempResultdescripcionArea1059 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typedescripcionLibro1061 = loadXMLtoBeanBloqueoInmueble1024mtemp.getDescripcionLibro();
        String tempResultdescripcionLibro1061 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionLibro1061));
        %>
        <%= tempResultdescripcionLibro1061 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typedocumentoEscrituraPublica1065 = tebece0.getDocumentoEscrituraPublica();
        String tempResultdocumentoEscrituraPublica1065 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedocumentoEscrituraPublica1065));
        %>
        <%= tempResultdocumentoEscrituraPublica1065 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typenombreArchivo1067 = tebece0.getNombreArchivo();
        String tempResultnombreArchivo1067 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombreArchivo1067));
        %>
        <%= tempResultnombreArchivo1067 %>
        <%
}}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedadAnonima1075 = tebece0.getDescripcionTipoSociedadAnonima();
        String tempResultdescripcionTipoSociedadAnonima1075 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedadAnonima1075));
        %>
        <%= tempResultdescripcionTipoSociedadAnonima1075 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRUC1077 = tebece0.getIndicadorRUC();
        String tempResultindicadorRUC1077 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRUC1077));
        %>
        <%= tempResultindicadorRUC1077 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRepresentante1079 = tebece0.getIndicadorRepresentante();
        String tempResultindicadorRepresentante1079 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRepresentante1079));
        %>
        <%= tempResultindicadorRepresentante1079 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typenumeroPartida1081 = tebece0.getNumeroPartida();
        String tempResultnumeroPartida1081 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroPartida1081));
        %>
        <%= tempResultnumeroPartida1081 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedad1083 = tebece0.getDescripcionTipoSociedad();
        String tempResultdescripcionTipoSociedad1083 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedad1083));
        %>
        <%= tempResultdescripcionTipoSociedad1083 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoParticipantePJSUNAT1085 = tebece0.getCodigoTipoParticipantePJSUNAT();
        String tempResultcodigoTipoParticipantePJSUNAT1085 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoParticipantePJSUNAT1085));
        %>
        <%= tempResultcodigoTipoParticipantePJSUNAT1085 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedadAnonima1087 = tebece0.getCodigoTipoSociedadAnonima();
        String tempResultcodigoTipoSociedadAnonima1087 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedadAnonima1087));
        %>
        <%= tempResultcodigoTipoSociedadAnonima1087 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typerazonSocial1089 = tebece0.getRazonSocial();
        String tempResultrazonSocial1089 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazonSocial1089));
        %>
        <%= tempResultrazonSocial1089 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoOficinaRegistral1091 = tebece0.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral1091 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral1091));
        %>
        <%= tempResultcodigoOficinaRegistral1091 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoZonaRegistral1093 = tebece0.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral1093 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral1093));
        %>
        <%= tempResultcodigoZonaRegistral1093 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typesiglas1095 = tebece0.getSiglas();
        String tempResultsiglas1095 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesiglas1095));
        %>
        <%= tempResultsiglas1095 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedad1097 = tebece0.getCodigoTipoSociedad();
        String tempResultcodigoTipoSociedad1097 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedad1097));
        %>
        <%= tempResultcodigoTipoSociedad1097 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typeipRemota1099 = loadXMLtoBeanBloqueoInmueble1024mtemp.getIpRemota();
        String tempResultipRemota1099 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeipRemota1099));
        %>
        <%= tempResultipRemota1099 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typesecuencialOperacion1101 = loadXMLtoBeanBloqueoInmueble1024mtemp.getSecuencialOperacion();
        String tempResultsecuencialOperacion1101 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesecuencialOperacion1101));
        %>
        <%= tempResultsecuencialOperacion1101 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typeporcentajeCancelado1105 = tebece0.getPorcentajeCancelado();
        String tempResultporcentajeCancelado1105 = org.eclipse.jst.ws.util.JspUtils.markup(typeporcentajeCancelado1105.toString());
        %>
        <%= tempResultporcentajeCancelado1105 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
%>
<%=tebece0.getNumero()
%><%}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typemontoCapital1109 = tebece0.getMontoCapital();
        String tempResultmontoCapital1109 = org.eclipse.jst.ws.util.JspUtils.markup(typemontoCapital1109.toString());
        %>
        <%= tempResultmontoCapital1109 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionMoneda1111 = tebece0.getDescripcionMoneda();
        String tempResultdescripcionMoneda1111 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionMoneda1111));
        %>
        <%= tempResultdescripcionMoneda1111 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typevalor1113 = tebece0.getValor();
        String tempResultvalor1113 = org.eclipse.jst.ws.util.JspUtils.markup(typevalor1113.toString());
        %>
        <%= tempResultvalor1113 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoCancelacionCapital1115 = tebece0.getCodigoCancelacionCapital();
        String tempResultcodigoCancelacionCapital1115 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoCancelacionCapital1115));
        %>
        <%= tempResultcodigoCancelacionCapital1115 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionCancelacionCapital1117 = tebece0.getDescripcionCancelacionCapital();
        String tempResultdescripcionCancelacionCapital1117 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionCancelacionCapital1117));
        %>
        <%= tempResultdescripcionCancelacionCapital1117 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoMoneda1119 = tebece0.getCodigoMoneda();
        String tempResultcodigoMoneda1119 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoMoneda1119));
        %>
        <%= tempResultcodigoMoneda1119 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typefechaSolicitud1121 = loadXMLtoBeanBloqueoInmueble1024mtemp.getFechaSolicitud();
        String tempResultfechaSolicitud1121 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaSolicitud1121));
        %>
        <%= tempResultfechaSolicitud1121 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoLibro1123 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoLibro();
        String tempResultcodigoLibro1123 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoLibro1123));
        %>
        <%= tempResultcodigoLibro1123 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoArea1125 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoArea();
        String tempResultcodigoArea1125 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoArea1125));
        %>
        <%= tempResultcodigoArea1125 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typenumeroTitulo1133 = tebece0.getNumeroTitulo();
        String tempResultnumeroTitulo1133 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroTitulo1133));
        %>
        <%= tempResultnumeroTitulo1133 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typeanhoTitulo1135 = tebece0.getAnhoTitulo();
        String tempResultanhoTitulo1135 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanhoTitulo1135));
        %>
        <%= tempResultanhoTitulo1135 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typenumeroHoja1137 = loadXMLtoBeanBloqueoInmueble1024mtemp.getNumeroHoja();
        String tempResultnumeroHoja1137 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroHoja1137));
        %>
        <%= tempResultnumeroHoja1137 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typecodigoServicio1143 = loadXMLtoBeanBloqueoInmueble1024mtemp.getCodigoServicio();
        String tempResultcodigoServicio1143 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoServicio1143));
        %>
        <%= tempResultcodigoServicio1143 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
java.lang.String typedescripcionActo1145 = loadXMLtoBeanBloqueoInmueble1024mtemp.getDescripcionActo();
        String tempResultdescripcionActo1145 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionActo1145));
        %>
        <%= tempResultdescripcionActo1145 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.math.BigDecimal typecostoTotalServicio1149 = tebece0.getCostoTotalServicio();
        String tempResultcostoTotalServicio1149 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio1149.toString());
        %>
        <%= tempResultcostoTotalServicio1149 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typehoraPago1151 = tebece0.getHoraPago();
        String tempResulthoraPago1151 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago1151));
        %>
        <%= tempResulthoraPago1151 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typenumeroOperacion1153 = tebece0.getNumeroOperacion();
        String tempResultnumeroOperacion1153 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion1153));
        %>
        <%= tempResultnumeroOperacion1153 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionTipoPago1155 = tebece0.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago1155 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago1155));
        %>
        <%= tempResultdescripcionTipoPago1155 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoFormaPago1157 = tebece0.getCodigoFormaPago();
        String tempResultcodigoFormaPago1157 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago1157));
        %>
        <%= tempResultcodigoFormaPago1157 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoTipoPago1159 = tebece0.getCodigoTipoPago();
        String tempResultcodigoTipoPago1159 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago1159));
        %>
        <%= tempResultcodigoTipoPago1159 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typefechaPago1161 = tebece0.getFechaPago();
        String tempResultfechaPago1161 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago1161));
        %>
        <%= tempResultfechaPago1161 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBloqueoInmueble1024mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBloqueoInmueble1024mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionFormaPago1163 = tebece0.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago1163 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago1163));
        %>
        <%= tempResultdescripcionFormaPago1163 %>
        <%
}}%>
</TD>
</TABLE>
<%
}
break;
case 1459:
        gotMethod = true;
        String descripcionServicio_206id=  request.getParameter("descripcionServicio1602");
        java.lang.String descripcionServicio_206idTemp  = descripcionServicio_206id;
        String codigoUsuario_207id=  request.getParameter("codigoUsuario1604");
        java.lang.String codigoUsuario_207idTemp  = codigoUsuario_207id;
        String codigoOficinaRegistral_208id=  request.getParameter("codigoOficinaRegistral1606");
        java.lang.String codigoOficinaRegistral_208idTemp  = codigoOficinaRegistral_208id;
        String descripcionOficinaRegistral_209id=  request.getParameter("descripcionOficinaRegistral1608");
        java.lang.String descripcionOficinaRegistral_209idTemp  = descripcionOficinaRegistral_209id;
        String codigoActo_210id=  request.getParameter("codigoActo1610");
        java.lang.String codigoActo_210idTemp  = codigoActo_210id;
        String cuo_211id=  request.getParameter("cuo1612");
        java.lang.String cuo_211idTemp  = cuo_211id;
        String codigoZonaRegistral_212id=  request.getParameter("codigoZonaRegistral1614");
        java.lang.String codigoZonaRegistral_212idTemp  = codigoZonaRegistral_212id;
        %>
        <jsp:useBean id="java1util1ArrayList_213id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoPresentante_215id=  request.getParameter("codigoPresentante1622");
        java.lang.String codigoPresentante_215idTemp  = codigoPresentante_215id;
        String codigoInstitucion_216id=  request.getParameter("codigoInstitucion1624");
        java.lang.String codigoInstitucion_216idTemp  = codigoInstitucion_216id;
        String descripcionInstitucion_217id=  request.getParameter("descripcionInstitucion1626");
        java.lang.String descripcionInstitucion_217idTemp  = descripcionInstitucion_217id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_214id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_214id.setCodigoPresentante(codigoPresentante_215idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_214id.setCodigoInstitucion(codigoInstitucion_216idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_214id.setDescripcionInstitucion(descripcionInstitucion_217idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_218id" scope="session" class="java.util.ArrayList" />
        <%
        String anho_219id=  request.getParameter("anho1632");
        java.lang.String anho_219idTemp  = anho_219id;
        String descripcionArea_220id=  request.getParameter("descripcionArea1634");
        java.lang.String descripcionArea_220idTemp  = descripcionArea_220id;
        String descripcionLibro_221id=  request.getParameter("descripcionLibro1636");
        java.lang.String descripcionLibro_221idTemp  = descripcionLibro_221id;
        String documentoEscrituraPublica_223id=  request.getParameter("documentoEscrituraPublica1640");
        java.lang.String documentoEscrituraPublica_223idTemp  = documentoEscrituraPublica_223id;
        String nombreArchivo_224id=  request.getParameter("nombreArchivo1642");
        java.lang.String nombreArchivo_224idTemp  = nombreArchivo_224id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_222id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_222id.setDocumentoEscrituraPublica(documentoEscrituraPublica_223idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_222id.setNombreArchivo(nombreArchivo_224idTemp);
        %>
        <jsp:useBean id="java1util1ArrayList_225id" scope="session" class="java.util.ArrayList" />
        <%
        String descripcionTipoSociedadAnonima_227id=  request.getParameter("descripcionTipoSociedadAnonima1650");
        java.lang.String descripcionTipoSociedadAnonima_227idTemp  = descripcionTipoSociedadAnonima_227id;
        String indicadorRUC_228id=  request.getParameter("indicadorRUC1652");
        java.lang.String indicadorRUC_228idTemp  = indicadorRUC_228id;
        String indicadorRepresentante_229id=  request.getParameter("indicadorRepresentante1654");
        java.lang.String indicadorRepresentante_229idTemp  = indicadorRepresentante_229id;
        String numeroPartida_230id=  request.getParameter("numeroPartida1656");
        java.lang.String numeroPartida_230idTemp  = numeroPartida_230id;
        String descripcionTipoSociedad_231id=  request.getParameter("descripcionTipoSociedad1658");
        java.lang.String descripcionTipoSociedad_231idTemp  = descripcionTipoSociedad_231id;
        String codigoTipoParticipantePJSUNAT_232id=  request.getParameter("codigoTipoParticipantePJSUNAT1660");
        java.lang.String codigoTipoParticipantePJSUNAT_232idTemp  = codigoTipoParticipantePJSUNAT_232id;
        String codigoTipoSociedadAnonima_233id=  request.getParameter("codigoTipoSociedadAnonima1662");
        java.lang.String codigoTipoSociedadAnonima_233idTemp  = codigoTipoSociedadAnonima_233id;
        String razonSocial_234id=  request.getParameter("razonSocial1664");
        java.lang.String razonSocial_234idTemp  = razonSocial_234id;
        String codigoOficinaRegistral_235id=  request.getParameter("codigoOficinaRegistral1666");
        java.lang.String codigoOficinaRegistral_235idTemp  = codigoOficinaRegistral_235id;
        String codigoZonaRegistral_236id=  request.getParameter("codigoZonaRegistral1668");
        java.lang.String codigoZonaRegistral_236idTemp  = codigoZonaRegistral_236id;
        String siglas_237id=  request.getParameter("siglas1670");
        java.lang.String siglas_237idTemp  = siglas_237id;
        String codigoTipoSociedad_238id=  request.getParameter("codigoTipoSociedad1672");
        java.lang.String codigoTipoSociedad_238idTemp  = codigoTipoSociedad_238id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setDescripcionTipoSociedadAnonima(descripcionTipoSociedadAnonima_227idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setIndicadorRUC(indicadorRUC_228idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setIndicadorRepresentante(indicadorRepresentante_229idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setNumeroPartida(numeroPartida_230idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setDescripcionTipoSociedad(descripcionTipoSociedad_231idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setCodigoTipoParticipantePJSUNAT(codigoTipoParticipantePJSUNAT_232idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setCodigoTipoSociedadAnonima(codigoTipoSociedadAnonima_233idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setRazonSocial(razonSocial_234idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setCodigoOficinaRegistral(codigoOficinaRegistral_235idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setCodigoZonaRegistral(codigoZonaRegistral_236idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setSiglas(siglas_237idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id.setCodigoTipoSociedad(codigoTipoSociedad_238idTemp);
        String ipRemota_239id=  request.getParameter("ipRemota1674");
        java.lang.String ipRemota_239idTemp  = ipRemota_239id;
        String secuencialOperacion_240id=  request.getParameter("secuencialOperacion1676");
        java.lang.String secuencialOperacion_240idTemp  = secuencialOperacion_240id;
        String porcentajeCancelado_242id=  request.getParameter("porcentajeCancelado1680");
        java.math.BigDecimal porcentajeCancelado_242idTemp  = new java.math.BigDecimal(porcentajeCancelado_242id);
        String numero_243id=  request.getParameter("numero1682");
        int numero_243idTemp  = Integer.parseInt(numero_243id);
        String montoCapital_244id=  request.getParameter("montoCapital1684");
        java.math.BigDecimal montoCapital_244idTemp  = new java.math.BigDecimal(montoCapital_244id);
        String descripcionMoneda_245id=  request.getParameter("descripcionMoneda1686");
        java.lang.String descripcionMoneda_245idTemp  = descripcionMoneda_245id;
        String valor_246id=  request.getParameter("valor1688");
        java.math.BigDecimal valor_246idTemp  = new java.math.BigDecimal(valor_246id);
        String codigoCancelacionCapital_247id=  request.getParameter("codigoCancelacionCapital1690");
        java.lang.String codigoCancelacionCapital_247idTemp  = codigoCancelacionCapital_247id;
        String descripcionCancelacionCapital_248id=  request.getParameter("descripcionCancelacionCapital1692");
        java.lang.String descripcionCancelacionCapital_248idTemp  = descripcionCancelacionCapital_248id;
        String codigoMoneda_249id=  request.getParameter("codigoMoneda1694");
        java.lang.String codigoMoneda_249idTemp  = codigoMoneda_249id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setPorcentajeCancelado(porcentajeCancelado_242idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setNumero(numero_243idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setMontoCapital(montoCapital_244idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setDescripcionMoneda(descripcionMoneda_245idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setValor(valor_246idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setCodigoCancelacionCapital(codigoCancelacionCapital_247idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setDescripcionCancelacionCapital(descripcionCancelacionCapital_248idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id.setCodigoMoneda(codigoMoneda_249idTemp);
        String fechaSolicitud_250id=  request.getParameter("fechaSolicitud1696");
        java.lang.String fechaSolicitud_250idTemp  = fechaSolicitud_250id;
        String codigoLibro_251id=  request.getParameter("codigoLibro1698");
        java.lang.String codigoLibro_251idTemp  = codigoLibro_251id;
        String codigoArea_252id=  request.getParameter("codigoArea1700");
        java.lang.String codigoArea_252idTemp  = codigoArea_252id;
        %>
        <jsp:useBean id="java1util1ArrayList_253id" scope="session" class="java.util.ArrayList" />
        <%
        String numeroTitulo_255id=  request.getParameter("numeroTitulo1708");
        java.lang.String numeroTitulo_255idTemp  = numeroTitulo_255id;
        String anhoTitulo_256id=  request.getParameter("anhoTitulo1710");
        java.lang.String anhoTitulo_256idTemp  = anhoTitulo_256id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_254id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_254id.setNumeroTitulo(numeroTitulo_255idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_254id.setAnhoTitulo(anhoTitulo_256idTemp);
        String numeroHoja_257id=  request.getParameter("numeroHoja1712");
        java.lang.String numeroHoja_257idTemp  = numeroHoja_257id;
        %>
        <jsp:useBean id="java1util1ArrayList_258id" scope="session" class="java.util.ArrayList" />
        <%
        String codigoServicio_259id=  request.getParameter("codigoServicio1718");
        java.lang.String codigoServicio_259idTemp  = codigoServicio_259id;
        String descripcionActo_260id=  request.getParameter("descripcionActo1720");
        java.lang.String descripcionActo_260idTemp  = descripcionActo_260id;
        String costoTotalServicio_262id=  request.getParameter("costoTotalServicio1724");
        java.math.BigDecimal costoTotalServicio_262idTemp  = new java.math.BigDecimal(costoTotalServicio_262id);
        String horaPago_263id=  request.getParameter("horaPago1726");
        java.lang.String horaPago_263idTemp  = horaPago_263id;
        String numeroOperacion_264id=  request.getParameter("numeroOperacion1728");
        java.lang.String numeroOperacion_264idTemp  = numeroOperacion_264id;
        String descripcionTipoPago_265id=  request.getParameter("descripcionTipoPago1730");
        java.lang.String descripcionTipoPago_265idTemp  = descripcionTipoPago_265id;
        String codigoFormaPago_266id=  request.getParameter("codigoFormaPago1732");
        java.lang.String codigoFormaPago_266idTemp  = codigoFormaPago_266id;
        String codigoTipoPago_267id=  request.getParameter("codigoTipoPago1734");
        java.lang.String codigoTipoPago_267idTemp  = codigoTipoPago_267id;
        String fechaPago_268id=  request.getParameter("fechaPago1736");
        java.lang.String fechaPago_268idTemp  = fechaPago_268id;
        String descripcionFormaPago_269id=  request.getParameter("descripcionFormaPago1738");
        java.lang.String descripcionFormaPago_269idTemp  = descripcionFormaPago_269id;
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setCostoTotalServicio(costoTotalServicio_262idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setHoraPago(horaPago_263idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setNumeroOperacion(numeroOperacion_264idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setDescripcionTipoPago(descripcionTipoPago_265idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setCodigoFormaPago(codigoFormaPago_266idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setCodigoTipoPago(codigoTipoPago_267idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setFechaPago(fechaPago_268idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id.setDescripcionFormaPago(descripcionFormaPago_269idTemp);
        %>
        <jsp:useBean id="gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id" scope="session" class="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion" />
        <%
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setDescripcionServicio(descripcionServicio_206idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoUsuario(codigoUsuario_207idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoOficinaRegistral(codigoOficinaRegistral_208idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setDescripcionOficinaRegistral(descripcionOficinaRegistral_209idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoActo(codigoActo_210idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCuo(cuo_211idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoZonaRegistral(codigoZonaRegistral_212idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setPartidas(java1util1ArrayList_213id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setPresentante(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1Presentante_214id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setParticipantesPersonaNatural(java1util1ArrayList_218id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setAnho(anho_219idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setDescripcionArea(descripcionArea_220idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setDescripcionLibro(descripcionLibro_221idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setEscrituraPublica(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1EscrituraPublica_222id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setVehiculos(java1util1ArrayList_225id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setPersonaJuridica(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1PersonaJuridica_226id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setIpRemota(ipRemota_239idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setSecuencialOperacion(secuencialOperacion_240idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCapital(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1Capital_241id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setFechaSolicitud(fechaSolicitud_250idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoLibro(codigoLibro_251idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoArea(codigoArea_252idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setInstrumentoPublico(java1util1ArrayList_253id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setReservaMercantil(gob1pe1sunarp1extranet1solicitud1inscripcion1escriturapublica1bean1ReservaMercantil_254id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setNumeroHoja(numeroHoja_257idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setParticipantesPersonaJuridica(java1util1ArrayList_258id);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setCodigoServicio(codigoServicio_259idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setDescripcionActo(descripcionActo_260idTemp);
        gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id.setDatosPago(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1DatosPago_261id);
        String datosXML_270id=  request.getParameter("datosXML1740");
        java.lang.String datosXML_270idTemp  = datosXML_270id;
        %>
        <jsp:useBean id="org1w3c1dom1Document_271id" scope="session" class="org.w3c.dom.Document" />
        <%
        gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion loadXMLtoBeanTransferenciaVehicular1459mtemp = sampleSunarpServiceProviderid.loadXMLtoBeanTransferenciaVehicular(gob1pe1sunarp1extranet1solicitud1inscripcion1bean1SolicitudInscripcion_205id,datosXML_270idTemp,org1w3c1dom1Document_271id);
if(loadXMLtoBeanTransferenciaVehicular1459mtemp == null){
%>
<%=loadXMLtoBeanTransferenciaVehicular1459mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typedescripcionServicio1462 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getDescripcionServicio();
        String tempResultdescripcionServicio1462 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionServicio1462));
        %>
        <%= tempResultdescripcionServicio1462 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoUsuario1464 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoUsuario();
        String tempResultcodigoUsuario1464 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario1464));
        %>
        <%= tempResultcodigoUsuario1464 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoOficinaRegistral1466 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral1466 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral1466));
        %>
        <%= tempResultcodigoOficinaRegistral1466 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typedescripcionOficinaRegistral1468 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getDescripcionOficinaRegistral();
        String tempResultdescripcionOficinaRegistral1468 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionOficinaRegistral1468));
        %>
        <%= tempResultdescripcionOficinaRegistral1468 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoActo:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoActo1470 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoActo();
        String tempResultcodigoActo1470 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoActo1470));
        %>
        <%= tempResultcodigoActo1470 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecuo1472 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCuo();
        String tempResultcuo1472 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo1472));
        %>
        <%= tempResultcuo1472 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoZonaRegistral1474 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral1474 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral1474));
        %>
        <%= tempResultcodigoZonaRegistral1474 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoPresentante1482 = tebece0.getCodigoPresentante();
        String tempResultcodigoPresentante1482 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoPresentante1482));
        %>
        <%= tempResultcodigoPresentante1482 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typecodigoInstitucion1484 = tebece0.getCodigoInstitucion();
        String tempResultcodigoInstitucion1484 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoInstitucion1484));
        %>
        <%= tempResultcodigoInstitucion1484 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPresentante();
if(tebece0 != null){
java.lang.String typedescripcionInstitucion1486 = tebece0.getDescripcionInstitucion();
        String tempResultdescripcionInstitucion1486 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionInstitucion1486));
        %>
        <%= tempResultdescripcionInstitucion1486 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaNatural:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">anho:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typeanho1492 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getAnho();
        String tempResultanho1492 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanho1492));
        %>
        <%= tempResultanho1492 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionArea:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typedescripcionArea1494 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getDescripcionArea();
        String tempResultdescripcionArea1494 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionArea1494));
        %>
        <%= tempResultdescripcionArea1494 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionLibro:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typedescripcionLibro1496 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getDescripcionLibro();
        String tempResultdescripcionLibro1496 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionLibro1496));
        %>
        <%= tempResultdescripcionLibro1496 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">escrituraPublica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">documentoEscrituraPublica:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typedocumentoEscrituraPublica1500 = tebece0.getDocumentoEscrituraPublica();
        String tempResultdocumentoEscrituraPublica1500 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedocumentoEscrituraPublica1500));
        %>
        <%= tempResultdocumentoEscrituraPublica1500 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">nombreArchivo:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getEscrituraPublica();
if(tebece0 != null){
java.lang.String typenombreArchivo1502 = tebece0.getNombreArchivo();
        String tempResultnombreArchivo1502 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombreArchivo1502));
        %>
        <%= tempResultnombreArchivo1502 %>
        <%
}}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedadAnonima1510 = tebece0.getDescripcionTipoSociedadAnonima();
        String tempResultdescripcionTipoSociedadAnonima1510 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedadAnonima1510));
        %>
        <%= tempResultdescripcionTipoSociedadAnonima1510 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRUC:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRUC1512 = tebece0.getIndicadorRUC();
        String tempResultindicadorRUC1512 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRUC1512));
        %>
        <%= tempResultindicadorRUC1512 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRepresentante:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typeindicadorRepresentante1514 = tebece0.getIndicadorRepresentante();
        String tempResultindicadorRepresentante1514 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRepresentante1514));
        %>
        <%= tempResultindicadorRepresentante1514 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroPartida:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typenumeroPartida1516 = tebece0.getNumeroPartida();
        String tempResultnumeroPartida1516 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroPartida1516));
        %>
        <%= tempResultnumeroPartida1516 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typedescripcionTipoSociedad1518 = tebece0.getDescripcionTipoSociedad();
        String tempResultdescripcionTipoSociedad1518 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoSociedad1518));
        %>
        <%= tempResultdescripcionTipoSociedad1518 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoParticipantePJSUNAT:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoParticipantePJSUNAT1520 = tebece0.getCodigoTipoParticipantePJSUNAT();
        String tempResultcodigoTipoParticipantePJSUNAT1520 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoParticipantePJSUNAT1520));
        %>
        <%= tempResultcodigoTipoParticipantePJSUNAT1520 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedadAnonima:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedadAnonima1522 = tebece0.getCodigoTipoSociedadAnonima();
        String tempResultcodigoTipoSociedadAnonima1522 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedadAnonima1522));
        %>
        <%= tempResultcodigoTipoSociedadAnonima1522 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">razonSocial:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typerazonSocial1524 = tebece0.getRazonSocial();
        String tempResultrazonSocial1524 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazonSocial1524));
        %>
        <%= tempResultrazonSocial1524 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoOficinaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoOficinaRegistral1526 = tebece0.getCodigoOficinaRegistral();
        String tempResultcodigoOficinaRegistral1526 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoOficinaRegistral1526));
        %>
        <%= tempResultcodigoOficinaRegistral1526 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoZonaRegistral:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoZonaRegistral1528 = tebece0.getCodigoZonaRegistral();
        String tempResultcodigoZonaRegistral1528 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoZonaRegistral1528));
        %>
        <%= tempResultcodigoZonaRegistral1528 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">siglas:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typesiglas1530 = tebece0.getSiglas();
        String tempResultsiglas1530 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesiglas1530));
        %>
        <%= tempResultsiglas1530 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoSociedad:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getPersonaJuridica();
if(tebece0 != null){
java.lang.String typecodigoTipoSociedad1532 = tebece0.getCodigoTipoSociedad();
        String tempResultcodigoTipoSociedad1532 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoSociedad1532));
        %>
        <%= tempResultcodigoTipoSociedad1532 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ipRemota:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typeipRemota1534 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getIpRemota();
        String tempResultipRemota1534 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeipRemota1534));
        %>
        <%= tempResultipRemota1534 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typesecuencialOperacion1536 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getSecuencialOperacion();
        String tempResultsecuencialOperacion1536 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesecuencialOperacion1536));
        %>
        <%= tempResultsecuencialOperacion1536 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">capital:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">porcentajeCancelado:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typeporcentajeCancelado1540 = tebece0.getPorcentajeCancelado();
        String tempResultporcentajeCancelado1540 = org.eclipse.jst.ws.util.JspUtils.markup(typeporcentajeCancelado1540.toString());
        %>
        <%= tempResultporcentajeCancelado1540 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numero:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
%>
<%=tebece0.getNumero()
%><%}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">montoCapital:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typemontoCapital1544 = tebece0.getMontoCapital();
        String tempResultmontoCapital1544 = org.eclipse.jst.ws.util.JspUtils.markup(typemontoCapital1544.toString());
        %>
        <%= tempResultmontoCapital1544 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionMoneda1546 = tebece0.getDescripcionMoneda();
        String tempResultdescripcionMoneda1546 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionMoneda1546));
        %>
        <%= tempResultdescripcionMoneda1546 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">valor:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.math.BigDecimal typevalor1548 = tebece0.getValor();
        String tempResultvalor1548 = org.eclipse.jst.ws.util.JspUtils.markup(typevalor1548.toString());
        %>
        <%= tempResultvalor1548 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoCancelacionCapital1550 = tebece0.getCodigoCancelacionCapital();
        String tempResultcodigoCancelacionCapital1550 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoCancelacionCapital1550));
        %>
        <%= tempResultcodigoCancelacionCapital1550 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionCancelacionCapital:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.lang.String typedescripcionCancelacionCapital1552 = tebece0.getDescripcionCancelacionCapital();
        String tempResultdescripcionCancelacionCapital1552 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionCancelacionCapital1552));
        %>
        <%= tempResultdescripcionCancelacionCapital1552 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoMoneda:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getCapital();
if(tebece0 != null){
java.lang.String typecodigoMoneda1554 = tebece0.getCodigoMoneda();
        String tempResultcodigoMoneda1554 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoMoneda1554));
        %>
        <%= tempResultcodigoMoneda1554 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">fechaSolicitud:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typefechaSolicitud1556 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getFechaSolicitud();
        String tempResultfechaSolicitud1556 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaSolicitud1556));
        %>
        <%= tempResultfechaSolicitud1556 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoLibro:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoLibro1558 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoLibro();
        String tempResultcodigoLibro1558 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoLibro1558));
        %>
        <%= tempResultcodigoLibro1558 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoArea:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoArea1560 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoArea();
        String tempResultcodigoArea1560 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoArea1560));
        %>
        <%= tempResultcodigoArea1560 %>
        <%
}%>
</TD>
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
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typenumeroTitulo1568 = tebece0.getNumeroTitulo();
        String tempResultnumeroTitulo1568 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroTitulo1568));
        %>
        <%= tempResultnumeroTitulo1568 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anhoTitulo:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getReservaMercantil();
if(tebece0 != null){
java.lang.String typeanhoTitulo1570 = tebece0.getAnhoTitulo();
        String tempResultanhoTitulo1570 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanhoTitulo1570));
        %>
        <%= tempResultanhoTitulo1570 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">numeroHoja:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typenumeroHoja1572 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getNumeroHoja();
        String tempResultnumeroHoja1572 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroHoja1572));
        %>
        <%= tempResultnumeroHoja1572 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">participantesPersonaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typecodigoServicio1578 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getCodigoServicio();
        String tempResultcodigoServicio1578 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoServicio1578));
        %>
        <%= tempResultcodigoServicio1578 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionActo:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
java.lang.String typedescripcionActo1580 = loadXMLtoBeanTransferenciaVehicular1459mtemp.getDescripcionActo();
        String tempResultdescripcionActo1580 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionActo1580));
        %>
        <%= tempResultdescripcionActo1580 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.math.BigDecimal typecostoTotalServicio1584 = tebece0.getCostoTotalServicio();
        String tempResultcostoTotalServicio1584 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio1584.toString());
        %>
        <%= tempResultcostoTotalServicio1584 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typehoraPago1586 = tebece0.getHoraPago();
        String tempResulthoraPago1586 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago1586));
        %>
        <%= tempResulthoraPago1586 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typenumeroOperacion1588 = tebece0.getNumeroOperacion();
        String tempResultnumeroOperacion1588 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion1588));
        %>
        <%= tempResultnumeroOperacion1588 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionTipoPago1590 = tebece0.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago1590 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago1590));
        %>
        <%= tempResultdescripcionTipoPago1590 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoFormaPago1592 = tebece0.getCodigoFormaPago();
        String tempResultcodigoFormaPago1592 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago1592));
        %>
        <%= tempResultcodigoFormaPago1592 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoTipoPago1594 = tebece0.getCodigoTipoPago();
        String tempResultcodigoTipoPago1594 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago1594));
        %>
        <%= tempResultcodigoTipoPago1594 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typefechaPago1596 = tebece0.getFechaPago();
        String tempResultfechaPago1596 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago1596));
        %>
        <%= tempResultfechaPago1596 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanTransferenciaVehicular1459mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanTransferenciaVehicular1459mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionFormaPago1598 = tebece0.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago1598 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago1598));
        %>
        <%= tempResultdescripcionFormaPago1598 %>
        <%
}}%>
</TD>
</TABLE>
<%
}
break;
case 1894:
        gotMethod = true;
        String datosXML_272id=  request.getParameter("datosXML1933");
        java.lang.String datosXML_272idTemp  = datosXML_272id;
        gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre.bean.SolicitudBusqueda loadXMLtoBeanBusquedaJuridica1894mtemp = sampleSunarpServiceProviderid.loadXMLtoBeanBusquedaJuridica(datosXML_272idTemp);
if(loadXMLtoBeanBusquedaJuridica1894mtemp == null){
%>
<%=loadXMLtoBeanBusquedaJuridica1894mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionServicio:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typedescripcionServicio1897 = loadXMLtoBeanBusquedaJuridica1894mtemp.getDescripcionServicio();
        String tempResultdescripcionServicio1897 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionServicio1897));
        %>
        <%= tempResultdescripcionServicio1897 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">descripcionInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typedescripcionInstitucion1899 = loadXMLtoBeanBusquedaJuridica1894mtemp.getDescripcionInstitucion();
        String tempResultdescripcionInstitucion1899 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionInstitucion1899));
        %>
        <%= tempResultdescripcionInstitucion1899 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoServicio:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typecodigoServicio1901 = loadXMLtoBeanBusquedaJuridica1894mtemp.getCodigoServicio();
        String tempResultcodigoServicio1901 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoServicio1901));
        %>
        <%= tempResultcodigoServicio1901 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.math.BigDecimal typecostoTotalServicio1905 = tebece0.getCostoTotalServicio();
        String tempResultcostoTotalServicio1905 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio1905.toString());
        %>
        <%= tempResultcostoTotalServicio1905 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typehoraPago1907 = tebece0.getHoraPago();
        String tempResulthoraPago1907 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago1907));
        %>
        <%= tempResulthoraPago1907 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typenumeroOperacion1909 = tebece0.getNumeroOperacion();
        String tempResultnumeroOperacion1909 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion1909));
        %>
        <%= tempResultnumeroOperacion1909 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionTipoPago1911 = tebece0.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago1911 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago1911));
        %>
        <%= tempResultdescripcionTipoPago1911 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoFormaPago1913 = tebece0.getCodigoFormaPago();
        String tempResultcodigoFormaPago1913 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago1913));
        %>
        <%= tempResultcodigoFormaPago1913 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typecodigoTipoPago1915 = tebece0.getCodigoTipoPago();
        String tempResultcodigoTipoPago1915 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago1915));
        %>
        <%= tempResultcodigoTipoPago1915 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typefechaPago1917 = tebece0.getFechaPago();
        String tempResultfechaPago1917 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago1917));
        %>
        <%= tempResultfechaPago1917 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece0=loadXMLtoBeanBusquedaJuridica1894mtemp.getDatosPago();
if(tebece0 != null){
java.lang.String typedescripcionFormaPago1919 = tebece0.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago1919 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago1919));
        %>
        <%= tempResultdescripcionFormaPago1919 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">filtroBusqueda:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">secuencialOperacion:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typesecuencialOperacion1925 = loadXMLtoBeanBusquedaJuridica1894mtemp.getSecuencialOperacion();
        String tempResultsecuencialOperacion1925 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typesecuencialOperacion1925));
        %>
        <%= tempResultsecuencialOperacion1925 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoInstitucion:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typecodigoInstitucion1927 = loadXMLtoBeanBusquedaJuridica1894mtemp.getCodigoInstitucion();
        String tempResultcodigoInstitucion1927 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoInstitucion1927));
        %>
        <%= tempResultcodigoInstitucion1927 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typecodigoUsuario1929 = loadXMLtoBeanBusquedaJuridica1894mtemp.getCodigoUsuario();
        String tempResultcodigoUsuario1929 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario1929));
        %>
        <%= tempResultcodigoUsuario1929 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(loadXMLtoBeanBusquedaJuridica1894mtemp != null){
java.lang.String typecuo1931 = loadXMLtoBeanBusquedaJuridica1894mtemp.getCuo();
        String tempResultcuo1931 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo1931));
        %>
        <%= tempResultcuo1931 %>
        <%
}%>
</TD>
</TABLE>
<%
}
break;
case 1935:
        gotMethod = true;
        String strByteAux_273id=  request.getParameter("strByteAux1938");
        java.lang.String strByteAux_273idTemp  = strByteAux_273id;
        java.lang.String generaHashMD51935mtemp = sampleSunarpServiceProviderid.generaHashMD5(strByteAux_273idTemp);
if(generaHashMD51935mtemp == null){
%>
<%=generaHashMD51935mtemp %>
<%
}else{
        String tempResultreturnp1936 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(generaHashMD51935mtemp));
        %>
        <%= tempResultreturnp1936 %>
        <%
}
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>