/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistraPagoAlumnoExternoController implements Initializable, Controlador {

    @FXML
    private ListView<?> lstColaboradores;
    @FXML
    private ListView<?> lstGrupos;
    @FXML
    private ListView<?> lstAlumno;
    @FXML
    private Label labelAlumno;
    @FXML
    private Label labelColaborador;
    @FXML
    private Label labelFecha;
    @FXML
    private Label labelGrupo;
    @FXML
    private JFXTextField txtMonto;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;

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
    private void registrarPago(ActionEvent event) {
    }

}
