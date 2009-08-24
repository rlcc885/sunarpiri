package gob.pe.sunarp.extranet.util;


//paquetes del sistema
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.misc.*;

//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.prepago.bean.AbonoBean;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.framework.session.*;


public class Abono extends SunarpBean{
	
	private AbonoBean abono = new AbonoBean();
	public boolean isTrace(Object _this) 
	{
		return Loggy.isTrace(_this);
	}
	
	public ComprobanteBean efectuaAbono(Connection conn, UsuarioBean usuarioBean) throws Throwable 
	{
		DBConnection dconn = new DBConnection(conn);
		
		boolean paso1 = true;
		StringTokenizer stk = null;
		String tipoPago = null;
		String aux_TipPag = null;
		
		try{
			stk = new StringTokenizer(abono.getTipoPago(), "|");
			tipoPago = stk.nextToken();
			paso1 = false;
			aux_TipPag = stk.nextToken();
			paso1 = true;
		}
		catch(Exception e1)
		{
			if(paso1)
				throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el tipo de pago.", "errorPrepago");
			else
				throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el nombre del tipo de pago.", "errorPrepago");
		}
		boolean esCheque = false;
					
		if(tipoPago.equals("C"))
			esCheque = true;
		
		
		String bancoId = null;
		String aux_Banco = null;
			
		if(esCheque)
		{	
			try
			{
		        stk = new StringTokenizer(abono.getBancoId(), "|");
		        bancoId = stk.nextToken();
		        paso1 = false;
				aux_Banco = stk.nextToken();
				paso1 = true;
			}
			catch(Exception e2)
			{
				if(paso1)
					throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el id. del banco.", "errorPrepago");
				else
					throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el nombre del banco.", "errorPrepago");
			}
		}
		
		String tipoCheque = null;
		String aux_TipCheque = null;
			
		if(esCheque)
		{
			try
			{
				stk = new StringTokenizer(abono.getTipoCheque(), "|");
				tipoCheque = stk.nextToken();
				paso1 = false;
				aux_TipCheque = stk.nextToken();
			}
			catch(Exception e3)
			{
				if(paso1)
					throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el tipo de cheque.", "errorPrepago");
				else
					throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener la descripcion del cheque.", "errorPrepago");
			}
		}
		
		String numCheque  = null;
		
		if(esCheque)
		{
			if(abono.getNumCheque() == null || abono.getNumCheque().trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar el número de cheque.", "errorPrepago");
				
			numCheque  = abono.getNumCheque();
		}
		PrepagoBean prepagoBean = new PrepagoBean();
		if(abono.getConcAbono()!=null && abono.getConcAbono().equals(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA))
			prepagoBean.setTipoConsAbono(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA);
/*Monto bruto*/	prepagoBean.setMontoBruto(Double.parseDouble(abono.getMonto_bruto())); // Monto ing. por el cliente
/*Medio Id*/	//prepagoBean.setMedioId(medioId);
					
/*Linea Prepago Id*/prepagoBean.setLineaPrepagoId(abono.getLineaPrePago());
/*Ventanilla*/		prepagoBean.setFlag_ventan(true);
					
	
		if(tipoPago.equals("E")){
/*EFECTIVO*/		prepagoBean.setFlag_efectivo(true);
/*NO VA*/			prepagoBean.setBancoId(null);
/*NO VA*/			prepagoBean.setTipoCheque(null);
/*NO VA*/			prepagoBean.setNumCheuqe(null);
		}else{
/*CHEQUE*/			prepagoBean.setFlag_efectivo(false);
/*Banco Id*/		prepagoBean.setBancoId(bancoId);
/*Tipo Cheque*/		prepagoBean.setTipoCheque(tipoCheque);
/*Num Cheque*/		prepagoBean.setNumCheuqe(numCheque);
		}
	
/*Usuario*/		prepagoBean.setUsuario(abono.getUsuario());
	
		// Incrementa Saldo del cliente				
		LineaPrepago lineaCmd = new LineaPrepago();
		ComprobanteBean beancomp = lineaCmd.incrementaSaldo(usuarioBean, prepagoBean, dconn);
					
		beancomp.setContratoId(abono.getContratoId());
		beancomp.setTipoPago(aux_TipPag);
		
		beancomp.setCajero(usuarioBean.getUserId());
		
		StringBuffer q = new StringBuffer();
		q.append("SELECT nombre FROM ofic_registral WHERE reg_pub_id= '").append(usuarioBean.getRegPublicoId()).append("' AND ofic_reg_id='").append(usuarioBean.getOficRegistralId()).append("'");
		if (isTrace(this)) System.out.println("Oficina Reg QUERY ---> "+q.toString());
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(q.toString());
		rset.next();
			
		beancomp.setOficina(rset.getString("nombre"));
		rset.close();
		stmt.close();
					
		if(tipoPago.equals("E"))
		{
			beancomp.setTipoCheque(null);
			beancomp.setBanco(null);
			beancomp.setNumcheque(null);
			
		}
		else
		{
			beancomp.setTipoCheque(aux_TipCheque);
			beancomp.setBanco(aux_Banco);
			//beancomp.setNumcheque(aux_TipPag);
			
		}
		
		
		return beancomp;
		
	}
	

	/**
	 * Gets the abono
	 * @return Returns a AbonoBean
	 */
	public AbonoBean getAbono() {
		return abono;
	}
	/**
	 * Sets the abono
	 * @param abono The abono to set
	 */
	public void setAbono(AbonoBean abono) {
		this.abono = abono;
	}

}

