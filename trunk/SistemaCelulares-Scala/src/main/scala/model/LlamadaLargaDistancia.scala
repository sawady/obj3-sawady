package model

case class LlamadaLargaDistancia() extends TipoDeLlamada {
  def valorDelMinuto(datos: DatosDeLlamada): Int = PrecioDesdeHacia(datos.ciudadOrigen, datos.ciudadDestino)
}
