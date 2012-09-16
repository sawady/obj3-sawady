package model

trait PlanNumerosAmigos extends Plan {

  val nrosLibres: List[Int]

  private def debeCambiarLlamada(l: Llamada): Boolean = !l.esLlamadaGratuita() && nrosLibres.contains(l.datos.nroDestino)
  private def nuevaLlamada(l: Llamada): Llamada = l.cambiarATipoGratuita()

  override def aplicarPlan(t: Comunicacion): Comunicacion = super.aplicarPlan(t) match {
    case l: Llamada if this.debeCambiarLlamada(l) => this.nuevaLlamada(l)
    case otro                                     => otro
  }

}