package model

trait PlanDeLlamada extends Plan {
  def debeCambiarLlamada(l: Llamada): Boolean
  def nuevaLlamada(l: Llamada): Llamada
  
  override def intercambiarPor(t: TipoDeComunicacion): TipoDeComunicacion = t match {
      case l: Llamada if this.debeCambiarLlamada(l) => this.nuevaLlamada(l)
      case otro => otro
    }
}