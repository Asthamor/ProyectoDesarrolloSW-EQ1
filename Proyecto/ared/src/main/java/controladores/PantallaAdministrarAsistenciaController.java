/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.PantallaPrincipalDirectorController.limpiarPanelPrincipal;
import interfaces.Controlador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Grupo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaAdministrarAsistenciaController implements Initializable, Controlador {

    @FXML
    private ListView<String> lstFechas;
    @FXML
    private ListView<String> lstAlumnos;

    private Document documento;
    private Element root;
    private Element gruposXML;
    private ArrayList<String> fechasLista;
    private ArrayList<String> nombresAlumnos;
    private Element grupoXML;
    private HashMap<String, Boolean> hashMap;
    private Element diaXML;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Grupo grupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechasLista = new ArrayList();
        crearXML();
        SAXReader reader = new SAXReader();
        try {
            documento = reader.read(System.getProperty("user.dir") + "/asistenciaAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaAdministrarAsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        root = documento.getRootElement();
        gruposXML = root.element("grupos");

    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        mostrarFechas();
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void mostrarFechas() {
        grupoXML = (Element) documento.selectSingleNode("/ared/grupos/grupo[@id = '" + grupo.getGrupoPK().getIdGrupo() + "']");
        if (grupoXML != null) {
            List<Element> dias = grupoXML.elements("dia");
            if (!dias.isEmpty()) {
                for (int i = dias.size() - 1; i >= 0; i--) {
                    fechasLista.add(dias.get(i).attributeValue("fecha"));
                }
                ObservableList<String> items = FXCollections.observableArrayList();
                items.addAll(fechasLista);
                lstFechas.setItems(items);
                lstFechas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        lstAlumnos.setItems(null);
                        nombresAlumnos = new ArrayList();
                        hashMap = new HashMap<>();
                        if (lstFechas.getSelectionModel().getSelectedIndex() != -1) {
                            diaXML = (Element) documento.selectSingleNode("/ared/grupos/grupo[@id = '" + grupo.getGrupoPK().getIdGrupo() + "']/dia[@fecha = '" + lstFechas.getSelectionModel().getSelectedItem() + "']");
                            List<Element> alumnos = diaXML.elements("alumno");
                            if (!alumnos.isEmpty()) {
                                for (Element alumno : alumnos) {
                                    nombresAlumnos.add(alumno.attributeValue("nombre"));
                                    if (alumno.attributeValue("asistencia").equals("1")) {
                                        hashMap.put(alumno.attributeValue("nombre"), Boolean.TRUE);
                                    } else {
                                        hashMap.put(alumno.attributeValue("nombre"), Boolean.FALSE);
                                    }

                                }
                                ObservableList<String> items = FXCollections.observableArrayList();
                                items.addAll(nombresAlumnos);
                                lstAlumnos.setItems(items);
                                lstAlumnos.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
                                    @Override
                                    public ObservableValue<Boolean> call(String item) {
                                        BooleanProperty observable = new SimpleBooleanProperty();
                                        observable.set(hashMap.get(item));
                                        observable.addListener((obs, wasSelected, isNowSelected)
                                                -> hashMap.replace(item, wasSelected, isNowSelected)
                                        );
                                        return observable;
                                    }
                                }));
                            }
                        }
                    }

                });
            }
        } else {
            Document document = DocumentHelper.createDocument();
            grupoXML = document.addElement("grupo");
            grupoXML.addAttribute("id", String.valueOf(grupo.getGrupoPK().getIdGrupo()));
            gruposXML.add(grupoXML);
            try {
                XMLWriter writer = new XMLWriter(
                        new FileWriter(System.getProperty("user.dir") + "/asistenciaAred.xml"));
                writer.write(this.documento);
                writer.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void guardar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setHeaderText("Atención");
        alert.setContentText("¿Seguro que deceas modificar la lista de asistencia?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Document document = DocumentHelper.createDocument();
            Element diaXML = document.addElement("dia");
            diaXML.addAttribute("fecha", DateFormat.getDateInstance().format(new Date()));
            for (String nombreAlumno : nombresAlumnos) {
                Element alumnoXML = diaXML.addElement("alumno");
                alumnoXML.addAttribute("nombre", nombreAlumno);
                if (hashMap.get(nombreAlumno)) {
                    alumnoXML.addAttribute("asistencia", "1");
                } else {
                    alumnoXML.addAttribute("asistencia", "0");
                }
            }
            grupoXML.remove(this.diaXML);
            grupoXML.add(diaXML);
            try {
                XMLWriter writer = new XMLWriter(
                        new FileWriter(System.getProperty("user.dir") + "/asistenciaAred.xml"));
                writer.write(this.documento);
                writer.close();
                limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(PantallaListaAlumnosController.class.getResource("/fxml/PantallaAdministrarAsistencia.fxml"));
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(PantallaListaAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void crearXML() {
        String ruta = System.getProperty("user.dir") + "/asistenciaAred.xml";
        //String ruta = "C:/Users/raymu/Desktop/asistenciaAred.xml";
        File file = new File(ruta);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PantallaAdministrarAsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (FileWriter fileWriter = new FileWriter(file);
                    PrintWriter printWriter = new PrintWriter(fileWriter, true)) {
                String contenido = "<ared>\n"
                        + "   <grupos>\n"
                        + "   </grupos>\n"
                        + "</ared>";
                printWriter.write(contenido);
            } catch (IOException ex) {
                Logger.getLogger(PantallaAdministrarAsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void nuevaLista(ActionEvent event) throws IOException {
        if (!new ArrayList(grupo.getAlumnoCollection()).isEmpty()) {
            if (fechasLista.isEmpty() || !fechasLista.get(0).equals(DateFormat.getDateInstance().format(new Date()))) {
                limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(PantallaAdministrarAsistenciaController.class.getResource("/fxml/PantallaNuevaAsistencia.fxml"));
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(PantallaAdministrarAsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                PantallaNuevaAsistenciaController controlador = loader.getController();
                controlador.setGrupo(grupo);
                controlador.setHorario(documento, grupoXML);
                controlador.setPantallaDividida(pantallaDividida);
                controlador.setPnlPrincipal(pnlPrincipal);
                pnlPrincipal.getChildren().add(root);
                pantallaDividida.getChildren().add(pnlPrincipal);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Atencion");
                alert.setHeaderText(null);
                alert.setContentText("Ya has creado la lista de este dia");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Atencion");
            alert.setHeaderText(null);
            alert.setContentText("No existen alumnos inscritos en el grupo");
            alert.showAndWait();
        }
    }

}
