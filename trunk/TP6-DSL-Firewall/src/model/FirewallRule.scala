package model

abstract class FirewallRule(rule: Rule, action: Action) {

  def apply(p: Packet): Boolean

}

class Accept(rule: Rule) extends OnAccept(rule, NullAction)

class Reject(rule: Rule) extends OnReject(Not(rule), NullAction)

class OnReject(rule: Rule, action: Action) extends FirewallRule(rule, action) {

  def apply(p: Packet): Boolean =
    if (rule(p)) {
      action(p)
      return false
    }
    else {
      return true
    }

}

class OnAccept(rule: Rule, action: Action) extends FirewallRule(rule, action) {

  def apply(p: Packet): Boolean = {
    if (rule(p)) action(p)
    return true
  }

}