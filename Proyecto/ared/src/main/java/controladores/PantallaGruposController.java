/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import modelo.Grupo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaGruposController implements Initializable, Controlador {

    @FXML
    private ScrollPane scrollGrupos;
    @FXML
    private AnchorPane pnlGrupos;
    @FXML
    private JFXButton btnRegistrarGrupo;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Document document;
    private Element gruposXML;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearArchivoXML();
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.dir") + "/horariosAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = document.getRootElement();
        gruposXML = root.element("grupos");

        scrollGrupos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;

    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        pnlGrupos.getChildren().add(mostrarGrupos());
    }

    public GridPane mostrarGrupos() {
        Grupo grupo = new Grupo();
        List<Grupo> grupos = grupo.obtenerTodosLosGrupos();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        int filas = grupos.size() / 2;
        int auxiliar = 0;
        if (grupos.size() % 2 != 0) {
            filas = (grupos.size() + 1) / 2;
        }

        for (int i = 0; i < filas; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/TarjetaInformacionGrupo.fxml"));
                Parent root = (Parent) loader.load();
                TarjetaInformacionGrupoController controlador = loader.getController();
                controlador.setGrupo(grupos.get(auxiliar));
                Element grupoXML = (Element) gruposXML.selectSingleNode("/ared/grupos/grupo[@id = "
                        + "'" + grupos.get(auxiliar).getGrupoPK().getIdGrupo() + "']");
                controlador.setColorGrupo(Color.web(grupoXML.attributeValue("color")));
                controlador.agregarHorario(obtnerHorarioGrupo(grupoXML));
                controlador.setPantallaDividida(pantallaDividida);
                controlador.setPnlPrincipal(pnlPrincipal);
                auxiliar++;
                grid.add(root, 0, i);
            } catch (IOException ex) {
                Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (auxiliar < grupos.size()) {
                try {
                    FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/TarjetaInformacionGrupo.fxml"));
                    Parent root = (Parent) loader.load();
                    TarjetaInformacionGrupoController controlador = loader.getController();
                    controlador.setGrupo(grupos.get(auxiliar));
                    Element grupoXML = (Element) gruposXML.selectSingleNode("/ared/grupos/grupo[@id = "
                            + "'" + grupos.get(auxiliar).getGrupoPK().getIdGrupo() + "']");
                    controlador.agregarHorario(obtnerHorarioGrupo(grupoXML));
                    controlador.setPantallaDividida(pantallaDividida);
                    controlador.setPnlPrincipal(pnlPrincipal);
                    auxiliar++;
                    grid.add(root, 1, i);
                } catch (IOException ex) {
                    Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return grid;
    }

    @FXML
    private void abrirVentanaRegistrarGrupo(ActionEvent event) {
        Parent root = PantallaPrincipalDirectorController.crearPantalla("/fxml/PantallaRegistrarGrupo.fxml",
                pnlPrincipal, pantallaDividida);
        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    public String obtnerHorarioGrupo(Element grupo) {
        String horario = "";
        if (grupo != null) {
            Element horarioXML = grupo.element("horario");
            for (Iterator<Element> itr3 = horarioXML.elementIterator("dia"); itr3.hasNext();) {
                Element dia = itr3.next();
                String columna = String.valueOf(dia.attributeValue("nombreDia"));
                horario += columna;
                for (Iterator<Element> itr4 = dia.elementIterator("hora"); itr4.hasNext();) {
                    String horaIncio = (String.valueOf(((Element) itr4.next()).getData())).trim();
                    String horaFin = (String.valueOf(((Element) itr4.next()).getData())).trim();

                    horario += " " + horaIncio + "-" + horaFin;

                }
                horario += "\n";
            }
        }

        return horario;
    }

    public void crearArchivoXML() {
        String ruta = System.getProperty("user.dir") + "/horariosAred.xml";
        System.out.println(ruta);
        File file = new File(ruta);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (FileWriter fileWriter = new FileWriter(file);
                    PrintWriter printWriter = new PrintWriter(fileWriter,true)) {
                String contenido = "<ared>"
                        +   "<grupos>"
                        +   "</grupos>"
                        +   "<rentas>"
                        +   "</rentas>"
                        + "</ared>";
                printWriter.write(contenido);
            } catch (IOException ex) {
                Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
