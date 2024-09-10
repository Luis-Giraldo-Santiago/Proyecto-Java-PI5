package ejercicios.ejercicio4;

import _datos.DatosReparto;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record RepartoEdge(RepartoVertex source, RepartoVertex target, Integer action, Double weight) 
		implements SimpleEdgeAction<RepartoVertex, Integer>{
	
	public static RepartoEdge of(RepartoVertex s, RepartoVertex t, Integer a) {
		return new RepartoEdge(s, t, a, (DatosReparto.getBeneficio(s.index()%DatosReparto.getNumVertices())-s.recorrido()));
	}

}
