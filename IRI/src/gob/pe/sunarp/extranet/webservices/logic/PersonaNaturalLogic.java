package gob.pe.sunarp.extranet.webservices.logic;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaSolicitudInscripcionBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.webservices.WebServiceUtil;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaCabeceraPersonaNaturalBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaDetallePersonaNaturalBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPNReqBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPersonaNaturalBean;
import gob.pe.sunarp.extranet.webservices.exception.RecuperaDataException;
import gob.pe.sunarp.extranet.webservices.exception.VerificacionDataException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PersonaNaturalLogic {

	private static final int COD_GLA = 3;
	private static final String FLAG_COBRO = "1";
	private static final String REMOTE_ADDR = "localhost";
	private static final String TIPO_OBJETO = "BusqXPersonaNatural";

	public static PartidaXPersonaNaturalBean buscaPersonaNatural (BuscaPartidaXPNReqBean req) {
		LogAuditoriaSolicitudInscripcionBean logBean = null;
		PartidaXPersonaNaturalBean bean = null;
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
			
					if (WebServiceUtil.validaPermisoUsuario(conn, usuario.getCuentaId(), Constantes.COD_SERVICIO_BUSQUEDA_X_PERSONA_NATURAL)) {
						logBean = new LogAuditoriaSolicitudInscripcionBean();
						
						logBean.setUsuarioSession(usuario);
						logBean.setCodigoGLA(COD_GLA);//PONER CONSTANTE
						logBean.setCodigoServicio(Integer.parseInt(Constantes.COD_SERVICIO_BUSQUEDA_X_PERSONA_NATURAL));//PONER CONSTANTE
						logBean.setFlagCobro(FLAG_COBRO);////
						logBean.setRemoteAddr(REMOTE_ADDR);///
				
						Transaction.getInstance().registraTransaccion(logBean, conn);
					}
				}

				bean = recuperaData(conn, req);
				
				WebServiceUtil.insertaConsulta(req.getCuo(), TIPO_OBJETO, (Serializable)bean);
			}
			else {
				bean = (PartidaXPersonaNaturalBean)obj;
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

	private static PartidaXPersonaNaturalBean getPagina(int nroPagina, PartidaXPersonaNaturalBean bean) throws VerificacionDataException {
		BuscaPartidaDetallePersonaNaturalBean[] detalle = null;
		BuscaPartidaDetallePersonaNaturalBean[] nuevo = null;
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
				nuevo = new BuscaPartidaDetallePersonaNaturalBean[size];
			else
				nuevo = new BuscaPartidaDetallePersonaNaturalBean[Constantes.CANTIDAD_REGISTROS_POR_PAGINA];
			
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
			nuevo = new BuscaPartidaDetallePersonaNaturalBean[size];
			
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

	private static void verificarData (BuscaPartidaXPNReqBean req) throws VerificacionDataException {
		if (req == null)
			throw new VerificacionDataException("Requerimiento inválido.");
		else if (req.getCodigoLibro() == null || req.getCodigoLibro().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de libro no debe de ser nulo.");
		else if (req.getCodigoUsuario() == null || req.getCodigoUsuario().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de usuario no debe de ser nulo.");
		else if (req.getApeMaterno() == null || req.getApeMaterno().trim().length() == 0)
			throw new VerificacionDataException("El parámetro apellido materno no debe de ser nulo.");
		else if (req.getApePaterno() == null || req.getApePaterno().trim().length() == 0)
			throw new VerificacionDataException("El parámetro apellido paterno no debe de ser nulo.");
		//else if (req.getNombre() == null || req.getNombre().trim().length() == 0)
			//throw new VerificacionDataException("El parámetro nombre no debe de ser nulo.");
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

	private static PartidaXPersonaNaturalBean recuperaData (Connection conn, BuscaPartidaXPNReqBean req) throws RecuperaDataException {
		PartidaXPersonaNaturalBean partida = null;
		BuscaPartidaCabeceraPersonaNaturalBean cabecera = null;
		BuscaPartidaDetallePersonaNaturalBean detalle = null;
		BuscaPartidaDetallePersonaNaturalBean[] list = null;
		StringBuffer sbSQL = null;
		Vector vDetalle = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			sbSQL = new StringBuffer();

			if (req.getApeMaterno() != null) {
				//sbSQL.append("SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
				sbSQL.append("SELECT ");
			}
			else {
				//sbSQL.append("SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
				sbSQL.append("SELECT ");
			}

			sbSQL.append(" PARTIDA.ESTADO as estado, partida.reg_pub_id, ");
			sbSQL.append(" partida.refnum_part,  partida.cod_libro,     partida.num_partida,");
			sbSQL.append(" regis_publico.siglas, ofic_registral.nombre, prtc_nat.ape_pat,");
			sbSQL.append(" prtc_nat.ape_mat,     prtc_nat.nombres,      prtc_nat.nu_doc_iden,");
			sbSQL.append(" prtc_nat.ti_doc_iden, ind_prtc.cod_partic,   ind_prtc.estado AS estadoPartic");

			sbSQL.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla, grupo_libro_area_det glad ");
			sbSQL.append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ");
			sbSQL.append(" AND ind_prtc.refnum_part = partida.refnum_part ");
			sbSQL.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
			sbSQL.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
			sbSQL.append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ");
			sbSQL.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
			sbSQL.append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
			sbSQL.append(" and ape_pat like '").append(req.getApePaterno()).append("%' ");
			sbSQL.append(" and PARTIDA.ESTADO != '2' ");

			if (req.getApeMaterno() != null) {
				sbSQL.append(" and ape_mat like '").append(req.getApeMaterno()).append("%'");
			}
			else {
				sbSQL.append(" and nombres like '").append(req.getNombre()).append("%'");
			}

			if (req.getZonasRegistrales() != null)
				sbSQL.append(" AND partida.reg_pub_id IN ").append(WebServiceUtil.getSedesSQL(req.getZonasRegistrales()));

			sbSQL.append(" AND partida.cod_libro = glad.cod_libro  ");
			sbSQL.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			sbSQL.append(" and gla.cod_grupo_libro_area ='").append(req.getCodigoLibro()).append("' ");

			// Incluir Inactivos
			sbSQL.append(" AND IND_PRTC.ESTADO = '1'");

			sbSQL.append(" ORDER BY prtc_nat.ape_pat, prtc_nat.ape_mat, prtc_nat.nombres, ind_prtc.estado DESC, PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida  ");

			System.out.println("[QUERY]: " + sbSQL.toString());

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sbSQL.toString());

			partida = new PartidaXPersonaNaturalBean();
			cabecera = new BuscaPartidaCabeceraPersonaNaturalBean();
			vDetalle = new Vector();
			
			cabecera.setCodigoUsuario(req.getCodigoUsuario());
			cabecera.setCostoServicio("");
			cabecera.setApeMaterno(req.getApeMaterno());
			cabecera.setApePaterno(req.getApePaterno());
			cabecera.setNombre(req.getNombre());
			cabecera.setZonasRegistrales(req.getZonasRegistrales());
			cabecera.setCodInstitucion(req.getCodInstitucion());
			cabecera.setCodServicio(req.getCodServicio());
			cabecera.setDescInstitucion(req.getDescInstitucion());
			cabecera.setCuo(req.getCuo());

			while (rs.next()) {
				detalle = new BuscaPartidaDetallePersonaNaturalBean();

				detalle.setZonaRegistral(rs.getString("REG_PUB_ID"));
				detalle.setOficinaRegistral(rs.getString("NOMBRE"));
				detalle.setNroPartida(rs.getString("NUM_PARTIDA"));
				detalle.setEstado(rs.getString("ESTADO"));
				detalle.setLibro(rs.getString("COD_LIBRO"));
				detalle.setNombreParticipante(getNombreParticipante(rs.getString("NOMBRES"), rs.getString("APE_PAT"), rs.getString("APE_MAT")));

				vDetalle.add(detalle);
			}
			
			list = new BuscaPartidaDetallePersonaNaturalBean[vDetalle.size()];
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
	
	public static String getNombreParticipante(String nombres, String apPaterno, String apMaterno) {
		StringBuffer sb = new StringBuffer();
		
		if (apPaterno != null)
			sb.append(apPaterno.trim()).append(Constantes.ESPACIO);
		
		if (apMaterno != null)
			sb.append(apMaterno.trim());

		if (sb.toString().length() > 0)
			sb.append(Constantes.COMA).append(Constantes.ESPACIO);

		if (nombres != null)
			sb.append(nombres.trim());
			
		return sb.toString();
	}
}