/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.beans;

import java.io.Serializable;

/**
 * @author jbugarin
 *
 */
public class Participantes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoDocu;
	private String descDocu;
	private String apePaterno;
	private String apeMaterno;
	private String nombre;
	private String razonSocial;
	private String numDocu;
	private String tipoParticipante;
	
	public String getTipoParticipante() {
		return tipoParticipante;
	}
	public void setTipoParticipante(String tipoParticipante) {
		this.tipoParticipante = tipoParticipante;
	}
	/**
	 * 
	 */
	public Participantes() {
		// TODO Auto-generated constructor stub
	}
	public String getApeMaterno() {
		return apeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}
	public String getApePaterno() {
		return apePaterno;
	}
	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}
	public String getDescDocu() {
		return descDocu;
	}
	public void setDescDocu(String descDocu) {
		this.descDocu = descDocu;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumDocu() {
		return numDocu;
	}
	public void setNumDocu(String numDocu) {
		this.numDocu = numDocu;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTipoDocu() {
		return tipoDocu;
	}
	public void setTipoDocu(String tipoDocu) {
		this.tipoDocu = tipoDocu;
	}
	
}
