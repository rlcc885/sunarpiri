package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.util.Propiedades;

public class PaginacionBean extends SunarpBean{
	
	
	private int num_pagina;
	private int paginacion;
	private int tamano;	
	private boolean hayNext;
	private Propiedades propiedades ;
	private String encontro;

	/**
	 * Gets the num_pagina
	 * @return Returns a int
	 */
	public int getNum_pagina() {
		return num_pagina;
	}
	/**
	 * Sets the num_pagina
	 * @param num_pagina The num_pagina to set
	 */
	public void setNum_pagina(int num_pagina) {
		this.num_pagina = num_pagina;
	}

	/**
	 * Gets the paginacion
	 * @return Returns a int
	 */
	public int getPaginacion() {
		return paginacion;
	}
	/**
	 * Sets the paginacion
	 * @param paginacion The paginacion to set
	 */
	public void setPaginacion(int paginacion) {
		this.paginacion = paginacion;
	}

	/**
	 * Gets the tamano
	 * @return Returns a int
	 */
	public int getTamano() {
		return tamano;
	}
	/**
	 * Sets the tamano
	 * @param tamano The tamano to set
	 */
	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

	/**
	 * Gets the hayNext
	 * @return Returns a boolean
	 */
	public boolean getHayNext() {
		return hayNext;
	}
	/**
	 * Sets the hayNext
	 * @param hayNext The hayNext to set
	 */
	public void setHayNext(boolean hayNext) {
		this.hayNext = hayNext;
	}

	/**
	 * Gets the propiedades
	 * @return Returns a Propiedades
	 */
	public Propiedades getPropiedades() {
		return propiedades;
	}


	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}


	/**
	 * Sets the encontro
	 * @param encontro The encontro to set
	 */
	public void setEncontro(String encontro) {
		this.encontro = encontro;
	}

	/**
	 * Gets the encontro
	 * @return Returns a String
	 */
	public String getEncontro() {
		return encontro;
	}
}

