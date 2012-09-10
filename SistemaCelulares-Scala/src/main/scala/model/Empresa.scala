package model
import scala.collection.mutable.HashSet

class Empresa {

  val clientes: HashSet[Cliente] = HashSet()

  /* Requerimientos Ej2 */

  def clienteQueMasMinutosHabloEnMes(fecha: Fecha): Cliente = clientes.maxBy(_.cantidadTotalMinutos(fecha))

  def clienteQueMasMinutosHablo(fecha: Fecha): Cliente = clientes.maxBy(_.montoAFacturar(fecha))

  def ciudadesDeLlamadasLargaDistancia(fecha: Fecha): Cliente = clientes.maxBy(_.cantidadDeComunicacionesLargas(fecha))

}