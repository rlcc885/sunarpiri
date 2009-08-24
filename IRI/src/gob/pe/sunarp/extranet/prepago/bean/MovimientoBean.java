package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class MovimientoBean extends SunarpBean{


	private String numAbono;
	private String fecha;
	private String hora;
	private String tipo;
	private String monto;
	private String agencia;
	private String formaPago;
	private String cajeroId;
	private String comprobanteId;
	private String seleccionado;
	private String numConstancia;
	private String numZona;
	private String nombreZona;
	private double dmonto=0;
	
	/**
	 * Gets the numAbono
	 * @return Returns a String
	 */
	public String getNumAbono() {
		return numAbono;
	}
	/**
	 * Sets the numAbono
	 * @param numAbono The numAbono to set
	 */
	public void setNumAbono(String numAbono) {
		this.numAbono = numAbono;
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

	/**
	 * Gets the tipo
	 * @return Returns a String
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Sets the tipo
	 * @param tipo The tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * Gets the agencia
	 * @return Returns a String
	 */
	public String getAgencia() {
		return agencia;
	}
	/**
	 * Sets the agencia
	 * @param agencia The agencia to set
	 */
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	/**
	 * Gets the formaPago
	 * @return Returns a String
	 */
	public String getFormaPago() {
		return formaPago;
	}
	/**
	 * Sets the formaPago
	 * @param formaPago The formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	/**
	 * @return
	 */
	public String getCajeroId() {
		return cajeroId;
	}

	/**
	 * @param string
	 */
	public void setCajeroId(String string) {
		cajeroId = string;
	}

	/**
	 * @return
	 */
	public String getComprobanteId() {
		return comprobanteId;
	}

	/**
	 * @param string
	 */
	public void setComprobanteId(String string) {
		comprobanteId = string;
	}

	/**
	 * @return
	 */
	public String getSeleccionado() {
		return seleccionado;
	}

	/**
	 * @param string
	 */
	public void setSeleccionado(String string) {
		seleccionado = string;
	}

	/**
	 * @return
	 */
	public String getNumConstancia() {
		return numConstancia;
	}

	/**
	 * @param string
	 */
	public void setNumConstancia(String string) {
		numConstancia = string;
	}

	/**
	 * @return
	 */
	public String getNumZona() {
		return numZona;
	}

	/**
	 * @param string
	 */
	public void setNumZona(String string) {
		numZona = string;
	}

	/**
	 * @return
	 */
	public String getNombreZona() {
		return nombreZona;
	}

	/**
	 * @param string
	 */
	public void setNombreZona(String string) {
		nombreZona = string;
	}

	/**
	 * @return
	 */
	public double getDmonto() {
		return dmonto;
	}

	/**
	 * @param d
	 */
	public void setDmonto(double d) {
		dmonto = d;
	}

}

