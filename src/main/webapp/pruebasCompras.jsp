<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="es.studium.DAO.ComprasDAO" %>
<%@ page import="es.studium.DAW_Practica4.Compras" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prueba ComprasDAO</title>
</head>
<body>
    <h1>Prueba DAO Compras</h1>

<%
    Integer idUsuario = (Integer) session.getAttribute("idUsuario");
    if (idUsuario == null) {
%>
    <p>No has iniciado sesión. <a href="login.jsp">Volver al login</a></p>
<%
    } else {
        ComprasDAO dao = new ComprasDAO();
        String mensaje = null;

        if (request.getMethod().equalsIgnoreCase("POST")) {
            try {
                int idTienda = Integer.parseInt(request.getParameter("idTienda"));
                double importe = Double.parseDouble(request.getParameter("importe"));
                LocalDate fecha = LocalDate.parse(request.getParameter("fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                dao.crearCompra(fecha, importe, idTienda, idUsuario);
                mensaje = "Compra insertada correctamente.";
            } catch (Exception e) {
                mensaje = "Error al insertar compra: " + e.getMessage();
            }
        }
        
        if (request.getParameter("borrar") != null) {
            try {
                int idCompra = Integer.parseInt(request.getParameter("idCompra"));
                dao.eliminarCompra(idCompra);
                mensaje = "Compra borrada correctamente.";
            } catch (Exception e) {
                mensaje = "Error al borrar compra: " + e.getMessage();
            }
        }

        if (mensaje != null) {
%>
    <p><strong><%= mensaje %></strong></p>
<%
        }
%>

    <form action="pruebasCompras.jsp" method="post">
        <label>Tienda ID:</label>
        <input type="number" name="idTienda" value="1"/><br/><br/>
        <label>Importe:</label>
        <input type="text" name="importe" value="15.50"/><br/><br/>
        <label>Fecha (YYYY-MM-DD):</label>
        <input type="text" name="fecha" value="<%= LocalDate.now() %>"/><br/><br/>
        <input type="submit" value="Insertar Compra"/>
    </form>

    <h2>Compras del usuario</h2>
<%
        // Mostrar las compras del mes actual
        LocalDate hoy = LocalDate.now();
        List<Compras> compras = dao.obtenerCompras(idUsuario, hoy.getMonthValue(), hoy.getYear());

        if (compras.isEmpty()) {
%>
    <p>No hay compras para este mes.</p>
<%
        } else {
%>
    <table border="1">
        <tr>
            <th>ID Compra</th>
            <th>Fecha</th>
            <th>Importe</th>
            <th>ID Tienda</th>
        </tr>
<%
            for (Compras c : compras) {
%>
        <tr>
            <td><%= c.getIdCompra() %></td>
            <td><%= c.getFechaCompra() %></td>
            <td><%= c.getImporte() %></td>
            <td><%= c.getTienda() %></td>
                <td>
                    <form action="pruebasCompras.jsp" method="post" style="display:inline;">
                        <input type="hidden" name="idCompra" value="<%= c.getIdCompra() %>"/>
                        <input type="submit" name="borrar" value="Borrar"/>
                    </form>
                </td>
        </tr>
<%
            }
%>
    </table>
<%
        }
    }
%>

</body>
</html>
