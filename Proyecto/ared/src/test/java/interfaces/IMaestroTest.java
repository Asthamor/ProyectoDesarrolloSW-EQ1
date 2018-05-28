/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Grupo;
import modelo.Horario;
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

    @Test
    public void testObtenerGruposMaestro() {
        System.out.println("obtenerGruposMaestro");
        Maestro maestroPrueba = new Maestro();
        List<Maestro> maestros = (List<Maestro>) ((Object) maestroPrueba.buscar("Istael"));
        if (maestros.size() == 0) {
            maestroPrueba.setApellidos("Ozuna");
            maestroPrueba.setNombre("Istael");
            maestroPrueba.setTelefono("25578");
            maestroPrueba.registrar(maestroPrueba);
        } else {
            maestroPrueba = maestros.get(0);
        }
        if (maestroPrueba.getGrupoCollection().size() <= 0) {
            Grupo grupo = new Grupo();
            grupo.setMaestro(maestroPrueba);
            grupo.setNombre("Salsa Isra");
            Horario horario = new Horario();
            horario.setRutaArchivo("/");
            horario.setIdHorario(1);
            grupo.setHorario(horario);
            grupo.setTipoDanza("Salsa");
            grupo.registrarGrupo(grupo);
            maestroPrueba.getGrupoCollection().add(grupo);
        }

        boolean expResult = true;
        boolean result = maestroPrueba.getGrupoCollection().size() > 0;

        assertEquals(expResult, result);
    }

}
