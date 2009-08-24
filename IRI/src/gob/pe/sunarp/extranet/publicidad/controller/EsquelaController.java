package gob.pe.sunarp.extranet.publicidad.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import com.sun.media.jai.codec.MemoryCacheSeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.util.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.*;
import javax.media.jai.TiledImage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class EsquelaController extends ControllerExtension {

	private String thisClass = EsquelaController.class.getName() + ".";

	public EsquelaController() {
		super();
		addState(new State("mostrarEsquela", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("verEsquela", "Ventana de Busq. x Apellidos y Nombres."));
		setInitialState("mostrarEsquela");
	}


	public String getTitle() {
		return new String("EsquelaController");
	}

	protected ControllerResponse runVerEsquelaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
				
		HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
		java.io.BufferedOutputStream out = null;
		
		try{
			init(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			String refNumTitu = request.getParameter("refnumtitu");
			String tipoEsquela = request.getParameter("tipoesquela");
			String areaRegId = request.getParameter("arearegid");
			
			int pagina = 1; //primera pagina del titulo
			
			String xp = request.getParameter("pagina");
			if (xp!=null)
				{
					try{
						pagina = Integer.parseInt(xp);
					} catch (Throwable tt)
					{
					}
				}
			
			if(refNumTitu == null || refNumTitu.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Número de Referencia de Título a buscar", "errorTitulo");
				
			if(tipoEsquela == null || tipoEsquela.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Tipo de Esquela a buscar", "errorTitulo");
				
			if(areaRegId == null || areaRegId.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el ID del Area Registral a buscar", "errorTitulo");
			
			if (isTrace(this)) trace("REF_NUMTITU/TIPO_ESQ/AREA_REG_ID: " + refNumTitu + "/" + tipoEsquela + "/" + areaRegId, request);	
			
			java.sql.Blob blob = Generales.getEsquela(refNumTitu, tipoEsquela, areaRegId, dconn);

			res.setContentType("image/gif");
			if (isTrace(this)) trace("Decodificando el formato Tiff, de la página" + pagina, request);
	
	/*
			Tiff tiff = new Tiff();
			tiff.readInputStream(blob.getBinaryStream()); */
			//Object image = PreparaImagen.getInstance().leerTIFF(blob.getBytes(1, (int)blob.length()), 1, 0);
			pagina--;
			Object image = PreparaImagen.getInstance().leerTIFF(blob.getBytes(1, (int)blob.length()), 1, pagina);
			
			//pagina--;
			if (isTrace(this)) System.out.println("pagina = "+pagina);
//			Image image = tiff.getImage(pagina);
	
			int imageWidth = PreparaImagen.getInstance().getWidth(image);
			int imageHeight = PreparaImagen.getInstance().getHeight(image);
				
/*			GifImage gifImage = new GifImage(imageWidth, imageHeight);
			Graphics2D graphics2D = (Graphics2D)gifImage.getGraphics();*/
//			Graphics2D graphics2D = image.createGraphics();
	
/*			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					// creamos el Giff y le sacamos un grephics
			graphics2D.drawImage(image, 0, 0, imageWidth, imageHeight, null);
					// dibujamos el tiff decodificado en el grphics del gif
		*/		
			if (isTrace(this)) trace("Codificando y enviando al usuario", request);
				
			out = new java.io.BufferedOutputStream(res.getOutputStream());
			PreparaImagen.getInstance().mandarImagenAStream(out, image);
			/*gifImage.encode(out);*/
//			javax.media.jai.JAI.create("encode", image, out, "PNG");
	
			if (isTrace(this)) trace("Terminado codificacion y envio al usuario", request);
	
			out.close();
		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			response.setCustomResponse(true);
			response.setStyle(null);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runMostrarEsquelaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			//validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			String refNumTitu = request.getParameter("refnumtitu");
			String tipoEsquela = request.getParameter("tipoesquela");
			String areaRegId = request.getParameter("arearegid");
			
			if(refNumTitu == null || refNumTitu.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Numero de Referencia de Titulo a buscar", "errorTitulo");
				
			if(tipoEsquela == null || tipoEsquela.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Tipo de Esquela a buscar", "errorTitulo");
				
			if(areaRegId == null || areaRegId.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el ID del Area Registral a buscar", "errorTitulo");

			if (isTrace(this)) trace("Validando que haya imagen para REF_NUMTITU/TIPO_ESQ/AREA_REG_ID: " + refNumTitu + "/" + tipoEsquela + "/" + areaRegId, request);	
			
			java.sql.Blob blob = Generales.getEsquela(refNumTitu, tipoEsquela, areaRegId, dconn);



			String hayImagen = null;
			
			
			if(blob != null)
			{
				hayImagen="X";
				
				//verificar contenido de blob
				java.io.InputStream ii = null;
				ii = blob.getBinaryStream();
				if(ii.read() < 0)
					hayImagen=null;
					
				try{				
						ii.close();
					} catch (Throwable tt)
						{ }					
			}
			
			req.setAttribute("hayImagen", hayImagen);
			
			//ver cuántas páginas tiene la esquela
			int totalpags=0;
			if (hayImagen!=null)
				{
					MemoryCacheSeekableStream stream = new MemoryCacheSeekableStream(blob.getBinaryStream());
					totalpags = TIFFDirectory.getNumDirectories(stream);
/*					Tiff tiff = new Tiff();
					tiff.readInputStream(); 
					totalpags = tiff.getPageCount();*/
					if (isTrace(this)) trace("esquela tiene " +totalpags + " páginas",request);
				}
				
			req.setAttribute("refnumtitu", refNumTitu);
			req.setAttribute("tipoesquela", tipoEsquela);
			req.setAttribute("arearegid", areaRegId);
			req.setAttribute("totalpags",String.valueOf(totalpags));
			response.setStyle("mostrarEsquela");
			
		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
}

