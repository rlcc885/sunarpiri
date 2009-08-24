package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmJurisdiccion extends GrandTask
{
public TaskTmJurisdiccion()
{
	super();
	this.nombreTabla = "Jurisdicciones";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Codigo",
						"Descripcion",
						"Estado"
					};	
	this.ruta = "frmTmJurisdiccion";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmJurisdiccion dbo = new DboTmJurisdiccion(conn);
dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_JURIS_ID, pag);

Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmJurisdiccion d = (DboTmJurisdiccion) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_JURIS_ID));
		gbean.setValor02(d.getField(d.CAMPO_NOMBRE));
		String e = d.getField(d.CAMPO_ESTADO);
		
		if (e.startsWith("1"))
			gbean.setValor03("Activo");
		else
			gbean.setValor03("Inactivo");
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_JURIS_ID));
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
	DboTmJurisdiccion dbo = new DboTmJurisdiccion(conn);
	dbo.setField(dbo.CAMPO_JURIS_ID,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_JURIS_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor03(dbo.getField(dbo.CAMPO_ESTADO));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmJurisdiccion dbo = new DboTmJurisdiccion(conn);
	dbo.setField(dbo.CAMPO_JURIS_ID,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_USR_ULT_MODIF+"|"+
						  dbo.CAMPO_TS_ULT_MODIF+"|"+
						  dbo.CAMPO_ESTADO);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor02());
	dbo.setField(dbo.CAMPO_USR_ULT_MODIF, this.usuario.getUserId());
	dbo.setField(dbo.CAMPO_TS_ULT_MODIF, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor03());
	
	/*
	Validacion: NO se puede desactivar una jurisdiccion si tiene
	registros asociados.... validar!
	*/
	if (bean.getValor03().startsWith("0"))
	{
		DboCuentaJuris dbo2 = new DboCuentaJuris(conn);
		dbo2.setFieldsToRetrieve(dbo2.CAMPO_JURIS_ID);
		dbo2.setField(dbo2.CAMPO_JURIS_ID,bean.getLlave01());
		if (dbo2.find())
			throw new ValidacionException("No se puede desactivar Jurisdiccion porque tiene registros asociados","");
			
		DboOficRegistral dbo3 = new DboOficRegistral(conn);
		dbo3.setFieldsToRetrieve(dbo3.CAMPO_JURIS_ID);
		dbo3.setField(dbo3.CAMPO_JURIS_ID,bean.getLlave01());
		if (dbo3.find())
			throw new ValidacionException("No se puede desactivar Jurisdiccion porque tiene registros asociados","");
			
		DboPersona dbo4 = new DboPersona(conn);
		dbo4.setFieldsToRetrieve(dbo4.CAMPO_JURIS_ID);
		dbo4.setField(dbo4.CAMPO_JURIS_ID,bean.getLlave01());
		if (dbo4.find())
			throw new ValidacionException("No se puede desactivar Jurisdiccion porque tiene registros asociados","");
			
		DboPeJuri dbo5 = new DboPeJuri(conn);
		dbo5.setFieldsToRetrieve(dbo5.CAMPO_JURIS_ID);
		dbo5.setField(dbo5.CAMPO_JURIS_ID,bean.getLlave01());
		if (dbo5.find())
			throw new ValidacionException("No se puede desactivar Jurisdiccion porque tiene registros asociados","");
	}
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmJurisdiccion dbo = new DboTmJurisdiccion(conn);
	dbo.setField(dbo.CAMPO_JURIS_ID,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmJurisdiccion dbo = new DboTmJurisdiccion(conn);
	
	int max = Integer.parseInt(dbo.getMax(dbo.CAMPO_JURIS_ID));
	max++;
	/*
	dbo.setField(dbo.CAMPO_JURIS_ID,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	*/
	
	dbo.clearAll();	
	dbo.setField(dbo.CAMPO_JURIS_ID, ""+max);
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor02());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor03());
	dbo.setField(dbo.CAMPO_USR_CREA, this.usuario.getUserId());
	dbo.setField(dbo.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
	dbo.setField(dbo.CAMPO_TS_ULT_MODIF, null);
	
	dbo.add();
}


}//fin clase

