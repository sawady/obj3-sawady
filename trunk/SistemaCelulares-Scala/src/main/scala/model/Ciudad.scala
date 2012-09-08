package model

abstract class Ciudad
case class BuenosAires() extends Ciudad
case class Lima() extends Ciudad

object PrecioDesdeHacia {
  def apply(desde: Ciudad, hacia : Ciudad): Int = {
    (desde,hacia) match {
      case (BuenosAires(), Lima()) => 250
      case _                       => 100
    }
  }
}