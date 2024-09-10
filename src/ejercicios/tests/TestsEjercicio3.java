package ejercicios.tests;

import java.util.List;

import _datos.DatosTrabajo;
import _soluciones.SolucionTrabajo;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio3.TrabajoVertex;

public class TestsEjercicio3 {
	
	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio3DatosEntrada", num_test, DatosTrabajo::iniDatos);
			
			TestsPI5.tests(
					TrabajoVertex.initial(), 
					TrabajoVertex.goal(), 
					GraphsPI5::trabajoBuilder, 
					TrabajoVertex::greedyEdge, 
					SolucionTrabajo::of);
		});
	}

}
