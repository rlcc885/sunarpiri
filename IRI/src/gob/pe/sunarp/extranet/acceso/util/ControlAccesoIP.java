package gob.pe.sunarp.extranet.acceso.util;

import gob.pe.sunarp.extranet.util.Propiedades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class ControlAccesoIP 
{   
    public static String obtenerIPRemota(HttpServletRequest request) 
    {
		String ivRemoteAddress = null;
		if (Propiedades.getInstance().getFlagProduccion() == false) 
		{
			//Valido solo para el entorno de desarrollo, para poder trabajar sin el 
			// Tivoli Access Manager for e-Business
			ivRemoteAddress = request.getRemoteAddr();
			//Fin Desarrollo
		}
		else
		{	
	        try
	        {
		        Enumeration headers = request.getHeaderNames();
		        while (headers.hasMoreElements()) 
		        {
		            String header = (String) headers.nextElement();
		            if ("IV-REMOTE-ADDRESS".equalsIgnoreCase(header)) 
		            {
		                ivRemoteAddress = request.getHeader(header);
		                System.out.println("IV-REMOTE-ADDRESS: " + ivRemoteAddress);
		                break;
		            }
		        }
	        }
	        catch (Exception e)
	        {
	        	ivRemoteAddress = null;
	        }        
		}   
        return ivRemoteAddress;
    }

    public static String[] obtenerRangoIP(String usuario, Connection connection) throws Exception
    {
        String [] rangos = null;
        String strSQL = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try 
        {
            //HACIA LA BODEGA CENTRAL
            strSQL = "SELECT IP FROM AUDIT_CONTROLES_IP";
            strSQL = strSQL + " WHERE USR_ID = ?";
            pstmt = connection.prepareStatement(strSQL);
            pstmt.setString(1, usuario.trim().toUpperCase());
            rs = pstmt.executeQuery(); 
            if (!rs.next()) 
            {
                rangos = null;
            }
            else 
            {
                String listaRangos = rs.getString("IP");
                rangos = StringUtils.split(listaRangos, ";");
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
        return rangos;
    }

    public static boolean accesoPermitidoIP(String direccionIPRemota, String[] rangosIP) 
    {
        int tamano = rangosIP.length;
        boolean rpta = false;
        for (int i = 0; i < tamano; i++)
        {
            String rango = rangosIP[i];
            if (direccionIPRemota.equals(rango))
            {
                rpta = true;
                break;                
            }
            
            if (rango.indexOf("*") > -1)
            {
                rpta = true;
                break;
            }
            
            if (rango.indexOf(":") > -1)
            {
                String[] listaDireccionesIP = listaIP(rango);
                if (listaDireccionesIP != null)
                {
                    int tamanoLista = listaDireccionesIP.length;
                    for (int j = 0; j < tamanoLista; j++)
                    {
                        String direccionIP = listaDireccionesIP[j];
                        if (direccionIPRemota.equals(direccionIP))
                        {
                            rpta = true;
                            break;
                        }
                    }
                }
                else
                {
                    rpta = false;
                }
            }
            if (rpta)
            {
            	break;
            }
        }
        return rpta;
    }

    public static boolean cualquierIPPermitida(String[] rangosIP) 
    {
        int tamano = rangosIP.length;
        boolean rpta = false;
        for (int i = 0; i < tamano; i++)
        {
            String rango = rangosIP[i];
            if (rango.indexOf("*") > -1)
            {
                rpta = true;
                break;
            }
            else
            {
                rpta = false;
                break;
            }
        }
        return rpta;
    }
  
    private static String[] listaIP(String rangoIP) 
    {
        String[] listaDireccionesIP = null;
        String[] direccionesIP = StringUtils.split(rangoIP, ":");
        if ((direccionesIP[0] != null) && (direccionesIP[1] != null))
        {
            String[] direccionIPInicial = StringUtils.split(direccionesIP[0], ".");
            String[] direccionIPFinal = StringUtils.split(direccionesIP[1], ".");
            if ((direccionIPInicial[0].equals(direccionIPFinal[0])) && (direccionIPInicial[1].equals(direccionIPFinal[1])) && (direccionIPInicial[2].equals(direccionIPFinal[2])))
            {
                String rango = direccionIPInicial[0] + "." + direccionIPInicial[1] + "." + direccionIPInicial[2]; 
                int valorInicial = Integer.parseInt(direccionIPInicial[3]);
                int valorFinal = Integer.parseInt(direccionIPFinal[3]);
                if (valorFinal > valorInicial)                
                {
                	int tamano = (valorFinal - valorInicial) + 1;
                    listaDireccionesIP = new String[tamano];
                    for (int i = 0; i < tamano; i++)
                    {
                        listaDireccionesIP[i] = rango + "." + valorInicial;
                        valorInicial++;
                    }
                    return listaDireccionesIP;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;    
        }
    }

}
