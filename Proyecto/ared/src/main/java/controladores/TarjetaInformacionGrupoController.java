/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Grupo;
import modelo.Persona;

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

    private Grupo grupo;

    private HBox pantallaDividida;

    private StackPane pnlSecundario = new StackPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        mostrarInformacion(grupo);
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void mostrarInformacion(Grupo grupo) {
        lblNombreGrupo.setText(grupo.getNombre());
        lblTipoDanza.setText(grupo.getTipoDanza());
    }

    @FXML
    private void editarGrupo(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaEditarGrupo.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        PantallaEditarGrupoController controlador = loader.getController();
        controlador.setGrupo(grupo);
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setControlador(this);
        pnlSecundario.getChildren().add(root);
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlSecundario);
        if (pantallaDividida.getChildren().size() > 1) {
            pantallaDividida.getChildren().remove(1);
        }
        pantallaDividida.getChildren().add(pnlSecundario);
    }

}
