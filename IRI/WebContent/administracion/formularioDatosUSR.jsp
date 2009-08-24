<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- 
	PANTALLA PARA CREACION/EDICION DE USUARIOS
	
	AG - CREACION/EDICION
	AJ - CREACION/EDICION
	AO - CREACION/EDICION
	CA - SOLAMENTE CREACION
--%>

<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%

String xza = (String) request.getAttribute("perfilId");
long perfilId = Long.parseLong(xza);
DatosUsuarioBean datosUsuarioBean = (DatosUsuarioBean) request.getAttribute("DATOS_FORMULARIO");

// modo, valores:
//        0  :  INSERCION-muestra pagina vacia para ingreso de nuevos valores
//        1  :  INSERCION-hubo un error y muestra pagina con datos cargados
//              para que el usuario corrija su error
//        2  :  ACTUALIZACION-con datos precargados
xza = (String) request.getAttribute("modo");
int modo = Integer.parseInt(xza);

if (datosUsuarioBean != null)
	{
		if (modo==0)
			modo=1;
	}
//DESCAJ 03/01/2007 IFIGUEROA
long perfilIdUsuario = 0;
if(datosUsuarioBean == null){
	perfilIdUsuario = 0;
} else {
	String strPerfilIdUsuario = datosUsuarioBean.getPerfilId();
	try{
		perfilIdUsuario = Integer.parseInt(strPerfilIdUsuario);
	}catch(NumberFormatException e){
 		perfilIdUsuario = 0;
	}
}
%>
<html>

<head>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<title></title>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<script language="javascript">
//_m<%=modo%>_p<%=perfilId%>_
/*
arreglo hijo (provincia, que depende del combo padre DEPARTAMENTO)

	Id Provincia,  Descripci&oacute;n Provincia,  Id Departamento
*/

var arr2 = new Array();
<% int k = 0; %>
<logic:iterate name="arrProvincia" id="itemp" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="itemp" property="value01"/>"; //id provincia
	arrx[1]="<bean:write name="itemp" property="value02"/>"; //descripcion provincia
	arrx[2]="<bean:write name="itemp" property="value03"/>"; //id departamento
	arr2[<%=k%>]=arrx;
	<%  k++; %>
</logic:iterate>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- *****************FUNCIONALIDAD NO SOPORTADA EN PANTALLA DE CAJERO   ***************** --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%if (perfilId != Constantes.PERFIL_CAJERO) {%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
//creacion de array de permisos
var arrUniversoPermisos = new Array();
<% int x = 0; %>
<logic:iterate name="arrPermisos" id="permiso" scope="request">
	var arrz = new Array();
	arrz[0]="<bean:write name="permiso" property="value01"/>"; <%--id Perfil recibidor--%>
	arrz[1]="<bean:write name="permiso" property="value02"/>"; <%--permiso id--%>
	arrz[2]="<bean:write name="permiso" property="value03"/>"; <%--permiso descripcion--%>
	arrz[3]="activo";
	arrUniversoPermisos[<%=x%>]=arrz;
	<%  x++; %>
</logic:iterate>

<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) {%>
<% x = 0; %>
var arrPerfiles = new Array();
<logic:iterate name="arrPerfiles" id="perfil" scope="request">
	var arrz = new Array();
	arrz[0] = '<bean:write name="perfil" property="value01"/>';
	arrz[1] = '<bean:write name="perfil" property="value02"/>';
	arrz[2] = '<bean:write name="perfil" property="value03"/>';
	arrPerfiles[<%=x%>] = arrz;
	<%  x++; %>	
</logic:iterate>
<%   }  %>


//Array de permisos actuales y que deben ser pre-cargados 
//al mostrar la pagina
var arrPermisosActuales = new Array();
var aCont=0;
<%if (datosUsuarioBean != null)
  {
  String[] arrx = datosUsuarioBean.getArrPermisoId();
  String[] arry = datosUsuarioBean.getArrPermisoDesc();
  if (arrx!=null)
  	{
  	for(int w=0; w<arrx.length; w++)
  		{%>
  		
  		var arrz = new Array();
		arrz[0] = '<%=arrx[w]%>';
		arrz[1] = '<%=arry[w]%>';
		arrPermisosActuales[aCont] = arrz;
		aCont++;
		
<%		}	
	}
  }%>

var contador=0;
var arrPermisosExtras = new Array();

function doAdd1()
{
var objeto = document.frm1.listbox1;

//busca seleccionados
  if (objeto.length != 0)
  { 
     if (objeto.selectedIndex != -1)
     {
		for(var i=0; i < objeto.options.length ; ++i)
		{
	   		if (objeto.options[i].selected)
	   		{
	   			var g = objeto.options[i].value;
	   			var h = objeto.options[i].text;
	 		     
	 		     //verificar que no exista en el arreglo destino
	 		     var flag = false;
	 		     for (var j = 0; j < arrPermisosExtras.length ; j++)
	 		     		{
	 		     			var estado = arrPermisosExtras[j][0];
	 		     			var codigo = arrPermisosExtras[j][1];
	 		     			if (estado != "**********" )
	 		     				{
   		     					if (codigo == g)
	 		     							flag = true;
	 		     				}
	 		     		} //for j
	 		     		
	 		     	//agregar a arreglo destino solo si no es duplicado
	 		     	if (flag == false)
	 		     	  {
						var arrx = new Array();
				     	arrx[0]="activo";
				     	arrx[1]=g;
				     	arrx[2]=h;
							arrPermisosExtras[contador] = arrx;
					     contador++;		 		     		
						}//if (flag == false)
						
				//eliminar permiso de lista de permisos
				for (var m = 0; m < arrUniversoPermisos.length; m++)
					{
						if (arrUniversoPermisos[m][1]==g)
							arrUniversoPermisos[m][3]="**********";
					}//for (var m = 0; m < arrUniversoPermisos.length; m++)
	   		}//if (objeto.options[i].selected)
        } //for i
     }
  }  
doFill0();
doFill1();
} // fin metodo doAdd1
//*******************************************************************************************
function doRemove1()
{
//obtener codigo de papa
var codigoPapa ="";
<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) {%>
var obj1;
obj1 = document.frm1.perfiles;  //papa

for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa = obj1.options[i].value;
				break;
			}
	}//for
<%   }  else { %>	
codigoPapa = '<%=Constantes.PERFIL_AFILIADO_EXTERNO%>';
<%   }%>
	
var objeto = document.frm1.listbox2;	
//borrar del arreglo los elementos seleccionados
if (objeto.length != 0)
  { 
     if (objeto.selectedIndex != -1)
     {
		for(var i=0; i < objeto.options.length ; ++i)
		{
	   		if (objeto.options[i].selected)
	   		{
	   			var g = objeto.options[i].value;
	       		arrPermisosExtras[g][0]="**********";  // marcarlo como "inactivo"
	       		objeto.options[i] = null;	       
	       		--i;
	       		
	       		var xid = arrPermisosExtras[g][1];
				for (var m = 0; m < arrUniversoPermisos.length; m++)
					{
						if (arrUniversoPermisos[m][1]==xid && arrUniversoPermisos[m][0]==codigoPapa)
							arrUniversoPermisos[m][3]="activo";
					}//for (var m = 0; m < arrUniversoPermisos.length; m++)
	   		}//if
        }// for var i
     }
  }   
doFill0();  
doFill1();
} // function doRemove1
//***************************************************************************************************
function doFill1()
{
var objeto = document.frm1.listbox2;

//borrar contenido actual de objeto
	if (objeto.length != 0)
		{ 
		for(var i=0; i<objeto.options.length ; ++i)
			{
				objeto.options[i]=null;
						--i;
			}
    	}
  	
//llenar objeto con elemento activos del arreglo
	for (var j=0; j < arrPermisosExtras.length; j++)
		{
			var activo = arrPermisosExtras[j][0];
			var xTexto = arrPermisosExtras[j][2];
			
			if (activo != "**********")
				objeto.options[objeto.options.length] = new Option(xTexto,j);
		} //for
}
//***************************************
function doSendChildren()
{   
   var cadena1="";
   var cadena2="";
   for(var i=0; i< contador; ++i)
   {  
      if (arrPermisosExtras[i][0]!="**********")
      	{
          cadena1 = cadena1 + arrPermisosExtras[i][1] + "*"; //end of field
          //cadena1 = cadena1 + arrSede[i][2] + "$"; //end of record
          
          cadena2 = cadena2 + arrPermisosExtras[i][2] + "*"; 
        }
   }   
   document.frm1.hidPermisos.value=cadena1;
   document.frm1.hidPermisos2.value=cadena2;
}
//***************************************
function llenaListaHija()
{
<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) {%>
var obj1;
obj1 = document.frm1.perfiles;  //papa

//obtener codigo de papa
var codigoPapa ="";
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa = obj1.options[i].value;
				break;
			}
	}//for
<%   }  else { %>	
codigoPapa = '<%=Constantes.PERFIL_AFILIADO_EXTERNO%>';
<%   }  %>	
    
/*
//limpiar recibidor    
for (var i = 0; i < arrPermisosExtras.length; i++)
	{
		arrPermisosExtras[i][0]="**********";
	}
*/
    
//habilita solo permisos posibles para el perfil
for (var j=0; j < arrUniversoPermisos.length; j++)
	{
			x0 = arrUniversoPermisos[j][0];
			x1 = arrUniversoPermisos[j][1];
			x2 = arrUniversoPermisos[j][2];
			x3 = arrUniversoPermisos[j][3];

			if (x0 != codigoPapa)
				{
				arrUniversoPermisos[j][3]="**********";
				}
			else
				{
				//if (arrUniversoPermisos[j][3]!="**********")
						arrUniversoPermisos[j][3]="activo";
				}
	}//for (var j=0; j < arrUnive
doFill0();
doFill1();
} // fin llenalistahija

function doFill0()
{
var objeto = document.frm1.listbox1;

//borrar contenido actual de objeto
	if (objeto.length != 0)
		{ 
		for(var i=0; i<objeto.options.length ; ++i)
			{
				objeto.options[i]=null;
						--i;
			}
    	}
  	
//llenar objeto con elemento activos del arreglo
	for (var j=0; j < arrUniversoPermisos.length; j++)
		{
			var x1 = arrUniversoPermisos[j][1];
			var x2 = arrUniversoPermisos[j][2];
			var x3 = arrUniversoPermisos[j][3];
			
			if (x3 != "**********")
				objeto.options[objeto.options.length] = new Option(x2,x1);
		} //for
} 

function doAgregaPermiso()
{
//Precargar los permisos que el usuario YA tiene actualmente
for(var i=0; i < arrPermisosActuales.length ; ++i)
	{
		var id  = arrPermisosActuales[i][0];
		var des = arrPermisosActuales[i][1];
		
		//alert('id = ' + id);
		//alert('des = ' + des);

		//agregar a arreglo destino
		var arrx = new Array();
		arrx[0]="activo";
		arrx[1]=id; 
		arrx[2]=des; 
		arrPermisosExtras[contador] = arrx;
		contador++;
		
		//eliminar permiso de lista de permisos
		for (var m = 0; m < arrUniversoPermisos.length; m++)
			{
				if (arrUniversoPermisos[m][1]==id)
					arrUniversoPermisos[m][3]="**********";
			}		
	}
doFill0();	
doFill1();
} 
//*********************************************************
function llenaComboHijo3()
{
<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) {%>
var flagInterno = false;

if(document.frm1.tipoUser[0].checked == true) 
	flagInterno = true;
else
	flagInterno = false;
	
var obj2;
obj2 = document.frm1.perfiles;  
if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length; ++i)
			{
				obj2.options[i]=null;
				--i;
			}
    }
   
//llenar combo hijo con informaci&oacute;n de acuerdo al Id de combo padre
var x0;
var x1;
for (var j=0; j < arrPerfiles.length; j++)
		{
			x0 = arrPerfiles[j][0];
			x1 = arrPerfiles[j][1];
			x2 = arrPerfiles[j][2];
			
			if (flagInterno == true)
				{
					if (x2=="1")
						obj2.options[obj2.options.length] = new Option(x1,x0);					
				}
			else
				{
					if (x2=="0")
						obj2.options[obj2.options.length] = new Option(x1,x0);				
				}

		}
<%  }  %>		
} // function llenaComboHijo3
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ********************* fin funcionalidad no soportada en pantalla de cajero ********** --%>
                                           <%   }  %>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
<%-- ************************************************************************************* --%>
function RecalculaRadio(opc)
{

<%if (modo<2) {%>

<%if (perfilId != Constantes.PERFIL_CAJERO) {%>  
  myform=document.forms[0];
  //if(myform.tipoUser[0].checked == true) 
  if(opc == 1)
  { 
    // Usuario Interno
    //verificar que sea de Peru
  	if (document.frm1.pais.value!='01')
  	{
  		alert("Usuario Interno debe ser peruano");
  		myform.tipoUser[1].checked = true;
  		return;
  	} 
	
	  	myform.exonerado[0].checked=true;
	    myform.exonerado[0].disabled=true;
	    myform.exonerado[1].disabled=true;
	    myform.userId.value="<%=request.getAttribute("NEXT_ID")%>";
	    myform.userId.disabled=true;
	
  }
  else 
  {
    //usuario externo
	<%if (perfilId != Constantes.PERFIL_ADMIN_ORG_EXT) {%>    
		  myform.exonerado[0].disabled=false
		  myform.exonerado[1].disabled=false
		  myform.exonerado[1].checked=true;
	    	  myform.userId.value="";
	    	  myform.userId.disabled=false;
	 <%}%>    
  }
  llenaComboHijo3();    
  llenaListaHija();
<%   }  %>  

<%  }  %>
}

function Recalcula()
{

<%if (modo<2) {%>

<%if (perfilId != Constantes.PERFIL_CAJERO) {%>  
  myform=document.forms[0];
  if(myform.tipoUser[0].checked == true) 
  { 
    // Usuario Interno
    //verificar que sea de Peru
  	if (document.frm1.pais.value!='01')
  	{
  		alert("Usuario Interno debe ser peruano");
  		myform.tipoUser[1].checked = true;
  		return;
  	} 
	<%if (perfilId != Constantes.PERFIL_ADMIN_ORG_EXT) {%>
	  	myform.exonerado[0].checked=true;
	    myform.exonerado[0].disabled=true;
	    myform.exonerado[1].disabled=true;
	 <%}%>
  }
  else 
  {
    //usuario externo
	<%if (perfilId != Constantes.PERFIL_ADMIN_ORG_EXT) {%>    
		  	myform.exonerado[0].disabled=false
		    myform.exonerado[1].disabled=false
		    myform.exonerado[1].checked=true;
	 <%}%>    
  }
  llenaComboHijo3();      
  llenaListaHija();
<%   }  %>  
<%}%>
}

function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}
function doValidaPais()
{
var flagInterno = false;

<%if (perfilId != Constantes.PERFIL_CAJERO) {%>
if(document.frm1.tipoUser[0].checked == true) 
	flagInterno = true;
else
	flagInterno = false;
	
var pais = document.frm1.pais.value;

if (flagInterno==true)	
	{
		if (pais != '01')
			{
				alert("Usuario Interno debe ser peruano");
				doCambiaCombo(document.frm1.pais,"01");
			}
	}
<%   }  %>

if (document.frm1.pais.value=='01')
	{
	document.frm1.departamento.disabled = 0;
	document.frm1.provincia.disabled    = 0;
	document.frm1.otroDepartamento.disabled  = "true";
	}
else
	{
	document.frm1.departamento.disabled = "true";
	document.frm1.provincia.disabled    = "true";
	document.frm1.otroDepartamento.disabled  = 0;	
	}
}

function llenaComboHijo()
{
var obj1;
var obj2;
obj1 = document.frm1.departamento;  //papa
obj2 = document.frm1.provincia;  //hijo

//obtener codigo de papa
var codigoPapa ="";
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa=obj1.options[i].value;
				break;
			}
	}

//limpiar combo hijo
if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length; ++i)
			{
				obj2.options[i]=null;
				--i;
			}
    }
    
//llenar combo hijo con informaci&oacute;n de acuerdo al Id de combo padre
		//TUTTI!!!!!!			objeto.options[objeto.options.length] = new Option("<TUTTI>","<TUTTI>");
var x0;
var x1;
var x2;			

for (var j=0; j<arr2.length; j++)
		{
			x0 = arr2[j][0];
			x1 = arr2[j][1];
			x2 = arr2[j][2];
			if (x2 == codigoPapa)
				obj2.options[obj2.options.length] = new Option(x1,x0);
		}

} // function llenaComboHijo

function doCancelar()
{
	history.back(1);
}

function doAceptar()
{
	//validaciones
	if (esVacio(document.frm1.apellidoPaterno.value)  || !contieneCarateresValidos(document.frm1.apellidoPaterno.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Paterno");
			document.frm1.apellidoPaterno.focus();
			return;
		}	
	if (!esVacio(document.frm1.apellidoMaterno.value)  && !contieneCarateresValidos(document.frm1.apellidoMaterno.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Apellido Materno");
			document.frm1.apellidoMaterno.focus();
			return;
		}	
		
	if (esVacio(document.frm1.nombres.value) || !contieneCarateresValidos(document.frm1.nombres.value,"nombre"))
		{	
			alert("Por favor ingrese correctamente el Nombre");
			document.frm1.nombres.focus();
			return;
		}	
	if (esVacio(document.frm1.numDoc.value))
		{	
			alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
			document.frm1.numDoc.focus();
			return;
		}	
	
	if (!esEntero(document.frm1.numDoc.value) || !esMayor(document.frm1.numDoc.value,8) || !esEnteroMayor(document.frm1.numDoc.value,1))
		{	
			alert("Por favor ingrese correctamente el Número del Documento.\nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
			document.frm1.numDoc.focus();
			return;
		}
		
		
	if(!esVacio(document.frm1.telefono.value) ){	
		if(!contieneCarateresValidos(document.frm1.telefono.value,"telefono") || !esMayor(document.frm1.telefono.value,6))	
		{
			alert("Por favor ingrese correctamente el Número de Teléfono.\nEl Número de Teléfono debe contener mas de 6 caracteres.\nEl Número de Teléfono puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.telefono.focus();
			return;		
		}
	}

	if(!esVacio(document.frm1.anexo.value) ){	
		if(!contieneCarateresValidos(document.frm1.anexo.value,"telefono"))	
		{
			alert("Por favor ingrese correctamente el Número de Anexo\nEl Número de Anexo puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.anexo.focus();
			return;		
		}
	}	
	
	if(!esVacio(document.frm1.fax.value)){	
		if(!contieneCarateresValidos(document.frm1.fax.value,"telefono") || !esMayor(document.frm1.fax.value,6))	
		{
			alert("Por favor ingrese correctamente el Número de Fax.\nEl Número de Fax debe contener mas de 6 caracteres.\nEl Número de Fax puede contener caracteres numéricos(0-9), espacios( ) y guiones(-).");
			document.frm1.fax.focus();
			return;
		}
	}	
	if (esVacio(document.frm1.distrito.value) || !contieneCarateresValidos(document.frm1.distrito.value,"numeronombre"))
		{	
			alert("Por favor ingrese correctamente el Distrito");
			document.frm1.distrito.focus();
			return;
		}	
	
	if (esVacio(document.frm1.direccion.value) )
		{	
			alert("Por favor ingrese correctamente la Dirección");
			document.frm1.direccion.focus();
			return;
		}	
	
	if (!esEmail(document.frm1.email.value) || !contieneCarateresValidos(document.frm1.email.value,"correo"))
		{	
			alert("Por favor ingrese correctamente el Correo Electrónico.\nEl Correo Electrónico puede contener caracteres alfanuméricos(A-Z 0-9),arroba(@),puntos(.) y guiones(_).");
			document.frm1.email.focus();
			return;
		}
	if (!esLongitudEntre(document.frm1.userId.value,6,13))
		{	
			alert("Por favor ingrese correctamente el usuario.\nEl usuario debe contener entre 6 y 13 caracteres.\nLos 3 últimos caracteres del usuario no deben ser numéricos (0-9)");
			document.frm1.userId.focus();
			return;		
		}	

// || !contieneCarateresValidos(document.frm1.userId.value,"usuariopassword") || esEntero(extraeDer(document.frm1.userId.value,3))
	<%//=Tarea.validaJS("apellidoPaterno","Apellido Paterno",2,true,true)%>
	<%//=Tarea.validaJS("apellidoMaterno","Apellido Materno",2,false,true)%>
	<%//=Tarea.validaJS("nombres","Nombre",2,true,true)%>
	<%//=Tarea.validaJS("numDoc","Numero Documento",0,true,true)%>

	<%//=Tarea.validaJS("userId","Usuario ID",0,true,true)%>
	
<%if (perfilId != Constantes.PERFIL_CAJERO) {%>  	

	doSendChildren();	
	
	<%//if (modo<=1) {%>
	//valida contrasena solamente si hay data en el campo
	
	if (document.frm1.clave.value.length > 0 ||
		document.frm1.confirmacionClave.value.length > 0 ||
		document.frm1.respuestaSecreta.value.length > 0)
	{	
		
		if(esVacio(document.frm1.clave.value) || !contieneCarateresValidos(document.frm1.clave.value,"usuariopassword") || !esLongitudEntre(document.frm1.clave.value,6,10) || !contieneNumero(document.frm1.clave))
		{
			alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña debe contener entre 6 y 10 caracteres.\nLa Contraseña debe contener al menos un caracter numérico (0-9)");
			document.frm1.clave.focus();
			return;		
		}
		if (document.frm1.clave.value.toUpperCase()== document.frm1.userId.value.toUpperCase())
		{	
			alert("Por favor ingrese correctamente la Contraseña.\nLa Contraseña no puede ser igual al campo Usuario");
			document.frm1.clave.focus();
			return;		
		}
		
		if (document.frm1.confirmacionClave.value.toUpperCase() != document.frm1.clave.value.toUpperCase())
		{	
			alert("La Contraseña no esta verificada");
			document.frm1.confirmacionClave.focus();
			return;		
		}
		
		if (document.frm1.clave.value.length <= 0){
				alert("Debe ingresar valor en Contraseña Actual");
				document.frm1.clave.focus();
				return;
		}
		if (document.frm1.confirmacionClave.value.length <= 0){
				alert("Debe ingresar valor en Nueva Contraseña");
				document.frm1.confirmacionClave.focus();
				return;
		}
		
		if (document.frm1.clave.value != document.frm1.confirmacionClave.value){
				alert("No coinciden la nueva contraseña y su confirmación");
				document.frm1.confirmacionClave.focus();
				return;
		}
		/*if (document.frm1.clave.value == document.frm1.confirmacionclave.value){
				alert("La nueva contraseña debe ser diferente a la contraseña actual");
				document.frm1.contrasena3.focus();
				return;
		}*/
		if (!esLongitudEntre(document.frm1.respuestaSecreta.value,5,30) || !contieneCarateresValidos(document.frm1.respuestaSecreta.value,"usuariopassword"))
		{	
			alert("Por favor ingrese correctamente la Respuesta Secreta.\nLa Respuesta Secreta debe contener entre 5 y 30 caracteres");
			document.frm1.respuestaSecreta.focus();
			return;		
		}
		if (document.frm1.respuestaSecreta.value.toUpperCase == document.frm1.userId.value)
		{	
			alert("La Respuesta Secreta no puede ser igual al campo Usuario");
			document.frm1.respuestaSecreta.focus();
			return;		
		}
		if (document.frm1.respuestaSecreta.value.length <= 0){
				alert("Debe ingresar valor en Respuesta");
				document.frm1.respuestaSecreta.focus();
				return;
		}
		<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
		   if(document.frm1.cboCaducidad.value == -1){
					alert("Debe seleccionar la caducidad de la contraseña");
					document.frm1.cboCaducidad.focus();
				return;
		}
		<%}%>
		
	}
	
	<%//=Tarea.validaJS("clave","Contrasena",0,true,true)%>
	<%//=Tarea.validaJS("confirmacionClave","Confirmacion Contrasena",0,true,true)%>
	
	/*if (document.frm1.clave.value != document.frm1.confirmacionClave.value)
		{
			alert("La contrasena no esta confirmada");
			document.frm1.clave.focus();
			return;
		}
	*/	
	<%// } %>	
		
		
<%  }  %>
	/*var vc = esEmail(document.frm1.email.value);
	if (vc == false)
		{
			alert("El correo electronico es invalido");
			document.frm1.email.focus();
			return;
		}
	*/
	//LSJ 8oct2002
	document.frm1.hidNEXT_ID.value=document.frm1.userId.value;
	<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
		document.frm1.cboCaducidad.disabled=false;	
	<%}%>
	<%if (modo<=1) {%>
	document.frm1.action = "/iri/CrearUsuario.do?state=registraDatos";
	<%} else { %>
	document.frm1.action = "/iri/EditarUsuario.do?state=registraDatos";
	<%}%>
	document.frm1.submit();
}
function habilitaCaducidad(){
<%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
if(document.frm1.clave.value.length > 0)
	document.frm1.cboCaducidad.disabled=false;
else
	document.frm1.cboCaducidad.disabled=true;
<%}%>
}
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body <%if (perfilId != Constantes.PERFIL_CAJERO) { if (modo <=1) {%>onload="Recalcula();"<%} }%>>
<%
String titulo="";
if (perfilId==Constantes.PERFIL_ADMIN_GENERAL || perfilId==Constantes.PERFIL_ADMIN_JURISDICCION) 
	{
		if (modo <= 1)
			titulo="Creaci&oacute;n de Nuevo Usuario Individual";
		else
			titulo="Edici&oacute;n de Usuario Individual";
    } 
if (perfilId==Constantes.PERFIL_ADMIN_ORG_EXT) 
	{
		if (modo <= 1)
			titulo="Creaci&oacute;n de Nuevo Usuario Afiliado";
		else
			titulo="Edici&oacute;n de Usuario Afiliado";
    } 
if (perfilId==Constantes.PERFIL_CAJERO) 
	{
		if (modo <= 1)
			titulo="Afiliaci&oacute;n de Usuario Individual (Ventanilla)";
		else
			titulo="Edici&oacute;n de Usuario Individual (Ventanilla)";
    }             
%>
<br>
<table cellspacing=0 class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N &gt;&gt; Usuario &gt;&gt;  </FONT><%=titulo%></td></tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
<input type="hidden" name="hidPermisos">
<input type="hidden" name="hidPermisos2">
<input type="hidden" value="<%=perfilIdUsuario%>" name="hidPerfilUsuario">
<table class=tablasinestilo >
<tr><th colspan=4>DATOS DEL USUARIO</th></tr>
  <jsp:include flush="true" page="datosUsuarioInc.jsp"></jsp:include>
<!--</table>-->
<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || 
      perfilId == Constantes.PERFIL_ADMIN_JURISDICCION ||
      perfilId == Constantes.PERFIL_ADMIN_ORG_EXT) {%>
<!--<table class="tablasinestilo">-->
  <tr><th colspan=4>DATOS DE LA CUENTA</th></tr>
  <tr>
    <td width="114">USUARIO ID</td>
    <td width="187"><input type="text" name="userId" value="" size="20" style="width:133" onblur="solonumlet(this)" <%if (modo>=2){%>disabled=true<%}%>></td>
	<%if (perfilId == Constantes.PERFIL_ADMIN_ORG_EXT) {%>
		<td width="159">
		    <input type="radio" value="I" name="tipoUser" style="visibility: hidden;">
		    <input type="radio" value="E" name="tipoUser" checked style="visibility: hidden;">
		</td>
		<td width="140"></td>		
	<%} else {%>
	    <td width="159"><input type="radio" value="I" name="tipoUser" onClick="RecalculaRadio(1)" >INTERNO</td>
	    <td width="140"><input type="radio" value="E" name="tipoUser" checked onClick="RecalculaRadio(2)"> EXTERNO</td>
	<% } %>
  </tr>
  <tr>
    <td width="114">CONTRASE&Ntilde;A</td>
    <td width="187"><input type="password" name="clave" size="20" style="width:133" onblur="solonumlet(this)" onkeyup="javascript:habilitaCaducidad();">
    <%if (perfilIdUsuario == Constantes.PERFIL_TESORERO || 
      perfilIdUsuario == Constantes.PERFIL_CAJERO ||
      perfilIdUsuario == Constantes.PERFIL_INTERNO) {%>
     	    <span lang="ES-PE" class="textorojoclaro"> * </span>
    
	<%   }  %>
	</td>
    <td width="159">CONFIRME CONTRASE&Ntilde;A</td>
    <td width="140"><input type="password" name="confirmacionClave" size="20" style="width:133" onblur="solonumlet(this)">
    </td>
  </tr>
  <tr>
    <td width="114">PREGUNTA SECRETA</td>
    <td width="187">
	  <select  name="pregSecreta" size="1" style="width:187">
        <logic:iterate name="pregSecretas" id="preg" scope="request">
          <option value="<bean:write name="preg" property="codigo"/>"><bean:write name="preg" property="descripcion"/></option>
       </logic:iterate>
	  </select>        
    </td>
    <td width="159">RESPUESTA</td>
    <td width="140"><input type="text" name="respuestaSecreta" size="20" style="width:133" onblur="sololet(this)"></td>
  </tr>
  <%if (perfilIdUsuario == Constantes.PERFIL_ADMIN_ORG_EXT ||perfilIdUsuario ==  Constantes.PERFIL_AFILIADO_EXTERNO || perfilIdUsuario == Constantes.PERFIL_ADMIN_GENERAL || perfilIdUsuario == Constantes.PERFIL_INDIVIDUAL_EXTERNO) {%>
   <tr>
    <td width="114">CADUCIDAD DE CONTRASEÑA</td>
    <td width="187">
	<select size="1" name="cboCaducidad" style="width:130" disabled="disabled">
    	<option selected value="-1">  </option>
      	<logic:iterate name="arrCaducidad" id="caducidad" scope="request">
        	<option value="<bean:write name="caducidad" property="atributo1"/>"><bean:write name="caducidad" property="descripcion"/></option>
        </logic:iterate>
	</select>        
    </td>
    <td width="159"></td>
    <td width="140"></td>
  </tr>
  <%}%>
  
  <tr>
	<%if (perfilId == Constantes.PERFIL_ADMIN_ORG_EXT) {%>
	<td width="114"></td>
	<td width="187"></td>
	<%  } else { %>  		    
    <td width="159">Exonerado de cobro</td>
    <td width="140">
		<input type="radio" value="S" name="exonerado">SI
		<input type="radio" value="N" name="exonerado" checked>NO
    </td>			
	<%  } %>
    <td width="159"><input type="radio" value="A" name="estadoUsuario" checked onClick="Recalcula()" >Activa</td>
    <td width="140"><input type="radio" value="I" name="estadoUsuario" onClick="Recalcula()" >Inactiva</td>
  </tr>		
<!--</table>  -->
<% } %>

<%if (perfilId == Constantes.PERFIL_CAJERO) {%>
<!--<table class="tablasinestilo"> -->
    <tr>
        <TD>USUARIO ID:</TD>
        <td colspan=4><input type="text" name="userId" maxlength="13" size="24" style="width:133" onblur="solonumlet(this)"></td>
        <input type="hidden" value="A" name="estadoUsuario">
    </tr>
<!--</table>-->
<%  } %>
<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || 
      perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) {%>
<!--<table class="tablasinestilo">-->
  <tr><th colspan=4>ASIGNACION DE PERFIL</th></tr>
  <tr>
    <td colspan=4>
      <select size="1" name="perfiles" style="width:380" onChange="llenaListaHija();" <%if (modo>=2){%>disabled=true<%}%>>
      </select>
	</td>
  </tr>
</table>  
<%  }  %>  
<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || 
      perfilId == Constantes.PERFIL_ADMIN_JURISDICCION ||
      perfilId == Constantes.PERFIL_ADMIN_ORG_EXT) {%>
<table class="tablasinestilo">
  <tr><th colspan=3>ASIGNACION DE PERMISOS ADICIONALES</th></tr>
  <tr>
    <td>
	  <select size="4" name="listbox1"  multiple style="width:280">
      </select>
    </td>
    <td style="width:40">
	  <input type="button" value=" &gt;&gt; " onClick="doAdd1();" >
      <input type="button" value=" &lt;&lt; " onClick="doRemove1();">    
	</td>
    <td>
      <select size="4" name="listbox2" multiple style="width:280"> 
      </select>
    </td>
  </tr>
</table>
<%   }  %>
<%if (perfilIdUsuario == Constantes.PERFIL_TESORERO || 
      perfilIdUsuario == Constantes.PERFIL_CAJERO ||
      perfilIdUsuario == Constantes.PERFIL_INTERNO) {%>
      <table class="tablasinestilo">
      <tr>
       <td  align="left">
	    <span lang="ES-PE" class="textorojoclaro"> (*) La contraseña modificada tiene una vigencia de <bean:write name="nDiasCad"/> días </span>
    	<input type="hidden" name="ndias" value="<bean:write name="nDiasCad"/>"></td>
      </tr>
      </table>
<%   }  %>
<table class="tablasinestilo">
  <tr>
    <td align="center">
      <A href="javascript:doAceptar()" onmouseover="javascript:mensaje_status('Aceptar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="images/btn_aceptar.gif"></a>
      <A href="javascript:doCancelar()" onmouseover="javascript:mensaje_status('Cancelar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img border=0 src="images/btn_cancelar.gif"></a>
	</td>
  </tr>
</table>



<script LANGUAGE="JavaScript">
<%if (perfilId != Constantes.PERFIL_CAJERO) {%>
	llenaComboHijo3();	
	llenaListaHija();
	
	
<% } %>	
	llenaComboHijo();
	
	<%if (perfilId == Constantes.PERFIL_ADMIN_ORG_EXT) { %>
 	    document.frm1.userId.value="<%=request.getAttribute("NEXT_ID")%>";
	    document.frm1.userId.disabled=true;
	<%   }  %>		

</script>

<% if (modo >= 1) 
	{ %>
<script LANGUAGE="JavaScript">
	document.frm1.apellidoPaterno.value  = "<%=datosUsuarioBean.getApellidoPaterno()%>";
	document.frm1.apellidoMaterno.value  = "<%=datosUsuarioBean.getApellidoMaterno()%>";
	document.frm1.nombres.value          = "<%=datosUsuarioBean.getNombres()%>";
	doCambiaCombo(document.frm1.tipoDoc, "<%=datosUsuarioBean.getTipoDocumento()%>");
	document.frm1.numDoc.value           = "<%=datosUsuarioBean.getNumDocumento()%>";
	document.frm1.fax.value              = "<%=datosUsuarioBean.getFax()%>";			
	document.frm1.telefono.value         = "<%=datosUsuarioBean.getTelefono()%>";
	document.frm1.anexo.value            = "<%=datosUsuarioBean.getAnexo()%>";
	doCambiaCombo(document.frm1.pais,      "<%=datosUsuarioBean.getPais()%>");
	doValidaPais();
	document.frm1.email.value            = "<%=datosUsuarioBean.getEmail()%>";
	doCambiaCombo(document.frm1.departamento, "<%=datosUsuarioBean.getDepartamento()%>");
	document.frm1.otroDepartamento.value = "<%=datosUsuarioBean.getOtroDepartamento()%>";
	llenaComboHijo();
	doCambiaCombo(document.frm1.provincia, "<%=datosUsuarioBean.getProvincia()%>");
	document.frm1.distrito.value         = "<%=datosUsuarioBean.getDistrito()%>";
	document.frm1.direccion.value        = "<%=datosUsuarioBean.getDireccion()%>";
	document.frm1.codPostal.value        = "<%=datosUsuarioBean.getCodPostal()%>";
	doCambiaCombo(document.frm1.provincia, "<%=datosUsuarioBean.getProvincia()%>");
	document.frm1.userId.value           = '<%=datosUsuarioBean.getUserId()%>';
	
	<%if (perfilId == Constantes.PERFIL_ADMIN_GENERAL || 
	      perfilId == Constantes.PERFIL_ADMIN_JURISDICCION) 
	     {%>
			
			
			
			<%if (datosUsuarioBean.getFlagExoneradoPago()==true) {%>
				document.frm1.exonerado[0].checked = "true";
			<%  } else { %>
				document.frm1.exonerado[1].checked = "true";
			<%  }%>
			
			
			
			
			<%if (datosUsuarioBean.getFlagInterno()==true) {%>
				document.frm1.tipoUser[0].checked = "true";
			<%  } else { %>
				document.frm1.tipoUser[1].checked = "true";
			<%  }%>
			
			<%if (perfilId != Constantes.PERFIL_CAJERO) {%>
				llenaComboHijo3();	
				llenaListaHija();
			<% } %>				
			
			doCambiaCombo(document.frm1.perfiles,  "<%=datosUsuarioBean.getPerfilId()%>");
			
	<%   } %>
	
	<%if   (perfilId == Constantes.PERFIL_ADMIN_GENERAL || 
      		perfilId == Constantes.PERFIL_ADMIN_JURISDICCION ||
      		perfilId == Constantes.PERFIL_ADMIN_ORG_EXT) 
      		{   %>

				
			        doAgregaPermiso(); //Precargar los permisos que el usuario YA tiene actualmente
			        
			  <%if (datosUsuarioBean.getFlagActivo()==true)
			  		{	%>
			  		document.frm1.estadoUsuario[0].checked = "true";
			  <%    } else { %>
					document.frm1.estadoUsuario[1].checked = "true";
			  <%    }%>
			  
				doCambiaCombo(document.frm1.pregSecreta, "<%=datosUsuarioBean.getPreguntaSecreta()%>");
				document.frm1.respuestaSecreta.value   = "<%=datosUsuarioBean.getRespuestaSecreta()%>";
			
			
	<%      }%>
	
	
<% if (modo == 2) 
	{ %>

	<% if (datosUsuarioBean.getFlagInterno()==true)  { %>
	
	document.frm1.pais.disabled=true;
	document.frm1.departamento.disabled=true;
	document.frm1.provincia.disabled=true;
	document.frm1.exonerado[0].checked=true;
	document.frm1.exonerado[0].disabled=true;
	document.frm1.exonerado[1].disabled=true;
	
	<%  } %>
	
	document.frm1.tipoUser[0].disabled="true";
	document.frm1.tipoUser[1].disabled="true";
	
<%   }%>	

</script>
<input type="hidden" name="hid4" value="<%=datosUsuarioBean.getUserId()%>">
<% } %>

<input type="hidden" name="hidNEXT_ID" value="">
</form>

<%
ValidacionException validacionException = (ValidacionException) request.getAttribute("VALIDACION_EXCEPTION");
if (validacionException!=null)
	{
%>
<br>
<script LANGUAGE="JavaScript">
	alert("<%=validacionException.getMensaje()%>");
	<% 
	String foco = validacionException.getFocus();
	if (foco.trim().length() > 0) {%>
		document.frm1.<%=foco%>.focus();
	<% } %>
</script>
<% } %>




<%
String casoAOd = (String) request.getAttribute("casoAOd");
if (casoAOd!=null)
   {%>
<script LANGUAGE="JavaScript">
	doCambiaCombo(document.frm1.departamento, "<%=request.getAttribute("casoAOd")%>");
	llenaComboHijo();
	doCambiaCombo(document.frm1.provincia, "<%=request.getAttribute("casoAOp")%>");	
</script>
<% } %>








</body>
</html>