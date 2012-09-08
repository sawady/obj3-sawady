package model

case class MensajeDeTexto(nroDestino: Int, caracteres: String) extends TipoDeComunicacion {
  override def precio: Int = caracteres.length() / 140 * 10

  override def esLarga: Boolean = caracteres.length() > 140
}
