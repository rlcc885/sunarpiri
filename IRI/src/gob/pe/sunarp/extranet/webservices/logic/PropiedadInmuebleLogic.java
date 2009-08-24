package gob.pe.sunarp.extranet.webservices.logic;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaSolicitudInscripcionBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.webservices.WebServiceUtil;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaCabeceraPropiedadInmuebleBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaDetallePropiedadInmuebleBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPIReqBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPropiedadInmuebleBean;
import gob.pe.sunarp.extranet.webservices.exception.RecuperaDataException;
import gob.pe.sunarp.extranet.webservices.exception.VerificacionDataException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PropiedadInmuebleLogic {

	private static final int COD_GLA = 3;
	private static final String FLAG_COBRO = "1";
	private static final String REMOTE_ADDR = "localhost";
	private static final String TIPO_OBJETO = "BusqXPropInmueble";

	public static PartidaXPropiedadInmuebleBean buscaPropiedadInmueble (BuscaPartidaXPIReqBean req) {
		LogAuditoriaSolicitudInscripcionBean logBean = null;
		PartidaXPropiedadInmuebleBean bean = null;
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
						logBean.setCodigoServicio(Integer.parseInt(Constantes.COD_SERVICIO_BUSQUEDA_X_PROPIEDAD_INMUEBLE));//PONER CONSTANTE
						logBean.setFlagCobro(FLAG_COBRO);////
						logBean.setRemoteAddr(REMOTE_ADDR);///
				
						Transaction.getInstance().registraTransaccion(logBean, conn);
					}
				}

				bean = recuperaData(conn, req);
				
				WebServiceUtil.insertaConsulta(req.getCuo(), TIPO_OBJETO, (Serializable)bean);
			}
			else {
				bean = (PartidaXPropiedadInmuebleBean)obj;
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

	private static PartidaXPropiedadInmuebleBean getPagina(int nroPagina, PartidaXPropiedadInmuebleBean bean) throws VerificacionDataException {
		BuscaPartidaDetallePropiedadInmuebleBean[] detalle = null;
		BuscaPartidaDetallePropiedadInmuebleBean[] nuevo = null;
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
				nuevo = new BuscaPartidaDetallePropiedadInmuebleBean[size];
			else
				nuevo = new BuscaPartidaDetallePropiedadInmuebleBean[Constantes.CANTIDAD_REGISTROS_POR_PAGINA];
			
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
			nuevo = new BuscaPartidaDetallePropiedadInmuebleBean[size];
			
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

	private static void verificarData (BuscaPartidaXPIReqBean req) throws VerificacionDataException {
		if (req == null)
			throw new VerificacionDataException("Requerimiento inválido.");
		else if (req.getCodigoLibro() == null || req.getCodigoLibro().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de libro no debe de ser nulo.");
		else if (req.getCodigoUsuario() == null || req.getCodigoUsuario().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de usuario no debe de ser nulo.");
		else if (req.getNroPartida() == null || req.getNroPartida().trim().length() == 0)
			throw new VerificacionDataException("El parámetro número partida no debe de ser nulo.");
		else if (req.getZonasRegistrales() == null || req.getZonasRegistrales().trim().length() == 0)
			throw new VerificacionDataException("El parámetro zona registral no debe de ser nulo.");
		else if (req.getCuo() == null || req.getCuo().trim().length() == 0)
			throw new VerificacionDataException("El parámetro cuo no debe de ser nulo.");
		else if (req.getCodServicio() == null || req.getCodServicio().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo servicio no debe de ser nulo.");
		else if (req.getCodInstitucion() == null || req.getCodInstitucion().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo institución no debe de ser nulo.");
		else if (req.getDescInstitucion() == null || req.getDescInstitucion().trim().length() == 0)
			throw new VerificacionDataException("El parámetro descripción institución no debe de ser nulo.");
	}

	private static PartidaXPropiedadInmuebleBean recuperaData (Connection conn, BuscaPartidaXPIReqBean req) throws RecuperaDataException {
		PartidaXPropiedadInmuebleBean partida = null;
		BuscaPartidaCabeceraPropiedadInmuebleBean cabecera = null;
		BuscaPartidaDetallePropiedadInmuebleBean detalle = null;
		BuscaPartidaDetallePropiedadInmuebleBean[] list = null;
		StringBuffer sbSQL = null;
		Vector vDetalle = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			sbSQL = new StringBuffer();

			sbSQL.append(" SELECT ");

			sbSQL.append(" PARTIDA.ESTADO as estado, partida.reg_pub_id,");
			sbSQL.append(" REGIS_PUBLICO.SIGLAS as siglas, PARTIDA.OFIC_REG_ID,");
			sbSQL.append(" OFIC_REGISTRAL.NOMBRE as nombre, ");
			sbSQL.append(" PARTIDA.area_reg_id as area_reg_id, ");
			sbSQL.append(" PARTIDA.REFNUM_PART as refnum_part, ");
			sbSQL.append(" PARTIDA.NUM_PARTIDA as num_partida, ");
			sbSQL.append(" PARTIDA.COD_LIBRO as cod_libro, ");

			// Departamento, Provincia, Distrito
			sbSQL.append(" TMD.NOMBRE AS DEPARTAMENTO, TMP.NOMBRE AS PROVINCIA, TMI.NOMBRE AS DISTRITO");
			
			sbSQL.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, grupo_libro_area gla,");
			sbSQL.append(" grupo_libro_area_det glad, TM_AREA_REGISTRAL area, ");
			
			// Departamento, Provincia, Distrito
			sbSQL.append(" TM_PROVINCIA TMP, TM_DEPARTAMENTO TMD, TM_DISTRITO TMI");
			
			sbSQL.append(" WHERE ");
			sbSQL.append(" PARTIDA.NUM_PARTIDA = '").append(req.getNroPartida()).append("'");
			sbSQL.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			sbSQL.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			sbSQL.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			sbSQL.append(" and PARTIDA.REG_PUB_ID ='").append(req.getZonasRegistrales()).append("' ");
			sbSQL.append(" and PARTIDA.OFIC_REG_ID = '").append(req.getOficinaRegistral()).append("' ");
			sbSQL.append(" and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
			sbSQL.append(" and partida.cod_libro = glad.cod_libro  ");
			sbSQL.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			sbSQL.append(" and gla.cod_grupo_libro_area ='").append(req.getCodigoLibro()).append("' ");
			sbSQL.append(" and PARTIDA.ESTADO != '2' ");
			
			// Departamento, Provincia, Distrito
			sbSQL.append(" AND OFIC_REGISTRAL.REG_PUB_ID = TMP.REG_PUB_ID AND OFIC_REGISTRAL.OFIC_REG_ID = TMP.OFIC_REG_ID");
			sbSQL.append(" AND TMP.PAIS_ID = TMD.PAIS_ID AND TMP.DPTO_ID = TMD.DPTO_ID");
			sbSQL.append(" AND TMP.PAIS_ID = TMI.PAIS_ID AND TMP.DPTO_ID = TMI.DPTO_ID AND TMP.PROV_ID = TMI.PROV_ID");
			
			sbSQL.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");

			System.out.println("[QUERY]: " + sbSQL.toString());

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sbSQL.toString());

			partida = new PartidaXPropiedadInmuebleBean();
			cabecera = new BuscaPartidaCabeceraPropiedadInmuebleBean();
			vDetalle = new Vector();
			
			cabecera.setCodigoUsuario(req.getCodigoUsuario());
			cabecera.setCostoServicio("");
			cabecera.setNroPartida(req.getNroPartida());
			cabecera.setZonasRegistrales(req.getZonasRegistrales());
			cabecera.setOficinaRegistral(req.getOficinaRegistral());
			cabecera.setCodInstitucion(req.getCodInstitucion());
			cabecera.setCodServicio(req.getCodServicio());
			cabecera.setDescInstitucion(req.getDescInstitucion());
			cabecera.setCuo(req.getCuo());

			while (rs.next()) {
				detalle = new BuscaPartidaDetallePropiedadInmuebleBean();

				detalle.setZonaRegistral(rs.getString("REG_PUB_ID"));
				detalle.setOficinaRegistral(rs.getString("NOMBRE"));
				detalle.setNroPartida(rs.getString("NUM_PARTIDA"));
				detalle.setEstado(rs.getString("ESTADO"));
				detalle.setLibro(rs.getString("COD_LIBRO"));
				detalle.setDepartamento(rs.getString("DEPARTAMENTO"));
				detalle.setProvincia(rs.getString("PROVINCIA"));
				detalle.setDistrito(rs.getString("DISTRITO"));

				vDetalle.add(detalle);
			}
			
			list = new BuscaPartidaDetallePropiedadInmuebleBean[vDetalle.size()];
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