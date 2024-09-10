package ejercicios.tests;

import java.util.List;

import _datos.DatosCafe;
import _soluciones.SolucionCafe;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio1.CafeVertex;

public class TestsEjercicio1 {
	
	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest(
					"Ejercicio1DatosEntrada",
					num_test, 
					DatosCafe::iniDatos);
			
			TestsPI5.tests(
					CafeVertex.initial(), 
					CafeVertex.goal(), 
					GraphsPI5::cafeBuilder, 
					CafeVertex::greedyEdge, 
					SolucionCafe::of);
		});
	}

}
