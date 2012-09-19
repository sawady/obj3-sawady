package model

trait PrepagoLocal extends Prepago {

  private val contador = new ContadorDeMinutos(60)

  private def debeCambiar(l: Llamada): Boolean = l match {
    case Llamada(_, t: LlamadaLocal) => true
    case otro                        => false
  }

  override def aplicarPlan(t: Comunicacion): Comunicacion = PlanesDeLlamada.aplicarParaLlamada(debeCambiar, nuevaLlamada(contador, _), super.aplicarPlan(t))

}