package model

trait PlanLlamadaGratuita extends PlanDeLlamada {
	override def nuevaLlamada(l: Llamada): Llamada = l.cambiarTipo(ComunicacionGrauita())
}