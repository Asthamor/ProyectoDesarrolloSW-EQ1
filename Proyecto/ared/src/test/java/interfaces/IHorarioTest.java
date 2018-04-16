/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import modelo.Horario;
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
public class IHorarioTest {
    
    public IHorarioTest() {
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
     * Test of obtenerRutaHorario method, of class IHorario.
     */
    @Test
    public void testObtenerRutaHorario() {
        System.out.println("obtenerRutaHorario");
        IHorario instance = new Horario();
        int expResult = 1;
        int result;
        Horario horario = instance.obtenerRutaHorario();
        result = horario.getIdHorario();
        assertEquals(expResult, result);
    }

    /**
     * Test of crearHorario method, of class IHorario.
     */
    @Test
    public void testCrearHorario() {
        System.out.println("crearHorario");
        Horario horario = new Horario();
        horario.setIdHorario(2);
        horario.setRutaArchivo("prueba");
        IHorario instance = new Horario();
        boolean expResult = true;
        boolean result = instance.crearHorario(horario);
        assertEquals(expResult, result);
    }

    
}
