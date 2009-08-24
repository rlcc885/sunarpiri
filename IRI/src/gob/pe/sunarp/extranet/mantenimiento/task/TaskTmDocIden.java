package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmDocIden extends GrandTask
{
public TaskTmDocIden()
{
	super();
	this.nombreTabla = "Documentos de Identidad";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Descripcion",
						"Estado"
					};	
	this.ruta = "frmTmDocIden";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboTmDocIden dbo = new DboTmDocIden(conn);
dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_DESCRIPCION, pag);

Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmDocIden d = (DboTmDocIden) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_DESCRIPCION));
		String e = d.getField(d.CAMPO_ESTADO);
		if (e.startsWith("1") || e.startsWith("A"))
			gbean.setValor02("Activo");
		else
			gbean.setValor02("Inactivo");
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_TIPO_DOC_ID));
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
	DboTmDocIden dbo = new DboTmDocIden(conn);
	dbo.setField(dbo.CAMPO_TIPO_DOC_ID,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_TIPO_DOC_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_NOMBRE_ABREV));
	bean.setValor03(dbo.getField(dbo.CAMPO_TIPO_PER));
	bean.setValor04(dbo.getField(dbo.CAMPO_ESTADO));
	bean.setValor05(dbo.getField(dbo.CAMPO_DESCRIPCION));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmDocIden dbo = new DboTmDocIden(conn);
	dbo.setField(dbo.CAMPO_TIPO_DOC_ID,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE_ABREV+"|"+
						  dbo.CAMPO_TIPO_PER+"|"+
						  dbo.CAMPO_ESTADO+"|"+
						  dbo.CAMPO_DESCRIPCION);
	
	dbo.setField(dbo.CAMPO_NOMBRE_ABREV, bean.getValor02());
	dbo.setField(dbo.CAMPO_TIPO_PER, bean.getValor03());
	dbo.setField(dbo.CAMPO_ESTADO,  bean.getValor04());
	dbo.setField(dbo.CAMPO_DESCRIPCION,  bean.getValor05());
	
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmDocIden dbo = new DboTmDocIden(conn);
	dbo.setField(dbo.CAMPO_TIPO_DOC_ID,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmDocIden dbo = new DboTmDocIden(conn);
	
	String max = dbo.getMax(dbo.CAMPO_TIPO_DOC_ID);
	int nmax = Integer.parseInt(max);
	nmax++;
	
	String nuevoId = Tarea.rellenaIzq(""+nmax,"0",2);
	
	
	/*
	dbo.setField(dbo.CAMPO_TIPO_DOC_ID,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	*/
	dbo.clearAll();
		
	dbo.setField(dbo.CAMPO_TIPO_DOC_ID, nuevoId);
	dbo.setField(dbo.CAMPO_NOMBRE_ABREV, bean.getValor02());
	dbo.setField(dbo.CAMPO_TIPO_PER, bean.getValor03());
	dbo.setField(dbo.CAMPO_ESTADO, bean.getValor04());	
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor05());	
	
	dbo.add();
}


}//fin clase

