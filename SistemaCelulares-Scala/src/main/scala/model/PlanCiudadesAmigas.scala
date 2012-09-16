package model

trait PlanCiudadesAmigas extends Plan {
  
  val ciudadAmiga: Ciudad
  
  private def debeCambiarLlamada(l: Llamada): Boolean = !l.esLlamadaGratuita() && l.datos.ciudadDestino == ciudadAmiga
  
  private def nuevaLlamada(l: Llamada): Llamada = l.cambiarATipoGratuita()
  
  override def aplicarPlan(t: Comunicacion): Comunicacion = super.aplicarPlan(t) match {
    case l: Llamada if this.debeCambiarLlamada(l) => this.nuevaLlamada(l)
    case otro                                      => otro
  }
  
}