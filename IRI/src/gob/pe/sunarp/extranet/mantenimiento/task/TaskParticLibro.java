package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskParticLibro extends GrandTask
{
public TaskParticLibro()
{
	super();
	this.nombreTabla = "Participaciones";
	this.nKeys = 2;  //numero de campos llave
	this.cabezas = new String[] {
						"Nombre",
						"Estado"
					};	
	this.ruta = "frmParticLibro";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboParticLibro dbo = new DboParticLibro(conn);
dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_NOMBRE, pag);

Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboParticLibro d = (DboParticLibro) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con n�mero de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_NOMBRE));
		String e = d.getField(d.CAMPO_ESTADO);
		if (e.startsWith("1") || e.startsWith("A"))
			gbean.setValor02("Activo");
		else
			gbean.setValor02("Inactivo");
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_COD_LIBRO));
		gbean.setLlave02(d.getField(d.CAMPO_COD_PARTIC));
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
	DboParticLibro dbo = new DboParticLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getLlave01());
	dbo.setField(dbo.CAMPO_COD_PARTIC,bean.getLlave02());
	
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
		
	bean.setValor01(dbo.getField(dbo.CAMPO_COD_LIBRO));
	bean.setValor02(dbo.getField(dbo.CAMPO_COD_PARTIC));
	bean.setValor03(dbo.getField(dbo.CAMPO_ESTADO));
	bean.setValor04(dbo.getField(dbo.CAMPO_NOMBRE));
}

public void update(GenericBean bean) throws Throwable
{
	DboParticLibro dbo = new DboParticLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getLlave01());
	dbo.setField(dbo.CAMPO_COD_PARTIC,bean.getLlave02());
	
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_ESTADO);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor04());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor03());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboParticLibro dbo = new DboParticLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getLlave01());
	dbo.setField(dbo.CAMPO_COD_PARTIC,bean.getLlave02());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboParticLibro dbo = new DboParticLibro(conn);
	dbo.setField(dbo.CAMPO_COD_LIBRO,bean.getValor01());
	dbo.setField(dbo.CAMPO_COD_PARTIC,bean.getValor02());	
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();
		
	dbo.setField(dbo.CAMPO_COD_LIBRO, bean.getValor01());
	dbo.setField(dbo.CAMPO_COD_PARTIC, bean.getValor02());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor03());
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor04());	
	
	dbo.add();
}


}//fin clase

