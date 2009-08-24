package gob.pe.sunarp.extranet.common.cm;

import java.util.Date;

import gob.pe.sunarp.extranet.common.DataImagen;
import gob.pe.sunarp.extranet.common.SunarpBean;

public class ObjetoCM extends SunarpBean{
	private long objetoID;
	private long partidaRN;
	private String numPartida;
	private String keyfileOID;
	private int keyfilePagina;
	private DataImagen[] imagenes;
	private int numImagenes;

	//jacaceres - CM8 - 27/02/2007
	private Date fechaCreacion;
	//jacaceres - CM8 - fin
	
	public String toString() {
		return new StringBuffer("objetoID=").append(objetoID)
				.append(" partidaRN=").append(partidaRN).append(" numPartida=").append(numPartida)
				.append(" keyfileOID=").append(keyfileOID).append(" keyfilePagina=").append(keyfilePagina)
				.append(" imagenes=").append(imagenes).toString();
	}
	/**
	 * Gets the imageID
	 * @return Returns a long
	 */
	public long getObjetoID() {
		return objetoID;
	}
	/**
	 * Sets the imageID
	 * @param imageID The imageID to set
	 */
	public void setObjetoID(long objetoID) {
		this.objetoID = objetoID;
	}

	/**
	 * Gets the numPartida
	 * @return Returns a String
	 */
	public String getNumPartida() {
		return numPartida;
	}
	/**
	 * Sets the numPartida
	 * @param numPartida The numPartida to set
	 */
	public void setNumPartida(String numPartida) {
		this.numPartida = numPartida;
	}

	/**
	 * Gets the keyfileOID
	 * @return Returns a String
	 */
	public String getKeyfileOID() {
		return keyfileOID;
	}
	/**
	 * Sets the keyfileOID
	 * @param keyfileOID The keyfileOID to set
	 */
	public void setKeyfileOID(String keyfileOID) {
		this.keyfileOID = keyfileOID;
	}

	/**
	 * Gets the keyfilePagina
	 * @return Returns a int
	 */
	public int getKeyfilePagina() {
		return keyfilePagina;
	}
	/**
	 * Sets the keyfilePagina
	 * @param keyfilePagina The keyfilePagina to set
	 */
	public void setKeyfilePagina(int keyfilePagina) {
		this.keyfilePagina = keyfilePagina;
	}

	/**
	 * Gets the imagenes
	 * @return Returns a DataImagen[]
	 */
	public DataImagen[] getImagenes() {
		return imagenes;
	}
	/**
	 * Sets the imagenes
	 * @param imagenes The imagenes to set
	 */
	public void setImagenes(DataImagen[] imagenes) {
		this.imagenes = imagenes;
		if (imagenes != null)
			this.numImagenes = imagenes.length;
	}

	/**
	 * Gets the partidaRN
	 * @return Returns a long
	 */
	public long getPartidaRN() {
		return partidaRN;
	}
	/**
	 * Sets the partidaRN
	 * @param partidaRN The partidaRN to set
	 */
	public void setPartidaRN(long partidaRN) {
		this.partidaRN = partidaRN;
	}

	/**
	 * Gets the numImagenes
	 * @return Returns a int
	 */
	public int getNumImagenes() {
		return numImagenes;
	}
	/**
	 * Sets the numImagenes
	 * @param numImagenes The numImagenes to set
	 */
	public void setNumImagenes(int numImagenes) {
		if (imagenes != null) throw new IllegalArgumentException("Ya tenemos las imagenes, no se puede actualizar el numero");
		this.numImagenes = numImagenes;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}

