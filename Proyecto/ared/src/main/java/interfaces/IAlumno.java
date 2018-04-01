/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Alumno;

/**
 *
 * @author alonso
 */
public interface IAlumno {
    public List<Alumno> obtenerAlumnosInscritos(String idGrupo);
    public List<Alumno> obtenerAlumnosNoInscritos(String idGrupo);
}
