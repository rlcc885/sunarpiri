package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
h
12 agosto 2002
*/
public class ComboBean extends SunarpBean{
	private String codigo="";
	private String descripcion="";
	//Tarifario
	private String atributo1="";//tarifa
	private String atributo2="";//area
	
	
	/**
	 * Gets the codigo
	 * @return Returns a String
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * Sets the codigo
	 * @param codigo The codigo to set
	 */
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	/**
	 * Gets the descripcion
	 * @return Returns a String
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Sets the descripcion
	 * @param descripcion The descripcion to set
	 */
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	/**
	 * Gets the atributo1
	 * @return Returns a String
	 */
	public String getAtributo1() {
		return atributo1;
	}
	/**
	 * Sets the atributo1
	 * @param atributo1 The atributo1 to set
	 */
	public void setAtributo1(String atributo1) {
		this.atributo1 = atributo1;
	}

	/**
	 * Gets the atributo2
	 * @return Returns a String
	 */
	public String getAtributo2() {
		return atributo2;
	}
	/**
	 * Sets the atributo2
	 * @param atributo2 The atributo2 to set
	 */
	public void setAtributo2(String atributo2) {
		this.atributo2 = atributo2;
	}

}