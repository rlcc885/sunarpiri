package gob.pe.sunarp.extranet.acceso.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

public class ControlAccesoFeriados
{
    public static boolean verificaAccesoFeriados(Date fechaHoy, Connection connection) throws Exception
    {
        String strSQL = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean rpta = false;
        try 
        {
            //HACIA LA BODEGA CENTRAL
            strSQL = "SELECT FE_NO_LABO FROM DIA_NO_LABO";
            strSQL = strSQL + " WHERE FE_NO_LABO = ?";
            pstmt = connection.prepareStatement(strSQL);
            java.sql.Date fechaQuery = new java.sql.Date(fechaHoy.getTime());
            pstmt.setDate(1, fechaQuery);
            rs = pstmt.executeQuery(); 
            if (!rs.next()) 
            {
                rpta = false;
            }
            else 
            {
                rpta = true;
            }
        }
        catch (Exception e) 
        {
            throw e;
        }
        finally 
        {
            pstmt.close();
            rs.close();
        }
        return rpta;
    }
}
