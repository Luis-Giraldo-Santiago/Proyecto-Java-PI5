package ejercicios.ejercicio2;

import _datos.DatosCursos;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CursosEdge(CursosVertex source, CursosVertex target, Integer action, Double weight)
	implements SimpleEdgeAction<CursosVertex,Integer>{
	
	public static CursosEdge of(CursosVertex s, CursosVertex t, Integer a) {
		return new CursosEdge(s, t, a, a*DatosCursos.getPrecio(s.index()));
	}
	
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}
