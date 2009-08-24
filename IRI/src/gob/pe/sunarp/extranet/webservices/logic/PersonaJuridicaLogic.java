package gob.pe.sunarp.extranet.webservices.logic;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaSolicitudInscripcionBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.webservices.WebServiceUtil;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaCabeceraPersonaJuridicaBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaDetallePersonaJuridicaBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPJReqBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPersonaJuridicaBean;
import gob.pe.sunarp.extranet.webservices.exception.RecuperaDataException;
import gob.pe.sunarp.extranet.webservices.exception.VerificacionDataException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PersonaJuridicaLogic {

	private static final int COD_GLA = 3;
	private static final String FLAG_COBRO = "1";
	private static final String REMOTE_ADDR = "localhost";
	private static final String TIPO_OBJETO = "BusqXRazonSocial";

	public static PartidaXPersonaJuridicaBean buscaRazonSocial (BuscaPartidaXPJReqBean req) {
		LogAuditoriaSolicitudInscripcionBean logBean = null;
		PartidaXPersonaJuridicaBean bean = null;
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
			
					if (WebServiceUtil.validaPermisoUsuario(conn, usuario.getCuentaId(), Constantes.COD_SERVICIO_BUSQUEDA_X_PERSONA_JURIDICA)) {
						logBean = new LogAuditoriaSolicitudInscripcionBean();
						
						logBean.setUsuarioSession(usuario);
						logBean.setCodigoGLA(COD_GLA);//PONER CONSTANTE
						logBean.setCodigoServicio(Integer.parseInt(Constantes.COD_SERVICIO_BUSQUEDA_X_PERSONA_JURIDICA));//PONER CONSTANTE
						logBean.setFlagCobro(FLAG_COBRO);////
						logBean.setRemoteAddr(REMOTE_ADDR);///
				
						Transaction.getInstance().registraTransaccion(logBean, conn);
					}
				}

				bean = recuperaData(conn, req);
				
				WebServiceUtil.insertaConsulta(req.getCuo(), TIPO_OBJETO, (Serializable)bean);
			}
			else {
				bean = (PartidaXPersonaJuridicaBean)obj;
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

	private static PartidaXPersonaJuridicaBean getPagina(int nroPagina, PartidaXPersonaJuridicaBean bean) throws VerificacionDataException {
		BuscaPartidaDetallePersonaJuridicaBean[] detalle = null;
		BuscaPartidaDetallePersonaJuridicaBean[] nuevo = null;
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
				nuevo = new BuscaPartidaDetallePersonaJuridicaBean[size];
			else
				nuevo = new BuscaPartidaDetallePersonaJuridicaBean[Constantes.CANTIDAD_REGISTROS_POR_PAGINA];
			
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
			nuevo = new BuscaPartidaDetallePersonaJuridicaBean[size];
			
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

	private static void verificarData (BuscaPartidaXPJReqBean req) throws VerificacionDataException {
		if (req == null)
			throw new VerificacionDataException("Requerimiento inválido.");
		else if (req.getCodigoLibro() == null || req.getCodigoLibro().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de libro no debe de ser nulo.");
		else if (req.getCodigoUsuario() == null || req.getCodigoUsuario().trim().length() == 0)
			throw new VerificacionDataException("El parámetro codigo de usuario no debe de ser nulo.");
		else if (req.getRazonSocial() == null || req.getRazonSocial().trim().length() == 0)
			throw new VerificacionDataException("El parámetro razón social no debe de ser nulo.");
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

	private static PartidaXPersonaJuridicaBean recuperaData (Connection conn, BuscaPartidaXPJReqBean req) throws RecuperaDataException {
		PartidaXPersonaJuridicaBean partida = null;
		BuscaPartidaCabeceraPersonaJuridicaBean cabecera = null;
		BuscaPartidaDetallePersonaJuridicaBean detalle = null;
		BuscaPartidaDetallePersonaJuridicaBean[] list = null;
		StringBuffer sbSQL = null;
		Vector vDetalle = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			sbSQL = new StringBuffer();

	        //sbSQL.append("SELECT  /*+ORDERED INDEX(PARTIDA SYS_C005644) */ 'PART' AS TIPO,PARTIDA.REFNUM_PART,REGIS_PUBLICO.REG_PUB_ID, REGIS_PUBLICO.NOMBRE, REGIS_PUBLICO.SIGLAS, ");
	        sbSQL.append("SELECT 'PART' AS TIPO,PARTIDA.REFNUM_PART,REGIS_PUBLICO.REG_PUB_ID, REGIS_PUBLICO.NOMBRE, REGIS_PUBLICO.SIGLAS, ");
	        sbSQL.append("REGIS_PUBLICO.USR_CREA, REGIS_PUBLICO.USR_ULT_MODIF, ");
	        sbSQL.append("OFIC_REGISTRAL.REG_PUB_ID AS OFRREGPUB, OFIC_REGISTRAL.OFIC_REG_ID, OFIC_REGISTRAL.NOMBRE AS OFRNOMBRE, ");
	        sbSQL.append("OFIC_REGISTRAL.USR_CREA AS OFRUSRCREA, OFIC_REGISTRAL.USR_ULT_MODIF AS USRULTMOD, RAZ_SOC_PJ.REFNUM_PART AS REFNUPART, ");
	        sbSQL.append("RAZ_SOC_PJ.NS_NOMBRE, RAZ_SOC_PJ.RAZON_SOC, RAZ_SOC_PJ.ESTADO as estadoInd, RAZ_SOC_PJ.SIGLAS AS RZSIGL, ");
	        sbSQL.append("RAZ_SOC_PJ.NU_FOJA, RAZ_SOC_PJ.NU_TOMO, RAZ_SOC_PJ.NU_ORIG_PART, ");
	        sbSQL.append("RAZ_SOC_PJ.AGNT_SYNC , PARTIDA.NUM_PARTIDA, PARTIDA.REG_PUB_ID AS PARTREGPUB, ");
	        sbSQL.append("PARTIDA.OFIC_REG_ID AS PARTOFRID, PARTIDA.COD_LIBRO, PARTIDA.AREA_REG_ID,PARTIDA.ESTADO AS estado, ");
	        sbSQL.append("PARTIDA.AGNT_SYNC AS PARTAGNSYN, PARTIDA.CO_LIBR_ORIG ");     
	        sbSQL.append("FROM RAZ_SOC_PJ, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad ");
	        sbSQL.append("WHERE RAZ_SOC_PJ.ESTADO = '1' AND ");
			sbSQL.append(" RAZ_SOC_PJ.RAZON_SOC LIKE '").append(req.getRazonSocial()).append("%' AND ");
	        sbSQL.append("PARTIDA.REFNUM_PART = RAZ_SOC_PJ.REFNUM_PART ");
			sbSQL.append(" and PARTIDA.ESTADO != '2' ");
	        sbSQL.append(" AND ");
			sbSQL.append(" partida.cod_libro = glad.cod_libro and ");
			sbSQL.append(" gla.cod_grupo_libro_area ='").append(req.getCodigoLibro()).append("' AND ");
			sbSQL.append(" gla.cod_grupo_libro_area = glad.cod_grupo_libro_area AND ");
		
			if (req.getZonasRegistrales() != null)
				sbSQL.append(" PARTIDA.REG_PUB_ID IN ").append(WebServiceUtil.getSedesSQL(req.getZonasRegistrales())).append(" AND ");
	 
	        sbSQL.append("OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID AND ");
	        sbSQL.append("OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID AND ");
	        sbSQL.append("REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
			sbSQL.append(" UNION ");
	        //sbSQL.append("SELECT  /*+ORDERED INDEX(PARTIDA SYS_C005644) */ 'PART' AS TIPO,PARTIDA.REFNUM_PART,REGIS_PUBLICO.REG_PUB_ID, REGIS_PUBLICO.NOMBRE, REGIS_PUBLICO.SIGLAS, ");
	        sbSQL.append("SELECT 'PART' AS TIPO,PARTIDA.REFNUM_PART,REGIS_PUBLICO.REG_PUB_ID, REGIS_PUBLICO.NOMBRE, REGIS_PUBLICO.SIGLAS, ");
	        sbSQL.append("REGIS_PUBLICO.USR_CREA, REGIS_PUBLICO.USR_ULT_MODIF, ");
	        sbSQL.append("OFIC_REGISTRAL.REG_PUB_ID AS OFRREGPUB, OFIC_REGISTRAL.OFIC_REG_ID, OFIC_REGISTRAL.NOMBRE AS OFRNOMBRE, ");
	        sbSQL.append("OFIC_REGISTRAL.USR_CREA AS OFRUSRCREA, OFIC_REGISTRAL.USR_ULT_MODIF AS USRULTMOD, RAZ_SOC_PJ.REFNUM_PART AS REFNUPART, ");
	        sbSQL.append("RAZ_SOC_PJ.NS_NOMBRE, RAZ_SOC_PJ.RAZON_SOC, RAZ_SOC_PJ.ESTADO as estadoInd, RAZ_SOC_PJ.SIGLAS AS RZSIGL, ");
	        sbSQL.append("RAZ_SOC_PJ.NU_FOJA, RAZ_SOC_PJ.NU_TOMO, RAZ_SOC_PJ.NU_ORIG_PART, ");
	        sbSQL.append("RAZ_SOC_PJ.AGNT_SYNC , PARTIDA.NUM_PARTIDA, PARTIDA.REG_PUB_ID AS PARTREGPUB, ");
	        sbSQL.append("PARTIDA.OFIC_REG_ID AS PARTOFRID, PARTIDA.COD_LIBRO, PARTIDA.AREA_REG_ID,PARTIDA.ESTADO AS estado, ");
	        sbSQL.append("PARTIDA.AGNT_SYNC AS PARTAGNSYN, PARTIDA.CO_LIBR_ORIG ");
	        sbSQL.append("FROM RAZ_SOC_PJ, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad ");
	        sbSQL.append("WHERE RAZ_SOC_PJ.ESTADO = '1' AND ");
			sbSQL.append("RAZ_SOC_PJ.siglas like '").append(req.getRazonSocial()).append("%' AND ");
	        sbSQL.append("PARTIDA.REFNUM_PART = RAZ_SOC_PJ.REFNUM_PART ");
			sbSQL.append(" and PARTIDA.ESTADO != '2' ");
	        sbSQL.append(" AND ");
			sbSQL.append(" partida.cod_libro = glad.cod_libro and ");
			sbSQL.append(" gla.cod_grupo_libro_area ='").append(req.getCodigoLibro()).append("' AND ");
			sbSQL.append(" gla.cod_grupo_libro_area = glad.cod_grupo_libro_area AND ");
	
			if (req.getZonasRegistrales() != null)
				sbSQL.append(" PARTIDA.REG_PUB_ID IN ").append(WebServiceUtil.getSedesSQL(req.getZonasRegistrales())).append(" AND ");
	
	        sbSQL.append("OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID AND ");
	        sbSQL.append("OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID AND ");
	        sbSQL.append("REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
			sbSQL.append(" UNION ");
	        //sbSQL.append("SELECT /*+ORDERED INDEX(TI SYS_C001712) INDEX(RES INDEX_RES_RZ_SOC) INDEX(DT INDX_REFNUM_FG_ACT)*/ ");
	        sbSQL.append("SELECT ");
	        sbSQL.append("'TITU' AS TIPO,RES.REFNUM_TITU,RP.REG_PUB_ID,RP.NOMBRE,RP.SIGLAS,null,TI.ANO_TITU,TI.NUM_TITU,null,OFR.NOMBRE, ");
	        sbSQL.append("null,null,0,0,RES.RAZ_SOC_RES,'1',null,null,null,null,null,null,TI.REG_PUB_ID,TI.OFIC_REG_ID,null,'22000',null,null,null ");
	        sbSQL.append("FROM RESERVA_RZ_SOC RES,TITULO TI, DETALLE_TITULO DT,OFIC_REGISTRAL OFR, REGIS_PUBLICO RP ");
	        sbSQL.append("WHERE ");
			sbSQL.append(" RES.RAZ_SOC_RES LIKE '").append(req.getRazonSocial()).append("%' AND ");
	
			if (req.getZonasRegistrales() != null)
				sbSQL.append(" TI.REG_PUB_ID IN ").append(WebServiceUtil.getSedesSQL(req.getZonasRegistrales())).append(" AND ");
	
	        sbSQL.append("TI.REFNUM_TITU = RES.REFNUM_TITU AND ");
	        sbSQL.append("DT.REFNUM_TITU = TI.REFNUM_TITU AND ");
	        sbSQL.append("DT.FG_ACTIVO = '1' AND ");
	        sbSQL.append("DT.ESTADO_TITULO_ID = 130 AND ");
	        sbSQL.append("ofr.reg_pub_id = TI.REG_PUB_ID AND ");
	        sbSQL.append("ofr.ofic_reg_id = TI.OFIC_REG_ID AND ");
	        sbSQL.append("rp.reg_pub_id = ofr.reg_pub_id ");

			System.out.println("[QUERY]: " + sbSQL.toString());

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sbSQL.toString());

			partida = new PartidaXPersonaJuridicaBean();
			cabecera = new BuscaPartidaCabeceraPersonaJuridicaBean();
			vDetalle = new Vector();
			
			cabecera.setCodigoUsuario(req.getCodigoUsuario());
			cabecera.setCostoServicio("");
			cabecera.setRazonSocial(req.getRazonSocial());
			cabecera.setZonasRegistrales(req.getZonasRegistrales());
			cabecera.setCodInstitucion(req.getCodInstitucion());
			cabecera.setCodServicio(req.getCodServicio());
			cabecera.setDescInstitucion(req.getDescInstitucion());
			cabecera.setCuo(req.getCuo());

			while (rs.next()) {
				detalle = new BuscaPartidaDetallePersonaJuridicaBean();

				detalle.setZonaRegistral(rs.getString("REG_PUB_ID"));
				detalle.setOficinaRegistral(rs.getString("OFIC_REG_ID"));
				detalle.setNroPartida(rs.getString("NUM_PARTIDA"));
				detalle.setEstado(rs.getString("ESTADO"));
				detalle.setRuc("007-TEMP");
				detalle.setRazonSocial(rs.getString("RAZON_SOC"));
				detalle.setLibro(rs.getString("COD_LIBRO"));

				vDetalle.add(detalle);
			}
			
			list = new BuscaPartidaDetallePersonaJuridicaBean[vDetalle.size()];
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