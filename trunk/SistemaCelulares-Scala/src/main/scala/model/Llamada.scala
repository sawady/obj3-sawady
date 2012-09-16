package model

case class Llamada(datos: DatosDeLlamada, tipoDeLlamada: TipoDeLlamada) extends Comunicacion {
  
  override val fecha = datos.fecha
  override def precio: Int = datos.minutos * tipoDeLlamada.valorDelMinuto(datos)
  override def esLarga: Boolean = datos.minutos > 5
  override def nroDestino: Int = datos.nroDestino

  def cambiarMinutos(minutos: Int): Llamada = new Llamada(datos.cambiarMinutos(minutos), tipoDeLlamada)

  private def cambiarTipo(tNuevo: TipoDeLlamada): Llamada = new Llamada(datos, tNuevo)
  def cambiarATipoGratuita(): Llamada = this.cambiarTipo(ComunicacionGratuita())

  def esLlamadaGratuita() = datos.minutos <= 0 || (tipoDeLlamada match {
    case ComunicacionGratuita() => true
    case _                      => false
  })
  
}
