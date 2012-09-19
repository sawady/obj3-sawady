package model
import scala.collection.mutable.Map

class Cliente(val numero: Int, val plan: Plan) {
  
  val comunicaciones: Map[Fecha, List[Comunicacion]] = Map()
  
  // Utilizar siempre este metodo para acceder a la lista de comunicaciones de una fecha
  def comunicacionesDeFecha(fecha: Fecha): List[Comunicacion] = if(comunicaciones.contains(fecha)) comunicaciones(fecha) else List()
  
  def registrarComunicaciones(fecha: Fecha, coms: List[Comunicacion]): Cliente = {
    for (com <- coms) registrarComunicacion(fecha, com)
    return this
  }
  
  def registrarComunicacion(fecha: Fecha, com: Comunicacion): Cliente = {
    comunicaciones.update(fecha, com :: comunicacionesDeFecha(fecha))
    return this
  }
  
  def cantidadTotalMinutos(fecha: Fecha): Int = comunicacionesDeFecha(fecha).collect { case l: Llamada => l.datos.minutos }.foldLeft(0)(_ + _)
  
  def aplicarPlanALlamadas(fecha: Fecha) = comunicaciones.update(fecha, comunicacionesDeFecha(fecha).map(p => plan.aplicarPlan(p))) 

  def montoAFacturar(fecha: Fecha): Int = {
   aplicarPlanALlamadas(fecha)
   return comunicacionesDeFecha(fecha).foldLeft(0)((r, x) => r + x.precio) 
  }
  
  def cantidadDeComunicacionesLargas(fecha: Fecha): Int = comunicacionesDeFecha(fecha).count(_.esLarga)

  def ciudadesDeLlamadasLargaDistancia(fecha: Fecha): List[Ciudad] = comunicacionesDeFecha(fecha).collect { case Llamada(datos, t: LlamadaLargaDistancia) => datos.ciudadDestino }

}