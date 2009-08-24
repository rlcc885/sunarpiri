/*
 * Usuario.java
 *
 * Created on 17 de mayo de 2006, 11:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/**
 *
 * @author Christian
 */
public class Usuario extends SunarpBean{
    
    
    /** Creates a new instance of Usuario */
    public Usuario() {
    }

    /**
     * Holds value of property nombre.
     */
    private String nombre;

    /**
     * Getter for property nombre.
     * @return Value of property nombre.
     */
    public String getNombre() {

        return this.nombre;
    }

    /**
     * Setter for property nombre.
     * @param nombre New value of property nombre.
     */
    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    /**
     * Holds value of property docidentidad.
     */
    private String docidentidad;

    /**
     * Getter for property docIdentidad.
     * @return Value of property docIdentidad.
     */
    public String getDocidentidad() {

        return this.docidentidad;
    }

    /**
     * Setter for property docIdentidad.
     * @param docIdentidad New value of property docIdentidad.
     */
    public void setDocidentidad(String docidentidad) {

        this.docidentidad = docidentidad;
    }

    /**
     * Holds value of property ape_paterno.
     */
    private String ape_paterno;

    /**
     * Getter for property ape_paterno.
     * @return Value of property ape_paterno.
     */
    public String getApe_paterno() {

        return this.ape_paterno;
    }

    /**
     * Setter for property ape_paterno.
     * @param ape_paterno New value of property ape_paterno.
     */
    public void setApe_paterno(String ape_paterno) {

        this.ape_paterno = ape_paterno;
    }

    /**
     * Holds value of property ape_materno.
     */
    private String ape_materno;

    /**
     * Getter for property ape_materno.
     * @return Value of property ape_materno.
     */
    public String getApe_materno() {

        return this.ape_materno;
    }

    /**
     * Setter for property ape_materno.
     * @param ape_materno New value of property ape_materno.
     */
    public void setApe_materno(String ape_materno) {

        this.ape_materno = ape_materno;
    }

    /**
     * Holds value of property lugar.
     */
    private String lugar;

    /**
     * Getter for property lugar.
     * @return Value of property lugar.
     */
    public String getLugar() {
        return this.lugar;
    }

    /**
     * Setter for property lugar.
     * @param lugar New value of property lugar.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Holds value of property num_partida.
     */
    private String num_partida;

    /**
     * Getter for property num_partida.
     * @return Value of property num_partida.
     */
    public String getNum_partida() {
        return this.num_partida;
    }

    /**
     * Setter for property num_partida.
     * @param num_partida New value of property num_partida.
     */
    public void setNum_partida(String num_partida) {
        this.num_partida = num_partida;
    }

    /**
     * Holds value of property num_titu.
     */
    private String num_titu;

    /**
     * Getter for property num_titu.
     * @return Value of property num_titu.
     */
    public String getNum_titu() {
        return this.num_titu;
    }

    /**
     * Setter for property num_titu.
     * @param num_titu New value of property num_titu.
     */
    public void setNum_titu(String num_titu) {
        this.num_titu = num_titu;
    }

    /**
     * Holds value of property tipo.
     */
    private String tipo;

    /**
     * Getter for property tipo.
     * @return Value of property tipo.
     */
    public String getTipo() {
        return this.tipo;
    }

    /**
     * Setter for property tipo.
     * @param tipo New value of property tipo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Holds value of property observaciones.
     */
    private String observaciones;

    /**
     * Getter for property observaciones.
     * @return Value of property observaciones.
     */
    public String getObservaciones() {
        return this.observaciones;
    }

    /**
     * Setter for property observaciones.
     * @param observaciones New value of property observaciones.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Holds value of property sql.
     */
    private String sql;

    /**
     * Getter for property sql.
     * @return Value of property sql.
     */
    public String getSql() {
        return this.sql;
    }

    /**
     * Setter for property sql.
     * @param sql New value of property sql.
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * Holds value of property codigo_registral.
     */
    private String codigo_registral;

    /**
     * Getter for property codigo_registral.
     * @return Value of property codigo_registral.
     */
    public String getCodigo_registral() {
        return this.codigo_registral;
    }

    /**
     * Setter for property codigo_registral.
     * @param codigo_registral New value of property codigo_registral.
     */
    public void setCodigo_registral(String codigo_registral) {
        this.codigo_registral = codigo_registral;
    }

    /**
     * Holds value of property zona.
     */
    private String zona;

    /**
     * Getter for property zona.
     * @return Value of property zona.
     */
    public String getZona() {
        return this.zona;
    }

    /**
     * Setter for property zona.
     * @param zona New value of property zona.
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * Holds value of property descripcion_libro.
     */
    private String descripcion_libro;

    /**
     * Getter for property descripcion_libro.
     * @return Value of property descripcion_libro.
     */
    public String getDescripcion_libro() {
        return this.descripcion_libro;
    }

    /**
     * Setter for property descripcion_libro.
     * @param descripcion_libro New value of property descripcion_libro.
     */
    public void setDescripcion_libro(String descripcion_libro) {
        this.descripcion_libro = descripcion_libro;
    }

    /**
     * Holds value of property estado.
     */
    private String estado;

    /**
     * Getter for property estado.
     * @return Value of property estado.
     */
    public String getEstado() {
        return this.estado;
    }

    /**
     * Setter for property estado.
     * @param estado New value of property estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
