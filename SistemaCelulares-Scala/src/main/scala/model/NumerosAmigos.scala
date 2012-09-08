package model

class NumerosAmigos(nrosLibres: List[Int]) extends PlanLlamadaGratuita {
  override def debeCambiarLlamada(l: Llamada): Boolean = !nrosLibres.contains(l.datos.nroDestino)
}