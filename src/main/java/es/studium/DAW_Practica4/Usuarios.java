package es.studium.DAW_Practica4;

public class Usuarios {
	//Atributos
	private int idUsuario;
	private String username;
	private String password;
	
	//Constructor
	public Usuarios(int idUsuario, String username, String password) {
		this.idUsuario = idUsuario;
		this.username = username;
		this.password = password;
	}

	//Getters y Setters
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//Método toString
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", username=" + username + "]";
	}
}
