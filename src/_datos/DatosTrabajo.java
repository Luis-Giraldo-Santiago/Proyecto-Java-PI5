package _datos;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosTrabajo {
	
	public static record Investigador(String nombre, Integer capacidad, Integer especialidad) {
		public static Investigador create(String s) {
			String[]trozos = s.split(":");
			String[]t = trozos[1].split(";");
			return new Investigador(trozos[0].trim(), Integer.valueOf(t[0].replace(" capacidad=", "").trim()), 
					Integer.valueOf(t[1].replace(" especialidad=", "").trim()));
		}
	}
	
	public static record Trabajo(String nombre, Integer calidad, Map<Integer,Integer> especialidadDias) 
				implements Comparable<Trabajo>{
		public static Trabajo create(String s) {
			String[] trozos = s.split("->");
			String[] t = trozos[1].split(";");
			return new Trabajo(trozos[0].trim(),Integer.valueOf(t[0].replace(" calidad=", "").trim()),
					parseaMap(t[1].replace(" reparto=", "").trim()));
			
		}

		private static Map<Integer, Integer> parseaMap(String s) {
			Map<Integer,Integer> map = new HashMap<>();
			String[] ls = s.split(",");
			for(String l: ls) {
				String[] num = l.split(":");
				map.put(Integer.valueOf(num[0].replace("(", "").trim()), Integer.valueOf(num[1].replace(")", "").trim()));
			}
			return map;
		}

		@Override
		public int compareTo(Trabajo t) {
			return calidad().compareTo(t.calidad());
		}
	}
	
	public static List<Investigador> investigadores;
	public static List<Trabajo> trabajos;
	
	public static Integer getNumInvestigadores() {
		return investigadores.size();
	}
	
	public static Integer getNumEspecialidades() {
		return trabajos.get(0).especialidadDias().keySet().size();
	}
	
	public static Integer getNumTrabajos() {
		return trabajos.size();
	}
	
	public static Integer getEspecialidad(Integer i, Integer k) {
		Integer res = 0;
		if(investigadores.get(i).especialidad()==k) {
			res = 1;
		}
		return res;
	}
	
	public static Integer getDiasDisponibles(Integer i) {
		return investigadores.get(i).capacidad();
	}
	
	public static Integer getDiasNecesarios(Integer j, Integer k) {
		return trabajos.get(j).especialidadDias().get(k);
	}
	
	public static Integer getCalidad(Integer j) {
		return trabajos.get(j).calidad();
	}
	
	public static List<Integer> getCapacidad(){
		List<Integer> res = List2.empty();
		for(int i = 0; i<investigadores.size(); i++) {
			res.add(investigadores.get(i).capacidad());
		}
		return res;
	}
	
	public static void iniDatos(String fichero) {
		investigadores = List2.empty();
		trabajos = List2.empty();
		List<Trabajo> aux= List2.empty();
		List<String> lineas = Files2.linesFromFile(fichero);
		for(int i = 0; i<lineas.size(); i++) {
			if(lineas.get(i).startsWith("INV")) {
				investigadores.add(Investigador.create(lineas.get(i)));
			}
			else if(lineas.get(i).startsWith("T")) {
				aux.add(Trabajo.create(lineas.get(i)));
			}
		}
		trabajos = aux.stream().sorted(Comparator.reverseOrder()).toList();
		toConsole();
	}
	
	public static List<Integer> getEspecialidadDias(){
		List<Integer> ls = List2.empty();
		for(int i = 0; i < getNumTrabajos(); i++) {
			ls.addAll(trabajos.get(i).especialidadDias.values());
		}
		return ls;
	}
	
	public static void toConsole() {
		String2.toConsole("Investigadores: %s",investigadores);
		String2.toConsole("Trabajos: %s",trabajos);
		String2.toConsole(String2.linea());
	}
	
	public static void main(String[] args) throws IOException{
		iniDatos("ficheros/Ejercicio3DatosEntrada2.txt");
	}
	

}
