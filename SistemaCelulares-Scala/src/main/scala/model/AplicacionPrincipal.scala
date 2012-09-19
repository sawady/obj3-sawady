package model

object AplicacionPrincipal {
  def main(args : Array[String]) = {
	  val unaEmpresa = new Empresa()
	   
	  val clienteSawa  = new Cliente(1, new Plan() with PlanCiudadesAmigas with PrepagoInternacional with PlanNumerosAmigos {
	    val ciudadAmiga: Ciudad = BuenosAires()
	    val nrosLibres: List[Int] = List(2)
	  })
	  
	  val fechaDummy = Fecha(10,10)
	  clienteSawa.registrarComunicacion(fechaDummy, new Llamada(DatosDeLlamada(fechaDummy, 60, 3, BuenosAires(), BuenosAires()),LlamadaLocal()))
	  clienteSawa.registrarComunicacion(fechaDummy, new Llamada(DatosDeLlamada(fechaDummy, 60, 2, Lima(), Lima()),LlamadaLocal()))
	  clienteSawa.registrarComunicacion(fechaDummy, new Llamada(DatosDeLlamada(fechaDummy, 60, 4, Lima(), Lima()),LlamadaLargaDistancia()))
	  
	  println(clienteSawa.montoAFacturar(fechaDummy))
	  
//	  RESULTADO
//	  List(Llamada(DatosDeLlamada(Fecha(1,1),30,4,Lima(),Lima()),LlamadaLargaDistancia())
//	  , Llamada(DatosDeLlamada(Fecha(1,1),60,2,Lima(),Lima()),LlamadaGratuita())
//	  , Llamada(DatosDeLlamada(Fecha(1,1),60,3,BuenosAires(),BuenosAires()),LlamadaGratuita()))
//	  MONTO sin planes: 12000, con planes: 3000, porque la tercera se reduce a la mitad en minutos y se multiplica por 100: 30 * 100 = 3000
	  
	  print(ObtenerMejorPlan(clienteSawa, fechaDummy))
//	   MEJOR PLAN HABLATE TODO dado que en el modo son todos fines de semana 
  }
}
