package model

trait Action {
  
  def apply(p: Packet)

}

case class JoinActions(actions: Iterable[Action]) extends Action
{
  def apply(p: Packet) = for (a <- actions) a(p)
}

case object NullAction extends Action {
  
  def apply(p: Packet) = {}
  
}

case object InformDest extends Action {  
  
  def apply(p: Packet) = {}
  
}

case object LogMessage extends Action {  
  
  def apply(p: Packet) = {}
  
}

case object LogDeny extends Action {  
  
  def apply(p: Packet) = {}
  
}