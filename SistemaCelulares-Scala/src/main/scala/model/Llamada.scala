package model

case class Llamada(datos: DatosDeLlamada, tipoDeLlamada: TipoDeLlamada) extends TipoDeComunicacion {
  override def precio: Int = datos.minutos * tipoDeLlamada.valorDelMinuto(datos)
  override def esLarga: Boolean = datos.minutos > 5
  override def nroDestino: Int = datos.nroDestino
  
  def cambiarMinutos(minutos: Int): Llamada  = new Llamada(datos.cambiarMinutos(minutos), tipoDeLlamada)
  def cambiarTipo(tNuevo: TipoDeLlamada): Llamada = new Llamada(datos, tNuevo)  
}
