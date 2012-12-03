package model

trait Action {

  def apply(p: Packet)

}

case object NullAction extends Action {

  def apply(p: Packet) = {}

}

case class JoinActions(actions: Seq[Action]) extends Action {
  
  def apply(p: Packet) = actions foreach(_(p))
  
}

case class Redirect(ip: IP) extends Action {

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