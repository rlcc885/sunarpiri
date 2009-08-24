package gob.pe.sunarp.extranet.prepago.bean;
public class MovimientoVentanillaBean extends AbonoVentanillaBean {

	private String fecha_inicio;
	private String fecha_fin;
	private String agencia;
	private String tipo;
	/**
	 * Gets the fecha_inicio
	 * @return Returns a String
	 */
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	/**
	 * Sets the fecha_inicio
	 * @param fecha_inicio The fecha_inicio to set
	 */
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	/**
	 * Gets the fecha_fin
	 * @return Returns a String
	 */
	public String getFecha_fin() {
		return fecha_fin;
	}
	/**
	 * Sets the fecha_fin
	 * @param fecha_fin The fecha_fin to set
	 */
	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
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

}

