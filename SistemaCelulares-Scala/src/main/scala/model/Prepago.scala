package model

trait Prepago extends PlanDeLlamada {
  var minutosLibres: Int
  
  override def nuevaLlamada(l: Llamada): Llamada = {
   val aRestar  = minutosLibres % l.datos.minutos
   this.minutosLibres -= aRestar
   return l.cambiarMinutos(l.datos.minutos - aRestar)
  }
}