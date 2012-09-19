package model

trait PlanCiudadesAmigas extends Plan {
  
  val ciudadAmiga: Ciudad
  
  private def debeCambiar(l: Llamada): Boolean = l.datos.ciudadDestino == ciudadAmiga
  
  override def aplicarPlan(t: Comunicacion): Comunicacion = PlanesDeLlamada.aplicarParaLlamadaGratuita(debeCambiar, super.aplicarPlan(t))
  
}