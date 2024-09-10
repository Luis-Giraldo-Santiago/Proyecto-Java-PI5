package ejercicios.ejercicio1.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import _datos.DatosCafe;
import _soluciones.SolucionCafe;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CafePDR {
	
	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<CafeProblem, Spm> memory;
	public static Integer mejorValor;
	
	public static SolucionCafe search() {
		memory = Map2.empty();
		mejorValor = Integer.MIN_VALUE;
		
		pdr_search(CafeProblem.initial(), 0, memory);
		return getSolucion();
	}
	
	private static Spm pdr_search(CafeProblem prob, Integer acumulado, Map<CafeProblem, Spm> memoria) {
		
		Spm res = null;
		Boolean esTerminal = prob.index().equals(DatosCafe.getNumVariedades());
		Boolean esSolucion = prob.remaining().stream().allMatch(x -> x==0);
		
		if(memory.containsKey(prob)) {
			res = memory.get(prob);
		} else if(esTerminal) {
			res = Spm.of(null, 0);
			memory.put(prob, res);
			if (acumulado > mejorValor) {
				mejorValor = acumulado;
			}
		} else {
			List<Spm> soluciones = List2.empty();
			for(Integer action: prob.actions()) {
				Double cota = acotar(acumulado, prob, action);
				if(cota < mejorValor) continue;
				CafeProblem vecino = prob.neighbor(action);
				Spm s = pdr_search(vecino, acumulado + action*DatosCafe.getBeneficio(prob.index()), memory);
				if(s != null) {
					Spm amp = Spm.of(action, s.weight() + action);
					soluciones.add(amp);
				}
			}
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(res != null) {
				memory.put(prob, res);
			}
		}
		return res;
	}
	
	private static Double acotar(Integer acum, CafeProblem p, Integer a) {
		return acum + a*DatosCafe.getBeneficio(p.index()) + p.neighbor(a).heuristic();
	}
	
	public static SolucionCafe getSolucion() {
		List<Integer> acciones = List2.empty();
		CafeProblem prob = CafeProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			CafeProblem old = prob;
			acciones.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionCafe.of(acciones);
	}

}
