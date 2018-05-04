/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import interfaces.Controlador;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaListaAlumnosController implements Initializable, Controlador {

    @FXML
    private ListView<String> tbAlumnos;
    private List<Alumno> alumnos;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
        mostrarAlumnos();
    }    

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }
    
    public void mostrarAlumnos(){
        ArrayList<String> nombresAlumnos = new ArrayList();
        for(Alumno alumno: alumnos){
            nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
        }
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresAlumnos);
        tbAlumnos.setItems(items);
    }

    @FXML
    private void realizarPago(ActionEvent event) {
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRegistrarPagoAlumno.fxml", this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }
}
