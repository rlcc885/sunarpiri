package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class AbonoBean extends SunarpBean{


	private String usuario;
	private String nombre;
	private String contratoId;
	private String monto_bruto;
	private String lineaPrePago;
	private String tipoPago;
	private String bancoId;
	private String tipoCheque;
	private String numCheque;
	private String concAbono;
	
	/**
	 * Gets the usuario
	 * @return Returns a String
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * Sets the usuario
	 * @param usuario The usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	 * Gets the contratoId
	 * @return Returns a String
	 */
	public String getContratoId() {
		return contratoId;
	}
	/**
	 * Sets the contratoId
	 * @param contratoId The contratoId to set
	 */
	public void setContratoId(String contratoId) {
		this.contratoId = contratoId;
	}

	/**
	 * Gets the monto_bruto
	 * @return Returns a String
	 */
	public String getMonto_bruto() {
		return monto_bruto;
	}
	/**
	 * Sets the monto_bruto
	 * @param monto_bruto The monto_bruto to set
	 */
	public void setMonto_bruto(String monto_bruto) {
		this.monto_bruto = monto_bruto;
	}

	/**
	 * Gets the lineaPrePago
	 * @return Returns a String
	 */
	public String getLineaPrePago() {
		return lineaPrePago;
	}
	/**
	 * Sets the lineaPrePago
	 * @param lineaPrePago The lineaPrePago to set
	 */
	public void setLineaPrePago(String lineaPrePago) {
		this.lineaPrePago = lineaPrePago;
	}

	/**
	 * Gets the tipoPago
	 * @return Returns a String
	 */
	public String getTipoPago() {
		return tipoPago;
	}
	/**
	 * Sets the tipoPago
	 * @param tipoPago The tipoPago to set
	 */
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	/**
	 * Gets the bancoId
	 * @return Returns a String
	 */
	public String getBancoId() {
		return bancoId;
	}
	/**
	 * Sets the bancoId
	 * @param bancoId The bancoId to set
	 */
	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}

	/**
	 * Gets the tipoCheque
	 * @return Returns a String
	 */
	public String getTipoCheque() {
		return tipoCheque;
	}
	/**
	 * Sets the tipoCheque
	 * @param tipoCheque The tipoCheque to set
	 */
	public void setTipoCheque(String tipoCheque) {
		this.tipoCheque = tipoCheque;
	}

	/**
	 * Gets the numCheque
	 * @return Returns a String
	 */
	public String getNumCheque() {
		return numCheque;
	}
	/**
	 * Sets the numCheque
	 * @param numCheque The numCheque to set
	 */
	public void setNumCheque(String numCheque) {
		this.numCheque = numCheque;
	}

	/**
	 * Gets the concAbono
	 * @return Returns a String
	 */
	public String getConcAbono() {
		return concAbono;
	}
	/**
	 * Sets the concAbono
	 * @param concAbono The concAbono to set
	 */
	public void setConcAbono(String concAbono) {
		this.concAbono = concAbono;
	}

}

