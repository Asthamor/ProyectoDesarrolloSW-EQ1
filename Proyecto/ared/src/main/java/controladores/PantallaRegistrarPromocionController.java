package controladores;

import clasesApoyo.JFXLimitedTextArea;
import clasesApoyo.JFXLimitedTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.jfoenix.validation.base.ValidatorBase;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import modelo.Maestro;
import modelo.Promocion;
import modelo.PromocionPK;
import org.controlsfx.control.Notifications;

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
    private JFXLimitedTextField txtNombre;
    @FXML
    private JFXLimitedTextArea txtDescripcion;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblTipoPromocion;
    @FXML
    private JFXComboBox<String> cmbTipoPromocion;
    @FXML
    private VBox vboxTipo;

    private Maestro maestro;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private ValidationFacade validationFacade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spinnerDescuento.setEditable(false);
        spinnerDescuento.setFocusTraversable(false);
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100,5   , 5);
        spinnerDescuento.setValueFactory(valueFactory);
        List<String> tipoPromociones = new ArrayList();
        tipoPromociones.add("Mensualidad");
        tipoPromociones.add("Inscripción");
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(tipoPromociones);
        cmbTipoPromocion.setItems(items);
        crearValidadorres();
    }

    public void crearValidadorres() {
        txtDescripcion.setRequired(true);
        txtDescripcion.setAlphanumericLimiter(100);
        txtNombre.setRequired(true);
        txtNombre.setAlphanumericLimiter(45);
        validationFacade = new ValidationFacade();
        validationFacade.setControl(cmbTipoPromocion);
        ValidatorBase req = new RequiredFieldValidator();
        req.setMessage("Campo requerido");
        validationFacade.getValidators().add(req);
        vboxTipo.getChildren().add(validationFacade);
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
        if (!existenCamposVacios()) {
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
                Notifications.create()
                        .title("¡Exito!")
                        .text("La promoción se creó correctamente")
                        .showInformation();
                pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaPromociones.fxml", this.pnlPrincipal, this.pantallaDividida));
                pantallaDividida.getChildren().add(pnlPrincipal);
            }
        }
    }

    public boolean existenCamposVacios() {
        txtDescripcion.setText(txtDescripcion.getText().trim());
        txtNombre.setText(txtNombre.getText().trim());
        return !txtDescripcion.validate() | !validationFacade.validate(cmbTipoPromocion)
                | !txtNombre.validate();
    }

}
