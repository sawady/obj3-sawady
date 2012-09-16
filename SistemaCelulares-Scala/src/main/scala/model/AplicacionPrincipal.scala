package model

object AplicacionPrincipal {
  def main(args : Array[String]) = {
	  val unaEmpresa = new Empresa()
	   
	  val clienteSawa  = new Cliente(1, new Plan() with PlanCiudadesAmigas with PrepagoLocal with PlanNumerosAmigos {
	    val ciudadAmiga: Ciudad = BuenosAires()
	    val nrosLibres: List[Int] = List(2)
	  })
	  
	  val fechaDummy = Fecha(1,1)
	  clienteSawa.registrarComunicacion(new Llamada(DatosDeLlamada(fechaDummy, 60, 3, BuenosAires(), BuenosAires()),LlamadaLocal()))
	  clienteSawa.registrarComunicacion(new Llamada(DatosDeLlamada(fechaDummy, 60, 2, Lima(), Lima()),LlamadaLocal()))
	  clienteSawa.registrarComunicacion(new Llamada(DatosDeLlamada(fechaDummy, 60, 4, Lima(), Lima()),LlamadaLocal()))
	  
	  println(clienteSawa.montoAFacturar(fechaDummy))
  }
}
