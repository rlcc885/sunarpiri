package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmProvincia extends GrandTask
{
public TaskTmProvincia()
{
	super();
	this.nombreTabla = "Provincias";
	this.nKeys = 3;  //numero de campos llave
	this.cabezas = new String[] {
						"Nombre",
						"Estado"
					};	
	this.ruta = "frmTmProvincia";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmProvincia dbo = new DboTmProvincia(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_NOMBRE, pag);
Vector res = new Vector();

//DboTmPais dbo1 = new DboTmPais(conn);

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmProvincia d = (DboTmProvincia) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
	/*
		String pais = d.getField(d.CAMPO_PAIS_ID);
		dbo1.clearAll();
		dbo1.setFieldsToRetrieve(dbo1.CAMPO_NOMBRE);
		dbo1.setField(dbo1.CAMPO_PAIS_ID,pais);
		if (dbo1.find())
			gbean.setValor01(dbo1.getField(dbo1.CAMPO_NOMBRE));
		else
			gbean.setValor01("---");
			*/

		gbean.setValor01(d.getField(d.CAMPO_NOMBRE));			

		String e = d.getField(d.CAMPO_ESTADO);
		if (e.startsWith("1") || e.startsWith("A"))
			gbean.setValor02("Activo");
		else
			gbean.setValor02("Inactivo");
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_PAIS_ID));
		gbean.setLlave02(d.getField(d.CAMPO_DPTO_ID));
		gbean.setLlave03(d.getField(d.CAMPO_PROV_ID));
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
	DboTmProvincia dbo = new DboTmProvincia(conn);
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getLlave03());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_PAIS_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_DPTO_ID));
	bean.setValor03(dbo.getField(dbo.CAMPO_PROV_ID));
	bean.setValor04(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor05(dbo.getField(dbo.CAMPO_ESTADO));
	String ofi = dbo.getField(dbo.CAMPO_REG_PUB_ID)+"|"+dbo.getField(dbo.CAMPO_OFIC_REG_ID);
	bean.setValor06(ofi);

}

public void update(GenericBean bean) throws Throwable
{
	DboTmProvincia dbo = new DboTmProvincia(conn);
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getLlave03());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_ESTADO+"|"+
						  dbo.CAMPO_REG_PUB_ID+"|"+
						  dbo.CAMPO_OFIC_REG_ID);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor04());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor05());
	
	//El valor 06 tiene reg+ofic   "09|11"
	String res[] = Tarea.convierteTiraEnArreglo(bean.getValor06(),"|");
	dbo.setField(dbo.CAMPO_REG_PUB_ID,res[0]);
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,res[1]);
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmProvincia dbo = new DboTmProvincia(conn);
	dbo.setField(dbo.CAMPO_PAIS_ID,bean.getLlave01());
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getLlave02());
	dbo.setField(dbo.CAMPO_PROV_ID,bean.getLlave03());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmProvincia dbo = new DboTmProvincia(conn);
	dbo.setField(dbo.CAMPO_PAIS_ID,"01");
	dbo.setField(dbo.CAMPO_DPTO_ID,bean.getValor02());
	//dbo.setField(dbo.CAMPO_PROV_ID,bean.getValor03());	
	
	ArrayList arri = dbo.searchAndRetrieveList(dbo.CAMPO_PROV_ID+" DESC");
	
	int nmax =0;
	if (arri.size()>0)
	{
		DboTmProvincia d = (DboTmProvincia) arri.get(0);
		String max = d.getField(DboTmProvincia.CAMPO_PROV_ID);
		nmax = Integer.parseInt(max);
		nmax++;
	}
	
	String nuevoId = Tarea.rellenaIzq(""+nmax,"0",2);
	
	/*
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	*/
	dbo.clearAll();		
		
	dbo.setField(dbo.CAMPO_PAIS_ID, "01");
	dbo.setField(dbo.CAMPO_DPTO_ID, bean.getValor02());

	dbo.setField(dbo.CAMPO_PROV_ID, nuevoId);
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor04());	
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor05());	
	
	//El valor 06 tiene reg+ofic   "09|11"
	String res[] = Tarea.convierteTiraEnArreglo(bean.getValor06(),"|");
	dbo.setField(dbo.CAMPO_REG_PUB_ID,res[0]);
	dbo.setField(dbo.CAMPO_OFIC_REG_ID,res[1]);
		
	dbo.add();
}

}//fin clase

