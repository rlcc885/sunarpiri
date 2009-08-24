package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.math.*;

/**
 * 
 * @modelguid {A1E687AB-89B9-4D66-B13E-F2BA311D0337}
 */
public class Vehiculo extends SunarpBean{
	
	/** @modelguid {7B2C9439-733A-4AEC-937E-DB03E948AF9B} */
	private String placa;

	/** @modelguid {69A75C07-9606-407B-9271-869B30F39A59} */
	private String serie;

	/** @modelguid {5C849119-5315-48CA-8C73-AC6C77F82E19} */
	private String motor;

	/** @modelguid {A5772311-EC72-4CD0-87EB-1E4934432FCC} */
	private String numeroPartida;

	/** @modelguid {5EB8289E-4DD1-4BA9-944B-FF091E1DD3C1} */
	private String codigoZonaRegistral;

	/** @modelguid {2C16DE7F-2295-47E0-B2E1-DE79E57CF603} */
	private String codigoOficinaRegistral;

	/** @modelguid {AE7FDEA4-8782-45D8-AADB-DC784F3C5F6B} */
	private String codigoTipoSistema;

	/** @modelguid {CFA69349-46F8-49FF-A462-2A2A1A078659} */
	private String descripcionTipoSistema;

	/** @modelguid {D8F1E390-EE56-411A-8BE7-7090FCEF6C66} */
	private String codigoSubActo;
	
	/** @modelguid {5F03C4F5-C64D-4F6A-BE02-69B595621FA9} */
	private String descripcionSubActo;
	
	/** @modelguid {5CA37883-1DAA-4443-87BB-7419CA33B2BA} */
	private String codigoFormaPago;
	
	/** @modelguid {44DF18F5-520A-48F9-AC05-4B836CF83265} */
	private String descripcionFormaPago;
	
	/** @modelguid {7D779A21-EBB6-428C-839B-D01F7BAE9191} */
	private String codigoMoneda;
	
	/** @modelguid {EBD04D13-3E8E-4E42-A78B-16B4B086DB52} */
	private String descripcionMoneda;
	
	/** @modelguid {5304B769-1D28-450A-8C24-C044FB696919} */
	private BigDecimal monto;
	
	/** @modelguid {4696285C-74AB-444C-9076-63D918F5862D} */
	private BigDecimal pagado;
	
	/** @modelguid {74EE4232-0F7A-40FA-B9F5-B8D54A8D9455} */
	private BigDecimal saldo;
	
	/** @modelguid {2291DE6B-C952-4AFF-8FFC-C975CBAF9A4C} */
	private String observaciones;
	
	/** @modelguid {09AEC9DC-5430-4C36-B41A-321044546E4A} */
	private String codigoVendedor;
	
	/** @modelguid {43B665BB-38CB-4D0D-9D4A-73434F50FC36} */
	private String descripcionVendedor;
	
	/** @modelguid {0E00FC83-E2C1-49AA-B185-47D2DADA5B53} */
	private String codigoComprador;
	
	/** @modelguid {79F7A5C2-AFB9-4E90-B63D-E22F24CBD858} */
	private String descripcionComprador;
	
	private String descripcionOficinaRegistral;
	/**
	 * 
	 * @modelguid {00AE5287-F01D-40D5-B80E-21BB1C9D0A24}
	 */
	private java.util.ArrayList compradoresPersonaJuridica;

	/**
	 * 
	 * @modelguid {D4472420-E459-482C-A6FB-20250A41F3EC}
	 */
	private java.util.ArrayList compradoresPersonaNatural;
	
    /*** inicio: jrosas 03-08-07  **/
	private String oficinaRegistral;
	private String zonaRegistral;
	private String codigoLibro;
	private String area_reg_id;
	private String marca;
	private String clase;
	private String tipoParticipacion;
	private String estadoParticipante;
	/*** fin: jrosas 03-08-07  **/
	
	/**
	 * @return the tipoParticipacion
	 */
	public String getTipoParticipacion() {
		return tipoParticipacion;
	}

	/**
	 * @param tipoParticipacion the tipoParticipacion to set
	 */
	public void setTipoParticipacion(String tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}

	/**
	 * @return the codigoLibro
	 */
	public String getCodigoLibro() {
		return codigoLibro;
	}

	/**
	 * @param codigoLibro the codigoLibro to set
	 */
	public void setCodigoLibro(String codigoLibro) {
		this.codigoLibro = codigoLibro;
	}

	/**
	 * @return the oficinaRegistral
	 */
	public String getOficinaRegistral() {
		return oficinaRegistral;
	}

	/**
	 * @param oficinaRegistral the oficinaRegistral to set
	 */
	public void setOficinaRegistral(String oficinaRegistral) {
		this.oficinaRegistral = oficinaRegistral;
	}

	/**
	 * @return the zonaRegistral
	 */
	public String getZonaRegistral() {
		return zonaRegistral;
	}

	/**
	 * @param zonaRegistral the zonaRegistral to set
	 */
	public void setZonaRegistral(String zonaRegistral) {
		this.zonaRegistral = zonaRegistral;
	}

	/**
	 * @return
	 * @modelguid {583CDA60-1090-4952-8B73-EDA082269353}
	 */
	public String getCodigoOficinaRegistral() {
		return codigoOficinaRegistral;
	}

	/**
	 * @return
	 * @modelguid {7C24590B-656F-4B08-B10A-EAE9A97D1A6C}
	 */
	public String getCodigoZonaRegistral() {
		return codigoZonaRegistral;
	}

	/**
	 * @return
	 * @modelguid {D6AA4923-1E6B-4B76-AF7B-FFD4E006C6AE}
	 */
	public String getMotor() {
		return motor;
	}

	/**
	 * @return
	 * @modelguid {D6252E59-E852-49B7-AB55-6FD6B7CC7B28}
	 */
	public String getNumeroPartida() {
		return numeroPartida;
	}

	/**
	 * @return
	 * @modelguid {95DD0175-F130-489C-85C3-0C7E637278D6}
	 */
	public String getPlaca() {
		return placa;
	}

	/**
	 * @return
	 * @modelguid {F04794AE-1FDE-4CCC-852A-6B9AC843B7D6}
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param string
	 * @modelguid {DE2A57E0-D5D0-465A-8928-8BCF483D880E}
	 */
	public void setCodigoOficinaRegistral(String string) {
		codigoOficinaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {40A0B0CA-39A1-4B6D-B765-466B14704F22}
	 */
	public void setCodigoZonaRegistral(String string) {
		codigoZonaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {A6A6CB8A-76E7-41FD-800D-C221FCEE53B2}
	 */
	public void setMotor(String string) {
		motor = string;
	}

	/**
	 * @param string
	 * @modelguid {22C6B2D8-DEF1-4501-B193-AF2128EFEEC9}
	 */
	public void setNumeroPartida(String string) {
		numeroPartida = string;
	}

	/**
	 * @param string
	 * @modelguid {BF717897-90E6-4FDA-B3C3-3DB9E51BB3C3}
	 */
	public void setPlaca(String string) {
		placa = string;
	}

	/**
	 * @param string
	 * @modelguid {EB641DB3-5EE9-4D45-AAA9-A5828AAB00DC}
	 */
	public void setSerie(String string) {
		serie = string;
	}

	/**
	 * @return
	 * @modelguid {E3CDE46F-76B7-4047-BD3E-BF069BC387D8}
	 */
	public String getCodigoTipoSistema() {
		return codigoTipoSistema;
	}

	/**
	 * @return
	 * @modelguid {70F5CB7E-7A7F-40FE-A360-4131DB9CECEB}
	 */
	public String getDescripcionTipoSistema() {
		return descripcionTipoSistema;
	}

	/**
	 * @param string
	 * @modelguid {B4BFC93C-2BBA-4C5B-BE28-CBCAC751223C}
	 */
	public void setCodigoTipoSistema(String string) {
		codigoTipoSistema = string;
	}

	/**
	 * @param string
	 * @modelguid {052AB8E6-427E-4FB4-956F-DED87AE333DA}
	 */
	public void setDescripcionTipoSistema(String string) {
		descripcionTipoSistema = string;
	}

	/**
	 * @return
	 * @modelguid {199CDE2B-8537-488B-BD04-1AEE94470FC4}
	 */
	public String getCodigoComprador() {
		return codigoComprador;
	}

	/**
	 * @return
	 * @modelguid {0CB6E6BA-CC05-4D3F-A8C4-F8E7B866C875}
	 */
	public String getCodigoFormaPago() {
		return codigoFormaPago;
	}

	/**
	 * @return
	 * @modelguid {098867C7-B3A1-4691-947C-DF085BD05C72}
	 */
	public String getCodigoMoneda() {
		return codigoMoneda;
	}

	/**
	 * @return
	 * @modelguid {C2831E0D-6C52-4EC0-A4A7-0AD3E99E06A2}
	 */
	public String getCodigoSubActo() {
		return codigoSubActo;
	}

	/**
	 * @return
	 * @modelguid {5CEA0711-D6AB-453E-ACF9-82642738FB24}
	 */
	public String getCodigoVendedor() {
		return codigoVendedor;
	}

	/**
	 * @return
	 * @modelguid {5F959352-EA14-4884-9CC7-ED9C97B652C3}
	 */
	public String getDescripcionComprador() {
		return descripcionComprador;
	}

	/**
	 * @return
	 * @modelguid {9F56AEF5-B54F-4774-9661-0B540942E47B}
	 */
	public String getDescripcionFormaPago() {
		return descripcionFormaPago;
	}

	/**
	 * @return
	 * @modelguid {BC397538-985D-470F-BA88-3DC2C81A7A93}
	 */
	public String getDescripcionMoneda() {
		return descripcionMoneda;
	}

	/**
	 * @return
	 * @modelguid {E666D865-43FE-45F1-AD63-A38ECE51BE04}
	 */
	public String getDescripcionSubActo() {
		return descripcionSubActo;
	}

	/**
	 * @return
	 * @modelguid {3A9D8097-F0AE-4ED4-8533-D63FE59D3A31}
	 */
	public String getDescripcionVendedor() {
		return descripcionVendedor;
	}

	/**
	 * @return
	 * @modelguid {87B5310C-E284-4279-8F60-296F8F2621B9}
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @return
	 * @modelguid {DC1A51D8-1CD4-4099-B329-3B696F500783}
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @return
	 * @modelguid {AE6E1A4A-7689-44F6-856C-F594DFD6AA37}
	 */
	public BigDecimal getPagado() {
		return pagado;
	}

	/**
	 * @return
	 * @modelguid {41D224D1-8F71-42C8-8D47-E2997A016175}
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param string
	 * @modelguid {C9FA74FF-6F3C-4E06-BEEB-53BF1D836ECF}
	 */
	public void setCodigoComprador(String string) {
		codigoComprador = string;
	}

	/**
	 * @param string
	 * @modelguid {08CCCF82-49D7-4B28-BC9A-C227927FF956}
	 */
	public void setCodigoFormaPago(String string) {
		codigoFormaPago = string;
	}

	/**
	 * @param string
	 * @modelguid {7ADCA502-2AB6-4CAD-BEE8-9B7B398A7091}
	 */
	public void setCodigoMoneda(String string) {
		codigoMoneda = string;
	}

	/**
	 * @param string
	 * @modelguid {C305C3D0-DD6D-4AB6-9EE4-80C348457332}
	 */
	public void setCodigoSubActo(String string) {
		codigoSubActo = string;
	}

	/**
	 * @param string
	 * @modelguid {D20E47A9-75CE-40F6-8D82-A67F6281E37F}
	 */
	public void setCodigoVendedor(String string) {
		codigoVendedor = string;
	}

	/**
	 * @param string
	 * @modelguid {B1CB3A23-05B9-416D-80E5-FD774FB2A5C1}
	 */
	public void setDescripcionComprador(String string) {
		descripcionComprador = string;
	}

	/**
	 * @param string
	 * @modelguid {28BCF0B0-0AD7-40F0-9040-3089B6C36880}
	 */
	public void setDescripcionFormaPago(String string) {
		descripcionFormaPago = string;
	}

	/**
	 * @param string
	 * @modelguid {21199374-1099-4B9A-8FDB-5A72CA2C6559}
	 */
	public void setDescripcionMoneda(String string) {
		descripcionMoneda = string;
	}

	/**
	 * @param string
	 * @modelguid {8142BC6A-C064-4999-A849-B8A24856799B}
	 */
	public void setDescripcionSubActo(String string) {
		descripcionSubActo = string;
	}

	/**
	 * @param string
	 * @modelguid {4353DE31-CAAB-47E2-A99C-16EB259D048F}
	 */
	public void setDescripcionVendedor(String string) {
		descripcionVendedor = string;
	}

	/**
	 * @param decimal
	 * @modelguid {D71F0B43-101A-43F5-AE02-FF88DF968DF0}
	 */
	public void setMonto(BigDecimal decimal) {
		monto = decimal;
	}

	/**
	 * @param string
	 * @modelguid {F0589FB8-6E52-413E-B2A4-C2F1D484826F}
	 */
	public void setObservaciones(String string) {
		observaciones = string;
	}

	/**
	 * @param decimal
	 * @modelguid {92B7B57C-D946-4C49-AD03-F6F21652C982}
	 */
	public void setPagado(BigDecimal decimal) {
		pagado = decimal;
	}

	/**
	 * @param decimal
	 * @modelguid {248631AD-ABB6-4F4B-9E40-08AF9466A2E1}
	 */
	public void setSaldo(BigDecimal decimal) {
		saldo = decimal;
	}

	/**
	 * @return
	 * @modelguid {E0F94796-9A4A-4BF4-A74F-DF40DEF1DB7B}
	 */
	public java.util.ArrayList getCompradoresPersonaJuridica() {
		return compradoresPersonaJuridica;
	}

	/**
	 * @return
	 * @modelguid {679C37D1-3997-45C9-A676-1261FA2F25DC}
	 */
	public java.util.ArrayList getCompradoresPersonaNatural() {
		return compradoresPersonaNatural;
	}

	/**
	 * @param list
	 * @modelguid {0838B465-F9D5-4018-BA3F-0921D141D6F3}
	 */
	public void setCompradoresPersonaJuridica(java.util.ArrayList list) {
		compradoresPersonaJuridica = list;
	}

	/**
	 * @param list
	 * @modelguid {992F9A53-03D3-4CDA-9C2B-2537B2A18183}
	 */
	public void setCompradoresPersonaNatural(java.util.ArrayList list) {
		compradoresPersonaNatural = list;
	}

	/**
	 * Gets the descripcionOficinaRegistral
	 * @return Returns a String
	 */
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}
	/**
	 * Sets the descripcionOficinaRegistral
	 * @param descripcionOficinaRegistral The descripcionOficinaRegistral to set
	 */
	public void setDescripcionOficinaRegistral(String descripcionOficinaRegistral) {
		this.descripcionOficinaRegistral = descripcionOficinaRegistral;
	}

	/**
	 * @return the clase
	 */
	public String getClase() {
		return clase;
	}

	/**
	 * @param clase the clase to set
	 */
	public void setClase(String clase) {
		this.clase = clase;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the estadoParticipante
	 */
	public String getEstadoParticipante() {
		return estadoParticipante;
	}

	/**
	 * @param estadoParticipante the estadoParticipante to set
	 */
	public void setEstadoParticipante(String estadoParticipante) {
		this.estadoParticipante = estadoParticipante;
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

}

