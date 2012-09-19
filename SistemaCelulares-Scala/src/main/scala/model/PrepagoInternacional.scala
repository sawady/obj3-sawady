package model

trait PrepagoInternacional extends Prepago {

  private val contador = new ContadorDeMinutos(30)

  private def debeCambiar(l: Llamada): Boolean = l match {
    case Llamada(_, t: LlamadaLargaDistancia) => true
    case otro                                 => false
  }

  override def aplicarPlan(t: Comunicacion): Comunicacion = PlanesDeLlamada.aplicarParaLlamada(debeCambiar, nuevaLlamada(contador, _), super.aplicarPlan(t))

}