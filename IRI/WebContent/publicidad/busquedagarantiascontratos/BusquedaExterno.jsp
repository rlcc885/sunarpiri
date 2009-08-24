<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <HEAD>
        <LINK href="../../styles/global.css" rel="stylesheet" type="text/css">
        <script language="JavaScript" src="../../javascript/util.js">
        </script>
    <META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
<BODY>

    <table class=cabeceraformulario cellspacing=0>
        <tr>
            <td>B&uacute;squeda Registro Garantías y Contratos por Participantes</td>
        </tr>
    </table>
    <br>
    <form action="/iri/BuscarUsuarioServlet" method="post">
        <table class=formulario cellspacing=0>
            <td colspan=4><strong>Buscar por Participante Persona Natural</strong></td>
            <tr>
                <td width="160">Apellido Paterno</td>
                <td width="140"><input name="ape_pat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
                <td width="160">Apellido Materno</td>
                <td width="140"><input name="ape_mat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
                <td width="31">&nbsp;</td>	
            </tr>
            <tr>
                <td width="160">Nombres</td>
                <td width="140"><input name="nombre" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
                <td width="160">Tpo de participaci&oacute;n</td>
                <td width="140">
                    <select name="combo1" size="1" width="150px">
                        <option value=1>TODOS</option>
                        <option value=2>ACREEDOR</option>
                        <option value=3>DEUDOR</option>
                        <option value=4>CONTRIBUYENTE</option>
                        <option value=5>DEPOSITARIO</option>
                    </select>
                </td>
                <td width="31"><input type="image" src="../../images/btn_buscar2.gif" onmouseover="javascript:mensaje_status('Busqueda Directa por Nombres o DNI');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
            </tr>
            <tr>
                <td width="160"><input type="checkbox" name="estado"> Incluir Inactivos</td>
                <td width="140">&nbsp;</td>
                <td width="160">&nbsp;</td>
                <td width="140">&nbsp;</td>
                <td width="31">&nbsp;</td>
            </tr>
        </table> 
    </form>
    <br>
   
    <form action="/iri/BuscarEmpresaServlet" method="post">
        <table class=formulario cellspacing=0 border=0>
            <tr>
                <td colspan=4><strong>Buscar por Participante Persona Jur&iacute;dica</strong></td>
            </tr>
            <tr>
                <td width="120">Raz&oacute;n Social</td>
                <td width="160"><input type="text" name="razonsocial" style="width: 130" maxlength="150" onblur="solonumlet(this)"></td>
                <td width="75">Siglas</td>
                <td width="225" aling=right><input type="text" name="siglas" style="width: 130" maxlength="50" onblur="solonumlet(this)"></td>
            </tr>
            <tr>
                <td width="120">Tipo de Participaci&oacute;n</td>	
                <td width="160">
                    <select name="combo1" size="1" width="140px">
                        <option value=1>TODOS</option>
                        <option value=2>ACREEDOR</option>
                        <option value=3>DEUDOR</option>
                        <option value=4>CONTRIBUYENTE</option>
                        <option value=5>DEPOSITARIO</option>
                    </select>	
                </td>
                <td width="75">&nbsp;</td>				
                <td width="225" align=right><input type="image" src="../../images/btn_buscar2.gif" onmouseover="javascript:mensaje_status('Busqueda Directa por Razon Social');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></td>
            </tr>
            <tr>
                <td width="160"><input type="checkbox" name="estado" value=0> Incluir Inactivos</td>
                <td width="140">&nbsp;</td>
                <td width="75">&nbsp;</td>
                <td width="225">&nbsp;</td>
            </tr>	
        </table>
    </form>

</BODY>
</HTML>            
