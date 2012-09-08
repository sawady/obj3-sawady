package model

class Cliente(val numero: Int, val comunicaciones: Map[Fecha, List[TipoDeComunicacion]], planes: List[Plan]) {

  def this(numero: Int, planes: List[Plan]) = this(numero, Map(), planes)
  
  /* Requerimientos Ej1 + BONUS */

  def cantidadTotalMinutos(fecha: Fecha): Int = comunicaciones(fecha).collect { case l: Llamada => l.datos.minutos }.foldLeft(0)(_ + _)

  def montoAFacturar(fecha: Fecha): Int = this.aplicarPlanes(comunicaciones(fecha)).foldLeft(0)((r, x) => r + x.precio)
  
  def aplicarPlanes(xs: List[TipoDeComunicacion]): List[TipoDeComunicacion] = planes.foldLeft(xs)((r,p) => p.aplicarPlan(r))

  def cantidadDeComunicacionesLargas(fecha: Fecha): Int = comunicaciones(fecha).count(_.esLarga)

  def ciudadesDeLlamadasLargaDistancia(fecha: Fecha): List[Ciudad] =  comunicaciones(fecha).collect { case Llamada(datos, t: LlamadaLargaDistancia) => datos.ciudadDestino }
  
}