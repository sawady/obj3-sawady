package tests
import org.scalatest.FunSuite
import model.FirewallConfig
import model.EqPort
import model.Reject

class TestFirewallConfig extends FunSuite {
  
  test("add a firewall rule") {
    val f = new FirewallConfig()
    val fRule = new Reject(EqPort(100))
//    f.addRule(fRule)
//    f.getConfig().contains(fRule)
  }

}