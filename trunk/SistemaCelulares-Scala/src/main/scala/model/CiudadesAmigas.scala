package model

class CiudadesAmigas(ciudadAmiga: Ciudad) extends PlanLlamadaGratuita {
  override def debeCambiarLlamada(l: Llamada): Boolean = l.datos.ciudadDestino == ciudadAmiga
}