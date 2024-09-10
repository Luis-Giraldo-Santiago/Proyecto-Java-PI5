package ejercicios.ejercicio3.manual;

import java.util.List;

import _datos.DatosTrabajo;
import _soluciones.SolucionTrabajo;
import us.lsi.common.List2;

public class TrabajoState {
	
	TrabajoProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<TrabajoProblem> anteriores;
	
	private TrabajoState(TrabajoProblem p, Double a, List<Integer> ls1, List<TrabajoProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}
	
	public static TrabajoState inicial() {
		TrabajoProblem pi = TrabajoProblem.inicial();
		return of(pi, 0., List2.empty(), List2.empty());
	}
	
	public static TrabajoState of(TrabajoProblem prob, Double acum, List<Integer> lsa, List<TrabajoProblem> lsp) {
		return new TrabajoState(prob, acum, lsa, lsp);
	}

	
	public void forward(Integer a) {
		acumulado = actual.getCalidad();
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}
	
	public void back() {
		int last = acciones.size() - 1;
		var prob_ant = anteriores.get(last);
		
		acumulado = actual.getCalidad();
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_ant;
		
	}
	
	public List<Integer> alternativas(){
		return actual.actions();
	}
	
	public Double cota(Integer a) {
		return actual.neighbor(a).getCalidad();
	}
	
	public SolucionTrabajo getSolucion() {
		System.out.println(SolucionTrabajo.of(acciones));
		return SolucionTrabajo.of(acciones);
	}
	
	public Boolean esSolucion() {
		return actual.remaining().stream().allMatch(x -> x>=0);
	}
	
	public Boolean esTerminal() {
		return actual.index() == DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumTrabajos();
	}
}
