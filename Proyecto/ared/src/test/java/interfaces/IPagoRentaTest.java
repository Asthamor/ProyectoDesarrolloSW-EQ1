/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.Date;
import modelo.PagoRenta;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author alonso
 */
@Ignore
public class IPagoRentaTest {
    
    public IPagoRentaTest() {
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
     * Test of obtenerUltimoPago method, of class IPagoRenta.
     */
    @Test
    public void testObtenerUltimoPago() {
        System.out.println("obtenerUltimoPago");
        PagoRenta instance = new PagoRenta();
        boolean expResult = true;
        boolean result = instance.obtenerUltimoPago() != null;
        assertEquals(expResult, result);
    }

    /**
     * Test of actualizarPago method, of class IPagoRenta.
     */
    @Test
    public void testActualizarPago() {
        System.out.println("actualizarPago");
        PagoRenta instance = new PagoRenta();
        instance = instance.obtenerUltimoPago();
        instance.setFecha(new Date());
        boolean expResult = true;
        boolean result = instance.actualizarPago();
        assertEquals(expResult, result);

    }

    /**
     * Test of eliminarPago method, of class IPagoRenta.
     */
    @Test
    public void testEliminarPago() {
        System.out.println("eliminarPago");
        PagoRenta instance = new PagoRenta();
        instance = instance.obtenerUltimoPago();
        boolean expResult = true;
        boolean result = instance.eliminarPago();
        assertEquals(expResult, result);
    }

    public class IPagoRentaImpl implements IPagoRenta {

        public PagoRenta obtenerUltimoPago() {
            return null;
        }

        public boolean actualizarPago() {
            return false;
        }

        public boolean eliminarPago() {
            return false;
        }
    }
    
}
