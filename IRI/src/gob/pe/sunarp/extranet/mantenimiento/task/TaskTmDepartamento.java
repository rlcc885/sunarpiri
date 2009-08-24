package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmDepartamento extends GrandTask
{
public TaskTmDepartamento()
{
	super();
	this.nombreTabla = "Departamentos";
	this.nKeys = 2;  //numero de campos llave
	this.cabezas = new String[] {
						"País",
						"Código Departamento",
						"Departamento",
						"Estado"
					};	
	this.ruta = "frmTmDepartamento";
}

public FormOutputListado getList(int pag) throws Throwable
{
//____________________JOIN___________________________________
MultiDBObject multi = new MultiDBObject(conn);
				
multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDepartamento", "tDboTmDepartamento");
multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmPais", "tDboTmPais");

//pais
multi.setForeignKey("tDboTmDepartamento", DboTmDepartamento.CAMPO_PAIS_ID, "tDboTmPais", DboTmPais.CAMPO_PAIS_ID);

multi.setMaxRecords(Propiedades.getInstance().getLineasPorPag());

/*
Vector vec = multi.searchAndRetrievePaginado(
	"TM_PAIS.NOMBRE|TM_DEPARTAMENTO.DPTO_ID|TM_DEPARTAMENTO.NOMBRE|TM_DEPARTAMENTO.ESTADO",
	pag);
*/

Vector vec = multi.searchAndRetrievePaginado(
	"TM_DEPARTAMENTO.NOMBRE",
	pag);
	
Vector res = new Vector();

for (int i = 0; i < vec.size(); ++i )
	{
	MultiDBObject mdo = (MultiDBObject) vec.elementAt(i);
	GenericBean gbean = new GenericBean();	
	
	gbean.setValor01(mdo.getField("tDboTmPais",DboTmPais.CAMPO_NOMBRE));
	gbean.setValor02(mdo.getField("tDboTmDepartamento",DboTmDepartamento.CAMPO_DPTO_ID));
	gbean.setValor03(mdo.getField("tDboTmDepartamento",DboTmDepartamento.CAMPO_NOMBRE));
	
	String e = mdo.getField("tDboTmDepartamento",DboTmDepartamento.CAMPO_ESTADO);

		if (e.startsWith("1") || e.startsWith("A"))
			gbean.setValor04("Activo");
		else
			gbean.setValor04("Inactivo");
			
		//_llaves
		gbean.setLlave01(mdo.getField("tDboTmDepartamento",DboTmDepartamento.CAMPO_DPTO_ID));
		gbean.setLlave02(mdo.getField("tDboTmDepartamento",DboTmDepartamento.CAMPO_PAIS_ID));
		//_
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
	DboTmDepartamento dbo = new DboTmDepartamento(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave02());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_DPTO_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_PAIS_ID));
	bean.setValor03(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor04(dbo.getField(dbo.CAMPO_ESTADO));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmDepartamento dbo = new DboTmDepartamento(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_PAIS_ID,"01");
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_ESTADO);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor03());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor04());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmDepartamento dbo = new DboTmDepartamento(conn);
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave02());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmDepartamento dbo = new DboTmDepartamento(conn);
	//dbo.setField(dbo.CAMPO_DPTO_ID,bean.getValor01());
	dbo.setField(dbo.CAMPO_PAIS_ID,"01");	
	
	ArrayList arri = dbo.searchAndRetrieveList(dbo.CAMPO_DPTO_ID+" DESC");
	
	int nmax=0;
	if (arri.size()>0)
	{
		DboTmDepartamento d = (DboTmDepartamento) arri.get(0);
		String max = d.getField(d.CAMPO_DPTO_ID);
		nmax = Integer.parseInt(max);
		nmax++;
	}
	
	String nuevoId = Tarea.rellenaIzq(""+nmax,"0",2);
	
	/*
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	*/
	dbo.clearAll();
		
	dbo.setField(dbo.CAMPO_DPTO_ID, nuevoId);
	dbo.setField(dbo.CAMPO_PAIS_ID, "01");
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor03());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor04());	
	
	dbo.add();
}

}//fin clase

