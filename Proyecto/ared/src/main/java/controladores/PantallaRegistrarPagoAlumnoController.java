/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistrarPagoAlumnoController implements Initializable, Controlador {

    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblNombreMaestro;
    @FXML
    private JFXTextField txtMonto;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private ListView<String> lstGrupos;
    @FXML
    private ListView<String> lstAlumnos;
    private ArrayList<String> nombresGrupos;
    private ArrayList<String> nombresAlumnos;
    private List<Alumno> alumnos;
    private List<Grupo> grupos;
    @FXML
    private JFXComboBox<?> cboxPromocion;
    @FXML
    private Label lblGrupo;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblFechaLimite;
    @FXML
    private Label lblMontoTotal;
    @FXML
    private Label lblAlumno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresGrupos = new ArrayList();
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        mostrarGruposMaestro();
    }

    public void mostrarGruposMaestro() {
        Date fechaProximoPago;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        fechaProximoPago = cal.getTime();
        lblFecha.setText(DateFormat.getDateInstance().format(new Date()));
        lblFechaLimite.setText(DateFormat.getDateInstance().format(fechaProximoPago));
        Maestro maestro = new Maestro();
        grupos = maestro.obtenerGruposMaestro(2);
        grupos.forEach((grupo) -> {
            nombresGrupos.add(grupo.getNombre());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresGrupos);
        lstGrupos.setItems(items);

        lstGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lstAlumnos.setItems(null);
                lblGrupo.setText("");
                lblAlumno.setText("");
                nombresAlumnos = new ArrayList();
                if (grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()).getAlumnoCollection().isEmpty()) {

                } else {
                    alumnos = new ArrayList(grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()).getAlumnoCollection());
                    alumnos.forEach((alumno) -> {
                        nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
                    });
                    ObservableList<String> items = FXCollections.observableArrayList();
                    items.addAll(nombresAlumnos);
                    lstAlumnos.setItems(items);
                    lstAlumnos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (lstAlumnos.getSelectionModel().getSelectedIndex() != -1) {
                                lblGrupo.setText(grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()).getNombre());
                                lblAlumno.setText(alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()).getNombre() + " " + alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()).getApellidos());
                            }
                        }

                    });
                }
            }
        });
    }

    @FXML
    private void añadirTotal(KeyEvent event) {
        lblMontoTotal.setText(txtMonto.getText());
    }

}
