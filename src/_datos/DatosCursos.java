package _datos;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;


public class DatosCursos {
	
	public static record Curso(Integer id, Set<Integer> temas, Double costes, Integer centro) {
		public static int cont;
		public static Curso create(String s) {
			String[] v = s.split(":");
			return new Curso(cont++, Set2.parse(v[0], "{,}" , Integer::parseInt), 
					Double.parseDouble(v[1].trim()), Integer.parseInt(v[2].trim()));
		}
	}
	public static Integer maxCentros;
	public static List<Curso> cursos;
	public static List<Integer> tematicas;
	
	public static void iniDatos(String fichero) {
		Curso.cont=0;
		cursos = List2.empty();
		Set<Integer> tem = new TreeSet<>();
		List<String> lineas = Files2.linesFromFile(fichero);
		maxCentros = Integer.valueOf(lineas.get(0).replace("Max_Centros = ", ""));
		for(int i = 1; i<lineas.size(); i++) {
			Curso c = Curso.create(lineas.get(i));
			cursos.add(c);
			tem.addAll(c.temas);
		}
		tematicas = List2.ofCollection(tem);
		toConsole();
	}
	
	public static Integer getNumCursos() {
		return cursos.size();
	}
	
	public static Integer getMaxCentro() {
		return maxCentros;
	}
	
	public static Integer getNumTematicas() {
		return tematicas.size();
	}
	
	public static Integer getNumCentros() {
		Set<Integer> centro = new HashSet<>();
		for(int i = 0; i<cursos.size(); i++) {
			centro.add(cursos.get(i).centro);
		}
		return centro.size();
	}
	
	public static Integer getTratada(Integer i, Integer j) {
		Integer res = 0;
		if(cursos.get(i).temas.contains(j+1)) {
			res = 1;
		}
		return res;
		
	}
	
	public static Double getPrecio(Integer i) {
		return cursos.get(i).costes;
	}
	
	public static Integer getImpartida(Integer i, Integer k) {
		Integer res = 0;
		if(cursos.get(i).centro==k) {
			res=1;
		}
		return res;
	}
	
	public static Set<Integer> getElementos(Integer index){
		return cursos.get(index).temas;
	}
	
	public static Set<Integer> getCentros(){
		Set<Integer> centro = new HashSet<>();
		for(int i = 0; i<cursos.size(); i++) {
			centro.add(cursos.get(i).centro);
		}
		return centro;
	}
	
	public static void toConsole() {
		String2.toConsole("Tematicas: %s",tematicas);
		String2.toConsole(cursos,"Crusos");
		String2.toConsole("Máximo de centros que se pueden elegir: %s", maxCentros);
		String2.toConsole(String2.linea());
	}
	
	public static void main(String[] args) throws IOException{
		iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");
	}

}
