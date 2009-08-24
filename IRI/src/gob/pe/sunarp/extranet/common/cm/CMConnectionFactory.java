package gob.pe.sunarp.extranet.common.cm;

//import com.ibm.mm.sdk.client.*;
import com.ibm.mm.sdk.server.*;
import com.ibm.mm.sdk.common.*;

import gob.pe.sunarp.extranet.common.ObjectPool;
import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * 
 * @author jacaceres - CM8 - 22/02/07
 *	Cambios en la clase CMConnectionFactory
 */
public class CMConnectionFactory extends ObjectPool {

	private static Hashtable factories = new Hashtable();

	/**
	 * @author jacaceres
	 * Cambio del metodo getInstance, no recibe parametros, 
	 * ya que trabaja con 1 factory
	 */
	/*
	public static CMConnectionFactory getInstance(String ID) {
		CMConnectionFactory factory = (CMConnectionFactory)factories.get(ID);
		if (factory == null) {
			factory = new CMConnectionFactory();
			factory.id = ID;
			factories.put(ID, factory);
		}
		return factory;
	}
	*/
	public static CMConnectionFactory getInstance() {
		if (factory == null) {
			factory = new CMConnectionFactory();	
		}
		return factory;
	}
	/****** fin *****/

	private CMConnectionFactory() {
		super();
	}
	
	private String libServer;
	private String user;
	private String pass;
	private String id;
	private String lockRelease = "lockRelease";

	//jacaceres - CM8 - Se utilizara solo "factory"
	private static CMConnectionFactory factory;
	//jacaceres - fin

	public String getStatus() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("CM CONNECTION FACTORY:").append(id).append("\r\n");
		buffer.append("\tLIBSERVER     -> ").append(libServer).append("\r\n");
		buffer.append("\tUSER          -> ").append(user).append("\r\n");
		buffer.append("\tMAX SIZE -> ").append(getMaxSize()).append("\r\n");
		buffer.append("\tSIZE     -> ").append(getSize()).append("\r\n");
		buffer.append("\tACT SIZE -> ").append(getLockedSize()).append("\r\n");
		buffer.append("\tINAC SIZE -> ").append(getUnlockedSize()).append("\r\n");
		buffer.append("\tEXP TIME -> ").append(getExpirationTime()).append("\r\n");
		buffer.append("\tTIME OUT -> ").append(getTimeOut()).append("\r\n");
		buffer.append("\tTHREAD   -> ").append(this.currentThread).append("\r\n");
		buffer.append("\tTIMEOUTS -> ").append(this.timeOutOcurred).append("\r\n");
		return buffer.toString();
	}

	/**
	 * @author jacaceres
	 * Cambios en el metodo - CM8
	 */
	/*	
	protected Object create() throws Exception {
		DKDatastoreDL dsDL = new DKDatastoreDL();
		dsDL.connect(libServer,user,pass,"");
		return dsDL;
	}
	*/
	protected Object create() throws Exception {		
		//DKDatastoreICM dsDL = new DKDatastoreICM("ICMENVFILE=(/home/ibmcmadm/cmgmt/connectors/cmbicmenv.ini)");
		ResourceBundle res = ResourceBundle.getBundle("gob.pe.sunarp.extranet.common.properties.cm");
		DKDatastoreICM dsDL = new DKDatastoreICM(res.getString("ruta"));
		//dsDL.connect(libServer,user,pass,"");
		dsDL.connect(libServer,user,pass,"SCHEMA=ICMADMIN");		
		return dsDL;
	}
	/***** fin *****/
	
	protected boolean validate(Object o) {
		try {
			//jacaceres - CM8
			//return ((DKDatastoreDL) o).isConnected();
			return ((DKDatastoreICM) o).isConnected();
		} catch (Exception e) {
			System.out.println("CMConnectionFactory.validate: " + e.toString());
			return false;
		}
	}
	
	protected void expire(Object o) {
		try {
			//jacaceres - CM8
			//DKDatastoreDL dl = (DKDatastoreDL)o;
			DKDatastoreICM dl = (DKDatastoreICM)o;
			if (dl != null) {
				if (dl.isConnected()) {
					dl.disconnect();
				}
				dl.destroy();
				dl = null;
			}
		} catch (Exception e) {
			System.out.println("CMConnectionFactory.expire: " + e.toString());
		}
	}

	public void init(String libServer, String user, String pass, int maxPoolSize, long expiryTime, long timeOut) throws CreateCMDatastoreException {
		this.libServer = libServer;
		this.user = user;
		this.pass = pass;
		
		setExpirationTime(expiryTime);
		setMaxSize(maxPoolSize);
		setTimeOut(timeOut);

		//jacaceres - CM8
		//DKEnvironment.setSubSystem(DKConstantDL.DK_CM_SS_CONSOLE);
		DKEnvironment.setSubSystem(DKConstantICM.DK_CM_SS_CONSOLE);

		//DKDatastoreDL ds = get();
		DKDatastoreICM ds = get();
		release(ds);
	}

	/**
	 * @author jacaceres
	 *	El metodo get() retorna un objeto de tipo DKDatastoreICM
	 */
	/*
	public synchronized DKDatastoreDL get() throws CreateCMDatastoreException {
		try {
			return (DKDatastoreDL) checkOut();
		} catch (Exception e) {
			throw new CreateCMDatastoreException(e.toString());
		}
	}
	*/
	public synchronized DKDatastoreICM get() throws CreateCMDatastoreException {
		try {
			//checkStatusConecction();
			return (DKDatastoreICM)checkOut();
		} catch (Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
			throw new CreateCMDatastoreException(e.toString());
		}
	}
	/****** fin ******/

	/**
	 * @author jacaceres
	 * El metodo release() recibira un parametro de tipo DKDatastoreICM
	 */
	/*
	public void release(DKDatastoreDL connection) {
		if (connection != null) {
			checkIn(connection);
		}
	}
	*/
	public void release(DKDatastoreICM connection) {
		if (connection != null) {
			checkIn(connection);
		}
	}	
	/****** fin ******/

}