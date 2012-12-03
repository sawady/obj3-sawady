package model

abstract class Rule(rule: Packet => Boolean) {

  def apply(p: Packet): Boolean = rule(p)

}

case object TrueRule extends Rule(p => true)

case class EqPort(n: Port) extends Rule(_.port == n)

case class GtPort(n: Port) extends Rule(_.port > n)

case class LtPort(n: Port) extends Rule(_.port < n)

case class EqIpSource(n: IP) extends Rule(_.source == n)

case class EqIpDest(n: IP) extends Rule(_.dest == n)

case class GtIpSource(n: IP) extends Rule(_.source > n)

case class GtIpDest(n: IP) extends Rule(_.dest > n)

case class LtIpSource(n: IP) extends Rule(_.source < n)

case class LtIpDest(n: IP) extends Rule(_.dest < n)

case class Not(rule: Rule) extends Rule(p => !rule(p))

case class OneApply(rules: Seq[Rule]) extends Rule(p => rules exists (_(p)))

case class AllApply(rules: Seq[Rule]) extends Rule(p => rules forall (_(p)))