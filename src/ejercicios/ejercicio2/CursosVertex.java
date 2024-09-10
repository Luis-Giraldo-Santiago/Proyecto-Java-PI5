package ejercicios.ejercicio2;


import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursosVertex(Integer index, Set<Integer> remaining, Set<Integer> numCentro) 
	implements VirtualVertex<CursosVertex, CursosEdge, Integer>{
	
	public static CursosVertex initial() {
		return of(0, Set2.copy(DatosCursos.tematicas), Set2.empty());
	}
	
	public static CursosVertex of(Integer index, Set<Integer> remaining, Set<Integer> centro) {
		return new CursosVertex(index, remaining, centro);
	}
	
	public static Predicate<CursosVertex> goal(){
		return v -> v.index() == DatosCursos.getNumCursos();
	}
	
	public static Predicate<CursosVertex> goalHasSolution(){
		return v -> v.remaining().isEmpty();
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
	
	public CursosVertex neighbor(Integer a) {
		Set<Integer> rest = a==0? Set2.copy(remaining) : Set2.difference(remaining, DatosCursos.getElementos(index));
		Set<Integer> centros = Set2.copy(numCentro);
		centros.add(DatosCursos.cursos.get(index).centro());
		Set<Integer> centro = a==0? Set2.copy(numCentro) : centros; 
		return of(index+1, rest, centro);
	}
	
	public CursosEdge edge(Integer a) {
		return CursosEdge.of(this, neighbor(a), a);
	}
	
	public CursosEdge greedyEdge() {
		Set<Integer> rest = Set2.difference(remaining, DatosCursos.getElementos(index));
		Set<Integer> centro = Set2.copy(numCentro);
		centro.add(DatosCursos.cursos.get(index).centro());
		return rest.equals(remaining)|| centro.size()>DatosCursos.maxCentros? edge(0): edge(1);
	}
	
	public String toString() {
		return String.format("%d; %d; %d", index, remaining.size(), numCentro.size());
	}

}
