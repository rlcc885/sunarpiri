package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import gob.pe.sunarp.extranet.framework.Loggy;
import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmTipoCarroceria extends GrandTask
{
public TaskTmTipoCarroceria()
{	
	super();
	this.nombreTabla = "Carrocería";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Código",
						"Descripción"						
					};	
	this.ruta = "frmTmTipoCarroceria";
}


public FormOutputListado getList(int pag) throws Throwable
{
DboTmTipoCarr dbo = new DboTmTipoCarr(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_COD_TIPO_CARR, pag);

Vector res = new Vector();

// - LLama al objeto de basede datos para el area registral
// DboTmAreaRegistral dbo1 = new DboTmAreaRegistral(conn);

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmTipoCarr d = (DboTmTipoCarr) arri.get(i);
		GenericBean gbean = new GenericBean();

		//_valores para pintar - Debe coincidir con número de elementos del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_COD_TIPO_CARR));
		gbean.setValor02(d.getField(d.CAMPO_DESCRIPCION));
									
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_COD_TIPO_CARR));
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
	DboTmTipoCarr dbo = new DboTmTipoCarr(conn);
	dbo.setField(dbo.CAMPO_COD_TIPO_CARR,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_COD_TIPO_CARR));
	bean.setValor02(dbo.getField(dbo.CAMPO_DESCRIPCION));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmTipoCarr dbo = new DboTmTipoCarr(conn);
	dbo.setField(dbo.CAMPO_COD_TIPO_CARR,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_DESCRIPCION);	
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor02());
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmTipoCarr dbo = new DboTmTipoCarr(conn);
	dbo.setField(dbo.CAMPO_COD_TIPO_CARR,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmTipoCarr dbo = new DboTmTipoCarr(conn);
	dbo.setField(dbo.CAMPO_COD_TIPO_CARR,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();		
	dbo.setField(dbo.CAMPO_COD_TIPO_CARR, bean.getValor01());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor02());	
	dbo.add();
}
	public boolean isTrace(Object _this) {
		return Loggy.isTrace(_this);
	}

}//fin clase

