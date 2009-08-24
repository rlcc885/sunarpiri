package gob.pe.sunarp.extranet.framework;

import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.common.RegisteredError;
import gob.pe.sunarp.extranet.common.SystemResources;
import gob.pe.sunarp.extranet.common.cm.CMConnectionFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import gob.pe.sunarp.extranet.framework.xml.*;

import gob.pe.sunarp.extranet.framework.tam.*;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.publicidad.certificada.MonitorCargaLaboral;
import gob.pe.sunarp.extranet.reportegeneral.bean.ConsolaCentral;
import gob.pe.sunarp.extranet.util.*;

public class InitSystemResources extends HttpServlet {

	private MailJob jobWeb = null;
	private MailJob job = null;
	
	public void init(javax.servlet.ServletConfig config)
		throws javax.servlet.ServletException {
		super.init(config);
		if (Loggy.isTrace(this)) System.out.println("INICIANDO SYSTEM RESOURCES");
		initSR();
	}

	private boolean initSR() {
		Connection conn = null;
		DBConnectionFactory pool = null;

		try {

			//--
			/*
			29agosto2002 - Se agrega inicialización de SecAdmin
			Se lee archivo webappResources.xml para obtener parámetros
			*/
			String rp = getServletContext().getRealPath(java.io.File.separator);
			//hasta aqui: rp = D:\wsad\workspace\SunarpExtranetWeb\webApplication\			
			String archivoWebappR = getServletConfig().getInitParameter("webappFile");
			XWebappResources xw = new XWebappResources(rp + archivoWebappR);
			
			//lectura de direcciones Visa
			XDirVisa xdirvisa = xw.getDirVisa();

			DirVisa dirvisa = DirVisa.getInstance();
			
			dirvisa.setDir1Desa(xdirvisa.getDir1Desa());
			dirvisa.setDir2Desa(xdirvisa.getDir2Desa());
			dirvisa.setDir1Prod(xdirvisa.getDir1Prod());
			dirvisa.setDir2Prod(xdirvisa.getDir2Prod());
			//hphp:2009/09/06
			//comentado para pruebas
			//descomentar cuando pase a produccion
			dirvisa.setDirDesa(xdirvisa.getDirDesa());
			
			
			//lectura de propiedades generales del sistema
			XPropiedades xprop = xw.getPropiedades();
			
			Propiedades propiedades = Propiedades.getInstance();
			
			String xp;
			int nxp;
			
			//flags
			xp = xprop.getFlag01();
			if (xp.equals("true"))
				propiedades.setFlagGrabaClave(true);
			else
				propiedades.setFlagGrabaClave(false);
				
			xp = xprop.getFlag02();
			if (xp.equals("true"))
				propiedades.setFlagProduccion(true);
			else
				propiedades.setFlagProduccion(false);
				
			xp = xprop.getValue12();
			if (xp.equals("true"))
				propiedades.setFlagTransaccion(true);
			else
				propiedades.setFlagTransaccion(false);
				
								
			//values
			propiedades.setLineasPorPag(Integer.parseInt(xprop.getValue01()));

			//-valores para envio de mail			
			propiedades.setSendMailSMTPServer(xprop.getValue02());
			propiedades.setSendMailUser(xprop.getValue03());
			propiedades.setSendMailPassword(xprop.getValue04());
			xp = xprop.getValue05();
			nxp = Integer.parseInt(xp);
			propiedades.setSendMailTiempo(nxp);
			
			
			xp = xprop.getValue20();
			nxp = Integer.parseInt(xp);
			propiedades.setSendMailTiempoNoWeb(nxp);

			xp = xprop.getValue21();
			nxp = Integer.parseInt(xp);
			propiedades.setMailActivoDias(nxp);

			xp = xprop.getValue22();
			nxp = Integer.parseInt(xp);
			propiedades.setMailActivoDiasNoWeb(nxp);
			
			//hphp:30/10
			xp = xprop.getValue23();
			propiedades.setRutaModeloSobre(xp);
			
			//-vehicular
			propiedades.setSendMailPersonasRPV(xprop.getValue19());
			
			//hphp:2003/09/06
			//comentado porque no levantaba el tam desde la Pc de Kuma
			//descomentar para pasar a produccion
			
			XTam xtam = xw.getTam();

			//java.io.File f = new java.io.File(rp + "WEB-INF/config/pd.properties");
			System.out.println(rp + xtam.getRutaConfigFile());
			java.io.File f = new java.io.File(rp + xtam.getRutaConfigFile());
			String rutaConfigFile = "file:///" + f.getAbsolutePath();
			//SAUL
			//SE ACTUALIZA EL METODO INIT DE TAM PARA PERMITIR EL INGRESO DE LOS USUARIO DE ADMINISTRACION Y AUTORIZACION 
			SecAdmin.getInstance().init(
				xtam.getRuta(),
				xtam.getLocaleIdioma(),
				xtam.getLocalePais(),
				xtam.getUsuario(),
				xtam.getPassword(),
				xtam.getUsuarioAutorizacion(),
				xtam.getPasswordAutorizacion(),				
				rutaConfigFile);
			
			//System.out.println("ruta es " + xtam.getRuta());
			/*
			SecAdmin.getInstance().init(
				"extranet/SNRPEXT01",
				"SPANISH",
				"PE",
				"aplicativoextranet",
				"appext01",
				"file:///D:\\WSAD\\workspace\\SunarpExtranetWeb\\webApplication\\WEB-INF\\config\\pd.properties");
			*///System Resources
			String root = getServletContext().getResource("/").getFile();
			SystemResources sr = SystemResources.getInstance();
			sr.setEmailFrom(xprop.getValue06());
			sr.setEmailToDefault(xprop.getValue07());
			File templatesDir = new File(root, xprop.getValue08());
			if (!templatesDir.exists()) 
				templatesDir.mkdirs();
			sr.setTemplatesDirectory(templatesDir.getAbsolutePath());
			sr.setSmtp(xprop.getValue02());
		
			File logFile = new File(root, xprop.getValue09());
			File logDir  = logFile.getParentFile();
			if (!logDir.exists()) 
				logDir.mkdirs();
			sr.setLogFile(logFile.getAbsolutePath());
			sr.setSysMinLevel(Integer.parseInt(xprop.getValue10()));	
			sr.setSubjectIncludeErr(xprop.getSubjectIncludeErr());
			sr.setSubjectIncludeReq(xprop.getSubjectIncludeReq());
			sr.setSubjectIncludeUsr(xprop.getSubjectIncludeUsr());
			
			propiedades.setMaxResultadosBusqueda(Integer.parseInt(xprop.getValue11()));
			
			//valores CONTACTENOS
			propiedades.setContactenosEmailsConsultas(xprop.getValue13());
			propiedades.setContactenosEmailsQuejas(xprop.getValue14());
			propiedades.setContactenosEmailsObservs(xprop.getValue15());
			
			//valores imágenes
			propiedades.setImageScaleAsiento(Double.parseDouble(xprop.getValue16()));
			propiedades.setImageScaleFicha(Double.parseDouble(xprop.getValue17()));
			propiedades.setImageScaleFolio(Double.parseDouble(xprop.getValue18()));
			
			
			// Datos Consola Central
			XConsolaCentral xcc = xw.getConsolaCentral();
			if (Loggy.isTrace(this)) 
				System.out.println("Cargando datos para la Consola Central");
			ConsolaCentral.getInstance().init(xcc.getErrorManejados(), xcc.getOficinasDB());



			// Content Manager
			//COMENTADO 
			//FALTA LA CX CON EL CM 8.3
                        /*
			XPoolCM xcm = xw.getPoolCM();
			if (xcm.getEnabled()) {
				if (Loggy.isTrace(this)) System.out.println("Iniciando CM al libServer" + xcm.getLibserver() + " con usuario: " + xcm.getUser());


					System.out.println("*getLibserver**** " + xcm.getLibserver());
					System.out.println("*getUser********* " + xcm.getUser());
					System.out.println("*getPass********* " + xcm.getPass());
					System.out.println("*getPoolSize***** " + xcm.getPoolSize());
					System.out.println("*getExpiryTime*** " + xcm.getExpiryTime());
					System.out.println("*getTimeOut****** " + xcm.getTimeOut());


				try {
				CMConnectionFactory.getInstance(Constantes.CM_ID_WEB).init(
					xcm.getLibserver(),
					xcm.getUser(),
					xcm.getPass(),
					xcm.getPoolSize(),
					xcm.getExpiryTime(),
					xcm.getTimeOut()
				);
				}
				catch (Exception e) {e.printStackTrace();}
			}*/
				
			/**
			 * jacaceres - CM8 - 22/02/07
			 * Iniciar el Content Manager 8 con los parametros del XML 
                        */		
			XPoolCM xcm = xw.getPoolCM();
			if (xcm.getEnabled()) {
				if (Loggy.isTrace(this)) System.out.println("Iniciando CM al libServer " + xcm.getLibserver() + " con usuario: " + xcm.getUser());
			 
					System.out.println("*getLibserver**** " + xcm.getLibserver());
					System.out.println("*getUser********* " + xcm.getUser());
					System.out.println("*getPass********* " + xcm.getPass());
					System.out.println("*getPoolSize***** " + xcm.getPoolSize());
					System.out.println("*getExpiryTime*** " + xcm.getExpiryTime());
					System.out.println("*getTimeOut****** " + xcm.getTimeOut());
			 				
				try {
					CMConnectionFactory.getInstance().init(
							xcm.getLibserver(),
							xcm.getUser(),
							xcm.getPass(),
							xcm.getPoolSize(),
							xcm.getExpiryTime(),
							xcm.getTimeOut()
						);
					System.out.println("InitSystemResources - Se conecto al Content Manager 8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			/***** jacaceres - CM8 *****/		

			//inicializar Pools de conexiones a la bases de datos
			if (Loggy.isTrace(this)) System.out.println("Inicializando conexiones a bases de datos");
			DBConnectionFactory ipool = DBConnectionFactory.getInstance();
			DBConnectionFactoryV vpool = DBConnectionFactoryV.getInstance();
			
			XConexionesBD xbd = xw.getConexionesBD();
			XBase[] arrw = xbd.getBases();
			for (int w = 0; w< arrw.length; w++)
			{ 
				int    codigo   = arrw[w].getCodigo();
				String driver   = arrw[w].getDriver();
				String url      = arrw[w].getUrl();
				String user     = arrw[w].getUser();
				String password = arrw[w].getPassword();
				int max         = arrw[w].getMaxConnections();
				long expiry     = arrw[w].getExpiryTime();
				long timeout    = arrw[w].getTimeOut();

				switch (codigo)				
				{
					case 1:
						if (propiedades.getFlagProduccion()==true)					
							ipool.init(driver,url,user,password,max,expiry,timeout);
						break;
					case 2:
						if (propiedades.getFlagProduccion()==false)
							ipool.init(driver,url,user,password,max,expiry,timeout);
						break;
					case 3:
						if (propiedades.getFlagProduccion()==true)	
							vpool.init(driver,url,user,password,max,expiry,timeout);
						break;
					case 4:
						if (propiedades.getFlagProduccion()==false)
							vpool.init(driver,url,user,password,max,expiry,timeout);
						break;						
				}//switch

			}//for
			

			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();

			//registrar errores del sistema
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select error_codigo, err_descripcion, err_level, err_mail from TM_ERROR");
			
			while (rs.next()) 
			{
				RegisteredError re = new RegisteredError();
				re.setErrorCode(rs.getString(1));
				re.setMessage(rs.getString(2));
				re.setLevel((int)rs.getLong(3));
				re.setEmail(rs.getLong(4) == 1);
				
				sr.addRegisteredError(re);
			}
			stmt.close();					
			
			// revisar disponibilidad de Registradores
			// comentar hasta pasar a produccion publicidad certificada
			MonitorCargaLaboral.getInstance().setDconn(new DBConnection(conn));
			MonitorCargaLaboral.getInstance().revisarDisponibilidadRegistradores();
			MonitorCargaLaboral.getInstance().cargarRegistradores();
			
if (propiedades.getFlagProduccion()==true)
{
			/**
			 * inicio:dbravo:Comentado por recomendacion del Saul
			 */
			/*
				if (Loggy.isTrace(this)) 
					System.out.println("INICIALIZANDO THREADS!!!!");
			*/
				//HILO DE EJECUCION DE TRABAJO DE ENVIO DE MAILS DE LA EXTRANET
			/*
				jobWeb = new MailJob(true);
				jobWeb.start();
			*/	
			/**
			 * fin:dbravo:Comentado por recomendacion del Saul
			 */
				//HILO DE EJECUCION DE TRABAJO DE ENVIO DE MAILS DEL REPLICADOR
				//job = new MailJob(false);
				//job.start();
}
			
/*
Inicializar proceso de lectura de imagenes TIF
*/			

/*
	FileInputStream is=null; 
	try{
			File file = new File(rp+"/images/tinicio.tif");	
			long lo = file.length();
	
			is = new FileInputStream(file);
			int tamano = (int) lo;
			byte[] arrb = new byte[tamano];
			int rr = is.read(arrb,0,tamano);
			//if (isTrace(this)) System.out.println("tif_size="+rr+"_"+tamano);
			Tiff tiff = new Tiff();
			//if (isTrace(this)) System.out.println("Inicializar Programa de Lectura de Imágenes Tiffs");
			tiff.read(arrb);
			is.close();
		}
		catch(Throwable tt)
		{
			//if (isTrace(this)) System.out.println("_Res1_");
			//tt.printStackTrace();
		}
		finally 
		{
			try{
			is.close();
			} catch (Throwable e3) {}
		}
		
*/
propiedades.setRutaWebApplication(rp);
			
		//Recuperar comodin
			Statement stmt1 = conn.createStatement();
			StringBuffer query = new StringBuffer();
			query.append("SELECT co.contrato_id, li.linea_prepago_id FROM cuenta cu, contrato co, linea_prepago li WHERE cu.cuenta_id = co.cuenta_id AND cu.cuenta_id = li.cuenta_id AND cu.usr_id= '").append(Constantes.COMODIN_USUARIO).append("'");
			if (Loggy.isTrace(this)) 
				System.out.println("User comodin query: " + query.toString());			
			ResultSet rs1 = stmt1.executeQuery(query.toString());
			
			rs1.next();
			//Comodin.getInstance().setContratoAbono(1);
			//Comodin.getInstance().setLineaPrePago(2);
			Comodin.getInstance().setContratoAbono(rs1.getInt("contrato_id"));
			Comodin.getInstance().setLineaPrePago(rs1.getInt("linea_prepago_id"));
			//Constantes.COMODIN_CONTRATO_ABONAR = Integer.parseInt(rs1.getString("contrato_id"));
			//Constantes.COMODIN_LINEA_PREPAGO = Integer.parseInt(rs1.getString("linea_prepago_id"));			
			stmt1.close();					
			
			return true;
		} catch (Throwable t) {
			if (Loggy.isTrace(this)) System.out.println("NO SE PUDO INICIAR SYSTEM RESOURCES!!!!");
			System.err.println("NO SE PUDO INICIAR SYSTEM RESOURCES!!!!");
			t.printStackTrace();
			return false;
		} finally {
			if (pool != null) {
				pool.release(conn);
			}
		}
	}

	public void doPost(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException {

		doGet(request, response);
	}

	public void destroy() {
		super.destroy();
		SystemResources.getInstance().close();
		try {
			SecAdmin.getInstance().close();
		} catch (Throwable t) {
		}
		try {
			/**
			 * jacaceres - CM8 - 22/02/07
			 */
			//CMConnectionFactory.getInstance(Constantes.CM_ID_WEB).close();
			CMConnectionFactory.getInstance().close();
			/**** jacaceres - CM8 ****/
		} catch (Throwable t) {
		}
		try {
			DBConnectionFactory.getInstance().closePool();
		} catch (Throwable t) {
		}
		try {
			DBConnectionFactoryV.getInstance().closePool();
		} catch (Throwable t) {
		}
		try {
			if(jobWeb != null)
				jobWeb.interrupt();
		} catch (Throwable t) {
		}
		try {
			if(job != null)
				job.interrupt();
		} catch (Throwable t) {
		}
	}

	public void doGet(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException {

		destroy();
		if (initSR()) {
			getServletContext().getRequestDispatcher("initResources.jsp").forward(
				request,
				response);
		} else {
			getServletContext().getRequestDispatcher("initResourcesKO.jsp").forward(
				request,
				response);
		}
	}
}