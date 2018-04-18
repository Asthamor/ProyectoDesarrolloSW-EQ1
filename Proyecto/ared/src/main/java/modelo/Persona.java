/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IPersona;
import java.util.List;

/**
 *
 * @author alonso
 */
public abstract class Persona implements IPersona {

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String imgFoto;
    protected String tipoUsario;
    
    

    @Override
    public abstract List<Persona> obtenerTodos();

    @Override
    public abstract boolean actualizarDatos();

    @Override
    public abstract List<Persona> buscar(String nombre);

    @Override
    public abstract boolean registrar(Persona persona);

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(String imgFoto) {
        this.imgFoto = imgFoto;
    }

    public String getTipoUsario() {
        return tipoUsario;
    }  
    
    public String obtenerImagen() {
        return imgFoto;
    }

}
