/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Persona;

/**
 *
 * @author alonso
 */
public interface IPersona {
    public List<Persona> obtenerTodos();
    public boolean actualizarDatos();
    public boolean actualizarDatos(Persona persona);
    public List<Persona> buscar(String nombre);
    public boolean registrar(Persona persona);
}
