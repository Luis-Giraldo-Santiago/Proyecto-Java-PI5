package ejercicios.tests;

import java.util.List;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio2.CursosVertex;

public class TestsEjercicio2 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio2DatosEntrada", num_test, DatosCursos::iniDatos);
			
			
			TestsPI5.tests(
					CursosVertex.initial(), 
					CursosVertex.goal(), 
					GraphsPI5::cursosBuilder, 
					CursosVertex::greedyEdge, 
					SolucionCursos::of);
		});

	}

}
