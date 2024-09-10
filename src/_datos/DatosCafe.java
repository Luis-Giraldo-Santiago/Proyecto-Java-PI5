package _datos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;


public class DatosCafe {
	public static record Cafe(String nombre, Integer kilogramos) {
		public static Cafe create(String s) {
			String[] v = s.split(":");
			return new Cafe(v[0], Integer.valueOf(v[1].trim().replace("kgdisponibles=", "").replace(";", "")));
		}
	}
	
	public static record Variedad(String nombre, Integer beneficio, Map<Integer,Double> porcentaje) 
		implements Comparable<Variedad>{
		public static Variedad create(String s) {
			Map<Integer, Double> porc = new HashMap<>();
			String[] variedad = s.split(" -> ");
			String[] prop = variedad[1].split(";");
			String comp = prop[1].trim().replace("comp=", "");
			String[] compo = comp.split(",");
			for(String c : compo) {
				String[] componente = c.split(":");
				Integer clave = Integer.valueOf(componente[0].trim().replace("(C", ""));
				Double valor = Double.valueOf(componente[1].trim().replace(")", ""));
				if(!porc.containsKey(clave)) {
					porc.put(clave, valor);
				}
			}
			return new Variedad(variedad[0].trim(), Integer.valueOf(prop[0].trim().replace("beneficio=", "")),
					porc);
			
		}

		@Override
		public int compareTo(Variedad o) {
			return beneficio.compareTo(o.beneficio());
		}
		
		
	}
	
	public static List<Cafe> tiposCafes = new ArrayList<>();
	public static List<Variedad> variedades = new ArrayList<>();
	
	public static void iniDatos(String fichero) {
		
		List<String> lineas = Files2.linesFromFile(fichero);
		tiposCafes = List2.empty();
		variedades = List2.empty();
		lineas.remove("// TIPOS");
		lineas.remove("// VARIEDADES");
		List<Variedad> ls = List2.empty();
		for(String l:lineas) {
			if(l.startsWith("C")==true) {
				Cafe cafe = Cafe.create(l);
				tiposCafes.add(cafe);
			}else {
				Variedad variedad = Variedad.create(l);
				ls.add(variedad);
			}
		}
		variedades = ls.stream().sorted(Comparator.reverseOrder()).toList();
		toConsole();
	}

	public static List<Cafe> getTipos(){
		return tiposCafes;
	}
	
	public static Double getPorcentajeTipo(Integer i, Integer j) {
		return variedades.get(j).porcentaje().get(i);
	}
	
	public static Integer getCantidad(Integer i) {
		return tiposCafes.get(i).kilogramos();
	}
	
	public static Integer getBeneficio(Integer j) {
		return variedades.get(j).beneficio();
	}
	
	public static Integer getNumCafes() {
		return tiposCafes.size();
	}
	
	public static Integer getNumVariedades() {
		return variedades.size();
	}
	
	public static List<Integer> getPesos(){
		List<Integer> lista = List2.empty();
		for(int i = 0; i< getNumCafes(); i++) {
			lista.add(tiposCafes.get(i).kilogramos());
		}
		return lista;
	}
	
	public static Map<Integer,Integer> getPesosMap(){
		Map<Integer,Integer> lista = Map2.empty();
		for(int i = 0; i< getNumCafes(); i++) {
			if(!lista.containsKey(i)) {
				lista.put(i, tiposCafes.get(i).kilogramos());
			}
		}
		return lista;
	}
	
	public static void toConsole() {
		String2.toConsole("Tipos de cafés: %s",tiposCafes);
		String2.toConsole("Variedades de cafés: %s", variedades);
		String2.toConsole(String2.linea());
		
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio1DatosEntrada2.txt");
	}

}
