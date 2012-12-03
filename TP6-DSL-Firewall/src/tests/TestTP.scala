package tests
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite

import model.Firewall

class TestTP extends FunSuite with BeforeAndAfter {
  
  before {
    var f = new Firewall()
  }
  
  test("Todos los puertos inferiores al 1024 estan bloqueados. Todos los superiores estan habilitados salvo el 8080." +
  		"Los puertos 80, 23, 25 y 110 deben estar habilitados.") {
//	  	val rule = OneApply(AllApply(Not(LtPort(1024)), Not(EqPort(8080))), EqPort(80), EqPort(23), EqPort(25), EqPort(110))
//	  	val fp = Packet(_: Int, null, null, null)
//	  	List(fp(80)
//	  	, fp(23)
//	  	, fp(25)
//	  	, fp(110)
//	  	, fp(1024)) foreach { p => assert(rule(p)) }
//	  	
//	  	List(fp(8080)
//	  	, fp(1023)) foreach { p => assert(!rule(p)) }
  }
  
  test("No se aceptará ningún mensaje proveniente de la IP 24.35.126.155") {
    
  }

}