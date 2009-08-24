package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmInstSir extends GrandTask
{
public TaskTmInstSir()
{
	super();
	this.nombreTabla = "Notarias";
	this.nKeys = 3;  //numero de campos llave
	this.cabezas = new String[] {
						"Nombre"
					};	
	this.ruta = "frmNotaria";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmInstSir dbo = new DboTmInstSir(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_NOMBRE_INST, pag);
Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmInstSir d = (DboTmInstSir) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_NOMBRE_INST));			

		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_REG_PUB_ID));
		gbean.setLlave02(d.getField(d.CAMPO_OFIC_REG_ID));
		gbean.setLlave03(d.getField(d.CAMPO_CUR_PRES));
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
	DboTmInstSir dbo = new DboTmInstSir(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_CUR_PRES,bean.getLlave03());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");

	String ofi = dbo.getField(dbo.CAMPO_REG_PUB_ID)+"|"+dbo.getField(dbo.CAMPO_OFIC_REG_ID);
	bean.setValor01(ofi);
	bean.setValor03(dbo.getField(dbo.CAMPO_CUR_PRES));
	bean.setValor04(dbo.getField(dbo.CAMPO_NOMBRE_INST));
	bean.setValor05(dbo.getField(dbo.CAMPO_SIGLAS));
	bean.setValor06(dbo.getField(dbo.CAMPO_PE_JURI_ID));
	/*
	String s7 = dbo.getField(dbo.CAMPO_TS_ULT_SYNC);
	s7 = FechaUtil.expressoDateTimeToUtilDateTime(s7);
	bean.setValor07(s7);
	bean.setValor08(dbo.getField(dbo.CAMPO_AGNT_SYNC));
	*/
}

public void update(GenericBean bean) throws Throwable
{
	DboTmInstSir dbo = new DboTmInstSir(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_CUR_PRES,bean.getLlave03());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE_INST+"|"+
						  dbo.CAMPO_SIGLAS+"|"+
						  dbo.CAMPO_PE_JURI_ID);
	
	dbo.setField(dbo.CAMPO_NOMBRE_INST, bean.getValor04());
	dbo.setField(dbo.CAMPO_SIGLAS, bean.getValor05());
	dbo.setField(dbo.CAMPO_PE_JURI_ID, bean.getValor06());
	
	//validar id persona jurídica
	DboPeJuri dboPeJuri = new DboPeJuri(conn);
	dboPeJuri.setField(dboPeJuri.CAMPO_PE_JURI_ID, bean.getValor06());
	if (dboPeJuri.find()==false)
		throw new ValidacionException("Id de Persona Jurídica no existe","val06");
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmInstSir dbo = new DboTmInstSir(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_CUR_PRES,bean.getLlave03());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmInstSir dbo = new DboTmInstSir(conn);
	//El valor 06 tiene reg+ofic   "09|11"
	String res[] = Tarea.convierteTiraEnArreglo(bean.getValor01(),"|");
	
	dbo.setField(dbo.CAMPO_REG_PUB_ID,res[0]);
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,res[1]);
	dbo.setField(dbo.CAMPO_CUR_PRES,bean.getLlave03());	
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();
	

	dbo.setField(dbo.CAMPO_REG_PUB_ID,res[0]);
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,res[1]);
		
	dbo.setField(dbo.CAMPO_CUR_PRES, bean.getValor03());
	dbo.setField(dbo.CAMPO_NOMBRE_INST, bean.getValor04());
	dbo.setField(dbo.CAMPO_SIGLAS, bean.getValor05());
	dbo.setField(dbo.CAMPO_PE_JURI_ID, bean.getValor06());	
	//dbo.setField(dbo.CAMPO_TS_ULT_SYNC, FechaUtil.stringTimeToOracleString(FechaUtil.getCurrentDateTime()));
	dbo.setField(dbo.CAMPO_TS_ULT_SYNC, null);
	//dbo.setField(dbo.CAMPO_AGNT_SYNC, bean.getValor08());	
	
	//validar id persona jurídica
	DboPeJuri dboPeJuri = new DboPeJuri(conn);
	dboPeJuri.setField(dboPeJuri.CAMPO_PE_JURI_ID, bean.getValor06());
	if (dboPeJuri.find()==false)
		throw new ValidacionException("Id de Persona Jurídica no existe","val06");	
		
	dbo.add();
}

}//fin clase

