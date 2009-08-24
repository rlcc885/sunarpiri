package gob.pe.sunarp.extranet.publicidad.bean;

public class InputBusqIndirectaBean extends InputBusquedaBean{

private static final long serialVersionUID = -1315685109158254716L;
	
private String comboArea="";
private String codGrupoLibroArea="";
private String descGrupoLibroArea="";

private String area1TipoParticipacion="";
private String area1ApePat="";
private String area1ApeMat="";
private String area1Nombre="";
private String area1Razon="";
private String area1Siglas="";

private String area2TipoParticipacion="";
private String area2ApePat="";
private String area2ApeMat="";
private String area2Nombre="";
private String area2Razon1="";
private String area2Siglas="";
private String area2SiglasB="";
private String area2TipoDocumento="";
private String area2NumeroDocumento="";
private String area2Razon2="";

private String area3TipoParticipacion="";
private String area3ParticipanteApePat="";
private String area3ParticipanteApeMat="";
private String area3ParticipanteNombre="";
private String area3ParticipanteRazon="";
private String area3PredioDepartamento="";
private String area3PredioProvincia="";
private String area3PredioDistrito="";
private String area3PredioTipoZona="";
private String area3PredioNombreZona="";
private String area3PredioTipoVia="";
private String area3PredioNombreVia="";
private String area3PredioTipoNumerac="";
private String area3PredioNumero="";
private String area3PredioTipoInterior="";
private String area3PredioInteriorNro="";
private boolean area3PredioSinNum=false;
private String area3MineriaDerechoMinero="";
private String area3MineriaSociedad="";
private String area3EmbarcacionNumeroMatricula="";
private String area3EmbarcacionNombre="";
private String area3BuqueNumeroMatricula="";
private String area3BuqueNombre="";
private String area3AeronaveNumeroMatricula="";
private String area3AeronaveApePat="";
private String area3AeronaveApeMat="";
private String area3AeronaveNombre="";
private String area3AeronaveRazon="";
private String area3Siglas="";
private String area3SiglasB="";

private String area4TipoParticipacion="";
private String area4ApePat="";
private String area4ApeMat="";
private String area4Nombre="";
private String area4Razon="";
private String area4Tipo = "";
private String area4NumMotor = "";
private String area4NumChasis = "";



//--Costo por sedes elegidas
private String[] sedesElegidas=null;
private String[] sedesElegidasOriginales=null;
private String sedesSQLString="";
//private String IdServicio="";
//private double costo=0;


//-extra
private String hid2="";

//flag para preguntar por estado
private boolean flagIncluirInactivos=false;


// flag para pagineo
// cuando este flag esta activado significa que el usuario ya
// realizo la busqueda y solamente esta navegando entre las paginas
private boolean flagPagineo=false;
private int salto=1;
private String cantidad;

/** inicio: jrosas 12-07-07 ****/
private String tipoDocumento="";
private String numeroDocumento="";
private String numeroPlaca="";
private String otrosDatos="";
/** fin: jrosas 12-07-07 ****/
/*
 * Inicio:jascencio:18/07/07
 */
private String nombreBien;
private String numeroMatricula;
private String numeroSerie;
private int saltoInferior=1;
private String cantidadInferior;
private boolean flagPagineoInferior=false;



/*
 * Fin:jascencio
 */
/**inicio: jrosas 10-08-07 **/
private String verifica=null;/**fin: jrosas 10-08-07 **/
//************** SETTERS Y GETTERS********************
/**
 * @return the verifica
 */
public String getVerifica() {
	return verifica;
}
/**
 * @param verifica the verifica to set
 */
public void setVerifica(String verifica) {
	this.verifica = verifica;
}
	/**
 * @autor jascencio
 * @fecha Jul 27, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @return the cantidadInferior
 */
public String getCantidadInferior() {
	return cantidadInferior;
}
/**
 * @autor jascencio
 * @fecha Jul 27, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @param cantidadInferior the cantidadInferior to set
 */
public void setCantidadInferior(String cantidadInferior) {
	this.cantidadInferior = cantidadInferior;
}
/**
 * @autor jascencio
 * @fecha Jul 27, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @return the saltoInferior
 */
public int getSaltoInferior() {
	return saltoInferior;
}
/**
 * @autor jascencio
 * @fecha Jul 27, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @param saltoInferior the saltoInferior to set
 */
public void setSaltoInferior(int saltoInferior) {
	this.saltoInferior = saltoInferior;
}
	/**

	/**
 * @autor jascencio
 * @fecha Jul 18, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @return the nombreBien
 */
public String getNombreBien() {
	return nombreBien;
}
/**
 * @autor jascencio
 * @fecha Jul 18, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @param nombreBien the nombreBien to set
 */
public void setNombreBien(String nombreBien) {
	this.nombreBien = nombreBien;
}
/**
 * @autor jascencio
 * @fecha Jul 18, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @return the numeroMatricula
 */
public String getNumeroMatricula() {
	return numeroMatricula;
}
/**
 * @autor jascencio
 * @fecha Jul 18, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @param numeroMatricula the numeroMatricula to set
 */
public void setNumeroMatricula(String numeroMatricula) {
	this.numeroMatricula = numeroMatricula;
}
/**
 * @autor jascencio
 * @fecha Jul 18, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @return the numeroSerie
 */
public String getNumeroSerie() {
	return numeroSerie;
}
/**
 * @autor jascencio
 * @fecha Jul 18, 2007
 * @CC: SUNARP-REGMOBCON-2006
 * @param numeroSerie the numeroSerie to set
 */
public void setNumeroSerie(String numeroSerie) {
	this.numeroSerie = numeroSerie;
}
	public String getComboArea() {
		return comboArea;
	}
	/**
	 * Sets the comboArea
	 * @param comboArea The comboArea to set
	 */
	public void setComboArea(String comboArea) {
		this.comboArea = comboArea;
	}

	/**
	 * Gets the area1TipoParticipacion
	 * @return Returns a String
	 */
	public String getArea1TipoParticipacion() {
		return area1TipoParticipacion;
	}
	/**
	 * Sets the area1TipoParticipacion
	 * @param area1TipoParticipacion The area1TipoParticipacion to set
	 */
	public void setArea1TipoParticipacion(String area1TipoParticipacion) {
		this.area1TipoParticipacion = area1TipoParticipacion;
	}

	/**
	 * Gets the area1ApePat
	 * @return Returns a String
	 */
	public String getArea1ApePat() {
		return area1ApePat;
	}
	/**
	 * Sets the area1ApePat
	 * @param area1ApePat The area1ApePat to set
	 */
	public void setArea1ApePat(String area1ApePat) {
		this.area1ApePat = area1ApePat;
	}

	/**
	 * Gets the area1ApeMat
	 * @return Returns a String
	 */
	public String getArea1ApeMat() {
		return area1ApeMat;
	}
	/**
	 * Sets the area1ApeMat
	 * @param area1ApeMat The area1ApeMat to set
	 */
	public void setArea1ApeMat(String area1ApeMat) {
		this.area1ApeMat = area1ApeMat;
	}

	/**
	 * Gets the area1Nombre
	 * @return Returns a String
	 */
	public String getArea1Nombre() {
		return area1Nombre;
	}
	/**
	 * Sets the area1Nombre
	 * @param area1Nombre The area1Nombre to set
	 */
	public void setArea1Nombre(String area1Nombre) {
		this.area1Nombre = area1Nombre;
	}

	/**
	 * Gets the area1Razon
	 * @return Returns a String
	 */
	public String getArea1Razon() {
		return area1Razon;
	}
	/**
	 * Sets the area1Razon
	 * @param area1Razon The area1Razon to set
	 */
	public void setArea1Razon(String area1Razon) {
		this.area1Razon = area1Razon;
	}

	/**
	 * Gets the area1Siglas
	 * @return Returns a String
	 */
	public String getArea1Siglas() {
		return area1Siglas;
	}
	/**
	 * Sets the area1Siglas
	 * @param area1Siglas The area1Siglas to set
	 */
	public void setArea1Siglas(String area1Siglas) {
		this.area1Siglas = area1Siglas;
	}

	/**
	 * Gets the area2TipoParticipacion
	 * @return Returns a String
	 */
	public String getArea2TipoParticipacion() {
		return area2TipoParticipacion;
	}
	/**
	 * Sets the area2TipoParticipacion
	 * @param area2TipoParticipacion The area2TipoParticipacion to set
	 */
	public void setArea2TipoParticipacion(String area2TipoParticipacion) {
		this.area2TipoParticipacion = area2TipoParticipacion;
	}

	/**
	 * Gets the area2ApePat
	 * @return Returns a String
	 */
	public String getArea2ApePat() {
		return area2ApePat;
	}
	/**
	 * Sets the area2ApePat
	 * @param area2ApePat The area2ApePat to set
	 */
	public void setArea2ApePat(String area2ApePat) {
		this.area2ApePat = area2ApePat;
	}

	/**
	 * Gets the area2ApeMat
	 * @return Returns a String
	 */
	public String getArea2ApeMat() {
		return area2ApeMat;
	}
	/**
	 * Sets the area2ApeMat
	 * @param area2ApeMat The area2ApeMat to set
	 */
	public void setArea2ApeMat(String area2ApeMat) {
		this.area2ApeMat = area2ApeMat;
	}

	/**
	 * Gets the area2Nombre
	 * @return Returns a String
	 */
	public String getArea2Nombre() {
		return area2Nombre;
	}
	/**
	 * Sets the area2Nombre
	 * @param area2Nombre The area2Nombre to set
	 */
	public void setArea2Nombre(String area2Nombre) {
		this.area2Nombre = area2Nombre;
	}

	/**
	 * Gets the area2Razon1
	 * @return Returns a String
	 */
	public String getArea2Razon1() {
		return area2Razon1;
	}
	/**
	 * Sets the area2Razon1
	 * @param area2Razon1 The area2Razon1 to set
	 */
	public void setArea2Razon1(String area2Razon1) {
		this.area2Razon1 = area2Razon1;
	}

	/**
	 * Gets the area2Siglas
	 * @return Returns a String
	 */
	public String getArea2Siglas() {
		return area2Siglas;
	}
	/**
	 * Sets the area2Siglas
	 * @param area2Siglas The area2Siglas to set
	 */
	public void setArea2Siglas(String area2Siglas) {
		this.area2Siglas = area2Siglas;
	}

	/**
	 * Gets the area2TipoDocumento
	 * @return Returns a String
	 */
	public String getArea2TipoDocumento() {
		return area2TipoDocumento;
	}
	/**
	 * Sets the area2TipoDocumento
	 * @param area2TipoDocumento The area2TipoDocumento to set
	 */
	public void setArea2TipoDocumento(String area2TipoDocumento) {
		this.area2TipoDocumento = area2TipoDocumento;
	}

	/**
	 * Gets the area2NumeroDocumento
	 * @return Returns a String
	 */
	public String getArea2NumeroDocumento() {
		return area2NumeroDocumento;
	}
	/**
	 * Sets the area2NumeroDocumento
	 * @param area2NumeroDocumento The area2NumeroDocumento to set
	 */
	public void setArea2NumeroDocumento(String area2NumeroDocumento) {
		this.area2NumeroDocumento = area2NumeroDocumento;
	}

	/**
	 * Gets the area2Razon2
	 * @return Returns a String
	 */
	public String getArea2Razon2() {
		return area2Razon2;
	}
	/**
	 * Sets the area2Razon2
	 * @param area2Razon2 The area2Razon2 to set
	 */
	public void setArea2Razon2(String area2Razon2) {
		this.area2Razon2 = area2Razon2;
	}

	/**
	 * Gets the area3TipoParticipacion
	 * @return Returns a String
	 */
	public String getArea3TipoParticipacion() {
		return area3TipoParticipacion;
	}
	/**
	 * Sets the area3TipoParticipacion
	 * @param area3TipoParticipacion The area3TipoParticipacion to set
	 */
	public void setArea3TipoParticipacion(String area3TipoParticipacion) {
		this.area3TipoParticipacion = area3TipoParticipacion;
	}

	/**
	 * Gets the area3ParticipanteApePat
	 * @return Returns a String
	 */
	public String getArea3ParticipanteApePat() {
		return area3ParticipanteApePat;
	}
	/**
	 * Sets the area3ParticipanteApePat
	 * @param area3ParticipanteApePat The area3ParticipanteApePat to set
	 */
	public void setArea3ParticipanteApePat(String area3ParticipanteApePat) {
		this.area3ParticipanteApePat = area3ParticipanteApePat;
	}

	/**
	 * Gets the area3ParticipanteApeMat
	 * @return Returns a String
	 */
	public String getArea3ParticipanteApeMat() {
		return area3ParticipanteApeMat;
	}
	/**
	 * Sets the area3ParticipanteApeMat
	 * @param area3ParticipanteApeMat The area3ParticipanteApeMat to set
	 */
	public void setArea3ParticipanteApeMat(String area3ParticipanteApeMat) {
		this.area3ParticipanteApeMat = area3ParticipanteApeMat;
	}

	/**
	 * Gets the area3ParticipanteNombre
	 * @return Returns a String
	 */
	public String getArea3ParticipanteNombre() {
		return area3ParticipanteNombre;
	}
	/**
	 * Sets the area3ParticipanteNombre
	 * @param area3ParticipanteNombre The area3ParticipanteNombre to set
	 */
	public void setArea3ParticipanteNombre(String area3ParticipanteNombre) {
		this.area3ParticipanteNombre = area3ParticipanteNombre;
	}

	/**
	 * Gets the area3ParticipanteRazon
	 * @return Returns a String
	 */
	public String getArea3ParticipanteRazon() {
		return area3ParticipanteRazon;
	}
	/**
	 * Sets the area3ParticipanteRazon
	 * @param area3ParticipanteRazon The area3ParticipanteRazon to set
	 */
	public void setArea3ParticipanteRazon(String area3ParticipanteRazon) {
		this.area3ParticipanteRazon = area3ParticipanteRazon;
	}

	/**
	 * Gets the area3PredioDepartamento
	 * @return Returns a String
	 */
	public String getArea3PredioDepartamento() {
		return area3PredioDepartamento;
	}
	/**
	 * Sets the area3PredioDepartamento
	 * @param area3PredioDepartamento The area3PredioDepartamento to set
	 */
	public void setArea3PredioDepartamento(String area3PredioDepartamento) {
		this.area3PredioDepartamento = area3PredioDepartamento;
	}

	/**
	 * Gets the area3PredioProvincia
	 * @return Returns a String
	 */
	public String getArea3PredioProvincia() {
		return area3PredioProvincia;
	}
	/**
	 * Sets the area3PredioProvincia
	 * @param area3PredioProvincia The area3PredioProvincia to set
	 */
	public void setArea3PredioProvincia(String area3PredioProvincia) {
		this.area3PredioProvincia = area3PredioProvincia;
	}

	/**
	 * Gets the area3PredioDistrito
	 * @return Returns a String
	 */
	public String getArea3PredioDistrito() {
		return area3PredioDistrito;
	}
	/**
	 * Sets the area3PredioDistrito
	 * @param area3PredioDistrito The area3PredioDistrito to set
	 */
	public void setArea3PredioDistrito(String area3PredioDistrito) {
		this.area3PredioDistrito = area3PredioDistrito;
	}

	/**
	 * Gets the area3PredioTipoZona
	 * @return Returns a String
	 */
	public String getArea3PredioTipoZona() {
		return area3PredioTipoZona;
	}
	/**
	 * Sets the area3PredioTipoZona
	 * @param area3PredioTipoZona The area3PredioTipoZona to set
	 */
	public void setArea3PredioTipoZona(String area3PredioTipoZona) {
		this.area3PredioTipoZona = area3PredioTipoZona;
	}

	/**
	 * Gets the area3PredioNombreZona
	 * @return Returns a String
	 */
	public String getArea3PredioNombreZona() {
		return area3PredioNombreZona;
	}
	/**
	 * Sets the area3PredioNombreZona
	 * @param area3PredioNombreZona The area3PredioNombreZona to set
	 */
	public void setArea3PredioNombreZona(String area3PredioNombreZona) {
		this.area3PredioNombreZona = area3PredioNombreZona;
	}

	/**
	 * Gets the area3PredioNombreVia
	 * @return Returns a String
	 */
	public String getArea3PredioNombreVia() {
		return area3PredioNombreVia;
	}
	/**
	 * Sets the area3PredioNombreVia
	 * @param area3PredioNombreVia The area3PredioNombreVia to set
	 */
	public void setArea3PredioNombreVia(String area3PredioNombreVia) {
		this.area3PredioNombreVia = area3PredioNombreVia;
	}

	/**
	 * Gets the area3TipoNumerac
	 * @return Returns a String
	 */
	public String getArea3PredioTipoNumerac() {
		return area3PredioTipoNumerac;
	}
	/**
	 * Sets the area3TipoNumerac
	 * @param area3TipoNumerac The area3TipoNumerac to set
	 */
	public void setArea3PredioTipoNumerac(String area3PredioTipoNumerac) {
		this.area3PredioTipoNumerac = area3PredioTipoNumerac;
	}

	/**
	 * Gets the area3PredioNumero
	 * @return Returns a String
	 */
	public String getArea3PredioNumero() {
		return area3PredioNumero;
	}
	/**
	 * Sets the area3PredioNumero
	 * @param area3PredioNumero The area3PredioNumero to set
	 */
	public void setArea3PredioNumero(String area3PredioNumero) {
		this.area3PredioNumero = area3PredioNumero;
	}

	/**
	 * Gets the area3PredioTipoInterior
	 * @return Returns a String
	 */
	public String getArea3PredioTipoInterior() {
		return area3PredioTipoInterior;
	}
	/**
	 * Sets the area3PredioTipoInterior
	 * @param area3PredioTipoInterior The area3PredioTipoInterior to set
	 */
	public void setArea3PredioTipoInterior(String area3PredioTipoInterior) {
		this.area3PredioTipoInterior = area3PredioTipoInterior;
	}

	/**
	 * Gets the area3PredioInteriorNro
	 * @return Returns a String
	 */
	public String getArea3PredioInteriorNro() {
		return area3PredioInteriorNro;
	}
	/**
	 * Sets the area3PredioInteriorNro
	 * @param area3PredioInteriorNro The area3PredioInteriorNro to set
	 */
	public void setArea3PredioInteriorNro(String area3PredioInteriorNro) {
		this.area3PredioInteriorNro = area3PredioInteriorNro;
	}

	/**
	 * Gets the area3PredioSinNum
	 * @return Returns a boolean
	 */
	public boolean getArea3PredioSinNum() {
		return area3PredioSinNum;
	}
	/**
	 * Sets the area3PredioSinNum
	 * @param area3PredioSinNum The area3PredioSinNum to set
	 */
	public void setArea3PredioSinNum(boolean area3PredioSinNum) {
		this.area3PredioSinNum = area3PredioSinNum;
	}

	/**
	 * Gets the area3MineriaDerechoMinero
	 * @return Returns a String
	 */
	public String getArea3MineriaDerechoMinero() {
		return area3MineriaDerechoMinero;
	}
	/**
	 * Sets the area3MineriaDerechoMinero
	 * @param area3MineriaDerechoMinero The area3MineriaDerechoMinero to set
	 */
	public void setArea3MineriaDerechoMinero(String area3MineriaDerechoMinero) {
		this.area3MineriaDerechoMinero = area3MineriaDerechoMinero;
	}

	/**
	 * Gets the area3MineriaSociedad
	 * @return Returns a String
	 */
	public String getArea3MineriaSociedad() {
		return area3MineriaSociedad;
	}
	/**
	 * Sets the area3MineriaSociedad
	 * @param area3MineriaSociedad The area3MineriaSociedad to set
	 */
	public void setArea3MineriaSociedad(String area3MineriaSociedad) {
		this.area3MineriaSociedad = area3MineriaSociedad;
	}

	/**
	 * Gets the area3EmbarcacionNumeroMatricula
	 * @return Returns a String
	 */
	public String getArea3EmbarcacionNumeroMatricula() {
		return area3EmbarcacionNumeroMatricula;
	}
	/**
	 * Sets the area3EmbarcacionNumeroMatricula
	 * @param area3EmbarcacionNumeroMatricula The area3EmbarcacionNumeroMatricula to set
	 */
	public void setArea3EmbarcacionNumeroMatricula(String area3EmbarcacionNumeroMatricula) {
		this.area3EmbarcacionNumeroMatricula = area3EmbarcacionNumeroMatricula;
	}

	/**
	 * Gets the area3EmbarcacionNombre
	 * @return Returns a String
	 */
	public String getArea3EmbarcacionNombre() {
		return area3EmbarcacionNombre;
	}
	/**
	 * Sets the area3EmbarcacionNombre
	 * @param area3EmbarcacionNombre The area3EmbarcacionNombre to set
	 */
	public void setArea3EmbarcacionNombre(String area3EmbarcacionNombre) {
		this.area3EmbarcacionNombre = area3EmbarcacionNombre;
	}

	/**
	 * Gets the area3BuqueNumeroMatricula
	 * @return Returns a String
	 */
	public String getArea3BuqueNumeroMatricula() {
		return area3BuqueNumeroMatricula;
	}
	/**
	 * Sets the area3BuqueNumeroMatricula
	 * @param area3BuqueNumeroMatricula The area3BuqueNumeroMatricula to set
	 */
	public void setArea3BuqueNumeroMatricula(String area3BuqueNumeroMatricula) {
		this.area3BuqueNumeroMatricula = area3BuqueNumeroMatricula;
	}

	/**
	 * Gets the area3BuqueNombre
	 * @return Returns a String
	 */
	public String getArea3BuqueNombre() {
		return area3BuqueNombre;
	}
	/**
	 * Sets the area3BuqueNombre
	 * @param area3BuqueNombre The area3BuqueNombre to set
	 */
	public void setArea3BuqueNombre(String area3BuqueNombre) {
		this.area3BuqueNombre = area3BuqueNombre;
	}

	/**
	 * Gets the area3AeronaveNumeroMatricula
	 * @return Returns a String
	 */
	public String getArea3AeronaveNumeroMatricula() {
		return area3AeronaveNumeroMatricula;
	}
	/**
	 * Sets the area3AeronaveNumeroMatricula
	 * @param area3AeronaveNumeroMatricula The area3AeronaveNumeroMatricula to set
	 */
	public void setArea3AeronaveNumeroMatricula(String area3AeronaveNumeroMatricula) {
		this.area3AeronaveNumeroMatricula = area3AeronaveNumeroMatricula;
	}

	/**
	 * Gets the area3AeronaveApePat
	 * @return Returns a String
	 */
	public String getArea3AeronaveApePat() {
		return area3AeronaveApePat;
	}
	/**
	 * Sets the area3AeronaveApePat
	 * @param area3AeronaveApePat The area3AeronaveApePat to set
	 */
	public void setArea3AeronaveApePat(String area3AeronaveApePat) {
		this.area3AeronaveApePat = area3AeronaveApePat;
	}

	/**
	 * Gets the area3AeronaveApeMat
	 * @return Returns a String
	 */
	public String getArea3AeronaveApeMat() {
		return area3AeronaveApeMat;
	}
	/**
	 * Sets the area3AeronaveApeMat
	 * @param area3AeronaveApeMat The area3AeronaveApeMat to set
	 */
	public void setArea3AeronaveApeMat(String area3AeronaveApeMat) {
		this.area3AeronaveApeMat = area3AeronaveApeMat;
	}

	/**
	 * Gets the area3AeronaveNombre
	 * @return Returns a String
	 */
	public String getArea3AeronaveNombre() {
		return area3AeronaveNombre;
	}
	/**
	 * Sets the area3AeronaveNombre
	 * @param area3AeronaveNombre The area3AeronaveNombre to set
	 */
	public void setArea3AeronaveNombre(String area3AeronaveNombre) {
		this.area3AeronaveNombre = area3AeronaveNombre;
	}

	/**
	 * Gets the area3AeronaveRazon
	 * @return Returns a String
	 */
	public String getArea3AeronaveRazon() {
		return area3AeronaveRazon;
	}
	/**
	 * Sets the area3AeronaveRazon
	 * @param area3AeronaveRazon The area3AeronaveRazon to set
	 */
	public void setArea3AeronaveRazon(String area3AeronaveRazon) {
		this.area3AeronaveRazon = area3AeronaveRazon;
	}

	/**
	 * Gets the hid2
	 * @return Returns a String
	 */
	public String getHid2() {
		return hid2;
	}
	/**
	 * Sets the hid2
	 * @param hid2 The hid2 to set
	 */
	public void setHid2(String hid2) {
		this.hid2 = hid2;
	}

	/**
	 * Gets the area3PredioTipoVia
	 * @return Returns a String
	 */
	public String getArea3PredioTipoVia() {
		return area3PredioTipoVia;
	}
	/**
	 * Sets the area3PredioTipoVia
	 * @param area3PredioTipoVia The area3PredioTipoVia to set
	 */
	public void setArea3PredioTipoVia(String area3PredioTipoVia) {
		this.area3PredioTipoVia = area3PredioTipoVia;
	}

	/**
	 * Gets the sedesSQLString
	 * @return Returns a String
	 */
	public String getSedesSQLString() {
		return sedesSQLString;
	}
	/**
	 * Sets the sedesSQLString
	 * @param sedesSQLString The sedesSQLString to set
	 */
	public void setSedesSQLString(String sedesSQLString) {
		this.sedesSQLString = sedesSQLString;
	}

	/**
	 * Gets the area3Siglas
	 * @return Returns a String
	 */
	public String getArea3Siglas() {
		return area3Siglas;
	}
	/**
	 * Sets the area3Siglas
	 * @param area3Siglas The area3Siglas to set
	 */
	public void setArea3Siglas(String area3Siglas) {
		this.area3Siglas = area3Siglas;
	}

	/**
	 * Gets the area2SiglasB
	 * @return Returns a String
	 */
	public String getArea2SiglasB() {
		return area2SiglasB;
	}
	/**
	 * Sets the area2SiglasB
	 * @param area2SiglasB The area2SiglasB to set
	 */
	public void setArea2SiglasB(String area2SiglasB) {
		this.area2SiglasB = area2SiglasB;
	}

	/**
	 * Gets the area3SiglasB
	 * @return Returns a String
	 */
	public String getArea3SiglasB() {
		return area3SiglasB;
	}
	/**
	 * Sets the area3SiglasB
	 * @param area3SiglasB The area3SiglasB to set
	 */
	public void setArea3SiglasB(String area3SiglasB) {
		this.area3SiglasB = area3SiglasB;
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
	 * Gets the sedesElegidas
	 * @return Returns a String[]
	 */
	public String[] getSedesElegidas() {
		return sedesElegidas;
	}
	/**
	 * Sets the sedesElegidas
	 * @param sedesElegidas The sedesElegidas to set
	 */
	public void setSedesElegidas(String[] sedesElegidas) {
		this.sedesElegidas = sedesElegidas;
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
	 * Gets the flagIncluirInactivos
	 * @return Returns a boolean
	 */
	public boolean getFlagIncluirInactivos() {
		return flagIncluirInactivos;
	}
	/**
	 * Sets the flagIncluirInactivos
	 * @param flagIncluirInactivos The flagIncluirInactivos to set
	 */
	public void setFlagIncluirInactivos(boolean flagIncluirInactivos) {
		this.flagIncluirInactivos = flagIncluirInactivos;
	}

	/**
	 * Gets the sedesElegidasOriginales
	 * @return Returns a String[]
	 */
	public String[] getSedesElegidasOriginales() {
		return sedesElegidasOriginales;
	}
	/**
	 * Sets the sedesElegidasOriginales
	 * @param sedesElegidasOriginales The sedesElegidasOriginales to set
	 */
	public void setSedesElegidasOriginales(String[] sedesElegidasOriginales) {
		this.sedesElegidasOriginales = sedesElegidasOriginales;
	}

	/**
	 * Gets the area4TipoParticipacion
	 * @return Returns a String
	 */
	public String getArea4TipoParticipacion() {
		return area4TipoParticipacion;
	}
	/**
	 * Sets the area4TipoParticipacion
	 * @param area4TipoParticipacion The area4TipoParticipacion to set
	 */
	public void setArea4TipoParticipacion(String area4TipoParticipacion) {
		this.area4TipoParticipacion = area4TipoParticipacion;
	}

	/**
	 * Gets the area4ApePat
	 * @return Returns a String
	 */
	public String getArea4ApePat() {
		return area4ApePat;
	}
	/**
	 * Sets the area4ApePat
	 * @param area4ApePat The area4ApePat to set
	 */
	public void setArea4ApePat(String area4ApePat) {
		this.area4ApePat = area4ApePat;
	}

	/**
	 * Gets the area4ApeMat
	 * @return Returns a String
	 */
	public String getArea4ApeMat() {
		return area4ApeMat;
	}
	/**
	 * Sets the area4ApeMat
	 * @param area4ApeMat The area4ApeMat to set
	 */
	public void setArea4ApeMat(String area4ApeMat) {
		this.area4ApeMat = area4ApeMat;
	}

	/**
	 * Gets the area4Nombre
	 * @return Returns a String
	 */
	public String getArea4Nombre() {
		return area4Nombre;
	}
	/**
	 * Sets the area4Nombre
	 * @param area4Nombre The area4Nombre to set
	 */
	public void setArea4Nombre(String area4Nombre) {
		this.area4Nombre = area4Nombre;
	}

	/**
	 * Gets the area4Razon
	 * @return Returns a String
	 */
	public String getArea4Razon() {
		return area4Razon;
	}
	/**
	 * Sets the area4Razon
	 * @param area4Razon The area4Razon to set
	 */
	public void setArea4Razon(String area4Razon) {
		this.area4Razon = area4Razon;
	}

	/**
	 * Gets the area4Tipo
	 * @return Returns a String
	 */
	public String getArea4Tipo() {
		return area4Tipo;
	}
	/**
	 * Sets the area4Tipo
	 * @param area4Tipo The area4Tipo to set
	 */
	public void setArea4Tipo(String area4Tipo) {
		this.area4Tipo = area4Tipo;
	}

	/**
	 * Gets the area4NumMotor
	 * @return Returns a String
	 */
	public String getArea4NumMotor() {
		return area4NumMotor;
	}
	/**
	 * Sets the area4NumMotor
	 * @param area4NumMotor The area4NumMotor to set
	 */
	public void setArea4NumMotor(String area4NumMotor) {
		this.area4NumMotor = area4NumMotor;
	}

	/**
	 * Gets the area4NumChasis
	 * @return Returns a String
	 */
	public String getArea4NumChasis() {
		return area4NumChasis;
	}
	/**
	 * Sets the area4NumChasis
	 * @param area4NumChasis The area4NumChasis to set
	 */
	public void setArea4NumChasis(String area4NumChasis) {
		this.area4NumChasis = area4NumChasis;
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
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @return the numeroPlaca
	 */
	public String getNumeroPlaca() {
		return numeroPlaca;
	}
	/**
	 * @param numeroPlaca the numeroPlaca to set
	 */
	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}
	/**
	 * @return the otrosDatos
	 */
	public String getOtrosDatos() {
		return otrosDatos;
	}
	/**
	 * @param otrosDatos the otrosDatos to set
	 */
	public void setOtrosDatos(String otrosDatos) {
		this.otrosDatos = otrosDatos;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 31, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the flagPagineoInferior
	 */
	public boolean isFlagPagineoInferior() {
		return flagPagineoInferior;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 31, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param flagPagineoInferior the flagPagineoInferior to set
	 */
	public void setFlagPagineoInferior(boolean flagPagineoInferior) {
		this.flagPagineoInferior = flagPagineoInferior;
	}

} // fin de clase

