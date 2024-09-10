package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosReparto;
import _utils.TestsPI5;
import ejercicios.ejercicio4.manual.RepartoBT;
import us.lsi.common.String2;

public class TestRepartoManual {
	
	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			DatosReparto.iniDatos("ficheros/Ejercicio4DatosEntrada"+num_test+".txt");
			RepartoBT.search();
			RepartoBT.getSoluciones().forEach(s -> String2.toConsole("Solucion obtenida: %s\n", s));
			TestsPI5.line("*");
		});
	}
}
