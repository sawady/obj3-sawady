package model

trait Action {

  def apply(p: Packet)

}

class JoinActions(actions: Action*) extends Action {
  
  def apply(p: Packet) = actions foreach(_(p))
  
}

case class Redirect(ip: Socket*) extends Action {

  def apply(p: Packet) = {}

}

object NullAction extends Action {

  def apply(p: Packet) = {}

}

object InformDest extends Action {

  def apply(p: Packet) = {}

}

object LogMessage extends Action {

  def apply(p: Packet) = {}

}

object LogDeny extends Action {

  def apply(p: Packet) = {}

}