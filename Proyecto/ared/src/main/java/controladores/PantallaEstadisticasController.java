/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import interfaces.Controlador;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Ingreso;
import modelo.PagoMaestro;
import modelo.PagoRenta;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaEstadisticasController implements Initializable , Controlador{

  @FXML
  private StackPane stack;
  @FXML
  private JFXToggleButton toggleEgresos;
  @FXML
  private JFXToggleButton toggleIngresos;
  @FXML
  private JFXButton btnCerrar;
  @FXML
  private AreaChart<String, Number> grafica;
  
  private List<Ingreso> ingresos;
  
  private List<Double> ingresoMes = new ArrayList();
  
  private List<String> lblmeses;
  
  private List<Date> meses;
  
  private Date fechaMin = new Date();
  
  private HBox pantallaDividida;
  private StackPane pnlPrincipal;
  
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    obtenerMeses(12);
    cargarDatos();
    graficarIngresos();
    
    
  }  
   @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }
  
  private void obtenerMeses(int numMesesAnteriores){
    meses = new ArrayList();
    lblmeses = new ArrayList();
    SimpleDateFormat formatoMes = new SimpleDateFormat("MMM-yyyy");
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    for (int i = 1; i <= numMesesAnteriores; i++){
      String nombreMes = formatoMes.format(cal.getTime());
      meses.add(0,cal.getTime());
      lblmeses.add(0,nombreMes);
      cal.add(Calendar.MONTH, -1);
      fechaMin = meses.get(0);
    }
  }

  @FXML
  private void mostrarEgresos(ActionEvent event) {
  }

  @FXML
  private void mostrarIngresos(ActionEvent event) {
  }

  @FXML
  private void cerrarPantalla(ActionEvent event) {
  }
  
  private void cargarDatos(){
    PagoMaestro pm = new PagoMaestro();
    PagoRenta pr = new PagoRenta();

    ingresos = new ArrayList();
    ingresos.addAll(pm.obtenerTodos());
    ingresos.addAll(pr.obtenerTodos());
  }
  
  private void graficarIngresos(){
    XYChart.Series<String, Number> serieIngresos = new XYChart.Series<>();
    Calendar calIng = Calendar.getInstance();
    Calendar calMeses = Calendar.getInstance();
    for (Date d: meses) {
      ingresoMes.add(0.0);
    }
    for (Ingreso ing : ingresos){
      if (ing.getDate().after(fechaMin) && ing.getDate().before(new Date())){
        for (Date d : meses){
          calIng.setTime(ing.getDate());
          calMeses.setTime(d);
          if (calIng.get(Calendar.MONTH) == calMeses.get(Calendar.MONTH)){
            double valor = ingresoMes.get(meses.indexOf(d)) + ing.getMonto();
            ingresoMes.set(meses.indexOf(d), valor);
          }
        }
      }
    }
    
    for (String label: lblmeses){
      serieIngresos.getData().add(new XYChart.Data<>(label, ingresoMes.get(lblmeses.indexOf(label)) ));
    }
    grafica.getData().add(serieIngresos);
  }
}
