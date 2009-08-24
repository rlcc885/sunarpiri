package gob.pe.sunarp.extranet.transaction.bean;

public class LogAuditoriaBusquedaMasivaRelacional extends TransactionBean {

	private static final long serialVersionUID = -4013374714780418138L;
	
	private String fechaHoraBusqueda;
	private String criterioBusqueda;
	private String descripcionRegistroPublico;
	
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the criterioBusqueda
	 */
	public String getCriterioBusqueda() {
		return criterioBusqueda;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param criterioBusqueda the criterioBusqueda to set
	 */
	public void setCriterioBusqueda(String criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the descripcionRegistroPublico
	 */
	public String getDescripcionRegistroPublico() {
		return descripcionRegistroPublico;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param descripcionRegistroPublico the descripcionRegistroPublico to set
	 */
	public void setDescripcionRegistroPublico(String descripcionRegistroPublico) {
		this.descripcionRegistroPublico = descripcionRegistroPublico;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaHoraBusqueda
	 */
	public String getFechaHoraBusqueda() {
		return fechaHoraBusqueda;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaHoraBusqueda the fechaHoraBusqueda to set
	 */
	public void setFechaHoraBusqueda(String fechaHoraBusqueda) {
		this.fechaHoraBusqueda = fechaHoraBusqueda;
	}
	
	
}

