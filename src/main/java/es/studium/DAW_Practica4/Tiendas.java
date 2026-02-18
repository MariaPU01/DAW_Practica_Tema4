package es.studium.DAW_Practica4;

public class Tiendas {
	//Atributos
	private int idTienda;
	private String nombre;

	//Constructor
	public Tiendas(int idTienda, String nombre) {
		this.idTienda = idTienda;
		this.nombre = nombre;
	}

	//Getters y Setters
	public int getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(int idTienda) {
		this.idTienda = idTienda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//Método toString
	public String toString() {
		return "Tienda [idTienda=" + idTienda + ", nombre=" + nombre + "]";
	}
}
