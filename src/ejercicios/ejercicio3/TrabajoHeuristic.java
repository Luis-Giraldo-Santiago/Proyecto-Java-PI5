package ejercicios.ejercicio3;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosTrabajo;

public class TrabajoHeuristic {
	
	public static Double heuristic(TrabajoVertex v1, Predicate<TrabajoVertex> goal, TrabajoVertex v2) {
		return v1.remaining().stream().allMatch(x -> x==0) ? 0. : 
			IntStream.range(v1.index(), DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumTrabajos())
			.mapToDouble(x -> DatosTrabajo.getCalidad(x/DatosTrabajo.getNumInvestigadores())).max().orElse(-100.0);
	}
	

}
