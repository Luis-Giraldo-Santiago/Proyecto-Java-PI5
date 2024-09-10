package _soluciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import _datos.DatosCafe;
import ejercicios.ejercicio1.CafeEdge;
import ejercicios.ejercicio1.CafeVertex;

public class SolucionCafe implements Comparable<SolucionCafe>{
	
	public static SolucionCafe of(List<Integer> ls) {
		return new SolucionCafe(ls);
	}
	
	public static SolucionCafe of(GraphPath<CafeVertex, CafeEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionCafe res = of(ls);
		res.path = ls;
		return res;
	}
	
	private Integer beneficio;
	private Map<String,Integer> solucion;
	
	private List<Integer> path;
	
	private SolucionCafe() {
		beneficio = 0;
		solucion = new HashMap<>();
	}
	
	private SolucionCafe(List<Integer> ls) {
		beneficio = 0;
		solucion = new HashMap<>();
		for (int i = 0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				String v = DatosCafe.variedades.get(i).nombre();
				solucion.put(v, ls.get(i));
			}

			beneficio += ls.get(i)*DatosCafe.getBeneficio(i);
		}
	}
	
	public String toString() {
		return String.format("Cantidad de cada variedad = %s; Beneficio total = %s; Camino = %s", solucion, beneficio, path);
	}

	@Override
	public int compareTo(SolucionCafe s) {
		return beneficio.compareTo(s.beneficio);
	}

}
