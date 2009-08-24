package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskOficRegistral extends GrandTask
{
public TaskOficRegistral()
{
	super();
	this.nombreTabla = "Oficinas Registrales";
	this.nKeys = 2;  //numero de campos llave
	this.cabezas = new String[] {
						"Codigo",
						"Oficina Registral",
						"Jurisdiccion"
					};	
	this.ruta = "frmOficRegistral";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboOficRegistral dbo = new DboOficRegistral(conn);
dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_NOMBRE, pag);

Vector res = new Vector();

DboTmJurisdiccion dboTmJurisidiccion = new DboTmJurisdiccion(conn);

for (int i = 0; i < arri.size(); ++i )
	{
		DboOficRegistral d = (DboOficRegistral) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		String cod = d.getField(d.CAMPO_REG_PUB_ID)+d.getField(d.CAMPO_OFIC_REG_ID);
		gbean.setValor01(cod);
		gbean.setValor02(d.getField(d.CAMPO_NOMBRE));

		dboTmJurisidiccion.clearAll();
		dboTmJurisidiccion.setFieldsToRetrieve(DboTmJurisdiccion.CAMPO_NOMBRE);
		dboTmJurisidiccion.setField(DboTmJurisdiccion.CAMPO_JURIS_ID,d.getField(d.CAMPO_JURIS_ID));
		if (dboTmJurisidiccion.find())
			gbean.setValor03(dboTmJurisidiccion.getField(dboTmJurisidiccion.CAMPO_NOMBRE));
		else
			gbean.setValor03("---");
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_REG_PUB_ID));
		gbean.setLlave02(d.getField(d.CAMPO_OFIC_REG_ID));
		//_
		res.add(gbean);
	}


FormOutputListado output = new FormOutputListado();

output.setLista(res);
//output.setCantidadRegistros(nr);
output.setKeys(this.nKeys);
output.setCabezas(this.cabezas);
output.setNombreTabla(this.nombreTabla);

//calcular número para boton "retroceder pagina"		
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
	DboOficRegistral dbo = new DboOficRegistral(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getLlave02());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_REG_PUB_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_OFIC_REG_ID));
	bean.setValor03(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor04(dbo.getField(dbo.CAMPO_JURIS_ID));
}

public void update(GenericBean bean) throws Throwable
{
	DboOficRegistral dbo = new DboOficRegistral(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getLlave02());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_JURIS_ID+"|"+
						  dbo.CAMPO_USR_ULT_MODIF+"|"+
						  dbo.CAMPO_TS_ULT_MODIF);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor03());
	dbo.setField(dbo.CAMPO_JURIS_ID, bean.getValor04());
	dbo.setField(dbo.CAMPO_USR_ULT_MODIF, this.usuario.getUserId());
	dbo.setField(dbo.CAMPO_TS_ULT_MODIF, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboOficRegistral dbo = new DboOficRegistral(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getLlave02());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboOficRegistral dbo = new DboOficRegistral(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getValor01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getValor02());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
		
	dbo.clearAll();
	
	dbo.setField(dbo.CAMPO_REG_PUB_ID, bean.getValor01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID, bean.getValor02());
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor03());
	dbo.setField(dbo.CAMPO_JURIS_ID, bean.getValor04());
	dbo.setField(dbo.CAMPO_USR_CREA, this.usuario.getUserId());
	dbo.setField(dbo.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));	
	dbo.setField(dbo.CAMPO_TS_ULT_MODIF, null);
	
	dbo.add();
}

}//fin clase