package gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre;

//paquetes del sistema
import javax.media.jai.TiledImage;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.misc.*;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.session.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.LoggeableException;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.common.SystemResources;
import gob.pe.sunarp.extranet.common.cm.CMProcessor;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.dbobj.DboMail;
import gob.pe.sunarp.extranet.dbobj.DboPartida;
import gob.pe.sunarp.extranet.reportes.beans.TransaccionBean;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.publicidad.bean.ImagenBean;
import gob.pe.sunarp.extranet.publicidad.certificada.Certificado;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.transaction.bean.*;
public class SolicitudBusquedaNacionaPJ extends ControllerExtension{
	
	public SolicitudBusquedaNacionaPJ() 
	{
		super();
		addState(new State("solicitarFormulario", "muestra formulario de busqueda"));
		setInitialState("solicitarFormulario");
		
	}
	
	public ControllerResponse runSolicitarFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		PreparedStatement pstmt = null;
		ResultSet rset = null;


		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			//Tarifario
			/*
			//obtener datos para comboBox
			DboTmLibro dbo3 = new DboTmLibro(dconn);
			StringBuffer sb = new StringBuffer();
			//-tomo libro
			java.util.ArrayList arreglo3 = new java.util.ArrayList();
			sb.delete(0,sb.length());
			sb.append(DboTmLibro.CAMPO_COD_LIBRO).append("|").append(DboTmLibro.CAMPO_DESCRIPCION).append("|");
			sb.append(DboTmLibro.CAMPO_AREA_REG_ID);
			dbo3.setFieldsToRetrieve(sb.toString());
			dbo3.setField(DboTmLibro.CAMPO_ESTADO, "1");
			java.util.ArrayList arr3 = dbo3.searchAndRetrieveList(DboTmLibro.CAMPO_DESCRIPCION);
			for (int i = 0; i < arr3.size(); i++) 
			{
				Value05Bean b = new Value05Bean();
				DboTmLibro d3 = (DboTmLibro) arr3.get(i);
				b.setValue01(d3.getField(DboTmLibro.CAMPO_COD_LIBRO));
				b.setValue02(d3.getField(DboTmLibro.CAMPO_DESCRIPCION));
				b.setValue03(d3.getField(DboTmLibro.CAMPO_AREA_REG_ID));
				arreglo3.add(b);
			}
			*/
			//fin Tarifario
			
		//Tarifario
			java.util.ArrayList arreglo3 = new java.util.ArrayList();
			
			StringBuffer quebusq = new StringBuffer();
			
			//Query busqueda de combo
			quebusq.append("SELECT l.cod_libro, l.descripcion, l.area_reg_id, glad.cod_grupo_libro_area ");
			quebusq.append("FROM tm_libro l, grupo_libro_area_det glad, tarifa t ");
			quebusq.append("WHERE l.cod_libro = glad.cod_libro AND glad.cod_grupo_libro_area = t.cod_grupo_libro_area  ");
			quebusq.append("AND t.servicio_id = ? ");
			pstmt = conn.prepareStatement(quebusq.toString());
			pstmt.setInt(1,Constantes.COD_SERVICIO_BUSQUEDANACIONAL);
			rset = pstmt.executeQuery();
			
			ArrayList resultado = new ArrayList();
			boolean encontro = false;
			while (rset.next())
			{
				Value05Bean b = new Value05Bean();
				b.setValue01(rset.getString("cod_libro"));
				b.setValue02(rset.getString("descripcion"));
				b.setValue03(rset.getString("area_reg_id"));
				b.setValue04(rset.getString("cod_grupo_libro_area"));
				arreglo3.add(b);
			}
		//fin Tarifario
			
			req.setAttribute("arr3", arreglo3);
			//Retirado por cambio de busqueda 10/10/2003
			//req.setAttribute("arrAreaRegistral", Tarea.getComboAreasRegistrales(dconn));
			//req.setAttribute("arrAreaLibro", Tarea.getComboAreaLibro(dconn,1));
			//Tarifario
			req.setAttribute("arrAreaLibro", Tarea.getComboAreaLibro(conn,Constantes.SERVICIO_CONSULTAR_PARTIDA));
			response.setStyle("formularioBusqueda");


		} //try
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	

	
}

