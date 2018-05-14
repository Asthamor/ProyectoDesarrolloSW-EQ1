/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.Date;
import modelo.Maestro;
import modelo.PagoMaestro;
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

public class IPagoMaestroTest {

    public IPagoMaestroTest() {
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
     * Test of registrarPago method, of class IPagoMaestro.
     */
    @Test
    public void testRegistrarPagoValido() {
        System.out.println("registrarPagoValido");
        PagoMaestro pagoMaestro = new PagoMaestro();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.obtenerTodos().get(0);
        pagoMaestro.setMaestro(maestro);
        pagoMaestro.setFecha(new Date());
        pagoMaestro.setFechaVencimiento(new Date());
        pagoMaestro.setMonto(1.0);
        boolean expResult = true;
        boolean result = pagoMaestro.registrarPago();
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarPagoInvalido() {
        System.out.println("registrarPagoInvalido");
        PagoMaestro pagoMaestro = new PagoMaestro();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.obtenerTodos().get(0);
        pagoMaestro.setMaestro(maestro);
        boolean expResult = true;
        boolean result = pagoMaestro.registrarPago();
        assertEquals(expResult, result);
    }

}
