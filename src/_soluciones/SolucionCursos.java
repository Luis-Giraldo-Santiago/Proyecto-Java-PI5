package _soluciones;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import _datos.DatosCursos;
import _datos.DatosCursos.Curso;
import ejercicios.ejercicio2.CursosEdge;
import ejercicios.ejercicio2.CursosVertex;
import us.lsi.common.List2;

public class SolucionCursos implements Comparable<SolucionCursos>{
	
	public static SolucionCursos of(List<Integer> ls) {
		return new SolucionCursos(ls);
	}
	
	public static SolucionCursos of(GraphPath<CursosVertex, CursosEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionCursos res = of(ls);
		res.path = ls;
		return res;
	}
	
	private Double precio;
	private List<Curso> cursos;
	
	private List<Integer> path;
	
	private SolucionCursos(List<Integer> ls) {
		precio = 0.;
		cursos = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				precio += DatosCursos.getPrecio(i);
				cursos.add(DatosCursos.cursos.get(i));
			}
		}
	}
	
	public String toString() {
		String s = cursos.stream().map(e -> "S"+e.id())
		.collect(Collectors.joining(", ", "Cursos elegidos: {", "}\n"));
		String res = String.format("%sPrecio Total: %.1f", s,precio);
		return path==null? res: String.format("%s\nPath de la solucion: %s", res, path);
	}
	
	public int compareTo(SolucionCursos s) {
		return precio.compareTo(s.precio);
	}

}
