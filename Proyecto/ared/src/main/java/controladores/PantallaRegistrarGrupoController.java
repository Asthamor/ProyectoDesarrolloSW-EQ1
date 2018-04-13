/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import static controladores.PantallaPrincipalDirectorController.crearPantallaUsuarios;
import interfaces.Controlador;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Grupo;
import modelo.GrupoPK;
import modelo.Horario;
import modelo.Maestro;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaRegistrarGrupoController implements Initializable, Controlador {

    @FXML
    private Label lblRegistrarGrupo;
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
    private JFXButton btnGuardarGrupo;
    @FXML
    private JFXButton btnDefinirHorario;
    @FXML
    private JFXTextArea txtHorario;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private List<Persona> maestros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNombreGrupo.setText("");
        txtTipoDanza.setText("");
        ArrayList<String> nombresMaestros = new ArrayList();
        Maestro instanciaMaestro = new Maestro();
        maestros = instanciaMaestro.obtenerTodos();
        maestros.forEach((maestro) -> {
            nombresMaestros.add(maestro.getNombre() + " " + maestro.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresMaestros);
        cmbMaestro.setItems(items);
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
    private void definirHorario(ActionEvent event) {
    }

    @FXML
    private void guardarGrupo(ActionEvent event) {
        if (existenCamposVacios()) {
            Mensajes.mensajeAlert("Algunos campos estan vacíos");
        } else {
//            if (!tamañoValidoCaracteres()) {
//                Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
//            } else {
            Grupo grupo = new Grupo();
            grupo.setTipoDanza(txtTipoDanza.getText());
            grupo.setNombre(txtNombreGrupo.getText());
            grupo.setFechaCreacion(new Date());
            grupo.setMaestro((Maestro) maestros.get(cmbMaestro.getSelectionModel().getSelectedIndex()));
            Horario horario= new Horario();
            horario = horario.obtenerRutaHorario();
            grupo.setHorario(horario);
            if (grupo.registrarGrupo(grupo)) {
                pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
                pantallaDividida.getChildren().add(pnlPrincipal);
                Mensajes.mensajeExitoso("El grupo se registro correctamente");
            }
//            }
        }
    }

    public boolean existenCamposVacios() {
        return txtNombreGrupo.getText().equals("") || txtTipoDanza.getText().equals("") || cmbMaestro.getValue() == null;
    }

}
