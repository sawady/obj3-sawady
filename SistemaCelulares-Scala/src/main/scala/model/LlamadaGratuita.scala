package model

case class LlamadaGratuita() extends TipoDeLlamada {
  def valorDelMinuto(datos: DatosDeLlamada): Int = 0
}