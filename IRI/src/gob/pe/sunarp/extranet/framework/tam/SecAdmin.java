package gob.pe.sunarp.extranet.framework.tam;

/*
17 agosto 2002 - 

Administracion de usuarios/grupos/ACLs del WebSeal

Notas:
	-Revisar documentacion del WebSeal y su instalacion en WSAD
	 antes de utilizar esta clase
	 
Para mayor referencia, revisar:

http://www.tivoli.com/support/public/Prodman/public_manuals/td/AccessManagerfore-business3.9.html

documentos:

Authorization Java Classes Developer's Reference
Administration Java Classes Developer's Reference
*/

import gob.pe.sunarp.extranet.framework.*;
//import gob.pe.sunarp.extranet.acceso.bean.PermisoBean;
import gob.pe.sunarp.extranet.util.*;
import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.dbobj.*;

//import java.io.*;
import java.net.*;
import java.util.*;

//APIs WebSeal - ADMINISTRATION
import com.tivoli.pd.jadmin.*;
import com.tivoli.pd.jazn.PDAuthorizationContext;
import com.tivoli.pd.jazn.PDLoginModule;
import com.tivoli.pd.jazn.PDPermission;
import com.tivoli.pd.jazn.PDPrincipal;
import com.tivoli.pd.jutil.*;
//import com.tivoli.pd.nls.pdbjamsg;

//APIs WebSeal - AUTHORIZATION

//import com.tivoli.mts.*;

//Apis seguridad - Autenticacion mediante LoginModule
import javax.security.auth.Subject;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

public class SecAdmin implements Constantes {

	//contexto
	private PDContext contexto = null;

	//CONTEXTO DE AUTORIZACION
	private PDAuthorizationContext contextoAutorizacion = null;	
	
	//instancia única
	private static SecAdmin secAdmin;

	//ruta inscrita en servidor tam
	private final String rutaTAM = "/WebSEAL/snrptaw01/interconexion";
	
	//constructor privado
	private SecAdmin() {
		super();
	}
	//getInstance
	public static SecAdmin getInstance() {
		if (secAdmin == null)
			secAdmin = new SecAdmin();
		return secAdmin;
	}

	//metodo para abrir la sesion con el TAM
	public void init(
		String ruta,
		String localeIdioma,
		String localePais,
		String usuario,
		String password,
		String usuarioAutorizacion,
		String passwordAutorizacion,
		String rutaConfigFile
		)
		throws Throwable {

		try {
			Propiedades p = Propiedades.getInstance();
			//if (p.getFlagProduccion()!=false)
			if (p.getFlagProduccion()==false)
			{
				if (Loggy.isTrace(this)) System.out.println("TAM*DUMMY ABIERTO");
				return;
			}
			
			//iniciar PDADMIN
			if (Loggy.isTrace(this)) System.out.println("TAM*Inicializando PDAdmin...");
			PDMessages mensajes = new PDMessages();
			//PDAdmin.initialize("extranet/taksam", mensajes);
			PDAdmin.initialize(ruta, mensajes);
			registraMensajes(mensajes);

			if (Loggy.isTrace(this)) 
				System.out.println("TAM*PDAMin inicializado? = " + PDAdmin.isInited());
			if (PDAdmin.isInited() == false)
				throw new CustomException(E08000_ERROR_ABRIENDO_SESION);

			//Iniciar contexto
			if (Loggy.isTrace(this)) System.out.println("TAM*Inicializando contexto...");
			//Locale ubicacion = new Locale("SPANISH", "PE");
			Locale ubicacion = new Locale(localeIdioma, localePais);
			//String adminName = "sec_master";
			//String adminPassword = "12345ar01";
			//URL configFileURL = new URL("file:///C:/am/pd.properties");
			URL configFileURL = new URL(rutaConfigFile);
			contexto =
				new PDContext(ubicacion, usuario, password.toCharArray(), configFileURL);
			if (Loggy.isTrace(this)) 
				System.out.println("TAM*Contexto Iniciado con el nombre              :" + contexto.toString());
			

			contextoAutorizacion = new PDAuthorizationContext(
					ubicacion,
					usuarioAutorizacion,
					passwordAutorizacion.toCharArray(),
					configFileURL);

			if (Loggy.isTrace(this)) 
				System.out.println("TAM*Contexto Autorizacion Iniciado con el nombre :" + contextoAutorizacion.toString());
			//SAUL - FIN
			
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08000_ERROR_ABRIENDO_SESION);
		} catch (Throwable t) {
			throw t;
		}
	}

	//cerrar sesion con TAM
	public void close() throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion() == false) 
			{
				if (Loggy.isTrace(this)) System.out.println("TAM*DUMMY CERRADO");
				return;
			}
			if (Loggy.isTrace(this)) System.out.println("TAM*Cerrando PDAdmin...");
			PDMessages mensajes = new PDMessages();
			PDAdmin.shutdown(mensajes);
			contexto.close();
			contextoAutorizacion.close();
			registraMensajes(mensajes);
			if (Loggy.isTrace(this)) System.out.println("TAM*PDAdmin cerrado");
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08001_ERROR_CERRANDO_SESION);
		} catch (Throwable t) {
			throw t;
		}

	} //close

	//*************************GRUPOS	

	//crear grupo	
	public void adicionarGrupo(String nombreGrupo, String descripcionGrupo)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;
				
			PDMessages mensajes = new PDMessages();
			String grgyname = "cn=" + nombreGrupo + " ,o=sunarp,c=pe";
			PDRgyGroupName gpdrgy = new PDRgyGroupName(grgyname);
			PDGroup.createGroup(
				contexto,
				nombreGrupo,
				gpdrgy,
				nombreGrupo,
				descripcionGrupo,
				mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin crear grupo
	//**************************USUARIO

	//crear usuario	
	public void adicionarUsuario(
		String IdUsuario,
		String nombreUsuario,
		String apellidoUsuario,
		String descripcion,
		String password)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
				
			PDMessages mensajes = new PDMessages();
			String rgyName = "cn=" + IdUsuario + ", o=sunarp, c=pe";
			PDRgyUserName pdrgy =
				new PDRgyUserName(rgyName, nombreUsuario, apellidoUsuario);
			boolean ssoUser = false;
			boolean pwdPolicy = true;
			ArrayList grouplist = new ArrayList();

			// OJO: por default: el usuario no pertenece a ningún grupo
			//cjvc77: 01102003
			if (Loggy.isTrace(this)) {
				System.out.println("Se llamara a PDUser.createUser() con parametros:");
				System.out.println("Contexto|IdUsuario|pdrgy|descripcion|password|grouplist|ssoUser|pwdPolicy|mensajes");
				StringBuffer stb = new StringBuffer().append(contexto).append("|").append(IdUsuario).append("|").append(pdrgy);
				stb.append("|").append(descripcion).append("|").append("<no imprimimos>").append("|").append(grouplist);
				stb.append("|").append(ssoUser).append("|").append(pwdPolicy).append("|").append(mensajes);
				System.out.println(stb.toString());
			}
			PDUser.createUser(
				contexto,
				IdUsuario,
				pdrgy,
				descripcion,
				password.toCharArray(),
				grouplist,
				ssoUser,
				pwdPolicy,
				mensajes);
			registraMensajes(mensajes);				

			//cjvc77: 01102003
			if (Loggy.isTrace(this)) {
				System.out.println("Se activará usuario " +IdUsuario);
			}
			//Despues de crear usuario, se debe ACTIVARLO
			PDUser.setAccountValid(contexto, IdUsuario, true, mensajes);

			//cjvc77: 01102003
			if (Loggy.isTrace(this)) {
				System.out.println("Se activo usuario satisfactoriamente " + IdUsuario);
			}
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin crear usuario
	


	public void borrarUsuario(String IdUsuario)
		throws Throwable {
			
		//borrar usuario del TAM
		
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			PDUser.deleteUser(contexto,IdUsuario, true,mensajes);
			registraMensajes(mensajes);				
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}
	
		

	public void adicionarUsuarioAGrupo(
		DBConnection conn,
		String IdUsuario,
		String IdGrupo)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;
			DboTmPerfil dboTmPerfil = new DboTmPerfil(conn);

			dboTmPerfil.setFieldsToRetrieve(DboTmPerfil.CAMPO_NOMBRE_TAM);
			dboTmPerfil.setField(DboTmPerfil.CAMPO_PERFIL_ID, IdGrupo);
			if (dboTmPerfil.find() == false)
				throw new CustomException(E08003_PERFIL_DESCONOCIDO);

			String grupoTAM = dboTmPerfil.getField(DboTmPerfil.CAMPO_NOMBRE_TAM);

			PDMessages mensajes = new PDMessages();
			java.util.ArrayList lista = new java.util.ArrayList();
			lista.add(IdUsuario);
			PDGroup.addMembers(contexto, grupoTAM, lista, mensajes);
			registraMensajes(mensajes);
			
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin agrega usuario a grupo

	public void cambiaPasswordUsuario(String IdUsuario, String nuevoPassword)
		throws Throwable 
		{
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;
			PDMessages mensajes = new PDMessages();
			/*
			if (isTrace(this)) System.out.println("***storageredim");
			if (isTrace(this)) System.out.println("contexto  = "+contexto.toString());
			if (isTrace(this)) System.out.println("idUsuario = "+IdUsuario);
			if (isTrace(this)) System.out.println("nuevoPassword = "+nuevoPassword);
			*/
			try
			{
				//SE COMENTA EL NUEVO PASSWORD PARA QUE NO SE ESCRIBA EN LA CONSOLA 
				//if (Loggy.isTrace(this)) 
				//System.out.println("___TAM_Secadmin_ nPass="+nuevoPassword);
			
				//cjvc77: 01102003
				if (Loggy.isTrace(this)) {
					System.out.println("Se llamara a PDUser.setPassword() con parametros:");
					System.out.println("Contexto|IdUsuario|nuevoPassword|mensajes");
					StringBuffer stb = new StringBuffer().append(contexto).append("|").append(IdUsuario).append("|").append("<no imprimimos>");
					stb.append("|").append(mensajes);
					System.out.println(stb.toString());
				}
			
			PDUser.setPassword(contexto, IdUsuario, nuevoPassword.toCharArray(), mensajes);
				//cjvc77: 01102003
				if (Loggy.isTrace(this)) 
					System.out.println("cambio password satisfactoriamente " + IdUsuario);
			}
			catch (PDException pde)
			{
				PDMessages am = pde.getMessages();
				int codigoErrorPrincipal = registraMensajes(am);
					//10dic2002 HT
					//se capturan posibles errores por políticas de password
					//los códigos de error se pueden ver en el documento :
					// "IBM tivoli access manager - Error Message Reference version3.9"
					// disponible en la página web de Tivoli
					// capítulo 2, página 56
					switch (codigoErrorPrincipal)
					{
						case 320938284:
							throw new ValidacionException("Contraseña rechazada porque no cumple con la política de seguridad de TAM");
						case 320938285:
							throw new ValidacionException("Contraseña rechazada porque no cumple con longitud mínima requerida por TAM");
						case 320938286:
							throw new ValidacionException("Contraseña rechazada porque no cumple con regla de caracteres en blanco establecida en TAM");
						case 320938287:
							throw new ValidacionException("Contraseña rechazada porque no cumple con regla de caracteres repetidos establecida en TAM");
						case 320938288:
							throw new ValidacionException("Contraseña rechazada porque no cumple con la mínima cantidad de caracteres alfabéticos requeridos por TAM");
						case 320938289:
							throw new ValidacionException("Contraseña rechazada porque no cumple con la mínima cantidad de caracteres numéricos requeridos por TAM");
						default :
							throw new CustomException(E08002_ERROR_TAM);
					}				
			}
	} //cambio password de usuario

	public void removerUsuarioDeGrupo(String IdUsuario, String grupo)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			java.util.ArrayList lista = new java.util.ArrayList();
			lista.add(IdUsuario);
			PDGroup.removeMembers(contexto, grupo, lista, mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin remover usuario de grupo	

	//darle a un grupo permiso en una ACL
	public void asignarACLaGrupo(String IdAcl, String IdGrupo) throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			PDAcl acl = new PDAcl(contexto, IdAcl, mensajes);
			PDAclEntryGroup entryGroup =
				new PDAclEntryGroup(contexto, IdGrupo, "Tr", mensajes);
			acl.setPDAclEntryGroup(contexto, entryGroup, mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}

	//darle a un usuario permiso en una ACL
	public void asignarACLaUsuario(String IdAcl, String IdUsuario)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			PDAcl acl = new PDAcl(contexto, IdAcl, mensajes);
			PDAclEntryUser entryUser =
				new PDAclEntryUser(contexto, IdUsuario, "Tr", mensajes);
			acl.setPDAclEntryUser(contexto, entryUser, mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}

	/*
	4sep2002h
	darle a un usuario permiso en una ACL - Version con IdPermiso
	Metodo usado para darle un permiso especifico a un usuario especifico
	*/
	public void asignarACLaUsuario(
		DBConnection conn,
		String IdPermiso,
		String IdUsuario)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			DboTmPermisoExt dboTmPermisoExt = new DboTmPermisoExt(conn);
			dboTmPermisoExt.setFieldsToRetrieve(DboTmPermisoExt.CAMPO_ACL);
			dboTmPermisoExt.setField(DboTmPermisoExt.CAMPO_PERMISO_ID, IdPermiso);

			if (dboTmPermisoExt.find() == false)
				throw new CustomException(E08002_ERROR_TAM);

			String IdAcl = dboTmPermisoExt.getField(DboTmPermisoExt.CAMPO_ACL);

			PDMessages mensajes = new PDMessages();
			PDAcl acl = new PDAcl(contexto, IdAcl, mensajes);
			PDAclEntryUser entryUser =
				new PDAclEntryUser(contexto, IdUsuario, "Tr", mensajes);
			acl.setPDAclEntryUser(contexto, entryUser, mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}
	
	public void removerACLdeUsuario(
		DBConnection conn,
		String idPermiso,
		String idUsuario)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			DboTmPermisoExt dboTmPermisoExt = new DboTmPermisoExt(conn);
			dboTmPermisoExt.setFieldsToRetrieve(DboTmPermisoExt.CAMPO_ACL);
			dboTmPermisoExt.setField(DboTmPermisoExt.CAMPO_PERMISO_ID, idPermiso);

			if (dboTmPermisoExt.find() == false)
				throw new CustomException(E08002_ERROR_TAM);

			String idAcl = dboTmPermisoExt.getField(DboTmPermisoExt.CAMPO_ACL);

			PDMessages mensajes = new PDMessages();
			
			PDAcl.removePDAclEntryUser(contexto,idAcl,idUsuario, mensajes);
			registraMensajes(mensajes);
			
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}	

	public void removerACLdeGrupo(String IdAcl, String IdGrupo) throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			PDAcl acl = new PDAcl(contexto, IdAcl, mensajes);
			acl.removePDAclEntryGroup(contexto, IdGrupo, mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}

	public void removerACLdeUsuario(String IdAcl, String IdUsuario)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			PDAcl acl = new PDAcl(contexto, IdAcl, mensajes);
			acl.removePDAclEntryUser(contexto, IdUsuario, mensajes);
			registraMensajes(mensajes);
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	}

	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS
	//*************************LISTAS

	//lista de ACLs
	public java.util.ArrayList listaACLs() throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return null;
			PDMessages mensajes = new PDMessages();
			java.util.ArrayList arr3 = PDAcl.listAcls(contexto, mensajes);
			registraMensajes(mensajes);
			return arr3;
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin listar de ACLs

	//listar grupos
	public java.util.ArrayList listaGrupos(boolean tipo) throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return null;			
			PDMessages mensajes = new PDMessages();
			java.util.ArrayList arr1 = PDGroup.listGroups(contexto, "*", 0, tipo, mensajes);
			registraMensajes(mensajes);
			return arr1;
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin listar grupos

	//listar usuarios
	public java.util.ArrayList listaUsuarios(boolean tipo) throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return null;			
			PDMessages mensajes = new PDMessages();
			java.util.ArrayList arr1 =	PDUser.listUsers(this.contexto, "*", 0, tipo, mensajes);
			registraMensajes(mensajes);
			return arr1;
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin listar usuarios

	//lista de ACLs
	public void adicionarACL(String IdAcl, String DescripcionAcl)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return;			
			PDMessages mensajes = new PDMessages();
			com.tivoli.pd.jutil.PDAttrs pruebaVacio = null;
			PDAcl.createAcl(
				this.contexto,
				IdAcl,
				DescripcionAcl,
				null,
				null,
				null,
				null,
				pruebaVacio,
				mensajes);
			registraMensajes(mensajes);				
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin listar de ACLs

	//listar los usuarios de un grupo especifico
	public java.util.ArrayList listaUsuariosDeGrupo(String grupo)
		throws Throwable {
		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return null;			
			PDMessages mensajes = new PDMessages();
			PDGroup grp = new PDGroup(contexto, grupo, mensajes);
			registraMensajes(mensajes);
			java.util.ArrayList arr1 = grp.getMembers();
			return arr1;
		} catch (PDException pde) {
			registraPDException(pde);
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			throw t;
		}
	} //fin listar los usuarios de un grupo especifico


/*
METODO COMENTADO 28 SET 2002

	public PermisoBean[] listaPermisosDeUsuario(
		DBConnection conn,
		String IdUsuario)
		throws Throwable {

			DboTmPermisoExt dboTmPermisoExt = new DboTmPermisoExt(conn);
			int maxp = Integer.parseInt(dboTmPermisoExt.getMax(dboTmPermisoExt.CAMPO_PERMISO_ID));
			dboTmPermisoExt.clearAll();
			StringBuffer sb = new StringBuffer();

			sb.append(DboTmPermisoExt.CAMPO_PERMISO_ID).append("|");
			sb.append(DboTmPermisoExt.CAMPO_NOMBRE).append("|");
			sb.append(DboTmPermisoExt.CAMPO_STRING_URL).append("|");
			sb.append(DboTmPermisoExt.CAMPO_METODO);
			dboTmPermisoExt.setFieldsToRetrieve(sb.toString());
			java.util.ArrayList arr1 = dboTmPermisoExt.searchAndRetrieveList();
			if (arr1.size()==0)
				throw new IllegalArgumentException("Opciones de menu no disponibles");

			maxp++;			
			PermisoBean arrp[] = new PermisoBean[maxp];

			for (int i = 0; i < arr1.size(); i++) 
			{
				DboTmPermisoExt dbo1 = (DboTmPermisoExt) arr1.get(i);
				String url = dbo1.getField(DboTmPermisoExt.CAMPO_STRING_URL);

				//validar si el usuario tiene permiso al URL
				boolean permiso;
				
				try {
					sb.delete(0,sb.length());
					sb.append(rutaTAM);
					sb.append(url);
					PDPrincipal pri = new PDPrincipal(IdUsuario);
					//if (isTrace(this)) System.out.println("sbruta = "+sb.toString());
					PDPermission perm1 = new PDPermission(sb.toString(), "r");
					permiso = perm1.implies(pri);

					} catch (Throwable t) {
						t.printStackTrace();
						permiso = false;
					}

				if (permiso == true) 
				{
					//colocar permiso en el arreglo, EN LA POSICION
					// ESPECIFICA DE SU ID
					
					PermisoBean permisoBean = new PermisoBean();
					//permisoBean.setNumber(dbo1.getField(DboTmPermisoExt.CAMPO_PERMISO_ID));
					permisoBean.setDesc(dbo1.getField(DboTmPermisoExt.CAMPO_NOMBRE));
					
					String metodo = dbo1.getField(DboTmPermisoExt.CAMPO_METODO);
					
					sb.delete(0, sb.length());
					sb.append(url);
					if (metodo!=null && metodo.trim().length()>0)
						{
							sb.append("?state=");
							sb.append(metodo);
						}
					permisoBean.setUrl(sb.toString());

					int permisoId = Integer.parseInt(dbo1.getField(DboTmPermisoExt.CAMPO_PERMISO_ID));
					
					arrp[permisoId] = permisoBean;
				} //if (permiso==true

			} //for i
			
			return arrp;


	} //listaPermisosUsuario
*/

	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************
	//****************************************************************************

	//verificar si el usuario tiene permiso para el recurso especifico
	public boolean verificaPermiso(String IdUsuario, String recurso) {
		boolean respuesta;
		try {						
			
			StringBuffer sb = new StringBuffer();
			sb.append(rutaTAM);
			sb.append(recurso);
			
			//if (isTrace(this)) System.out.println("rec = " + sb.toString());
			//SAUL
			PDPrincipal principal = new PDPrincipal(contextoAutorizacion);
			//PDPrincipal pri = new PDPrincipal(IdUsuario);
			//SAUL
			PDPermission permiso = new PDPermission(sb.toString(), "r"); 
			//PDPermission perm1 = new PDPermission(sb.toString(), "r");
			
				//cjvc77: 01102003
				if (Loggy.isTrace(this)) {
					System.out.println("En PDUser.verificaPermiso(), parametros:");
					System.out.println("IdUsuario|recurso|rutaTAM");
					StringBuffer stb = new StringBuffer().append(IdUsuario).append("|").append(recurso).append("|").append(rutaTAM);
					System.out.println(stb.toString());
				}

				//SAUL
				//cjvc77: 01102003
				/*if (Loggy.isTrace(this)) 
					System.out.println("Llamando PDPermission.implies(), parametros: PDPrincipal: " + pri);*/

				if (Loggy.isTrace(this)) 
				System.out.println("Llamando PDPermission.implies(ctxt,principal);, parametros: PDPrincipal::: " + principal);
				
				//SAUL
				//respuesta = perm1.implies(pri);
				respuesta = permiso.implies(contextoAutorizacion,principal);

				//cjvc77: 01102003
				if (Loggy.isTrace(this)) 
					System.out.println("Respuesta de PDPermission.implies(): " + respuesta);
		} catch (Throwable t) {
			t.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	} // fin verificar si el usuario tiene permiso para el recurso especifico

	//Validar si un USUARIO/PASSWORD es correcto
	public boolean validaUsuario(String IdUsuario, String clave)
		throws CustomException {
		boolean respuesta = false;
		//SAUL
		//boolean salir = false;

		try {
			//if (Propiedades.getInstance().getFlagProduccion() != false) 
			if (Propiedades.getInstance().getFlagProduccion()==false)
				return true;
				
			java.util.HashMap hm1 = new java.util.HashMap();
			java.util.HashMap hm2 = new java.util.HashMap();
			//SAUL
			com.tivoli.pd.jazn.PDLoginModule loginModulo = new com.tivoli.pd.jazn.PDLoginModule(); 
			//PDLoginModule loginModule = new PDLoginModule();
			Subject sujeto = new Subject();
			UserHandler handler = new UserHandler(IdUsuario, clave);
			
				//cjvc77: 01102003
				if (Loggy.isTrace(this)) {
					System.out.println("Usuario: " + IdUsuario);
					System.out.println("Se llamara a PDUser.validaUsuario() al método PDLoginModule.initialize() con parametros:");
					System.out.println("Sujeto|handler|hm1|hm2");
					StringBuffer stb = new StringBuffer().append(sujeto).append("|").append(handler).append("|").append(hm1);
					stb.append("|").append(hm2);
					System.out.println(stb.toString());
				}
			
			//SAUL
			//loginModule.initialize(sujeto, handler, hm1, hm2);
			loginModulo.initialize(sujeto, handler, hm1, hm2);
			System.out.println("PDLoginModule.initialize() se ejecuto satisfactoriamente.Sujeto " + sujeto);
			loginModulo.setDefaultAuthorizationContext(contextoAutorizacion);
				//cjvc77: 01102003
				//if (Loggy.isTrace(this))
					System.out.println("PDLoginModule.initialize() se ejecuto satisfactoriamente el set dafault ");

			//SAUL	
			//respuesta = loginModule.login();
			respuesta = loginModulo.login();

				//cjvc77: 01102003
				if (Loggy.isTrace(this))
					System.out.println("Respuesta de PDLoginModule.login(): " + respuesta);
			//SAUL	
			//boolean salir = loginModule.logout();
			respuesta = loginModulo.logout();
						
		} catch (AccountExpiredException e1) {
			if (Loggy.isTrace(this)) System.out.println("cuenta expirada : " + e1.getMessage());
			throw new CustomException(E08050_CUENTA_EXPIRADA);
		} catch (CredentialExpiredException e2) {
			if (Loggy.isTrace(this)) System.out.println("credencial expirada : " + e2.getMessage());
			throw new CustomException(E08051_CREDENCIAL_EXPIRADA);
		} catch (FailedLoginException e3) {

			if (Loggy.isTrace(this)) System.out.println("login error : " + e3.getMessage());
			String CodigoError = E08052_NO_AUTENTICADO;
			/*
			A continuacion, se intenta obtener un codigo de error
			más especifico:
			
						Casos mas comunes para entrar a esta excepcion :
						Unknown user
						Incorrect password
			
			*/
			String mensajeError = e3.getMessage();
			if (mensajeError.equals("Unknown user"))
				CodigoError = E08054_USUARIO_INCORRECTO;
			if (mensajeError.equals("Incorrect password"))
				CodigoError = E08055_CLAVE_INCORRECTA;
				
			throw new CustomException(CodigoError);
		} catch (LoginException e4) {
			if (Loggy.isTrace(this)) System.out.println("LoginException" + e4.getMessage());
			throw new CustomException(E08002_ERROR_TAM);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new CustomException(E08053_LOGIN_ERROR);
		}
		return respuesta;
	} //**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	//**************************************************************************
	private int registraMensajes(PDMessages mensajes) 
	{
		int codigoErrorPrincipal = 0;
		if (mensajes != null && mensajes.size() > 0) 
		{
			if (Loggy.isTrace(this)) System.out.println("TAM*___Mensajes:");
			for (int i = 0; i < mensajes.size(); i++) 
			{
				PDMessage pm = (PDMessage) mensajes.get(i);
				if (Loggy.isTrace(this)) System.out.println("TAM*Mensaje No.= " + (i+1));
				if (Loggy.isTrace(this)) System.out.println("TAM*Codigo de errror TAM   = " + pm.getMsgCode());
				if (i==0)
					codigoErrorPrincipal = pm.getMsgCode();
				String s = "" + pm.getMsgSeverity();
				int severidad = Integer.parseInt(s);
				switch (severidad) 
				{
					case PDMessage.PDMESSAGE_SEVERITY_INFO :
						if (Loggy.isTrace(this)) System.out.println("TAM*Severity  = Info / Nivel = " + pm.getMsgSeverity());
						break;
					case PDMessage.PDMESSAGE_SEVERITY_WARNING :
						if (Loggy.isTrace(this)) System.out.println("TAM*Severity  = Warning / Nivel = " + pm.getMsgSeverity());
						break;
					case PDMessage.PDMESSAGE_SEVERITY_ERROR :
						if (Loggy.isTrace(this)) System.out.println("TAM*Severity  = Error / Nivel = " + pm.getMsgSeverity());
						break;
				}
				if (Loggy.isTrace(this)) System.out.println("TAM*Texto     = " + pm.getMsgText());
			}
			if (Loggy.isTrace(this)) System.out.println("TAM*___fin mensajes");
		}
		return codigoErrorPrincipal;
	}

	private void registraPDException(PDException pde) {
		PDMessages am = pde.getMessages();
		if (am != null)
			registraMensajes(am);
		if (Loggy.isTrace(this)) System.out.println("TAM*Mensaje Principal = " + pde.getMessage());
		if (Loggy.isTrace(this)) System.out.println("TAM*mensaje de error localizado = " + pde.getLocalizedMessage());
		if (Loggy.isTrace(this)) System.out.println("TAM*----stack----");
		pde.printStackTrace();
	}

        //Modificado por: Proyecto Filtros de Acceso
        //Fecha: 02/10/2006     
        public void configurarFechaHoraAcceso(String usuario) throws Throwable 
        {
            try 
            {
                if (Propiedades.getInstance().getFlagProduccion()==false)
                {
                        return;
                }                       
                                
                PDMessages mensajes = new PDMessages();
                //SAUL
                //boolean pwdPolicy = true;
                //ArrayList grouplist = new ArrayList();
      
                if (Loggy.isTrace(this)) 
                {
                    System.out.println("Se llamara a PDPolicy.setTodAccess()");
                }
                //EL VALOR DE TRUE SE ENCUENTRA EN PRODUCCION      
                //PDPolicy.setTodAccess(contexto, usuario, PDPolicy.PDPOLICY_TOD_WEEKDAY, 480, 1200, PDPolicy.PDPOLICY_TIME_LOCAL, false, mensajes);
                PDPolicy.setTodAccess(contexto, usuario, PDPolicy.PDPOLICY_TOD_WEEKDAY, 480, 1200, PDPolicy.PDPOLICY_TIME_LOCAL, true, mensajes);
                registraMensajes(mensajes);
            
                if (Loggy.isTrace(this)) 
                {
                    System.out.println("Se definirá política de control de acceso al usuario " + usuario);
                }
      
                if (Loggy.isTrace(this)) 
                {
                    System.out.println("Se configuró política al usuario" + usuario  + " satisfactoriamente.");
                }                       
            } 
            catch (PDException pde) 
            {
                secAdmin.registraPDException(pde);
                throw new CustomException(E08006_ERROR_POLITICA_TOD_ACCESS_TAM_);
            } 
            catch (Throwable t) 
            {
                throw t;
            }
        }
        //Fin Modificacion
}