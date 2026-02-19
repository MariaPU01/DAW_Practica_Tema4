<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
	</head>
	<body>
		<h1>Login</h1>
		<form action="login" method="post">
			<input type="text" name="usuario"/>
			<input type="password" name="clave"/>
			<br/>
			<input type="submit" name="Acceder"/>
			<input type="reset" name="Cancelar"/>
		</form>
	</body>
</html>