package model

trait PlanHablateTodo extends Plan {

  private def debeCambiar(l: Llamada): Boolean = l.datos.fecha.esFinDeSemana()

  private def nuevaLlamada(l: Llamada): Llamada = l.cambiarMinutos(l.datos.minutos % 5)

  override def aplicarPlan(t: Comunicacion): Comunicacion = PlanesDeLlamada.aplicarParaLlamada(debeCambiar, nuevaLlamada, super.aplicarPlan(t))

}