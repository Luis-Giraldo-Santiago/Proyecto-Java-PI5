package ejercicios.ejercicio1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import _datos.DatosCafe;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Integer> remaining) 
		implements VirtualVertex<CafeVertex, CafeEdge, Integer>{
		
	public static CafeVertex initial() {
		List<Integer> ls = List2.copy(DatosCafe.getPesos());
		return of(0, ls);
	}
	
	public static CafeVertex of(Integer i, List<Integer> rest) {
		return new CafeVertex(i,rest);
	}
	
	public static Predicate<CafeVertex> goal(){
		return v -> v.index() == DatosCafe.getNumVariedades();
	}
	
	public static Predicate<CafeVertex> goalHasSolution(){
		
		return v -> v.remaining().stream().allMatch(x -> x==0);
	}
	
	public List<Integer> actions(){
		List<Integer> alternativas = List2.empty();
		if(index < DatosCafe.getNumVariedades()) {
			if(remaining.stream().allMatch(x -> x == 0)) {
				alternativas = List2.of(0);
			}else {
				List<Integer> options = getOptions();
				if(options.size()==0) {
					alternativas = List2.of(0);
				} else if(index==DatosCafe.getNumVariedades()-1) {
					alternativas = options.size()==0? List2.empty() : options;
				}else {
					alternativas = options;
				}	
			}
		}
		return alternativas;
	}
	
	public List<Integer> getOptions() {
		Integer res = 0;
		List<Integer> ls = List2.copy(remaining);
		Map<Integer, Double> porcentajes = DatosCafe.variedades.get(index).porcentaje();
		for(Integer c: porcentajes.keySet()) {
			if(ls.get(c-1)>=0) {
				res += ls.get(c -1);
			}
		}
		List<Integer> opcionesPosibles = List2.rangeList(0, res + 1);
		List<Integer> lista = List2.copy(opcionesPosibles);
		for(Integer l : lista) {
			for(Integer c : porcentajes.keySet()) {
				if(l*porcentajes.get(c)!=(int)Math.round(l*porcentajes.get(c)) 
						||(ls.get(c-1) - (int)(porcentajes.get(c)*l)) < 0) {
					opcionesPosibles.remove(l);
				}
			}
		}
		return opcionesPosibles;
	}
	
	public List<Integer> actualizarRemaining(Integer a){
		Map<Integer, Double> porcentajes = DatosCafe.variedades.get(index).porcentaje();
		List<Integer> lista = List2.copy(remaining);
		for(Integer p: porcentajes.keySet()) {
			lista.set(p-1, lista.get(p-1) - (int)(porcentajes.get(p)*a));
		}
		return lista;
	}
	
	public CafeVertex neighbor(Integer a) {
		return of(index + 1, actualizarRemaining(a));
	}
	
	public CafeEdge edge(Integer a) {
		return CafeEdge.of(this, neighbor(a) , a);
	}
	
	public CafeEdge greedyEdge() {
		return edge(actions().stream().max(Comparator.naturalOrder()).orElse(0));
	}
	
	public String toString() {
		return String.format("%d; %s", index, remaining);
	}

}
