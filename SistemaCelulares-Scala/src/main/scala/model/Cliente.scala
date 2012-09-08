package model

class Cliente(val numero: Int, val comunicaciones: Map[Fecha, List[TipoDeComunicacion]], plan: List[Plan]) {

  def this(numero: Int, plan: List[Plan]) = this(numero, Map(), plan)

  def cantidadTotalMinutos(fecha: Fecha): Int = comunicaciones(fecha).collect { case l: Llamada => l.datos.minutos }.foldLeft(0)(_ + _)

  def montoAFacturar(fecha: Fecha): Int = aplicarPlanes(fecha).foldLeft(0)((r, x) => r + x.precio)
  
  def aplicarPlanes(fecha: Fecha): List[TipoDeComunicacion] = plan.foldLeft(comunicaciones(fecha))((r,p) => p.aplicarPlan(r))

  def cantidadDeComunicacionesLargas(fecha: Fecha): Int = comunicaciones(fecha).count(_.esLarga)

  def ciudadesDeLlamadasLargaDistancia(fecha: Fecha): List[Ciudad] = comunicaciones(fecha).collect { case Llamada(datos, t: LlamadaLargaDistancia) => datos.ciudadDestino }

}