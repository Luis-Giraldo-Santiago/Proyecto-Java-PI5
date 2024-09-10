package _utils;

import java.util.function.Predicate;

import ejercicios.ejercicio1.CafeEdge;
import ejercicios.ejercicio1.CafeHeuristic;
import ejercicios.ejercicio1.CafeVertex;
import ejercicios.ejercicio2.CursosEdge;
import ejercicios.ejercicio2.CursosHeuristic;
import ejercicios.ejercicio2.CursosVertex;
import ejercicios.ejercicio3.TrabajoEdge;
import ejercicios.ejercicio3.TrabajoHeuristic;
import ejercicios.ejercicio3.TrabajoVertex;
import ejercicios.ejercicio4.RepartoEdge;
import ejercicios.ejercicio4.RepartoHeuristic;
import ejercicios.ejercicio4.RepartoVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.graphs.virtual.EGraphBuilder;
import us.lsi.path.EGraphPath.PathType;


public class GraphsPI5 {
	
	// Ejercicio1: Builder
	public static EGraphBuilder<CafeVertex, CafeEdge>
	cafeBuilder(CafeVertex v_inicial, Predicate<CafeVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(CafeVertex.goalHasSolution())
				.heuristic(CafeHeuristic::heuristic);
	}

	// Ejercicio2: Builder
	public static EGraphBuilder<CursosVertex, CursosEdge>
	cursosBuilder(CursosVertex v_inicial, Predicate<CursosVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(CursosVertex.goalHasSolution())
				.heuristic(CursosHeuristic::heuristic);
	}
	
	// Ejecicio3: Builder
	public static EGraphBuilder<TrabajoVertex, TrabajoEdge>
	trabajoBuilder(TrabajoVertex v_inicial, Predicate<TrabajoVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Last, Type.Max)
				.goalHasSolution(TrabajoVertex.goalHasSolution())
				.vertexWeight(v -> v.getCalidad())
				.heuristic(TrabajoHeuristic::heuristic);
	}
	
	// Ejercicio4: Builder
		public static EGraphBuilder<RepartoVertex, RepartoEdge>
		repartoBuilder(RepartoVertex v_inicial, Predicate<RepartoVertex> es_terminal) { 
			return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
					.goalHasSolution(RepartoVertex.goalHasSolution())
					.heuristic(RepartoHeuristic::heuristic);
		}
	
}
