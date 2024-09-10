package ejercicios.ejercicio3;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import _datos.DatosTrabajo;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record TrabajoVertex(Integer index, List<Integer> remaining, List<Integer> camino) 
		implements VirtualVertex<TrabajoVertex, TrabajoEdge, Integer>{
	
	public static TrabajoVertex initial() {
		List<Integer> ls = List2.copy(DatosTrabajo.getEspecialidadDias());
		return of(0, List2.copy(DatosTrabajo.getCapacidad()), ls);
	}
	
	public static TrabajoVertex vFinal() {
		return of(DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumTrabajos(), List2.empty(), List2.empty());
	}
	
	public Double getCalidad() {
		List<Integer> lista = List2.copy(camino);
		Double res = 0.0;
		for(int i = 0; i< DatosTrabajo.getNumTrabajos(); i++) {
			List<Integer> subLista = lista.subList(
					i*DatosTrabajo.getNumEspecialidades(), (i+1)*DatosTrabajo.getNumEspecialidades());
			if(subLista.stream().allMatch(x -> x==0)) {
				res += DatosTrabajo.trabajos.get(i).calidad();
			}
			
		}
		return res;
	}
	
	public static TrabajoVertex of(Integer index, List<Integer> remaining, List<Integer> camino) {
		return new TrabajoVertex(index,remaining, camino);
	}
	
	public static Predicate<TrabajoVertex> goal(){
		return v -> v.index() == DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumTrabajos();
	}
	
	public static Predicate<TrabajoVertex> goalHasSolution(){
		return v -> v.remaining.stream().allMatch(x -> x==0);
	}

	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if(index < DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumTrabajos()) {
			if(remaining.stream().allMatch(x -> x == 0)) {
				alternativas = List2.of(0);
			}else{
				List<Integer> options = getOptions();
				if(options.isEmpty()) {
					alternativas = List2.empty();
				} else if(index == DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumTrabajos()-1) {
					alternativas = options.isEmpty()? List2.empty() : options;
				} else {
					alternativas = options;
				}
			}
		}
		return alternativas;
	}
	
	public List<Integer> getOptions(){
		Integer res = 0;
		List<Integer> lista = List2.copy(remaining);
		List<Integer> caminos = List2.copy(camino);
		Map<Integer,Integer> especialidades = DatosTrabajo.trabajos.
				get(index/DatosTrabajo.getNumInvestigadores()).especialidadDias();
		res = especialidades.get(DatosTrabajo.investigadores.
				get(index%DatosTrabajo.getNumInvestigadores()).especialidad());
		Integer especialidad = DatosTrabajo.investigadores.
				get(index%DatosTrabajo.getNumInvestigadores()).especialidad();
		List<Integer> opcionesPosibles = List2.rangeList(0, (res + 1));
		List<Integer> ls = List2.copy(opcionesPosibles);
		for(Integer l: ls) {
			if(((lista.get(index%DatosTrabajo.getNumInvestigadores()) - l) < 0) || (
					(caminos.get(especialidad+index/DatosTrabajo.getNumInvestigadores()
					*DatosTrabajo.getNumEspecialidades()) - l) < 0)) {
				opcionesPosibles.remove(l);
			}
		}
		return opcionesPosibles;
	}
	
	public List<Integer> actualizarRemaining(Integer a){
		List<Integer> lista = List2.copy(remaining);
		Integer res = lista.get(index%DatosTrabajo.getNumInvestigadores()) - a;
		lista.set(index%DatosTrabajo.getNumInvestigadores(), res);
		return lista;
	}
	
	public List<Integer> actualizaCamino(Integer a){
		List<Integer> caminos = List2.copy(camino);
		Integer especialidad = DatosTrabajo.investigadores.get(
				index%DatosTrabajo.getNumInvestigadores()).especialidad();
		Integer nuevoValor = caminos.get(especialidad+
				(index/DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumEspecialidades()));
		nuevoValor = nuevoValor - a;
		caminos.remove(especialidad+index/DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumEspecialidades());
		caminos.add(especialidad+index/DatosTrabajo.getNumInvestigadores()*DatosTrabajo.getNumEspecialidades(), nuevoValor);
		return caminos;
	}
	
	public TrabajoVertex neighbor(Integer a) {
		return of(index + 1, actualizarRemaining(a), actualizaCamino(a));
	}

	@Override
	public TrabajoEdge edge(Integer a) {
		return TrabajoEdge.of(this, neighbor(a), a);
	}
	
	public TrabajoEdge greedyEdge() {
		return edge(actions().stream().max(Comparator.naturalOrder()).orElse(0));
	}
	
	public String toString() {
		return String.format("%d; %s", index, remaining);
	}

}
