/*
 * UsuarioDAO.java
 *
 * Created on 18 de mayo de 2006, 12:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.dao;




import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Empresa;

import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Usuario;

import java.sql.*;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.EmpresaList;
import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.UsuarioList;


import java.sql.ResultSet;


/**
 *
 * @author Christian
 */
public class UsuarioDAO {
    
    /** Creates a new instance of UsuarioDAO */
    public UsuarioDAO() {
    }
    
    public static String  Search(Usuario u, UsuarioList List) {
        String res = null;
        DBConnectionFactory pool = DBConnectionFactory.getInstance();
        Connection conn = null;
        Statement stm = null;
        try {
            System.out.println("Antes de Tener la Conexion");
            String sql = "SELECT PART.CUR_PRTC AS COD_REGISTRAL,RPU.NOMBRE AS ZONA,OFI.NOMBRE AS OFICINA,PART.NOMBRES AS NOMBRE,PART.APE_PAT AS AP_PATERNO,PART.APE_MAT AS AP_MATERNO,PART.TI_DOC_IDEN AS TIP_DOC_IDENT,PART.NU_DOC_IDEN AS NRO_DOC_IDENT,PLIB.NOMBRE AS TIPO_PARTICIPACION,TM.DESCRIPCION AS DESCRIPCION_LIBRO,P.NUM_PARTIDA AS NROPART,DECODE(IND.ESTADO,'1','ACTIVO','0','INACTIVO') AS ESTADO,IND.NS_GRAVAMEN AS GARANTIA " +
                    "FROM USER1.PRTC_NAT PART,USER1.IND_PRTC IND,USER1.PARTIDA P,USER1.REGIS_PUBLICO RPU,USER1.OFIC_REGISTRAL OFI,USER1.PARTIC_LIBRO PLIB,USER1.TM_LIBRO TM " +
                    "WHERE PART.CUR_PRTC=IND.CUR_PRTC AND P.REFNUM_PART=IND.REFNUM_PART AND PART.OFIC_REG_ID=OFI.OFIC_REG_ID  AND PART.REG_PUB_ID=OFI.REG_PUB_ID AND P.REG_PUB_ID=OFI.REG_PUB_ID AND P.OFIC_REG_ID=OFI.OFIC_REG_ID AND PART.REG_PUB_ID=RPU.REG_PUB_ID  AND IND.COD_PARTIC = PLIB.COD_PARTIC AND PLIB.COD_LIBRO=TM.COD_LIBRO AND P.COD_LIBRO IN ('022','023','043','077','062','088','038','054','040','015','010') AND PART.APE_PAT ='"+u.getApe_paterno()+"' AND PART.APE_MAT = '"+u.getApe_materno()+"' AND PART.NOMBRES LIKE '"+u.getNombre()+"%' "+u.getSql()+"" /*sentencia select*/;
            System.out.println(sql);
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            DBConnection dconn = new DBConnection(conn);
            System.out.println("sql");
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            System.out.println(sql);
            while (rs.next()){
                Usuario u1 = new Usuario();
                u1.setNombre(rs.getString("NOMBRE"));
                u1.setApe_paterno(rs.getString("AP_PATERNO"));
                u1.setApe_materno(rs.getString("AP_MATERNO"));
                u1.setDocidentidad(rs.getString("NRO_DOC_IDENT"));
                if(u1.getDocidentidad()==null){u1.setDocidentidad("No Puso DNI");}
                u1.setNum_partida(rs.getString("NROPART"));
                u1.setLugar(rs.getString("OFICINA"));
                u1.setTipo(rs.getString("TIPO_PARTICIPACION"));
                u1.setCodigo_registral(rs.getString("COD_REGISTRAL"));
                u1.setZona(rs.getString("ZONA"));
                u1.setDescripcion_libro(rs.getString("DESCRIPCION_LIBRO"));
                u1.setEstado(rs.getString("ESTADO"));
                List.add(u1);
            }
        } catch (Exception e2){
            System.out.println("No salio U_U");
            res = e2.getMessage();
            e2.printStackTrace();
        } finally{
            try{
                //JDBC.getInstance().closeResultSet(rs);
                stm.close();
                pool.release(conn);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return res;
    }
    
    public static String BuscarEmpresa(Empresa e, EmpresaList List) {
        String res = null;
        DBConnectionFactory pool = DBConnectionFactory.getInstance();
        Connection conn = null;
        Statement stm = null;
        try {
            System.out.println("Antes de Tener la Conexion");
            String sql = "SELECT PART.CUR_PRTC AS COD_REGISTRAL,RPU.NOMBRE AS ZONA,OFI.NOMBRE AS OFICINA,PART.RAZON_SOCIAL AS RZ,PART.SIGLAS AS SIGLAS,PLIB.NOMBRE AS TIPO_PARTICIPACION,TM.DESCRIPCION AS DESCRIPCION_LIBRO,P.NUM_PARTIDA AS NROPART,DECODE(IND.ESTADO,'1','ACTIVO','0','INACTIVO') AS ESTADO,IND.NS_GRAVAMEN AS GARANTIA " +
                    "FROM USER1.PRTC_JUR PART,USER1.IND_PRTC IND,USER1.PARTIDA P,USER1.REGIS_PUBLICO RPU,USER1.OFIC_REGISTRAL OFI,USER1.PARTIC_LIBRO PLIB,USER1.TM_LIBRO TM " +
                    "WHERE PART.CUR_PRTC=IND.CUR_PRTC AND P.REFNUM_PART=IND.REFNUM_PART AND PART.OFIC_REG_ID=OFI.OFIC_REG_ID  AND PART.REG_PUB_ID=OFI.REG_PUB_ID AND P.REG_PUB_ID=OFI.REG_PUB_ID AND P.OFIC_REG_ID=OFI.OFIC_REG_ID AND PART.REG_PUB_ID=RPU.REG_PUB_ID  AND IND.COD_PARTIC = PLIB.COD_PARTIC AND PLIB.COD_LIBRO=TM.COD_LIBRO AND P.COD_LIBRO IN ('022','023','043','077','062','088','038','054','040','015','010') AND PART.RAZON_SOCIAL LIKE '"+e.getRazon_social()+"%' "+e.getSql()+"";//Escribir query
            System.out.println(sql);
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            DBConnection dconn = new DBConnection(conn);
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            System.out.println(sql);
            while (rs.next()){
                Empresa e1 = new Empresa();
                e1.setRazon_social(rs.getString("RZ"));
                e1.setZona(rs.getString("ZONA"));
                e1.setNum_partida(rs.getString("COD_REGISTRAL"));
                e1.setTipo(rs.getString("TIPO_PARTICIPACION"));
                e1.setLugar(rs.getString("OFICINA"));
                e1.setDescripcion_libro(rs.getString("DESCRIPCION_LIBRO"));
                e1.setNum_partida(rs.getString("NROPART"));
                e1.setEstado(rs.getString("ESTADO"));
                List.add(e1);
            }
        } catch (Exception e2){
            System.out.println("No salio U_U");
            res = e2.getMessage();
            e2.printStackTrace();
        } finally{
            try{
                stm.close();
                pool.release(conn);
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return res;
    }
    
    
    
    
    
}
