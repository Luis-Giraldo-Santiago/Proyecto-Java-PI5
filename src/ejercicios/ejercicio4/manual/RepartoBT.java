package ejercicios.ejercicio4.manual;

import java.util.Set;

import _soluciones.SolucionReparto;
import us.lsi.common.Set2;

public class RepartoBT {
	
	private static Double mejorValor;
	private static RepartoState estado;
	private static Set<SolucionReparto> soluciones;
	
	public static void search() {
		soluciones = Set2.empty();
		mejorValor = Double.MIN_VALUE;
		estado = RepartoState.inicial();
		bt_search();
	}
	
	private static void bt_search() {
		if(estado.esTerminal()) {
			Double valorObtenido = estado.acumulado;
			if(valorObtenido > mejorValor) {
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if(!estado.esTerminal()) {
			for (Integer a: estado.alternativas()) {			
				if(estado.cota(a) >= mejorValor) {
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	
	public static Set<SolucionReparto> getSoluciones(){
		return soluciones;
	}
}
