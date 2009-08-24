package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmAreaRegistral extends GrandTask
{
public TaskTmAreaRegistral()
{
	super();
	this.nombreTabla = "Areas Registrales";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Codigo de Area",
						"Nombre",
						"Estado"
					};	
	this.ruta = "frmTmAreaRegistral";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmAreaRegistral dbo = new DboTmAreaRegistral(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_AREA_REG_ID, pag);

Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmAreaRegistral d = (DboTmAreaRegistral) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_AREA_REG_ID));
		gbean.setValor02(d.getField(d.CAMPO_NOMBRE));
		String e = d.getField(d.CAMPO_ESTADO);
		if (e.startsWith("1") || e.startsWith("A"))
			gbean.setValor03("Activo");
		else
			gbean.setValor03("Inactivo");
			
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
	DboTmAreaRegistral dbo = new DboTmAreaRegistral(conn);
	dbo.setField(dbo.CAMPO_AREA_REG_ID,bean.getLlave01());
	
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_AREA_REG_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor03(dbo.getField(dbo.CAMPO_ESTADO));
	bean.setValor04(dbo.getField(dbo.CAMPO_DESCRIPCION));
	bean.setValor05(dbo.getField(dbo.CAMPO_PREFIJO));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmAreaRegistral dbo = new DboTmAreaRegistral(conn);
	dbo.setField(dbo.CAMPO_AREA_REG_ID,bean.getLlave01());
	
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_ESTADO+"|"+
						  dbo.CAMPO_DESCRIPCION+"|"+
						  dbo.CAMPO_PREFIJO);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor02());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor03());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor04());
	dbo.setField(dbo.CAMPO_PREFIJO, bean.getValor05());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmAreaRegistral dbo = new DboTmAreaRegistral(conn);
	dbo.setField(dbo.CAMPO_AREA_REG_ID,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmAreaRegistral dbo = new DboTmAreaRegistral(conn);
	dbo.setField(dbo.CAMPO_AREA_REG_ID,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();
		
	dbo.setField(dbo.CAMPO_AREA_REG_ID, bean.getValor01());
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor02());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor03());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor04());	
	dbo.setField(dbo.CAMPO_PREFIJO, bean.getValor05());	
	
	dbo.add();
}


}//fin clase

