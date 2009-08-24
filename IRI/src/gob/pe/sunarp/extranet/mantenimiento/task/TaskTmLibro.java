package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmLibro extends GrandTask
{
public TaskTmLibro()
{
	super();
	this.nombreTabla = "Libros";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Codigo de Libro",
						"Area Registral",
						"Descripcion",
						"Estado"
					};	
	this.ruta = "frmTmLibro";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmLibro dbo = new DboTmLibro(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_COD_LIBRO, pag);

Vector res = new Vector();

DboTmAreaRegistral dbo1 = new DboTmAreaRegistral(conn);

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmLibro d = (DboTmLibro) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_COD_LIBRO));
		
		//_area registral
		String area = d.getField(d.CAMPO_AREA_REG_ID);
		dbo1.clearAll();
		dbo1.setFieldsToRetrieve(dbo1.CAMPO_NOMBRE);
		dbo1.setField(dbo1.CAMPO_AREA_REG_ID,area);
		if (dbo1.find())
			gbean.setValor02(dbo1.getField(dbo1.CAMPO_NOMBRE));
		else
			gbean.setValor02("---");
		
		gbean.setValor03(d.getField(d.CAMPO_DESCRIPCION));
		String e = d.getField(d.CAMPO_ESTADO);
		if (e.startsWith("1") || e.startsWith("A"))
			gbean.setValor04("Activo");
		else
			gbean.setValor04("Inactivo");
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_COD_LIBRO));
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
	DboTmLibro dbo = new DboTmLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_COD_LIBRO));
	bean.setValor02(dbo.getField(dbo.CAMPO_AREA_REG_ID));
	bean.setValor03(dbo.getField(dbo.CAMPO_DESCRIPCION));
	bean.setValor04(dbo.getField(dbo.CAMPO_ESTADO));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmLibro dbo = new DboTmLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_AREA_REG_ID+"|"+
						  dbo.CAMPO_DESCRIPCION+"|"+
						  dbo.CAMPO_ESTADO);
	
	dbo.setField(dbo.CAMPO_AREA_REG_ID, bean.getValor02());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor03());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor04());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmLibro dbo = new DboTmLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmLibro dbo = new DboTmLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();		
	dbo.setField(dbo.CAMPO_COD_LIBRO, bean.getValor01());
	dbo.setField(dbo.CAMPO_AREA_REG_ID, bean.getValor02());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor03());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor04());	
	
	dbo.add();
}


}//fin clase

