/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Alumno;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author raymundo
 */
public class IAlumnoTest {
    
    public IAlumnoTest() {
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
     * Test of obtenerAlumnosInscritos method, of class IAlumno.
     */
    @org.junit.Test
    public void testObtenerAlumnosInscritos() {
        System.out.println("obtenerAlumnosInscritos");
        String idGrupo = "1";
        IAlumno instance = new Alumno();
        int expResult = 4;
        List<Alumno> alumnosInscritos = instance.obtenerAlumnosInscritos(idGrupo);
        int result = alumnosInscritos.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerAlumnosNoInscritos method, of class IAlumno.
     */
    @org.junit.Test
    public void testObtenerAlumnosNoInscritos() {
        System.out.println("obtenerAlumnosNoInscritos");
        String idGrupo = "1";
        IAlumno instance = new Alumno();
        int expResult = 5;
        List<Alumno> alumnosNoInscritos = instance.obtenerAlumnosNoInscritos(idGrupo);
        int result = alumnosNoInscritos.size();
        assertEquals(expResult, result);
    }

    
}
