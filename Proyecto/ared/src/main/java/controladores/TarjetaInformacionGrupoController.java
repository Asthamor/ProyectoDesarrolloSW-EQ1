/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaInformacionGrupoController implements Initializable {

    @FXML
    private Label lblNombreGrupo;
    @FXML
    private Label lblTipoDanza;
    @FXML
    private Label lblHorario;
    @FXML
    private JFXTextArea txtHorario;
    @FXML
    private JFXButton btnEditarGrupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
