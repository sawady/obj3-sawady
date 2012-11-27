package tests
import org.scalatest.FunSuite
import model.Packet
import model.IP
import model.EqPort
import model.EqIpDest
import model.EqIpSource
import model.GtPort
import model.LtPort
import model.OneApply
import model.AllApply
import model.Not

class TestRules extends FunSuite {
  
  val ipDest = IP(200,1,1,1)
  val ipSource = IP(192,1,1,1)
  val port     = 100
  val body     = "casa"
  
  val p = Packet(port, ipSource, ipDest, body)
  
  test("equal to port") {
    assert(EqPort(port)(p))
  }
  
  test("equal source ip") {
    assert(EqIpSource(ipSource)(p))
  }  
  
  test("equal dest ip") {
    assert(EqIpDest(ipDest)(p))
  }
  
  test("grater than port") {
    assert(GtPort(port-1)(p))
  }
  
  test("lower than port") {
    assert(LtPort(port+1)(p))
  }
  
  test("one rule must apply") {
    assert(OneApply(EqPort(port-10), EqPort(port+10), EqPort(port))(p))
    assert(Not(OneApply(EqPort(port-10), EqPort(port+10)))(p))
  }
  
  test("all rules must apply") {
    assert(AllApply(EqIpSource(ipSource), EqPort(port))(p))
    assert(Not(AllApply(EqPort(port-10), EqPort(port+10), EqPort(port)))(p))
  }
  
}