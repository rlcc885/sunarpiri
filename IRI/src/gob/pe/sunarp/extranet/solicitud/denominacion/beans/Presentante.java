/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.beans;

import java.io.Serializable;

/**
 * @author jbugarin
 *
 */
public class Presentante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apePaterno;
	private String apeMaterno;
	private String tipoDocu;
	private String descDocu;
	private String numDocu;
	private String participacion;
	private String email;
	private String direccion;
	private String descParticipacion;
	
	
	
	public String getDescParticipacion() {
		return descParticipacion;
	}

	public void setDescParticipacion(String descParticipacion) {
		this.descParticipacion = descParticipacion;
	}

	/**
	 * 
	 */
	public Presentante() {
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getParticipacion() {
		return participacion;
	}

	public void setParticipacion(String participacion) {
		this.participacion = participacion;
	}

	public String getTipoDocu() {
		return tipoDocu;
	}

	public void setTipoDocu(String tipoDocu) {
		this.tipoDocu = tipoDocu;
	}

	public String getDescDocu() {
		return descDocu;
	}

	public void setDescDocu(String descDocu) {
		this.descDocu = descDocu;
	}

}
