package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosTrabajo;
import _utils.TestsPI5;
import ejercicios.ejercicio3.manual.TrabajoBT;

public class TestTrabajoManual {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			DatosTrabajo.iniDatos("ficheros/Ejercicio3DatosEntrada"+num_test+".txt");
			TrabajoBT.search();
			TrabajoBT.getSolucion();
			TestsPI5.line("*");
		});

	}

}
