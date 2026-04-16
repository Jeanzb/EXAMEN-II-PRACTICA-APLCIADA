package co.edu.poli.examen2_zambrano.servicios;

import co.edu.poli.examen2_zambrano.modelo.Apartamento;
import co.edu.poli.examen2_zambrano.modelo.Casa;
import co.edu.poli.examen2_zambrano.modelo.Inmueble;
import co.edu.poli.examen2_zambrano.modelo.Propietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DAOInmueble implements CRUD<Inmueble> {

    @Override
    public String create(Inmueble inmueble) throws Exception {
        Connection con = ConexionBD.getInstancia().getConexion();

        con.setAutoCommit(false);

        try {
            String sqlInsertInmueble = "INSERT INTO inmueble (numero, fecha_compra, estado, propietario_id) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sqlInsertInmueble);
            ps.setString(1, inmueble.getNumero());
            ps.setString(2, inmueble.getFechaCompra());
            ps.setBoolean(3, inmueble.isEstado());
            ps.setString(4, inmueble.getPropietario().getId());
            ps.executeUpdate();

            String sqlInsertApartamento = "INSERT INTO apartamento (numero, numero_piso) VALUES (?, ?)";
            String sqlInsertCasa = "INSERT INTO casa (numero, cantidad_pisos) VALUES (?, ?)";

            String sql = (inmueble instanceof Apartamento) ? sqlInsertApartamento : sqlInsertCasa;
            ps = con.prepareStatement(sql);
            ps.setString(1, inmueble.getNumero());
            if (inmueble instanceof Apartamento) {
                ps.setInt(2, ((Apartamento) inmueble).getNumeroPiso());
            } else {
                ps.setInt(2, ((Casa) inmueble).getCantidadPisos());
            }
            ps.executeUpdate();
            con.commit();
            return "OK " + inmueble.getClass().getSimpleName() + " [" + inmueble.getNumero() + "] guardado correctamente.";
        } catch (Exception e) {
            con.rollback();
            return e.getMessage();
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public <K> Inmueble readone(K numero) throws Exception {
        Connection con = ConexionBD.getInstancia().getConexion();

        String sqlSelectApartamento = "SELECT i.numero, i.fecha_compra, i.estado, p.id AS propietario_id, p.nombre AS propietario_nombre, a.numero_piso "
                + "FROM apartamento a "
                + "INNER JOIN inmueble i ON a.numero = i.numero "
                + "INNER JOIN propietario p ON i.propietario_id = p.id "
                + "WHERE a.numero = ?";

        PreparedStatement ps = con.prepareStatement(sqlSelectApartamento);
        ps.setString(1, (String) numero);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Apartamento(
                    rs.getString("numero"),
                    rs.getString("fecha_compra"),
                    rs.getBoolean("estado"),
                    new Propietario(rs.getString("propietario_id"), rs.getString("propietario_nombre")),
                    rs.getInt("numero_piso")
            );
        }

        String sqlSelectCasa = "SELECT i.numero, i.fecha_compra, i.estado, p.id AS propietario_id, p.nombre AS propietario_nombre, c.cantidad_pisos "
                + "FROM casa c "
                + "INNER JOIN inmueble i ON c.numero = i.numero "
                + "INNER JOIN propietario p ON i.propietario_id = p.id "
                + "WHERE c.numero = ?";

        ps = con.prepareStatement(sqlSelectCasa);
        ps.setString(1, (String) numero);
        rs = ps.executeQuery();
        if (rs.next()) {
            return new Casa(
                    rs.getString("numero"),
                    rs.getString("fecha_compra"),
                    rs.getBoolean("estado"),
                    new Propietario(rs.getString("propietario_id"), rs.getString("propietario_nombre")),
                    rs.getInt("cantidad_pisos")
            );
        }

        return null;
    }

    @Override
    public List<Inmueble> readall() {
        return null;
    }
}
