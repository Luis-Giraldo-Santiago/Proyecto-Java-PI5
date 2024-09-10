package _soluciones;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosReparto;
import ejercicios.ejercicio4.RepartoEdge;
import ejercicios.ejercicio4.RepartoVertex;

public class SolucionReparto {
	
	public static SolucionReparto of(List<Integer> ls) {
		return new SolucionReparto(ls);
	}
	
	public static SolucionReparto of(GraphPath<RepartoVertex, RepartoEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionReparto res = of(ls);
		res.path = ls;
		return res;
	}
	
	public static SolucionReparto empty() {
		return new SolucionReparto();
	}
	
	private Double kmsTotal;
	private Double beneficioTotal;
	private List<Integer> solucion;
	
	private List<Integer> path;
	
	private SolucionReparto(List<Integer> ls) {
		kmsTotal = 0.0;
		beneficioTotal = 0.0;
		solucion = new ArrayList<>();
		solucion.addAll(ls);
		for(int n = 0; n<ls.size() - 1; n++) {
			kmsTotal += DatosReparto.getKilometro(ls.get(n), ls.get(n+1));
			beneficioTotal += DatosReparto.getBeneficio(ls.get(n)) - kmsTotal;
			if(n==(ls.size() - 2)) {
				kmsTotal -= DatosReparto.getKilometro(ls.get(n), ls.get(n+1));
			}
		}
	}
	
	private SolucionReparto() {
		kmsTotal = 0.;
		beneficioTotal = 0.;
		solucion = new ArrayList<>();
	}

	public void obtenerSolución(List<Integer> ls) {
		solucion = ls;
	}
	
	public void obtenerKms(Double i) {
		kmsTotal = i;
	}
	
	public void obtenerBeneficio(Double i) {
		beneficioTotal = i;
	}
	
	public String toString() {
		return "Camino desde "+ DatosReparto.getOrigen().idCliente()+" hasta "+DatosReparto.getOrigen().idCliente()
				+"\n"+solucion+"\nKilometros: "+ kmsTotal+"\nBeneficio: "+ beneficioTotal;
	}

}
