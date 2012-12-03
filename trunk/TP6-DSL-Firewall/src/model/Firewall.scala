package model
import scala.collection.mutable.MutableList
import scala.reflect.BeanProperty
import scalaz._
import Scalaz._
import scala.collection.mutable.Stack

class Firewall {

  val rules: Stack[FirewallRule] = Stack()

  def add(fr: FirewallRule) = rules.push(fr)

  def checkPacket(p: Packet): Option[RuleType] = rules.find(_(p)).map(_.ruleType)

}