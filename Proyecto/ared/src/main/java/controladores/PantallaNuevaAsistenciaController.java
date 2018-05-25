/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.PantallaPrincipalDirectorController.limpiarPanelPrincipal;
import interfaces.Controlador;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.jfoenix.controls.JFXListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.Grupo;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaNuevaAsistenciaController implements Initializable, Controlador {

    @FXML
    private JFXListView<String> lstAlumnos;
    private ArrayList<String> nombreAlumnos;
    private HashMap<String, Boolean> hashMap;
    private Document documento;
    private Element grupoXML;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Grupo grupo;
    private List<Alumno> alumnos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreAlumnos = new ArrayList();
        hashMap = new HashMap<>();
        lstAlumnos.setExpanded(true);
        lstAlumnos.depthProperty().set(1);
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        mostrarAlumnos();
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setHorario(Document documento, Element grupoXML) {
        this.documento = documento;
        this.grupoXML = grupoXML;
    }

    public void mostrarAlumnos() {      
        alumnos = new ArrayList(grupo.getAlumnoCollection());
        for (Alumno alumno : alumnos) {
            nombreAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
            hashMap.put(alumno.getNombre() + " " + alumno.getApellidos(), Boolean.FALSE);
        }
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombreAlumnos);
        lstAlumnos.setItems(items);
        lstAlumnos.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(String item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected)
                        -> hashMap.replace(item, wasSelected, isNowSelected)
                );
                return observable;
            }
        }));
    }

    @FXML
    private void registrar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setHeaderText("Atención");
        alert.setContentText("¿Desea registrar la lista de asistencia?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Document document = DocumentHelper.createDocument();
            Element diaXML = document.addElement("dia");
            diaXML.addAttribute("fecha", DateFormat.getDateInstance().format(new Date()));
            for (String nombreAlumno : nombreAlumnos) {
                Element alumnoXML = diaXML.addElement("alumno");
                alumnoXML.addAttribute("nombre", nombreAlumno);
                if (hashMap.get(nombreAlumno)) {
                    alumnoXML.addAttribute("asistencia", "1");
                } else {
                    alumnoXML.addAttribute("asistencia", "0");
                }
            }
            grupoXML.add(diaXML);
            try {
                XMLWriter writer = new XMLWriter(
                        new FileWriter(System.getProperty("user.dir") + "/asistenciaAred.xml"));
                writer.write(this.documento);
                writer.close();
                limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(PantallaNuevaAsistenciaController.class.getResource("/fxml/PantallaAdministrarAsistencia.fxml"));
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(PantallaNuevaAsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                PantallaAdministrarAsistenciaController controlador = loader.getController();
                controlador.setGrupo(grupo);
                controlador.setPantallaDividida(pantallaDividida);
                controlador.setPnlPrincipal(pnlPrincipal);

                pnlPrincipal.getChildren().add(root);
                pantallaDividida.getChildren().add(pnlPrincipal);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
