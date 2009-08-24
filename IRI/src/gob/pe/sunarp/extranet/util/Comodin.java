package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.common.SunarpBean;
import sun.java2d.loops.ScaledBlit;

public final class Comodin extends SunarpBean{
	private static Comodin singleton = null;
	public static synchronized Comodin getInstance()
	{
		if(singleton == null)
		{
			singleton = new Comodin();
		}
		return singleton;
	}
	private Comodin()
	{
		
	}
	
	private String usuario = Constantes.COMODIN_USUARIO;
	private int lineaPrePago = 0;
	private int contratoAbono = 0;
	

	/**
	 * Gets the usuario
	 * @return Returns a String
	 */
	public String getUsuario() {
		return usuario;
	}
	
	//No hay setter
	/**
	 * Gets the lineaPrePago
	 * @return Returns a int
	 */
	public int getLineaPrePago() {
		return lineaPrePago;
	}
	/**
	 * Sets the lineaPrePago
	 * @param lineaPrePago The lineaPrePago to set
	 */
	public void setLineaPrePago(int lineaPrePago) {
		this.lineaPrePago = lineaPrePago;
	}

	/**
	 * Gets the contratoAbono
	 * @return Returns a int
	 */
	public int getContratoAbono() {
		return contratoAbono;
	}
	/**
	 * Sets the contratoAbono
	 * @param contratoAbono The contratoAbono to set
	 */
	public void setContratoAbono(int contratoAbono) {
		this.contratoAbono = contratoAbono;
	}

}

