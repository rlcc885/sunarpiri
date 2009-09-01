<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<HTML>
<HEAD>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
<TITLE></TITLE>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<script language="JavaScript"
	SRC="<%=request.getContextPath()%>/javascript/overlib.js"></script>
<script language="javascript">

function VentanaFlotante(pag)
{
	var ancho= 500;
	var alto= 563;
	NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
}

var arrSede = new Array();
var contador=0;

var arr4 = new Array();
var arr5 = new Array();
//Tarifario
var arr6 = new Array();//Tarifas por grupolibroarea
var costoMayor = "";
Navegador();
function doAdd1()
{
	var objeto = document.frm1.listbox1;
	var flag001=false;
	
	if (objeto.length != 0)
	{ 
		if (objeto.selectedIndex != -1)
		{
			/**Validacion de zonas**/
			/**Aqui se pone el orden que ocupa en la lista para la validacion**/
			if(document.frm1.comboAreaLibro.value=="6" //Vehicular
			   && (objeto.selectedIndex!=8
			   && objeto.selectedIndex!=10
               && objeto.selectedIndex!=2
                                                   && objeto.selectedIndex!=12
                                                   && objeto.selectedIndex!=0
                                                   && objeto.selectedIndex!=1
                                                   && objeto.selectedIndex!=3
                                                   && objeto.selectedIndex!=4
                                                   && objeto.selectedIndex!=5
                                                   && objeto.selectedIndex!=6
                                                   && objeto.selectedIndex!=7
                                                   && objeto.selectedIndex!=9
                                                   && objeto.selectedIndex!=11)
			   )
			{
				alert("La Zona Registral seleccionada no está disponible");
				return;
			}
			/****/
			for(var i=0; i < objeto.options.length ; ++i)
			{
				if (objeto.options[i].selected)
				{
					var g = objeto.options[i].value;
					var h = objeto.options[i].text;
					var flag = false;
					for (var j = 0; j < arrSede.length ; j++)
					{
						var estado = arrSede[j][0];
						var codigo = arrSede[j][1];
						if (estado != "**********" )
						{
							if (codigo == g)
							{
								flag = true;
							}
						}
					} 
					if (flag == false)
					{
						var arrx = new Array();
						arrx[0]="activo";
						arrx[1]=g;
						arrx[2]=h;
						arrSede[contador] = arrx;
						contador++;
						if (g=="01")
							flag001=true;
					}
				}
			} 
		}
	}
	  
	doFill1();
}
function doFill1()
{
var objeto = document.frm1.listbox2;
if (objeto.length != 0)
	{ 
		for(var i=0; i<objeto.options.length ; ++i)
			{
				objeto.options[i]=null;
						--i;
			}
    	}
var cuenta = 0;    	
	for (var j=0; j < arrSede.length; j++)
		{
		var activo = arrSede[j][0];
		var xTexto = arrSede[j][2];
		var xCode  = arrSede[j][1];
		if (activo != "**********")
			{
			objeto.options[objeto.options.length] = new Option(xTexto,j);
			<%-- 3ene2003: Buscar en Lima NO cuesta, por lo tanto no se cuenta
			               como sede para cobrar--%>
			<%-- 31jul2003: Buscar en Lima SI cuesta, por lo tanto si se cuenta
			               como sede para cobrar--%>			 
			 valor = document.frm1.comboAreaLibro.value;
			 <%--alert("codigo " + xCode);--%>
			 <%--alert("valor" + valor); --%>             
			 
			//if (xCode!="01")
				cuenta++;
			//else		
				//if(valor=="24000")
					//cuenta++;               				
			
			}
		} 

/*var xcosto = 0;

// 20040112 kuma, corregido problema de generacion secuencial y referencia por codigo
//if (cuenta >= arr5.length)
//if (cuenta >= arr6[document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].value].length)
if (cuenta >= arr6[document.frm1.comboAreaLibro.selectedIndex + 1].length)
	xcosto = costoMayor;
else
	xcosto = arr6[document.frm1.comboAreaLibro.selectedIndex + 1][cuenta][1];
	//xcosto = arr6[document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].value][cuenta][1];
	//xcosto = arr5[document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].value][cuenta];

	
<%--alert("costo " + xcosto);--%>
document.frm1.costo.value = xcosto;
alert("costo: "+document.frm1.costo.value);*/
}

function selectAllOptions(obj) {
	for (var i=0; i<obj.options.length; i++) {
		obj.options[i].selected = true;
	}
	doAdd1();
}
function removeAllOptions(obj2) {
	for (var i=0; i<obj2.options.length; i++) {
		obj2.options[i].selected = true;
	}
	doRemove1();
}

function doRemove1()
{
var objeto = document.frm1.listbox2;

if (objeto.length != 0)
  { 
     if (objeto.selectedIndex != -1)
     {
		for(var i=0; i < objeto.options.length ; ++i)
		{
	   		if (objeto.options[i].selected)
	   		{
	   			var g = objeto.options[i].value;
	   			//if (arrSede[g][1]=="35")
	   			//invisible_navegador(navegador,'area5','narea5','i');	
	       		arrSede[g][0]="**********";  
	       		objeto.options[i] = null;	       
	       		--i;
	   		}
        }           
     }
  }   
  
doFill1();
} 

function Cambio(valor)
{
	invisible_navegador(navegador,'area9','narea9','i');
    invisible_navegador(navegador,'area10','narea10','i');
	invisible_navegador(navegador,'area11','narea11','i');
	invisible_navegador(navegador,'area12','narea12','i');
	invisible_navegador(navegador,'area13','narea13','i');
	invisible_navegador(navegador,'area14','narea14','i');
	
	if (valor==21) 
	{
		invisible_navegador(navegador,'area9','narea9','i');
	    invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','v');
		invisible_navegador(navegador,'area13','narea13','i');
		invisible_navegador(navegador,'area14','narea14','i');
	}
	if (valor==20) 
	{
		invisible_navegador(navegador,'area9','narea9','v');
	    invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		invisible_navegador(navegador,'area13','narea13','i');
		invisible_navegador(navegador,'area14','narea14','i');
	}
	if (valor==17) 
	{
		invisible_navegador(navegador,'area9','narea9','i');
	    invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		invisible_navegador(navegador,'area13','narea13','v');
		if(document.frm1.resultado[0].checked)
		{ 
			invisible_navegador(navegador,'area14','narea14','i');
		}if(document.frm1.resultado[1].checked)
		{
			invisible_navegador(navegador,'area14','narea14','v');
		}
		
		
	}
	if (valor==18) 
	{
		invisible_navegador(navegador,'area9','narea9','i');
	    invisible_navegador(navegador,'area10','narea10','v');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		invisible_navegador(navegador,'area13','narea13','i');
		invisible_navegador(navegador,'area14','narea14','i');
	}
	if (valor==19) 
	{
		invisible_navegador(navegador,'area9','narea9','i');
	    invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','v');
		invisible_navegador(navegador,'area12','narea12','i');
		invisible_navegador(navegador,'area13','narea13','i');
		invisible_navegador(navegador,'area14','narea14','i');
	}
}
function ValidaCampo(campo, caract_extra, nulo) 
{
  if (nulo == 1 && campo == "") 
  {
    alert("ERROR: No puede dejar este campo vacio")
    return false
  }

  var ubicacion
  var enter = "\n"
  var caracteres = "abcdefghijklmnopqrstuvwxyzñ1234567890 ABCDEFGHIJKLMNOPQRSTUVWXYZÑáéíóúÁÉÍÓÚ()" + String.fromCharCode(13) + enter + caract_extra

  var contador = 0
  for (var i=0; i < campo.length; i++) 
  {
    ubicacion = campo.substring(i, i + 1)
    if (caracteres.indexOf(ubicacion) != -1) 
    {
      contador++
    }else 
    {
       return false;
    }
  }
  return true;
}

function doBuscar(param1, param2)
{
//************************************************************************************
	// SUNARP-RMC-BORRAR
	//inicio:dbravo:02/10/2007
	//descripcion: cambio para validar acceso a nuevos recursos
	//<%--
	//  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
	//  long perfilusuarioid =usuarioBean.getPerfilId();
	//%>  
	//if(noTieneAccesoRecursoRMC(10, <%=usuarioBean.getPerfilId()%>, '<%=usuarioBean.getUserId()--%>'))
	//{
	//	return;	
	//}
	//fin:dbravo:02/10/2007
//************************************************************************************
v = doSendChildren();
if (v==0)
{
	alert("Por favor seleccione al menos una sede");
	document.frm1.listbox1.focus();
	return;
}
	if (param1 == 1)
	{
		if (param2==1)
		{
			if(document.frm1.fechaInscripcionDesdeVeh.value=="" && document.frm1.fechaInscripcionHastaVeh.value=="" &&
			   document.frm1.marca.value=="" && document.frm1.modeloVehiculo.value=="" && document.frm1.anoFabriDesde.value=="" &&
			   document.frm1.anoFabriHasta.value=="" && document.frm1.color.value=="" && document.frm1.tipoVehiculo.value =="" &&
			   document.frm1.tipoCombustibleVeh.value=="")
			   {
			    alert("Debe ingresar al menos un criterio de búsqueda");
			   	return;
			   }
			
			var aux1 =  document.frm1.fechaInscripcionDesdeVeh.value;
			var aux2 =  document.frm1.fechaInscripcionHastaVeh.value;
			
			var aux3 =  document.frm1.fechaInscripcionDesdeVeh.value;
			var aux4 =  document.frm1.fechaInscripcionHastaVeh.value;
			
			var ano1;
			var mes1;
			var dia1;
			var ano2;
			var mes2;
			var dia2; 
	
			aux1 = aux1.split("/");
			aux2 = aux2.split("/");
			dia1 = aux1[0];
			dia2 = aux2[0];
			mes1 = aux1[1];
			mes2 = aux2[1];
			ano1 = aux1[2];
			ano2 = aux2[2];
			
			if (!contieneCarateresValidos(document.frm1.marca.value,"nombre"))
			{						
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.marca.focus();
				return;
			}
			if(document.frm1.anoFabriDesde.value!="" & document.frm1.anoFabriHasta.value!="")
			{
				if(document.frm1.anoFabriDesde.value > document.frm1.anoFabriHasta.value)
				{
					alert("El rango de año de fabricación invalido");
					document.frm1.anoFabriHasta.focus();
					return;	
				}
			}
			if(aux1!="")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionDesdeVeh.focus();
					return;
				}
			}
			if(aux2!="")
			{
				if(!esFecha(dia2,mes2,ano2))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionHastaVeh.focus();
					return;
				}
			}	
			if(aux1 != "" && aux2 != "")
			{
				if(rangoFecha(aux3, aux4))
				{
					alert("La fecha del campo Hasta debe ser mayor o igual a la fecha del campo Desde");
					return;
				}	
			}
			if (!ValidaCampo(document.frm1.modeloVehiculo.value,'-',0))
			{	
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.modeloVehiculo.focus();
				return;			
			}
			if (!contieneCarateresValidos(document.frm1.color.value,"nombre"))
			{						
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.color.focus();
				return;
			}
			if (!ValidaCampo(document.frm1.anoFabriDesde.value,'-',0))
			{						
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.anoFabriDesde.focus();
				return;
			}
			if (!ValidaCampo(document.frm1.anoFabriHasta.value,'-',0))
			{						
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.anoFabriHasta.focus();
				return;
			}
			document.frm1.action="<%=request.getContextPath()%>/PublicidadMasivaRelacionalIRI.do?state=busquedaVehicular";
			
		}
		if (param2 == 2)
		{
			if(document.frm1.fechaInscripcionDesdeAero.value=="" && document.frm1.fechaInscripcionHastaAero.value=="" && 
			   document.frm1.modelo.value=="" && document.frm1.tipoAero.value=="")
			{
				alert("Debe ingresar al menos un criterio de búsqueda");
				return;
			}
			var aux1 =  document.frm1.fechaInscripcionDesdeAero.value;
			var aux2 =  document.frm1.fechaInscripcionHastaAero.value;
			
			var aux3 =  document.frm1.fechaInscripcionDesdeAero.value;
			var aux4 =  document.frm1.fechaInscripcionHastaAero.value;
			
			var ano1;
			var mes1;
			var dia1;
			var ano2;
			var mes2;
			var dia2;
	
			aux1 = aux1.split("/");
			aux2 = aux2.split("/");
			dia1 = aux1[0];
			dia2 = aux2[0];
			mes1 = aux1[1];
			mes2 = aux2[1];
			ano1 = aux1[2];
			ano2 = aux2[2];
		
			if(aux1 != "")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionDesdeAero.focus();
					return;
				}
			}
			if(aux2 != "")
			{
				if(!esFecha(dia2,mes2,ano2))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionHastaAero.focus();
					return;
				}
			}
			if(aux1 != "" && aux2 != "")
			{
				if(rangoFecha(aux3, aux4))
				{
					alert("La fecha del campo Hasta debe ser mayor o igual a la fecha del campo Desde");
					return;
				}
				
			}
			if (!ValidaCampo(document.frm1.modelo.value,'-',0))
			{						
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.modelo.focus();
				return;
			}
			document.frm1.action="<%=request.getContextPath()%>/PublicidadMasivaRelacionalIRI.do?state=busquedaAeroNave";	
		}
		if (param2 == 3)
		{
			if(document.frm1.fechaInscripDesdeBuque.value=="" && document.frm1.fechaInscripHastaBuque.value=="" &&
			   document.frm1.nombreBuque.value=="" && document.frm1.capitaniaBuque.value=="")
			{
				alert("Debe ingresar al menos un criterio de búsqueda");
				return;
			}
			
			var aux1 =  document.frm1.fechaInscripDesdeBuque.value;
			var aux2 =  document.frm1.fechaInscripHastaBuque.value;
			
			var aux3 =  document.frm1.fechaInscripDesdeBuque.value;
			var aux4 =  document.frm1.fechaInscripHastaBuque.value;
			
			var ano1;
			var mes1;
			var dia1;
			var ano2;
			var mes2;
			var dia2;
	
			aux1 = aux1.split("/");
			aux2 = aux2.split("/");
			dia1 = aux1[0];
			dia2 = aux2[0];
			mes1 = aux1[1];
			mes2 = aux2[1];
			ano1 = aux1[2];
			ano2 = aux2[2];
		
			
			if(aux1!= "")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripDesdeBuque.focus();
					return;
				}
			}
			if(aux2!= "")
			{
				if(!esFecha(dia2,mes2,ano2))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripHastaBuque.focus();
					return;
				}	
			}
			if(aux1!= "" && aux2!= "")
			{
				if(rangoFecha(aux3, aux4))
				{
					alert("La fecha del campo Hasta debe ser mayor o igual a la fecha del campo Desde");
					return;
				}
			}
			if (!contieneCarateresValidos(document.frm1.nombreBuque.value,"nombre"))
			{						
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.nombreBuque.focus();
				return;
			}
			document.frm1.action="<%=request.getContextPath()%>/PublicidadMasivaRelacionalIRI.do?state=busquedaBuques";
		}
		if (param2 == 4)
		{
			if(document.frm1.fechaInscripcionDesdeEmb.value=="" && document.frm1.fechaInscripcionHastaEmb.value=="" &&
			   document.frm1.nombreEmbarcacion.value=="" && document.frm1.tipoEmbarcacion.value=="" && document.frm1.capitaniaEmbarcacion.value=="")
			{
				alert("Debe ingresar al menos un criterio de búsqueda");
				return;
			}
			
			var aux1 =  document.frm1.fechaInscripcionDesdeEmb.value;
			var aux2 =  document.frm1.fechaInscripcionHastaEmb.value;
			
			var aux3 =  document.frm1.fechaInscripcionDesdeEmb.value;
			var aux4 =  document.frm1.fechaInscripcionHastaEmb.value;
			
			var ano1;
			var mes1;
			var dia1;
			var ano2;
			var mes2;
			var dia2;
	
			aux1 = aux1.split("/");
			aux2 = aux2.split("/");
			dia1 = aux1[0];
			dia2 = aux2[0];
			mes1 = aux1[1];
			mes2 = aux2[1];
			ano1 = aux1[2];
			ano2 = aux2[2];
		
			if(aux1!= "")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionDesdeEmb.focus();
					return;
				}
			}
			if(aux2!= "")
			{
				if(!esFecha(dia2,mes2,ano2))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionHastaEmb.focus();
					return;
				}	
			}
			if(aux1!= "" && aux2!= "")
			{
				if(rangoFecha(aux3, aux4))
				{
					alert("La fecha del campo Hasta debe ser mayor o igual a la fecha del campo Desde");
					return;
				}
			}
			/*if(aux1!="" && aux2=="")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionDesdeEmb.focus();
					return;
				}
			}
			if(aux1=="")
			{
				if(!(aux2==""))
				{
					alert("Ingrese La Fecha Desde");
					return;
				}
			}*/
			if (!ValidaCampo(document.frm1.modelo.value,'-',0))
			{							
				alert("Por favor no ingrese caracteres no válidos");
				document.frm1.nombreEmbarcacion.focus();
				return;
			}
			document.frm1.action="<%=request.getContextPath()%>/PublicidadMasivaRelacionalIRI.do?state=busquedaEmbarcacionPesquera";
		}
		if (param2 == 5)
		{
			if(document.frm1.fechaInscripcionDesdeRMC.value=="" && document.frm1.fechaInscripcionHastaRMC.value=="" &&
			   document.frm1.montoDesdeRMC.value=="" && document.frm1.montoHastaRMC.value=="" )
			{
				alert("Debe ingresar al menos un criterio de búsqueda");
				return;
			}
			
			var aux1 =  document.frm1.fechaInscripcionDesdeRMC.value;
			var aux2 =  document.frm1.fechaInscripcionHastaRMC.value;
			
			var aux3 =  document.frm1.fechaInscripcionDesdeRMC.value;
			var aux4 =  document.frm1.fechaInscripcionHastaRMC.value;
			
			var ano1;
			var mes1;
			var dia1;
			var ano2;
			var mes2;
			var dia2;
	
			aux1 = aux1.split("/");
			aux2 = aux2.split("/");
			dia1 = aux1[0];
			dia2 = aux2[0];
			mes1 = aux1[1];
			mes2 = aux2[1];
			ano1 = aux1[2];
			ano2 = aux2[2];
		
			if(aux1!= "")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionDesdeRMC.focus();
					return;
				}
			}
			if(aux2!= "")
			{
				if(!esFecha(dia2,mes2,ano2))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionHastaRMC.focus();
					return;
				}
			}
			if(aux1!= "" && aux2!= "")
			{
				/*if(rangoFecha(aux3, aux4))
				{
					alert("La fecha del campo Hasta debe ser mayor o igual a la fecha del campo Desde");
					return;
				}*/
			}
			/*if(aux1!="" && aux2=="")
			{
				if(!esFecha(dia1,mes1,ano1))
				{
					alert("El formato de fecha no es el Correcto");
					document.frm1.fechaInscripcionDesdeRMC.focus();
					return;
				}
			}
			if(aux1=="")
			{
				if(!(aux2==""))
				{
					alert("Ingrese La Fecha Desde");
					return;
				}
			}*/
			if(document.frm1.montoHastaRMC.value!="" & document.frm1.montoDesdeRMC.value!="")
			{
				var auxDesde= parseFloat(document.frm1.montoDesdeRMC.value);
				var auxHasta= parseFloat(document.frm1.montoHastaRMC.value);
				
				if(auxHasta < auxDesde)
				{
					alert("El monto del campo Hasta debe ser mayor o igual al monto del campo Desde");
					return;
				}
			}
			
			/*if(document.frm1.montoHastaRMC.value != "" && document.frm1.montoDesdeRMC.value == "")
			{
				alert("Ingresar el Monto Desde");
				return;
			}*/
			if(checkDecimals(document.frm1.montoDesdeRMC, document.frm1.montoDesdeRMC.value))
			{
				return;
			}
			if(checkDecimals(document.frm1.montoHastaRMC, document.frm1.montoHastaRMC.value))
			{
				return;
			}
			
			document.frm1.action="<%=request.getContextPath()%>/PublicidadMasivaRelacionalIRI.do?state=busquedaRMC";
		}
	}
	document.frm1.submit();
	
}
function contieneCarateresInvalidos(cadena, cadenainvalida)
{
	if(!esEspacio(cadena))
	{
		cad= eval('cad'+ cadenainvalida);					   
		for(var i=0; i<cadena.length; i++){
			var caracter = cadena.substring(i,i+1);
			if (cad.indexOf(caracter) >0)
			{
				return true;}
			}
			return false;	
			}else{
					return true;
				}
}
function contieneCarateresValidos(cadena, cadenavalida)
{
	
		cad= eval('cad'+ cadenavalida);					   
			for(var i=0; i<cadena.length; i++)
			{
				var caracter = cadena.substring(i,i+1);
				if(!(caracter=="-"))
				{
					if (cad.indexOf(caracter) == -1)
					{
					return false;
					}
				}
			}
			return true;	

}
function esLongitudMayor(cadena,minimo) 						
{
	if(esEspacio)
	{
		if (cadena.length<minimo)
		{
			return false;				
		}else
		{
			return true;
		}
	}
}
function rangoFecha(cadena1, cadena2)
{
	var ano1;
	var mes1;
	var dia1;
	var ano2;
	var mes2;
	var dia2;
	
	cadena1 = cadena1.split("/");
	cadena2 = cadena2.split("/");
	dia1 = cadena1[0];
	dia2 = cadena2[0];
	mes1 = cadena1[1];
	mes2 = cadena2[1];
	ano1 = cadena1[2];
	ano2 = cadena2[2];
	
	if(!(ano2>=ano1))
	{
		return true;
	}else if(ano2==ano1)
	{
		if(!(mes2>=mes1))
		{
			return true;
		}else if(mes2==mes1)
		{
			if(!(dia2>=dia1))
			{
				return true;
			}
		}
	}
	return false;
}
function checkDecimals(fieldName, fieldValue) {

decallowed = 2;  // how many decimals are allowed?

if (isNaN(fieldValue)) {
alert("Debe ingresar sólo caracteres numéricos");
fieldName.select();
fieldName.focus();
return true;
}
else {
if (fieldValue.indexOf('.') == -1) fieldValue += ".";
dectext = fieldValue.substring(fieldValue.indexOf('.')+1, fieldValue.length);

if (dectext.length > decallowed)
{
alert ("El cantidad debe tener solo " + decallowed + " decimales.");
fieldName.select();
fieldName.focus();
return true;
      }

   }
}
function doSendChildren()
{   
	var v = 0;
   var cadena1="";
   for(var i=0; i< contador; ++i)
   {  
      if (arrSede[i][0]!="**********")
      	{
      		v++;
          cadena1 = cadena1 + arrSede[i][1] + "*"; //end of field
        }
   }   
   document.frm1.hid1.value=cadena1;
   return v;
}
function RefrescaCombos(codProv) 
{
	fbox = document.frm1.listbox1;
	var cod = codProv.substring(0,2);
	 for(var i=0; i < fbox.options.length; i++)
	  {
	  	if (fbox.options[i].value == cod) 
			fbox.options[i].selected=true;
	}
doAdd1();
}
function agrupacion(valor)
{
	alert(""+valor);
	document.frm1.radio.value = valor;
	if(document.frm1.comboAreaLibro.value=="17")
	{
		if(document.frm1.resultado[0].checked)
		{
	  		invisible_navegador(navegador,'area14','narea14','i');
		}
		if(document.frm1.resultado[1].checked)
		{
	  		invisible_navegador(navegador,'area14','narea14','v');
		}  
	}
	
}
function group(valor)
{
	document.frm1.radio.value = valor;
	if(document.frm1.comboAreaLibro.value=="17")
	{
		if(document.frm1.resultado[0].checked)
		{
	  		invisible_navegador(navegador,'area14','narea14','i');
		}
		if(document.frm1.resultado[1].checked)
		{
	  		invisible_navegador(navegador,'area14','narea14','v');
		}  
	}
}
var isIE = document.all?true:false;
var isNS = document.layers?true:false;
function onlyDigits(e,decReq) 
{
	var key = (isIE) ? window.event.keyCode : e.which;
	var obj = (isIE) ? event.srcElement : e.target;
	var isNum = (key > 47 && key < 58) ? true:false;
	var dotOK = (key==46 && decReq=='decOK' && (obj.value.indexOf(".")<0 || obj.value.length==0)) ? true:false;
	window.event.keyCode = (!isNum && !dotOK && isIE) ? 0:key;
	e.which = (!isNum && !dotOK && isNS) ? 0:key;
	return (isNum || dotOK);
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY onLoad="Cambio(document.frm1.comboAreaLibro.value);">
	<div id="maincontent">
		<div class="innertube">
		<table width="600px"><tr><td>
		
<form name="frm1" method="post"><input type="hidden" name="hid1">
<input type="hidden" name="hid2"> <input type="hidden"
	name="pidedis" value="S"> <br>
<table class="punteadoTabla" class=titulo cellspacing=0 width="100%">
	<tr>
		<td width="276" colspan="2"><b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Publicidad
		Masiva Relacional</font></b></td>
	</tr>
	<tr>
		<td width="276" align="right"><input type="radio" name="resultado" value="D" onclick="javascript:group('D');"><strong><font color="#666666">Detallado</font></strong></td>
		<td width="324"><input type="radio" name="resultado" value="C" checked="checked" onclick="javascript:group('C');"><strong><font color="#666666">Consolidado</font></strong></td>
	</tr>
</table>
<input type="hidden" name="cadena1" value="" /> <input type="hidden" name="radio" value="C" /> <br>
<table cellspacing=0>

	<tr>
		<td align=center><select multiple name="listbox1" size="10"
			width="260px" onDblClick="doAdd1()" style="width: 260px">
			<option value="05">Zona Registral No I - Sede Piura</option>
			<option value="11">Zona Registral No II - Sede Chiclayo</option>
			<option value="12">Zona Registral No III - Sede Moyobamba</option>
			<option value="09">Zona Registral No IV - Sede Iquitos</option>
			<option value="08">Zona Registral No V - Sede Trujillo</option>
			<option value="13">Zona Registral No VI - Sede Pucallpa</option>
			<option value="04">Zona Registral No VII - Sede Huaraz</option>
			<option value="02">Zona Registral No VIII - Sede Huancayo</option>
			<option value="01">Zona Registral No IX - Sede Lima</option>
			<option value="06">Zona Registral No X - Sede Cuzco</option>
			<option value="10">Zona Registral No XI - Sede Ica</option>
			<option value="03">Zona Registral No XII - Sede Arequipa</option>
			<option value="07">Zona Registral No XIII - Sede Tacna</option>
		</select></td>
		<td align=center>
		
		<input type="button" name="todos"
			value="&gt;&gt;" onclick="selectAllOptions(document.frm1.listbox1)"
			title="Seleccionar Todas las Oficinas Registrales"
			style="width: 25px"><br>
		<input type="button" name="uno" value="&gt;" onclick="doAdd1()"
			title="Seleccionar Oficina Registral" style="width: 25px"><br>
		<input type="button" name="runo" onClick="doRemove1()" value="&lt;"
			title="Retirar de la Seleccion la Oficina Registral"
			style="width: 25px"><br>
		<input type="button" name="rtodos" value="&lt;&lt;"
			onclick="removeAllOptions(document.frm1.listbox2)"
			title="Retirar de la Seleccion Todas las Oficinas Registrales"
			style="width: 25px"><br>
		</td>
		<td align=center><select multiple name="listbox2" size="10"
			style="width: 260px" width="260px" onDblClick="doRemove1()">
		</select></td>
	</tr>
</table>

<table class="punteadoTabla" class=formulario cellspacing=0 width="100%">
	<tr>
		<td align="left" vAlign="left"><layer id="narea5"
			visibility="hide">
		<div id="area5"
			style="visibility: hidden; background-color: FFFFDD; width: 430px; border-width: 1px; border-color: 000000; border-style: solid">
		Vehículos registrados a nivel nacional.</div>
		</layer></td>
	</tr>
</table>


<table class="punteadoTabla" class=formulario cellspacing=0 width="100%">
	<tr>
		<td align="left"><layer id="narea6">
		<div id="area6" style="visibility: visible"><a
			href="javascript:VentanaFlotante('<%=request.getContextPath()%>/acceso/mapas/MAPA1.htm')"
			onmouseover="javascript:mensaje_status('Identifique su Oficina Registral');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><font color="#666666">Identifique
		su Oficina Registral</font></a></div>
		</layer></td>
	</tr>
</table>
<table class="punteadoTabla" cellspacing=0 width="100%">
	<tr>
		<td width="100%" class=formulario style="width: 598px" align="center">Area
		Registral: <select name="comboAreaLibro" size="1"
			onchange=Cambio(this.value);>
			<logic:iterate name="arrAreaLibro" id="item22" scope="request">
				<logic:equal name="item22" property="codigo" value="1">
					<option value="<bean:write name="item22" property="codigo"/>"
						selected><bean:write name="item22" property="descripcion" /></option>
				</logic:equal>
				<logic:notEqual name="item22" property="codigo" value="1">
					<option value="<bean:write name="item22" property="codigo"/>"><bean:write
						name="item22" property="descripcion" /></option>
				</logic:notEqual>
			</logic:iterate>
		</select></td>
	</tr>
</table>
<%
	String positionLeft = "10";
	String positionTop = "260";
%> <%-- *******************    DIV 9                *************************** --%>

<layer id="narea9">
<div id="area9" style="visibility:visible;display:">
<table class="punteadoTabla" class=cabeceraformulario cellspacing=0 width="100%">
	<tr bordercolor="AFAFAF" bgcolor="D7D7D7">
		<td width="673" colspan="9">
		<p align="left"><font color="132E4C"><strong>B&uacute;squeda
		Registro Embarcaciones Pesqueras</strong></font>
		</td>
	</tr>
</table>
<table class="punteadoTabla" class=formulario cellspacing=2 width="100%">
	<tr>
		<td width="260">Fecha de Inscripcion Desde</td>
		<td width="187"><input type="text"
			name="fechaInscripcionDesdeEmb" style="width: 133" maxlength="15"
			onblur="sololet(this)"></td>
			<td width="135" align="left">Hasta<input type="text"
			name="fechaInscripcionHastaEmb" style="width: 133" maxlength="100"
			onblur="sololet(this)"></td>
		<td width="90" align="center">
		
		<!-- A href="javascript:doBuscar(1,4)"
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Embarcaciones Pesqueras');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><img
			border=0 src="images/btn_buscar2.gif">
			</a-->
			<input type="button" class="formbutton" value="Buscar" 
			onclick="javascript:doBuscar(1,4)" onmouseover="javascript:mensaje_status('Busqueda de Registro de Embarcaciones Pesqueras');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;" />
			</td>
	</tr>
	<tr>
		<td width="260">Nombre de la Embarcación</td>
		<td width="187"><input type="text" name="nombreEmbarcacion"
			style="width: 133" maxlength="40" onblur="sololet(this)"></td>
		<td>&nsp;</td>
		<td>&nsp;</td>
	</tr>
	<tr>
		<td width="260">Tipo Embarcación</td>
		<td width="187"><SELECT name="tipoEmbarcacion">
			<option value="">Seleccione un Tipo</option>
			<logic:iterate name="tipoEmbPesquera" id="item11" scope="request">
				<option value="<bean:write name="item11" property="codigo"/>"><bean:write
					name="item11" property="descripcion" /></option>
			</logic:iterate>
		</SELECT></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>

	</tr>
	<tr>
		<td width="260">Capitania</td>
		<td width="187"><SELECT name="capitaniaEmbarcacion">
			<option value="">Seleccione Una Capitania</option>
			<logic:iterate name="capitania" id="item11" scope="request">
				<option value="<bean:write name="item11" property="codigo"/>"><bean:write
					name="item11" property="descripcion" /></option>
			</logic:iterate>
		</SELECT></td>
		
				<td>&nbsp;</td>
				<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Tipo Acto Causal</td>
		<td width="187"><SELECT name="tipoActoEmbarcacion">
			<option value="T">TODOS</option>
			<option value="GRA">GRAVAMENES</option>
			<option value="CAN">CANCELACIONES</option>
		</SELECT></td>
		<td width="53"></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
	</tr>
</table>


</div>
</layer> <%-- *******************    DIV 10                *************************** --%>
<layer id="narea10">
<div id="area10" style="visibility:visible;display:">
<table class="punteadoTabla" class=cabeceraformulario cellspacing=0 width="100%">
	<tr bordercolor="AFAFAF" bgcolor="D7D7D7">
		<td><font color="132E4C"><strong>B&uacute;squeda
		Registro Buques</strong></font></td>
	</tr>
</table>
<table class="punteadoTabla" class=formulario cellspacing=0  width="100%">
	<tr>
		<td width="210">Fecha de Inscripcion Desde</td>
		<td width="128"><input type="text" name="fechaInscripDesdeBuque"
			style="width: 133" maxlength="15" onblur="sololet(this)"></td>
		<%--<td width="113"><input type="button" value="Buscar" onclick="doBuscar(3,50);"></td>--%>
		<td width="140" align="center">Hasta</td>
		<td width="135"><input type="text" name="fechaInscripHastaBuque"
			style="width: 133" maxlength="100" onblur="sololet(this)"></td>
		<td width="90">
		<!-- A href="javascript:doBuscar(1,3)"
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Buques');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><img
			border=0 src="images/btn_buscar2.gif"></a-->
			
			<input type="button" class="formbutton" value="Buscar" onclick="javascript:doBuscar(1,3)" 
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Buques');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;" />
			
			</td>
	</tr>
	<tr>
		<td width="210">Nombre del Buque</td>
		<td width="128"><input type="text" name="nombreBuque"
			style="width: 133" maxlength="15" onblur="sololet(this)"></td>
					<td>&nbsp;</td>
							<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="210">Capitania</td>
		<td width="128"><SELECT name="capitaniaBuque">
			<option value="">Seleccione Una Capitania</option>
			<logic:iterate name="capitania" id="item11" scope="request">
				<option value="<bean:write name="item11" property="codigo"/>"><bean:write
					name="item11" property="descripcion" /></option>
			</logic:iterate>
		</SELECT></td>
				<td>&nbsp;</td>
						<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="210">Tipo Acto Causal</td>
		<td width="128"><SELECT name="tipoActoBuque">
			<option value="T">TODOS</option>
			<option value="GRA">GRAVAMENES</option>
			<option value="CAN">CANCELACIONES</option>
		</SELECT></td>
				<td>&nbsp;</td>
						<td>&nbsp;</td>
	</tr>
</table>


</div>
</layer> <%-- *******************    DIV 11  *************************** --%>
<layer id="narea11">
<div id="area11" style="visibility:visible;display:">

<table class="punteadoTabla" class=cabeceraformulario cellspacing=0 width="100%">
	<tr bordercolor="AFAFAF" bgcolor="D7D7D7">
		<td width="673">
		<p align="left"><font color="132E4C"><strong>B&uacute;squeda
		Registro Aeronaves</strong></font>
		</td>
	</tr>
</table>
<table class="punteadoTabla"  class=formulario cellspacing=2 width="100%">
	<tr>
		<td width="260">Fecha de Inscripcion Desde</td>
		<td width="155"><input type="text"
			name="fechaInscripcionDesdeAero" style="width: 133" maxlength="10"
			onblur="sololet(this)"></td>
		<td align="center" width="77">Hasta</td>
		<td width="135"><input type="text"
			name="fechaInscripcionHastaAero" style="width: 133" maxlength="10"
			onblur="sololet(this)"></td>
		<td width="90">
		
		<!-- A href="javascript:doBuscar(1,2)"
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Aeronaves');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><img
			border=0 src="images/btn_buscar2.gif"></a-->
			
				<input type="button" class="formbutton" value="Buscar" onclick="javascript:doBuscar(1,2)" 
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Aeronaves');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"/>
			
			</td>
	</tr>
	<tr>
		<td width="260">Tipo de AeroNave</td>
		<td width="155"><SELECT name="tipoAero">
			<option value="">Seleccione un Tipo</option>
			<logic:iterate name="tipoAeronave" id="item11" scope="request">
				<option value="<bean:write name="item11" property="codigo"/>"><bean:write
					name="item11" property="descripcion" /></option>
			</logic:iterate>
		</SELECT></td>
		<td width="77"></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Modelo</td>
		<td width="155"><input type="text" name="modelo" style="width: 133"
			maxlength="100" onblur="sololet(this)"></td>
		<td width="77"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td width="260">Tipo Acto Causal</td>
		<td width="155"><SELECT name="tipoActoAero">
			<option value="T">TODOS</option>
			<option value="GRA">GRAVAMENES</option>
			<option value="CAN">CANCELACIONES</option>
		</SELECT></td>
		<td width="77"></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
</div>
</layer> <%-- *******************    DIV 12               *************************** --%>
<layer id="narea12">
<div id="area12" style="visibility:visible;display:">

<table class="punteadoTabla" class=cabeceraformulario cellspacing=0>
	<tr bordercolor="AFAFAF" bgcolor="D7D7D7">
		<td width="673">
		<p align="left"><font color="132E4C"><strong>Registro
		Mobiliario de Contratos</strong></font>
		</td>
	</tr>
</table>
<table class="punteadoTabla" class=formulario cellspacing=2>
	<tr>
		<td width="260">Fecha de Inscripcion Desde</td>
		<td width="135"><input type="text"
			name="fechaInscripcionDesdeRMC" style="width: 133" maxlength="10"
			onblur="sololet(this)"></td>
		<td width="100" align="center">Hasta</td>
		<td width="150"><input type="text"
			name="fechaInscripcionHastaRMC" style="width: 133" maxlength="10"
			onblur="sololet(this)"></td>
		<td width="90">
		<!-- A href="javascript:doBuscar(1,5)"
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Moviliario de Contrato');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><img
			border=0 src="images/btn_buscar2.gif"></a-->
			
			<input type="button" class="formbutton" value="Buscar" onclick="javascript:doBuscar(1,5)" 
			onmouseover="javascript:mensaje_status('Busqueda de Registro de Moviliario de Contrato');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;">
			
			</td>
	</tr>
	<tr>
		<td width="260">Tipo Acto Causal</td>
		<td><SELECT name="tipoActoRMC">
			<option value="T">TODOS</option>
			<option value="GRA">GRAVAMENES</option>
			<option value="CAN">CANCELACIONES</option>
		</SELECT></td>
	</tr>
	<tr>
		<td width="260">Monto de la Garantia Desde</td>
		<td><input type="text" name="montoDesdeRMC" style="width: 133"
			maxlength="100" onblur="sololet(this)" onkeypress="onlyDigits(event,'decOK')"></td>
		<td width="100" align="center">Hasta</td>
		<td><input type="text" name="montoHastaRMC" style="width: 133"
			maxlength="100" onblur="sololet(this)" onkeypress="onlyDigits(event,'decOK')"></td>
	</tr>
</table>
</div>
</layer> <%-- *******************    DIV 13               *************************** --%>
<layer id="narea13">
<div id="area13" style="visibility:visible;display:">

<table class="punteadoTabla" class=cabeceraformulario cellspacing=0 width="100%">
	<tr bordercolor="AFAFAF" bgcolor="D7D7D7">
		<td width="673">
		<p align="left"><font color="132E4C"><strong>Registro
		de Propiedad Vehicular</strong></font>
		</td>
	</tr>
</table>
<table class="punteadoTabla" class=formulario cellspacing=2 width="100%">
	<tr>
		<td width="260">Marca</td>
		<td width="146"><input type="text" name="marca"
			style="width: 133" maxlength="35" onblur="sololet(this)"></td>
		<td align="right" width="136">
		<!-- A href="javascript:doBuscar(1,1)"
			onmouseover="javascript:mensaje_status('Busqueda del Registro Vehicular');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"><img
			border=0 src="images/btn_buscar2.gif"></a-->
			
			<input type="button" class="formbutton" value="Buscar" onclick="javascript:doBuscar(1,1)" 
			onmouseover="javascript:mensaje_status('Busqueda del Registro Vehicular');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;">
			
			</td>
			<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Modelo</td>
		<td width="146"><input type="text" name="modeloVehiculo"
			style="width: 133" maxlength="35" onblur="sololet(this)"></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Año de Fabricación Desde</td>
		<td width="146"><input type="text" name="anoFabriDesde"
			style="width: 133" maxlength="4" onblur="sololet(this)" onkeypress="onlyDigits(event,'noDec')"></td>
		<td align="center" width="130">Hasta</td>
		<td width="186"><input type="text" name="anoFabriHasta"
			style="width: 133" maxlength="4" onblur="sololet(this)" onkeypress="onlyDigits(event,'noDec')"></td>
	</tr>
	<tr>
		<td width="260">Fecha de Inscripción Desde</td>
		<td width="146"><input type="text"
			name="fechaInscripcionDesdeVeh" style="width: 133" maxlength="15"
			onblur="sololet(this)"></td>
		<td align="center" width="130">Hasta</td>
		<td width="186"><input type="text"
			name="fechaInscripcionHastaVeh" style="width: 133" maxlength="100"
			onblur="sololet(this)"></td>
	</tr>
	<tr>
		<td width="260">Tipo Vehiculo(clase)</td>
		<td width="146"><SELECT name="tipoVehiculo">
			<option value="">Seleccione un Tipo</option>
			<logic:iterate name="tipoVehiculo" id="item11" scope="request">
				<option value="<bean:write name="item11" property="codigo"/>"><bean:write
					name="item11" property="descripcion" /></option>
			</logic:iterate>
		</SELECT></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Color</td>
		<td width="146"><input type="text" name="color"
			style="width: 133" maxlength="100" onblur="sololet(this)"></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Tipo de Combustible</td>
		<td width="146"><SELECT name="tipoCombustibleVeh">
			<option value="">Seleccione el Tipo</option>
			<logic:iterate name="tipoCombustible" id="item11" scope="request">
				<option value="<bean:write name="item11" property="codigo"/>"><bean:write
					name="item11" property="descripcion" /></option>
			</logic:iterate>
		</SELECT></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="260">Tipo Acto Causal</td>
		<td width="146"><SELECT name="tipoActoVeh">
			<option value="T">TODOS</option>
			<option value="GRA">GRAVAMENES</option>
			<option value="CAN">CANCELACIONES</option>
		</SELECT></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
</div>
</layer> <layer id="narea14">
<div id="area14" style="visibility:visible;display:">
<table class="punteadoTabla" class=formulario cellspacing=2 width="100%">
	<tr>
		<td width="256">Consolidado agrupado por:</td>
		<td width="342"><select name="agrupacion">
			<option value="1">Sin sub-agrupación</option>
			<option value="2">Marca</option>
			<option value="3">Modelo</option>
			<option value="4">Año de Fabricación</option>
			<option value="5">Fecha de Inscripción</option>
			<option value="6">Tipo de Vehiculo</option>
			<option value="7">Color</option>
			<option value="8">Tipo de Combustible</option>
		</select></td>
		
	</tr>
</table>
</div>
</layer></form>

			</td></tr></table>
			
</div></div>
</BODY>
</HTML>



