package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.*;


public class FormOutputListado extends SunarpBean{
	
private int codTabla;
private Vector lista=null;
private int nKeys;
private String[] cabezas;
private String nombreTabla;
private String action="";
private int pagSiguiente=-1;
private int pagAnterior=-1;
private long cantidadRegistros=0;
	/**
	 * Gets the codTabla
	 * @return Returns a int
	 */
	public int getCodTabla() {
		return codTabla;
	}
	/**
	 * Sets the codTabla
	 * @param codTabla The codTabla to set
	 */
	public void setCodTabla(int codTabla) {
		this.codTabla = codTabla;
	}

	/**
	 * Gets the lista
	 * @return Returns a Vector
	 */
	public Vector getLista() {
		return lista;
	}
	/**
	 * Sets the lista
	 * @param lista The lista to set
	 */
	public void setLista(Vector lista) {
		this.lista = lista;
	}
	/**
	 * Gets the cabezas
	 * @return Returns a String[]
	 */
	public String[] getCabezas() {
		return cabezas;
	}
	/**
	 * Sets the cabezas
	 * @param cabezas The cabezas to set
	 */
	public void setCabezas(String[] cabezas) {
		this.cabezas = cabezas;
	}

	/**
	 * Gets the nombreTabla
	 * @return Returns a String
	 */
	public String getNombreTabla() {
		return nombreTabla;
	}
	/**
	 * Sets the nombreTabla
	 * @param nombreTabla The nombreTabla to set
	 */
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	/**
	 * Gets the action
	 * @return Returns a String
	 */
	public String getAction() {
		return action;
	}
	/**
	 * Sets the action
	 * @param action The action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the pagSiguiente
	 * @return Returns a int
	 */
	public int getPagSiguiente() {
		return pagSiguiente;
	}
	/**
	 * Sets the pagSiguiente
	 * @param pagSiguiente The pagSiguiente to set
	 */
	public void setPagSiguiente(int pagSiguiente) {
		this.pagSiguiente = pagSiguiente;
	}

	/**
	 * Gets the pagAnterior
	 * @return Returns a int
	 */
	public int getPagAnterior() {
		return pagAnterior;
	}
	/**
	 * Sets the pagAnterior
	 * @param pagAnterior The pagAnterior to set
	 */
	public void setPagAnterior(int pagAnterior) {
		this.pagAnterior = pagAnterior;
	}

	/**
	 * Gets the cantidadRegistros
	 * @return Returns a long
	 */
	public long getCantidadRegistros() {
		return cantidadRegistros;
	}
	/**
	 * Sets the cantidadRegistros
	 * @param cantidadRegistros The cantidadRegistros to set
	 */
	public void setCantidadRegistros(long cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	/**
	 * Gets the keys
	 * @return Returns a int
	 */
	public int getKeys() {
		return nKeys;
	}
	/**
	 * Sets the keys
	 * @param keys The keys to set
	 */
	public void setKeys(int keys) {
		nKeys = keys;
	}

}

