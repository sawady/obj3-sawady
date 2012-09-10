package model

object AplicacionPrincipal {
  def main(args : Array[String]) = {
	  val unaEmpresa = new Empresa()
	   
	  val clienteSawa  = new Cliente(1169328418, PlanBasico)
	  val clienteRocha = new Cliente(555, PlanBasico)	   
  }
}
