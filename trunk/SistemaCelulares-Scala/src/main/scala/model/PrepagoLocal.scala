package model

class PrepagoLocal extends Prepago {
  var minutosLibres: Int = 60
  
  override def debeCambiarLlamada(l: Llamada): Boolean = l match {
      case l: LlamadaLocal => minutosLibres > 0
      case otro => false
  }
}