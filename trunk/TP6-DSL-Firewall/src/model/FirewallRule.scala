package model

abstract class FirewallRule(rule: Rule, action: Action) {

  def apply(p: Packet): Option[Packet]

}

class OnReject(rule: Rule, action: Action) extends FirewallRule(rule, action) {

  def apply(p: Packet): Option[Packet] =
    if (rule(p)) {
      action(p)
      return None
    }
    else {
      return Some(p)
    }

}

class OnlyReject(rule: Rule) extends OnReject(rule, NullAction)

class OnAccept(rule: Rule, action: Action) extends FirewallRule(rule, action) {

  def apply(p: Packet): Option[Packet] = {
    if (rule(p)) action(p)
    return Some(p)
  }

}