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
import modelo.PagoAlumnoExterno;
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
public class IPagoAlumnoExternoTest {
    
    public IPagoAlumnoExternoTest() {
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
     * Test of registrarPago method, of class IPagoAlumnoExterno.
     */
    @Test
    public void testRegistrarPagoValido() {
        System.out.println("registrarPago");
        PagoAlumnoExterno instance = new PagoAlumnoExterno();
        Maestro maestro = new Maestro();
        maestro = (Maestro) maestro.obtenerTodos().get(4);
        List<Grupo> grupo = new ArrayList(maestro.getGrupoCollection());
        List<Alumno> alumno = new ArrayList(grupo.get(0).getAlumnoCollection());
        instance.setAlumno(alumno.get(0));
        instance.setMaestro(maestro);
        instance.setFecha(new Date());
        instance.setMonto(1);
        boolean expResult = true;
        boolean result = instance.registrarPago();
        assertEquals(expResult, result);
    }
    
        @Test
    public void testRegistrarPagoInvalido() {
        System.out.println("registrarPagoInvalido");
        PagoAlumnoExterno instance = new PagoAlumnoExterno();
        boolean expResult = false;
        boolean result = instance.registrarPago();
        assertEquals(expResult, result);
    }

    
}
