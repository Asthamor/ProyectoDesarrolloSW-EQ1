/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.PantallaGruposController.crearArchivoXML;
import static controladores.PantallaGruposController.obtnerHorarioGrupo;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Grupo;
import modelo.Maestro;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaMisGruposController implements Initializable, Controlador {

    @FXML
    private ScrollPane scrollGrupos;
    @FXML
    private GridPane gridGrupos;
    
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Maestro maestro;
    private Element gruposXML;
    private Document document;
  @FXML
  private Label lblNohay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearArchivoXML();
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.home") + "/.ared/horariosAred.xml");
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
        mostrarMisGrupos();
    }
    
    public void mostrarMisGrupos(){
        maestro = new Maestro();
        String nombreUsuario = System.getProperty("nombreUsuario");
        maestro = maestro.obtenerMaestro(nombreUsuario);
        gridGrupos.setVgap(20);
        gridGrupos.setHgap(20);
        int filas = maestro.getGrupoCollection().size() / 2;
        int auxiliar = 0;
        if (maestro.getGrupoCollection().isEmpty()){
          lblNohay.setVisible(true);
        }
        if (maestro.getGrupoCollection().size() % 2 != 0) {
            filas = (maestro.getGrupoCollection().size() + 1) / 2;
        }
        List<Grupo> grupos = new ArrayList(maestro.getGrupoCollection());
         for (int i = 0; i < filas; i++) {
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
                controlador.setAlumnos(new ArrayList(grupos.get(auxiliar).getAlumnoCollection()));
                controlador.setEditarGrupo(false);
                auxiliar++;
                gridGrupos.add(root, 0, i);
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
                    controlador.setAlumnos(new ArrayList(grupos.get(auxiliar).getAlumnoCollection()));
                    controlador.setEditarGrupo(false);
                    auxiliar++;
                    gridGrupos.add(root, 1, i);
                } catch (IOException ex) {
                    Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
