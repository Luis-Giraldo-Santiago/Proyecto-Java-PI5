package ejercicios.ejercicio1.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import _datos.DatosCafe;
import us.lsi.common.List2;

public record CafeProblem(Integer index, List<Integer> remaining) {
	
	public static CafeProblem initial() {
		List<Integer> ls = List2.copy(DatosCafe.getPesos());
		return of(0, ls);
	}
	
	public static CafeProblem of(Integer i, List<Integer> rest) {
		return new CafeProblem(i,rest);
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
		alternativas.sort(Comparator.reverseOrder());
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
	
	public CafeProblem neighbor(Integer a) {
		return of(index + 1, actualizarRemaining(a));
	}
	
	public Double heuristic() {
		return remaining.stream().allMatch(x -> x<=0) ? 0. : 
			IntStream.range(index, DatosCafe.getNumVariedades())
			.mapToDouble(x -> DatosCafe.getBeneficio(x)).max().orElse(-100.0);
	}

}
