package ejercicios.ejercicio4;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosReparto;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record RepartoVertex(Integer index, List<Integer> remaining, Double recorrido, List<Integer> ce) 
		implements VirtualVertex<RepartoVertex, RepartoEdge, Integer>{
	
	public static RepartoVertex initial() {
		return of(0, List2.copy(DatosReparto.getClientes()), 0.0, List2.empty());
	}
	
	public static RepartoVertex of(Integer index, List<Integer> remaining, Double recorrido, List<Integer> ce) {
		return new RepartoVertex(index, remaining, recorrido, ce);
	}
	
	public static Predicate<RepartoVertex> goal(){
		return v -> v.index() == DatosReparto.getNumVertices() + 1;
	}
	
	public static Predicate<RepartoVertex> goalHasSolution(){
		return v -> v.remaining().isEmpty();
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
	
	public RepartoVertex neighbor(Integer a) {
		List<Integer> camino = List2.copy(ce);
		camino.add(0,a);
		return of(index+1, actualizarRemaining(a), actualizarRecorrido(a), camino);
	}
	
	public RepartoEdge edge(Integer a) {
		return RepartoEdge.of(this, neighbor(a), a);
	}
	
	public RepartoEdge greedyEdge() {
		Integer res = 0;
		List<Integer> ls = List2.copy(actions());
		List<Double> beneficios = List2.empty();
		for(Integer l: ls) {
			beneficios.add(DatosReparto.vertices.get(l).beneficio());
		}
		Double maximo = beneficios.get(0);
		for(Integer v: remaining) {
			if(DatosReparto.getBeneficio(v)==maximo) {
				res = v;
			}
		}
		return edge(res);
	}
	
	public String toString() {
		return String.format("%d; %s", index, remaining);
	}

}
