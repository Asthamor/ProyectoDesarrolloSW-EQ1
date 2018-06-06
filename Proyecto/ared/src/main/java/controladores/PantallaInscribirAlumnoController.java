/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import interfaces.Controlador;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.Persona;
import modelo.Promocion;
import modelo.Usuario;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaInscribirAlumnoController implements Initializable, Controlador {

    @FXML
    private JFXLimitedTextField txtMonto;
    @FXML
    private JFXComboBox<Promocion> comboPromocion;
    @FXML
    private Label labelMontoFinal;
    @FXML
    private JFXButton btnInscribir;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXListView<String> lstAlumnos;
    private ArrayList<String> nombresAlumnos;
    private List<Alumno> alumnos;
    @FXML
    private JFXListView<String> lstGrupo;
    private ArrayList<String> nombresGrupos;
    private List<Grupo> grupos;

    private Persona persona;
    private Usuario usuario;
    private double porcentajeDesc = 0;

    public boolean setUserData() {
        String nombreUsuario = System.getProperty("nombreUsuario");
        usuario = new Usuario();
        usuario = usuario.buscar(nombreUsuario);
        this.persona = (Maestro) usuario.getMaestroCollection().toArray()[0];
        return true;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtMonto.setRequired(true);
        txtMonto.setCurrencyFilter();
        txtMonto.setText("$");

        lstGrupo.setExpanded(true);
        lstGrupo.depthProperty().set(1);
        lstAlumnos.setExpanded(true);
        lstAlumnos.depthProperty().set(1);

        comboPromocion.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                double porcentaje = comboPromocion.getValue().getDescuento();
                porcentajeDesc = porcentaje / 100;
                calcTotal();
            }
        });

        setUserData();
        setListGrupos();
        ValidatorBase numVal = new NumberValidator();
        numVal.setMessage("Inserte el monto");
        ValidatorBase req = new RequiredFieldValidator();
        req.setMessage("Inserte el monto de inscripción");

        txtMonto.setValidators(req);

        txtMonto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                calcTotal();

            }
        });

    }

    private void calcTotal() {
        DecimalFormat decFormat = new DecimalFormat("#.##");

        if (!txtMonto.getText().replace("$", "").trim().isEmpty()) {
            double monto = Double.valueOf(txtMonto.getText().replace("$", ""));
            labelMontoFinal.setText("$" + decFormat.format(monto * (1 - porcentajeDesc)));
        } else {
            labelMontoFinal.setText("$0.00");
        }
    }

    @FXML
    private void inscribirAlumno(ActionEvent event) {
        int grpIndex = lstGrupo.getSelectionModel().getSelectedIndex();
        int almIndex = lstAlumnos.getSelectionModel().getSelectedIndex();

        if (!txtMonto.getText().replace("$", "").trim().isEmpty() && almIndex != -1 && grpIndex != -1) {

            Grupo grupo = grupos.get(grpIndex);
            Alumno alumn = alumnos.get(almIndex);
            PagoAlumno pago = new PagoAlumno();
            PagoAlumno ultimoPago = new PagoAlumno();
            boolean registrarPago = true;
            boolean continuarRegistro = true;
            boolean tienePagosInscripcion = false;
            if (pago.contarPagosInscripcion(alumn.getIdAlumno(), grupo.getGrupoPK().getIdGrupo()) > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                ultimoPago = ultimoPago.obtenerUltimoPago(alumn.getIdAlumno(), grupo.getGrupoPK().getIdGrupo());
                alert.setContentText(alumn.getNombre() + " " + alumn.getApellidos()
                        + " tiene un pago vigente hasta el " + DateFormat.getDateInstance().format(ultimoPago.getFechaVencimiento())
                        + "\n Eliga la acción deseada");

                ButtonType buttonTypeOne = new ButtonType("Inscribir y pagar");
                ButtonType buttonTypeTwo = new ButtonType("Inscribir sin pagar");
                ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOne, buttonTypeTwo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeTwo) {
                    registrarPago = false;
                } else if (result.get() == buttonTypeOne) {
                    tienePagosInscripcion = true;
                } else {
                    continuarRegistro = false;
                }

            }

            if (continuarRegistro) {
                alumn.getGrupoCollection().add(grupo);
                grupo.getAlumnoCollection().add(alumn);
                alumn.actualizarDatos(false);
                grupo.actualizarDatosGrupo(grupo);
                if (registrarPago) {
                    pago.setAlumno(alumn);
                    pago.setGrupo(grupo);
                    pago.setEsInscripcion(true);
                    if (comboPromocion.getValue() != null && comboPromocion.getValue().getDescuento() != 0){
                    pago.setPromocion(comboPromocion.getValue());
                    }
                    pago.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")) * (1 - porcentajeDesc));
                    pago.setFechaPago(new Date());
                    Date vence;
                    if (tienePagosInscripcion) {
                        LocalDate date = ultimoPago.getFechaVencimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        vence = java.sql.Date.valueOf(date.plusYears(1));

                    } else {
                        vence = java.sql.Date.valueOf(LocalDate.now().plusYears(1));
                    }
                    pago.setFechaVencimiento(vence);
                    pago.registrarPago();
                }

                setListGrupos();
                Notifications.create()
                        .title("¡Exito!")
                        .text(alumn.getNombre() + " " + alumn.getApellidos()
                                + " inscrito en el grupo " + grupo.getNombre())
                        .showInformation();
            }
        } else if (almIndex == -1) {
            Mensajes.mensajeAlert("Selecciona un alumno");
        } else if (grpIndex == -1) {
            Mensajes.mensajeAlert("Selecciona un grupo");
        }

    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    private boolean setListGrupos() {
        Grupo g = new Grupo();
        nombresGrupos = new ArrayList();

        if (usuario.getTipoUsuario().equals("director")) {
            grupos = g.obtenerTodosLosGrupos();
            Promocion promoAux = new Promocion();
            promoAux.setCodigo("Ninguna");
            promoAux.setDescuento(0);
            List<Promocion> promoList = FXCollections.observableArrayList();
            for (Promocion p : promoAux.obtenerPromociones()){
              if(p.getParaInscripcion() == 1){
                promoList.add(p);
              }
            }
            promoList.add(0, promoAux);
            comboPromocion.setItems(FXCollections.observableArrayList(promoList));
        } else {
          List<Promocion> promoList = FXCollections.observableArrayList();
          for (Promocion p: ((Maestro) persona).getPromocionCollection()){
            if (p.getParaInscripcion() == 1){
              promoList.add(p);
            }
          }
            grupos = g.obtenerGruposMaestro(((Maestro) persona).getMaestroPK().getIdMaestro());
        }

        grupos.forEach((grupo) -> {
            nombresGrupos.add(grupo.getNombre());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresGrupos);
        lstGrupo.setItems(items);
        lstGrupo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lstAlumnos.setItems(null);
                nombresAlumnos = new ArrayList();
                int grpIndex = lstGrupo.getSelectionModel().getSelectedIndex();
                if (grpIndex != -1) {
                    alumnos = grupos.get(grpIndex).obtenerAlumnosNoInscritos();
                    alumnos.forEach((alumno) -> {
                        nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
                    });
                    ObservableList<String> items = FXCollections.observableArrayList();
                    items.addAll(nombresAlumnos);
                    lstAlumnos.setItems(items);
                }
            }
        });
        return true;
    }

}
