package ejercicios.ejercicio2.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CursoPDR {
	
	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<CursoProblem, Spm> memory;
	public static Integer mejorValor; 

	public static SolucionCursos search() {
		memory =  Map2.empty();
		mejorValor = Integer.MAX_VALUE; // Estamos minimizando
		
		pdr_search(CursoProblem.initial(), 0, memory);
		return getSolucion();
	}
	
	private static Spm pdr_search(CursoProblem prob, Integer acumulado, Map<CursoProblem, Spm> memoria) {

		Spm res = null;
		Boolean esTerminal = prob.index().equals(DatosCursos.getNumTematicas());
		Boolean esSolucion = prob.remaining().isEmpty();

		if (memory.containsKey(prob)) {
			res = memory.get(prob);
			
		} else if (esSolucion) {
			res = Spm.of(null, 0);
			memory.put(prob, res);
			if (acumulado < mejorValor) { // Estamos minimizando
				mejorValor = acumulado;
			}
		} else {
			List<Spm> soluciones = List2.empty();
			for (Integer action : prob.actions()) {
				Double cota = acotar(acumulado, prob, action);   		
				if (cota > mejorValor) continue;
				CursoProblem vecino = prob.neighbor(action);
				Spm s = pdr_search(vecino, acumulado + action, memory);
				if (s != null) {
					Spm amp = Spm.of(action, s.weight() + action);
					soluciones.add(amp);
				}
			}
			// Estamos minimizando
			res = soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			if (res != null)
				memory.put(prob, res);
		}

		return res;
	}

	private static Double acotar(Integer acum, CursoProblem p, Integer a) {
		return acum + p.neighbor(a).heuristic()*a;
	}

	public static SolucionCursos getSolucion() {
		List<Integer> acciones = List2.empty();
		CursoProblem prob = CursoProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			CursoProblem old = prob;
			acciones.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionCursos.of(acciones);
	}

}
