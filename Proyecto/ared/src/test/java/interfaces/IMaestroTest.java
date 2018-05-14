/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import modelo.Grupo;
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
     * Test of obtenerActivos method, of class IMaestro.
     */
    @Test
    public void testObtenerActivos() {
        System.out.println("obtenerActivos");
        IMaestro instance = new Maestro();
        boolean expResult = true;
        boolean result = instance.obtenerActivos().size() > 0;
        assertEquals(expResult, result);
    }

    
    @Test
    public void testObtenerMaestro(){
        System.out.println("obtenerMaestro");
        IMaestro instance = new Maestro();
        boolean expResult = true;
        Maestro maestro = instance.obtenerMaestro("IrvinVera");
        boolean result = maestro != null;
        assertEquals(expResult, result);
    }
    
    @Test
    public void testObtenerGruposMaestro(){
        Maestro maestroPrueba = new Maestro();
        if(maestroPrueba.obtenerMaestro("IstaelOzuna") == null){
            maestroPrueba.setApellidos("Ozuna");
            maestroPrueba.setNombre("Istael");
            maestroPrueba.setTelefono("25578");
            maestroPrueba.registrar(maestroPrueba);
           
            
            
        }
        
        Grupo grupo = new Grupo();
        
        
        System.out.println("obtenerGruposMaestro");

        boolean expResult = true;
        
        boolean result = true;
        
        System.out.println(maestroPrueba.obtenerMaestro("IstaelOzuna"));
        assertEquals(expResult, result);
    }


    
}
