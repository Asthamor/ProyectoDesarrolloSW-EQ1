/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaGruposController implements Initializable,Controlador {

    @FXML
    private ScrollPane scrollGrupos;
    @FXML
    private AnchorPane pnlGrupos;
    @FXML
    private JFXButton btnRegistrarGrupo;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnlGrupos.getChildren().add(addFlowPane());
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
        scrollGrupos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }
    
    

    public GridPane addFlowPane() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/TarjetaInformacionGrupo.fxml"));
                    grid.add(root, j, i);
                } catch (IOException ex) {
                    Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return grid;
    }

    @FXML
    private void abrirVentanaRegistrarGrupo(ActionEvent event) {
        Parent root = PantallaPrincipalDirectorController.crearPantalla("/fxml/PantallaRegistrarGrupo.fxml",
                pnlPrincipal, pantallaDividida);
        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

}
