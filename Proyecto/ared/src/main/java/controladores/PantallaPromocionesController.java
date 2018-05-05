package controladores;

import com.jfoenix.controls.JFXButton;
import static controladores.PantallaGruposController.obtnerHorarioGrupo;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import modelo.Maestro;
import modelo.Promocion;
import org.dom4j.Element;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaPromocionesController implements Initializable, Controlador {

    @FXML
    private ScrollPane scrollPromociones;
    @FXML
    private GridPane gridPromociones;
    @FXML
    private JFXButton bntNuevaPromocion;

    private Maestro maestro;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maestro = new Maestro();
        String nombreUsuario = System.getProperty("nombreUsuario");
        maestro = maestro.obtenerMaestro(nombreUsuario);
    }

    public void mostrarPromociones() {
        gridPromociones.setVgap(10);
        gridPromociones.setHgap(10);
        List<Promocion> promociones = new ArrayList(maestro.getPromocionCollection());
        int filas = promociones.size() / 3;
        int auxiliar = 0;
        if (promociones.size() % 3 != 0) {
            filas = ((promociones.size()) / 3) + 1;
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < 3; j++) {
                if (auxiliar < promociones.size()) {
                    FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/TarjetaInformacionPromocion.fxml"));
                    Parent root;
                    try {
                        root = (Parent) loader.load();
                        TarjetaInformacionPromocionController controlador = loader.getController();
                        controlador.setPromocion(promociones.get(auxiliar));
                        auxiliar++;
                        gridPromociones.add(root, j, i);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaPromocionesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }

    }

    @FXML
    private void crearPromocion(ActionEvent event) {
        PantallaPrincipalDirectorController.limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaRegistrarPromocion.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaRegistrarPromocionController controlador = loader.getController();
        controlador.setMaestro(maestro);
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);

    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
        mostrarPromociones();
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

}
