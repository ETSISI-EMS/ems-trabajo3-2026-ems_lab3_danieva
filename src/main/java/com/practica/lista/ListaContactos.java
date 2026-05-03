package com.practica.lista;

import com.practica.genericas.Coordenada;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;
import java.util.function.Function;

public class ListaContactos {
	private NodoTemporal lista;
	private int size;
	
	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de nodos de coordenadas. 
	 * En la lista de coordenadas metemos el documento de la persona que está en esa coordenada 
	 * en un instante 
	 */
	public void insertarNodoTemporal(PosicionPersona p) {
		NodoTemporal aux = lista, anterior = null;

		// Buscar posición en la lista temporal
		while (aux != null && !aux.esPosteriorA(p.getFechaPosicion())) {
			if (aux.tieneFecha(p.getFechaPosicion())) {
				aux.insertarCoordenada(p.getCoordenada());
				return; // encontrado: salimos directamente, sin flags
			}
			anterior = aux;
			aux = aux.getSiguiente();
		}

		// No encontrado: crear nuevo nodo temporal
		NodoTemporal nuevo = new NodoTemporal(p.getFechaPosicion());
		nuevo.insertarCoordenada(p.getCoordenada());
		enlazarNodoTemporal(nuevo, anterior, aux);
		this.size++;
	}

	private void enlazarNodoTemporal(NodoTemporal nuevo, NodoTemporal anterior, NodoTemporal siguiente) {
		nuevo.setSiguiente(siguiente);
		if (anterior != null)
			anterior.setSiguiente(nuevo);
		else
			lista = nuevo;
	}

	private boolean buscarPersona (String documento, NodoPersonas nodo) {
		NodoPersonas aux = nodo;
		while(aux!=null) {
			if(aux.getDocumento().equals(documento)) {
				return true;
			}else {
				aux = aux.getSiguiente();
			}
		}
		return false;
	}

	private void insertarPersona (String documento, NodoPersonas nodo) {
		NodoPersonas aux = nodo, nuevo = new NodoPersonas(documento, null);
		while(aux.getSiguiente()!=null) {
			aux = aux.getSiguiente();
		}
		aux.setSiguiente(nuevo);
	}

	public int personasEnCoordenadas () {
		NodoPosicion aux = this.lista.getListaCoordenadas();
		if(aux==null)
			return 0;
		else {
			int cont;
			for(cont=0;aux!=null;) {
				cont += aux.getNumPersonas();
				aux=aux.getSiguiente();
			}
			return cont;
		}
	}

	public int tamanioLista () {
		return this.size;
	}

	public String getPrimerNodo() {
		NodoTemporal aux = lista;
		String cadena = aux.getFecha().getFecha().toString();
		cadena+= ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de 
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	// Método privado común
	private int contarEntreDosInstantes(FechaHora inicio, FechaHora fin, Function<NodoPosicion, Integer> extractor) {
		if (this.size == 0)
			return 0;

		NodoTemporal aux = lista;
		int cont = 0;

		while (aux != null) {
			if (aux.getFecha().compareTo(inicio) >= 0 && aux.getFecha().compareTo(fin) <= 0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while (nodo != null) {
					cont = cont + extractor.apply(nodo); // <-- aquí está la diferencia
					nodo = nodo.getSiguiente();
				}
			}
			aux = aux.getSiguiente();
		}
		return cont;
	}

	// Los dos métodos públicos ahora delegan en el común
	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return contarEntreDosInstantes(inicio, fin, nodo -> nodo.getNumPersonas());
	}

	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return contarEntreDosInstantes(inicio, fin, nodo -> 1);
	}
	
	
	
	@Override
	public String toString() {
		String cadena="";
		int a,cont;
		cont=0;
		NodoTemporal aux = lista;
		for(cont=1; cont<size; cont++) {
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
			aux=aux.getSiguiente();
		}
		cadena += aux.getFecha().getFecha().toString();
		cadena += ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}
	
	
	
}
