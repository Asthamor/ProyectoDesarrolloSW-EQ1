/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Grupo;
import modelo.Maestro;

/**
 *
 * @author mauricio
 */
public interface IMaestro extends IPersona {
    public Maestro obtenerDatos(String maestro);
    public List<Maestro> obtenerActivos();
    public boolean obtenerEstado();
    public String obtenerImagen();
    public List<Grupo> obtenerGruposMaestro(int idMaestro);
    public Maestro obtenerMaestro(String nombreUsuario);
    
    
}
