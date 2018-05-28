/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import interfaces.Controlador;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Egreso;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaEditarEgresoController implements Initializable, Controlador {

  @FXML
  private JFXLimitedTextField txtMonto;
  @FXML
  private JFXTextArea txtConcepto;
  @FXML
  private JFXDatePicker pickerDate;
  @FXML
  private JFXButton btnGuardar;
  @FXML
  private Label lblMain;
  private boolean esNuevoEgreso;
  private Egreso egreso;

  private HBox pantallaDividida;
  private StackPane pnlPrincipal;
  private String concepto;
  private double monto;
  private Date fecha;

  private PantallaEgresosController controlador;
  private RequiredFieldValidator reqVal;

  public PantallaEgresosController getControlador() {
    return controlador;
  }

  public void setControlador(PantallaEgresosController controlador) {
    this.controlador = controlador;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    UnaryOperator<TextFormatter.Change> limiteConcepto = (change -> {
      String newText = change.getControlNewText();
      if (newText.matches(".{0,150}")) {
        return change;
      }
      return null;
    });

    txtMonto.setCurrencyFilter();
    txtMonto.setRequired(true);
    pickerDate.setEditable(false);
    reqVal = new RequiredFieldValidator();
    ((JFXTextField) pickerDate.getEditor()).setValidators(reqVal);
    txtConcepto.setValidators(reqVal);
    txtConcepto.setTextFormatter(new TextFormatter(limiteConcepto));

    // TODO
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
  private void actualizarEgreso(ActionEvent event) {
    if (validarDatos()) {
      if (esNuevoEgreso) {
        egreso = new Egreso();
        egreso.setConcepto(txtConcepto.getText());
        egreso.setFecha(Date.from(pickerDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        egreso.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
        egreso.registrar();
      } else {
        egreso.setConcepto(txtConcepto.getText());
        egreso.setFecha(Date.from(pickerDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        egreso.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
        egreso.editar();
      }
      controlador.refreshList();
    }
  }

  public void setEgreso(Egreso egreso) {
    if (egreso != null) {
      this.egreso = egreso;
      this.monto = egreso.getMonto();
      this.concepto = egreso.getConcepto();
      this.fecha = egreso.getFecha();
      cargarDatos();
      esNuevoEgreso = false;
    } else {
      esNuevoEgreso = true;
      lblMain.setText("Registrar Egreso");
    }
  }

  public void cargarDatos() {
    txtConcepto.setText(concepto);

    txtMonto.setText(Double.toString(monto));
    pickerDate.setValue(
            fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
  }

  private boolean validarDatos() {
    boolean errores = false;

    boolean errorDeMonto = false;
    
    if (txtMonto.getText().replace("$", "").trim().isEmpty()) {
      txtMonto.setTextFormatter(null);
      txtMonto.setText("");
      errorDeMonto = true;
    }
    if (!txtConcepto.validate()) {
      errores = true;
    }
    if (!txtMonto.validate()){
      errores = true;
    }
    if (errorDeMonto) {
      txtMonto.setText("$");
      txtMonto.setCurrencyFilter();
    }

    return !errores;
  }

}
