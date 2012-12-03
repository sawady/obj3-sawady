package model

abstract class Rule(rule: Packet => Boolean) {

  def apply(p: Packet): Boolean = rule(p)

}

case class EqPortSource(n: Port) extends Rule(_.source.port == n)

case class EqPortDest(n: Port) extends Rule(_.dest.port == n)

case class GtPortSource(n: Port) extends Rule(_.source.port > n)

case class LtPortSource(n: Port) extends Rule(_.source.port < n)

case class GtPortDest(n: Port) extends Rule(_.dest.port > n)

case class LtPortDest(n: Port) extends Rule(_.dest.port < n)

case class EqIpSource(n: Ip) extends Rule(_.source.ip == n)

case class EqIpDest(n: Ip) extends Rule(_.dest.ip == n)

case class Not(rule: Rule) extends Rule(p => !rule(p))

case class OneApply(rules: Rule*) extends Rule(p => rules exists (_(p)))

case class AllApply(rules: Rule*) extends Rule(p => rules forall (_(p)))