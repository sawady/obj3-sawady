package model

class Plan {
  def aplicarPlan(tipoDeComunicacion: Comunicacion): Comunicacion = tipoDeComunicacion
}

//object AplicarPlanParaLlamada {
//  def apply(p: Llamada => Boolean, f: Llamada => Llamada, t: Comunicacion): Comunicacion = t match {
//    case l: Llamada if !l.esLlamadaGratuita() && p(l) => f(l)
//    case otro => otro
//  }
//}
//
//object AplicarPlanParaLlamadaGratuita {
//  def apply(p: Llamada => Boolean, t: Comunicacion): Comunicacion = AplicarPlanParaLlamada(p, _.cambiarATipoGratuita(), t)
//}