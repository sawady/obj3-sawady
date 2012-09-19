package model

trait PlanNumerosAmigos extends Plan {

  val nrosLibres: List[Int]

  private def debeCambiar(l: Llamada): Boolean = nrosLibres.contains(l.datos.nroDestino)
  
  override def aplicarPlan(t: Comunicacion): Comunicacion = PlanesDeLlamada.aplicarParaLlamadaGratuita(debeCambiar, super.aplicarPlan(t))

}