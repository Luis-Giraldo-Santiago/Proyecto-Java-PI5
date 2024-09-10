package ejercicios.ejercicio4.manual;

import java.util.List;

import _datos.DatosReparto;
import _soluciones.SolucionReparto;
import us.lsi.common.List2;

public class RepartoState {
	
	RepartoProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<RepartoProblem> anteriores;
	
	private RepartoState(RepartoProblem p, Double a, List<Integer> ls1, List<RepartoProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}
	
	public static RepartoState inicial() {
		RepartoProblem pi = RepartoProblem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}
	
	public static RepartoState of(RepartoProblem prob, Double acum, List<Integer> lsa, List<RepartoProblem> lsp) {
		return new RepartoState(prob, acum, lsa, lsp);
	}
	
	public void forward(Integer a) {
		acumulado += (DatosReparto.getBeneficio(a) - actual.recorrido());
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}
	
	public void back() {
		int last = acciones.size() - 1;
		var prob_ant = anteriores.get(last);
		
		acumulado -= (DatosReparto.getBeneficio(acciones.get(last)) - prob_ant.recorrido());
		actual = prob_ant;
		acciones.remove(last);
		anteriores.remove(last);
		
	}
	
	public List<Integer> alternativas(){
		return actual.actions();
	}
	
	public Double cota(Integer a) {
		Double weight = DatosReparto.getBeneficio(a) - actual.recorrido();
		return acumulado + weight + (actual.neighbor(a).heuristic()- actual.neighbor(a).recorrido());
	}
	
	public Boolean esSolucion() {
		return actual.remaining().stream().allMatch(x -> x==0);
	}
	
	public Boolean esTerminal() {
		return actual.index() == DatosReparto.getNumVertices() + 1;
	}
	
	public SolucionReparto getSolucion() {
		return SolucionReparto.of(acciones);
	}

}
