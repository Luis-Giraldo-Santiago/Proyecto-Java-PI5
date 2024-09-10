package ejercicios.ejercicio2.manual;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record CursoProblem(Integer index, Set<Integer> remaining, Set<Integer> numCentro) {
	
	public static CursoProblem initial() {
		return of(0, Set2.copy(DatosCursos.tematicas), Set2.empty());
	}
	
	public static CursoProblem of(Integer index, Set<Integer> remaining, Set<Integer> centro) {
		return new CursoProblem(index, remaining, centro);
	}
	
	public List<Integer> actions(){
		List<Integer> alternativas = List2.empty();
		if(index<DatosCursos.getNumCursos()) {
			if(remaining.isEmpty()) {
				alternativas = List2.of(0);
			}else {
				Set<Integer> rest = Set2.difference(remaining, DatosCursos.getElementos(index));
				Set<Integer> centro = Set2.copy(numCentro);
				centro.add(DatosCursos.cursos.get(index).centro());
				if(rest.equals(remaining) || centro.size()>DatosCursos.maxCentros) {
					alternativas = List2.of(0);
				} else if(index==DatosCursos.getNumCursos() - 1) {
					alternativas = rest.isEmpty()? List2.of(1) : List2.empty();
				} else {
					alternativas = List2.of(0,1);
				}
			}
		}
		return alternativas;
	}
	
	public CursoProblem neighbor(Integer a) {
		Set<Integer> rest = a==0? Set2.copy(remaining) : Set2.difference(remaining, DatosCursos.getElementos(index));
		Set<Integer> centros = Set2.copy(numCentro);
		centros.add(DatosCursos.cursos.get(index).centro());
		Set<Integer> centro = a==0? Set2.copy(numCentro) : centros; 
		return of(index+1, rest, centro);
	}
	
	public Double heuristic() {
		return remaining.isEmpty()? 0.:
			IntStream.range(index, DatosCursos.getNumCursos())
			.filter(i -> !List2.intersection(remaining, 
					DatosCursos.getElementos(i)).isEmpty())
			.mapToDouble(i -> DatosCursos.getPrecio(i)).min().orElse(100.);
	}

}
