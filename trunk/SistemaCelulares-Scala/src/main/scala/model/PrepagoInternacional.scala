package model

trait PrepagoInternacional extends Plan {
  
  var minutosLibres: Int = 60
  
  def nuevaLlamada(l: Llamada): Llamada = {
    if (minutosLibres <= 0) return l

    var aRestar: Int = 0

    if (l.datos.minutos >= minutosLibres) aRestar = minutosLibres
    else aRestar = minutosLibres - l.datos.minutos

    this.minutosLibres -= aRestar
    return l.cambiarMinutos(l.datos.minutos - aRestar)
  }  
  
  def debeCambiarLlamada(l: Llamada): Boolean = !l.esLlamadaGratuita() && (l match {
      case Llamada(_, t: LlamadaLargaDistancia) => true
      case otro => false
  })
  
  override def aplicarPlan(t: Comunicacion): Comunicacion = super.aplicarPlan(t) match {
    case l: Llamada if this.debeCambiarLlamada(l) => this.nuevaLlamada(l)
    case otro                                      => otro
  }
  
}