package ejercicios.ejercicio2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCursos;
import us.lsi.common.List2;

public class CursosHeuristic {
	
	public static Double heuristic(CursosVertex v1, Predicate<CursosVertex> goal, CursosVertex v2) {
		return v1.remaining().isEmpty()? 0.:
			IntStream.range(v1.index(), DatosCursos.getNumCursos())
			.filter(i -> !List2.intersection(v1.remaining(), 
					DatosCursos.getElementos(i)).isEmpty())
			.filter(c -> v1.numCentro().size() <= DatosCursos.maxCentros)
			.mapToDouble(i -> DatosCursos.getPrecio(i)).min().orElse(100.);
	}

}
