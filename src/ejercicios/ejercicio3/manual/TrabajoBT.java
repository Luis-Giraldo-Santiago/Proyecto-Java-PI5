package ejercicios.ejercicio3.manual;

import java.util.Set;

import _soluciones.SolucionTrabajo;
import us.lsi.common.Set2;

public class TrabajoBT {
	
	private static Double mejorValor;
	private static TrabajoState estado;
	private static Set<SolucionTrabajo> soluciones;
	
	public static void search() {
		soluciones = Set2.newTreeSet();
		mejorValor = 0.0;
		estado = TrabajoState.inicial();
		bt_search();
	}
	
	private static void bt_search() {
		if(estado.esTerminal()) {
			Double valorObtenido = estado.acumulado;
			if(valorObtenido > mejorValor) {
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if(!estado.esTerminal()){
			for (Integer a: estado.alternativas()) {
				if(estado.cota(a) >= mejorValor) {
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	
	public static Set<SolucionTrabajo> getSolucion(){
		return soluciones;
	}

}
