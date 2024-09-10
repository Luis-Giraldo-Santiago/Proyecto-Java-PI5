package _soluciones;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import _datos.DatosTrabajo;
import ejercicios.ejercicio3.TrabajoEdge;
import ejercicios.ejercicio3.TrabajoVertex;
import us.lsi.common.List2;

public class SolucionTrabajo implements Comparable<SolucionTrabajo>{
	
	public static SolucionTrabajo of(List<Integer> ls){
		return new SolucionTrabajo(ls);
	}
	
	public static SolucionTrabajo of(GraphPath<TrabajoVertex, TrabajoEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionTrabajo res = of(ls);
		prueba = path.getWeight();
		res.path = ls;
		return res;
	}
	
	public static SolucionTrabajo empty() {
		return new SolucionTrabajo();
	}
	
	private Double calidadTotal;
	private SortedMap<Integer, List<Integer>> solucion;
	private List<Integer> path;
	private static Double prueba;
	
	private SolucionTrabajo(List<Integer> ls) {
		calidadTotal = 0.0;
		solucion = new TreeMap<>();
		List<Integer> diasTrabajo = List2.ofTam(0, DatosTrabajo.getNumTrabajos());
		for(int n = 0; n < ls.size(); n++) {
			int i = n%DatosTrabajo.getNumInvestigadores();
			int j = n/DatosTrabajo.getNumInvestigadores();
			diasTrabajo.set(j, diasTrabajo.get(j) + ls.get(n));
			if(solucion.containsKey(i)) {
				solucion.get(i).add(ls.get(n));
			}else {
				solucion.put(i, List2.of(ls.get(n)));
			}
		}
		
		if(prueba!=null) {
			calidadTotal = prueba;
		}else {
			List<Integer> lista = List2.copy(ls);
			for(int i = 0; i< DatosTrabajo.getNumTrabajos(); i++) {
				List<Integer> subLista = lista.subList(
						i*DatosTrabajo.getNumInvestigadores(), (i+1)*DatosTrabajo.getNumInvestigadores());
				Integer completo = DatosTrabajo.trabajos.get(i).especialidadDias()
						.values().stream().collect(Collectors.summingInt(x -> x));
				Integer hecho = subLista.stream().collect(Collectors.summingInt(x -> x));
				if(completo.equals(hecho)) {
					calidadTotal += DatosTrabajo.trabajos.get(i).calidad();
					
				}
			}
		}
	}
	
	private SolucionTrabajo() {
		calidadTotal = 0.0;
		solucion = new TreeMap<>();
	}
	
	public void add(Integer i, Integer j) {
		if(solucion.containsKey(i)) {
			solucion.get(i).add(j);
		}else {
			solucion.put(i, List2.of(j));
		}
	}
	
	public void obtener(Integer i) {
		calidadTotal = i.doubleValue();
	}
	
	public String toString() {
		String s = String.format("\nSUMA DE LAS CALIDADES DE LOS TRABAJOS REALIZADOS: %s", calidadTotal);
		return solucion.entrySet().stream().map(e -> "INV"+(e.getKey()+1)+": "+ e.getValue()).collect(
				Collectors.joining("\n", "\nReparto obtenido (días trabajados por cada investigador en cada\r\n"
						+ "trabajo):\n"
						+ "El camino obtenido es: " + path+"\n", s));
	}
	
	public int compareTo(SolucionTrabajo s) {
		return calidadTotal.compareTo(s.calidadTotal);
	}

}
