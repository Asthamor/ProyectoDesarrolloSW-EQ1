/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Maestro;
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
public class IMaestroTest {
    
    public IMaestroTest() {
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
     * Test of obtenerDatos method, of class IMaestro.
     */
    @Test
    public void testObtenerDatos() {
        System.out.println("obtenerDatos");
        String maestro = "1";
        IMaestro instance = new Maestro();
        Maestro expResult = new Maestro();
        expResult.setNombre("Carlos Perez");
        Maestro result = instance.obtenerDatos(maestro);
        assertEquals(expResult.getNombre(), result.getNombre());
    }

    /**
     * Test of obtenerActivos method, of class IMaestro.
     */
    @Test
    public void testObtenerActivos() {
        System.out.println("obtenerActivos");
        IMaestro instance = new Maestro();
        int expResult = 3;
        List<Maestro> result = instance.obtenerActivos();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of obtenerEstado method, of class IMaestro.
     */
    @Test
    public void testObtenerEstado() {
        System.out.println("obtenerEstado");
        IMaestro instance = new Maestro();
        boolean expResult = true;
        boolean result = instance.obtenerEstado();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerImagen method, of class IMaestro.
     */
    @Test
    public void testObtenerImagen() {
        System.out.println("obtenerImagen");
        IMaestro instance = new Maestro();
        String expResult = "";
        String result = instance.obtenerImagen();
        assertEquals(expResult, result);
    }

    
}
