/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Grupo;
import modelo.Maestro;
import modelo.Persona;

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
    private JFXTextArea txtHorario;
    @FXML
    private JFXButton btnGuardarGrupo;
    @FXML
    private JFXButton btnEliminarGrupo;
    @FXML
    private JFXButton btnEditarHorario;

    private Grupo grupo;

    private HBox pantallaDividida;

    private StackPane pnlPrincipal;

    private TarjetaInformacionGrupoController controlador;

    @FXML
    private Label lblNombreMaestro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        mostrarInformacion();
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void setControlador(TarjetaInformacionGrupoController controlador) {
        this.controlador = controlador;
    }

    public void mostrarInformacion() {
        txtNombreGrupo.setText(grupo.getNombre());
        txtTipoDanza.setText(grupo.getTipoDanza());
        lblNombreMaestro.setText(grupo.getMaestro().getNombre()+" "+grupo.getMaestro().getApellidos());

    }

    @FXML
    private void eliminarGrupo(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro que desea eliminar el grupo?,"
                + " se eliminaran las inscripciones y pagos asocioados.", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            grupo.eliminarGrupo(grupo.getGrupoPK());
            pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
            pantallaDividida.getChildren().add(pnlPrincipal);
            Mensajes.mensajeExitoso("Grupo eliminado correctamente");
        }
    }

    @FXML
    private void actualizarInformacion(ActionEvent event) {
        if (existenCamposVacios()) {
            Mensajes.mensajeAlert("Algunos campos estan vacíos");
        } else {
//            if (!tamañoValidoCaracteres()) {
//                Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
//            } else {
            grupo.setTipoDanza(txtTipoDanza.getText());
            grupo.setNombre(txtNombreGrupo.getText());
            if (grupo.actualizarDatosGrupo(grupo)) {
                pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
                pantallaDividida.getChildren().add(pnlPrincipal);
                Mensajes.mensajeExitoso("La información se actualizó correctamente");
            }
//            }

        }

    }

    public boolean existenCamposVacios() {
        return txtNombreGrupo.getText().equals("") || txtTipoDanza.getText().equals("");
    }

}
