/**
 * 
 */
package gob.pe.sunarp.extranet.diasnolaborables.bean;

import java.io.Serializable;

/**
 * @author jbugarin
 *
 */
public class DiasNoLaborables implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regPubId;
	private String oficina;
	private String dia;
	private String descripcion;
	private String docuSustento;
	private String estado;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getDocuSustento() {
		return docuSustento;
	}
	public void setDocuSustento(String docuSusttento) {
		this.docuSustento = docuSusttento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getRegPubId() {
		return regPubId;
	}
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}
	
}
