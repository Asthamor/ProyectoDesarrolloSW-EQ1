/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Alumno;
import modelo.Grupo;
import modelo.GrupoPK;

/**
 *
 * @author raymundo
 */
public interface IGrupo {
    public List<Grupo> obtenerTodosLosGrupos();
    public boolean registrarGrupo(Grupo grupo);
    public boolean actualizarDatosGrupo(Grupo grupo);
    public boolean eliminarGrupo();
    public List<Alumno> obtenerAlumnosNoInscritos();
    public String obtenerUltimoGrupo();
//    public boolean registrarInscripcionAlumno(String idAlumno, GrupoPK grupoPK);
//    public boolean eluminarInscripciónAlumno(String idAlumno, GrupoPK grupoPK);
    
    
    
}
