package model

case class Fecha(mes: Int, año: Int){
  if(mes > 13 || mes <= 0) {
    throw new IllegalArgumentException("Un mes debe estar entre 1 y 12, recibido " + mes)
  }
  if(año <= 0) {
    throw new IllegalArgumentException("Un año no puede ser negativo, recibido " + año)
  }
  
  def esFinDeSemana(): Boolean = true
}