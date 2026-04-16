package co.edu.poli.examen2_zambrano.controlador;

import co.edu.poli.examen2_zambrano.modelo.Apartamento;
import co.edu.poli.examen2_zambrano.modelo.Casa;
import co.edu.poli.examen2_zambrano.modelo.Inmueble;
import co.edu.poli.examen2_zambrano.modelo.Propietario;
import co.edu.poli.examen2_zambrano.servicios.DAOInmueble;
import co.edu.poli.examen2_zambrano.servicios.DAOPropietario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControlFormInmueble {

    @FXML
    private Button bttConsulta;
    @FXML
    private TextField txtNumeroConsulta;
    @FXML
    private TextArea txtAreaResultado;
    @FXML
    private Button bttCreacion;
    @FXML
    private TextField txtNumeroCrear;
    @FXML
    private DatePicker datepkCompra;
    @FXML
    private ComboBox<Propietario> cmbPropietario;
    @FXML
    private RadioButton radioApartamento;
    @FXML
    private RadioButton radioCasa;
    @FXML
    private ToggleGroup tipo;
    @FXML
    private TextField txtDatoTipo;
    @FXML
    private Label lblDatoTipo;

    private DAOInmueble daoInmueble;
    private DAOPropietario daoPropietario;

    @FXML
    private void initialize() {
        daoInmueble = new DAOInmueble();
        daoPropietario = new DAOPropietario();

        datepkCompra.setValue(LocalDate.now());

        try {
            List<Propietario> lista = daoPropietario.readall();
            cmbPropietario.getItems().setAll(lista);
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }

        actualizarLabelTipo();
        tipo.selectedToggleProperty().addListener((obs, oldVal, newVal) -> actualizarLabelTipo());

        txtNumeroConsulta.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validarSoloNumeros(txtNumeroConsulta);
            }
        });

        txtNumeroCrear.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validarSoloNumeros(txtNumeroCrear);
            }
        });

        txtDatoTipo.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validarSoloNumeros(txtDatoTipo);
            }
        });
    }

    @FXML
    private void pressConsulta(ActionEvent event) {
        txtAreaResultado.setText("");
        String numero = txtNumeroConsulta.getText().trim();

        if (numero.isEmpty()) {
            mostrarAlerta("Ingrese numero del inmueble");
            return;
        }

        try {
            Inmueble inmueble = daoInmueble.readone(numero);
            if (inmueble != null) {
                txtAreaResultado.setText(inmueble.toString());
            } else {
                mostrarAlerta("No existe el numero de inmueble");
            }
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
    }

    @FXML
    private void pressCreacion(ActionEvent event) {
        String numero = txtNumeroCrear.getText().trim();
        if (numero.isEmpty()) {
            mostrarAlerta("Ingrese numero de inmueble");
            return;
        }

        if (datepkCompra.getValue() == null) {
            mostrarAlerta("Seleccione la fecha de compra");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaCompra = datepkCompra.getValue().format(formatter);

        Propietario propietario = cmbPropietario.getValue();
        if (propietario == null) {
            mostrarAlerta("Seleccione un propietario");
            return;
        }

        String valorTipo = txtDatoTipo.getText().trim();
        if (valorTipo.isEmpty() || !valorTipo.matches("\\d+")) {
            mostrarAlerta("Ingrese un valor numerico para " + lblDatoTipo.getText().toLowerCase());
            return;
        }

        int datoEntero = Integer.parseInt(valorTipo);
        Inmueble nuevo;
        if (radioApartamento.isSelected()) {
            nuevo = new Apartamento(numero, fechaCompra, true, propietario, datoEntero);
        } else {
            nuevo = new Casa(numero, fechaCompra, true, propietario, datoEntero);
        }

        try {
            String resultado = daoInmueble.create(nuevo);
            mostrarAlerta(resultado);
            if (resultado.startsWith("OK")) {
                limpiarFormCrear();
            }
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void actualizarLabelTipo() {
        if (radioApartamento != null && radioApartamento.isSelected()) {
            lblDatoTipo.setText("Numero de piso");
            txtDatoTipo.setPromptText("Numero de piso");
        } else {
            lblDatoTipo.setText("Cantidad de pisos");
            txtDatoTipo.setPromptText("Cantidad de pisos");
        }
    }

    private void limpiarFormCrear() {
        txtNumeroCrear.clear();
        txtDatoTipo.clear();
        datepkCompra.setValue(LocalDate.now());
        cmbPropietario.setValue(null);
        radioApartamento.setSelected(true);
        actualizarLabelTipo();
    }

    private void validarSoloNumeros(TextField txt) {
        String texto = txt.getText().trim();
        if (!texto.isBlank()) {
            if (!texto.matches("\\d+")) {
                txtAreaResultado.setText("");
                txt.setStyle("-fx-border-color: red;");
                mostrarAlerta("Solo numeros son permitidos");
                txt.setText("");
                Platform.runLater(txt::requestFocus);
            } else {
                txt.setStyle("");
            }
        } else {
            txt.setStyle("");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
