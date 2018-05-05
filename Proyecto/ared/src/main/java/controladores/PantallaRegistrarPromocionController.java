package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.converter.IntegerStringConverter;
import modelo.Maestro;
import modelo.Promocion;
import modelo.PromocionPK;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaRegistrarPromocionController implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblDescuento;
    @FXML
    private Spinner<Integer> spinnerDescuento;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXButton btnGuardar;

    private Maestro maestro;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private Label lblTipoPromocion;
    @FXML
    private JFXComboBox<String> cmbTipoPromocion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0
                        || parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 0, filter);
        spinnerDescuento.setEditable(true);
        spinnerDescuento.setFocusTraversable(false);
        SpinnerValueFactory<Integer> valueFactory
                = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        spinnerDescuento.setValueFactory(valueFactory);
        spinnerDescuento.getEditor().setTextFormatter(priceFormatter);
        List<String> tipoPromociones = new ArrayList();
        tipoPromociones.add("Mensualidad");
        tipoPromociones.add("Inscripción");
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(tipoPromociones);
        cmbTipoPromocion.setItems(items);

    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    @FXML
    private void guardarPromocion(ActionEvent event) {
        Promocion promocion = new Promocion();
        promocion.setCodigo(txtNombre.getText());
        promocion.setConcepto(txtDescripcion.getText());
        promocion.setMaestro(maestro);
        promocion.setDescuento(Integer.parseInt(spinnerDescuento.getEditor().getText()));
        System.out.println(spinnerDescuento.toString());
        if (cmbTipoPromocion.getValue().equals("Mensualidad")) {
            promocion.setParaInscripcion(Short.parseShort("0"));
        } else {
            promocion.setParaInscripcion(Short.parseShort("1"));
        }
        PromocionPK promocionPK = new PromocionPK();
        promocionPK.setMaestroidMaestro(maestro.getMaestroPK().getIdMaestro());
        promocionPK.setMaestrousuarionombreUsuario(maestro.getUsuario().getNombreUsuario());
        if (promocion.crearPromocion()) {
            Mensajes.mensajeAlert("La promoción se creó correctamente");
            pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaPromociones.fxml", this.pnlPrincipal, this.pantallaDividida));
            pantallaDividida.getChildren().add(pnlPrincipal);
        } else {

        }
    }

}
