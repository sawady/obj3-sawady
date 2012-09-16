package model

trait Comunicacion {
  val fecha: Fecha
  def precio: Int
  def esLarga: Boolean
  def nroDestino: Int
}