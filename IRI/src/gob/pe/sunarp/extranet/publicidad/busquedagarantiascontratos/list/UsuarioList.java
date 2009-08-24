/*
 * UsuarioList.java
 *
 * Created on 18 de mayo de 2006, 12:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list;


import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public class UsuarioList extends ArrayList {
    
    /** Creates a new instance of UsuarioList */
    public UsuarioList() {
    }
    public Usuario getList( int i) {
        return (Usuario)get(i);
    }
}
