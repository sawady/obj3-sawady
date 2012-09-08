package model

trait Plan {
  def aplicarPlan(xs: List[TipoDeComunicacion]): List[TipoDeComunicacion] = xs.map(this.intercambiarPor)
  def intercambiarPor(tipoDeComunicacion: TipoDeComunicacion): TipoDeComunicacion = tipoDeComunicacion
}