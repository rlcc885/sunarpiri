package gob.pe.sunarp.extranet.transaction.bean;

import com.ibm.etools.webservice.wscommonext.Timestamp;

public class LogAuditoriaAtencionSolicitudBean extends TransactionBean {

	private Timestamp fechaActualizacion;
	private String estado; 
	private String comentario;
	private String solicitudId;
	private String cuentaId;
	
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the cuentaId
	 */
	public String getCuentaId() {
		return cuentaId;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @param cuentaId the cuentaId to set
	 */
	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaActualizacion
	 */
	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the solicitudId
	 */
	public String getSolicitudId() {
		return solicitudId;
	}
	/**
	 * @autor dbravo
	 * @fecha 14/06/2007
	 * @CC:SUNARP-REGMOBCON
	 * @param solicitudId the solicitudId to set
	 */
	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}
}

