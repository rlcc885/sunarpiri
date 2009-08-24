/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.beans;

import java.io.Serializable;

/**
 * @author jbugarin
 *
 */
public class Juridica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo;
	private String descTipo;
	private String razonSocial;
	private String provincia;
	private String partida;
	private String ficha;
	private String departamento;
	private String descDepto;
	private String descProv;
	/**
	 * @return el descDepto
	 */
	public String getDescDepto() {
		return descDepto;
	}
	/**
	 * @param descDepto el descDepto a establecer
	 */
	public void setDescDepto(String descDepto) {
		this.descDepto = descDepto;
	}
	/**
	 * @return el descProv
	 */
	public String getDescProv() {
		return descProv;
	}
	/**
	 * @param descProv el descProv a establecer
	 */
	public void setDescProv(String descProv) {
		this.descProv = descProv;
	}
	/**
	 * 
	 */
	public Juridica() {
		// TODO Auto-generated constructor stub
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getFicha() {
		return ficha;
	}
	public void setFicha(String ficha) {
		this.ficha = ficha;
	}
	public String getPartida() {
		return partida;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescTipo() {
		return descTipo;
	}
	public void setDescTipo(String descTipo) {
		this.descTipo = descTipo;
	}

}
