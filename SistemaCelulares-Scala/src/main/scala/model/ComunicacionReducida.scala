package model

case class ComunicacionReducida(t: TipoDeLlamada) extends TipoDeLlamada {
  def valorDelMinuto(datos: DatosDeLlamada): Int = t.valorDelMinuto(datos)
}