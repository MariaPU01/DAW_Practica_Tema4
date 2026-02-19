package es.studium.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import es.studium.DAW_Practica4.Compras;

public class ComprasDAO {

    private DataSource pool;

    public ComprasDAO() {
        try {
            InitialContext ctx = new InitialContext();
            pool = (DataSource) ctx.lookup("java:comp/env/jdbc/control_gastos");
            if (pool == null) {
                throw new RuntimeException("DataSource 'control_gastos' no encontrado");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    // Obtener compras por usuario, mes y año
    public List<Compras> obtenerCompras(int idUsuario, int mes, int anio) {
        List<Compras> compraMensual = new ArrayList<>();
        String sql = "SELECT * FROM COMPRA WHERE idUsuario = ? AND MONTH(fecha) = ? AND YEAR(fecha) = ?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, mes);
            ps.setInt(3, anio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idCompra = rs.getInt("id_compra");
                    LocalDate fechaCompra = rs.getDate("fecha").toLocalDate();
                    double importe = rs.getDouble("importe");
                    int idTienda = rs.getInt("idTienda");
                    int idUsuarioCompra = rs.getInt("idUsuario");

                    Compras compra = new Compras(idCompra, idTienda, idUsuarioCompra, fechaCompra, importe);
                    compraMensual.add(compra);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compraMensual;
    }

    // Crear compra
    public void crearCompra(LocalDate fechaCompra, double importe, int idTienda, int idUsuario) {
        String sql = "INSERT INTO COMPRA (fecha, importe, idTienda, idUsuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, java.sql.Date.valueOf(fechaCompra));
            ps.setDouble(2, importe);
            ps.setInt(3, idTienda);
            ps.setInt(4, idUsuario);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("Compra creada con ID: " + idGenerado);
                }
            }

            System.out.println("Compra creada correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar compra
    public void eliminarCompra(int idCompra) {
        String sql = "DELETE FROM COMPRA WHERE id_compra = ?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Compra eliminada correctamente");
            } else {
                System.out.println("No se encontró la compra con ID: " + idCompra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Editar compra
    public void editarCompra(int idCompra, LocalDate fechaCompra, double importe, int idTienda) {
        String sql = "UPDATE COMPRA SET fecha = ?, importe = ?, idTienda = ? WHERE id_compra = ?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fechaCompra));
            ps.setDouble(2, importe);
            ps.setInt(3, idTienda);
            ps.setInt(4, idCompra); // CORREGIDO

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Compra editada correctamente");
            } else {
                System.out.println("No se encontró la compra con ID: " + idCompra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
