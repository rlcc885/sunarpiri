package gob.pe.sunarp.extranet.publicidad.bean;

public class InputBusqDirectaBean extends InputBusquedaBean{
	
private static final long serialVersionUID = 1044970277189408401L;

private String numeroPartida;
private String numeroFicha;
private String numeroPlaca;
private String areaRegistral;
private String libro;
private String tomo;
private String folio;
private String codGrupoLibroArea;
private String descGrupoLibroArea;
/*** inicio: jrosas 25-07-07 ***/
private String estado;
private String numeroPartidaMigrada;
private String area_reg_id;
private String oficRegDescripcion;
/*** fin: jrosas 25-07-07 ***/
private	String regPubId;
private	String oficRegId;
			
/**inicio: dbravo 10-08-07 **/
private String verifica=null;
/**fin: dbravo 10-08-07 **/

// flag para pagineo
// cuando este flag esta activado significa que el usuario ya
// realizó la busqueda y solamente esta navegando entre las paginas
private boolean flagPagineo=false;
private int salto=1;
private String cantidad;



//*****************SETTERS Y GETTERS******************

	public String getNumeroPartida() {
		return numeroPartida;
	}
	/**
	 * Sets the numeroPartida
	 * @param numeroPartida The numeroPartida to set
	 */
	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}

	/**
	 * Gets the numeroFicha
	 * @return Returns a String
	 */
	public String getNumeroFicha() {
		return numeroFicha;
	}
	/**
	 * Sets the numeroFicha
	 * @param numeroFicha The numeroFicha to set
	 */
	public void setNumeroFicha(String numeroFicha) {
		this.numeroFicha = numeroFicha;
	}

	/**
	 * Gets the areaRegistral
	 * @return Returns a String
	 */
	public String getAreaRegistral() {
		return areaRegistral;
	}
	/**
	 * Sets the areaRegistral
	 * @param areaRegistral The areaRegistral to set
	 */
	public void setAreaRegistral(String areaRegistral) {
		this.areaRegistral = areaRegistral;
	}

	/**
	 * Gets the libro
	 * @return Returns a String
	 */
	public String getLibro() {
		return libro;
	}
	/**
	 * Sets the libro
	 * @param libro The libro to set
	 */
	public void setLibro(String libro) {
		this.libro = libro;
	}

	/**
	 * Gets the tomo
	 * @return Returns a String
	 */
	public String getTomo() {
		return tomo;
	}
	/**
	 * Sets the tomo
	 * @param tomo The tomo to set
	 */
	public void setTomo(String tomo) {
		this.tomo = tomo;
	}

	/**
	 * Gets the folio
	 * @return Returns a String
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * Sets the folio
	 * @param folio The folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return regPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}

	/**
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return oficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}

	/**
	 * Gets the flagPagineo
	 * @return Returns a boolean
	 */
	public boolean getFlagPagineo() {
		return flagPagineo;
	}
	/**
	 * Sets the flagPagineo
	 * @param flagPagineo The flagPagineo to set
	 */
	public void setFlagPagineo(boolean flagPagineo) {
		this.flagPagineo = flagPagineo;
	}

	/**
	 * Gets the salto
	 * @return Returns a int
	 */
	public int getSalto() {
		return salto;
	}
	/**
	 * Sets the salto
	 * @param salto The salto to set
	 */
	public void setSalto(int salto) {
		this.salto = salto;
	}
	/**
	 * Gets the cantidad
	 * @return Returns a String
	 */
	public String getCantidad() {
		return cantidad;
	}
	/**
	 * Sets the cantidad
	 * @param cantidad The cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Gets the numeroPlaca
	 * @return Returns a String
	 */
	public String getNumeroPlaca() {
		return numeroPlaca;
	}
	/**
	 * Sets the numeroPlaca
	 * @param numeroPlaca The numeroPlaca to set
	 */
	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}

	/**
	 * Gets the codGrupoLibroArea
	 * @return Returns a String
	 */
	public String getCodGrupoLibroArea() {
		return codGrupoLibroArea;
	}
	/**
	 * Sets the codGrupoLibroArea
	 * @param codGrupoLibroArea The codGrupoLibroArea to set
	 */
	public void setCodGrupoLibroArea(String codGrupoLibroArea) {
		this.codGrupoLibroArea = codGrupoLibroArea;
	}

	/**
	 * Gets the descGrupoLibroArea
	 * @return Returns a String
	 */
	public String getDescGrupoLibroArea() {
		return descGrupoLibroArea;
	}
	/**
	 * Sets the descGrupoLibroArea
	 * @param descGrupoLibroArea The descGrupoLibroArea to set
	 */
	public void setDescGrupoLibroArea(String descGrupoLibroArea) {
		this.descGrupoLibroArea = descGrupoLibroArea;
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the area_reg_id
	 */
	public String getArea_reg_id() {
		return area_reg_id;
	}
	/**
	 * @param area_reg_id the area_reg_id to set
	 */
	public void setArea_reg_id(String area_reg_id) {
		this.area_reg_id = area_reg_id;
	}
	/**
	 * @return the numeroPartidaMigrada
	 */
	public String getNumeroPartidaMigrada() {
		return numeroPartidaMigrada;
	}
	/**
	 * @param numeroPartidaMigrada the numeroPartidaMigrada to set
	 */
	public void setNumeroPartidaMigrada(String numeroPartidaMigrada) {
		this.numeroPartidaMigrada = numeroPartidaMigrada;
	}
	/**
	 * @return the oficRegDescripcion
	 */
	public String getOficRegDescripcion() {
		return oficRegDescripcion;
	}
	/**
	 * @param oficRegDescripcion the oficRegDescripcion to set
	 */
	public void setOficRegDescripcion(String oficRegDescripcion) {
		this.oficRegDescripcion = oficRegDescripcion;
	}
	/**
	 * @autor dbravo
	 * @fecha Sep 14, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the verifica
	 */
	public String getVerifica() {
		return verifica;
	}
	/**
	 * @autor dbravo
	 * @fecha Sep 14, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param verifica the verifica to set
	 */
	public void setVerifica(String verifica) {
		this.verifica = verifica;
	}

}

