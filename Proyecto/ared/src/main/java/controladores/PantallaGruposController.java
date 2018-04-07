/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
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
import modelo.Grupo;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrollGrupos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pnlGrupos.getChildren().add(mostrarGrupos());
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;

    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
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
                auxiliar++;
                controlador.setPantallaDividida(pantallaDividida);
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
                    auxiliar++;
                    controlador.setPantallaDividida(pantallaDividida);
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

}
