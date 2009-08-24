package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import gob.pe.sunarp.extranet.framework.Loggy;
import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskTmModelo extends GrandTask
{
public TaskTmModelo()
{
	super();
	this.nombreTabla = "Modelos";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Código",
						"Descripción",
						"Marca"
					};	
	this.ruta = "frmTmModelo";
}


public FormOutputListado getList(int pag) throws Throwable
{
DboTmModeloVehi dbo = new DboTmModeloVehi(conn);

dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_COD_MODELO, pag);

Vector res = new Vector();

// - LLama al objeto de basede datos para el area registral
// DboTmAreaRegistral dbo1 = new DboTmAreaRegistral(conn);

for (int i = 0; i < arri.size(); ++i )
	{
		DboTmModeloVehi d = (DboTmModeloVehi) arri.get(i);
		DboTmMarcaVehi dbo1 = new DboTmMarcaVehi();
		GenericBean gbean = new GenericBean();
		if (isTrace(this)) System.out.println("En la class TaskModelo - metodo getList");
		//_valores para pintar - Debe coincidir con número de elementos del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_COD_MODELO));
		gbean.setValor02(d.getField(d.CAMPO_DESCRIPCION));
				
		//_marca
		String marca = d.getField(d.CAMPO_COD_MARCA);
		dbo1.clearAll();
		dbo1.setFieldsToRetrieve(dbo1.CAMPO_DESCRIPCION);
		dbo1.setField(dbo1.CAMPO_COD_MARCA,marca);
		if (dbo1.find())
			gbean.setValor03(dbo1.getField(dbo1.CAMPO_DESCRIPCION));
		else
			gbean.setValor03("---");
								
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_COD_MODELO));
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
	DboTmModeloVehi dbo = new DboTmModeloVehi(conn);
	dbo.setField(dbo.CAMPO_COD_MODELO,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_COD_MODELO));
	bean.setValor02(dbo.getField(dbo.CAMPO_DESCRIPCION));
	bean.setValor03(dbo.getField(dbo.CAMPO_COD_MARCA));
}

public void update(GenericBean bean) throws Throwable
{
	DboTmModeloVehi dbo = new DboTmModeloVehi(conn);
	dbo.setField(dbo.CAMPO_COD_MODELO,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_DESCRIPCION+"|"+
						  dbo.CAMPO_COD_MARCA);	
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor02());
	dbo.setField(dbo.CAMPO_COD_MARCA, bean.getValor03());
	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboTmModeloVehi dbo = new DboTmModeloVehi(conn);
	dbo.setField(dbo.CAMPO_COD_MODELO,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboTmModeloVehi dbo = new DboTmModeloVehi(conn);
	dbo.setField(dbo.CAMPO_COD_MODELO,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();		
	dbo.setField(dbo.CAMPO_COD_MODELO, bean.getValor01());
	dbo.setField(dbo.CAMPO_DESCRIPCION, bean.getValor02());
	dbo.setField(dbo.CAMPO_COD_MARCA, bean.getValor03());
	
	dbo.add();
}
	public boolean isTrace(Object _this) {
		return Loggy.isTrace(_this);
	}

}//fin clase

