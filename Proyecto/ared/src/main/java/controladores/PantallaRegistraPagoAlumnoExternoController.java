/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import clasesApoyo.Mensajes;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.PagoAlumnoExterno;
import modelo.PagoMaestro;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistraPagoAlumnoExternoController implements Initializable, Controlador {

    @FXML
    private ListView<String> lstColaboradores;
    @FXML
    private ListView<String> lstGrupos;
    @FXML
    private ListView<String> lstAlumno;
    @FXML
    private Label labelAlumno;
    @FXML
    private Label labelColaborador;
    @FXML
    private Label labelFecha;
    @FXML
    private Label labelGrupo;
    @FXML
    private JFXTextField txtMonto;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private ArrayList<String> nombresGrupos;
    private ArrayList<String> nombresColaboradores;
    private ArrayList<String> nombresAlumnos;
    private List<Alumno> alumnos;
    private List<Grupo> grupos;
    private List<Persona> maestros;
    private Maestro maestro;
    @FXML
    private Label labelFechaProximoPago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresColaboradores = new ArrayList();
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

    public void mostrarColaboradores() {
        labelFecha.setText(DateFormat.getDateInstance().format(new Date()));
        maestro = new Maestro();
        maestros = maestro.obtenerTodos();
        maestros.forEach((colaborador) -> {
            nombresColaboradores.add(colaborador.getNombre() + " " + colaborador.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresColaboradores);
        lstColaboradores.setItems(items);
        lstColaboradores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lstGrupos.setItems(null);
                labelFechaProximoPago.setText("");
                labelColaborador.setText("");
                labelGrupo.setText("");
                labelAlumno.setText("");
                nombresGrupos = new ArrayList();
                Maestro maestro = (Maestro) maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex());
                if (maestro.getGrupoCollection().isEmpty()) {

                } else {
                    grupos = new ArrayList(maestro.getGrupoCollection());
                    grupos.forEach((grupo) -> {
                        nombresGrupos.add(grupo.getNombre());
                    });
                    ObservableList<String> items = FXCollections.observableArrayList();
                    items.addAll(nombresGrupos);
                    lstGrupos.setItems(items);

                    lstGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (lstGrupos.getSelectionModel().getSelectedIndex() != -1) {
                                lstAlumno.setItems(null);
                                labelFechaProximoPago.setText("");
                                labelColaborador.setText("");
                                labelGrupo.setText("");
                                labelAlumno.setText("");
                                nombresAlumnos = new ArrayList();
                                Grupo grupo = grupos.get(lstGrupos.getSelectionModel().getSelectedIndex());
                                if (grupo.getAlumnoCollection().isEmpty()) {

                                } else {
                                    alumnos = new ArrayList(grupo.getAlumnoCollection());
                                    alumnos.forEach((alumno) -> {
                                        nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
                                    });
                                    ObservableList<String> items = FXCollections.observableArrayList();
                                    items.addAll(nombresAlumnos);
                                    lstAlumno.setItems(items);

                                    lstAlumno.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                                        @Override
                                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                            if (lstAlumno.getSelectionModel().getSelectedIndex() != -1) {
                                                List<PagoAlumno> pagos = new ArrayList<>(alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()).getPagoAlumnoCollection());
                                                //PagoAlumno ultimoPago = pagos.get(pagos.size() - 1);
                                                PagoAlumno ultimoPago = null;
                                                PagoAlumno auxiliar;
                                                for (int i = pagos.size() - 1; i >= 0; i--) {
                                                    auxiliar = pagos.get(i);
                                                    if (auxiliar.getGrupo().getGrupoPK().getIdGrupo() == grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()).getGrupoPK().getIdGrupo()) {
                                                        ultimoPago = pagos.get(i);
                                                        break;
                                                    }
                                                }
                                                Date fechaActualPago = ultimoPago.getFechaVencimiento();
                                                labelFechaProximoPago.setText(DateFormat.getDateInstance().format(fechaActualPago));
                                                labelColaborador.setText(maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex()).getNombre() + " " + maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex()).getApellidos());
                                                labelGrupo.setText(grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()).getNombre());
                                                labelAlumno.setText(alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()).getNombre() + " " + alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()).getApellidos());
                                            }
                                        }

                                    });
                                }
                            }
                        }

                    });
                }
            }

        });

    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        mostrarColaboradores();
    }

    @FXML
    private void registrarPago(ActionEvent event) {
        boolean bandera = false;
        if (lstAlumno.getSelectionModel().getSelectedIndex() != -1) {
            if (!existenCamposErroneos()) {
                if (tamañoInvalidoCaracteres()) {
                    Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
                } else {
                    PagoAlumnoExterno pagoAlumno = new PagoAlumnoExterno();
                    pagoAlumno.setAlumno(alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()));
                    pagoAlumno.setMaestro((Maestro) maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex()));
                    pagoAlumno.setFecha(new Date());
                    pagoAlumno.setMonto(Integer.parseInt(txtMonto.getText()));
                    if (pagoAlumno.registrarPago()) {
                        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRegistraPagoAlumnoExterno.fxml", this.pnlPrincipal, this.pantallaDividida));
                        pantallaDividida.getChildren().add(pnlPrincipal);
                        Mensajes.mensajeExitoso("El pago del alumno se registro correctamente, puede visualizarlo en historial de pagos");
                    }

                }

            }
        } else {
            Mensajes.mensajeAlert("Debe seleccionar un alumno");
        }
    }

    public boolean tamañoInvalidoCaracteres() {
        return txtMonto.getText().length() > 11;
    }

    public boolean existenCamposErroneos() {
        return !txtMonto.validate();
    }

}
