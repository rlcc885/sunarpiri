package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class MovimientosDetalleBean extends SunarpBean{
	
	private String abonoId;
	private String fecha;
	private String hora;
	private String monto;
	private String nombre;
	private String tipoPagoVentanilla;
	private String lineaPrepago;
	

	/**
	 * Gets the abonoId
	 * @return Returns a String
	 */
	public String getAbonoId() {
		return abonoId;
	}
	/**
	 * Sets the abonoId
	 * @param abonoId The abonoId to set
	 */
	public void setAbonoId(String abonoId) {
		this.abonoId = abonoId;
	}

	/**
	 * Gets the fecha
	 * @return Returns a String
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * Sets the fecha
	 * @param fecha The fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the monto
	 * @return Returns a String
	 */
	public String getMonto() {
		return monto;
	}
	/**
	 * Sets the monto
	 * @param monto The monto to set
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}

	/**
	 * Gets the nombre
	 * @return Returns a String
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Sets the nombre
	 * @param nombre The nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the tipoPagoVentanilla
	 * @return Returns a String
	 */
	public String getTipoPagoVentanilla() {
		return tipoPagoVentanilla;
	}
	/**
	 * Sets the tipoPagoVentanilla
	 * @param tipoPagoVentanilla The tipoPagoVentanilla to set
	 */
	public void setTipoPagoVentanilla(String tipoPagoVentanilla) {
		this.tipoPagoVentanilla = tipoPagoVentanilla;
	}

	/**
	 * Gets the lineaPrepago
	 * @return Returns a String
	 */
	public String getLineaPrepago() {
		return lineaPrepago;
	}
	/**
	 * Sets the lineaPrepago
	 * @param lineaPrepago The lineaPrepago to set
	 */
	public void setLineaPrepago(String lineaPrepago) {
		this.lineaPrepago = lineaPrepago;
	}

	/**
	 * Gets the hora
	 * @return Returns a String
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * Sets the hora
	 * @param hora The hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

}

