package model

trait FirewallRule {

  def apply(p: Packet): Boolean

}

abstract class Rule(rule: Packet => Boolean) extends FirewallRule {

  def apply(p: Packet): Boolean = rule(p)

}

case class EqPort(n: Int) extends Rule(_.port == n)

case class GtPort(n: Int) extends Rule(_.port > n)

case class LtPort(n: Int) extends Rule(_.port < n)

case class EqIpDest(n: IP) extends Rule(_.destIP == n)

case class EqIpSource(n: IP) extends Rule(_.sourceIP == n)

case class Not(rule: Rule) extends Rule(p => !rule(p))

case class OneApply(rules: Rule*) extends Rule(p => rules exists (_(p)))

case class AllApply(rules: Rule*) extends Rule(p => rules forall (_(p)))

case class ActionRule(rule: Rule, action: Action) extends FirewallRule {

  override def apply(p: Packet): Boolean = {
    if (rule(p)) {
      action(p)
      return true;
    }
    else {
      return false
    }
  }

}