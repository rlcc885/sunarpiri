package gob.pe.sunarp.extranet.webservices;


public interface BusquedaIndicesServiceProvider_SEI extends java.rmi.Remote
{
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorPersonaJuridica(java.lang.String usuario,java.lang.String clave,java.lang.String razonSocial,java.lang.String siglas);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorTipoNumeroDocumento(java.lang.String usuario,java.lang.String clave,java.lang.String codigoTipoDocumento,java.lang.String numeroDocumento);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorPersonaNatural(java.lang.String usuario,java.lang.String clave,java.lang.String apellidoPaterno,java.lang.String apellidoMaterno,java.lang.String nombre);
}