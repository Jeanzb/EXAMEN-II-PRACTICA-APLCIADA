package co.edu.poli.examen2_zambrano.tests.unitaria;

import co.edu.poli.examen2_zambrano.modelo.Apartamento;
import co.edu.poli.examen2_zambrano.modelo.Casa;
import co.edu.poli.examen2_zambrano.modelo.Inmueble;
import co.edu.poli.examen2_zambrano.modelo.Propietario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInmueble {

    @Test
    void apartamento_semilla_constructor_y_getters() {
        Propietario propietario = new Propietario("1", "Jean Zambrano");
        Apartamento apartamento = new Apartamento("111", "01/01/2024", true, propietario, 4);

        assertEquals("111", apartamento.getNumero());
        assertEquals("01/01/2024", apartamento.getFechaCompra());
        assertTrue(apartamento.isEstado());
        assertEquals("1", apartamento.getPropietario().getId());
        assertEquals("Jean Zambrano", apartamento.getPropietario().getNombre());
        assertEquals(4, apartamento.getNumeroPiso());
    }

    @Test
    void casa_semilla_constructor_y_getters() {
        Propietario propietario = new Propietario("2", "Sofia Rivas");
        Casa casa = new Casa("112", "15/02/2023", true, propietario, 2);

        assertEquals("112", casa.getNumero());
        assertEquals("15/02/2023", casa.getFechaCompra());
        assertTrue(casa.isEstado());
        assertEquals("2", casa.getPropietario().getId());
        assertEquals("Sofia Rivas", casa.getPropietario().getNombre());
        assertEquals(2, casa.getCantidadPisos());
    }

    @Test
    void inmueble_setters_modifican_datos_base() {
        Inmueble inmueble = new Apartamento("111", "01/01/2024", true, new Propietario("1", "Jean Zambrano"), 4);

        inmueble.setNumero("999");
        inmueble.setFechaCompra("20/12/2025");
        inmueble.setEstado(false);
        inmueble.setPropietario(new Propietario("2", "Sofia Rivas"));

        assertEquals("999", inmueble.getNumero());
        assertEquals("20/12/2025", inmueble.getFechaCompra());
        assertFalse(inmueble.isEstado());
        assertEquals("2", inmueble.getPropietario().getId());
        assertEquals("Sofia Rivas", inmueble.getPropietario().getNombre());
    }

    @Test
    void toString_incluye_campos_principales() {
        Apartamento apartamento = new Apartamento("111", "01/01/2024", true, new Propietario("1", "Jean Zambrano"), 4);

        String texto = apartamento.toString();

        assertTrue(texto.contains("111"));
        assertTrue(texto.contains("01/01/2024"));
        assertTrue(texto.contains("Jean Zambrano"));
        assertTrue(texto.contains("numeroPiso=4"));
    }
}
