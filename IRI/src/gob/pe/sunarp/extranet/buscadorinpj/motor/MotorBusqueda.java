/**
 * 
 */
package gob.pe.sunarp.extranet.buscadorinpj.motor;

import gob.pe.sunarp.extranet.buscadorinpj.beans.ResulBuscadorPJBean;
import gob.pe.sunarp.extranet.buscadorinpj.controller.BuscadorIndicePJController.tipoBusqueda;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author jbugarin
 *
 */
public class MotorBusqueda implements Serializable {
	
	enum tipoBusquedaCampo{
		RAZON_SOC,SIGLAS;
	}
	private static MotorBusqueda motorBusqueda;
	int totalResultadosLucene;
	int inicioPaginacion;
	int finPaginacion;
	
	ArrayList listaResultadoTotal = new ArrayList();
	ArrayList listaPaginada;
	ArrayList listaQuery = new ArrayList();
	
	String[] cadenaBusqueda;
	String tipoBusqueda;
	String criterioBusqueda;
	String costoServicio;
	
	FSDirectory directorio;
	Hits hits;
	IndexSearcher searcher;
	BooleanQuery b = new BooleanQuery();
	

	public IndexSearcher getSearcher() {
		return searcher;
	}

	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}

	public Hits getHits() {
		return hits;
	}

	public void setHits(Hits hits) {
		this.hits = hits;
	}

	public String getCriterioBusqueda() {
		return criterioBusqueda;
	}
	
	public void setCriterioBusqueda(String criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}

	public ArrayList buscaLuceneArchivo() throws IOException, ParseException, NamingException{
		//FSDirectory directory =FSDirectory.getDirectory("c:/lucene_output_dir_index");
		//directorio =FSDirectory.getDirectory("c:/lucene_output_dir_index");
		InitialContext ictx = new InitialContext();
		//Context myenv = (Context)ictx.lookup("java:comp/env");
		//String ruta = (String)myenv.lookup("rutaIndice");
		//System.out.println("Valor de Replicacion:-------->"+ruta);
		//directorio =FSDirectory.getDirectory(ruta); 
		directorio =FSDirectory.getDirectory("/inpj/");
		if ("R".equals(tipoBusqueda)){
			//total de palabras a buscar
			ArrayList list=getQueriesRazSoc(getCadenaBusqueda());
			if (list.size()>0){
				//b = new BooleanQuery();
				for (int i=0;i<list.size();i++){
					Query que=(Query)list.get(i);
					b.add(que, BooleanClause.Occur.MUST);
				}
				b.setMaxClauseCount(2147483647);
				setSearcher(null);
				runQueryAndDisplayResults(directorio, b);
				
			}
		}
		if ("S".equals(tipoBusqueda)){
			ArrayList list=getQueriesSigla(getCadenaBusqueda());
			if (list.size()>0){
				BooleanQuery b = new BooleanQuery();
				//BooleanQuery b = BooleanQuery.TooManyClauses;
				for (int i=0;i<list.size();i++){
					Query que=(Query)list.get(i);
					b.add(que, BooleanClause.Occur.MUST);
				}
				b.setMaxClauseCount(2147483647);
				setSearcher(null);
				runQueryAndDisplayResults(directorio, b);
			}
		}	
				
		return getListaResultadoTotal();
	}
	
	public ArrayList paginacion(){
		
		ArrayList listPag = new ArrayList();
		int total=getListaResultadoTotal().size();
		if (finPaginacion>total)
			finPaginacion=total;
		
		for(getInicioPaginacion(); inicioPaginacion < finPaginacion ;inicioPaginacion++){
			//recorro el arreglo total de resultados para solo recuperar los 15 primeros
			ResulBuscadorPJBean res = new ResulBuscadorPJBean();
			res = (ResulBuscadorPJBean)listaResultadoTotal.get(inicioPaginacion);
			listPag.add(res);
		}	
		listaPaginada = listPag;
		return listaPaginada;
	}

	public String[] getCadenaBusqueda() {
		return cadenaBusqueda;
	}


	public void setCadenaBusqueda(String[] cadenaBusqueda) {
		this.cadenaBusqueda = cadenaBusqueda;
	}


	public int getFinPaginacion() {
		return finPaginacion;
	}

	public void setFinPaginacion(int finPaginacion) {
		this.finPaginacion = finPaginacion;
	}

	public int getInicioPaginacion() {
		return inicioPaginacion;
	}

	public void setInicioPaginacion(int inicioPaginacion) {
		this.inicioPaginacion = inicioPaginacion;
	}

	public ArrayList getListaPaginada() {
		return listaPaginada;
	}

	public void setListaPaginada(ArrayList listaPaginada) {
		this.listaPaginada = listaPaginada;
	}

	public ArrayList getListaResultadoTotal() {
		return listaResultadoTotal;
	}

	public void setListaResultadoTotal(ArrayList listaResultadoTotal) {
		this.listaResultadoTotal = listaResultadoTotal;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public int getTotalResultadosLucene() {
		return totalResultadosLucene;
	}

	public void setTotalResultadosLucene(int totalResultadosLucene) {
		this.totalResultadosLucene = totalResultadosLucene;
	}
	
	static public MotorBusqueda getInstance() throws Exception {
		if (motorBusqueda == null) {
			motorBusqueda=new MotorBusqueda();
		}

		return motorBusqueda;
	}

	public ArrayList getQueriesRazSoc(String[] arg){
		
		int j = 0;  
		   String [] campos = arg;  
		   while(j<campos.length){  
		    System.out.println(campos[j]);     
		    Query query = new WildcardQuery(new Term(tipoBusquedaCampo.RAZON_SOC.toString(), "*"+campos[j]+"*"));
		    listaQuery.add(query);
		    j++;  
		   }  
		
		
		return listaQuery;
		
	}
	
	public ArrayList getQueriesSigla(String[] arg){
		int j = 0;  
		   String [] campos = arg;  
		   while(j<campos.length){  
		    System.out.println(campos[j]);     
		    Query query = new WildcardQuery(new Term(tipoBusquedaCampo.SIGLAS.toString(), "*"+campos[j]+"*"));
		    listaQuery.add(query);
		    j++;  
		   }  
		
		
		return listaQuery;
		
	}

	public ArrayList runQueryAndDisplayResults(Directory indexStore, Query q) 
	throws IOException {
		setSearcher(new IndexSearcher(indexStore));
		setHits(searcher.search(q));
		int _length = hits.length();
		setTotalResultadosLucene(_length);
		System.out.println("HITS: " + _length);
		int tmp = 0;
		if (getTotalResultadosLucene()>20)
			tmp=20;
		else
			tmp=_length;
		
		for (int i = 0; i < tmp; i++) {
			Document d = hits.doc(i);
			ResulBuscadorPJBean res = new ResulBuscadorPJBean();
			if (d.get("RAZON_SOC").toString()!=null && !d.get("RAZON_SOC").toString().equals("")){
				 res.setRazSoc(d.get("RAZON_SOC").toString().trim().toLowerCase());
			 }else{
				 res.setRazSoc("--");
			 }
			 if (d.get("SIGLAS").toString()!=null && !d.get("SIGLAS").toString().equals("")){
				 res.setSigla(d.get("SIGLAS").toString().trim().toLowerCase());
			 }else{
				 res.setSigla("--");
			 }
			 if (d.get("DESC_ESTADO").toString()!=null && !d.get("DESC_ESTADO").toString().equals("")){
				 res.setEstado(d.get("DESC_ESTADO").toString().trim().toLowerCase());
			 }else{
				 res.setEstado("--");
			 }
			 if (d.get("FICHA").toString()!=null && !d.get("FICHA").toString().equals("")){
				 res.setFicha(d.get("FICHA").toString().trim().toLowerCase());
			 }else{
				 res.setFicha("--");
			 }
			 if (d.get("DESC_PAIS").toString()!=null && !d.get("DESC_PAIS").toString().equals("")){
				 res.setNacionalidad(d.get("DESC_PAIS").toString().trim().toLowerCase());
			 }else{
				 res.setNacionalidad("--");
			 }
			 if (d.get("DESC_OFIC").toString()!=null && !d.get("DESC_OFIC").toString().equals("")){
				 res.setOficReg(d.get("DESC_OFIC").toString().trim().toLowerCase());
			 }else{
				 res.setOficReg("--");
			 }
			 if (d.get("NUM_PARTIDA").toString()!=null && !d.get("NUM_PARTIDA").toString().equals("")){
				 res.setPartidaElectronica(d.get("NUM_PARTIDA").toString().trim().toLowerCase());
			 }else{
				 res.setPartidaElectronica("--");
			 }
			 if (d.get("DESC_LIBRO").toString()!=null && !d.get("DESC_LIBRO").toString().equals("")){
				 res.setTipoPersonaJuridica(d.get("DESC_LIBRO").toString().trim().toLowerCase());
			 }else{
				 res.setTipoPersonaJuridica("--");
			 }
			 if (d.get("NUM_TITU").toString()!=null && !d.get("NUM_TITU").toString().equals("")){
				 res.setTitulo(d.get("NUM_TITU").toString().trim().toLowerCase());
			 }else{
				 res.setTitulo("--");
			 }
			 if (d.get("NU_TOMO").toString()!=null && !d.get("NU_TOMO").toString().equals("")){
				 res.setTomoFolio(d.get("NU_TOMO").toString().trim().toLowerCase());
			 }else{
				 res.setTomoFolio("--");
			 }
			 listaResultadoTotal.add(res);
			
		}
		//searcher.close();
		System.out.println(" total: " + hits.length());
		
		return listaResultadoTotal;
	}
	
	public ArrayList runQueryAndDisplayResultsPag(Directory indexStore, Query q) 
	throws IOException {
		
		int total=getTotalResultadosLucene();
		if (finPaginacion>total)
			finPaginacion=total;
		ArrayList list = new ArrayList();
		searcher = getSearcher();
		hits = getHits();
		//int _length = hits.length();
		//setTotalResultadosLucene(_length);
		//System.out.println("HITS: " + _length);
		for (getInicioPaginacion(); inicioPaginacion < finPaginacion; inicioPaginacion++) {
			Document d = hits.doc(inicioPaginacion);
			ResulBuscadorPJBean res = new ResulBuscadorPJBean();
			if (d.get("RAZON_SOC").toString()!=null && !d.get("RAZON_SOC").toString().equals("")){
				 res.setRazSoc(d.get("RAZON_SOC").toString().trim().toLowerCase());
			 }else{
				 res.setRazSoc("--");
			 }
			 if (d.get("SIGLAS").toString()!=null && !d.get("SIGLAS").toString().equals("")){
				 res.setSigla(d.get("SIGLAS").toString().trim().toLowerCase());
			 }else{
				 res.setSigla("--");
			 }
			 if (d.get("DESC_ESTADO").toString()!=null && !d.get("DESC_ESTADO").toString().equals("")){
				 res.setEstado(d.get("DESC_ESTADO").toString().trim().toLowerCase());
			 }else{
				 res.setEstado("--");
			 }
			 if (d.get("FICHA").toString()!=null && !d.get("FICHA").toString().equals("")){
				 res.setFicha(d.get("FICHA").toString().trim().toLowerCase());
			 }else{
				 res.setFicha("--");
			 }
			 if (d.get("DESC_PAIS").toString()!=null && !d.get("DESC_PAIS").toString().equals("")){
				 res.setNacionalidad(d.get("DESC_PAIS").toString().trim().toLowerCase());
			 }else{
				 res.setNacionalidad("--");
			 }
			 if (d.get("DESC_OFIC").toString()!=null && !d.get("DESC_OFIC").toString().equals("")){
				 res.setOficReg(d.get("DESC_OFIC").toString().trim().toLowerCase());
			 }else{
				 res.setOficReg("--");
			 }
			 if (d.get("NUM_PARTIDA").toString()!=null && !d.get("NUM_PARTIDA").toString().equals("")){
				 res.setPartidaElectronica(d.get("NUM_PARTIDA").toString().trim().toLowerCase());
			 }else{
				 res.setPartidaElectronica("--");
			 }
			 if (d.get("DESC_LIBRO").toString()!=null && !d.get("DESC_LIBRO").toString().equals("")){
				 res.setTipoPersonaJuridica(d.get("DESC_LIBRO").toString().trim().toLowerCase());
			 }else{
				 res.setTipoPersonaJuridica("--");
			 }
			 if (d.get("NUM_TITU").toString()!=null && !d.get("NUM_TITU").toString().equals("")){
				 res.setTitulo(d.get("NUM_TITU").toString().trim().toLowerCase());
			 }else{
				 res.setTitulo("--");
			 }
			 if (d.get("NU_TOMO").toString()!=null && !d.get("NU_TOMO").toString().equals("")){
				 res.setTomoFolio(d.get("NU_TOMO").toString().trim().toLowerCase());
			 }else{
				 res.setTomoFolio("--");
			 }
			 list.add(res);
			
		}
		//searcher.close();
		System.out.println(" total: " + hits.length());
		listaPaginada = list;
		return listaPaginada;
	}


	public static MotorBusqueda getMotorBusqueda() {
		return motorBusqueda;
	}


	public static void setMotorBusqueda(MotorBusqueda motorBusqueda) {
		MotorBusqueda.motorBusqueda = motorBusqueda;
	}

	public FSDirectory getDirectorio() {
		return directorio;
	}

	public void setDirectorio(FSDirectory directorio) {
		this.directorio = directorio;
	}

	public BooleanQuery getB() {
		return b;
	}

	public void setB(BooleanQuery b) {
		this.b = b;
	}

	public String getCostoServicio() {
		return costoServicio;
	}

	public void setCostoServicio(String costoServicio) {
		this.costoServicio = costoServicio;
	}


}
