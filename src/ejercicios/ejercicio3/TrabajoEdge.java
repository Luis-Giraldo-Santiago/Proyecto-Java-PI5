package ejercicios.ejercicio3;



import us.lsi.graphs.virtual.SimpleEdgeAction;

public record TrabajoEdge(TrabajoVertex source, TrabajoVertex target, Integer action, Double weight) 
		implements SimpleEdgeAction<TrabajoVertex, Integer>{
	
	public static TrabajoEdge of(TrabajoVertex s, TrabajoVertex t, Integer a) {
		return new TrabajoEdge(s, t, a, 1.);
	}
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}
