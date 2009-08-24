package gob.pe.sunarp.extranet.common.cm;

import com.ibm.mm.sdk.server.*;
import com.ibm.mm.sdk.common.*;

public class CMConnectionFactoryOld {
	private static CMConnectionFactoryOld factory;
	public static CMConnectionFactoryOld getInstance() {
		if (factory == null) {
			factory = new CMConnectionFactoryOld();
		}
		return factory;
	}
	private CMConnectionFactoryOld() {
		super();
	}
	
	private String libServer;
	private String user;
	private String pass;
	private DKDatastoreDL[] datastores = null;
	private boolean[] inUseDatastore = null;

	private String lockRelease = "lockRelease";

	public String getStatus() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("CM CONNECTION FACTORY:\r\n");
		buffer.append("\tLIBSERVER -> ").append(libServer).append("\r\n");
		buffer.append("\tUSER      -> ").append(user).append("\r\n");
		buffer.append("\tPOOL SIZE -> ").append(inUseDatastore.length).append("\r\n");
		int usadas = 0;
		for (int i = 0; i < inUseDatastore.length; i++) {
			if (inUseDatastore[i]) usadas++;
		}
		buffer.append("\tUSADAS    -> ").append(usadas).append("\r\n");
		return buffer.toString();
	}
	
	private DKDatastoreDL createConnection() throws CreateCMDatastoreException {
		try {
			DKDatastoreDL dsDL = new DKDatastoreDL();
			dsDL.connect(libServer,user,pass,"");
			return dsDL;
		} catch (Exception e) {
			throw new CreateCMDatastoreException(e.toString());
		}
	}
	
	public void close() {
		for (int i = 0; i < datastores.length; i++) {
			try {
				if (datastores[i] != null) {
					datastores[i].disconnect();
					datastores[i].destroy();
					datastores[i] = null;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public void init(String libServer, String user, String pass, int poolSize) throws CreateCMDatastoreException {
		System.out.println("user.dir=" + System.getProperty("user.dir"));
		this.libServer = libServer;
		this.user = user;
		this.pass = pass;

		datastores = new DKDatastoreDL[poolSize];
		inUseDatastore = new boolean[poolSize];

		DKEnvironment.setSubSystem(DKConstantDL.DK_CM_SS_CONSOLE);
		for (int i = 0; i < poolSize; i++) {
			datastores[i] = createConnection();
			inUseDatastore[i] = false;
		}
	}

	public synchronized DKDatastoreDL get() throws CreateCMDatastoreException {
		DKDatastoreDL resultado = null;
		while (resultado == null) {

			for (int i = 0; i < inUseDatastore.length; i++) {
				if (!inUseDatastore[i]) {
					try {
						if (!datastores[i].isConnected()) {
							datastores[i].destroy();
							datastores[i] = createConnection();
						}
					} catch (CreateCMDatastoreException e) {
						throw e;
					} catch (Exception e) {
						datastores[i] = createConnection();
					}
					resultado = datastores[i];
					inUseDatastore[i] = true;
					break;
				}
			}

			if (resultado == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("Aplicativo demorará un poco en terminar");
					System.out.println("Tratando recuperar conexion a CM nuevamente");
				}
			}
		}
		notifyAll();
		return resultado;
	}

	public void release(DKDatastoreDL connection) {
		if (connection == null) return;

		boolean founded = false;
		synchronized(lockRelease) {
			for (int i = 0; i < datastores.length; i++) {
				if (datastores[i].equals(connection)) {
					inUseDatastore[i] = false;
					founded = true;
					break;
				}
			}
		}
		if (!founded) throw new IllegalArgumentException("La conexion que se quiere liberar no pertenece a este pool de conexiones");
	}
}