/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Egreso;
import modelo.Ingreso;
import modelo.PagoMaestro;
import modelo.PagoRenta;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaEstadisticasController implements Initializable, Controlador {

  @FXML
  private JFXToggleButton toggleEgresos;
  @FXML
  private JFXToggleButton toggleIngresos;
  @FXML
  private JFXToggleButton togglePagoM;
  @FXML
  private JFXToggleButton togglePagoR;
  @FXML
  private JFXToggleButton toggleUtilidad;
  @FXML
  private JFXButton btnCerrar;
  @FXML
  private JFXComboBox<String> cbPeriodo;
  @FXML
  private AreaChart<String, Number> grafica;

  private List<Ingreso> ingresos;
  private List<Egreso> egresos;

  private List<Double> ingresoMes = new ArrayList();
  private List<Double> pMaestroMes = new ArrayList();
  private List<Double> pRentaMes = new ArrayList();
  private List<Double> egresoMes = new ArrayList();
  private List<Double> utilidadMes = new ArrayList();

  private ObservableList<String> lblmeses = FXCollections.observableArrayList();

  private List<Date> meses = new ArrayList();

  private Date fechaMin = new Date();

  private HBox pantallaDividida;
  private StackPane pnlPrincipal;

  Series<String, Number> serieIngresos = new Series<>();
  Series<String, Number> seriePMaestro = new Series<>();
  Series<String, Number> seriePRenta = new Series<>();
  Series<String, Number> serieEgresos = new Series<>();
  Series<String, Number> serieUtilidad = new Series<>();
  @FXML
  private NumberAxis yaxis;
  @FXML
  private CategoryAxis xaxis;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    cargarDatos();
    xaxis.setCategories(lblmeses);

    ObservableList<String> opciones = FXCollections.observableArrayList("12 Meses", "6 Meses", "3 Meses");
    cbPeriodo.setItems(opciones);

    graficar(12);

  }

  @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }

  private void obtenerMeses(int numMesesAnteriores) {
    meses.clear();
    lblmeses.clear();
    SimpleDateFormat formatoMes = new SimpleDateFormat("MMM-yyyy");
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    for (int i = 1; i <= numMesesAnteriores; i++) {
      String nombreMes = formatoMes.format(cal.getTime());
      meses.add(0, cal.getTime());
      lblmeses.add(0, nombreMes);
      cal.add(Calendar.MONTH, -1);
      fechaMin = meses.get(0);
    }
  }

  private void cargarDatos() {
    PagoMaestro pm = new PagoMaestro();
    PagoRenta pr = new PagoRenta();
    Egreso eg = new Egreso();

    ingresos = new ArrayList();
    ingresos.addAll(pm.obtenerTodos());
    ingresos.addAll(pr.obtenerTodos());

    egresos = new ArrayList();
    egresos = eg.obtenerTodos();

  }

  private void graficarIngresos(int numMeses) {
    serieIngresos.getData().clear();
    seriePMaestro = new XYChart.Series<>();
    seriePRenta = new XYChart.Series<>();
    ingresoMes.clear();
    pMaestroMes.clear();
    pRentaMes.clear();
    Calendar calIng = Calendar.getInstance();
    Calendar calMeses = Calendar.getInstance();
    for (int i = 0; i < numMeses; i++) {
      ingresoMes.add(0.0);
      pMaestroMes.add(0.0);
      pRentaMes.add(0.0);
    }
    for (Ingreso ing : ingresos) {
      if (ing.getDate().after(fechaMin) && ing.getDate().before(new Date())) {
        for (Date d : meses) {
          calIng.setTime(ing.getDate());
          calMeses.setTime(d);
          if (calIng.get(Calendar.MONTH) == calMeses.get(Calendar.MONTH)) {
            int insertInMonth = meses.indexOf(d);
            double valor = ingresoMes.get(insertInMonth) + ing.getMonto();
            ingresoMes.set(insertInMonth, valor);
            if (ing.getClass().equals(PagoMaestro.class)) {
              valor = pMaestroMes.get(insertInMonth) + ing.getMonto();
              pMaestroMes.set(insertInMonth, valor);
            } else {
              valor = pRentaMes.get(insertInMonth) + ing.getMonto();
              pRentaMes.set(insertInMonth, valor);
            }
          }
        }
      }
    }

    for (String label : lblmeses) {
      int index = lblmeses.indexOf(label);
      serieIngresos.getData().add(new XYChart.Data<>(label, ingresoMes.get(index)));
      seriePMaestro.getData().add(new XYChart.Data<>(label, pMaestroMes.get(index)));
      seriePRenta.getData().add(new XYChart.Data<>(label, pRentaMes.get(index)));

    }
    serieIngresos.setName("Ingresos");
    seriePMaestro.setName("Pagos de Maestros");
    seriePRenta.setName("Pagos de Renta");

//    grafica.getData().add(serieIngresos);
//    grafica.getData().add(seriePRenta);
//    grafica.getData().add(seriePMaestro);
  }

  private void graficarEgresosUtilidad(int numMeses) {
    serieEgresos.getData().clear();

    egresoMes.clear();
    utilidadMes.clear();
    Calendar calEg = Calendar.getInstance();
    Calendar calMeses = Calendar.getInstance();
    for (int i = 0; i < numMeses; i++) {
      egresoMes.add(0.0);
      utilidadMes.add(0.0);

    }

    for (Egreso egIdx : egresos) {
      if (egIdx.getFecha().after(fechaMin) && egIdx.getFecha().before(new Date())) {
        for (Date d : meses) {
          calEg.setTime(egIdx.getFecha());
          calMeses.setTime(d);

          if (calEg.get(Calendar.MONTH) == calMeses.get(Calendar.MONTH)) {
            int insertInMonth = meses.indexOf(d);
            double valor = egresoMes.get(insertInMonth) - egIdx.getMonto();
            egresoMes.set(insertInMonth, valor);
          }
        }
      }
    }
    lblmeses.forEach((label) -> {
      int index = lblmeses.indexOf(label);
      serieEgresos.getData().add(new XYChart.Data<>(label, egresoMes.get(index)));
      serieUtilidad.getData().add(new XYChart.Data<>(label,
              (ingresoMes.get(index) + egresoMes.get(index))));
    });
    serieEgresos.setName("Egresos");
    serieUtilidad.setName("Utilidad");

//    grafica.getData().add(serieEgresos);
//    grafica.getData().add(serieUtilidad);
  }

  @FXML
  private void mostrarPagoMaestro(ActionEvent event) {
    if (togglePagoM.isSelected()) {
      if (!grafica.getData().contains(seriePMaestro)) {
        grafica.getData().add(seriePMaestro);
      }
    } else {
      grafica.getData().remove(seriePMaestro);
    }

  }

  @FXML
  private void mostrarPagoR(ActionEvent event) {
    if (togglePagoR.isSelected()) {
      if (!grafica.getData().contains(seriePRenta)) {
        grafica.getData().add(seriePRenta);
      }
    } else {
      grafica.getData().remove(seriePRenta);
    }
  }

  @FXML
  private void mostrarEgresos(ActionEvent event) {
    if (toggleEgresos.isSelected()) {
      if (!grafica.getData().contains(serieEgresos)) {
        grafica.getData().add(serieEgresos);
      }
    } else if (grafica.getData().contains(serieEgresos)) {
      grafica.getData().remove(serieEgresos);
    }

  }

  @FXML
  private void mostrarIngresos(ActionEvent event) {
    if (toggleIngresos.isSelected()) {
      if (!grafica.getData().contains(serieIngresos)) {
        grafica.getData().add(serieIngresos);
      }
    } else {
      grafica.getData().remove(serieIngresos);
    }

  }

  @FXML
  private void cerrarPantalla(ActionEvent event) {

  }

  @FXML
  private void cambiarPeriodo(ActionEvent event) {
    int periodoSelecionado = cbPeriodo.getSelectionModel().getSelectedIndex();
    switch (periodoSelecionado) {
      case 0:
        graficar(12);
        break;
      case 1:
        graficar(6);
        break;
      case 2:
        graficar(3);
        break;
    }
  }

  private void graficar(int meses) {
    serieUtilidad = new Series<>();
    serieEgresos = new Series<>();
    serieIngresos = new Series<>();
    seriePMaestro = new Series<>();
    seriePRenta = new Series<>();

    grafica.getData().clear();

    obtenerMeses(meses);

    xaxis.setGapStartAndEnd(false);
    graficarIngresos(meses);
    graficarEgresosUtilidad(meses);
    grafica.getData().addAll(serieIngresos, serieEgresos, seriePMaestro, seriePRenta, serieUtilidad);
  }

  @FXML
  private void mostrarUtilidad(ActionEvent event) {
    if (toggleUtilidad.isSelected()) {
      if (!grafica.getData().contains(serieUtilidad)) {
        grafica.getData().add(serieUtilidad);
      }
    } else {
      grafica.getData().remove(serieUtilidad);
    }

  }
}
