package es.studium.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.studium.DAW_Practica4.Compras;

public class ComprasDAO {

    // Conexión temporal
    private String url = "jdbc:mysql://localhost:3306/control_gastos";
    private String user = "root";
    private String password = "12345";

    public void pruebaConexion() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión correcta");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Metodos CRUD de compras
    // Obtener compras por usuario
    public List<Compras> obtenerCompras(int idUsuario, int mes, int anio) {
    	List<Compras> compraMensual = new ArrayList<>();
		String sql = "SELECT * FROM COMPRAS WHERE idUsuario = ? AND MONTH(fechaCompra) = ? AND YEAR(fechaCompra) = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
			 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, idUsuario);
			ps.setInt(2, mes);
			ps.setInt(3, anio);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int idCompra = rs.getInt("idCompra");
					LocalDate fechaCompra = rs.getDate("fechaCompra").toLocalDate();
					double importe = rs.getDouble("importe");
					int idTienda = rs.getInt("idTienda");
					
					Compras compra = new Compras(idCompra, null, null, fechaCompra, importe);
					compraMensual.add(compra);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compraMensual;
		}
    
	// Crear compra
    public void crearCompra(LocalDate fechaCompra, double importe, int idTienda, int idUsuario) {

        String sql = "INSERT INTO COMPRAS (fechaCompra, importe, idTienda, idUsuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fechaCompra));
            ps.setDouble(2, importe);
            ps.setInt(3, idTienda);
            ps.setInt(4, idUsuario);

            ps.executeUpdate();

            // Obtener ID generado si quieres usarlo
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("Compra creada con ID: " + idGenerado);
                }
            }

            System.out.println("Compra creada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Eliminar compra
    public void eliminarCompra(int idCompra) {
		String sql = "DELETE FROM COMPRAS WHERE idCompra = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
			 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, idCompra);
			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Compra eliminada correctamente");
			} else {
				System.out.println("No se encontró la compra con ID: " + idCompra);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    //Editar compra
    public void editarCompra(int idCompra, LocalDate fechaCompra, double importe, int idTienda) {
		String sql = "UPDATE COMPRAS SET fechaCompra = ?, importe = ?, idTienda = ? WHERE idCompra = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
			 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setDate(1, java.sql.Date.valueOf(fechaCompra));
			ps.setDouble(2, importe);
			ps.setInt(3, idTienda);
			ps.setInt(5, idCompra);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Compra editada correctamente");
			} else {
				System.out.println("No se encontró la compra con ID: " + idCompra);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
}
