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
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import interfaces.Controlador;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Egreso;
import modelo.Maestro;
import modelo.Persona;
import modelo.Publicidad;
import modelo.PublicidadPK;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaEditarAnuncioController implements Initializable, Controlador {

  @FXML
  private Label lblMain;
  @FXML
  private JFXLimitedTextField txtMonto;
  @FXML
  private JFXDatePicker startPicker;
  @FXML
  private JFXDatePicker endPicker;
  @FXML
  private JFXTextArea txtDescripcion;
  @FXML
  private JFXLimitedTextField txtURL;
  @FXML
  private JFXButton btnGuardar;
  
  private Publicidad publicidad;
  private Date inicio;
  private Date fin;
  private Double monto;
  private String desc;
  private Maestro creador;
  private String url;
  
  private PantallaAnunciosController controlador;
  
   private HBox pantallaDividida;
  private StackPane pnlPrincipal;
  
  private boolean esNuevoAnuncio;
  @FXML
  private JFXComboBox<Persona> maestroCB;
  
  
   @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }

  public PantallaAnunciosController getControlador() {
    return controlador;
  }

  public void setControlador(PantallaAnunciosController controlador) {
    this.controlador = controlador;
  }
  
  public Publicidad getPublicidad() {
    return publicidad;
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
    startPicker.setEditable(false);
    endPicker.setEditable(false);
    txtMonto.setCurrencyFilter();
    txtMonto.setRequired(true);
    txtURL.setSizeLimiter(200);
    txtURL.setRequired(true);
    txtDescripcion.setValidators(new RequiredFieldValidator());
    txtDescripcion.setTextFormatter(new TextFormatter(limiteConcepto));
    Maestro maestroAux = new Maestro();
    
    maestroCB.setItems(FXCollections.observableArrayList(maestroAux.obtenerActivos()));
  }  

  @FXML
  private void actualizarPublicidad(ActionEvent event) {
    if (validarDatos()) {
      if (esNuevoAnuncio) {
        publicidad = new Publicidad(new PublicidadPK());
        Egreso egreso = new Egreso();
        egreso.setConcepto(txtDescripcion.getText().trim());
        egreso.setFecha(Date.from(startPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        egreso.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
        egreso.registrar();
        
        publicidad.setDescripcion(txtDescripcion.getText().trim());
        publicidad.setFechaInicio(Date.from(startPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        publicidad.setFechaFin(Date.from(endPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Maestro maestro = (Maestro) maestroCB.getValue();
        publicidad.setMaestro(maestro);
        publicidad.setUrl(txtURL.getText().trim());
        publicidad.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
        
        
        
        publicidad.setEgresoidEgreso(egreso);
        publicidad.registrar();

      } else {
        Egreso eg = publicidad.getEgresoidEgreso();
        eg.setConcepto(txtDescripcion.getText().trim());
        eg.setFecha(Date.from(startPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        eg.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
        eg.editar();
        Publicidad p = publicidad;
        
        publicidad.eliminar();

        p.setDescripcion(txtDescripcion.getText().trim());
        p.setFechaInicio(Date.from(startPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        p.setFechaFin(Date.from(endPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Maestro maestro = (Maestro) maestroCB.getValue();
        p.setMaestro(maestro);
        p.setUrl(txtURL.getText().trim());
        p.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
        p.setEgresoidEgreso(eg);
        p.registrar();
        
      }
      controlador.refreshList();
    }
  }
  
  public void setPublicidad(Publicidad publicidad) {
    if (publicidad != null) {
      this.publicidad = publicidad;
      this.monto = publicidad.getMonto();
      this.desc = publicidad.getDescripcion();
      this.inicio = publicidad.getFechaInicio();
      this.fin = publicidad.getFechaFin();
      this.url = publicidad.getUrl();
      this.creador = publicidad.getMaestro();
      cargarDatos();
      esNuevoAnuncio = false;
    } else {
      esNuevoAnuncio = true;
      lblMain.setText("Registrar Publicidad");
    }
  }
  
  public void cargarDatos() {
    txtDescripcion.setText(desc);
    txtMonto.setText(Double.toString(monto));
    txtURL.setText(url);
    maestroCB.getSelectionModel().select(creador);
    startPicker.setValue(
            inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    endPicker.setValue(
            fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());  
  }
  
  private boolean validarDatos() {
    boolean huboErrores = false;
    boolean errorDeMonto = false;
    txtDescripcion.setText(txtDescripcion.getText().trim());
    txtURL.setText(txtURL.getText().trim());
    if (txtMonto.getText().replace("$", "").trim().isEmpty()) {
      txtMonto.setTextFormatter(null);
      txtMonto.setText("");
      errorDeMonto = true;
    }
    if (!txtDescripcion.validate()) {
      huboErrores = true;
    }
    if (!txtMonto.validate()){
      huboErrores = true;
    }
    if (!txtURL.validate()){
      huboErrores = true;
    }
    if (errorDeMonto) {
      txtMonto.setText("$");
      txtMonto.setCurrencyFilter();
    }
    return !huboErrores;
  }

  @FXML
  private void chkInitialDate(ActionEvent event) {
    if (startPicker.getValue().isAfter(LocalDate.now())){
      startPicker.setValue(LocalDate.now());
      Mensajes.mensajeAlert("La fecha de inicio no puede ser posterior al día de hoy");
      chkEndDate(event);
    }
  }

  @FXML
  private void chkEndDate(ActionEvent event) {
    if (startPicker.getValue() != null){
      if (endPicker.getValue().isBefore(startPicker.getValue())){
        endPicker.setValue(startPicker.getValue().plusDays(1));
        Mensajes.mensajeAlert("La fecha de término no puede ser previa a la fecha de inicio");
        
      }
    }
  }

  
}
