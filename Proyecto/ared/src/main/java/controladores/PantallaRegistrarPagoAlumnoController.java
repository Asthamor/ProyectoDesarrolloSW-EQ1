/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import interfaces.Controlador;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import modelo.PagoAlumno;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistrarPagoAlumnoController implements Initializable, Controlador {

    @FXML
    private JFXButton btnRegistrar;
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
    private JFXComboBox<String> cboxPromocion;
    @FXML
    private Label lblGrupo;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblMontoTotal;
    @FXML
    private Label lblAlumno;
    Maestro maestro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresGrupos = new ArrayList();
        ValidatorBase requeridos = new NumberValidator();
        requeridos.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size("1em")
                .styleClass("error")
                .build());
        txtMonto.getValidators().add(requeridos);
        ValidatorBase requeridos2 = new RequiredFieldValidator();
        requeridos.setMessage("Monto necesario");
        requeridos.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size("1em")
                .styleClass("error")
                .build());
        txtMonto.getValidators().add(requeridos2);
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
        lblFecha.setText(DateFormat.getDateInstance().format(new Date()));
        maestro = new Maestro();
        String nombreUsuario = System.getProperty("nombreUsuario");
        maestro = maestro.obtenerMaestro(nombreUsuario);
        grupos = maestro.obtenerGruposMaestro(maestro.getMaestroPK().getIdMaestro());
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
//
//    @FXML
//    private void registrarAlumno(ActionEvent event) {
//        if (lstAlumnos.getSelectionModel().getSelectedIndex() != -1) {
//            if (!existenCamposErroneos()) {
//                PagoAlumno pagoAlumno = new PagoAlumno();
//                pagoAlumno.setAlumno(alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()));
//                pagoAlumno.setGrupo(grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()));
//                pagoAlumno.setFechaPago(new Date());
//                pagoAlumno.setMonto(Integer.parseInt(txtMonto.getText()));
//                if (pagoAlumno.registrarPagoMensual(pagoAlumno)) {
//                    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRegistrarPagoAlumno.fxml", this.pnlPrincipal, this.pantallaDividida));
//                    pantallaDividida.getChildren().add(pnlPrincipal);
//                    Mensajes.mensajeExitoso("El pago se registro correctamente");
//                }
//            }
//        } else {
//            Mensajes.mensajeAlert("Debe seleccionar un alumno");
//        }
//
//    }

    public boolean existenCamposErroneos() {
        return !txtMonto.validate();
    }

}
