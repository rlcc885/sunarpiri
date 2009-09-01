<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.acceso.bean.*" %>

<%
//recoger arreglo de permisos
PermisoBean arrp[] = (PermisoBean[]) request.getAttribute("arregloPermisos");
%>
<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css">
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
	//window.open("acceso/menuInformacion.html","Informacion",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=555,height=400');
	<%-- window.open("acceso/win_premio.html","Premio",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=190,height=258'); --%>

	function informacion(){
	window.open("acceso/menuInformacion.html","Informacion",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=555,height=400');
	}
	<%--function Premio(){
	window.open("acceso/win_premio.html","Premio",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=190,height=258');
	}--%>

</script>
</head>

<!-- body background="images/bk_left.gif"-->
<body>




<div id="framecontentLeft">
	<div class="innertube">
		
		<img src="images/iri/logotipo.jpg">

		<div id="navMenu">
			<div id="Explorer" style="visibility: visible">			
					
					<!-- SERVICIOS DE BUSQUEDA -->
					<ul>
					<%if(arrp[7]!=null || arrp[8]!=null || arrp[9]!=null || arrp[72]!=null)
					{%>
							<%System.out.println("perfilusuarioid-->"+perfilusuarioid);%>
							<%System.out.println("Constantes.PERFIL_ADMIN_ORG_EXT-->"+Constantes.PERFIL_ADMIN_ORG_EXT);%>
							<%System.out.println("Constantes.PERFIL_AFILIADO_EXTERNO-->"+Constantes.PERFIL_AFILIADO_EXTERNO);%>
							<%System.out.println("Constantes.PERFIL_INDIVIDUAL_EXTERNO-->"+Constantes.PERFIL_INDIVIDUAL_EXTERNO);%>
							<%System.out.println("usuarioBean.getExonPago()"+usuarioBean.getExonPago());%>
							<li><a href="#" onclick="javascript:clickHandler1('1')" target="_self">Servicios</a></li>
					    	<ul>       
								<layer id=OutN1details visibility="show" LEFT=1px POSITION= relative TOP= 5px WIDTH= 100%> 
									<div id=Out1details> 
				        				<!-- Copia de la 8 -->
								        <%if(arrp[87]!=null){PermisoBean p = (PermisoBean) arrp[87];%>
							            	<li>
								           		<a href="<%=p.getUrl()%>" target="main_frame1" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
								          	</li>
										<%}%>
										<!-- 9 -->
							         
							            <%if(arrp[90]!=null){PermisoBean p = (PermisoBean) arrp[90];%>
				<li>
	             <a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
	            	</li>
	            <%}%>  
								        <!-- Copia de la 9 -->
							            <%if(arrp[85]!=null){PermisoBean p = (PermisoBean) arrp[85];%>
							              	<li>
								              	<a href="<%=p.getUrl()%>" target="main_frame1" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
								              		<%=p.getDesc()%>
								              	</a>
								            </li>
							            <%}%>  
								        <!-- Copia de la 7 -->
				        				<%if(arrp[86]!=null){PermisoBean p = (PermisoBean) arrp[86];%>
										   	<li>
								           		<a href="<%=p.getUrl()%>" target="main_frame1" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
								          	</li>
								        <%}%>        
								        <!-- Copia de la 59 -->
										<%if((arrp[88]!=null)&&(((perfilusuarioid == Constantes.PERFIL_ADMIN_ORG_EXT)||(perfilusuarioid == Constantes.PERFIL_AFILIADO_EXTERNO)||(perfilusuarioid == Constantes.PERFIL_INDIVIDUAL_EXTERNO)))){PermisoBean p = (PermisoBean) arrp[88];%>
										<%//FIRME//if((arrp[88]!=null)&&(((perfilusuarioid == Constantes.PERFIL_ADMIN_ORG_EXT)||(perfilusuarioid == Constantes.PERFIL_AFILIADO_EXTERNO)||(perfilusuarioid == Constantes.PERFIL_INDIVIDUAL_EXTERNO))&&((!usuarioBean.getExonPago())))){PermisoBean p = (PermisoBean) arrp[88];%>
											<li>
												<a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
											</li>
							            <%}%>   
							            <%if(arrp[91]!=null){PermisoBean p = (PermisoBean) arrp[91];%>
							            <li>
							            <a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
							            </li>
										<%}%>  
										 <%if(arrp[92]!=null){PermisoBean p = (PermisoBean) arrp[92];%>
							            <li>
							            <a href="<%=p.getUrl()%>" target="main_frame1" class="opcLeft" onmouseover="javascript:mensaje_status('<%=p.getDesc()%>');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=p.getDesc()%></a>
							            </li>
										<%}%>           
									</div>
						  		</layer>
						  	</ul>
					<%}%>
					<!-- 18 =  editar datos personales -->
					<%if(arrp[18]!=null && (perfilusuarioid != Constantes.PERFIL_ADMIN_ORG_EXT)) {%>
					  <li><a href="#" onclick="javascript:clickHandler1('2')" target="_self">Datos Personales</a></li>
					  <ul>
					  </ul>
					<%}%>

					<!-- 1 = incrementar saldo -->
					<%if(arrp[1]!=null){%>
					<%if(perfilusuarioid != Constantes.PERFIL_ADMIN_ORG_EXT) {%>
					<%}else{%>
					<%}%>
  					<li><a href="#" onclick="javascript:clickHandler1('3')" target="_self">Prepagar</a></li>
						<ul>
						</ul>
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
     arrp[47]!=null || 
     arrp[50]!=null ||
     arrp[51]!=null ||
     arrp[65]!=null || 
     arrp[46]!=null ||
     arrp[68]!=null ||
     arrp[69]!=null ||
     arrp[71]!=null ||
     arrp[75]!=null 
     )
     flagRpt = true;
%>

<!-- menu adicional reportes -->
<%if(flagRpt==true) {%>
  <li>
  	<a href="#" onclick="javascript:clickHandler1('9')" target="_self">Reportes</a>
  </li>
  <ul>  
  </ul>
<%}%>


<%-- consola central --%>
<%if(perfilusuarioid==Constantes.PERFIL_ADMIN_GENERAL || 
     perfilusuarioid==Constantes.PERFIL_ADMIN_JURISDICCION)     {%>
<layer id=OutN10 LEFT=0px POSITION= relative TOP= 5px WIDTH= 100%> 
	<div id=Out10 style="LEFT: 0px; POSITION: relative; TOP: 5px; WIDTH: 100%"> 
  		<li><a href="#" onclick="javascript:clickHandler1('10')" target="_self">Consola Central</a></li>
  		<layer id=OutN10details visibility="show" LEFT=0px POSITION= relative>  
  		<div id=Out10details style="display: 0; LEFT: 0px; POSITION: relative"> 
		    <ul>
		       	<li>
		       		<a HREF="<%=request.getContextPath()%>/ConsolaCentral.do" target="main_frame1" class="opcleft" onmouseover="javascript:mensaje_status('Consola Central');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Consola Central</a>
		       	</li>
		    </ul>
  </div>
  </layer>
</div>
</layer>
<%}%>



<%-- **** salir **** --%>

<%if(arrp[37]!=null ){%>
<%if(arrp[37]!=null){PermisoBean p = (PermisoBean) arrp[37];%>
<layer id=OutN11 > 
<div id=Out11 >
	
</div>
</layer>
<%}%>
<%}%>
</ul>

</div>
</layer>
</div>

<img src="images/iri/mapa.jpg">

</div>
</div>

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
		  //alert("srcElement=" + srcElement + "-->srcElement.className=" + srcElement.className + "-->srcElement.id"+srcElement.id);
		  
		
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
		
		
		//Out1details.style.display='';
		//document.onclick = clickHandler;

}




	function clickHandler1(id) 
	{
		ElementosDelMenu = 20;
		for (h=1; h < ElementosDelMenu ; h++) 
		{
		     if (eval(id)!=eval(h)){
		     	 targetId = "Out" + h +"details";
			     targetElement = document.all(targetId);
			     if (eval(targetElement) != null) 
					targetElement.style.display = "none";	
			 }
		}
		
		var targetId, targetElement;
		targetId = "Out" + id + "details";
		targetElement = document.all(targetId);
		//alert("targetId " + targetId + "-->" + targetElement.style.display);
		if (targetElement.style.display == "none") 
		   targetElement.style.display = "";
		else 
		   targetElement.style.display = "none";
	}




</script>
</body>
</html>
