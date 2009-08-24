package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class ComprobanteBean extends SunarpBean{
	
	
	private String comprobanteId;
	private String fecha_hora;
	private String userId; 
	private String nombreEntidad;
	private String contratoId;
	private String monto;
	private String tipoPago; // En Linea: Wiese - Visa; Ventanilla: Efectivo - Cheque
	private String oficina;
	private String cajero;
	private String banco;
	private String tipoCheque;
	private String numcheque;
	private long mov_id;
	private String abono_id;
	private String solicitudId;
	private String tipoPub = "S"; //Publicidad Simple, Publicidad Certificada
	private String solDesc;
	private String glosa;
	/**DESCAJ IFIGUEROA 05/01/2006 INICIO**/
	private String documento;
	private String numeroDoc;
	
	/**
	 * Gets the comprobanteId
	 * @return Returns a String
	 */
	public String getComprobanteId() {
		return comprobanteId;
	}
	/**
	 * Sets the comprobanteId
	 * @param comprobanteId The comprobanteId to set
	 */
	public void setComprobanteId(String comprobanteId) {
		this.comprobanteId = comprobanteId;
	}

	/**
	 * Gets the fecha_hora
	 * @return Returns a String
	 */
	public String getFecha_hora() {
		return fecha_hora;
	}
	/**
	 * Sets the fecha_hora
	 * @param fecha_hora The fecha_hora to set
	 */
	public void setFecha_hora(String fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	/**
	 * Gets the userId
	 * @return Returns a String
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the nombreEntidad
	 * @return Returns a String
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	/**
	 * Sets the nombreEntidad
	 * @param nombreEntidad The nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
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
	 * Gets the banco
	 * @return Returns a String
	 */
	public String getBanco() {
		return banco;
	}
	/**
	 * Sets the banco
	 * @param banco The banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
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
	 * Gets the numcheque
	 * @return Returns a String
	 */
	public String getNumcheque() {
		return numcheque;
	}
	/**
	 * Sets the numcheque
	 * @param numcheque The numcheque to set
	 */
	public void setNumcheque(String numcheque) {
		this.numcheque = numcheque;
	}

	/**
	 * Gets the mov_id
	 * @return Returns a long
	 */
	public long getMov_id() {
		return mov_id;
	}
	/**
	 * Sets the mov_id
	 * @param mov_id The mov_id to set
	 */
	public void setMov_id(long mov_id) {
		this.mov_id = mov_id;
	}

	/**
	 * Gets the oficina
	 * @return Returns a String
	 */
	public String getOficina() {
		return oficina;
	}
	/**
	 * Sets the oficina
	 * @param oficina The oficina to set
	 */
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	/**
	 * Gets the cajero
	 * @return Returns a String
	 */
	public String getCajero() {
		return cajero;
	}
	/**
	 * Sets the cajero
	 * @param cajero The cajero to set
	 */
	public void setCajero(String cajero) {
		this.cajero = cajero;
	}

	/**
	 * Gets the abono_id
	 * @return Returns a String
	 */
	public String getAbono_id() {
		return abono_id;
	}
	/**
	 * Sets the abono_id
	 * @param abono_id The abono_id to set
	 */
	public void setAbono_id(String abono_id) {
		this.abono_id = abono_id;
	}

	/**
	 * Gets the solicitudId
	 * @return Returns a String
	 */
	public String getSolicitudId() {
		return solicitudId;
	}
	/**
	 * Sets the solicitudId
	 * @param solicitudId The solicitudId to set
	 */
	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}

	/**
	 * Gets the tipoPub
	 * @return Returns a String
	 */
	public String getTipoPub() {
		return tipoPub;
	}
	/**
	 * Sets the tipoPub
	 * @param tipoPub The tipoPub to set
	 */
	public void setTipoPub(String tipoPub) {
		this.tipoPub = tipoPub;
	}

	/**
	 * Gets the solDesc
	 * @return Returns a String
	 */
	public String getSolDesc() {
		return solDesc;
	}
	/**
	 * Sets the solDesc
	 * @param solDesc The solDesc to set
	 */
	public void setSolDesc(String solDesc) {
		this.solDesc = solDesc;
	}
	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	/**
	 * @return
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @return
	 */
	public String getNumeroDoc() {
		return numeroDoc;
	}

	/**
	 * @param string
	 */
	public void setDocumento(String string) {
		documento = string;
	}

	/**
	 * @param string
	 */
	public void setNumeroDoc(String string) {
		numeroDoc = string;
	}

}

