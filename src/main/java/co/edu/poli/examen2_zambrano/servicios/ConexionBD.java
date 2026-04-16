package co.edu.poli.examen2_zambrano.servicios;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() throws Exception {
        limpiarBomDeEnvSiExiste();
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String pass = dotenv.get("DB_PASSWORD");

        if (url == null || user == null || pass == null) {
            throw new RuntimeException("Faltan variables en el .env");
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection(url, user, pass);
    }

    private static void limpiarBomDeEnvSiExiste() throws Exception {
        Path envPath = Path.of(".env");
        if (!Files.exists(envPath)) {
            return;
        }

        byte[] bytes = Files.readAllBytes(envPath);
        if (bytes.length >= 3
                && bytes[0] == (byte) 0xEF
                && bytes[1] == (byte) 0xBB
                && bytes[2] == (byte) 0xBF) {
            byte[] sinBom = Arrays.copyOfRange(bytes, 3, bytes.length);
            Files.write(envPath, sinBom);
        }
    }

    public static ConexionBD getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() throws Exception {
        if (conexion == null || conexion.isClosed()) {
            instancia = new ConexionBD();
            return instancia.conexion;
        }
        return conexion;
    }
}
