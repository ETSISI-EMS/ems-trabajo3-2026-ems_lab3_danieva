package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.Coordenada;



/**
 * Nodo para guardar un instante de tiempo. Además guardamos una lista con las coordeandas
 * y las personas (solo número) que en ese instante están en una coordeanda en concreto  
 *
 */
public class NodoTemporal {
	private NodoPosicion listaCoordenadas;
	private FechaHora fecha;
	private NodoTemporal siguiente;


	// En NodoTemporal.java - añade este constructor
	public NodoTemporal(FechaHora fecha) {
		super();
		this.fecha = fecha;
		this.siguiente = null;
		this.listaCoordenadas = null;
	}
	public NodoPosicion getListaCoordenadas() {
		return listaCoordenadas;
	}
	public void setListaCoordenadas(NodoPosicion listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}
	public FechaHora getFecha() {
		return fecha;
	}
	public void setFecha(FechaHora fecha) {
		this.fecha = fecha;
	}
	public NodoTemporal getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(NodoTemporal siguiente) {
		this.siguiente = siguiente;
	}
	// En NodoTemporal
	public boolean tieneFecha(FechaHora fecha) {
		return this.getFecha().compareTo(fecha) == 0;
	}

	public boolean esPosteriorA(FechaHora fecha) {
		return this.getFecha().compareTo(fecha) > 0;
	}

	// En NodoTemporal.java - añade este método
	public void insertarCoordenada(Coordenada coordenada) {
		NodoPosicion actual = this.listaCoordenadas; // ✅ es la lista del NodoTemporal
		NodoPosicion anterior = null;

		while (actual != null) {
			if (actual.getCoordenada().equals(coordenada)) {
				actual.setNumPersonas(actual.getNumPersonas() + 1);
				return;
			}
			anterior = actual;
			actual = actual.getSiguiente();
		}

		NodoPosicion nuevo = new NodoPosicion(coordenada, 1, null);
		if (anterior == null)
			this.listaCoordenadas = nuevo;  // ✅ setListaCoordenadas existe en NodoTemporal
		else
			anterior.setSiguiente(nuevo);
	}
}
