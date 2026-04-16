package co.edu.poli.examen2_zambrano.servicios;

import co.edu.poli.examen2_zambrano.modelo.Propietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOPropietario implements CRUD<Propietario> {

    @Override
    public String create(Propietario propietario) {
        return null;
    }

    @Override
    public <K> Propietario readone(K id) {
        return null;
    }

    @Override
    public List<Propietario> readall() throws Exception {
        Connection con = ConexionBD.getInstancia().getConexion();
        List<Propietario> lista = new ArrayList<>();

        String sqlSelectPropietario = "SELECT p.id AS propietario_id, p.nombre AS propietario_nombre FROM propietario p;";

        PreparedStatement ps = con.prepareStatement(sqlSelectPropietario);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Propietario propietario = new Propietario(rs.getString("propietario_id"), rs.getString("propietario_nombre"));
            lista.add(propietario);
        }
        return lista;
    }
}
