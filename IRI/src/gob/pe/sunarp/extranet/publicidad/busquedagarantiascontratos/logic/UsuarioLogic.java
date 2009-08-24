/*
 * UsuarioLogic.java
 *
 * Created on 18 de mayo de 2006, 01:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.logic;


import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Empresa;

import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Usuario;
import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.dao.UsuarioDAO;

import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.EmpresaList;
import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.UsuarioList;



/**
 *
 * @author Christian
 */
public class UsuarioLogic {
    
    /** Creates a new instance of UsuarioLogic */
    public UsuarioLogic() {
    }
    
    public static String BuscarUsuario(Usuario u,UsuarioList List) {
        return UsuarioDAO.Search(u,List);
    }
    
    public static  String BuscarEmpresa(Empresa e, EmpresaList List) {
        return UsuarioDAO.BuscarEmpresa(e,List);
    }
    
    
    
}
