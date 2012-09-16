package model

trait PlanHablateTodo extends Plan {
  
  override def aplicarPlan(t: Comunicacion): Comunicacion = super.aplicarPlan(t) match {
    case l: Llamada if this.debeCambiarLlamada(l) => this.nuevaLlamada(l)
    case otro                                      => otro
  }
  
  def debeCambiarLlamada(l: Llamada): Boolean = !l.esLlamadaGratuita() && l.datos.fecha.esFinDeSemana()
  def nuevaLlamada(l: Llamada): Llamada = l.cambiarMinutos(l.datos.minutos % 5)
  
}