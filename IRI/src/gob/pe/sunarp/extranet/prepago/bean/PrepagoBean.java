package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class PrepagoBean extends SunarpBean{


	private String bancoId;
	private String tipoCheque;
	private String numCheuqe;
	
	private boolean flag_efectivo;
	private String lineaPrepagoId;	// Linea Prepago del cliente 
	private String usuario;			// Cliente a quien se le modificara linea prepago
	private double saldo;
	private double comision;
	private boolean flag_ventan;
	private String medioId;
	private boolean abono;
	
	private double montoBruto;
	private double montoNeto;
	private double descuento;
	
	private boolean flag_transferencia;
	private long transacId;
	
	private boolean esExtorno = false;
	private boolean esFgDeposito = false;
	//Para Publicidad Certificada
	private String tipoConsAbono = "A"; //P para Publicidad Certificada
	private String consumoId; //Para guardar el consumo de la solicitud
	
	/**
	 * inicio: dbravo: 31/07/2007 
	 */
	private int codigoServicio;
	/**
	 * fin: dbravo: 31/07/2007 
	 */
	
	
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
	 * Gets the numCheuqe
	 * @return Returns a String
	 */
	public String getNumCheuqe() {
		return numCheuqe;
	}
	/**
	 * Sets the numCheuqe
	 * @param numCheuqe The numCheuqe to set
	 */
	public void setNumCheuqe(String numCheuqe) {
		this.numCheuqe = numCheuqe;
	}

	/**
	 * Gets the flag_efectivo
	 * @return Returns a boolean
	 */
	public boolean getFlag_efectivo() {
		return flag_efectivo;
	}
	/**
	 * Sets the flag_efectivo
	 * @param flag_efectivo The flag_efectivo to set
	 */
	public void setFlag_efectivo(boolean flag_efectivo) {
		this.flag_efectivo = flag_efectivo;
	}

	/**
	 * Gets the lineaPrepagoId
	 * @return Returns a String
	 */
	public String getLineaPrepagoId() {
		return lineaPrepagoId;
	}
	/**
	 * Sets the lineaPrepagoId
	 * @param lineaPrepagoId The lineaPrepagoId to set
	 */
	public void setLineaPrepagoId(String lineaPrepagoId) {
		this.lineaPrepagoId = lineaPrepagoId;
	}

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
	 * Gets the saldo
	 * @return Returns a double
	 */
	public double getSaldo() {
		return saldo;
	}
	/**
	 * Sets the saldo
	 * @param saldo The saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * Gets the comision
	 * @return Returns a double
	 */
	public double getComision() {
		return comision;
	}
	/**
	 * Sets the comision
	 * @param comision The comision to set
	 */
	public void setComision(double comision) {
		this.comision = comision;
	}

	/**
	 * Gets the flag_ventan
	 * @return Returns a boolean
	 */
	public boolean getFlag_ventan() {
		return flag_ventan;
	}
	/**
	 * Sets the flag_ventan
	 * @param flag_ventan The flag_ventan to set
	 */
	public void setFlag_ventan(boolean flag_ventan) {
		this.flag_ventan = flag_ventan;
	}

	/**
	 * Gets the medioId
	 * @return Returns a String
	 */
	public String getMedioId() {
		return medioId;
	}
	/**
	 * Sets the medioId
	 * @param medioId The medioId to set
	 */
	public void setMedioId(String medioId) {
		this.medioId = medioId;
	}

	/**
	 * Gets the abono
	 * @return Returns a boolean
	 */
	public boolean getAbono() {
		return abono;
	}
	/**
	 * Sets the abono
	 * @param abono The abono to set
	 */
	public void setAbono(boolean abono) {
		this.abono = abono;
	}

	/**
	 * Gets the montoBruto
	 * @return Returns a double
	 */
	public double getMontoBruto() {
		return montoBruto;
	}
	/**
	 * Sets the montoBruto
	 * @param montoBruto The montoBruto to set
	 */
	public void setMontoBruto(double montoBruto) {
		this.montoBruto = montoBruto;
	}

	/**
	 * Gets the montoNeto
	 * @return Returns a double
	 */
	public double getMontoNeto() {
		return montoNeto;
	}
	/**
	 * Sets the montoNeto
	 * @param montoNeto The montoNeto to set
	 */
	public void setMontoNeto(double montoNeto) {
		this.montoNeto = montoNeto;
	}

	/**
	 * Gets the descuento
	 * @return Returns a double
	 */
	public double getDescuento() {
		return descuento;
	}
	/**
	 * Sets the descuento
	 * @param descuento The descuento to set
	 */
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	/**
	 * Gets the flag_transferencia
	 * @return Returns a boolean
	 */
	public boolean getFlag_transferencia() {
		return flag_transferencia;
	}
	/**
	 * Sets the flag_transferencia
	 * @param flag_transferencia The flag_transferencia to set
	 */
	public void setFlag_transferencia(boolean flag_transferencia) {
		this.flag_transferencia = flag_transferencia;
	}

	/**
	 * Gets the transacId
	 * @return Returns a long
	 */
	public long getTransacId() {
		return transacId;
	}
	/**
	 * Sets the transacId
	 * @param transacId The transacId to set
	 */
	public void setTransacId(long transacId) {
		this.transacId = transacId;
	}

	/**
	 * Gets the esExtorno
	 * @return Returns a boolean
	 */
	public boolean getEsExtorno() {
		return esExtorno;
	}
	/**
	 * Sets the esExtorno
	 * @param esExtorno The esExtorno to set
	 */
	public void setEsExtorno(boolean esExtorno) {
		this.esExtorno = esExtorno;
	}


	/**
	 * Sets the esFgDeposito
	 * @param esFgDeposito The esFgDeposito to set
	 */
	public void setEsFgDeposito(boolean esFgDeposito) {
		this.esFgDeposito = esFgDeposito;
	}

	/**
	 * Gets the esFgDeposito
	 * @return Returns a boolean
	 */
	public boolean getEsFgDeposito() {
		return esFgDeposito;
	}
	/**
	 * Gets the tipoConsAbono
	 * @return Returns a String
	 */
	public String getTipoConsAbono() {
		return tipoConsAbono;
	}
	/**
	 * Sets the tipoConsAbono
	 * @param tipoConsAbono The tipoConsAbono to set
	 */
	public void setTipoConsAbono(String tipoConsAbono) {
		this.tipoConsAbono = tipoConsAbono;
	}

	/**
	 * Gets the consumoId
	 * @return Returns a String
	 */
	public String getConsumoId() {
		return consumoId;
	}
	/**
	 * Sets the consumoId
	 * @param consumoId The consumoId to set
	 */
	public void setConsumoId(String consumoId) {
		this.consumoId = consumoId;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 31, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the codigoServicio
	 */
	public int getCodigoServicio() {
		return codigoServicio;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 31, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param codigoServicio the codigoServicio to set
	 */
	public void setCodigoServicio(int codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

}

