/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
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
public class RentaTest {
    
    public RentaTest() {
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
     * Test of obtenerTodaRentas method, of class Renta.
     */
    @Test
    public void testObtenerTodaRentas() {
        System.out.println("obtenerTodaRentas");
        Renta instance = new Renta();
        boolean expResult = true;
        boolean result = instance.obtenerTodaRentas().size() > 0;
        assertEquals(expResult, result);
    }

    /**
     * Test of crearRenta method, of class Renta.
     */
    @Test
    public void testCrearRenta() {
        System.out.println("crearRenta");
        Renta instance = new Renta();
        PagoRenta pagoRenta = new PagoRenta();
        pagoRenta.setFecha(new Date());
        pagoRenta.setMonto(450);
        instance.setPagoRenta(pagoRenta);
        Cliente cliente = new Cliente();
        instance.setCliente((Cliente)cliente.obtenerActivos().get(0));
        instance.setRentaPK(new RentaPK());
        Horario horario = new Horario();
        horario.setRutaArchivo("/");
        horario.setIdHorario(1);
        instance.setHorario(horario);
        boolean expResult = true;
        boolean result = instance.crearRenta();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerUltimaRenta method, of class Renta.
     */
    @Test
    public void testObtenerUltimaRenta() {
        System.out.println("obtenerUltimaRenta");
        Renta instance = new Renta();
        boolean expResult = true;
        boolean result = instance.obtenerUltimaRenta() != null;
        assertEquals(expResult, result);
    }

    /**
     * Test of eliminarRenta method, of class Renta.
     */
    @Test
    public void testEliminarRenta() {
        System.out.println("eliminarRenta");
        Renta instance = new Renta();
        int idRenta = Integer.parseInt(instance.obtenerUltimaRenta());
        instance = instance.buscarRenta(idRenta);
        
        boolean expResult = true;
        boolean result = instance.eliminarRenta();
        assertEquals(expResult, result);
    }
    
}
