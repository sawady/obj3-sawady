package model

class RedirectRule(newP: Packet, rule: Rule, action: Action) {
  
  def apply(p: Packet): Option[Packet] = {
    if (rule(p)) {
      action(p)
      return Some(p)
    }
    return None
  }    
  
}

class SimpleRedirect(newP: Packet, rule: Rule) extends RedirectRule(newP, rule, NullAction)