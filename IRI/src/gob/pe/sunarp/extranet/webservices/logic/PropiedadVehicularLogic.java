package gob.pe.sunarp.extranet.webservices.logic;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaSolicitudInscripcionBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.webservices.WebServiceUtil;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaCabeceraPropiedadVehicularBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaDetallePropiedadVehicularBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPVReqBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPropiedadVehicularBean;
import gob.pe.sunarp.extranet.webservices.exception.RecuperaDataException;
import gob.pe.sunarp.extranet.webservices.exception.VerificacionDataException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PropiedadVehicularLogic {

	private static final int COD_GLA = 3;
	private static final String FLAG_COBRO = "1";
	private static final String REMOTE_ADDR = "localhost";
	private static final String TIPO_OBJETO = "BusqXPropVehicular";

	public static PartidaXPropiedadVehicularBean buscaPropiedadVehicular (BuscaPartidaXPVReqBean req) {
		LogAuditoriaSolicitudInscripcionBean logBean = null;
		PartidaXPropiedadVehicularBean bean = null;
		UsuarioBean usuario = null;
		DBConnectionFactory pool = null;
		Connection conn = null;
		Serializable obj = null;
		int nroPagina = 0;
		
		try {
			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit(false);

			obj = WebServiceUtil.getConsultaAnterior(conn, req.getCuo(), TIPO_OBJETO);

			if (obj == null) {
				verificarData(req);

				if (WebServiceUtil.validaOperacion(conn, req.getCuo(), req.getCodServicio(), req.getCodigoUsuario(), req.getCodInstitucion(), req.getDescInstitucion())) {
					usuario = WebServiceUtil.getUserBean(conn, req.getCodigoUsuario());
			
					if (WebServiceUtil.validaPermisoUsuario(conn, usuario.getCuentaId(), Constantes.COD_SERVICIO_BUSQUEDA_X_PROPIEDAD_INMUEBLE)) {
						logBean = new LogAuditoriaSolicitudInscripcionBean();
						
						logBean.setUsuarioSession(usuario);
						logBean.setCodigoGLA(COD_GLA);//PONER CONSTANTE
						logBean.setCodigoServicio(Integer.parseInt(Constantes.COD_SERVICIO_BUSQUEDA_X_PROPIEDAD_VEHICULAR));//PONER CONSTANTE
						logBean.setFlagCobro(FLAG_COBRO);////
						logBean.setRemoteAddr(REMOTE_ADDR);///
				
						Transaction.getInstance().registraTransaccion(logBean, conn);
					}
				}

				bean = recuperaData(conn, req);
				
				WebServiceUtil.insertaConsulta(req.getCuo(), TIPO_OBJETO, (Serializable)bean);
			}
			else {
				bean = (PartidaXPropiedadVehicularBean)obj;
			}
			
			if (bean.getDetalle().length > 0)
				bean = getPagina(Integer.parseInt(req.getNroPagina()), bean);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		
		return bean;
	}

	private static PartidaXPropiedadVehicularBean getPagina(int nroPagina, PartidaXPropiedadVehicularBean bean) throws VerificacionDataException {
		BuscaPartidaDetallePropiedadVehicularBean[] detalle = null;
		BuscaPartidaDetallePropiedadVehicularBean[] nuevo = null;
		int size = 0;
		int aux = 0;
		int limInf = 0;
		int limSup = 0;

		detalle = bean.getDetalle();
		size = detalle.length;

		if (nroPagina == 0)
			nroPagina++;
				
		if (nroPagina == 1) {
			if (size < Constantes.CANTIDAD_REGISTROS_POR_PAGINA)
				nuevo = new BuscaPartidaDetallePropiedadVehicularBean[size];
			else
				nuevo = new BuscaPartidaDetallePropiedadVehicularBean[Constantes.CANTIDAD_REGISTROS_POR_PAGINA];
			
			for (int i = 0; i < size; i++) {
				if (i < Constantes.CANTIDAD_REGISTROS_POR_PAGINA)
					nuevo[i] = detalle[i];
				else
					break;
			}
		}
		else {
			limInf = (nroPagina - 1) * Constantes.CANTIDAD_REGISTROS_POR_PAGINA;
			limSup = (nroPagina * Constantes.CANTIDAD_REGISTROS_POR_PAGINA);
			
			if (limSup > size)
				limSup = size;

			int j = limInf;
			
			size = limSup - limInf;
			nuevo = new BuscaPartidaDetallePropiedadVehicularBean[size];
			
			for (int i = 0; i < size; i++) {
				if (j >= limSup)
					break;

				nuevo[i] = detalle[j];
				j++;
			}
		}
		
		bean.setDetalle(nuevo);
		
		return bean;
	}

	private static void verificarData (BuscaPartidaXPVReqBean req) throws VerificacionDataException {
		if (req == null)
			throw new VerificacionDataException("Requerimiento inválido.");
		else if ((req.getNroPartida() == null || req.getNroPartida().trim().length() == 0) && (req.getNroPlaca() == null || req.getNroPlaca().trim().length() == 0))
			throw new VerificacionDataException("El parámetro número partida o el número de placa no debe de ser nulo.");
		else if (req.getAreaRegistral() == null || req.getAreaRegistral().trim().length() == 0)
			throw new VerificacionDataException("El parámetro area registral no debe de ser nulo.");
		else if (req.getOficinaRegistral() == null || req.getOficinaRegistral().trim().length() == 0)
			throw new VerificacionDataException("El parámetro oficina registral no debe de ser nulo.");
		else if (req.getRegPubId() == null || req.getRegPubId().trim().length() == 0)
			throw new VerificacionDataException("El parámetro registro público no debe de ser nulo.");
		else if (req.getCodigoUsuario() == null || req.getCodigoUsuario().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de usuario no debe de ser nulo.");
		else if (req.getCuo() == null || req.getCuo().trim().length() == 0)
			throw new VerificacionDataException("El parámetro cuo no debe de ser nulo.");
		else if (req.getCodServicio() == null || req.getCodServicio().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo servicio no debe de ser nulo.");
		else if (req.getCodInstitucion() == null || req.getCodInstitucion().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo institución no debe de ser nulo.");
		else if (req.getDescInstitucion() == null || req.getDescInstitucion().trim().length() == 0)
			throw new VerificacionDataException("El parámetro descripción institución no debe de ser nulo.");
	}

	private static PartidaXPropiedadVehicularBean recuperaData (Connection conn, BuscaPartidaXPVReqBean req) throws RecuperaDataException {
		PartidaXPropiedadVehicularBean partida = null;
		BuscaPartidaCabeceraPropiedadVehicularBean cabecera = null;
		BuscaPartidaDetallePropiedadVehicularBean detalle = null;
		BuscaPartidaDetallePropiedadVehicularBean[] list = null;
		StringBuffer sbSQL = null;
		Vector vDetalle = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			sbSQL = new StringBuffer();

			sbSQL.append("SELECT vh.num_placa, vh.fg_baja, pa.estado, pa.num_partida, pa.REG_PUB_ID, ");
			sbSQL.append("pa.area_reg_id, pa.cod_libro, regp.SIGLAS as siglas, ofir.NOMBRE as nombre, ");
			sbSQL.append("pa.REFNUM_PART as refnum_part, area.nombre as descripcionAreaRegistral, ofir.OFIC_REG_ID ");
			sbSQL.append("FROM user1.partida pa, user1.VEHICULO vh, user1.REGIS_PUBLICO regp, ");
			sbSQL.append("user1.OFIC_REGISTRAL ofir, user1.TM_AREA_REGISTRAL area ");
			sbSQL.append("WHERE pa.reg_pub_id = '").append(req.getRegPubId()).append("' ");

			if (req.getNroPlaca() != null)
				sbSQL.append("AND vh.num_placa = '").append(req.getNroPlaca()).append("' ");

			if (req.getNroPartida() != null)
				sbSQL.append("AND pa.num_partida = '").append(req.getNroPartida()).append("' ");

			sbSQL.append("AND pa.ofic_reg_id = '").append(req.getOficinaRegistral()).append("' ");
			sbSQL.append("AND pa.area_reg_id = '").append(req.getAreaRegistral()).append("' ");
			sbSQL.append("AND pa.estado != '2' ");
			sbSQL.append("AND pa.refnum_part = vh.refnum_part ");
			sbSQL.append("AND vh.fg_baja = '0' ");
			sbSQL.append("AND regp.REG_PUB_ID = ofir.REG_PUB_ID ");
			sbSQL.append("AND ofir.OFIC_REG_ID = pa.OFIC_REG_ID ");
			sbSQL.append("AND ofir.REG_PUB_ID = pa.REG_PUB_ID ");
			sbSQL.append("AND area.area_reg_id = pa.area_reg_id ");

			System.out.println("[QUERY]: " + sbSQL.toString());

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sbSQL.toString());

			partida = new PartidaXPropiedadVehicularBean();
			cabecera = new BuscaPartidaCabeceraPropiedadVehicularBean();
			vDetalle = new Vector();
			
			cabecera.setNroPartida(req.getNroPartida());
			cabecera.setNroPlaca(req.getNroPlaca());
			cabecera.setOficinaRegistral(req.getOficinaRegistral());
			cabecera.setRegPubId(req.getRegPubId());
			cabecera.setAreaRegistral(req.getAreaRegistral());
			
			cabecera.setCostoServicio("");
			cabecera.setCodigoUsuario(req.getCodigoUsuario());
			cabecera.setCodInstitucion(req.getCodInstitucion());
			cabecera.setCodServicio(req.getCodServicio());
			cabecera.setDescInstitucion(req.getDescInstitucion());
			cabecera.setCuo(req.getCuo());

			while (rs.next()) {
				detalle = new BuscaPartidaDetallePropiedadVehicularBean();

				detalle.setZonaRegistral(rs.getString("REG_PUB_ID"));
				detalle.setOficinaRegistral(rs.getString("OFIC_REG_ID"));
				detalle.setNroPartida(rs.getString("NUM_PARTIDA"));
				detalle.setEstado(rs.getString("ESTADO"));
				detalle.setPlaca(rs.getString("NUM_PLACA"));
				detalle.setEstadoVehiculo(rs.getString("FG_BAJA"));

				vDetalle.add(detalle);
			}
			
			list = new BuscaPartidaDetallePropiedadVehicularBean[vDetalle.size()];
			vDetalle.copyInto(list);
			
			partida.setCabecera(cabecera);
			partida.setDetalle(list);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("*****RESULTADO ENVIADO*****");
		return partida;
	}
}