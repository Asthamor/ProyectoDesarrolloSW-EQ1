/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import interfaces.Controlador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaPagosController implements Initializable,Controlador {
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXButton btnPagoMaestro;
    @FXML
    private JFXButton btnPagoRenta;
    @FXML
    private JFXButton btnPagoAlumno;
    @FXML
    private Label lbRegistrarPago;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPagoAlumno.setFocusTraversable(false);
        btnPagoMaestro.setFocusTraversable(false);
        btnPagoRenta.setFocusTraversable(false);
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
    private void ventanaPagoMaestro(ActionEvent event) {
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRegistrarPagoMaestro.fxml", this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void ventanaPagoAlumnoExterno(ActionEvent event) {
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRegistraPagoAlumnoExterno.fxml", this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }
    
}
