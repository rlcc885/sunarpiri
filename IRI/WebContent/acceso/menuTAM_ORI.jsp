<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.acceso.bean.*" %>

<%
//recoger arreglo de permisos
PermisoBean arrp[] = (PermisoBean[]) request.getAttribute("arregloPermisos");
%>
<html>
<head>
<link href="<%=request.getContextPath()%>/styles/stylo1.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">

<script language=javascript>
nav_nombre = navigator.appName.substring(0,5);
nav_version = parseInt(navigator.appVersion);
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
window.open("acceso/menuInformacion.html","Informacion",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=555,height=400');

function informacion(){
window.open("acceso/menuInformacion.html","Informacion",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=555,height=400');
}
</script>
</head>

<body background="../images/bk_left.gif">
<img src="../images/tit_cabecera2.gif">
<layer id="Netscape" name="Netscape" top=60 left=0 visibility= SHOW>
<div id="Explorer" style="position:absolute;top:60;left:0;visibility: visible">			

<!-- SERVICIOS DE BUSQUEDA -->
<%if(arrp[7]!=null || arrp[8]!=null || arrp[9]!=null)
{%>
<layer id=OutN0 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out0 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr> 
      <td width="4%"><div id=Out1 class=Outline style="CURSOR: hand"><IMG src="images/bull_left1.gif" width="29" height="37"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out1 class=Outline style="CURSOR: hand">Servicios</b></font></td>
    </tr>
  </table>
  <layer id=OutN1details visibility=show LEFT=0px POSITION= relative> 
  <div id=Out1details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_leftf.gif"><img src="images/space.gif" width="15" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
			<%if(arrp[9]!=null){PermisoBean p = (PermisoBean) arrp[9];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>        
            <%if(arrp[8]!=null){PermisoBean p = (PermisoBean) arrp[8];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
			<%}%>            
			<%if(arrp[7]!=null){PermisoBean p = (PermisoBean) arrp[7];%>
			<tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>        
            <%if((arrp[59]!=null)&&(((perfilusuarioid == Constantes.PERFIL_ADMIN_ORG_EXT)||(perfilusuarioid == Constantes.PERFIL_AFILIADO_EXTERNO)||(perfilusuarioid == Constantes.PERFIL_INDIVIDUAL_EXTERNO))&&((!usuarioBean.getExonPago())))){PermisoBean p = (PermisoBean) arrp[59];%>
			<tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
			<%if(arrp[55]!=null){PermisoBean p = (PermisoBean) arrp[55];%>
			<tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>   
            <%if(arrp[76]!=null){PermisoBean p = (PermisoBean) arrp[76];%>
				<tr> 
	              <td width="5%">-</td>
	              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
	            </tr>
	            <%}%>       
			<%--if(arrp[53]!=null){PermisoBean p = (PermisoBean) arrp[53];%>
			<tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}--%>
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>


<!-- 18 =  editar datos personales -->
<%if(arrp[18]!=null && (perfilusuarioid != Constantes.PERFIL_ADMIN_ORG_EXT)) {%>
<layer id=OutN2 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out2 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr> 
      <td width="4%"><div id=Out2 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft" wrap><font class=linkLeft><b id=Out2 class=Outline style="CURSOR: hand">Datos 
        Personales</b></font></td>
    </tr>
  </table>
  <layer id=OutN2details visibility="show" LEFT=0px POSITION= relative> 
  <div id=Out2details style="display:0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
	  <%if(arrp[18]!=null){PermisoBean p = (PermisoBean) arrp[18];%>    
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
      <%}%>      
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>

 
<!-- 1 = incrementar saldo -->
<%if(arrp[1]!=null){%>
<layer id=OutN3 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out3 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr> 

<%if(perfilusuarioid != Constantes.PERFIL_ADMIN_ORG_EXT) {%>
      <td width="4%"><div id=Out3 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
<%}else{%>
      <td width="4%"><div id=Out3 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
<%}%>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out3 class=Outline style="CURSOR: hand">Prepagar</b></font></td>
    </tr>
  </table>
  <layer id=OutN3details visibility="show" LEFT=0px POSITION= relative> 
  <div id=Out3details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
	  <%
	  if(arrp[1]!=null)
	  {
	  		PermisoBean p = (PermisoBean) arrp[1];%>    
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
      <%}%> 
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>           
</div>
</layer>
<%}%>

<!-- **** MENU PARar ADMIN ORG EXT**** -->
<%if(perfilusuarioid==Constantes.PERFIL_ADMIN_ORG_EXT){%>
<layer id=OutN4 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out4 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out4 class=Outline style="CURSOR: hand"><IMG src="images/bull_left5.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out4 class=Outline style="CURSOR: hand">Adminitracion Organizacion</b></font></td>
    </tr>
  </table>
  <layer id=OutN4details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out4details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="/iri/EditarDatosPersonales.do" target="main_frame1" class="opcleft">Editar mi Organizacion</a></td>
            </tr>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="/iri/Mantenimiento.do" target="main_frame1" class="opcleft">Administrar Usuarios</a></td>
            </tr>
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>




<!-- **** MIS TITULOS **** -->
<%if(arrp[49]!=null ){%>
<layer id=OutN5 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out5 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out5 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out5 class=Outline style="CURSOR: hand">Mis Titulos</b></font></td>
    </tr>
  </table>
  <layer id=OutN5details visibility="hide" LEFT=0px POSITION= relative>  
  <div id=Out5details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
        	<%if(arrp[49]!=null){PermisoBean p = (PermisoBean) arrp[49];%>
            <tr> 
              <td width="5%" >-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>

<!-- ***** menu para REGISTRADOR ****  -->
<%if((arrp[58]!=null)&&(perfilusuarioid==Constantes.PERFIL_INTERNO)){%>

<layer id=OutN12 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out12 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out12 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out12 class=Outline style="CURSOR: hand">Registrador</b></font></td>
    </tr>
  </table>
  <layer id=OutN12details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out12details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
        	<%PermisoBean p = (PermisoBean) arrp[58];%>
            <tr> 
              <td width="5%" >-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>

<!-- ***** menu para CAJERO ****  -->
<%if(perfilusuarioid==Constantes.PERFIL_CAJERO){%>
<%if(arrp[22]!=null || arrp[24]!=null || arrp[43]!=null || arrp[46]!=null ) {%>
<layer id=OutN6 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out6 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out6 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out6 class=Outline style="CURSOR: hand">Caja</b></font></td>
    </tr>
  </table>
  <layer id=OutN6details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out6details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
        	<%if(arrp[22]!=null){PermisoBean p = (PermisoBean) arrp[22];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            <%if(arrp[24]!=null){PermisoBean p = (PermisoBean) arrp[24];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            <%if(arrp[43]!=null){PermisoBean p = (PermisoBean) arrp[43];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            <%if(arrp[46]!=null){PermisoBean p = (PermisoBean) arrp[46];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            <%if(arrp[59]!=null){PermisoBean p = (PermisoBean) arrp[59];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>
<%}%>



<!-- **** constancia pagos **** -->
<%if((arrp[47]!=null)||(arrp[57]!=null)){%>
<layer id=OutN7 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out7 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out7 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out7 class=Outline style="CURSOR: hand">Tesorería</b></font></td>
    </tr>
  </table>
  <layer id=OutN7details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out7details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr>
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif" ><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
        	<%if(arrp[47]!=null){PermisoBean p = (PermisoBean) arrp[47];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[57]!=null){PermisoBean p = (PermisoBean) arrp[57];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
               
          </table>            
          </td>
          </table></td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>




<%if(perfilusuarioid==Constantes.PERFIL_ADMIN_GENERAL || 
     perfilusuarioid==Constantes.PERFIL_ADMIN_JURISDICCION)  {%>
<%if(arrp[22]!=null || arrp[23]!=null || arrp[24]!=null || 
	 arrp[28]!=null || arrp[29]!=null || 
	 arrp[35]!=null || 
	 arrp[36]!=null ) {%>
<layer id=OutN8 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out8 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out8 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out8 class=Outline style="CURSOR: hand">Administracion Extranet</b></font></td>
    </tr>
  </table>
  <layer id=OutN8details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out8details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr>
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif" ><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
        	<%if(arrp[22]!=null){PermisoBean p = (PermisoBean) arrp[22];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            <%if(arrp[24]!=null){PermisoBean p = (PermisoBean) arrp[24];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            <%if(arrp[23]!=null){PermisoBean p = (PermisoBean) arrp[23];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
            
            <%if (perfilusuarioid==Constantes.PERFIL_ADMIN_GENERAL) {%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=request.getContextPath()%>/mantenimiento/Mantenimiento.jsp" target="main_frame1" class="opcleft">Mantenimiento de Tablas</a></td>
            </tr> 
            <tr> 
              <td width="5%">-</td>            
              <td width="95%"><a href="/iri/MantenimientoContrato.do" target="main_frame1" class="opcleft">Mantenimiento de Contratos</a></td>          
            </tr>  
            <% } %>             
            
            
            <%if(arrp[45]!=null){PermisoBean p = (PermisoBean) arrp[45];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
      	       
          </table>            
          </td>
          </table></td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>
<%}%>





<%
//reportes conocidos por este JSP
// id descripcion

// 30 ingresos *
// 31 movimientos *
// 32 transacciones *
// 33 usoServicios *
// 34 usuariosRegistrados *
// 50 evolucion *
// 51 mediosPago   *

boolean flagRpt = false;
if ( arrp[30]!=null || 
     arrp[31]!=null ||
     arrp[32]!=null ||
     arrp[33]!=null ||
     arrp[34]!=null || 
     arrp[50]!=null ||
     arrp[51]!=null  )
     flagRpt = true;
%>

<!-- menu adicional reportes -->
<%if(flagRpt==true) {%>
<layer id=OutN9 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out9 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out9 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out9 class=Outline style="CURSOR: hand">Reportes</b></font></td>
    </tr>
  </table>
  <layer id=OutN9details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out9details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
        	<%if(arrp[30]!=null) {PermisoBean p = (PermisoBean) arrp[30];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[31]!=null) {PermisoBean p = (PermisoBean) arrp[31];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[32]!=null) {PermisoBean p = (PermisoBean) arrp[32];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[33]!=null) {PermisoBean p = (PermisoBean) arrp[33];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[34]!=null) {PermisoBean p = (PermisoBean) arrp[34];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[50]!=null) {PermisoBean p = (PermisoBean) arrp[50];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>
        	<%if(arrp[51]!=null) {PermisoBean p = (PermisoBean) arrp[51];%>
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a href="<%=p.getUrl()%>" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a></td>
            </tr>
            <%}%>                                                                                                                                                            
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>


<%-- consola central --%>
<%if(perfilusuarioid==Constantes.PERFIL_ADMIN_GENERAL || 
     perfilusuarioid==Constantes.PERFIL_ADMIN_JURISDICCION)     {%>
<layer id=OutN10 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out10 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
    <tr> 
      <td width="4%"><div id=Out10 class=Outline style="CURSOR: hand"><IMG src="images/bull_left4.gif"></div></td>
      <td width="96%" class="opctitLeft"><font class=linkLeft><b id=Out10 class=Outline style="CURSOR: hand">Consola Central</b></font></td>
    </tr>
  </table>
  <layer id=OutN10details visibility="show" LEFT=0px POSITION= relative>  
  <div id=Out10details style="display: 0; LEFT: 0px; POSITION: relative"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#DDE1E4">
      <tr> 
        <td width="10%" valign=top bordercolor="#E5E5E5" background="images/bull_lefte.gif"><img src="images/space.gif" width="25" height="17"></td>
        <td width="100%" valign=top bordercolor="#A2A2A2" bgcolor="E9E9E9" border=1>
        <table border="1px" width="100%" bgcolor="E9E9E9" bordercolor="#bbbbbb" cellpadding="0" cellspacing="0">
         <td>
		  <table border="0" width="100%" cellspacing="0" cellpadding="2" bgcolor="E9E9E9">
            <tr> 
              <td width="5%">-</td>
              <td width="95%"><a HREF="/iri/ConsolaCentral.do" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('Consola Central');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Consola Central</a></td>
            </tr>
          </table>
        </td>
          </table>
        </td>
      </tr>
    </table>
  </div>
  </layer>
</div>
</layer>
<%}%>



<%-- **** salir **** --%>

<%if(arrp[37]!=null ){%>
<%if(arrp[37]!=null){PermisoBean p = (PermisoBean) arrp[37];%>
<layer id=OutN11 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
<div id=Out11 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%">
  <table border="0" width="100%" cellpadding="0" cellspacing="0">
    <tr> 
      <tD width="10%"><img src="images/bull_left0.gif"></td>
      <tD width="90%">
	  <a href="<%=p.getUrl()%>" target="_top" class="opctitLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
    </tr>
  </table>
</div>
</layer>
<%}%>
<%}%>




</div>
</layer>
<script>
if(nav_nombre =="Netsc")
{
	Netscape.visibility="SHOW";
	function clickHandler() 
	{
	
	  var targetId, srcElement, targetElement;
	  srcElement = window.event.srcElement;
	
	 if (srcElement.className == "OutlineN") 
	 {
		ElementosDelMenu = 20;
		for (h=1; h < ElementosDelMenu ; h++) 
		{
		     targetId = "OutN" + h +"details";
		     targetElement = document.all(targetId);
		     if (eval(targetElement) != null) 
				targetElement.visibility="show";			
		}
	 }
	 
	 
	 
	  if (srcElement.className == "OutlineN") 
	  {
	     targetId = srcElement.id + "details";
	     targetElement = document.all(targetId);
	     if (targetElement.visibility == "hide") 
	        targetElement.visibility = "show";
	      else 
	        targetElement.visibility = "hide";
	  }
	  
	}
	
	document.onclick = clickHandler;

}
else
{


		Explorer.style.visibility="visible";
		function clickHandler() 
		{
		  var targetId, srcElement, targetElement;
		  srcElement = window.event.srcElement;
		
		 if (srcElement.className == "Outline") 
		 {
			ElementosDelMenu = 20;
			for (h=1; h < ElementosDelMenu ; h++) 
			{
			     targetId = "Out" + h +"details";
			     targetElement = document.all(targetId);
			     if (eval(targetElement) != null) 
					targetElement.style.display = "none";	
			}
		 }
		 if (srcElement.className == "Outline") {
		     targetId = srcElement.id + "details";
		     targetElement = document.all(targetId);
		
		     if (targetElement.style.display == "none") 
		        targetElement.style.display = "";
		      else 
		        targetElement.style.display = "none";
		  }
		  
		}
		
		ElementosDelMenu = 20;

		for (h=1; h < ElementosDelMenu ; h++) 
		{
		     targetId = "Out" + h +"details";
		     targetElement = document.all(targetId);
		     if (eval(targetElement) != null) 
				targetElement.style.display = "none";	
		}
		
		Out1details.style.display='';
		document.onclick = clickHandler;

}
</script>
</body>
</html>
