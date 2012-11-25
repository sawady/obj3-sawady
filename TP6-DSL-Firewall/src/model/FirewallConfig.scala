package model

class FirewallConfig {
  
  var config: Iterable[Packet => Unit] = _
  
  def checkPacket(p: Packet): Unit = true

}