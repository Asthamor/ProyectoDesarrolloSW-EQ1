/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Alumno;
import modelo.Cliente;
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
@Ignore
public class IPersonaTest {
    
    public IPersonaTest() {
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
     * Test of obtenerTodos method, of class IPersona.
     */
    @Test
    public void testObtenerTodos() {
        System.out.println("obtenerTodos");
        IPersona instance = new Alumno();
        boolean expResult = true;
        List<Persona> alumnos = instance.obtenerTodos();
        boolean result = alumnos.size()>0;
        assertEquals(expResult, result);
    }


    /**
     * Test of buscar method, of class IPersona.
     */
    @Test
    public void testBuscar() {
        System.out.println("buscar");
        String nombre = "P";
        IPersona instance = new Alumno();
        boolean expResult = true;
        boolean result = instance.buscar(nombre).size()>0;   
        assertEquals(expResult, result);
    }

    /**
     * Test of registrar method, of class IPersona.
     */
    @Test
    public void testRegistrar() {
        System.out.println("registrar");
        Persona persona = new Cliente();
        persona.setNombre("Jesus 2");
        persona.setApellidos("Aja 2");
        persona.setTelefono("223029");
        boolean expResult = true;
        boolean result = persona.registrar(persona);
        assertEquals(expResult, result);
    }
    
}
