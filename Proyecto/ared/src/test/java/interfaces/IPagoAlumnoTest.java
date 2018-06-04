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
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.Promocion;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author raymundo
 */
//@Ignore
public class IPagoAlumnoTest {

    public IPagoAlumnoTest() {
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
     * Test of registrarPagoMensual method, of class IPagoAlumno.
     */
    @Test
    public void testRegistrarPagoMensual() {
        System.out.println("registrarPagoMensual");
        PagoAlumno pago = new PagoAlumno();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.buscar("Alon").get(0); 
        List<Grupo> grupo = new ArrayList(maestro.getGrupoCollection());
        List<Alumno> alumno = new ArrayList(grupo.get(0).getAlumnoCollection());
        pago.setGrupo(grupo.get(0));
        pago.setAlumno(alumno.get(0));
        pago.setFechaPago(new Date());
        pago.setFechaVencimiento(new Date());
        pago.setMonto(200);
        boolean expResult = true;
        boolean result = pago.registrarPagoMensual(pago);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRegistrarPagoMensualInvalido() {
        System.out.println("registrarPagoMensual");
        PagoAlumno pago = new PagoAlumno();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.buscar("Alon").get(0);        
        boolean expResult = false;
        boolean result = pago.registrarPagoMensual(pago);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRegistrarPagoMensualPromocion() {
        System.out.println("registrarPagoMensual");
        PagoAlumno pago = new PagoAlumno();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.buscar("Alon").get(0);
        List<Grupo> grupo = new ArrayList(maestro.getGrupoCollection());
        List<Alumno> alumno = new ArrayList(grupo.get(0).getAlumnoCollection());
        pago.setGrupo(grupo.get(0));
        pago.setAlumno(alumno.get(0));
        pago.setPromocion((Promocion)new ArrayList(maestro.getPromocionCollection()).get(0));
        pago.setFechaPago(new Date());
        pago.setFechaVencimiento(new Date());
        pago.setMonto(200);
        boolean expResult = true;
        boolean result = pago.registrarPagoMensual(pago);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRegistrarPagoMensualPromocionInvalido() {
        System.out.println("registrarPagoMensual");
        PagoAlumno pago = new PagoAlumno();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.buscar("Alon").get(0);
        List<Grupo> grupo = new ArrayList(maestro.getGrupoCollection());
        List<Alumno> alumno = new ArrayList(grupo.get(0).getAlumnoCollection());
        pago.setPromocion((Promocion)new ArrayList(maestro.getPromocionCollection()).get(0));
        pago.setFechaPago(new Date());
        pago.setFechaVencimiento(new Date());
        pago.setMonto(200);
        boolean expResult = false;
        boolean result = pago.registrarPagoMensual(pago);
        assertEquals(expResult, result);
    }
    

}
