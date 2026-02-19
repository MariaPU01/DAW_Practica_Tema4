package es.studium.DAW_Practica4;

import java.time.LocalDate;

public class Compras {

	//Atributos
	private int idCompra;
	private int tienda;
	private int usuario;
	private LocalDate fechaCompra;
	private double importe;
	
	//Constructor
	public Compras(int idCompra, int tienda, int usuario, LocalDate fechaCompra, double importe) {
		this.idCompra = idCompra;
		this.tienda = tienda;
		this.usuario = usuario;
		this.fechaCompra = fechaCompra;
		this.importe = importe;
	}
	
	//Getters y Setters
	public int getIdCompra() {
		return idCompra;
	}
	
	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
	
	public int getTienda() {
		return tienda;
	}
	
	public void setTiendas(int tienda) {
		this.tienda = tienda;
	}
	
	public int getUsuario() {
		return usuario;
	}
	
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
	public LocalDate getFechaCompra() {
		return fechaCompra;
	}
	
	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	
	public double getImporte() {
		return importe;
	}
	
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	//Método toString
	public String toString() {
		return "Compra [idCompra=" + idCompra + ", tiendas=" + tienda + ", usuario=" + usuario + ", fechaCompra="
				+ fechaCompra + ", importe=" + importe + "]";
	}
	
	
	
}
