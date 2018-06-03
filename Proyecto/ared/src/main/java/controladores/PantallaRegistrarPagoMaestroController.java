/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import com.jfoenix.controls.JFXButton;
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
import com.jfoenix.controls.JFXListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Maestro;
import modelo.PagoMaestro;
import modelo.Persona;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistrarPagoMaestroController implements Initializable, Controlador {

    @FXML
    private JFXListView<String> lstMaestros;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblFechaPago;
    @FXML
    private JFXLimitedTextField lblNombreMaestro;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXLimitedTextField txtMonto;
    @FXML
    private Label labelProximoPago;

    private ArrayList<String> nombresColaboradores;
    private Maestro maestro;
    private List<Persona> maestros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstMaestros.setExpanded(true);
        lstMaestros.depthProperty().set(1);
        nombresColaboradores = new ArrayList();
        crearValidaciones();
    }

    public void crearValidaciones() {
        txtMonto.setRequired(true);
        txtMonto.setCurrencyFilter();
        txtMonto.setText("$");
        lblNombreMaestro.setRequired(true);
    }

    public void mostrarColaboradores() {
        lstMaestros.getItems().clear();
        lblFechaPago.setText(DateFormat.getDateInstance().format(new Date()));
        maestro = new Maestro();
        maestros = maestro.obtenerTodos();
        maestros.forEach((colaborador) -> {
            nombresColaboradores.add(colaborador.getNombre() + " " + colaborador.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresColaboradores);
        lstMaestros.setItems(items);
        lstMaestros.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (lstMaestros.getSelectionModel().getSelectedIndex() != -1) {
                    Maestro maestro = (Maestro) maestros.get(lstMaestros.getSelectionModel().getSelectedIndex());
                    List<PagoMaestro> pagos = new ArrayList<>(maestro.getPagoMaestroCollection());
                    if (pagos.size() > 0) {
                        PagoMaestro ultimoPago = pagos.get(pagos.size() - 1);
                        Date fechaActualPago = ultimoPago.getFechaVencimiento();
                        labelProximoPago.setText(DateFormat.getDateInstance().format(fechaActualPago));
                    } else {
                        labelProximoPago.setText("Primer pago");
                    }
                    lblNombreMaestro.setText(maestros.get(lstMaestros.getSelectionModel().getSelectedIndex()).getNombre() + " " + maestros.get(lstMaestros.getSelectionModel().getSelectedIndex()).getApellidos());
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
        if (!existenCamposVacios()) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(new Date());
            calendario.add(Calendar.MONTH, 1);
            Date fechaVencimiento = calendario.getTime();
            Maestro maestro = (Maestro) maestros.get(lstMaestros.getSelectionModel().getSelectedIndex());
            List<PagoMaestro> pagos = new ArrayList<>(maestro.getPagoMaestroCollection());

            if (pagos.size() > 0) {
                PagoMaestro ultimoPago = pagos.get(pagos.size() - 1);
                Date fechaActualPago = ultimoPago.getFechaVencimiento();
                Date fechaActual = new Date();
                if (fechaActualPago.getTime() > fechaActual.getTime()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Atención");
                    alert.setHeaderText("Atención");
                    alert.setContentText("El ultimo pago se realizó el: " + DateFormat.getDateInstance().format(ultimoPago.getFecha()) + ",\n"
                            + "el proximo pago se debe realizar el: " + DateFormat.getDateInstance().format(fechaActualPago) + " \n¿Seguro que desea continuar?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        bandera = false;
                        calendario.setTime(fechaActualPago);
                        calendario.add(Calendar.MONTH, 1);
                        fechaVencimiento = calendario.getTime();
                    } else {
                        bandera = true;
                    }

                }
            }

            if (bandera != true) {
                PagoMaestro pagoMaestro = new PagoMaestro();
                pagoMaestro.setMaestro((Maestro) maestros.get(lstMaestros.getSelectionModel().getSelectedIndex()));
                pagoMaestro.setFecha(new Date());
                pagoMaestro.setFechaVencimiento(fechaVencimiento);
                pagoMaestro.setMonto(Double.parseDouble(txtMonto.getText().replace("$", "")));
                if (pagoMaestro.registrarPago()) {
                    Notifications.create()
                    .title("¡Exito!")
                    .text("El pago se registro correctamente")
                    .showInformation();
                    mostrarColaboradores();
                    txtMonto.setText("$");
                    labelProximoPago.setText("");
                    lblNombreMaestro.setText("");
                }
            }

        }
    }

    public boolean existenCamposVacios() {
        boolean errorDeMonto = false;
        boolean huboErrores = false;

        if (txtMonto.getText().replace("$", "").trim().isEmpty()) {
            txtMonto.setTextFormatter(null);
            txtMonto.setText("");
            errorDeMonto = true;
            huboErrores = true;
        }
        if (!txtMonto.validate()) {
            huboErrores = true;
        }
        if (errorDeMonto) {
            txtMonto.setText("$");
            txtMonto.setCurrencyFilter();
        }

        return huboErrores | !lblNombreMaestro.validate();
    }

}
