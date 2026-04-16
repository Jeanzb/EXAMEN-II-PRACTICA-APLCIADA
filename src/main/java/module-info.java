module co.edu.poli.examen2_zambrano {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires io.github.cdimascio.dotenv.java;

    opens co.edu.poli.examen2_zambrano.vista to javafx.fxml, javafx.graphics;
    opens co.edu.poli.examen2_zambrano.controlador to javafx.fxml;
    exports co.edu.poli.examen2_zambrano.controlador;
}
