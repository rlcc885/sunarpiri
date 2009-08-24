package gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre;

import gob.pe.sunarp.extranet.dbobj.DboActoSolicitudInscripcion;
import gob.pe.sunarp.extranet.dbobj.DboTmDepartamento;
import gob.pe.sunarp.extranet.dbobj.DboTmProvincia;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.ActoSolicitudInscripcion;
import gob.pe.sunarp.extranet.util.ComboBean;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.Value05Bean;
import java.sql.Connection;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;

public abstract class SolicitudInscripcion {

	public static ArrayList getActos() {
		ActoSolicitudInscripcion bean = null;
		DboActoSolicitudInscripcion dboActo = null;
		DboActoSolicitudInscripcion dboAux = null;
		DBConnectionFactory pool = null;
		DBConnection dconn = null;
		StringBuffer fieldSB = null;
		Connection conn = null;
		ArrayList resultado = null;
		ArrayList actos = null;
		int size = 0;
		
		try {
			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
			
			conn.setAutoCommit(false);
			dconn = new DBConnection(conn);
			dboActo = new DboActoSolicitudInscripcion(dconn);

			fieldSB = new StringBuffer();
			
			fieldSB.append(DboActoSolicitudInscripcion.CAMPO_AREA_REG_ID);
			fieldSB.append("|");
			fieldSB.append(DboActoSolicitudInscripcion.CAMPO_COD_ACTO);
			fieldSB.append("|");
			fieldSB.append(DboActoSolicitudInscripcion.CAMPO_COD_LIBRO);
			fieldSB.append("|");
			fieldSB.append(DboActoSolicitudInscripcion.CAMPO_COD_RUBRO);
			
			dboActo.setFieldsToRetrieve(fieldSB.toString());
			resultado = dboActo.searchAndRetrieveList();
			size = resultado.size();
			
			for (int i = 0; i < size; i++) {
				dboAux = (DboActoSolicitudInscripcion)resultado.get(i);
				bean = new ActoSolicitudInscripcion();
				
				bean.setCodigoArea(dboAux.getField(dboAux.CAMPO_AREA_REG_ID));
				bean.setCodigoActo(dboAux.getField(dboAux.CAMPO_COD_ACTO));
				bean.setCodigoLibro(dboAux.getField(dboAux.CAMPO_COD_LIBRO));
				bean.setCodigoRubro(dboAux.getField(dboAux.CAMPO_COD_RUBRO));
				
				actos.add(bean);
			}
		}
		catch (Exception e) {
				e.printStackTrace();
		}
		
		return actos;
	}

	public static ArrayList getDptoProvTipDocu () {
		DBConnectionFactory pool = null;
		DBConnection dconn = null;
		DboTmDepartamento dboDpto = null;
		DboTmDepartamento d = null;
		DboTmProvincia dboProv = null;
		DboTmProvincia dp = null;
		Value05Bean b5 = null;
		Connection conn = null;
		ComboBean cboBean = null;
		ArrayList dptoAux = null;
		ArrayList provAux = null;
		ArrayList dptos = null;
		ArrayList provs = null;
		ArrayList respuesta = null;
		int sizeDpto = 0;
		int sizeProv = 0;
		
		try {
			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
				
			conn.setAutoCommit(false);
			dconn = new DBConnection(conn);

			dboDpto = new DboTmDepartamento(dconn);
			dboProv = new DboTmProvincia(dconn);	
			respuesta = new ArrayList();
			dptos = new ArrayList();
			provs = new ArrayList();
	
			dboDpto.setField(DboTmDepartamento.CAMPO_PAIS_ID, "01");
			dboDpto.setField(DboTmDepartamento.CAMPO_ESTADO, "1");
	
			dptoAux = dboDpto.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
			sizeDpto = dptoAux.size();
				
			for (int i = 0; i < sizeDpto; i++) {
				d = (DboTmDepartamento) dptoAux.get(i);
				cboBean = new ComboBean();
		
				cboBean.setCodigo(d.getField(DboTmDepartamento.CAMPO_DPTO_ID));
				cboBean.setDescripcion(d.getField(DboTmDepartamento.CAMPO_NOMBRE));
		
				dptos.add(cboBean);
					
				//buscar las provincias del departamento
				dboProv.clearAll();
				dboProv.setField(DboTmProvincia.CAMPO_DPTO_ID, cboBean.getCodigo());
				dboProv.setField(DboTmProvincia.CAMPO_ESTADO, "1");
		
				provAux = dboProv.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
				sizeProv = provAux.size();
		
				for (int w = 0; w < sizeProv; w++) {
					dp = (DboTmProvincia)provAux.get(w);
					b5 = new Value05Bean();
		
					b5.setValue01(dp.getField(DboTmProvincia.CAMPO_PROV_ID));
					b5.setValue02(dp.getField(DboTmProvincia.CAMPO_NOMBRE));
					b5.setValue03(cboBean.getCodigo());
		
					provs.add(b5);
				}
			}
			
			respuesta.add(dptos);
			respuesta.add(provs);
			respuesta.add(Tarea.getComboTiposDocumento(dconn));
		}
		catch (Throwable th) {
			th.printStackTrace();
		}
		
		return respuesta;
	}
}

