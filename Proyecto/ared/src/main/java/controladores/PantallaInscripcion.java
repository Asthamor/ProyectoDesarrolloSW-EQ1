/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author mauricio
 */
public class PantallaInscripcion implements Initializable, Controlador {

    @FXML
    private JFXButton btnInscripcion;
    @FXML
    private JFXButton btnBaja;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXButton btnReiscripciones;
    @FXML
    private StackPane pnlInscripciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    @FXML
    private void abrirPantallaInscripcion(ActionEvent event) {
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlInscripciones);
        pnlInscripciones.getChildren().add(crearPantalla("/fxml/PantallaInscribirAlumno.fxml"));
    }

    @FXML
    private void abrirPantallaBajas(ActionEvent event) {
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlInscripciones);
        pnlInscripciones.getChildren().add(crearPantalla("/fxml/PantallaBajaInscripcion.fxml"));
    }

    @FXML
    private void abrirPantallaReinscribir(ActionEvent event) {
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlInscripciones);
        pnlInscripciones.getChildren().add(crearPantalla("/fxml/PantallaReinscripcion.fxml"));
    }

    public Parent crearPantalla(String ruta) {
        pnlInscripciones.getChildren().clear();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource(ruta));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        Controlador controlador = loader.getController();
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        return root;
    }

}
