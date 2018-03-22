/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaInformacionUsuarioController implements Initializable {

    @FXML
    private ImageView imgFotoUsuario;
    @FXML
    private Label lblNombresUsuario;
    @FXML
    private Label lblApellidosUsuario;
    @FXML
    private Label lblTelefonoUsuario;
    @FXML
    private Label lblCorreoElectronicoUsuario;
    @FXML
    private JFXButton btnEditarUsuario;
    @FXML
    private Tooltip etBtnEditar;
    @FXML
    private FontAwesomeIconView iconoBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
