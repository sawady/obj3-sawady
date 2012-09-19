package model

trait Prepago extends Plan {
  
  protected def nuevaLlamada(contador: ContadorDeMinutos, l: Llamada): Llamada = {
    if (contador.minutosLibres <= 0) return l

    var aRestar: Int = 0

    if (l.datos.minutos >= contador.minutosLibres) aRestar = contador.minutosLibres
    else aRestar = contador.minutosLibres - l.datos.minutos

    contador.minutosLibres -= aRestar
    return l.cambiarMinutos(l.datos.minutos - aRestar)
  }
  
  class ContadorDeMinutos(var minutosLibres: Int)
  
}