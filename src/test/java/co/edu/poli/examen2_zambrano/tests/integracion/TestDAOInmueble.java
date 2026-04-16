package co.edu.poli.examen2_zambrano.tests.integracion;

import co.edu.poli.examen2_zambrano.modelo.Apartamento;
import co.edu.poli.examen2_zambrano.modelo.Casa;
import co.edu.poli.examen2_zambrano.modelo.Inmueble;
import co.edu.poli.examen2_zambrano.modelo.Propietario;
import co.edu.poli.examen2_zambrano.servicios.ConexionBD;
import co.edu.poli.examen2_zambrano.servicios.DAOInmueble;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDAOInmueble {

    private final DAOInmueble dao = new DAOInmueble();
    private final List<String> numerosCreados = new ArrayList<>();

    @AfterEach
    void limpiarDatosPrueba() throws Exception {
        if (numerosCreados.isEmpty()) {
            return;
        }

        Connection con = ConexionBD.getInstancia().getConexion();
        for (String numero : numerosCreados) {
            try (PreparedStatement psApartamento = con.prepareStatement("DELETE FROM apartamento WHERE numero = ?");
                 PreparedStatement psCasa = con.prepareStatement("DELETE FROM casa WHERE numero = ?");
                 PreparedStatement psInmueble = con.prepareStatement("DELETE FROM inmueble WHERE numero = ?")) {
                psApartamento.setString(1, numero);
                psCasa.setString(1, numero);
                psInmueble.setString(1, numero);

                psApartamento.executeUpdate();
                psCasa.executeUpdate();
                psInmueble.executeUpdate();
            }
        }
        numerosCreados.clear();
    }

    private String numeroUnico(String prefijo) {
        return prefijo + System.currentTimeMillis();
    }

    @Test
    void create_apartamento_y_readone() throws Exception {
        String numero = numeroUnico("A");
        Propietario propietario = new Propietario("1", "Jean Zambrano");

        Apartamento apartamento = new Apartamento(numero, "15/04/2026", true, propietario, 5);

        String result = dao.create(apartamento);
        assertTrue(result.startsWith("OK"), result);
        numerosCreados.add(numero);

        Inmueble inmueble = dao.readone(numero);

        assertNotNull(inmueble);
        assertTrue(inmueble instanceof Apartamento);

        Apartamento a = (Apartamento) inmueble;
        assertEquals(5, a.getNumeroPiso());
        assertEquals(numero, a.getNumero());
    }

    @Test
    void create_casa_y_readone() throws Exception {
        String numero = numeroUnico("C");
        Propietario propietario = new Propietario("1", "Jean Zambrano");

        Casa casa = new Casa(numero, "15/04/2026", true, propietario, 2);

        String result = dao.create(casa);
        assertTrue(result.startsWith("OK"), result);
        numerosCreados.add(numero);

        Inmueble inmueble = dao.readone(numero);

        assertNotNull(inmueble);
        assertTrue(inmueble instanceof Casa);

        Casa c = (Casa) inmueble;
        assertEquals(2, c.getCantidadPisos());
        assertEquals(numero, c.getNumero());
    }

    @Test
    void readone_noExiste() throws Exception {
        Inmueble inmueble = dao.readone("NOEXISTE-001");
        assertNull(inmueble);
    }

    @Test
    void readone_apartamentoSemilla() throws Exception {
        Inmueble inmueble = dao.readone("111");

        assertNotNull(inmueble);
        assertTrue(inmueble instanceof Apartamento);

        Apartamento apartamento = (Apartamento) inmueble;
        assertEquals("111", apartamento.getNumero());
        assertEquals(4, apartamento.getNumeroPiso());
        assertEquals("1", apartamento.getPropietario().getId());
        assertEquals("Jean Zambrano", apartamento.getPropietario().getNombre());
    }

    @Test
    void readone_casaSemilla() throws Exception {
        Inmueble inmueble = dao.readone("112");

        assertNotNull(inmueble);
        assertTrue(inmueble instanceof Casa);

        Casa casa = (Casa) inmueble;
        assertEquals("112", casa.getNumero());
        assertEquals(2, casa.getCantidadPisos());
        assertEquals("2", casa.getPropietario().getId());
        assertEquals("Sofia Rivas", casa.getPropietario().getNombre());
    }
}
