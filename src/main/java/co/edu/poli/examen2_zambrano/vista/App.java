package co.edu.poli.examen2_zambrano.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        TabPane root = FXMLLoader.load(getClass().getResource("/co/edu/poli/examen2_zambrano/formInmueble.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inmuebles");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
