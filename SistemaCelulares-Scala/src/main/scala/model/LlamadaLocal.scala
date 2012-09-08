package model

case class LlamadaLocal() extends TipoDeLlamada {
  def valorDelMinuto(datos: DatosDeLlamada): Int = 50
}
