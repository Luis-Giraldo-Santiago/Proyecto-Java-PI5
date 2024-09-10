package ejercicios.ejercicio4;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosReparto;

public class RepartoHeuristic {
	
	public static Double heuristic(RepartoVertex v1, Predicate<RepartoVertex> goal, RepartoVertex v2) {
		return v1.remaining().isEmpty() ? 0. : 
			IntStream.range(v1.index(), DatosReparto.getNumVertices() + 1)
			.mapToDouble(x -> DatosReparto.getBeneficio(x%DatosReparto.getNumVertices())).max().orElse(-100.0);
	}

}
