package model
import scala.collection.mutable.MutableList
import scala.reflect.BeanProperty

class FirewallConfig {
  
  @BeanProperty val reject: MutableList[FirewallRule] = MutableList()
  @BeanProperty val permit: MutableList[FirewallRule] = MutableList()
  
  def addRejectRule(fr: FirewallRule) = reject += fr
  def addPermitRule(fr: FirewallRule) = permit += fr
  
  def checkPacket(p: Packet): Unit = true

}