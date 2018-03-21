/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
public class PantallaEditarGrupoController implements Initializable {

    @FXML
    private Label lblDetalleGrupo;
    @FXML
    private Label lblNombreGrupo;
    @FXML
    private Label lblMaestro;
    @FXML
    private Label lblTipoDanza;
    @FXML
    private Label lblHorario;
    @FXML
    private JFXTextField txtNombreGrupo;
    @FXML
    private JFXTextField txtTipoDanza;
    @FXML
    private JFXComboBox<?> cmbMaestro;
    @FXML
    private JFXTextArea txtHorario;
    @FXML
    private JFXButton btnGuardarGrupo;
    @FXML
    private JFXButton btnEliminarGrupo;
    @FXML
    private JFXButton btnEditarHorario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
