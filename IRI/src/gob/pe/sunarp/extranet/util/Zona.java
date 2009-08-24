package gob.pe.sunarp.extranet.util;

import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.dbobj.*;



/*
8 noviembre 2002
Clase creada para calcular

OFICINAS, ZONALES Y JURISDICCION

Nota: zona = regPubid
cada vez que se crea un nuevo usuario u organización
*/

public class Zona extends SunarpBean{
	
//input
private DBConnection conn=null;
private String provinciaId;
private String departamentoId;
private String paisId;
private UsuarioBean usuario=null;
//output
private String jurisId;
private String regPubId; 
private String oficRegId;

public void calculaZona() throws Throwable
{
/*
calcular jurisId, regPubId, oficRegId. Reglas:

1.- Si el pais no es Perú, los valores serán todos FUERA DEL PERU
    y no se calcula más nada.
2.- Se calcula la provincia, que es el del lugar donde VIVE el usuario
    u organización que se está registrando. Pero, si el perfil del creador
    es Administrador de Organización, la provincia será aquella donde queda la
    organización a la que pertenece el administrador.
3.- Una vez calculada la provincia, se extrae de ella el regPubId y el OficRegId
4.- Luego, con esos dos, se busca el JurisId
*/	

jurisId    = Constantes.ID_JURISDICCION_FUERA_DEL_PERU;
regPubId   = Constantes.ID_REGISTRO_PUBLICO_FUERA_DEL_PERU; 
oficRegId  = Constantes.ID_OFICINA_FUERA_DEL_PERU;
	
if (paisId!=null && paisId.equals("01"))
			{
				DboTmProvincia dboTmProvincia = new DboTmProvincia(conn);
				
				//nota: si el usuario es null es porque
				//      la creación es por web y no hay usuario
				//      en sesión
				if (usuario!=null && usuario.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT)
				{
					String peJuriId = usuario.getCodOrg();
					DboPeJuri dboPeJuri = new DboPeJuri(conn);
					dboPeJuri.setFieldsToRetrieve(dboPeJuri.CAMPO_PERSONA_ID);
					dboPeJuri.setField(dboPeJuri.CAMPO_PE_JURI_ID,peJuriId);
					boolean b = dboPeJuri.find();
					
					DboDireccion dboDireccion = new DboDireccion(conn);
					dboDireccion.setField(dboDireccion.CAMPO_PERSONA_ID,dboPeJuri.getField(dboPeJuri.CAMPO_PERSONA_ID));
					b = dboDireccion.find();
					
					dboTmProvincia.setField(DboTmProvincia.CAMPO_PROV_ID, dboDireccion.getField(dboDireccion.CAMPO_PROV_ID));
					dboTmProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, dboDireccion.getField(dboDireccion.CAMPO_DPTO_ID));
					dboTmProvincia.setField(DboTmProvincia.CAMPO_PAIS_ID, dboDireccion.getField(dboDireccion.CAMPO_PAIS_ID));
				}
				else
				{
					dboTmProvincia.setField(DboTmProvincia.CAMPO_PROV_ID, provinciaId);
					dboTmProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, departamentoId);
					dboTmProvincia.setField(DboTmProvincia.CAMPO_PAIS_ID, paisId);
				}
				
				dboTmProvincia.setField(DboTmProvincia.CAMPO_ESTADO, "1");
				dboTmProvincia.find();
					
				regPubId  = dboTmProvincia.getField(DboTmProvincia.CAMPO_REG_PUB_ID);
				oficRegId = dboTmProvincia.getField(DboTmProvincia.CAMPO_OFIC_REG_ID);
				
				DboOficRegistral dboOficRegistral = new DboOficRegistral(conn);
				dboOficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficRegId);
				dboOficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regPubId);
				dboOficRegistral.find();
				
				jurisId = dboOficRegistral.getField(DboOficRegistral.CAMPO_JURIS_ID);
			}//if (paisId!=null && paisId.equals("01"))
}//fin de metodo//public void calculaZona() throws Throwable

















	public String getJurisId() {
		return jurisId;
	}

	public String getRegPubId() {
		return regPubId;
	}

	public String getOficRegId() {
		return oficRegId;
	}

	public void setConn(DBConnection conn) {
		this.conn = conn;
	}

	public void setProvinciaId(String provinciaId) {
		this.provinciaId = provinciaId;
	}

	public void setDepartamentoId(String departamentoId) {
		this.departamentoId = departamentoId;
	}

	/**
	 * Sets the paisId
	 * @param paisId The paisId to set
	 */
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	/**
	 * Sets the usuario
	 * @param usuario The usuario to set
	 */
	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

}

