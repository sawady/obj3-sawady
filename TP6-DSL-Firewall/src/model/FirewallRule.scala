package model

class FirewallRule(val rule: Rule, val ruleType: RuleType, val action: Action) {
  
    def apply(p: Packet): Boolean = {
    if (rule(p)) {
      action(p)
      return true;
    }
    else {
      return false
    }
  }
  
}