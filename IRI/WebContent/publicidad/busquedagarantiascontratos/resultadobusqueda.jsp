<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<jsp:useBean id="lista" scope="request" class="gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.UsuarioList"/>
<%@page import="gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Usuario"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.UsuarioList"%>

<html>
    <head>
        <LINK href="../../styles/global.css" rel="stylesheet" type="text/css">
        <script language="JavaScript" src="../../javascript/util.js">
        </script>


        <!--Resultados -->
        <script language="JavaScript">

            function Regresa()
            {
            history.back();
            }

            function Imprimir()
            {
            HOJA2.style.visibility="hidden";
            HOJA3.style.visibility="hidden";
            window.print();
            HOJA2.style.visibility="visible";
            HOJA3.style.visibility="visible";	
            }

        </script>
    <META name="GENERATOR" content="IBM WebSphere Studio">
</head>
    <body>

        <form name="frm1" method="POST" class="formulario">
            <br>
            <!-- Resultados--> 
            <div>
                <table class=titulo cellspacing=0>
                    <tr> 
                        <td><font color=black>SERVICIOS &gt;&gt;  Consulta de Registros Mobiliarios de Contratos ></font>Resultados</td>
                    </tr>
                </table>

                <table class=grilla cellspacing=3 >
                    <tr >
                        <td>Nombres</td>
                        <td>Lugar</td>
                        <td>Nº Partida</td>
                        <td>Tipo de participaciónn</td>
                        <td>Estado</td>
                        <td>Descripcion del Libro</td>
                    </tr>	
                </table>
                <table class=grilla2 cellspacing=3 >
                    <%for (int i=1;i<lista.size();i++){
                            Usuario u = lista.getList(i);%>
                    <tr >
                        <td><%=u.getNombre()%> <%=u.getApe_paterno()%> <%=u.getApe_materno()%></td>
                        <td><%=u.getLugar()%></td>
                        <td><%=u.getNum_partida()%></td>
                        <td><%=u.getTipo()%></td>
                        <td><%=u.getDescripcion_libro()%></td>
                    </tr>
                    <%}%>
                </table>
            </div>

        </form>
        <br>
        <table class=tablasinestilo>
            <tr>
                <td width="50%" align="left">
                    <div id="HOJA2"> 
                        <a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true">
                            <IMG height=25 hspace=4 src="../../images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0>
                        </a>
                    </div>
                </td>
                <td width="50%" align="right">
                <div id="HOJA3">	
                    <a href="javascript:Regresa()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true">
                        <IMG height=25 hspace=4 src="../../images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0>
                    </a>
                </div></td>
            </tr>
        </table>

    </body>
</html>