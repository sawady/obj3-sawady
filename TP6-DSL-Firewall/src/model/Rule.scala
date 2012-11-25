package model

abstract class Rule(rule: Packet => Boolean) {
  
  def apply(p: Packet): Boolean = rule(p)

}

class EqPort(n: Int) extends Rule(_.port == n)

class GtPort(n: Int) extends Rule(_.port > n)

class LtPort(n: Int) extends Rule(_.port < n)

class EqIpDest(n: Int) extends Rule(_.destIP == n)

class EqIpSource(n: Int) extends Rule(_.sourceIP == n)

class Not(rule: Rule) extends Rule(p => !rule(p))

class Exists(rules: Iterable[Rule]) extends Rule( p => rules exists(_(p)) )

class ForAll(rules: Iterable[Rule]) extends Rule( p => rules forall(_(p)) )