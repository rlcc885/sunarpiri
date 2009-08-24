/**
 * 
 */
package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class ResultadoPartidaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8696925566736803720L;
	
	private String codigoError;
	private String montoPagado;
	private String fechaOperacion;
	private PartidaBean[] partidaBeans;
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param codigoError the codigoError to set
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaOperacion
	 */
	public String getFechaOperacion() {
		return fechaOperacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaOperacion the fechaOperacion to set
	 */
	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the montoPagado
	 */
	public String getMontoPagado() {
		return montoPagado;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param montoPagado the montoPagado to set
	 */
	public void setMontoPagado(String montoPagado) {
		this.montoPagado = montoPagado;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the partidaBeans
	 */
	public PartidaBean[] getPartidaBeans() {
		return partidaBeans;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param partidaBeans the partidaBeans to set
	 */
	public void setPartidaBeans(PartidaBean[] partidaBeans) {
		this.partidaBeans = partidaBeans;
	}
	
	
	
}
