package model

case class MensajeDeTexto(fecha: Fecha, nroDestino: Int, caracteres: String) extends Comunicacion {
  override def precio: Int = caracteres.length() / 140 * 10
  override def esLarga: Boolean = caracteres.length() > 140
}
