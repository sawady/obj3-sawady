package model
import scala.collection.mutable.MutableList
import scala.reflect.BeanProperty
import scalaz._
import Scalaz._

class FirewallConfig {

  private var rejections: List[FirewallRule] = List()
  private var permissions: List[FirewallRule] = List()
  private var redirections: List[RedirectRule] = List()

  def addReject(fr: FirewallRule) = rejections ::= fr
  def addPermit(fr: FirewallRule) = permissions ::= fr

  def checkPacket(p: Packet): Boolean = !rejects(p) || permits(p)

  def checkForRedirect(p: Packet): List[Packet] = redirections.flatMap(_(p))

  private def rejects(p: Packet): Boolean = rejections.forall(_(p))

  private def permits(p: Packet): Boolean = permissions.exists(_(p))

}