/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
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
public class PantallaUsuariosController implements Initializable {

    @FXML
    private ScrollPane scrollUsuarios;
    @FXML
    private AnchorPane pnlUsuarios;
    @FXML
    private Label etNombreUsuario;
    @FXML
    private JFXButton btnRegistrarUsuario;
    @FXML
    private JFXButton btnBuscar;
    
    private String tipoUsuario;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXTextField txtNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnlUsuarios.getChildren().add(addFlowPane());
        scrollUsuarios.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
    }    

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        etNombreUsuario.setText("Nombre del " + this.tipoUsuario + ":");
        btnRegistrarUsuario.setText("Registrar " + this.tipoUsuario);
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }
    
    

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }
    
    public GridPane addFlowPane() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/TarjetaInformacionUsuario.fxml"));
                    grid.add(root, j, i);
                }
                catch (IOException ex) {
                    Logger.getLogger(PantallaUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return grid;
    }

    @FXML
    private void abrirVentanaRegistrarUsuario(ActionEvent event) {
        PantallaPrincipalDirectorController.limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaRegistrarUsuario.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaRegistrarUsuarioController controlador = loader.getController();
        controlador.setTipoUsuario(tipoUsuario);
        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }
    
    
    
}
