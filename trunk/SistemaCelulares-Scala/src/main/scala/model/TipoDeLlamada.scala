package model

trait TipoDeLlamada {
  def valorDelMinuto(datos: DatosDeLlamada): Int
}