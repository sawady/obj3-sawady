package model

object PlanBasico extends Plan {
  override def aplicarPlan(xs: List[TipoDeComunicacion]): List[TipoDeComunicacion] = xs
}