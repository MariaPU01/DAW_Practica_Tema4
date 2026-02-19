package es.studium.PoolConexiones;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Pool de conexiones a la base de datos
	private DataSource pool;

	public LoginServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		try {
// Crea un contexto para poder luego buscar el recurso DataSource
			InitialContext ctx = new InitialContext();
// Busca el recurso DataSource en el contexto
			pool = (DataSource) ctx.lookup("java:comp/env/jdbc/control_gastos");
			if (pool == null) {
				throw new ServletException("DataSource desconocida ‘control_gastos");
			}
		} catch (NamingException ex) {
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");

	    String usuario = request.getParameter("usuario").trim();
	    String password = request.getParameter("clave").trim();

	    try (PrintWriter out = response.getWriter();
	         Connection conn = pool.getConnection()) {

	        out.println("<!DOCTYPE html>");
	        out.println("<html lang='es'>");
	        out.println("<head><title>Login</title></head>");
	        out.println("<body>");
	        out.println("<h2>Login</h2>");

	        // Validaciones básicas
	        if (usuario.isEmpty()) {
	            out.println("<p>Debes introducir tu usuario</p>");
	        } else if (password.isEmpty()) {
	            out.println("<p>Debes introducir tu contraseña</p>");
	        } else {
	            // Usando PreparedStatement para mayor seguridad
	            String sql = "SELECT * FROM usuario WHERE username = ? AND pass = ?";
	            try (PreparedStatement ps = conn.prepareStatement(sql)) {
	                ps.setString(1, usuario);
	                ps.setString(2, password);

	                try (ResultSet rs = ps.executeQuery()) {
	                    if (rs.next()) {
	                        // Login correcto
	                        HttpSession session = request.getSession(false);
	                        if (session != null) session.invalidate();
	                        session = request.getSession(true);
	                        int idUsuario = rs.getInt("id_usuario");
	                        session.setAttribute("idUsuario", idUsuario);

	                        out.println("<p>Hola, " + usuario + "!</p>");
	                        out.println("<p><a href='pruebasCompras.jsp'>Compras</a></p>");
	                    } else {
	                        // Login incorrecto
	                        out.println("<p>Nombre de usuario o contraseña incorrectos</p>");
	                        out.println("<p><a href='index.jsp'>Volver a Login</a></p>");
	                    }
	                }
	            } catch (SQLException e) {
	                out.println("<p>Error en la base de datos: " + e.getMessage() + "</p>");
	            }
	        }

	        out.println("</body></html>");

	    } catch (SQLException e) {
	        throw new ServletException("No se pudo obtener la conexión", e);
	    }
	}

}