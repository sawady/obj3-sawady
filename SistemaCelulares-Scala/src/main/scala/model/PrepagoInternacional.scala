package model

class PrepagoInternacional extends Prepago {
  var minutosLibres: Int = 30
  
  override def debeCambiarLlamada(l: Llamada): Boolean = l match {
      case l: LlamadaLargaDistancia => minutosLibres > 0
      case otro => false
  }
}