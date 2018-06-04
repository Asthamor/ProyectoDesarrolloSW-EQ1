/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import modelo.Maestro;
import modelo.Promocion;
import modelo.PromocionPK;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author alonso
 */
public class IPromocionTest {
    
    public IPromocionTest() {
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
     * Test of crearPromocion method, of class IPromocion.
     */
    @Test
    public void testCrearPromocionExitosa() {
        System.out.println("crearPromocion");
        Promocion instance = new Promocion();
        instance.setCodigo("Navidad");
        instance.setConcepto("Deben de pagar antes del 24 de diciembre");
        instance.setDescuento(30);
        Maestro maestro = new Maestro();
        instance.setMaestro((Maestro)maestro.obtenerMaestro("RayPerez"));
        instance.setParaInscripcion(Short.valueOf("0"));
        instance.setPromocionPK(new PromocionPK());
        boolean expResult = true;
        boolean result = instance.crearPromocion();
        assertEquals(expResult, result);
    }
    
        @Test
    public void testCrearPromocionFallida() {
        System.out.println("crearPromocion");
        Promocion instance = new Promocion();
        instance.setCodigo("Navidad");
        instance.setConcepto("Deben de pagar antes del 24 de diciembre");
        instance.setDescuento(30);
        Maestro maestro = new Maestro();
        instance.setMaestro(null);
        instance.setParaInscripcion(Short.valueOf("0"));
        instance.setPromocionPK(new PromocionPK());
        boolean expResult = false;
        boolean result = instance.crearPromocion();
        assertEquals(expResult, result);
    }

    public class IPromocionImpl implements IPromocion {

        public boolean crearPromocion() {
            return false;
        }
    }
    
}
