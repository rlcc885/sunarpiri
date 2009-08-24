<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>



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
        String ofic_0id=  request.getParameter("ofic171");
        java.lang.String ofic_0idTemp  = ofic_0id;
        String reg_1id=  request.getParameter("reg173");
        java.lang.String reg_1idTemp  = reg_1id;
        String servicio_2id=  request.getParameter("servicio175");
        java.lang.String servicio_2idTemp  = servicio_2id;
        gob.pe.sunarp.extranet.webservices.bean.TituloEnLineaBean getServicio2mtemp = null;//sampleWSFactoryid.getServicio(ofic_0idTemp,reg_1idTemp,servicio_2idTemp);
if(getServicio2mtemp == null){
%>
<%=getServicio2mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">deno:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">listaDenominaciones:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">coOficRegiPres:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecoOficRegiPres11 = tebece0.getCoOficRegiPres();
        String tempResultcoOficRegiPres11 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecoOficRegiPres11));
        %>
        <%= tempResultcoOficRegiPres11 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroTitulo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typenumeroTitulo13 = tebece0.getNumeroTitulo();
        String tempResultnumeroTitulo13 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroTitulo13));
        %>
        <%= tempResultnumeroTitulo13 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoUsuario:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecodigoUsuario15 = tebece0.getCodigoUsuario();
        String tempResultcodigoUsuario15 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoUsuario15));
        %>
        <%= tempResultcodigoUsuario15 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">coRegiPres:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecoRegiPres17 = tebece0.getCoRegiPres();
        String tempResultcoRegiPres17 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecoRegiPres17));
        %>
        <%= tempResultcoRegiPres17 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoActo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecodigoActo19 = tebece0.getCodigoActo();
        String tempResultcodigoActo19 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoActo19));
        %>
        <%= tempResultcodigoActo19 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">cuo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecuo21 = tebece0.getCuo();
        String tempResultcuo21 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuo21));
        %>
        <%= tempResultcuo21 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">presentante:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">numDocu:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typenumDocu25 = tebece1.getNumDocu();
        String tempResultnumDocu25 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumDocu25));
        %>
        <%= tempResultnumDocu25 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">direccion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typedireccion27 = tebece1.getDireccion();
        String tempResultdireccion27 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedireccion27));
        %>
        <%= tempResultdireccion27 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">descDocu:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typedescDocu29 = tebece1.getDescDocu();
        String tempResultdescDocu29 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescDocu29));
        %>
        <%= tempResultdescDocu29 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">participacion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typeparticipacion31 = tebece1.getParticipacion();
        String tempResultparticipacion31 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeparticipacion31));
        %>
        <%= tempResultparticipacion31 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">apePaterno:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typeapePaterno33 = tebece1.getApePaterno();
        String tempResultapePaterno33 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeapePaterno33));
        %>
        <%= tempResultapePaterno33 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">email:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typeemail35 = tebece1.getEmail();
        String tempResultemail35 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeemail35));
        %>
        <%= tempResultemail35 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">apeMaterno:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typeapeMaterno37 = tebece1.getApeMaterno();
        String tempResultapeMaterno37 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeapeMaterno37));
        %>
        <%= tempResultapeMaterno37 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">tipoDocu:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typetipoDocu39 = tebece1.getTipoDocu();
        String tempResulttipoDocu39 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetipoDocu39));
        %>
        <%= tempResulttipoDocu39 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">nombre:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante tebece1=tebece0.getPresentante();
if(tebece1 != null){
java.lang.String typenombre41 = tebece1.getNombre();
        String tempResultnombre41 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombre41));
        %>
        <%= tempResultnombre41 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descServicio:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typedescServicio43 = tebece0.getDescServicio();
        String tempResultdescServicio43 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescServicio43));
        %>
        <%= tempResultdescServicio43 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descripcionArea:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typedescripcionArea45 = tebece0.getDescripcionArea();
        String tempResultdescripcionArea45 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionArea45));
        %>
        <%= tempResultdescripcionArea45 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorRegistrado:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typeindicadorRegistrado47 = tebece0.getIndicadorRegistrado();
        String tempResultindicadorRegistrado47 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorRegistrado47));
        %>
        <%= tempResultindicadorRegistrado47 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descLibro:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typedescLibro49 = tebece0.getDescLibro();
        String tempResultdescLibro49 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescLibro49));
        %>
        <%= tempResultdescLibro49 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">personaJuridica:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">ficha:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typeficha53 = tebece1.getFicha();
        String tempResultficha53 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeficha53));
        %>
        <%= tempResultficha53 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">razonSocial:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typerazonSocial55 = tebece1.getRazonSocial();
        String tempResultrazonSocial55 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazonSocial55));
        %>
        <%= tempResultrazonSocial55 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">descTipo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typedescTipo57 = tebece1.getDescTipo();
        String tempResultdescTipo57 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescTipo57));
        %>
        <%= tempResultdescTipo57 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">tipo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typetipo59 = tebece1.getTipo();
        String tempResulttipo59 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetipo59));
        %>
        <%= tempResulttipo59 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">departamento:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typedepartamento61 = tebece1.getDepartamento();
        String tempResultdepartamento61 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedepartamento61));
        %>
        <%= tempResultdepartamento61 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">provincia:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typeprovincia63 = tebece1.getProvincia();
        String tempResultprovincia63 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeprovincia63));
        %>
        <%= tempResultprovincia63 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">partida:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica tebece1=tebece0.getPersonaJuridica();
if(tebece1 != null){
java.lang.String typepartida65 = tebece1.getPartida();
        String tempResultpartida65 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typepartida65));
        %>
        <%= tempResultpartida65 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">servicio:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typeservicio67 = tebece0.getServicio();
        String tempResultservicio67 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeservicio67));
        %>
        <%= tempResultservicio67 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">inidcadorDenominacion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typeinidcadorDenominacion69 = tebece0.getInidcadorDenominacion();
        String tempResultinidcadorDenominacion69 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeinidcadorDenominacion69));
        %>
        <%= tempResultinidcadorDenominacion69 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoLibro:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecodigoLibro71 = tebece0.getCodigoLibro();
        String tempResultcodigoLibro71 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoLibro71));
        %>
        <%= tempResultcodigoLibro71 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">usuario:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">apeMat:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typeapeMat75 = tebece1.getApeMat();
        String tempResultapeMat75 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeapeMat75));
        %>
        <%= tempResultapeMat75 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">nivelAccesoId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getNivelAccesoId()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">num_contrato:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typenum_contrato79 = tebece1.getNum_contrato();
        String tempResultnum_contrato79 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenum_contrato79));
        %>
        <%= tempResultnum_contrato79 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">cur:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typecur81 = tebece1.getCur();
        String tempResultcur81 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecur81));
        %>
        <%= tempResultcur81 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">userId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typeuserId83 = tebece1.getUserId();
        String tempResultuserId83 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeuserId83));
        %>
        <%= tempResultuserId83 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">lineaPrePagoOrganizacion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typelineaPrePagoOrganizacion85 = tebece1.getLineaPrePagoOrganizacion();
        String tempResultlineaPrePagoOrganizacion85 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typelineaPrePagoOrganizacion85));
        %>
        <%= tempResultlineaPrePagoOrganizacion85 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">personaId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typepersonaId87 = tebece1.getPersonaId();
        String tempResultpersonaId87 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typepersonaId87));
        %>
        <%= tempResultpersonaId87 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">peNatuId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typepeNatuId89 = tebece1.getPeNatuId();
        String tempResultpeNatuId89 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typepeNatuId89));
        %>
        <%= tempResultpeNatuId89 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">fgIndividual:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getFgIndividual()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">razSocial:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typerazSocial93 = tebece1.getRazSocial();
        String tempResultrazSocial93 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerazSocial93));
        %>
        <%= tempResultrazSocial93 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">fgInterno:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getFgInterno()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">userAdminOrg:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typeuserAdminOrg97 = tebece1.getUserAdminOrg();
        String tempResultuserAdminOrg97 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeuserAdminOrg97));
        %>
        <%= tempResultuserAdminOrg97 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">linPrePago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typelinPrePago99 = tebece1.getLinPrePago();
        String tempResultlinPrePago99 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typelinPrePago99));
        %>
        <%= tempResultlinPrePago99 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">tipoUser:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typetipoUser101 = tebece1.getTipoUser();
        String tempResulttipoUser101 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetipoUser101));
        %>
        <%= tempResulttipoUser101 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">regPublicoId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typeregPublicoId103 = tebece1.getRegPublicoId();
        String tempResultregPublicoId103 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeregPublicoId103));
        %>
        <%= tempResultregPublicoId103 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">oficRegistralId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typeoficRegistralId105 = tebece1.getOficRegistralId();
        String tempResultoficRegistralId105 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeoficRegistralId105));
        %>
        <%= tempResultoficRegistralId105 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">fgAdmin:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getFgAdmin()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">cuentaId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typecuentaId109 = tebece1.getCuentaId();
        String tempResultcuentaId109 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecuentaId109));
        %>
        <%= tempResultcuentaId109 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">apePat:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typeapePat111 = tebece1.getApePat();
        String tempResultapePat111 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeapePat111));
        %>
        <%= tempResultapePat111 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">codOrg:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typecodOrg113 = tebece1.getCodOrg();
        String tempResultcodOrg113 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodOrg113));
        %>
        <%= tempResultcodOrg113 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">saldo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getSaldo()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">nombres:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typenombres117 = tebece1.getNombres();
        String tempResultnombres117 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenombres117));
        %>
        <%= tempResultnombres117 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">jurisdiccionId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
java.lang.String typejurisdiccionId119 = tebece1.getJurisdiccionId();
        String tempResultjurisdiccionId119 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typejurisdiccionId119));
        %>
        <%= tempResultjurisdiccionId119 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">perfilId:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getPerfilId()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">exonPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.framework.session.UsuarioBean tebece1=tebece0.getUsuario();
if(tebece1 != null){
%>
<%=tebece1.getExonPago()
%><%}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anio:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typeanio125 = tebece0.getAnio();
        String tempResultanio125 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanio125));
        %>
        <%= tempResultanio125 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaProceso:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typefechaProceso127 = tebece0.getFechaProceso();
        String tempResultfechaProceso127 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaProceso127));
        %>
        <%= tempResultfechaProceso127 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descActo:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typedescActo129 = tebece0.getDescActo();
        String tempResultdescActo129 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescActo129));
        %>
        <%= tempResultdescActo129 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">persAutoizadaPres:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typepersAutoizadaPres131 = tebece0.getPersAutoizadaPres();
        String tempResultpersAutoizadaPres131 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typepersAutoizadaPres131));
        %>
        <%= tempResultpersAutoizadaPres131 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">codigoArea:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typecodigoArea133 = tebece0.getCodigoArea();
        String tempResultcodigoArea133 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoArea133));
        %>
        <%= tempResultcodigoArea133 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">descSeleccion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typedescSeleccion135 = tebece0.getDescSeleccion();
        String tempResultdescSeleccion135 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescSeleccion135));
        %>
        <%= tempResultdescSeleccion135 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">fechaPresTitu:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typefechaPresTitu137 = tebece0.getFechaPresTitu();
        String tempResultfechaPresTitu137 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPresTitu137));
        %>
        <%= tempResultfechaPresTitu137 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">monto:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.Double typemonto139 = tebece0.getMonto();
        String tempResultmonto139 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typemonto139));
        %>
        <%= tempResultmonto139 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">anioTitu:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typeanioTitu141 = tebece0.getAnioTitu();
        String tempResultanioTitu141 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeanioTitu141));
        %>
        <%= tempResultanioTitu141 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">numeroHoja:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typenumeroHoja143 = tebece0.getNumeroHoja();
        String tempResultnumeroHoja143 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroHoja143));
        %>
        <%= tempResultnumeroHoja143 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">refNumTitu:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typerefNumTitu145 = tebece0.getRefNumTitu();
        String tempResultrefNumTitu145 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typerefNumTitu145));
        %>
        <%= tempResultrefNumTitu145 %>
        <%
}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">listaParticipantes:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">datosPago:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">costoTotalServicio:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.math.BigDecimal typecostoTotalServicio153 = tebece1.getCostoTotalServicio();
        String tempResultcostoTotalServicio153 = org.eclipse.jst.ws.util.JspUtils.markup(typecostoTotalServicio153.toString());
        %>
        <%= tempResultcostoTotalServicio153 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">horaPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typehoraPago155 = tebece1.getHoraPago();
        String tempResulthoraPago155 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typehoraPago155));
        %>
        <%= tempResulthoraPago155 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">numeroOperacion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typenumeroOperacion157 = tebece1.getNumeroOperacion();
        String tempResultnumeroOperacion157 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typenumeroOperacion157));
        %>
        <%= tempResultnumeroOperacion157 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">descripcionTipoPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typedescripcionTipoPago159 = tebece1.getDescripcionTipoPago();
        String tempResultdescripcionTipoPago159 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionTipoPago159));
        %>
        <%= tempResultdescripcionTipoPago159 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">codigoFormaPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typecodigoFormaPago161 = tebece1.getCodigoFormaPago();
        String tempResultcodigoFormaPago161 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoFormaPago161));
        %>
        <%= tempResultcodigoFormaPago161 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">codigoTipoPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typecodigoTipoPago163 = tebece1.getCodigoTipoPago();
        String tempResultcodigoTipoPago163 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typecodigoTipoPago163));
        %>
        <%= tempResultcodigoTipoPago163 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">fechaPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typefechaPago165 = tebece1.getFechaPago();
        String tempResultfechaPago165 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typefechaPago165));
        %>
        <%= tempResultfechaPago165 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">descripcionFormaPago:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago tebece1=tebece0.getDatosPago();
if(tebece1 != null){
java.lang.String typedescripcionFormaPago167 = tebece1.getDescripcionFormaPago();
        String tempResultdescripcionFormaPago167 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedescripcionFormaPago167));
        %>
        <%= tempResultdescripcionFormaPago167 %>
        <%
}}}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">indicadorSeleccion:</TD>
<TD>
<%
if(getServicio2mtemp != null){
gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion tebece0=getServicio2mtemp.getDeno();
if(tebece0 != null){
java.lang.String typeindicadorSeleccion169 = tebece0.getIndicadorSeleccion();
        String tempResultindicadorSeleccion169 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeindicadorSeleccion169));
        %>
        <%= tempResultindicadorSeleccion169 %>
        <%
}}%>
</TD>
</TABLE>
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