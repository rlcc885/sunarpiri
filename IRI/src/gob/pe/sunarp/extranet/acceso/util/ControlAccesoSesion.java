package gob.pe.sunarp.extranet.acceso.util;

import gob.pe.sunarp.extranet.util.Propiedades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class ControlAccesoSesion {
    
    public static boolean indicadorSesionesActivo(String usuario, Connection connection) throws Exception{
    
        String strSQL = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean rpta = false;

        try{
            //HACIA LA BODEGA CENTRAL
            strSQL = "SELECT USR_ID, FLAG_SESION FROM AUDIT_CONTROLES_IP";
            strSQL = strSQL + " WHERE USR_ID = ?";
            pstmt = connection.prepareStatement(strSQL);
            pstmt.setString(1, usuario);
            rs = pstmt.executeQuery(); 
            if (!rs.next()) {
                rpta = false;
            }else {
                String flag = rs.getString("FLAG_SESION");
                if ("0".equals(flag)) {
                    rpta = false;
                }else {
                    rpta = true;
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            rs.close();
            pstmt.close();
        }
        return rpta;            
    }
    
    public static boolean tieneSesionesActivas(String idCuenta, String usuario, Connection connection) throws Exception
    {
        String strSQL = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean rpta = false;
        try {
        	/**
        	 * SVASQUEZ - AVATAR GLOBAL
        	 * SOLO SE HACE UN COUNT(1) A UN COLUMNA DE LA TABLA
        	 */        	
            //HACIA LA BODEGA CENTRAL
            strSQL = "SELECT COUNT(1) AS EXISTE FROM AUDIT_INICIO_SESION";
            strSQL = strSQL + " WHERE USR_ID = ? AND TS_ACCESO_FIN IS NULL";
            pstmt = connection.prepareStatement(strSQL);
            pstmt.setString(1, usuario);
            rs = pstmt.executeQuery(); 
            if (!rs.next()) {
                rpta = false;    
            }else {
            	int existe = rs.getInt("EXISTE");	
                if (existe == 0){
                	rpta = false;
                }else{
                	rpta = true;
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            rs.close();
            pstmt.close();
        }
        return rpta;
    }

    
    public static boolean registrarFinSesion(String idSesion, Connection connection) throws Exception{
        String strSQL = null;
        PreparedStatement pstmt = null;
        boolean rpta = false;
        try {
            //HACIA LA BODEGA CENTRAL
            strSQL = "UPDATE AUDIT_INICIO_SESION";
            strSQL = strSQL + " SET TS_ACCESO_FIN = ?";
            strSQL = strSQL + " WHERE SESION_ID = ?";
            pstmt = connection.prepareStatement(strSQL);
            pstmt.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setString(2, idSesion);
            pstmt.execute();
            rpta = true;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            pstmt.close();
        }
        return rpta;        
    }
    
    public static boolean registrarNuevaSesion(String usuario, String idSesion, String ipSesion, String idEquipo, Connection connection) throws Exception
    {
        String strSQL = null;
        PreparedStatement pstmt = null;
        boolean rpta = false;
        try {
            //HACIA LA BODEGA CENTRAL
            strSQL = "INSERT INTO AUDIT_INICIO_SESION";
            strSQL = strSQL + " (SESION_ID, USR_ID, IP_SESION, EQUIPO_ID, TS_ACCESO_INICIO)";
            strSQL = strSQL + " values (?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(strSQL);
            pstmt.setString(1, idSesion);
            pstmt.setString(2, usuario);
            pstmt.setString(3, ipSesion);
            pstmt.setString(4, idEquipo);
            pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.execute();
            rpta = true;            
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            pstmt.close();
        }
        return rpta;
    }  
    
    //Fecha: 07/10/2006
    public static boolean existeSesion(String idSesion, Connection connection) throws Exception
    {
        String strSQL = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean rpta = false;
        try {
        	/**
        	 * SVASQUEZ - AVATAR GLOBAL
        	 * SOLO SE HACE UN COUNT(1) A UN COLUMNA DE LA TABLA
        	 */        	
            //HACIA LA BODEGA CENTRAL
            strSQL = "SELECT COUNT(1) AS EXISTE FROM AUDIT_INICIO_SESION";
            strSQL = strSQL + " WHERE SESION_ID = ?";
            pstmt = connection.prepareStatement(strSQL);
            pstmt.setString(1, idSesion);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
            	int existe = rs.getInt("Existe");
            	if (existe == 1){
            		rpta = true;
            	}else{
            		rpta = false;
            	}
            }
            else{
            	rpta = false;
            }
        }
        catch (Exception e) 
        {
            throw e;
        }
        finally 
        {
            rs.close();
            pstmt.close();
        }
        return rpta;
    }
    
    //Fecha: 07/10/2006      
    public static String obtenerAMIdSesion(HttpServletRequest request) 
    {
		String pdUserSesionId = null;
	
		if (Propiedades.getInstance().getFlagProduccion() == false) {
			//Valido solo para el entorno de desarrollo, para poder trabajar sin el 
			// Tivoli Access Manager for e-Business
			pdUserSesionId = request.getSession(false).getId();
			//Fin Desarrollo
		}
		else
		{
        	// Para el Pase a Produccion se debe descomentar este codigo para poder interactuar
        	// con el Tivoli Access Manager for e-Business y se pueda obtener el Id de Sesion Unico.
			try{
	        	Enumeration headers = request.getHeaderNames();
	        	while (headers.hasMoreElements()) {
	            	String header = (String) headers.nextElement();
	            	if ("user-session-id".equalsIgnoreCase(header)) {
	               		pdUserSesionId = request.getHeader(header);
	               		break;
	            	}
	        	}		        
	        	if (pdUserSesionId.length() == 0){		
					pdUserSesionId = (String) request.getSession().getAttribute("user-session-id");
	       		}
       		}
       		catch (Exception e)
       		{
       			pdUserSesionId = null;
        	}
			//Fin Produccion
		}
        return pdUserSesionId;
    }    
}
