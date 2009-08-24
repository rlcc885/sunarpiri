package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmActo extends GrandTask
{

public TaskTmActo() 
{
	super();
	this.nombreTabla = "Actos";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Codigo Acto",
						"Descripcion",
						"Libro",
						"Rubro"
					};	
					
	this.ruta = "frmTmActo";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmRubro dboTmRubro = new DboTmRubro(conn);
/*
MultiDBObject multi = new MultiDBObject();
multi.setDBName(DBName);

multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo",  "tDboTmActo");

multi.setForeignKey("tDboTmActo", DboTmActo.CAMPO_COD_ACTO,"ghost", DboTmActo.CAMPO_COD_ACTO);

multi.setMaxRecords(Propiedades.getInstance().getLineasPorPag());

//long nr = multi.c

Vector vec = multi.searchAndRetrievePaginado(DboTmActo.CAMPO_COD_ACTO, pag);

Vector res = new Vector();



for (int i = 0; i < vec.size(); ++i )
	{
		MultiDBObject mdo = (MultiDBObject) vec.elementAt(i);
		
		GenericBean gbean = new GenericBean();
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(mdo.getField("tDboTmActo",DboTmActo.CAMPO_COD_ACTO));
		gbean.setValor02(mdo.getField("tDboTmActo",DboTmActo.CAMPO_DESCRIPCION));
		gbean.setValor03(mdo.getField("tDboTmActo",DboTmActo.CAMPO_COD_LIBRO));
		//descripcion rubro, si tuviera
		String codRubro = mdo.getField("tDboTmActo",DboTmActo.CAMPO_COD_RUBRO);
		dboTmRubro.clearAll();
		dboTmRubro.setFieldsToRetrieve(dboTmRubro.CAMPO_NOMBRE);
		dboTmRubro.setField(dboTmRubro.CAMPO_ESTADO,"1");
		dboTmRubro.setField(dboTmRubro.CAMPO_COD_RUBRO,codRubro);
		if (dboTmRubro.find())
			gbean.setValor04(dboTmRubro.getField(dboTmRubro.CAMPO_NOMBRE));
		else
			gbean.setValor04("---");
		//_llaves
		gbean.setLlave01(gbean.getValor01());
		//_
		res.add(gbean);
	}
*/

DboTmActo dbo = new DboTmActo(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(DboTmActo.CAMPO_COD_ACTO, pag);

Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmActo d = (DboTmActo) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(DboTmActo.CAMPO_COD_ACTO));
		gbean.setValor02(d.getField(DboTmActo.CAMPO_DESCRIPCION));
		gbean.setValor03(d.getField(DboTmActo.CAMPO_COD_LIBRO));
		//descripcion rubro, si tuviera
		String codRubro = d.getField(DboTmActo.CAMPO_COD_RUBRO);
		dboTmRubro.clearAll();
		dboTmRubro.setFieldsToRetrieve(dboTmRubro.CAMPO_NOMBRE);
		dboTmRubro.setField(dboTmRubro.CAMPO_ESTADO,"1");
		dboTmRubro.setField(dboTmRubro.CAMPO_COD_RUBRO,codRubro);
		if (dboTmRubro.find())
			gbean.setValor04(dboTmRubro.getField(dboTmRubro.CAMPO_NOMBRE));
		else
			gbean.setValor04("---");
		//_llaves
		gbean.setLlave01(gbean.getValor01());
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
if (dbo.getHaySiguiente()==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(pag + 1);

	
return output;
}//get lista

public void read(GenericBean bean) throws Throwable
{
	DboTmActo dbo = new DboTmActo(conn);
	dbo.setField(dbo.CAMPO_COD_ACTO,bean.getLlave01());

	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");

	
	bean.setValor01(dbo.getField(dbo.CAMPO_COD_ACTO));
	bean.setValor02(dbo.getField(dbo.CAMPO_DESCRIPCION));
	bean.setValor03(dbo.getField(dbo.CAMPO_COD_LIBRO));
	bean.setValor04(dbo.getField(dbo.CAMPO_COD_RUBRO));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmActo dbo = new DboTmActo(conn);
	dbo.setField(dbo.CAMPO_COD_ACTO,bean.getLlave01());

	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");

	dbo.setFieldsToUpdate(dbo.CAMPO_COD_LIBRO+"|"+
						  dbo.CAMPO_COD_RUBRO+"|"+
						  dbo.CAMPO_DESCRIPCION);
	
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor02());
	dbo.setField(dbo.CAMPO_COD_LIBRO, bean.getValor03());
	dbo.setField(dbo.CAMPO_COD_RUBRO, bean.getValor04());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmActo dbo = new DboTmActo(conn);
	dbo.setField(dbo.CAMPO_COD_ACTO,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmActo dbo = new DboTmActo(conn);
	dbo.setField(dbo.CAMPO_COD_ACTO,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
		
	dbo.clearAll();
	
	dbo.setField(dbo.CAMPO_COD_ACTO, bean.getValor01());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor02());
	dbo.setField(dbo.CAMPO_COD_LIBRO, bean.getValor03());
	dbo.setField(dbo.CAMPO_COD_RUBRO, bean.getValor04());	
	
	dbo.add();
}


}//fin clase

