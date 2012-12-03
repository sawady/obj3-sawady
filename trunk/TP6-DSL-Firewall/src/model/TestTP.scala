package model
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import scala.collection.immutable.Queue

class TestTP extends FunSuite with BeforeAndAfter with FirewallDSL {

  var net: Net = _

  before {
    net = new Net()
  }

  test("No se aceptará ningún mensaje proveniente de la IP 24.35.126.155") {

    onFirewall(net.firewall) {
      BLOCK EQ SOURCE(24, 35, 126, 155)
    }

    assert(net.firewall.rules.length === 1)

    assert(net.firewall.rules.exists(r =>
      r.rule == AllApply(List(EqIpSource(IP(24, 35, 126, 155))))
    ))

  }

  test("Filtrar todos los puertos salvo el 80, 443 y 8080") {

    onFirewall(net.firewall) {
      BLOCK ALL (*)
      PASS EQ Ports(80, 443, 8080)
    }

    assert(net.firewall.rules.exists(r =>
      r.rule == AllApply(List(TrueRule)) && r.ruleType == Block
    ))

    assert(net.firewall.rules.exists(r =>
      r.rule == AllApply(List(OneApply(List(EqPort(Port(80)), EqPort(Port(443)), EqPort(Port(8080))))))
        && r.ruleType == Pass
    ))

  }

  test("Todos los puertos inferiores al 1024 estan bloqueados. " +
    "Todos los superiores estan habilitados salvo el 8080." +
    "Los puertos 80, 23, 25 y 110 deben estar habilitados.") {

    onFirewall(net.firewall) {
      BLOCK LT Port(1024)
      PASS GT Port(1024)
      BLOCK EQ Ports(80, 23, 25, 110)
    }

    assert(net.firewall.rules.forall(r =>
      r.action == JoinActions(List())
    ))

    assert(net.firewall.rules.exists(r =>
      r.rule == AllApply(List(LtPort(Port(1024))))
        && r.ruleType == Block
    ))

    assert(net.firewall.rules.exists(r =>
      r.rule == AllApply(List(GtPort(Port(1024))))
        && r.ruleType == Pass
    ))

    assert(net.firewall.rules.exists(r =>
      r.rule == AllApply(List(OneApply(List(EqPort(Port(80)), EqPort(Port(23)), EqPort(Port(25)), EqPort(Port(110))))))
        && r.ruleType == Block
    ))

  }

  test("Redirigir todo los paquetes con destino a 192.168.1.185 a la IP 192.168.1.73") {

    onFirewall(net.firewall) {

      BLOCK EQ DEST(192, 168, 1, 185) REDIRECT (192, 168, 1, 73)

    }

    assert(net.firewall.rules.exists(r =>
      r.action == JoinActions(Queue(Redirect(IP(192, 168, 1, 73))))
    ))

  }

  test("Bloquear todos los mensajes provinientes de las IP 25.78.78.78 y de " +
    "la IP 84.23.43.22. Adem´s se desea loguear todo este tráﬁco") {

    onFirewall(net.firewall) {
      BLOCK EQ SOURCE(25, 78, 78, 78) EQ SOURCE(84, 23, 43, 22) LOG
    }

    assert(net.firewall.rules.exists(r => 
      r.rule == AllApply(List(EqIpSource(IP(25, 78, 78, 78)), EqIpSource(IP(84, 23, 43, 22))))
      && r.action == JoinActions(Queue(LogMessage))
    ))

    net.firewall.rules foreach println
  }

}