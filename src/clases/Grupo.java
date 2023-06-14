/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author WIN 10
 */
public class Grupo {
    private int idGrupo;
    private String nombre;
    private String descripcion; 
    private int idArticulo;
    private int idColeccion;
    private String condicionVenta; //inactivo
    private String condicionInter; //inactivo
    private float precio; //inactivo
    private float costo;

    public Grupo(String nombre) {
        this.nombre = nombre;
    }

    public Grupo(){
        this.idGrupo = 0;
        this.nombre = "";
        this.descripcion = "";
        this.idArticulo = 0;
        this.idColeccion = 0;
        this.condicionVenta = "";
        this.condicionInter = "";
        this.precio = 0;
        this.costo = 0;
    }

    public Grupo(int idGrupo, String nombre, String descripcion, int idArticulo, int idColeccion, String condicionVenta, String condicionInter, int precio, int costo) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idArticulo = idArticulo;
        this.idColeccion = idColeccion;
        this.condicionVenta = condicionVenta;
        this.condicionInter = condicionInter;
        this.precio = precio;
        this.costo = costo;
    }
    

      
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(int idColeccion) {
        this.idColeccion = idColeccion;
    }

    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public String getCondicionInter() {
        return condicionInter;
    }

    public void setCondicionInter(String condicionInter) {
        this.condicionInter = condicionInter;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
    
    
    
}
