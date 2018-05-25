/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo.hilos;

import controladores.PantallaPrincipalDirectorController;
import static controladores.PantallaPrincipalDirectorController.crearPantallaUsuarios;
import javafx.concurrent.Task;
import modelo.Persona;

/**
 *
 * @author alonso
 */
public class HiloPersona extends Task<Void> {

    @Override
    protected Void call() throws Exception {
//        pnlPrincipal.getChildren().add(crearPantallaUsuarios(persona,
//                pnlPrincipal, pantallaDividida));
//        pantallaDividida.getChildren().add(pnlPrincipal);
        return null;
    }
}
