<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<html>
<head><title></title>
<link rel="stylesheet" href="../styles/global.css">
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt;</FONT>  Mantenimiento de Tablas</td></tr>
</table>
<br>

<table border="0" width="100%" class="formulario">
  <tr>
    <td width="27%">&nbsp;</td>
    <td width="47%">
    <h3>Mantenimiento de Tablas</h3>
 </td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="27%"></td>
    <td width="47%"></td>
    <td width="20%"></td>
  </tr>
  <tr>
    <td width="33%" align="right">1.&nbsp;</td>
    <td width="47%" >
      <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_JURISDICCION%>" target="_self">Jurisdicciones</a>
    </td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">2.&nbsp;</td>
    <td width="47%" align="left">
      <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_REGIS_PUBLICO%>" target="_self">Oficinas Zonales</a>
    </td>
  <center>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">3.&nbsp;</td>
    <td width="47%" align="left">
        <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_OFIC_REGISTRAL%>" target="_self">Oficinas Registrales</a>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">4.&nbsp;</td>
    <td width="47%" align="left">
        <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_BANCO%>" target="_self">Entidades Bancarias</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">5.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_ACTO%>" target="_self">Actos</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>  
  <tr>
    <td width="33%" align="right">6.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_AREA_REGISTRAL%>" target="_self">Areas Registrales</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>  
  <tr>
    <td width="33%" align="right">7.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_LIBRO%>" target="_self">Libros</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>     
  <tr>
    <td width="33%" align="right">8.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_RUBRO%>" target="_self">Rubros</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>   
  <tr>
    <td width="33%" align="right">9.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_DEPARTAMENTO%>" target="_self">Departamentos</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>     
  <tr>
    <td width="33%" align="right">10.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_PROVINCIA%>" target="_self">Provincias</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>       
  <tr>
    <td width="33%" align="right">11.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_DISTRITO%>" target="_self">Distritos</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>         
  <tr>
    <td width="33%" align="right">12.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_DOC_IDEN%>" target="_self">Documentos de Identidad</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>           
  <tr>
    <td width="33%" align="right">13.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_GIRO%>" target="_self">Giros</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>             
  <tr>
    <td width="33%" align="right">14.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_NOTARIA%>" target="_self">Notar&iacute;as</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>    
  <tr>
    <td width="33%" align="right">15.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_PARTIC_LIBRO%>" target="_self">Participaciones Libro</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>      
  <tr>
    <td width="33%" align="right">16.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_SERVICIO%>" target="_self">Servicios</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>       
 <!--  Agregado por JACR - Inicio-->
 <!--  Falta reemplazar <%//=Constantes.TABLA_TM_MODELO_VEHI%> -->  
  <tr>
    <td width="33%" align="right">17.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_MODELO_VEHI%>" target="_self">Modelo</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">18.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_MARCA_VEHI%>" target="_self">Marca</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">19.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_COND_VEHI%>" target="_self">Condición de Vehículo</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">20.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_TIPO_VEHI%>" target="_self">Tipo de Vehículo</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">21.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_TIPO_COMB%>" target="_self">Tipo de Combustible</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">22.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TM_TIPO_CARR%>" target="_self">Tipo de Carrocería</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%" align="right">23.&nbsp;</td>
    <td width="47%" align="left">
     <a href="/iri/ManteTabla.do?state=listado&P0=<%=Constantes.TABLA_TIPO_AFEC%>" target="_self">Tipo de Afectación</a>
	</td>
    <td width="20%">&nbsp;</td>
  </tr>
  
  <!--  Agregado por JACR - Fin --> 
  <tr>
    <td width="33%">&nbsp;</td>
    <td width="47%" align="center"></td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%"></td>
    <td width="47%" align="left"></td>
    <td width="20%">&nbsp;</td>
  </tr>
</table>


</body>
</html>