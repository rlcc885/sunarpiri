package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmDistrito extends GrandTask
{
public TaskTmDistrito()
{
	super();
	this.nombreTabla = "Distritos";
	this.nKeys = 4;  //numero de campos llave
	this.cabezas = new String[] {
						"País",
						"Departamento",
						"Provincia",
						"Código de distrito",
						"Distrito",
						"Estado"
					};	
	this.ruta = "frmTmDistrito";
}

public FormOutputListado getList(int pag) throws Throwable
{
//____________________JOIN___________________________________
MultiDBObject multi = new MultiDBObject(conn);
				
multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDistrito", "tDboTmDistrito");
multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmProvincia", "tDboTmProvincia");
multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDepartamento", "tDboTmDepartamento");
multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmPais", "tDboTmPais");


//pais
multi.setForeignKey("tDboTmDistrito", DboTmDistrito.CAMPO_PAIS_ID, "tDboTmPais", DboTmPais.CAMPO_PAIS_ID);
//provincia
multi.setForeignKey("tDboTmDistrito", DboTmDistrito.CAMPO_PAIS_ID, "tDboTmProvincia", DboTmProvincia.CAMPO_PAIS_ID);
multi.setForeignKey("tDboTmDistrito", DboTmDistrito.CAMPO_DPTO_ID, "tDboTmProvincia", DboTmProvincia.CAMPO_DPTO_ID);
multi.setForeignKey("tDboTmDistrito", DboTmDistrito.CAMPO_PROV_ID, "tDboTmProvincia", DboTmProvincia.CAMPO_PROV_ID);
//departamento
multi.setForeignKey("tDboTmDistrito", DboTmDistrito.CAMPO_PAIS_ID, "tDboTmDepartamento", DboTmDepartamento.CAMPO_PAIS_ID);
multi.setForeignKey("tDboTmDistrito", DboTmDistrito.CAMPO_DPTO_ID, "tDboTmDepartamento", DboTmDepartamento.CAMPO_DPTO_ID);

multi.setMaxRecords(Propiedades.getInstance().getLineasPorPag());

/*
Vector vec = multi.searchAndRetrievePaginado(
	"TM_PAIS.NOMBRE|TM_DEPARTAMENTO.NOMBRE|TM_PROVINCIA.NOMBRE|TM_DISTRITO.DIST_ID|TM_DISTRITO.NOMBRE|TM_DISTRITO.ESTADO",
	pag);
*/
Vector vec = multi.searchAndRetrievePaginado(
	"TM_DISTRITO.NOMBRE",
	pag);
Vector res = new Vector();

for (int i = 0; i < vec.size(); ++i )
	{
	MultiDBObject mdo = (MultiDBObject) vec.elementAt(i);
	GenericBean gbean = new GenericBean();

	gbean.setValor01(mdo.getField("tDboTmPais",DboTmPais.CAMPO_NOMBRE));
	gbean.setValor02(mdo.getField("tDboTmDepartamento",DboTmDepartamento.CAMPO_NOMBRE));
	gbean.setValor03(mdo.getField("tDboTmProvincia",DboTmProvincia.CAMPO_NOMBRE));
	gbean.setValor04(mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_DIST_ID));
	gbean.setValor05(mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_NOMBRE));
	
	String e = mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_ESTADO);
	if (e.equals("1"))
		gbean.setValor06("Activo");
	else
		gbean.setValor06("Inactivo");
		
	//_llaves
	gbean.setLlave01(mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_DPTO_ID));
	gbean.setLlave02(mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_PAIS_ID));
	gbean.setLlave03(mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_DIST_ID));
	gbean.setLlave04(mdo.getField("tDboTmDistrito",DboTmDistrito.CAMPO_PROV_ID));
	//////////	
	res.add(gbean);
	}


FormOutputListado output = new FormOutputListado();

output.setLista(res);
//output.setCantidadRegistros(nr);
output.setKeys(this.nKeys);
output.setCabezas(this.cabezas);
output.setNombreTabla(this.nombreTabla);

//calcular numero para boton "retroceder pagina"		
if (pag==1)
	output.setPagAnterior(-1);
else
	output.setPagAnterior(pag - 1);
	
//calcular numero para boton "avanzar pagina"
if (multi.getHaySiguiente()==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(pag + 1);

	
return output;
}//get lista

public void read(GenericBean bean) throws Throwable
{
	DboTmDistrito dbo = new DboTmDistrito(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_DIST_ID,bean.getLlave03());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getLlave04());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_DPTO_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_PAIS_ID));
	bean.setValor03(dbo.getField(dbo.CAMPO_DIST_ID));
	bean.setValor04(dbo.getField(dbo.CAMPO_PROV_ID));
	bean.setValor05(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor06(dbo.getField(dbo.CAMPO_ESTADO));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmDistrito dbo = new DboTmDistrito(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_DIST_ID,bean.getLlave03());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getLlave04());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");;
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_ESTADO);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor05());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor06());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmDistrito dbo = new DboTmDistrito(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_DIST_ID,bean.getLlave03());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getLlave04());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmDistrito dbo = new DboTmDistrito(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getValor01());
	dbo.setField(dbo.CAMPO_PAIS_ID,"01");
	//dbo.setField(dbo.CAMPO_DIST_ID,bean.getValor03());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getValor04());
	
	ArrayList arri = dbo.searchAndRetrieveList(dbo.CAMPO_DIST_ID+" DESC");
	
	int nmax = 0;
	if (arri.size()>0)
		{
			DboTmDistrito di = (DboTmDistrito) arri.get(0);
			String max = di.getField(di.CAMPO_DIST_ID);
			nmax = Integer.parseInt(max);
			nmax++;			
		}
	
	String nuevoId = Tarea.rellenaIzq(""+nmax,"0",2);
	
	/*	
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	*/
	dbo.clearAll();
		
	dbo.setField(dbo.CAMPO_DPTO_ID, bean.getValor01());
	dbo.setField(dbo.CAMPO_PAIS_ID, "01");
	dbo.setField(dbo.CAMPO_DIST_ID, nuevoId);
	dbo.setField(dbo.CAMPO_PROV_ID, bean.getValor04());	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor05());	
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor06());	
	
	dbo.add();
}

}//fin clase

