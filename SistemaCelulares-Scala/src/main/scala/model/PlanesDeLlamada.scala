package model

object PlanesDeLlamada {
  
  def aplicarParaLlamada(p: Llamada => Boolean, f: Llamada => Llamada, t: Comunicacion): Comunicacion = t match {
    case l: Llamada if !l.esLlamadaGratuita() && p(l) => f(l)
    case otro => otro
  }  
  
  def aplicarParaLlamadaGratuita(p: Llamada => Boolean, t: Comunicacion): Comunicacion = aplicarParaLlamada(p, _.cambiarATipoGratuita(), t)

}