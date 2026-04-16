package co.edu.poli.examen2_zambrano.tests.integracion;

import co.edu.poli.examen2_zambrano.modelo.Propietario;
import co.edu.poli.examen2_zambrano.servicios.DAOPropietario;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDAOPropietario {

    @Test
    void readAll_noDebeRetornarNull() throws Exception {
        DAOPropietario dao = new DAOPropietario();
        List<Propietario> lista = dao.readall();
        assertNotNull(lista);
    }

    @Test
    void readAll_listaInicializada() throws Exception {
        DAOPropietario dao = new DAOPropietario();
        List<Propietario> lista = dao.readall();
        assertFalse(lista.isEmpty(), "La lista no debe venir vacia con los datos base del script");
    }

    @Test
    void readAll_objetosValidos() throws Exception {
        DAOPropietario dao = new DAOPropietario();
        List<Propietario> lista = dao.readall();

        for (Propietario p : lista) {
            assertNotNull(p.getId());
            assertNotNull(p.getNombre());
            assertTrue(!p.getId().isBlank());
            assertTrue(!p.getNombre().isBlank());
        }
    }

    @Test
    void readAll_contieneDatosSemillaDelScript() throws Exception {
        DAOPropietario dao = new DAOPropietario();
        List<Propietario> lista = dao.readall();

        boolean existeJean = lista.stream().anyMatch(p ->
                "1".equals(p.getId()) && "Jean Zambrano".equals(p.getNombre()));
        boolean existeSofia = lista.stream().anyMatch(p ->
                "2".equals(p.getId()) && "Sofia Rivas".equals(p.getNombre()));

        assertTrue(existeJean, "Debe existir el propietario 1 (Jean Zambrano)");
        assertTrue(existeSofia, "Debe existir el propietario 2 (Sofia Rivas)");
    }
}
