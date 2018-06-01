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
//@Ignore
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
    public void testObtenerTodosAlumnos() {
        System.out.println("obtenerTodosAlumnos");
        IPersona instance = new Alumno();
        boolean expResult = true;
        List<Persona> alumnos = instance.obtenerTodos();
        boolean result = alumnos.size() > 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerTodosClientes() {
        System.out.println("obtenerTodosClientes");
        IPersona instance = new Cliente();
        boolean expResult = true;
        List<Persona> clientes = instance.obtenerTodos();
        boolean result = clientes.size() > 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerTodosMaestros() {
        System.out.println("obtenerTodosMaestros");
        IPersona instance = new Maestro();
        boolean expResult = true;
        List<Persona> maestros = instance.obtenerTodos();
        boolean result = maestros.size() > 0;
        assertEquals(expResult, result);
    }

    /**
     * Test of buscar method, of class IPersona.
     */
    @Test
    public void testBuscarAlumno() {
        System.out.println("buscarAlumno");
        String nombre = "W";
        IPersona instance = new Alumno();
        boolean expResult = true;
        boolean result = instance.buscar(nombre).size() > 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarMaestro() {
        System.out.println("buscarMaestro");
        String nombre = "R";
        IPersona instance = new Maestro();
        boolean expResult = true;
        boolean result = instance.buscar(nombre).size() > 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarCliente() {
        System.out.println("buscarCliente");
        String nombre = "S";
        IPersona instance = new Cliente();
        boolean expResult = true;
        boolean result = instance.buscar(nombre).size() > 0;
        assertEquals(expResult, result);
    }

    /**
     * Test of registrar method, of class IPersona.
     */
    @Test
    public void testRegistrarCliente() {
        System.out.println("registrarCliente");
        Persona persona = new Cliente();
        persona.setNombre("Jesus 2");
        persona.setApellidos("Aja 2");
        persona.setTelefono("223029");
        persona.setEsActivo(true);
        boolean expResult = true;
        boolean result = persona.registrar(persona);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarAlumno() {
        System.out.println("registrarAlumno");
        Persona persona = new Alumno();
        persona.setNombre("Jorge Alberto");
        persona.setApellidos("Gomez Sanchez");
        persona.setEsActivo(true);
        persona.setTelefono("222445");

        boolean expResult = true;
        boolean result = persona.registrar(persona);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarMaestro() {
        System.out.println("registrarMaestro");
        Persona persona = new Maestro();
        persona.setNombre("Roberto");
        persona.setApellidos("Diaz Leon");
        persona.setTelefono("2456445");
        persona.setEsActivo(true);
        boolean expResult = true;
        boolean result = persona.registrar(persona);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerActivos method, of class IPersona.
     */
    @Test
    public void testObtenerActivosAlumnos() {
        System.out.println("ObtenerActivosAlumnos");
        Persona persona = new Alumno();
        boolean expResult = true;
        boolean result = persona.obtenerActivos().size() > 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerActivosClientes() {
        System.out.println("ObtenerActivosClientes");
        Persona persona = new Cliente();
        boolean expResult = true;
        boolean result = persona.obtenerActivos().size() > 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerActivosMaestros() {
        System.out.println("ObtenerActivosMaestros");
        Persona persona = new Maestro();
        boolean expResult = true;
        boolean result = persona.obtenerActivos().size() > 0;
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerInactivos method, of class IPersona.
     */
    @Test
    public void testObtenerInactivosAlumnos() {
        System.out.println("ObtenerInactivosAlumnos");
        Persona persona = new Alumno();
        boolean expResult = true;
        int inactivos= persona.obtenerInactivos().size();
        boolean result = inactivos == 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerInactivosClientes() {
        System.out.println("ObtenerInactivosClientes");
        Persona persona = new Cliente();
        boolean expResult = true;
        int inactivos= persona.obtenerInactivos().size();
        boolean result = inactivos == 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerInactivosMaestros() {
        System.out.println("ObtenerInactivosMaestros");
        Persona persona = new Maestro();
        boolean expResult = true;
        int inactivos= persona.obtenerInactivos().size();
        boolean result = inactivos > 0;
        assertEquals(expResult, result);
    }
    
    /**
     * Test of actualizarDatos method, of class IPersona.
     */
    
    @Test
    public void testActualizarMaestro() {
        System.out.println("ActualizarMaestro");
        Persona persona = new Maestro();
        persona = persona.obtenerActivos().get(0);
        persona.setNombre("Alberto");
        boolean expResult = true;
        boolean result = persona.actualizarDatos(false);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testActualizarAlumno() {
        System.out.println("ActualizarAlumno");
        Persona persona = new Alumno();
        persona = persona.obtenerActivos().get(0);
        persona.setNombre("Carlos");
        boolean expResult = true;
        boolean result = persona.actualizarDatos(false);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testActualizarCliente() {
        System.out.println("ActualizarCliente");
        Persona persona = new Cliente();
        persona = persona.obtenerActivos().get(0);
        persona.setNombre("Raymundo");
        boolean expResult = true;
        boolean result = persona.actualizarDatos(false);
        assertEquals(expResult, result);
    }
}
