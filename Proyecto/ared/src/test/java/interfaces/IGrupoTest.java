/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Alumno;
import modelo.Grupo;
import modelo.GrupoPK;
import modelo.Horario;
import modelo.Maestro;
import modelo.Persona;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author raymundo
 */
//@Ignore
public class IGrupoTest {

    public IGrupoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of obtenerTodosLosGrupos method, of class IGrupo.
     */
    @Test
    public void testObtenerTodosLosGrupos() {
        System.out.println("obtenerTodosLosGrupos");
        IGrupo instance = new Grupo();
        boolean expResult = true;
        List<Grupo> grupos = instance.obtenerTodosLosGrupos();
        boolean result = grupos.size() > 0;
        assertEquals(expResult, result);
    }

    /**
     * Test of registrarGrupo method, of class IGrupo.
     */
    @Test
    public void testRegistrarGrupo() {
        System.out.println("registrarGrupo");
        Grupo grupo = new Grupo();
        grupo.setNombre("prueba");
        grupo.setTipoDanza("prueba");
        grupo.setFechaCreacion(new Date());
        Horario horario = new Horario();
        horario = horario.obtenerRutaHorario();
        grupo.setHorario(horario);
        Maestro maestro = new Maestro();
        List<Persona> maestros = maestro.obtenerTodos();
        grupo.setMaestro((Maestro) maestros.get(0));
        IGrupo instance = new Grupo();
        boolean expResult = true;
        boolean result = instance.registrarGrupo(grupo);
        assertEquals(expResult, result);
    }

    /**
     * Test of actualizarDatosGrupo method, of class IGrupo.
     */
    @Test
    public void testActualizarDatosGrupo() {
        System.out.println("actualizarDatosGrupo");
        Grupo grupo = new Grupo();
        List<Grupo> grupos = grupo.obtenerTodosLosGrupos();
        for (Grupo auxiliar : grupos) {
            if (auxiliar.getNombre().equals("prueba")) {
                grupo = auxiliar;
                break;
            }
        }
        grupo.setNombre("prueba2");
        grupo.setTipoDanza("prueba2");
        boolean expResult = true;
        boolean result = grupo.actualizarDatosGrupo(grupo);
        assertEquals(expResult, result);
    }

    /**
     * Test of eliminarGrupo method, of class IGrupo.
     */
    @Test
    public void testEliminarGrupo() {
        System.out.println("eliminarGrupo");
        Grupo grupo = new Grupo();
        List<Grupo> grupos = grupo.obtenerTodosLosGrupos();
        for (Grupo auxiliar : grupos) {
            if (auxiliar.getNombre().equals("prueba2")) {
                grupo = auxiliar;
                break;
            }
        }
        GrupoPK grupoPK = grupo.getGrupoPK();
        IGrupo instance = new Grupo();
        boolean expResult = true;
        boolean result = instance.eliminarGrupo(grupoPK);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testObtenerAlumnosNoInscritos(){
        System.out.println("obtenerAlumnosNoInscritos");
        IGrupo instance = new Grupo();
        boolean expResult = true;
        Grupo grupo = instance.obtenerTodosLosGrupos().get(0);
        List<Alumno> alumnos = grupo.obtenerAlumnosNoInscritos();
        boolean result = alumnos.size() > 0;
        assertEquals(expResult, result);
    }
    
    @Test
    public void testObtenerUltimoGrupo(){
        System.out.println("obtenerUltimoGrupo");
        IGrupo instance = new Grupo();
        boolean expResult = true;
        String id = instance.obtenerUltimoGrupo();
        boolean result = !id.equals(null);
        assertEquals(expResult, result);
    }


//    /**
//     * Test of registrarInscripcionAlumno method, of class IGrupo.
//     */
//    @Test
//    public void testRegistrarInscripcionAlumno() {
//        System.out.println("registrarInscripcionAlumno");
//        String idAlumno = "";
//        GrupoPK grupoPK = null;
//        IGrupo instance = new IGrupoImpl();
//        boolean expResult = false;
//        boolean result = instance.registrarInscripcionAlumno(idAlumno, grupoPK);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of eluminarInscripciónAlumno method, of class IGrupo.
//     */
//    @Test
//    public void testEluminarInscripciónAlumno() {
//        System.out.println("eluminarInscripci\u00f3nAlumno");
//        String idAlumno = "";
//        GrupoPK grupoPK = null;
//        IGrupo instance = new IGrupoImpl();
//        boolean expResult = false;
//        boolean result = instance.eluminarInscripciónAlumno(idAlumno, grupoPK);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
