package ejercicios.ejercicio1;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCafe;

public class CafeHeuristic {
	
	public static Double heuristic(CafeVertex v1, Predicate<CafeVertex> goal, CafeVertex v2) {
		return v1.remaining().stream().allMatch(x -> x<=0) ? 0. : 
			IntStream.range(v1.index(), DatosCafe.getNumVariedades())
			.mapToDouble(x -> DatosCafe.getBeneficio(x)).max().orElse(-100.0);
	}
}
