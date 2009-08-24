package gob.pe.sunarp.extranet.mantenimiento.task;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class TaskRegisPublico extends GrandTask
{
public TaskRegisPublico()
{
	super();
	this.nombreTabla = "Oficinas Zonales";
	this.nKeys = 1;  //numero de campos llave
	this.cabezas = new String[] {
						"Codigo",
						"Nombre",
						"Prefijo"
					};	
	this.ruta = "frmRegisPublico";
}

public FormOutputListado getList(int pag) throws Throwable
{
DboRegisPublico dbo = new DboRegisPublico(conn);
dbo.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
ArrayList arri = dbo.searchAndRetrieveListPaginado(dbo.CAMPO_REG_PUB_ID, pag);

Vector res = new Vector();

for (int i = 0; i < arri.size(); ++i )
	{
		DboRegisPublico d = (DboRegisPublico) arri.get(i);
		
		GenericBean gbean = new GenericBean();
		
		//_valores para pintar - Debe coincidir con número de elementos
		//                       del arreglo "Cabezas"
		gbean.setValor01(d.getField(d.CAMPO_REG_PUB_ID));
		gbean.setValor02(d.getField(d.CAMPO_NOMBRE));
		gbean.setValor03(d.getField(d.CAMPO_SIGLAS));
			
		//_llaves
		gbean.setLlave01(d.getField(d.CAMPO_REG_PUB_ID));
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
	DboRegisPublico dbo = new DboRegisPublico(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	bean.setValor01(dbo.getField(dbo.CAMPO_REG_PUB_ID));
	bean.setValor02(dbo.getField(dbo.CAMPO_NOMBRE));
	bean.setValor03(dbo.getField(dbo.CAMPO_SIGLAS));
}

public void update(GenericBean bean) throws Throwable
{
	DboRegisPublico dbo = new DboRegisPublico(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	if (dbo.find()==false)
		throw new IllegalArgumentException("Error accediendo a base de datos");
	
	dbo.setFieldsToUpdate(dbo.CAMPO_NOMBRE+"|"+
						  dbo.CAMPO_SIGLAS+"|"+
						  dbo.CAMPO_USR_ULT_MODIF+"|"+
						  dbo.CAMPO_TS_ULT_MODIF);
	
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor02());
	dbo.setField(dbo.CAMPO_SIGLAS, bean.getValor03());
	dbo.setField(dbo.CAMPO_USR_ULT_MODIF,  this.usuario.getUserId());
	dbo.setField(dbo.CAMPO_TS_ULT_MODIF, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));

	dbo.update();
}

public void delete(GenericBean bean) throws Throwable
{
	DboRegisPublico dbo = new DboRegisPublico(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getLlave01());
	boolean b = dbo.find();
	if (b==true)
		dbo.delete();	
}

public void insert(GenericBean bean) throws Throwable
{
	DboRegisPublico dbo = new DboRegisPublico(conn);
	dbo.setField(dbo.CAMPO_REG_PUB_ID,bean.getValor01());
	if (dbo.find())
		throw new ValidacionException("LLave de tabla no puede duplicarse","");
	dbo.clearAll();
		
	dbo.setField(dbo.CAMPO_REG_PUB_ID, bean.getValor01());
	dbo.setField(dbo.CAMPO_NOMBRE, bean.getValor02());
	dbo.setField(dbo.CAMPO_SIGLAS, bean.getValor03());
	dbo.setField(dbo.CAMPO_USR_CREA,  this.usuario.getUserId());
	dbo.setField(dbo.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
	dbo.setField(dbo.CAMPO_TS_ULT_MODIF, null);
	
	dbo.add();
}


}//fin clase

