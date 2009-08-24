package gob.pe.sunarp.extranet.publicidad.certificada;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboVwRegiCertOfic;
import gob.pe.sunarp.extranet.framework.Loggy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;

public class MonitorCargaLaboral {
	
	private static final MonitorCargaLaboral monitorCargaLaboral = new MonitorCargaLaboral();
	private final Hashtable estadoCargaLaboral = new Hashtable();
	private DBConnection dconn;

	/**
	 * Constructor for MonitorCargaLaboral
	 */
	private MonitorCargaLaboral() {
		
	}

    public static MonitorCargaLaboral getInstance( ) {
        return monitorCargaLaboral;
    }

    public void cargarRegistradores( ) {
    	Statement stmt = null;
    	ResultSet ctaIdReg = null;
      try{
        stmt = this.dconn.getMyConnection().createStatement();
        ctaIdReg = stmt.executeQuery("select distinct crite.cuenta_id from user1.cuenta cta, user1.criterios_asigna crite where crite.cuenta_id=cta.cuenta_id and cta.estado=1");
        while (ctaIdReg.next()){
        	setEstadoCargaRegistrador(ctaIdReg.getString("cuenta_id"),false);
        }
      }catch(SQLException e){
    	e.printStackTrace();
      }finally{
      	JDBC.getInstance().closeResultSet(ctaIdReg);
      	JDBC.getInstance().closeStatement(stmt);
      }
    }

    public synchronized void setEstadoCargaRegistrador(String cuentaID, boolean estadoCarga) {
        if(estadoCarga)
        	estadoCargaLaboral.put(cuentaID,"true");
        else
        	estadoCargaLaboral.put(cuentaID,"false");
    }
   
    public synchronized boolean getEstadoCargaRegistrador(String cuentaID) {
        if(estadoCargaLaboral.get(cuentaID)==null){
        	this.getInstance().setEstadoCargaRegistrador(cuentaID, true);
        	return true;
        } else if(estadoCargaLaboral.get(cuentaID).equals("true"))
        	return true;
        else
        	return false;
    }
    
    public void descargarRegistrador(String cuentaID) {
		estadoCargaLaboral.remove(cuentaID);
    }
    
    public void revisarDisponibilidadRegistradores() {
      try {
    	if (Loggy.isTrace(this)) System.out.println("Revisando Disponibilidad de Registradores!!!!");
    	DboVwRegiCertOfic dboRegistradoresDisponibles = new DboVwRegiCertOfic(dconn);
    	dboRegistradoresDisponibles.setFieldsToRetrieve(DboVwRegiCertOfic.CAMPO_REG_PUB_ID);
    	dboRegistradoresDisponibles.setFieldsToRetrieve(DboVwRegiCertOfic.CAMPO_OFIC_REG_ID);
    	dboRegistradoresDisponibles.setFieldsToRetrieve(DboVwRegiCertOfic.CAMPO_OFIC_NOMBRE);
    	dboRegistradoresDisponibles.setFieldsToRetrieve(DboVwRegiCertOfic.CAMPO_TIPO_CERTIFICADO);
    	dboRegistradoresDisponibles.setFieldsToRetrieve(DboVwRegiCertOfic.CAMPO_CERTIFICADO_NOMBRE);
    	dboRegistradoresDisponibles.setFieldsToRetrieve(DboVwRegiCertOfic.CAMPO_CANTIDAD);
    	
    	String oficinaActual = "";
    	String oficinaAnterior = "";
    	StringBuffer certificados = new StringBuffer();
    	StringBuffer libros = new StringBuffer();
    	StringBuffer mensaje = new StringBuffer();
    	boolean disponibilidadTotal = true;
    	for(Iterator i = dboRegistradoresDisponibles.searchAndRetrieveList(DboVwRegiCertOfic.CAMPO_OFIC_NOMBRE + " asc, " + DboVwRegiCertOfic.CAMPO_TIPO_CERTIFICADO + " desc").iterator(); i.hasNext();){
    		dboRegistradoresDisponibles = (DboVwRegiCertOfic) i.next();
    		if((dboRegistradoresDisponibles.getField(DboVwRegiCertOfic.CAMPO_CANTIDAD)).equals("0")){
    			oficinaActual = dboRegistradoresDisponibles.getField(DboVwRegiCertOfic.CAMPO_OFIC_NOMBRE);
    			if ((!oficinaAnterior.equals(""))&&(!oficinaActual.equals(oficinaAnterior))){
    				mensaje.append("La Oficina ")
    						.append(oficinaAnterior)
    						.append(" no tiene quien atienda las solicitudes de:");
    				if(certificados.length()!=0) mensaje.append(" (-) Certificados de ").append(certificados.toString());
    				if(libros.length()!=0) mensaje.append(" (-) Copia literal de Partidas del(os) Libro(s) de ").append(libros.toString());
    				System.out.println(mensaje.toString());
    				certificados = certificados.delete(0,certificados.length());
    				libros = libros.delete(0,libros.length());
    				mensaje = mensaje.delete(0,mensaje.length());
    				disponibilidadTotal = false;
    			}
   				if(dboRegistradoresDisponibles.getField(DboVwRegiCertOfic.CAMPO_TIPO_CERTIFICADO).equals("N")){
   					if(certificados.length()!=0) certificados.append(", ");
   					certificados.append(dboRegistradoresDisponibles.getField(DboVwRegiCertOfic.CAMPO_CERTIFICADO_NOMBRE));
   				}else if(dboRegistradoresDisponibles.getField(DboVwRegiCertOfic.CAMPO_TIPO_CERTIFICADO).equals("L")){
   					if(libros.length()!=0) libros.append(", ");
   					libros.append(dboRegistradoresDisponibles.getField(DboVwRegiCertOfic.CAMPO_CERTIFICADO_NOMBRE));
   				}
   				oficinaAnterior = oficinaActual;
    		}
    	}
    	if(disponibilidadTotal) System.out.println("Todas las solicitudes están cubiertas en todas las oficinas registrales.");
    	
      } catch (DBException e) {
      	e.printStackTrace();
      } catch (Throwable t) {
      	t.printStackTrace();
      }
    }

	/**
	 * Gets the dconn
	 * @return Returns a DBConnection
	 */
	public DBConnection getDconn() {
		return dconn;
	}
	/**
	 * Sets the dconn
	 * @param dconn The dconn to set
	 */
	public void setDconn(DBConnection dconn) {
		this.dconn = dconn;
	}

}

