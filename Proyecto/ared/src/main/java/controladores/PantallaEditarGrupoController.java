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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
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
    private JFXComboBox<String> cmbMaestro;
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

    private TarjetaInformacionGrupoController controlador;

    private List<Persona> maestros;

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

    public void setControlador(TarjetaInformacionGrupoController controlador) {
        this.controlador = controlador;
    }

    public void mostrarInformacion() {
        txtNombreGrupo.setText(grupo.getNombre());
        txtTipoDanza.setText(grupo.getTipoDanza());
        ArrayList<String> nombresMaestros = new ArrayList();
        Maestro instanciaMaestro = new Maestro();
        maestros = instanciaMaestro.obtenerTodos();
        maestros.forEach((maestro) -> {
            nombresMaestros.add(maestro.getNombre() + " " + maestro.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresMaestros);
        cmbMaestro.setItems(items);
        Maestro maestro;
        for (int i = 0; i < maestros.size(); i++) {
            maestro = (Maestro) maestros.get(i);
            if (maestro.getMaestroPK().getIdMaestro() == grupo.getMaestro().getMaestroPK().getIdMaestro()) {
                cmbMaestro.getSelectionModel().select(i);
            }
        }

    }

}
