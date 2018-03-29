/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaRegistrarUsuarioController implements Initializable {

    @FXML
    private Label lblInfoNecesaria;
    @FXML
    private Label lblInfoOpcional;
    @FXML
    private Label lblNombresUsuario;
    @FXML
    private JFXTextField txtNombresUsuario;
    @FXML
    private Label lblApellidosUsuario;
    @FXML
    private Label lblTelefonoUsuario;
    @FXML
    private JFXTextField txtApellidosUsuario;
    @FXML
    private JFXTextField txtTelefonoUsuario;
    @FXML
    private Label lblFotografiaUsuario;
    @FXML
    private ImageView imgFotoUsuario;
    @FXML
    private JFXButton btnSeleccionarFoto;
    @FXML
    private Label lblCorreoElectronicoUsuario;
    @FXML
    private JFXTextField txtCorreoElectronicoUsuario;
    @FXML
    private JFXButton btnGuardarUsuario;
    @FXML
    private Label lblNuevoUsuario;

    private String tipoUsuario;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        lblNuevoUsuario.setText("Nuevo " + this.tipoUsuario);
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.jpg","*.png")
        );
        
        File file = fileChooser.showOpenDialog(null);
        System.out.println(file);
        Image imagen = new Image("file:" + file.getAbsolutePath());
        imgFotoUsuario.setImage(imagen);
    }

    @FXML
    private void guardarUsuario(ActionEvent event) {
    }

}
