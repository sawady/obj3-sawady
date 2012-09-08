package model

case class DatosDeLlamada(fecha: Fecha, minutos: Int, nroDestino: Int, ciudadOrigen: Ciudad, ciudadDestino: Ciudad) {
  def cambiarMinutos(minutosNuevos: Int): DatosDeLlamada = DatosDeLlamada(fecha, minutosNuevos, nroDestino, ciudadOrigen, ciudadDestino)
}
