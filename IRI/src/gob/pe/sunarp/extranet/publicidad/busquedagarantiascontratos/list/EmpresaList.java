/*
 * EmpresaList.java
 *
 * Created on 2 de enero de 2002, 10:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list;

import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Empresa;
import java.util.ArrayList;

/**
 *
 * @author FNJ
 */
public class EmpresaList extends ArrayList{
    
    /** Creates a new instance of EmpresaList */
    public EmpresaList() {
    }
    public Empresa getList( int i) {
        return (Empresa)get(i);
    }
}