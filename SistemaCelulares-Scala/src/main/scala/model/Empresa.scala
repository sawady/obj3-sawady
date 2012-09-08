package model

class Empresa {

  val clientes: List[Cliente] = List()

  /* Requerimientos Ej2 */

  def clienteQueMasMinutosHabloEnMes(fecha: Fecha): Cliente = clientes.maxBy(_.cantidadTotalMinutos(fecha))

  def clienteQueMasMinutosHablo(fecha: Fecha): Cliente = clientes.maxBy(_.montoAFacturar(fecha))

  def ciudadesDeLlamadasLargaDistancia(fecha: Fecha): Cliente = clientes.maxBy(_.cantidadDeComunicacionesLargas(fecha))

}