package gob.pe.sunarp.extranet.webservices;


public interface BusquedaNacionalIndicesServiceProvider_SEI extends java.rmi.Remote
{
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaPorTipoNumeroDocumento(java.lang.String usuario,java.lang.String clave,java.lang.String codigoTipoDocumento,java.lang.String numeroDocumento);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaPorPersonaJuridica(java.lang.String usuario,java.lang.String clave,java.lang.String razonSocial,java.lang.String siglas);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorPersonaNatural(java.lang.String usuario,java.lang.String clave,java.lang.String apellidoPaterno,java.lang.String apellidoMaterno,java.lang.String nombre);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaPorBien(java.lang.String usuario,java.lang.String clave,java.lang.String numeroPlaca,java.lang.String numeroMatricula,java.lang.String nombre,java.lang.String numeroSerie);
}