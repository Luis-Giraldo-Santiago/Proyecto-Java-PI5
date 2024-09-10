package ejercicios.ejercicio4.manual;

import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosReparto;
import us.lsi.common.List2;

public record RepartoProblem(Integer index, List<Integer> remaining, Double recorrido, List<Integer> ce) {
	
	public static RepartoProblem initial() {
		return of(0, List2.copy(DatosReparto.getClientes()), 0.0, List2.empty());
	}
	
	public static RepartoProblem of(Integer index, List<Integer> remaining, Double recorrido, List<Integer> ce) {
		return new RepartoProblem(index, remaining, recorrido, ce);
	}
	
	public List<Integer> actions(){
		List<Integer> alternativas = List2.empty();
		if(index==0) {
			alternativas = List2.of(DatosReparto.getOrigen().idCliente());
		}else if(index<DatosReparto.getNumVertices() + 1) {
			if(remaining.isEmpty()) {
				alternativas = List2.of(0);
			}
			else {
				List<Integer> options = getOptions();
				if(options.size()==0) {
					alternativas = List2.of(0);
				} else if(index==DatosReparto.getNumVertices()) {
					alternativas = options.size()==0? List2.of(0) : options;
				}else {
					alternativas = options;
				}
			}
		}
		return alternativas;
	}
	
	public List<Integer> getOptions(){
		List<Integer> lista = List2.empty();
		List<Integer> ls = List2.copy(remaining);
		Integer accionAnterior = ce.get(0);
		for(Integer l: ls) {
			if(DatosReparto.existeArista(accionAnterior, l)) {
				lista.add(l);
			}
		}
		if(lista.isEmpty()) lista.add(0);
		return lista;
	}
	
	public Double actualizarRecorrido(Integer a) {
		Double res = recorrido;
		res += DatosReparto.getKilometro(a, index%DatosReparto.getNumVertices());
		return res;
	}
	
	public List<Integer> actualizarRemaining(Integer a){
		List<Integer> ls = List2.copy(remaining);
		ls.remove(a);
		return ls;
	}
	
	public RepartoProblem neighbor(Integer a) {
		List<Integer> camino = List2.copy(ce);
		camino.add(0,a);
		return of(index+1, actualizarRemaining(a), actualizarRecorrido(a), camino);
	}
	
	public Double heuristic() {
		return remaining.isEmpty() ? 0. : 
			IntStream.range(index, DatosReparto.getNumVertices() + 1)
			.mapToDouble(x -> DatosReparto.getBeneficio(x%DatosReparto.getNumVertices())).max().orElse(-1000.0);
	}

}
