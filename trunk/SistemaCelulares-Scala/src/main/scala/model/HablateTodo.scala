package model

class HablateTodo extends PlanDeLlamada {
  override def debeCambiarLlamada(l: Llamada): Boolean = l.datos.fecha.esFinDeSemana()
  
  override def nuevaLlamada(l: Llamada): Llamada = l.cambiarMinutos(l.datos.minutos % 5)
}