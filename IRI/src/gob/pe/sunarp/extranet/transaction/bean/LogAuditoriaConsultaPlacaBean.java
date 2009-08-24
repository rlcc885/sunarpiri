package gob.pe.sunarp.extranet.transaction.bean;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
/** Kuma + Carlo **/
public class LogAuditoriaConsultaPlacaBean extends TransactionBean{
	private String paramBusqueda;	//Identifica el Número de Partida o Número de Placa o Nombre Apellido o Razon Social
	private String oficRegId;
	private String regPubId;
	private int tipoBusq;	//  0 = Placa, 1 = Partida, 2 = Nombre Apellido, 3 = Razon Social
	private String tipoParticipacion;	// 1 = Partida, 4 = Placa
	private String numSedes = "1"; // Para este caso es 1
	private String codAreaReg; // Area Registral
	private String TipoPersPart;	/**
	 * Gets the paramBusqueda
	 * @return Returns a String
	 */
	
	public String getParamBusqueda() {
		return paramBusqueda;
	}
	/**
	 * Sets the paramBusqueda
	 * @param paramBusqueda The paramBusqueda to set
	 */
	public void setParamBusqueda(String paramBusqueda) {
		this.paramBusqueda = paramBusqueda;
	}

	/**
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return oficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}

	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return regPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}

	/**
	 * Gets the tipoBusq
	 * @return Returns a int
	 */
	public int getTipoBusq() {
		return tipoBusq;
	}
	/**
	 * Sets the tipoBusq
	 * @param tipoBusq The tipoBusq to set
	 */
	public void setTipoBusq(int tipoBusq) {
		this.tipoBusq = tipoBusq;
	}

	/**
	 * Gets the tipoParticipacion
	 * @return Returns a String
	 */
	public String getTipoParticipacion() {
		return tipoParticipacion;
	}
	/**
	 * Sets the tipoParticipacion
	 * @param tipoParticipacion The tipoParticipacion to set
	 */
	public void setTipoParticipacion(String tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}

	/**
	 * Gets the numSedes
	 * @return Returns a String
	 */
	public String getNumSedes() {
		return numSedes;
	}
	/**
	 * Sets the numSedes
	 * @param numSedes The numSedes to set
	 */
	public void setNumSedes(String numSedes) {
		this.numSedes = numSedes;
	}

	/**
	 * Gets the codAreaReg
	 * @return Returns a String
	 */
	public String getCodAreaReg() {
		return codAreaReg;
	}
	/**
	 * Sets the codAreaReg
	 * @param codAreaReg The codAreaReg to set
	 */
	public void setCodAreaReg(String codAreaReg) {
		this.codAreaReg = codAreaReg;
	}

	/**
	 * Gets the tipoPersPart
	 * @return Returns a String
	 */
	public String getTipoPersPart() {
		return TipoPersPart;
	}
	/**
	 * Sets the tipoPersPart
	 * @param tipoPersPart The tipoPersPart to set
	 */
	public void setTipoPersPart(String tipoPersPart) {
		TipoPersPart = tipoPersPart;
	}

}

