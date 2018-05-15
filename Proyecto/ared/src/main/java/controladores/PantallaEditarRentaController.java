/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mapas;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.Renta;
import modelo.RentaXML;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaEditarRentaController implements Initializable, Controlador {

    @FXML
    private Label lblFecha;
    @FXML
    private Label lblNombreCliente;
    @FXML
    private Label lblHorario;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelarRenta;
    @FXML
    private JFXButton btnEditarHorario;
    
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private RentaXML rentaXML;
    private Renta renta;
    private ArrayList<Integer> horarioRenta;
    private Mapas mapa;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        horarioRenta = new ArrayList();
        mapa = new Mapas();
    }    

    @FXML
    private void actualizarRenta(ActionEvent event) {
    }

    @FXML
    private void cancelarRenta(ActionEvent event) {
    }

    @FXML
    private void editarHorario(ActionEvent event) {
        Parent root = null;
            FXMLLoader loader = new FXMLLoader(PantallaEditarRentaController.class.getResource("/fxml/PantallaEditarHorarioRenta.fxml"));
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            PantallaEditarHorarioRentaController controlador = loader.getController();
            controlador.setRenta(rentaXML);
            controlador.setHorarioRenta(horarioRenta);
            controlador.setControlador(this);
            controlador.cargarDatos();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        
    }
    
    public void cambiarHorario(String dia, String horario){
        lblFecha.setText(dia);
        lblHorario.setText(horario);
    }

    public void setRentaXML(RentaXML rentaXML) {
        this.rentaXML = rentaXML;
        lblFecha.setText(this.rentaXML.getDia());
        lblNombreCliente.setText(this.rentaXML.getNombreCliente());
        lblHorario.setText(this.rentaXML.getHorario());
        txtMonto.setText(this.rentaXML.getMonto());
        obtenerHorario();
    }
    
    public void obtenerHorario(){
        int horaInicio = Integer.parseInt(mapa.getMapaHoras().get(this.rentaXML.getHorario().split("-")[0]).toString());
        int horaFin = Integer.parseInt(mapa.getMapaHoras().get(this.rentaXML.getHorario().split("-")[1]).toString());        
        for(int i = horaInicio; i < horaFin; i++){
            horarioRenta.add(i);
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
    
    
}
