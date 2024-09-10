package ejercicios.tests;

import java.util.List;

import _datos.DatosReparto;
import _soluciones.SolucionReparto;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio4.RepartoVertex;

public class TestsEjercicio4 {

	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio4DatosEntrada", num_test, DatosReparto::iniDatos);
		
			TestsPI5.tests(
					RepartoVertex.initial(),
					RepartoVertex.goal(), 
					GraphsPI5::repartoBuilder, 
					RepartoVertex::greedyEdge, 
					SolucionReparto::of);
		});

	}

}
