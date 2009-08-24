package gob.pe.sunarp.extranet.pool;
/**
 * @author svasquez 
 * AVATAR - GLOBAL
 */
public class MonitorConnectionFactory {
	ObjectPoolMonitor objectPool;
	long timeSleep;

		
	
	/**
	 * @author svasquez
	 * Metodo: monitorear() 
	 * AVATAR - GLOBAL
	 */	
	public void monitorear(ObjectPool objeto) {
		try {			
			objectPool = new ObjectPoolMonitor(objeto);			
			objectPool.init(getTimeSleep());
			objectPool.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author svasquez
	 * Metodo: terminarListener() 
	 * AVATAR - GLOBAL
	 */
	public void terminarListener(){
		try {
			objectPool.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocurrio una excepcion al terminar el Hilo: Pool Monitor");
			e.printStackTrace();
		}		
	}
	
	/**
	 * @author svasquez
	 * Metodo: closeListener() 
	 * AVATAR - GLOBAL
	 */
	public void closeListener(){
		try {
			objectPool.close();	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocurrio una excepcion al terminar el Hilo: Pool Monitor");
			e.printStackTrace();
		}		
	}


	public long getTimeSleep() {
		return timeSleep;
	}


	public void setTimeSleep(long timeSleep) {
		this.timeSleep = timeSleep;
	}	
}
