package gob.pe.sunarp.extranet.common;
public class DataImagen extends SunarpBean{
	private byte[] contenido;

	public DataImagen() {
	}
	public DataImagen(byte[] contenido) {
		this.contenido = contenido;
	}
	/**
	 * Gets the contenido
	 * @return Returns a byte[]
	 */
	public byte[] getContenido() {
		return contenido;
	}
	/**
	 * Sets the contenido
	 * @param contenido The contenido to set
	 */
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

}

